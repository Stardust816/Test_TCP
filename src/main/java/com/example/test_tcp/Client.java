package com.example.test_tcp;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class Client {

    public static String response;
    public static PrintWriter out;

    public String antwort;

    private static Socket clientSocket;

    public static BufferedReader in;

    public Client(int Sc) throws IOException {

        //clientSocket = new Socket("192.168.8.164", 1286);
        clientSocket = new Socket("localhost", Sc);


        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);

    }


        /*public void getresponse() throws IOException {

        response = in.readLine();

        }*/


    public static String sendMessage(String msg) {

        UUID uniqueKey = UUID.randomUUID();
        System.out.println(uniqueKey);
        out.println(msg);
        //String response = 0;

        try {

            //Hier wird Antwort vom Server ausgegeben
            response = in.readLine();
            String antwort;
            antwort = response;
            System.out.println(response);

            //Response aufteilen
            String[] parts = response.split(";");
            String part1 = parts[0];
            System.out.println(part1);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);

        }
        return response;
    }


    public void setClientSocket(Socket clientSocket) {

        this.clientSocket = clientSocket;

    }

}


