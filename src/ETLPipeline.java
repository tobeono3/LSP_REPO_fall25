package org.howard.edu.lsp.assignment2;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ETLPipeline {

    private static final String INPUT_FILE = "data/products.csv";
    private static final String OUTPUT_FILE = "data/transformed_products.csv";

    public static void main(String[] args) {
        List<String[]> transformedRows = new ArrayList<>();
        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;

        File inputFile = new File(INPUT_FILE);
        if (!inputFile.exists()) {
            System.err.println("Error: Input file '" + INPUT_FILE + "' does not exist.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line = br.readLine(); // Read header
            if (line == null) {
                System.err.println("Error: Input file is empty.");
                writeHeaderOnly();
                return;
            }

            // Always write header to output
            transformedRows.add(new String[]{"ProductID", "Name", "Price", "Category", "PriceRange"});

            while ((line = br.readLine()) != null) {
                rowsRead++;
                String[] fields = line.split(",", -1);
                if (fields.length != 4) {
                    rowsSkipped++;
                    continue;
                }

                try {
                    String productId = fields[0].trim();
                    String name = fields[1].trim().toUpperCase(); // Step 1: Uppercase
                    BigDecimal price = new BigDecimal(fields[2].trim());
                    String category = fields[3].trim();

                    // Step 2: Apply 10% discount if Electronics
                    if (category.equalsIgnoreCase("Electronics")) {
                        price = price.multiply(BigDecimal.valueOf(0.9));
                        price = price.setScale(2, RoundingMode.HALF_UP);
                    }

                    // Step 3: Recategorization if price > 500 and original category is Electronics
                    if (price.compareTo(BigDecimal.valueOf(500.00)) > 0 && fields[3].trim().equalsIgnoreCase("Electronics")) {
                        category = "Premium Electronics";
                    }

                    // Step 4: Determine PriceRange
                    String priceRange = getPriceRange(price);

                    // Add transformed row
                    transformedRows.add(new String[]{productId, name, price.toString(), category, priceRange});
                    rowsTransformed++;

                } catch (NumberFormatException e) {
                    rowsSkipped++;
                }
            }

            // Write transformed rows to output file
            writeCSV(transformedRows);

            // Print run summary
            System.out.println("ETL Summary:");
            System.out.println("Rows read: " + rowsRead);
            System.out.println("Rows transformed: " + rowsTransformed);
            System.out.println("Rows skipped: " + rowsSkipped);
            System.out.println("Output file written: " + OUTPUT_FILE);

        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }

    private static String getPriceRange(BigDecimal price) {
        if (price.compareTo(BigDecimal.valueOf(0.00)) >= 0 && price.compareTo(BigDecimal.valueOf(10.00)) <= 0) {
            return "Low";
        } else if (price.compareTo(BigDecimal.valueOf(10.01)) >= 0 && price.compareTo(BigDecimal.valueOf(100.00)) <= 0) {
            return "Medium";
        } else if (price.compareTo(BigDecimal.valueOf(100.01)) >= 0 && price.compareTo(BigDecimal.valueOf(500.00)) <= 0) {
            return "High";
        } else {
            return "Premium";
        }
    }

    private static void writeCSV(List<String[]> rows) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            for (String[] row : rows) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        }
    }

    private static void writeHeaderOnly() {
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
                bw.write("ProductID,Name,Price,Category,PriceRange");
                bw.newLine();
            }
            System.out.println("ETL Summary:");
            System.out.println("Rows read: 0");
            System.out.println("Rows transformed: 0");
            System.out.println("Rows skipped: 0");
            System.out.println("Output file written: " + OUTPUT_FILE);
        } catch (IOException e) {
            System.err.println("Error writing output file: " + e.getMessage());
        }
    }
}
