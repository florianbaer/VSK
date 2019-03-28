package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.common.messagepassing.AbstractBasicMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResultMessage extends AbstractBasicMessage {

    private static final Logger LOG = LogManager.getLogger(ResultMessage.class);
    private static final String RESULT_TYPE = "result";

    /**
     * Default-Konstruktor.
     */
    public ResultMessage() {
        super();
    }

    /**
     * Konstruktor.
     *
     * @param value Resultatwert der Fibonacci Berechnung.
     * @param param Fibonacci Argument.
     */
    public ResultMessage(final String value, final String param) {
        this.addArg(value);
        this.addArg(param);
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
    @Override
    public boolean operate() {

        //TODO implement
        return false;
    }

    /**
     * Definition des Nachrichten Typs.
     */
    @Override
    protected void defineMessageType() {
        this.setMessageId(RESULT_TYPE);

    }
}
