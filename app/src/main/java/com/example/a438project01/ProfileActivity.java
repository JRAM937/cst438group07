package com.example.a438project01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";

    private TextView profileName;
    private TextView profileBio;
    private TextView pictures;
    private Button editProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Set title
        setTitle("PicPanda - Profile Page");

        wireupDisplay();

        //Set shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String user = sharedPreferences.getString(USERNAME, "");

        String profilePage = user + "'s Profile Page";
        profileName.setText(profilePage);

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

    public static Intent intentFactory(Context context) {
        return new Intent(context, ProfileActivity.class);
    }
}
