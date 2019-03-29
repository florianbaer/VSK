package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.common.messagepassing.AbstractBasicMessage;
import ch.hslu.vsk.logger.common.messagepassing.AbstractBasicMessageHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogMessage extends AbstractBasicMessage {

    private static final Logger LOG = LogManager.getLogger(LogMessage.class);
    private static final String LOG_TYPE = "log";

    /**
     * Default-Konstruktor.
     */
    public LogMessage() {
        super();
    }

    /**
     * Konstruktor.
     *
     * @param logMessage Log der übertragen werden soll
     */
    public LogMessage(final String logMessage) {
        this.addArg(logMessage);
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
        //TODO implement operate method
        final AbstractBasicMessageHandler handler = AbstractBasicMessageHandler.getMessageHandler();
        final String value = (String) this.getArgList().get(0);

        return false;
    }

    /**
     * Definition des Nachrichten Typs.
     */
    @Override
    protected void defineMessageType() {
        this.setMessageId(LOG_TYPE);
    }
}
