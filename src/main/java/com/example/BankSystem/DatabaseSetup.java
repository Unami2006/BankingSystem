package com.example.BankSystem;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    public static void initialize() {
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {

            // Users table (FINAL)
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    username TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    email TEXT NOT NULL,
                    account_type TEXT
                )
            """);

            // Accounts table (FINAL)
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS accounts (
                    accountId TEXT PRIMARY KEY,
                    accountName TEXT NOT NULL,
                    balance REAL NOT NULL,
                    accountType TEXT NOT NULL,
                    userId TEXT NOT NULL
                )
            """);

            // Transactions table (FINAL FIX — TEXT transactionId)
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transactions (
                    transactionId TEXT PRIMARY KEY,
                    accountId TEXT NOT NULL,
                    type TEXT NOT NULL,
                    amount REAL NOT NULL,
                    description TEXT,
                    targetAccount TEXT,
                    userId TEXT NOT NULL,
                    timestamp TEXT
                )
            """);

            System.out.println("✅ Database tables created or verified (correct schema).");

        } catch (Exception e) {
            System.out.println("❌ Error creating tables: " + e.getMessage());
        }
    }
}
