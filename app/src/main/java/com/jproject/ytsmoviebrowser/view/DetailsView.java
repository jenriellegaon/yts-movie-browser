package com.jproject.ytsmoviebrowser.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import com.jproject.ytsmoviebrowser.presenter.adapters.PagerAdapter;
import com.jproject.ytsmoviebrowser.presenter.presenter.DetailsPresenter;
import com.jproject.ytsmoviebrowser.presenter.util.GlideApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

public class DetailsView extends AppCompatActivity implements DetailsContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.iv)
    ImageView background_image_view;

    PagerAdapter pagerAdapter;
    ViewPager viewPager;

    DetailsPresenter presenter;

    Bundle extras;
    String movie_id;
    String movie_title;
    String bg_image;

    List<String> torrentQuality = new ArrayList<>();
    List<String> torrentUrl = new ArrayList<>();

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

        }

        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle(movie_title);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        presenter = new DetailsPresenter(this);
        presenter.getMovieDetails(movie_id);


    }

    @SuppressLint("LongLogTag")
    @Override
    public void showMovieDetails(ResObj resObj) {

        bg_image = resObj.getData().getMovie().getMediumScreenshotImage1();

        Log.d("RESULT", resObj.getStatus());

        if (resObj.getStatus().equals("ok")) {

            setBgImage(getApplicationContext(), bg_image);

            for (int i = 0; i <= 1; i++) {

                try {
                    Log.d("Torrent" + i, String.valueOf(resObj.getData().getMovie().getTorrents().get(i).getQuality()));
                    torrentQuality.add(String.valueOf(resObj.getData().getMovie().getTorrents().get(i).getQuality()));
                    torrentUrl.add(String.valueOf(resObj.getData().getMovie().getTorrents().get(i).getUrl()));

                } catch (IndexOutOfBoundsException error) {
                    // Output expected IndexOutOfBoundsExceptions.
                    Log.e("IndexOutOfBoundsException", String.valueOf(error));
                }

            }

            Log.d(movie_title, "READY");
        }

    }

    @Override
    public void showError(String error) {
        showToast(error);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setBgImage(Context context, String imageurl) {

        GlideApp.with(context)
                .asDrawable()
                .load(imageurl)
                .transition(DrawableTransitionOptions.withCrossFade())
//                .placeholder(R.drawable.placeholder_landscape)
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
//            showToast("Download Torrent");

            new MaterialDialog.Builder(this)
                    .title("Download Torrent")
                    .items(torrentQuality)
                    .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

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


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
