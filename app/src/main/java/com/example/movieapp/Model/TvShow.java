package com.example.movieapp.Model;

import com.google.gson.annotations.SerializedName;

public class TvShow {
    @SerializedName("name")
    private String mTitle;
    @SerializedName("first_air_date")
    private String mReleaseYear;
    @SerializedName("vote_average")
    private String mRating;
    private String mGenre;
    @SerializedName("poster_path")
    private String mPosterUrl;
    @SerializedName("id")
    private String mId;
    @SerializedName("genre_ids")
    private Integer[] mGenreIds;

    public Integer[] getGenreIds() {
        return mGenreIds;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getReleaseYear() {
        return mReleaseYear;
    }

    public String getRating() {
        return mRating;
    }

    public String getGenre() {
        return mGenre;
    }

    public String getId() { return mId; }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setReleaseYear(String releaseYear) {
        mReleaseYear = releaseYear;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public void setPosterUrl(String posterUrl) {
        mPosterUrl = posterUrl;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setGenreIds(Integer[] genreIds) {
        mGenreIds = genreIds;
    }

}
