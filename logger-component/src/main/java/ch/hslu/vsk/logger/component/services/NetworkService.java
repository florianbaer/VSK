package ch.hslu.vsk.logger.component.services;

import ch.hslu.vsk.logger.common.messagepassing.LoggerComHandler;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;

import java.io.IOException;
import java.net.Socket;

/**
 * This class is responsible interacting with the server where LOG-Messages are logged to. It is
 * implemented as Singleton, because multiple Log-Objects use it and whe don't want every Log-Object
 * to use a different Instance of this class.
 *
 */
public class NetworkService {

    private static NetworkService service;
    private LoggerComHandler loggerComHandler;
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
            loggerComHandler = new LoggerComHandler(clientSocket.getInputStream(), clientSocket.getOutputStream());
        } catch (IOException ioe) {

        }
    }

    /**
     * Returns Instance of the NetworkService class. By default,
     * the host is '127.0.0.1' and port is '59090'. This must be changed later!!
     * @return newly created instance or existing instance
     */
    public static NetworkService getInstance(){
        if (service == null){
            synchronized (NetworkService.class){
                if (service == null)
                    service = new NetworkService("127.0.0.1", 59090);
            }
        }
        return service;
    }

    /**
     * This method is needed because the connection String of the LoggerComponent
     * can be set outside of the properties file.
     */
    public void changeConnectionDetails(String host, int port){
        try {
            clientSocket.close();
            clientSocket = new Socket(host, port);
            loggerComHandler = new LoggerComHandler(clientSocket.getInputStream(), clientSocket.getOutputStream());
        } catch (IOException ioe) {

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
            loggerComHandler.sendMsg(message);
        } catch (IOException ioe) {

        }
    }
}
