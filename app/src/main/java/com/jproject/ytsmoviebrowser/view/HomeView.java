package com.jproject.ytsmoviebrowser.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jproject.ytsmoviebrowser.R;
import com.jproject.ytsmoviebrowser.contract.HomeContract;
import com.jproject.ytsmoviebrowser.model.data.home.Movie;
import com.jproject.ytsmoviebrowser.model.data.home.MovieDataModel;
import com.jproject.ytsmoviebrowser.model.data.home.ResObj;
import com.jproject.ytsmoviebrowser.presenter.adapters.MovieDataAdapter;
import com.jproject.ytsmoviebrowser.presenter.presenter.HomePresenter;
import com.kennyc.view.MultiStateView;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeContract.View, MultiStateView.StateListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView rView;
    @BindView(R.id.multiStateView)
    MultiStateView state;

    HomePresenter homePresenter;
    ArrayList<MovieDataModel> movieDataModel;
    MovieDataModel pddm;
    List<Movie> movieData;
    MovieDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

        Permissions.check(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                //Bind views
                ButterKnife.bind(HomeView.this);

                //Initialize views
                initViews();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                Log.e("PERMISSION:" , "DENIED");
                finish();
            }
        });

    }

    @Override
    public void initViews() {

        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        movieDataModel = new ArrayList<>();

        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home));
        navigationView.setCheckedItem(R.id.nav_home);

        rView.setHasFixedSize(true);
        adapter = new MovieDataAdapter(movieDataModel, this);
        rView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rView.setAdapter(adapter);

        state.setStateListener(this);
        state.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        state.setViewState(MultiStateView.VIEW_STATE_LOADING);
                        movieDataModel.clear();
                        homePresenter = new HomePresenter(HomeView.this);
                        homePresenter.getLatestUploads("date_added");

                    }
                });
    }

    @Override
    public void showError(String error) {

        state.setViewState(MultiStateView.VIEW_STATE_ERROR);
        Log.d("RESPONSE ERROR! ", "Response error");
    }

    @Override
    public void showLatestUploads(ResObj resObj) {

        int limit = resObj.getData().getLimit();
        int movie_count = resObj.getData().getMovieCount();
        int page_number = resObj.getData().getPageNumber();
        int last_page = movie_count / limit;

        Log.d("RESULT", resObj.getStatus());
        Log.d("LIMIT", String.valueOf(limit));
        Log.d("MOVIE COUNT", String.valueOf(movie_count));
        Log.d("PAGE NUMBER", String.valueOf(page_number));
        Log.d("LAST PAGE", String.valueOf(last_page));

        if (resObj.getStatus().equals("ok")) {

            pddm = new MovieDataModel();
            pddm.setHeaderTitle("Latest Uploads");

            movieData = new ArrayList<>();
            movieData.addAll(resObj.getData().getMovies());
            adapter.notifyDataSetChanged();

            pddm.setMovieList(movieData);
            movieDataModel.add(pddm);

            Log.d("LATEST UPLOADS", "READY");

            homePresenter = new HomePresenter(this);
            homePresenter.getTopDownloads("download_count");
        }
    }

    @Override
    public void showTopDownloads(ResObj resObj) {

        int limit = resObj.getData().getLimit();
        int movie_count = resObj.getData().getMovieCount();
        int page_number = resObj.getData().getPageNumber();
        int last_page = movie_count / limit;

        Log.d("RESULT", resObj.getStatus());
        Log.d("LIMIT", String.valueOf(limit));
        Log.d("MOVIE COUNT", String.valueOf(movie_count));
        Log.d("PAGE NUMBER", String.valueOf(page_number));
        Log.d("LAST PAGE", String.valueOf(last_page));

        if (resObj.getStatus().equals("ok")) {

            pddm = new MovieDataModel();
            pddm.setHeaderTitle("Top Downloads");

            movieData = new ArrayList<>();
            movieData.addAll(resObj.getData().getMovies());
            adapter.notifyDataSetChanged();

            pddm.setMovieList(movieData);
            movieDataModel.add(pddm);

            Log.d("TOP DOWNLOADS", "READY");

            homePresenter = new HomePresenter(this);
            homePresenter.getTopRated("rating");
        }
    }

    @Override
    public void showTopRated(ResObj resObj) {

        int limit = resObj.getData().getLimit();
        int movie_count = resObj.getData().getMovieCount();
        int page_number = resObj.getData().getPageNumber();
        int last_page = movie_count / limit;

        Log.d("RESULT", resObj.getStatus());
        Log.d("LIMIT", String.valueOf(limit));
        Log.d("MOVIE COUNT", String.valueOf(movie_count));
        Log.d("PAGE NUMBER", String.valueOf(page_number));
        Log.d("LAST PAGE", String.valueOf(last_page));

        if (resObj.getStatus().equals("ok")) {

            pddm = new MovieDataModel();
            pddm.setHeaderTitle("Top Rated");

            movieData = new ArrayList<>();
            movieData.addAll(resObj.getData().getMovies());
            adapter.notifyDataSetChanged();

            pddm.setMovieList(movieData);
            movieDataModel.add(pddm);

            Log.d("TOP RATED", "READY");

            state.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    @Override
    public void onStateChanged(@MultiStateView.ViewState int viewState) {
        Log.v("HomeView", " View State: " + viewState);
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finishAndRemoveTask();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            if (!navigationView.getMenu().findItem(R.id.nav_home).isChecked()) {
                //Loads Sectioned Movies
                movieDataModel.clear();
                homePresenter = new HomePresenter(this);
                homePresenter.getLatestUploads("date_added");
            }

        } else if (id == R.id.nav_about) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}