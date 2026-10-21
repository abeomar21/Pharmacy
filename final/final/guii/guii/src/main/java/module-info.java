module com.example.guii {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.guii to javafx.fxml;
    opens com.example.guii.models to javafx.base;
    exports com.example.guii;
    exports com.example.guii.models;
}