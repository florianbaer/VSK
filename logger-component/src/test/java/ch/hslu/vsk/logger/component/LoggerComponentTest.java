package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LoggerComponentTest {

    private LoggerComponent logger;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpLogger() {
        logger = new LoggerComponent();
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
        assertEquals(logger.getCurrentDateAndTime() + " DEBUG LoggerComponent test", outContent.toString());
    }

    @Test
    void testDebugThrowable() {
    }

    @Test
    void testInfo() {
        logger.info("test");
        assertEquals(logger.getCurrentDateAndTime() + " INFO LoggerComponent test", outContent.toString());
    }

    @Test
    void testInfoThrowable() {
    }

    @Test
    void testWarning() {
        logger.warning("test");
        assertEquals(logger.getCurrentDateAndTime() + " WARNING LoggerComponent test", outContent.toString());
    }

    @Test
    void testWarningThrowable() {
    }

    @Test
    void testError() {
        logger.error("test");
        assertEquals(logger.getCurrentDateAndTime() + " ERROR LoggerComponent test", outContent.toString());
    }

    @Test
    void testErrorThrowable() {
    }

    @Test
    void testCritical() {
        logger.critical("test");
        assertEquals(logger.getCurrentDateAndTime() + " CRITICAL LoggerComponent test", outContent.toString());
    }

    @Test
    void testCriticalThrowable() {
    }

    @Test
    void testLogError() {
        logger.log(LogLevel.ERROR, "test");
        assertEquals(logger.getCurrentDateAndTime() + " ERROR LoggerComponent test", outContent.toString());
    }

    @Test
    void testLogThrowable() {
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
        assertEquals(logger.getCurrentDateAndTime() + " CRITICAL LoggerComponent test", outContent.toString());
    }

    @Test
    void testLogLevelEqual() {
        logger.setMinLogLevel(LogLevel.ERROR);
        logger.error("test");
        assertEquals(logger.getCurrentDateAndTime() + " ERROR LoggerComponent test", outContent.toString());
    }

    @Test
    void testGetMinLogLevel() {
        assertEquals(LogLevel.DEBUG, logger.getMinLogLevel());
    }
}