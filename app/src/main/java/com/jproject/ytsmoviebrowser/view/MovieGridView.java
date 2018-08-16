package com.jproject.ytsmoviebrowser.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
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
import com.jproject.ytsmoviebrowser.contract.MoviesContract;
import com.jproject.ytsmoviebrowser.model.data.home.Movie;
import com.jproject.ytsmoviebrowser.model.data.home.ResObj;
import com.jproject.ytsmoviebrowser.presenter.adapters.MoviesAdapter;
import com.jproject.ytsmoviebrowser.presenter.presenter.MoviesPresenter;
import com.kennyc.view.MultiStateView;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieGridView extends AppCompatActivity implements MoviesContract.View, MultiStateView.StateListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.multiStateView)
    MultiStateView state;

    @BindView(R.id.swipy)
    SwipyRefreshLayout swipy;

    MoviesAdapter adapter;
    List<Movie> movieList = new ArrayList<>();
    MoviesPresenter presenter;

    Bundle extras;
    String section;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_view);

        //Bind views
        ButterKnife.bind(this);

        //Initialize views
        initViews();

        presenter = new MoviesPresenter(this);
        presenter.getMoviesBySection(section);

    }

    @Override
    public void initViews() {

        extras = getIntent().getExtras();
        section = extras.getString("section");
        title = extras.getString("title");

        setTitle(title);

        Log.d("BY GENRE VIEW ", "INITIALIZED");

        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        rv.setLayoutManager(new GridLayoutManager(MovieGridView.this, 2));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);

        adapter = new MoviesAdapter(movieList, rv, MovieGridView.this);
        adapter.enableFooter(false);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        swipy.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {

                //On pull down
                adapter.enableFooter(false);
                movieList.clear();
                presenter = new MoviesPresenter(MovieGridView.this);
                presenter.getMoviesBySection(section);

            }
        });

        adapter.setOnBottomReachedListener(new MoviesContract.OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                presenter.getNextPageBySection(section);
            }
        });

        state.setStateListener(this);
        state.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        state.setViewState(MultiStateView.VIEW_STATE_LOADING);

                        movieList.clear();
                        presenter = new MoviesPresenter(MovieGridView.this);
                        presenter.getNextPageBySection(section);
                    }
                });
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {
        showToast(error);

        movieList.clear();
        adapter.enableFooter(false);
        adapter.notifyDataSetChanged();
        state.setViewState(MultiStateView.VIEW_STATE_ERROR);
        swipy.setRefreshing(false);
        Log.d("RESPONSE ERROR! ", "Response error");
    }

    @Override
    public void showMoviesBySection(ResObj resObj) {

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

            movieList.clear();
            movieList.addAll(resObj.getData().getMovies());
            adapter.notifyDataSetChanged();
            swipy.setRefreshing(false);
            state.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            adapter.enableFooter(true);
        }
    }

    @Override
    public void showNextPageBySection(ResObj resObj) {

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

            //Check if page number is equal to last page
            if (page_number == last_page - 1) {

                Log.d("Last page reached", String.valueOf(last_page));
                adapter.enableFooter(false);
            } else {

                movieList.addAll(resObj.getData().getMovies());
                adapter.notifyDataSetChanged();
                swipy.setRefreshing(false);
                state.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                adapter.enableFooter(true);
            }
        }

    }

    @Override
    public void onStateChanged(@MultiStateView.ViewState int viewState) {
        Log.v("MovieGridView", " View State: " + viewState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.by_genre_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort) {





            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
