package com.example.movieapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.movieapp.Activities.MainActivity;
import com.example.movieapp.R;

public class RetryFragment extends Fragment {

    Button mRetryButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.no_connection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRetryButton = view.findViewById(R.id.retry_button);
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                assert activity != null;
                if (activity.getCurrent_fragment() == MainActivity.MOVIES) {
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new MovieListFragment())
                            .commit();
                } else {
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new TvShowListFragment())
                            .commit();
                }
            }
        });
    }
}
