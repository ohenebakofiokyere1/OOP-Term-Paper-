package com.crs.main;

import com.crs.model.User;
import com.crs.services.CoreIncidentProcessor;
import com.crs.services.DataManager;
import com.crs.services.IncidentLogProcessor;
import com.crs.services.IncidentReport;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;


public class Admin extends Application {
    // Business Logic Engine instances (Polymorphic binding)
    private final IncidentLogProcessor logProcessor = new CoreIncidentProcessor();
    private final ObservableList<IncidentReport> incidentReportData= FXCollections.observableArrayList();

    // Frontend Interface Components
    private ComboBox<String> threatTypeSelector;
    private TextField severityInputField;
    private TextField targetSystemField;
    private TextArea incidentDescriptionArea;

    // Backend View Components
    private TableView<IncidentReport> frontendIncidentTable;
    private TableView<IncidentReport> incidentreport;
    private final DataManager dataManager = new DataManager();
    String reportedBy;


    private User currentUser=new User();
    public void setUser(User user) {
        currentUser= user;
    }

    private void refreshTable() {
        // Admin is the staff-side viewer, so it should see every incident on file,
        // not just ones filtered by a single reporter.
        var list = dataManager.getAllIncidentReports();
        incidentReportData.setAll(list);
        frontendIncidentTable.setItems(incidentReportData);
    }

