package frame.cart;

import entity.Cart;
import entity.CartItem;
import entity.Product;
import entity.ProductList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CartTable {


    private JScrollPane scrollPane;
    private Box holder;
    private JTabbedPane tab;

    private Cart cart;
    private ProductList productList;

    // TODO - implement a better cartTable

    public JScrollPane createCartTable(ProductList list, JTabbedPane tab){
        this.tab = tab;
        this.productList = list;
        cart = new Cart();
        scrollPane = new JScrollPane();

        holder = Box.createVerticalBox();

        for(Product p : list.getProductList()){
            CartItem item = new CartItem(p, tab, this);
            if(p.isInCart()){
                cart.addItemToCart(item);
                holder.add(item.getItem());
                holder.add(Box.createVerticalStrut(10));
            }

        }

        holder.add(Box.createVerticalGlue());

        scrollPane = new JScrollPane(holder);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBar(null);

        scrollPane.setMinimumSize(new Dimension(0,tab.getHeight()));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE,tab.getHeight()));
        scrollPane.setPreferredSize(new Dimension(tab.getWidth(), tab.getHeight()));
        scrollPane.setBorder(new LineBorder(Color.ORANGE, 4));

        return scrollPane;
    }

    public JScrollPane updateCartTable(ProductList list, JFrame frame){
        holder.removeAll();
        cart.getCart().clear();

        for(Product p : list.getProductList()){
            CartItem item = new CartItem(p, tab, this);
            if(p.isInCart()){
                cart.addItemToCart(item);
                holder.add(item.getItem());
                holder.add(Box.createVerticalStrut(10));
            }

        }

        holder.add(Box.createVerticalGlue());

        holder.revalidate();
        frame.revalidate();
        scrollPane.repaint();
        scrollPane.revalidate();

        return scrollPane;
    }

    public JScrollPane updateCartTable(){
        holder.removeAll();
        cart.getCart().clear();

        for(Product p : productList.getProductList()){
            CartItem item = new CartItem(p, tab, this);
            if(p.isInCart()){
                cart.addItemToCart(item);
                holder.add(item.getItem());
                holder.add(Box.createVerticalStrut(10));
            }

        }

        holder.add(Box.createVerticalGlue());

        holder.revalidate();
        scrollPane.repaint();
        scrollPane.revalidate();

        return scrollPane;
    }


}
