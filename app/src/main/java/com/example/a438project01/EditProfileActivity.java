package com.example.a438project01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.a438project01.db.AccountDAO;
import com.example.a438project01.db.AccountDatabase;

import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";

    EditText bio;
    Button saveButton;

    private String newBio;
    private String username;

    private AccountDAO mAccountDAO;
    private Account mAccount;
    List<Account> mAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setTitle("Edit Profile Page");

        //Set shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String user = sharedPreferences.getString(USERNAME, "");
        username = user;

        getDatabase();
        getUsers();
        getSpecificUser();

        wireupDisplay();

        bio.setText(mAccount.getBio());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInput();
                mAccount.setBio(newBio);

                mAccountDAO.update(mAccount);

                Intent intent = ProfileActivity.intentFactory(getApplicationContext());
                String edit = "changed";
                intent.putExtra("changed", edit);
                finish();
                startActivity(intent);
            }
        });
    }

    public void wireupDisplay() {
        bio = findViewById(R.id.editTextBio);
        saveButton = findViewById(R.id.saveButton);
    }

    private void getInput() {
        newBio = bio.getText().toString();
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
        return new Intent(context, EditProfileActivity.class);
    }
}
