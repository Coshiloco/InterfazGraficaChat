package com.example.interfazgrafica;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class IRCClient extends Application {

    private WebSocketClient client;
    private TextArea chatArea;
    private TextField inputField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        chatArea = new TextArea();
        chatArea.setEditable(false);
        inputField = new TextField();

        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> {
            String message = inputField.getText();
            client.send(message);
            inputField.clear();
        });

        VBox root = new VBox();
        root.getChildren().addAll(chatArea, inputField, sendButton);

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        connectToServer();
    }

    private void connectToServer() {
        try {
            URI serverUri = new URI("ws://localhost:8080/chat");
            client = new WebSocketClient(serverUri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    chatArea.appendText("Connected to server\n");
                }

                @Override
                public void onMessage(String message) {
                    chatArea.appendText(message + "\n");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    chatArea.appendText("Disconnected from server\n");
                }

                @Override
                public void onError(Exception ex) {
                    chatArea.appendText("Error: " + ex.getMessage() + "\n");
                }
            };
            client.connect();
        } catch (Exception e) {
            chatArea.appendText("Error connecting to server: " + e.getMessage() + "\n");
        }
    }
}
