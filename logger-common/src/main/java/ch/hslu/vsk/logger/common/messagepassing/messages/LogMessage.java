package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.common.adapter.LogPersistor;
import ch.hslu.vsk.logger.common.messagepassing.AbstractBasicMessage;
import ch.hslu.vsk.logger.common.rmi.server.PushServer;


import java.rmi.RemoteException;
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
    public boolean operate(final LogPersistor persistor, PushServer server) {
        if (persistor != null) {
            persistor.save(this);
        }
        if(server != null) {
            try {
                server.getAllViewers().stream().forEach(x -> {
                    try {
                        x.sendLogMessage(this);
                    } catch (RemoteException ex) {
                        System.out.println("Send message to viewer failed...");
                    }
                });
            } catch (RemoteException ex) {
                System.out.println("Send message to viewer failed...");
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
