package ch.hslu.vsk.logger.server;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ServerProperties {

    private static String PROPERTIES_LOGFILE = "ch.hslu.vsk.server.logfile";
    private static String PROPERTIES_PORT = "ch.hslu.vsk.server.port";

    Properties serverProperties = null;

    public File loadLoggerFileProperty() throws IOException {
        var logFilePath = this.serverProperties.getProperty(PROPERTIES_LOGFILE);
        File logFile = null;
        if(logFilePath == null || logFilePath.isEmpty()) {
            System.out.println("No valid LogFile defined, logging to tmp File...");
            logFile = File.createTempFile("Server", ".log");
        }
        else{
            logFile = new File(logFilePath);
        }

        System.out.println(String.format("Logging to file : '%s'", logFile.getAbsoluteFile()));
        return logFile;
    }

    public int loadServerPortProperty() {
        return Integer.parseInt(this.serverProperties.getProperty(PROPERTIES_PORT));
    }

    public void loadProperties() {
        Properties serverProperties = new Properties();
        try {
            serverProperties.load(LoggerServer.class.getClassLoader().getResourceAsStream("Server.properties"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        this.serverProperties = serverProperties;
    }

    public Properties getServerProperties(){
        return this.serverProperties;
    }
}
