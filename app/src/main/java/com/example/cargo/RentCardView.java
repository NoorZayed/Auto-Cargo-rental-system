package com.example.cargo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
public class RentCardView extends RecyclerView.Adapter<RentCardView.CarViewHolder> {
    private List<Car> cars;
    private Context context;

    public RentCardView(List<Car> cars, Context context) {
        this.cars = cars;
        this.context = context;
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

    static class CarViewHolder extends RecyclerView.ViewHolder {
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

            // Logging to find out which views are null
            if (car_id == null) {
                Log.e("CarViewHolder", "car_id is null");
            }
            if (brandTextView == null) {
                Log.e("CarViewHolder", "brandTextView is null");
            }
            if (locationTextView == null) {
                Log.e("CarViewHolder", "locationTextView is null");
            }
            if (yearModelTextView == null) {
                Log.e("CarViewHolder", "yearModelTextView is null");
            }
            if (seatsNumberTextView == null) {
                Log.e("CarViewHolder", "seatsNumberTextView is null");
            }
            if (transmissionTextView == null) {
                Log.e("CarViewHolder", "transmissionTextView is null");
            }
            if (motorFuelTextView == null) {
                Log.e("CarViewHolder", "motorFuelTextView is null");
            }
            if (offeredPriceTextView == null) {
                Log.e("CarViewHolder", "offeredPriceTextView is null");
            }
            if (carImageView == null) {
                Log.e("CarViewHolder", "carImageView is null");
            }
            if (rentButton == null) {
                Log.e("CarViewHolder", "rentButton is null");
            }
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
                    // Handle the "Rent Now" button click
                }
            });
        }
    }
}
