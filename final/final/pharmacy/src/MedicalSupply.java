public class MedicalSupply extends PharmacyItem{
    private String category;

    public MedicalSupply() {
    }
    public MedicalSupply(String name, String manufacturer, String description, double price, int quantity, String category) {
        super(name, manufacturer, description, price, quantity);
        this.category = category;
    }
    @Override
    public String getType(){
        return "MedicalSupply";
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Category: " + getCategory());
    }

    public MedicalSupply deepCopy() {
        return new MedicalSupply(
                getName(),
                getManufacturer(),
                getDescription(),
                getPrice(),
                getQuantity(),
                getCategory()
        );
    }
}
