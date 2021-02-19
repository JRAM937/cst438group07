package com.example.a438project01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchImageActivity extends AppCompatActivity  {
    private ImageView imageView;
    private EditText searchBarText;
    private TextView imageIndexText;
    private Button searchButton;
    private Button leftButton;
    private Button rightButton;
    private Button randomSearchButton;
    private Button randomImageButton;

    private final String KEY = "5589438-47a0bca778bf23fc2e8c5bf3e";
    private final String[] CATEGORY = {"backgrounds","fashion","nature","science","education",
                                        "feelings","health","people","religion","places","animals",
                                        "industry","computer","food","sports","transportation",
                                        "travel","buildings","business","music"};

    private List<Image> hits = new ArrayList<>();
    private int index = 0;
    Call<ImageList> call;

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

        pixabayAPI = retrofit.create(PixabayAPI.class);

        wireupDisplay();
    }

    private void searchForImages(String search) {
        call = pixabayAPI.getImages(KEY, search);

        call.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                if (!response.isSuccessful()) {
                    Log.d("Fail", Integer.toString(response.code()));
                    return;
                }

                ImageList images = response.body();
                if(images != null) {
                    hits = images.getHits();
                    index = 0;
                    changeImageList();
                    changeImageIndexText(index);
                }
            }

            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
                Log.d("Fail", t.getMessage());
            }
        });
    }

    private void randomSearch() {
        String randomCategory = CATEGORY[(int)(Math.random() * CATEGORY.length)];

        call = pixabayAPI.getCategory(KEY, randomCategory);

        call.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                if (!response.isSuccessful()) {
                    Log.d("Fail", Integer.toString(response.code()));
                    return;
                }

                ImageList images = response.body();
                if(images != null) {
                    hits = images.getHits();
                    index = 0;
                    changeImageList();
                    changeImageIndexText(index);
                }
            }

            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
                Log.d("Fail", t.getMessage());
            }
        });
    }

    private void changeImageList() {
        String url = hits.get(index).getPreviewURL();
        Picasso.with(SearchImageActivity.this).load(url)
                .resize(0, 500)
                .into(imageView);
    }

    private void changeImageIndexText(int index) {
        String indexText = (index + 1) + " / " + hits.size();
        imageIndexText.setText(indexText);
    }

    private void moveIndex(int moveByAmount) {
        if(hits.size() != 0) {
            index += moveByAmount;
            if (index < 0)
                index += hits.size();
            if (index >= hits.size())
                index -= hits.size();

            changeImageList();
            changeImageIndexText(index);
        }
    }

    private void goRandom() {
        if(hits.size() != 0) {
            int newIndex = (int) (Math.random() * hits.size());
            while (newIndex == index) {
                newIndex = (int) (Math.random() * hits.size());
            }
            index = newIndex;

            changeImageList();
            changeImageIndexText(newIndex);
        }
    }

    private void wireupDisplay() {
        imageView = findViewById(R.id.image0);
        searchBarText = findViewById(R.id.searchBar);
        imageIndexText = findViewById(R.id.imageIndexText);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        searchButton = findViewById(R.id.searchButton);
        randomSearchButton = findViewById(R.id.randomSearchButton);
        randomImageButton = findViewById(R.id.randomImageButton);

        leftButton.setOnClickListener(v -> moveIndex(-1));
        rightButton.setOnClickListener(v -> moveIndex(1));
        randomImageButton.setOnClickListener(v -> goRandom());

        searchButton.setOnClickListener(v -> {
            String search = searchBarText.getText().toString();

            if (search.isEmpty()) {
                randomSearch();
                goRandom();
            } else {
                searchForImages(search);
            }
        });

        randomSearchButton.setOnClickListener(v -> {
            randomSearch();
            goRandom();
        });
    }
}
