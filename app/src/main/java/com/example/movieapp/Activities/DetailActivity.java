package com.example.movieapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.movieapp.DataBaseHelper;
import com.example.movieapp.Model.BackDrop;
import com.example.movieapp.Model.BackDropList;
import com.example.movieapp.Model.Movie;
import com.example.movieapp.Model.TvShow;
import com.example.movieapp.Model.Watchable;
import com.example.movieapp.R;
import com.example.movieapp.Util.MyRetrofit;
import com.example.movieapp.Util.TmdbClient;
import com.example.movieapp.Util.URL;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailActivity extends AppCompatActivity {

    private boolean mFavorite;
    private DataBaseHelper mDataBaseHelper;
    private Watchable mWatchable;
    private TextView mName, plotSynopsis, userRating, releaseDate;
    public static final String INTENT_KEY = "watchable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDataBaseHelper = new DataBaseHelper(this);
        mName = findViewById(R.id.movie_title);
        plotSynopsis = findViewById(R.id.plot_synopsis);
        userRating = findViewById(R.id.user_rating);
        releaseDate = findViewById(R.id.release_date);


        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        initCollapsingToolbar();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mWatchable = (Watchable) bundle.getSerializable(INTENT_KEY);
        }
        if (mWatchable != null) {
            loadImages();
            mName.setText(mWatchable.getTitle());
            plotSynopsis.setText(mWatchable.getSynopsis());
            userRating.setText(mWatchable.getRating());
            releaseDate.setText(mWatchable.getReleaseYear());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout =
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + i == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.movie_details));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite, menu);
        MenuItem item = menu.findItem(R.id.menu_favorite);
        if (mWatchable.getClass() == Movie.class) {
            mFavorite = mDataBaseHelper.isMoviePresent((Movie) mWatchable);
        } else if (mWatchable.getClass() == TvShow.class) {
            mFavorite = mDataBaseHelper.isTvShowPresent((TvShow) mWatchable);
        }
        if (mFavorite) {
            item.setIcon(R.drawable.ic_favorite_white);
        } else {
            item.setIcon(R.drawable.ic_favorite_border_white);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_favorite && mFavorite) {
            boolean removed = false;
            if (mWatchable.getClass() == Movie.class) {
                removed = mDataBaseHelper.removeMovie((Movie) mWatchable);
            } else if (mWatchable.getClass() == TvShow.class) {
                removed = mDataBaseHelper.removeTvShow((TvShow) mWatchable);
            }
            if (removed) {
                mFavorite = false;
                item.setIcon(R.drawable.ic_favorite_border_white);
                showToast("Removed from Favorites");
            }
        } else if (item.getItemId() == R.id.menu_favorite) {
            boolean inserted = false;
            if (mWatchable.getClass() == Movie.class) {
                inserted = mDataBaseHelper.insertWatchable(mWatchable, DataBaseHelper.MOVIE_TABLE_NAME);
            } else if (mWatchable.getClass() == TvShow.class) {
                inserted = mDataBaseHelper.insertWatchable(mWatchable, DataBaseHelper.TV_TABLE_NAME);
            }
            if (inserted) {
                mFavorite = true;
                item.setIcon(R.drawable.ic_favorite_white);
                showToast("Added to Favorites");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void loadImages() {

        final SliderLayout sliderLayout = findViewById(R.id.slider);
        sliderLayout.stopAutoCycle();
        PagerIndicator indicator = findViewById(R.id.custom_indicator);
        sliderLayout.setCustomIndicator(indicator);
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);

        Retrofit retrofit = MyRetrofit.getRetrofitInstance();
        TmdbClient client = retrofit.create(TmdbClient.class);
        Call<BackDropList> call;
        if (mWatchable.getClass() == Movie.class) {
            call = client.getBackDrops(URL.getBaseUrl() + "3/movie/" + mWatchable.getId() +
                    "/images?api_key=" + URL.getApiKey());
        } else {
            call = client.getBackDrops(URL.getBaseUrl() + "3/tv/" + mWatchable.getId() +
                    "/images?api_key=" + URL.getApiKey());
        }

        call.enqueue(new Callback<BackDropList>() {
            @Override
            public void onResponse(Call<BackDropList> call, Response<BackDropList> response) {
                if (response.isSuccessful()) {
                    List<BackDrop> backDrops = response.body().getBackDrops();
                    if (backDrops.size() == 0) {
                        DefaultSliderView defaultSliderView = new DefaultSliderView(getApplicationContext());
                        defaultSliderView.image(R.drawable.poster_show_not_available)
                                .setScaleType(BaseSliderView.ScaleType.Fit);
                        sliderLayout.addSlider(defaultSliderView);
                    }
                    for (int i = 0; i < 10 && i < backDrops.size(); i++) {
                        DefaultSliderView defaultSliderView = new DefaultSliderView(getApplicationContext());
                        defaultSliderView.image(backDrops.get(i).getUrl())
                                .setScaleType(BaseSliderView.ScaleType.Fit);
                        sliderLayout.addSlider(defaultSliderView);
                    }
                }
            }

            @Override
            public void onFailure(Call<BackDropList> call, Throwable t) {
                DefaultSliderView defaultSliderView = new DefaultSliderView(getApplicationContext());
                defaultSliderView.image(R.drawable.poster_show_not_available)
                        .setScaleType(BaseSliderView.ScaleType.Fit);
                sliderLayout.addSlider(defaultSliderView);
            }
        });
    }
}
