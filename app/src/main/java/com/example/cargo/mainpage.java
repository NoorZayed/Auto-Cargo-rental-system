package com.example.cargo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.DialogInterface;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class mainpage extends AppCompatActivity {
    private Button serbtn, contact, moreBtn; // Changed logout to moreBtn
    private EditText edit_pickupLocation;
    private ImageView aboutBtn, contactBtn, homeBtn; // Removed logout
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
        moreBtn = findViewById(R.id.accountIcon); // Changed to more button
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

        moreBtn.setOnClickListener(new View.OnClickListener() { // Updated click listener to handle more options
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
        final Drawable[] icons = {
                getResources().getDrawable(R.drawable.ic_settings),
                getResources().getDrawable(R.drawable.profile),
                getResources().getDrawable(R.drawable.logout)
        };

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

        // Customize the AlertDialog appearance
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Change dialog width
        alertDialog.getWindow().setLayout(1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Change font color and style
        alertDialog.getListView().setBackgroundColor(Color.RED);
        alertDialog.getListView().setDivider(new ColorDrawable(Color.WHITE));
        alertDialog.getListView().setDividerHeight(2);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, options) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);

                // Set icon
                Drawable icon = icons[position];
                if (icon != null) {
                    int iconSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()); // Change 24 to your desired icon size
                    icon.setBounds(0, 0, iconSize, iconSize);
                    textView.setCompoundDrawables(icon, null, null, null);
                }
                return view;
            }
        };
        alertDialog.getListView().setAdapter(adapter);
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
