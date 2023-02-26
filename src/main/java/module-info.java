module com.example.interfazgrafica {
    requires javafx.controls;
    requires javafx.fxml;
    requires Java.WebSocket;


    opens com.example.interfazgrafica to javafx.fxml;
    exports com.example.interfazgrafica;
}