package com.example.microservice.service;

import com.example.microservice.model.*;
import com.example.microservice.repository.EfficiencyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FuelEfficiencyService {

    private static final Logger logger = LoggerFactory.getLogger(FuelEfficiencyService.class);
    private final EfficiencyReportRepository repository;

    @Autowired
    private CarbonService carbonService;

    public FuelEfficiencyService(EfficiencyReportRepository repository) {
        this.repository = repository;
    }

    /**
     * ANALYZE TRIP: Fixes the merging issue by using Plate Number.
     */
    public EfficiencyReport analyzeTrip(TripData data) {
        Double efficiency = 0.0;
        if (data.getFuelConsumedLiters() != null && data.getFuelConsumedLiters() > 0 && data.getDistanceKm() != null) {
             efficiency = data.getDistanceKm() / data.getFuelConsumedLiters();
        }
        
        String message = getEfficiencyMessage(efficiency);
        EfficiencyReport report = new EfficiencyReport();
        
        // FIXED: Using vehicleType and plateNumber to match your new Model fields
        report.setVehicleType(data.getVehicleType()); 
        report.setPlateNumber(data.getPlateNumber()); 
        
        report.setFuelConsumedLiters(data.getFuelConsumedLiters()); 
        report.setDistanceKm(data.getDistanceKm());             
        report.setEfficiencyKmPerL(efficiency);
        report.setMessage(message);
        report.setAnalyzedAt(LocalDateTime.now());
        
        if (data.getFuelPricePerLiter() != null && data.getFuelPricePerLiter() > 0 && data.getFuelConsumedLiters() != null) {
            double tripCost = data.getFuelConsumedLiters() * data.getFuelPricePerLiter();
            report.setFuelPricePerLiter(data.getFuelPricePerLiter());
            report.setTripCost(tripCost);
        }
        return repository.save(report); 
    }

    private String getEfficiencyMessage(double efficiency) {
        if (efficiency >= 15.0) return "Excellent efficiency!";
        else if (efficiency >= 10.0) return "Good efficiency.";
        else return "Low efficiency.";
    }

    public List<EfficiencyReport> findAllReports() {
        return repository.findAll();
    }

    public List<MonthlyEfficiencyData> calculateMonthlyEfficiencyTrends() {
        List<Object[]> results = repository.calculateMonthlyEfficiency();
        return results.stream().map(result -> new MonthlyEfficiencyData((String) result[0], 
                result[1] != null ? ((Number) result[1]).doubleValue() : 0.0)).collect(Collectors.toList());
    }

    public List<VehicleTypeEfficiency> calculateEfficiencyBreakdownByVehicle() {
        List<Object[]> results = repository.calculateEfficiencyBreakdown();
        return results.stream().map(result -> new VehicleTypeEfficiency((String) result[0], 
                result[1] != null ? ((Number) result[1]).doubleValue() : 0.0, 
                ((Number) result[2]).longValue())).collect(Collectors.toList());
    }

    public List<MonthlyCostData> calculateMonthlyCosts() {
        List<Object[]> results = repository.calculateMonthlyCosts();
        return results.stream().map(result -> new MonthlyCostData((String) result[0], 
                result[1] != null ? ((Number) result[1]).doubleValue() : 0.0, 
                result[2] != null ? ((Number) result[2]).doubleValue() : 0.0)).collect(Collectors.toList());
    }

    public List<VehicleCostAnalysis> calculateCostBreakdownByVehicle() {
        List<Object[]> results = repository.calculateCostBreakdownByVehicle();
        return results.stream().map(result -> new VehicleCostAnalysis((String) result[0], 
                result[1] != null ? ((Number) result[1]).doubleValue() : 0.0, 
                result[2] != null ? ((Number) result[2]).doubleValue() : 0.0, 
                ((Number) result[3]).longValue())).collect(Collectors.toList());
    }

    public double getTotalFleetCost() {
        Double total = repository.getTotalFleetCost();
        return total != null ? total : 0.0;
    }

    public Map<String, Object> getTripSummary() {
        List<EfficiencyReport> allReports = findAllReports();
        double totalDistance = allReports.stream().mapToDouble(r -> r.getDistanceKm() != null ? r.getDistanceKm() : 0.0).sum();
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalTrips", allReports.size());
        summary.put("totalDistance", totalDistance);
        return summary;
    }

    /**
     * LEADERBOARD: Shows vehicles separately by including the Plate Number in the display.
     */
    public List<EcoRanking> getLeaderboardData() {
        List<Object[]> rawData = repository.getRawDataForLeaderboard();
        if (rawData == null || rawData.isEmpty()) return new ArrayList<>();

        return rawData.stream().map(result -> {
            String plate = (String) result[0];
            String type = (String) result[1];
            double totalDist = (result[2] != null) ? ((Number) result[2]).doubleValue() : 0.0;
            double totalFuel = (result[3] != null) ? ((Number) result[3]).doubleValue() : 0.0;
            
            double efficiency = (totalFuel > 0) ? (totalDist / totalFuel) : 0;
            double co2Emitted = totalFuel * 2.68; 
            
            double rawSaved = (totalDist * 0.25) - co2Emitted;
            double co2Saved = (rawSaved < 0) ? 0.0 : rawSaved;
            
            int ecoScore = carbonService.calculateEcoScore(efficiency);
            
            // Format: "Toyota Vios (ABC-123)"
            String uniqueDisplayName = type + " (" + plate + ")";
            
            return new EcoRanking(uniqueDisplayName, totalDist, co2Saved, ecoScore);
        })
        .sorted((a, b) -> Integer.compare(b.getEcoScore(), a.getEcoScore())) 
        .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getCarbonTrendsData() {
        List<Object[]> data = repository.getMonthlyCarbonSavings();
        if (data == null) return new ArrayList<>();
        
        return data.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("month", row[0]);
            map.put("savings", row[1]);
            return map;
        }).collect(Collectors.toList());
    }
}