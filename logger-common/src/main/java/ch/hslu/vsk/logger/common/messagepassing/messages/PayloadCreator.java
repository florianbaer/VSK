package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.common.ExceptionSerializer.ExceptionToStringSerializer;
import java.time.Instant;

/**
 * Used to generate payload for the message objects in a nice manner.
 */
public final class PayloadCreator {
    private static final String SEPARATOR = "|";

    /**
     * Private constructor.
     */
    private PayloadCreator() { }

    /**
     * Return payload composed of given parameters.
     * @param instant the recorded time
     * @param logLevel to log
     * @param message to log
     * @param identifier of the LoggerComponent
     * @param loggingClass that logs the messages
     * @return payload of message object as string
     */
    public static String generatePayload(final Instant instant, final LogLevel logLevel, final String identifier,
                                         final Class loggingClass,
                                         final String message) {
        StringBuilder builder = new StringBuilder();
        try {
            String formattedMessage = message.trim();
            builder.append(instant.toString() + SEPARATOR + identifier + SEPARATOR + logLevel.toString()
                    + SEPARATOR + loggingClass.getSimpleName() + SEPARATOR + formattedMessage);
            return builder.toString();
        } catch (Exception e) {
            System.out.println("Error while generating payload: " + e.getMessage());
        }
        return null;
    }

    /**
     * Return payload composed of given parameters (with exception).
     * @param instant the recorded time
     * @param logLevel to log
     * @param message to log
     * @param identifier of the LoggerComponent
     * @param loggingClass that logs the messages
     * @param e that needs to be transferred
     * @return payload with Throwable of message object as string
     */
    public static String generatePayload(final Instant instant, final LogLevel logLevel, final String identifier,
                                         final Class loggingClass,
                                         final String message, final Throwable e) {
        StringBuilder builder = new StringBuilder();
        try {
            String payloadWithoutError = generatePayload(instant, logLevel, identifier, loggingClass, message);
            builder.append(payloadWithoutError + SEPARATOR + ExceptionToStringSerializer.execute(e));
            return builder.toString();
        } catch (Exception ex) {
            System.out.println("Error while generating payload with Throwable: " + ex.getMessage());
        }

        return null;
    }
}
