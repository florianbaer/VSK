package ch.hslu.vsk.logger.component.services;

import ch.hslu.vsk.logger.common.messagepassing.LogCommunicationHandler;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;

import java.io.IOException;
import java.net.Socket;
import java.time.Instant;
import java.util.List;
import java.util.TimerTask;

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
        this.startConnectionTimer();
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


    @Override
    public void sendMessageToServer(final String messageToSend) {
        LogMessage message = new LogMessage(messageToSend);
        List<LogMessage> messages = clientLogPersister.getAllLocalLogs();
        try {
            clientLogPersister.clearLocalLogFile();
            if(messages.size() > 0 && clientSocket != null && logCommunicationHandler != null) {
                sendAllLocalLogs(messages);
            }
            logCommunicationHandler.sendMsg(message);
        } catch (Exception e) {
            messages.add(message);
            messages.stream().forEach(x -> storeLogsLocally(Instant.now(), x));
        }
    }

    /**
     * Send all local logs that were persisted while connection was not available.
     * @param messages The messages to send.
     */
    private void sendAllLocalLogs(List<LogMessage> messages) {
        try {
            for (LogMessage logMessage : messages) {
                logCommunicationHandler.sendMsg(logMessage);
            }
            System.out.println("All local logs sent successfully.");
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
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

    /**
     * Timer that tries to establish connection every 5 seconds if connection is lost.
     */
    private void startConnectionTimer () {
        new java.util.Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    clientSocket = new Socket(host, port);
                    logCommunicationHandler = new LogCommunicationHandler(clientSocket.getInputStream(),
                            clientSocket.getOutputStream());
                } catch (Exception e) {
                    clientSocket = null;
                    logCommunicationHandler = null;
                    System.out.println("Timer couldn't establish connection");
                }
            }
        },0, 5000);
    }
}
