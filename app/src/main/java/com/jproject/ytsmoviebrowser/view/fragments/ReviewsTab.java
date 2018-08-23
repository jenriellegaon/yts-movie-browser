package com.jproject.ytsmoviebrowser.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jproject.ytsmoviebrowser.R;

public class ReviewsTab extends Fragment {

    String id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_tab_reviews, container, false);

        if (getArguments() != null) {
            id = getArguments().getString("movie_id");
            Log.d("REVIEWS TAB", "Movie ID: " + id);
        }

        return rootView;
    }
}
