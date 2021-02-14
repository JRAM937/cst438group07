package com.example.a438project01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.a438project01.db.AccountDAO;
import com.example.a438project01.db.AccountDatabase;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";

    private TextView profileName;
    private TextView profileBio;
    private TextView pictures;
    private Button editProfile;

    private AccountDAO mAccountDAO;
    private Account mAccount;
    List<Account> mAccounts;
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Set title
        setTitle("PicPanda - Profile Page");

        wireupDisplay();
        getDatabase();
        getUsers();

        //Set shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String user = sharedPreferences.getString(USERNAME, "");
        username = user;
        getSpecificUser();

        String profilePage = user + "'s Profile Page";
        profileName.setText(profilePage);

        //Check for intent extras
        Intent prevIntent = getIntent();
        prevIntent.getExtras();

        //Display toast based on the extra
        if(prevIntent.hasExtra("changed")) {
            Toast.makeText(ProfileActivity.this, "Bio has been edited.", Toast.LENGTH_SHORT).show();
        }

        profileBio.setText(returnBio(mAccount));

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EditProfileActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    public void wireupDisplay() {
        profileName = findViewById(R.id.topTextView);
        profileBio = findViewById(R.id.textViewBio);
        pictures = findViewById(R.id.text_view_result);
        editProfile = findViewById(R.id.editProfileButton);
    }

    private String returnBio(Account acc) {
        if (acc.getBio() == null) {
            acc.setBio("Bio not added");
        }
        return acc.getBio();
    }

    private void getDatabase() {
        mAccountDAO = Room.databaseBuilder(this, AccountDatabase.class, AccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAccountDAO();
    }

    //Obtain all users from the database
    private void getUsers() {
        mAccounts = mAccountDAO.getAll();
    }

    private void getSpecificUser() {
        mAccount = mAccountDAO.getUserByUsername(username);
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, ProfileActivity.class);
    }
}
