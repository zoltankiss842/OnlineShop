package entity;

import java.util.ArrayList;
import java.util.UUID;

public class Cart {

    private ArrayList<CartItem> cart;
    private double amount;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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
}
