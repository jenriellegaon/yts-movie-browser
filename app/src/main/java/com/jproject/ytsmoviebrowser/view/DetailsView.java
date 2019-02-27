package com.jproject.ytsmoviebrowser.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jproject.ytsmoviebrowser.R;
import com.jproject.ytsmoviebrowser.contract.DetailsContract;
import com.jproject.ytsmoviebrowser.model.data.details.ResObj;
import com.jproject.ytsmoviebrowser.model.data.details.Sections;
import com.jproject.ytsmoviebrowser.presenter.adapters.DetailsAdapter;
import com.jproject.ytsmoviebrowser.presenter.presenter.DetailsPresenter;
import com.jproject.ytsmoviebrowser.presenter.util.GlideApp;
import com.kennyc.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

public class DetailsView extends AppCompatActivity implements DetailsContract.View, MultiStateView.StateListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv)
    ImageView background_image_view;

    @BindView(R.id.fabPlayTrailer)
    FloatingActionButton fabPlayTrailer;

    @BindView(R.id.rvDetails)
    RecyclerView rvDetails;

    @BindView(R.id.multiStateViewDetails)
    MultiStateView msvDetails;

    DetailsPresenter presenter;

    //From bundle
    Bundle extras;
    String movie_id;
    String movie_title;
    String bg_image;
    //From bundle

    String synopsis;
    String ytcode;
    String quality;
    String size;
    String torrent_url;
    String mpaRating;
    String rating;
    String year;
    String movie_genres;
    String runtime;

    List<String> torrentQuality = new ArrayList<>();
    List<String> torrentUrl = new ArrayList<>();

    List<Sections> sectionList = new ArrayList<>();
    DetailsAdapter adapter;
    Sections sections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);

        //Bind view
        ButterKnife.bind(this);

        //Initialize views
        initViews();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void initViews() {

        extras = getIntent().getExtras();

        if (extras != null) {

            movie_id = extras.getString("movie_id");
            movie_title = extras.getString("movie_title");
            movie_genres = extras.getString("genres");
        }

        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle(movie_title);

        rvDetails.setLayoutManager(new LinearLayoutManager(DetailsView.this));
        rvDetails.setItemAnimator(new DefaultItemAnimator());
        rvDetails.setHasFixedSize(true);

        adapter = new DetailsAdapter(sectionList, rvDetails, getApplicationContext());
        rvDetails.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });


        fabPlayTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ytcode != null) {
                    showTrailer();
                } else {
                    showToast("Unavailable");
                }

            }
        });

        msvDetails.setStateListener(this);
        msvDetails.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        msvDetails.setViewState(MultiStateView.VIEW_STATE_LOADING);

                        presenter = new DetailsPresenter(DetailsView.this);
                        presenter.getMovieDetails(movie_id);
                    }
                });


        presenter = new DetailsPresenter(DetailsView.this);
        presenter.getMovieDetails(movie_id);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void showMovieDetails(ResObj resObj) {

        Log.d("RESULT", resObj.getStatus());

        if (resObj.getStatus().equals("ok")) {

            bg_image = resObj.getData().getMovie().getBackgroundImageOriginal();
            setImage(getApplicationContext(), bg_image);

            //Details
            ytcode = String.valueOf(resObj.getData().getMovie().getYtTrailerCode());

            synopsis = String.valueOf(resObj.getData().getMovie().getDescriptionFull());
            if (synopsis.isEmpty()) {
                synopsis = getString(R.string.unavailable);
            }
            sections = new Sections();
            sections.setTitle("Synopsis");
            sections.setDetails(synopsis);
            sectionList.add(sections);

            if (movie_genres.isEmpty()) {
                movie_genres = getString(R.string.unavailable);
            }
            sections = new Sections();
            sections.setTitle("Genre");
            sections.setDetails(movie_genres.replaceAll("\\[|\\]", ""));
            sectionList.add(sections);

            year = String.valueOf(resObj.getData().getMovie().getYear());
            if (year.isEmpty()) {
                year = getString(R.string.unavailable);
            }
            sections = new Sections();
            sections.setTitle("Year");
            sections.setDetails(year);
            sectionList.add(sections);

            mpaRating = String.valueOf(resObj.getData().getMovie().getMpaRating());
            if (mpaRating.isEmpty()) {
                mpaRating = getString(R.string.unavailable);
            }
            sections = new Sections();
            sections.setTitle("MPA Rating");
            sections.setDetails(mpaRating);
            sectionList.add(sections);

            rating = String.valueOf(resObj.getData().getMovie().getRating());
            if (rating.isEmpty()) {
                rating = getString(R.string.unavailable);
            }
            sections = new Sections();
            sections.setTitle("Rating");
            sections.setDetails(rating);
            sectionList.add(sections);

            runtime = String.valueOf(resObj.getData().getMovie().getRuntime());
            if (runtime.isEmpty() || runtime.equals("0")) {
                runtime = getString(R.string.unavailable);
                sections = new Sections();
                sections.setTitle("Run Time");
                sections.setDetails(runtime);
                sectionList.add(sections);
            } else {
                sections = new Sections();
                sections.setTitle("Run Time");
                sections.setDetails(runtime + " minutes");
                sectionList.add(sections);
            }

            Log.d(movie_title + " Synopsis", synopsis);
            Log.d(movie_title + " Genres", movie_genres);
            Log.d(movie_title + " MPA Rating", mpaRating);
            Log.d(movie_title + " Rating", rating);
            Log.d(movie_title + " Year", year);
            Log.d(movie_title + " Runtime", runtime);

            for (int i = 0; i <= 5; i++) {

                try {

                    quality = String.valueOf(resObj.getData().getMovie().getTorrents().get(i).getQuality());
                    size = String.valueOf(resObj.getData().getMovie().getTorrents().get(i).getSize());
                    torrent_url = String.valueOf(resObj.getData().getMovie().getTorrents().get(i).getUrl());

                    Log.d("Torrent " + quality, " Available");

                    torrentQuality.add(quality + " (" + size + ")");
                    torrentUrl.add(torrent_url);
                    //Details

                } catch (NullPointerException npe) {
                    //Output expected NullPointer Exceptions.
                    Log.e("NullPointerException", String.valueOf(npe));

                } catch (IndexOutOfBoundsException ioobe) {
                    // Output expected IndexOutOfBoundsExceptions.
                    Log.e("IndexOutOfBoundsException", String.valueOf(ioobe));
                }

            }

            msvDetails.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            Log.d(movie_title, "READY");
        }

    }

    @Override
    public void showError(String error) {
//        showToast(error);
        msvDetails.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setImage(Context context, String imageUrl) {

        GlideApp.with(context)
                .asDrawable()
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.d("FAILED", e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("GLIDE", "READY");
                        return false;
                    }
                })
                .into(background_image_view);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_menu_view, menu);
        return true;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_download) {

            if (torrentUrl.isEmpty()) {
                showToast("Unavailable");
            } else {

                new MaterialDialog.Builder(getApplicationContext())
                        .title("Download Torrent")
                        .items(torrentQuality)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, final int which, CharSequence text) {


                                showToast(torrentUrl.get(which));
                                return true;
                            }
                        })
                        .positiveText("Download")
                        .widgetColor(getResources().getColor(android.R.color.white))
                        .backgroundColor(getResources().getColor(R.color.primaryDarkTextColor))
                        .choiceWidgetColor(getColorStateList(R.color.primaryLightColor))
                        .titleColor(getResources().getColor(android.R.color.white))
                        .positiveColor(getResources().getColor(R.color.primaryLightColor))
                        .contentColor(getResources().getColor(android.R.color.white))
                        .show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStateChanged(@MultiStateView.ViewState int viewState) {
        Log.v("Details View", " View State: " + viewState);
    }

    public void showTrailer() {
        Intent showTrailer = new Intent(DetailsView.this, TrailerView.class);
        showTrailer.putExtra("ytcode", ytcode);
        showTrailer.putExtra("title", movie_title);
        showTrailer.putExtra("year", year);
        startActivity(showTrailer);
    }


}
