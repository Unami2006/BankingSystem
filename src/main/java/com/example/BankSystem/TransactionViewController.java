package com.example.BankSystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.List;
import java.util.UUID;

public class TransactionViewController {

    @FXML private TextField accountNumberField;
    @FXML private TextField targetAccountField;
    @FXML private TextField amountField;
    @FXML private TextField descriptionField;
    @FXML private ChoiceBox<String> typeChoice;
    @FXML private Label statusLabel;

    @FXML
    private void initialize() {
        typeChoice.getItems().addAll("Deposit", "Withdrawal", "Transfer");
    }

    @FXML
    private void handleProcessTransaction() {
        String accNum = accountNumberField.getText().trim();
        String targetAcc = targetAccountField.getText().trim();
        String type = typeChoice.getValue();
        String desc = descriptionField.getText().trim();

        if (accNum.isEmpty() || type == null || amountField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Info", "Please fill in all required fields.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Amount must be numeric.");
            return;
        }

        boolean success = false;

        switch (type) {
            case "Deposit" -> success = TransactionController.deposit(new Account(accNum, "User", 0, "Savings"), amount);
            case "Withdrawal" -> success = TransactionController.withdraw(new Account(accNum, "User", 0, "Savings"), amount);
            case "Transfer" -> success = TransactionController.transfer(
                    new Account(accNum, "User", 0, "Savings"),
                    new Account(targetAcc, "Target", 0, "Savings"),
                    amount
            );
        }

        if (success) {
            statusLabel.setText("Transaction successful!");
            statusLabel.setStyle("-fx-text-fill: green;");
            clearFields();
        } else {
            statusLabel.setText("Transaction failed.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleViewHistory() {
        List<Transaction> transactions = TransactionDAO.loadTransactions();
        StringBuilder sb = new StringBuilder();
        for (Transaction tx : transactions) sb.append(tx).append("\n");

        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Transaction History");
        alert.setHeaderText("All Transactions");
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) accountNumberField.getScene().getWindow();
        stage.close();
    }

    private void clearFields() {
        accountNumberField.clear();
        amountField.clear();
        targetAccountField.clear();
        descriptionField.clear();
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
