package frame.products;

import entity.Product;
import entity.ProductList;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ProductsPanel {

    private Box holder;
    private JScrollPane listOfProducts;
    private JTabbedPane tab;
    private JPanel listAndSearchHolder;
    private HashMap<Integer, ListItem> listItemMap;

    public JScrollPane createProductsPanel(ProductList list, JTabbedPane tab, JPanel listAndSearchHolder){
        this.tab = tab;
        this.listAndSearchHolder = listAndSearchHolder;
        this.listItemMap = new HashMap<>();

        holder = Box.createVerticalBox();

        int numberOfItems = 0;

        for(Product p : list.getProductList()){
            if(p.isShown()){
                ListItem item = new ListItem(p, listAndSearchHolder);
                item.setShown(true);
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

        listOfProducts = new JScrollPane(holder);
        listOfProducts.getVerticalScrollBar().setUnitIncrement(16);
        listOfProducts.setHorizontalScrollBar(null);

        listOfProducts.setMinimumSize(new Dimension(0,listAndSearchHolder.getHeight()));
        listOfProducts.setMaximumSize(new Dimension(Integer.MAX_VALUE,listAndSearchHolder.getHeight()));
        listOfProducts.setPreferredSize(new Dimension(listAndSearchHolder.getWidth(), listAndSearchHolder.getHeight()));

        return listOfProducts;
    }

    public JScrollPane updateProductPanel(ProductList list, JFrame frame){
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
        holder.revalidate();

        frame.repaint();
        frame.validate();

        listOfProducts.revalidate();

        return listOfProducts;
    }
}
