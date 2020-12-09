package frame.products;

import entity.Product;
import tools.StringManipulation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ListItem {

    private final String emptyHeart = "resources\\images\\heart.png";
    private final String fullHeart = "resources\\images\\heartFull.png";
    private final String basketIcon = "resources\\images\\basket.png";
    private final String basketAddedIcon = "resources\\images\\basket_added.png";
    private final String basketNoMoreIcon = "resources\\images\\basket_nomore.png";

    private final String addToWishListDesc = "Kívánságlistánoz adás";
    private final String addToCartListDesc = "Kosárba helyezés";

    private JPanel item;

    private JLabel productName;
    private JLabel productCategory;
    private JPanel productAndCategoryHolder;
    private JLabel productPrice;
    private JLabel productQuantity;
    private JPanel productIsOnWishlist;
    private JPanel productAddToCart;
    private JPanel productAmount;
    private JSpinner format;
    private JLabel basketPicture;

    private BufferedImage heartEmpty;
    private BufferedImage heartFull;
    private BufferedImage basket;
    private BufferedImage basketAdded;
    private BufferedImage basketNoMore;

    private Product product;
    private boolean isShown;

    public ListItem(Product product, JPanel panel){
        item = new JPanel(new GridLayout(1,6));
        this.product = product;
        this.isShown = true;

        item.setMinimumSize(new Dimension(800,100));
        item.setMaximumSize(new Dimension(2000,100));
        item.setPreferredSize(new Dimension(800, 100));

        readIcons();
        createLabels(this.product);

        item.add(productAndCategoryHolder);
        item.add(productPrice);
        item.add(productQuantity);
        item.add(productIsOnWishlist);
        item.add(productAmount);
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private void readIcons() {

        try{
            heartFull = ImageIO.read(new File(fullHeart));
            heartEmpty = ImageIO.read(new File(emptyHeart));
            basket = ImageIO.read(new File(basketIcon));
            basketAdded = ImageIO.read(new File(basketAddedIcon));
            basketNoMore = ImageIO.read(new File(basketNoMoreIcon));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createLabels(Product product) {
        productName = new JLabel(product.getProductName());
        productName.setFont(new Font("Serif", Font.BOLD, 24));
        productName.setToolTipText(product.getProductName());
        productName.setHorizontalAlignment(JLabel.CENTER);

        productCategory = new JLabel(product.getCategory());
        productCategory.setFont(new Font("Serif", Font.ITALIC, 18));
        productCategory.setHorizontalAlignment(JLabel.CENTER);

        productAndCategoryHolder = new JPanel(new GridLayout(2,1));
        productAndCategoryHolder.add(productName);
        productAndCategoryHolder.add(productCategory);

        productPrice = new JLabel(String.valueOf(product.getPrice()) + " Ft");
        productPrice.setFont(new Font("Serif", Font.BOLD, 24));
        productPrice.setHorizontalAlignment(JLabel.CENTER);

        productQuantity = new JLabel("Raktáron: " + String.valueOf(product.getWarehouseQuantity()));
        productQuantity.setFont(new Font("Serif", Font.BOLD, 20));
        productQuantity.setHorizontalAlignment(JLabel.CENTER);

        productIsOnWishlist = setWishlistIcon(product.isOnWishList());
        productIsOnWishlist.setLayout(new GridBagLayout());

        productAddToCart = setCartIcon();

        productAmount = setAmountFormatting();
    }

    private JPanel setAmountFormatting() {
        productAddToCart = setCartIcon();

        JPanel temp = new JPanel(new GridBagLayout());

        format = new JSpinner();
        format.setModel(createSpinnerModel());


        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;

        temp.add(productAddToCart,c);
        temp.add(format,c);
        return temp;
    }

    private SpinnerModel createSpinnerModel() {
        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(0);
        model.setStepSize(1);
        model.setValue(product.getAmountInCart());

        return model;
    }

    private JPanel setCartIcon() {
        JPanel cartContainer = new JPanel();
        basketPicture = new JLabel(new ImageIcon(basket));
        basketPicture.setToolTipText(addToCartListDesc);
        basketPicture.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                String value = ((JSpinner.DefaultEditor) format.getEditor()).getTextField().getText();
                Integer amount = null;
                try{
                    StringManipulation manipulator = new StringManipulation();
                    value = manipulator.sanitizeForNumbers(value);

                    if(value == null){
                        throw new NullPointerException();
                    }

                    amount = Integer.parseInt(value);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }

                if(amount == null ||  amount > 999){
                    JOptionPane.showMessageDialog(item,
                            "Hibás érték! Az értéknek 0 és 999 között kell lennie.",
                            "Hibás kosár érték",
                            JOptionPane.ERROR_MESSAGE);
                }
                else{
                    product.setAmountInCart(amount);
                    if(amount == 0){
                        product.setInCart(false);
                    }
                    else{
                        basketPicture.setIcon(new ImageIcon(basketAdded));
                        product.setInCart(true);
                    }
                }

                System.out.println("In cart amount: " + product.getAmountInCart());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(product.getWarehouseQuantity() > 0){
                    basketPicture.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                basketPicture.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                if(product.getWarehouseQuantity() <= 0){
                    basketPicture.setIcon(new ImageIcon(basketNoMore));
                }
                else{
                    basketPicture.setIcon(new ImageIcon(basket));
                }
            }
        });

        cartContainer.add(basketPicture);

        return cartContainer;
    }

    public JPanel getItem() {
        return item;
    }

    public void setItem(JPanel item) {
        this.item = item;
    }

    private JPanel setWishlistIcon(boolean onWishlist){
        JPanel heartContainer = new JPanel();

        JLabel heart = null;
        if(onWishlist){
            heart = new JLabel(new ImageIcon(heartFull));
        }
        else{
            heart = new JLabel(new ImageIcon(heartEmpty));
        }

        JLabel finalHeart = heart;
        heart.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(product.isOnWishList()){
                    finalHeart.setIcon(new ImageIcon(heartEmpty));
                    product.setOnWishList(false);
                    System.out.println("Full to empty");
                }
                else{
                    finalHeart.setIcon(new ImageIcon(heartFull));
                    product.setOnWishList(true);
                    System.out.println("Empty to full");
                }
                item.revalidate();
                item.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                finalHeart.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                finalHeart.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        finalHeart.setToolTipText(addToWishListDesc);

        heartContainer.add(finalHeart);

        return heartContainer;
    }

    private void updateWishlistIcon(){

        productIsOnWishlist.remove(0);

        productIsOnWishlist.add(setWishlistIcon(product.isOnWishList()));

        productIsOnWishlist.revalidate();
    }

    public void update(){
        updateWishlistIcon();
        updateAmountInCart();
        updateWareHouseQuantity();
        updateCartIcon();
        updateAmountSpinner();
    }

    private void updateAmountSpinner() {
        if(product.getWarehouseQuantity() <= 0){
            format.setEnabled(false);
        }
        else{
            format.setEnabled(true);
        }
    }

    private void updateWareHouseQuantity() {
        productQuantity.setText("Raktáron: " + String.valueOf(product.getWarehouseQuantity()));
    }

    private void updateAmountInCart() {
        format.setValue(product.getAmountInCart());
    }

    private void updateCartIcon(){
        if(product.getWarehouseQuantity() <= 0){
            basketPicture.setIcon(new ImageIcon(basketNoMore));
        }
        else{
            basketPicture.setIcon(new ImageIcon(basket));
        }
    }

}
