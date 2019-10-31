package com.example.movieapp.Activities;

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

import com.example.movieapp.Fragments.MovieListFragment;
import com.example.movieapp.Fragments.TvShowListFragment;
import com.example.movieapp.R;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    public static final int MOVIES = 0;
    public static final int TV = 1;
    private int current_fragment;
    private MenuItem popular_menu_item;

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
        MovieListFragment.setShow_popular(showPopular);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.movies);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MovieListFragment()).commit();
    }

    void showTvFragment(boolean showPopular) {
        TvShowListFragment.setShow_popular(showPopular);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.tv_shows);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new TvShowListFragment()).commit();
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
                popular_menu_item.setChecked(true);
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
        popular_menu_item = menu.findItem(R.id.popular);
        // checking the popular menu item when activity is started
        popular_menu_item.setChecked(true);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean checked = item.isChecked();
        if (!checked && id == R.id.popular) {
            switch (current_fragment) {
                case MOVIES:
                    showMovieFragment(true);
                    break;
                case TV:
                    showTvFragment(true);
                    break;
            }
        } else if (!checked && id == R.id.top_rated) {
            switch (current_fragment) {
                case MOVIES:
                    showMovieFragment(false);
                    break;
                case TV:
                    showTvFragment(false);
                    break;
            }
        }
        item.setChecked(true);
        return super.onOptionsItemSelected(item);
    }

    public int getCurrent_fragment() {
        return current_fragment;
    }
}
