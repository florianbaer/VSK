package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.common.ExceptionSerializer.ExceptionToStringSerializer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Used to generate payload for the message objects in a nice manner.
 */
final public class PayloadCreator {

    /**
     * Private constructor.
     */
    private PayloadCreator() { }

    /**
     * Return payload composed of given parameters.
     * @param logLevel to log
     * @param message to log
     * @param identifier of the LoggerComponent
     * @param loggingClass that logs the messages
     * @return payload of message object as string
     */
    public static String generatePayload(final LogLevel logLevel, final String identifier, final Class loggingClass, final String message) {
        StringBuilder builder = new StringBuilder();

        SimpleDateFormat swissFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date currentDate = new Date();
        String timestamp =  swissFormat.format(currentDate);

        builder.append(identifier + " " + timestamp + " " + logLevel.toString() + ": " + (loggingClass == null ? "null" : loggingClass.getSimpleName()) + " " + message + " | ");
        return builder.toString();
    }

    /**
     * Return payload composed of given parameters (with exception).
     * @param logLevel to log
     * @param message to log
     * @param identifier of the LoggerComponent
     * @param loggingClass that logs the messages
     * @param e that needs to be transferred
     * @return payload of message object as string
     */
    public static String generatePayload(final LogLevel logLevel, final String identifier, final Class loggingClass, final String message, final Throwable e) {
        String messageWithoutException = generatePayload(logLevel, identifier, loggingClass, message);

        StringBuilder builder = new StringBuilder();

        builder.append(messageWithoutException + ExceptionToStringSerializer.execute(e));
        return builder.toString();
    }
}
