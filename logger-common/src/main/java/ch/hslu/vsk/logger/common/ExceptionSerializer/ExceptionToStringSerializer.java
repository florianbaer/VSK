package ch.hslu.vsk.logger.common.ExceptionSerializer;


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
    public static String execute(final Throwable ex) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ex.getMessage());
        stringBuilder.append("|");
        StackTraceElement[] stackTrace = (ex.getStackTrace());
        stringBuilder.append(stackTrace[0].toString());
        return stringBuilder.toString();
    }
}
