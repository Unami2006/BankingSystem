package com.example.BankSystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private void openCustomerView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/BankSystem/CustomerView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Customer Registration");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openAccountView() {
        openWindow("/com/example/BankSystem/AccountManagement.fxml", "Account Management");
    }

    @FXML
    private void openTransactionView() {
        openWindow("/com/example/BankSystem/TransactionView.fxml", "Transactions");
    }

    @FXML
    private void logout() {
        // For now, just close the dashboard
        Stage stage = (Stage) javafx.stage.Stage.getWindows().filtered(Window -> Window.isShowing()).get(0);
        stage.close();
    }

    /**
     * Utility method to open a new window given an FXML path and title
     */
    private void openWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
