import java.io.Serializable;
import java.util.Date;

public class Medication extends PharmacyItem {
    private double dosage;
    private Date expirationDate;
    private boolean requiresPrescription;

    public Medication() {
    }

    public Medication(String name, String manufacturer, String description, double price, int quantity, double dosage, Date expirationDate, boolean requiresPrescription) {
        super(name, manufacturer, description, price, quantity);
        this.dosage = dosage;
        this.expirationDate = expirationDate;
        this.requiresPrescription = requiresPrescription;
    }
    @Override
    public String getType(){
        return "Medication";
    }

    public double getDosage() {
        return dosage;
    }

    public void setDosage(double dosage) {
        this.dosage = dosage;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    public boolean getRequiresPrescription() {
        return requiresPrescription;
    }

    public void setRequiresPrescription(boolean requiresPrescription) {
        this.requiresPrescription = requiresPrescription;
    }

    public boolean isExpired(){
        return expirationDate.after(new Date());
    }
    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Dosage: " + getDosage());
        System.out.println("Expiration Date: " + getExpirationDate());
        System.out.println("Requires Prescription: " + getRequiresPrescription());
    }
    public Medication deepCopy() {
        // Create a new tablet with the same values
        // Important: create a new Date object for expirationDate to avoid sharing reference

        return new Medication(
                getName(),
                getManufacturer(),
                getDescription(),
                getPrice(),
                getQuantity(),
                getDosage(),
                getExpirationDate(),
                getRequiresPrescription()
        );
    }

}
