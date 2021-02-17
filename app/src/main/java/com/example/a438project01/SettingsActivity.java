package com.example.a438project01;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.a438project01.db.AccountDAO;
import com.example.a438project01.db.AccountDatabase;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class SettingsActivity extends AppCompatActivity {
    //Database objects
    private AccountDAO mAccountDAO;
    private Account mAccount;
    List< Account > mAccounts;


    //The following are some helper functions to be called in the onCreate method

    //Get the singular instance of the database
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

    /* Note: This is only for testing out the settings page. The final product should pass in the Account
     * That's currently logged in. This is only for testing purposes until that is implemented.
     * When it is please remove this method and any times its called.
     */
    private void testUsers() {
        Account account = new Account("settingstest", "password");
        mAccount = account;

        mAccountDAO.addAccount(account);
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_settings);

        getDatabase();
        testUsers();
        getUsers();

        setTitle("PicPanda - App Settings");

        Button logoutButton = findViewById(R.id.logout_button);
        Button deleteAccButton = findViewById(R.id.delete_acc_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        
        
        //Easter Egg that takes you to an interesting Pokemon video
        Button easterEgg = findViewById(R.id.e_egg_button);

        easterEgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri youtubeVideo = Uri.parse("https://www.youtube.com/watch?v=EE-xtCF3T94");
                Intent visitLink = new Intent(Intent.ACTION_VIEW, youtubeVideo);
                startActivity(visitLink);
            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch notiSwitch = findViewById(R.id.noti_switch);

        notiSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Does Something
            }
        });

        //Logic for Back Button
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = LandingActivity.intentFactory(getApplicationContext());
                startActivity(i);
            }
        });

        deleteAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteAccPopup = new AlertDialog.Builder(SettingsActivity.this);
                // Popup message asking if the user is sure they want to delete their account.
                // Returns to MainActivity if they do
                deleteAccPopup.setMessage("Are you sure you would like to delete this account?");

                deleteAccPopup.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        mAccountDAO.delete(mAccountDAO.getUserByUsername(mAccount.getUsername()));
                        Toast.makeText(SettingsActivity.this, "Account " + mAccount.getUsername() + " deleted.", Toast.LENGTH_SHORT).show();

                        Intent intent = MainActivity.intentFactory(getApplicationContext());
                        startActivity(intent);
                    }
                });

                deleteAccPopup.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                deleteAccPopup.show();
            }
        });
    }

    // Returns intent for this activity
    public static Intent intentFactory(Context context) {
        return new Intent(context, SettingsActivity.class);
    }
}