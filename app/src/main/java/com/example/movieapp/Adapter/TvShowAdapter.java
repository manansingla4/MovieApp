package com.example.movieapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.Model.TvShow;
import com.example.movieapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static class TvShowViewHolder extends RecyclerView.ViewHolder {
        ShimmerFrameLayout mShimmerFrameLayout;
        ImageView mPoster;
        TextView mName, mGenre, mRating, mRelease;

        TvShowViewHolder(View view) {
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

    private ArrayList<TvShow> mTvShows;
    private boolean showShimmer = true;
    private Context mContext;
    private final int MOVIE_VIEW = 1;
    private final int LOADING_VIEW = 0;


    public TvShowAdapter(ArrayList<TvShow> objects, Context context) {
        mTvShows = objects;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MOVIE_VIEW) {
            View itemView = LayoutInflater.from(mContext)
                    .inflate(R.layout.movie_card_item, parent, false);
            return new TvShowViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (showShimmer) {
            ((TvShowViewHolder) holder).mShimmerFrameLayout.startShimmer();
        } else if (holder instanceof TvShowViewHolder) {
            final TvShowViewHolder tvShowViewHolder = (TvShowViewHolder) holder;
            tvShowViewHolder.mShimmerFrameLayout.stopShimmer();
            tvShowViewHolder.mShimmerFrameLayout.setShimmer(null);

            tvShowViewHolder.mPoster.setBackground(null);
            tvShowViewHolder.mName.setBackground(null);
            tvShowViewHolder.mRelease.setBackground(null);
            tvShowViewHolder.mRating.setBackground(null);
            tvShowViewHolder.mGenre.setBackground(null);

            TvShow tvShow = mTvShows.get(position);
            tvShowViewHolder.mName.setText(tvShow.getTitle());
            tvShowViewHolder.mRating.setText(tvShow.getRating());
            tvShowViewHolder.mRelease.setText(tvShow.getReleaseYear());
            tvShowViewHolder.mGenre.setText(tvShow.getGenre());
            Picasso.with(mContext)
                    .load(tvShow.getPosterUrl())
                    .placeholder(R.drawable.poster_show_loading)
                    .error(R.drawable.poster_show_not_available)
                    .into(tvShowViewHolder.mPoster);
        }
    }

    @Override
    public int getItemCount() {
        // no of shimmer items shown while loading
        final int SHIMMER_ITEM_NO = 10;
        return showShimmer ? SHIMMER_ITEM_NO : mTvShows.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mTvShows.size() - 1) {
            return LOADING_VIEW;
        }
        return MOVIE_VIEW;
    }

    public void setShowShimmer(boolean showShimmer) {
        this.showShimmer = showShimmer;
    }
}
