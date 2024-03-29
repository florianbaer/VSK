package ch.hslu.vsk.logger.common.messagepassing;

import ch.hslu.vsk.logger.common.adapter.LogPersistor;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.logger.common.rmi.server.RegistrationService;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Communication handler that is responsible for the server-side communication.
 */
public final class LogServerCommunicationHandler extends AbstractBasicMessageHandler implements Runnable {

    private final LogPersistor persistor;
    private RegistrationService pushServer;

    /**
     * Constructor.
     *  @param inputStream  Inputstream
     * @param outputStream Outputstream
     * @param persistor LogPersistor
     * @param pushServer RegistrationServer
     */
    public LogServerCommunicationHandler(final InputStream inputStream, final OutputStream outputStream,
                                         final LogPersistor persistor, final RegistrationService pushServer) {
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
    protected AbstractBasicMessage buildMessage(final String msgId) throws OperationNotSupportedException {
        if (msgId.equals("log")) {
            return new LogMessage();
        }
        throw new OperationNotSupportedException("Only the log message is implemented");
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
        } catch (OperationNotSupportedException ex) {
            System.out.println("Error while reading incoming message");
            ex.printStackTrace();
        }
    }
}
