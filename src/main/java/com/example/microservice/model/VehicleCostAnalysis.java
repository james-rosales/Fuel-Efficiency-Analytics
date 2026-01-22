package com.example.microservice.model;

public class VehicleCostAnalysis {
    private String vehicleId;
    private double totalCost;
    private double averageCostPerTrip;
    private long tripCount;

    public VehicleCostAnalysis(String vehicleId, double totalCost, double averageCostPerTrip, long tripCount) {
        this.vehicleId = vehicleId;
        this.totalCost = totalCost;
        this.averageCostPerTrip = averageCostPerTrip;
        this.tripCount = tripCount;
    }

    // Getters and Setters
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public double getAverageCostPerTrip() { return averageCostPerTrip; }
    public void setAverageCostPerTrip(double averageCostPerTrip) { this.averageCostPerTrip = averageCostPerTrip; }

    public long getTripCount() { return tripCount; }
    public void setTripCount(long tripCount) { this.tripCount = tripCount; }
}