package Day2.exceptions;

import java.io.*;
import java.util.logging.Logger;

/**
 * The {@code FileProcessor} class provides functionality to read and process the contents
 * of a file line by line. It attempts to parse each line as an integer and handles
 * different exceptions such as file not found, IO errors, and parsing issues.
 */
public class FileProcessor {

    // Logger instance for the FileProcessor class
    private static final Logger logger = Logger.getLogger(FileProcessor.class.getName());

    /**
     * Processes the specified file by reading each line and attempting to parse it as an integer.
     * <p>
     * If the file is not found or an I/O error occurs during reading, a {@link FileProcessingException}
     * is thrown. If a line cannot be parsed into an integer, a {@link ParsingException} is thrown.
     * </p>
     *
     * @param filePath the path to the file to be processed
     * @throws FileProcessingException if the file is not found or a read error occurs
     * @throws ParsingException if any line in the file cannot be parsed as an integer
     */
    public void processFile(String filePath) throws FileProcessingException, ParsingException {
        BufferedReader reader = null;
        try {
            logger.info("Attempting to process file: " + filePath);
            File file = new File(filePath);
            reader = new BufferedReader(new FileReader(file));
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                try {
                    int parsedValue = Integer.parseInt(line);
                    logger.fine("Parsed value from line " + lineNumber + ": " + parsedValue);
                    System.out.println("Parsed value: " + parsedValue);
                } catch (NumberFormatException e) {
                    logger.warning("Parsing failed at line " + lineNumber + ": \"" + line + "\"");
                    throw new ParsingException("Error parsing the line: " + line + " - Invalid number format.");
                }
                lineNumber++;
            }

            logger.info("File processed successfully: " + filePath);

        } catch (FileNotFoundException e) {
            logger.severe("File not found: " + filePath);
            throw new FileProcessingException("File not found: " + filePath);
        } catch (IOException e) {
            logger.severe("I/O error while reading file: " + filePath);
            throw new FileProcessingException("Error reading file: " + filePath);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    logger.fine("File reader closed successfully.");
                } catch (IOException e) {
                    logger.warning("Error closing the file reader.");
                }
            }
        }
    }
}
