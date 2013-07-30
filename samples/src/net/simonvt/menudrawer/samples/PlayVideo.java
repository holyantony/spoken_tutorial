package net.simonvt.menudrawer.samples;

 

import android.app.Activity;

import android.content.Context;

import android.content.Intent;

import android.media.MediaPlayer;

import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import android.widget.MediaController;

import android.widget.VideoView;

 

public class PlayVideo extends Activity {

    Button back;

    final Context context=this;

    VideoView v;

    String path;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.video_layout);
//
       
 

        v=(VideoView)findViewById(R.id.video);

        Intent i=getIntent();

        path=i.getStringExtra("path");

        Log.i("path of video file......", path);

        v.setVideoPath(path);

 

        v.setMediaController(new MediaController(context));

        v.requestFocus();

 

        v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override

            public void onPrepared(MediaPlayer arg0) {

            	try {
            		 v.start();
				} catch (Exception e) {
					Toast.makeText(PlayVideo.this, "Error in video", Toast.LENGTH_SHORT).show();
				}
            	
               

            }

        });

 

        v.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {

                mp.reset();

                v.setVideoPath(path);

                v.start();

            }

        });

    }

}

