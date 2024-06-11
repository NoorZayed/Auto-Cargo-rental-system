package com.example.cargo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

public class mainpage extends AppCompatActivity {
    private Button serbtn,contact;
    private EditText edit_pickupLocation;
    private ImageView aboutBtn, contactBtn, homeBtn, moreBtn;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        contact = findViewById(R.id.contact);
        serbtn = findViewById(R.id.search);
        contactBtn = findViewById(R.id.contacticon);
        aboutBtn = findViewById(R.id.aboutIcon);
        homeBtn = findViewById(R.id.homepic);
        edit_pickupLocation = findViewById(R.id.edt_pickupLocation);
        moreBtn = findViewById(R.id.accountIcon); // Assuming accountIcon is the "more" icon

        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLastPickupLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePickupLocation();
    }

    private void setupListeners() {
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
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainpage.this, contactUs.class);
                startActivity(intent);
            }
        });

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreOptionsDialog();
            }
        });
    }

    private void savePickupLocation() {
        String pickupLocation = edit_pickupLocation.getText().toString().trim();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("last_pickup_location", pickupLocation);
        editor.apply();
    }

    private void loadLastPickupLocation() {
        String lastPickupLocation = sharedPreferences.getString("last_pickup_location", "");
        if (!TextUtils.isEmpty(lastPickupLocation)) {
            edit_pickupLocation.setText(lastPickupLocation);
        }
    }

    private void showMoreOptionsDialog() {
        CharSequence[] options = {"Settings", "Profile", "Logout"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("More Options");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Settings
                        Intent settingsIntent = new Intent(mainpage.this, SettingsActivity.class);
                        startActivity(settingsIntent);
                        break;
                    case 1: // Profile
                        Intent profileIntent = new Intent(mainpage.this, ProfileActivity.class);
                        startActivity(profileIntent);
                        break;
                    case 2: // Logout
                        showLogoutConfirmationDialog();
                        break;
                }
            }
        });
        builder.show();
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Clear session data or perform logout actions
                // For now, let's just navigate back to the login page
                Intent loginIntent = new Intent(mainpage.this, login.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loginIntent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Dismiss the dialog
            }
        });
        builder.show();
    }
}
