package com.example.movieapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowList {
    @SerializedName("results")
    private List<TvShow> mTvShows;

    public List<TvShow> getTvShows() {
        return mTvShows;
    }
}
