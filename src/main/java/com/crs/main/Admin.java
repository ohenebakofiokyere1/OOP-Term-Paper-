package com.crs.main;

import com.crs.model.User;

import java.util.ArrayList;
import java.util.List;

public class Admin {
    private List<User> userList=new ArrayList<>();
    public void loadLoginData() {


        User admin=new User();
        admin.setEmail("Zobila@gmail.com");
        admin.setName("Zobila");
        admin.setPassword("zobila1");

        userList.add(admin);
    }
}
