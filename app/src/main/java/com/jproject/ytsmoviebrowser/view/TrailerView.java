package com.jproject.ytsmoviebrowser.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.jproject.ytsmoviebrowser.R;
import com.jproject.ytsmoviebrowser.contract.TrailerContract;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.ui.PlayerUIController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerView extends AppCompatActivity implements TrailerContract.View {

    @BindView(R.id.trailer_view)
    YouTubePlayerView trailer_view;

    Bundle extras;
    String ytcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_movie_trailer);


        //Bind view
        ButterKnife.bind(this);

        //Initialize view
        initViews();
    }

    @Override
    public void initViews() {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        extras = getIntent().getExtras();

        if (extras != null) {
            ytcode = extras.getString("ytcode");

            PlayerUIController playerUIController = trailer_view.getPlayerUIController();
            playerUIController.showFullscreenButton(false);
            playerUIController.showYouTubeButton(true);

            trailer_view.enterFullScreen();
            trailer_view.initialize(new YouTubePlayerInitListener() {
                @Override
                public void onInitSuccess(final YouTubePlayer initializedYouTubePlayer) {
                    initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady() {

                            initializedYouTubePlayer.cueVideo(ytcode, 0);
                        }
                    });
                }
            }, true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
