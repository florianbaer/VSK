package ch.hslu.vsk.logger.common.messagepassing.messages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogMessageTest {

    @Test
    void testCreation() {
        LogMessage msg = new LogMessage();

        assertTrue(msg.getMessageId().equals("log"));
    }

    @Test
    void testCreationWithParams() {
        LogMessage msg = new LogMessage("test");

        assertTrue(msg.getMessageId().equals("log") && msg.getArgList().get(0).equals("test"));
    }
}