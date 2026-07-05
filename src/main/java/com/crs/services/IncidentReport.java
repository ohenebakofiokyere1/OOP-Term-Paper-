package com.crs.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

    /**
     * Demonstrates Encapsulation by protecting the internal states of
     * incident reports via verified accessors and mutators.
     */
    public class IncidentReport {
        private String incidentId;
        private String threatType;
        private int severityLevel;
        private String affectedSystem;
        private String description;
        private String timestamp;
        private String status;

        public IncidentReport(String incidentId, String threatType, int severityLevel,
                              String affectedSystem, String description) {
            this.incidentId = incidentId;
            this.threatType = threatType;
            setSeverityLevel(severityLevel); // Enforces structural business validation rule
            this.affectedSystem = affectedSystem;
            this.description = description;
            this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.status = "OPEN";
        }

        // Getters and Setters demonstrating Encapsulation
        public String getIncidentId() { return incidentId; }
        public String getThreatType() { return threatType; }
        public String getAffectedSystem() { return affectedSystem; }
        public String getDescription() { return description; }
        public String getTimestamp() { return timestamp; }
        public String getStatus() { return status; }

        public int getSeverityLevel() { return severityLevel; }
        public void setSeverityLevel(int severityLevel) {
            // Validation: Severity must be within standard 1-5 corporate ranges
            if (severityLevel < 1 || severityLevel > 5) {
                throw new IllegalArgumentException("Severity tier must range between 1 and 5.");
            }
            this.severityLevel = severityLevel;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

