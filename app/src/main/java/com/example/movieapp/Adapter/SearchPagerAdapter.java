package com.example.movieapp.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.movieapp.Fragments.MovieSearchResultsFragment;
import com.example.movieapp.Fragments.TvShowSearchResultsFragment;
import com.example.movieapp.Interfaces.Subject;

public class SearchPagerAdapter extends FragmentPagerAdapter {
    private Subject mSubject;

    public SearchPagerAdapter(FragmentManager fm, Subject subject) {
        super(fm);
        mSubject = subject;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                MovieSearchResultsFragment movieFragment = new MovieSearchResultsFragment();
                mSubject.register(movieFragment);
                return movieFragment;
            case 1:
                TvShowSearchResultsFragment tvShowfragment = new TvShowSearchResultsFragment();
                mSubject.register(tvShowfragment);
                return tvShowfragment;
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
