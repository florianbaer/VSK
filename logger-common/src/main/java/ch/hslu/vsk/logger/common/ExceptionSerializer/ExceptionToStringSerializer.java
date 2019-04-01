package ch.hslu.vsk.logger.common.ExceptionSerializer;

public class ExceptionToStringSerializer {
    private static final String PARAMETER_SEPARATOR = " | ";

    private ExceptionToStringSerializer(){
    }

    /**
     * Converts the given exception to a human readable string.
     * @param ex The exception to be converted.
     * @return The passed exception as readable string;
     */
    public static String Execute(Exception ex){
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMessage());
        builder.append(PARAMETER_SEPARATOR);
        builder.append(ex.getStackTrace());
        return builder.toString();
    }
}
