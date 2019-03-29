package ch.hslu.vsk.logger.common.messagepassing;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.logger.common.messagepassing.messages.ResultMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;

public final class LoggerComHandler extends AbstractBasicMessageHandler {

    private static final Logger LOG = LogManager.getLogger(LoggerComHandler.class);

    /**
     * Konstruktor.
     *
     * @param inputStream  Inputstream.
     * @param outputStream Outputstream.
     */
    public LoggerComHandler(InputStream inputStream, OutputStream outputStream) {
        super(inputStream, outputStream);
    }

    /**
     * Interpretiert Message ID und erzeugt ein entsprechendes Message-Objekt.
     *
     * @param msgId MessageID.
     * @return Message
     */
    @Override
    protected AbstractBasicMessage buildMessage(String msgId) {
        AbstractBasicMessage message = null;
        LOG.info("Got message type: " + msgId);
        if (msgId.compareTo("log") == 0) {
            message = new LogMessage();
        } else if (msgId.compareTo("result") == 0) {
            message = new ResultMessage();
        }
        return message;
    }


}
