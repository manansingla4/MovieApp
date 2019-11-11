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

import com.example.movieapp.Adapter.TvShowAdapter;
import com.example.movieapp.Interfaces.Observer;
import com.example.movieapp.Model.TvShow;
import com.example.movieapp.Model.TvShowList;
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

public class TvShowSearchResultsFragment extends Fragment implements Observer {
    RecyclerView mRecyclerView;
    ArrayList<TvShow> mTvShows = new ArrayList<>();
    TvShowAdapter mAdapter;
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
        mAdapter = new TvShowAdapter(mTvShows, mView.getContext());
        mAdapter.setShowShimmer(false);
        initRecyclerView();
    }

    void LoadItems() {
        if (currentCall != null) {
            currentCall.cancel();
        }
        if (currentQuery.isEmpty()) {
            mTvShows.clear();
            notifyAdapter();
            return;
        }
        TmdbClient client = MyRetrofit.getRetrofitInstance().create(TmdbClient.class);
        Call<TvShowList> call = client.searchTvShows(URL.getApiKey(), currentQuery);
        currentCall = call;
        call.enqueue(new Callback<TvShowList>() {
            @Override
            public void onResponse(@NonNull Call<TvShowList> call, @NonNull Response<TvShowList> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    mTvShows.clear();
                    List<TvShow> tvShows = response.body().getTvShows();
                    for (TvShow m : tvShows) {
                        String genres = "";
                        for (int i = 0; i < m.getGenreIds().length; i++) {
                            if (i != 0) genres = genres.concat(", ");
                            genres = genres.concat(Genre.getGenre(m.getGenreIds()[i]));
                        }
                        m.setGenre(genres);
                        mTvShows.add(m);
                    }
                    notifyAdapter();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowList> call, @NonNull Throwable t) {
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
