import java.util.Date;

public class Injection extends Medication{
private double needleGauge;
private double volumePerDose;
    public Injection() {
        super();
    }

    public double getNeedleGauge() {
        return needleGauge;
    }


    public void setNeedleGauge(double needleGauge) {
        this.needleGauge = needleGauge;
    }

    public double getVolumePerDose() {
        return volumePerDose;
    }

    public Injection(String name, String manufacturer, String description, double price, int quantity, double dosage, Date expirationDate, boolean requiresPrescription, double needleGauge,double volumePerDose) {
        super(name, manufacturer, description, price, quantity, dosage, expirationDate, requiresPrescription    );
        this.needleGauge = needleGauge;
        this.volumePerDose = volumePerDose;
    }
    @Override
    public String getType(){
        return "Injection";
    }

    public void setVolumePerDose(double volumePerDose) {
        this.volumePerDose = volumePerDose;
    }
    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("NeedleGauge: " + getNeedleGauge());
        System.out.println("VolumePerDose: " + getVolumePerDose());
    }

    @Override
    public Injection deepCopy() {
        // Create a new tablet with the same values
        // Important: create a new Date object for expirationDate to avoid sharing reference

        return new Injection(
                getName(),
                getManufacturer(),
                getDescription(),
                getPrice(),
                getQuantity(),
                getDosage(),
                getExpirationDate(),
                getRequiresPrescription(),
                this.needleGauge,
                this.volumePerDose
        );
    }

}
