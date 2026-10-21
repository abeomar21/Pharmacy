import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testCheckoutWithSufficientBalance() {
        // Create an order with items that total to less than the customer's balance
        Customer customer=new Customer();
        customer.setBalance(50);
        Order order = new Order();
        order.addItem(new MedicalSupply("paracetamol","aaa","for headache",40,1,"pain relief")); // 1*40=40
        assertTrue(customer.checkout(order));
    }

    @Test
    void testCheckoutWithExactBalance() {
        Customer customer=new Customer();
        customer.setBalance(50);
        Order order = new Order();
        order.addItem(new MedicalSupply("paracetamol","aaa","for headache",50,1,"pain relief")); // 1*40=40
        assertTrue(customer.checkout(order));
    }

    @Test
    void testCheckoutWithInsufficientBalance() {
        // Set customer balance to less than the order total
        Customer customer=new Customer();
        customer.setBalance(50);
        Order order = new Order();
        order.addItem(new MedicalSupply("paracetamol","aaa","for headache",600,1,"pain relief")); // 1*40=40
        assertFalse(customer.checkout(order));
    }
}