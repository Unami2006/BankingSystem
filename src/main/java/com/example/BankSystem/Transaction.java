package com.example.BankSystem;

import java.time.LocalDateTime;

public class Transaction {
    private final String transactionId;
    private final String accountNumber;
    private final String type; // Deposit, Withdrawal, Transfer
    private final double amount;
    private final LocalDateTime date;
    private final String description;

    public Transaction(String transactionId, String accountNumber, String type, double amount, String description) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.description = description;
    }

    public String getTransactionId() { return transactionId; }
    public String getAccountNumber() { return accountNumber; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getDate() { return date; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("%s | %s | %.2f | %s | %s", date, type, amount, accountNumber, description);
    }
}
