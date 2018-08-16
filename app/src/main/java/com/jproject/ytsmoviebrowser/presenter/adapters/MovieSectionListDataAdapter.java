package com.jproject.ytsmoviebrowser.presenter.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jproject.ytsmoviebrowser.R;
import com.jproject.ytsmoviebrowser.model.data.home.Movie;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class MovieSectionListDataAdapter extends RecyclerView.Adapter<MovieSectionListDataAdapter.SingleItemRowHolder> {

    String movie_id;
    private List<Movie> movieList;
    private Context context;

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
        String imageUrl = movie.getLargeCoverImage();
        movie_id = String.valueOf(movie.getId());

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
                .into(holder.itemImage);

        holder.itemImage.setTransitionName(movie_id);

    }


    @Override
    public int getItemCount() {
        return (null != movieList ? movieList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected ImageView itemImage;

        public SingleItemRowHolder(View itemView) {
            super(itemView);

            this.itemImage = itemView.findViewById(R.id.itemImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(view.getContext(), itemImage.getTransitionName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
