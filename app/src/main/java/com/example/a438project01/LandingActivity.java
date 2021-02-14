package com.example.a438project01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LandingActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";

    private TextView welcome;
    private Button searchButton;
    private Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        //Set title
        setTitle("PicPanda - Logged In");

        wireupButtons();

        //Set shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String user = sharedPreferences.getString(USERNAME, "");

        String welcomeString = "Thanks for logging in, " + user + "!";
        welcome.setText(welcomeString);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SearchImageActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ProfileActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public void wireupButtons() {
        welcome = findViewById(R.id.landingTextView);
        searchButton = findViewById(R.id.landingSearchButton);
        profileButton = findViewById(R.id.landingProfileButton);
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, LandingActivity.class);
    }
}
