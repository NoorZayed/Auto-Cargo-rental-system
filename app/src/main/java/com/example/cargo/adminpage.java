package com.example.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class adminpage extends AppCompatActivity {
    private Button logoutb;
    private Button addb;
    private Button deleteb;
    private Button cus;
    private Button carsb;
    private Button rentalb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpage);

        logoutb = findViewById(R.id.logout_btn);
        addb = findViewById(R.id.add_btn);
        deleteb = findViewById(R.id.delete_btn);
        cus = findViewById(R.id.custom_btn);
       carsb = findViewById(R.id.allcars_btn);
        rentalb = findViewById(R.id.rental_btn);
        logoutb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the next Activity (replace NextActivity with your actual class name)
                Intent intent = new Intent(adminpage.this ,login.class);
                // Start the next Activity
                startActivity(intent);
            }
        });
        carsb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the next Activity (replace NextActivity with your actual class name)
                Intent intent = new Intent(adminpage.this ,searchCar.class);
                // Start the next Activity
                startActivity(intent);
            }
        });
        addb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the next Activity (replace NextActivity with your actual class name)
                Intent intent = new Intent(adminpage.this ,addcar.class);
                // Start the next Activity
                startActivity(intent);
            }
        });
        deleteb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the next Activity (replace NextActivity with your actual class name)
                Intent intent = new Intent(adminpage.this , RentRecyclerView.class);
                // Start the next Activity
                startActivity(intent);
            }
        });
        cus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the next Activity (replace NextActivity with your actual class name)
                Intent intent = new Intent(adminpage.this ,CustomerListActivity.class);
                // Start the next Activity
                startActivity(intent);
            }
        });
        rentalb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the next Activity (replace NextActivity with your actual class name)
                Intent intent = new Intent(adminpage.this ,rentedCars.class);
                // Start the next Activity
                startActivity(intent);
            }
        });

    }


}
