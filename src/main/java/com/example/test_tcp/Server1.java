package com.example.test_tcp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Delayed;


public class Server1 extends Application {
    ObservableList<String> logtext = FXCollections.emptyObservableList();
    LinkedList<String> fifolog = new LinkedList<String>();
    @FXML
    ListView<String>  logview = new ListView<>();
    BufferedReader bufferedReader = null;
    BufferedWriter bufferedWriter = null;
    ServerSocket serverSocket = null;


    public Server1(int socket) throws IOException {

        serverSocket = new ServerSocket(socket);
        String ID = null;
        String Command = null;

        String Operant1 = null;
        String Operant2 = null;

        while (true) {


            try {
                Socket clientSocket = serverSocket.accept();

                bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                bufferedWriter = new BufferedWriter((new OutputStreamWriter(clientSocket.getOutputStream())));
                int run = 1;
                while (run > 0 ) {

                    String srvMsgReceived = bufferedReader.readLine();

                    if (srvMsgReceived != null) {
                        System.out.println("RowDaten: " + srvMsgReceived);
                        String[] parts = new String[4];
                        parts = srvMsgReceived.split(";");
                        if (parts.length != 4) {
                            System.out.println("Laenge passt nicht");
                            bufferedWriter.write("Geh im RecycleBin");
                            bufferedWriter.newLine();
                            bufferedWriter.flush();

                        } else {
                            ID = parts[0];
                            Command = parts[1];
                            Operant1 = parts[2];
                            Operant2 = parts[3];

                            System.out.println("Client ID: " + ID + "\nBefehl: " + Command + "\nFlugnummer: " + Operant1 + "\nOperant: " + Operant2);
                            if (Command.equals("GET")) {
                                String text1 = GetHandler(Operant1, Operant2);
                                LogAdd("Server -> Client: " + text1);
                                bufferedWriter.write(text1);
                            } else if (Command.equals("SET")) {
                                String text = SetHandler(Operant1, Operant2);
                                //  logtext.add("Server -> Client: " + text);
                                bufferedWriter.write(text);
                            } else {
                                //   logtext.add("Server -> Client: Wrong Request" + Command);
                                bufferedWriter.write("Wrong Request => " + Command);
                            }

                            if (Operant1.equals("EXIT")) run = 0;
                            //logtext.add("Client:" + srvMsgReceived);
                            bufferedWriter.newLine();
                            bufferedWriter.flush();

                            if (srvMsgReceived.equals("bye")) {
                                clientSocket.close();
                                serverSocket.close();
                                bufferedReader.close();
                                bufferedWriter.close();

                            }

                        }
                    }

                }
//


            }catch( Exception e){
                System.out.println("Client Disconnected");
            }
        }
    }

