package com.example.BankSystem;

import java.time.LocalDateTime;

public class Transaction {
    private int transactionId;          // DB primary key
    private String accountId;           // FK to Account
    private String type;                // Deposit, Withdrawal, Transfer
    private double amount;
    private String targetAccount;       // Optional, for transfers
    private String description;
    private LocalDateTime timestamp;    // For date/time

    // Constructor for creating a new transaction (without DB ID)
    public Transaction(String accountId, String type, double amount, String description, String targetAccount) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.targetAccount = targetAccount;
        this.timestamp = LocalDateTime.now();
    }

    // Constructor for loading from DB (with ID and timestamp)
    public Transaction(int transactionId, String accountId, String type, double amount,
                       String targetAccount, String description, String timestamp) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.targetAccount = targetAccount;
        this.description = description;
        this.timestamp = LocalDateTime.parse(timestamp);
    }

    // Getters
    public int getTransactionId() { return transactionId; }
    public String getAccountId() { return accountId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getTargetAccount() { return targetAccount; }
    public String getDescription() { return description; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + type + " - " + amount +
                (targetAccount != null ? " (Target: " + targetAccount + ")" : "") +
                " | " + description;
    }
}
