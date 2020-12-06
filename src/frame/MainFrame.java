package frame;

import entity.Cart;
import entity.Product;
import entity.ProductList;
import frame.cart.CartTable;
import frame.products.ProductsPanel;
import frame.products.SearchPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {

    private final String version = "V1.2";
    private final String frameTitle = "Online Shop";
    private final String productsTabTitle = "Termékek";
    private final String productsTabTitleDesc = "Raktárunkban megtalálható termékek";
    private final String cartTabTitle = "Kosár";
    private final String cartTabTitleDesc = "Az Ön kosara";

    private final String favIconPath = "resources\\images\\favicon.png";
    private final String productsIconPath = "resources\\images\\productsIcon.png";
    private final String cartIconPath = "resources\\images\\cartIcon.png";

    private JFrame window;
    private JTabbedPane tabPane;
    private JPanel listAndSearchHolder;

    private SearchPanel searchPanel;
    private ProductsPanel productsPanel;
    private CartTable cartTable;

    private ProductList list;
    private Cart cart;

    public MainFrame(){}

    public void init(ProductList list, Cart cart){
        this.list = list;
        this.cart = cart;

        createFrame();
        createTabPane();
        createProductsTab(list);
        createCartTab(list);

        window.add(tabPane);
        window.setVisible(true);
    }

    private void createTabPane() {
        tabPane = new JTabbedPane();
        tabPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        tabPane.setTabPlacement(JTabbedPane.TOP);

        tabPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(e.getSource() instanceof JTabbedPane){
                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    if(pane.getSelectedIndex() == 1){
                        cartTable.updateCartTable(list, window);
                    }
                }
            }
        });
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
        ImageIcon icon = new ImageIcon(productsIconPath); // Sets icon

        initListAndSearchHolder();
        createSearchPanel(list, window);
        createProductsPanel(list);


        tabPane.addTab(productsTabTitle, icon, listAndSearchHolder, productsTabTitleDesc);
    }

    private void initListAndSearchHolder() {
        listAndSearchHolder = new JPanel(new BorderLayout());
        listAndSearchHolder.setBorder(new LineBorder(Color.RED,4));
    }

    private void createSearchPanel(ProductList list, JFrame frame) {
        searchPanel = new SearchPanel();
        productsPanel = new ProductsPanel();
        listAndSearchHolder.add(searchPanel.createSearchPanel(list, frame, productsPanel), BorderLayout.PAGE_START);
    }

    /**
     * Creates the panel, which makes the products
     * display as a list
     */
    private void createProductsPanel(ProductList list){
        listAndSearchHolder.add(productsPanel.createProductsPanel(list, tabPane, listAndSearchHolder), BorderLayout.CENTER);

    }

    private void createCartTab(ProductList list) {
        ImageIcon icon = new ImageIcon(cartIconPath); // Sets icon

        cartTable = new CartTable();

        tabPane.addTab(cartTabTitle, icon, cartTable.createCartTable(list, tabPane), cartTabTitleDesc);
    }
}
