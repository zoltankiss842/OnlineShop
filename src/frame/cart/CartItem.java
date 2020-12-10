package frame.cart;

import entity.Product;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is a visual representation
 * of a product in the cart.
 *
 * @see Product
 * @see entity.Cart
 */
public class CartItem {

    // Fields for the description of labels and buttons
    private final String removeFromCartDesc = "Eltávolítás";
    private final String addToWishlistDesc = "Kívánságlistára";

    // Field for the CartTable, if the list need update
    private final CartTable cartTable;

    // Field for the actual product it represents
    private Product product;

    // Fields for visual representation
    private Color backgroundColor;
    private JPanel item;                    // Holder of every component
    private JLabel productName;
    private JLabel productPrice;
    private JPanel spinnerHolder;           // Holder for spinner
    private JSpinner amountInCart;
    private JLabel toBePaidForProduct;
    private JPanel buttonHolder;            // Holder for the buttons
    private JButton removeFromCart;
    private JButton addToWishlist;

    // Constructor with parameters
    public CartItem(Product product, CartTable cartTable) {
        this.product = product;
        this.cartTable = cartTable;
        this.backgroundColor = new Color(208, 220, 210);

        initializeItem();
        createLabels();
        addComponentsToItem();
    }

    /**
     * Initializes an item with some default values
     */
    private void initializeItem() {
        item = new JPanel(new GridLayout(1,5));
        item.setMinimumSize(new Dimension(CartTable.MIN_ITEM_WIDTH,CartTable.ITEM_HEIGHT));
        item.setMaximumSize(new Dimension(CartTable.MAX_ITEM_WIDTH,CartTable.ITEM_HEIGHT));
        item.setPreferredSize(new Dimension(CartTable.PREFERRED_ITEM_WIDTH,CartTable.ITEM_HEIGHT));
        item.setBackground(backgroundColor);
    }

    /**
     * Creating the visual components for the product
     */
    private void createLabels() {
        productName = new JLabel(product.getProductName());
        productName.setFont(new Font(Font.SERIF, Font.BOLD, 18));
        productName.setHorizontalAlignment(JLabel.LEFT);
        productName.setToolTipText(product.getProductName());

        productPrice = new JLabel(product.getPrice() + " Ft");
        productPrice.setFont(new Font(Font.SERIF, Font.BOLD, 18));
        productPrice.setHorizontalAlignment(JLabel.CENTER);

        spinnerHolder = new JPanel(new GridBagLayout());
        spinnerHolder.setBackground(backgroundColor);

        amountInCart = new JSpinner();
        amountInCart.addChangeListener(createChangeListener());
        amountInCart.setModel(createSpinnerModel());
        JFormattedTextField field = ((JSpinner.DefaultEditor)amountInCart.getEditor()).getTextField();
        field.setColumns(4);

        spinnerHolder.add(amountInCart);

        toBePaidForProduct = new JLabel(product.getAmountInCart() * product.getPrice() + " Ft");
        toBePaidForProduct.setFont(new Font(Font.SERIF, Font.BOLD, 18));
        toBePaidForProduct.setHorizontalAlignment(JLabel.CENTER);

        buttonHolder = new JPanel(new BorderLayout());
        buttonHolder.setBackground(backgroundColor);

        removeFromCart = new JButton(removeFromCartDesc);
        removeFromCart.addActionListener(createRemoveFromCartActionListener());
        addToWishlist = new JButton(addToWishlistDesc);
        addToWishlist.addActionListener(createAddToWishListActionListener());

        buttonHolder.add(removeFromCart, BorderLayout.PAGE_START);
        buttonHolder.add(addToWishlist,BorderLayout.PAGE_END);

    }

    /**
     * Adding the created components to the holder
     */
    private void addComponentsToItem() {
        item.add(productName);
        item.add(productPrice);
        item.add(spinnerHolder);
        item.add(toBePaidForProduct);
        item.add(buttonHolder);
    }

    // Getters and Setters

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public JPanel getItem() {
        return item;
    }

    public void setItem(JPanel item) {
        this.item = item;
    }

    // Action and Change listeners

    /**
     * If the user clicks the button, it adds the product to the wishlist
     * @return      listener for this action
     */
    private ActionListener createAddToWishListActionListener() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                product.setOnWishList(true);
            }
        };

        return action;
    }

    /**
     * If the user clicks the button, it removes the product from the cart
     * and updates the table.
     * @return      listener for this action
     */
    private ActionListener createRemoveFromCartActionListener() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                product.setAmountInCart(0);
                product.setInCart(false);

                cartTable.updateCartTable();
            }
        };

        return action;
    }

    /**
     * If the user changes the value of the spinner
     * either by clicking on the arrows or by typing,
     * it will sets the amount in cart for that product.
     *
     * However, if the input is greater than the product buy limit
     * or the amount that is in the warehouse, the spinner simply
     * resets to the value 1.
     *
     * If the spinner has been set to 0, the product will be
     * removed from the cart.
     *
     * @return      listener for this action
     */
    private ChangeListener createChangeListener() {
        ChangeListener listener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(e.getSource() instanceof JSpinner){
                    JSpinner spinner = (JSpinner) e.getSource();
                    int amount = (Integer) spinner.getValue();

                    if(amount > Product.BUY_LIMIT || amount > product.getWarehouseQuantity()){
                        spinner.setValue(1);
                    }
                    else if(amount == 0){
                        product.setInCart(false);
                        product.setAmountInCart(amount);
                    }
                    else{
                        product.setAmountInCart(amount);
                    }
                }

                cartTable.updateCartTable();
            }
        };

        return listener;
    }

    /**
     * Initializes the spinner with settings.
     * @return      model for the spinner
     */
    private SpinnerModel createSpinnerModel() {
        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(0);                                    // Minimum value that can be set
        model.setStepSize(1);                                   // Step size for
        model.setValue(product.getAmountInCart());              // Initial value

        return model;
    }
}
