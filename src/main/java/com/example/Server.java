package com.example.test_tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    BufferedReader bufferedReader = null;
    BufferedWriter bufferedWriter = null;
    ServerSocket serverSocket = null;

    public Client c;


    public Server() throws IOException {

        serverSocket = new ServerSocket(1235);


        while (true) {

            try {

                Socket clientSocket = serverSocket.accept();

               bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
               bufferedWriter = new BufferedWriter((new OutputStreamWriter(clientSocket.getOutputStream())));

                while (true) {
                    Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
                    String srvMsgReceived = scanner.next();

                    System.out.println("Server: " + srvMsgReceived);

                    bufferedWriter.write("Client:" + srvMsgReceived);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    if(srvMsgReceived == "bye"){
                        break;
                    }

                }
                clientSocket.close();
                serverSocket.close();
                bufferedReader.close();
                bufferedWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException{

        new Server();

    }
}


