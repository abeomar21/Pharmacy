package com.example.guii;
import java.util.Date;

public class Liquid extends Medication{
private double volume;
public Liquid(){
    super();
}

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
    @Override
    public String getType(){
        return "Liquid";
    }


    public Liquid(String name, String manufacturer, String description, double price, int quantity, double dosage, Date expirationDate, boolean requiresPrescription, double volume) {
        super(name, manufacturer, description, price, quantity, dosage, expirationDate, requiresPrescription);
        this.volume = volume;
    }
    @Override
    public void displayInfo(){
    super.displayInfo();
    System.out.println("Volume: " + getVolume());
    }
    @Override
    public Liquid deepCopy() {
        // Create a new tablet with the same values
        // Important: create a new Date object for expirationDate to avoid sharing reference

        return new Liquid(
                getName(),
                getManufacturer(),
                getDescription(),
                getPrice(),
                getQuantity(),
                getDosage(),
                getExpirationDate(),
                getRequiresPrescription(),
                this.volume
        );
    }
}
