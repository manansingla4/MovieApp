package com.example.movieapp.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.movieapp.Fragments.MovieFavoriteFragment;
import com.example.movieapp.Fragments.TvShowFavoriteFragment;

public class FavouritePagerAdapter extends FragmentPagerAdapter {

    public FavouritePagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new MovieFavoriteFragment();
            case 1:
                return new TvShowFavoriteFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Movies";
            case 1:
                return "Tv Shows";
        }
        return "";
    }
}
