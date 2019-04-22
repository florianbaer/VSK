package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.component.logger.LoggerProperties;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the LoggerProperties
 */
public class LoggerPropertiesTest {

    @Test
    public void testLoggerPropertiesFileCreation() throws IOException {
        LoggerProperties properties = new LoggerProperties();
        File file = new File("logger.properties");
        file.delete();
        file.deleteOnExit();

        assertEquals(file.isFile(), false);
        properties.loadProperties();

        assertEquals(file.isFile(), true);
    }

    @Test
    public void testLoggerPropertiesDefaultInstantiation() throws IOException {
        LoggerProperties properties = new LoggerProperties();
        properties.loadProperties();

        assertEquals(properties.getPropertyConnectionString(), "localhost:59090");
        assertEquals(properties.getPropertyIdentifier(), "test");
        assertEquals(properties.getPropertyMinLogLevel().name(), "DEBUG");
        assertEquals(properties.getPropertyLocalFile(), "");
    }
}
