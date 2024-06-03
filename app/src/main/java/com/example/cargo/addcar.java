package com.example.cargo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

    private static final int IMAGE_PICK_CODE = 1000;
    private static final String URL = "http://192.168.1.104/android/add_car.php";


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
            carImageView.setImageURI(data.getData());
        }
    }

    private String imageToString() {
        Bitmap bitmap = ((BitmapDrawable) carImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
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
}