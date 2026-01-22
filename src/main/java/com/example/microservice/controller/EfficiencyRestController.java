package com.example.microservice.controller;

import com.example.microservice.model.*;
import com.example.microservice.service.FuelEfficiencyService;
import com.example.microservice.service.SustainabilityReportService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;  
import org.springframework.http.MediaType;      
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class EfficiencyRestController {

    private final FuelEfficiencyService efficiencyService;
    private final SustainabilityReportService sustainabilityReportService; 

    @Autowired
    public EfficiencyRestController(FuelEfficiencyService efficiencyService, 
                                   SustainabilityReportService sustainabilityReportService) { 
        this.efficiencyService = efficiencyService;
        this.sustainabilityReportService = sustainabilityReportService;
    }

    /**
     * Analyzes trip data. 
     * By including the Plate Number in the TripData object, the service 
     * can now save records for two different cars even if they are the same model.
     */
    @PostMapping("/trip/analyze")
    public EfficiencyReport analyzeTrip(@RequestBody TripData tripData) {
        return efficiencyService.analyzeTrip(tripData);
    }

    @GetMapping("/efficiency/trends") 
    public List<MonthlyEfficiencyData> getEfficiencyTrends() {
        return efficiencyService.calculateMonthlyEfficiencyTrends(); 
    }

    @GetMapping("/efficiency/breakdown")
    public List<VehicleTypeEfficiency> getEfficiencyBreakdown() {
        return efficiencyService.calculateEfficiencyBreakdownByVehicle();
    }
    
    @GetMapping("/costs/trends")
    public List<MonthlyCostData> getCostTrends() {
        return efficiencyService.calculateMonthlyCosts();
    }

    @GetMapping("/costs/breakdown")
    public List<VehicleCostAnalysis> getCostBreakdown() {
        return efficiencyService.calculateCostBreakdownByVehicle();
    }

    @GetMapping("/costs/total")
    public Map<String, Double> getTotalCost() {
        return Collections.singletonMap("totalCost", efficiencyService.getTotalFleetCost());
    }
    
    @GetMapping("/trips/summary")
    public Map<String, Object> getTripSummary() {
        return efficiencyService.getTripSummary();
    }
    
    @GetMapping("/analytics/leaderboard")
    public List<EcoRanking> getLeaderboard() {
        return efficiencyService.getLeaderboardData();
    }
    
    @GetMapping("/analytics/carbon-trends")
    public List<Map<String, Object>> getCarbonTrends() {
        return efficiencyService.getCarbonTrendsData();
    }
    
    @GetMapping("/reports/monthly/{year}/{month}")
    public ResponseEntity<byte[]> downloadReport(@PathVariable int year, @PathVariable int month) {
        byte[] pdf = sustainabilityReportService.generateMonthlyReport(year, month);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Sustainability_Report_" + month + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}