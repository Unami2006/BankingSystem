package com.example.BankSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountDAO {

    public static String generateAccountId() {
        // First digit: 1–9
        int firstDigit = (int) (Math.random() * 9) + 1;

        // Next 4 digits: 0–9
        int d2 = (int) (Math.random() * 10);
        int d3 = (int) (Math.random() * 10);
        int d4 = (int) (Math.random() * 10);
        int d5 = (int) (Math.random() * 10);

        return "A" + firstDigit + d2 + d3 + d4 + d5;
    }

    public static void saveAccount(Account account) {
        String sql = "INSERT OR REPLACE INTO accounts(accountId, accountName, balance, accountType, userId) VALUES(?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, account.getAccountId());
            pstmt.setString(2, account.getAccountName());
            pstmt.setDouble(3, account.getBalance());
            pstmt.setString(4, account.getAccountType());
            pstmt.setString(5, account.getUserId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Account> loadAccountsForUser(String userId) {
        List<Account> list = new ArrayList<>();

        String sql = "SELECT * FROM accounts WHERE userId = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new Account(
                        rs.getString("accountId"),
                        rs.getString("accountName"),
                        rs.getDouble("balance"),
                        rs.getString("accountType"),
                        rs.getString("userId")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void updateBalance(String accountId, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE accountId = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accountId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAccount(String accountId) {
        String sql = "DELETE FROM accounts WHERE accountId = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Account getAccountById(String accountId) {
        String sql = "SELECT * FROM accounts WHERE accountId = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getString("accountId"),
                        rs.getString("accountName"),
                        rs.getDouble("balance"),
                        rs.getString("accountType"),
                        rs.getString("userId")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
