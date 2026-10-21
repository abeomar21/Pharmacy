package com.example.guii.models;


import com.example.guii.PharmacyItem;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PharmacyItemAdapter {
    private final PharmacyItem item;
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty price;
    private final SimpleIntegerProperty quantity;

    public PharmacyItemAdapter(PharmacyItem item) {
        this.item = item;
        this.name = new SimpleStringProperty(item.getName());
        this.price = new SimpleDoubleProperty(item.getPrice());
        this.quantity = new SimpleIntegerProperty(item.getQuantity());
    }

    public PharmacyItem getItem() { return item; }

    // Getters and setters
    public String getName() { return name.get(); }
    public SimpleStringProperty nameProperty() { return name; }

    public double getPrice() { return price.get(); }
    public SimpleDoubleProperty priceProperty() { return price; }

    public int getQuantity() { return quantity.get(); }
    public SimpleIntegerProperty quantityProperty() { return quantity; }
}