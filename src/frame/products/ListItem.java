package frame.products;

import entity.Product;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ListItem {

    private final String emptyHeart = "resources\\images\\heart.png";
    private final String fullHeart = "resources\\images\\heartFull.png";
    private final String basketIcon = "resources\\images\\basket.png";
    private final String formatter = "###";

    private JPanel item;

    private JLabel productName;
    private JLabel productCategory;
    private JLabel productPrice;
    private JLabel productQuantity;
    private JLabel productIsOnWishlist;
    private JLabel productAddToCart;
    private JPanel productAmount;


    public ListItem(Product product, JTabbedPane tab){
        item = new JPanel(new GridLayout(1,7));

        item.setMinimumSize(new Dimension(0,100));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        item.setPreferredSize(new Dimension(tab.getWidth(), 100));
        item.setBorder(new EmptyBorder(5,50,50,5));

        createLabels(product);

        item.add(productName);
        item.add(productCategory);
        item.add(productPrice);
        item.add(productQuantity);
        item.add(productIsOnWishlist);
        item.add(productAmount);
    }

    private void createLabels(Product product) {
        productName = new JLabel(product.getProductName());
        productName.setFont(new Font("Serif", Font.BOLD, 24));
        productName.setHorizontalAlignment(JLabel.LEFT);

        productCategory = new JLabel(product.getCategory());
        productCategory.setFont(new Font("Serif", Font.BOLD, 20));
        productCategory.setHorizontalAlignment(JLabel.CENTER);

        productPrice = new JLabel(String.valueOf(product.getPrice()));
        productPrice.setFont(new Font("Serif", Font.BOLD, 24));
        productPrice.setHorizontalAlignment(JLabel.CENTER);

        productQuantity = new JLabel(String.valueOf(product.getWarehouseQuantity()));
        productQuantity.setFont(new Font("Serif", Font.BOLD, 20));
        productQuantity.setHorizontalAlignment(JLabel.CENTER);

        productIsOnWishlist = setWishlistIcon(product.isOnWishList());
        productIsOnWishlist.setHorizontalAlignment(JLabel.CENTER);

        productAddToCart = setCartIcon();
        productIsOnWishlist.setHorizontalAlignment(JLabel.CENTER);

        productAmount = setAmountFormatting();
    }

    private JPanel setAmountFormatting() {
        productAddToCart = setCartIcon();
        JFormattedTextField format = null;
        try {
            format = new JFormattedTextField(new MaskFormatter(formatter));
            format.setColumns(4);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        JPanel temp = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);

        temp.add(productAddToCart,c);
        temp.add(format,c);
        return temp;
    }

    private JLabel setCartIcon() {
        JLabel basket = null;
        try{
            BufferedImage pic = ImageIO.read(new File(basketIcon));
            basket = new JLabel(new ImageIcon(pic));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return basket;
    }

    public JPanel getItem() {
        return item;
    }

    public void setItem(JPanel item) {
        this.item = item;
    }

    private JLabel setWishlistIcon(boolean onWishlist){
        JLabel heart = null;
        try{
            if(onWishlist){
                BufferedImage pic = ImageIO.read(new File(fullHeart));
                heart = new JLabel(new ImageIcon(pic));
            }
            else{
                BufferedImage pic = ImageIO.read(new File(emptyHeart));
                heart = new JLabel(new ImageIcon(pic));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return heart;
    }
}
