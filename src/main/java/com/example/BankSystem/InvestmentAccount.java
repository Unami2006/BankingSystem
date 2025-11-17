package com.example.BankSystem;

public class InvestmentAccount extends Account {

    private static final double INTEREST_RATE = 0.05; // 5% interest

    public InvestmentAccount(String accountId, String accountName, double balance, String userId) {
        super(accountId, accountName, balance, "Investment", userId);
    }

    /** Apply exactly 5% interest as required by the assignment */
    public double applyInterest() {
        double newBalance = getBalance() * (1 + INTEREST_RATE);
        setBalance(newBalance);
        return newBalance;
    }
}
