package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.common.adapter.LogPersistor;
import ch.hslu.vsk.logger.common.adapter.StringPersistorAdapter;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class StringPersistorTest {

    @Test
    void testCreation() {
        StringPersistor mock = mock(StringPersistor.class);
        LogPersistor persistor = new StringPersistorAdapter(mock);

        assertEquals(mock, persistor.getStringPersistor());
    }

    @Test
    void testCreationWithParams() {
        StringPersistor mock = mock(StringPersistor.class);
        LogPersistor persistor = new StringPersistorAdapter(mock);

        persistor.save(new LogMessage("PAYLOAD"));
        verify(mock).save(isA(Instant.class), isA(String.class));
    }
}