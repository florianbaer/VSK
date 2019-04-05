package ch.hslu.vsk.logger.common.messagepassing.messages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ResultMessageTest {

    @Test
    void testCreation() {
        ResultMessage msg = new ResultMessage();

        assertTrue(msg.getMessageId().equals("result"));
    }

    @Test
    void testCreationWithParams() {
        ResultMessage msg = new ResultMessage("test", "value");

        assertTrue(msg.getMessageId().equals("result") && msg.getArgList().get(0).equals("test")
                && msg.getArgList().get(1).equals("value"));
    }
}