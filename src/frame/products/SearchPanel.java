package frame.products;

import entity.Product;
import entity.ProductList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchPanel {

    private final String searchLabelText = "Keresés a termékek között:";
    private final String infoLabelDesc = "Termék megjelenítve: ";
    private final String wishToCartDesc = "Kívánságlista -> Kosár";
    private final String wishToCartToolTip = "Kívánságlistán lévő termékek kosárba helyezése";

    private JPanel listAndSearchHolder;
    private JPanel searchPanel;
    private JTextField searchField;
    private JLabel searchFieldLabel;
    private JComboBox<String> searchCategory;
    private JComboBox<String> searchType;
    private JLabel infoLabel;

    private JPanel infoPanel;
    private JButton wishToCart;

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

        searchPanel.setMinimumSize(new Dimension(0,70));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,70));
        searchPanel.setPreferredSize(new Dimension(listAndSearchHolder.getWidth(), 70));

        createSearchField();
        createSearchCategory(list);
        createInfoLabel();
        createInfoPanel();

        searchPanel.add(searchFieldLabel,c);
        searchPanel.add(searchField,c);
        searchPanel.add(searchCategory,c);
        searchPanel.add(infoLabel, c);
        searchPanel.add(infoPanel,c);


        return searchPanel;
    }

    private void createInfoPanel() {
        infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        wishToCart = new JButton(wishToCartDesc);
        wishToCart.setToolTipText(wishToCartToolTip);

        infoPanel.add(wishToCart);

    }

    private void createInfoLabel() {
        infoLabel = new JLabel(infoLabelDesc + list.getProductList().size());
    }

    private void updateInfoLabel(int amount) {
        searchPanel.remove(3);
        infoLabel = new JLabel(infoLabelDesc + amount);

        searchPanel.add(infoLabel);
        searchPanel.revalidate();
    }

    private void createSearchCategory(ProductList list) {
        searchCategory = new JComboBox(list.getCategoryList().toArray());
        searchCategory.insertItemAt("Kívánságlistán", 0);
        searchCategory.insertItemAt("Összes", 0);
        searchCategory.setSelectedIndex(0);
        searchCategory.setEditable(false);

        searchCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Update! : " + searchField.getText());
                int amount = 0;
                for(Product p : list.getProductList()){
                    if(searchCategory.getSelectedItem().equals("Összes")){
                        if(!p.getProductName().contains(searchField.getText()) && p.isShown() == true){
                            p.setShown(false);
                        }
                        else if(p.getProductName().contains(searchField.getText()) && p.isShown() == false){
                            p.setShown(true);
                        }
                    }
                    else if(searchCategory.getSelectedItem().equals("Kívánságlistán")){
                        if(p.isOnWishList()){
                            if(!p.getProductName().contains(searchField.getText()) && p.isShown() == true){
                                p.setShown(false);
                            }
                            else if(p.getProductName().contains(searchField.getText()) && p.isShown() == false){
                                p.setShown(true);
                            }
                        }
                        else{
                            p.setShown(false);
                        }
                    }
                    else if(searchCategory.getSelectedItem().equals(p.getCategory())){
                        if(!p.getProductName().contains(searchField.getText()) && p.isShown() == true){
                            p.setShown(false);
                        }
                        else if(p.getProductName().contains(searchField.getText()) && p.isShown() == false){
                            p.setShown(true);
                        }
                    }
                    else{
                        p.setShown(false);
                    }

                    if(p.isShown()){
                        amount++;
                    }
                }

                productsPanel.updateProductPanel(list, frame);
                updateInfoLabel(amount);

                frame.revalidate();
                frame.repaint();
            }
        });
    }

    private void createSearchField() {
        searchFieldLabel = new JLabel(searchLabelText);
        searchField = new JTextField("", 30);
        searchField.setEditable(true);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("Update! : " + searchField.getText());
                int amount = 0;
                for(Product p : list.getProductList()){
                    if(searchCategory.getSelectedItem().equals("Összes")){
                        if(!p.getProductName().contains(searchField.getText()) && p.isShown() == true){
                            p.setShown(false);
                        }
                        else if(p.getProductName().contains(searchField.getText()) && p.isShown() == false){
                            p.setShown(true);
                        }
                    }
                    else if(searchCategory.getSelectedItem().equals("Kívánságlistán")){
                        if(p.isOnWishList()){
                            if(!p.getProductName().contains(searchField.getText()) && p.isShown() == true){
                                p.setShown(false);
                            }
                            else if(p.getProductName().contains(searchField.getText()) && p.isShown() == false){
                                p.setShown(true);
                            }
                        }
                        else{
                            p.setShown(false);
                        }
                    }
                    else if(searchCategory.getSelectedItem().equals(p.getCategory())){
                        if(!p.getProductName().contains(searchField.getText()) && p.isShown() == true){
                            p.setShown(false);
                        }
                        else if(p.getProductName().contains(searchField.getText()) && p.isShown() == false){
                            p.setShown(true);
                        }
                    }
                    else{
                        p.setShown(false);
                    }

                    if(p.isShown()){
                        amount++;
                    }
                }

                productsPanel.updateProductPanel(list, frame);
                updateInfoLabel(amount);

                frame.revalidate();
                frame.repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("Update! : " + searchField.getText());
                int amount = 0;
                for(Product p : list.getProductList()){
                    if(searchCategory.getSelectedItem().equals("Összes")){
                        if(!p.getProductName().contains(searchField.getText()) && p.isShown() == true){
                            p.setShown(false);
                        }
                        else if(p.getProductName().contains(searchField.getText()) && p.isShown() == false){
                            p.setShown(true);
                        }
                    }
                    else if(searchCategory.getSelectedItem().equals("Kívánságlistán")){
                        if(p.isOnWishList()){
                            if(!p.getProductName().contains(searchField.getText()) && p.isShown() == true){
                                p.setShown(false);
                            }
                            else if(p.getProductName().contains(searchField.getText()) && p.isShown() == false){
                                p.setShown(true);
                            }
                            else{
                                p.setShown(false);
                            }
                        }
                        else{
                            p.setShown(false);
                        }
                    }
                    else if(searchCategory.getSelectedItem().equals(p.getCategory())){
                        if(!p.getProductName().contains(searchField.getText()) && p.isShown() == true){
                            p.setShown(false);
                        }
                        else if(p.getProductName().contains(searchField.getText()) && p.isShown() == false){
                            p.setShown(true);
                        }
                    }
                    else{
                        p.setShown(false);
                    }

                    if(p.isShown()){
                        amount++;
                    }
                }

                productsPanel.updateProductPanel(list, frame);
                updateInfoLabel(amount);

                frame.revalidate();
                frame.repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("Update! : " + searchField.getText());
                int amount = 0;
                for(Product p : list.getProductList()){
                    if(searchCategory.getSelectedItem().equals("Összes")){
                        if(!p.getProductName().contains(searchField.getText()) && p.isShown() == true){
                            p.setShown(false);
                        }
                        else if(p.getProductName().contains(searchField.getText()) && p.isShown() == false){
                            p.setShown(true);
                        }
                    }
                    else if(searchCategory.getSelectedItem().equals("Kívánságlistán")){
                        if(p.isOnWishList()){
                            if(!p.getProductName().contains(searchField.getText()) && p.isShown() == true){
                                p.setShown(false);
                            }
                            else if(p.getProductName().contains(searchField.getText()) && p.isShown() == false){
                                p.setShown(true);
                            }
                        }
                    }
                    else if(searchCategory.getSelectedItem().equals(p.getCategory())){
                        if(!p.getProductName().contains(searchField.getText()) && p.isShown() == true){
                            p.setShown(false);
                        }
                        else if(p.getProductName().contains(searchField.getText()) && p.isShown() == false){
                            p.setShown(true);
                        }
                    }
                    else{
                        p.setShown(false);
                    }

                    if(p.isShown()){
                        amount++;
                    }
                }

                productsPanel.updateProductPanel(list, frame);
                updateInfoLabel(amount);

                frame.revalidate();
                frame.repaint();
            }
        });

    }

    public JPanel getSearchPanel() {
        return searchPanel;
    }
}
