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

public class HelloApplication extends Application {

   // public Client c;

    public HelloApplication() throws IOException {

     //   c = new Client();

    }

    public void start(Stage stage) throws IOException {

        /*VBox outerVBox = new VBox();
        outerVBox.setPadding(new Insets(10));
        outerVBox.setSpacing(10);
        BorderPane borderPane = new BorderPane();

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 0));

        //Scene scene = new Scene(outerVBox);
        //BorderPane borderPane = new BorderPane();
        VBox innerVBox = new VBox();
        //ListView<String> lvbag = new ListView<>();

        TextField txtMessage = new TextField();
        txtMessage.setPromptText("Enter Message");

        TextField txtReload = new TextField();
        txtReload = new TextField();

        Button btnTransmit = new Button("Transmit");
        btnTransmit.setPrefWidth(400);

        outerVBox.setSpacing(10);
        outerVBox.setPadding(new Insets(10));
        innerVBox.setPadding(new Insets(5));

        btnTransmit.setOnAction(event -> {
            String msg = txtMessage.getText();
            c.sendMessage(msg);

        });

        outerVBox.getChildren().addAll(txtMessage, borderPane);
        borderPane.setLeft(innerVBox);
        innerVBox.getChildren().addAll(txtMessage, btnTransmit);
        Scene scene = new Scene(outerVBox, 300, 300);
        stage.setTitle("Transmitter");
        stage.setScene(scene);

        stage.show();*/

        Parent root = FXMLLoader.load(getClass().getResource("booking.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        launch(args);


    }

}