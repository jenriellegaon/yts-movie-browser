package com.jproject.ytsmoviebrowser.presenter.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jproject.ytsmoviebrowser.R;
import com.jproject.ytsmoviebrowser.model.data.home.Movie;
import com.jproject.ytsmoviebrowser.presenter.util.GlideApp;
import com.jproject.ytsmoviebrowser.view.DetailsView;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class MovieSectionListDataAdapter extends RecyclerView.Adapter<MovieSectionListDataAdapter.SingleItemRowHolder> {

    private List<Movie> movieList;
    private Context context;
    private String imageUrl;
    private String title;
    private String genres;
    private String id;

    public MovieSectionListDataAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_item, null);
        SingleItemRowHolder singleItemRowHolder = new SingleItemRowHolder(v);
        return singleItemRowHolder;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int position) {

        Movie movie = movieList.get(position);
        imageUrl = movie.getLargeCoverImage();
        title = movie.getTitleEnglish();
        genres = String.valueOf(movie.getGenres());
        id = String.valueOf(movie.getId());


        GlideApp.with(context)
                .asDrawable()
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_unavailable)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
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
                .into(holder.itemImage);

        holder.movieId = id;
        holder.movieGenres = genres;
        holder.movieTitle = title;
    }

    @Override
    public int getItemCount() {
        return (null != movieList ? movieList.size() : 0);
    }


    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage;
        public String movieId;
        public String movieTitle;
        public String movieGenres;

        SingleItemRowHolder(View v) {
            super(v);

            this.itemImage = v.findViewById(R.id.itemImage);

            itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, DetailsView.class);
                    intent.putExtra("movie_id", movieId);
                    intent.putExtra("movie_title", movieTitle);
                    intent.putExtra("genres", movieGenres);

                    Activity activity = (Activity) context;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }
            });
        }
    }

}
