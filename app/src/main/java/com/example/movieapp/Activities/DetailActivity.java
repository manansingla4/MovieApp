package com.example.movieapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.DataBaseHelper;
import com.example.movieapp.Model.Movie;
import com.example.movieapp.Model.TvShow;
import com.example.movieapp.Model.Watchable;
import com.example.movieapp.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private boolean mFavorite;
    private DataBaseHelper mDataBaseHelper;
    private Watchable mWatchable;
    private TextView mName, plotSynopsis, userRating, releaseDate;
    private ImageView mImageView;
    public static final String INTENT_KEY = "watchable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDataBaseHelper = new DataBaseHelper(this);
        mName = findViewById(R.id.movie_title);
        mImageView = findViewById(R.id.thumbnail_image_header);
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
            mName.setText(mWatchable.getTitle());
            plotSynopsis.setText(mWatchable.getSynopsis());
            userRating.setText(mWatchable.getRating());
            releaseDate.setText(mWatchable.getReleaseYear());
            Picasso.with(this)
                    .load(mWatchable.getBackdropURL())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.poster_show_not_available)
                    .into(mImageView);
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

}
