package com.example.BankSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionDAO {

    public static String generateTransactionId() {
        return UUID.randomUUID().toString();
    }

    public static void saveTransaction(Transaction tx) {
        String sql = "INSERT INTO transactions(transactionId, accountId, type, amount, description, targetAccount, userId, timestamp) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tx.getTransactionId());
            pstmt.setString(2, tx.getAccountId());
            pstmt.setString(3, tx.getType());
            pstmt.setDouble(4, tx.getAmount());
            pstmt.setString(5, tx.getDescription());
            pstmt.setString(6, tx.getTargetAccount());
            pstmt.setString(7, tx.getUserId());
            pstmt.setString(8, tx.getTimestamp());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Transaction> getTransactionsForUser(String userId) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE userId = ? ORDER BY timestamp DESC";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new Transaction(
                        rs.getString("transactionId"),
                        rs.getString("accountId"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("targetAccount"),
                        rs.getString("userId"),
                        rs.getString("timestamp")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
