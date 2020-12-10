package frame.cart;

import entity.Cart;
import entity.Product;
import entity.ProductList;
import frame.MainFrame;
import frame.payment.PaymentFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CartTable {

    public final static int MIN_ITEM_WIDTH = 300;
    public final static int PREFERRED_ITEM_WIDTH = 300;
    public final static int MAX_ITEM_WIDTH = 2000;
    public final static int ITEM_HEIGHT = 60;
    public final static int SUMMARY_WIDTH = 300;
    public final static int PADDING_BETWEEN_CART_ITEMS = 10;

    // Fields for the description of labels and buttons
    private final String sumDescription = "Fizetendő összeg: ";
    private final String addAllToWishlistDescription = "Mindent a kívánságlistára";
    private final String paymentDescription = "Fizetés";
    private final String removeAllFromCartDesc = "Kosár törlése";

    // Fields for visual representation
    private JPanel cartTableHolder;                 // Holds the list of items in cart and the summary
    private JScrollPane cartItems;                  // Holds the list of items
    private Box cartItemHolder;
    private JPanel summaryHolder;                   // Holds the summary
    private JLabel summedPrice;
    private JButton checkout;
    private JButton putEverythingOnWishlist;
    private JButton removeAllFromCart;
    private Color backgroundColor;

    private Cart cart;
    private ProductList list;
    private JFrame frame;

    /**
     * Creates a panel which be added to the mainframe's tabbed pane.
     * This panel holds the items in the cart and the summary of the cart.
     *
     * @param list          ProductList instance containing every products
     * @param frame         Instance of the mainframe, to disable when payment is started
     * @return              JPanel with the added components
     */
    public JPanel createCartTable(ProductList list, JFrame frame){
        this.frame = frame;
        cartTableHolder = new JPanel(new BorderLayout());

        createCart(list);  //left side

        createSummary(); // right side

        return cartTableHolder;
    }

    private void createSummary() {
        summaryHolder = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        summaryHolder.setPreferredSize(new Dimension(300, MainFrame.FRAME_HEIGHT));

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
        this.list = list;
        cart = new Cart();

        cartItemHolder = Box.createVerticalBox();

        for(Product p : list.getProductList()){
            CartItem item = new CartItem(p, this);
            if(p.isInCart()){
                cart.setAmount(cart.getAmount()+(p.getPrice() * p.getAmountInCart()));
                cart.addItemToCart(item);
                cartItemHolder.add(item.getItem());
                cartItemHolder.add(Box.createVerticalStrut(10));
            }

        }

        cartItemHolder.add(Box.createVerticalGlue());

        cartItems = new JScrollPane(cartItemHolder);

        cartItems.setMinimumSize(new Dimension(800,200));
        cartItems.setMaximumSize(new Dimension(2000,200));
        cartItems.setPreferredSize(new Dimension(800, 200));

        cartItems.getVerticalScrollBar().setUnitIncrement(16);
        cartItems.setHorizontalScrollBar(null);

        cartTableHolder.add(cartItems, BorderLayout.CENTER);
    }

    public JScrollPane updateCartTable(ProductList list, JFrame frame){
        cartItemHolder.removeAll();
        cart.getCart().clear();

        cart.setAmount(0);

        int numberOfItems = 0;

        for(Product p : list.getProductList()){
            CartItem item = new CartItem(p, this);
            if(p.isInCart()){
                cart.setAmount(cart.getAmount()+(p.getPrice() * p.getAmountInCart()));
                cart.addItemToCart(item);
                cartItemHolder.add(item.getItem());
                cartItemHolder.add(Box.createVerticalStrut(10));
                numberOfItems++;
            }

        }

        if(numberOfItems == 0){
            EmptyCartItem empty = new EmptyCartItem();
            cartItemHolder.add(empty.getItem());
        }

        summaryHolder.remove(0);
        summedPrice.removeAll();
        summedPrice = new JLabel(sumDescription + cart.getAmount() + "Ft");
        summedPrice.setFont(new Font("Serif", Font.BOLD, 18));

        summaryHolder.add(summedPrice, 0);

        cartItemHolder.add(Box.createVerticalGlue());

        cartItemHolder.revalidate();
        frame.revalidate();
        cartItems.repaint();
        cartItems.revalidate();

        return cartItems;
    }

    public JScrollPane updateCartTable(){
        cartItemHolder.removeAll();
        cart.getCart().clear();

        cart.setAmount(0);

        int numberOfItems = 0;

        for(Product p : list.getProductList()){
            CartItem item = new CartItem(p, this);
            if(p.isInCart()){
                cart.setAmount(cart.getAmount()+(p.getPrice() * p.getAmountInCart()));
                cart.addItemToCart(item);
                cartItemHolder.add(item.getItem());
                cartItemHolder.add(Box.createVerticalStrut(10));
                numberOfItems++;
            }

        }

        if(numberOfItems == 0){
            EmptyCartItem empty = new EmptyCartItem();
            cartItemHolder.add(empty.getItem());
        }

        cartItemHolder.add(Box.createVerticalGlue());

        summedPrice.setText(sumDescription + cart.getAmount() + "Ft");
        summedPrice.setFont(new Font("Serif", Font.BOLD, 18));

        cartItemHolder.revalidate();
        cartItems.repaint();
        cartItems.revalidate();

        cartTableHolder.revalidate();

        return cartItems;
    }


}
