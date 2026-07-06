package com.crs.controller;

import com.crs.main.Mainapp;
import com.crs.services.DataStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Logincontroller {
    @FXML
    TextField emailField;
    @FXML
    PasswordField passwordField;

    public void handleLogin(ActionEvent actionEvent) throws Exception {
        String email = emailField.getText();
        String password = passwordField.getText();
        if (email == null && password == null) {
            System.out.println("This field cannot be empty");
        } else if (email != DataStore.getUserEmail()
                   && password !=DataStore.getUserPassword()) {
            System.out.println("Incorrect email or password");
        } else if(email==DataStore.getUserEmail()
                    && password==DataStore.getUserPassword()){
            openDashboard();
        }
        openDashboard();

    }
    public void openDashboard() throws Exception {
        Stage stage = (Stage) emailField.getScene().getWindow();

        // Create dashboard and load its UI into the same stage
        Mainapp dashboard = new Mainapp();
        dashboard.start(stage);
    }
}
