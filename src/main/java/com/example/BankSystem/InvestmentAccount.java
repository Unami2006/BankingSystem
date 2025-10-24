package com.example.BankSystem;

public class InvestmentAccount extends Account implements InterestPayable, Withdrawable {
    private final double MIN_BALANCE = 500.0;

    public InvestmentAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void calculateInterest() {
        double interest = getBalance() * 0.05; // 5%
        setBalance(getBalance() + interest); // use setter
        System.out.println("Investment interest added: " + interest);
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() - amount >= MIN_BALANCE) {
            setBalance(getBalance() - amount); // use setter
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Cannot withdraw below minimum balance of " + MIN_BALANCE);
        }
    }
}
