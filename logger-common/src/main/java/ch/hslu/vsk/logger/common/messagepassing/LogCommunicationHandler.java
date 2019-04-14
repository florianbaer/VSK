package ch.hslu.vsk.logger.common.messagepassing;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.logger.common.messagepassing.messages.ResultMessage;

import java.io.InputStream;
import java.io.OutputStream;



/**
 * Client communication handler that is responsible for the client-side communication.
 */
public class LogCommunicationHandler extends AbstractBasicMessageHandler {

    /**
     * Constructor.
     *
     * @param inputStream  Inputstream
     * @param outputStream Outputstream
     */
    public LogCommunicationHandler(final InputStream inputStream, final OutputStream outputStream) {
        super(inputStream, outputStream);
    }

    /**
     * Interprets a message id and returns the matching message-type.
     *
     * @param msgId MessageID
     * @return Message
     */
    @Override
    protected AbstractBasicMessage buildMessage(final String msgId) {
        if (msgId.equals("log")) {
            return new LogMessage();
        } else {
            return new ResultMessage();
        }
    }
}
