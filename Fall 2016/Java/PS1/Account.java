public class Account {
    private double balance;
    private String name;
    private int accNum;
    private String type;

    Account(int accNum, String type, String name, double balance) {
        this.accNum = accNum;
        this.type = type;
        this.name = name;
        if (balance >= 0.0) {
            this.balance = balance;
        }
        else {
            this.balance = 0.0;
        }
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccNum() {
        return accNum;
    }

    public void setAccNum(int accNum) {
        this.accNum = accNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean deposit(double deposit) {
        if (deposit >= 0.0) {
            balance += deposit;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        else {
            return false;
        }
    }
}

class Main {
    public static void main(String[] args) {
        Account test = new Account(1234, "Platinum", "John Doe", 10000);
        System.out.println(String.format("%1$s Account number %2$d created for %3$s with initial balance of $%4$.2f."
                , test.getType(), test.getAccNum(), test.getName(), test.getBalance()));
        System.out.println(String.format("Now attempting to withdraw $50,000.00 from %1$s's account...", test.getName()));
        if (test.withdraw(50000.00)) {
            System.out.println(String.format("$50,000.00 withdrawn from %1$s's %2$s Bank Account, leaving the account with a remaining balance of $%3$.2f."
                    , test.getName(), test.getType(), test.getBalance()));
        }
        else {
            System.out.println("Withdrawal failed due to insufficient funds.");
        }
        System.out.println(String.format("Now attempting to deposit $-50,000.00 in %1$s's account...", test.getName()));
        if(test.deposit(-50000.00)) {
            System.out.println(String.format("$-50,000.00 deposited into %1$s's %2$s Bank Account, resulting in a new balance of $%3$.2f."
                    , test.getName(), test.getType(), test.getBalance()));
        }
        else {
            System.out.println("Deposit failed.");
        }
        System.out.println(String.format("Now attempting to deposit $50,000.00 in %1$s's account...", test.getName()));
        if(test.deposit(50000.00)) {
            System.out.println(String.format("$50,000.00 deposited into %1$s's %2$s Bank Account, resulting in a new balance of $%3$.2f."
                    , test.getName(), test.getType(), test.getBalance()));
        }
        else {
            System.out.println("Deposit failed.");
        }
        System.out.println(String.format("Now attempting to withdraw $5000.00 from %1$s's account...", test.getName()));
        if (test.withdraw(5000.00)) {
            System.out.println(String.format("$5000.00 withdrawn from %1$s's %2$s Bank Account, leaving the account with a remaining balance of $%3$.2f."
                    , test.getName(), test.getType(), test.getBalance()));
        }
        else {
            System.out.println("Withdrawal failed due to insufficient funds.");
        }
    }
}