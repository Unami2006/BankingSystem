package com.example.BankSystem;

import javafx.beans.property.*;

public class Account {
    private final StringProperty accountId;
    private final StringProperty accountName;
    private final DoubleProperty balance;
    private final StringProperty accountType;

    // ✅ Main constructor
    public Account(String accountId, String accountName, double balance, String accountType) {
        this.accountId = new SimpleStringProperty(accountId);
        this.accountName = new SimpleStringProperty(accountName);
        this.balance = new SimpleDoubleProperty(balance);
        this.accountType = new SimpleStringProperty(accountType);
    }

    // ✅ Optional simplified constructor
    public Account(String accountId, double balance) {
        this(accountId, "Unnamed", balance, "General");
    }

    // ✅ Getters and property accessors
    public String getAccountId() { return accountId.get(); }
    public StringProperty accountIdProperty() { return accountId; }

    public String getAccountName() { return accountName.get(); }
    public StringProperty accountNameProperty() { return accountName; }

    public double getBalance() { return balance.get(); }
    public DoubleProperty balanceProperty() { return balance; }

    public String getAccountType() { return accountType.get(); }
    public StringProperty accountTypeProperty() { return accountType; }

    // ✅ Setter for balance
    public void setBalance(double value) { this.balance.set(value); }

    // ✅ Deposit method
    public void deposit(double amount) {
        if (amount > 0) {
            setBalance(getBalance() + amount);
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Deposit amount must be positive!");
        }
    }

    // ✅ Withdraw method
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= getBalance()) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrew: " + amount);
            return true;
        } else {
            System.out.println("Invalid withdrawal: insufficient funds or negative amount.");
            return false;
        }
    }

    // ✅ Helpful for debugging and logs
    @Override
    public String toString() {
        return String.format("Account[%s | %s | %.2f | %s]",
                getAccountId(), getAccountName(), getBalance(), getAccountType());
    }
}
