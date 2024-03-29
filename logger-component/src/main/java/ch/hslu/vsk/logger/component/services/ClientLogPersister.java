package ch.hslu.vsk.logger.component.services;

import ch.hslu.vsk.logger.common.adapter.StringPersistorAdapter;
import ch.hslu.vsk.logger.common.helpers.FileHandler;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.logger.component.logger.LoggerProperties;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.impl.FileContext;
import ch.hslu.vsk.stringpersistor.impl.FileStringPersistor;
import ch.hslu.vsk.stringpersistor.impl.MessageLastStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to log messages into a local logFile during the time the server is not reachable.
 */
public class ClientLogPersister extends FileHandler {

    private StringPersistorAdapter stringPersistorAdapter = null;
    private File logFile = null;


    /**
     * Constructor.
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
     * Method to persist a message locally using the StringPersister.
     *
     * @param messageToPersist Message that will be persistet locally
     * @param timeStamp        timestamp to use
     */
    public void persistLocally(final Instant timeStamp, final LogMessage messageToPersist) {
        this.stringPersistorAdapter.save(timeStamp, messageToPersist);
    }

    /**
     * Get all local logs.
     * @return log message stored locally.
     */
    public List<LogMessage> getAllLocalLogs() {
        List<LogMessage> logMessages = new ArrayList<>();
        for (PersistedString persistedString : this.stringPersistorAdapter.getAllPersistedStrings()) {
            logMessages.add(new LogMessage(persistedString.getPayload()));
        }
        return logMessages;
    }

    /**
     * Clears the log file.
     */
    public void clearLocalLogFile() {
        try (PrintWriter writer = new PrintWriter(this.logFile)) {
            writer.write("");
            System.out.println("Local LogFile cleared");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * @return The logFile that will contain the local logs.
     * @throws IOException that my be thrown
     */
    File getLoggerFile() throws IOException {
        LoggerProperties clientProperties = new LoggerProperties();

        clientProperties.loadProperties();
        var logFilePath = clientProperties.getPropertyLocalFile();
        File logFile = null;
        if (logFilePath == null || !new File(logFilePath).isFile()) {
            System.out.println("No valid LogFile defined, logging to tmp File...");
            logFile = File.createTempFile("MessageLoggerClient", ".log"); //creates File in %localappdata%
        } else {
            logFile = new File(logFilePath);
        }

        this.createFileIfNotExisting(logFile);
        return logFile;
    }

    /**
     * Sets up the StringPersistorAdapter.
     *
     * @return the stringPersistorAdapter
     */
    private StringPersistorAdapter setupPersisterAdapter() {
        FileStringPersistor filePersister = new FileStringPersistor();
        filePersister.setFile(logFile);
        return new StringPersistorAdapter(filePersister);
    }


}
