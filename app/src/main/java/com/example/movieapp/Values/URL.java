package com.example.movieapp.Values;

import com.loopj.android.http.RequestParams;

public class URL {
    private static String baseUrl = "http://api.themoviedb.org/";
    private static final String API_KEY = "ca9c3957bf4c2ba09439fdbf8dc06127";
    private static String baseImageUrl = "http://image.tmdb.org/t/p/w500/";

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getUrl_popular(int page) {
        RequestParams params = new RequestParams();
        params.put("api_key", API_KEY);
        params.put("page", page);
        params.put("language", "en-US");
        return baseUrl + "popular?" + params;
    }

    public static String getUrl_MovieDetails(String id) {
        RequestParams params = new RequestParams();
        params.put("api_key", API_KEY);
        params.put("language", "en-US");
        return baseUrl + id + "?" + params;
    }

    public static String getPosterUrl(String relative) {
        return String.format("%s%s", baseImageUrl, relative);
    }
}
