package com.example.cargo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
    public void updateList(List<Car> newCarsList) {
        cars.clear();
        cars.addAll(newCarsList);
        notifyDataSetChanged();
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
        }

        public void bind(Car car) {
            car_id.setText("ID: "+car.getId());
            brandTextView.setText("Brand: " + car.getBrand());
            locationTextView.setText("Location: " + car.getLocation());
            yearModelTextView.setText("Year Model: " + car.getYearModel());
            seatsNumberTextView.setText("Seats Number: " + car.getSeatsNumber());
            transmissionTextView.setText("Transmission: " + car.getTransmission());
            motorFuelTextView.setText("Motor Fuel: " + car.getMotorFuel());
            offeredPriceTextView.setText("Offered Price: " + car.getOfferedPrice());

            // Load image using BitmapFactory
//            String imagePath = car.getImage();
//            if (imagePath != null) {
//                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//                carImageView.setImageBitmap(bitmap);
//            } else {
//                // Set default image if no image available
//                //carImageView.setImageResource(R.drawable.default_car_image);
//            }
            String imageUrl = car.getImage();
            if (imageUrl != null) {
                Glide.with(itemView.getContext())
                        .load(imageUrl)
                        .into(carImageView);
            } else {
                // Set default image if no image available
//                 carImageView.setImageResource(R.drawable.default_car_image);
            }
        }

    }
}
