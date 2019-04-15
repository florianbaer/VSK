package ch.hslu.vsk.logger.component.services;

import ch.hslu.vsk.logger.common.messagepassing.LogCommunicationHandler;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;

import java.net.Socket;
import java.util.Properties;

/**
 * This class is responsible interacting with the server where LOG-Messages are logged to. It is
 * implemented as Singleton, because multiple Log-Objects use it and whe don't want every Log-Object
 * to use a different Instance of this class.
 */
public final class NetworkService implements NetworkCommunication {
    private static final String LOGGER_PROPERTY_FILE = "vsklogger.properties";
    private static final String PROPERTY_CONNECTION_STRING = "ch.hslu.vsk.logger.connectionstring";

    private static NetworkService service;
    private LogCommunicationHandler logCommunicationHandler;
    private Socket clientSocket;
    private String host;
    private int port;


    /**
     * Private constructor. Creates a client socket with the
     * parameter specified in the properties file of the logger.
     *
     * @param host host address
     * @param port port
     */
    private NetworkService(final String host, final int port) {
        this.host = host;
        this.port = port;

        try {
            clientSocket = new Socket(this.host, this.port);
            logCommunicationHandler = new LogCommunicationHandler(clientSocket.getInputStream(),
                    clientSocket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Used to get an Instance of this class. It ensures that only one Instance
     * exists at runtime, because there should only be one network-point to communicate
     *
     * @return Instance of NetworkService to be used
     */
    public static NetworkService getInstance() {
        Properties networkProperties = new Properties();

        synchronized (NetworkService.class) {
            if (service == null) {
                try {
                    networkProperties.load(NetworkService.class.getClassLoader().getResourceAsStream(
                            LOGGER_PROPERTY_FILE));
                    String propertyAsString = networkProperties.getProperty(PROPERTY_CONNECTION_STRING);

                    String[] connectionString = propertyAsString.split(":");

                    service = new NetworkService(connectionString[0], Integer.valueOf(connectionString[1]));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

        }
        return service;
    }

    /**
     * Return the connection string.
     *
     * @return host:port
     */
    public String getConnectionDetails() {
        return "Host: " + this.host + ", Port: " + port;
    }

    @Override
    public void sendMessageToServer(final String messageToSend) {
        LogMessage message = new LogMessage(messageToSend);
        try {
            logCommunicationHandler.sendMsg(message);
        } catch (Exception e) {
            // TODO hier muss das lokale pesistieren rein
            System.out.println("Sending to the server not possible...");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void changeConnectionDetails(final String connectionString) {
        String[] connection = connectionString.split(":");

        String newHost = connection[0];
        int newPort = Integer.valueOf(connection[1]);

        try {
            if (clientSocket != null) {
                clientSocket.close();
            }

            this.host = newHost;
            this.port = newPort;
            clientSocket = new Socket(newHost, newPort);
            logCommunicationHandler = new LogCommunicationHandler(clientSocket.getInputStream(),
                    clientSocket.getOutputStream());
        } catch (Exception ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
