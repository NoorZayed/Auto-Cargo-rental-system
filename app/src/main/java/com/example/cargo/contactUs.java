package com.example.cargo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class contactUs extends AppCompatActivity {

    private LinearLayout menu_feedback;
    private LinearLayout menu_homep;
    private LinearLayout menu_about;
    private LinearLayout menu_account;
    private LinearLayout menu_contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_us);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        menu_feedback = findViewById(R.id.menu_feedback);
        menu_homep = findViewById(R.id.menu_homep);
        menu_about = findViewById(R.id.menu_about);
        menu_account = findViewById(R.id.menu_account);
        menu_contact = findViewById(R.id.menu_contact);
        TextView phoneTextView = findViewById(R.id.textView5);
        TextView emailTextView = findViewById(R.id.textView4);
        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the phone dialer
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+9705678736728"));
                startActivity(intent);
            }
        });
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the email app
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:cargo@gmail.com"));
                // Optionally add email subject and body
                intent.putExtra(Intent.EXTRA_SUBJECT, "Your Subject");
                intent.putExtra(Intent.EXTRA_TEXT, "Your email body");
                startActivity(intent);
            }
        });


        menu_homep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contactUs.this ,mainpage.class);
                // Start the next Activity
                startActivity(intent);

            }
        });
    }
}