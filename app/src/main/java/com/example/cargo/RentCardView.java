package com.example.cargo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

public class RentCardView extends RecyclerView.Adapter<RentCardView.CarViewHolder> {
    private List<Car> cars;
    private Context context;
    private static RequestQueue queue;
    private SharedPreferences sharedPreferences;

    public RentCardView(List<Car> cars, Context context) {
        this.cars = cars;
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        this.sharedPreferences = context.getSharedPreferences("rent_prefs", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rent_card, parent, false);
        return new CarViewHolder(itemView);
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
        private Button rentButton;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            car_id = itemView.findViewById(R.id.car_id);
            brandTextView = itemView.findViewById(R.id.carBrand);
            locationTextView = itemView.findViewById(R.id.carDetails);
            yearModelTextView = itemView.findViewById(R.id.carDetails);
            seatsNumberTextView = itemView.findViewById(R.id.carDetails);
            transmissionTextView = itemView.findViewById(R.id.carDetails);
            motorFuelTextView = itemView.findViewById(R.id.carDetails);
            offeredPriceTextView = itemView.findViewById(R.id.carPrice);
            carImageView = itemView.findViewById(R.id.carImage);
            rentButton = itemView.findViewById(R.id.rentButton);
        }

        public void bind(Car car) {
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

            rentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickers(car.getId(), getAdapterPosition());
                }
            });
        }

        private void showDatePickers(int carId, int position) {
            final Calendar calendar = Calendar.getInstance();

            DatePickerDialog.OnDateSetListener pickupDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String pickupDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    saveDate("pickup_date", pickupDate);

                    DatePickerDialog dropDatePickerDialog = new DatePickerDialog(itemView.getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String dropDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                            saveDate("drop_date", dropDate);
                            rentCar(carId, pickupDate, dropDate, position);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                    dropDatePickerDialog.show();
                }
            };

            DatePickerDialog pickupDatePickerDialog = new DatePickerDialog(itemView.getContext(), pickupDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            pickupDatePickerDialog.show();
        }

        private void rentCar(int carId, String pickupDate, String dropDate, int position) {
            String url = null;
            try {
                url = "http://192.168.1.104/android/update_car_status.php?id=" + carId + "&rented=1&date_rental=" + URLEncoder.encode(pickupDate, "UTF-8") + "&drop_date=" + URLEncoder.encode(dropDate, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean success = response.getBoolean("success");
                                String message = response.getString("message");
                                if (success) {
                                    // Remove the car from the list and notify the adapter
                                    cars.remove(position);
                                    notifyItemRemoved(position);
                                    Toast.makeText(itemView.getContext(), message, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(itemView.getContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.e("RentCardView", "Error response: " + error.getMessage());
                }
            });
            queue.add(jsonObjectRequest);
        }

        private void saveDate(String key, String date) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, date);
            editor.apply();
        }
    }
}
