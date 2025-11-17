package com.example.BankSystem;

public class LoginSession {
    private static String loggedUserId;

    public static void setLoggedUserId(String id) {
        loggedUserId = id;
    }

    public static String getLoggedUserId() {
        return loggedUserId;
    }
}
