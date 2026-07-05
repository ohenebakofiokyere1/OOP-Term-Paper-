package com.cybersecurityreportsystem;

public class IncidentComponent {
    // Abstract base class representing a generic System Operation
        private String componentId;

        public IncidentComponent(String componentId) {
            this.componentId = componentId;
        }

    // Abstract method to be overridden by subclasses
    public void processLog() {

    }

    public String getComponentId() {
            return componentId;
        }
    }
