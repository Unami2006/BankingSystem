package com.example.BankSystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField emailField;
    @FXML private ChoiceBox<String> accountTypeChoice;

    @FXML
    private void initialize() {
        accountTypeChoice.getItems().addAll("Savings", "Cheque", "Investment");
    }

    @FXML
    private void handleRegister() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String email = emailField.getText().trim();
        String accountType = accountTypeChoice.getValue();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || accountType == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please fill in all fields.");
            return;
        }

        try {
            java.io.File file = new java.io.File("users.txt");
            if (!file.exists()) file.createNewFile();

            // Check if username already exists
            java.util.Scanner scanner = new java.util.Scanner(file);
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length > 1 && parts[0].equalsIgnoreCase(username)) {
                    showAlert(Alert.AlertType.ERROR, "Registration Failed", "Username already exists.");
                    scanner.close();
                    return;
                }
            }
            scanner.close();

            // Save new user
            java.io.FileWriter writer = new java.io.FileWriter(file, true);
            writer.write(username + "," + password + "," + name + "," + email + "," + accountType + "\n");
            writer.close();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Account successfully created for " + name);

            // Close current stage after registration
            closeStage();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not save user data.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeStage() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
