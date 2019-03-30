package ch.hslu.vsk.logger.common.testserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This implementation of a simple server is used for local testing. It can be compiled and run in
 * the terminal. Then the GameOfLife Application can be started and the log-messages are sent to this
 * server.
 */
public class TestServer {

    public static void main(String[]args) throws IOException {

        try (ServerSocket listener = new ServerSocket(59090)){

            System.out.println("The Server is running...");
            System.out.println("Waiting for Connections");
            while(true){
                try (Socket socket = listener.accept()){
                    InputStream inputStream = socket.getInputStream();

                    DataInputStream in = new DataInputStream(inputStream);


                    while(true){
                        String token = in.readUTF();

                        System.out.print("ID: " + token + " | ");

                        String message = in.readUTF();

                        System.out.print("Message: " + message  + " | ");

                        String end = in.readUTF();

                        System.out.print("End-Signal: " + end  + " | ");

                        System.out.println("");
                    }
                }
            }
        }

    }

}

