package com.example.BankSystem;

public class ChequeAccount extends Account implements Withdrawable {
    private String employerName;
    private static final double OVERDRAFT_LIMIT = 500.0; // Optional feature

    public ChequeAccount(String accountNumber, double balance, String employerName) {
        super(accountNumber, balance);
        this.employerName = employerName;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive!");
            return false;
        }

        // ✅ Allow small overdraft if needed
        if (getBalance() - amount >= -OVERDRAFT_LIMIT) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrawn: " + amount + " | New Balance: " + getBalance());
            return true;
        } else {
            System.out.println("Insufficient funds or overdraft limit exceeded!");
            return false;
        }
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    @Override
    public String toString() {
        return String.format("ChequeAccount[%s | %.2f | Employer: %s]",
                getAccountId(), getBalance(), employerName);
    }
}
