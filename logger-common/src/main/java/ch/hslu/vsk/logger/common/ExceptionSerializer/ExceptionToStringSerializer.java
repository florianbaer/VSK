package ch.hslu.vsk.logger.common.ExceptionSerializer;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Class that generates readable String from an Exception.
 */
public final class ExceptionToStringSerializer {
    /**
     * Private constructor.
     */
    private ExceptionToStringSerializer() { }

    /**
     * Converts the given exception to a human readable string.
     * @param ex The exception to be converted.
     * @return The passed exception as readable string;
     */
    public static String execute(final Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
