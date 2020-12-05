package frame.products;

import entity.Product;
import entity.ProductList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ProductsPanel {

    private Box holder;
    private JScrollPane listOfProducts;
    private JTabbedPane tab;
    private JPanel listAndSearchHolder;

    public JScrollPane createProductsPanel(ProductList list, JTabbedPane tab, JPanel listAndSearchHolder){
        this.tab = tab;
        this.listAndSearchHolder = listAndSearchHolder;

        holder = Box.createVerticalBox();

        for(Product p : list.getProductList()){
            ListItem item = new ListItem(p, tab);
            if(p.isShown()){
                holder.add(item.getItem());
                holder.add(Box.createVerticalStrut(10));
            }

        }

        holder.add(Box.createVerticalGlue());

        listOfProducts = new JScrollPane(holder);
        listOfProducts.getVerticalScrollBar().setUnitIncrement(16);

        listOfProducts.setMinimumSize(new Dimension(0,listAndSearchHolder.getHeight()));
        listOfProducts.setMaximumSize(new Dimension(Integer.MAX_VALUE,listAndSearchHolder.getHeight()));
        listOfProducts.setPreferredSize(new Dimension(listAndSearchHolder.getWidth(), listAndSearchHolder.getHeight()));
        listOfProducts.setBorder(new LineBorder(Color.ORANGE, 4));

        return listOfProducts;
    }

    public JScrollPane getListOfProducts() {
        return listOfProducts;
    }

    public JScrollPane updateProductPanel(ProductList list, JFrame frame){
        holder.removeAll();

        for(Product p : list.getProductList()){
            ListItem item = new ListItem(p, tab);
            if(p.isShown()){
                holder.add(item.getItem());
                holder.add(Box.createVerticalStrut(10));
            }

        }

        holder.add(Box.createVerticalGlue());


        return listOfProducts;
    }
}
