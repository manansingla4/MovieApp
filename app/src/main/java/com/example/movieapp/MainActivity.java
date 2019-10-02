package com.example.movieapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.movieapp.Model.Movie;
import com.example.movieapp.Model.MovieList;
import com.example.movieapp.Values.Genre;
import com.example.movieapp.Values.URL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<Movie> mMovies = new ArrayList<>();
    MovieAdapter mAdapter = new MovieAdapter(mMovies, this);
    private boolean isLoading = false;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Call<MovieList> call = client.getPopularMovies(URL.getApiKey(), page);

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccessful()) {
                    List<Movie> movies = response.body().getMovies();
                    if(!mMovies.isEmpty()) mMovies.remove(mMovies.size()-1);
                    for(Movie m : movies) {
                        m.setPosterUrl(URL.getPosterUrl(m.getPosterUrl()));
                        m.setReleaseYear(m.getReleaseYear().substring(0,4));
                        String genres = "";
                        for(int i=0;i<m.getGenreIds().length;i++) {
                            if(i!=0) genres = genres.concat(", ");
                            genres = genres.concat(Genre.getGenre(m.getGenreIds()[i]));
                        }
                        System.out.println(genres);
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
            public void onFailure(Call<MovieList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, 0));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    void notifyAdapter() {
        mAdapter.setShowShimmer(false);
        mAdapter.notifyDataSetChanged();
    }
}
