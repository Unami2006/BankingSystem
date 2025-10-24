package com.example.BankSystem;

public class ChequeAccount extends Account implements Withdrawable {
    private String employerName;

    public ChequeAccount(String accountNumber, double balance, String employerName) {
        super(accountNumber, balance);
        this.employerName = employerName;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= getBalance()) {
            setBalance(getBalance() - amount); // Use getter & setter
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }
}
