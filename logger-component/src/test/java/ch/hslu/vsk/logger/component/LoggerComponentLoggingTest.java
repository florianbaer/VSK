package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.component.mocks.LoggerComponentMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class focuses on Testing the logging-aspects of the LoggerComponent.
 */
class LoggerComponentLoggingTest {

    private LoggerComponentMock logger;
    private Throwable throwable;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpLogger() {
        logger =   new LoggerComponentMock(LogLevel.DEBUG,"","", LoggerComponentMock.class);
    }

    @BeforeEach
    void setUpThrowable(){
        this.throwable = new Throwable();

    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


    @Test
    void testDebug() {
        logger.debug("test");
        assertTrue(outContent.toString().contains("DEBUG LoggerComponentMock test"));
    }

    @Test
    void testDebugThrowable() {
        logger.debug("test", throwable);
        assertTrue( outContent.toString().contains(" DEBUG LoggerComponentMock test " + throwable.toString()));
    }

    @Test
    void testInfo() {
        logger.info("test");
        assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " INFO LoggerComponentMock test"));
    }

    @Test
    void testInfoThrowable() {
        logger.info("test", throwable);
        assertTrue(outContent.toString().contains(" INFO LoggerComponentMock test " + throwable.toString()));
    }

    @Test
    void testWarning() {
        logger.warning("test");
        assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " WARNING LoggerComponentMock test"));
    }

    @Test
    void testWarningThrowable() {
        logger.warning("test", throwable);
        assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " WARNING LoggerComponentMock test " + throwable.toString()));
    }

    @Test
    void testError() {
        logger.error("test");
        assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock test"));
    }

    @Test
    void testErrorThrowable() {
        logger.error("test", throwable);
        assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock test " + throwable.toString()));
    }

    @Test
    void testCritical() {
        logger.critical("test");
        assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " CRITICAL LoggerComponentMock test"));
    }

    @Test
    void testCriticalThrowable() {
        logger.critical("test", throwable);
        assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " CRITICAL LoggerComponentMock test " + throwable.toString()));
    }

    @Test
    void testLogError() {
        logger.log(LogLevel.ERROR, "test");
        assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock test"));
    }

    @Test
    void testLogErrorThrowable() {
        logger.log(LogLevel.ERROR, "test", throwable);
        assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock test " + throwable.toString()));
    }

    @Test
    void testSetMinLogLevel() {
        logger.setMinLogLevel(LogLevel.CRITICAL);
        assertEquals(LogLevel.CRITICAL, logger.getMinLogLevel());
    }

    @Test
    void testLogLevelToLow() {
        logger.setMinLogLevel(LogLevel.CRITICAL);
        logger.info("test");
        assertEquals("", outContent.toString());
    }

    @Test
    void testLogLevelHighEnough() {
        logger.setMinLogLevel(LogLevel.ERROR);
        logger.critical("test");
        assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " CRITICAL LoggerComponentMock test"));
    }

    @Test
    void testLogLevelEqual() {
        logger.setMinLogLevel(LogLevel.ERROR);
        logger.error("test");
        assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock test"));
    }

    @Test
    void testGetMinLogLevel() {
        assertEquals(LogLevel.DEBUG, logger.getMinLogLevel());
    }
}