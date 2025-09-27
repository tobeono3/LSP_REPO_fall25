package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for Assignment 3 ETL pipeline.
 *
 * Reads data/products.csv, transforms, and writes data/transformed_products.csv.
 * Prints a run summary at the end.
 */
public class ETLPipeline {
    private static final Path INPUT_PATH = Paths.get("data", "products.csv");
    private static final Path OUTPUT_PATH = Paths.get("data", "transformed_products.csv");

    public static void main(String[] args) {
        CSVExtractor extractor = new CSVExtractor(INPUT_PATH);
        CSVLoader loader = new CSVLoader(OUTPUT_PATH);
        ProductTransformer transformer = new ProductTransformer();

        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;

        List<String> outputRows = new ArrayList<>();

        try {
            CSVExtractor.ExtractionResult result = extractor.extract();

            // If header only (no rows) -> write just header and print summary
            if (result.headerOnlyOrNoHeader) {
                loader.write(outputRows); // no rows, but write header only
                System.out.println("Input file had no header or only header (no data rows).");
                printSummary(0, 0, 0, OUTPUT_PATH.toString());
                return;
            }

            rowsRead = result.rowsRead;
            rowsSkipped = result.rowsSkipped;

            for (Product p : result.products) {
                String priceRange = transformer.transform(p);
                outputRows.add(p.toCsvRow(priceRange));
                rowsTransformed++;
            }

            // Write output
            loader.write(outputRows);

            printSummary(rowsRead, rowsTransformed, rowsSkipped, OUTPUT_PATH.toString());

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            // If file missing, extractor throws FileNotFoundException (subclass of IOException)
            // Print friendly message (already in exception message)
        }
    }

    private static void printSummary(int rowsRead, int rowsTransformed, int rowsSkipped, String outputPath) {
        System.out.println("ETL Summary:");
        System.out.println("Rows read: " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped: " + rowsSkipped);
        System.out.println("Output file written: " + outputPath);
    }
}
