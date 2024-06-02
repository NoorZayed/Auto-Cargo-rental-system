package com.example.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class updatecar extends AppCompatActivity {

        private EditText carBrandrSpin;
        private EditText locationtxt;
        private EditText carYearspin;
        private EditText seatsText;
        private EditText carTransmition;
        private EditText carFuel;
        private EditText CarPrice;
        private Button updatebtn;
        private int carId;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.updatepage);

            carBrandrSpin = findViewById(R.id.carBrandrSpin);
            locationtxt = findViewById(R.id.locationtxt);
            carYearspin = findViewById(R.id.carYearspin);
            seatsText = findViewById(R.id.seatsText);
            carTransmition = findViewById(R.id.carTransmition);
            carFuel = findViewById(R.id.carFuel);
            CarPrice = findViewById(R.id.CarPrice);

            updatebtn = findViewById(R.id.updatebtn);

            Intent intent = getIntent();
            carId = intent.getIntExtra("car_id", -1);
            carBrandrSpin.setText(intent.getStringExtra("brand"));
            locationtxt.setText(intent.getStringExtra("location"));
            carYearspin.setText(String.valueOf(intent.getIntExtra("year_model", 0)));
            seatsText.setText(String.valueOf(intent.getIntExtra("seats_number", 0)));
            carTransmition.setText(intent.getStringExtra("transmission"));
            carFuel.setText(intent.getStringExtra("motor_fuel"));
            CarPrice.setText(String.valueOf(intent.getDoubleExtra("offered_price", 0)));

            updatebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateCarDetails();
                }
            });
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
            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Update Car", "Response: " + response);
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
                    return params;
                }
            };
            queue.add(stringRequest);
        }
    }
