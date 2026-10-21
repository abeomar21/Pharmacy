package com.example.guii;


import com.example.guii.models.CartItem;
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

public class CustomerDashboard {
    private TableView<PharmacyItemAdapter> inventoryTable;
    private ObservableList<PharmacyItemAdapter> items;
    private ObservableList<CartItem> cartItems;
    private PharmacyManagementSystem pharmacySystem;

    public CustomerDashboard() {
        this.pharmacySystem = PharmacyManagementSystem.getInstance();
        this.items = FXCollections.observableArrayList();
        this.cartItems = FXCollections.observableArrayList();
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

        TableColumn<PharmacyItemAdapter, Integer> quantityCol = new TableColumn<>("Available");
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        quantityCol.setPrefWidth(150);

        TableColumn<PharmacyItemAdapter, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(col -> new TableCell<>() {
            private final HBox buttons = new HBox(10);
            private final Button viewButton = new Button("View Details");
            private final Button addToCartButton = new Button("Add to Cart");
            private final Spinner<Integer> quantitySpinner = new Spinner<>(1, 100, 1);

            {
                viewButton.getStyleClass().add("button");
                addToCartButton.getStyleClass().add("button");
                
                // Set button sizes
                viewButton.setPrefWidth(120);
                addToCartButton.setPrefWidth(120);
                quantitySpinner.setPrefWidth(80);
                
                // Style the spinner
                quantitySpinner.setStyle("-fx-font-size: 14px;");
                
                buttons.getChildren().addAll(viewButton, quantitySpinner, addToCartButton);
                buttons.setAlignment(Pos.CENTER);

                viewButton.setOnAction(e -> {
                    PharmacyItemAdapter item = getTableView().getItems().get(getIndex());
                    showItemDetails(item.getItem());
                });

                addToCartButton.setOnAction(e -> {
                    PharmacyItemAdapter item = getTableView().getItems().get(getIndex());
                    int quantity = quantitySpinner.getValue();
                    addToCart(item.getItem(), quantity);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttons);
            }
        });
        actionsCol.setPrefWidth(350);

        // Set up inventory table
        inventoryTable = new TableView<>(items);
        inventoryTable.getColumns().addAll(nameCol, priceCol, quantityCol, actionsCol);
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        inventoryTable.setItems(items);

        // Load inventory after table is initialized
        loadInventory();

        // Create buttons
        Button cartButton = new Button("View Cart");
        Button backButton = new Button("Back");

        // Style buttons
        cartButton.getStyleClass().add("button");
        backButton.getStyleClass().add("button");

        // Create button layout
        HBox buttonBox = new HBox(10, cartButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Create main layout
        VBox root = new VBox(20, inventoryTable, buttonBox);
        root.setPadding(new Insets(20));

        // Set up scene
        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

        // Add event handlers
        setupEventHandlers(stage, cartButton, backButton);

        stage.setTitle("Customer Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    private void setupEventHandlers(Stage stage, Button cartButton, Button backButton) {
        cartButton.setOnAction(e -> {
            if (cartItems.isEmpty()) {
                showAlert("Cart Empty", "Your cart is empty. Add some items first!", Alert.AlertType.INFORMATION);
                return;
            }
            ShoppingCartDialog cartDialog = new ShoppingCartDialog(cartItems);
            cartDialog.show(stage);
        });

        backButton.setOnAction(e -> {
            // Show confirmation if cart is not empty
            if (!cartItems.isEmpty()) {
                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("Leave Page");
                confirmDialog.setHeaderText("Cart Not Empty");
                confirmDialog.setContentText("You have items in your cart. Are you sure you want to leave?");
                
                confirmDialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // Return items to inventory
                        for (CartItem cartItem : cartItems) {
                            PharmacyItem item = pharmacySystem.getItemByName(cartItem.getName());
                            if (item != null) {
                                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                            }
                        }
                        cartItems.clear();
                        loadInventory();
                        
                        HomePage homePage = new HomePage();
                        homePage.start(new Stage());
                        stage.close();
                    }
                });
            } else {
                HomePage homePage = new HomePage();
                homePage.start(new Stage());
                stage.close();
            }
        });
    }

    private void showItemDetails(PharmacyItem item) {
        ItemDetailsDialog dialog = new ItemDetailsDialog();
        dialog.show(item);
    }

    private void addToCart(PharmacyItem item, int quantity) {
        if (quantity <= 0) {
            showAlert("Error", "Please select a valid quantity", Alert.AlertType.ERROR);
            return;
        }
        
        if (quantity > item.getQuantity()) {
            showAlert("Error", "Not enough items in stock!", Alert.AlertType.ERROR);
            return;
        }

        // Show confirmation dialog
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Add to Cart");
        confirmDialog.setHeaderText("Confirm Item Addition");
        confirmDialog.setContentText(String.format(
            "Add %d %s to cart?\nPrice per item: $%.2f\nTotal: $%.2f",
            quantity, item.getName(), item.getPrice(), item.getPrice() * quantity
        ));

        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Update inventory
                    item.setQuantity(item.getQuantity() - quantity);
                    
                    // Add to cart
                    cartItems.add(new CartItem(item.getName(), quantity, item.getPrice()));
                    
                    // Refresh inventory display
                    loadInventory();
                    
                    showAlert("Success", "Item added to cart!", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    showAlert("Error", "Failed to add item to cart: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}