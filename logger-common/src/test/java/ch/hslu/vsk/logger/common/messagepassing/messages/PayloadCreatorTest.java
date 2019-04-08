package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.common.ExceptionSerializer.ExceptionToStringSerializer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the payload creator
 */
public class PayloadCreatorTest {

    @Test
    public void testNormalPayload(){
        String payload = PayloadCreator.generatePayload(LogLevel.INFO, "TestClient", this.getClass(), "This is a test");
        assertTrue(payload.contains("TestClient") && payload.contains("INFO") && payload.contains(this.getClass().getSimpleName())
                && payload.contains("This is a test"));
    }

    @Test
    public void testNormalPayloadNoLoggingClass(){
        String payload = PayloadCreator.generatePayload(LogLevel.INFO, "TestClient", null, "This is a test");
        assertTrue(payload.contains("TestClient") && payload.contains("INFO") && payload.contains("null")
                && payload.contains("This is a test"));
    }

    @Test
    public void testPayloadWithThrowable(){
        IOException ex = new IOException("test");
        String payload = PayloadCreator.generatePayload(LogLevel.INFO, "TestClient", this.getClass(), "This is a test", ex);
        assertTrue(payload.contains("TestClient") && payload.contains("INFO") && payload.contains(this.getClass().getSimpleName())
                && payload.contains("This is a test") && payload.contains(ExceptionToStringSerializer.execute(ex)));


    }
}
