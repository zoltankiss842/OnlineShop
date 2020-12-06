package entity;

import java.util.ArrayList;

public class Cart {

    private ArrayList<CartItem> cart;

    public Cart(){
        cart = new ArrayList<>();
    }

    public ArrayList<CartItem> getCart() {
        return cart;
    }

    public void setCart(ArrayList<CartItem> cart) {
        this.cart = cart;
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
