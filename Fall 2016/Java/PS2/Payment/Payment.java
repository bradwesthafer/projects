package Payment;

/**
 * Created by Brad on 9/27/2016.
 */
public class Payment {
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void paymentDetails() {
        System.out.println(String.format("$%.2f paid", amount));
    }
}
