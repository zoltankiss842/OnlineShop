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
    private JScrollPane listOfProducts;
    private Box holder;

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
        int number_of_products = 50;
        holder = Box.createVerticalBox();

        for(int i = 0; i < number_of_products; ++i){
            JPanel temp = new JPanel();
            temp.setMinimumSize(new Dimension(0,100));
            temp.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
            temp.setPreferredSize(new Dimension(products.getWidth(), 100));
            temp.setBorder(new LineBorder(Color.GREEN,4));

            if(i%2 == 0){
                temp.add(new JLabel("TEST"));
            }
            else{
                temp.add(new JLabel("TEST"));
            }

            holder.add(temp);
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
