package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.api.LoggerSetup;

/**
 * Setup-Class for initializing a LoggerComponent. The builder pattern is applied.
 *
 * @author Matthias Egli, David Gut, Florian Bär, Dennis Dekker
 * @version 0.5.0
 */
public class LoggerComponentSetup implements LoggerSetup {
    private LogLevel level;
    private String identifier;
    private String connectionString;
    private Class clazz;

    @Override
    public LoggerSetup withConnectionString(String connectionString) {
        this.connectionString = connectionString;
        return this;
    }

    @Override
    public LoggerSetup withMinLogLevel(LogLevel logLevel) {
        this.level = logLevel;
        return this;
    }

    @Override
    public LoggerSetup withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    @Override
    public LoggerSetup withClass(Class clazz) {
        this.clazz = clazz;
        return this;
    }

    @Override
    public Logger build() {
        return new LoggerComponent(this.level, this.connectionString, this.identifier, this.clazz);
    }
}