package ch.hslu.vsk.logger.common.messagepassing;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

/**
 * Client communication handler that is responsible for the client-side communication
 */
public final class LogCommunicationHandler extends AbstractBasicMessageHandler {

    /**
     * Constructor
     *
     * @param inputStream  Inputstream
     * @param outputStream Outputstream
     */
    public LogCommunicationHandler(InputStream inputStream, OutputStream outputStream) {
        super(inputStream, outputStream);
    }

    /**
     * Interprets a message id and returns the matching message-type
     *
     * @param msgId MessageID.
     * @return Message
     */
    @Override
    protected AbstractBasicMessage buildMessage(String msgId) {

        Optional<AbstractBasicMessage> message = this.getMessageTypes().stream().filter(x -> x.getMessageId() == msgId).findFirst();

        if (!message.isEmpty()) {
            return message.get();
        }
        return null;
    }
}
