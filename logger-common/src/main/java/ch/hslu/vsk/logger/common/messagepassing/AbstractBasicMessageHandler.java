package ch.hslu.vsk.logger.common.messagepassing;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BasicMessageHandler.
 */
public abstract class AbstractBasicMessageHandler implements Runnable {

    private static final Logger LOG = LogManager.getLogger(AbstractBasicMessageHandler.class);

    /**
     * Statischer Message Handler für Anwendungen, bei denen nur ein Nachrichten
     * Handler verwendet wird. Er muss global zugänglich sein.
     */
    private static AbstractBasicMessageHandler current;
    private final InputStream msgIn;
    private final OutputStream msgOut;
    private static final String END_TOKEN = ";END";

    /**
     * Konstruktor.
     *
     * @param inputStream Inputstream.
     * @param outputStream Outputstream.
     */
    public AbstractBasicMessageHandler(final InputStream inputStream, final OutputStream outputStream) {
        msgIn = inputStream;
        msgOut = outputStream;
        current = this;
    }

    /**
     * Stellt den statischen Message Handler zur Verfügung für Anwendungen, bei
     * denen nur ein Nachrichten Handler verwendet wird. Punkt-zu-Punkt
     * Kommunikation.
     *
     * @return current.
     */
    public static AbstractBasicMessageHandler getMessageHandler() {
        return current;
    }

    /**
     * Liest die ankommende Nachricht vom Sender und gibt das empfange Message
     * Objekt zurück.
     *
     * @return Message. Enthält die Nachricht und alle dazugehörigen Argumente.
     * Falls im Datenstrom eine nicht existierende Nachrichten-Identifikation
     * war, wird null zurück gegeben.
     * @throws IOException IO-Fehler.
     */
    public final AbstractBasicMessage readMsg() throws IOException {
        final DataInputStream din = new DataInputStream(msgIn);
        String token = din.readUTF();
        final AbstractBasicMessage msg = buildMessage(token);

        if (msg != null) {
            boolean endOfMessage = false;

            while (!endOfMessage){
                token = din.readUTF();

                if(token.compareTo(END_TOKEN) == 0){
                    endOfMessage = true;
                } else {
                    msg.addArg(token);
                }
            }
        }
        return msg;
    }

    /**
     * TODO
     *
     * @param msg Message.
     * @throws IOException IO-Fehler.
     */
    public final void sendMsg(final AbstractBasicMessage msg) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(msgOut);

        dataOutputStream.writeUTF(msg.getMessageId());

        // hole alle Argumente vom Message-Objekt
        Vector<String> arguments = msg.getArgList();
        int argSize = arguments.size();

        // schreibe alle Argumente in den DataOutputStream
        for (int i = 0; i < argSize; i ++){
            dataOutputStream.writeUTF(arguments.elementAt(i));
        }

        // wenn alle Argumente durch, setze END_Token
        dataOutputStream.writeUTF(END_TOKEN);
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
                    busy = msg.operate();
                }
            }
        } catch (IOException e) {
            // Treat an IOException as a termination of the message
            // exchange, and let this message-processing thread die.
            LOG.debug("termination of the message");
        }
    }

    /**
     * Interpretiert Message ID und erzeugt ein entsprechendes Message-Objekt.
     *
     * @param msgId MessageID.
     * @return Message
     */
    protected abstract AbstractBasicMessage buildMessage(String msgId);
}

