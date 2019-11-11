package com.example.movieapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BackDropList {
    @SerializedName("backdrops")
    private List<BackDrop> mBackDrops;

    public List<BackDrop> getBackDrops() {
        return mBackDrops;
    }
}
