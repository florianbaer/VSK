package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.common.adapter.LogPersistor;
import ch.hslu.vsk.logger.common.messagepassing.LogServerCommunicationHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;



/**
 * Test class for the LogServerCommunicationHandler.
 */
public class LogServerCommunicationHandlerTest {
    private final LogPersistor persMock = mock(LogPersistor.class);

    @Test
    public void sendAndReadLogMessage() throws IOException {
        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out);

        LogServerCommunicationHandler handler = new LogServerCommunicationHandler(in, out, persMock);

        LogMessage msg = new LogMessage();
        msg.addArg("Test");
        handler.sendMsg(msg);

        assertTrue(((LogMessage) handler.readMsg()).getMessageText().equals("Test"));
    }
}
