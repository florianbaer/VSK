package ch.hslu.vsk.logger.common.messagepassing;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.logger.common.messagepassing.messages.ResultMessage;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

public final class LogCommunicationHandler extends AbstractBasicMessageHandler {

    /**
     * Konstruktor.
     *
     * @param inputStream  Inputstream.
     * @param outputStream Outputstream.
     */
    public LogCommunicationHandler(InputStream inputStream, OutputStream outputStream) {
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

        Optional<AbstractBasicMessage> message = this.getMessageTypes().stream().filter(x -> x.getMessageId() == msgId).findFirst();

        if (!message.isEmpty()) {
            return message.get();
        }
        return null;
    }
}
