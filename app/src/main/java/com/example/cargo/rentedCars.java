package com.example.cargo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class rentedCars extends AppCompatActivity {
    private RequestQueue queue;
    private RecyclerView RV;
    private rentedAdapter adapter;
    private List<Car> rentedCarsList;
    private Spinner spinnerReport;

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
        queue = Volley.newRequestQueue(this);
        RV.setLayoutManager(new LinearLayoutManager(this));
        rentedCarsList = new ArrayList<>();
        adapter = new rentedAdapter(rentedCarsList);
        RV.setAdapter(adapter);
        spinnerReport = findViewById(R.id.spinner);
        String[] filters = {"Select", "rented this day", "this week", "most rented", "max day rented", "max price" , "least price"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filters);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filters);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReport.setAdapter(adapter);

        fetchRentedCars();
        spinnerReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFilter = parent.getItemAtPosition(position).toString();
                if (!selectedFilter.equals("Select")) {
                    // Call fetchRentedCars() with the selected filter option
                    fetchRentedCars(selectedFilter.toLowerCase().replace(" ", "_"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected (optional)
            }
        });


    }

    private void fetchRentedCars() {
        String url = "http://192.168.1.104/android/rented.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                JSONArray carsArray = response.getJSONArray("rentedCars");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
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
                                    int rate = carObject.getInt("rating");

                                    // Retrieve and parse the date strings
                                    String fromdStr = carObject.getString("date_rental");
                                    String todStr = carObject.getString("drop_date");
                                    Date fromd = null;
                                    Date tod = null;
                                    try {
                                        fromd = sdf.parse(fromdStr);
                                        tod = sdf.parse(todStr);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    String image = carObject.getString("image");

                                    rentedCarsList.add(new Car(id, brand, location, yearModel, seatsNumber, transmission, motorFuel, offeredPrice,rate, fromd, tod,image));
                                    Log.d("fetchRentedCars3", "Car added: " + brand);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Log.d("fetchRentedCars2", "Success is false");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("fetchRentedCars1", "Error: " + error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }
    private void fetchRentedCars(String filter) {
        String url = "http://192.168.1.104/android/rentedF.php?filter=" + filter.replace(" ", "_").toLowerCase();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                rentedCarsList.clear(); // Clear existing list
                                JSONArray carsArray = response.getJSONArray("rentedCars");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
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
                                    int rate = carObject.getInt("rating");

                                    // Retrieve and parse the date strings
                                    String fromdStr = carObject.getString("date_rental");
                                    String todStr = carObject.getString("drop_date");
                                    Date fromd = null;
                                    Date tod = null;
                                    try {
                                        fromd = sdf.parse(fromdStr);
                                        tod = sdf.parse(todStr);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    String image = carObject.getString("image");

                                    rentedCarsList.add(new Car(id, brand, location, yearModel, seatsNumber, transmission, motorFuel, offeredPrice, rate, fromd, tod, image));
                                    Log.d("fetchRentedCars", "Car added: " + brand);
                                }
                                adapter.notifyDataSetChanged(); // Notify adapter about data change
                            } else {
                                Log.d("fetchRentedCars", "Success is false");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("fetchRentedCars", "Error: " + error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }

}
