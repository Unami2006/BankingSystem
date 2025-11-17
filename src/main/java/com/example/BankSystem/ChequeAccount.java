package com.example.BankSystem;

public class ChequeAccount extends Account implements Withdrawable {
    private final String companyName;

    public ChequeAccount(String accountId, String accountName, double balance, String userId, String companyName) {
        super(accountId, accountName, balance, "Cheque", userId);
        this.companyName = companyName;
    }

    public String getCompanyName() { return companyName; }

    @Override
    public boolean withdraw(double amount) {
        return super.withdraw(amount);
    }
}
