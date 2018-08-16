package com.jproject.ytsmoviebrowser.presenter.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.jproject.ytsmoviebrowser.R;
import com.jproject.ytsmoviebrowser.model.data.home.MovieDataModel;
import com.jproject.ytsmoviebrowser.view.MovieGridView;

import java.util.List;

public class MovieDataAdapter extends RecyclerView.Adapter<MovieDataAdapter.ItemRowHolder> {

    private List<MovieDataModel> popularDownloadsDataList;
    private Context context;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private SnapHelper snapHelper;
    String section;

    public MovieDataAdapter(List<MovieDataModel> popularDownloadsDataList, Context context) {
        this.popularDownloadsDataList = popularDownloadsDataList;
        this.context = context;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item, null);
        ItemRowHolder rowHolder = new ItemRowHolder(v);
        snapHelper = new GravitySnapHelper(Gravity.START);
        return rowHolder;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, int position) {

        final String sectionName = popularDownloadsDataList.get(position).getHeaderTitle();
        List singleSectionItems = popularDownloadsDataList.get(position).getMovieList();
        holder.itemTitle.setText(sectionName);

        MovieSectionListDataAdapter adapter = new MovieSectionListDataAdapter(singleSectionItems, context);

        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setRecycledViewPool(recycledViewPool);

        snapHelper.attachToRecyclerView(holder.recyclerView);

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (sectionName) {

                    case "Latest Uploads":
                        section = "date_added";
                        break;

                    case "Top Downloads":
                        section = "download_count";
                        break;

                    case "Top Rated":
                        section = "rating";
                        break;
                }

                Intent intent = new Intent(context, MovieGridView.class);
                intent.putExtra("section", section);
                intent.putExtra("title" , sectionName);

                Activity activity = (Activity) context;
                activity.startActivity(intent);

                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != popularDownloadsDataList ? popularDownloadsDataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected RecyclerView recyclerView;
        protected Button btnMore;

        public ItemRowHolder(View itemView) {
            super(itemView);
            this.itemTitle = itemView.findViewById(R.id.itemTitle);
            this.recyclerView = itemView.findViewById(R.id.recycler_view_list);
            this.btnMore = itemView.findViewById(R.id.btnMore);
        }
    }
}
