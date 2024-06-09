package com.example.cargo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class mainpage extends AppCompatActivity {
    private Button serbtn ;
    private EditText edit_pickupLocation;
    private ImageView aboutBtn ,contactBtn ,homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        serbtn = findViewById(R.id.search);
        contactBtn = findViewById(R.id.contacticon);
        aboutBtn = findViewById(R.id.aboutIcon);
        homeBtn = findViewById(R.id.homepic);
        edit_pickupLocation = findViewById(R.id.edt_pickupLocation);




        serbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pickupLocation = edit_pickupLocation.getText().toString().trim();
                Intent intent = new Intent(mainpage.this, RentRecyclerView.class);
                if (!TextUtils.isEmpty(pickupLocation)) {
                    intent.putExtra("location", pickupLocation);
                }
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
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainpage.this, mainpage.class);
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

    }
}
