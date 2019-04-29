package ch.hslu.vsk.logger.viewer;

import javafx.beans.property.SimpleStringProperty;

/**
 * Class that represents a table entry. This class is necessary because our LogMessage-class is not an DTO.
 */
public class TableEntryOfLogMessages {
    private SimpleStringProperty serverTimestamp;
    private SimpleStringProperty logTimestamp;
    private SimpleStringProperty identifier;
    private SimpleStringProperty logLevel;
    private SimpleStringProperty loggingClass;
    private SimpleStringProperty logMessage;

    public TableEntryOfLogMessages(String serverTimestamp, String logTimestamp, String identifier, String logLevel,
                                   String loggingClass, String logMessage) {
        this.serverTimestamp = new SimpleStringProperty(serverTimestamp);
        this.logTimestamp = new SimpleStringProperty(logTimestamp);
        this.identifier = new SimpleStringProperty(identifier);
        this.logLevel = new SimpleStringProperty(logLevel);
        this.loggingClass = new SimpleStringProperty(loggingClass);
        this.logMessage = new SimpleStringProperty(logMessage);
    }

    public String getLoggingClass() {
        return this.loggingClass.get();
    }

    public void setLoggingClass(String loggingClass) {
        this.loggingClass = new SimpleStringProperty(loggingClass);
    }

    public String getLogMessage() {
        return this.logMessage.get();
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = new SimpleStringProperty(logMessage);
    }

    public String getLogLevel() {
        return this.logLevel.get();
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = new SimpleStringProperty(logLevel);
    }

    public String getServerTimestamp() {
        return serverTimestamp.get();
    }

    public void setServerTimestamp(String timestamp) {
        this.serverTimestamp = new SimpleStringProperty(timestamp);
    }

   public String getLogTimestamp() {
        return this.logTimestamp.get();
   }

   public void setLogTimestamp(String timestamp) {
        this.logTimestamp = new SimpleStringProperty(timestamp);
   }

   public String getIdentifier() {
        return this.identifier.get();
   }

   public void setIdentifier(String identifier) {
        this.identifier = new SimpleStringProperty(identifier);
   }



}
