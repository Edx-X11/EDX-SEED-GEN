import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class SeedGenerator {

    // Function to generate a random seed
    private static long generateRandomSeed(String input) {
        long seed;
        try {
            // Convert input to a 64-bit integer
            seed = Long.parseLong(input);
        } catch (NumberFormatException e) {
            // Input is not a number, so use a hash function to generate a seed
            seed = 0;
            for (char c : input.toCharArray()) {
                seed = (seed << 5) + c;
            }
        }
        return seed;
    }

    // Function to save a single seed to file
    private static void saveSeedToFile(long seed, String filename) {
        try (FileWriter fileWriter = new FileWriter(filename, true)) {
            fileWriter.write(seed + "\n");
            System.out.println("Seed saved to " + filename + ": " + seed);
        } catch (IOException e) {
            System.err.println("Error: Could not open file '" + filename + "' for writing.");
        }
    }

    // Function to save seeds to file based on the output type
    private static void saveSeedsToFile(List<Long> seeds, String filename) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            for (long seed : seeds) {
                fileWriter.write(seed + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error: Could not open file '" + filename + "' for writing.");
        }
    }

    // Function to generate seeds
    private static void generateAndSaveSeeds(int count, String outputType, String filenamePrefix) {
        Set<Long> seeds = new HashSet<>(); // Use a set to store unique seeds

        // Loop for automatic seed generation
        Random random = new Random(System.currentTimeMillis());
        while (seeds.size() < count) {
            long seed = random.nextLong(); // Generate a random seed

            // Ensure seed is within the specified range and not 0
            if (seed != 0 && Math.abs(seed) >= 3 && String.valueOf(seed).length() <= 20) {
                seeds.add(seed); // Add the seed to the set
            }
        }

        // Convert set to list for shuffling (if needed)
        List<Long> shuffledSeeds = new ArrayList<>(seeds);

        // Shuffle the seeds to ensure randomness
        Collections.shuffle(shuffledSeeds, random);

        // Output seeds based on the specified format
        switch (outputType) {
            case "txt":
            case "json":
            case "csv":
            case "nbt":
                saveSeedsToFile(shuffledSeeds, filenamePrefix + "_seeds." + outputType);
                break;
            case "all":
                saveSeedsToFile(shuffledSeeds, filenamePrefix + "_seeds.txt");
                saveSeedsToFile(shuffledSeeds, filenamePrefix + "_seeds.json");
                saveSeedsToFile(shuffledSeeds, filenamePrefix + "_seeds.csv");
                saveSeedsToFile(shuffledSeeds, filenamePrefix + "_seeds.nbt");
                break;
            default:
                System.err.println("Invalid output type. Supported types: txt, json, csv, nbt, all.");
        }
    }

    // Function to validate user input
    private static boolean isValidChoice(char choice) {
        return (choice == '1' || choice == '2' || choice == '3');
    }

    // Function to run the seed generator
    public void runSeedGenerator() {
        Scanner scanner = new Scanner(System.in);
        char choice;
        boolean exit = false;

        do {
            System.out.println("\nSelect an option:");
            System.out.println("1. Input your own seed");
            System.out.println("2. Automatically generate seeds");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.next().charAt(0);

            if (!isValidChoice(choice)) {
                System.err.println("Invalid choice. Please enter a number between 1 and 3.");
                continue;
            }

            switch (choice) {
                case '1':
                    System.out.print("Enter your own seed: ");
                    String input = scanner.next();
                    long seed = generateRandomSeed(input);
                    System.out.println("Generated Seed: " + seed);
                    saveSeedToFile(seed, "custom_seeds.txt");
                    break;
                case '2':
                    System.out.print("Enter the number of seeds to generate: ");
                    int count = scanner.nextInt();
                    System.out.print("Enter the output type (txt, json, csv, nbt, all): ");
                    String outputType = scanner.next();
                    System.out.print("Enter the prefix for filename (e.g., 'my_seeds'): ");
                    String filenamePrefix = scanner.next();
                    generateAndSaveSeeds(count, outputType, filenamePrefix);
                    break;
                case '3':
                    exit = true;
                    break;
                default:
                    break;
            }
        } while (!exit);

        System.out.println("Exiting program. Goodbye!");
    }
}
