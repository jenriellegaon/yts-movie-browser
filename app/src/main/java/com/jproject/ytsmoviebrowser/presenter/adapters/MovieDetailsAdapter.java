package com.jproject.ytsmoviebrowser.presenter.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jproject.ytsmoviebrowser.R;
import com.jproject.ytsmoviebrowser.contract.DetailsContract;
import com.jproject.ytsmoviebrowser.model.data.home.Movie;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class MovieDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_PROGRESSBAR = 0;

    DetailsContract.OnBottomReachedListener onBottomReachedListener;

    private Context context;
    private boolean isFooterEnabled = true;

    private GridLayoutManager gridLayoutManager;

    private List<Movie> movieList;
    String imageUrl;

    public MovieDetailsAdapter(List<Movie> movieList, RecyclerView rView, Context context) {

        this.movieList = movieList;
        this.gridLayoutManager = (GridLayoutManager) rView.getLayoutManager();
        this.context = context;

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                switch (getItemViewType(position)) {

                    case VIEW_TYPE_ITEM:
                        return 1;
                    case VIEW_TYPE_PROGRESSBAR:
                        return 2;
                    default:
                        return -1;
                }
            }
        });

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        context = parent.getContext();

        if (viewType == VIEW_TYPE_ITEM) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_card_item, parent, false);
            vh = new ImageViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_footer, null, false);
            vh = new ProgressViewHolder(itemView);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ProgressViewHolder) {

            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);

        } else if (movieList.size() > 0 && position < movieList.size()) {

            if (position == movieList.size() - 1) {

                onBottomReachedListener.onBottomReached(position);
            }

            Movie movie = movieList.get(position);
            imageUrl = movie.getLargeCoverImage();
            RequestOptions requestOptions = new RequestOptions();

            Glide.with(context)
                    .asDrawable()
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(requestOptions.placeholder(R.drawable.placeholder))
                    .apply(requestOptions.error(R.drawable.placeholder_unavailable))
                    .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.d("FAILED", e.getMessage());
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            Log.d("GLIDE", "READY");
                            return false;
                        }
                    })
                    .into(((ImageViewHolder) holder).imageView);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return (isFooterEnabled && position >= movieList.size()) ? VIEW_TYPE_PROGRESSBAR : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return (isFooterEnabled) ? movieList.size() + 1 : movieList.size();
    }

    public void setOnBottomReachedListener(DetailsContract.OnBottomReachedListener onBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener;
    }

    public void enableFooter(boolean isEnabled) {
        this.isFooterEnabled = isEnabled;
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar);
        }
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ImageViewHolder(View v) {

            super(v);
            imageView = v.findViewById(R.id.movieImage);
        }
    }
}