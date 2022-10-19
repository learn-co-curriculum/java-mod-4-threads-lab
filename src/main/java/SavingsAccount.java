public class SavingsAccount {
    private String id;
    private int balance;

    public SavingsAccount(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public void withdraw(int amount) {
        if (amount <= balance)
            balance -= amount;
    }

    @Override
    public String toString() {
        return "SavingsAccount{" + "id='" + id + '\'' + ", balance=" + balance + '}';
    }
}
