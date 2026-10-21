package com.example.guii.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CartItem {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty quantity;
    private final SimpleDoubleProperty price;
    private final SimpleDoubleProperty total;

    public CartItem(String name, int quantity, double price) {
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.total = new SimpleDoubleProperty(quantity * price);
    }

    // Getters and setters
    public String getName() { return name.get(); }
    public SimpleStringProperty nameProperty() { return name; }
    public void setName(String name) { this.name.set(name); }

    public int getQuantity() { return quantity.get(); }
    public SimpleIntegerProperty quantityProperty() { return quantity; }
    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
        this.total.set(quantity * price.get());
    }

    public double getPrice() { return price.get(); }
    public SimpleDoubleProperty priceProperty() { return price; }
    public void setPrice(double price) {
        this.price.set(price);
        this.total.set(quantity.get() * price);
    }

    public double getTotal() { return total.get(); }
    public SimpleDoubleProperty totalProperty() { return total; }
}