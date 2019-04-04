package ch.hslu.vsk.logger.server;

import java.util.Scanner;
import java.util.concurrent.Executors;

public class Program {
    public static void main(String[]args) {
        Thread serverThread = null;
        try{
            ServerProperties serverProperties = new ServerProperties();
            serverProperties.loadProperties();
            LoggerServer server = new LoggerServer(serverProperties, Executors.newCachedThreadPool());

            serverThread = new Thread(server);
            Scanner keyboard = new Scanner(System.in);
            serverThread.start();

            char input = ' ';
            while(input != 'q' && input != 'Q') {
                System.out.println("Press 'q' or 'Q' to quit the server");
                input = keyboard.next().charAt(0);
            }
        }catch (Exception ex){
            // generic exception handling on server
            System.out.println("CRITICAL ERROR. SERVER GOT KILLED...");
        }
        finally {
            if(serverThread != null && serverThread.isAlive())
            {
                serverThread.interrupt();
            }
        }
    }
}
