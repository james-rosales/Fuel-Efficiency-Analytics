package com.example.microservice.controller;

import com.example.microservice.model.TripData;
import com.example.microservice.service.FuelEfficiencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class EfficiencyViewController {

    private final FuelEfficiencyService efficiencyService;

    // Comprehensive list of common Philippine car models
    private final List<String> carModels = Arrays.asList(
        "Toyota Vios", "Toyota Fortuner", "Toyota Hilux", "Toyota Wigo", "Toyota Innova", 
        "Toyota Avanza", "Toyota Raize", "Toyota Rush", "Toyota Corolla Cross", "Toyota Hiace",
        "Toyota Veloz", "Toyota Zenix", "Toyota Tamaraw",
        "Mitsubishi Mirage G4", "Mitsubishi Xpander", "Mitsubishi Montero Sport", 
        "Mitsubishi L300", "Mitsubishi Triton", "Mitsubishi Mirage (Hatch)", "Mitsubishi Xforce",
        "Ford Ranger", "Ford Everest", "Ford Territory", "Nissan Navara", "Nissan Terra", 
        "Nissan Almera", "Nissan Urvan", "Nissan Kicks e-Power",
        "Suzuki Ertiga", "Suzuki S-Presso", "Suzuki Dzire", "Suzuki Swift", "Suzuki Jimny",
        "Honda Civic", "Honda City", "Honda CR-V", "Honda BR-V", "Honda HR-V", "Honda Brio",
        "Hyundai Accent", "Hyundai Stargazer", "Hyundai Tucson", "Hyundai Creta", 
        "Kia Sonet", "Kia Soluto", "Kia Seltos", "Kia Carnival",
        "Geely Coolray", "Geely Okavango", "MG 5", "MG ZS", "BYD Sealion 6", "BYD Atto 3", 
        "GAC GS3 Emzoom", "Isuzu D-Max", "Isuzu mu-X"
    );

    @Autowired
    public EfficiencyViewController(FuelEfficiencyService efficiencyService) {
        this.efficiencyService = efficiencyService;
    }

    @GetMapping({"/", "/efficiency"})
    public String showForm(Model model) {
        // This 'tripData' object now contains 'vehicleType' and 'plateNumber'
        model.addAttribute("tripData", new TripData()); 
        model.addAttribute("reports", efficiencyService.findAllReports());
        model.addAttribute("carList", carModels); 
        return "efficiency-form";
    }

    /**
     * Handles the form submission from efficiency-form.html.
     * The @ModelAttribute "tripData" must match the th:object in the HTML.
     */
    @PostMapping("/calculate")
    public String calculateEfficiency(@ModelAttribute("tripData") TripData data) { 
        // The service now saves the plateNumber and vehicleType separately
        efficiencyService.analyzeTrip(data);
        return "redirect:/";
    }
    
    @GetMapping("/analytics/dashboard")
    public String showDashboard(Model model) {
        return "efficiency-dashboard";
    }
    
    @GetMapping("/eco-leaderboard")
    public String showLeaderboard() {
        return "eco-leaderboard"; 
    }
}