package com.example.BankSystem;

public class User {
    private final int id;
    private final String name;
    private final String username;
    private final String email;
    private final String accountType;

    public User(int id, String name, String username, String email, String accountType) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.accountType = accountType;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getAccountType() { return accountType; }
}
