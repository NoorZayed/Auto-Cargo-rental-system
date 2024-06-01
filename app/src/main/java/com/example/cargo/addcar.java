package com.example.cargo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
public class addcar extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private ImageView carImageView;
    private EditText seatsText, carPriceText, locationText, carBrandSpin, carYearSpin, carTransmitionSpin, carFuelSpin;
    private Button uploadButton, addButton;
    private Uri selectedImageUri;
    JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcar);

        // Initialize views
        carImageView = findViewById(R.id.imageView3);
        seatsText = findViewById(R.id.seatsText);
        carPriceText = findViewById(R.id.CarPrice);
        locationText = findViewById(R.id.locationtxt);
        carBrandSpin = findViewById(R.id.carBrandrSpin);
        carYearSpin = findViewById(R.id.carYearspin);
        carTransmitionSpin = findViewById(R.id.carTransmition);
        carFuelSpin = findViewById(R.id.carFuel);
        uploadButton = findViewById(R.id.uploadbtn);
        addButton = findViewById(R.id.addbtn);

        // Set upload button click listener
        uploadButton.setOnClickListener(v -> openGallery());

        // Set add button click listener
        addButton.setOnClickListener(v -> addCar());
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && data != null) {
            selectedImageUri = data.getData();
            carImageView.setImageURI(selectedImageUri);
        }
    }

    private void addCar() {
        // Retrieve values from input fields
        String brand = carBrandSpin.getText().toString();
        String location = locationText.getText().toString();
        int yearModel = Integer.parseInt(carYearSpin.getText().toString());
        int seatsNumber = Integer.parseInt(seatsText.getText().toString());
        String transmission = carTransmitionSpin.getText().toString();
        String motorFuel = carFuelSpin.getText().toString();
        double offeredPrice = Double.parseDouble(carPriceText.getText().toString());

        // Get image data as Base64 String
        String imageData = getImageData(selectedImageUri);

        if (imageData == null) {
            Toast.makeText(this, "Failed to get image data", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a JSON object with the car data
        JSONObject carData = new JSONObject();
        try {
            carData.put("brand", brand);
            carData.put("location", location);
            carData.put("yearModel", yearModel);
            carData.put("seatsNumber", seatsNumber);
            carData.put("transmission", transmission);
            carData.put("motorFuel", motorFuel);
            carData.put("offeredPrice", offeredPrice);
            carData.put("imageData", imageData);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating JSON object", Toast.LENGTH_SHORT).show();
            return;
        }

        // Send the JSON object to the server
        sendCarDataToServer(carData);
    }

    private void sendCarDataToServer(JSONObject carData) {
        String url = "http://192.168.1.104/android/add.php";

        // Create a JsonObjectRequest to send the data to the server
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {  @Override
                public void onResponse(JSONObject response) {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            try {
                                Log.d("ServerResponse", response.toString()); // Log the response
                                String message = response.getString("message");
                                Toast.makeText(addcar.this, message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(addcar.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
//                }
//                        ,
//                error -> {
//                    Log.e("AddCar", "Error: " + error.getMessage());
//                    error.printStackTrace();
//                    Toast.makeText(addcar.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//        );
                        } else {
                            Log.d("carCars", "Success is false");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("addcars", "Error: " + error.toString());
            }
        }
        );
        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }



    private String getImageData(Uri imageUri) {
        if (imageUri == null) {
            return null;
        }

        try {
            // Get the bitmap from the image URI
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

            // Convert bitmap to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            stream.close();

            // Convert byte array to Base64 String
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}