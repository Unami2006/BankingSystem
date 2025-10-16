package com.example.BankSystem;

public class CompanyCustomer extends Customer {
    private String companyRegNo;

    public CompanyCustomer(String customerId, String firstName, String lastName,
                           String address, String phone, String email, String companyRegNo) {
        super(customerId, firstName, lastName, address, phone, email);
        this.companyRegNo = companyRegNo;
    }
}
