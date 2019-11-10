package com.example.movieapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.movieapp.Adapter.MovieAdapter;
import com.example.movieapp.DataBaseHelper;
import com.example.movieapp.Model.Movie;
import com.example.movieapp.R;

import java.util.ArrayList;

public class MovieFavoriteFragment extends Fragment {
    RecyclerView mRecyclerView;
    ArrayList<Movie> mMovies = new ArrayList<>();
    MovieAdapter mAdapter;
    View mView;
    TextView mTextView;
    DataBaseHelper mDataBaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favorite_list_fragment, container, false);
        mTextView = v.findViewById(R.id.empty_list);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        mDataBaseHelper = new DataBaseHelper(mView.getContext());
        mAdapter = new MovieAdapter(mMovies, mView.getContext());
        mAdapter.setShowShimmer(false);
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadItems();
        notifyAdapter();
    }

    void LoadItems() {
        mTextView.setVisibility(View.INVISIBLE);
        mMovies.clear();
        mMovies.addAll(mDataBaseHelper.getMovies());
        if (mMovies.size() == 0) {
            mTextView.setVisibility(View.VISIBLE);
        }
    }

    void initRecyclerView() {
        mRecyclerView = mView.findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mView.getContext(), 0));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    void notifyAdapter() {
        mAdapter.notifyDataSetChanged();
    }
}
