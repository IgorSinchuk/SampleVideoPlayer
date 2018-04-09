package com.nonexistentware.igorsinchuk.samplevideoplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class ViewVideo extends Activity {
      private String filename;
      VideoView videoView;
      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Intent play = getIntent();
            Bundle extras = play.getExtras();
            filename = extras.getString("videofilename");
            setContentView(R.layout.activity_view);
            videoView = (VideoView) findViewById(R.id.videoView);
            videoView.setVideoPath(filename);
            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();
            videoView.start();
      }
}
