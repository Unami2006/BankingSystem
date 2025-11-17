package com.example.BankSystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void login() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                // CREATE A USER OBJECT — IMPORTANT!
                User loggedInUser = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("account_type")
                );

                // Call the dashboard and pass the user
                openDashboard(loggedInUser);

            } else {
                showAlert("Error", "Invalid username or password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Database error.");
        }
    }

    private void openDashboard(User loggedInUser) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/BankSystem/MainView.fxml"));
            Parent root = loader.load();

            MainController controller = loader.getController();
            controller.setUser(loggedInUser.getId(), loggedInUser.getName());

            Stage stage = new Stage();
            stage.setTitle("Bank Dashboard");
            stage.setScene(new Scene(root, 700, 480));
            stage.show();

            // close login
            Stage current = (Stage) usernameField.getScene().getWindow();
            current.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClear() {
        usernameField.clear();
        passwordField.clear();
    }

    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/BankSystem/RegisterView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("User Registration");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
}
