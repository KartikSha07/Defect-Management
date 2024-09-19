package com.cognizant.services;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class BugResolutionCalculator {
 
    public LocalDate calculateExpectedResolutionDate(String severity, String priority, LocalDate detectedOn) {
        LocalDate expectedResolutionDate = detectedOn; // Start with the detected on date
 
        switch (severity.toLowerCase()) {
            case "blocking":
                switch (priority.toLowerCase()) {
                    case "p1":
                        expectedResolutionDate = detectedOn.plusDays(2);
                        break;
                    case "p2":
                        expectedResolutionDate = detectedOn.plusDays(3);
                        break;
                }
                break;
            case "critical":
                switch (priority.toLowerCase()) {
                    case "p1":
                        expectedResolutionDate = detectedOn.plusDays(1);
                        break;
                    case "p2":
                        expectedResolutionDate = detectedOn.plusDays(2);
                        break;
                }
                break;
            default:
                switch (priority.toLowerCase()) {
                    case "p1":
                        expectedResolutionDate = detectedOn.plusDays(5);
                        break;
                    case "p2":
                        expectedResolutionDate = detectedOn.plusDays(8);
                        break;
                    case "p3":
                        expectedResolutionDate = detectedOn.plusDays(10);
                        break;
                    default: expectedResolutionDate = detectedOn.plusDays(1);
                }
                break;
        }
 
        return expectedResolutionDate;
    }
}