package com.example.BankSystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

    private int loggedUserId;
    private String loggedUserName;

    public void setUser(int userId, String name) {
        this.loggedUserId = userId;
        this.loggedUserName = name;
    }

    // Open Customer Registration Window
    @FXML
    private void openCustomerView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/BankSystem/CustomerRegistration.fxml"));
            Parent root = loader.load();

            // Optionally pass logged-in user ID if needed
            CustomerRegistrationController controller = loader.getController();
            // controller.setUserId(String.valueOf(loggedUserId)); // Uncomment if you want to use user ID

            Stage stage = new Stage();
            stage.setTitle("Customer Registration");
            stage.setScene(new Scene(root, 500, 500)); // Set preferred size
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openAccountView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/BankSystem/AccountManagement.fxml"));
            Parent root = loader.load();

            AccountManagementController controller = loader.getController();
            controller.setUserId(String.valueOf(loggedUserId));

            Stage stage = new Stage();
            stage.setTitle("Account Management");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openTransactionView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/BankSystem/TransactionView.fxml"));
            Parent root = loader.load();

            TransactionViewController controller = loader.getController();
            controller.setUserId(String.valueOf(loggedUserId));

            Stage stage = new Stage();
            stage.setTitle("Transaction History");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout() {
        // Close current window and return to login
        Stage stage = (Stage) Stage.getWindows().filtered(Window -> Window.isShowing()).get(0);
        stage.close();
    }
}
