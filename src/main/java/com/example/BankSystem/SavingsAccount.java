package com.example.BankSystem;

public class SavingsAccount extends Account implements InterestPayable {

    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void calculateInterest() {
        double interest = getBalance() * 0.0005; // 0.05%
        setBalance(getBalance() + interest); // use setter
        System.out.println("Savings interest added: " + interest);
    }
}
