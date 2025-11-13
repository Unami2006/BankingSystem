package com.example.BankSystem;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    public static void initialize() {
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {

            // Users table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    username TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    email TEXT NOT NULL,
                    account_type TEXT NOT NULL
                )
            """);

            // Accounts table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS accounts (
                    accountId TEXT PRIMARY KEY,
                    accountName TEXT NOT NULL,
                    balance REAL NOT NULL,
                    accountType TEXT NOT NULL
                )
            """);

            // Transactions table (correct schema)
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transactions (
                    transactionId INTEGER PRIMARY KEY AUTOINCREMENT,
                    accountId TEXT,
                    type TEXT,
                    amount REAL,
                    targetAccount TEXT,
                    description TEXT,
                    timestamp TEXT
                )
            """);

            System.out.println("✅ Database tables created or verified.");
        } catch (Exception e) {
            System.out.println("❌ Error creating tables: " + e.getMessage());
        }
    }
}
