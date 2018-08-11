package com.jproject.ytsmoviebrowser.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import com.jproject.ytsmoviebrowser.model.data.ResObj;
import com.jproject.ytsmoviebrowser.presenter.adapters.PopularDownloadsAdapter;
import com.jproject.ytsmoviebrowser.presenter.presenter.MainPresenter;
import com.kennyc.view.MultiStateView;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

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
    @BindView(R.id.swipy)
    SwipyRefreshLayout swipy;
    @BindView(R.id.rv)
    RecyclerView rView;
    @BindView(R.id.multiStateView)
    MultiStateView state;

    List<Movie> movieList = new ArrayList<>();
    PopularDownloadsAdapter popularDownloadsAdapter;
    MainPresenter mainPresenter;


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

        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home));
        navigationView.setCheckedItem(R.id.nav_home);

        rView.setLayoutManager(new GridLayoutManager(MainView.this, 2));
        rView.setItemAnimator(new DefaultItemAnimator());
        rView.setHasFixedSize(true);

        popularDownloadsAdapter = new PopularDownloadsAdapter(movieList, rView, MainView.this);
        popularDownloadsAdapter.enableFooter(false);
        rView.setAdapter(popularDownloadsAdapter);
        popularDownloadsAdapter.notifyDataSetChanged();

        swipy.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {

                //On pull down
                popularDownloadsAdapter.enableFooter(false);
                movieList.clear();
                mainPresenter = new MainPresenter(MainView.this);
                mainPresenter.getPopularDownloads(getResources().getString(R.string.popular_downloads));

            }
        });

        popularDownloadsAdapter.setOnBottomReachedListener(new MainContract.OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {

                mainPresenter.getNextPage(getResources().getString(R.string.popular_downloads));
            }
        });

        state.setStateListener(this);
        state.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        state.setViewState(MultiStateView.VIEW_STATE_LOADING);

                        movieList.clear();
                        mainPresenter = new MainPresenter(MainView.this);
                        mainPresenter.getPopularDownloads(getResources().getString(R.string.popular_downloads));

                    }
                });
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(MainView.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {
//        showToast(error);
        movieList.clear();
        popularDownloadsAdapter.enableFooter(false);
        popularDownloadsAdapter.notifyDataSetChanged();
        state.setViewState(MultiStateView.VIEW_STATE_ERROR);
        swipy.setRefreshing(false);
        Log.d("RESPONSE ERROR! ", "Response error");

    }

    @Override
    public void showPopularDownloads(ResObj resObj) {

        Log.d("RESULT", resObj.getStatus());

        if (resObj.getStatus().equals("ok")) {

            movieList.clear();
            movieList.addAll(resObj.getData().getMovies());
            popularDownloadsAdapter.notifyDataSetChanged();
            swipy.setRefreshing(false);
            Log.d("MovieList", String.valueOf(movieList));
            state.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            popularDownloadsAdapter.enableFooter(true);
        }

    }

    @Override
    public void showTopRated(ResObj resObj) {

    }

    @Override
    public void showLatestUploads(ResObj resObj) {

    }

    @Override
    public void showThisYear(ResObj resObj) {

    }

    @Override
    public void showNextPage(ResObj resObj) {

        Log.d("RESULT", resObj.getStatus());

        if (resObj.getStatus().equals("ok")) {

            int limit = resObj.getData().getLimit();
            int movie_count = resObj.getData().getMovieCount();
            int page_number = resObj.getData().getPageNumber();
            int last_page = movie_count / limit;

            Log.d("Page Number", String.valueOf(page_number));

            resObj.getData().setPageNumber(last_page);

            //Check if page number is equal to last page
            if (page_number == last_page) {

                Log.d("Last page reached", String.valueOf(last_page));
                popularDownloadsAdapter.enableFooter(false);
            } else {

                movieList.addAll(resObj.getData().getMovies());
                popularDownloadsAdapter.notifyDataSetChanged();
                swipy.setRefreshing(false);
                Log.d("MovieList", String.valueOf(movieList));
                state.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                popularDownloadsAdapter.enableFooter(true);
            }
        }
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

            mainPresenter = new MainPresenter(MainView.this);
            mainPresenter.getPopularDownloads(getResources().getString(R.string.popular_downloads));

        } else if (id == R.id.nav_about) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
