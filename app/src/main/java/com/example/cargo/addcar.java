package com.example.cargo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class addcar extends AppCompatActivity {

    private EditText brandEditText, locationEditText, yearEditText, seatsEditText, transmissionEditText, fuelEditText, priceEditText;
    private ImageView carImageView;
    private Button uploadButton, addButton;
    private Button backb;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final String URL = "http://192.168.88.2/android/add_car.php";
    //    private static final String URL = "http://192.168.68.52/android/add_car.php";

    private Uri imageUri;
    private Bitmap carBitmap;

    private static final String STATE_IMAGE_URI = "image_uri";
    private static final String STATE_BRAND = "brand";
    private static final String STATE_LOCATION = "location";
    private static final String STATE_YEAR = "year";
    private static final String STATE_SEATS = "seats";
    private static final String STATE_TRANSMISSION = "transmission";
    private static final String STATE_FUEL = "fuel";
    private static final String STATE_PRICE = "price";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcar);

        brandEditText = findViewById(R.id.carBrandrSpin);
        locationEditText = findViewById(R.id.locationtxt);
        yearEditText = findViewById(R.id.carYearspin);
        seatsEditText = findViewById(R.id.seatsText);
        transmissionEditText = findViewById(R.id.carTransmition);
        fuelEditText = findViewById(R.id.carFuel);
        priceEditText = findViewById(R.id.CarPrice);
        carImageView = findViewById(R.id.imageView3);
        uploadButton = findViewById(R.id.uploadbtn);
        addButton = findViewById(R.id.addbtn);
        backb = findViewById(R.id.back);

        if (savedInstanceState != null) {
            imageUri = savedInstanceState.getParcelable(STATE_IMAGE_URI);
            brandEditText.setText(savedInstanceState.getString(STATE_BRAND));
            locationEditText.setText(savedInstanceState.getString(STATE_LOCATION));
            yearEditText.setText(savedInstanceState.getString(STATE_YEAR));
            seatsEditText.setText(savedInstanceState.getString(STATE_SEATS));
            transmissionEditText.setText(savedInstanceState.getString(STATE_TRANSMISSION));
            fuelEditText.setText(savedInstanceState.getString(STATE_FUEL));
            priceEditText.setText(savedInstanceState.getString(STATE_PRICE));

            if (imageUri != null) {
                carImageView.setImageURI(imageUri);
            }
        }

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCar();
            }
        });

        backb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the next Activity (replace NextActivity with your actual class name)
                Intent intent = new Intent(addcar.this, adminpage.class);
                // Start the next Activity
                startActivity(intent);
            }
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data.getData();
            carImageView.setImageURI(imageUri);
            carBitmap = ((BitmapDrawable) carImageView.getDrawable()).getBitmap();
        }
    }

    private String imageToString() {
        if (carBitmap == null) {
            return "";
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        carBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void addCar() {
        final String brand = brandEditText.getText().toString().trim();
        final String location = locationEditText.getText().toString().trim();
        final String year = yearEditText.getText().toString().trim();
        final String seats = seatsEditText.getText().toString().trim();
        final String transmission = transmissionEditText.getText().toString().trim();
        final String fuel = fuelEditText.getText().toString().trim();
        final String price = priceEditText.getText().toString().trim();
        final String image = imageToString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        brandEditText.setText(null);
                        locationEditText.setText(null);
                        yearEditText.setText(null);
                        seatsEditText.setText(null);
                        transmissionEditText.setText(null);
                        fuelEditText.setText(null);
                        priceEditText.setText(null);
                        carImageView.setImageURI(null);
                        carBitmap = null;

                        Toast.makeText(addcar.this, "Car added successfully!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(addcar.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("brand", brand);
                params.put("location", location);
                params.put("year_model", year);
                params.put("seats_number", seats);
                params.put("transmission", transmission);
                params.put("motor_fuel", fuel);
                params.put("offered_price", price);
                params.put("image", image);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save state
        outState.putParcelable(STATE_IMAGE_URI, imageUri);
        outState.putString(STATE_BRAND, brandEditText.getText().toString().trim());
        outState.putString(STATE_LOCATION, locationEditText.getText().toString().trim());
        outState.putString(STATE_YEAR, yearEditText.getText().toString().trim());
        outState.putString(STATE_SEATS, seatsEditText.getText().toString().trim());
        outState.putString(STATE_TRANSMISSION, transmissionEditText.getText().toString().trim());
        outState.putString(STATE_FUEL, fuelEditText.getText().toString().trim());
        outState.putString(STATE_PRICE, priceEditText.getText().toString().trim());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        imageUri = savedInstanceState.getParcelable(STATE_IMAGE_URI);
        brandEditText.setText(savedInstanceState.getString(STATE_BRAND));
        locationEditText.setText(savedInstanceState.getString(STATE_LOCATION));
        yearEditText.setText(savedInstanceState.getString(STATE_YEAR));
        seatsEditText.setText(savedInstanceState.getString(STATE_SEATS));
        transmissionEditText.setText(savedInstanceState.getString(STATE_TRANSMISSION));
        fuelEditText.setText(savedInstanceState.getString(STATE_FUEL));
        priceEditText.setText(savedInstanceState.getString(STATE_PRICE));

        if (imageUri != null) {
            carImageView.setImageURI(imageUri);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Save data when the activity is paused
        saveData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Save data when the activity is stopped
        saveData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release bitmap to avoid memory leaks
        if (carBitmap != null && !carBitmap.isRecycled()) {
            carBitmap.recycle();
            carBitmap = null;
        }
    }
    private void saveData() {
        // Get the data you want to save
        String brand = brandEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();
        String year = yearEditText.getText().toString().trim();
        String seats = seatsEditText.getText().toString().trim();
        String transmission = transmissionEditText.getText().toString().trim();
        String fuel = fuelEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();
        String image = imageToString();

        // Save the data using SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.putString("brand", brand);
        editor.putString("location", location);
        editor.putString("year", year);
        editor.putString("seats", seats);
        editor.putString("transmission", transmission);
        editor.putString("fuel", fuel);
        editor.putString("price", price);
        editor.putString("image", image);
        editor.apply();
    }

}
