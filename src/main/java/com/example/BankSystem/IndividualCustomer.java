package com.example.BankSystem;

import java.util.ArrayList;
import java.util.List;

public class IndividualCustomer {
    private final String userId;
    private final String firstName, lastName, address, phone, email, idNumber;
    private final List<Account> accounts = new ArrayList<>();

    public IndividualCustomer(String userId, String firstName, String lastName, String address, String phone, String email, String idNumber) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.idNumber = idNumber;
    }

    public String getUserId() { return userId; }

    public void addAccount(Account account) { accounts.add(account); }

    public void displayAccounts() {
        System.out.println(firstName + " " + lastName + " accounts:");
        for (Account acc : accounts) System.out.println(acc);
    }
}
