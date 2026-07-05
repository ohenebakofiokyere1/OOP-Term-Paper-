package com.crs.services;

import javafx.scene.control.Alert;

    /**
     * Inheritance: Inherits structural behavior from IncidentLogProcessor.
     * Polymorphism: Provides concrete implementation overrides for incident handling.
     */
    public class CoreIncidentProcessor extends IncidentLogProcessor {

        public void processIncident(IncidentReport report) {
            // Polymorphic rule validation checking
            if (report.getSeverityLevel() >= 4) {
                // High threat rule logic modification
                report.setStatus("ESCALATED");
                triggerEmergencyAlert(report);
            }
            masterIncidentList.add(report);
        }

        private void triggerEmergencyAlert(IncidentReport report) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CRITICAL THREAT ESCALATION");
            alert.setHeaderText("Severity Tier " + report.getSeverityLevel() + " Breach Detected!");
            alert.setContentText("Immediate triage required for: " + report.getAffectedSystem());
            alert.showAndWait();
        }
    }
