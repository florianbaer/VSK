package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.component.logger.LoggerComponent;
import ch.hslu.vsk.logger.component.services.NetworkCommunication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 * This test class focuses on Testing the logging-aspects of the LoggerComponent.
 */
class LoggerComponentLoggingTest {

    private LoggerComponent logger;
    private Throwable throwable;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private NetworkCommunication networkServiceMock = mock(NetworkCommunication.class);

    void setUpLogger(LogLevel level) {
        this.logger =   new LoggerComponent(level , "localhost:59090", "test", LoggerComponentLoggingTest.class, this.networkServiceMock);
    }

    void setUpThrowable(){
        this.throwable = mock(Throwable.class);
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
    void testDebugWorking() {
        this.setUpLogger(LogLevel.DEBUG);
        this.logger.debug("test-debug");
        verify(this.networkServiceMock, times(1)).sendMessageToServer(contains("test-debug"));
    }

    @Test
    void testDebugExceptionWorking() {
        this.setUpThrowable();

        String logMessage = "test-debug";
        String errorMessage = "THROWABLE";

        when(this.throwable.getMessage()).thenReturn(errorMessage);
        this.setUpLogger(LogLevel.DEBUG);
        this.logger.debug(logMessage, this.throwable);
        verify(this.networkServiceMock, times(1)).sendMessageToServer(matches(String.format("(.*%s.*%s.*)", "test-debug", errorMessage)));
    }

    @Test
    void testDebugNotInvoked() {
        this.setUpLogger(LogLevel.INFO);
        this.logger.debug("test-debug");
        verify(this.networkServiceMock, never()).sendMessageToServer(isA(String.class));
    }

    @Test
    void testDebugThrowable() {
        //logger.debug("test", throwable);
        //assertTrue( outContent.toString().contains(" DEBUG LoggerComponentMock test " + throwable.toString()));
    }

    @Test
    void testInfo() {
        //logger.info("test");
        //assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " INFO LoggerComponentMock test"));
    }

    @Test
    void testInfoThrowable() {
        //logger.info("test", throwable);
        //assertTrue(outContent.toString().contains(" INFO LoggerComponentMock test " + throwable.toString()));
    }

    @Test
    void testWarning() {
        //logger.warning("test");
        //assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " WARNING LoggerComponentMock " +
               // "test"));
    }

    @Test
    void testWarningThrowable() {
        //logger.warning("test", throwable);
        //assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " WARNING LoggerComponentMock " +
                //"test " + throwable.toString()));
    }

    @Test
    void testError() {
        //logger.error("test");
        //assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock
        // test"));
    }

    @Test
    void testErrorThrowable() {
        //logger.error("test", throwable);
        //assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock test
        // " + throwable.toString()));
    }

    @Test
    void testCritical() {
        //logger.critical("test");
        //assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " CRITICAL LoggerComponentMock " +
                //"test"));
    }

    @Test
    void testCriticalThrowable() {
        //logger.critical("test", throwable);
        //assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " CRITICAL LoggerComponentMock " +
                //"test " + throwable.toString()));
    }

    @Test
    void testLogError() {
        //logger.log(LogLevel.ERROR, "test");
        //assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock
        // test"));
    }

    @Test
    void testLogErrorThrowable() {
        //logger.log(LogLevel.ERROR, "test", throwable);
        //assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock test
        // " + throwable.toString()));
    }

    @Test
    void testSetMinLogLevel() {
        //logger.setMinLogLevel(LogLevel.CRITICAL);
        //assertEquals(LogLevel.CRITICAL, logger.getMinLogLevel());
    }

    @Test
    void testLogLevelToLow() {
        //logger.setMinLogLevel(LogLevel.CRITICAL);
        //logger.info("test");
        //assertEquals("", outContent.toString());
    }

    @Test
    void testLogLevelHighEnough() {
        //logger.setMinLogLevel(LogLevel.ERROR);
        //logger.critical("test");
        //assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " CRITICAL LoggerComponentMock " +
                //"test"));
    }

    @Test
    void testLogLevelEqual() {
        //logger.setMinLogLevel(LogLevel.ERROR);
        //logger.error("test");
        //assertTrue(outContent.toString().contains(logger.getCurrentDateAndTime() + " ERROR LoggerComponentMock
        // test"));
    }

    @Test
    void testGetMinLogLevel() {
        //assertEquals(LogLevel.DEBUG, logger.getMinLogLevel());
    }
}