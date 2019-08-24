package Payment;

/**
 * Created by Brad on 9/27/2016.
 */
public class PaymentTest {
    public static void main(String[] args) {
        CashPayment cash1 = new CashPayment(101.23);
        CashPayment cash2 = new CashPayment(202.46);
        CreditCardPayment card1 = new CreditCardPayment(19000000, "San Francisco 49ers", "02/21", "3000 0000 0000 04");
        CreditCardPayment card2 = new CreditCardPayment(33000000, "New York Yankees", "09/17", "5500 0000 0000 0004");
        cash1.paymentDetails();
        cash2.paymentDetails();
        card1.paymentDetails();
        card2.paymentDetails();
    }
}
