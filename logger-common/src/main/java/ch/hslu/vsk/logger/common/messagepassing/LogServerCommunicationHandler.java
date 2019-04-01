package ch.hslu.vsk.logger.common.messagepassing;

import ch.hslu.vsk.logger.common.adapter.LogPersistor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

/**
 * Communication handler that is responsible for the server-side communication
 */
public final class LogServerCommunicationHandler extends AbstractBasicMessageHandler implements Runnable {

    private final LogPersistor persistor;

    /**
     * Constructor
     *
     * @param inputStream  Inputstream
     * @param outputStream Outputstream
     */
    public LogServerCommunicationHandler(InputStream inputStream, OutputStream outputStream, LogPersistor persistor) {
        super(inputStream, outputStream);
        this.persistor = persistor;
    }

    /**
     * Builds a message matching the given message id
     *
     * @param msgId MessageID.
     * @return Message
     */
    @Override
    protected AbstractBasicMessage buildMessage(String msgId) {

        var message = this.getMessageTypes().stream().filter(x -> x.getMessageId().equals(msgId)).findFirst();

        if(!message.isEmpty()){
            return message.get();
        }
        return null;
    }


    /**
     * Used in asynchronous message handling. Currently used only on the server side
     */
    @Override
    public final void run() {
        try {
            boolean busy = true;
            while (busy) {
                final AbstractBasicMessage msg = readMsg();
                if (msg != null) {
                    busy = msg.operate(this.persistor);
                }
            }
        } catch (IOException e) {
            // Treat an IOException as a termination of the message
            // exchange, and let this message-processing thread die.
            e.printStackTrace();
        }
    }
}
