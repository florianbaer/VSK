package ch.hslu.vsk.logger.viewer;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.logger.common.rmi.viewer.Viewer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for the LogViewer.
 */
public class LogViewerModel implements Viewer {
    private final List<TableEntryOfLogMessages> listOfLogMessages = new ArrayList<>();
    private final ObservableList<TableEntryOfLogMessages> observableList =
            FXCollections.synchronizedObservableList(FXCollections.observableList(this.listOfLogMessages));

    /**
     * Returns the list of log messages.
     * @return LogMessags to display
     */
    public ObservableList<TableEntryOfLogMessages> getListOfLogMessages() {
        return this.observableList;
    }


    /**
     * Method that gets called when a log message is pushed via RMI.
     * @param logMessage that is pushed
     */
    @Override
    public void sendLogMessage(LogMessage logMessage) {
        String[] msgText = logMessage.getArgList().toArray(new String[0]);

        String[] args = msgText[0].split("\\|");
        this.observableList.add(new TableEntryOfLogMessages(args[0], args[0],args[1], args[2], args[3], args[4]));
    }
}
