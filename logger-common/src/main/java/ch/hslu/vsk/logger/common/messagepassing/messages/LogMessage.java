package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.common.DTO.LogMessageDTO;
import ch.hslu.vsk.logger.common.adapter.LogPersistor;
import ch.hslu.vsk.logger.common.messagepassing.AbstractBasicMessage;
import ch.hslu.vsk.logger.common.rmi.server.RegistrationServer;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.time.Instant;
import java.util.Vector;

/**
 * Klasse welche eine Log-Nachricht darstellt.
 */
public class LogMessage extends AbstractBasicMessage {

    private static final String MESSAGE_ID = "log";

    /**
     * Default Konstruktor.
     */
    public LogMessage() {
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
    public boolean operate(final LogPersistor persistor, RegistrationServer notifier) {
        final Instant now = Instant.now();

        if (persistor != null) {
            persistor.save(now, this);
        }
        if(notifier != null) {
            try {
                notifier.notifyViewers(LogMessageDTO.fromLogMessage(now, this));
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException ex){
                System.out.println("MessagePassing did not pass all the used data to the server. Skipping this message.");
            }
        }

        return true;
    }


    /**
     * Returns message Text. Currently message text is always the second element in the argument list
     * @return first Argument of Arg-List
     */
    public String getMessageText() {
        Vector<String> args = super.getArgList();

        return String.valueOf(args.get(0));
    }
}
