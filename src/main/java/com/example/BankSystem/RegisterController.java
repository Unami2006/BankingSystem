package com.example.BankSystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField emailField;
    @FXML private ChoiceBox<String> accountTypeChoice;

    @FXML
    private void handleRegister() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String accountType = accountTypeChoice.getValue();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || accountType == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please fill in all fields.");
            return;
        }

        // TODO: Save new user + account to DB (via DAO later)
        showAlert(Alert.AlertType.INFORMATION, "Success", "Account successfully created for " + name + " (" + accountType + " Account)");

        // Close window after successful registration
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
