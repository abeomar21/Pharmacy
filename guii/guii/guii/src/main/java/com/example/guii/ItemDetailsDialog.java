package com.example.guii;

import com.example.guii.PharmacyItem;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ItemDetailsDialog {
    public void show(PharmacyItem item) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);

        // Create details layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Add common item details
        grid.addRow(0, new Label("Name:"), new Label(item.getName()));
        grid.addRow(1, new Label("Manufacturer:"), new Label(item.getManufacturer()));
        grid.addRow(2, new Label("Price:"), new Label(String.format("$%.2f", item.getPrice())));
        grid.addRow(3, new Label("Quantity:"), new Label(String.valueOf(item.getQuantity())));
        grid.addRow(4, new Label("Description:"), new Label(item.getDescription()));
        grid.addRow(5, new Label("Type:"), new Label(item.getType()));

        int row = 6;

        // Add type-specific details
        if (item instanceof Tablet) {
            Tablet tablet = (Tablet) item;
            grid.addRow(row++, new Label("Dosage:"), new Label(String.format("%.1f mg", tablet.getDosage())));
            grid.addRow(row++, new Label("Concentration:"), new Label(String.format("%d mg", tablet.getConcentration())));
            grid.addRow(row++, new Label("Count per Pack:"), new Label(String.valueOf(tablet.getCount())));
            grid.addRow(row++, new Label("Expiry Date:"), new Label(tablet.getExpirationDate().toString()));
            grid.addRow(row++, new Label("Requires Prescription:"), new Label(tablet.getRequiresPrescription() ? "Yes" : "No"));
        } else if (item instanceof Liquid) {
            Liquid liquid = (Liquid) item;
            grid.addRow(row++, new Label("Dosage:"), new Label(String.format("%.1f ml", liquid.getDosage())));
            grid.addRow(row++, new Label("Volume:"), new Label(String.format("%.1f ml", liquid.getVolume())));
            grid.addRow(row++, new Label("Expiry Date:"), new Label(liquid.getExpirationDate().toString()));
            grid.addRow(row++, new Label("Requires Prescription:"), new Label(liquid.getRequiresPrescription() ? "Yes" : "No"));
        } else if (item instanceof Injection) {
            Injection injection = (Injection) item;
            grid.addRow(row++, new Label("Dosage:"), new Label(String.format("%.1f mg", injection.getDosage())));
            grid.addRow(row++, new Label("Needle Gauge:"), new Label(String.format("%.1f G", injection.getNeedleGauge())));
            grid.addRow(row++, new Label("Volume per Dose:"), new Label(String.format("%.1f ml", injection.getVolumePerDose())));
            grid.addRow(row++, new Label("Expiry Date:"), new Label(injection.getExpirationDate().toString()));
            grid.addRow(row++, new Label("Requires Prescription:"), new Label(injection.getRequiresPrescription() ? "Yes" : "No"));
        } else if (item instanceof Bandages) {
            Bandages bandage = (Bandages) item;
            grid.addRow(row++, new Label("Size:"), new Label(bandage.getSize()));
            grid.addRow(row++, new Label("Material:"), new Label(bandage.getMaterial()));
            grid.addRow(row++, new Label("Quantity per Pack:"), new Label(String.valueOf(bandage.getQuantityPerPack())));
        } else if (item instanceof Syringe) {
            Syringe syringe = (Syringe) item;
            grid.addRow(row++, new Label("Capacity:"), new Label(String.format("%.1f ml", syringe.getCapacity())));
            grid.addRow(row++, new Label("Needle Gauge:"), new Label(String.format("%.1f G", syringe.getNeedleGauge())));
        }

        // Create close button
        Button closeButton = new Button("Close");
        closeButton.getStyleClass().add("button");
        closeButton.setOnAction(e -> dialog.close());

        // Create main layout
        VBox root = new VBox(20, grid, closeButton);
        root.setPadding(new Insets(20));
        root.setAlignment(javafx.geometry.Pos.CENTER);

        // Set up scene
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

        dialog.setTitle("Item Details");
        dialog.setScene(scene);
        dialog.show();
    }
}