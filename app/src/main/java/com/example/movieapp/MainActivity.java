package com.example.movieapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private final int MOVIES = 0;
    private final int TV = 1;
    private int current_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        initNavigationView();
        addToggleBar();
        current_fragment = MOVIES;
        showMovieFragment(true);
    }


    void showMovieFragment(boolean showPopular) {
        MovieFragment.setShow_popular(showPopular);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.movies);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MovieFragment()).commit();
    }

    void showTvFragment(boolean showPopular) {
        TvShowFragment.setShow_popular(showPopular);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.tv_shows);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new TvShowFragment()).commit();
    }

    void initNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_movies:
                        current_fragment = MOVIES;
                        showMovieFragment(true);
                        break;
                    case R.id.nav_tv_shows:
                        current_fragment = TV;
                        showTvFragment(true);
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        navigationView.setCheckedItem(R.id.nav_movies);
    }

    void addToggleBar() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.popular) {
            switch (current_fragment) {
                case MOVIES:
                    showMovieFragment(true);
                    break;
                case TV:
                    showTvFragment(true);
                    break;
            }
        }
        if (id == R.id.top_rated) {
            switch (current_fragment) {
                case MOVIES:
                    showMovieFragment(false);
                    break;
                case TV:
                    showTvFragment(false);
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
