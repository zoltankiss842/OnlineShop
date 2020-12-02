import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;

// TODO Generate products
// TODO Clean up - put them in separate classes

public class Program {

    private final String version = "V1.0";
    private final String frameTitle = "Online Shop";
    private final String productsTabTitle = "Termékek";
    private final String productsTabTitleDesc = "Raktárunkban megtalálható termékek";

    private final String favIconPath = "resources\\images\\favicon.png";
    private final String productsIconPath = "resources\\images\\productsIcon.png";

    private JFrame window;
    private JTabbedPane products;
    private JPanel productsHolder;
    private JScrollPane listOfProducts;

    public void run(){

        createFrame();

        createProductsTab();

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
    private void createProductsTab(){
        products = new JTabbedPane();
        products.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        products.setTabPlacement(JTabbedPane.TOP);

        ImageIcon icon = new ImageIcon(productsIconPath); // Sets icon

        createProductsHolder();

        createProductsScrollPane();

        products.addTab(productsTabTitle, icon, listOfProducts, productsTabTitleDesc);

        window.add(products);
    }

    /**
     * Creates the panel, which makes the products
     * display as a list
     */
    private void createProductsHolder(){
        productsHolder = new JPanel();
        productsHolder.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        for(int i = 0; i < 60; ++i){
            JPanel temp = new JPanel(new GridBagLayout());

            if(i%2 == 0){
                temp.setBackground(Color.BLACK);
                temp.add(new JLabel("TEST"));
            }
            else{
                temp.setBackground(Color.RED);
                temp.add(new JLabel("TEST"));
            }
            c.ipady = 50;
            c.anchor = GridBagConstraints.NORTH;
            c.weightx = 1;
            c.gridx = 1;
            c.insets = new Insets(5,5,5,5);
            c.fill = GridBagConstraints.HORIZONTAL;
            productsHolder.add(temp,c);
        }
    }

    /**
     * Creates a scrollable environment for the list
     */
    private void createProductsScrollPane(){
        listOfProducts = new JScrollPane(productsHolder);
        listOfProducts.getVerticalScrollBar().setUnitIncrement(16);
    }
}
