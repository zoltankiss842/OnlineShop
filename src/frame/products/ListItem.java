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

/**
 * The visual representation for a Product,
 * this is where a product can be added to wishlist,
 * cart or this is where it shows the results of a search.
 */
public class ListItem {

    public static final int LIST_ITEM_WIDTH = 800;
    public static final int LIST_ITEM_HEIGHT = 100;

    // Fields for file paths and labels
    private final String emptyHeart = "resources\\images\\heart.png";
    private final String fullHeart = "resources\\images\\heartFull.png";
    private final String basketIcon = "resources\\images\\basket.png";
    private final String basketAddedIcon = "resources\\images\\basket_added.png";
    private final String basketNoMoreIcon = "resources\\images\\basket_nomore.png";

    private final String addToWishListDesc = "Kívánságlistánoz adás";
    private final String addToCartListDesc = "Kosárba helyezés";

    // Fields for visual representation
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
    private final Color backgroundColor;

    // Fields for icons
    private BufferedImage heartEmpty;
    private BufferedImage heartFull;
    private BufferedImage basket;
    private BufferedImage basketAdded;
    private BufferedImage basketNoMore;

    private Product product;
    private boolean isShown;

    // Constructor with parameters
    public ListItem(Product product){
        item = new JPanel(new GridLayout(1,6));
        this.product = product;
        this.isShown = true;
        this.backgroundColor = new Color(208, 220, 210);

        item.setPreferredSize(new Dimension(LIST_ITEM_WIDTH, LIST_ITEM_HEIGHT));
        item.setBackground(backgroundColor);

        readIcons();
        createLabels();

        item.add(productAndCategoryHolder);
        item.add(productPrice);
        item.add(productQuantity);
        item.add(productIsOnWishlist);
        item.add(productAmount);
    }

    // Getters and setters

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

    public JPanel getItem() {
        return item;
    }

    public void setItem(JPanel item) {
        this.item = item;
    }

    /**
     * Method for reading in the icons,
     * such as heart and basket
     */
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

    /**
     * Creating labels to add it to the item panel
     */
    private void createLabels() {
        productName = new JLabel(product.getProductName());
        productName.setFont(new Font(Font.SERIF, Font.BOLD, 24));
        productName.setToolTipText(product.getProductName());
        productName.setHorizontalAlignment(JLabel.CENTER);

        productCategory = new JLabel(product.getCategory());
        productCategory.setFont(new Font(Font.SERIF, Font.ITALIC, 18));
        productCategory.setHorizontalAlignment(JLabel.CENTER);

        productAndCategoryHolder = new JPanel(new GridLayout(2,1));
        productAndCategoryHolder.add(productName);
        productAndCategoryHolder.add(productCategory);
        productAndCategoryHolder.setBackground(backgroundColor);

        productPrice = new JLabel(product.getPrice() + " Ft");
        productPrice.setFont(new Font(Font.SERIF, Font.BOLD, 24));
        productPrice.setHorizontalAlignment(JLabel.CENTER);

        productQuantity = new JLabel("Raktáron: " + product.getWarehouseQuantity());
        productQuantity.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        productQuantity.setHorizontalAlignment(JLabel.CENTER);

        productIsOnWishlist = setWishlistIcon(product.isOnWishList());
        productIsOnWishlist.setLayout(new GridBagLayout());
        productIsOnWishlist.setBackground(backgroundColor);

        productAddToCart = setCartIcon();
        productAddToCart.setBackground(backgroundColor);

        productAmount = setAmountField();
    }

    /**
     * Creating the amount field, for how many
     * items does the user want to add
     * @return      panel containing the field
     */
    private JPanel setAmountField() {
        productAddToCart = setCartIcon();

        JPanel temp = new JPanel(new GridBagLayout());
        temp.setBackground(backgroundColor);

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

    /**
     * Model for the amount spinner
     * @return      model for spinner
     */
    private SpinnerModel createSpinnerModel() {
        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(0);
        model.setStepSize(1);
        model.setValue(product.getAmountInCart());

        return model;
    }


    /**
     * Setting the proper cart icon for different
     * actions
     * @return      panel containing cart image and amount field
     */
    private JPanel setCartIcon() {
        JPanel cartContainer = new JPanel();
        cartContainer.setBackground(backgroundColor);

        basketPicture = new JLabel(new ImageIcon(basket));
        basketPicture.setToolTipText(addToCartListDesc);
        basketPicture.addMouseListener(createMouseListenerForBasket());

        cartContainer.add(basketPicture);

        return cartContainer;
    }

    /**
     * When the user hover over the cart image
     * the cursor changes to a hand. Also it changes the icon for the cart
     * when the product has been successfully added to the cart. If the
     * product is not in the warehouse it disables the spinner and
     * allso changes the cart.
     * @return      listener for mouse
     */
    private MouseListener createMouseListenerForBasket(){
        MouseListener listener = new MouseListener() {
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
        };

        return listener;
    }

    /**
     * Setting the wish list icon, if its on the wishlist
     * its a full red heart, else it is empty.
     * @param onWishlist        if true, then red heart
     *                          if false, it is an empty heart
     * @return                  panel containing heart
     */
    private JPanel setWishlistIcon(boolean onWishlist){
        JPanel heartContainer = new JPanel();
        heartContainer.setBackground(backgroundColor);

        JLabel heart = null;
        if(onWishlist){
            heart = new JLabel(new ImageIcon(heartFull));
        }
        else{
            heart = new JLabel(new ImageIcon(heartEmpty));
        }

        JLabel finalHeart = heart;
        heart.addMouseListener(createWishListMouseListener(finalHeart));

        finalHeart.setToolTipText(addToWishListDesc);

        heartContainer.add(finalHeart);

        return heartContainer;
    }

    /**
     * When the user hovers over the heart it changes the cursor,
     * if clicks on it, the product will be added to the wishlist,
     * and the heart icon changes.
     * @param finalHeart        JLabel containing the heart
     * @return                  listener for mouse
     */
    private MouseListener createWishListMouseListener(JLabel finalHeart){
        MouseListener listener = new MouseListener() {
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
        };

        return listener;
    }

    /**
     * Updating some labels and field after reload
     */
    public void update(){
        updateWishlistIcon();
        updateAmountInCart();
        updateWareHouseQuantity();
        updateCartIcon();
        updateAmountSpinner();
    }

    private void updateWishlistIcon(){
        productIsOnWishlist.remove(0);

        productIsOnWishlist.add(setWishlistIcon(product.isOnWishList()));

        productIsOnWishlist.revalidate();
    }

    private void updateAmountSpinner() {
        format.setEnabled(product.getWarehouseQuantity() > 0);
    }

    private void updateWareHouseQuantity() {
        productQuantity.setText("Raktáron: " + product.getWarehouseQuantity());
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
