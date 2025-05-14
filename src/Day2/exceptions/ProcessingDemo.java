package Day2.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code ProcessingDemo} class contains the main method to demonstrate
 * the usage of the {@link FileProcessor} class. It attempts to process a given file
 * and handles any exceptions that may occur during processing.
 */
public class ProcessingDemo {

    // Logger instance for the ProcessingDemo class
    private static final Logger logger = Logger.getLogger(ProcessingDemo.class.getName());

    /**
     * The main method that initiates file processing using the {@link FileProcessor}.
     */
    public static void main(String[] args) {
        FileProcessor processor = new FileProcessor();
        String filePath = "Day1/exceptions/sample.txt"; // Specify the file you want to process

        try {
            logger.info("Starting file processing for: " + filePath);
            processor.processFile(filePath);
            logger.info("File processed successfully.");
        } catch (FileProcessingException e) {
            logger.log(Level.SEVERE, "File Processing Error: " + e.getMessage(), e);
            System.err.println("File Processing Error: " + e.getMessage());
        } catch (ParsingException e) {
            logger.log(Level.WARNING, "Parsing Error: " + e.getMessage(), e);
            System.err.println("Parsing Error: " + e.getMessage());
        }
    }
}
