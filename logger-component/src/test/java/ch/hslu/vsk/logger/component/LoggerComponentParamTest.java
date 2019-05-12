package ch.hslu.vsk.logger.component;
import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.api.LoggerSetupFactory;
import ch.hslu.vsk.logger.component.logger.LoggerComponent;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class focuses on the creation of the LoggerComponent via properties file and
 * the changing of parameters
 */
public class LoggerComponentParamTest {

    @Test
    public void testSetupPropertiesFile() throws IOException {
        ServerSocket socket = new ServerSocket(59090);
        final Logger component = LoggerSetupFactory.createSetup().build();
        assertArrayEquals(new String[]{LogLevel.DEBUG.name()}, new String[]{component.getMinLogLevel().name()});

        socket.close();
    }

    @Test
    public void testSetupBuilderPattern() throws IOException {
        ServerSocket socket = new ServerSocket(59090);
        final LoggerComponent component = (LoggerComponent) LoggerSetupFactory.createSetup().withMinLogLevel(LogLevel.WARNING).
                withConnectionString("localhost:59090").withClass(LoggerComponentParamTest.class).withIdentifier("test").build();
        assertArrayEquals(new String[]{LogLevel.WARNING.name(), "localhost:59090", LoggerComponentParamTest.class.getName(), "test"},
        new String[]{component.getMinLogLevel().name(), component.getConnectionString(), component.getLoggingClass().getName(), component.getIdentifier()});

        socket.close();
    }
}
