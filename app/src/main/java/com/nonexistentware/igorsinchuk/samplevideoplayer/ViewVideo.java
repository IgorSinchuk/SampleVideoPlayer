package com.nonexistentware.igorsinchuk.samplevideoplayer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class ViewVideo extends Activity {
      private String filename;
      VideoView videoView;
      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Intent play = getIntent();
            Bundle extras = play.getExtras();
            filename = extras.getString("VideoFile");
            setContentView(R.layout.activity_view);
            videoView = (VideoView) findViewById(R.id.videoView);
            videoView.setVideoPath(filename);
            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();
            videoView.start();
      }
}
