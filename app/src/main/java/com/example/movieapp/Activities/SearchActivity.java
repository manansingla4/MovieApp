package com.example.movieapp.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.movieapp.Adapter.SearchPagerAdapter;
import com.example.movieapp.Interfaces.Observer;
import com.example.movieapp.Interfaces.Subject;
import com.example.movieapp.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements Subject {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String currentQueryString;
    private ArrayList<Observer> mObservers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupActionBar();
        mViewPager = findViewById(R.id.tab_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager.setAdapter(new SearchPagerAdapter(getSupportFragmentManager(), this));
        mTabLayout.setupWithViewPager(mViewPager);
    }


    void setupActionBar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.onActionViewExpanded();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() < 3) {
                    currentQueryString = "";
                } else {
                    currentQueryString = newText;
                }
                notifyObservers();
                return false;
            }
        });
        return true;
    }

    @Override
    public void register(Observer observer) {
        mObservers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : mObservers) {
            observer.update(currentQueryString);
        }
    }
}
