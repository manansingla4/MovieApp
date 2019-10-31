package com.example.movieapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.Model.Watchable;
import com.example.movieapp.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView mName, plotSynopsis, userRating, releaseDate;
    private ImageView mImageView;
    public static final String INTENT_KEY = "watchable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mName = findViewById(R.id.movie_title);
        mImageView = findViewById(R.id.thumbnail_image_header);
        plotSynopsis = findViewById(R.id.plot_synopsis);
        userRating = findViewById(R.id.user_rating);
        releaseDate = findViewById(R.id.release_date);


        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        initCollapsingToolbar();

        Intent intent = getIntent();
        Watchable watchable = (Watchable) intent.getExtras().getSerializable(INTENT_KEY);
        mName.setText(watchable.getTitle());
        plotSynopsis.setText(watchable.getSynopsis());
        userRating.setText(watchable.getRating());
        releaseDate.setText(watchable.getReleaseYear());
        Picasso.with(this)
                .load(watchable.getBackdropURL())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.poster_show_not_available)
                .into(mImageView);


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
}
