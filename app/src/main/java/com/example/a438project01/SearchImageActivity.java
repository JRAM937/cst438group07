package com.example.a438project01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchImageActivity extends AppCompatActivity  {

    private TextView textViewResult;

    private String key = "5589438-47a0bca778bf23fc2e8c5bf3e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        textViewResult = findViewById(R.id.textViewResult);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pixabay.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PixabayAPI pixabayAPI = retrofit.create(PixabayAPI.class);

        //Call<ImageList> call = pixabayAPI.getImages(key,"anything");
        Call<ImageList> call = pixabayAPI.getSome();

        call.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                if(!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                ImageList images = response.body();
                List<Image> hits = images.getHits();

                for(Image image : hits) {
                    String content = "";
                    content += "ID: " + image.getId() + "\n";
                    content += "User ID: " + image.getPageURL() + "\n";
                    content += "Title: " + image.getTags() + "\n";
                    content += "Text: " + image.getType() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
                textViewResult.setText(t.getMessage());
                Log.d("Hello", "oh it fails");
            }
        });
    }
}
