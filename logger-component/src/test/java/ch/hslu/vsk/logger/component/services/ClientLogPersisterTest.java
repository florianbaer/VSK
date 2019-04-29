package ch.hslu.vsk.logger.component.services;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.FileAssert;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ClientLogPersisterTest {

    private ClientLogPersister clientLogPersister = new ClientLogPersister();

    @Test
    void testPersistLocally() throws IOException {

        clientLogPersister.persistLocally(Instant.now(), new LogMessage("test1"));
        clientLogPersister.persistLocally(Instant.now(), new LogMessage("test2"));
        clientLogPersister.persistLocally(Instant.now(),new LogMessage("test3"));
        clientLogPersister.persistLocally(Instant.now(), new LogMessage("blu"));
        clientLogPersister.persistLocally(Instant.now(), new LogMessage("blu"));

        assertTrue(clientLogPersister.getAllLocalLogs().size() == 5);
    }


    @Test
    void testGetAllLocalLogs() throws IOException {

        LogMessage message1 = new LogMessage("test1");
        LogMessage message2 = new LogMessage("test2");
        LogMessage message3 = new LogMessage("test3");

        clientLogPersister.persistLocally(Instant.now(), message1);
        clientLogPersister.persistLocally(Instant.now(), message2);
        clientLogPersister.persistLocally(Instant.now(), message3);

        List<String> expectedList = new ArrayList<>();
        expectedList.add("test1");
        expectedList.add("test2");
        expectedList.add("test3");

        List<String> actualList = new ArrayList<>();

        for (LogMessage logMessage : clientLogPersister.getAllLocalLogs()) {
            actualList.add(logMessage.getMessageText());
        }

        assertEquals(expectedList,actualList);
    }
}