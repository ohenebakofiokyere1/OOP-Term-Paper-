package com.crs.controller;

import com.crs.main.Admin;
import com.crs.main.Mainapp;
import com.crs.model.Role;
import com.crs.model.User;
import com.crs.services.AppData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Logincontroller {
    @FXML
    TextField emailField;
    @FXML
    PasswordField passwordField;
    private final AppData appData=new AppData();

    @FXML
    public void initialize(){
        appData.loadLoginData();
    }
    public static void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Success");
        alert.showAndWait();
    }


    public void handleLogin(ActionEvent actionEvent) throws Exception {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email == null && password == null) {
            showInfo("This field cannot be empty");
        }

        User found= appData.getUserList().stream()
                .filter(a->a.getEmail().equals(email) && a.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        if (found == null){
            showInfo("invalid user name or password");
            return;
        }

        openDashboard(found);

    }
    public void openDashboard(User user) throws Exception {
        Stage stage = (Stage) emailField.getScene().getWindow();

        // Create dashboard and load its UI into the same stage
        if (user.getRole() == Role.SUBSCRIBER){
            Mainapp dashboard = new Mainapp();
            dashboard.setUser(user);
            dashboard.start(stage);}
        else if(user.getRole() == Role.ADMIN){
            Admin dashboard = new Admin();
            dashboard.setUser(user);
            dashboard.start(stage);}
    }
}
