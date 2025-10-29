package com.example.BankSystem;

public class IndividualCustomer extends Customer {

    private String address;
    private String phoneNumber;
    private String idNumber;

    public IndividualCustomer(String customerId, String name, String username, String address,
                              String phoneNumber, String email, String idNumber) {
        // Call the parent Customer constructor
        super(customerId, name, username, "defaultPass", email, "Individual");
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.idNumber = idNumber;
    }

    // ✅ Display customer details and accounts
    public void displayAccounts() {
        System.out.println("Customer: " + getName() + " (" + getUsername() + ")");
        for (Account account : getAccounts()) {
            System.out.println("Account ID: " + account.getAccountId() +
                    " | Type: " + account.getAccountType() +
                    " | Balance: " + account.getBalance());
        }
    }

    // Getters and setters
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
}
