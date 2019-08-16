package com.dueeeke.dkplayer.activity.api;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.dueeeke.dkplayer.R;
import com.dueeeke.dkplayer.util.Utils;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.exo.ExoMediaPlayerFactory;
import com.dueeeke.videoplayer.player.VideoView;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;

import java.io.IOException;

/**
 * 播放raw/assets视频
 */

public class PlayRawAssetsActivity extends AppCompatActivity {

    private VideoView mVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_raw_assets);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.str_raw_or_assets);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mVideoView = findViewById(R.id.player);
        StandardVideoController controller = new StandardVideoController(this);
        mVideoView.setVideoController(controller);
    }


    public void onButtonClick(View view) {
        mVideoView.release();
        Object playerFactory = Utils.getCurrentPlayerFactory();

        switch (view.getId()) {
            case R.id.btn_raw:
                if (playerFactory instanceof ExoMediaPlayerFactory) { //ExoPlayer
                    DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.movie));
                    RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(this);
                    try {
                        rawResourceDataSource.open(dataSpec);
                    } catch (RawResourceDataSource.RawResourceDataSourceException e) {
                        e.printStackTrace();
                    }
                    String url = rawResourceDataSource.getUri().toString();
                    mVideoView.setUrl(url);
                } else { //MediaPlayer,IjkPlayer
                    String url = "android.resource://" + getPackageName() + "/" + R.raw.movie;
                    mVideoView.setUrl(url);
                }
                break;
            case R.id.btn_assets:
                if (playerFactory instanceof ExoMediaPlayerFactory) { //ExoPlayer
                    mVideoView.setUrl("file:///android_asset/" + "test.mp4");
                } else { //MediaPlayer,IjkPlayer
                    AssetManager am = getResources().getAssets();
                    AssetFileDescriptor afd = null;
                    try {
                        afd = am.openFd("test.mp4");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mVideoView.setAssetFileDescriptor(afd);
                }
                break;
        }

        mVideoView.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.release();
    }

    @Override
    public void onBackPressed() {
        if (!mVideoView.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
