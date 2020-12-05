package entity;

public class Product {

    private String productName;
    private String category;
    private double price;
    private int warehouseQuantity;
    private boolean onWishList;
    private boolean inCart;
    private int amountInCart;
    private boolean isShown;

    public Product(String productName, String category, double price, int warehouseQuantity) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.warehouseQuantity = warehouseQuantity;
        this.onWishList = false;
        this.inCart = false;
        this.amountInCart = 0;
        this.isShown = true;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", warehouseQuantity=" + warehouseQuantity +
                '}';
    }
}
