package ch.hslu.vsk.logger.common.messagepassing;

import ch.hslu.vsk.logger.common.adapter.LogPersistor;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.logger.common.messagepassing.messages.ResultMessage;
import ch.hslu.vsk.logger.common.rmi.server.PushServer;

import java.io.IOException;
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

    /**
     * Communication handler that is responsible for the server-side communication.
     */
    public static final class LogServerCommunicationHandler extends AbstractBasicMessageHandler implements Runnable {

        private final LogPersistor persistor;
        private PushServer pushServer;

        /**
         * Constructor.
         *  @param inputStream  Inputstream
         * @param outputStream Outputstream
         * @param persistor LogPersistor
         * @param pushServer
         */
        public LogServerCommunicationHandler(final InputStream inputStream, final OutputStream outputStream,
                                             final LogPersistor persistor, PushServer pushServer) {
            super(inputStream, outputStream);
            this.persistor = persistor;
            this.pushServer = pushServer;
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
                        busy = msg.operate(this.persistor, this.pushServer);
                    }
                }
            } catch (IOException e) {
                // Treat an IOException as a termination of the message
                // exchange, and let this message-processing thread die.

                // CODE VON DOZENTEN...
            }
        }
    }
}
