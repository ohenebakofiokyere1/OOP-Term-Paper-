module com.example.cybersecurityreportsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.cybersecurityreportsystem to javafx.fxml;
    opens com.crs.controller to javafx.fxml;
    exports com.example.cybersecurityreportsystem;
    exports com.crs.main to javafx.graphics;
}