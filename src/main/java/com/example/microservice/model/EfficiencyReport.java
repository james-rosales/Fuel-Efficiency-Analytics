package com.example.microservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "efficiency_reports")
public class EfficiencyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // We keep the DB column name as vehicle_id but use vehicleType in Java
    @Column(name = "vehicle_id", nullable = false)
    private String vehicleType; 

    // NEW FIELD: This is the unique identifier for the leaderboard
    @Column(name = "plate_number", nullable = false)
    private String plateNumber;

    @Column(nullable = false)
    private Double distanceKm;

    @Column(nullable = false)
    private Double fuelConsumedLiters;

    @Column(nullable = false)
    private Double efficiencyKmPerL;

    @Column(nullable = true)
    private Double fuelPricePerLiter;

    @Column(nullable = true)
    private Double tripCost;

    private String message;

    @Column(name = "analyzed_at")
    private LocalDateTime analyzedAt;

    // Constructors
    public EfficiencyReport() {
    }

    public EfficiencyReport(String vehicleType, String plateNumber, Double distanceKm, Double fuelConsumedLiters, 
                           Double efficiencyKmPerL, String message) {
        this.vehicleType = vehicleType;
        this.plateNumber = plateNumber;
        this.distanceKm = distanceKm;
        this.fuelConsumedLiters = fuelConsumedLiters;
        this.efficiencyKmPerL = efficiencyKmPerL;
        this.message = message;
        this.analyzedAt = LocalDateTime.now();
    }

    // --- UPDATED GETTERS AND SETTERS ---

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    // --- REMAINING GETTERS AND SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Double getFuelConsumedLiters() { return fuelConsumedLiters; }
    public void setFuelConsumedLiters(Double fuelConsumedLiters) { this.fuelConsumedLiters = fuelConsumedLiters; }

    public Double getEfficiencyKmPerL() { return efficiencyKmPerL; }
    public void setEfficiencyKmPerL(Double efficiencyKmPerL) { this.efficiencyKmPerL = efficiencyKmPerL; }

    public Double getFuelPricePerLiter() { return fuelPricePerLiter; }
    public void setFuelPricePerLiter(Double fuelPricePerLiter) { this.fuelPricePerLiter = fuelPricePerLiter; }

    public Double getTripCost() { return tripCost; }
    public void setTripCost(Double tripCost) { this.tripCost = tripCost; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getAnalyzedAt() { return analyzedAt; }
    public void setAnalyzedAt(LocalDateTime analyzedAt) { this.analyzedAt = analyzedAt; }

    @Override
    public String toString() {
        return "EfficiencyReport{" +
                "id=" + id +
                ", vehicleType='" + vehicleType + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", efficiencyKmPerL=" + efficiencyKmPerL +
                '}';
    }
}