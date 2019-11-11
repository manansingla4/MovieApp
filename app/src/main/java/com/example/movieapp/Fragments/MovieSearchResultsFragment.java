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

import com.example.movieapp.Adapter.MovieAdapter;
import com.example.movieapp.Interfaces.Observer;
import com.example.movieapp.Model.Movie;
import com.example.movieapp.Model.MovieList;
import com.example.movieapp.R;
import com.example.movieapp.Util.Genre;
import com.example.movieapp.Util.MyRetrofit;
import com.example.movieapp.Util.TmdbClient;
import com.example.movieapp.Util.URL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieSearchResultsFragment extends Fragment implements Observer {

    RecyclerView mRecyclerView;
    ArrayList<Movie> mMovies = new ArrayList<>();
    MovieAdapter mAdapter;
    String currentQuery = "";
    View mView;
    Call currentCall;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        mAdapter = new MovieAdapter(mMovies, mView.getContext());
        mAdapter.setShowShimmer(false);
        initRecyclerView();
    }

    void LoadItems() {
        if (currentCall != null) {
            currentCall.cancel();
        }
        if (currentQuery.isEmpty()) {
            mMovies.clear();
            notifyAdapter();
            return;
        }
        TmdbClient client = MyRetrofit.getRetrofitInstance().create(TmdbClient.class);
        Call<MovieList> call = client.searchMovies(URL.getApiKey(), currentQuery);
        currentCall = call;
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    mMovies.clear();
                    List<Movie> movies = response.body().getMovies();
                    for (Movie m : movies) {
                        String genres = "";
                        for (int i = 0; i < m.getGenreIds().length; i++) {
                            if (i != 0) genres = genres.concat(", ");
                            genres = genres.concat(Genre.getGenre(m.getGenreIds()[i]));
                        }
                        m.setGenre(genres);
                        mMovies.add(m);
                    }
                    notifyAdapter();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
            }
        });
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

    @Override
    public void update(String query) {
        currentQuery = query;
        LoadItems();
    }
}
