package gui;

import com.pharmacy.PharmacyItem;
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

        // Add item details
        grid.addRow(0, new Label("Name:"), new Label(item.getName()));
        grid.addRow(1, new Label("Price:"), new Label(String.format("$%.2f", item.getPrice())));
        grid.addRow(2, new Label("Quantity:"), new Label(String.valueOf(item.getQuantity())));
        grid.addRow(3, new Label("Description:"), new Label(item.getDescription()));
        
        // Add type-specific details
        if (item instanceof com.pharmacy.Medication) {
            com.pharmacy.Medication med = (com.pharmacy.Medication) item;
            grid.addRow(4, new Label("Dosage:"), new Label(med.getDosage()));
            grid.addRow(5, new Label("Expiry Date:"), new Label(med.getExpiryDate().toString()));
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
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        dialog.setTitle("Item Details");
        dialog.setScene(scene);
        dialog.show();
    }
} 