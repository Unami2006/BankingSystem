package com.example.BankSystem;

public class Main {
    public static void main(String[] args) {
        // Create an individual customer
        IndividualCustomer cust1 = new IndividualCustomer(
                "C001", "Nyasha", "Zuva", "Gaborone", "72000000",
                "nyasha@example.com", "ID12345");

        // Create accounts
        SavingsAccount savings = new SavingsAccount("SAV001", 1000);
        InvestmentAccount invest = new InvestmentAccount("INV001", 2000);
        ChequeAccount cheque = new ChequeAccount("CHQ001", 1500, "ABC Ltd");

        // Add to customer
        cust1.addAccount(savings);
        cust1.addAccount(invest);
        cust1.addAccount(cheque);

        // Perform actions
        invest.deposit(500);
        invest.withdraw(300);
        invest.calculateInterest();
        
        cust1.displayAccounts();
    }
}

