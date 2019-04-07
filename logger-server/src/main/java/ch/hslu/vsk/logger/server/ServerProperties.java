package ch.hslu.vsk.logger.server;

import java.io.*;
import java.util.Properties;

public class ServerProperties {

    private static String PROPERTIES_LOGFILE = "ch.hslu.vsk.server.logfile";
    private static String PROPERTIES_PORT = "ch.hslu.vsk.server.port";

    Properties serverProperties = null;

    public File getLoggerFile() throws IOException {
        var logFilePath = this.serverProperties.getProperty(PROPERTIES_LOGFILE);
        File logFile = null;
        if(logFilePath == null || logFilePath.isEmpty()) {
            System.out.println("No valid LogFile defined, logging to tmp File...");
            logFile = File.createTempFile("Server", ".log");
        }
        else{
            logFile = new File(logFilePath);
        }

        this.createFileIfNotExisting(logFile);

        System.out.println(String.format("Logging to file : '%s'", logFile.getAbsoluteFile()));
        return logFile;
    }

    private void createFileIfNotExisting(File file) throws IOException {
        file.getParentFile().mkdirs();

        if(!file.isFile()){
            file.createNewFile();
        }
    }

    public int getServerPort() {
        return Integer.parseInt(this.serverProperties.getProperty(PROPERTIES_PORT));
    }

    public void loadProperties() throws IOException {
        File configFile = new File("Server.properties");

        if(!configFile.isFile()){
            createPropertiesFile(configFile);
        }

        FileReader reader = new FileReader(configFile);
        Properties props = new Properties();
        props.load(reader);

        reader.close();

        this.serverProperties = props;
    }

    private void createPropertiesFile(File configFile) throws IOException {
        if(configFile.createNewFile()){
            FileWriter writer = new FileWriter(configFile);
            writer.append("ch.hslu.vsk.server.port=1337\n");
            writer.append("ch.hslu.vsk.server.logfile=");
            writer.flush();
            writer.close();
        }
    }

    public Properties getServerProperties(){
        return this.serverProperties;
    }
}
