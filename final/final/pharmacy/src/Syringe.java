public class Syringe extends MedicalSupply{
    private double capacity;
    private double needleGauge;


    public Syringe() {
        super();
    }
    public Syringe(String name, String manufacturer, String description, double price, int quantity, String category, double capacity, double needleGauge) {
        super(name, manufacturer, description, price, quantity, category);
        this.capacity = capacity;
        this.needleGauge = needleGauge;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getNeedleGauge() {
        return needleGauge;
    }

    public void setNeedleGauge(double needleGauge) {
        this.needleGauge = needleGauge;
    }
    @Override
    public String getType(){
        return "Syringe";
    }

    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Syringe Capacity : " + getCapacity());
        System.out.println("Needle Gauge: " + getNeedleGauge());
    }
    @Override
    public Syringe deepCopy() {
        return new Syringe(
                getName(),
                getManufacturer(),
                getDescription(),
                getPrice(),
                getQuantity(),
                getCategory(),
                this.capacity,
                this.needleGauge
        );
    }
}
