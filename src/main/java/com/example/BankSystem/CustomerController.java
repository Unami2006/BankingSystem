package com.example.BankSystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CustomerController {

    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private ChoiceBox<String> typeChoice;

    @FXML
    private void saveCustomer() {
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String type = typeChoice.getValue();

        if (name.isEmpty() || id.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || type == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please fill out all fields before saving.");
            return;
        }

        String message = String.format("""
                Customer Registered Successfully:
                Type: %s
                Name: %s
                ID/Passport: %s
                Email: %s
                Phone: %s
                Address: %s
                """, type, name, id, email, phone, address);

        showAlert(Alert.AlertType.INFORMATION, "Registration Complete", message);
        clearFields();
    }

    private void clearFields() {
        nameField.clear();
        idField.clear();
        emailField.clear();
        phoneField.clear();
        addressField.clear();
        typeChoice.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
