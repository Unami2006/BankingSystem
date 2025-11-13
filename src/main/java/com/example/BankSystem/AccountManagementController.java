package com.example.BankSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class AccountManagementController {

    @FXML private TableView<Account> accountTable;
    @FXML private TableColumn<Account, String> accountIdColumn;
    @FXML private TableColumn<Account, String> accountNameColumn;
    @FXML private TableColumn<Account, Double> accountBalanceColumn;
    @FXML private TableColumn<Account, String> accountTypeColumn;

    @FXML private Button addAccountButton;
    @FXML private Button deleteAccountButton;
    @FXML private Button depositButton;
    @FXML private Button withdrawButton;
    @FXML private Button applyInterestButton;

    private final ObservableList<Account> accounts = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        accountIdColumn.setCellValueFactory(cellData -> cellData.getValue().accountIdProperty());
        accountNameColumn.setCellValueFactory(cellData -> cellData.getValue().accountNameProperty());
        accountBalanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
        accountTypeColumn.setCellValueFactory(cellData -> cellData.getValue().accountTypeProperty());

        // Color styling by account type
        accountTypeColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String type, boolean empty) {
                super.updateItem(type, empty);
                if (empty || type == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(type);
                    switch (type.toLowerCase()) {
                        case "savings" -> setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                        case "cheque" -> setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
                        case "investment" -> setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                        default -> setStyle("-fx-text-fill: black;");
                    }
                }
            }
        });

        loadAccounts();

        addAccountButton.setOnAction(e -> showAddAccountForm());
        deleteAccountButton.setOnAction(e -> deleteSelectedAccount());
        depositButton.setOnAction(e -> handleDeposit());
        withdrawButton.setOnAction(e -> handleWithdraw());
        applyInterestButton.setOnAction(e -> handleApplyInterest());
    }

    private void loadAccounts() {
        accounts.setAll(AccountDAO.loadAccounts());
        accountTable.setItems(accounts);
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
                    String id = "A" + System.currentTimeMillis();
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
            AccountDAO.saveAccount(account);
            loadAccounts();
        });
    }

    private void deleteSelectedAccount() {
        Account selected = accountTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            AccountDAO.deleteAccount(selected.getAccountId());
            loadAccounts();
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
                    AccountDAO.saveAccount(selected);
                    loadAccounts();
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

        if (!selected.getAccountType().equalsIgnoreCase("Cheque") &&
                !selected.getAccountType().equalsIgnoreCase("Investment")) {
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
                    AccountDAO.saveAccount(selected);
                    loadAccounts();
                } else {
                    showAlert("Invalid Amount", "Amount must be positive and not exceed balance.");
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid amount entered.");
            }
        });
    }

    private void handleApplyInterest() {
        boolean anyUpdated = false;
        for (Account acc : accounts) {
            if (acc.getAccountType().equalsIgnoreCase("Investment")) {
                double oldBalance = acc.getBalance();
                double interest = oldBalance * 0.05;
                acc.setBalance(oldBalance + interest);
                AccountDAO.saveAccount(acc);
                anyUpdated = true;
            }
        }

        if (anyUpdated) {
            loadAccounts();
            showAlert("Success", "Interest applied to all Investment Accounts (5%).");
        } else {
            showAlert("Info", "No Investment Accounts found.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
