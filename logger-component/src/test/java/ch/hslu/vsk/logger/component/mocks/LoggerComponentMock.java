package ch.hslu.vsk.logger.component.mocks;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is a Mock used for the LoggerComponent-Class. This Mock logs to the console, so the logs
 * can be tested using unit-tests.
 */
public class LoggerComponentMock implements Logger {

    private LogLevel logLevel;
    private String connectionString;
    private String identifier;
    private Class loggerClass;

    /**
     * Mock constructor
     * @param level to log
     * @param connectionString to connect
     * @param identifier of component
     * @param clazz that logs
     */
    public LoggerComponentMock(final LogLevel level, final String connectionString, final String identifier, final Class clazz) {
        this.logLevel = level;
        this.connectionString = connectionString;
        this.identifier = identifier;
        this.loggerClass = clazz;
    }

    @Override
    public void debug(final String s) {
        if (isLogLevelHighEnough(LogLevel.DEBUG)) {
            printInTerminal(getLogOutputPattern(LogLevel.DEBUG.toString(), s));
        }

    }

    @Override
    public void debug(final String s, final Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.DEBUG)) {
            printInTerminal(getLogOutputPattern(LogLevel.DEBUG.toString(), s, throwable));
        }
    }

    @Override
    public void info(final String s) {
        if (isLogLevelHighEnough(LogLevel.INFO)) {
            printInTerminal(getLogOutputPattern(LogLevel.INFO.toString(), s));
        }

    }

    @Override
    public void info(final String s, final Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.INFO)) {
            printInTerminal(getLogOutputPattern(LogLevel.INFO.toString(), s, throwable));
        }

    }

    @Override
    public void warning(final String s) {
        if (isLogLevelHighEnough(LogLevel.WARNING)) {
            printInTerminal(getLogOutputPattern(LogLevel.WARNING.toString(), s));
        }

    }

    @Override
    public void warning(final String s, final Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.WARNING)) {
            printInTerminal(getLogOutputPattern(LogLevel.WARNING.toString(), s, throwable));
        }
    }

    @Override
    public void error(final String s) {
        if (isLogLevelHighEnough(LogLevel.ERROR)) {
            printInTerminal(getLogOutputPattern(LogLevel.ERROR.toString(), s));
        }

    }

    @Override
    public void error(final String s, final Throwable throwable) {
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
    public void critical(final String s, final Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.CRITICAL)) {
            printInTerminal(getLogOutputPattern(LogLevel.CRITICAL.toString(), s, throwable));
        }
    }

    @Override
    public void log(final LogLevel logLevel, final String s) {
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
            default:
                break;
        }

    }

    @Override
    public void log(final LogLevel logLevel, final String s, final Throwable throwable) {
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
            default:
                break;
        }
    }

    @Override
    public void setMinLogLevel(final LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public LogLevel getMinLogLevel() {
        return this.logLevel;
    }

    // All following Methods are just a workaround because the server side is missing at the moment

    /**
     * method which is used as long as the logger-server is not implemented
     *
     * @param messageToPrint
     */
    private void printInTerminal(final String messageToPrint) {
        System.out.print(messageToPrint);
    }

    /**
     * Construct current time in swiss format
     * @return time in swiss format
     */
    public String getCurrentDateAndTime() { //public because its used in tests
        SimpleDateFormat swissFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date currentDate = new Date();
        return swissFormat.format(currentDate);
    }

    /**
     * Get the pattern to log
     * @param loggertyp type of logger
     * @param message to log
     * @return
     */
    private String getLogOutputPattern(final String loggertyp, final String message) {
        return getCurrentDateAndTime() + " " + loggertyp + " " + this.getClass().getSimpleName() + " " + message;
    }

    //TODO Choose how to output the throwable object in the log message --> .toString() für den Moment.
    private String getLogOutputPattern(final String loggertyp, final String message, final Throwable throwable) {
        return getCurrentDateAndTime() + " " + loggertyp + " " + this.getClass().getSimpleName() + " " + message + " " + throwable.toString();
    }

    /**
     * Checks if the level to log is high enough
     * @param logLevelOfMethod to log
     * @return true if high enough, false if not
     */
    private boolean isLogLevelHighEnough(final LogLevel logLevelOfMethod) {
        return logLevelOfMethod.ordinal() >= (this.logLevel).ordinal();
    }
}
