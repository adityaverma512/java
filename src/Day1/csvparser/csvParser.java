package Day1.csvparser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * A CSV parser that filters employee records based on specific conditions using both legacy I/O and modern NIO2 APIs.
 */
public class csvParser {

    // Logger for logging messages and errors
    private static final Logger LOGGER = Logger.getLogger(csvParser.class.getName());

    /**
     * Entry point of the application.
     * It reads employee data from a CSV file and filters based on:
     * - Department ID == 10
     * - Salary >= 10000
     *
     *@throws IOException if there is an error in file operations
     */
    public static void main(String[] args) throws IOException {

        // Input file path
        Path inputFile = Paths.get("Day1/csvparser/Employee.csv");

        // Output file paths
        Path outputFileLegacy = Paths.get("Day1/csvparser/FilterEmployeeUsingStream.csv");
        Path outputFileModern = Paths.get("Day1/csvparser/FilterEmployeeUsingNIO2.csv");

        // Filter using modern NIO2 approach
        filterStreamUsingNIO2(inputFile, outputFileModern);

        // Filter using legacy I/O streams
        filterStreamUsingInputOutputStream(inputFile.toString(), outputFileLegacy.toString());
    }

    /**
     * Filters employees using legacy I/O (BufferedReader and BufferedWriter).
     * Writes only those employees to the output file who belong to department 10 and earn >= 10000.
     *
     * @param inputFile  the path to the input CSV file
     * @param outputFile the path to the output CSV file
     * @throws IOException if file operations fail
     */
    private static void filterStreamUsingInputOutputStream(String inputFile, String outputFile) throws IOException {
        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8)
                );
                BufferedWriter bw = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8)
                )
        ) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    bw.write(line);
                    bw.newLine();
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 4 &&
                        Integer.parseInt(parts[3]) >= 10000 &&
                        Integer.parseInt(parts[2]) == 10) {
                    bw.write(line);
                    bw.newLine();
                }
            }
            LOGGER.info("Filtered output written (legacy) to: " + outputFile);
        } catch (IOException | NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Error processing legacy I/O stream: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Filters employees using Java NIO2 file streams.
     * Uses Java Streams to read, filter, and write lines from and to CSV files.
     *
     * @param inputFile  the path to the input CSV file
     * @param outputFile the path to the output CSV file
     * @throws IOException if file operations fail
     */
    private static void filterStreamUsingNIO2(Path inputFile, Path outputFile) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(inputFile, StandardCharsets.UTF_8)) {

            // Filter employee lines
            List<String> filtered = reader.lines()
                    .filter(l -> !l.startsWith("EMP_NAME")) // Skip header
                    .filter(l -> {
                        String[] parts = l.split(",");
                        try {
                            return parts.length == 4 &&
                                    Integer.parseInt(parts[3]) >= 10000 &&
                                    Integer.parseInt(parts[2]) == 10;
                        } catch (NumberFormatException e) {
                            LOGGER.warning("Skipping malformed line (number format): " + l);
                            return false;
                        }
                    }).collect(Collectors.toList());

            // Re-add the header
            filtered.add(0, "EMP_NAME,EMP_ID,DEPT_ID,SAL");

            // Write the filtered lines to output file
            Files.write(outputFile, filtered, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            LOGGER.info("Filtered output written (NIO2) to: " + outputFile);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error processing file with NIO2: " + e.getMessage(), e);
            throw e;
        }
    }
}
