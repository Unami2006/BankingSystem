package com.example.BankSystem;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    protected String customerId;
    protected String firstName;
    protected String lastName;
    protected String address;
    protected String phone;
    protected String email;
    protected List<Account> accounts = new ArrayList<>();

    public Customer(String customerId, String firstName, String lastName,
                    String address, String phone, String email) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public void addAccount(Account acc) {
        accounts.add(acc);
    }

    public void displayAccounts() {
        System.out.println(firstName + "'s Accounts:");
        for (Account a : accounts) {
            System.out.println("- " + a.getAccountId()
                    + " | Balance: " + a.getBalance());
        }
    }
}
