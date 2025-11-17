package com.example.BankSystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class TransactionViewController {

    @FXML private TextField accountNumberField;
    @FXML private TextField amountField;
    @FXML private ChoiceBox<String> typeChoice;
    @FXML private TextField targetAccountField;
    @FXML private TextField descriptionField;
    @FXML private Label statusLabel;

    private String currentUserId = "1"; // default until login passes real ID

    @FXML
    public void initialize() {
        typeChoice.getItems().addAll("Deposit", "Withdrawal", "Transfer");
        typeChoice.setValue("Deposit"); // default
    }

    // 🔥 Must exist so MainController can pass user ID
    public void setUserId(String userId) {
        this.currentUserId = userId;
        System.out.println("TransactionViewController received user ID = " + userId);
    }

    @FXML
    private void handleProcessTransaction() {
        String accountId = accountNumberField.getText().trim();
        String type = typeChoice.getValue();
        String description = descriptionField.getText().trim();
        String target = targetAccountField.getText().trim();

        // Validate amount
        double amount;
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (Exception e) {
            statusLabel.setText("Invalid amount.");
            return;
        }

        // Validate account
        Account account = AccountDAO.getAccountById(accountId);
        if (account == null) {
            statusLabel.setText("Account not found.");
            return;
        }

        switch (type) {
            case "Deposit" -> {
                TransactionController.deposit(account, amount, currentUserId);
                statusLabel.setText("Deposit successful.");
            }
            case "Withdrawal" -> {
                boolean ok = TransactionController.withdraw(account, amount, currentUserId);
                if (ok) statusLabel.setText("Withdrawal successful.");
                else statusLabel.setText("Insufficient funds.");
            }
            case "Transfer" -> {
                Account targetAcc = AccountDAO.getAccountById(target);
                if (targetAcc == null) {
                    statusLabel.setText("Target account not found.");
                    return;
                }
                boolean ok = TransactionController.transfer(account, targetAcc, amount, currentUserId);
                if (ok) statusLabel.setText("Transfer successful.");
                else statusLabel.setText("Transfer failed.");
            }
        }
    }

    @FXML
    private void handleViewHistory() {
        List<Transaction> list = TransactionDAO.getTransactionsForUser(currentUserId);

        StringBuilder sb = new StringBuilder();
        for (Transaction t : list) {
            sb.append(t.getType()).append("  |  ")
                    .append(t.getAmount()).append("  |  ")
                    .append(t.getDescription()).append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Transaction History");
        alert.setHeaderText("Your Activity");
        alert.setContentText(sb.toString());
        alert.showAndWait();
    }

    @FXML
    private void handleBack() {
        // close window
        accountNumberField.getScene().getWindow().hide();
    }
}
