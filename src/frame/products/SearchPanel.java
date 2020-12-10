package frame.products;

import entity.Product;
import entity.ProductList;
import tools.sort.AlphabeticalAsc;
import tools.sort.Popularity;
import tools.sort.PriceAsc;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class SearchPanel {

    private final String searchLabelText = "Keresés a termékek között:";
    private final String searchCategoryText = "Kategória";
    private final String sortText = "Rendezés";
    private final String infoLabelDesc = "Termék megjelenítve: ";
    private final String wishToCartDesc = "Kívánságlista -> Kosár";
    private final String wishToCartToolTip = "Kívánságlistán lévő termékek kosárba helyezése";
    private final String deleteWishListDesc = "Kívánságlista törlése";
    private final String[] searchTypeList = {"ABC sorrend A-Z", "ABC sorrend Z-A", "Ár növekvő", "Ár csökkenő", "Népszerűség"};

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

    private AlphabeticalAsc alphaAsc;
    private PriceAsc priceAsc;
    private Popularity popularity;

    private Color backgroundColor;

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
        createSearchCategory(list);
        createSort();
        createInfoLabel();
        createInfoPanel();

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

    private void createSort() {
        sortLabel = new JLabel(sortText);

        sort = new JComboBox<>(searchTypeList);

        sort.addActionListener(createSortAction());
    }

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

    private void applyPopularityAsc() {
        Collections.sort(list.getProductList(), popularity);

        updatePanel();
    }

    private void updatePanel() {
        productsPanel.updateProductPanel(list, frame);

        frame.revalidate();
    }

    private void applyPriceDesc() {
        Collections.sort(list.getProductList(), priceAsc.reversed());

        updatePanel();
    }

    private void applyPriceAsc() {
        Collections.sort(list.getProductList(), priceAsc);

        updatePanel();
    }

    private void applyAlphabeticalAsc() {
        Collections.sort(list.getProductList(), alphaAsc);

        updatePanel();
    }

    private void applyAlphabeticalDesc() {
        Collections.sort(list.getProductList(), alphaAsc.reversed());

        updatePanel();
    }

    private void createInfoPanel() {
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

    private void createInfoLabel() {
        infoLabel = new JLabel(infoLabelDesc + list.getProductList().size());
    }

    private void updateInfoLabel(int amount) {
        infoLabel.setText(infoLabelDesc + amount);

        searchPanel.revalidate();
    }

    private void createSearchCategory(ProductList list) {
        searchCategoryLabel = new JLabel(searchCategoryText);

        searchCategory = new JComboBox(list.getCategoryList().toArray());
        searchCategory.insertItemAt("Kívánságlistán", 0);
        searchCategory.insertItemAt("Összes", 0);
        searchCategory.setSelectedIndex(0);
        searchCategory.setEditable(false);

        searchCategory.addActionListener(createSearchCategoryListener());
    }

    private ActionListener createSearchCategoryListener(){
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        };

        return action;
    }

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

    private void setProductToShow(Product p, String productName, String fieldText, boolean isShown){
        if(!productName.contains(fieldText) && isShown){
            p.setShown(false);
        }
        else if(productName.contains(fieldText) && !isShown){
            p.setShown(true);
        }
    }

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
