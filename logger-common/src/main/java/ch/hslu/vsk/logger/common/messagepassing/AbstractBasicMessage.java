package ch.hslu.vsk.logger.common.messagepassing;

import ch.hslu.vsk.logger.common.adapter.LogPersistor;

import java.util.Vector;

/**
 * BaisMessage class. A basic message consists of an optional identifier and a list of arguments.
 * Both in the String format.
 */
public abstract class AbstractBasicMessage {

    private String messageId;
    private Vector<String> argList;

    /**
     * Default message constructor without message id
     */
    public AbstractBasicMessage() {
        this.argList = new Vector<>();
    }

    /**
     * Construcotr with initial message id
     * @param id id of the message
     */
    public AbstractBasicMessage(String id){
        this.messageId = id;
        this.argList = new Vector<>();
    }

    /**
     * Set the message's identifier
     *
     * @param msgId MessageID.
     */
    protected final void setMessageId(final String msgId) {
        this.messageId = msgId;
    }

    /**
     * Add a argument to the message's argument list
     *
     * @param arg argument to add
     */
    public final void addArg(final String arg) {
        this.argList.add(arg);
    }

    /**
     * Message identifier
     *
     * @return id MessageID.
     */
    public final String getMessageId() {
        return messageId;
    }

    /**
     * Argument list of the message
     *
     * @return Argumentliste.
     */
    public final Vector<String> getArgList() {
        return this.argList;
    }

    /**
     * This methods is responsible for determing actions after it was received.
     * Add your logic here.
     *
     * @return True if logic defined in operate() was successful
     */
    public abstract boolean operate(LogPersistor persistor);
}

