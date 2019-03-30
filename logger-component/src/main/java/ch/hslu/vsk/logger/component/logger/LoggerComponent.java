package ch.hslu.vsk.logger.component.logger;


import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.common.messagepassing.LoggerComHandler;
import ch.hslu.vsk.logger.component.services.NetworkService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a logger which can be used to log messages with different log-levels. To obtain a instance
 * of this class, the LoggerComponentSetup-class should be used.
 *
 * @author Matthias Egli, David Gut, Florian Bär, Dennis Dekker
 * @version 0.5.0
 */
public class LoggerComponent implements Logger {

    private LogLevel logLevel;
    private String connectionString;
    private String identifier;
    private Class loggerClass;
    private NetworkService service;

    public LoggerComponent(LogLevel level, String connectionString, String identifier, Class clazz) {
        this.logLevel = level;
        this.connectionString = connectionString;
        this.identifier = identifier;
        this.loggerClass = clazz;
        this.service = NetworkService.getInstance();

        // If the connection-String was obtained using the properties file
        // or by setting it while creating, we need to update the NetworkService accordingly
        // so the connection-properties match those of the LoggerComponent
        if(connectionString.contains(":")){
            String[] connDetails = this.connectionString.split(":");
            if (!connDetails[0].equals("") && connDetails[1].equals("")){
                service.changeConnectionDetails(connDetails[0], Integer.valueOf(connDetails[1]));
            }
        }
    }

    @Override
    public void debug(String s) {
        if (isLogLevelHighEnough(LogLevel.DEBUG)) {
            service.sendLogMessageToServer(getLogOutputPattern(LogLevel.DEBUG.toString(), s));
        }

    }

    @Override
    public void debug(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.DEBUG)) {
            service.sendLogMessageToServer(getLogOutputPattern(LogLevel.DEBUG.toString(), s, throwable));
        }
    }

    @Override
    public void info(String s) {
        if (isLogLevelHighEnough(LogLevel.INFO)) {
            service.sendLogMessageToServer(getLogOutputPattern(LogLevel.INFO.toString(), s));
        }

    }

    @Override
    public void info(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.INFO)) {
            service.sendLogMessageToServer(getLogOutputPattern(LogLevel.INFO.toString(), s, throwable));
        }

    }

    @Override
    public void warning(String s) {
        if (isLogLevelHighEnough(LogLevel.WARNING)) {
            service.sendLogMessageToServer(getLogOutputPattern(LogLevel.WARNING.toString(), s));
        }

    }

    @Override
    public void warning(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.WARNING)) {
            service.sendLogMessageToServer(getLogOutputPattern(LogLevel.WARNING.toString(), s, throwable));
        }
    }

    @Override
    public void error(String s) {
        if (isLogLevelHighEnough(LogLevel.ERROR)) {
            service.sendLogMessageToServer(getLogOutputPattern(LogLevel.ERROR.toString(), s));
        }

    }

    @Override
    public void error(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.ERROR)) {
            service.sendLogMessageToServer(getLogOutputPattern(LogLevel.ERROR.toString(), s, throwable));
        }

    }

    @Override
    public void critical(String s) {
        if (isLogLevelHighEnough(LogLevel.CRITICAL)) {
            service.sendLogMessageToServer(getLogOutputPattern(LogLevel.CRITICAL.toString(), s));
        }

    }

    @Override
    public void critical(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.CRITICAL)) {
            service.sendLogMessageToServer(getLogOutputPattern(LogLevel.CRITICAL.toString(), s, throwable));
        }
    }

    @Override
    public void log(LogLevel logLevel, String s) {
        switch (logLevel) {
            case OFF:
                break;
            case INFO:
                this.info(s);
                break;
            case DEBUG:
                this.debug(s);
                break;
            case ERROR:
                this.error(s);
                break;
            case WARNING:
                this.warning(s);
                break;
            case CRITICAL:
                this.critical(s);
                break;
        }

    }

    @Override
    public void log(LogLevel logLevel, String s, Throwable throwable) {
        switch (logLevel) {
            case OFF:
                break;
            case INFO:
                this.info(s, throwable);
                break;
            case DEBUG:
                this.debug(s, throwable);
                break;
            case ERROR:
                this.error(s, throwable);
                break;
            case WARNING:
                this.warning(s, throwable);
                break;
            case CRITICAL:
                this.critical(s, throwable);
                break;
        }
    }

    @Override
    public void setMinLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public LogLevel getMinLogLevel() {
        return this.logLevel;
    }

    public String getConnectionString(){return this.connectionString;}

    public String getIdentifier(){return this.identifier;}

    public Class getLoggingClass(){return this.loggerClass;}


    // All following Methods are just a workaround because the server side is missing at the moment

    /**
     * method which is used as long as the logger-server is not implemented
     *
     * @param messageToPrint
     */
    private void printInTerminal(final String messageToPrint) {
        System.out.println(messageToPrint);
    }

    public String getCurrentDateAndTime() { //public because its used in tests
        SimpleDateFormat swissFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date currentDate = new Date();
        return swissFormat.format(currentDate);
    }


    private String getLogOutputPattern(final String loggertyp, final String message) {
        return getCurrentDateAndTime() + " " + loggertyp + " " + this.getClass().getSimpleName() + " " + message;
    }

    //TODO Choose how to output the throwable object in the log message --> .toString() für den Moment.
    private String getLogOutputPattern(final String loggertyp, final String message, final Throwable throwable) {
        return getCurrentDateAndTime() + " " + loggertyp + " " + this.getClass().getSimpleName() + " " + message + " " + throwable.toString();
    }

    private boolean isLogLevelHighEnough(final LogLevel logLevelOfMethod) {
        return logLevelOfMethod.ordinal() >= (this.logLevel).ordinal();
    }
}
