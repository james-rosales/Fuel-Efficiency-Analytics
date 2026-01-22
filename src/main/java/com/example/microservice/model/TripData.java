package com.example.microservice.model;

public class TripData {
    
    private String vehicleType;    // Renamed from vehicleId
    private String plateNumber;   
    private Double distanceKm;
    private Double fuelConsumedLiters;
    private Double fuelPricePerLiter; 
    
    public TripData() {}

    // Constructor
    public TripData(String vehicleType, String plateNumber, Double distanceKm, Double fuelConsumedLiters, Double fuelPricePerLiter) {
        this.vehicleType = vehicleType;
        this.plateNumber = plateNumber;
        this.distanceKm = distanceKm;
        this.fuelConsumedLiters = fuelConsumedLiters;
        this.fuelPricePerLiter = fuelPricePerLiter;
    }

    // This is what the Service calls: data.getVehicleType()
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Double getFuelConsumedLiters() { return fuelConsumedLiters; }
    public void setFuelConsumedLiters(Double fuelConsumedLiters) { this.fuelConsumedLiters = fuelConsumedLiters; }

    public Double getFuelPricePerLiter() { return fuelPricePerLiter; }
    public void setFuelPricePerLiter(Double fuelPricePerLiter) { this.fuelPricePerLiter = fuelPricePerLiter; }

    @Override
    public String toString() {
        return "TripData{" + "type='" + vehicleType + "', plate='" + plateNumber + "'}";
    }
}