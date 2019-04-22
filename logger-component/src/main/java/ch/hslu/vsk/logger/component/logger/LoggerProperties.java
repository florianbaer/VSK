package ch.hslu.vsk.logger.component.logger;

import ch.hslu.vsk.logger.api.LogLevel;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Properties class for the LoggerComponent. If the properties file is already existing while accesing an object of
 * this class, then the properties file is used for the config-parameter. If the properties file was freshly created
 * in a time period shorter than 5 sec ago, the vsklogger.properties file is use (shitty workaround due to bad desing
 * desicions).
 */
public class LoggerProperties {
    private static final String LOGGER_PROPERTY_FILE = "vsklogger.properties";
    private static final String PROPERTY_MIN_LOG_LEVEL = "ch.hslu.vsk.logger.minloglevel";
    private static final String PROPERTY_CONNECTION_STRING = "ch.hslu.vsk.logger.connectionstring";
    private static final String PROPERTY_IDENTIFIER = "ch.hslu.vsk.logger.identifier";
    private static final String PROPERTY_LOCAL_FILE = "ch.hslu.vsk.logger.logFile";
    private boolean configFileFreshlyInstalled = true;

    private Properties loggerProperties = null;

    /**
     * Loads the properties.
     *
     * @throws IOException The unhandled io exception.
     */
    public void loadProperties() {
        try {
            File configFile = new File("logger.properties");

            if (!configFile.isFile()) {
                createLoggerPropertiesFile(configFile);
            }

            FileReader loggerPropsReader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(loggerPropsReader);

            loggerPropsReader.close();

            this.loggerProperties = props;
        } catch (Exception e) {
            System.out.println("Failed to load logger properties " + e.getMessage());
        }
    }

    /**
     * Creates properties file if it does not exists.
     * @param configFile to be created
     * @throws IOException raised while creating file
     */
    private void createLoggerPropertiesFile(final File configFile) throws IOException {
       Properties vskloggerProperties = new Properties();
       vskloggerProperties.load(LoggerProperties.class.getClassLoader().getResourceAsStream(LOGGER_PROPERTY_FILE));
       String connectionString = vskloggerProperties.getProperty(PROPERTY_CONNECTION_STRING);
       String minLogLevel = vskloggerProperties.getProperty(PROPERTY_MIN_LOG_LEVEL);

        if (configFile.createNewFile()) {
            FileWriter writer = new FileWriter(configFile);
            writer.append(PROPERTY_MIN_LOG_LEVEL + "=" + (minLogLevel.isEmpty() || minLogLevel == null?
                    "OFF" : minLogLevel) + System.lineSeparator());
            writer.append(PROPERTY_CONNECTION_STRING + "=" + (connectionString.isEmpty() || connectionString == null
                    ? "localhost:1234" : connectionString) + System.lineSeparator());
            writer.append(PROPERTY_IDENTIFIER + "=NoIdentifierSpecified" + System.lineSeparator());
            writer.append(PROPERTY_LOCAL_FILE + "=" + System.lineSeparator());
            writer.flush();
            writer.close();

            if(configFile.lastModified() > System.currentTimeMillis() + 5000) {
                this.configFileFreshlyInstalled = false;
            }
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
     * Get the min log level.
     * @return min log level
     */
    public LogLevel getPropertyMinLogLevel() {
        return LogLevel.valueOf(loggerProperties.getProperty(PROPERTY_MIN_LOG_LEVEL));
    }

    /**
     * Get the identifier.
     * @return identifier
     */
    public String getPropertyIdentifier() {
        return loggerProperties.getProperty(PROPERTY_IDENTIFIER);
    }

    /**
     * Get the local file path to store log messages
     * @return the local file path
     */
    public String getPropertyLocalFile() {
        return  loggerProperties.getProperty(PROPERTY_LOCAL_FILE);
    }

    /**
     * If the config file was created the first time, this method returns true. This means, that the
     * configurations from the vsklogger.properties-File are taken for the LoggerComponent. If the config-File
     * already exists, this method returns false and the configurations form the config file are taken.
     * @return
     */
    public boolean getConfigStatus() {
        return this.configFileFreshlyInstalled;
    }

}
