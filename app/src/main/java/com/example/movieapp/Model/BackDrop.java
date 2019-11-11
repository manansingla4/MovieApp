package com.example.movieapp.Model;

import com.google.gson.annotations.SerializedName;

public class BackDrop {
    @SerializedName("file_path")
    private String file_path;

    public String getUrl() {
        return "https://image.tmdb.org/t/p/w780" + file_path;
    }
}
