package com.example.cargo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class updatecar extends AppCompatActivity {
    private static final int REQUEST_IMAGE_SELECT = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 100;
    private RequestQueue queue;
    private EditText carBrandrSpin;
    private EditText locationtxt;
    private EditText carYearspin;
    private EditText seatsText;
    private EditText carTransmition;
    private EditText carFuel;
    private EditText CarPrice;
    private Button updatebtn;
    private Button upload;
    private Button deleteButton;
    private ImageButton imageButton;
    private Uri selectedImageUri;
    private int carId;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatepage);

        imageButton = findViewById(R.id.imageButton);
        deleteButton = findViewById(R.id.delete_button);
        upload = findViewById(R.id.button);
        Intent intent = getIntent();
        String imageUrl =  intent.getStringExtra("image");

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this).load(imageUrl).into(imageButton); // Load the image using Glide
        }

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStoragePermissionAndSelectImage();
            }
        });

        carBrandrSpin = findViewById(R.id.carBrandrSpin);
        locationtxt = findViewById(R.id.locationtxt);
        carYearspin = findViewById(R.id.carYearspin);
        seatsText = findViewById(R.id.seatsText);
        carTransmition = findViewById(R.id.carTransmition);
        carFuel = findViewById(R.id.carFuel);
        CarPrice = findViewById(R.id.CarPrice);

        updatebtn = findViewById(R.id.updatebtn);

        carId = intent.getIntExtra("car_id", -1);
        fetchCarDetails();

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
    }

    private void checkStoragePermissionAndSelectImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            } else {
                selectImageFromGallery();
            }
        } else {
            selectImageFromGallery();
        }
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_SELECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_SELECT && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imageButton.setImageURI(selectedImageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImageFromGallery();
            } else {
                // Permission denied
            }
        }
    }

    private void fetchCarDetails() {
        String url = "http://192.168.1.104/android/get_car_details.php?car_id=" + carId;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject car = new JSONObject(response);
                            carBrandrSpin.setText(car.getString("brand"));
                            locationtxt.setText(car.getString("location"));
                            carYearspin.setText(car.getString("year_model"));
                            seatsText.setText(car.getString("seats_number"));
                            carTransmition.setText(car.getString("transmission"));
                            carFuel.setText(car.getString("motor_fuel"));
                            CarPrice.setText(car.getString("offered_price"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Fetch Car", "Error: " + error.toString());
                    }
                });
        queue.add(stringRequest);
    }

    private void updateCarDetails() {
        String brand = carBrandrSpin.getText().toString();
        String location = locationtxt.getText().toString();
        int yearModel = Integer.parseInt(carYearspin.getText().toString());
        int seatsNumber = Integer.parseInt(seatsText.getText().toString());
        String transmission = carTransmition.getText().toString();
        String motorFuel = carFuel.getText().toString();
        double offeredPrice = Double.parseDouble(CarPrice.getText().toString());
        String url = "http://192.168.1.104/android/update.php";

//        String url = "http://192.168.68.52/android/update.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Update Car", "Response: " + response);
                        // Navigate back to the previous activity and refresh the data
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Update Car", "Error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("car_id", String.valueOf(carId));
                params.put("brand", brand);
                params.put("location", location);
                params.put("year_model", String.valueOf(yearModel));
                params.put("seats_number", String.valueOf(seatsNumber));
                params.put("transmission", transmission);
                params.put("motor_fuel", motorFuel);
                params.put("offered_price", String.valueOf(offeredPrice));
                // If the user selected a new image, add it to the params
                if (selectedImageUri != null) {
                    params.put("image", selectedImageUri.toString());
                }
                return params;
            }
        };
        queue.add(stringRequest);
    }


    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Update Car")
                .setMessage("Are you sure you want to update this car's details?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        updateCarDetails();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Car")
                .setMessage("Are you sure you want to delete this car?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCar();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteCar() {
        String url = "http://192.168.1.104/android/delete.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Delete Car", "Response: " + response);
                        setResult(RESULT_OK);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Delete Car", "Error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("car_id", String.valueOf(carId));
                return params;
            }
        };
        queue.add(stringRequest);
    }

}