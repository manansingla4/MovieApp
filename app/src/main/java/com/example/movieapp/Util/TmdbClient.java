package com.example.movieapp.Util;

import com.example.movieapp.Model.BackDropList;
import com.example.movieapp.Model.MovieList;
import com.example.movieapp.Model.TvShowList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface TmdbClient {
    @GET("/3/movie/popular")
    Call<MovieList> getPopularMovies(@Query("api_key") String API_KEY,
                                     @Query("page") int page);

    @GET("/3/movie/top_rated")
    Call<MovieList> getTopMovies(@Query("api_key") String API_KEY,
                                 @Query("page") int page);

    @GET("/3/tv/popular")
    Call<TvShowList> getPopularTvShows(@Query("api_key") String API_KEY,
                                       @Query("page") int page);

    @GET("/3/tv/top_rated")
    Call<TvShowList> getTopTvShows(@Query("api_key") String API_KEY,
                                   @Query("page") int page);

    @GET("/3/search/movie?adult=false")
    Call<MovieList> searchMovies(@Query("api_key") String API_KEY,
                                 @Query("query") String queryString);

    @GET("/3/search/tv?adult=false")
    Call<TvShowList> searchTvShows(@Query("api_key") String API_KEY,
                                   @Query("query") String queryString);

    @GET
    Call<BackDropList> getBackDrops(@Url String url);
}
