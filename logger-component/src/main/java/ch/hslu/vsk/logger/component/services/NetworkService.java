package ch.hslu.vsk.logger.component.services;

import ch.hslu.vsk.logger.api.LoggerSetupFactory;
import ch.hslu.vsk.logger.common.messagepassing.LogCommunicationHandler;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

/**
 * This class is responsible interacting with the server where LOG-Messages are logged to. It is
 * implemented as Singleton, because multiple Log-Objects use it and whe don't want every Log-Object
 * to use a different Instance of this class.
 *
 */
public final class NetworkService {
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
     */
    private NetworkService(String host, int port) {
        this.host = host;
        this.port = port;

        try {
            clientSocket = new Socket(this.host, this.port);
            logCommunicationHandler = new LogCommunicationHandler(clientSocket.getInputStream(), clientSocket.getOutputStream());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    /**
     * Returns Instance of the NetworkService class. The host and port are fetched from
     * the LoggerComponents-Properties file, because they must match.
     * @return newly created instance or existing instance
     */
    public static NetworkService getInstance() {
        Properties networkProperties = new Properties();

        synchronized (NetworkService.class){
            if (service == null)
                try {
                    networkProperties.load(NetworkService.class.getClassLoader().getResourceAsStream(LOGGER_PROPERTY_FILE));
                    String propertyAsString =  networkProperties.getProperty(PROPERTY_CONNECTION_STRING);

                    String[] connectionString = propertyAsString.split(":");

                    service = new NetworkService(connectionString[0], Integer.valueOf(connectionString[1]));
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }

        }
        return service;
    }

    /**
     * This method is needed because the connection String of the LoggerComponent
     * can be set outside of the properties file.
     */
    public void changeConnectionDetails(String connectionString){
        String[] connection = connectionString.split(":");

        String host = connection[0];
        int port = Integer.valueOf(connection[1]);

        try {
            if(clientSocket != null) {
                clientSocket.close();
            }

            this.host = host;
            this.port = port;
            clientSocket = new Socket(host, port);
            logCommunicationHandler = new LogCommunicationHandler(clientSocket.getInputStream(), clientSocket.getOutputStream());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public String getConnectionDetails(){
        return "Host: " + this.host + ", Port: " + port;
    }

    /**
     * Sends a log message to the remote server application
     * @param messageToSend
     */
    public void sendLogMessageToServer(String messageToSend) {
        LogMessage message = new LogMessage(messageToSend);
        try {
            logCommunicationHandler.sendMsg(message);
        } catch (Exception e)  {
            System.out.println("Sending to the server not possible...");
            System.out.println(e.getMessage());
        }
    }
}
