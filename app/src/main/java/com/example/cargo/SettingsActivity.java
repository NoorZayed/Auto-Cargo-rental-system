package com.example.cargo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Find TextViews
        TextView firstNameTextView = findViewById(R.id.firstNameTextView);
        TextView lastNameTextView = findViewById(R.id.lastNameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView passwordTextView = findViewById(R.id.passwordTextView);
        TextView phoneNumberTextView = findViewById(R.id.phoneNumberTextView);

        // Set click listeners for TextViews
        firstNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on First Name TextView
                // For example, start a new activity to update first name
                startActivityToUpdateActivity("firstName");
            }
        });

        lastNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on Last Name TextView
                // For example, start a new activity to update last name
                startActivityToUpdateActivity("lastName");
            }
        });

        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on Email TextView
                // For example, start a new activity to update email
                startActivityToUpdateActivity("email");
            }
        });

        passwordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on Password TextView
                // For example, start a new activity to update password
                startActivityToUpdateActivity("password");
            }
        });

        phoneNumberTextView.setOnClickListener(new View.OnClickListener() {
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
