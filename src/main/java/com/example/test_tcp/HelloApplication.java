package com.example.test_tcp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public HelloApplication() throws IOException {

    }

    /**
     * Starts GUI and Client
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        launch(args);

    }

    /**
     * Loading the GUI for usage
     */

    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("booking.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

}