package Day2.DataProcessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The {@code FrequentWords} class reads a text file, processes its content,
 * and identifies the top N most frequent words.
 * <p>
 * It uses Java Streams, lambda expressions, and a custom functional interface
 * to normalize words. Non-word characters are used as delimiters, and the words
 * are normalized to lowercase before counting.
 */
public class FrequentWords {

    // Logger instance for logging execution details
    private static final Logger logger = Logger.getLogger(FrequentWords.class.getName());

    /**
     * The entry point of the application. Reads words from a file, normalizes them,
     * computes frequencies, and prints the top N most frequent words.
     *
     * @param args command-line arguments (not used)
     * @throws IOException if an error occurs while reading the file
     */
    public static void main(String[] args) throws IOException {
        String filePath = "Day1/DataProcessing/sampledata";
        int topN = 5;

        logger.info("Starting word frequency analysis for file: " + filePath);

        // Read all words from file
        List<String> words;
        try {
            words = Files.lines(Paths.get(filePath))
                    .map(String::toLowerCase)
                    .flatMap(line -> Arrays.stream(line.split("\\W+"))) // split on non-word chars
                    .filter(Predicate.not(String::isBlank)) // filter out empty words
                    .collect(Collectors.toList());
            logger.info("Successfully read and tokenized words from file.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to read file: " + filePath, e);
            throw e;
        }

        // Normalization function
        WordNormalizer normalizer = word -> word.trim().toLowerCase();

        // Frequency map
        Map<String, Long> frequencyMap = words.stream()
                .map(normalizer::normalize)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        logger.fine("Word frequency map created.");

        // Top N frequent words
        List<Map.Entry<String, Long>> topWords = frequencyMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(topN)
                .collect(Collectors.toList());
        logger.info("Top " + topN + " frequent words computed.");

        // Output result
        topWords.forEach(entry ->
                System.out.printf("Word: '%s' -> Frequency: %d%n", entry.getKey(), entry.getValue()));
    }
}

/**
 * Functional interface for word normalization.
 * Implementations should define how words are transformed before frequency analysis.
 */
@FunctionalInterface
interface WordNormalizer {
    /**
     * Normalizes a given word (e.g., by trimming, converting to lowercase).
     *
     * @param word the input word to normalize
     * @return the normalized word
     */
    String normalize(String word);
}
