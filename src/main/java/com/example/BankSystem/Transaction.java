package com.example.BankSystem;

public class Transaction {
    private String transactionId;
    private String type;
    private double amount;

    public Transaction(String transactionId, String type, double amount) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
    }

    public void performTransaction() {
        System.out.println("Transaction performed: " + type + " of " + amount);
    }
}
