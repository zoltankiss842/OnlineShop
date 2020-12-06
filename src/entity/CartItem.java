package entity;

import frame.cart.CartTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class CartItem {

    private Product product;

    private JPanel item;
    private JLabel productName;
    private JLabel productPrice;
    private JSpinner amountInCart;
    private JLabel toBePaidForProduct;
    private JButton removeFromCart;

    private CartTable cartTable;

    public CartItem(Product product, JTabbedPane tab, CartTable cartTable) {
        item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        this.product = product;
        this.cartTable = cartTable;

        item.setMinimumSize(new Dimension(0,100));
        item.setMaximumSize(new Dimension(5000,100));
        item.setPreferredSize(new Dimension(tab.getWidth(), 100));
        item.setBorder(new EmptyBorder(5,50,50,5));

        createLabels();

        item.add(productName);
        item.add(productPrice);
        item.add(amountInCart);
        item.add(toBePaidForProduct);
        item.add(removeFromCart);
    }

    private void createLabels() {
        productName = new JLabel(product.getProductName());
        productName.setFont(new Font("Serif", Font.BOLD, 18));
        productName.setHorizontalAlignment(JLabel.LEFT);

        productPrice = new JLabel(String.valueOf(product.getPrice()));
        productPrice.setFont(new Font("Serif", Font.BOLD, 18));
        productPrice.setHorizontalAlignment(JLabel.CENTER);

        amountInCart = new JSpinner();
        amountInCart.addChangeListener(createChangeListener());
        amountInCart.setModel(creatSpinnerModel());

        toBePaidForProduct = new JLabel(String.valueOf(product.getAmountInCart()*product.getPrice()));
        toBePaidForProduct.setFont(new Font("Serif", Font.BOLD, 18));
        toBePaidForProduct.setHorizontalAlignment(JLabel.CENTER);

        removeFromCart = new JButton("Remove");

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
