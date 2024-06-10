package com.example.cargo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ReportActivity extends AppCompatActivity {

    private TextView mostRentedCarTitleTextView, mostRentedCarNameTextView, mostRentedCarCountTextView;
    private TextView mostRatedCarTitleTextView, mostRatedCarNameTextView, mostRatedCarCountTextView;
    private TextView mostPriceCarTitleTextView, mostPriceCarNameTextView, mostPriceCarCountTextView;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mostRentedCarTitleTextView = findViewById(R.id.mostRentedCarTitleTextView);
        mostRentedCarNameTextView = findViewById(R.id.mostRentedCarNameTextView);
        mostRentedCarCountTextView = findViewById(R.id.mostRentedCarCountTextView);

        mostRatedCarTitleTextView = findViewById(R.id.mostRatedCarTitleTextView);
        mostRatedCarNameTextView = findViewById(R.id.mostRatedCarNameTextView);
        mostRatedCarCountTextView = findViewById(R.id.mostRatedCarCountTextView);

        mostPriceCarTitleTextView = findViewById(R.id.mostpriceCarTitleTextView);
        mostPriceCarNameTextView = findViewById(R.id.mostpriceCarNameTextView);
        mostPriceCarCountTextView = findViewById(R.id.mostpriceCarCountTextView);

        queue = Volley.newRequestQueue(this);

        fetchMostRentedCar();
        fetchMostRatedCar();
        fetchMostPriceCar();
    }

    private void fetchMostRentedCar() {
        String url = "http://192.168.1.104/android/fetch_most_rented.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String carName = response.getString("car_name");
                            int rentalCount = response.getInt("rental_count");

                            mostRentedCarNameTextView.setText(carName);
                            mostRentedCarCountTextView.setText(String.valueOf(rentalCount));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fetch Most Rented Car", "Error: " + error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    private void fetchMostRatedCar() {
        String url = "http://192.168.1.104/android/fetch_most_rated.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String carName = response.getString("car_name");
                            double avgRating = response.getDouble("avg_rating");

                            mostRatedCarNameTextView.setText(carName);
                            mostRatedCarCountTextView.setText(String.valueOf(avgRating));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fetch Most Rated Car", "Error: " + error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    private void fetchMostPriceCar() {
        String url = "http://192.168.1.104/android/fetch_highest_priced.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String carName = response.getString("brand");
                            double offeredPrice = response.getDouble("offered_price");

                            mostPriceCarNameTextView.setText(carName);
                            mostPriceCarCountTextView.setText(String.valueOf(offeredPrice));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fetch Highest Priced Car", "Error: " + error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }
}
