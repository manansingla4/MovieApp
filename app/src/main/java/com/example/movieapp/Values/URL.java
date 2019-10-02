package com.example.movieapp.Values;

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

    public static String getPosterUrl(String relative) {
        return String.format("%s%s", baseImageUrl, relative);
    }
}
