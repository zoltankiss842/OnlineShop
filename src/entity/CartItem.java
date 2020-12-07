package entity;

import frame.cart.CartTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class CartItem {

    private Product product;

    private JPanel item;
    private JLabel productName;
    private JLabel productPrice;
    private JPanel spinnerHolder;
    private JSpinner amountInCart;
    private JLabel toBePaidForProduct;
    private JPanel buttonHolder;
    private JButton removeFromCart;
    private JButton addToWishlist;

    private CartTable cartTable;

    public CartItem(Product product, JScrollPane pane, CartTable cartTable) {
        item = new JPanel(new GridLayout(1,5));
        this.product = product;
        this.cartTable = cartTable;

        item.setMinimumSize(new Dimension(0,60));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE,60));
        item.setPreferredSize(new Dimension(pane.getWidth()-5,60));
        item.setBorder(new LineBorder(Color.BLACK, 4));

        createLabels();

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,5,3,5);
        c.gridwidth = 5;
        c.weightx = 0.1;

        c.weightx = 0.1;
        item.add(productName);
        c.weightx = 1.0;
        item.add(productPrice);
        item.add(spinnerHolder);
        item.add(toBePaidForProduct);
        item.add(buttonHolder);
    }

    private void createLabels() {
        productName = new JLabel(product.getProductName());
        productName.setFont(new Font("Serif", Font.BOLD, 18));
        productName.setHorizontalAlignment(JLabel.LEFT);
        productName.setToolTipText(product.getProductName());

        productPrice = new JLabel(String.valueOf(product.getPrice()));
        productPrice.setFont(new Font("Serif", Font.BOLD, 18));
        productPrice.setHorizontalAlignment(JLabel.CENTER);

        spinnerHolder = new JPanel(new GridBagLayout());

        amountInCart = new JSpinner();
        amountInCart.addChangeListener(createChangeListener());
        amountInCart.setModel(creatSpinnerModel());
        JFormattedTextField field = ((JSpinner.DefaultEditor)amountInCart.getEditor()).getTextField();
        field.setColumns(4);

        spinnerHolder.add(amountInCart);

        toBePaidForProduct = new JLabel(String.valueOf(product.getAmountInCart()*product.getPrice()));
        toBePaidForProduct.setFont(new Font("Serif", Font.BOLD, 18));
        toBePaidForProduct.setHorizontalAlignment(JLabel.CENTER);

        buttonHolder = new JPanel(new BorderLayout());

        removeFromCart = new JButton("Eltavolitas");
        addToWishlist = new JButton("Kedvencekhez adas");

        buttonHolder.add(removeFromCart, BorderLayout.PAGE_START);
        buttonHolder.add(addToWishlist,BorderLayout.PAGE_END);

    }

    private ChangeListener createChangeListener() {
        ChangeListener listener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(e.getSource() instanceof JSpinner){
                    JSpinner spinner = (JSpinner) e.getSource();
                    int amount = (Integer) spinner.getValue();

                    if(amount == 0){
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

    private SpinnerModel creatSpinnerModel() {
        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(0);
        model.setStepSize(1);
        model.setValue(product.getAmountInCart());

        return model;
    }

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
}
