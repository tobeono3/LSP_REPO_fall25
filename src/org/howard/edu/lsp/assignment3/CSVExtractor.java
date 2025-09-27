package org.howard.edu.lsp.assignment3;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for extracting product rows from CSV.
 * Assumes CSV delimiter is comma, no quoted fields, header in first row.
 */
public class CSVExtractor {
    private final Path inputPath;

    /**
     * Constructs an extractor for the given relative input path.
     *
     * @param inputPath relative (project root) path to CSV input
     */
    public CSVExtractor(Path inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * Reads the CSV file and returns a list of Product objects for valid rows.
     * Skips malformed rows and returns counts via the returned Result object.
     *
     * @return ExtractionResult containing products and counts
     * @throws FileNotFoundException if the input file doesn't exist
     * @throws IOException           on other read errors
     */
    public ExtractionResult extract() throws IOException {
        File inputFile = inputPath.toFile();
        if (!inputFile.exists()) {
            throw new FileNotFoundException("Input file '" + inputPath.toString() + "' does not exist.");
        }

        List<Product> products = new ArrayList<>();
        int rowsRead = 0;      // number of data rows (excluding header) read (including malformed)
        int rowsSkipped = 0;   // malformed rows

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String header = br.readLine(); // consume header (may be null)
            if (header == null) {
                // empty file (no header) — treat as header missing => behave as header-only (no rows)
                return new ExtractionResult(products, rowsRead, rowsSkipped, true);
            }

            String line;
            while ((line = br.readLine()) != null) {
                rowsRead++;
                String[] fields = line.split(",", -1);
                if (fields.length != 4) {
                    rowsSkipped++;
                    continue;
                }
                try {
                    int id = Integer.parseInt(fields[0].trim());
                    String name = fields[1].trim();
                    BigDecimal price = new BigDecimal(fields[2].trim());
                    String category = fields[3].trim();
                    products.add(new Product(id, name, price, category));
                } catch (NumberFormatException e) {
                    rowsSkipped++;
                }
            }
        }

        return new ExtractionResult(products, rowsRead, rowsSkipped, false);
    }

    /**
     * Result holder returned from extract().
     */
    public static class ExtractionResult {
        public final List<Product> products;
        public final int rowsRead;
        public final int rowsSkipped;
        public final boolean headerOnlyOrNoHeader;

        public ExtractionResult(List<Product> products, int rowsRead, int rowsSkipped, boolean headerOnlyOrNoHeader) {
            this.products = products;
            this.rowsRead = rowsRead;
            this.rowsSkipped = rowsSkipped;
            this.headerOnlyOrNoHeader = headerOnlyOrNoHeader;
        }
    }
}
