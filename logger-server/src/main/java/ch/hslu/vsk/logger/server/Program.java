package ch.hslu.vsk.logger.server;

import java.util.Scanner;

public class Program {
    public static void main(String[]args) {
        LoggerServer server = new LoggerServer();

        Thread serverThread = new Thread(server);

        Scanner keyboard = new Scanner(System.in);
        serverThread.start();
        char input = ' ';
        while(input != 'q' && input != 'Q') {
            System.out.println("Press 'q' or 'Q' to quit the server");
            input = keyboard.next().charAt(0);
        }

        System.out.println("Server will close within the next 5 Seconds.");
        serverThread.interrupt();
    }
}
