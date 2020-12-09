package frame.cart;

import entity.Cart;
import entity.Product;
import entity.ProductList;
import frame.payment.PaymentFrame;
import frame.products.EmptyListItem;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CartTable {

    private final String sumDescription = "Fizetendő összeg: ";
    private final String addAllToWishlistDescription = "Mindent a kívánságlistára";
    private final String paymentDescription = "Fizetés";
    private final String removeAllFromCartDesc = "Kosár törlése";

    private double amountToPay = 0;

    private JScrollPane scrollPane;
    private Box holder;
    private JTabbedPane tab;

    private JPanel cartTableHolder;
    private JPanel summaryHolder;

    private JLabel summedPrice;
    private JButton checkout;
    private JButton putEverythingOnWishlist;
    private JButton removeAllFromCart;

    private Cart cart;
    private ProductList productList;
    private JFrame frame;

    // TODO - no more than available quantity

    public JPanel createCartTable(ProductList list, JTabbedPane tab, JFrame frame){
        this.tab = tab;
        this.frame = frame;
        cartTableHolder = new JPanel(new BorderLayout());

        createCart(list);  //left side

        createSummary(); // right side

        return cartTableHolder;
    }

    private void createSummary() {
        summaryHolder = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        summaryHolder.setBorder(new LineBorder(Color.pink, 4));
        summaryHolder.setPreferredSize(new Dimension(300, tab.getHeight()));

        summedPrice = new JLabel(sumDescription + cart.getAmount() + "Ft");
        summedPrice.setFont(new Font("Serif", Font.BOLD, 18));

        checkout = new JButton(paymentDescription);
        checkout.addActionListener(setCheckoutActionListener(this));

        putEverythingOnWishlist = new JButton(addAllToWishlistDescription);
        putEverythingOnWishlist.addActionListener(setPutEverytingOnWishlistActionListener());

        removeAllFromCart = new JButton(removeAllFromCartDesc);
        removeAllFromCart.addActionListener(createRemoveAllFromCartActionListener());

        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        summaryHolder.add(summedPrice,c);

        c.gridx = 0;
        c.gridy = 1;
        summaryHolder.add(checkout,c);

        c.gridx = 0;
        c.gridy = 2;
        summaryHolder.add(putEverythingOnWishlist,c);

        c.gridx = 0;
        c.gridy = 3;
        summaryHolder.add(removeAllFromCart,c);

        cartTableHolder.add(summaryHolder, BorderLayout.LINE_END);
    }

    private ActionListener createRemoveAllFromCartActionListener() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(CartItem item : cart.getCart()){
                    item.getProduct().setInCart(false);
                    item.getProduct().setAmountInCart(0);
                }

                updateCartTable();
            }
        };

        return action;
    }

    private ActionListener setCheckoutActionListener(CartTable cartTable) {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cart.getCart().size() != 0){
                    PaymentFrame paymentFrame = new PaymentFrame();
                    paymentFrame.createPaymentFrame(cart, frame, cartTable);
                }

                updateCartTable();
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

        scrollPane.setMinimumSize(new Dimension(800,200));
        scrollPane.setMaximumSize(new Dimension(2000,200));
        scrollPane.setPreferredSize(new Dimension(800, 200));

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBar(null);

        scrollPane.setBorder(new LineBorder(Color.ORANGE, 4));

        cartTableHolder.add(scrollPane, BorderLayout.CENTER);
    }

    public JScrollPane updateCartTable(ProductList list, JFrame frame){
        holder.removeAll();
        cart.getCart().clear();

        cart.setAmount(0);

        int numberOfItems = 0;

        for(Product p : list.getProductList()){
            CartItem item = new CartItem(p, scrollPane, this);
            if(p.isInCart()){
                cart.setAmount(cart.getAmount()+(p.getPrice() * p.getAmountInCart()));
                cart.addItemToCart(item);
                holder.add(item.getItem());
                holder.add(Box.createVerticalStrut(10));
                numberOfItems++;
            }

        }

        if(numberOfItems == 0){
            EmptyCartItem empty = new EmptyCartItem();
            holder.add(empty.getItem());
        }

        summaryHolder.remove(0);
        summedPrice.removeAll();
        summedPrice = new JLabel(sumDescription + cart.getAmount() + "Ft");
        summedPrice.setFont(new Font("Serif", Font.BOLD, 18));

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

        cart.setAmount(0);

        int numberOfItems = 0;

        for(Product p : productList.getProductList()){
            CartItem item = new CartItem(p, scrollPane, this);
            if(p.isInCart()){
                cart.setAmount(cart.getAmount()+(p.getPrice() * p.getAmountInCart()));
                cart.addItemToCart(item);
                holder.add(item.getItem());
                holder.add(Box.createVerticalStrut(10));
                numberOfItems++;
            }

        }

        if(numberOfItems == 0){
            EmptyCartItem empty = new EmptyCartItem();
            holder.add(empty.getItem());
        }

        holder.add(Box.createVerticalGlue());

        summedPrice.setText(sumDescription + cart.getAmount() + "Ft");
        summedPrice.setFont(new Font("Serif", Font.BOLD, 18));

        holder.revalidate();
        scrollPane.repaint();
        scrollPane.revalidate();

        cartTableHolder.revalidate();

        return scrollPane;
    }


}
