package com.example.a438project01;

import android.app.AppComponentFactory;
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

public class LoginActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String GUEST = "guest";

    private String mUsername; //Holds username converted from editText to String
    private String mPassword; //Holds password converted from editText to String

    //user input fields
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button guestButton;
    private Button backButton;

    //Database objects
    private AccountDAO mAccountDAO;
    private Account mAccount;
    List < Account > mAccounts;


    //The following are some helper functions to be called in the onCreate method

    //Get the singular instance of the database
    private void getDatabase() {
        mAccountDAO = Room.databaseBuilder(this, AccountDatabase.class, AccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAccountDAO();
    }


    //Convert edit text to string
    private void getUserInput() {
        mUsername = usernameInput.getText().toString();
        mPassword = passwordInput.getText().toString();
    }


    //Search for user in database. Returns false if username does not exist in database.
    private boolean validateUser() {
        //Retrieve the account from the database if it exists
        mAccount = mAccountDAO.getUserByUsername(mUsername);

        if (mAccount == null) {
            Toast.makeText(this, "No user " + mUsername + " found. ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //Search for password in database. Returns false if password doesn't exist
    private boolean validatePassword() {
        return mAccount.getPassword().equals(mPassword);
    }


    //Creates new intent for this activity
    public static Intent intentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }


    //Username and password for testing purposes only
    private void testUsers() {
        Account account = new Account("test", "password");
        mAccount = account;

        mAccountDAO.addAccount(account);
    }

    //Obtain all users from the database
    private void getUsers() {
        mAccounts = mAccountDAO.getAll();
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);

        setTitle("PicPanda - Login Page");
        getDatabase();
        testUsers();
        getUsers();

        wireDisplay();

    }


    //Logic for username/password check
    private void wireDisplay() {
        usernameInput = findViewById(R.id.editText_username);
        passwordInput = findViewById(R.id.editText_password);

        loginButton = findViewById(R.id.loginButton2);
        guestButton = findViewById(R.id.guestButton);
        backButton = findViewById(R.id.back_button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getUserInput(); //Call helper function to turn user input into strings. Values saved in mUsername and mPassword

                if (validateUser()) {
                    if (!validatePassword()) {
                        Toast.makeText(LoginActivity.this, "Invalid Password. Please Try Again", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(USERNAME, usernameInput.getText().toString());
                        editor.putBoolean(GUEST, false);
                        editor.apply();

                        Intent intent = LandingActivity.intentFactory(getApplicationContext());
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Username. Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(USERNAME, "Rando Calrissian");
                editor.putBoolean(GUEST, true);
                editor.apply();

                Intent intent = SearchImageActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }
}