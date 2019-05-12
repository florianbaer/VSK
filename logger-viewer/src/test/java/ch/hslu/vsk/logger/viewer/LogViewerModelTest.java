package ch.hslu.vsk.logger.viewer;

import ch.hslu.vsk.logger.common.DTO.LogMessageDTO;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class LogViewerModelTest {

    @Test
    public void testAddingOfTableEntry() {
        final LogViewerModel model = new LogViewerModel();

        assertTrue(model.getListOfLogMessages().size() == 0);

        LogMessage msg = new LogMessage();
        msg.addArg("2019-04-29T18:05:58.255900Z|NoIdentifierSpecified|INFO|GameOfLifeGrid|Cell dies [42,7]");

        model.sendLogMessage(LogMessageDTO.fromLogMessage(Instant.now(), msg));

        assertTrue(model.getListOfLogMessages().size() == 1);
    }



}