    public String GetHandler(String Operant1, String Operant2) throws IOException{

        String line = null;

        if(Operant1.equals("flightnumber") && Operant2.equals("all"))
        {
            ArrayList<String> Flugnummern = new ArrayList<String>();
            BufferedReader bufReader = new BufferedReader(new FileReader("C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\flightnumber.txt"));
            line = bufReader.readLine();

            while(line != null)
            {
                Flugnummern.add(line);
                line = bufReader.readLine();
            }

            bufReader.close();

            String listString = String.join(";", Flugnummern);
            System.out.println("FlugnummerString: " + listString);

            return listString;
        }else if(Operant1.equals("flightnumber") )
        {
            System.out.println("Vorm oeffnen vom File flightnumber: " + Operant2);
            try {
                ArrayList<String> flugdaten = new ArrayList<>();
                BufferedReader bufReader = new BufferedReader(new FileReader("C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\" + Operant2 + ".txt"));
                line = bufReader.readLine();
                System.out.println("Im File oeffnen");
                while(line != null)
                {
                    flugdaten.add(line);
                    line = bufReader.readLine();
                }
                bufReader.close();

                String listString = String.join("; ", flugdaten);
                System.out.println(listString);
                return listString;
            }catch( Exception e){
                System.out.println("Client Disconnected wegen file");
            }
            return "Wrong Data";
        }

        if(Operant1.equals("airplane")  )
        {
            ArrayList<String> Airplane = new ArrayList<String>();
            BufferedReader bufReader = new BufferedReader(new FileReader("C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\airplane.txt"));
            line = bufReader.readLine();

            while(line != null){
                Airplane.add(line);
                line = bufReader.readLine();
            }
            bufReader.close();

            String listString1 = String.join("; ", Airplane);
            System.out.println("FlugnummerString: " + listString1);
            return listString1;
        }
        return "noData";
    }

    /**
     * SetHandler beschreibt in Englsich
     * @param Operant1
     * @param Operant2
     * @return
     * @throws IOException
     */
    public String SetHandler(String Operant1, String Operant2) throws IOException {
        String mode = null;
        String command = null;
        String[] parts = null;
        parts = Operant2.split(",", 2);
        File fflightnumber = new File("C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\flightnumber.txt");
        File fairplane = new File("C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\");
        mode = parts[0];
        command = parts[1];



        if (Operant1.equals("flightnumber")) {

            ArrayList<String> flightnumber = new ArrayList<String>();
            String line;
            try {
                BufferedReader bufReader = new BufferedReader(new FileReader(fflightnumber));
                line = bufReader.readLine();
                while(line != null){
                    flightnumber.add(line);
                    line = bufReader.readLine();
                }
                bufReader.close();
            } catch (IOException ioe) {
                System.err.println(ioe);
            }
            //Ausgeben

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fflightnumber, false));
                if (mode.equals(("del"))) {
                    for (String a : flightnumber) {
                        if (!(a.startsWith(command))) {
                            writer.write(a);
                            writer.newLine();
                        } else {
                            System.out.println("Eintrag: " + command + "geloescht");
                        }
                    }
                }
                if (mode.equals(("add"))) {
                    System.out.println("Bin im Add");
                    writer.write(command);
                    for (String a : flightnumber) {
                        if (a.equals(command)) {
                            System.out.println("Eintrag: " + command + "existiert bereits");

                        } else {
                            writer.newLine();
                            writer.write(a);

                        }



                    }
                }
                writer.close();
            } catch (IOException ioe) {
                System.err.println(ioe);
            }

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fflightnumber, false));
                int flightexist = 1;
                if (mode.equals(("del"))) {
                    for (String a : flightnumber) {
                        if (!(a.startsWith(command))) {
                            writer.write(a);
                            writer.newLine();
                        } else {
                            System.out.println("Eintrag: " + command + "geloescht");
                        }
                    }
                }
                if (mode.equals(("add"))) {
                    writer.write(command);
                    for (String a : flightnumber) {
                        if (a.equals(command)) {
                            System.out.println("Eintrag: " + command + "existiert bereits");
                            flightexist = 0;
                        } else {
                            writer.newLine();
                            writer.write(a);
                        }
                    }
                    writer.close();
                    if(flightexist != 0) {
                        String[] flightdata = null;
                        //  OS842#1propp#Dep:12.5.2022-12:20#Arr:13.5.2022-18:00#Src:London#Dest:Easterisland

                        flightdata = command.split("#");
                        String neuerflug;
                        if (flightdata.length == 6) {
                            //     try {

                            // Get the file
                            neuerflug = "C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\" + flightdata[0] + ".txt";
                            File f = new File(neuerflug);

                            // Create new file
                            // Check if it does not exist
                            if (f.createNewFile())
                                System.out.println("File " + flightdata[0] + " created");
                            else
                                System.out.println("File already exists");
                            //       }
                            //       catch (Exception e) {
                            //           System.err.println(e);
                            //       }
                        } else {
                            return ("Wrong Flightformat");
                        }
                        ArrayList<String> airplane = new ArrayList<String>();
                        //Airplane einlesen

                        BufferedReader bufReader = new BufferedReader(new FileReader(fairplane));
                        line = bufReader.readLine();
                        while (line != null) {
                            airplane.add(line);
                            line = bufReader.readLine();
                        }
                        bufReader.close();


                        int rows = 0;
                        int cols = 0;
                        System.out.println("gesuchtes Airplane:" + flightdata[1] + " geladene Airplanes: " + airplane.get(1));
                        //herausfiltern der Sitzplatzinformationen

                        for (String a : airplane) {
                            System.out.println("Suchflugzeug: " + a);
                            String[] airplanedata = a.split(",");
                            System.out.println("Suchflugzeug: " + airplanedata[0] + "Gesuchtes: " + flightdata[1]);
                            if (airplanedata[0].startsWith(flightdata[1])) {
                                //String[] airplanedata = a.split(",");
                                System.out.println("Ich hab das bloede flugzeug gefunden");
                                String[] seats = airplanedata[1].split("#");
                                rows = Integer.parseInt(seats[0]);
                                cols = Integer.parseInt(seats[1]);

                            }

                        }
                        System.out.println("Rows : " + rows);
                        System.out.println("Cols : " + cols);

                        BufferedWriter flightwriter = new BufferedWriter(new FileWriter(neuerflug, false));

                        for (int i = 1; i <= cols; i++) {
                            for (int a = 1; a <= rows; a++) {
                                flightwriter.write(i + "#" + a + "#");
                                flightwriter.newLine();
                            }
                        }

                        flightwriter.close();

                    }

                }
                //writer.close();
            } catch (IOException ioe) {
                System.err.println(ioe);
            }

        }

        if (Operant1.equals("airplane")) {

            ArrayList<String> airplane = new ArrayList<String>();
            String line;
            try {
                BufferedReader bufReader = new BufferedReader(new FileReader(fairplane));
                line = bufReader.readLine();
                while(line != null){
                    airplane.add(line);
                    line = bufReader.readLine();
                }
                bufReader.close();

                BufferedWriter writer = new BufferedWriter(new FileWriter(fairplane, false));
                if (mode.equals(("del"))) {
                    for (String a : airplane) {
                        if (!(a.equals(command))) {
                            writer.write(a);
                            writer.newLine();
                        } else {
                            System.out.println("Eintrag: " + command + "geloescht");
                        }
                    }
                }
                if (mode.equals(("add"))) {

                    writer.write(command);
                    for (String a : airplane) {
                        if (a.equals(command)) {
                            System.out.println("Eintrag: " + command + "existiert bereits");

                        } else {
                            writer.newLine();
                            writer.write(a);
                        }
                    }
                }
                writer.close();
            } catch (IOException ioe) {
                System.err.println(ioe);
            }
        }

        if(mode.equals("book"))
        {
            //String[] seat = null;
            String line = null;

            try {
                ArrayList<String> sitzplatz = new ArrayList<String>();
                BufferedReader bufReader = new BufferedReader(new FileReader("C:\\Users\\molti\\Documents\\Java\\Test_TCP\\data\\" + Operant1 + ".txt"));
                line = bufReader.readLine();
                while(line != null)
                {
                    sitzplatz.add(line);
                    line = bufReader.readLine();
                }
                bufReader.close();

                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\molti\\Documents\\Java\\Test_TCP\\data\\" + Operant1 + ".txt", false));
                String place = null;
                place = command.substring(0,4);
                for (String a : sitzplatz) {
                    if ((a.startsWith(place))) {
                        writer.write(command);
                        writer.newLine();
                    } else {
                        writer.write(a);
                        writer.newLine();
                    }
                }
                writer.close();
                String listString = String.join("; ", sitzplatz);
                //     System.out.println(listString);
                return ("Booked: " + command);
            }catch( Exception e){
                System.out.println("Client Disconnected in File");
            }
        }




        return "command complete";
    }

    public LinkedList<String> GetLogRead ()
    {System.out.println("Bin im logread");
               return fifolog;
    }
    public void LogAdd (String logtext)
    {
        System.out.println("Logtext hinzugefegt: " + logtext);
        fifolog.add(logtext);

        return ;
    }


    public static void main(String[] args) throws IOException{

        Server1 a =  new Server1(1287);
        //Server b =  new Server(1286);
        // Server c =  new Server(1288);

    }
    @Override
    public void start(Stage stage) throws IOException {
//UI von Server

        VBox outerVBox = new VBox();
        outerVBox.setPadding(new Insets(10));
        outerVBox.setSpacing(10);
        BorderPane borderPane = new BorderPane();

        //logview.setItems(logtext);

        outerVBox.getChildren().addAll(logview, borderPane);

        Scene scene = new Scene(outerVBox, 500, 500);

        stage.setTitle("TicketRequest");
        stage.setScene(scene);
        stage.show();
    }


}



