package ch.hslu.vsk.logger.common.messagepassing;

import ch.hslu.vsk.logger.common.adapter.LogPersistor;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.logger.common.messagepassing.messages.ResultMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Communication handler that is responsible for the server-side communication.
 */
public final class LogServerCommunicationHandler extends AbstractBasicMessageHandler implements Runnable {

    private final LogPersistor persistor;

    /**
     * Constructor.
     *
     * @param inputStream  Inputstream
     * @param outputStream Outputstream
     * @param persistor LogPersistor
     */
    public LogServerCommunicationHandler(final InputStream inputStream, final OutputStream outputStream, final LogPersistor persistor) {
        super(inputStream, outputStream);
        this.persistor = persistor;
    }

    /**
     * Builds a message matching the given message id.
     *
     * @param msgId MessageID.
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


    /**
     * Used in asynchronous message handling. Currently used only on the server side.
     */
    @Override
    public void run() {
        try {
            boolean busy = true;
            while (busy) {
                AbstractBasicMessage msg = readMsg();
                if (msg != null) {
                    busy = msg.operate(this.persistor);
                }
            }
        } catch (IOException e) {
            // Treat an IOException as a termination of the message
            // exchange, and let this message-processing thread die.

            // CODE VON DOZENTEN...
        }
    }
}
