package ch.hslu.vsk.logger.component.logger;


import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.common.messagepassing.messages.PayloadCreator;
import ch.hslu.vsk.logger.component.services.NetworkCommunication;
import ch.hslu.vsk.logger.component.services.NetworkService;


/**
 * Represents a logger which can be used to log messages with different log-levels. To obtain a instance
 * of this class, the LoggerComponentSetup-class should be used.
 *
 * @author Matthias Egli, David Gut, Florian Bär, Dennis Dekker
 * @version 0.1.0
 */
public final class LoggerComponent implements Logger {

    private LogLevel logLevel;
    private String connectionString;
    private String identifier;
    private Class loggerClass;
    private NetworkCommunication service;

    /**
     * Constructor used to initiate the LoggerComponent.
     * @param level minLevel of the component
     * @param connectionString host:port as string
     * @param identifier id of the component
     * @param clazz logging class
     */
    public LoggerComponent(final LogLevel level, final String connectionString,
                           final String identifier, final Class clazz) {
        this.logLevel = level;
        this.connectionString = connectionString;
        this.identifier = identifier;
        this.loggerClass = clazz;
        this.service = NetworkService.getInstance();

        if (connectionString != null && !connectionString.isEmpty()) {
            this.service.changeConnectionDetails(connectionString);
        }
    }

    /**
     * Constructor used to initiate the LoggerComponent and injecting a dependency for the
     * NetworkService.
     * @param level minLevel of the component
     * @param connectionString host:port as string
     * @param identifier id of the component
     * @param clazz logging class
     * @param networkService used network service
     */
    public LoggerComponent(final LogLevel level, final String connectionString, final String identifier,
                           final Class clazz, final NetworkCommunication networkService) {
        this.logLevel = level;
        this.connectionString = connectionString;
        this.identifier = identifier;
        this.loggerClass = clazz;
        this.service = networkService;

        if (connectionString != null && !connectionString.isEmpty()) {
            this.service.changeConnectionDetails(connectionString);
        }
    }

    @Override
    public void debug(final String s) {
        if (isLogLevelHighEnough(LogLevel.DEBUG)) {
            service.sendMessageToServer(PayloadCreator.generatePayload(
                    LogLevel.DEBUG, this.identifier, this.loggerClass, s));
        }

    }

    @Override
    public void debug(final String s, final Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.DEBUG)) {
            service.sendMessageToServer(PayloadCreator.generatePayload(
                    LogLevel.DEBUG, this.identifier, this.loggerClass, s, throwable));
        }
    }

    @Override
    public void info(final String s) {
        if (isLogLevelHighEnough(LogLevel.INFO)) {
            service.sendMessageToServer(PayloadCreator.generatePayload(
                    LogLevel.INFO, this.identifier, this.loggerClass, s));
        }

    }

    @Override
    public void info(final String s, final Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.INFO)) {
            service.sendMessageToServer(PayloadCreator.generatePayload(
                    LogLevel.INFO, this.identifier, this.loggerClass, s, throwable));
        }

    }

    @Override
    public void warning(final String s) {
        if (isLogLevelHighEnough(LogLevel.WARNING)) {
            service.sendMessageToServer(PayloadCreator.generatePayload(
                    LogLevel.WARNING, this.identifier, this.loggerClass, s));
        }

    }

    @Override
    public void warning(final String s, final Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.WARNING)) {
            service.sendMessageToServer(PayloadCreator.generatePayload(
                    LogLevel.WARNING, this.identifier, this.loggerClass, s, throwable));
        }
    }

    @Override
    public void error(final String s) {
        if (isLogLevelHighEnough(LogLevel.ERROR)) {
            service.sendMessageToServer(PayloadCreator.generatePayload(
                    LogLevel.ERROR, this.identifier, this.loggerClass, s));
        }

    }

    @Override
    public void error(final String s, final Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.ERROR)) {
            service.sendMessageToServer(PayloadCreator.generatePayload(
                    LogLevel.ERROR, this.identifier, this.loggerClass, s, throwable));
        }

    }

    @Override
    public void critical(final String s) {
        if (isLogLevelHighEnough(LogLevel.CRITICAL)) {
            service.sendMessageToServer(PayloadCreator.generatePayload(
                    LogLevel.CRITICAL, this.identifier, this.loggerClass, s));
        }

    }

    @Override
    public void critical(final String s, final Throwable throwable) {
        if (isLogLevelHighEnough(LogLevel.CRITICAL)) {
            service.sendMessageToServer(PayloadCreator.generatePayload(
                    LogLevel.CRITICAL, this.identifier, this.loggerClass, s, throwable));
        }
    }

    @Override
    public void log(final LogLevel level, final String s) {
        switch (level) {
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
    public void log(final LogLevel level, final String s, final Throwable throwable) {
        switch (level) {
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
    public void setMinLogLevel(final LogLevel level) {
        this.logLevel = level;
    }

    @Override
    public LogLevel getMinLogLevel() {
        return this.logLevel;
    }

    /**
     * Getter for connection string used for logging.
     * @return host:port as String
     */
    public String getConnectionString() {
        return this.connectionString;
    }

    /**
     * Returns id of the logger component.
     * @return id used to identify logger
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * Returns class that logs.
     * @return logging class
     */
    public Class getLoggingClass() {
        return this.loggerClass;
    }

    /**
     * Checks if given log level is high enough compared to the minLogLevel.
     * @param logLevelOfMethod log level to log
     * @return true if high enough, false if not
     */
    private boolean isLogLevelHighEnough(final LogLevel logLevelOfMethod) {
        return logLevelOfMethod.ordinal() >= (this.logLevel).ordinal();
    }
}
