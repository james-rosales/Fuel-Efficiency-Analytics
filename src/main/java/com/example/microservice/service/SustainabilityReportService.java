package com.example.microservice.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context; 
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
public class SustainabilityReportService {

    @Autowired
    private TemplateEngine templateEngine;

    public byte[] generateMonthlyReport(int year, int month) {
        Context context = new Context();
        
        // 1. Gather data (In a real app, fetch these from your Repository)
        Map<String, Object> data = new HashMap<>();
        data.put("reportMonth", month + "/" + year);
        
        // Example: These should come from EfficiencyReportRepository
        data.put("totalDistance", 5400.0); 
        data.put("co2Saved", 120.5);    
        data.put("topVehicle", "Toyota Vios (Plate: ABC-123)"); 
        
        context.setVariables(data);

        // 2. Render HTML template to String
        String htmlContent = templateEngine.process("sustainability-report-template", context);

        // 3. Convert HTML String to PDF bytes
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage(), e);
        }
    }
}