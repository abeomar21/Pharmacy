package com.example.guii;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class HomePage {
   // private static final String LOGO_PATH = "/images/pharmacy_logo.png";

    public void start(Stage stage) {
        // Create logo
       // ImageView logo = new ImageView(new Image(getClass().getResourceAsStream(LOGO_PATH)));
//        logo.setFitHeight(200);
//        logo.setPreserveRatio(true);

        // Create buttons
        Button adminButton = new Button("Admin");
        Button customerButton = new Button("Customer");

        // Style buttons
        adminButton.getStyleClass().add("button");
        customerButton.getStyleClass().add("button");

        // Set button actions
        adminButton.setOnAction(e -> {
            AdminDashboard adminDashboard = new AdminDashboard();
            adminDashboard.start(new Stage());
            stage.close();
        });

        customerButton.setOnAction(e -> {
            CustomerDashboard customerDashboard = new CustomerDashboard();
            customerDashboard.start(new Stage());
            stage.close();
        });

        // Create layout
        VBox root = new VBox(20, adminButton, customerButton);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20px;");

        // Create scene
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/style.css")).toExternalForm());

        stage.setTitle("Pharmacy Management System");
        stage.setScene(scene);
        stage.show();
    }
}