package com.example.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server extends Application {
    @Override
    public void start (Stage primaryStage){
        TextArea ta = new TextArea();

        Scene scene = new Scene(new ScrollPane(ta),450,200);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8000);
                Platform.runLater(() -> ta.appendText("Server started at " + new Date() + '\n'));

                Socket socket = serverSocket.accept();

                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                while(true){
                    double radius = inputFromClient.readDouble();

                    double area = radius * radius * Math.PI;

                    outputToClient.writeDouble(area);

                    Platform.runLater(() -> {
                        ta.appendText("Radius received from client: " + radius + '\n');
                    });

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        }).start();
    }

}
