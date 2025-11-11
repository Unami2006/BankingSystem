package com.example.BankSystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;

public class CustomerRegistrationController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private static final String FILE_PATH = "registered.txt";

    @FXML
    private void saveCustomer() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Check if fields are empty
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()
                || username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please fill in all fields.");
            return;
        }

        // Prevent duplicate username
        if (isUsernameTaken(username)) {
            showAlert(Alert.AlertType.ERROR, "Duplicate Username", "This username already exists.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(name + "," + email + "," + phone + "," + address + "," + username + "," + password);
            writer.newLine();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer registered successfully!");
            clearFields();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not save customer data.");
        }
    }

    private boolean isUsernameTaken(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 4 && parts[4].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File may not exist yet — ignore
        }
        return false;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void clearFields() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        addressField.clear();
        usernameField.clear();
        passwordField.clear();
    }
}
