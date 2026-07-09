package com.crs.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AppData {
    private final Map<String, IncidentReport> inReports = new LinkedHashMap<>();
    public void addIncidentReport(IncidentReport ir){
        inReports.put(ir.getIncidentId(),ir);
    }

    public void loadDefaultData() {
        inReports.clear();
        addIncidentReport(new IncidentReport("1","type 1",
                4,"momo", "possible refund","Ernest"));
        addIncidentReport(new IncidentReport("2","type 1",
                4,"momo", "possible refund","Nana"));

    }

    public List<IncidentReport> getIncidentReport() {
        return new ArrayList<>(inReports.values());
    }

    public List<IncidentReport> getIncidentReportbyuser(String reporteduser) {
        List<IncidentReport> list= new ArrayList<>(inReports.values());
        return list.stream().filter(a->a.getReportedBy()==reporteduser).toList();
    }
}
