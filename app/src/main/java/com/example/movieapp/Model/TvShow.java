package com.example.movieapp.Model;

import com.example.movieapp.Values.URL;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TvShow implements Serializable, Watchable {
    @SerializedName("first_air_date")
    private String mReleaseDate;
    @SerializedName("name")
    private String mTitle;
    @SerializedName("vote_average")
    private String mRating;
    private String mGenre;
    @SerializedName("poster_path")
    private String mReativePosterUrl;
    @SerializedName("backdrop_path")
    private String mRelativeBackdropURL;
    @SerializedName("id")
    private String mId;
    @SerializedName("genre_ids")
    private Integer[] mGenreIds;
    @SerializedName("overview")
    private String synopsis;


    @Override
    public String getRelativeBackdropURL() {
        return mRelativeBackdropURL;
    }

    public void setRelativeBackdropURL(String relativeBackdropURL) {
        mRelativeBackdropURL = relativeBackdropURL;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer[] getGenreIds() {
        return mGenreIds;
    }

    public String getRelativePosterUrl() {
        return mReativePosterUrl;
    }

    public String getBackdropURL() {
        return URL.getPosterUrl(mRelativeBackdropURL);
    }

    public String getTitle() {
        return mTitle;
    }


    public String getRating() {
        return mRating;
    }

    public String getGenre() {
        return mGenre;
    }

    public String getId() {
        return mId;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    public void setRating(String rating) {
        mRating = rating;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }


    public void setId(String id) {
        mId = id;
    }

    public void setGenreIds(Integer[] genreIds) {
        mGenreIds = genreIds;
    }

    public String getReleaseYear() {
        if (mReleaseDate != null && !mReleaseDate.isEmpty())
            return mReleaseDate.substring(0, 4);
        return "";
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

}
