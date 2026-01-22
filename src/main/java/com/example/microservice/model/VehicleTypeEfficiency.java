package com.example.microservice.model;

/**
 * Data Transfer Object (DTO) for efficiency breakdown by Vehicle ID.
 * Used to structure data for the front-end breakdown/bar charts.
 */
public class VehicleTypeEfficiency {

    private String vehicleId;
    private double averageEfficiency;
    private long reportCount;

    // Constructor
    public VehicleTypeEfficiency(String vehicleId, double averageEfficiency, long reportCount) {
        this.vehicleId = vehicleId;
        this.averageEfficiency = averageEfficiency;
        this.reportCount = reportCount;
    }

    // Getters
    public String getVehicleId() {
        return vehicleId;
    }

    public double getAverageEfficiency() {
        return averageEfficiency;
    }

    public long getReportCount() {
        return reportCount;
    }

    // Setters (Optional)
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setAverageEfficiency(double averageEfficiency) {
        this.averageEfficiency = averageEfficiency;
    }

    public void setReportCount(long reportCount) {
        this.reportCount = reportCount;
    }
}