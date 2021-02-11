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

    private String mUsername;
    private String mPassword;

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;

    private AccountDAO mAccountDAO;
    private Account mAccount;
    List<Account> mAccounts;

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

    private void getDatabase() {
        mAccountDAO = Room.databaseBuilder(this, AccountDatabase.class, AccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAccountDAO();
    }

    private void getUsers() { mAccounts = mAccountDAO.getAll(); }

    private void wireDisplay() {
        usernameInput = findViewById(R.id.editText_username);
        passwordInput = findViewById(R.id.editText_password);

        loginButton = findViewById(R.id.loginButton2);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();

                if (checkForUserInDatabase()) {
                    if(!validatePassword()) {
                        Toast.makeText(LoginActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(USERNAME, usernameInput.getText().toString());
                        editor.apply();

                        Intent intent = LandingActivity.intentFactory(getApplicationContext());
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void getValuesFromDisplay() {
        mUsername = usernameInput.getText().toString();
        mPassword = passwordInput.getText().toString();
    }

    private boolean checkForUserInDatabase() {
        mAccount = mAccountDAO.getUserByUsername(mUsername);

        if (mAccount == null) {
            Toast.makeText(this, "No user " + mUsername + " found. ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePassword() { return mAccount.getPassword().equals(mPassword); }

    public static Intent intentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    private void testUsers() {
        Account account = new Account("test", "password");
        mAccount = account;

        mAccountDAO.addAccount(account);
    }
}
