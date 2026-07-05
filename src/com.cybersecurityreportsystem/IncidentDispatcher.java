package com.cybersecurityreportsystem;

    public class IncidentDispatcher {
        // Accepts any subclass of IncidentComponent polymorphically
        public void executeComponentTasks(IncidentComponent component) {
            System.out.println("Activating Component ID: " + component.getComponentId());
            component.processLog(); // Executes the specific subclass overriding method
        }
    }

