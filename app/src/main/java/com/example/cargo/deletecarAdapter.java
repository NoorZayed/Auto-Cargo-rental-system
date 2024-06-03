package com.example.cargo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class deletecarAdapter extends RecyclerView.Adapter<deletecarAdapter.CarViewHolder> {

    private List<Car> cars;
    private Context context;

    public deletecarAdapter(List<Car> cars, Context context) {
        this.cars = cars;
        this.context = context;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.updatepage, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);
        holder.bind(car);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {
        private TextView car_id;
        private TextView brandTextView;
        private TextView locationTextView;
        private TextView yearModelTextView;
        private TextView seatsNumberTextView;
        private TextView transmissionTextView;
        private TextView motorFuelTextView;
        private TextView offeredPriceTextView;
        private ImageView carImageView;
        private Button deleteButton;
        private Button updateButton;


        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            car_id = itemView.findViewById(R.id.car_id);
            brandTextView = itemView.findViewById(R.id.car_brand);
            locationTextView = itemView.findViewById(R.id.car_location);
            yearModelTextView = itemView.findViewById(R.id.car_year_model);
            seatsNumberTextView = itemView.findViewById(R.id.car_seats_number);
            transmissionTextView = itemView.findViewById(R.id.car_transmission);
            motorFuelTextView = itemView.findViewById(R.id.car_motor_fuel);
            offeredPriceTextView = itemView.findViewById(R.id.car_offered_price);
            carImageView = itemView.findViewById(R.id.car_image);
            deleteButton = itemView.findViewById(R.id.delete_button);
            updateButton = itemView.findViewById(R.id.update_button);

        }

        public void bind(final Car car) {
            car_id.setText("ID: " + car.getId());
            brandTextView.setText("Brand: " + car.getBrand());
            locationTextView.setText("Location: " + car.getLocation());
            yearModelTextView.setText("Year Model: " + car.getYearModel());
            seatsNumberTextView.setText("Seats Number: " + car.getSeatsNumber());
            transmissionTextView.setText("Transmission: " + car.getTransmission());
            motorFuelTextView.setText("Motor Fuel: " + car.getMotorFuel());
            offeredPriceTextView.setText("Offered Price: " + car.getOfferedPrice());

            String imageUrl = car.getImage();
            if (imageUrl != null) {
                Glide.with(itemView.getContext())
                        .load(imageUrl)
                        .into(carImageView);
            }

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteDialog(car);
                }
            });

            updateButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                Intent intent = new Intent(context, updatecar.class);
                intent.putExtra("car_id", car.getId());
                intent.putExtra("brand", car.getBrand());
                intent.putExtra("location", car.getLocation());
                intent.putExtra("year_model", car.getYearModel());
                intent.putExtra("seats_number", car.getSeatsNumber());
                intent.putExtra("transmission", car.getTransmission());
                intent.putExtra("motor_fuel", car.getMotorFuel());
                intent.putExtra("offered_price", car.getOfferedPrice());
                intent.putExtra("image", car.getImage());
                context.startActivity(intent);
            }
        });
    }
        private void showDeleteDialog(final Car car) {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Car")
                    .setMessage("Are you sure you want to delete this car?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteCar(car);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        private void deleteCar(final Car car) {
           String url = "http://192.168.1.104/android/delete.php";
//            String url = "http://192.168.68.52/android/delete.php";
            RequestQueue queue = Volley.newRequestQueue(context);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Delete Car", "Response: " + response);
                            cars.remove(car);
                            notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Delete Car", "Error: " + error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("car_id", String.valueOf(car.getId()));
                    return params;
                }
            };
            queue.add(stringRequest);
        }
    }
}
