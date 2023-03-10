package com.example.test_tcp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Server Object, which gets the port and handls the requests. According to the Request it is directed to the GET_Handler and SET_Handler
 *
 * @param "socket" is used to activate the socket
 * @throws IOException if the Server can not be started
 */

public class Server1 extends Application {
    //  ObservableList<String> logtext = FXCollections.emptyObservableList();
    //  LinkedList<String> fifolog = new LinkedList<String>();
    @FXML
    //  ListView<String> logview = new ListView<>();
            BufferedReader bufferedReader = null;
    BufferedWriter bufferedWriter = null;
    ServerSocket serverSocket = null;


    public Server1(int socket) throws IOException {

        serverSocket = new ServerSocket(socket);
        String ID = null;
        String Command = null;

        String Operant1 = null;
        String Operant2 = null;
        // fifolog.add("Server gestartet");
        while (true) {


            try {
                Socket clientSocket = serverSocket.accept();

                bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                bufferedWriter = new BufferedWriter((new OutputStreamWriter(clientSocket.getOutputStream())));
                int run = 1;
                while (run > 0) {

                    String srvMsgReceived = bufferedReader.readLine();

                    if (srvMsgReceived != null) {

                        String[] parts = new String[4];
                        parts = srvMsgReceived.split(";");
                        if (parts.length < 3) {
                            System.out.println("Laenge passt nicht");
                            bufferedWriter.write("Geh im RecycleBin");
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                        } else if (parts.length == 3) {
                            System.out.println("in laenge 3");
                            //666;SET/GET;flightnumber/airplane
                            ID = parts[0];  //h??tte f??r Logging verwendet werden sollen
                            Command = parts[1];
                            Operant1 = parts[2];
                            if (Command.equals("GET")) {
                                String text1 = GetHandler(Operant1);
                                //logtext.addAll("Server -> Client: " + text1);
                                bufferedWriter.write(text1);
                                bufferedWriter.newLine();
                                bufferedWriter.flush();

                            } else {
                                //   logtext.add("Server -> Client: Wrong Request" + Command);
                                bufferedWriter.write("Wrong Request => " + Command);
                            }

                        } else {
                            ID = parts[0];
                            Command = parts[1];
                            Operant1 = parts[2];
                            Operant2 = parts[3];

                            System.out.println("Client ID: " + ID + "\nBefehl: " + Command + "\nFlugnummer: " + Operant1 + "\nOperant: " + Operant2);
                            if (Command.equals("GET")) {
                                String text2 = GetHandler(Operant1, Operant2);
                                //logtext.addAll("Server -> Client: " + text1);
                                bufferedWriter.write(text2);
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


            } catch (Exception e) {
                System.out.println("Client Disconnected main");
            }
        }
    }

    /**
     * Is Used if you want to start one Server without a Thread
     * To start multiple Servers you must start the ServerHandler, which starts the Server Application
     * according to the project needs
     *
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {

        //  Server1 a = new Server1(1287);
        //   Server1 b =  new Server1(1286);
        //   Server1 c =  new Server1(1288);

    }

    /**
     * The GetHandler is responsible for processing the requests from the Client to the Server.
     * This requests are only reading requests.
     *
     * @param Operant1 is responsible for direct the request between flightnumber and airplane
     * @param Operant2 specifies the Search-Parameter.
     *                 Possible requests for flightnumber switch
     *                 flightnumber;all => delivers all registered flights with their informations
     *                 flightnumber;OSxxx => offers all Seats according to the flight
     * @return
     * @throws IOException if files can't be opend
     */

    public String GetHandler(String Operant1, String Operant2) throws IOException {

        String line = null;
        if (Operant1.equals("flightnumber") && Operant2.equals("all")) {
            ArrayList<String> Flugnummern = new ArrayList<>();
            BufferedReader bufReader = new BufferedReader(new FileReader("C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\flightnumber.txt"));
            line = bufReader.readLine();

            while (line != null) {
                Flugnummern.add(line);
                line = bufReader.readLine();
            }

            bufReader.close();

            String listString = String.join(";", Flugnummern);
            System.out.println("FlugnummerString: " + listString);

            return listString;


        } else if (Operant1.equals("flightnumber")) {
            //    System.out.println("Vorm oeffnen vom File flightnumber: " + Operant2);
            try {
                ArrayList<String> flugdaten = new ArrayList<>();
                BufferedReader bufReader = new BufferedReader(new FileReader("C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\" + Operant2 + ".txt"));
                //BufferedReader bufReader = new BufferedReader(new FileReader("/Users/stephanweidinger/Documents/FH/3rd Semester/ODE/ODE Projekt/Test_TCP/data/" + Operant2 + ".txt"));
                line = bufReader.readLine();
                System.out.println("Im File oeffnen");
                while (line != null) {
                    flugdaten.add(line);
                    line = bufReader.readLine();
                }
                bufReader.close();

                String listString = String.join(";", flugdaten);
                System.out.println(listString);
                return listString;
            } catch (Exception e) {
                System.out.println("Client Disconnected wegen file");
            }
            return "Wrong Data";
        }

        if (Operant1.equals("airplane")) {
            ArrayList<String> Airplane = new ArrayList<>();
            BufferedReader bufReader = new BufferedReader(new FileReader("C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\airplane.txt"));
            //BufferedReader bufReader = new BufferedReader(new FileReader("Users/stephanweidinger/Documents/FH/3rd Semester/ODE/ODE Projekt/Test_TCP/data/airplane.txt"));
            line = bufReader.readLine();

            while (line != null) {
                Airplane.add(line);
                line = bufReader.readLine();
            }
            bufReader.close();

            String listString1 = String.join(";", Airplane);
            System.out.println("FlugnummerString:" + listString1);
            return listString1;
        }
        return "noData";
    }

    public String GetHandler(String Operant1) throws IOException {

        String line = null;

        if (Operant1.equals("flightnumber")) {
            ArrayList<String> Flugnummern = new ArrayList<>();
            BufferedReader bufReader = new BufferedReader(new FileReader("C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\flightnumber.txt"));
            //BufferedReader bufReader = new BufferedReader(new FileReader("/Users/stephanweidinger/Documents/FH/3rd Semester/ODE/ODE Projekt/Test_TCP/data/flightnumber.txt"));
            line = bufReader.readLine();

            while (line != null) {
                Flugnummern.add(line);
                line = bufReader.readLine();
            }

            bufReader.close();

            String listString = String.join(";", Flugnummern);
            System.out.println("FlugnummerString: " + listString);

            return listString;

        } else if (Operant1.equals("airplane")) {
            ArrayList<String> Airplane = new ArrayList<>();
            BufferedReader bufReader = new BufferedReader(new FileReader("C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\airplane.txt"));
            //BufferedReader bufReader = new BufferedReader(new FileReader("Users/stephanweidinger/Documents/FH/3rd Semester/ODE/ODE Projekt/Test_TCP/data/airplane.txt"));
            line = bufReader.readLine();

            while (line != null) {
                Airplane.add(line);
                line = bufReader.readLine();
            }
            bufReader.close();

            String listString1 = String.join(";", Airplane);
            System.out.println("FlugnummerString: " + listString1);
            return listString1;

        }
        return "noData";
    }

    /**
     * SetHandler writes data to the files
     *
     * @param Operant1 switches between airplane and flightnumber
     * @param Operant2 "del", "add" and "book" specifies the Operation
     *                 all 3 operators are not atomic, which means, that the need do be seperated with a "," from the other informations
     *                 <p>
     *                 Example for deleting a flight
     *                 flightnumber;del,OSxxx
     *                 Example to add a flight
     *                 flightnumber;add,OS855#1propp#Dep:3.5.2022-23:20#Arr:4.5.2022-05:00#Src:Wien Praterstern#Dest:Zwettel
     *                 Example to book a seat
     *                 OSxxx;book,1#1#PersonName#PassortNumber
     * @return
     * @throws IOException
     */
    public String SetHandler(String Operant1, String Operant2) throws IOException {
        String mode = null;
        String command = null;
        String[] parts = null;
        parts = Operant2.split(",", 2);
        File fflightnumber = new File("C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\flightnumber.txt");
        //File fflightnumber = new File("/Users/stephanweidinger/Documents/FH/3rd Semester/ODE/ODE Projekt/Test_TCP/data/flightnumber.txt");
        File fairplane = new File("C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\airplane.txt");
        //File fairplane = new File("/Users/stephanweidinger/Documents/FH/3rd Semester/ODE/ODE Projekt/Test_TCP/data/");
        mode = parts[0];
        command = parts[1];

        if (Operant1.equals("flightnumber")) {

            ArrayList<String> flightnumber = new ArrayList<String>();
            String line;
            try {
                BufferedReader bufReader = new BufferedReader(new FileReader(fflightnumber));
                line = bufReader.readLine();
                while (line != null) {
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
                    if (flightexist != 0) {
                        String[] flightdata = null;
                        //  OS842#1propp#Dep:12.5.2022-12:20#Arr:13.5.2022-18:00#Src:London#Dest:Easterisland

                        flightdata = command.split("#");
                        String neuerflug;
                        if (flightdata.length == 6) {
                            //     try {

                            // Get the file
                            neuerflug = "C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\" + flightdata[0] + ".txt";
                            //neuerflug = "Users/stephanweidinger/Documents/FH/3rd Semester/ODE/ODE Projekt/Test_TCP/data/" + flightdata[0] + ".txt";
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

            ArrayList<String> airplane = new ArrayList<>();
            String line;
            try {
                BufferedReader bufReader = new BufferedReader(new FileReader(fairplane));
                line = bufReader.readLine();
                while (line != null) {
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

        if (mode.equals("book")) {
            //String[] seat = null;
            String line = null;

            try {
                ArrayList<String> sitzplatz = new ArrayList<>();
                BufferedReader bufReader = new BufferedReader(new FileReader("C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\" + Operant1 + ".txt"));
                //BufferedReader bufReader = new BufferedReader(new FileReader("/Users/stephanweidinger/Documents/FH/3rd Semester/ODE/ODE Projekt/Test_TCP/data/" + Operant1 + ".txt"));
                line = bufReader.readLine();
                while (line != null) {
                    sitzplatz.add(line);
                    line = bufReader.readLine();
                }
                bufReader.close();

                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\molti\\IdeaProjects\\Test_TCP\\data\\" + Operant1 + ".txt", false));
                //BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/stephanweidinger/Documents/FH/3rd Semester/ODE/ODE Projekt/Test_TCP/data/" + Operant1 + ".txt", false));
                String place = null;
                place = command.substring(0, 4);
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
                String listString = String.join(";", sitzplatz);
                //     System.out.println(listString);
                return ("Booked: " + command);
            } catch (Exception e) {
                System.out.println("Client Disconnected in File");
            }
        }


        return "command complete";
    }

    /**
     * Is inserted to acitivate a GUI for the Server.
     * The GUI should log the Requests in a seperated window, but it was not implimented
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}





