package com.example.guii;
public class Bandages extends MedicalSupply{
    private String size;
    private String material;
    private int quantityPerPack;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getQuantityPerPack() {
        return quantityPerPack;
    }

    public void setQuantityPerPack(int quantityPerPack) {
        this.quantityPerPack = quantityPerPack;
    }

    public Bandages(String name, String manufacturer, String description, double price, int quantity, String category, String size, String material, int quantityPerPack) {
        super(name, manufacturer, description, price, quantity, category);
        this.size = size;
        this.material = material;
        this.quantityPerPack = quantityPerPack;
    }
    @Override
    public String getType(){
        return "Bandages";
    }

    public Bandages() {
        super();
}
@Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Size: " + getSize());
        System.out.println("Material: " + getMaterial());
        System.out.println("Quantity: " + getQuantityPerPack());
}
    @Override
    public Bandages deepCopy() {
        // Create a new tablet with the same values
        // Important: create a new Date object for expirationDate to avoid sharing reference

        return new Bandages(
                getName(),
                getManufacturer(),
                getDescription(),
                getPrice(),
                getQuantity(),
                getCategory(),
                this.size,
                this.material,
                this.quantityPerPack
        );
    }
}
