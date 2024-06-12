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

    private TextView mostRentedCarNameTextView, mostRentedCarCountTextView;
    private TextView mostRatedCarNameTextView, mostRatedCarCountTextView;
    private TextView mostPriceCarNameTextView, mostPriceCarCountTextView;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mostRentedCarNameTextView = findViewById(R.id.mostrentedCarNameTextView);
        mostRentedCarCountTextView = findViewById(R.id.mostrentedCarCountTextView);

        mostRatedCarNameTextView = findViewById(R.id.mostratedCarNameTextView);
        mostRatedCarCountTextView = findViewById(R.id.mostratedCarCountTextView);

        mostPriceCarNameTextView = findViewById(R.id.mostpriceCarNameTextView);
        mostPriceCarCountTextView = findViewById(R.id.mostpriceCarCountTextView);

        queue = Volley.newRequestQueue(this);

        // Fetch data for most rented car
        fetchData("most_rented_car");
        // Fetch data for most rated car
        fetchData("most_rated_car");
        // Fetch data for most expensive car
        fetchData("most_price_car");
    }

    private void fetchData(String filter) {
        String url = "http://192.168.1.104/android/report.php?filter=" + filter;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String brand = response.getString("brand");
                            double count = response.getDouble("count");

                            if (filter.equals("most_rented_car")) {
                                mostRentedCarNameTextView.setText(brand);
                                mostRentedCarCountTextView.setText(String.valueOf((int) count));
                            } else if (filter.equals("most_rated_car")) {
                                mostRatedCarNameTextView.setText(brand);
                                mostRatedCarCountTextView.setText(String.format("%.2f", count));
                            } else if (filter.equals("most_price_car")) {
                                mostPriceCarNameTextView.setText(brand);
                                mostPriceCarCountTextView.setText(String.format("%.2f", count));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);
    }
}
