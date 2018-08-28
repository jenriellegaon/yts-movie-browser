package com.jproject.ytsmoviebrowser.presenter.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jproject.ytsmoviebrowser.R;
import com.jproject.ytsmoviebrowser.model.data.details.Sections;

import java.util.List;


public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ItemRowHolder> {


    List<Sections> sectionsList;
    LinearLayoutManager linearLayoutManager;
    private Context context;

    public DetailsAdapter(List<Sections> sections, RecyclerView recyclerView, Context context) {
        this.sectionsList = sections;
        this.linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        this.context = context;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_card_item, null, false);
        v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        ItemRowHolder itemRowHolder = new ItemRowHolder(v);
        return itemRowHolder;
    }

    @Override
    public void onBindViewHolder(final ItemRowHolder holder, final int position) {

        Sections sections = sectionsList.get(position);
        String title = sections.getTitle();
        String details = sections.getDetails();

        holder.tvTitle.setText(title);
        holder.tvDetails.setText(details);
    }

    @Override
    public int getItemCount() {
        return (null != sectionsList ? sectionsList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvDetails;

        ItemRowHolder(View v) {
            super(v);

            this.tvTitle = v.findViewById(R.id.detailTitle);
            this.tvDetails = v.findViewById(R.id.tvDetails);
        }
    }

}
