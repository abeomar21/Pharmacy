package gui;

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

public class AdminDashboard {
    private TableView<PharmacyItemAdapter> inventoryTable;
    private ObservableList<PharmacyItemAdapter> items;
    private PharmacyManagementSystem pharmacySystem;

    public AdminDashboard() {
        this.pharmacySystem = new PharmacyManagementSystem();
        this.items = FXCollections.observableArrayList();
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

        TableColumn<PharmacyItemAdapter, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<PharmacyItemAdapter, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(col -> new TableCell<>() {
            private final Button viewButton = new Button("View Details");
            {
                viewButton.getStyleClass().add("button");
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

        // Set up inventory table
        inventoryTable = new TableView<>(items);
        inventoryTable.getColumns().addAll(nameCol, priceCol, quantityCol, actionsCol);
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

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
        // Implementation for adding new item
        Dialog<PharmacyItem> dialog = new Dialog<>();
        dialog.setTitle("Add New Item");
        // Add dialog content and logic
    }

    private void showUpdateItemDialog(PharmacyItem item) {
        // Implementation for updating item
        Dialog<PharmacyItem> dialog = new Dialog<>();
        dialog.setTitle("Update Item");
        // Add dialog content and logic
    }
} 