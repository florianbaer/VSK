package ch.hslu.vsk.logger.component.services;

import ch.hslu.vsk.logger.api.LoggerSetupFactory;
import ch.hslu.vsk.logger.common.messagepassing.LoggerComHandler;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

/**
 * This class is responsible interacting with the server where LOG-Messages are logged to. It is
 * implemented as Singleton, because multiple Log-Objects use it and whe don't want every Log-Object
 * to use a different Instance of this class. A connection String must be in the properties-file, otherwise
 * the initialization of this class fails.
 *
 */
public class NetworkService {
    private final static Logger LOG = LogManager.getLogger(NetworkService.class);
    private static final String PROPERTY_FILE = "vsklogger.properties";
    private static final String PROPERTY_CONNECTION_STRING = "ch.hslu.vsk.logger.connectionstring";


    private static NetworkService service;
    private LoggerComHandler loggerComHandler;
    private Socket clientSocket;
    private Properties networkProperties;
    private String host;
    private int port;


    /**
     * Private constructor. Creates a client socket with the
     * parameter specified in the properties file of the logger.
     */
    private NetworkService() {
        networkProperties = new Properties();

        try {
            networkProperties.load(NetworkService.class.getResourceAsStream(PROPERTY_FILE));
            String[] hostAndPort = networkProperties.getProperty(PROPERTY_CONNECTION_STRING).split(":");

            clientSocket = new Socket(hostAndPort[0], Integer.valueOf(hostAndPort[1]));
            loggerComHandler = new LoggerComHandler(clientSocket.getInputStream(), clientSocket.getOutputStream());
        } catch (IOException ioe) {
            LOG.error("Exception while creating NetworkService: " + ioe.getMessage());
        }
    }

    public static NetworkService getInstance(){
        if (service != null){
            synchronized (NetworkService.class){
                if (service != null)
                    service = new NetworkService();
            }
        }
        return service;
    }

    /**
     * Sends a log message to the remote server application
     * @param messageToSend
     */
    public void sendLogMessageToServer(String messageToSend) {
        LogMessage message = new LogMessage(messageToSend);
        try {
            loggerComHandler.sendMsg(message);
        } catch (IOException ioe) {
            LOG.error("Failed to send LOG-Message to Server: " + ioe.getMessage());
        }
    }
}
