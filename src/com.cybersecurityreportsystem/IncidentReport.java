package com.cybersecurityreportsystem;

    public class IncidentReport {
        private String incidentId;
        private String threatType; // e.g., Phishing, Ransomware, DDoS
        private int severityLevel; // 1 to 5
        private String description;

        public IncidentReport(String incidentId, String threatType, int severityLevel, String description, String trim) {
            this.incidentId = incidentId;
            this.threatType = threatType;
            setSeverityLevel(severityLevel); // Enforces validation rule
            this.description = description;
        }

        public int getSeverityLevel() { return severityLevel; }

        public void setSeverityLevel(int severityLevel) {
            if (severityLevel >= 1 && severityLevel <= 5) {
                this.severityLevel = severityLevel;
            } else {
                throw new IllegalArgumentException("Severity level must be between 1 and 5.");
            }
        }
        // Additional getters and setters...
    }
