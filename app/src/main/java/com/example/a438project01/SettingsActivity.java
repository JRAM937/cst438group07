package com.example.a438project01;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.a438project01.db.AccountDAO;
import com.example.a438project01.db.AccountDatabase;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class SettingsActivity extends AppCompatActivity {

    //Shared Preferences
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";

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

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_settings);

        getDatabase();
        getUsers();

        setTitle("PicPanda - App Settings");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String user = sharedPreferences.getString(USERNAME, "");

        // Current Account. Use for editing values in the database. ex:
        // mAccount.setPassword(newPass);
        // mAccountDAO.update(mAccount);
        mAccount = mAccountDAO.getUserByUsername(user);

        Button logoutButton = findViewById(R.id.logout_button);
        Button changePassButton = findViewById(R.id.change_pass_button);
        Button deleteAccButton = findViewById(R.id.delete_acc_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder changePassAlert = new AlertDialog.Builder(SettingsActivity.this);
                changePassAlert.setTitle("Change Password");
                changePassAlert.setMessage("What would you like to change your password to?");

                final EditText newPassword = new EditText(SettingsActivity.this);
                // Sets textbox to be password input type
                newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                changePassAlert.setView(newPassword);

                changePassAlert.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String newPass = newPassword.getText().toString();
                        
                        mAccount.setPassword(newPass);
                        mAccountDAO.update(mAccount);
                        Toast.makeText(SettingsActivity.this, "Password changed.", Toast.LENGTH_SHORT).show();
                    }
                });

                changePassAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });

                changePassAlert.show();
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

        //Logic for Notification On/Off switch
       @SuppressLint("UseSwitchCompatOrMaterialCode") Switch notiSwitch = findViewById(R.id.noti_switch);
        notiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Set noti to true
                    mAccount.setNotif(true);
                } else {
                    //set noti to false
                    mAccount.setNotif(false);
                }
                //Update the settings for the current user
                mAccountDAO.update(mAccount);
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
                        mAccountDAO.delete(mAccountDAO.getUserByUsername(user));
                        Toast.makeText(SettingsActivity.this, "Account " + user + " deleted.", Toast.LENGTH_SHORT).show();

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