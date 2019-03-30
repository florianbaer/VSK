package ch.hslu.vsk.logger.common.messagepassing;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.logger.common.messagepassing.messages.ResultMessage;

import java.io.InputStream;
import java.io.OutputStream;

public final class LoggerComHandler extends AbstractBasicMessageHandler {

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
     * Interpretiert Message ID und erzeugt ein entsprechendes Message-Objekt. Diese
     * Methode wird in der Super-Klasse in der Methode readMsg() aufgerufen.
     *
     * @param msgId MessageID.
     * @return Message
     */
    @Override
    protected AbstractBasicMessage buildMessage(String msgId) {
        AbstractBasicMessage message = null;

        if (msgId.compareTo("log") == 0) {
            message = new LogMessage();
        } else if (msgId.compareTo("result") == 0) {
            message = new ResultMessage();
        }
        return message;
    }


}
