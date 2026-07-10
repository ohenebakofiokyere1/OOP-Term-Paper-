package com.crs.main;

import com.crs.services.AppData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cybersecurityreportsystem/Login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 450, 300);

        stage.setTitle("Cybersecurity report System");
        stage.setScene(scene);
        stage.show();
    }

}
