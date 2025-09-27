package org.howard.edu.lsp.assignment3;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

public class CSVLoader {
    private final Path outputPath;

    /**
     * Constructs a loader for the given relative output path.
     *
     * @param outputPath path to write output CSV to
     */
    public CSVLoader(Path outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * Writes header and rows (each row is already formatted CSV line).
     *
     * @param rows list of csv lines (without header)
     * @throws IOException on write errors
     */
    public void write(List<String> rows) throws IOException {
        File outFile = outputPath.toFile();
        // Ensure parent dir exists
        File parent = outFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))) {
            bw.write("ProductID,Name,Price,Category,PriceRange");
            bw.newLine();
            for (String r : rows) {
                bw.write(r);
                bw.newLine();
            }
        }
    }
}
