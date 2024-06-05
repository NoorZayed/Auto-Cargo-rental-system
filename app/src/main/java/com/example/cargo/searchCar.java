package com.example.cargo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

public class searchCar extends AppCompatActivity {
    private RequestQueue queue;
    private RecyclerView RV;
    private carAdapter adapter;
    private List<Car> CarsList;

    private EditText searchEditText;
    private Button searchButton;
    private Spinner spinnerReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_car);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RV = findViewById(R.id.RV);
        queue = Volley.newRequestQueue(this);
        RV.setLayoutManager(new LinearLayoutManager(this));
        CarsList = new ArrayList<>();
        adapter = new carAdapter(CarsList);
        RV.setAdapter(adapter);

        searchEditText = findViewById(R.id.searchtxt);
        searchButton = findViewById(R.id.searchbtn);

        // Initialize the Spinner and set up the adapter with static filters
        spinnerReport = findViewById(R.id.spinnerreport);
        String[] filters = {"Select", "brand", "location", "year_model", "seats_number", "transmission", "motor_fuel", "offered_price"};        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filters);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filters);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReport.setAdapter(adapter);

        fetchCars();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = searchEditText.getText().toString().trim();
                String filter = spinnerReport.getSelectedItem().toString();
                if (!TextUtils.isEmpty(keyword)&& !filter.equals("Select")) {
                    CarsList.clear();
                    adapter.notifyDataSetChanged();
                    searchCars(keyword, filter);
                } else {
                    CarsList.clear();
                    adapter.notifyDataSetChanged();
                    fetchCars();
                }
            }
        });
    }

    private void fetchCars() {
        //String url = "http://192.168.1.104/android/fetch_cars.php";
        String url = "http://192.168.68.66/android/fetch_cars.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                JSONArray carsArray = response.getJSONArray("cars");
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

                                    CarsList.add(new Car(id, brand, location, yearModel, seatsNumber, transmission, motorFuel, offeredPrice, image));
                                    Log.d("fetchCars", "Car added: " + brand);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Log.d("fetchCars", "Success is false");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("fetchCars", "Error: " + error.toString());
            }
        }
        );
        queue.add(jsonObjectRequest);
    }


    private void searchCars(String keyword,String filter) {
       // String url = "http://192.168.1.104/android/search.php?searchQuery=" + keyword + "&filter=" + filter;
        String url = "http://192.168.68.66/android/search.php?searchQuery=" + keyword + "&filter=" + filter;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        CarsList.clear();  // Clear the list before adding new items
                        try {
                            JSONArray carsArray = response.getJSONArray("cars");
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

                                CarsList.add(new Car(id, brand, location, yearModel, seatsNumber, transmission, motorFuel, offeredPrice, image));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
            }
        });
        queue.add(jsonObjectRequest);
    }
}
