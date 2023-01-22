package com.example.test_tcp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application{

   // public Client c;

    public HelloApplication() throws IOException {

     //   c = new Client();

    }

    /**
    *Loading the GUI for usage
     */

    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("booking.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    /**
     *Starts GUI and Client
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        launch(args);


            //HelloController thread = new HelloController();

            //thread.start();




    }

}