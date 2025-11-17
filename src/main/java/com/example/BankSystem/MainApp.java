package com.example.BankSystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize DB (creates tables if missing)
        DatabaseSetup.initialize();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/BankSystem/LoginView.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Banking System - Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
