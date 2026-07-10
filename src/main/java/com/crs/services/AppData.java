package com.crs.services;

import com.crs.model.Role;
import com.crs.model.User;

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

    private List<User> userList=new ArrayList<>();
    public void loadLoginData() {
        User sub=new User();
        sub.setEmail("oheneba@gmail.com");
        sub.setName("Yesu");
        sub.setPassword("oheneba1");
        sub.setRole(Role.SUBSCRIBER);

        User admin=new User();
        admin.setEmail("Zobila@gmail.com");
        admin.setName("Zobila");
        admin.setPassword("zobila1");
        admin.setRole(Role.ADMIN);

        userList.add(sub);
        userList.add(admin);
    }

    public List<User> getUserList() {
        return userList;
    }
}
