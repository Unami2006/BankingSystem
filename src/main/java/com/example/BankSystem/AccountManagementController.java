package com.example.BankSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.util.Scanner;

public class AccountManagementController {

    @FXML private TableView<Account> accountTable;
    @FXML private TableColumn<Account, String> accountIdColumn;
    @FXML private TableColumn<Account, String> accountNameColumn;
    @FXML private TableColumn<Account, Double> accountBalanceColumn;
    @FXML private Button addAccountButton;
    @FXML private Button deleteAccountButton;
    @FXML private Button depositButton;
    @FXML private Button withdrawButton;

    private final String FILE_NAME = "accounts.txt";
    private final ObservableList<Account> accounts = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        accountIdColumn.setCellValueFactory(cellData -> cellData.getValue().accountIdProperty());
        accountNameColumn.setCellValueFactory(cellData -> cellData.getValue().accountNameProperty());
        accountBalanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());

        loadAccountsFromFile();
        accountTable.setItems(accounts);

        addAccountButton.setOnAction(e -> showAddAccountForm());
        deleteAccountButton.setOnAction(e -> deleteSelectedAccount());
        depositButton.setOnAction(e -> handleDeposit());
        withdrawButton.setOnAction(e -> handleWithdraw());
    }

    private void loadAccountsFromFile() {
        accounts.clear();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 4) {
                    accounts.add(new Account(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveAccountsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Account account : accounts) {
                writer.println(account.getAccountId() + "," + account.getAccountName() + "," +
                        account.getBalance() + "," + account.getAccountType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAddAccountForm() {
        Dialog<Account> dialog = new Dialog<>();
        dialog.setTitle("Add New Account");
        dialog.setHeaderText("Enter Account Details");

        Label nameLabel = new Label("Account Name:");
        TextField nameField = new TextField();

        Label typeLabel = new Label("Account Type:");
        ChoiceBox<String> typeChoice = new ChoiceBox<>();
        typeChoice.getItems().addAll("Savings", "Cheque", "Investment");

        Label balanceLabel = new Label("Initial Balance:");
        TextField balanceField = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, nameLabel, nameField);
        grid.addRow(1, typeLabel, typeChoice);
        grid.addRow(2, balanceLabel, balanceField);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    String id = "A" + (accounts.size() + 1);
                    String name = nameField.getText().trim();
                    String type = typeChoice.getValue();
                    double balance = Double.parseDouble(balanceField.getText());
                    return new Account(id, name, balance, type);
                } catch (Exception e) {
                    showAlert("Error", "Invalid input. Please try again.");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(account -> {
            accounts.add(account);
            saveAccountsToFile();
        });
    }

    private void deleteSelectedAccount() {
        Account selected = accountTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            accounts.remove(selected);
            saveAccountsToFile();
        } else {
            showAlert("No Selection", "Please select an account to delete.");
        }
    }

    private void handleDeposit() {
        Account selected = accountTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select an account to deposit into.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit");
        dialog.setHeaderText("Deposit into " + selected.getAccountName());
        dialog.setContentText("Enter amount to deposit:");

        dialog.showAndWait().ifPresent(amountStr -> {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount > 0) {
                    selected.setBalance(selected.getBalance() + amount);
                    accountTable.refresh();
                    saveAccountsToFile();
                } else {
                    showAlert("Invalid Amount", "Amount must be positive.");
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid amount entered.");
            }
        });
    }

    private void handleWithdraw() {
        Account selected = accountTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select an account to withdraw from.");
            return;
        }

        String type = selected.getAccountType();
        if (!type.equalsIgnoreCase("Cheque") && !type.equalsIgnoreCase("Investment")) {
            showAlert("Not Allowed", "Withdrawals are only allowed for Cheque and Investment accounts.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Withdraw");
        dialog.setHeaderText("Withdraw from " + selected.getAccountName());
        dialog.setContentText("Enter amount to withdraw:");

        dialog.showAndWait().ifPresent(amountStr -> {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount > 0 && amount <= selected.getBalance()) {
                    selected.setBalance(selected.getBalance() - amount);
                    accountTable.refresh();
                    saveAccountsToFile();
                } else {
                    showAlert("Invalid Amount", "Amount must be positive and not exceed balance.");
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid amount entered.");
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
