package ch.hslu.vsk.logger.component.services;

/**
 * Interface that declares the methods needed to communicate over
 * a tcp/ip network.
 */
public interface NetworkCommunication {
    /**
     * Sends a log message to the remote server application.
     * @param messageToSend message that should be added as payload to the message object
     */
    void sendMessageToServer(String messageToSend);
}
