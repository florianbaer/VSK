package ch.hslu.vsk.logger.common.messagepassing;

import ch.hslu.vsk.logger.common.adapter.LogPersistor;
import ch.hslu.vsk.logger.common.rmi.server.PushServer;

import java.util.Vector;

/**
 * BaisMessage class. A basic message consists of an optional identifier and a list of arguments.
 * Both in the String format.
 */
public abstract class AbstractBasicMessage {

    private String messageId;
    private Vector<String> argList;

    /**
     * Construcotr with initial message id.
     * @param id id of the message
     */
    public AbstractBasicMessage(final String id) {
        this.messageId = id;
        this.argList = new Vector<>();
    }

    /**
     * Add a argument to the message's argument list.
     *
     * @param arg argument to add
     */
    public final void addArg(final String arg) {
        this.argList.add(arg);
    }

    /**
     * Message identifier.
     *
     * @return id MessageID.
     */
    public final String getMessageId() {
        return messageId;
    }

    /**
     * Argument list of the message.
     *
     * @return Argumentliste.
     */
    public final Vector<String> getArgList() {
        return this.argList;
    }

    /**
     * This methods is responsible for determing actions after it was received.
     * Add your logic here.
     * @param persistor LogPersistor to use
     *
     * @return true if successfull
     */
    public abstract boolean operate(LogPersistor persistor, PushServer server);
}

