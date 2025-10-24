package com.example.BankSystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button createAccountButton;

    @FXML
    private void login() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please enter both username and password.");
            return;
        }

        // Simple login validation (you can connect this to real data later)
        if (username.equals("admin") && password.equals("1234")) {
            openDashboard();
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    private void openDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/BankSystem/MainView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setTitle("Bank System Dashboard");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to open the Dashboard.");
        }
    }

    @FXML
    private void handleClear() {
        usernameField.clear();
        passwordField.clear();
        usernameField.requestFocus();
    }

    @FXML
    private void handleCreateAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/BankSystem/AccountManagement.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Create New Account");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to open account creation window.");
        }
    }
    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
