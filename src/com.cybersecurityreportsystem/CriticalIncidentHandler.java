package com.cybersecurityreportsystem;

import java.util.ArrayList;
import java.util.List;

public class CriticalIncidentHandler extends IncidentComponent {
        private List<IncidentReport> criticalLogs;

        public CriticalIncidentHandler(String componentId) {
            super(componentId); // Inherits from base class
            this.criticalLogs = new ArrayList<>();
        }

        @Override
        public void processLog() {
            // Specific processing logic for high-priority/escalated threats
        }
    }
