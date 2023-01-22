package com.example.test_tcp;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

/**
 * This class impliments the client
 */
public class Client {

    public static String response;
    public static PrintWriter out;
    public static BufferedReader in;
    private static Socket clientSocket;
    public String antwort;

    /**
     * The Client needs to run a defined Source Port
     * for later Use it will also generate a identifier, but this was not implimented
     * @param Sc is the Source Port of the Server
     * @throws IOException
     */
    public Client(int Sc) throws IOException {

        //clientSocket = new Socket("192.168.8.164", 1286);
        clientSocket = new Socket("localhost", Sc);


        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);

    }


        /*public void getresponse() throws IOException {

        response = in.readLine();

        }*/

    /**
     * Send message sends the Request to the Server Application and gets the Response, which is transferred
     * the Response back to the Application, wo launched it
     * @param msg
     * @return
     */
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


