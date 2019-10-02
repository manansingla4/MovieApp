package com.example.movieapp;

import com.example.movieapp.Model.MovieList;
import com.example.movieapp.Model.TvShowList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbClient {
    @GET("/3/movie/popular")
    Call<MovieList> getPopularMovies(@Query("api_key") String API_KEY,
                                     @Query("page") int page);

    @GET("/3/tv/popular")
    Call<TvShowList> getPopularTvShows(@Query("api_key") String API_KEY,
                                       @Query("page") int page);
}
