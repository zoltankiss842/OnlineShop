package entity;

import frame.cart.CartItem;

import java.util.ArrayList;
import java.util.UUID;


/**
 * This entity represents what products the user
 * would likely to buy. Each cart has a unique ID, a list of
 * products, and has the price value of the whole cart.
 */
public class Cart {

    private final double popularityDelta = 0.0005; // Modifier constant for popularity

    // Fields of the Cart class
    private String id;
    private ArrayList<CartItem> cart;
    private int amount;

    // Default constructor
    public Cart(){
        id = UUID.randomUUID().toString();
        cart = new ArrayList<>();
        amount = 0;
    }

    // Getters and Setters

    public ArrayList<CartItem> getCart() {
        return cart;
    }

    public void setCart(ArrayList<CartItem> cart) {
        this.cart = cart;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public boolean addItemToCart(CartItem item){
        return this.cart.add(item);
    }


    /**
     * This method generates a new ID for the cart.
     */
    public void generateNewId() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Going through the items and removing them
     * from the cart, by setting them individually.
     * Also deducting the purchased amount from
     * the warehouse quantity and increasing the popularity.
     */
    public void emptyCartAfterSuccessfulPurchase(){
        for(CartItem item : this.cart){
            Product p = item.getProduct();
            p.setWarehouseQuantity(p.getWarehouseQuantity()-p.getAmountInCart());
            p.setInCart(false);
            p.setAmountInCart(0);
            p.setPopularity(p.getPopularity()+popularityDelta);
        }
    }
}
