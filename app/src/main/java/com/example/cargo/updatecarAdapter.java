package com.example.cargo;

import android.content.Context;
import android.content.Intent;
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

public class updatecarAdapter  extends RecyclerView.Adapter<updatecarAdapter.CarViewHolder> {

    private List<Car> cars;
    private Context context;

    public updatecarAdapter(List<Car> cars, Context context) {
        this.cars = cars;
        this.context = context;
    }

    @NonNull
    @Override
    public updatecarAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item_with_update, parent, false);
        return new updatecarAdapter.CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull updatecarAdapter.CarViewHolder holder, int position) {
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

    }
}
