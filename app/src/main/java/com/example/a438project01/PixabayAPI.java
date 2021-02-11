package com.example.a438project01;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PixabayAPI {
    @GET("api/")
    Call<ImageList> getImages(
            @Query("key") String key,
            @Query("q") String tag
    );

    @GET("api/?key=5589438-47a0bca778bf23fc2e8c5bf3e&q=sun")
    Call<ImageList> getSome();
}
