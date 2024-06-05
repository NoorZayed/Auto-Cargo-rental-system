package com.example.cargo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class rentedCars extends AppCompatActivity {
    private static final String URL = "http://192.168.1.104/android/rented.php";
//    private static final String URL = "http://192.168.68.52/android/rented.php";


        private static final String TAG = "rentedCars";
        private static final String KEY_RENTED_CARS = "rentedCarsList";
        private RequestQueue queue;
        private RecyclerView RV;
        private carAdapter adapter;
        private List<Car> rentedCarsList;
        private ProgressBar progressBar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_rented_cars);

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            RV = findViewById(R.id.RV);
            progressBar = findViewById(R.id.progressBar);
            queue = Volley.newRequestQueue(this);
            RV.setLayoutManager(new LinearLayoutManager(this));
            rentedCarsList = new ArrayList<>();
            adapter = new carAdapter(rentedCarsList);
            RV.setAdapter(adapter);

            if (savedInstanceState == null) {
                fetchRentedCars();
            } else {
                String json = savedInstanceState.getString(KEY_RENTED_CARS);
                if (json != null) {
                    rentedCarsList = deserializeFromJson(json);
                    adapter.updateList(rentedCarsList);
                }
            }
        }

        @Override
        protected void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            String json = serializeToJson(rentedCarsList);
            outState.putString(KEY_RENTED_CARS, json);
        }

        private void fetchRentedCars() {
            progressBar.setVisibility(View.VISIBLE);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                    response -> {
                        progressBar.setVisibility(View.GONE);
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                JSONArray carsArray = response.getJSONArray("rentedCars");
                                for (int i = 0; i < carsArray.length(); i++) {
                                    JSONObject carObject = carsArray.getJSONObject(i);
                                    int id = carObject.getInt("id");
                                    String brand = carObject.getString("brand");
                                    String location = carObject.getString("location");
                                    int yearModel = carObject.getInt("year_model");
                                    int seatsNumber = carObject.getInt("seats_number");
                                    String transmission = carObject.getString("transmission");
                                    String motorFuel = carObject.getString("motor_fuel");
                                    double offeredPrice = carObject.getDouble("offered_price");
                                    String image = carObject.getString("image");

                                    rentedCarsList.add(new Car(id, brand, location, yearModel, seatsNumber, transmission, motorFuel, offeredPrice, image));
                                    Log.d(TAG, "Car added: " + brand);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Log.d(TAG, "Success is false");
                                Toast.makeText(rentedCars.this, getString(R.string.no_cars_available), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Error: " + error.toString());
                Toast.makeText(rentedCars.this, getString(R.string.error_fetching_data), Toast.LENGTH_SHORT).show();
            }
            );
            queue.add(jsonObjectRequest);
        }

        private String serializeToJson(List<Car> cars) {
            Gson gson = new Gson();
            return gson.toJson(cars);
        }

        private List<Car> deserializeFromJson(String json) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Car>>() {}.getType();
            return gson.fromJson(json, type);
        }
    }
