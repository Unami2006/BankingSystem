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

    /*@FXML
    private void initialize() {
        accountTypeChoice.getItems().addAll("Savings", "Cheque", "Investment");
    }*/

    @FXML
    private void handleRegister() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String email = emailField.getText().trim();
        String accountType = accountTypeChoice.getValue();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || accountType == null) {
            showAlert("Missing Fields", "Please fill in all fields.");
            return;
        }

        User user = new User(0, name, username, email, accountType);
        UserDAO.saveUser(user, password);

        showAlert("Success", "Account created. You can now log in.");
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleBackToLogin() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
