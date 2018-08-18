package com.jproject.ytsmoviebrowser.presenter.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jproject.ytsmoviebrowser.R;
import com.jproject.ytsmoviebrowser.model.data.details.Movie;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Movie> casts;
    LinearLayoutManager linearLayoutManager;
    private Context context;


    public CastAdapter(List<Movie> casts, RecyclerView recyclerView, Context context) {
        this.casts = casts;
        this.linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return casts.size();
    }


    public class CastViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView image;
        public TextView tvName;
        public TextView tvCharacterName;

        CastViewHolder(View v) {

            super(v);
            image = v.findViewById(R.id.cast_image);
            tvName = v.findViewById(R.id.tvName);
            tvCharacterName = v.findViewById(R.id.tvCharacterName);
        }
    }
}
