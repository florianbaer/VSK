package ch.hslu.vsk.logger.common.messagepassing;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * BasicMessageHandler.
 */
public abstract class AbstractBasicMessageHandler {

    /**
     * Statischer Message Handler für Anwendungen, bei denen nur ein Nachrichten
     * Handler verwendet wird. Er muss global zugänglich sein.
     */
    private static AbstractBasicMessageHandler current;
    private final InputStream msgIn;
    private final OutputStream msgOut;
    private static final String END_TOKEN = "END";
    private List<AbstractBasicMessage> messages = new ArrayList<>();

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
     * Gets the message types configured in the message handler.
     * @return the messages in a list.
     */
    public List<AbstractBasicMessage> getMessageTypes(){
        return this.messages;
    }

    /**
     * Adds a message type to the list of messages
     * @param messageType
     */
    public void addMessageType(AbstractBasicMessage messageType){
        this.messages.add(messageType);
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
     * Liest eine ankommende Nachricht und gibt das empfange Message
     * Objekt zurück.
     *
     * @return Message. Enthält die Nachricht und alle dazugehörigen Argumente.
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

                System.out.println("read utf" + token);

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
     * Schreibt die übergebene Nachricht in den Output-Stream und beendet sie mit dem
     * END_TOKEN.
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
        dataOutputStream.flush();
    }


    /**
     * Interpretiert Message ID und erzeugt ein entsprechendes Message-Objekt.
     *
     * @param msgId MessageID.
     * @return Message
     */
    protected abstract AbstractBasicMessage buildMessage(String msgId);
}

