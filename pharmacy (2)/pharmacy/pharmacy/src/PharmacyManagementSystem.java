import java.util.ArrayList;
import java.util.List;

public class PharmacyManagementSystem {
    private final String username = "admin";
    private final String password = "adminpassword";
    private List<PharmacyItem> inventory;

    public PharmacyManagementSystem(List<PharmacyItem> inventory) {
        this.inventory = inventory;
    }

    public PharmacyManagementSystem() {
        this.inventory = new ArrayList<PharmacyItem>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<PharmacyItem> getInventory() {
        return inventory;
    }

    public void setInventory(List<PharmacyItem> inventory) {
        this.inventory = inventory;
    }

    public void addItem(PharmacyItem item) {
        inventory.add(item);
    }
    public void removeItem(PharmacyItem item) {
        inventory.remove(item);
    }
    public void updateItem(PharmacyItem item) {//do we pass an item or pass id or name, and how exactly do we update?
        inventory.set(inventory.indexOf(item), item);
    }
    public void viewInventory() {
        for (PharmacyItem item : inventory) {
            item.displayInfo();
            System.out.println();
        }
    }
    public PharmacyItem getItemByName(String name) {
        for (PharmacyItem item : inventory) {
            if (item.getName().equals(name)) {
                return item; // This should return the actual subclass object
            }
        }
        return null;
    }
}