    private void executeReportSubmission() {
        try {
            // Validation Check: Field presence compliance
            if (targetSystemField.getText().trim().isEmpty() || incidentDescriptionArea.getText().trim().isEmpty()) {
                throw new Exception("All descriptive text boxes must be populated prior to routing.");
            }

            // Exception Handling: Trapping parsing anomalies safely
            int severityMetric;
            try {
                severityMetric = Integer.parseInt(severityInputField.getText().trim());
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Severity input field requires a numeric value.");
            }

            // Create encapsulated Domain instance model
            String systemGeneratedId = "LOG-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            IncidentReport structuralReport = new IncidentReport(
                    systemGeneratedId,
                    threatTypeSelector.getValue(),
                    severityMetric,
                    targetSystemField.getText().trim(),
                    incidentDescriptionArea.getText().trim(),
                    reportedBy
            );

            // Polymorphically process log data arrays
            // logProcessor.processIncident(structuralReport);
            dataManager.addIncidentReport(structuralReport);
            dataManager.saveToFile();
            refreshTable();

            // Clean form components post successful insertion pipeline
            severityInputField.clear();
            targetSystemField.clear();
            incidentDescriptionArea.clear();

        } catch (Exception ex) {
            // Generate user-facing error dialog frames dynamically
            Alert errorModal = new Alert(Alert.AlertType.ERROR);
            errorModal.setTitle("Input Verification Failure");
            errorModal.setHeaderText("Invalid Data Format Found");
            errorModal.setContentText(ex.getMessage());
            errorModal.showAndWait();
        }
    }


    private VBox buildFrontendPanel() {
        VBox container = new VBox(12);
        container.setPadding(new Insets(15));
        container.setStyle("-fx-background-color: #ffffff; -fx-border-color: #dcdcdc; -fx-border-radius: 5; -fx-background-radius: 5;");
        container.setPrefWidth(450);

        Label header = new Label("Incident Reporting Portal (Frontend)");
        header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1e3d59;");
        container.getChildren().add(header);

        // Threat Categories Form Element
        container.getChildren().add(new Label("Threat Category:"));
        threatTypeSelector = new ComboBox<>();
        threatTypeSelector.getItems().addAll("Phishing Vector", "Ransomware Deployment", "DDoS Inundation", "Unauthorized Access Exfiltration");
        threatTypeSelector.setMaxWidth(Double.MAX_VALUE);
        threatTypeSelector.setValue("Phishing Vector");
        container.getChildren().add(threatTypeSelector);

        // Severity Numeric Inputs Form Element
        container.getChildren().add(new Label("Severity Metric Level (1 - 5 Scale):"));
        severityInputField = new TextField();
        severityInputField.setPromptText("Enter an integer from 1 to 5");
        container.getChildren().add(severityInputField);

        // System Target Identification Vector Form Element
        container.getChildren().add(new Label("Target/Affected Network System Infrastructure:"));
        targetSystemField = new TextField();
        targetSystemField.setPromptText("e.g. Core Banking API Server");
        container.getChildren().add(targetSystemField);

        // Incident Log Text Explanations
        container.getChildren().add(new Label("Technical Incident Synopsis / Remediation Log:"));
        incidentDescriptionArea = new TextArea();
        incidentDescriptionArea.setPrefRowCount(4);
        incidentDescriptionArea.setPromptText("Enter localized anomaly signatures, target scope parameters, and system indicators...");
        container.getChildren().add(incidentDescriptionArea);

        // Submit Action Button Event Interceptor
        Button submitReportButton = new Button("Dispatch Incident Report");
        submitReportButton.setStyle("-fx-background-color: #ff6e40; -fx-text-fill: white; -fx-font-weight: bold;");
        submitReportButton.setMaxWidth(Double.MAX_VALUE);
        submitReportButton.setOnAction(e -> executeReportSubmission());
        container.getChildren().add(submitReportButton);

        return container;
    }


    private VBox buildBackendPanel() {
        VBox container = new VBox(12);
        container.setPadding(new Insets(15));
        container.setStyle("-fx-background-color: #ffffff; -fx-border-color: #dcdcdc; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label header = new Label("Security Operations Command Dashboard (Backend)");
        header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #17b978;");
        container.getChildren().add(header);

        // Initialize Logging Display Grid View Component (TableView)
        frontendIncidentTable = new TableView<>();
        // incidentTable.setItems(IncidentLogProcessor.getMasterIncidentList());

        TableColumn<IncidentReport, String> idCol = new TableColumn<>("Log ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("incidentId"));
        idCol.setPrefWidth(90);

        TableColumn<IncidentReport, String> typeCol = new TableColumn<>("Threat Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("threatType"));
        typeCol.setPrefWidth(130);

        TableColumn<IncidentReport, Integer> sevCol = new TableColumn<>("Severity");
        sevCol.setCellValueFactory(new PropertyValueFactory<>("severityLevel"));
        sevCol.setPrefWidth(65);

        TableColumn<IncidentReport, String> assetCol = new TableColumn<>("Affected Asset");
        assetCol.setCellValueFactory(new PropertyValueFactory<>("affectedSystem"));
        assetCol.setPrefWidth(120);

        TableColumn<IncidentReport, String> statusCol = new TableColumn<>("Triage Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(90);

        TableColumn<IncidentReport, String> timeCol = new TableColumn<>("Log Timestamp");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        timeCol.setPrefWidth(130);

        TableColumn<IncidentReport, String> description = new TableColumn<>("description");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        timeCol.setPrefWidth(130);

        frontendIncidentTable.getColumns().addAll(idCol, typeCol, sevCol, assetCol, statusCol, timeCol, description);
        VBox.setVgrow(frontendIncidentTable, Priority.ALWAYS);
        container.getChildren().add(frontendIncidentTable);

        // Control Panel Options for Backend Operators
        HBox operatorControls = new HBox(10);
        operatorControls.setAlignment(Pos.CENTER_LEFT);

        Button resolveLogButton = new Button("Resolve Selected Incident");
        resolveLogButton.setStyle("-fx-background-color: #17b978; -fx-text-fill: white;");
        resolveLogButton.setOnAction(e -> markIncidentResolved());

        operatorControls.getChildren().addAll(resolveLogButton);
        container.getChildren().add(operatorControls);

        return container;
    }


    private void markIncidentResolved() {
        IncidentReport selectedItem = frontendIncidentTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setStatus("RESOLVED");
            try {
                dataManager.saveToFile();
            } catch (IOException e) {
                Alert saveError = new Alert(Alert.AlertType.ERROR);
                saveError.setTitle("Data Save Failure");
                saveError.setHeaderText("Could not save the updated incident status");
                saveError.setContentText(e.getMessage());
                saveError.showAndWait();
            }
            frontendIncidentTable.refresh();
        } else {
            Alert noticeModal = new Alert(Alert.AlertType.INFORMATION);
            noticeModal.setTitle("Operational Alert");
            noticeModal.setHeaderText(null);
            noticeModal.setContentText("Select an incident from the backend table to change its status.");
            noticeModal.showAndWait();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Admin | Cybersecurity Incident Reporting System");

        // Main structural layout container
        HBox mainContainer = new HBox(20);
        mainContainer.setPadding(new Insets(15));
        mainContainer.setStyle("-fx-background-color: #f4f6f9;");

        // Build structural panel regions
        // VBox frontendUI = buildFrontendPanel();
        VBox backendUI = buildBackendPanel();
        reportedBy=currentUser.getName();

        try {
            Map<String, IncidentReport> loaded = dataManager.loadFromFile();
            if (loaded == null) {
                System.out.println("No existing incidentReports.dat found - starting fresh.");
            }
        } catch (IOException | ClassNotFoundException e) {
            Alert loadError = new Alert(Alert.AlertType.ERROR);
            loadError.setTitle("Data Load Failure");
            loadError.setHeaderText("Could not load saved incident reports");
            loadError.setContentText(e.getMessage());
            loadError.showAndWait();
        }

        refreshTable();

        // Bind structures horizontally to present concurrent views
        //HBox.setHgrow(frontendUI, Priority.ALWAYS);
        HBox.setHgrow(backendUI, Priority.ALWAYS);
        mainContainer.getChildren().addAll(backendUI);

        Scene scene = new Scene(mainContainer, 1150, 650);
        stage.setScene(scene);
        stage.show();
    }

}
