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
 * BasicMessage-Klasse. Eine Basic-Message besteht aus einem (optionalen) Identifier und einer
 * Argumenten-Liste. Beide werden als Strings dargestellt.
 */
public abstract class AbstractBasicMessage {

    private String messageId;
    private Vector<String> argList;

    /**
     * Definiert das Ende der Argumentenliste
     */
    protected static final String END_TOKEN = ";END";

    /**
     * Default-Konstruktor ohne initiale messageId
     */
    public AbstractBasicMessage() {
        this.argList = new Vector<>();
    }

    /**
     * Konstruktor mit initialer messageId
     * @param id id of the message
     */
    public AbstractBasicMessage(String id){
        this.messageId = id;
        this.argList = new Vector<>();
    }

    /**
     * Identifikation der Nachricht definieren.
     *
     * @param msgId MessageID.
     */
    protected final void setMessageId(final String msgId) {
        this.messageId = msgId;
    }

    /**
     * Ein Argument der Argumentenliste der Nachricht hinzufügen.
     *
     * @param arg Argumentobjekt.
     */
    public final void addArg(final String arg) {
        this.argList.add(arg);
    }

    /**
     * Identifikation der Nachricht.
     *
     * @return id MessageID.
     */
    public final String getMessageId() {
        return messageId;
    }

    /**
     * Argumentenliste der Nachricht.
     *
     * @return Argumentliste.
     */
    public final Vector<String> getArgList() {
        return this.argList;
    }

    /**
     * Die Implementierung der Methode operate verküpft die Methoden des
     * Applikationsobjektes mit dem Protokoll des Message-Passing-Systems. Sie
     * bestimmt die Aktionen, welche laut dem Protokoll der Nachrichten
     * Kommunikation auszuführen sind. Der Rückgabewert bestimmt, ob der
     * Nachrichten Austausch beendet wird oder weiterläuft.
     *
     * @return Boolscher Wert, wenn false war dies die letzte Nachricht der
     * Kommunikation mit dem CLient.
     */
    public abstract boolean operate();
}

