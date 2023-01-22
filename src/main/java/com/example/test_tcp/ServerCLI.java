package com.example.test_tcp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * ServerCLI is control the complet function of the Server
 * for example Adding a flight
 * 666;SET;flightnumber;add,OS878#privjet#Dep:23.1.2022-15:20#Arr:24.1.2022-05:00#Src:Linz#Dest:Wien
 * <p>
 * At the moment it is possible to do this over the CLI which sends the request to the Server
 * The plan was, to extend this application which makes a GUI to make this funktion more User friendly.
 * This is not finished
 */
public class ServerCLI extends Application {

    private Client c;

    public ServerCLI() throws IOException {

        c = new Client(1288);


    }

    /**
     * Starts the GUI
     *
     * @param stage
     * @throws IOException
     */
    public void start(Stage stage) throws IOException {

        VBox outerVBox = new VBox();
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
        Scene scene = new Scene(outerVBox, 600, 200);
        stage.setTitle("Transmitter");
        stage.setScene(scene);

        stage.show();
    }

}
    
    
    
    

