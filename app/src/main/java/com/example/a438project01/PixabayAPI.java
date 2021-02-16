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

    @GET("api/")
    Call<ImageList> getCategory(
            @Query("key") String key,
            @Query("category") String category
    );
}
