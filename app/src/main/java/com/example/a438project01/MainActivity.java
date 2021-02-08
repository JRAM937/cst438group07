package com.example.a438project01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = findViewById(R.id.SearchButton);

        searchButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchImageActivity.class);
            startActivity(intent);
        });
    }
}