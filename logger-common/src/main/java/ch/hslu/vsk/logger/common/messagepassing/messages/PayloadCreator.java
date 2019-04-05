package ch.hslu.vsk.logger.common.messagepassing.messages;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.common.ExceptionSerializer.ExceptionToStringSerializer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Used to generate payload for the message objects in a nice manner.
 */
public class PayloadCreator {

    /**
     * Return payload composed of given parameters
     * @param logLevel
     * @param identifier
     * @param loggingClass
     * @return payload of message object as string
     */
    public static String generatePayload(LogLevel logLevel, String identifier, Class loggingClass, String message){
        StringBuilder builder = new StringBuilder();

        SimpleDateFormat swissFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date currentDate = new Date();
        String timestamp =  swissFormat.format(currentDate);

        builder.append(identifier + " " + timestamp + " " + logLevel.toString() + ": " + (loggingClass == null ? "null" : loggingClass.getSimpleName()) + " " + message + " | ");
        return builder.toString();
    }

    /**
     * Return payload composed of given parameters
     * @param logLevel
     * @param identifier
     * @param loggingClass
     * @param e
     * @return payload of message object as string
     */
    public static String generatePayload(LogLevel logLevel, String identifier, Class loggingClass, String message, Exception e){
        String messageWithoutException = generatePayload(logLevel, identifier, loggingClass, message);

        StringBuilder builder = new StringBuilder();

        builder.append(messageWithoutException + ExceptionToStringSerializer.Execute(e));
        return builder.toString();
    }
}
