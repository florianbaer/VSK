package ch.hslu.vsk.logger.component;
import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.LoggerSetupFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class focuses on the creation of the LoggerComponent via properties file and
 * the changing of parameters
 */
public class LoggerComponentParamTest {

    @Test
    public void testSetupPropertiesFile(){
        final LoggerComponent component = (LoggerComponent) LoggerSetupFactory.createSetup().build();
        assertArrayEquals(new String[]{LogLevel.DEBUG.name()}, new String[]{component.getMinLogLevel().name()});
    }

    @Test
    public void testSetupBuilderPattern(){
        final LoggerComponent component = (LoggerComponent) LoggerSetupFactory.createSetup().withMinLogLevel(LogLevel.WARNING).
                withConnectionString("localhost").withClass(LoggerComponentParamTest.class).withIdentifier("test").build();
        assertArrayEquals(new String[]{LogLevel.WARNING.name(), "localhost", LoggerComponentParamTest.class.getName(), "test"},
        new String[]{component.getMinLogLevel().name(), component.getConnectionString(), component.getLoggingClass().getName(), component.getIdentifier()});
    }
}
