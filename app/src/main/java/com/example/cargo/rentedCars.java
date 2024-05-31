package com.example.cargo;

import android.os.Bundle;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class rentedCars extends AppCompatActivity {
    private RequestQueue queue;
    private RecyclerView RV;
    private carAdapter adapter;
    private List<Car> rentedCarsList;
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
        adapter = new carAdapter(rentedCarsList);
        RV.setAdapter(adapter);

    }
    private void fetchRentedCars() {
        String url = "URL_TO_PHP_SCRIPT_FOR_FETCHING_RENTED_CARS";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject carObject = response.getJSONObject(i);
                                int id = carObject.getInt("id");
                                String brand = carObject.getString("brand");
                                String location = carObject.getString("location");
                                int yearModel = carObject.getInt("year_model");
                                int seatsNumber = carObject.getInt("seats_number");
                                String transmission = carObject.getString("transmission");
                                String motorFuel = carObject.getString("motor_fuel");
                                double offeredPrice = carObject.getDouble("offered_price");
                                // Assuming the image is stored as a byte array in the database
                                byte[] imageBlob = carObject.getString("image_blob").getBytes(); // Replace getString() with getBlob() if the image is stored as a BLOB

                                // Create a new Car object with the parsed data and add it to the rentedCarsList
                                rentedCarsList.add(new Car(id, brand, location, yearModel, seatsNumber, transmission, motorFuel, offeredPrice, imageBlob));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
            }
        });
        queue.add(jsonArrayRequest);
    }
}