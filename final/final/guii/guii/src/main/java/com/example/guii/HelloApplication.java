package com.example.guii;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        HomePage homePage = new HomePage();
        homePage.start(primaryStage);
    }   
    public static void main(String[] args) {
        launch();
    }
}
