package ch.hslu.vsk.logger.server;

import ch.hslu.vsk.logger.common.rmi.server.LogPushServer;
import ch.hslu.vsk.logger.common.rmi.server.PushServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * The Class which starts the program.
 */
public final class Program {

    /**
     * The private constructor for the program.
     */
    private Program() {
    }

    /**
     * The main method.
     *
     * @param args The main method arguments.
     */
    public static void main(final String[] args) {
        Thread serverThread = null;

        setupRegistrationServer();

        try {
            ServerProperties serverProperties = new ServerProperties();
            serverProperties.loadProperties();
            LoggerServer server = new LoggerServer(serverProperties, Executors.newCachedThreadPool());

            serverThread = new Thread(server);
            Scanner keyboard = new Scanner(System.in);
            serverThread.start();

            char input = ' ';
            while (input != 'q' && input != 'Q') {
                System.out.println("Press 'q' or 'Q' to quit the server");
                input = keyboard.next().charAt(0);
            }
        } catch (Exception ex) {
            // generic exception handling on server
            System.out.println("CRITICAL ERROR. SERVER GOT KILLED...");
        } finally {
            if (serverThread != null && serverThread.isAlive()) {
                serverThread.interrupt();
            }
        }
    }

    private static void setupRegistrationServer() {
        try {
            System.setProperty("java.rmi.server.hostname","localhost");
            PushServer pushServer = new LogPushServer();
            PushServer stub = (PushServer) UnicastRemoteObject.exportObject(pushServer, 0);
            Registry registry = LocateRegistry.createRegistry(3455);
            registry.bind("logpushserver", stub);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
