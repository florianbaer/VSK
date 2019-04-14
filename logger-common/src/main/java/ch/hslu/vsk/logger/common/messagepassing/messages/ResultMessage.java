package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.common.adapter.LogPersistor;
import ch.hslu.vsk.logger.common.messagepassing.AbstractBasicMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Vector;

/**
 * Klasse welche momentan als Platzhalter fungiert. Für ein Nachrichten-Austausch mit Feedback gedacht.
 */
public class ResultMessage extends AbstractBasicMessage {

    private static final String MESSAGE_ID = "result";

    public ResultMessage(){
        super(MESSAGE_ID);
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
    public boolean operate(LogPersistor persistor) {
        return false;
    }

    /**
     * Konstruktor.
     *
     * @param value Resultatwert der LogMessage.
     * @param param Argument.
     */
    public ResultMessage(final String value, final String param) {
        super(MESSAGE_ID);
        this.addArg(value);
        this.addArg(param);
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
