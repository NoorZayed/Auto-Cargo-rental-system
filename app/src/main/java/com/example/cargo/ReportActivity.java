package com.example.cargo;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

public class ReportActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarReportAdapter adapter;
    private List<Car> carList;
    private RequestQueue requestQueue;
    private CardView cardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

//        cardView = findViewById(R.id.rcardView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        carList = new ArrayList<>();
        adapter = new CarReportAdapter(carList);
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
        fetchCarDetails();

        // Apply fade-in animation to the CardView
//        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
//        cardView.startAnimation(fadeInAnimation);
    }

    private void fetchCarDetails() {
        String url = "http://192.168.88.13/android/fetch_cars.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject carObject = response.getJSONObject(i);
                                int id = carObject.getInt("id");
                                String brand = carObject.getString("brand");
                                String location = carObject.getString("location");
                                int yearModel = carObject.getInt("yearModel");
                                int seatsNumber = carObject.getInt("seatsNumber");
                                String transmission = carObject.getString("transmission");
                                String motorFuel = carObject.getString("motorFuel");
                                double offeredPrice = carObject.getDouble("offeredPrice");
                                String image = carObject.getString("image");
                                byte isRented = (byte) carObject.getInt("isRented");

                                Car car = new Car(id, brand, location, yearModel, seatsNumber, transmission, motorFuel, offeredPrice, image);
                                car.setIsRented(isRented);
                                carList.add(car);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Generate report
                        generateReport(carList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReportActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    private void generateReport(List<Car> carList) {
        // Process the carList to generate the report
        // You can find the most rented car, least rented cars, and cars rented today here
        // Example:
        // Find the most rented car
        Car mostRentedCar = findMostRentedCar(carList);

        // Find the least rented cars
        List<Car> leastRentedCars = findLeastRentedCars(carList);

        // Find cars rented today
        List<Car> carsRentedToday = findCarsRentedToday(carList);

        // Display or use the generated report as needed
    }

    private Car findMostRentedCar(List<Car> carList) {
        // Implement logic to find the most rented car
        // Example:
        Car mostRentedCar = null;
        int maxRentalCount = 0;
        for (Car car : carList) {
//            if (car.getRentalCount() > maxRentalCount) {
//                mostRentedCar = car;
//                maxRentalCount = car.getRentalCount();
//            }
        }
        return mostRentedCar;
    }

    private List<Car> findLeastRentedCars(List<Car> carList) {
        // Implement logic to find the least rented cars
        // Example:
        List<Car> leastRentedCars = new ArrayList<>();
        int minRentalCount = Integer.MAX_VALUE;
        for (Car car : carList) {
//            if (car.getRentalCount() < minRentalCount) {
//                leastRentedCars.clear();
//                leastRentedCars.add(car);
//                minRentalCount = car.getRentalCount();
//            } else if (car.getRentalCount() == minRentalCount) {
//                leastRentedCars.add(car);
//            }
        }
        return leastRentedCars;
    }

    private List<Car> findCarsRentedToday(List<Car> carList) {
        // Implement logic to find cars rented today
        // Example:
        List<Car> carsRentedToday = new ArrayList<>();
        for (Car car : carList) {
//            if (car.isRentedToday()) {
//                carsRentedToday.add(car);
//            }
        }
        return carsRentedToday;
    }}
