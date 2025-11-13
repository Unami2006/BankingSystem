package com.example.BankSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class AccountDAO {

    public static ObservableList<Account> loadAccounts() {
        ObservableList<Account> accounts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM accounts";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                accounts.add(new Account(
                        rs.getString("accountId"),
                        rs.getString("accountName"),
                        rs.getDouble("balance"),
                        rs.getString("accountType")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static void saveAccount(Account account) {
        String sql = "INSERT OR REPLACE INTO accounts(accountId, accountName, balance, accountType) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account.getAccountId());
            pstmt.setString(2, account.getAccountName());
            pstmt.setDouble(3, account.getBalance());
            pstmt.setString(4, account.getAccountType());
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
}
