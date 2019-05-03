package ch.hslu.vsk.logger.common.messagepassing;

import javax.naming.OperationNotSupportedException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;


/**
 * BasicMessageHandler.
 */
public abstract class AbstractBasicMessageHandler {
    private static AbstractBasicMessageHandler current;
    private final InputStream msgIn;
    private final OutputStream msgOut;
    private static final String END_TOKEN = "END";

    /**
     * Constructor.
     *
     * @param inputStream Inputstream
     * @param outputStream Outputstream
     */
    public AbstractBasicMessageHandler(final InputStream inputStream, final OutputStream outputStream) {
        msgIn = inputStream;
        msgOut = outputStream;
        current = this;
    }

    /**
     * Returns the static message handler object.
     * @return current
     */
    public static AbstractBasicMessageHandler getMessageHandler() {
        return current;
    }

    /**
     * Reads an incoming message and returns it.
     *
     * @return Message with arguments
     * @throws IOException that can occur during reading
     */
    public final AbstractBasicMessage readMsg() throws IOException, OperationNotSupportedException {
        final DataInputStream din = new DataInputStream(msgIn);
        String token = din.readUTF();
        AbstractBasicMessage msg = null;

        msg = buildMessage(token);

        if (msg != null) {
            boolean endOfMessage = false;

            while (!endOfMessage) {
                token = din.readUTF();

                if (token.compareTo(END_TOKEN) == 0) {
                    endOfMessage = true;
                } else {
                    msg.addArg(token);
                }
            }
        }
        return msg;
    }

    /**
     * Writes the given message to the output-stream and marks it's end with the END_TOKEN.
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
        for (int i = 0; i < argSize; i++) {
            dataOutputStream.writeUTF(arguments.elementAt(i));
        }

        // wenn alle Argumente durch, setze END_Token
        dataOutputStream.writeUTF(END_TOKEN);
        dataOutputStream.flush();
    }


    /**
     * Build a message with the given id.
     *
     * @param msgId MessageID.
     * @return Message
     */
    protected abstract AbstractBasicMessage buildMessage(String msgId) throws OperationNotSupportedException;
}

