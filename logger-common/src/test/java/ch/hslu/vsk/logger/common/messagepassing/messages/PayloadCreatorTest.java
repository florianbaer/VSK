package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.api.LogLevel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the payload creator
 */
public class PayloadCreatorTest {

    @Test
    public void testNormalPayload(){
        Instant instant = Instant.now();
        String payload = PayloadCreator.generatePayload(instant, LogLevel.INFO, "TestClient", this.getClass(), "This " +
                "is a test");
        assertEquals( instant.toString() + "|TestClient|INFO|PayloadCreatorTest|This is a test", payload);
    }


    @Test
    public void testPayloadWithThrowable(){
        Instant instant = Instant.now();
        IOException ex = new IOException("test");
        String payload = PayloadCreator.generatePayload(instant, LogLevel.INFO, "TestClient", this.getClass(), "This is a " +
                "test", ex);
        System.out.println(payload);
        assertEquals(instant.toString() + "|TestClient|INFO|PayloadCreatorTest|This is a test|test|ch.hslu.vsk.logger" +
                ".common" +
                ".messagepassing.messages.PayloadCreatorTest.testPayloadWithThrowable(PayloadCreatorTest.java:28)",
                payload);
    }
}
