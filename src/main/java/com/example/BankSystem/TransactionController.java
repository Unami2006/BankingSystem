package com.example.BankSystem;

public class TransactionController {

    // Deposit money into an account
    public static boolean deposit(Account account, double amount) {
        if (amount <= 0) return false;

        account.deposit(amount); // Update account balance

        Transaction tx = new Transaction(
                account.getAccountId(),
                "Deposit",
                amount,
                "Deposit successful",
                null
        );

        TransactionDAO.saveTransaction(tx);
        return true;
    }

    // Withdraw money from an account
    public static boolean withdraw(Account account, double amount) {
        if (amount <= 0 || !account.withdraw(amount)) return false;

        Transaction tx = new Transaction(
                account.getAccountId(),
                "Withdrawal",
                amount,
                "Withdrawal successful",
                null
        );

        TransactionDAO.saveTransaction(tx);
        return true;
    }

    // Transfer money from one account to another
    public static boolean transfer(Account from, Account to, double amount) {
        if (amount <= 0 || !from.withdraw(amount)) return false;

        to.deposit(amount);

        Transaction tx = new Transaction(
                from.getAccountId(),
                "Transfer",
                amount,
                "Transferred to " + to.getAccountId(),
                to.getAccountId()
        );

        TransactionDAO.saveTransaction(tx);
        return true;
    }
}
