package com.studio.matchtune.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.VideoView;

public class activity_splash extends Activity {

    private VideoView mVideoView;


    private String getVersion() {
        String version = "";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e(this.getClass().getSimpleName(), "Name not found", e1);
        }
        return version;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_splash);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);
        mVideoView = (VideoView) findViewById(R.id.videoview);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_splash_compressed_cropped_cropped);
        mVideoView.setVideoURI(uri);
        mVideoView.setZOrderOnTop(false);
        SurfaceHolder surfaceholder = mVideoView.getHolder();
        surfaceholder.setFormat(PixelFormat.TRANSPARENT);
        mVideoView.setZOrderOnTop(true);
        mVideoView.start();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(false);
                mediaPlayer.start();
            }
        });
        // video finish listener
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.reset();
                Intent intent = new Intent(activity_splash.this, MainActivity.class);
                startActivity(intent);
                activity_splash.this.finish();

            }
        });
    }

    @Override
    protected void onResume() {
        mVideoView.resume();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        mVideoView.start();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        mVideoView.suspend();
        super.onPause();
    }

}