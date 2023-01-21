package com.example.test_tcp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerHandler extends Application {

 public  Server1 s1;

    public ServerHandler() throws IOException {
    //Thread Server1 = new Thread();

    //Thread Server2 = new Thread();
    //Thread Server3 = new Thread();


      //  s2 = new Server1(1287);
Runnable ST1 = new ServerThread();
Thread th1 = new Thread(ST1);
th1.start();

    }

    public class ServerThread extends Thread{
        public void run(){

            try {
                s1 = new Server1(1287);
                System.out.println("Thread S1 gestartet");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





    public void start(Stage stage) throws IOException {

        VBox outerVBox = new VBox();
        outerVBox.setPadding(new Insets(10));
        outerVBox.setSpacing(10);
        outerVBox.setPrefHeight(1000);
        outerVBox.setPrefWidth(800);
        //outerVBox.se(1800,10000);
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefHeight(1000);
        borderPane.setPrefWidth(800);
        borderPane.setLayoutX(1000);
        borderPane.setLayoutY(800);

        TextArea NA = new TextArea();
        NA.setPromptText("Passagier");
        NA.setMaxHeight(4);
        TextArea PA = new TextArea();
        PA.setPromptText("PassportNumber");
        PA.setMaxHeight(4);
        TextArea FN = new TextArea();
        FN.setPromptText("Flugnummer");
        FN.setMaxHeight(4);
        TextArea SI = new TextArea();
        SI.setPromptText("Sitzplatz");
        SI.setMaxHeight(4);
        TextArea AP = new TextArea();
        AP.setPromptText("Airplane");
        AP.setMaxHeight(4);
        TextArea DE = new TextArea();
        DE.setPromptText("Departure");
        DE.setMaxHeight(4);
        TextArea AR = new TextArea();
        AR.setPromptText("Arrival");
        AR.setMaxHeight(4);
        TextArea SR = new TextArea();
        SR.setPromptText("Source");
        SR.setMaxHeight(4);
        TextArea DS = new TextArea();
        DS.setPromptText("Destination");
        DS.setMaxHeight(4);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 0));
        vbox.setPrefWidth(800);
        vbox.setPrefHeight(1000);
        //Scene scene = new Scene(outerVBox);
        //BorderPane borderPane = new BorderPane();
        VBox innerVBox = new VBox();
        //ListView<String> lvbag = new ListView<>();

        TextField fligntNumber = new TextField();
        fligntNumber.setPromptText("Flugnummer");

        TextField PassPort = new TextField();
        PassPort.setPromptText("PassPort");

        Button btnTransmit = new Button("Request");
        btnTransmit.setPrefWidth(200);

        outerVBox.setSpacing(10);
        outerVBox.setPadding(new Insets(10));
        innerVBox.setPadding(new Insets(5));

        btnTransmit.setOnAction(event -> {
            System.out.println("bin im Button");
            try {
                s1.SetHandler("flightnumber","all");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //NA.setText(s1.GetLogRead().toString());
        });

        outerVBox.getChildren().addAll(fligntNumber,PassPort,NA,PA, FN,SI,AP,DE,AR,SR,DS, borderPane);
        borderPane.setLeft(innerVBox);
        innerVBox.getChildren().addAll(fligntNumber,PassPort,btnTransmit,NA,PA,FN,SI,AP,DE,AR,SR,DS);
        Scene scene = new Scene(outerVBox, 500, 500);

        stage.setTitle("ServerHandler");
        stage.setScene(scene);

        stage.show();
    }

}