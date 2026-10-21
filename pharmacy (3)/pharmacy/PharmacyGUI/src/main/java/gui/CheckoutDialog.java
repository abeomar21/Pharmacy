package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CheckoutDialog {
    public void show(Stage parentStage, double total) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(parentStage);

        // Create form fields
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        TextField balanceField = new TextField();
        balanceField.setPromptText("Enter your balance");

        // Create payment method selection
        ToggleGroup paymentGroup = new ToggleGroup();
        RadioButton cashRadio = new RadioButton("Cash");
        RadioButton creditRadio = new RadioButton("Credit Card");
        cashRadio.setToggleGroup(paymentGroup);
        creditRadio.setToggleGroup(paymentGroup);
        cashRadio.setSelected(true);

        // Create credit card fields (initially hidden)
        VBox creditCardFields = new VBox(10);
        TextField cardNumberField = new TextField();
        cardNumberField.setPromptText("Card Number");
        TextField expiryField = new TextField();
        expiryField.setPromptText("MM/YY");
        TextField cvvField = new TextField();
        cvvField.setPromptText("CVV");
        creditCardFields.getChildren().addAll(cardNumberField, expiryField, cvvField);
        creditCardFields.setVisible(false);

        // Show/hide credit card fields based on payment method
        creditRadio.selectedProperty().addListener((obs, oldVal, newVal) -> 
            creditCardFields.setVisible(newVal));

        // Create buttons
        Button confirmButton = new Button("Confirm Payment");
        Button cancelButton = new Button("Cancel");

        // Style buttons
        confirmButton.getStyleClass().add("button");
        cancelButton.getStyleClass().add("button");

        // Create layout
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.CENTER);

        form.addRow(0, new Label("Name:"), nameField);
        form.addRow(1, new Label("Email:"), emailField);
        form.addRow(2, new Label("Balance:"), balanceField);
        form.addRow(3, new Label("Payment Method:"), new VBox(5, cashRadio, creditRadio));
        form.addRow(4, new Label(""), creditCardFields);

        VBox root = new VBox(20,
            new Label(String.format("Total: $%.2f", total)),
            form,
            new HBox(10, confirmButton, cancelButton)
        );
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Set up scene
        Scene scene = new Scene(root, 400, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        // Add event handlers
        confirmButton.setOnAction(e -> {
            if (validateAndProcessPayment(nameField.getText(), emailField.getText(),
                    balanceField.getText(), cashRadio.isSelected(), cardNumberField.getText(),
                    expiryField.getText(), cvvField.getText(), total)) {
                showSuccessDialog();
                dialog.close();
            }
        });

        cancelButton.setOnAction(e -> dialog.close());

        dialog.setTitle("Checkout");
        dialog.setScene(scene);
        dialog.show();
    }

    private boolean validateAndProcessPayment(String name, String email, String balance,
            boolean isCash, String cardNumber, String expiry, String cvv, double total) {
        // Validate input
        if (name.isEmpty() || email.isEmpty() || balance.isEmpty()) {
            showAlert("Error", "Please fill in all required fields", Alert.AlertType.ERROR);
            return false;
        }

        try {
            double balanceAmount = Double.parseDouble(balance);
            if (balanceAmount < total) {
                showAlert("Error", "Insufficient balance", Alert.AlertType.ERROR);
                return false;
            }

            if (!isCash) {
                if (cardNumber.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) {
                    showAlert("Error", "Please fill in all credit card details", Alert.AlertType.ERROR);
                    return false;
                }
                // Add credit card validation logic here
            }

            // Process payment logic here
            return true;
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid balance amount", Alert.AlertType.ERROR);
            return false;
        }
    }

    private void showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Payment Successful");
        alert.setContentText("Thank you for your purchase!");
        alert.showAndWait();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 