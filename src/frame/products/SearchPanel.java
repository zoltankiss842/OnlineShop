package frame.products;

import entity.Product;
import entity.ProductList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class SearchPanel {

    private final String searchLabelText = "Keresés a termékek között:";

    private JPanel listAndSearchHolder;
    private JPanel searchPanel;
    private JTextField searchField;
    private JLabel searchFieldLabel;
    private JComboBox<String> searchCategory;

    private ProductList list;
    private JFrame frame;
    private ProductsPanel productsPanel;

    public JPanel createSearchPanel(ProductList list, JFrame frame, ProductsPanel panel){
        this.list = list;
        this.frame = frame;
        this.productsPanel = panel;

        listAndSearchHolder = new JPanel(new BorderLayout());
        listAndSearchHolder.setBorder(new LineBorder(Color.RED,4));
        searchPanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);

        searchPanel.setBorder(new LineBorder(Color.MAGENTA, 4));

        searchPanel.setMinimumSize(new Dimension(0,50));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,50));
        searchPanel.setPreferredSize(new Dimension(listAndSearchHolder.getWidth(), 50));

        createSearchField();
        createSearchCategory(list);

        searchPanel.add(searchFieldLabel,c);
        searchPanel.add(searchField,c);
        searchPanel.add(searchCategory,c);

        return searchPanel;
    }

    private void createSearchCategory(ProductList list) {
        searchCategory = new JComboBox(list.getCategoryList().toArray());
    }

    private void createSearchField() {
        searchFieldLabel = new JLabel(searchLabelText);
        searchField = new JTextField("", 30);
        searchField.setEditable(true);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("Update! : " + searchField.getText());
                for(Product p : list.getProductList()){
                    if(!p.getProductName().contains(searchField.getText())){
                        p.setShown(false);
                    }
                }

                productsPanel.updateProductPanel(list, frame);

                frame.revalidate();
                frame.repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("Update! : " + searchField.getText());
                for(Product p : list.getProductList()){
                    if(!p.getProductName().contains(searchField.getText())){
                        p.setShown(false);
                    }
                }

                productsPanel.updateProductPanel(list, frame);

                frame.revalidate();
                frame.repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("Update! : " + searchField.getText());
                for(Product p : list.getProductList()){
                    if(!p.getProductName().contains(searchField.getText())){
                        p.setShown(false);
                    }
                }

                productsPanel.updateProductPanel(list, frame);

                frame.revalidate();
                frame.repaint();
            }
        });

        frame.revalidate();
        frame.repaint();

    }

    public JPanel getSearchPanel() {
        return searchPanel;
    }
}
