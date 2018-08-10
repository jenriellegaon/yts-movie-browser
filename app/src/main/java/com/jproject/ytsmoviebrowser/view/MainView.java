package com.jproject.ytsmoviebrowser.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jproject.ytsmoviebrowser.R;
import com.jproject.ytsmoviebrowser.contract.MainContract;
import com.jproject.ytsmoviebrowser.model.data.ResObj;
import com.kennyc.view.MultiStateView;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

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

        swipy.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {

                //On pull down

            }
        });

        state.setStateListener(this);
        state.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        state.setViewState(MultiStateView.VIEW_STATE_LOADING);

                    }
                });
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(MainView.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {
        showToast(error);
    }

    @Override
    public void showPopularDownloads(ResObj resObj) {

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

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}