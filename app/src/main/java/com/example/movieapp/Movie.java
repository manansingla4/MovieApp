package com.example.movieapp;

import org.json.JSONException;
import org.json.JSONObject;

class Movie {
    private String mName;
    private String mReleaseYear;
    private String mRating;
    private String mGenre;
    private String mPosterUrl;
    private String mId;

    private Movie(String name, String releaseYear, String rating, String genre, String posterUrl, String id) {
        mName = name;
        mReleaseYear = releaseYear;
        mRating = rating;
        mGenre = genre;
        mPosterUrl = posterUrl;
        mId = id;
    }

    String getPosterUrl() {
        return mPosterUrl;
    }

    String getName() {
        return mName;
    }

    String getReleaseYear() {
        return mReleaseYear;
    }

    String getRating() {
        return mRating;
    }

    String getGenre() {
        return mGenre;
    }

    String getId() { return mId; }

    static Movie getMovie(JSONObject object) {

        String name = "";
        String release = "";
        String rating = "";
        String genre = "";
        String url = "";
        String id = "";

        try {
            name = object.getString("title");
            release = object.getString("release_date").substring(0, 4);
            rating = object.getString("vote_average");
            url = URL.getPosterUrl(object.getString("poster_path"));
            id = object.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Movie(name, release, rating, genre, url, id);
    }
}
