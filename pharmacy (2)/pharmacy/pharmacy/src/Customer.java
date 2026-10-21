import java.util.List;

public class Customer {
    private final int id;
    private static int nextId =1;
    private String name;
    private String address;
    private String phone;
    private double balance;
    private Payment payment;
    private List<Order> orders;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public static void setId(int id) {
        nextId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Customer() {
        this.id = nextId++;
        payment = new Payment();
    }

    public Customer( String name, String address, String phone, double balance, Payment payment, List<Order> orders) {
        this.id = nextId++;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.balance = balance;
        this.payment = payment;
        this.orders = orders;
    }

    public boolean checkout(Order order) {
        return order.getTotalAmount() <= getBalance();
    }
    public List<Order> getOrders() {
        return orders;
    }
}