package com.example.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class mainpage extends AppCompatActivity {
    private Button serbtn ;
    private EditText location;
    private ImageView aboutBtn ,contactBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        serbtn = findViewById(R.id.search);
        contactBtn = findViewById(R.id.contacticon);
        aboutBtn = findViewById(R.id.aboutIcon);
        location = findViewById(R.id.edt_pickupLocation);

        serbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pickupLocation = location.getText().toString().trim();
                Intent intent = new Intent(mainpage.this, RentRecyclerView.class);
                if (!TextUtils.isEmpty(pickupLocation)) {
                    intent.putExtra("location", pickupLocation);
                }
                startActivity(intent);
            }
        });

        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainpage.this, contactUs.class);
                startActivity(intent);
            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainpage.this, AboutUs.class);
                startActivity(intent);
            }
        });
    }
}
