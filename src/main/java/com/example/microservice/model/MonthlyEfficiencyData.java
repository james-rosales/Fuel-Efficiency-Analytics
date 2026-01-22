package com.example.microservice.model;

/**
 * Data Transfer Object (DTO) for monthly average fuel efficiency trends.
 * Used to structure data for the front-end line/bar chart.
 */
public class MonthlyEfficiencyData {

    private String monthYear;
    private double averageEfficiency;

    // Constructor
    public MonthlyEfficiencyData(String monthYear, double averageEfficiency) {
        this.monthYear = monthYear;
        this.averageEfficiency = averageEfficiency;
    }

    // Getters
    public String getMonthYear() {
        return monthYear;
    }

    public double getAverageEfficiency() {
        return averageEfficiency;
    }

    // Setters (Optional, but good practice for Spring/Jackson deserialization)
    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public void setAverageEfficiency(double averageEfficiency) {
        this.averageEfficiency = averageEfficiency;
    }
}