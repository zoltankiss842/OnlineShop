package frame.cart;

import entity.Product;
import frame.cart.CartTable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CartItem {

    private final String removeFromCartDesc = "Eltávolítás";
    private final String addToWishlistDesc = "Kívánságlistára";

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

        item.setMinimumSize(new Dimension(300,60));
        item.setMaximumSize(new Dimension(2000,60));
        item.setPreferredSize(new Dimension(300, 60));
        item.setBorder(new LineBorder(Color.BLACK, 4));

        createLabels();

        item.add(productName);
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

        productPrice = new JLabel(String.valueOf(product.getPrice()) + " Ft");
        productPrice.setFont(new Font("Serif", Font.BOLD, 18));
        productPrice.setHorizontalAlignment(JLabel.CENTER);

        spinnerHolder = new JPanel(new GridBagLayout());

        amountInCart = new JSpinner();
        amountInCart.addChangeListener(createChangeListener());
        amountInCart.setModel(creatSpinnerModel());
        JFormattedTextField field = ((JSpinner.DefaultEditor)amountInCart.getEditor()).getTextField();
        field.setColumns(4);

        spinnerHolder.add(amountInCart);

        toBePaidForProduct = new JLabel(String.valueOf(product.getAmountInCart()*product.getPrice()) + " Ft");
        toBePaidForProduct.setFont(new Font("Serif", Font.BOLD, 18));
        toBePaidForProduct.setHorizontalAlignment(JLabel.CENTER);

        buttonHolder = new JPanel(new BorderLayout());

        removeFromCart = new JButton(removeFromCartDesc);
        removeFromCart.addActionListener(createRemoveFromCartActionListener());
        addToWishlist = new JButton(addToWishlistDesc);
        addToWishlist.addActionListener(createAddToWishListActionListener());

        buttonHolder.add(removeFromCart, BorderLayout.PAGE_START);
        buttonHolder.add(addToWishlist,BorderLayout.PAGE_END);

    }

    private ActionListener createAddToWishListActionListener() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                product.setOnWishList(true);
            }
        };

        return action;
    }

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

    private ChangeListener createChangeListener() {
        ChangeListener listener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(e.getSource() instanceof JSpinner){
                    JSpinner spinner = (JSpinner) e.getSource();
                    int amount = (Integer) spinner.getValue();

                    if(amount > 999 || amount > product.getWarehouseQuantity()){
                        spinner.setValue(999);
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
