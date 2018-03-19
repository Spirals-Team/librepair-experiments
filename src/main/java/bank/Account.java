package bank;

public class Account {

    private String id;
    private int balance;

    public Account(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }
}
