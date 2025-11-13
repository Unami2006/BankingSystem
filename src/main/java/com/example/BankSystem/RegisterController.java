package com.example.BankSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField emailField;
    @FXML private ChoiceBox<String> accountTypeChoice;
    @FXML private Label messageLabel;

    // ✅ Handle Registration Button
    @FXML
    private void handleRegister(ActionEvent event) {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String email = emailField.getText().trim();
        String accountType = accountTypeChoice.getValue();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || accountType == null) {
            messageLabel.setText("Please fill in all fields!");
            return;
        }

        // Insert user into database
        String sql = "INSERT INTO users (name, username, password, email, account_type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, email);
            pstmt.setString(5, accountType);

            pstmt.executeUpdate();
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Registration successful!");

            // Clear fields
            nameField.clear();
            usernameField.clear();
            passwordField.clear();
            emailField.clear();
            accountTypeChoice.setValue(null);

        } catch (SQLException e) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ Handle Back to Login Button
    @FXML
    private void handleBackToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/BankSystem/LoginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Banking System - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
