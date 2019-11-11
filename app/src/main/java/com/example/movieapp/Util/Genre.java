package com.example.movieapp.Util;

public class Genre {
    public static String getGenre(int id) {
        String res = "";
        switch (id){
            case 28 : res = "Action"; break;
            case 12 : res = "Adventure"; break;
            case 16 : res = "Animation"; break;
            case 35 : res = "Comedy"; break;
            case 80 : res = "Crime"; break;
            case 99 : res = "Documentary"; break;
            case 18 : res = "Drama"; break;
            case 10751 : res = "Family"; break;
            case 14 : res = "Fantasy"; break;
            case 36 : res = "History"; break;
            case 27 : res = "Horror"; break;
            case 10402 : res = "Music"; break;
            case 9648 : res = "Mystery"; break;
            case 10749 : res = "Romance"; break;
            case 878 : res = "Science Fiction"; break;
            case 10770 : res = "TV Movie"; break;
            case 53 : res = "Thriller"; break;
            case 10752 : res = "War"; break;
            case 37 : res = "Western"; break;
            case 10759 : res = "Action & Adventure"; break;
            case 10762: res = "Kids"; break;
            case 10763 : res = "News"; break;
            case 10764 : res = "Reality"; break;
            case 10765 : res = "Sci-Fi & Fantasy"; break;
            case 10766 : res = "Soap"; break;
            case 10767 : res = "Talk"; break;
            case 10768 : res = "War & Politics"; break;
        }
        return res;
    }
}
