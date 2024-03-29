package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.common.messagepassing.LogCommunicationHandler;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the LogCommunicationHandler.
 */
public class LogCommunicationHandlerTest {

    @Test
    public void sendAndReadLogMessage() throws IOException, OperationNotSupportedException {
        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out);

        LogCommunicationHandler handler = new LogCommunicationHandler(in, out);

        LogMessage msg = new LogMessage();
        msg.addArg("Test");

        handler.sendMsg(msg);
        assertTrue(((LogMessage) handler.readMsg()).getMessageText().equals("Test"));
    }
}
