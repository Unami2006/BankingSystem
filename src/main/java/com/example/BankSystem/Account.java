package com.example.BankSystem;

import javafx.beans.property.*;

public class Account {
    private final StringProperty accountId;
    private final StringProperty accountName;
    private final DoubleProperty balance;
    private final StringProperty accountType;
    private final String userId;

    public Account(String accountId, String accountName, double balance, String accountType, String userId) {
        this.accountId = new SimpleStringProperty(accountId);
        this.accountName = new SimpleStringProperty(accountName);
        this.balance = new SimpleDoubleProperty(balance);
        this.accountType = new SimpleStringProperty(accountType);
        this.userId = userId;
    }

    public String getAccountId() { return accountId.get(); }
    public StringProperty accountIdProperty() { return accountId; }

    public String getAccountName() { return accountName.get(); }
    public StringProperty accountNameProperty() { return accountName; }

    public double getBalance() { return balance.get(); }
    public DoubleProperty balanceProperty() { return balance; }

    public String getAccountType() { return accountType.get(); }
    public StringProperty accountTypeProperty() { return accountType; }

    public String getUserId() { return userId; }

    public void setBalance(double value) { this.balance.set(value); }

    public void deposit(double amount) {
        if (amount > 0) setBalance(getBalance() + amount);
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= getBalance()) {
            setBalance(getBalance() - amount);
            return true;
        }
        return false;
    }
}
