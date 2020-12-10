package entity;

import tools.StringManipulation;

import java.util.Objects;
import java.util.Random;

/**
 * This entity represents a product which a user could
 * browse, buy or add to their wishlist.
 */
public class Product {

    public static final int BUY_LIMIT = 999;

    // Fields for Product class
    private String productName;             // Name of the product, from the file
    private String sanitizedProductName;    // Name of the product, just containing only words and numbers (needed for alphabetical sorting)
    private String category;                // Category of the product
    private int price;                      // Price of the product
    private int warehouseQuantity;          // How many available in the warehouse
    private boolean onWishList;             // If the product is on the users wishlist
    private boolean inCart;                 // If the product has been placed in the cart
    private int amountInCart;               // How many is in the cart
    private boolean isShown;                // If the product is shown on the screen (needed for sorting and searching)
    private double popularity;              // How popular the product is (random values for every run)

    // Constructor for the Product (used during reading in)
    public Product(String productName, String category, int price, int warehouseQuantity) {
        Random rnd = new Random();
        this.productName = productName;
        this.sanitizedProductName = new StringManipulation().sanitizeString(productName);
        this.category = category;
        this.price = price;
        this.warehouseQuantity = warehouseQuantity;
        this.onWishList = false;
        this.inCart = false;
        this.amountInCart = 0;
        this.isShown = true;
        this.popularity = rnd.nextDouble();
    }

    // Getters and Setters

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSanitizedProductName() {
        return sanitizedProductName;
    }

    public void setSanitizedProductName(String sanitizedProductName) {
        this.sanitizedProductName = sanitizedProductName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWarehouseQuantity() {
        return warehouseQuantity;
    }

    public void setWarehouseQuantity(int warehouseQuantity) {
        this.warehouseQuantity = warehouseQuantity;
    }

    public boolean isOnWishList() {
        return onWishList;
    }

    public void setOnWishList(boolean onWishList) {
        this.onWishList = onWishList;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public int getAmountInCart() {
        return amountInCart;
    }

    public void setAmountInCart(int amountInCart) {
        this.amountInCart = amountInCart;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", sanitizedProductName='" + sanitizedProductName + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", warehouseQuantity=" + warehouseQuantity +
                ", onWishList=" + onWishList +
                ", inCart=" + inCart +
                ", amountInCart=" + amountInCart +
                ", isShown=" + isShown +
                ", popularity=" + popularity +
                '}';
    }

    /**
     * For comparing 2 instances of Product entities.
     * IF the specified values are the same, then they are the same.
     * @param toBeCompared      Product to be compared
     * @return                  true, if they are the same
     *                          false, if the are not the same
     */
    @Override
    public boolean equals(Object toBeCompared) {
        if (this == toBeCompared) return true;
        if (toBeCompared == null || getClass() != toBeCompared.getClass()) return false;
        Product product = (Product) toBeCompared;
        return price == product.price &&
                productName.equals(product.productName) &&
                sanitizedProductName.equals(product.sanitizedProductName) &&
                category.equals(product.category);
    }

    /**
     * Generates a hashcode for this Product instance
     * @return      calculated hash value
     */
    @Override
    public int hashCode() {
        return Objects.hash(productName, sanitizedProductName, category, price);
    }
}
