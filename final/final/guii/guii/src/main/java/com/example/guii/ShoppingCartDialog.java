package com.example.guii;
import com.example.guii.models.CartItem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.ObservableList;

public class ShoppingCartDialog {
    private final ObservableList<CartItem> cartItems;
    private Label totalLabel;
    private final PharmacyManagementSystem pharmacySystem;

    public ShoppingCartDialog(ObservableList<CartItem> cartItems) {
        this.cartItems = cartItems;
        this.pharmacySystem = PharmacyManagementSystem.getInstance();
    }
    public void show(Stage parentStage) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(parentStage);

        // Create table columns
        TableColumn<CartItem, String> nameCol = new TableColumn<>("Item");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<CartItem, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<CartItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<CartItem, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<CartItem, Void> removeCol = new TableColumn<>("Remove");
        removeCol.setCellFactory(col -> new TableCell<>() {
            private final Button removeButton = new Button("Remove");
            {
                removeButton.getStyleClass().add("button");
                removeButton.setOnAction(e -> {
                    CartItem item = getTableView().getItems().get(getIndex());
                    
                    // Show confirmation dialog
                    Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmDialog.setTitle("Remove Item");
                    confirmDialog.setHeaderText("Confirm Removal");
                    confirmDialog.setContentText(String.format(
                        "Remove %d %s from cart?",
                        item.getQuantity(), item.getName()
                    ));

                    confirmDialog.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            // Return items to inventory
                            PharmacyItem inventoryItem = pharmacySystem.getItemByName(item.getName());
                            if (inventoryItem != null) {
                                inventoryItem.setQuantity(inventoryItem.getQuantity() + item.getQuantity());
                            }
                            
                            // Remove from cart
                            cartItems.remove(item);
                            updateTotal();
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : removeButton);
            }
        });

        // Set up cart table
        TableView<CartItem> cartTable = new TableView<>(cartItems);
        cartTable.getColumns().addAll(nameCol, quantityCol, priceCol, totalCol, removeCol);
        cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Create total label
        totalLabel = new Label("Total: $0.00");
        totalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        updateTotal();

        // Create buttons
        Button checkoutButton = new Button("Checkout");
        Button backButton = new Button("Back");

        // Style buttons
        checkoutButton.getStyleClass().add("button");
        backButton.getStyleClass().add("button");

        // Create layout
        VBox root = new VBox(20, cartTable, totalLabel, new HBox(10, checkoutButton, backButton));
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Set up scene
        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

        // Add event handlers
        checkoutButton.setOnAction(e -> {
            double total = calculateTotal();
            
            // Show confirmation dialog
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Checkout");
            confirmDialog.setHeaderText("Confirm Purchase");
            confirmDialog.setContentText(String.format(
                "Total amount: $%.2f\nProceed with checkout?",
                total
            ));

            confirmDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    CheckoutDialog checkoutDialog = new CheckoutDialog();
                    checkoutDialog.show(dialog, total);
                    
                    // If checkout is successful, clear the cart
                    checkoutDialog.setOnCheckoutComplete(() -> {
                        cartItems.clear();
                        dialog.close();
                    });
                }
            });
        });

        backButton.setOnAction(e -> dialog.close());

        dialog.setTitle("Shopping Cart");
        dialog.setScene(scene);
        dialog.show();
    }

    private void updateTotal() {
        double total = calculateTotal();
        totalLabel.setText(String.format("Total: $%.2f", total));
    }

    private double calculateTotal() {
        return cartItems.stream()
                .mapToDouble(CartItem::getTotal)
                .sum();
    }
}