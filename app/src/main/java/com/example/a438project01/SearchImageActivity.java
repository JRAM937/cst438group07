package com.example.a438project01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private final String KEY = "5589438-47a0bca778bf23fc2e8c5bf3e";
    private final String[] CATEGORY = {"backgrounds","fashion","nature","science","education",
                                        "feelings","health","people","religion","places","animals",
                                        "industry","computer","food","sports","transportation",
                                        "travel","buildings","business","music"};

    // Shared Preferences
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String GUEST = "guest";

    private ImageView imageView;
    private EditText searchBarText;
    private Button searchButton;
    private Button backButton;
    private TextView imageIndexText;
    private TextView userTextView;

    private List<Image> hits = new ArrayList<Image>();
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

        setTitle("PicPanda - Search Image");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String user = sharedPreferences.getString(USERNAME, "");
        // Use isGuest for favoriting image. if isGuest is true do not allow the user to favorite an image.
        // Going back should also go back to the MainActivity and not the LandingPage
        boolean isGuest = sharedPreferences.getBoolean(GUEST, false);

        Button leftButton;
        Button rightButton;
        Button randomSearchButton;
        Button randomImageButton;

        imageView = findViewById(R.id.image0);
        searchBarText = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);
        backButton = findViewById(R.id.backButton);
        imageIndexText = findViewById(R.id.imageIndexText);
        userTextView = findViewById(R.id.userTextView);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        randomSearchButton = findViewById(R.id.randomSearchButton);
        randomImageButton = findViewById(R.id.randomImageButton);

        // Displays image at the top of the page
        userTextView.setText("Searching for image as " + user);

        pixabayAPI = retrofit.create(PixabayAPI.class);

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

        backButton.setOnClickListener(v -> {
            // If the user is a guest it should always go back to the MainActivity since it doesn't have a landing page
            if (isGuest) {
                Intent intent = MainActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            } else {
                Intent intent = LandingActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        leftButton.setOnClickListener(v -> goLeft());
        rightButton.setOnClickListener(v -> goRight());
        randomImageButton.setOnClickListener(v -> goRandom());
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
                if(images.getTotalHits() != 0) {
                    hits = images.getHits();
                    index = 0;
                    changeImageList();
                    changeImageIndexText(index);
                }

                Log.d("Hey", "This image's done!");
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
                hits = images.getHits();
                index = 0;
                changeImageList();
                changeImageIndexText(index);
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
        imageIndexText.setText((index + 1) + " / " + hits.size());
    }

    private void goLeft() {
        if(hits.size() != 0) {
            index--;
            if (index < 0)
                index += hits.size();

            changeImageList();
            changeImageIndexText(index);
        }
    }

    private void goRight() {
        if(hits.size() != 0) {
            index++;
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
}
