package com.example.movieapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.movieapp.Model.Movie;
import com.example.movieapp.Model.TvShow;
import com.example.movieapp.Model.Watchable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Movie_App.db";

    // Tables
    public static final String MOVIE_TABLE_NAME = "movie_table";
    public static final String TV_TABLE_NAME = "tv_table";

    // Columns
    private static final String COL_1 = "ID";
    private static final String COL_2 = "RELEASE_DATE";
    private static final String COL_3 = "TITLE";
    private static final String COL_4 = "RATING";
    private static final String COL_5 = "GENRE";
    private static final String COL_6 = "RELATIVE_BACKDROP_URL";
    private static final String COL_7 = "SYNOPSIS";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MOVIE_TABLE_NAME + " (" + COL_1 + " TEXT PRIMARY KEY, " +
                "" + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT, " + COL_5 + " TEXT, " + COL_6 + " TEXT, " + COL_7 + " TEXT)");

        db.execSQL("create table " + TV_TABLE_NAME + " (" + COL_1 + " TEXT PRIMARY KEY, " +
                "" + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT, " + COL_5 + " TEXT, " + COL_6 + " TEXT, " + COL_7 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean insertWatchable(Watchable watchable, String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, watchable.getId());
        contentValues.put(COL_2, watchable.getReleaseDate());
        contentValues.put(COL_3, watchable.getTitle());
        contentValues.put(COL_4, watchable.getRating());
        contentValues.put(COL_5, watchable.getGenre());
        contentValues.put(COL_6, watchable.getRelativeBackdropURL());
        contentValues.put(COL_7, watchable.getSynopsis());
        return db.insert(tableName, null, contentValues) != -1;
    }

    public boolean isMoviePresent(Movie movie) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + MOVIE_TABLE_NAME + " where " + COL_1 +
                "='" + movie.getId() + "'", null);

        boolean ans = (c.getCount() != 0);
        c.close();
        return ans;
    }

    public boolean removeMovie(Movie movie) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(MOVIE_TABLE_NAME, COL_1 + "=" + movie.getId(), null) > 0;
    }

    public ArrayList<Movie> getMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select *from " + MOVIE_TABLE_NAME, null);
        while (cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.setId(cursor.getString(0));
            movie.setReleaseDate(cursor.getString(1));
            movie.setTitle(cursor.getString(2));
            movie.setRating(cursor.getString(3));
            movie.setGenre(cursor.getString(4));
            movie.setRelativeBackdropURL(cursor.getString(5));
            movie.setSynopsis(cursor.getString(6));
            movies.add(movie);
        }
        cursor.close();
        return movies;
    }

    public boolean isTvShowPresent(TvShow tvShow) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TV_TABLE_NAME + " where " + COL_1 +
                "='" + tvShow.getId() + "'", null);

        boolean ans = (c.getCount() != 0);
        c.close();
        return ans;
    }

    public boolean removeTvShow(TvShow tvShow) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TV_TABLE_NAME, COL_1 + "=" + tvShow.getId(), null) > 0;
    }

    public ArrayList<TvShow> getTvShows() {
        ArrayList<TvShow> tvShows = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select *from " + TV_TABLE_NAME, null);
        while (cursor.moveToNext()) {
            TvShow tvShow = new TvShow();
            tvShow.setId(cursor.getString(0));
            tvShow.setReleaseDate(cursor.getString(1));
            tvShow.setTitle(cursor.getString(2));
            tvShow.setRating(cursor.getString(3));
            tvShow.setGenre(cursor.getString(4));
            tvShow.setRelativeBackdropURL(cursor.getString(5));
            tvShow.setSynopsis(cursor.getString(6));
            tvShows.add(tvShow);
        }
        cursor.close();
        return tvShows;
    }
}

