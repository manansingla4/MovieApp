package com.example.movieapp;

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
import android.widget.Toast;

import com.example.movieapp.Adapter.MovieAdapter;
import com.example.movieapp.Adapter.TvShowAdapter;
import com.example.movieapp.Model.Movie;
import com.example.movieapp.Model.MovieList;
import com.example.movieapp.Model.TvShow;
import com.example.movieapp.Model.TvShowList;
import com.example.movieapp.Values.Genre;
import com.example.movieapp.Values.URL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowFragment extends Fragment {
    RecyclerView mRecyclerView;
    ArrayList<TvShow> mTvShows = new ArrayList<>();
    TvShowAdapter mAdapter;
    private boolean isLoading = false;
    private int page = 1;
    View mView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mView = view;
        mAdapter = new TvShowAdapter(mTvShows, mView.getContext());
        initRecyclerView();
        initScrollListener();
        LoadItems();
    }

    void initScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                if (!isLoading && layoutManager != null &&
                        layoutManager.findLastVisibleItemPosition() == mTvShows.size() - 1) {
                    LoadItems();
                    isLoading = true;
                }
            }
        });
    }

    void LoadItems() {
        TmdbClient client = MyRetrofit.getRetrofitInstance().create(TmdbClient.class);
        Call<TvShowList> call = client.getPopularTvShows(URL.getApiKey(), page);

        call.enqueue(new Callback<TvShowList>() {
            @Override
            public void onResponse(Call<TvShowList> call, Response<TvShowList> response) {
                if (response.isSuccessful()) {
                    List<TvShow> movies = response.body().getTvShows();
                    if (!mTvShows.isEmpty()) mTvShows.remove(mTvShows.size() - 1);
                    for (TvShow m : movies) {
                        m.setPosterUrl(URL.getPosterUrl(m.getPosterUrl()));
                        m.setReleaseYear(m.getReleaseYear().substring(0, 4));
                        String genres = "";
                        for (int i = 0; i < m.getGenreIds().length; i++) {
                            if (i != 0) genres = genres.concat(", ");
                            genres = genres.concat(Genre.getGenre(m.getGenreIds()[i]));
                        }
                        m.setGenre(genres);
                        mTvShows.add(m);
                    }
                    mTvShows.add(null);
                    notifyAdapter();
                    isLoading = false;
                    if (page < 1000) page++;
                }
            }

            @Override
            public void onFailure(Call<TvShowList> call, Throwable t) {
                Toast.makeText(mView.getContext(), "No connection", Toast.LENGTH_LONG).show();
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
        mAdapter.setShowShimmer(false);
        mAdapter.notifyDataSetChanged();
    }
}
