package ch.hslu.vsk.logger.common.messagepassing;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * BasicMessage.
 */
public abstract class AbstractBasicMessage {

    private String messageId;
    private final List<Object> argList = new ArrayList<>();

    /**
     * Definiert das Ende der Argumentenliste
     */
    protected static final String END_TOKEN = "END";

    /**
     * Default-Konstruktor. Er definiert den Nachrichten Typ.
     */
    public AbstractBasicMessage() {
        this.defineMessageType();
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
    public final void addArg(final Object arg) {
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
    public final List<Object> getArgList() {
        return this.argList;
    }

    /**
     * Alle Argumente aus dem Datenstrom auslesen und in die Argumentenliste
     * einfügen. Das Ende der Argumente definiert das END_TOKEN.
     *
     * @param ins InputStream.
     * @return Argumente gelesen = true.
     */
    public boolean readArgs(final InputStream ins) {
        final DataInputStream din = new DataInputStream(ins);
        try {
            String token = din.readUTF();
            while (token.compareTo(END_TOKEN) != 0) {
                addArg(token);
                token = din.readUTF();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Alle Argumente in den angegeben Datenstrom schreiben. Am Ende wird das
     * definierte Symbol END_TOKEN geschrieben.
     *
     * @param outs OutputStream.
     * @return Argumente fehlerfrei geschrieben = true.
     */
    public boolean writeArgs(final OutputStream outs) {
        final int len = argList.size();
        final DataOutputStream dout = new DataOutputStream(outs);
        try {
            for (int i = 0; i < len; i++) {
                final String arg = (String) argList.get(i);
                dout.writeUTF(arg);
            }
            dout.writeUTF(END_TOKEN);
        } catch (IOException e) {
            return false;
        }
        return true;
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

    /**
     * Definition des Nachrichten Typs.
     */
    protected abstract void defineMessageType();
}

