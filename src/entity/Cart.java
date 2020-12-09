package entity;

import frame.cart.CartItem;

import java.util.ArrayList;
import java.util.UUID;

public class Cart {

    private ArrayList<CartItem> cart;
    private int amount;
    private String id;

    public Cart(){
        cart = new ArrayList<>();
        amount = 0;
        id = UUID.randomUUID().toString();
    }

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

    public void printItemsInCart(){
        for(CartItem item : cart){
            System.out.println(item.getProduct().toString());
        }
    }

    public void generateNewId() {
        this.id = UUID.randomUUID().toString();
    }

    public void emptyCartAfterSuccessfulPurchase(){
        for(CartItem item : this.cart){
            item.getProduct().setWarehouseQuantity(item.getProduct().getWarehouseQuantity()-item.getProduct().getAmountInCart());
            item.getProduct().setInCart(false);
            item.getProduct().setAmountInCart(0);
        }
    }
}
