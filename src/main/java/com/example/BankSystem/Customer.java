package com.example.BankSystem;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerId;
    private String name;
    private String username;
    private String password;
    private String email;
    private String accountType;

    // Each customer can have multiple accounts
    private List<Account> accounts = new ArrayList<>();

    // ✅ Constructor with all 6 parameters
    public Customer(String customerId, String name, String username, String password, String email, String accountType) {
        this.customerId = customerId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.accountType = accountType;
    }

    // Constructor with fewer arguments (optional)
    public Customer(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    // ✅ Add account to the customer's list
    public void addAccount(Account account) {
        accounts.add(account);
    }

    // ✅ Get all customer accounts
    public List<Account> getAccounts() {
        return accounts;
    }

    // ✅ Find an account by ID (optional helper)
    public Account getAccountById(String accountId) {
        for (Account acc : accounts) {
            if (acc.getAccountId().equals(accountId)) {
                return acc;
            }
        }
        return null;
    }

    // ✅ Getters and Setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
}
