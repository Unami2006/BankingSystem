package com.example.BankSystem;

public class InvestmentAccount extends Account implements InterestPayable, Withdrawable {
    private static final double MIN_BALANCE = 500.0;
    private static final double INTEREST_RATE = 0.05; // 5%

    public InvestmentAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void calculateInterest() {
        double interest = getBalance() * INTEREST_RATE;
        setBalance(getBalance() + interest);
        System.out.println("Investment interest added: " + interest);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive!");
            return false;
        }

        if (getBalance() - amount >= MIN_BALANCE) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrawn: " + amount + " | Remaining balance: " + getBalance());
            return true;
        } else {
            System.out.println("Cannot withdraw below minimum balance of " + MIN_BALANCE);
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("InvestmentAccount[%s | Balance: %.2f | Min: %.2f]",
                getAccountId(), getBalance(), MIN_BALANCE);
    }
}
