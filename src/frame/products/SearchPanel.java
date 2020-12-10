package frame.products;

import entity.Product;
import entity.ProductList;
import tools.sort.AlphabeticalAsc;
import tools.sort.Popularity;
import tools.sort.PriceAsc;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class SearchPanel {

    // Fields for labels and buttons descriptions
    private final String searchLabelText = "Keresés a termékek között:";
    private final String searchCategoryText = "Kategória";
    private final String sortText = "Rendezés";
    private final String infoLabelDesc = "Termék megjelenítve: ";
    private final String wishToCartDesc = "Kívánságlista -> Kosár";
    private final String wishToCartToolTip = "Kívánságlistán lévő termékek kosárba helyezése";
    private final String deleteWishListDesc = "Kívánságlista törlése";
    private final String[] searchTypeList = {"ABC sorrend A-Z", "ABC sorrend Z-A", "Ár növekvő", "Ár csökkenő", "Népszerűség"};

    // Fields for visual representations
    private JPanel listAndSearchHolder;
    private JPanel searchPanel;
    private JTextField searchField;
    private JLabel searchFieldLabel;
    private JLabel searchCategoryLabel;
    private JComboBox<String> searchCategory;
    private JLabel sortLabel;
    private JComboBox<String> sort;
    private JLabel infoLabel;

    private JPanel wishToCartPanel;
    private JButton wishToCart;
    private JButton deleteWishList;

    private ProductList list;
    private JFrame frame;
    private ProductsPanel productsPanel;

    private Color backgroundColor;

    // Fields for comparator classes
    private AlphabeticalAsc alphaAsc;
    private PriceAsc priceAsc;
    private Popularity popularity;

    /**
     * Creates the search panel, where user can search and sort products
     * @param list          list of products
     * @param frame         frame for relvalidation
     * @param panel         for sizing components
     * @return              panel containing every search component
     */
    public JPanel createSearchPanel(ProductList list, JFrame frame, ProductsPanel panel){
        this.list = list;
        this.frame = frame;
        this.productsPanel = panel;
        this.backgroundColor = new Color(171, 196, 187);

        alphaAsc = new AlphabeticalAsc();
        priceAsc = new PriceAsc();
        popularity = new Popularity();

        listAndSearchHolder = new JPanel(new BorderLayout());
        searchPanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);

        searchPanel.setBorder(new MatteBorder(0,0,2,0,Color.BLACK));

        searchPanel.setBackground(backgroundColor);

        searchPanel.setMinimumSize(new Dimension(0,70));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,70));
        searchPanel.setPreferredSize(new Dimension(listAndSearchHolder.getWidth(), 70));

        createSearchField();
        createSearchCategory();
        createSort();
        createInfoLabel();
        createWishListActions();

        c.gridx = 0;
        c.gridy = 0;
        searchPanel.add(searchFieldLabel,c);

        c.gridx = 1;
        c.gridy = 0;
        searchPanel.add(searchCategoryLabel,c);

        c.gridx = 2;
        c.gridy = 0;
        searchPanel.add(sortLabel,c);

        c.gridx = 3;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        JPanel temp = new JPanel(new GridBagLayout());
        temp.add(new JLabel());
        searchPanel.add(temp,c);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        searchPanel.add(searchField,c);

        c.gridx = 1;
        c.gridy = 1;
        searchPanel.add(searchCategory,c);

        c.gridx = 2;
        c.gridy = 1;
        searchPanel.add(sort,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 1;
        searchPanel.add(infoLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.gridy = 1;
        searchPanel.add(wishToCartPanel,c);


        return searchPanel;
    }

    /**
     * Creates the sort combobox
     */
    private void createSort() {
        sortLabel = new JLabel(sortText);

        sort = new JComboBox<>(searchTypeList);

        sort.addActionListener(createSortAction());
    }

    /**
     * Sorts the list of products according to the chosen value
     * @return      action for combobox
     */
    private ActionListener createSortAction() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = sort.getSelectedIndex();

                switch(choice){
                    case 0: applyAlphabeticalAsc();
                            break;
                    case 1: applyAlphabeticalDesc();
                            break;
                    case 2: applyPriceAsc();
                            break;
                    case 3: applyPriceDesc();
                            break;
                    case 4: applyPopularityAsc();
                            break;
                    default: applyAlphabeticalAsc();
                            break;
                }
            }
        };

        return action;
    }

    /**
     * Sort by popularity
     */
    private void applyPopularityAsc() {
        Collections.sort(list.getProductList(), popularity);

        updatePanel();
    }

    /**
     * Updates panel, to refresh page
     */
    private void updatePanel() {
        productsPanel.updateProductPanel();

        frame.revalidate();
    }

    /**
     * Sort by descending price
     */
    private void applyPriceDesc() {
        Collections.sort(list.getProductList(), priceAsc.reversed());

        updatePanel();
    }

    /**
     * Sort by ascending price
     */
    private void applyPriceAsc() {
        Collections.sort(list.getProductList(), priceAsc);

        updatePanel();
    }

    /**
     * Sort by ascending alphabetical
     */
    private void applyAlphabeticalAsc() {
        Collections.sort(list.getProductList(), alphaAsc);

        updatePanel();
    }

    /**
     * Sort by descending alphabetical
     */
    private void applyAlphabeticalDesc() {
        Collections.sort(list.getProductList(), alphaAsc.reversed());

        updatePanel();
    }

    /**
     * Creates panel for wishlist actions
     */
    private void createWishListActions() {
        wishToCartPanel = new JPanel(new GridBagLayout());
        wishToCartPanel.setBackground(backgroundColor);
        GridBagConstraints c = new GridBagConstraints();

        wishToCart = new JButton(wishToCartDesc);
        wishToCart.setToolTipText(wishToCartToolTip);
        wishToCart.addActionListener(createAddToCartActionListener());

        deleteWishList = new JButton(deleteWishListDesc);
        deleteWishList.addActionListener(createDeleteWishListActionListener());

        c.insets = new Insets(5,5,0,5);
        wishToCartPanel.add(wishToCart,c);
        wishToCartPanel.add(deleteWishList,c);

    }

    /**
     * If the user clikc on the button, it removes
     * every product from wishlist
     * @return          action for button
     */
    private ActionListener createDeleteWishListActionListener() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Product p : list.getProductList()){
                    p.setOnWishList(false);
                }

                updatePanel();
            }
        };

        return action;
    }

    /**
     * When the user clicks on the button, it puts
     * every product to the cart, which were on the wishlist
     * @return          action for button
     */
    private ActionListener createAddToCartActionListener() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Product p : list.getProductList()){
                    if(p.isOnWishList()) {
                        if (p.getAmountInCart() == 0 && p.getWarehouseQuantity() > 0) {
                            p.setAmountInCart(1);
                            p.setInCart(true);
                        }
                    }
                }
            }
        };

        return action;
    }

    /**
     * Creates an info label, how many items is currently
     * showing on screen
     */
    private void createInfoLabel() {
        infoLabel = new JLabel(infoLabelDesc + list.getProductList().size());
    }

    /**
     * Update the label, which shows the amount of items on screen
     * @param amount         the amount of items on screen
     */
    private void updateInfoLabel(int amount) {
        infoLabel.setText(infoLabelDesc + amount);

        searchPanel.revalidate();
    }

    /**
     * Creates the search by category combobox
     */
    private void createSearchCategory() {
        searchCategoryLabel = new JLabel(searchCategoryText);

        searchCategory = new JComboBox(list.getCategoryList().toArray());
        searchCategory.insertItemAt("Kívánságlistán", 0);
        searchCategory.insertItemAt("Összes", 0);
        searchCategory.setSelectedIndex(0);
        searchCategory.setEditable(false);

        searchCategory.addActionListener(createSearchCategoryListener());
    }

    /**
     * If the value changes it will display items which are
     * in the category
     * @return      action for combobox
     */
    private ActionListener createSearchCategoryListener(){
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        };

        return action;
    }

    /**
     * When the user searches or changes any combobox, this method
     * is called. It changes the products isShown value
     */
    private void search(){
        String fieldText = searchField.getText();
        fieldText = fieldText.toLowerCase();
        String selectedSearchCategory = searchCategory.getSelectedItem().toString();
        int amount = 0;

        System.out.println("Update! : " + fieldText);
        for(Product p : list.getProductList()){
            String productName = p.getProductName().toLowerCase();
            boolean onWishList = p.isOnWishList();
            boolean isShown = p.isShown();

            if(selectedSearchCategory.equals("Összes")){
                setProductToShow(p, productName, fieldText, isShown);
            }
            else if(selectedSearchCategory.equals("Kívánságlistán")){
                if(onWishList){
                    setProductToShow(p, productName, fieldText, isShown);
                }
                else{
                    p.setShown(false);
                }
            }
            else if(selectedSearchCategory.equals(p.getCategory())){
                setProductToShow(p, productName, fieldText, isShown);
            }
            else{
                p.setShown(false);
            }

            if(p.isShown()){
                amount++;
            }
        }

        updateInfoLabel(amount);
        updatePanel();

    }

    /**
     * If the product is in the category, then this method
     * decides if it contains the character from the search field
     * @param p                 product to be decided
     * @param productName       porducts name
     * @param fieldText         search field text
     * @param isShown           to show the product or not
     */
    private void setProductToShow(Product p, String productName, String fieldText, boolean isShown){
        if(!productName.contains(fieldText) && isShown){
            p.setShown(false);
        }
        else if(productName.contains(fieldText) && !isShown){
            p.setShown(true);
        }
    }

    /**
     * Creates the searchfield
     */
    private void createSearchField() {
        searchFieldLabel = new JLabel(searchLabelText);
        searchField = new JTextField();
        searchField.setEditable(true);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search();
            }
        });

    }
}
