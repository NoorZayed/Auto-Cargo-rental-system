package com.example.cargo;
public class Car {
    private int id;
    private String brand;
    private String location;
    private int yearModel;
    private int seatsNumber;
    private String transmission;
    private String motorFuel;
    private double offeredPrice;
    private String image;

    public Car(int id, String brand, String location, int yearModel, int seatsNumber, String transmission, String motorFuel, double offeredPrice, String image) {
        this.id = id;
        this.brand = brand;
        this.location = location;
        this.yearModel = yearModel;
        this.seatsNumber = seatsNumber;
        this.transmission = transmission;
        this.motorFuel = motorFuel;
        this.offeredPrice = offeredPrice;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getYearModel() {
        return yearModel;
    }

    public void setYearModel(int yearModel) {
        this.yearModel = yearModel;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getMotorFuel() {
        return motorFuel;
    }

    public void setMotorFuel(String motorFuel) {
        this.motorFuel = motorFuel;
    }

    public double getOfferedPrice() {
        return offeredPrice;
    }

    public void setOfferedPrice(double offeredPrice) {
        this.offeredPrice = offeredPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImageBlob(String image) {
        this.image = image;
    }

}

