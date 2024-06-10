package com.example.cargo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class contactUs extends AppCompatActivity {

    private LinearLayout menu_feedback;
    private LinearLayout menu_homep;
    private LinearLayout menu_about;
    private LinearLayout menu_account;
    private LinearLayout menu_contact;
    private SharedPreferences sharedPreferences;
  private  TextView emailTextView ,phoneTextView;
    private ImageView aboutBtn,logout;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);

        onResume();
        setupClickListeners();
        saveUserInteraction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        menu_feedback = findViewById(R.id.menu_feedback);
        menu_homep = findViewById(R.id.menu_homep);
        menu_about = findViewById(R.id.menu_about);
        menu_account = findViewById(R.id.menu_account);
        menu_contact = findViewById(R.id.menu_contact);
        phoneTextView = findViewById(R.id.textView5);
        emailTextView = findViewById(R.id.textView4);
        aboutBtn = findViewById(R.id.aboutIcon);
        logout = findViewById(R.id.logout);
    }


    private void setupClickListeners() {
        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+9705678736728"));
                startActivity(intent);
            }
        });

        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:cargo@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Your Subject");
                intent.putExtra(Intent.EXTRA_TEXT, "Your email body");
                startActivity(intent);
            }
        });

        menu_homep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contactUs.this, mainpage.class);
                startActivity(intent);
            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(contactUs.this, AboutUs.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the next Activity (replace NextActivity with your actual class name)
                Intent intent = new Intent(contactUs.this ,login.class);
                // Start the next Activity
                startActivity(intent);
            }
        });
    }

    private void saveUserInteraction() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("visited_contact_us", true);
        editor.apply();
    }

    private boolean hasVisitedBefore() {
        return sharedPreferences.getBoolean("visited_contact_us", false);
    }
}
