package com.example.a438project01;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageList {
    @SerializedName("total")
    private int total;

    @SerializedName("totalHits")
    private int totalHits;

    @SerializedName("hits")
    private List<Image> hits;

    public int getTotal() { return total; }

    public int getTotalHits() { return totalHits; }

    public List<Image> getHits() {
        return hits;
    }
}
