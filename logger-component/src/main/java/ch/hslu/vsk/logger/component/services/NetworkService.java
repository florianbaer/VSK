package ch.hslu.vsk.logger.component.services;

import ch.hslu.vsk.logger.common.messagepassing.LogCommunicationHandler;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Instant;

/**
 * This class is responsible interacting with the server where LOG-Messages are logged to. It is
 * implemented as Singleton, because multiple Log-Objects use it and whe don't want every Log-Object
 * to use a different Instance of this class.
 */
public final class NetworkService implements NetworkCommunication {

    private static NetworkService networkService;
    private LogCommunicationHandler logCommunicationHandler;
    private Socket clientSocket;
    private Socket testSocket;
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
        this.isLoggingLocally = true;

        this.clientLogPersister = new ClientLogPersister();

        if (isServerReachable()) {
            setupServerSocketAndStream();
            isLoggingLocally = false;
        } else {
            isLoggingLocally = true;
        }

    }


    /**
     * Used to get an Instance of this class. It ensures that only one Instance
     * exists at runtime, because there should only be one network-point to communicate.
     *
     * @param connectionString for the service
     * @return Instance of NetworkService to be used
     */
    public static NetworkService getInstance(final String connectionString) {

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
                setupServerSocketAndStream();
                setupTestSocket();
                try {
                    this.sendAllLocalLogs();
                    logCommunicationHandler.sendMsg(message);
                    System.out.println("Message sent successfully");
                    this.isLoggingLocally = false;
                } catch (IOException e) {
                    this.isLoggingLocally = true;
                    System.out.println(e.getMessage());
                    System.out.println("Server seems to be available, but the message could not be sent!");
                }
            } else {
                System.out.println("Server is still not reachable. "
                        + "Storing logs locally until server is reachable again");
                this.storeLogsLocally(Instant.now(), message);
            }
        } else {
            //LoggerComponent was logging on the server at this point.
            if (isServerReachable()) {
                //server is reachable, trying to send message
                try {
                    logCommunicationHandler.sendMsg(message);
                    System.out.println("Server online. Sent message directly");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Exception while sending message to server, "
                            + "even though server was reachable shortly before");
                }
            } else {
                System.out.println("Server not reachable anymore, switching to local logging.");
                this.isLoggingLocally = true;
                storeLogsLocally(Instant.now(), message);
            }


        }
    }

    /**
     * Send all local logs that were persisted while connection was not available.
     */
    private void sendAllLocalLogs() {
        try {
            for (LogMessage logMessage
                    : clientLogPersister.getAllLocalLogs()) {
                logCommunicationHandler.sendMsg(logMessage);
            }
            System.out.println("All local logs sent successfully.");
            clientLogPersister.clearLocalLogFile();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }

    /**
     * Initialize Sockets and Streams.
     */
    private void setupServerSocketAndStream() {
        try {
            clientSocket = new Socket(this.host, this.port);
            logCommunicationHandler = new LogCommunicationHandler(clientSocket.getInputStream(),
                    clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Used to check if host (server) is still reachable.
     * @return true if reachable, false if not
     */
    private boolean setupTestSocket() {

        try {
            this.testSocket = new Socket(this.host, this.port);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    /**
     * Checks if the server, which is specified in the LoggerComponent, is reachable or not.
     *
     * @return true if server is reachable; false if server is unreachable.
     */
    private boolean isServerReachable() {

        try {
            if (testSocket == null && !setupTestSocket()) {
                return false;
            } else if (!setupTestSocket()) {
                return false;
            } else if (testSocket == null) {
                setupTestSocket();
            }

            return !(new PrintWriter(testSocket.getOutputStream()).checkError());
        } catch (
                IOException ioe) {
            System.out.println(ioe.getMessage());
            return false;
        }


    }


    /**
     * Sends the logmessage to the ClientPersistor in order to store the log locally.
     *
     * @param messageToPersistLocally Message that will be persisted in the local logfile.
     * @param instant to save besides the message.
     */
    private void storeLogsLocally(final Instant instant, final LogMessage messageToPersistLocally) {
        clientLogPersister.persistLocally(instant, messageToPersistLocally);
    }
}
