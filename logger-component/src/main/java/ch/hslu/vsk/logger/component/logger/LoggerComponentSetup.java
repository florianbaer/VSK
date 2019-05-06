package ch.hslu.vsk.logger.component.logger;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.api.LoggerSetup;

/**
 * Setup class for initializing a LoggerComponent. The builder pattern is applied.
 *
 * @author Matthias Egli, David Gut, Florian Bär, Dennis Dekker
 * @version 0.5.0
 */
public final class LoggerComponentSetup implements LoggerSetup {
    private LogLevel level;
    private String identifier;
    private String connectionString;
    private Class clazz;

    @Override
    public LoggerSetup withConnectionString(final String connectionString) {
        this.connectionString = connectionString;
        return this;
    }

    @Override
    public LoggerSetup withMinLogLevel(final LogLevel logLevel) {
        this.level = logLevel;
        return this;
    }

    @Override
    public LoggerSetup withIdentifier(final String identifier) {
        this.identifier = identifier;
        return this;
    }

    @Override
    public LoggerSetup withClass(final Class clazz) {
        this.clazz = clazz;
        return this;
    }

    @Override
    public Logger build() {
        LoggerProperties properties = new LoggerProperties();
        properties.loadProperties();

        // this workaround is needed because of bad design decisions
        if (!properties.getConfigStatus() && this.level != null && this.connectionString != null
                && this.identifier == null) {

            this.connectionString = properties.getPropertyConnectionString();

            this.level = properties.getPropertyMinLogLevel();

            this.identifier = properties.getPropertyIdentifier();
        }

        return new LoggerComponent(this.level, this.connectionString, this.identifier,
                this.clazz);
    }
}