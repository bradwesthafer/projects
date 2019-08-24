package Payment;

/**
 * Created by Brad on 9/27/2016.
 */
public class CreditCardPayment extends Payment {
    private String cardholderName;
    private String expirationDate;
    private String creditCardNumber;

    // Constructor
    public CreditCardPayment(double amount, String cardholderName, String expirationDate, String creditCardNumber) {
        setAmount(amount);
        setCardholderName(cardholderName);
        setCreditCardNumber(creditCardNumber);
        setExpirationDate(expirationDate);
    }

    // Getters and Setters
    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    // Override
    public void paymentDetails() {
        System.out.println(String.format("$%.2f paid from %s's credit card number %s, which has an expiration date of %s.", this.getAmount(), this.cardholderName, this.creditCardNumber, this.expirationDate));
    }
}
