package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Encapsulates transformation rules for Product objects.

 * Transform order must be:
 * 1) uppercase name
 * 2) discount if Electronics (10% off)
 * 3) recategorization if discounted price > 500 and original category was Electronics
 * 4) compute PriceRange from final price
 */
public class ProductTransformer {

    /**
     * Apply the required transformation to product in place.
     *
     * @param product the product to transform
     * @return the PriceRange string computed from final price
     */
    public String transform(Product product) {
        // 1) uppercase name
        product.setName(product.getName().toUpperCase());

        // Keep original category for recategorization decision
        String originalCategory = product.getCategory();

        // 2) discount if Electronics (case-insensitive)
        BigDecimal price = product.getPrice();
        if ("Electronics".equalsIgnoreCase(originalCategory)) {
            price = price.multiply(BigDecimal.valueOf(0.9));
            price = price.setScale(2, RoundingMode.HALF_UP);
            product.setPrice(price);
        } else {
            // ensure rounding for non-discounted also
            price = price.setScale(2, RoundingMode.HALF_UP);
            product.setPrice(price);
        }

        // 3) recategorization
        if ("Electronics".equalsIgnoreCase(originalCategory) && price.compareTo(BigDecimal.valueOf(500.00)) > 0) {
            product.setCategory("Premium Electronics");
        }

        // 4) compute PriceRange from final price
        return computePriceRange(product.getPrice());
    }

    /**
     * Computes the price range string based on final price.
     *
     * Ranges:
     * 0.00–10.00 => Low
     * 10.01–100.00 => Medium
     * 100.01–500.00 => High
     * 500.01+ => Premium
     *
     * @param price BigDecimal final price (rounded to 2 decimals)
     * @return PriceRange string
     */
    public String computePriceRange(BigDecimal price) {
        BigDecimal p = price.setScale(2, RoundingMode.HALF_UP);
        if (p.compareTo(BigDecimal.valueOf(0.00)) >= 0 && p.compareTo(BigDecimal.valueOf(10.00)) <= 0) {
            return "Low";
        } else if (p.compareTo(BigDecimal.valueOf(10.01)) >= 0 && p.compareTo(BigDecimal.valueOf(100.00)) <= 0) {
            return "Medium";
        } else if (p.compareTo(BigDecimal.valueOf(100.01)) >= 0 && p.compareTo(BigDecimal.valueOf(500.00)) <= 0) {
            return "High";
        } else {
            return "Premium";
        }
    }
}
