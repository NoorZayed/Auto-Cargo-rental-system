package com.example.cargo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Find TextViews
        Button firstNameButton = findViewById(R.id.firstNameButton);
        Button lastNameButton = findViewById(R.id.lastNameButton);
        Button emailButton = findViewById(R.id.emailButton);
        Button passwordButton = findViewById(R.id.passwordButton);
        Button phoneNumberButton = findViewById(R.id.phoneNumberButton);

        // Set click listeners for TextViews
        firstNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on First Name TextView
                // For example, start a new activity to update first name
                startActivityToUpdateActivity("firstName");
            }
        });

        lastNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on Last Name TextView
                // For example, start a new activity to update last name
                startActivityToUpdateActivity("lastName");
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on Email TextView
                // For example, start a new activity to update email
                startActivityToUpdateActivity("email");
            }
        });

        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on Password TextView
                // For example, start a new activity to update password
                startActivityToUpdateActivity("password");
            }
        });

        phoneNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on Phone Number TextView
                // For example, start a new activity to update phone number
                    startActivityToUpdateActivity("phoneNumber");
            }
        });
    }

    // Method to start UpdateActivity with the specified field name
    private void startActivityToUpdateActivity(String fieldName) {
        Intent intent = new Intent(SettingsActivity.this, UpdateActivity.class);
        intent.putExtra("field", fieldName);
        startActivity(intent);
    }
}
