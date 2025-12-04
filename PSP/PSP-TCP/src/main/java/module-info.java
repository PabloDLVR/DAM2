module com.example.psptcp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.psptcp to javafx.fxml;
    exports com.example.psptcp;
}