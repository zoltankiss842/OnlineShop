package frame;

import entity.Product;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

// TODO - Beautify display of items
// TODO - Heart icon if on wishlist

public class ListItem {

    private JPanel item;

    public ListItem(Product product, JTabbedPane tab){
        item = new JPanel();

        item.setMinimumSize(new Dimension(0,100));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        item.setPreferredSize(new Dimension(tab.getWidth(), 100));
        item.setBorder(new LineBorder(Color.GREEN,4));

        item.add(new JLabel(product.getProductName()));
        item.add(new JLabel(product.getCategory()));
        item.add(new JLabel(String.valueOf(product.getPrice())));
        item.add(new JLabel(String.valueOf(product.getWarehouseQuantity())));
    }

    public JPanel getItem() {
        return item;
    }

    public void setItem(JPanel item) {
        this.item = item;
    }
}
