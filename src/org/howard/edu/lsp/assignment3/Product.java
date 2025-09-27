package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * Model class representing a product record.
 */
public class Product {
    private final int productId;
    private String name;
    private BigDecimal price;
    private String category;

    /**
     * Constructs a Product.
     *
     * @param productId integer product id
     * @param name      product name
     * @param price     product price
     * @param category  product category
     */
    public Product(int productId, String name, BigDecimal price, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    /* Getters and setters */

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Set the price (expected to be already rounded if required).
     *
     * @param price BigDecimal price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Return CSV row for this product in order:
     * ProductID,Name,Price,Category,PriceRange
     *
     * @param priceRange computed price range string
     * @return csv line (fields comma separated)
     */
    public String toCsvRow(String priceRange) {
        return String.format("%d,%s,%s,%s,%s",
                productId,
                name,
                price.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString(),
                category,
                priceRange);
    }
}
