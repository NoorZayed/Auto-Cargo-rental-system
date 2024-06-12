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

public class rentedAdapter extends RecyclerView.Adapter<rentedAdapter.CarViewHolder> {

    protected List<Car> cars;

    public rentedAdapter(List<Car> cars) {
        this.cars = cars;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_report_item, parent, false);
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

    protected static class CarViewHolder extends RecyclerView.ViewHolder {
        protected TextView car_id;
        protected TextView brandTextView;
        protected TextView locationTextView;
        protected TextView yearModelTextView;
        protected TextView seatsNumberTextView;
        protected TextView transmissionTextView;
        protected TextView motorFuelTextView;
        protected TextView offeredPriceTextView;
        protected TextView fromdTextView;
        protected TextView todTextView;
        protected TextView rateTextView;
        protected ImageView carImageView;

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
            fromdTextView = itemView.findViewById(R.id.fromd);
           todTextView = itemView.findViewById(R.id.tod);
            rateTextView = itemView.findViewById(R.id.rate);
            carImageView = itemView.findViewById(R.id.car_image);
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
            fromdTextView.setText("Date from: " + car.getFromd());
            todTextView.setText("To: " + car.getTod());
            rateTextView.setText("Rate: " + car.getRate());

            String imageUrl = car.getImage();
            if (imageUrl != null) {
                Glide.with(itemView.getContext())
                        .load(imageUrl)
                        .into(carImageView);
            }
        }
    }
}
