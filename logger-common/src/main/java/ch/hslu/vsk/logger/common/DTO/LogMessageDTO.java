package ch.hslu.vsk.logger.common.DTO;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;

import java.io.Serializable;
import java.time.Instant;

public class LogMessageDTO implements Serializable {
    private String serverTimestamp;
    private String logTimestamp;
    private String identifier;
    private String logLevel;
    private String loggingClass;
    private String logMessage;

    /**
     * Constructor.
     * @param serverTimestamp to use
     * @param logTimestamp to use
     * @param identifier to use
     * @param logLevel to use
     * @param loggingClass to use
     * @param logMessage to use
     */
    public LogMessageDTO(final String serverTimestamp, final String logTimestamp, final String identifier,
                                   final String logLevel,
                                   final String loggingClass, final String logMessage) {
        this.serverTimestamp = serverTimestamp;
        this.logTimestamp = logTimestamp;
        this.identifier = identifier;
        this.logLevel = logLevel;
        this.loggingClass = loggingClass;
        this.logMessage = logMessage;
    }

    public static LogMessageDTO fromLogMessage(final Instant serverTimestamp, LogMessage message) {
        String[] msgText = message.getArgList().toArray(new String[0]);

        String[] args = msgText[0].split("\\|");
        return new LogMessageDTO(serverTimestamp.toString(), args[0], args[1], args[2],
                args[3], args[4]);
    }

    public String getServerTimestamp() {
        return serverTimestamp;
    }

    public void setServerTimestamp(String serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    public String getLogTimestamp() {
        return logTimestamp;
    }

    public void setLogTimestamp(String logTimestamp) {
        this.logTimestamp = logTimestamp;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLoggingClass() {
        return loggingClass;
    }

    public void setLoggingClass(String loggingClass) {
        this.loggingClass = loggingClass;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }
}
