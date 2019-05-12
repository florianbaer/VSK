package ch.hslu.vsk.logger.common.DTO;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;

import java.io.Serializable;
import java.time.Instant;

/**
 * Represents a data transfer object for the log message.
 */
public class LogMessageDTO implements Serializable {
    private String serverTimestamp;
    private String logTimestamp;
    private String identifier;
    private String logLevel;
    private String loggingClass;
    private String logMessage;

    /**
     * Constructor.
     * @param serverTimestamp to create the instance.
     * @param logTimestamp to create the instance.
     * @param identifier to create the instance.
     * @param logLevel to create the instance.
     * @param loggingClass to create the instance.
     * @param logMessage to create the instance.
     */
    public LogMessageDTO(final String serverTimestamp, final String logTimestamp, final String identifier,
                                   final String logLevel,
                                   final String loggingClass, final String logMessage) {
        this.setServerTimestamp(serverTimestamp);
        this.setLogTimestamp(logTimestamp);
        this.setIdentifier(identifier);
        this.setLogLevel(logLevel);
        this.setLoggingClass(loggingClass);
        this.setLogMessage(logMessage);
    }

    /**
     * Creates a log message form parameters.
     * @param serverTimestamp The server timestamp.
     * @param message The message.
     * @return The log message data transfer object.
     */
    public static LogMessageDTO fromLogMessage(final Instant serverTimestamp, final LogMessage message) {
        String[] msgText = message.getArgList().toArray(new String[0]);

        String[] args = msgText[0].split("\\|");
        return new LogMessageDTO(serverTimestamp.toString(), args[0], args[1], args[2],
                args[3], args[4]);
    }

    /**
     * Gets the server timestamp.
     * @return the server timestamp.
     */
    public String getServerTimestamp() {
        return serverTimestamp;
    }

    /**
     * Sets the server timestamp.
     * @param serverTimestamp Ther server timestamp to set.
     */
    public void setServerTimestamp(final String serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    /**
     * Gets the log timestamp.
     * @return The log timestamp.
     */
    public String getLogTimestamp() {
        return logTimestamp;
    }

    /**
     * Sets the log timestamp.
     * @param logTimestamp The log timestamp to set.
     */
    public void setLogTimestamp(final String logTimestamp) {
        this.logTimestamp = logTimestamp;
    }

    /**
     * Gets the logger identifier.
     * @return The logger identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier.
     * @param identifier The identifier to set.
     */
    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the log level.
     * @return The log level.
     */
    public String getLogLevel() {
        return logLevel;
    }

    /**
     * Sets the log level.
     * @param logLevel The log level to set.
     */
    public void setLogLevel(final String logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * Gets the logging class.
     * @return The logging class.
     */
    public String getLoggingClass() {
        return loggingClass;
    }

    /**
     * Sets the logging class.
     * @param loggingClass the logging class to set.
     */
    public void setLoggingClass(final String loggingClass) {
        this.loggingClass = loggingClass;
    }

    /**
     * Gets the log message.
     * @return The log message.
     */
    public String getLogMessage() {
        return logMessage;
    }

    /**
     * Sets the log message.
     * @param logMessage The log message to set.
     */
    public void setLogMessage(final String logMessage) {
        this.logMessage = logMessage;
    }
}
