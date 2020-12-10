package frame;

import entity.Cart;
import entity.ProductList;
import frame.cart.CartTable;
import frame.products.ProductsPanel;
import frame.products.SearchPanel;
import tools.FileIO;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainFrame {

    public static final int FRAME_WIDTH = 1200;
    public static final int FRAME_HEIGHT = 700;


    // Field for label and button desscriptions
    private final String frameTitle = "Online Shop";
    private final String productsTabTitle = "Termékek";
    private final String productsTabTitleDesc = "Raktárunkban megtalálható termékek";
    private final String cartTabTitle = "Kosár";
    private final String cartTabTitleDesc = "Az Ön kosara";
    private final String settingsTabTitle = "Beállítások";
    private final String settingsTabTitleDesc = "Felhasználói műveletek és beállítások";

    // Fields for file paths
    private final String favIconPath = "resources\\images\\favicon.png";
    private final String productsIconPath = "resources\\images\\productsIcon.png";
    private final String cartIconPath = "resources\\images\\cartIcon.png";
    private final String settingsIconPath = "resources\\images\\settingsIcon.png";

    // Fields for visual representation
    private JFrame window;
    private JTabbedPane tabPane;
    private JPanel listAndSearchHolder;

    // Fields for frame
    private SearchPanel searchPanel;
    private ProductsPanel productsPanel;
    private CartTable cartTable;

    // Fields for entities
    private ProductList list;
    private Cart cart;

    public MainFrame(){}

    /**
     * Initializes window t show
     * @param list      list of products
     * @param cart      items in cart
     */
    public void init(ProductList list, Cart cart){
        this.list = list;
        this.cart = cart;

        createFrame();
        createTabPane();
        createProductsTab();
        createCartTab();

        window.add(tabPane);
        window.setVisible(true);
    }

    /**
     * Creates a tab pane
     */
    private void createTabPane() {
        tabPane = new JTabbedPane();
        tabPane.setTabPlacement(JTabbedPane.TOP);

        tabPane.addChangeListener(createTabChangeListener());
    }

    /**
     * If the tab is changes, refresh the current page
     * @return      listener for tab
     */
    private ChangeListener createTabChangeListener(){
        ChangeListener listener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(e.getSource() instanceof JTabbedPane){
                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    if(pane.getSelectedIndex() == 1){
                        cartTable.updateCartTable();
                    }
                    else if(pane.getSelectedIndex() == 0){
                        productsPanel.updateProductPanel();
                    }
                }
            }
        };

        return listener;
    }

    /**
     * Creates the initial frame of the program
     * Initializes: close operation, sizes, icon, position
     */
    private void createFrame(){
        window = new JFrame(frameTitle);        // creates Frame
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // What should the X do?

        window.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        window.setResizable(false);

        ImageIcon icon = new ImageIcon(favIconPath);
        window.setIconImage(icon.getImage());                   // Sets icon

        window.setLocationRelativeTo(null);                     // Set frame to the center of screen

        window.addWindowListener(createWindowListener());
    }

    /**
     * If the window is closed, it saves a txt file
     * about the cart and the wishlist
     * @return      listener for window
     */
    private WindowListener createWindowListener() {
        WindowListener listener = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                FileIO io = new FileIO();
                io.writeProductToTxt(list);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        };

        return listener;
    }

    /**
     * Creates the products tab, which displays
     * the available products in the shop
     */
    private void createProductsTab(){
        ImageIcon icon = new ImageIcon(productsIconPath); // Sets icon

        initListAndSearchHolder();
        createSearchPanel();
        createProductsPanel();


        tabPane.addTab(productsTabTitle, icon, listAndSearchHolder, productsTabTitleDesc);
    }

    /**
     * Initializes a panel, which will hold
     * the SearchPanel and ProductPanel
     */
    private void initListAndSearchHolder() {
        listAndSearchHolder = new JPanel(new BorderLayout());
    }

    /**
     * Creates SearchPanel
     */
    private void createSearchPanel() {
        searchPanel = new SearchPanel();
        productsPanel = new ProductsPanel();
        listAndSearchHolder.add(searchPanel.createSearchPanel(list, window, productsPanel), BorderLayout.PAGE_START);
    }

    /**
     * Creates the panel, which makes the products
     * display as a list
     */
    private void createProductsPanel(){
        listAndSearchHolder.add(productsPanel.createProductsPanel(list), BorderLayout.CENTER);

    }

    /**
     * Creates CartTable
     */
    private void createCartTab() {
        ImageIcon icon = new ImageIcon(cartIconPath); // Sets icon

        cartTable = new CartTable();

        tabPane.addTab(cartTabTitle, icon, cartTable.createCartTable(list, window), cartTabTitleDesc);
    }
}
