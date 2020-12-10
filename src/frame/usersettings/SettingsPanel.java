package frame.usersettings;

import entity.Cart;
import entity.ProductList;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel {

    private final String loadCardDesc = "Új bankkártya betöltése";
    private final String saveCardDesc = "Új bankkártya mentése";
    private final String loadWishListDesc = "Új kívánságlista betöltése";
    private final String saveWishListDesc = "Jelenlegi kívánságlista mentése";
    private final String loadCustomerDesc = "Új vásárló betöltése";
    private final String saveCustomerDesc = "Új vásárló mentése";
    private final String loadCartDesc = "Új kosár betöltése";
    private final String saveCartDesc = "Jelenlegi kosár mentése";

    private JPanel settingsPanel;

    private JPanel cartSettings;
    private JButton saveCart;
    private JButton loadCart;

    private JPanel wishListSettings;
    private JButton saveWishList;
    private JButton loadWishList;

    private JPanel customerSettings;
    private JButton saveCustomer;
    private JButton loadCustomer;

    private JPanel cardSettings;
    private JButton saveCard;
    private JButton loadCard;

    private final Cart cart;
    private final ProductList list;

    public SettingsPanel(Cart cart, ProductList list){
        this.cart = cart;
        this.list = list;
    }

    public JPanel createSettingsPanel(){
        settingsPanel = new JPanel(new GridBagLayout());

        createCartSettings();

        createCustomerSettings();

        createWishListSettings();

        createCardSettings();

        return settingsPanel;

    }

    private void createCartSettings() {
        loadCart = new JButton(loadCartDesc);
        saveCart = new JButton(saveCartDesc);

        cartSettings = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        cartSettings.setBorder(BorderFactory.createTitledBorder("Kosár"));

        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        cartSettings.add(loadCart,c);

        c.gridx = 0;
        c.gridy = 1;
        cartSettings.add(saveCart,c);

        c.gridx = 0;
        c.gridy = 0;
        settingsPanel.add(cartSettings, c);
    }

    private void createCustomerSettings() {
        loadCustomer = new JButton(loadCustomerDesc);
        saveCustomer = new JButton(saveCustomerDesc);

        customerSettings = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        customerSettings.setBorder(BorderFactory.createTitledBorder("Vásárlók"));

        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        customerSettings.add(loadCustomer,c);

        c.gridx = 0;
        c.gridy = 1;
        customerSettings.add(saveCustomer,c);

        c.gridx = 1;
        c.gridy = 0;
        settingsPanel.add(customerSettings, c);
    }

    private void createWishListSettings() {
        loadWishList = new JButton(loadWishListDesc);
        saveWishList = new JButton(saveWishListDesc);

        wishListSettings = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        wishListSettings.setBorder(BorderFactory.createTitledBorder("Kívánságlista"));

        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        wishListSettings.add(loadWishList,c);

        c.gridx = 0;
        c.gridy = 1;
        wishListSettings.add(saveWishList,c);

        c.gridx = 0;
        c.gridy = 1;
        settingsPanel.add(wishListSettings, c);
    }

    private void createCardSettings() {
        loadCard = new JButton(loadCardDesc);
        saveCard = new JButton(saveCardDesc);

        cardSettings = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        cardSettings.setBorder(BorderFactory.createTitledBorder("Bankkártya"));

        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        cardSettings.add(loadCard,c);

        c.gridx = 0;
        c.gridy = 1;
        cardSettings.add(saveCard,c);

        c.gridx = 1;
        c.gridy = 1;
        settingsPanel.add(cardSettings, c);

    }

}
