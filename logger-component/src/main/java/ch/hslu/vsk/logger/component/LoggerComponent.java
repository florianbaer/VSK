package ch.hslu.vsk.logger.component;


import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Matthias Egli, David Gut, Florian Bär, Dennis Dekker
 * @version 0.1
 */
public class LoggerComponent implements Logger {

    private LogLevel logLevel; //Default Value = DEBUG

    //needs adjustment when implementing LoggerSetup interface
    public LoggerComponent() {
        this.logLevel = LogLevel.DEBUG;
    }

    @Override
    public void debug(String s) {
        if (isLogLevelHighEnough(LogLevel.DEBUG)) {
            printInTerminal(getLogOutputPattern(LogLevel.DEBUG.toString(), s));
        }

    }

    @Override
    public void debug(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.DEBUG)) {
            printInTerminal(getLogOutputPattern(LogLevel.DEBUG.toString(), s, throwable));
        }
    }

    @Override
    public void info(String s) {
        if (isLogLevelHighEnough(LogLevel.INFO)) {
            printInTerminal(getLogOutputPattern(LogLevel.INFO.toString(), s));
        }

    }

    @Override
    public void info(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.INFO)) {
            printInTerminal(getLogOutputPattern(LogLevel.INFO.toString(), s, throwable));
        }

    }

    @Override
    public void warning(String s) {
        if (isLogLevelHighEnough(LogLevel.WARNING)) {
            printInTerminal(getLogOutputPattern(LogLevel.WARNING.toString(), s));
        }

    }

    @Override
    public void warning(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.WARNING)) {
            printInTerminal(getLogOutputPattern(LogLevel.WARNING.toString(), s));
        }
    }

    @Override
    public void error(String s) {
        if (isLogLevelHighEnough(LogLevel.ERROR)) {
            printInTerminal(getLogOutputPattern(LogLevel.ERROR.toString(), s));
        }

    }

    @Override
    public void error(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.ERROR)) {
            printInTerminal(getLogOutputPattern(LogLevel.ERROR.toString(), s, throwable));
        }

    }

    @Override
    public void critical(String s) {
        if (isLogLevelHighEnough(LogLevel.CRITICAL)) {
            printInTerminal(getLogOutputPattern(LogLevel.CRITICAL.toString(), s));
        }

    }

    @Override
    public void critical(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.CRITICAL)) {
            printInTerminal(getLogOutputPattern(LogLevel.CRITICAL.toString(), s, throwable));
        }
    }

    @Override
    public void log(LogLevel logLevel, String s) {
        switch (logLevel) {
            case OFF:
                //TODO: Was ist der Sinn dieses Cases, wenn ja bei OFF nichts passieren sollte?
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
                //TODO: Was ist der Sinn dieses Cases, wenn ja bei OFF nichts passieren sollte?
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

    /**
     * method which is used as long as the logger-server is not implemented
     *
     * @param messageToPrint
     */
    private void printInTerminal(final String messageToPrint) {
        System.out.print(messageToPrint);
    }

    public String getCurrentDateAndTime() { //public because its used in tests
        SimpleDateFormat swissFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date currentDate = new Date();
        return swissFormat.format(currentDate);
    }


    private String getLogOutputPattern(final String loggertyp, final String message) {
        return getCurrentDateAndTime() + " " + loggertyp + " " + this.getClass().getSimpleName() + " " + message;
    }

    //TODO Choose how to output the throwable object in the log message
    private String getLogOutputPattern(final String loggertyp, final String message, final Throwable throwable) {
        return getCurrentDateAndTime() + " " + loggertyp + " " + this.getClass().getSimpleName() + " " + message + " " + throwable.toString();
    }

    private boolean isLogLevelHighEnough(final LogLevel logLevelOfMethod) {
        return getLogLevelValue(logLevelOfMethod) >= getLogLevelValue(this.logLevel);
    }

    private int getLogLevelValue(final LogLevel logLevel) {
        switch (logLevel) {

            case DEBUG:
                return 0;
            case INFO:
                return 1;
            case WARNING:
                return 2;
            case ERROR:
                return 3;
            case CRITICAL:
                return 4;
            case OFF:
                return 5;
            default:
                return -1;
        }
    }


}
