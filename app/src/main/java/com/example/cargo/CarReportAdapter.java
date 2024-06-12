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

//public class CarReportAdapter extends RecyclerView.Adapter<CarReportAdapter.CarViewHolder> {
//
//    private List<Car> carList;
//
//    public CarReportAdapter(List<Car> carList) {
//        this.carList = carList;
//    }
//
//    @NonNull
//    @Override
//    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_report_item, parent, false);
//        return new CarViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
//        Car car = carList.get(position);
//        holder.carId.setText(String.valueOf(car.getId()));
//        holder.brand.setText(car.getBrand());
//        holder.location.setText(car.getLocation());
//        holder.yearModel.setText(String.valueOf(car.getYearModel()));
//        holder.seatsNumber.setText(String.valueOf(car.getSeatsNumber()));
//        holder.transmission.setText(car.getTransmission());
//        holder.motorFuel.setText(car.getMotorFuel());
//        holder.offeredPrice.setText(String.valueOf(car.getOfferedPrice()));
//        holder.isRented.setText(car.getIsRented() == 1 ? "Yes" : "No");
//
//        // Load image using Glide
//        Glide.with(holder.itemView.getContext())
//                .load(car.getImage())
//                .into(holder.carImage);
//
//        // Apply fade-in animation
////        Animation fadeIn = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
////        holder.carImage.startAnimation(fadeIn);
//    }
//
//    @Override
//    public int getItemCount() {
//        return carList.size();
//    }
//
//    public static class CarViewHolder extends RecyclerView.ViewHolder {
//
//        TextView carId, brand, location, yearModel, seatsNumber, transmission, motorFuel, offeredPrice, isRented;
//        ImageView carImage;
//
//        public CarViewHolder(@NonNull View itemView) {
//            super(itemView);
//            carId = itemView.findViewById(R.id.carId);
//            brand = itemView.findViewById(R.id.brand);
//            location = itemView.findViewById(R.id.location);
//            yearModel = itemView.findViewById(R.id.yearModel);
//            seatsNumber = itemView.findViewById(R.id.seatsNumber);
//            transmission = itemView.findViewById(R.id.transmission);
//            motorFuel = itemView.findViewById(R.id.motorFuel);
//            offeredPrice = itemView.findViewById(R.id.offeredPrice);
//            isRented = itemView.findViewById(R.id.isRented);
//            carImage = itemView.findViewById(R.id.carImage);
//        }
//    }
//}
