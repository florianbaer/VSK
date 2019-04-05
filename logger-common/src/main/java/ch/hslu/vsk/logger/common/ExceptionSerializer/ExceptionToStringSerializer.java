package ch.hslu.vsk.logger.common.ExceptionSerializer;

public class ExceptionToStringSerializer {

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

        int i = 0;
        for(StackTraceElement element :  ex.getStackTrace()){
            builder.append(System.getProperty("line.separator"));
            builder.append(element.toString());
            i++;

            if(i > 5){
                break;
            }
        }

        return builder.toString();
    }
}
