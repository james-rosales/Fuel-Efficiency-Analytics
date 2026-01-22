package com.example.microservice.model;

public class MonthlyCostData {
    private String monthYear;
    private double totalCost;
    private double totalFuelLiters;

    public MonthlyCostData(String monthYear, double totalCost, double totalFuelLiters) {
        this.monthYear = monthYear;
        this.totalCost = totalCost;
        this.totalFuelLiters = totalFuelLiters;
    }

    // Getters and Setters
    public String getMonthYear() { return monthYear; }
    public void setMonthYear(String monthYear) { this.monthYear = monthYear; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public double getTotalFuelLiters() { return totalFuelLiters; }
    public void setTotalFuelLiters(double totalFuelLiters) { this.totalFuelLiters = totalFuelLiters; }
}