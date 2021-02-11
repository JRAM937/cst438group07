package com.example.a438project01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchImageActivity extends AppCompatActivity  {
    //private final int AMOUNT = 3;

    //private ImageView[] imageView = new ImageView[AMOUNT];
    private ImageView imageView;
    private EditText searchBarText;
    private Button searchButton;

    private Button leftButton;
    private Button rightButton;

    private List<Image> hits;
    private int index = 0;

    private String key = "5589438-47a0bca778bf23fc2e8c5bf3e";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://pixabay.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private PixabayAPI pixabayAPI;

    public static Intent intentFactory(Context context) {
        return new Intent(context, SearchImageActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*
        for(int i = 0; i < AMOUNT; i++) {
            imageView[i] = findViewById(R.);
        }*/

        imageView = findViewById(R.id.image0);
        searchBarText = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);

        pixabayAPI = retrofit.create(PixabayAPI.class);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = searchBarText.getText().toString();

                if(search.isEmpty()) {
                    searchForImages(key,"sun");
                }
                else {
                    searchForImages(key, search);
                }
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLeft();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRight();
            }
        });
    }

    private void searchForImages(String key, String search) {
        Call<ImageList> call = pixabayAPI.getImages(key, search);

        call.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                if (!response.isSuccessful()) {
                    Log.d("Fail", Integer.toString(response.code()));
                    return;
                }

                ImageList images = response.body();
                hits = images.getHits();

                index = 0;
                String url = hits.get(index).getPreviewURL();
                Picasso.with(SearchImageActivity.this).load(url)
                        .resize(0,500)
                        .into(imageView);
            }

            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
                Log.d("Fail", t.getMessage());
            }
        });
    }

    private void goLeft() {
        if(!hits.isEmpty()) {
            index--;
            if (index < 0)
                index += hits.size();

            String url = hits.get(index).getPreviewURL();
            Picasso.with(SearchImageActivity.this).load(url)
                    .resize(0, 500)
                    .into(imageView);
        }
    }

    private void goRight() {
        if(!hits.isEmpty()) {
            index++;
            if (index >= hits.size())
                index -= hits.size();

            String url = hits.get(index).getPreviewURL();
            Picasso.with(SearchImageActivity.this).load(url)
                    .resize(0, 500)
                    .into(imageView);
        }
    }
}
