package ch.hslu.vsk.logger.component.logger;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.component.services.NetworkService;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Properties class for the LoggerComponent
 */
public class LoggerProperties extends Properties {
    private static final String LOGGER_PROPERTY_FILE = "vsklogger.properties";
    private static final String PROPERTY_MIN_LOG_LEVEL = "ch.hslu.vsk.logger.minloglevel";
    private static final String PROPERTY_CONNECTION_STRING = "ch.hslu.vsk.logger.connectionstring";
    private static final String PROPERTY_IDENTIFIER = "ch.hslu.vsk.logger.identifier";
    private static final String PROPERTY_LOGGING_CLASS = "ch.hslu.vsk.logger.loggingclass";

    private Properties loggerProperties = null;

    /**
     * Loads the properties.
     *
     * @throws IOException The unhandled io exception.
     */
    public void loadProperties() throws IOException {
        File configFile = new File("logger.properties");

        if (!configFile.isFile()) {
            createLoggerPropertiesFile(configFile);
        }

        FileReader loggerPropsReader = new FileReader(configFile);
        Properties props = new Properties();
        props.load(loggerPropsReader);

        loggerPropsReader.close();

        this.loggerProperties = props;
    }

    /**
     * Creates properties file if it does not exists.
     * @param configFile to be created
     */
    private void createLoggerPropertiesFile(final File configFile) throws IOException {
       Properties vskloggerProperties = new Properties();
       vskloggerProperties.load(LoggerProperties.class.getClassLoader().getResourceAsStream(LOGGER_PROPERTY_FILE));
       String connectionString = vskloggerProperties.getProperty(PROPERTY_CONNECTION_STRING);
       String minLogLevel = vskloggerProperties.getProperty(PROPERTY_MIN_LOG_LEVEL);

        if (configFile.createNewFile()) {
            FileWriter writer = new FileWriter(configFile);
            writer.append(PROPERTY_MIN_LOG_LEVEL + "=" + minLogLevel + System.lineSeparator());
            writer.append(PROPERTY_CONNECTION_STRING+ "=" + connectionString + System.lineSeparator());
            writer.append(PROPERTY_IDENTIFIER + "=test" + System.lineSeparator());
            writer.append(PROPERTY_LOGGING_CLASS + "=DefaultLoggingClass" + System.lineSeparator());
            writer.flush();
            writer.close();
        }
    }

    /**
     * Get the connection string.
     * @return connection string
     */
    public String getPropertyConnectionString() {
        return loggerProperties.getProperty(PROPERTY_CONNECTION_STRING);
    }

    /**
     * Get the min log level
     * @return min log level
     */
    public LogLevel getPropertyMinLogLevel() {
        return LogLevel.valueOf(loggerProperties.getProperty(PROPERTY_MIN_LOG_LEVEL));
    }

    /**
     * Get the identifier
     * @return identifier
     */
    public String getPropertyIdentifier()
    {
        return loggerProperties.getProperty(PROPERTY_IDENTIFIER);
    }

    public String getPropertyLoggingClass() {
        return loggerProperties.getProperty(PROPERTY_LOGGING_CLASS);
    }

}
