package com.example.a438project01;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageList {
    @SerializedName("hits")
    private List<Image> hits;

    public List<Image> getHits() {
        return hits;
    }
}
