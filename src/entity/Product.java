package entity;

import tools.StringManipulation;

import java.util.Objects;
import java.util.Random;

public class Product {

    private String productName;
    private String sanitizedProductName;
    private String category;
    private int price;
    private int warehouseQuantity;
    private boolean onWishList;
    private boolean inCart;
    private int amountInCart;
    private boolean isShown;
    private double popularity;

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

    public Product(String productName,
                   String sanitizedProductName,
                   String category,
                   int price,
                   int amountInCart) {
        Random rnd = new Random();
        this.productName = productName;
        this.sanitizedProductName = sanitizedProductName;
        this.category = category;
        this.price = price;
        this.warehouseQuantity = 0;
        this.onWishList = false;
        this.amountInCart = amountInCart;

        if(this.amountInCart>0){
            this.inCart = true;
        }
        else {
            this.inCart = false;
        }

        this.isShown = true;
        this.popularity = rnd.nextDouble();
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price &&
                productName.equals(product.productName) &&
                sanitizedProductName.equals(product.sanitizedProductName) &&
                category.equals(product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, sanitizedProductName, category, price);
    }
}
