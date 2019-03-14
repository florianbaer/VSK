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

    private LogLevel logLevel;

    //needs adjustment when implementing LoggerSetup interface
    public LoggerComponent() {
        this.logLevel = LogLevel.OFF;
    }

    @Override
    public void debug(String s) {
        printInTerminal(getLogPattern((new Object() {
        }.getClass().getEnclosingMethod().getName()), s));

    }

    @Override
    public void debug(String s, Throwable throwable) {
        printInTerminal(getLogPattern((new Object() {
        }.getClass().getEnclosingMethod().getName()), s, throwable));
    }

    @Override
    public void info(String s) {
        printInTerminal(getLogPattern((new Object() {
        }.getClass().getEnclosingMethod().getName()), s));

    }

    @Override
    public void info(String s, Throwable throwable) {
        printInTerminal(getLogPattern((new Object() {
        }.getClass().getEnclosingMethod().getName()), s, throwable));

    }

    @Override
    public void warning(String s) {
        printInTerminal(getLogPattern((new Object() {
        }.getClass().getEnclosingMethod().getName()), s));

    }

    @Override
    public void warning(String s, Throwable throwable) {
        printInTerminal(getLogPattern((new Object() {
        }.getClass().getEnclosingMethod().getName()), s, throwable));
    }

    @Override
    public void error(String s) {
        printInTerminal(getLogPattern((new Object() {
        }.getClass().getEnclosingMethod().getName()), s));

    }

    @Override
    public void error(String s, Throwable throwable) {
        printInTerminal(getLogPattern((new Object() {
        }.getClass().getEnclosingMethod().getName()), s, throwable));

    }

    @Override
    public void critical(String s) {
        printInTerminal(getLogPattern((new Object() {
        }.getClass().getEnclosingMethod().getName()), s));

    }

    @Override
    public void critical(String s, Throwable throwable) {
        printInTerminal(getLogPattern((new Object() {
        }.getClass().getEnclosingMethod().getName()), s, throwable));
    }

    @Override
    public void log(LogLevel logLevel, String s) {
        switch (logLevel) {
            case OFF:
                //not sure what is meant with OFF
                //OFF should probably not be available here
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
                //not sure what is meant with OFF
                //OFF should probably not be available here
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
    private void printInTerminal(String messageToPrint) {
        System.out.println(messageToPrint);
    }

    private String getCurrentDateAndTime() {
        SimpleDateFormat swissFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date currentDate = new Date();
        String now = swissFormat.format(currentDate);
        return now;
    }

    private int getLineNumberOfLogCaller() {
        return new Exception().getStackTrace()[3].getLineNumber(); //3 --> depends on how many method calls are between the initial Log.deb() call and this line here
    }

    private String getLogPattern(String loggertyp, String message) {
        return getCurrentDateAndTime() + " " + loggertyp.toUpperCase() + " " + this.getClass().getSimpleName() + ":" + getLineNumberOfLogCaller() + " " + message;
    }

    //TODO Choose how to output the throwable object in the log message
    private String getLogPattern(String loggertyp, String message, Throwable throwable) {
        return getCurrentDateAndTime() + " " + loggertyp.toUpperCase() + " " + this.getClass().getSimpleName() + ":" + getLineNumberOfLogCaller() + " " + message + " " + throwable.toString();
    }

    private int getLogLevelValue(LogLevel logLevel) {
        switch (logLevel) {
            case OFF:
                return 0;
            case INFO:
                return 1;
            case DEBUG:
                return 2;
            case WARNING:
                return 3;
            case ERROR:
                return 4;
            case CRITICAL:
                return 5;
            default:
                return 0;
        }
    }


}
