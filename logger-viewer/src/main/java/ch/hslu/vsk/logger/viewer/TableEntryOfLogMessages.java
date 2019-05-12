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

    /**
     * Constructor.
     * @param serverTimestamp to use
     * @param logTimestamp to use
     * @param identifier to use
     * @param logLevel to use
     * @param loggingClass to use
     * @param logMessage to use
     */
    public TableEntryOfLogMessages(final String serverTimestamp, final String logTimestamp, final String identifier,
                                   final String logLevel,
                                   final String loggingClass, final String logMessage) {
        this.serverTimestamp = new SimpleStringProperty(serverTimestamp);
        this.logTimestamp = new SimpleStringProperty(logTimestamp);
        this.identifier = new SimpleStringProperty(identifier);
        this.logLevel = new SimpleStringProperty(logLevel);
        this.loggingClass = new SimpleStringProperty(loggingClass);
        this.logMessage = new SimpleStringProperty(logMessage);
    }

    /**
     * Getter.
     * @return loggingClass
     */
    public String getLoggingClass() {
        return this.loggingClass.get();
    }

    /**
     * Setter.
     * @param loggingClass to set
     */
    public void setLoggingClass(final String loggingClass) {
        this.loggingClass = new SimpleStringProperty(loggingClass);
    }

    /**
     * Getter.
     * @return logMessage
     */
    public String getLogMessage() {
        return this.logMessage.get();
    }

    /**
     * Setter.
     * @param logMessage to set
     */
    public void setLogMessage(final String logMessage) {
        this.logMessage = new SimpleStringProperty(logMessage);
    }

    /**
     * Getter.
     * @return logLevel
     */
    public String getLogLevel() {
        return this.logLevel.get();
    }

    /**
     * Setter.
     * @param logLevel to set
     */
    public void setLogLevel(final String logLevel) {
        this.logLevel = new SimpleStringProperty(logLevel);
    }

    /**
     * Getter.
     * @return serverTimestamp
     */
    public String getServerTimestamp() {
        return serverTimestamp.get();
    }

    /**
     * Setter.
     * @param timestamp to set
     */
    public void setServerTimestamp(final String timestamp) {
        this.serverTimestamp = new SimpleStringProperty(timestamp);
    }

    /**
     * Getter.
     * @return logTimestamp
     */
   public String getLogTimestamp() {
        return this.logTimestamp.get();
   }

    /**
     * Setter.
     * @param timestamp to set
     */
   public void setLogTimestamp(final String timestamp) {
        this.logTimestamp = new SimpleStringProperty(timestamp);
   }

    /**
     * Getter.
     * @return identifier
     */
   public String getIdentifier() {
        return this.identifier.get();
   }

    /**
     * Setter.
     * @param identifier to set
     */
   public void setIdentifier(final String identifier) {
        this.identifier = new SimpleStringProperty(identifier);
   }



}
