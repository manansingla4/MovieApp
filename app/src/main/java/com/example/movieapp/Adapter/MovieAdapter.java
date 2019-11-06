package com.example.movieapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.Activities.DetailActivity;
import com.example.movieapp.Model.Movie;
import com.example.movieapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ShimmerFrameLayout mShimmerFrameLayout;
        ImageView mPoster;
        TextView mName, mGenre, mRating, mRelease;

        MovieViewHolder(View view) {
            super(view);
            mPoster = view.findViewById(R.id.item_movie_banner);
            mName = view.findViewById(R.id.item_movie_name);
            mGenre = view.findViewById(R.id.item_movie_genre);
            mRating = view.findViewById(R.id.item_movie_rating);
            mRelease = view.findViewById(R.id.item_release_year);
            mShimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        LoadingViewHolder(View view) {
            super(view);
        }
    }

    static class RetryViewHolder extends RecyclerView.ViewHolder {
        RetryViewHolder(View view) {
            super(view);
        }
    }

    private ArrayList<Movie> mMovies;
    private boolean showShimmer = true;
    private Context mContext;
    private final int MOVIE_VIEW = 1;
    private final int LOADING_VIEW = 0;
    private final int RETRY_VIEW = 2;
    private boolean showRetry = false;
    private View.OnClickListener retryClickListener;


    public MovieAdapter(ArrayList<Movie> objects, Context context) {
        mMovies = objects;
        this.mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MOVIE_VIEW) {
            View itemView = LayoutInflater.from(mContext)
                    .inflate(R.layout.movie_card_item, parent, false);
            return new MovieViewHolder(itemView);
        } else if (viewType == RETRY_VIEW) {
            View itemView = LayoutInflater.from(mContext).
                    inflate(R.layout.retry_list_item, parent, false);
            return new RetryViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieViewHolder && showShimmer) {
            ((MovieViewHolder) holder).mShimmerFrameLayout.startShimmer();
        } else if (holder instanceof MovieViewHolder) {
            final MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            movieViewHolder.mShimmerFrameLayout.stopShimmer();
            movieViewHolder.mShimmerFrameLayout.setShimmer(null);

            movieViewHolder.mPoster.setBackground(null);
            movieViewHolder.mName.setBackground(null);
            movieViewHolder.mRelease.setBackground(null);
            movieViewHolder.mRating.setBackground(null);
            movieViewHolder.mGenre.setBackground(null);

            final Movie movie = mMovies.get(position);
            movieViewHolder.mName.setText(movie.getTitle());
            movieViewHolder.mRating.setText(movie.getRating());
            movieViewHolder.mRelease.setText(movie.getReleaseYear());
            movieViewHolder.mGenre.setText(movie.getGenre());
            Picasso.with(mContext)
                    .load(movie.getBackdropURL())
                    .placeholder(R.drawable.poster_show_loading)
                    .error(R.drawable.poster_show_not_available)
                    .into(movieViewHolder.mPoster);
            movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(DetailActivity.INTENT_KEY, movie);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        } else if (holder instanceof RetryViewHolder) {
            holder.itemView.setOnClickListener(retryClickListener);
        }
    }

    @Override
    public int getItemCount() {
        // no of shimmer items shown while loading
        final int SHIMMER_ITEM_NO = 10;
        return showShimmer ? SHIMMER_ITEM_NO : mMovies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (showShimmer) {
            return MOVIE_VIEW;
        } else if (position == mMovies.size() - 1 && showRetry) {
            return RETRY_VIEW;
        } else if (mMovies.get(position) == null) {
            return LOADING_VIEW;
        } else
            return MOVIE_VIEW;
    }

    public void setShowShimmer(boolean showShimmer) {
        this.showShimmer = showShimmer;
    }

    public void setShowRetry(boolean showRetry) {
        this.showRetry = showRetry;
    }

    public void setRetryClickListener(View.OnClickListener retryClickListener) {
        this.retryClickListener = retryClickListener;
    }
}
