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
        this.list = list;
        this.backgroundColor = new Color(171, 196, 187);
        cartTableHolder = new JPanel(new BorderLayout());

        createCart();

        createSummary();

        return cartTableHolder;
    }


    /**
     * Creates cart by adding items to it
     */
    private void createCart() {
        cart = new Cart();
        cartItemHolder = Box.createVerticalBox();

        addItems();

        setUpScrollPane();

        cartTableHolder.add(cartItems, BorderLayout.CENTER);
    }

    /**
     * Adding items to the cart.
     * If no item is present, an empty
     * message will be displayed.
     */
    private void addItems() {
        int numberOfItems = 0;

        for(Product p : list.getProductList()){
            if(p.isInCart()){
                CartItem item = new CartItem(p, this);
                cart.setAmount(cart.getAmount()+(p.getPrice() * p.getAmountInCart()));
                cart.addItemToCart(item);
                cartItemHolder.add(item.getItem());
                cartItemHolder.add(Box.createVerticalStrut(PADDING_BETWEEN_CART_ITEMS));
                numberOfItems++;
            }

        }

        if(numberOfItems == 0){
            cartItemHolder.removeAll();
            EmptyCartItem empty = new EmptyCartItem();
            cartItemHolder.add(empty.getItem());
        }

        cartItemHolder.add(Box.createVerticalGlue());
    }

    /**
     * Sets up scrollpane for the cart items
     */
    private void setUpScrollPane() {
        cartItems = new JScrollPane(cartItemHolder);

        cartItems.getVerticalScrollBar().setUnitIncrement(16);
        cartItems.setHorizontalScrollBar(null);
    }

    /**
     * Creates the summary, which is on the right side of
     * the panel. It is for displaying the amount which
     * to be paid and other useful buttons.
     */
    private void createSummary() {
        summaryHolder = new JPanel(new GridBagLayout());
        summaryHolder.setBackground(backgroundColor);
        GridBagConstraints c = new GridBagConstraints();

        summaryHolder.setPreferredSize(new Dimension(SUMMARY_WIDTH, MainFrame.FRAME_HEIGHT));

        summedPrice = new JLabel(sumDescription + cart.getAmount() + "Ft");
        summedPrice.setFont(new Font(Font.SERIF, Font.BOLD, 18));

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

    /**
     * When the user clicks on the button, it removes
     * every item from the cart.
     * @return      action for the button
     */
    private ActionListener createRemoveAllFromCartActionListener() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Product item : list.getProductList()){
                    item.setInCart(false);
                    item.setAmountInCart(0);
                }

                updateCartTable();
            }
        };

        return action;
    }

    /**
     * When the user clicks on the button, it creates a PaymentFrame
     * where the payment can be made.
     *
     * If, the cart is empty, nothing will happen.
     *
     * @see PaymentFrame
     * @param cartTable     for updating the cart after successful payment
     * @return              action for button
     */
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

    /**
     * When the user clicks the button, it sets every product
     * to be on the wishlist.
     *
     * @return      action for the button
     */
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

    /**
     * Updates the cart and displays a refreshed
     * batch of items.
     */
    public void updateCartTable(){
        cartItemHolder.removeAll();
        cart.getCart().clear();

        cart.setAmount(0);

        addItems();

        summedPrice.setText(sumDescription + cart.getAmount() + "Ft");
        summedPrice.setFont(new Font("Serif", Font.BOLD, 18));

        cartItems.repaint();
    }


}
