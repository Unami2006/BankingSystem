package com.example.BankSystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;

public class CustomerController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField nameField;

    private static final String FILE_PATH = "customers.txt";

    @FXML
    private void saveCustomer() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String name = nameField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please fill in all fields.");
            return;
        }

        if (isUsernameTaken(username)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Username already exists.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(username + "," + password + "," + name);
            writer.newLine();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer saved successfully!");
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
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File may not exist yet
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

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        nameField.clear();
    }
}
