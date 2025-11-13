package com.example.BankSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:bank.db"; // Database in project root

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("✅ Connected to SQLite database.");
            return conn;
        } catch (SQLException e) {
            System.out.println("❌ Connection failed: " + e.getMessage());
            return null;
        }
    }
}
