package com.example.BankSystem;

public class SavingsAccount extends Account implements InterestPayable {
    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void calculateInterest() {
        double interest = balance * 0.0005; // 0.05%
        balance += interest;
        System.out.println("Savings interest added: " + interest);
    }
}
