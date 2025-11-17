package com.example.BankSystem;

public class SavingsAccount extends Account implements InterestPayable {
    public SavingsAccount(String accountId, String accountName, double balance, String userId) {
        super(accountId, accountName, balance, "Savings", userId);
    }

    @Override
    public void calculateInterest() {
        double interest = getBalance() * 0.0005; // as your original code
        setBalance(getBalance() + interest);
    }
}
