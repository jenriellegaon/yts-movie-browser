package com.jproject.ytsmoviebrowser.view;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jproject.ytsmoviebrowser.R;
import com.jproject.ytsmoviebrowser.contract.MainContract;
import com.jproject.ytsmoviebrowser.model.data.Movie;
import com.jproject.ytsmoviebrowser.model.data.MovieDataModel;
import com.jproject.ytsmoviebrowser.model.data.ResObj;
import com.jproject.ytsmoviebrowser.presenter.adapters.MovieDataAdapter;
import com.jproject.ytsmoviebrowser.presenter.presenter.MainPresenter;
import com.kennyc.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View, MultiStateView.StateListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.swipy)
//    SwipyRefreshLayout swipy;
    @BindView(R.id.recycler)
    RecyclerView rView;
    @BindView(R.id.multiStateView)
    MultiStateView state;

    MainPresenter mainPresenter;
    ArrayList<MovieDataModel> movieDataModel;
    MovieDataModel pddm;
    List<Movie> movieData;
    MovieDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        //Bind views
        ButterKnife.bind(this);

        //Initialize views
        initViews();

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
//        topRatedDataModel = new ArrayList<>();

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
//                        mainPresenter = new MainPresenter(MainView.this);
//                        mainPresenter.getPopularDownloads("download_count");

                        Intent reload = new Intent(MainView.this, MainView.class);
                        startActivity(reload);
                        finish();

                    }
                });

        if (state.getViewState() == 1) {

            mainPresenter = new MainPresenter(this);
            mainPresenter.detachAll();
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(MainView.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {

        state.setViewState(MultiStateView.VIEW_STATE_ERROR);
        mainPresenter = new MainPresenter(this);
        mainPresenter.detachAll();

        Log.d("RESPONSE ERROR! ", "Response error");

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

            mainPresenter = new MainPresenter(this);
            mainPresenter.getThisYear("year");

            Log.d("TOP DOWNLOADS", "READY");

            state.setViewState(MultiStateView.VIEW_STATE_CONTENT);

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

            mainPresenter = new MainPresenter(this);
            mainPresenter.getTopDownloads("download_count");

            state.setViewState(MultiStateView.VIEW_STATE_CONTENT);

        }

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

            mainPresenter = new MainPresenter(this);
            mainPresenter.getTopRated("rating");

            state.setViewState(MultiStateView.VIEW_STATE_CONTENT);

        }
    }

    @Override
    public void showThisYear(ResObj resObj) {

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
            pddm.setHeaderTitle("This Year");

            movieData = new ArrayList<>();
            movieData.addAll(resObj.getData().getMovies());
            adapter.notifyDataSetChanged();

            pddm.setMovieList(movieData);
            movieDataModel.add(pddm);

            state.setViewState(MultiStateView.VIEW_STATE_CONTENT);

        }

    }

    @Override
    public void showNextPage(ResObj resObj) {

//        int limit = resObj.getData().getLimit();
//        int movie_count = resObj.getData().getMovieCount();
//        int page_number = resObj.getData().getPageNumber();
//        int last_page = movie_count / limit;
//
//        Log.d("RESULT", resObj.getStatus());
//        Log.d("LIMIT", String.valueOf(limit));
//        Log.d("MOVIE COUNT", String.valueOf(movie_count));
//        Log.d("PAGE NUMBER", String.valueOf(page_number));
//        Log.d("LAST PAGE", String.valueOf(last_page));
//
//        if (resObj.getStatus().equals("ok")) {
//
//            //Check if page number is equal to last page
//            if (page_number == last_page) {
//
//                Log.d("Last page reached", String.valueOf(last_page));
//                popularDownloadsAdapter.enableFooter(false);
//            } else {
//
//                movieList.addAll(resObj.getData().getMovies());
//                popularDownloadsAdapter.notifyDataSetChanged();
//                swipy.setRefreshing(false);
//                state.setViewState(MultiStateView.VIEW_STATE_CONTENT);
//                popularDownloadsAdapter.enableFooter(true);
//            }
//        }
    }

    @Override
    public void onStateChanged(@MultiStateView.ViewState int viewState) {
        Log.v("MainView", " View State: " + viewState);
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            mainPresenter = new MainPresenter(this);
            mainPresenter.getLatestUploads("date_added");


        } else if (id == R.id.nav_about) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}