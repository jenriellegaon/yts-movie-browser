package com.jproject.ytsmoviebrowser.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jproject.ytsmoviebrowser.R;
import com.jproject.ytsmoviebrowser.contract.InfoContract;
import com.jproject.ytsmoviebrowser.model.data.details.ResObj;
import com.jproject.ytsmoviebrowser.presenter.presenter.InfoPresenter;

public class InfoTab extends Fragment implements InfoContract.View {

    String id;
    String synopsis;
    TextView tvSynopsis;
    InfoPresenter presenter;
    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_tab_info, container, false);

        if (getArguments() != null) {
            id = getArguments().getString("movie_id");
            Log.d("INFO TAB", "Movie ID: " + id);
        }

        tvSynopsis = rootView.findViewById(R.id.tvSynopsis);

        preferences = getContext().getSharedPreferences("movie_details", Context.MODE_PRIVATE);
        synopsis = preferences.getString("synopsis", null); //null is default


        if (synopsis != null) {
            //Not null
            tvSynopsis.setText(synopsis);
            Log.d(id + " Synopsis", "Fetch from preferences");
        } else {

            presenter = new InfoPresenter(this);
            presenter.getInfo(id);
        }

        return rootView;
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void showInfo(ResObj resObj) {
        Log.d("RESULT", resObj.getStatus());

        if (resObj.getStatus().equals("ok")) {

            synopsis = resObj.getData().getMovie().getDescriptionFull();
            tvSynopsis.setText(synopsis);
            Log.d(id + " Synopsis", "Fetch from api call");

//            Log.d(id + " Rating" , String.valueOf(resObj.getData().getMovie().getRating()));
//            Log.d(id + " Runtime" , String.valueOf(resObj.getData().getMovie().getRuntime()));
//            Log.d(id + " Year" , String.valueOf(resObj.getData().getMovie().getYear()));
//            Log.d(id + " Youtube Trailer Code" , resObj.getData().getMovie().getYtTrailerCode());

            SharedPreferences preferences = getContext().getSharedPreferences("movie_details", Context.MODE_PRIVATE);
            preferences.edit().putString("synopsis", synopsis).apply();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        clearPrefs();
    }


    @Override
    public void showError(String error) {
        showToast(error);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void clearPrefs() {
        SharedPreferences preferences = getContext().getSharedPreferences("movie_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}