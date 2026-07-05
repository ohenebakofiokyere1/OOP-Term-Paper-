module com.example.cybersecurityreportsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.cybersecurityreportsystem to javafx.fxml;
    exports com.example.cybersecurityreportsystem;
}