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
import com.example.movieapp.Model.Movie;
import com.example.movieapp.Model.MovieList;
import com.example.movieapp.R;
import com.example.movieapp.Util.MyRetrofit;
import com.example.movieapp.Util.TmdbClient;
import com.example.movieapp.Values.Genre;
import com.example.movieapp.Values.URL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListFragment extends Fragment {
    RecyclerView mRecyclerView;
    ArrayList<Movie> mMovies = new ArrayList<>();
    MovieAdapter mAdapter;
    private boolean isLoading = false;
    private int page = 1;
    View mView;
    private static boolean show_popular;


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
        mAdapter.setRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setShowRetry(false);
                mAdapter.notifyItemChanged(mMovies.size() - 1);
                LoadItems();
            }
        });
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
                if (!isLoading && layoutManager != null && layoutManager.findLastVisibleItemPosition() == mMovies.size() - 1) {
                    LoadItems();
                    isLoading = true;
                }
            }
        });
    }

    void LoadItems() {
        TmdbClient client = MyRetrofit.getRetrofitInstance().create(TmdbClient.class);
        Call<MovieList> call;
        if (show_popular) {
            call = client.getPopularMovies(URL.getApiKey(), page);
        } else {
            call = client.getTopMovies(URL.getApiKey(), page);
        }

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<Movie> movies = response.body().getMovies();
                    if (!mMovies.isEmpty()) mMovies.remove(mMovies.size() - 1);
                    for (Movie m : movies) {
                        String genres = "";
                        for (int i = 0; i < m.getGenreIds().length; i++) {
                            if (i != 0) genres = genres.concat(", ");
                            genres = genres.concat(Genre.getGenre(m.getGenreIds()[i]));
                        }
                        m.setGenre(genres);
                        mMovies.add(m);
                    }
                    mMovies.add(null);
                    notifyAdapter();
                    isLoading = false;
                    if (page < 1000) page++;
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                if (page == 1) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new RetryFragment())
                            .commit();
                } else {
                    mAdapter.setShowRetry(true);
                    mAdapter.notifyItemChanged(mMovies.size() - 1);
                }
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


    public static void setShow_popular(boolean show_popular) {
        MovieListFragment.show_popular = show_popular;
    }
}
