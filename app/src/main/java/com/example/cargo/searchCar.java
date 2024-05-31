//package com.example.cargo;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class searchCar extends AppCompatActivity {
//    private RequestQueue queue;
//    private RecyclerView RV;
//    private carAdapter adapter;
//    private List<Car> rentedCarsList;
//    private EditText searchEditText;
//    private Button searchButton;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_search_car);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        RV = findViewById(R.id.RV);
//        queue = Volley.newRequestQueue(this);
//        RV.setLayoutManager(new LinearLayoutManager(this));
//        rentedCarsList = new ArrayList<>();
//        adapter = new carAdapter(rentedCarsList);
//        RV.setAdapter(adapter);
//        fetchRentedCars();
//
//    }
//    private void fetchRentedCars() {
//        String url = "http://192.168.68.52/android/search.php";
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            boolean success = response.getBoolean("success");
//                            if (success) {
//                                JSONArray carsArray = response.getJSONArray("cars");
//                                for (int i = 0; i < carsArray.length(); i++) {
//                                    JSONObject carObject = carsArray.getJSONObject(i);
//                                    int id = carObject.getInt("id");
//                                    String brand = carObject.getString("brand");
//                                    String location = carObject.getString("location");
//                                    int yearModel = carObject.getInt("year_model");
//                                    int seatsNumber = carObject.getInt("seats_number");
//                                    String transmission = carObject.getString("transmission");
//                                    String motorFuel = carObject.getString("motor_fuel");
//                                    double offeredPrice = carObject.getDouble("offered_price");
//                                    String image = carObject.getString("image");
//
//                                    rentedCarsList.add(new Car(id, brand, location, yearModel, seatsNumber, transmission, motorFuel, offeredPrice, image));
//                                    Log.d("fetchRentedCars", "Car added: " + brand);
//                                }
//                                adapter.notifyDataSetChanged();
//                            } else {
//                                Log.d("fetchRentedCars", "Success is false");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("fetchRentedCars", "Error: " + error.toString());
//            }
//        }
//        );
//        queue.add(jsonObjectRequest);
//    }
//
//
//
//}
package com.example.cargo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class searchCar extends AppCompatActivity {
    private RequestQueue queue;
    private RecyclerView RV;
    private carAdapter adapter;
    private List<Car> rentedCarsList;
    private EditText searchEditText;
    private Button searchButton;

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
        rentedCarsList = new ArrayList<>();
        adapter = new carAdapter(rentedCarsList);
        RV.setAdapter(adapter);

        searchEditText = findViewById(R.id.searchtxt);
        searchButton = findViewById(R.id.searchbtn);

        fetchRentedCars();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = searchEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(keyword)) {
                    searchCars(keyword);
                }
            }
        });
    }

    private void fetchRentedCars() {
        String url = "http://192.168.68.52/android/search.php?stringQuery=";
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
                                String image = carObject.getString("image");

                                rentedCarsList.add(new Car(id, brand, location, yearModel, seatsNumber, transmission, motorFuel, offeredPrice, image));
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

    private void searchCars(String keyword) {
        String url = "http://192.168.68.52/android/search.php?searchQuery=" + keyword;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        rentedCarsList.clear(); // Clear the existing list
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
                                String image = carObject.getString("image");

                                rentedCarsList.add(new Car(id, brand, location, yearModel, seatsNumber, transmission, motorFuel, offeredPrice, image));
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
