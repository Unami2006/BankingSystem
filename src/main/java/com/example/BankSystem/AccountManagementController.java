package com.example.BankSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AccountManagementController {

    @FXML
    private TableView<Account> accountTable;

    @FXML
    private TableColumn<Account, Integer> accountIdColumn;

    @FXML
    private TableColumn<Account, String> accountNameColumn;

    @FXML
    private TableColumn<Account, Double> accountBalanceColumn;

    private final ObservableList<Account> accountList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Link TableColumns with Account properties
        accountIdColumn.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        accountNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        accountBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        // Load initial data if needed
        accountTable.setItems(accountList);
    }

    public void addAccount(Account account) {
        accountList.add(account);
    }

    public void removeAccount(Account account) {
        accountList.remove(account);
    }
}
