package com.example.cargo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Start extends AppCompatActivity {
    private static final String TAG = "StartActivity";

    ImageView img_logo;
    TextView tf_note;
    private Animation top;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();
    }

    private void setupView() {
        img_logo = findViewById(R.id.img_logo);
        tf_note = findViewById(R.id.tf_note);

        if (img_logo == null || tf_note == null) {
            Log.e(TAG, "ImageView or TextView not found in layout.");
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString("userId", null);

        Log.d(TAG, "User ID: " + user_id);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        top = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        img_logo.setAnimation(top);

        final String animateText = tf_note.getText().toString();
        tf_note.setText("");

        int charDisplayDuration = 300; // Adjust this value for longer display time per character
        int totalDuration = animateText.length() * charDisplayDuration;

        new CountDownTimer(totalDuration, charDisplayDuration) {
            @Override
            public void onTick(long l) {
                if (count < animateText.length()) {
                    tf_note.setText(tf_note.getText().toString() + animateText.charAt(count));
                    count++;
                }
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "CountDownTimer finished");
                transitionToNextActivity(user_id);
            }
        }.start();
    }

    private void transitionToNextActivity(String user_id) {
        Intent intent;
        if (user_id == null || user_id.isEmpty()) {
            Log.d(TAG, "Navigating to login activity");
            intent = new Intent(Start.this, login.class);
        } else {
            Log.d(TAG, "Navigating to welcome activity");
            intent = new Intent(Start.this, welcome.class);
        }
        startActivity(intent);
        finish();
    }
}
