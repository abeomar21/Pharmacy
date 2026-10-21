package com.example.guii;
import java.util.Date;

public class Tablet extends Medication {
    private int concentration;
    private int count;

    public int getConcentration() {
        return concentration;
    }

    public void setConcentration(int concentration) {
        this.concentration = concentration;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Tablet(String name, String manufacturer, String description, double price, int quantity, double dosage, Date expirationDate, boolean requiresPrescription, int concentration, int count) {
        super(name, manufacturer, description, price, quantity, dosage, expirationDate, requiresPrescription);
        this.concentration = concentration;
        this.count = count;
    }

    @Override
    public String getType() {
        return "Tablet";
    }

    public Tablet() {
        super();
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Concentration: " + getConcentration());
        System.out.println("Count: " + getCount());
    }
    @Override
    public Tablet deepCopy() {
        // Create a new tablet with the same values
        // Important: create a new Date object for expirationDate to avoid sharing reference

        return new Tablet(
                getName(),
                getManufacturer(),
                getDescription(),
                getPrice(),
                getQuantity(),
                getDosage(),
                getExpirationDate(),
                getRequiresPrescription(),
                this.concentration,
                this.count
        );
    }
}
