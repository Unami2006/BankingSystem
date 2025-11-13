package com.example.BankSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // Save a transaction
    public static void saveTransaction(Transaction tx) {
        String sql = "INSERT INTO transactions (accountId, type, amount, targetAccount, description, timestamp) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tx.getAccountId());
            pstmt.setString(2, tx.getType());
            pstmt.setDouble(3, tx.getAmount());
            pstmt.setString(4, tx.getTargetAccount());
            pstmt.setString(5, tx.getDescription());
            pstmt.setString(6, tx.getTimestamp().toString());

            pstmt.executeUpdate();
            System.out.println("✅ Transaction saved successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Error saving transaction: " + e.getMessage());
        }
    }

    // Load all transactions
    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("transactionId"),
                        rs.getString("accountId"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("targetAccount"),
                        rs.getString("description"),
                        rs.getString("timestamp")
                ));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error loading transactions: " + e.getMessage());
        }

        return transactions;
    }
}
