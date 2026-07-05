package com.crs.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
     * Abstraction: Defines the structural blueprint for standard log operations.
     */
    public abstract class IncidentLogProcessor {
        // Shared state for all sub-handlers
        protected static ObservableList<IncidentReport> masterIncidentList = FXCollections.observableArrayList();

        public abstract void processIncident(IncidentReport report);

        public static List<IncidentReport> getMasterIncidentList() {
            return masterIncidentList;
        }
    }
