package ch.hslu.vsk.logger.viewer;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TableEntryOfLogMessagesTest {
    Instant serverInstant = Instant.now();
    Instant logInstant = Instant.now();
    TableEntryOfLogMessages tableEntryOfLogMessages = new TableEntryOfLogMessages(serverInstant.toString(),
            logInstant.toString(), "test", "DEBUG", this.getClass().getSimpleName(), "logging test");

    @Test
    void getLoggingClass() {
        assertTrue(tableEntryOfLogMessages.getLoggingClass().equals(this.getClass().getSimpleName()));
    }

    @Test
    void setLoggingClass() {
        tableEntryOfLogMessages.setLoggingClass(TableEntryOfLogMessages.class.getSimpleName());
        assertTrue(tableEntryOfLogMessages.getLoggingClass().equals(TableEntryOfLogMessages.class.getSimpleName()));
    }

    @Test
    void getLogMessage() {
        assertTrue(tableEntryOfLogMessages.getLogMessage().equals("logging test"));
    }

    @Test
    void setLogMessage() {
        tableEntryOfLogMessages.setLogMessage("another logging test");
        assertTrue(tableEntryOfLogMessages.getLogMessage().equals("another logging test"));
    }

    @Test
    void getLogLevel() {
        assertTrue(tableEntryOfLogMessages.getLogLevel().equals("DEBUG"));
    }

    @Test
    void setLogLevel() {
        tableEntryOfLogMessages.setLogLevel("INFO");
        assertTrue(tableEntryOfLogMessages.getLogLevel().equals("INFO"));
    }

    @Test
    void getServerTimestamp() {
        assertTrue(tableEntryOfLogMessages.getServerTimestamp().equals(serverInstant.toString()));
    }

    @Test
    void setServerTimestamp() {
        Instant now = Instant.now();
        tableEntryOfLogMessages.setServerTimestamp(now.toString());
        assertTrue(tableEntryOfLogMessages.getServerTimestamp().equals(now.toString()));
    }

    @Test
    void getLogTimestamp() {
        assertTrue(tableEntryOfLogMessages.getLogTimestamp().equals(logInstant.toString()));
    }

    @Test
    void setLogTimestamp() {
        Instant now = Instant.now();
        tableEntryOfLogMessages.setLogTimestamp(now.toString());
        assertTrue(tableEntryOfLogMessages.getLogTimestamp().equals(now.toString()));
    }

    @Test
    void getIdentifier() {
        assertTrue(tableEntryOfLogMessages.getIdentifier().equals("test"));
    }

    @Test
    void setIdentifier() {
        tableEntryOfLogMessages.setIdentifier("test1");
        assertTrue(tableEntryOfLogMessages.getIdentifier().equals("test1"));
    }
}