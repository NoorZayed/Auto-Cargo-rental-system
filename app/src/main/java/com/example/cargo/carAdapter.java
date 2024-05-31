package com.example.cargo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class carAdapter extends RecyclerView.Adapter<carAdapter.CarViewHolder> {

    private List<Car> cars;

    public carAdapter(List<Car> cars) {
        this.cars = cars;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
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

    static class CarViewHolder extends RecyclerView.ViewHolder {

        private TextView brandTextView;
        private TextView locationTextView;
        private TextView yearModelTextView;
        private TextView seatsNumberTextView;
        private TextView transmissionTextView;
        private TextView motorFuelTextView;
        private TextView offeredPriceTextView;
        private ImageView carImageView;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            brandTextView = itemView.findViewById(R.id.car_brand);
            locationTextView = itemView.findViewById(R.id.car_location);
            yearModelTextView = itemView.findViewById(R.id.car_year_model);
            seatsNumberTextView = itemView.findViewById(R.id.car_seats_number);
            transmissionTextView = itemView.findViewById(R.id.car_transmission);
            motorFuelTextView = itemView.findViewById(R.id.car_motor_fuel);
            offeredPriceTextView = itemView.findViewById(R.id.car_offered_price);
            carImageView = itemView.findViewById(R.id.car_image);
        }

        public void bind(Car car) {
            brandTextView.setText("Brand: " + car.getBrand());
            locationTextView.setText("Location: " + car.getLocation());
            yearModelTextView.setText("Year Model: " + car.getYearModel());
            seatsNumberTextView.setText("Seats Number: " + car.getSeatsNumber());
            transmissionTextView.setText("Transmission: " + car.getTransmission());
            motorFuelTextView.setText("Motor Fuel: " + car.getMotorFuel());
            offeredPriceTextView.setText("Offered Price: " + car.getOfferedPrice());

            // Load image using BitmapFactory
            if (car.getImageBlob() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(car.getImageBlob(), 0, car.getImageBlob().length);
                carImageView.setImageBitmap(bitmap);
            } else {
                // Set default image if no image available
                //carImageView.setImageResource(R.drawable.default_car_image);
            }
        }
    }
}
