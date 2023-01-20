package com.example.test_tcp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.System.out;

public class HelloController implements Initializable {

    String host = "localhost";
    int port = 1235;
    ObservableList listFlightnumber = FXCollections.observableArrayList();
    ObservableList listAirplane = FXCollections.observableArrayList();

    ObservableList listSrc = FXCollections.observableArrayList();

    ObservableList listTime = FXCollections.observableArrayList();

    ObservableList seatempty = FXCollections.observableArrayList();

    ObservableList bookedseat = FXCollections.observableArrayList();

    String seating;




    @FXML
    private Button btnTransmit;

    @FXML
    private Button loadStrings;



    @FXML
    private TextField txtMessage;

    @FXML
    private ComboBox flightNumber;

    @FXML
    private ComboBox airplane;

    @FXML
    private ComboBox Time;

    @FXML
    private ComboBox Time2;

    @FXML
    private ComboBox Src;



    //----------------------------------------------------------

    @FXML
    private TextField timearr;

    @FXML
    private TextField timedep;

    @FXML
    private TextField AR;

    @FXML
    private TextField DE;

    @FXML
    private TextField airp;

    @FXML
    private ListView emptySeats;

    @FXML
    private ListView bookedSeats;

    //@FXML
    //private Button book;

    //@FXML
    //private Button del;







//-------------------------------------------------------





    @FXML
    private ComboBox DST;

    @FXML
    private Button btn_Transmit;

    private Client c;

    public HelloController() throws IOException {
        c = new Client(1287);
    }

    //FlightNumberChanged Combobox befüllen***
    @FXML
    private void onFlightNumberChanged(ActionEvent event) throws IOException {
        String flugn = c.sendMessage("666;GET;flightnumber;all");



        if (flugn.isEmpty()){
            return;
        }

        String flug = flightNumber.getSelectionModel().getSelectedItem().toString();
        String airpl = null;
        String arrival = null;
        String departure = null;
        String source = null;
        String destination = null;
//System.out.println("Belegung  direkt:" + belegung);
      //  String response = c.response;
        //String[] antwort = response.split(";");
        String[] antwort = flugn.split(";");
        for(int i = 0; i<antwort.length; i++){

            System.out.println("+++++++++");
            System.out.println(antwort[i]);
            System.out.println("+++++++++");

            System.out.println("Antwort aus Zaehlschleife: " + antwort[i]);

            String nummer = flightNumber.getSelectionModel().getSelectedItem().toString();
            System.out.println("gefilterte nummer:" + nummer);

            if(antwort[i].startsWith(nummer)){

                String [] flugdaten = null;
                flugdaten = antwort[i].split("#");
                flug = flugdaten[0];
                airpl = flugdaten[1];
                departure = flugdaten[2];
                arrival = flugdaten[3];
                source = flugdaten[4];
                destination = flugdaten[5];

                System.out.println("-------------");
                System.out.println(flug);
                System.out.println(airpl);
                System.out.println(departure);
                System.out.println(arrival);
                System.out.println(source);
                System.out.println(destination);
                System.out.println("-------------");
                System.out.println("Schleifenausgabe" + antwort[i]);
                i = antwort.length;

            }

            AR.setText(destination);
            DE.setText(source);
            timedep.setText(departure);
            timearr.setText(arrival);
            airp.setText(airpl);
            String belegung = null;

            belegung = c.sendMessage("666;GET;flightnumber;" + flightNumber.getSelectionModel().getSelectedItem().toString());

            System.out.println("Belegung vor split:" + belegung);
            String [] seating = belegung.split(";");
            seatempty.clear();
            bookedseat.clear();
            for(int x = 0; x<seating.length ; x++)
            {
                String [] sitzteilung = seating[x].split("#");
                System.out.println("seating: " + seating[x] + "laenge" + sitzteilung.length);
                if(sitzteilung.length < 3) {
                    seatempty.add(seating[x]);
                }else{
                    bookedseat.add(seating[x]);
                }

            }
            emptySeats.setItems(seatempty);
            bookedSeats.setItems(bookedseat);

        }
System.out.println("Gebucht:" + bookedseat.toString());
        System.out.println("Frei: " + seatempty.toString());
    }



    private void setTimearr(){


    }

    private void setTimedep(){



    }

    private void setAR(){

    }

    private void setDE(){

    }

    private void setAirp(){

    }

    //flightNumber Combobox mit Werten befüllen***
    private void setChoiceBoxFlightnumber() throws IOException {

        String flug=  null;
        String airp = null;
        String arrival = null;
        String departure = null;
        String source = null;
        String destination = null;

        if (c.response == null){
            return;
        }

        String response = c.response;

        String[] antwort = response.split(";");
        for(int i = 0; i<antwort.length; i++){
                String [] flugdaten = null;
                flugdaten = antwort[i].split("#");
                flug = flugdaten[0];

                listFlightnumber.add(flugdaten[0]);

                airp = flugdaten[1];
                departure = flugdaten[2];
                arrival = flugdaten[3];
                source = flugdaten[4];
                destination = flugdaten[5];

        }
        flightNumber.getItems().addAll(listFlightnumber);
    }






    //TRANSMITTEN ABFRAGE
    @FXML
    public void btnSend(ActionEvent event) throws IOException {

        String msg = txtMessage.getText();
        c.sendMessage(msg);
        out.println(msg);
        //c.getresponse();

    }

    @FXML
    public void loading(ActionEvent event){

        flightNumber.getSelectionModel().getSelectedItem();



    }


    //INITIALES ABFRAGEN ALLE FLÜGE
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            seating = c.sendMessage("666;GET;flightnumber;all");
            //c.getresponse();
            setChoiceBoxFlightnumber();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}