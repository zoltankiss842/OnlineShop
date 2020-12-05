package frame.cart;

import entity.Cart;
import entity.Product;

import javax.swing.*;
import java.util.ArrayList;

public class CartTable {

    private String[] header = {"Item", "Price", "Quantity"};
    private String[][] data;

    private JTable table;
    private JScrollPane scrollPane;

    private Cart cart;

    public CartTable(ArrayList<Product> list){
        cart = new Cart();

        fillCart(list);

        data = new String[list.size()][3];

        for(int i = 0; i < list.size(); ++i){
            data[i][0] = list.get(i).getProductName();
            data[i][1] = String.valueOf(list.get(i).getPrice());
            data[i][2] = String.valueOf(list.get(i).getWarehouseQuantity());
        }

        table = new JTable(data, header);
    }

    private void fillCart(ArrayList<Product> list) {
        for(Product p : list){
            this.cart.addItemToCart(p);
        }
    }

    public JScrollPane createCartTable(){
        scrollPane = new JScrollPane(table);
        return scrollPane;
    }
}
