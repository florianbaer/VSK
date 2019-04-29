package ch.hslu.vsk.logger.component.services;

import ch.hslu.vsk.logger.common.messagepassing.LogCommunicationHandler;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;

import java.io.IOException;
import java.net.Socket;

/**
 * This class is responsible interacting with the server where LOG-Messages are logged to. It is
 * implemented as Singleton, because multiple Log-Objects use it and whe don't want every Log-Object
 * to use a different Instance of this class.
 */
public final class NetworkService implements NetworkCommunication {

    private static NetworkService networkService;
    private LogCommunicationHandler logCommunicationHandler;
    private Socket clientSocket;
    private String host;
    private int port;

    private boolean isLoggingLocally;
    private ClientLogPersister clientLogPersister;


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

        this.clientLogPersister = new ClientLogPersister();
        try {
            clientSocket = new Socket(this.host, this.port);
            logCommunicationHandler = new LogCommunicationHandler(clientSocket.getInputStream(),
                    clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        this.isLoggingLocally = !isServerReachable();


    }

    /**
     * Used to get an Instance of this class. It ensures that only one Instance
     * exists at runtime, because there should only be one network-point to communicate.
     *
     * @param connectionString for the service
     * @return Instance of NetworkService to be used
     */
    public static NetworkService getInstance(String connectionString) {

        synchronized (NetworkService.class) {
            if (networkService == null) {
                try {
                    String[] connection = connectionString.split(":");

                    networkService = new NetworkService(connection[0], Integer.valueOf(connection[1]));
                    return networkService;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return networkService;
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

        if (isLoggingLocally) {
            //LoggerComponent was logging locally at this point.
            if (isServerReachable()) {
                //Looks like server defined by LoggerComponent is available again. Trying to reach server now.
                this.isLoggingLocally = false;
                try {
                    this.sendAllLocalLogs();
                    logCommunicationHandler.sendMsg(message);
                } catch (IOException e) {
                    this.isLoggingLocally = true;
                    System.out.println(e.getMessage());
                    System.out.println("Server seems to be available, but the message could not be sent!");
                }
            } else {
                System.out.println("Server is still not reachable");
                System.out.println("Storing logs locally until connection to server can be established again.");
                this.storeLogsLocally(message);
            }
        } else {
            //LoggerComponent was logging on the server at this point.
            if (isServerReachable()) {
                //server is reachable, trying to send message
                try {
                    logCommunicationHandler.sendMsg(message);
                } catch (IOException e) {
                    //exception while sending message to server, even though server was reachable shortly before
                    System.out.println(e.getMessage());
                    System.out.println("");
                }
            } else {
                System.out.println("Server not reachable anymore, switching to local logging.");
                this.isLoggingLocally = true;
                storeLogsLocally(message);
            }


        }
    }

    private void sendAllLocalLogs() {

        try {
            for (LogMessage logMessage
                    : clientLogPersister.getAllLocalLogs()) {
                logCommunicationHandler.sendMsg(logMessage);
            }
            clientLogPersister.clearLocalLogFile();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }


    /**
     * Checks if the server, which is specified in the LoggerComponent, is reachable or not.
     *
     * @return true if server is reachable; false if server is unreachable.
     */
    private boolean isServerReachable() {
        try {
            clientSocket.getOutputStream().write(2);
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    /**
     * Sends the logmessage to the ClientPersistor in order to store the log locally.
     *
     * @param messageToPersistLocally Message that will be persisted in the local logfile.
     */
    private void storeLogsLocally(final LogMessage messageToPersistLocally) {
        clientLogPersister.persistLocally(messageToPersistLocally);
    }
}
