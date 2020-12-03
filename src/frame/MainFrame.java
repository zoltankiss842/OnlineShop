package frame;

import entity.Product;
import entity.ProductList;

import javax.swing.*;
import java.awt.*;

// TODO - Implement search and category options
// TODO - V1.1 is started

public class MainFrame {

    private final String version = "V1.0";
    private final String frameTitle = "Online Shop";
    private final String productsTabTitle = "Termékek";
    private final String productsTabTitleDesc = "Raktárunkban megtalálható termékek";

    private final String favIconPath = "resources\\images\\favicon.png";
    private final String productsIconPath = "resources\\images\\productsIcon.png";

    private JFrame window;
    private JTabbedPane products;
    private JScrollPane listOfProducts;
    private Box holder;

    public MainFrame(){}

    public void init(ProductList list){

        createFrame();

        createProductsTab(list);

        window.setVisible(true);
    }

    /**
     * Creates the initial frame of the program
     * Initializes: close operation, sizes, icon, position
     */
    private void createFrame(){
        window = new JFrame(frameTitle + " " + version);        // creates Frame
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // What should the X do?

        window.setMinimumSize(new Dimension(800, 400));
        window.setMaximumSize(new Dimension(1500, 1000));
        window.setSize(1000,500);

        ImageIcon icon = new ImageIcon(favIconPath);
        window.setIconImage(icon.getImage());                   // Sets icon

        window.setLocationRelativeTo(null);                     // Set frame to the center of screen

    }

    /**
     * Creates the products tab, which displays
     * the available products in the shop
     */
    private void createProductsTab(ProductList list){
        products = new JTabbedPane();
        products.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        products.setTabPlacement(JTabbedPane.TOP);

        ImageIcon icon = new ImageIcon(productsIconPath); // Sets icon

        createProductsHolder(list);

        createProductsScrollPane();

        products.addTab(productsTabTitle, icon, listOfProducts, productsTabTitleDesc);

        window.add(products);
    }

    /**
     * Creates the panel, which makes the products
     * display as a list
     */
    private void createProductsHolder(ProductList list){
        holder = Box.createVerticalBox();

        for(Product p : list.getProductList()){
            ListItem item = new ListItem(p, this.products);
            holder.add(item.getItem());
            holder.add(Box.createVerticalStrut(10));
        }

        holder.add(Box.createVerticalGlue());

    }

    /**
     * Creates a scrollable environment for the list
     */
    private void createProductsScrollPane(){
        listOfProducts = new JScrollPane(holder);
        listOfProducts.getVerticalScrollBar().setUnitIncrement(16);
    }

}
