package com.crs.services;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataManager {

    private Map<String, IncidentReport> incidentReports = new LinkedHashMap<>();
    private static final String DATA_FILE = "incidentReports.dat";

    public void setIncidentReports(Map<String, IncidentReport> reps){
        this.incidentReports = reps;
    }

    public void addIncidentReport(IncidentReport report) throws Exception {
        if (incidentReports.containsKey(report.getIncidentId())) {
            throw new Exception("An incident with ID '" + report.getIncidentId() + "' already exists.");
        }
        incidentReports.put(report.getIncidentId(), report);
    }

    public IncidentReport getReportById(String incidentId) throws Exception {
        IncidentReport inc = incidentReports.get(incidentId);
        if (inc == null) {
            throw new Exception("No incident found with ID '" + incidentId);
        }
        return inc;
    }

    public List<IncidentReport> getAllIncidentReports() {
        return new ArrayList<>(incidentReports.values());
    }

    /** Returns only the incidents submitted by a given subscriber (used by Mainapp). */
    public List<IncidentReport> getIncidentReportsByUser(String reportedBy) {
        List<IncidentReport> list = new ArrayList<>(incidentReports.values());
        return list.stream()
                .filter(a -> a.getReportedBy() != null && a.getReportedBy().equals(reportedBy))
                .toList();
    }

    /** Persists all records to disk. Demonstrates checked-exception handling for I/O. */
    public void saveToFile() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(new ArrayList<>(incidentReports.values()));
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, IncidentReport> loadFromFile() throws IOException, ClassNotFoundException {
        File file = new File(DATA_FILE);

        if (!file.exists()) return null;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            List<IncidentReport> loaded = (List<IncidentReport>) in.readObject();
            incidentReports.clear();
            for (IncidentReport s : loaded) {
                incidentReports.put(s.getIncidentId(), s);
            }
        }
        return incidentReports;
    }
}
