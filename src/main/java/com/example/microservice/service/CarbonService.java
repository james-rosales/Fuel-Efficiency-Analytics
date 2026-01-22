package com.example.microservice.service;

import org.springframework.stereotype.Service;

@Service
public class CarbonService {

    // Standard emission factors (kg of CO2 per unit)
    private static final double CO2_PER_LITER_PETROL = 2.31;
    private static final double CO2_PER_LITER_DIESEL = 2.68;

    /**
     * Calculates CO2 emissions based on fuel type and amount.
     */
    public double calculateCarbonFootprint(double fuelConsumed, String fuelType) {
        if ("DIESEL".equalsIgnoreCase(fuelType)) {
            return fuelConsumed * CO2_PER_LITER_DIESEL;
        }
        return fuelConsumed * CO2_PER_LITER_PETROL;
    }

    /**
     * Calculates a "Green Score" (0-100)
     * Based on fuel efficiency (km/L). Higher km/L = Higher Score.
     */
    public int calculateEcoScore(double efficiency) {
        // Example logic: 15 km/L is considered a perfect 100
        double score = (efficiency / 15.0) * 100;
        return (int) Math.min(score, 100); 
    }
    
    /**
     * Calculates total CO2 savings for the entire fleet.
     * Formula: (Total distance * standard emission rate) - (Actual fuel consumed * fuel factor)
     */
    public double calculateFleetCo2Savings(double totalDistance, double totalFuel) {
        // Standard benchmark: 0.25 kg of CO2 per km (average truck/van)
        double baselineEmissions = totalDistance * 0.25; 
        double actualEmissions = calculateCarbonFootprint(totalFuel, "DIESEL");
        
        return Math.max(0, baselineEmissions - actualEmissions);
    }
}