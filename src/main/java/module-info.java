module com.example.BankSystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.BankSystem to javafx.fxml;
    exports com.example.BankSystem;
}
