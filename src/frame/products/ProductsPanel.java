package frame.products;

import entity.Product;
import entity.ProductList;
import frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Represents a list of products.
 */
public class ProductsPanel {

    // Fields for visual representation
    private JScrollPane listOfProducts;
    private Box holder;

    private ProductList list;

    // List containing products
    private HashMap<Integer, ListItem> listItemMap;

    /**
     * Creates a component, which contains products
     * @param list                      a list of products
     * @return                          scrollpane containing the product
     */
    public JScrollPane createProductsPanel(ProductList list){
        this.list = list;
        this.listItemMap = new HashMap<>();

        holder = Box.createVerticalBox();

        createItems();

        createListOfProducts();

        return listOfProducts;
    }

    /**
     * Creates the scrollpanel
     */
    private void createListOfProducts() {
        listOfProducts = new JScrollPane(holder);
        listOfProducts.getVerticalScrollBar().setUnitIncrement(16);
        listOfProducts.setHorizontalScrollBar(null);

        listOfProducts.setPreferredSize(new Dimension(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT));
    }

    /**
     * Adding the products on the first time
     */
    private void createItems() {
        int numberOfItems = 0;

        for(Product p : list.getProductList()){
            if(p.isShown()){
                ListItem item = new ListItem(p);
                listItemMap.put(p.hashCode(), item);
                holder.add(item.getItem());
                holder.add(Box.createVerticalStrut(5));
                numberOfItems++;
            }

        }

        if(numberOfItems == 0){
            EmptyListItem empty = new EmptyListItem();
            holder.add(empty.getItem());
        }

        holder.add(Box.createVerticalGlue());
    }

    /**
     * Updating the panel, to refresh the products to show
     */
    public void updateProductPanel(){
        holder.removeAll();

        int numberOfItems = 0;

        for(Product p : list.getProductList()){
            if(p.isShown()){
                listItemMap.get(p.hashCode()).setShown(true);
                listItemMap.get(p.hashCode()).update();
                holder.add(listItemMap.get(p.hashCode()).getItem());
                holder.add(Box.createVerticalStrut(5));
                numberOfItems++;
            }
            else{
                listItemMap.get(p.hashCode()).setShown(false);
                listItemMap.get(p.hashCode()).update();
            }

        }

        if(numberOfItems == 0){
            EmptyListItem empty = new EmptyListItem();
            holder.add(empty.getItem());
        }

        holder.add(Box.createVerticalGlue());

        listOfProducts.repaint();
    }
}
