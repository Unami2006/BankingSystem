package com.example.BankSystem;

import javafx.beans.property.*;

public class Transaction {
    private final StringProperty transactionId;
    private final StringProperty accountId;
    private final StringProperty type;
    private final DoubleProperty amount;
    private final StringProperty description;
    private final StringProperty targetAccount;
    private final StringProperty userId;
    private final StringProperty timestamp;

    public Transaction(String transactionId, String accountId, String type,
                       double amount, String description, String targetAccount,
                       String userId) {
        this.transactionId = new SimpleStringProperty(transactionId);
        this.accountId = new SimpleStringProperty(accountId);
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleDoubleProperty(amount);
        this.description = new SimpleStringProperty(description);
        this.targetAccount = new SimpleStringProperty(targetAccount);
        this.userId = new SimpleStringProperty(userId);
        this.timestamp = new SimpleStringProperty(java.time.LocalDateTime.now().toString());
    }

    // constructor used when loading DB values (timestamp included)
    public Transaction(String transactionId, String accountId, String type,
                       double amount, String description, String targetAccount,
                       String userId, String timestamp) {
        this.transactionId = new SimpleStringProperty(transactionId);
        this.accountId = new SimpleStringProperty(accountId);
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleDoubleProperty(amount);
        this.description = new SimpleStringProperty(description);
        this.targetAccount = new SimpleStringProperty(targetAccount);
        this.userId = new SimpleStringProperty(userId);
        this.timestamp = new SimpleStringProperty(timestamp);
    }

    public String getTransactionId() { return transactionId.get(); }
    public String getAccountId() { return accountId.get(); }
    public String getType() { return type.get(); }
    public double getAmount() { return amount.get(); }
    public String getDescription() { return description.get(); }
    public String getTargetAccount() { return targetAccount.get(); }
    public String getUserId() { return userId.get(); }
    public String getTimestamp() { return timestamp.get(); }
}
