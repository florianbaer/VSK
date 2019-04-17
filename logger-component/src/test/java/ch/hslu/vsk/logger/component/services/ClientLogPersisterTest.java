package ch.hslu.vsk.logger.component.services;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import org.assertj.core.api.FileAssert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ClientLogPersisterTest {

    private ClientLogPersister clientLogPersister = new ClientLogPersister();

    @Test
    void testPersistLocally() throws IOException {

        clientLogPersister.getLoggerFile();
        clientLogPersister.persistLocally(new LogMessage("testMessage"));
        clientLogPersister.persistLocally(new LogMessage("testMessage2"));
        clientLogPersister.persistLocally(new LogMessage("testMessage3"));


    }


    @Test
    void testGetAllLocalLogs() {
        System.out.println(clientLogPersister.getAllLocalLogs().size());
        for (LogMessage logMessage : clientLogPersister.getAllLocalLogs()) {
            System.out.println("test");
            System.out.println(logMessage.getMessageText());
        }
    }
}