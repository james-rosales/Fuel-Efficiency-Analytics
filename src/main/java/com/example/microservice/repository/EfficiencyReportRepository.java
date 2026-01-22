package com.example.microservice.repository;

import com.example.microservice.model.EfficiencyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EfficiencyReportRepository extends JpaRepository<EfficiencyReport, Long> {

    @Query(value = "SELECT DATE_FORMAT(r.analyzed_at, '%b %Y'), AVG(r.efficiency_km_perl) FROM efficiency_reports r GROUP BY DATE_FORMAT(r.analyzed_at, '%b %Y') ORDER BY YEAR(r.analyzed_at) ASC, MONTH(r.analyzed_at) ASC", nativeQuery = true)
    List<Object[]> calculateMonthlyEfficiency();

    // UPDATED: Now groups by plate_number to keep vehicles separate in the breakdown
    @Query(value = "SELECT r.vehicle_id, AVG(r.efficiency_km_perl), COUNT(r.id), r.plate_number " +
           "FROM efficiency_reports r GROUP BY r.plate_number, r.vehicle_id " +
           "ORDER BY AVG(r.efficiency_km_perl) DESC", nativeQuery = true)
    List<Object[]> calculateEfficiencyBreakdown();

    @Query(value = "SELECT DATE_FORMAT(r.analyzed_at, '%b %Y'), SUM(r.trip_cost), SUM(r.fuel_consumed_liters) FROM efficiency_reports r WHERE r.trip_cost IS NOT NULL GROUP BY DATE_FORMAT(r.analyzed_at, '%b %Y') ORDER BY YEAR(r.analyzed_at) ASC, MONTH(r.analyzed_at) ASC", nativeQuery = true)
    List<Object[]> calculateMonthlyCosts();

    // UPDATED: Added plate_number to ensure different cars of same model don't merge costs
    @Query(value = "SELECT r.vehicle_id, SUM(r.trip_cost) as totalCost, AVG(r.trip_cost) as avgCost, COUNT(r.id) as tripCount, r.plate_number " +
           "FROM efficiency_reports r WHERE r.trip_cost IS NOT NULL " +
           "GROUP BY r.plate_number, r.vehicle_id ORDER BY totalCost DESC", nativeQuery = true)
    List<Object[]> calculateCostBreakdownByVehicle();

    @Query(value = "SELECT COALESCE(SUM(trip_cost), 0) FROM efficiency_reports WHERE trip_cost IS NOT NULL", nativeQuery = true)
    Double getTotalFleetCost();

    /**
     * FIXED & UPDATED: 
     * We now select plate_number AND vehicle_id, and group by BOTH.
     * This allows the Service layer to show "Toyota Vios (ABC-123)" and "Toyota Vios (XYZ-789)" separately.
     */
    @Query(value = "SELECT r.plate_number, r.vehicle_id, " +
            "COALESCE(SUM(r.distance_km), 0), " +
            "COALESCE(SUM(r.fuel_consumed_liters), 0) " +
            "FROM efficiency_reports r " +
            "GROUP BY r.plate_number, r.vehicle_id", nativeQuery = true)
    List<Object[]> getRawDataForLeaderboard();

    @Query(value = "SELECT DATE_FORMAT(r.analyzed_at, '%b %Y') as month, " +
            "COALESCE(SUM(r.distance_km * 0.25 - r.fuel_consumed_liters * 2.68), 0) as co2Saved " +
            "FROM efficiency_reports r " +
            "GROUP BY DATE_FORMAT(r.analyzed_at, '%b %Y') " +
            "ORDER BY MIN(r.analyzed_at) ASC", nativeQuery = true)
    List<Object[]> getMonthlyCarbonSavings();
    
}