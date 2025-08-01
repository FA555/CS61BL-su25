/**
 * This class represents a bank account whose current balance is a nonnegative
 * amount in US dollars.
 */
public class Account {

    private int balance;
    private final Account parentAccount;

    /** Initialize an account with the given balance. */
    public Account(int balance) {
        this.balance = balance;
        this.parentAccount = null;
    }

    public Account(int balance, Account parentAccount) {
        this.balance = balance;
        this.parentAccount = parentAccount;
    }

    /** Returns the balance for the current account. */
    public int getBalance() {
        return balance;
    }

    /** Deposits amount into the current account. */
    public void deposit(int amount) {
        if (amount < 0) {
            System.out.println("Cannot deposit negative amount.");
        } else {
            balance += amount;
        }
    }

    /**
     * Subtract amount from the account if possible. If subtracting amount
     * would leave a negative balance, print an error message and leave the
     * balance unchanged.
     */
    public boolean withdraw(int amount) {
        // DONE
        if (amount < 0) {
            System.out.println("Cannot withdraw negative amount.");
            return false;
        }

        if (balance < amount) {
            if (parentAccount != null && parentAccount.withdraw(amount - balance)) {
                // Covered by parent account
                balance = 0;
                return true;
            }

            System.out.println("Insufficient funds");
            return false;
        }

        balance -= amount;
        return true;
    }

    /**
     * Merge account other into this account by removing all money from other
     * and depositing it into this account.
     */
    public void merge(Account other) {
        int otherBalance = other.getBalance();
        other.withdraw(otherBalance);
        deposit(otherBalance);
    }
}
