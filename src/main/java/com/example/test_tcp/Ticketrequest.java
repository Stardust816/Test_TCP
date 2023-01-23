package com.example.test_tcp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Application TicketRequest gets the necesary parameters to run with a gui
 * This Application is used to send requests to the server which includes Flightnumber and Passwort.
 * If the server has this infomrations stored, it will send back the Informations about the flight
 * This Client Application runs on port 1288 which is startet in the ServerHandler Application as Server 2
 */
public class Ticketrequest extends Application {
    // ObservableList<String> LogServer1 = FXCollections.<String>emptyObservableList();  wurde nicht implimentiert
    public Client c;

    public Ticketrequest() throws IOException {

        c = new Client(1287);

    }

    public void start(Stage stage) throws IOException {

        VBox outerVBox = new VBox();
        outerVBox.setPadding(new Insets(10));
        outerVBox.setSpacing(10);
        outerVBox.setPrefHeight(1000);
        outerVBox.setPrefWidth(800);
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

        VBox innerVBox = new VBox();


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
            String msg = fligntNumber.getText();
            String pass = PassPort.getText();

            String seating = null;
            String flight = null;

            String flugnummer = null;
            String airplane = null;
            String dep = null;
            String arrival = null;
            String src = null;
            String dest = null;
            String name = null;
            String sitz = null;
            String passnummer = null;
            String[] selectedflight = null;
            String[] allflights = null;


            String[] platzliste = null;
            String[] sitzplatz = null;
            if (!(msg.isEmpty()) || !(pass.isEmpty())) {
                seating = c.sendMessage("999;GET;flightnumber;" + msg);
                System.out.println("Seating feedback: " + seating);
                flight = c.sendMessage("999;GET;flightnumber");

                System.out.println("Flight feedback: " + flight);

                platzliste = seating.split(";");
                for (int i = 0; i < platzliste.length; i++) {
                    if (platzliste[i].contains(pass)) {
                        sitzplatz = platzliste[i].split("#");
                        sitz = "Reihe: " + sitzplatz[0] + "Spalte: " + sitzplatz[1];
                        name = sitzplatz[2];
                        passnummer = sitzplatz[3];
                        i = platzliste.length;
                        //Abfrage vom Flug
                        allflights = flight.split(";");
                        for (int a = 0; a < allflights.length; a++) {
                            if (allflights[a].startsWith(msg)) {
                                selectedflight = allflights[a].split("#");
                                flugnummer = selectedflight[0];
                                airplane = selectedflight[1];
                                dep = selectedflight[2];
                                arrival = selectedflight[3];
                                src = selectedflight[4];
                                dest = selectedflight[5];
                                a = allflights.length;
                            }
                        }


                    } else {
                        sitz = "nodata";
                        name = "nodata";
                        passnummer = "nodata";
                        flugnummer = "nodata";
                        airplane = "nodata";
                        dep = "nodata";
                        arrival = "nodata";
                        src = "nodata";
                        dest = "nodata";
                    }
                }
                NA.setText("Name: " + name);
                PA.setText("Passport: " + passnummer);
                SI.setText(sitz);
                FN.setText("Flugnummer: " + flugnummer);
                AP.setText("Flugzeug: " + airplane);
                DE.setText(dep);
                AR.setText(arrival);
                SR.setText(src);
                DS.setText(dest);


            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Flugnummer und Passport eingeben", ButtonType.OK);
                alert.show();

            }


        });

        outerVBox.getChildren().addAll(fligntNumber, PassPort, NA, PA, FN, SI, AP, DE, AR, SR, DS, borderPane);
        borderPane.setLeft(innerVBox);
        innerVBox.getChildren().addAll(fligntNumber, PassPort, btnTransmit, NA, PA, FN, SI, AP, DE, AR, SR, DS);
        Scene scene = new Scene(outerVBox, 500, 500);

        stage.setTitle("TicketRequest");
        stage.setScene(scene);

        stage.show();
    }

}