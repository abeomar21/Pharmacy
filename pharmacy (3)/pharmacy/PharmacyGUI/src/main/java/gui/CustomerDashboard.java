package gui;

import gui.models.CartItem;
import gui.models.PharmacyItemAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.pharmacy.PharmacyItem;
import com.pharmacy.PharmacyManagementSystem;

public class CustomerDashboard {
    private TableView<PharmacyItemAdapter> inventoryTable;
    private ObservableList<PharmacyItemAdapter> items;
    private ObservableList<CartItem> cartItems;
    private PharmacyManagementSystem pharmacySystem;

    public CustomerDashboard() {
        this.pharmacySystem = new PharmacyManagementSystem();
        this.items = FXCollections.observableArrayList();
        this.cartItems = FXCollections.observableArrayList();
        loadInventory();
    }

    private void loadInventory() {
        items.clear();
        for (PharmacyItem item : pharmacySystem.getInventory()) {
            items.add(new PharmacyItemAdapter(item));
        }
    }

    public void start(Stage stage) {
        // Create table columns
        TableColumn<PharmacyItemAdapter, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<PharmacyItemAdapter, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<PharmacyItemAdapter, Integer> quantityCol = new TableColumn<>("Available");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<PharmacyItemAdapter, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(col -> new TableCell<>() {
            private final HBox buttons = new HBox(5);
            private final Button viewButton = new Button("View Details");
            private final Button addToCartButton = new Button("Add to Cart");
            private final Spinner<Integer> quantitySpinner = new Spinner<>(1, 100, 1);

            {
                viewButton.getStyleClass().add("button");
                addToCartButton.getStyleClass().add("button");
                buttons.getChildren().addAll(viewButton, quantitySpinner, addToCartButton);

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

        // Set up inventory table
        inventoryTable = new TableView<>(items);
        inventoryTable.getColumns().addAll(nameCol, priceCol, quantityCol, actionsCol);
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        // Add event handlers
        setupEventHandlers(stage, cartButton, backButton);

        stage.setTitle("Customer Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    private void setupEventHandlers(Stage stage, Button cartButton, Button backButton) {
        cartButton.setOnAction(e -> {
            ShoppingCartDialog cartDialog = new ShoppingCartDialog(cartItems);
            cartDialog.show(stage);
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

    private void addToCart(PharmacyItem item, int quantity) {
        if (quantity <= item.getQuantity()) {
            cartItems.add(new CartItem(item.getName(), quantity, item.getPrice()));
            showAlert("Success", "Item added to cart!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Not enough items in stock!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 