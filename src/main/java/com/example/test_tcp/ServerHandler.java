package com.example.test_tcp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerHandler extends Application {

 public Server1 s1;
 public Server1 s2;
 public Server1 s3;
 public Runnable ST1 = new ServerThread1();
 public Runnable ST2 = new ServerThread2();
 public Runnable ST3 = new ServerThread3();
 public Thread th1 = new Thread(ST1);
 public Thread th2 = new Thread(ST2);
 public Thread th3 = new Thread(ST3);

 public ServerHandler() throws IOException {
    //Thread Server1 = new Thread();

    //Thread Server2 = new Thread();
    //Thread Server3 = new Thread();

   //  th3.start();
     //c = new Client(1287);
     //   s2 = new Server1(1288);

    // th1.start();
    // th2.start();
    // th3.start();


    }

    public class ServerThread1 extends Thread{
        public void run(){

            try {
                s1 = new Server1(1286);
                System.out.println("Server 1286 getsartet");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class ServerThread2 extends Thread{
        public void run(){

            try {
                s2 = new Server1(1287);
                System.out.println("Server 1288 getsartet");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class ServerThread3 extends Thread{
        public void run(){

            try {
                s3 = new Server1(1288);
                System.out.println("Server 1288 gestartet");
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

    /*    TextArea NA = new TextArea();
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
*/
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 0));
        vbox.setPrefWidth(800);
        vbox.setPrefHeight(1000);

        VBox innerVBox = new VBox();


        TextField fligntNumber = new TextField();
        fligntNumber.setPromptText("Flugnummer");

        TextField PassPort = new TextField();
        PassPort.setPromptText("PassPort");

        Button btnStartS1 = new Button("Start S1");
        Button btnStartS2 = new Button("Start S2");
        Button btnStartS3 = new Button("Start S3");
        Button btnStopS1 = new Button("Stop S1");
        Button btnStopS2 = new Button("Stop S2");
        Button btnStopS3 = new Button("Stop S3");



        btnStartS1.setPrefWidth(200);
        btnStartS2.setPrefWidth(200);
        btnStartS3.setPrefWidth(200);
        btnStopS1.setPrefWidth(200);
        btnStopS2.setPrefWidth(200);
        btnStopS3.setPrefWidth(200);

        btnStopS1.setVisible(false);
        btnStopS2.setVisible(false);
        btnStopS3.setVisible(false);
        outerVBox.setSpacing(10);
        outerVBox.setPadding(new Insets(10));
        innerVBox.setPadding(new Insets(5));

        btnStartS1.setOnAction(event -> {

            th1.start();
            btnStopS1.setVisible(true);
            btnStartS1.setVisible((false));
            Alert alert = new Alert(Alert.AlertType.WARNING, "Server1 wurde gestartet", ButtonType.OK);
            alert.show();

        });

        btnStartS2.setOnAction(event -> {
            th2.start();
            btnStopS2.setVisible(true);
            btnStartS2.setVisible((false));
            Alert alert = new Alert(Alert.AlertType.WARNING, "Server2 wurde gestartet", ButtonType.OK);
            alert.show();

        });

        btnStartS3.setOnAction(event -> {
            th3.start();
            btnStopS3.setVisible(true);
            btnStartS3.setVisible((false));
            Alert alert = new Alert(Alert.AlertType.WARNING, "Server3 wurde gestartet", ButtonType.OK);
            alert.show();


        });

        btnStopS1.setOnAction(event -> {
            th1.stop();
            btnStopS1.setVisible(false);
            btnStartS1.setVisible((true));
            Alert alert = new Alert(Alert.AlertType.WARNING, "Server1 wurde gestoppt", ButtonType.OK);
            alert.show();

        });

        btnStopS2.setOnAction(event -> {
            th2.stop();
            btnStopS2.setVisible(false);
            btnStartS2.setVisible((true));
            Alert alert = new Alert(Alert.AlertType.WARNING, "Server2 wurde gestoppt", ButtonType.OK);
            alert.show();

        });
        btnStopS3.setOnAction(event -> {
            th3.stop();
            btnStopS3.setVisible(false);
            btnStartS3.setVisible((true));
            Alert alert = new Alert(Alert.AlertType.WARNING, "Server3 wurde gestoppt", ButtonType.OK);
            alert.show();

        });




   outerVBox.getChildren().addAll(btnStartS1,btnStopS1,btnStartS2,btnStopS2,btnStartS3,btnStopS3, borderPane);
        borderPane.setLeft(innerVBox);
        innerVBox.getChildren().addAll(btnStartS1,btnStopS1,btnStartS2,btnStopS2,btnStartS3,btnStopS3);
        Scene scene = new Scene(outerVBox, 500, 500);

        stage.setTitle("ServerHandler");
        stage.setScene(scene);

        stage.show();
    }

}