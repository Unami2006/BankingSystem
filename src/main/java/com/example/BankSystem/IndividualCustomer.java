package com.example.BankSystem;

public class IndividualCustomer extends Customer {
    private String idNumber;

    public IndividualCustomer(String customerId, String firstName, String lastName,
                              String address, String phone, String email, String idNumber) {
        super(customerId, firstName, lastName, address, phone, email);
        this.idNumber = idNumber;
    }
}
