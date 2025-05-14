package Day2.exceptions;

/**
 * The {@code ParsingException} is a custom checked exception that is thrown
 * when a line from a file cannot be parsed into the expected format, such as
 * when attempting to convert a string to an integer and the format is invalid.
 */
public class ParsingException extends Exception {

    /**
     * Constructs a new {@code ParsingException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public ParsingException(String message) {
        super(message);
    }
}
