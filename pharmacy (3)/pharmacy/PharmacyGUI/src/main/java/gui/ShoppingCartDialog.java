package gui;

import gui.models.CartItem;
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

    public ShoppingCartDialog(ObservableList<CartItem> cartItems) {
        this.cartItems = cartItems;
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
                    cartItems.remove(item);
                    updateTotal();
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
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        // Add event handlers
        checkoutButton.setOnAction(e -> {
            CheckoutDialog checkoutDialog = new CheckoutDialog();
            checkoutDialog.show(dialog, calculateTotal());
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