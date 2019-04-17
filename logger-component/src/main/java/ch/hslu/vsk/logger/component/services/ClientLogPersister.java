package ch.hslu.vsk.logger.component.services;

import ch.hslu.vsk.logger.common.adapter.StringPersistorAdapter;
import ch.hslu.vsk.logger.common.helpers.Property;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.impl.FileStringPersistor;
import ch.hslu.vsk.stringpersistor.impl.PersistedStringCsvConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class is used to log messages into a local logFile during the time the server is not reachable.
 */
class ClientLogPersister extends Property {

    private StringPersistorAdapter stringPersistorAdapter = null;
    private File logFile = null;
    private static final String LOGGER_PROPERTY_FILE = "vsklogger.properties";
    private static final String PROPERTY_LOGFILE = "ch.hslu.vsk.logger.logFile";


    /**
     *
     */
    ClientLogPersister() {

        try {
            this.logFile = this.getLoggerFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.stringPersistorAdapter = this.setupPersisterAdapter();

    }

    /**
     * Method to persist a message locally using the StringPersister
     *
     * @param messageToPersist Message that will be persistet locally
     */
    public void persistLocally(final LogMessage messageToPersist) {
        this.stringPersistorAdapter.save(messageToPersist);
    }

    public List<LogMessage> getAllLocalLogs() {
        List<LogMessage> logMessages = new ArrayList<>();
        for (PersistedString persistedString :
                stringPersistorAdapter.getAllPersistedStrings()) {
            logMessages.add(new LogMessage(persistedString.getPayload()));
        }
        clearLocalLogFile();
        return logMessages;
    }

    private void clearLocalLogFile() {
        try (PrintWriter writer = new PrintWriter(this.logFile)) {
            writer.write("");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * @return The logFile that will contain the local logs.
     * @throws IOException
     */
    File getLoggerFile() throws IOException {
        Properties clientProperties = new Properties();

        clientProperties.load(NetworkService.class.getClassLoader().getResourceAsStream(LOGGER_PROPERTY_FILE));
        var logFilePath = clientProperties.getProperty(PROPERTY_LOGFILE);
        File logFile = null;
        if (logFilePath == null || logFilePath.isEmpty()) {
            System.out.println("No valid LogFile defined, logging to tmp File...");
            logFile = File.createTempFile("MessageLoggerClient", ".csv");
        } else {
            logFile = new File(logFilePath);
        }

        this.createFileIfNotExisting(logFile);
        return logFile;
    }

    /**
     * Sets up the StringPersistorAdapter
     *
     * @return the stringPersistorAdapter
     */
    private StringPersistorAdapter setupPersisterAdapter() {
        FileStringPersistor filePersister = new FileStringPersistor(new PersistedStringCsvConverter());
        filePersister.setFile(logFile);
        return new StringPersistorAdapter(filePersister);
    }


}
