package frame.cart;

import entity.Cart;
import entity.CartItem;
import entity.Product;
import entity.ProductList;
import frame.payment.PaymentFrame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CartTable {

    private final String sumDescription = "Fizetendő összeg: ";
    private final String addAllToWishlistDescription = "Mindent a kívánságlistára";
    private final String paymentDescription = "Fizetés";
    private final String removeFromCart = "Eltávolítás";
    private final String addToWishlistDescription = "Kívánságlistára";

    private double amountToPay = 0;

    private JScrollPane scrollPane;
    private Box holder;
    private JTabbedPane tab;

    private JPanel cartTableHolder;
    private JPanel summaryHolder;

    private JLabel summedPrice;
    private JButton checkout;
    private JButton putEverythingOnWishlist;

    private Cart cart;
    private ProductList productList;

    // TODO - no more than available quantity

    public JPanel createCartTable(ProductList list, JTabbedPane tab){
        this.tab = tab;
        cartTableHolder = new JPanel(new BorderLayout());

        createCart(list);  //left side

        createSummary(); // right side

        return cartTableHolder;
    }

    private void createSummary() {
        summaryHolder = new JPanel();

        summaryHolder.setBorder(new LineBorder(Color.pink, 4));
        summaryHolder.setPreferredSize(new Dimension(300, tab.getHeight()));

        summedPrice = new JLabel(sumDescription + cart.getAmount() + "Ft");

        checkout = new JButton(paymentDescription);
        checkout.addActionListener(setCheckoutActionListener());

        putEverythingOnWishlist = new JButton(addAllToWishlistDescription);
        putEverythingOnWishlist.addActionListener(setPutEverytingOnWishlistActionListener());

        summaryHolder.add(summedPrice);
        summaryHolder.add(checkout);
        summaryHolder.add(putEverythingOnWishlist);

        cartTableHolder.add(summaryHolder, BorderLayout.LINE_END);
    }

    private ActionListener setCheckoutActionListener() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cart.getCart().size() != 0){
                    PaymentFrame paymentFrame = new PaymentFrame();
                    paymentFrame.createPaymentFrame(cart);
                }
            }
        };

        return action;
    }

    private ActionListener setPutEverytingOnWishlistActionListener() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(CartItem item : cart.getCart()){
                    if(!item.getProduct().isOnWishList()){
                        item.getProduct().setOnWishList(true);
                    }
                }

                updateCartTable();
            }
        };

        return action;
    }

    private void createCart(ProductList list) {
        this.productList = list;
        cart = new Cart();

        scrollPane = new JScrollPane();

        holder = Box.createVerticalBox();

        for(Product p : list.getProductList()){
            CartItem item = new CartItem(p, scrollPane, this);
            if(p.isInCart()){
                cart.setAmount(cart.getAmount()+(p.getPrice() * p.getAmountInCart()));
                cart.addItemToCart(item);
                holder.add(item.getItem());
                holder.add(Box.createVerticalStrut(10));
            }

        }

        holder.add(Box.createVerticalGlue());

        scrollPane = new JScrollPane(holder);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBar(null);

        scrollPane.setBorder(new LineBorder(Color.ORANGE, 4));

        cartTableHolder.add(scrollPane, BorderLayout.CENTER);
    }

    public JScrollPane updateCartTable(ProductList list, JFrame frame){
        holder.removeAll();
        cart.getCart().clear();

        for(Product p : list.getProductList()){
            CartItem item = new CartItem(p, scrollPane, this);
            if(p.isInCart()){
                cart.setAmount(cart.getAmount()+(p.getPrice() * p.getAmountInCart()));
                cart.addItemToCart(item);
                holder.add(item.getItem());
                holder.add(Box.createVerticalStrut(10));
            }

        }

        summaryHolder.remove(0);
        summedPrice.removeAll();
        summedPrice = new JLabel(sumDescription + cart.getAmount() + "Ft");

        summaryHolder.add(summedPrice, 0);

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
            CartItem item = new CartItem(p, scrollPane, this);
            if(p.isInCart()){
                cart.setAmount(cart.getAmount()+(p.getPrice() * p.getAmountInCart()));
                cart.addItemToCart(item);
                holder.add(item.getItem());
                holder.add(Box.createVerticalStrut(10));
            }

        }

        holder.add(Box.createVerticalGlue());

        summaryHolder.remove(0);
        summedPrice.removeAll();
        summedPrice = new JLabel(sumDescription + cart.getAmount() + "Ft");

        summaryHolder.add(summedPrice, 0);

        holder.revalidate();
        scrollPane.repaint();
        scrollPane.revalidate();

        return scrollPane;
    }


}
