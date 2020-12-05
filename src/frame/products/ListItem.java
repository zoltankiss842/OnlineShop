package frame.products;

import entity.Product;
import tools.StringManipulation;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private JFormattedTextField format;

    private BufferedImage heartEmpty;
    private BufferedImage heartFull;
    private BufferedImage basket;

    private JLabel heartFullLabel;
    private JLabel heartEmptyLabel;

    private Product product;


    public ListItem(Product product, JTabbedPane tab){
        item = new JPanel(new GridLayout(1,7));
        this.product = product;

        item.setMinimumSize(new Dimension(0,100));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        item.setPreferredSize(new Dimension(tab.getWidth(), 100));
        item.setBorder(new EmptyBorder(5,50,50,5));

        readIcons();
        createLabels(this.product);

        item.add(productName);
        item.add(productCategory);
        item.add(productPrice);
        item.add(productQuantity);
        item.add(productIsOnWishlist);
        item.add(productAmount);
    }


    private void readIcons() {

        try{
            heartFull = ImageIO.read(new File(fullHeart));
            heartEmpty = ImageIO.read(new File(emptyHeart));
            basket = ImageIO.read(new File(basketIcon));
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
        JLabel basketPicture = new JLabel(new ImageIcon(basket));
        basketPicture.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                String value = format.getText();
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

                if(amount == null){
                    JOptionPane.showMessageDialog(item,"Invalid amount");
                }
                else{
                    if(product.isInCart()){
                        product.setAmountInCart(product.getAmountInCart()+amount);
                    }
                    else{
                        product.setInCart(true);
                        product.setAmountInCart(amount);
                    }
                }

                System.out.println("In cart amount: " + product.getAmountInCart());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        return basketPicture;
    }

    public JPanel getItem() {
        return item;
    }

    public void setItem(JPanel item) {
        this.item = item;
    }

    private JLabel setWishlistIcon(boolean onWishlist){
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

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        return finalHeart;
    }
}
