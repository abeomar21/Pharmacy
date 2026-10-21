package com.example.guii;
import java.util.ArrayList;
import java.util.List;

public class Order implements Payable {
    private final int id;
    private static int nextId = 1;
    private double totalAmount;
    private List<PharmacyItem>cart;

    public Order() {
        this.id = nextId++;
        this.cart = new ArrayList<PharmacyItem>();
    }
    public Order( double totalAmount, List<PharmacyItem> cart, List<Prescription> prescriptions) {
        this.id = nextId++;
        this.totalAmount = totalAmount;
        this.cart = cart;
    }

    public List<PharmacyItem> getCart() {
        return cart;
    }

    public void setCart(List<PharmacyItem> cart) {
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public static void setId(int id) {
        nextId = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<PharmacyItem> getItems() {
        return cart;
    }
    public void viewCart(){
        for(PharmacyItem item : cart) {
            item.displayInfo();
            System.out.println();
        }

    }

    public void setItems(List<PharmacyItem> cart) {
        this.cart = cart;
    }


    public void addItem(PharmacyItem item) {
        cart.add(item);
    }
    public void removeItem(PharmacyItem item) {
        cart.remove(item);
    }

    public PharmacyItem getItemByName(String name) {
        for (PharmacyItem item : cart) {
            if (item.getName().equals(name)) {
                return item; // This should return the actual subclass object
            }
        }
        return null;
    }
    public double calculateTotalAmount() { //calculate total amount
        totalAmount = 0;
        for (PharmacyItem item : cart) {
            totalAmount += item.getPrice()*item.getQuantity();
        }
        return totalAmount;
    }
}
