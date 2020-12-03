package entity;

public class Product {

    private String productName;
    private String category;
    private double price;
    private int warehouseQuantity;
    private boolean onWishList;

    public Product(String productName, String category, double price, int warehouseQuantity) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.warehouseQuantity = warehouseQuantity;
        this.onWishList = false;
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
