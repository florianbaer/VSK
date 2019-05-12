package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.common.ExceptionSerializer.ExceptionToStringSerializer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link ExceptionToStringSerializer}.
 */
public class ExceptionToStringSerializerTest {

    /**
     * Tests if the serialization does work properly.
     */
    @Test
    void testExceptionToStringSerializer(){
        String exceptionText = "NoClue";
        String result = ExceptionToStringSerializer.execute(new IllegalArgumentException(exceptionText));
        assertThat(result).contains(exceptionText);
        assertThat(result).contains("testExceptionToStringSerializer");
    }

}
