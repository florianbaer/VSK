package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.common.adapter.LogPersistor;
import ch.hslu.vsk.logger.common.messagepassing.AbstractBasicMessage;
import ch.hslu.vsk.logger.common.messagepassing.AbstractBasicMessageHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Klasse welche eine Log-Nachricht darstellt.
 */
public class LogMessage extends AbstractBasicMessage {

    private static final String MESSAGE_ID = "log";

    /**
     * Default Konstruktor
     */
    public LogMessage(){
        super(MESSAGE_ID);
    }

    /**
     * Konstruktor.
     *
     * @param logMessage Log der übertragen werden soll
     */
    public LogMessage(final String logMessage) {
        super(MESSAGE_ID);
        this.addArg(logMessage);
    }



    /**
     * Die Implementierung der Methode operate verküpft die Methoden des
     * Applikationsobjektes mit dem Protokoll des Message-Passing-Systems. Sie
     * bestimmt die Aktionen, welche laut dem Protokoll der Nachrichten
     * Kommunikation auszuführen sind. Der Rückgabewert bestimmt, ob der
     * Nachrichten Austausch beendet wird oder weiterläuft. Wird nur für die Unterstützung
     * des asynchronen Teils benötigt
     *
     * @return Boolscher Wert, wenn false war dies die letzte Nachricht der
     * Kommunikation mit dem CLient.
     */
    @Override
    public boolean operate(LogPersistor persistor) {
        if(persistor != null){

            persistor.save(this);
        }

        return true;
    }
}
