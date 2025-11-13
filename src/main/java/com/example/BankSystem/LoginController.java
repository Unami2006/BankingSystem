package com.example.BankSystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void login() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Login successful → open dashboard
                openDashboard();
            } else {
                showAlert("Error", "Invalid username or password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    @FXML
    private void handleClear() {
        usernameField.clear();
        passwordField.clear();
    }

    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/BankSystem/RegisterView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("User Registration");
            stage.setScene(new Scene(root, 400, 450));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/BankSystem/MainView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Bank Dashboard");
            stage.setScene(new Scene(root, 700, 480));
            stage.show();

            // Close login window
            Stage current = (Stage) usernameField.getScene().getWindow();
            current.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
