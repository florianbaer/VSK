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
    public static String execute(final Exception ex) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMessage());

        int i = 0;
        for (StackTraceElement element :  ex.getStackTrace()) {
            builder.append(System.getProperty("line.separator"));
            builder.append(element.toString());
            i++;

            if (i > 5) {
                break;
            }
        }

        return builder.toString();
    }
}
