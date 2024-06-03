package com.example.cargo;

import android.os.Bundle;
import android.util.Log;

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
        fetchRentedCars();

    }

private void fetchRentedCars() {
   // String url = "http://192.168.1.104/android/rented.php";
    String url = "http://192.168.68.52/android/rented.php";
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
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
                                Log.d("fetchRentedCars", "Car added: " + brand);
                            }
                            adapter.notifyDataSetChanged();
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
    }
    );
    queue.add(jsonObjectRequest);
}


}