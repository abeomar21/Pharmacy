import java.util.Date;

public class Payment {
    private final int id;
    private static int nextId=1;
    private String paymentMethod;
    private Date paymentDate;

    public Payment() {
        this.id=nextId++;
    }

    public Payment( String paymentMethod, Date paymentDate) {
        this.id = nextId++;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
    }

    public int getId() {
        return id;
    }

    public static  void setId(int id) {
        nextId = id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void generateReceipt(Order order) {
        System.out.println("Invoice for " + order.getId());
        for (PharmacyItem item : order.getItems()) {
            item.displayInfo();
            System.out.println("Total price: "+item.getQuantity()*item.getPrice());
        }
        System.out.println("Total amount for order " + order.getId() + ": " + order.calculateTotalAmount());
    }
}
