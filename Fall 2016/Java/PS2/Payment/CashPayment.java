package Payment;

/**
 * Created by Brad on 9/27/2016.
 */
public class CashPayment extends Payment {

    // Constructors
    public CashPayment() {
        this.setAmount(0);
    }

    public CashPayment(double amount) {
        this.setAmount(amount);
    }

    // Override
    public void paymentDetails() {
        System.out.println(String.format("$%.2f paid in cash", this.getAmount()));
    }
}
