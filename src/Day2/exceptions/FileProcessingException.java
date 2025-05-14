package Day2.exceptions;

/**
 * The {@code FileProcessingException} is a custom checked exception that is thrown
 * to indicate issues during file processing, such as when a file is not found
 * or an I/O error occurs while reading the file.
 */
public class FileProcessingException extends Exception {

    /**
     * Constructs a new {@code FileProcessingException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public FileProcessingException(String message) {
        super(message);
    }
}
