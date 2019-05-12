package ch.hslu.vsk.logger.server;

import ch.hslu.vsk.logger.common.ExceptionSerializer.ExceptionToStringSerializer;
import ch.hslu.vsk.logger.common.rmi.server.RegistrationService;

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
        System.setProperty("java.security.policy", "reg_rules.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        Thread serverThread = null;
        RegistrationService pushServer = null;

        Thread registryBootstrapper = new Thread(new RmiRegistryBootstrapper());

        try {
            registryBootstrapper.start();
            pushServer = RemotePushService.getInstance();
            ServerProperties serverProperties = new ServerProperties();
            serverProperties.loadProperties();
            LoggerServer server = new LoggerServer(serverProperties, Executors.newCachedThreadPool(), pushServer);

            serverThread = new Thread(server);
            Scanner keyboard = new Scanner(System.in);
            serverThread.start();

            char input = ' ';
            while (input != 'q' && input != 'Q') {
                System.out.println("Press 'q' or 'Q' to quit the server");
                input = keyboard.next().charAt(0);
            }

            if (pushServer != null) {
                pushServer.stop();
            }

        } catch (Exception ex) {
            // generic exception handling on server
            System.out.println("CRITICAL ERROR. SERVER GOT KILLED...");
            System.out.println(ExceptionToStringSerializer.execute(ex));
        } finally {
            if (serverThread != null && serverThread.isAlive()) {
                serverThread.interrupt();
            }
            if (registryBootstrapper != null && registryBootstrapper.isAlive()) {
                registryBootstrapper.interrupt();
            }
        }
    }
}
