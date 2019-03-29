package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.LoggerSetupFactory;

import ch.hslu.vsk.logger.component.logger.LoggerComponent;
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

    private LoggerComponent logger;
    private Throwable throwable;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpLogger() {
        logger =   (LoggerComponent) LoggerSetupFactory.createSetup().build();
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
        assertEquals(logger.getCurrentDateAndTime() + " DEBUG LoggerComponent test", outContent.toString().replaceAll("\n", ""));
    }

    @Test
    void testDebugThrowable() {
        logger.debug("test", throwable);
        assertEquals(logger.getCurrentDateAndTime() + " DEBUG LoggerComponent test " + throwable.toString(), outContent.toString().replaceAll("\n", ""));
    }

    @Test
    void testInfo() {
        logger.info("test");
        assertEquals(logger.getCurrentDateAndTime() + " INFO LoggerComponent test", outContent.toString().replaceAll("\n", ""));
    }

    @Test
    void testInfoThrowable() {
        logger.info("test", throwable);
        assertEquals(logger.getCurrentDateAndTime() + " INFO LoggerComponent test " + throwable.toString(), outContent.toString().replaceAll("\n", ""));
    }

    @Test
    void testWarning() {
        logger.warning("test");
        assertEquals(logger.getCurrentDateAndTime() + " WARNING LoggerComponent test", outContent.toString().replaceAll("\n", ""));
    }

    @Test
    void testWarningThrowable() {
        logger.warning("test", throwable);
        assertEquals(logger.getCurrentDateAndTime() + " WARNING LoggerComponent test " + throwable.toString(), outContent.toString().replaceAll("\n", ""));
    }

    @Test
    void testError() {
        logger.error("test");
        assertEquals(logger.getCurrentDateAndTime() + " ERROR LoggerComponent test", outContent.toString().replaceAll("\n", ""));
    }

    @Test
    void testErrorThrowable() {
        logger.error("test", throwable);
        assertEquals(logger.getCurrentDateAndTime() + " ERROR LoggerComponent test " + throwable.toString(), outContent.toString().replaceAll("\n", ""));
    }

    @Test
    void testCritical() {
        logger.critical("test");
        assertEquals(logger.getCurrentDateAndTime() + " CRITICAL LoggerComponent test", outContent.toString().replaceAll("\n", ""));
    }

    @Test
    void testCriticalThrowable() {
        logger.critical("test", throwable);
        assertEquals(logger.getCurrentDateAndTime() + " CRITICAL LoggerComponent test " + throwable.toString(), outContent.toString().replaceAll("\n", ""));
    }

    @Test
    void testLogError() {
        logger.log(LogLevel.ERROR, "test");
        assertEquals(logger.getCurrentDateAndTime() + " ERROR LoggerComponent test", outContent.toString().replaceAll("\n", ""));
    }

    @Test
    void testLogErrorThrowable() {
        logger.log(LogLevel.ERROR, "test", throwable);
        assertEquals(logger.getCurrentDateAndTime() + " ERROR LoggerComponent test " + throwable.toString(), outContent.toString().replaceAll("\n", ""));
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
        assertEquals(logger.getCurrentDateAndTime() + " CRITICAL LoggerComponent test", outContent.toString().replaceAll("\n", ""));
    }

    @Test
    void testLogLevelEqual() {
        logger.setMinLogLevel(LogLevel.ERROR);
        logger.error("test");
        assertEquals(logger.getCurrentDateAndTime() + " ERROR LoggerComponent test", outContent.toString().replaceAll("\n", ""));
    }

    @Test
    void testGetMinLogLevel() {
        assertEquals(LogLevel.DEBUG, logger.getMinLogLevel());
    }
}