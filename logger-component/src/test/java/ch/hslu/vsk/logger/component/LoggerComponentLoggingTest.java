package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.LoggerSetupFactory;

import ch.hslu.vsk.logger.component.logger.LoggerComponent;
import ch.hslu.vsk.logger.component.mocks.LoggerComponentMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class focuses on Testing the logging-aspects of the LoggerComponent
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
        // replaceAll("\\r\\n|\\r|\\n", "") is needed to eliminate the different line-breaks for Unix/Windows systems.
        assertEquals(logger.getCurrentDateAndTime() + " DEBUG LoggerComponentMock test", outContent.toString().replaceAll("\\r\\n|\\r|\\n", ""));
    }

    @Test
    void testDebugThrowable() {
        logger.debug("test", throwable);
        assertEquals(logger.getCurrentDateAndTime() + " DEBUG LoggerComponentMock test " + throwable.toString(), outContent.toString().replaceAll("\\r\\n|\\r|\\n", ""));
    }

    @Test
    void testInfo() {
        logger.info("test");
        assertEquals(logger.getCurrentDateAndTime() + " INFO LoggerComponentMock test", outContent.toString().replaceAll("\\r\\n|\\r|\\n", ""));
    }

    @Test
    void testInfoThrowable() {
        logger.info("test", throwable);
        assertEquals(logger.getCurrentDateAndTime() + " INFO LoggerComponentMock test " + throwable.toString(), outContent.toString().replaceAll("\\r\\n|\\r|\\n", ""));
    }

    @Test
    void testWarning() {
        logger.warning("test");
        assertEquals(logger.getCurrentDateAndTime() + " WARNING LoggerComponentMock test", outContent.toString().replaceAll("\\r\\n|\\r|\\n", ""));
    }

    @Test
    void testWarningThrowable() {
        logger.warning("test", throwable);
        assertEquals(logger.getCurrentDateAndTime() + " WARNING LoggerComponentMock test " + throwable.toString(), outContent.toString().replaceAll("\\r\\n|\\r|\\n", ""));
    }

    @Test
    void testError() {
        logger.error("test");
        assertEquals(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock test", outContent.toString().replaceAll("\\r\\n|\\r|\\n", ""));
    }

    @Test
    void testErrorThrowable() {
        logger.error("test", throwable);
        assertEquals(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock test " + throwable.toString(), outContent.toString().replaceAll("\\r\\n|\\r|\\n", ""));
    }

    @Test
    void testCritical() {
        logger.critical("test");
        assertEquals(logger.getCurrentDateAndTime() + " CRITICAL LoggerComponentMock test", outContent.toString().replaceAll("\\r\\n|\\r|\\n", ""));
    }

    @Test
    void testCriticalThrowable() {
        logger.critical("test", throwable);
        assertEquals(logger.getCurrentDateAndTime() + " CRITICAL LoggerComponentMock test " + throwable.toString(), outContent.toString().replaceAll("\\r\\n|\\r|\\n", ""));
    }

    @Test
    void testLogError() {
        logger.log(LogLevel.ERROR, "test");
        assertEquals(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock test", outContent.toString().replaceAll("\\r\\n|\\r|\\n", ""));
    }

    @Test
    void testLogErrorThrowable() {
        logger.log(LogLevel.ERROR, "test", throwable);
        assertEquals(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock test " + throwable.toString(), outContent.toString().replaceAll("\\r\\n|\\r|\\n", ""));
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
        assertEquals(logger.getCurrentDateAndTime() + " CRITICAL LoggerComponentMock test", outContent.toString().replaceAll("\\r\\n|\\r|\\n", ""));
    }

    @Test
    void testLogLevelEqual() {
        logger.setMinLogLevel(LogLevel.ERROR);
        logger.error("test");
        assertEquals(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock test", outContent.toString().replaceAll("\\r\\n|\\r|\\n", ""));
    }

    @Test
    void testGetMinLogLevel() {
        assertEquals(LogLevel.DEBUG, logger.getMinLogLevel());
    }
}