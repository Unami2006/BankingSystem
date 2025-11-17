package com.example.BankSystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Initialize database tables
        DatabaseSetup.initialize();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/BankSystem/MainApp.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setTitle("Bank System");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
