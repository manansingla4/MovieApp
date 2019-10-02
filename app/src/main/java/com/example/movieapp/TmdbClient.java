package com.example.movieapp;

import com.example.movieapp.Model.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbClient {
    @GET("/3/movie/popular")
    Call<MovieList> getPopularMovies(@Query("api_key") String API_KEY,
                                     @Query("page") int page);
}
