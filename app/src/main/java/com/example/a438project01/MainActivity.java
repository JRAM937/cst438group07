package com.example.a438project01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< Updated upstream
=======

        //Set title
        setTitle("PicPanda - Welcome!");

        wireupButtons();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        createAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CreateAccountActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    public void wireupButtons() {
        searchButton = findViewById(R.id.search_button);
        loginButton = findViewById(R.id.login_button);
        createAccButton = findViewById(R.id.create_account_button);
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, MainActivity.class);
>>>>>>> Stashed changes
    }
}