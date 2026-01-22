package com.example.microservice.model;

public class EcoRanking {
    private String vehicleId;
    private double totalDistance;
    private double co2Saved;
    private int ecoScore;

    public EcoRanking(String vehicleId, double totalDistance, double co2Saved, int ecoScore) {
        this.vehicleId = vehicleId;
        this.totalDistance = totalDistance;
        this.co2Saved = co2Saved;
        this.ecoScore = ecoScore;
    }

    // Getters and Setters
    public String getVehicleId() { return vehicleId; }
    public double getTotalDistance() { return totalDistance; }
    public double getCo2Saved() { return co2Saved; }
    public int getEcoScore() { return ecoScore; }
}