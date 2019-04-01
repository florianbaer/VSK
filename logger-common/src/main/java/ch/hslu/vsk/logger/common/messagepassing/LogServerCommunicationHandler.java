package ch.hslu.vsk.logger.common.messagepassing;

import ch.hslu.vsk.logger.common.adapter.LogPersistor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

public final class LogServerCommunicationHandler extends AbstractBasicMessageHandler implements Runnable {

    private final LogPersistor persistor;

    /**
     * Konstruktor.
     *
     * @param inputStream  Inputstream.
     * @param outputStream Outputstream.
     */
    public LogServerCommunicationHandler(InputStream inputStream, OutputStream outputStream, LogPersistor persistor) {
        super(inputStream, outputStream);
        this.persistor = persistor;
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

        var message = this.getMessageTypes().stream().filter(x -> x.getMessageId().equals(msgId)).findFirst();

        if(!message.isEmpty()){
            return message.get();
        }
        return null;
    }


    /**
     * Zur Unterstützun des aysnchronen Nachrichten Austausches. Die run
     * Methode, und damit der Thread, wird beendet, wenn die Aktion einer
     * Nachricht false zurück gibt. Dies ist dann der Fall, wenn die letzte
     * Nachricht der Kommunikation abgearbeitet wurde.
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
