package ch.hslu.vsk.logger.component.logger;


import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.common.messagepassing.messages.PayloadCreator;
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

        if(connectionString != null && !connectionString.isEmpty()){
            this.service.changeConnectionDetails(connectionString);
        }
    }

    @Override
    public void debug(String s) {
        if (isLogLevelHighEnough(LogLevel.DEBUG)) {
            service.sendLogMessageToServer(PayloadCreator.generatePayload(LogLevel.DEBUG, this.identifier, this.loggerClass, s));
        }

    }

    @Override
    public void debug(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.DEBUG)) {
            service.sendLogMessageToServer(PayloadCreator.generatePayload(LogLevel.DEBUG, this.identifier, this.loggerClass, throwable.getMessage()));
        }
    }

    @Override
    public void info(String s) {
        if (isLogLevelHighEnough(LogLevel.INFO)) {
            service.sendLogMessageToServer(PayloadCreator.generatePayload(LogLevel.INFO, this.identifier, this.loggerClass, s));
        }

    }

    @Override
    public void info(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.INFO)) {
            service.sendLogMessageToServer(PayloadCreator.generatePayload(LogLevel.INFO, this.identifier, this.loggerClass, throwable.getMessage()));
        }

    }

    @Override
    public void warning(String s) {
        if (isLogLevelHighEnough(LogLevel.WARNING)) {
            service.sendLogMessageToServer(PayloadCreator.generatePayload(LogLevel.WARNING, this.identifier, this.loggerClass, s));
        }

    }

    @Override
    public void warning(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.WARNING)) {
            service.sendLogMessageToServer(PayloadCreator.generatePayload(LogLevel.WARNING, this.identifier, this.loggerClass, throwable.getMessage()));
        }
    }

    @Override
    public void error(String s) {
        if (isLogLevelHighEnough(LogLevel.ERROR)) {
            service.sendLogMessageToServer(PayloadCreator.generatePayload(LogLevel.ERROR, this.identifier, this.loggerClass, s));
        }

    }

    @Override
    public void error(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.ERROR)) {
            service.sendLogMessageToServer(PayloadCreator.generatePayload(LogLevel.ERROR, this.identifier, this.loggerClass, throwable.getMessage()));
        }

    }

    @Override
    public void critical(String s) {
        if (isLogLevelHighEnough(LogLevel.CRITICAL)) {
            service.sendLogMessageToServer(PayloadCreator.generatePayload(LogLevel.CRITICAL, this.identifier, this.loggerClass, s));
        }

    }

    @Override
    public void critical(String s, Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.CRITICAL)) {
            service.sendLogMessageToServer(PayloadCreator.generatePayload(LogLevel.CRITICAL, this.identifier, this.loggerClass, throwable.getMessage()));
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

    private boolean isLogLevelHighEnough(final LogLevel logLevelOfMethod) {
        return logLevelOfMethod.ordinal() >= (this.logLevel).ordinal();
    }
}
