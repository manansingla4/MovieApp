package com.example.movieapp.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieList {
    @SerializedName("results")
    private List<Movie> mMovies;

    public List<Movie> getMovies() {
        return mMovies;
    }
}
