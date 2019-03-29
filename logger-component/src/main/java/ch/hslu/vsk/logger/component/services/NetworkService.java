package ch.hslu.vsk.logger.component.services;

import ch.hslu.vsk.logger.common.messagepassing.LoggerComHandler;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 *
 */
public class NetworkService {

    private static final Logger LOG = LogManager.getLogger(NetworkService.class);
    private LoggerComHandler loggerComHandler;


    public NetworkService(String host, int port) {
        try {
            Socket clientSocket = new Socket(host, port);
            loggerComHandler = new LoggerComHandler(clientSocket.getInputStream(), clientSocket.getOutputStream());
        } catch (IOException ioe) {
            LOG.error("Exception: " + ioe.getMessage());
        }
    }

    public void sendToServer(String messageToSend) {
        LogMessage message = new LogMessage(messageToSend);
        try {
            loggerComHandler.sendMsg(message);
        } catch (IOException ioe) {
            LOG.error("Exception: " + ioe.getMessage());
        }
    }
}
