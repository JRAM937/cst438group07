package com.example.a438project01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button searchButton;
    private Button loginButton;
    private Button createAccButton;

    //Controls if notifications are to be showed or not
    private boolean notis = true;

    //Get and Set methods for notis when favoriting an image
    public boolean isNotis() {
        return notis;
    }

    public void setNotis(boolean notis) {
        this.notis = notis;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        searchButton.setOnClickListener(v -> {
            Intent intent = SearchImageActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        });

    }

    public void wireupButtons() {
        searchButton = findViewById(R.id.search_button);
        loginButton = findViewById(R.id.login_button);
        createAccButton = findViewById(R.id.create_account_button);
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, MainActivity.class);
    }
}