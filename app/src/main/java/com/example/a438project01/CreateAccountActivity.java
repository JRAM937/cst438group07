package com.example.a438project01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a438project01.db.AccountDAO;
import com.example.a438project01.db.AccountDatabase;

import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {
    private String mUsername;
    private String mPassword;

    private EditText usernameInput;
    private EditText passwordInput;
    private Button createAccountButton;
    private Button backButton;

    private AccountDAO mAccountDAO;
    List<Account> mAccounts;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_create_account);

        setTitle("PicPanda - Create Account Page");
        getDatabase();
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

        createAccountButton = findViewById(R.id.create_account_button);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();

                if (!validateUsername()) {
                    Toast.makeText(CreateAccountActivity.this, "Username already exists.", Toast.LENGTH_SHORT).show();
                } else {
                    mAccountDAO.addAccount(new Account(mUsername, mPassword));

                    Toast.makeText(CreateAccountActivity.this, "Account created.", Toast.LENGTH_SHORT).show();

                    Intent intent = LoginActivity.intentFactory(getApplicationContext());
                    startActivity(intent);
                }
            }
        });

        backButton = findViewById(R.id.back_button_create_acc);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void getValuesFromDisplay() {
        mUsername = usernameInput.getText().toString();
        mPassword = passwordInput.getText().toString();
    }

    // Returns true if the username isn't already used in the database
    private boolean validateUsername() { return mAccountDAO.getUserByUsername(mUsername) == null; }

    public static Intent intentFactory(Context context) {
        return new Intent(context, CreateAccountActivity.class);
    }
}