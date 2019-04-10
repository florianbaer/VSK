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

    /**
     * This method is needed because the connection String of the LoggerComponent
     * can be set outside of the properties file.
     *
     * @param connectionString connection string 'host:port'
     */
    void changeConnectionDetails(String connectionString);
}
