package com.example.BankSystem;

import javafx.beans.property.*;

public class Account {
    private final StringProperty accountId;
    private final StringProperty accountName;
    private final DoubleProperty balance;
    private final StringProperty accountType;

    // ✅ Main constructor (used by AccountManagement etc.)
    public Account(String accountId, String accountName, double balance, String accountType) {
        this.accountId = new SimpleStringProperty(accountId);
        this.accountName = new SimpleStringProperty(accountName);
        this.balance = new SimpleDoubleProperty(balance);
        this.accountType = new SimpleStringProperty(accountType);
    }

    // ✅ Optional constructor (used by subclasses like ChequeAccount, InvestmentAccount)
    public Account(String accountId, double balance) {
        this.accountId = new SimpleStringProperty(accountId);
        this.accountName = new SimpleStringProperty("Unnamed");
        this.balance = new SimpleDoubleProperty(balance);
        this.accountType = new SimpleStringProperty("General");
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
    public void setBalance(double value) {
        this.balance.set(value);
    }

    // ✅ Deposit method
    public void deposit(double amount) {
        if (amount > 0) {
            setBalance(getBalance() + amount);
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Deposit amount must be positive!");
        }
    }
}
