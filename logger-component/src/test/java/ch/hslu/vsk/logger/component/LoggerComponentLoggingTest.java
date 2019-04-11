package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.component.logger.LoggerComponent;
import ch.hslu.vsk.logger.component.services.NetworkCommunication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

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
    private final String errorMessage = "THROWABLE";

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
    void createComponentWithoutNetworkServiceTest() {
        String connectionString = "localhost:59090";
        String identifier = "test";
        LogLevel minLogLevel = LogLevel.DEBUG;
        LogLevel newMinLogLevel = LogLevel.DEBUG;
        var component = new LoggerComponent(minLogLevel , connectionString, identifier, LoggerComponentLoggingTest.class);
        assertEquals(connectionString, component.getConnectionString());
        assertEquals(identifier, component.getIdentifier());
        assertEquals(minLogLevel, component.getMinLogLevel());
        component.setMinLogLevel(newMinLogLevel);
        assertEquals(newMinLogLevel, component.getMinLogLevel());
    }

    @Test
    void testDebugInvoked() {
        this.setUpLogger(LogLevel.DEBUG);
        this.logger.debug("test-debug");
        verify(this.networkServiceMock, times(1)).sendMessageToServer(contains("test-debug"));
    }

    @Test
    void testDebugExceptionInvoked() {
        this.setUpThrowable();

        String logMessage = "test-debug";

        when(this.throwable.getMessage()).thenReturn(errorMessage);
        this.setUpLogger(LogLevel.DEBUG);
        this.logger.debug(logMessage, this.throwable);
        verify(this.networkServiceMock, times(1)).sendMessageToServer(matches(String.format("(.*%s.*%s.*)",
                logMessage, errorMessage)));
    }

    @Test
    void testDebugExceptionNotInvoked() {
        this.setUpThrowable();

        String logMessage = "test-debug";

        when(this.throwable.getMessage()).thenReturn(errorMessage);
        this.setUpLogger(LogLevel.INFO);
        this.logger.debug(logMessage, this.throwable);
        verify(this.networkServiceMock, never()).sendMessageToServer(isA(String.class));
    }

    @Test
    void testDebugNotInvoked() {
        this.setUpLogger(LogLevel.INFO);
        this.logger.debug("test-debug");
        verify(this.networkServiceMock, never()).sendMessageToServer(isA(String.class));
    }

    @Test
    void testInfoInvoked() {
        this.setUpLogger(LogLevel.INFO);
        this.logger.info("test-info");
        verify(this.networkServiceMock, times(1)).sendMessageToServer(contains("test-info"));
    }

    @Test
    void testInfoExceptionInvoked() {
        this.setUpThrowable();

        String logMessage = "test-info";

        when(this.throwable.getMessage()).thenReturn(errorMessage);
        this.setUpLogger(LogLevel.INFO);
        this.logger.info(logMessage, this.throwable);
        verify(this.networkServiceMock, times(1)).sendMessageToServer(matches(String.format("(.*%s.*%s.*)",
                logMessage , errorMessage)));
    }

    @Test
    void testInfoExceptionNotInvoked() {
        this.setUpThrowable();

        String logMessage = "test-info";

        when(this.throwable.getMessage()).thenReturn(errorMessage);
        this.setUpLogger(LogLevel.WARNING);
        this.logger.info(logMessage, this.throwable);
        verify(this.networkServiceMock, never()).sendMessageToServer(isA(String.class));
    }

    @Test
    void testInfoNotInvoked() {
        this.setUpLogger(LogLevel.WARNING);
        this.logger.info("test-info");
        verify(this.networkServiceMock, never()).sendMessageToServer(isA(String.class));
    }

    @Test
    void testWarningInvoked() {
        this.setUpLogger(LogLevel.WARNING);
        this.logger.warning("test-warning");
        verify(this.networkServiceMock, times(1)).sendMessageToServer(contains("test-warning"));
    }

    @Test
    void testWarningExceptionInvoked() {
        this.setUpThrowable();

        String logMessage = "test-warning";

        when(this.throwable.getMessage()).thenReturn(errorMessage);
        this.setUpLogger(LogLevel.WARNING);
        this.logger.warning(logMessage, this.throwable);
        verify(this.networkServiceMock, times(1)).sendMessageToServer(matches(String.format("(.*%s.*%s.*)",
                logMessage , errorMessage)));
    }

    @Test
    void testWarningExceptionNotInvoked() {
        this.setUpThrowable();

        String logMessage = "test-warning";

        when(this.throwable.getMessage()).thenReturn(errorMessage);
        this.setUpLogger(LogLevel.ERROR);
        this.logger.warning(logMessage, this.throwable);
        verify(this.networkServiceMock, never()).sendMessageToServer(isA(String.class));
    }

    @Test
    void testWarningNotInvoked() {
        this.setUpLogger(LogLevel.ERROR);
        this.logger.warning("test-warning");
        verify(this.networkServiceMock, never()).sendMessageToServer(isA(String.class));
    }

    @Test
    void testErrorInvoked() {
        this.setUpLogger(LogLevel.ERROR);
        this.logger.error("test-error");
        verify(this.networkServiceMock, times(1)).sendMessageToServer(contains("test-error"));
    }

    @Test
    void testErrorExceptionInvoked() {
        this.setUpThrowable();

        String logMessage = "test-error";

        when(this.throwable.getMessage()).thenReturn(errorMessage);
        this.setUpLogger(LogLevel.ERROR);
        this.logger.error(logMessage, this.throwable);
        verify(this.networkServiceMock, times(1)).sendMessageToServer(matches(String.format("(.*%s.*%s.*)",
                logMessage , errorMessage)));
    }

    @Test
    void testErrorExceptionNotInvoked() {
        this.setUpThrowable();

        String logMessage = "test-error";

        when(this.throwable.getMessage()).thenReturn(errorMessage);
        this.setUpLogger(LogLevel.CRITICAL);
        this.logger.error(logMessage, this.throwable);
        verify(this.networkServiceMock, never()).sendMessageToServer(isA(String.class));
    }

    @Test
    void testErrorNotInvoked() {
        this.setUpLogger(LogLevel.CRITICAL);
        this.logger.error("test-error");
        verify(this.networkServiceMock, never()).sendMessageToServer(isA(String.class));
    }

    @Test
    void testCriticalInvoked() {
        this.setUpLogger(LogLevel.CRITICAL);
        this.logger.critical("test-critical");
        verify(this.networkServiceMock, times(1)).sendMessageToServer(contains("test-critical"));
    }

    @Test
    void testCriticalExceptionInvoked() {
        this.setUpThrowable();

        String logMessage = "test-critical";

        when(this.throwable.getMessage()).thenReturn(errorMessage);
        this.setUpLogger(LogLevel.CRITICAL);
        this.logger.critical(logMessage, this.throwable);
        verify(this.networkServiceMock, times(1)).sendMessageToServer(matches(String.format("(.*%s.*%s.*)",
                logMessage , errorMessage)));
    }

    @Test
    void testCriticalExceptionNotInvoked() {
        this.setUpThrowable();

        String logMessage = "test-critical";

        when(this.throwable.getMessage()).thenReturn(errorMessage);
        this.setUpLogger(LogLevel.OFF);
        this.logger.critical(logMessage, this.throwable);
        verify(this.networkServiceMock, never()).sendMessageToServer(isA(String.class));
    }

    @Test
    void testCriticalNotInvoked() {
        this.setUpLogger(LogLevel.OFF);
        this.logger.critical("test-critical");
        verify(this.networkServiceMock, never()).sendMessageToServer(isA(String.class));
    }
}