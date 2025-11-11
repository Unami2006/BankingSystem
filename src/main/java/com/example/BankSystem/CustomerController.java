package com.example.BankSystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CustomerController {

    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private ChoiceBox<String> typeChoice;

    private static final String FILE_PATH = "customers.txt";

    @FXML
    private void saveCustomer() {
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String type = typeChoice.getValue();

        if (name.isEmpty() || id.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || type == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please fill in all fields before saving.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(name + "," + id + "," + email + "," + phone + "," + address + "," + type);
            writer.newLine();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer saved successfully!");
            clearFields();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not save customer data.");
        }
    }

    private void clearFields() {
        nameField.clear();
        idField.clear();
        emailField.clear();
        phoneField.clear();
        addressField.clear();
        typeChoice.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
