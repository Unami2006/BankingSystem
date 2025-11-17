package com.example.BankSystem;

public class TransactionController {

    // Deposit
    public static void deposit(Account account, double amount, String userId) {
        if (amount <= 0) return;

        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        AccountDAO.updateBalance(account.getAccountId(), newBalance);

        Transaction tx = new Transaction(
                TransactionDAO.generateTransactionId(),
                account.getAccountId(),
                "Deposit",
                amount,
                "Deposit into account",
                "",
                userId
        );

        TransactionDAO.saveTransaction(tx);
    }

    // Withdraw
    public static boolean withdraw(Account account, double amount, String userId) {
        if (amount <= 0 || amount > account.getBalance()) return false;

        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        AccountDAO.updateBalance(account.getAccountId(), newBalance);

        Transaction tx = new Transaction(
                TransactionDAO.generateTransactionId(),
                account.getAccountId(),
                "Withdrawal",
                amount,
                "Withdraw from account",
                "",
                userId
        );

        TransactionDAO.saveTransaction(tx);
        return true;
    }

    // Transfer (two transaction records)
    public static boolean transfer(Account source, Account target, double amount, String userId) {
        if (amount <= 0 || amount > source.getBalance()) return false;

        source.setBalance(source.getBalance() - amount);
        AccountDAO.updateBalance(source.getAccountId(), source.getBalance());

        target.setBalance(target.getBalance() + amount);
        AccountDAO.updateBalance(target.getAccountId(), target.getBalance());

        Transaction txOut = new Transaction(
                TransactionDAO.generateTransactionId(),
                source.getAccountId(),
                "Transfer Out",
                amount,
                "Transfer to " + target.getAccountId(),
                target.getAccountId(),
                userId
        );

        Transaction txIn = new Transaction(
                TransactionDAO.generateTransactionId(),
                target.getAccountId(),
                "Transfer In",
                amount,
                "Transfer from " + source.getAccountId(),
                source.getAccountId(),
                userId
        );

        TransactionDAO.saveTransaction(txOut);
        TransactionDAO.saveTransaction(txIn);

        return true;
    }
}
