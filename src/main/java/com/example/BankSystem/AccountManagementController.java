package com.example.BankSystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

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

    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
        loadAccounts();
    }

    @FXML
    public void initialize() {
        accountIdColumn.setCellValueFactory(cell -> cell.getValue().accountIdProperty());
        accountNameColumn.setCellValueFactory(cell -> cell.getValue().accountNameProperty());
        accountBalanceColumn.setCellValueFactory(cell -> cell.getValue().balanceProperty().asObject());
        accountTypeColumn.setCellValueFactory(cell -> cell.getValue().accountTypeProperty());

        addAccountButton.setOnAction(e -> addAccount());
        deleteAccountButton.setOnAction(e -> deleteAccount());
        depositButton.setOnAction(e -> deposit());
        withdrawButton.setOnAction(e -> withdraw());
        applyInterestButton.setOnAction(e -> applyInterest());
    }

    private void loadAccounts() {
        accountTable.getItems().setAll(AccountDAO.loadAccountsForUser(userId));
    }

    // ----------------------- ADD ACCOUNT ----------------------------
    private void addAccount() {

        Dialog<Account> dialog = new Dialog<>();
        dialog.setTitle("Add Account");

        Label nameLabel = new Label("Account Name:");
        TextField nameField = new TextField();

        Label typeLabel = new Label("Type:");
        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Savings", "Investment", "Cheque");
        typeCombo.setValue("Savings");

        Label balanceLabel = new Label("Initial Balance:");
        TextField balanceField = new TextField();

        // Layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(typeLabel, 0, 1);
        grid.add(typeCombo, 1, 1);
        grid.add(balanceLabel, 0, 2);
        grid.add(balanceField, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    String name = nameField.getText();
                    String type = typeCombo.getValue();
                    double balance = Double.parseDouble(balanceField.getText());
                    String id = AccountDAO.generateAccountId();

                    switch (type) {
                        case "Savings":
                            return new SavingsAccount(id, name, balance, userId);
                        case "Investment":
                            return new InvestmentAccount(id, name, balance, userId);
                        case "Cheque":
                            return new ChequeAccount(id, name, balance, userId, "None"); // Default
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(account -> {
            AccountDAO.saveAccount(account);
            loadAccounts();
        });
    }

    // ----------------------- DELETE ACCOUNT ----------------------------
    private void deleteAccount() {
        Account selected = accountTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        AccountDAO.deleteAccount(selected.getAccountId());
        loadAccounts();
    }

    // ----------------------- DEPOSIT ----------------------------
    private void deposit() {
        Account selected = accountTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter deposit amount:");

        dialog.showAndWait().ifPresent(amountStr -> {
            try {
                double amount = Double.parseDouble(amountStr);
                TransactionController.deposit(selected, amount, userId);
                loadAccounts();
            } catch (Exception ignored) {}
        });
    }

    // ----------------------- WITHDRAW ----------------------------
    private void withdraw() {
        Account selected = accountTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter withdrawal amount:");

        dialog.showAndWait().ifPresent(amountStr -> {
            try {
                double amount = Double.parseDouble(amountStr);

                if (!TransactionController.withdraw(selected, amount, userId)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Insufficient funds!");
                    alert.show();
                }

                loadAccounts();

            } catch (Exception ignored) {}
        });
    }

    // ----------------------- APPLY INTEREST ----------------------------
    private void applyInterest() {
        Account selected = accountTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (!selected.getAccountType().equalsIgnoreCase("Investment")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Interest applies to Investment accounts only.");
            alert.show();
            return;
        }

        double oldBalance = selected.getBalance();
        double newBalance = oldBalance * 1.05;

        selected.setBalance(newBalance);
        AccountDAO.updateBalance(selected.getAccountId(), newBalance);

        Transaction tx = new Transaction(
                TransactionDAO.generateTransactionId(),
                selected.getAccountId(),
                "Interest Added",
                newBalance - oldBalance,
                "5% Interest Applied",
                "",
                userId
        );

        TransactionDAO.saveTransaction(tx);

        loadAccounts();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "5% interest added successfully!");
        alert.show();
    }
}
