package com.example.test_tcp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;


public class HelloController extends Thread implements Initializable, Aircraft {

    ObservableList<Object> listFlightnumber = FXCollections.observableArrayList();
    ObservableList<Object> seatempty = FXCollections.observableArrayList();
    ObservableList<Object> bookedseat = FXCollections.observableArrayList();
    String seating;



    //----------------------------------------------------------

    @FXML
    private TextField txtMessage;
    @FXML
    private ComboBox<Object> flightNumber;
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
    private ListView<Object> emptySeats;
    @FXML
    private ListView<Object> bookedSeats;
    @FXML
    private TextField name;
    @FXML
    private TextField reisepass;

    //-------------------------------------------------------


    /**
     * New Client start
     *
     * @throws IOException
     */
    public HelloController() throws IOException {
        Client c = new Client(1286);
    }

    /**
     * Changes the other GUI elements based on the selected flightnumber
     */
    @FXML
    private void onFlightNumberChanged() {

        String flugn = Client.sendMessage("666;GET;flightnumber;all");

        if (flugn.isEmpty()) {
            return;
        }

        String airpl = null;
        String arrival = null;
        String departure = null;
        String source = null;
        String destination = null;

        String[] antwort = flugn.split(";");
        for (int i = 0; i < antwort.length; i++) {
            String nummer = flightNumber.getSelectionModel().getSelectedItem().toString();

            if (antwort[i].startsWith(nummer)) {

                String[] flugdaten = antwort[i].split("#");
                airpl = flugdaten[1];
                departure = flugdaten[2];
                arrival = flugdaten[3];
                source = flugdaten[4];
                destination = flugdaten[5];

                i = antwort.length;

            }

            AR.setText(destination);
            DE.setText(source);
            timedep.setText(departure);
            timearr.setText(arrival);
            airp.setText(airpl);

            String belegung = Client.sendMessage("666;GET;flightnumber;" + flightNumber.getSelectionModel().getSelectedItem().toString());

            String[] seating = belegung.split(";");
            seatempty.clear();
            bookedseat.clear();

            for (String s : seating) {
                String[] sitzteilung = s.split("#");
                if (sitzteilung.length < 3) {
                    seatempty.add(s);
                } else {
                    bookedseat.add(s);
                }
            }
            emptySeats.setItems(seatempty);
            bookedSeats.setItems(bookedseat);
        }
    }

    /**
     * Books a selected, desired seat
     */
    @FXML
    public void btnBook() {

        String wer = name.getText();
        String wo = emptySeats.getSelectionModel().getSelectedItem().toString();
        String reise = "#R" + reisepass.getText();
        System.out.println(reise);
        String buch = ("666;SET;" + flightNumber.getSelectionModel().getSelectedItem().toString() + ";book," + wo.trim() + wer.trim() + reise);
        Client.sendMessage(buch);

        String belegung = Client.sendMessage("666;GET;flightnumber;" + flightNumber.getSelectionModel().getSelectedItem().toString());

        String[] seating = belegung.split(";");
        seatempty.clear();
        bookedseat.clear();
        for (String s : seating) {
            String[] sitzteilung = s.split("#");
            if (sitzteilung.length < 3) {
                seatempty.add(s);
            } else {
                bookedseat.add(s);
            }
        }
        emptySeats.setItems(seatempty);
        bookedSeats.setItems(bookedseat);

        Aircrafttype1();
    }


    /**
     * Removes the selected desired seat from file and Listview
     */
    @FXML
    private void delete() {

        String wo = bookedSeats.getSelectionModel().getSelectedItem().toString();
        String[] newwo = wo.split("#");
        String del = newwo[0] + "#" + newwo[1] + "#";


        String buch = ("666;SET;" + flightNumber.getSelectionModel().getSelectedItem().toString() + ";book," + del.trim());
        System.out.println(del);
        Client.sendMessage(buch);

        bookedseat.remove(wo);
        String belegung = Client.sendMessage("666;GET;flightnumber;" + flightNumber.getSelectionModel().getSelectedItem().toString());

        String[] seating = belegung.split(";");
        seatempty.clear();
        bookedseat.clear();

        for (String s : seating) {
            String[] sitzteilung = s.split("#");
            if (sitzteilung.length < 3) {
                seatempty.add(s);
            } else {
                bookedseat.add(s);
            }
        }
        emptySeats.setItems(seatempty);
        bookedSeats.setItems(bookedseat);

        Aircrafttype2();

    }


    /**
     * Gets and sets the Flightnumbers for Choicebox Flightnumber
     *
     * @throws IOException
     */
    private void setChoiceBoxFlightnumber() throws IOException {

        if (Client.response == null) {
            return;
        }

        String response = Client.response;

        String[] antwort = response.split(";");
        for (String s : antwort) {
            String[] flugdaten = s.split("#");

            listFlightnumber.add(flugdaten[0]);

        }
        flightNumber.getItems().addAll(listFlightnumber);
    }


    @FXML
    public void loading() {

        flightNumber.getSelectionModel().getSelectedItem();

    }

    /**
     * Initializes the GUI and sends a request for all data
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            seating = Client.sendMessage("666;GET;flightnumber;all");
            setChoiceBoxFlightnumber();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays Advertisement 1
     */
    @Override
    public void Aircrafttype1() {

        txtMessage.appendText("AD: Enjoy our newest Aircraft the A321!");
    }

    /**
     * Displays Advertisement 2
     */
    @Override
    public void Aircrafttype2() {

        txtMessage.clear();
        txtMessage.appendText("AD: We fly for your Smile");

    }
}