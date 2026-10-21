package com.example.guii;

import com.example.guii.models.PharmacyItemAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import com.example.guii.PharmacyItem;
import com.example.guii.PharmacyManagementSystem;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class AdminDashboard {
    private TableView<PharmacyItemAdapter> inventoryTable;
    private ObservableList<PharmacyItemAdapter> items;
    private PharmacyManagementSystem pharmacySystem;

    public AdminDashboard() {
        this.pharmacySystem = PharmacyManagementSystem.getInstance();
        this.items = FXCollections.observableArrayList();
        
        // Add dummy items if inventory is empty
        if (pharmacySystem.getInventory().isEmpty()) {
            // Add some tablets
            pharmacySystem.addItem(new Tablet(
                "Paracetamol 500mg",
                "GSK",
                "Pain reliever and fever reducer",
                5.99,
                100,
                500.0,
                new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000), // 1 year from now
                false,
                500,
                20
            ));
            
            pharmacySystem.addItem(new Tablet(
                "Amoxicillin 250mg",
                "Pfizer",
                "Antibiotic for bacterial infections",
                12.99,
                50,
                250.0,
                new Date(System.currentTimeMillis() + 180L * 24 * 60 * 60 * 1000), // 6 months from now
                true,
                250,
                30
            ));

            // Add some liquid medications
            pharmacySystem.addItem(new Liquid(
                "Cough Syrup",
                "Robitussin",
                "Cough suppressant and expectorant",
                8.99,
                75,
                10.0,
                new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000),
                false,
                120.0 // 120ml bottle
            ));

            // Add some injections
            pharmacySystem.addItem(new Injection(
                "Insulin Regular",
                "Novo Nordisk",
                "Human insulin injection",
                45.99,
                30,
                100.0,
                new Date(System.currentTimeMillis() + 180L * 24 * 60 * 60 * 1000),
                true,
                30.0, // 30G needle
                10.0 // 10ml per dose
            ));

            // Add some bandages
            pharmacySystem.addItem(new Bandages(
                "Elastic Bandage",
                "Johnson & Johnson",
                "Self-adhesive elastic bandage",
                7.99,
                75,
                "First Aid",
                "Medium",
                "Cotton",
                10
            ));

            // Add some syringes
            pharmacySystem.addItem(new Syringe(
                "Disposable Syringe",
                "BD",
                "Sterile disposable syringe",
                2.99,
                200,
                "Medical Supplies",
                5.0, // 5ml capacity
                25.0 // 25G needle
            ));
        }
    }

    private void loadInventory() {
        items.clear();
        for (PharmacyItem item : pharmacySystem.getInventory()) {
            items.add(new PharmacyItemAdapter(item));
        }
        if (inventoryTable != null) {
            inventoryTable.refresh();
        }
    }

    public void start(Stage stage) {
        // Create table columns
        TableColumn<PharmacyItemAdapter, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameCol.setPrefWidth(300);

        TableColumn<PharmacyItemAdapter, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        priceCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", price));
                }
            }
        });
        priceCol.setPrefWidth(150);

        TableColumn<PharmacyItemAdapter, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        quantityCol.setPrefWidth(150);

        TableColumn<PharmacyItemAdapter, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(col -> new TableCell<>() {
            private final Button viewButton = new Button("View Details");
            {
                viewButton.getStyleClass().add("button");
                viewButton.setPrefWidth(120);
                viewButton.setOnAction(e -> {
                    PharmacyItemAdapter item = getTableView().getItems().get(getIndex());
                    showItemDetails(item.getItem());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : viewButton);
            }
        });
        actionsCol.setPrefWidth(150);

        // Set up inventory table
        inventoryTable = new TableView<>(items);
        inventoryTable.getColumns().addAll(nameCol, priceCol, quantityCol, actionsCol);
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        inventoryTable.setItems(items);

        // Load inventory after table is initialized
        loadInventory();

        // Create buttons
        Button addButton = new Button("Add Item");
        Button removeButton = new Button("Remove Item");
        Button updateButton = new Button("Update Item");
        Button backButton = new Button("Back");

        // Style buttons
        addButton.getStyleClass().add("button");
        removeButton.getStyleClass().add("button");
        updateButton.getStyleClass().add("button");
        backButton.getStyleClass().add("button");

        // Create button layout
        HBox buttonBox = new HBox(10, addButton, removeButton, updateButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Create main layout
        VBox root = new VBox(20, inventoryTable, buttonBox);
        root.setPadding(new Insets(20));

        // Set up scene
        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

        // Add event handlers
        setupEventHandlers(stage, addButton, removeButton, updateButton, backButton);

        stage.setTitle("Admin Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    private void setupEventHandlers(Stage stage, Button addButton, Button removeButton,
                                    Button updateButton, Button backButton) {
        addButton.setOnAction(e -> showAddItemDialog());
        removeButton.setOnAction(e -> {
            PharmacyItemAdapter selected = inventoryTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                pharmacySystem.removeItem(selected.getItem());
                loadInventory();
            }
        });
        updateButton.setOnAction(e -> {
            PharmacyItemAdapter selected = inventoryTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showUpdateItemDialog(selected.getItem());
            }
        });
        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage();
            homePage.start(new Stage());
            stage.close();
        });
    }

    private void showItemDetails(PharmacyItem item) {
        ItemDetailsDialog dialog = new ItemDetailsDialog();
        dialog.show(item);
    }

    private void showAddItemDialog() {
        Dialog<PharmacyItem> dialog = new Dialog<>();
        dialog.setTitle("Add New Item");
        dialog.setHeaderText("Enter Item Details");

        // Create the custom dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Add form fields
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField manufacturerField = new TextField();
        manufacturerField.setPromptText("Manufacturer");
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");
        TextField priceField = new TextField();
        priceField.setPromptText("Price");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        // Add type selection
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Tablet", "Liquid", "Injection", "Bandages", "Syringe");
        typeComboBox.setValue("Tablet");

        // Add fields to grid
        grid.addRow(0, new Label("Type:"), typeComboBox);
        grid.addRow(1, new Label("Name:"), nameField);
        grid.addRow(2, new Label("Manufacturer:"), manufacturerField);
        grid.addRow(3, new Label("Description:"), descriptionField);
        grid.addRow(4, new Label("Price:"), priceField);
        grid.addRow(5, new Label("Quantity:"), quantityField);

        // Add buttons
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Add type-specific fields
        VBox typeSpecificFields = new VBox(10);
        grid.addRow(6, new Label("Additional Details:"), typeSpecificFields);

        // Update type-specific fields based on selection
        typeComboBox.setOnAction(e -> {
            typeSpecificFields.getChildren().clear();
            switch (typeComboBox.getValue()) {
                case "Tablet":
                case "Liquid":
                case "Injection":
                    final TextField dosageField = new TextField();
                    dosageField.setPromptText("Dosage");
                    final DatePicker expiryPicker = new DatePicker();
                    final CheckBox prescriptionCheck = new CheckBox("Requires Prescription");
                    typeSpecificFields.getChildren().addAll(
                            new Label("Dosage:"), dosageField,
                            new Label("Expiry Date:"), expiryPicker,
                            prescriptionCheck
                    );
                    
                    // Add type-specific fields
                    if (typeComboBox.getValue().equals("Tablet")) {
                        final TextField concentrationField = new TextField();
                        concentrationField.setPromptText("Concentration");
                        final TextField countField = new TextField();
                        countField.setPromptText("Count per pack");
                        typeSpecificFields.getChildren().addAll(
                                new Label("Concentration:"), concentrationField,
                                new Label("Count per pack:"), countField
                        );

                        // Convert the result to a PharmacyItem when the Add button is clicked
                        dialog.setResultConverter(dialogButton -> {
                            if (dialogButton == addButtonType) {
                                try {
                                    String name = nameField.getText();
                                    String manufacturer = manufacturerField.getText();
                                    String description = descriptionField.getText();
                                    double price = Double.parseDouble(priceField.getText());
                                    int quantity = Integer.parseInt(quantityField.getText());

                                    if (name.isEmpty() || manufacturer.isEmpty() || description.isEmpty()) {
                                        showAlert("Error", "Please fill in all required fields", Alert.AlertType.ERROR);
                                        return null;
                                    }

                                    if (expiryPicker.getValue() == null) {
                                        showAlert("Error", "Please select an expiry date", Alert.AlertType.ERROR);
                                        return null;
                                    }
                                    Date date = Date.from(expiryPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                                    return new Tablet(name, manufacturer, description, price, quantity,
                                            Double.parseDouble(dosageField.getText()),
                                            date,
                                            prescriptionCheck.isSelected(),
                                            Integer.parseInt(concentrationField.getText()),
                                            Integer.parseInt(countField.getText()));
                                } catch (NumberFormatException ex) {
                                    showAlert("Error", "Please enter valid numbers for numeric fields", Alert.AlertType.ERROR);
                                    return null;
                                }
                            }
                            return null;
                        });
                    } else if (typeComboBox.getValue().equals("Liquid")) {
                        final TextField volumeField = new TextField();
                        volumeField.setPromptText("Volume");
                        typeSpecificFields.getChildren().addAll(
                                new Label("Volume:"), volumeField
                        );

                        dialog.setResultConverter(dialogButton -> {
                            if (dialogButton == addButtonType) {
                                try {
                                    String name = nameField.getText();
                                    String manufacturer = manufacturerField.getText();
                                    String description = descriptionField.getText();
                                    double price = Double.parseDouble(priceField.getText());
                                    int quantity = Integer.parseInt(quantityField.getText());

                                    if (name.isEmpty() || manufacturer.isEmpty() || description.isEmpty()) {
                                        showAlert("Error", "Please fill in all required fields", Alert.AlertType.ERROR);
                                        return null;
                                    }

                                    if (expiryPicker.getValue() == null) {
                                        showAlert("Error", "Please select an expiry date", Alert.AlertType.ERROR);
                                        return null;
                                    }
                                    Date date = Date.from(expiryPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                                    return new Liquid(name, manufacturer, description, price, quantity,
                                            Double.parseDouble(dosageField.getText()),
                                            date,
                                            prescriptionCheck.isSelected(),
                                            Double.parseDouble(volumeField.getText()));
                                } catch (NumberFormatException ex) {
                                    showAlert("Error", "Please enter valid numbers for numeric fields", Alert.AlertType.ERROR);
                                    return null;
                                }
                            }
                            return null;
                        });
                    } else if (typeComboBox.getValue().equals("Injection")) {
                        final TextField needleGaugeField = new TextField();
                        needleGaugeField.setPromptText("Needle Gauge");
                        final TextField volumePerDoseField = new TextField();
                        volumePerDoseField.setPromptText("Volume per Dose");
                        typeSpecificFields.getChildren().addAll(
                                new Label("Needle Gauge:"), needleGaugeField,
                                new Label("Volume per Dose:"), volumePerDoseField
                        );

                        dialog.setResultConverter(dialogButton -> {
                            if (dialogButton == addButtonType) {
                                try {
                                    String name = nameField.getText();
                                    String manufacturer = manufacturerField.getText();
                                    String description = descriptionField.getText();
                                    double price = Double.parseDouble(priceField.getText());
                                    int quantity = Integer.parseInt(quantityField.getText());

                                    if (name.isEmpty() || manufacturer.isEmpty() || description.isEmpty()) {
                                        showAlert("Error", "Please fill in all required fields", Alert.AlertType.ERROR);
                                        return null;
                                    }

                                    if (expiryPicker.getValue() == null) {
                                        showAlert("Error", "Please select an expiry date", Alert.AlertType.ERROR);
                                        return null;
                                    }
                                    Date date = Date.from(expiryPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                                    return new Injection(name, manufacturer, description, price, quantity,
                                            Double.parseDouble(dosageField.getText()),
                                            date,
                                            prescriptionCheck.isSelected(),
                                            Double.parseDouble(needleGaugeField.getText()),
                                            Double.parseDouble(volumePerDoseField.getText()));
                                } catch (NumberFormatException ex) {
                                    showAlert("Error", "Please enter valid numbers for numeric fields", Alert.AlertType.ERROR);
                                    return null;
                                }
                            }
                            return null;
                        });
                    }
                    break;
                    
                case "Bandages":
                    final TextField sizeField = new TextField();
                    sizeField.setPromptText("Size");
                    final TextField materialField = new TextField();
                    materialField.setPromptText("Material");
                    final TextField packQuantityField = new TextField();
                    packQuantityField.setPromptText("Quantity per Pack");
                    typeSpecificFields.getChildren().addAll(
                            new Label("Size:"), sizeField,
                            new Label("Material:"), materialField,
                            new Label("Quantity per Pack:"), packQuantityField
                    );

                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == addButtonType) {
                            try {
                                String name = nameField.getText();
                                String manufacturer = manufacturerField.getText();
                                String description = descriptionField.getText();
                                double price = Double.parseDouble(priceField.getText());
                                int quantity = Integer.parseInt(quantityField.getText());

                                if (name.isEmpty() || manufacturer.isEmpty() || description.isEmpty()) {
                                    showAlert("Error", "Please fill in all required fields", Alert.AlertType.ERROR);
                                    return null;
                                }

                                return new Bandages(name, manufacturer, description, price, quantity,
                                        "First Aid", sizeField.getText(), materialField.getText(),
                                        Integer.parseInt(packQuantityField.getText()));
                            } catch (NumberFormatException ex) {
                                showAlert("Error", "Please enter valid numbers for numeric fields", Alert.AlertType.ERROR);
                                return null;
                            }
                        }
                        return null;
                    });
                    break;
                    
                case "Syringe":
                    final TextField capacityField = new TextField();
                    capacityField.setPromptText("Capacity");
                    final TextField needleGaugeField = new TextField();
                    needleGaugeField.setPromptText("Needle Gauge");
                    typeSpecificFields.getChildren().addAll(
                            new Label("Capacity:"), capacityField,
                            new Label("Needle Gauge:"), needleGaugeField
                    );

                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == addButtonType) {
                            try {
                                String name = nameField.getText();
                                String manufacturer = manufacturerField.getText();
                                String description = descriptionField.getText();
                                double price = Double.parseDouble(priceField.getText());
                                int quantity = Integer.parseInt(quantityField.getText());

                                if (name.isEmpty() || manufacturer.isEmpty() || description.isEmpty()) {
                                    showAlert("Error", "Please fill in all required fields", Alert.AlertType.ERROR);
                                    return null;
                                }

                                return new Syringe(name, manufacturer, description, price, quantity,
                                        "Medical Supplies",
                                        Double.parseDouble(capacityField.getText()),
                                        Double.parseDouble(needleGaugeField.getText()));
                            } catch (NumberFormatException ex) {
                                showAlert("Error", "Please enter valid numbers for numeric fields", Alert.AlertType.ERROR);
                                return null;
                            }
                        }
                        return null;
                    });
                    break;
            }
        });

        // Trigger initial type selection
        typeComboBox.getOnAction().handle(null);

        // Create a scroll pane to handle overflow
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(500);

        dialog.getDialogPane().setContent(scrollPane);

        // Show dialog and handle result
        dialog.showAndWait().ifPresent(item -> {
            if (item != null) {
                pharmacySystem.addItem(item);
                loadInventory();
            }
        });
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showUpdateItemDialog(PharmacyItem item) {
        Dialog<PharmacyItem> dialog = new Dialog<>();
        dialog.setTitle("Update Item");
        dialog.setHeaderText("Update Item Details");

        // Create the custom dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Add form fields
        TextField nameField = new TextField(item.getName());
        TextField manufacturerField = new TextField(item.getManufacturer());
        TextField descriptionField = new TextField(item.getDescription());
        TextField priceField = new TextField(String.valueOf(item.getPrice()));
        TextField quantityField = new TextField(String.valueOf(item.getQuantity()));

        // Add buttons
        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        // Add type-specific fields
        VBox typeSpecificFields = new VBox(10);
        grid.addRow(0, new Label("Additional Details:"), typeSpecificFields);

        // Add fields to grid
        grid.addRow(1, new Label("Name:"), nameField);
        grid.addRow(2, new Label("Manufacturer:"), manufacturerField);
        grid.addRow(3, new Label("Description:"), descriptionField);
        grid.addRow(4, new Label("Price:"), priceField);
        grid.addRow(5, new Label("Quantity:"), quantityField);

        // Add type-specific fields based on item type
        if (item instanceof Tablet) {
            Tablet tablet = (Tablet) item;
            final TextField dosageField = new TextField(String.valueOf(tablet.getDosage()));
            LocalDate localDate = tablet.getExpirationDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            final DatePicker expiryPicker = new DatePicker(localDate);
            final CheckBox prescriptionCheck = new CheckBox("Requires Prescription");
            prescriptionCheck.setSelected(tablet.getRequiresPrescription());
            final TextField concentrationField = new TextField(String.valueOf(tablet.getConcentration()));
            final TextField countField = new TextField(String.valueOf(tablet.getCount()));
            typeSpecificFields.getChildren().addAll(
                    new Label("Dosage:"), dosageField,
                    new Label("Expiry Date:"), expiryPicker,
                    prescriptionCheck,
                    new Label("Concentration:"), concentrationField,
                    new Label("Count per Pack:"), countField
            );

            // Convert the result to a PharmacyItem when the Update button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    try {
                        String name = nameField.getText();
                        String manufacturer = manufacturerField.getText();
                        String description = descriptionField.getText();
                        double price = Double.parseDouble(priceField.getText());
                        int quantity = Integer.parseInt(quantityField.getText());

                        if (name.isEmpty() || manufacturer.isEmpty() || description.isEmpty()) {
                            showAlert("Error", "Please fill in all required fields", Alert.AlertType.ERROR);
                            return null;
                        }

                        if (expiryPicker.getValue() == null) {
                            showAlert("Error", "Please select an expiry date", Alert.AlertType.ERROR);
                            return null;
                        }
                        Date date = Date.from(expiryPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        return new Tablet(name, manufacturer, description, price, quantity,
                                Double.parseDouble(dosageField.getText()),
                                date,
                                prescriptionCheck.isSelected(),
                                Integer.parseInt(concentrationField.getText()),
                                Integer.parseInt(countField.getText()));
                    } catch (NumberFormatException ex) {
                        showAlert("Error", "Please enter valid numbers for numeric fields", Alert.AlertType.ERROR);
                        return null;
                    }
                }
                return null;
            });
        } else if (item instanceof Liquid) {
            Liquid liquid = (Liquid) item;
            final TextField dosageField = new TextField(String.valueOf(liquid.getDosage()));
            LocalDate localDate = liquid.getExpirationDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            final DatePicker expiryPicker = new DatePicker(localDate);
            final CheckBox prescriptionCheck = new CheckBox("Requires Prescription");
            prescriptionCheck.setSelected(liquid.getRequiresPrescription());
            final TextField volumeField = new TextField(String.valueOf(liquid.getVolume()));
            typeSpecificFields.getChildren().addAll(
                    new Label("Dosage:"), dosageField,
                    new Label("Expiry Date:"), expiryPicker,
                    prescriptionCheck,
                    new Label("Volume:"), volumeField
            );

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    try {
                        String name = nameField.getText();
                        String manufacturer = manufacturerField.getText();
                        String description = descriptionField.getText();
                        double price = Double.parseDouble(priceField.getText());
                        int quantity = Integer.parseInt(quantityField.getText());

                        if (name.isEmpty() || manufacturer.isEmpty() || description.isEmpty()) {
                            showAlert("Error", "Please fill in all required fields", Alert.AlertType.ERROR);
                            return null;
                        }

                        if (expiryPicker.getValue() == null) {
                            showAlert("Error", "Please select an expiry date", Alert.AlertType.ERROR);
                            return null;
                        }
                        Date date = Date.from(expiryPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        return new Liquid(name, manufacturer, description, price, quantity,
                                Double.parseDouble(dosageField.getText()),
                                date,
                                prescriptionCheck.isSelected(),
                                Double.parseDouble(volumeField.getText()));
                    } catch (NumberFormatException ex) {
                        showAlert("Error", "Please enter valid numbers for numeric fields", Alert.AlertType.ERROR);
                        return null;
                    }
                }
                return null;
            });
        } else if (item instanceof Injection) {
            Injection injection = (Injection) item;
            final TextField dosageField = new TextField(String.valueOf(injection.getDosage()));
            LocalDate localDate = injection.getExpirationDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            final DatePicker expiryPicker = new DatePicker(localDate);
            final CheckBox prescriptionCheck = new CheckBox("Requires Prescription");
            prescriptionCheck.setSelected(injection.getRequiresPrescription());
            final TextField needleGaugeField = new TextField(String.valueOf(injection.getNeedleGauge()));
            final TextField volumePerDoseField = new TextField(String.valueOf(injection.getVolumePerDose()));
            typeSpecificFields.getChildren().addAll(
                    new Label("Dosage:"), dosageField,
                    new Label("Expiry Date:"), expiryPicker,
                    prescriptionCheck,
                    new Label("Needle Gauge:"), needleGaugeField,
                    new Label("Volume per Dose:"), volumePerDoseField
            );

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    try {
                        String name = nameField.getText();
                        String manufacturer = manufacturerField.getText();
                        String description = descriptionField.getText();
                        double price = Double.parseDouble(priceField.getText());
                        int quantity = Integer.parseInt(quantityField.getText());

                        if (name.isEmpty() || manufacturer.isEmpty() || description.isEmpty()) {
                            showAlert("Error", "Please fill in all required fields", Alert.AlertType.ERROR);
                            return null;
                        }

                        if (expiryPicker.getValue() == null) {
                            showAlert("Error", "Please select an expiry date", Alert.AlertType.ERROR);
                            return null;
                        }
                        Date date = Date.from(expiryPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        return new Injection(name, manufacturer, description, price, quantity,
                                Double.parseDouble(dosageField.getText()),
                                date,
                                prescriptionCheck.isSelected(),
                                Double.parseDouble(needleGaugeField.getText()),
                                Double.parseDouble(volumePerDoseField.getText()));
                    } catch (NumberFormatException ex) {
                        showAlert("Error", "Please enter valid numbers for numeric fields", Alert.AlertType.ERROR);
                        return null;
                    }
                }
                return null;
            });
        } else if (item instanceof Bandages) {
            Bandages bandage = (Bandages) item;
            final TextField sizeField = new TextField(bandage.getSize());
            final TextField materialField = new TextField(bandage.getMaterial());
            final TextField packQuantityField = new TextField(String.valueOf(bandage.getQuantityPerPack()));
            typeSpecificFields.getChildren().addAll(
                    new Label("Size:"), sizeField,
                    new Label("Material:"), materialField,
                    new Label("Quantity per Pack:"), packQuantityField
            );

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    try {
                        String name = nameField.getText();
                        String manufacturer = manufacturerField.getText();
                        String description = descriptionField.getText();
                        double price = Double.parseDouble(priceField.getText());
                        int quantity = Integer.parseInt(quantityField.getText());

                        if (name.isEmpty() || manufacturer.isEmpty() || description.isEmpty()) {
                            showAlert("Error", "Please fill in all required fields", Alert.AlertType.ERROR);
                            return null;
                        }

                        return new Bandages(name, manufacturer, description, price, quantity,
                                "First Aid", sizeField.getText(), materialField.getText(),
                                Integer.parseInt(packQuantityField.getText()));
                    } catch (NumberFormatException ex) {
                        showAlert("Error", "Please enter valid numbers for numeric fields", Alert.AlertType.ERROR);
                        return null;
                    }
                }
                return null;
            });
        } else if (item instanceof Syringe) {
            Syringe syringe = (Syringe) item;
            final TextField capacityField = new TextField(String.valueOf(syringe.getCapacity()));
            final TextField needleGaugeField = new TextField(String.valueOf(syringe.getNeedleGauge()));
            typeSpecificFields.getChildren().addAll(
                    new Label("Capacity:"), capacityField,
                    new Label("Needle Gauge:"), needleGaugeField
            );

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    try {
                        String name = nameField.getText();
                        String manufacturer = manufacturerField.getText();
                        String description = descriptionField.getText();
                        double price = Double.parseDouble(priceField.getText());
                        int quantity = Integer.parseInt(quantityField.getText());

                        if (name.isEmpty() || manufacturer.isEmpty() || description.isEmpty()) {
                            showAlert("Error", "Please fill in all required fields", Alert.AlertType.ERROR);
                            return null;
                        }

                        return new Syringe(name, manufacturer, description, price, quantity,
                                "Medical Supplies",
                                Double.parseDouble(capacityField.getText()),
                                Double.parseDouble(needleGaugeField.getText()));
                    } catch (NumberFormatException ex) {
                        showAlert("Error", "Please enter valid numbers for numeric fields", Alert.AlertType.ERROR);
                        return null;
                    }
                }
                return null;
            });
        }

        // Create a scroll pane to handle overflow
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(500);

        dialog.getDialogPane().setContent(scrollPane);

        // Show dialog and handle result
        dialog.showAndWait().ifPresent(updatedItem -> {
            if (updatedItem != null) {
                try {
                    // Remove the old item and add the updated one
                    pharmacySystem.removeItem(item);
                    pharmacySystem.addItem(updatedItem);
                    loadInventory(); // Reload the inventory
                    showAlert("Success", "Item updated successfully!", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    showAlert("Error", "Failed to update item: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }
}