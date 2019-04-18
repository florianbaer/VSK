package ch.hslu.vsk.logger.server;

import ch.hslu.vsk.logger.common.helpers.Property;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * The Serverproperties used to configure the server.
 */
public class ServerProperties extends Property {

    private static final String LOGFILE = "ch.hslu.vsk.server.logfile";
    private static final String PORT = "ch.hslu.vsk.server.port";

    private Properties serverProperties = null;

    /**
     * Gets the loggerfile which is used to save the log content.
     *
     * @return The loggerfile.
     * @throws IOException an exception which can be caused because by the filesystem.
     */
    public File getLoggerFile() throws IOException {
        var logFilePath = this.serverProperties.getProperty(LOGFILE);
        File logFile = null;
        if (logFilePath == null || logFilePath.isEmpty()) {
            System.out.println("No valid LogFile defined, logging to tmp File...");
            logFile = File.createTempFile("Server", ".log");
        } else {
            logFile = new File(logFilePath);
        }

        this.createFileIfNotExisting(logFile);

        System.out.println(String.format("Logging to file : '%s'", logFile.getAbsoluteFile()));
        return logFile;
    }


    /**
     * Gets the server port.
     *
     * @return The configured server port.
     */
    public int getServerPort() {
        return Integer.parseInt(this.serverProperties.getProperty(PORT));
    }

    /**
     * Loads the properties.
     *
     * @throws IOException The unhandled io exception.
     */
    public void loadProperties() throws IOException {
        File configFile = new File("server.properties");

        if (!configFile.isFile()) {
            createPropertiesFile(configFile);
        }

        FileReader reader = new FileReader(configFile);
        Properties props = new Properties();
        props.load(reader);

        reader.close();

        this.serverProperties = props;
    }

    /**
     * Creates a default properties file if not existing.
     *
     * @param configFile the config file to be created.
     * @throws IOException The unhandled io exception.
     */
    private void createPropertiesFile(final File configFile) throws IOException {
        if (configFile.createNewFile()) {
            FileWriter writer = new FileWriter(configFile);
            writer.append("ch.hslu.vsk.server.port=1337\n");
            writer.append("ch.hslu.vsk.server.logfile=");
            writer.flush();
            writer.close();
        }
    }

    /**
     * Gets the server properties.
     *
     * @return The loaded server properties.
     */
    public Properties getProperties() {
        return this.serverProperties;
    }
}
