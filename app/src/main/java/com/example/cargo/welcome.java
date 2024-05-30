package com.example.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class welcome  extends AppCompatActivity {

        private Button booking;
        private VideoView videoView;
        private int playbackPosition = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.welcome);
            videoView = findViewById(R.id.videoView);
            videoView.setVideoPath("android.resource://"+getPackageName()+"/"+ R.raw.welc);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            booking = findViewById(R.id.booking);
            booking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create an Intent to start the next Activity (replace NextActivity with your actual class name)
                    Intent intent = new Intent(welcome.this ,HomePage.class);
                    // Start the next Activity
                    startActivity(intent);
                }
            });
        }
        @Override
        protected void onStart() {
            super.onStart();
            if (videoView != null) {
                videoView.seekTo(playbackPosition);
                videoView.start();
            }
        }

        @Override
        protected void onResume() {
            super.onResume();
            if (videoView != null && !videoView.isPlaying()) {
                videoView.seekTo(playbackPosition);
                videoView.start();
            }
        }

        @Override
        protected void onPause() {
            super.onPause();
            if (videoView != null && videoView.isPlaying()) {
                videoView.pause();
                playbackPosition = videoView.getCurrentPosition();
            }
        }

        @Override
        protected void onStop() {
            super.onStop();
            if (videoView != null) {
                videoView.stopPlayback();
            }
        }
    }

