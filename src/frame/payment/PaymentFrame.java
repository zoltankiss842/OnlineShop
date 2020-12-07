package frame.payment;

import entity.Cart;
import entity.CountyList;
import tools.FileIO;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentFrame {

    private final String paymentFrameTitle = "Termékek kifizetése";
    private final String paymentFrameFaviconPath = "resources\\images\\payment.png";
    private final String countiesFilePath = "resources\\data\\megyek.txt";
    private final String citiesFilePath = "resources\\data\\telepulesek.txt";
    private final String countriesFilePath = "resources\\data\\orszagok.txt";
    private final String[] namePrefixList = {"", "Dr.", "Ifj.", "Id.", "Prof.", "Özv.", "Jr.", "Sr."};

    private final String paymentIdDesc = "Fizetési azonosító:";
    private final String paymentAmountDesc = "Fizetendő összeg:";

    private final String lastNameDesc = "Vezetéknév:";
    private final String firstNameDesc = "Keresztnév:";
    private final String streetDesc = "Utca:";
    private final String houseNumberDesc = "Házszám:";
    private final String floorOrDoorDesc = "Emelet/Ajtó:";
    private final String cityDesc = "Város:";
    private final String countyDesc = "Megye:";
    private final String countryDesc = "Ország:";
    private final String postCodeDesc = "Irányítószám:";
    private final String districtDesc = "Kerület:";

    private final String cardHoldersNameDesc = "Kártyán lévő név";
    private final String cardNumberDesc = "Kártyaszám";
    private final String expiryDateDesc = "Lejárati dátum (pl. 09/20)";
    private final String cvcDesc = "CVC";
    private final String countryOfIssueDesc = "Kibocsátó ország";

    private final String payButtonDesc = "Fizetés";
    private final String abortPaymentButtonDesc = "Fizetés megszakítása";

    private CountyList counties;

    private JLabel paymentId;
    private JLabel paymentAmount;
    private JPanel paymentDetailsHolder;

    private JPanel nameDetailsHolder;
    private JComboBox<String> namePrefix;
    private JLabel firstNameDescription;
    private JFormattedTextField firstName;
    private JLabel lastNameDescription;
    private JFormattedTextField lastName;

    private JPanel addressDetailsHolder;

    private JLabel streetDescription;
    private JFormattedTextField street;
    private JLabel houseNumberDescription;
    private JFormattedTextField houseNumber;
    private JLabel floorOrDoorDescription;
    private JFormattedTextField floorOrDoor;
    private JPanel strHouseFloorHolder;

    private JLabel cityDescription;
    private JComboBox<String> city;
    private JLabel countyDescription;
    private JComboBox<String> county;
    private JLabel postCodeDescription;
    private JFormattedTextField postCode;
    private JPanel cityCountyPostHolder;

    private JLabel countryDescription;
    private JComboBox<String> country;
    private JPanel countryHolder;

    private JPanel cardDetailsHolder;
    private JLabel cardHoldersNameDescription;
    private JFormattedTextField cardHoldersName;
    private JPanel cardHolderHolder;

    private JLabel cardNumberDescription;
    private JFormattedTextField cardNumber;
    private JPanel cardNumberHolder;

    private JLabel expiryDateDescription;
    private JFormattedTextField expiryDate;
    private JLabel cvcDescription;
    private JFormattedTextField cvc;
    private JPanel expCvcHolder;

    private JLabel countryOfIssueDescription;
    private JComboBox<String> countryOfIssue;
    private JPanel countryOfIssueHolder;

    private JPanel centerHolder;

    private JButton payButton;
    private JButton abortPayment;
    private JPanel payButtonsHolder;

    private JFrame paymentFrame;
    private Cart cart;

    public void createPaymentFrame(Cart cart){
        paymentFrame = new JFrame(paymentFrameTitle);
        paymentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        paymentFrame.setLayout(new BorderLayout());

        paymentFrame.setSize(700,500);
        paymentFrame.setLocationRelativeTo(null);
        paymentFrame.setResizable(true);

        ImageIcon icon = new ImageIcon(paymentFrameFaviconPath);
        paymentFrame.setIconImage(icon.getImage());

        initializePaymentFrame();

        paymentFrame.setVisible(true);
    }

    private void initializePaymentFrame(){
        createPaymentDetails();
        
        createNameDetails();

        createAddressDetails();

        createCardDetails();

        createPaymentButtons();
    }

    private void createPaymentDetails() {
        paymentId = new JLabel(paymentIdDesc);
        paymentAmount = new JLabel(paymentAmountDesc);

        paymentDetailsHolder = new JPanel(new GridLayout(1,2));
        paymentDetailsHolder.add(paymentId);
        paymentDetailsHolder.add(paymentAmount);

        paymentFrame.add(paymentDetailsHolder, BorderLayout.PAGE_START);

    }

    private void createPaymentButtons() {
        payButton = new JButton(payButtonDesc);
        abortPayment = new JButton(abortPaymentButtonDesc);
        payButtonsHolder = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);

        payButtonsHolder.add(payButton,c);
        payButtonsHolder.add(abortPayment,c);

        paymentFrame.add(payButtonsHolder, BorderLayout.PAGE_END);

    }

    private void createCardDetails() {

        cardDetailsHolder = new JPanel(new GridLayout(4,1));

        cardHoldersNameDescription = new JLabel(cardHoldersNameDesc);
        cardHoldersName = new JFormattedTextField(cardHolderFormatter());
        cardHolderHolder = new JPanel(new GridLayout(1,2));
        cardHolderHolder.add(cardHoldersNameDescription);
        cardHolderHolder.add(cardHoldersName);

        cardNumberDescription = new JLabel(cardNumberDesc);
        cardNumber = new JFormattedTextField(cardNumberFormatter());
        cardNumberHolder = new JPanel(new GridLayout(1,2));
        cardNumberHolder.add(cardNumberDescription);
        cardNumberHolder.add(cardNumber);

        expiryDateDescription = new JLabel(expiryDateDesc);
        expiryDate = new JFormattedTextField(expiryDateNumberFormatter());
        cvcDescription = new JLabel(cvcDesc);
        cvc = new JFormattedTextField(cvcNumberFormatter());
        expCvcHolder = new JPanel(new GridLayout(1,4));
        expCvcHolder.add(expiryDateDescription);
        expCvcHolder.add(expiryDate);
        expCvcHolder.add(cvcDescription);
        expCvcHolder.add(cvc);

        countryOfIssueDescription = new JLabel(countryOfIssueDesc);
        countryOfIssue = new JComboBox<>(new FileIO().readFromTxtFileCountries(countriesFilePath));
        countryOfIssueHolder = new JPanel(new GridLayout(1,2));
        countryOfIssueHolder.add(countryOfIssueDescription);
        countryOfIssueHolder.add(countryOfIssue);

        cardDetailsHolder.add(cardHolderHolder);
        cardDetailsHolder.add(cardNumberHolder);
        cardDetailsHolder.add(expCvcHolder);
        cardDetailsHolder.add(countryOfIssueHolder);

        centerHolder.add(cardDetailsHolder);

        paymentFrame.add(centerHolder, BorderLayout.CENTER);

    }

    private MaskFormatter cvcNumberFormatter() {
        MaskFormatter format = new MaskFormatter();
        try{
            format.setMask("###");
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return format;
    }

    private MaskFormatter expiryDateNumberFormatter() {
        MaskFormatter format = new MaskFormatter();
        try{
            format.setMask("##/##");
            format.setPlaceholder("/");
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return format;
    }

    private MaskFormatter cardNumberFormatter() {
        MaskFormatter format = new MaskFormatter();
        try{
            format.setMask("####-####-####-####-###");
            format.setPlaceholder("-");
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return format;
    }

    private MaskFormatter cardHolderFormatter() {
        MaskFormatter format = new MaskFormatter();
        try{
            format.setMask("???????????????????");
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return format;
    }

    private void createAddressDetails() {
        readCounties();

        addressDetailsHolder = new JPanel(new GridLayout(3,1));
        addressDetailsHolder.setBorder(new MatteBorder(0,0,1,0,Color.BLACK));
      

        streetDescription = new JLabel(streetDesc);
        street = new JFormattedTextField(restrictedFormatter());

        houseNumberDescription = new JLabel(houseNumberDesc);
        houseNumber = new JFormattedTextField(houseNumberFormatter());

        floorOrDoorDescription = new JLabel(floorOrDoorDesc);
        floorOrDoor = new JFormattedTextField(floorOrDoorFormatter());

        strHouseFloorHolder = new JPanel(new GridLayout(3,2));
        strHouseFloorHolder.add(streetDescription);
        strHouseFloorHolder.add(street);
        strHouseFloorHolder.add(houseNumberDescription);
        strHouseFloorHolder.add(houseNumber);
        strHouseFloorHolder.add(floorOrDoorDescription);
        strHouseFloorHolder.add(floorOrDoor);

        countyDescription = new JLabel(countyDesc);
        county = new JComboBox<>(counties.getEveryCounty());
        county.addActionListener(countyActionListener());

        cityDescription = new JLabel(cityDesc);
        city = new JComboBox<>(counties.getEveryCityForCountry((String)county.getSelectedItem()));

        postCodeDescription = new JLabel(postCodeDesc);
        postCode = new JFormattedTextField(postCodeFormatter());

        cityCountyPostHolder = new JPanel(new GridLayout(3,2));
        cityCountyPostHolder.add(countyDescription);
        cityCountyPostHolder.add(county);
        cityCountyPostHolder.add(cityDescription);
        cityCountyPostHolder.add(city);
        cityCountyPostHolder.add(postCodeDescription);
        cityCountyPostHolder.add(postCode);

        countryDescription = new JLabel(countryDesc);
        country = new JComboBox<>(new String[]{"Magyarország"});
        countryHolder = new JPanel(new GridLayout(1,2));
        countryHolder.add(countryDescription);
        countryHolder.add(country);

        addressDetailsHolder.add(strHouseFloorHolder);

        addressDetailsHolder.add(cityCountyPostHolder);

        addressDetailsHolder.add(countryHolder);

        centerHolder.add(addressDetailsHolder);

    }

    private ActionListener countyActionListener() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                city.removeAllItems();

                String[] temp = counties.getEveryCityForCountry((String)county.getSelectedItem());

                for(String s : temp){
                    city.addItem(s);
                }

                city.revalidate();

            }
        };

        return action;
    }

    private MaskFormatter postCodeFormatter() {
        MaskFormatter format = new MaskFormatter();
        try{
            format.setMask("####");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return format;
    }

    private MaskFormatter floorOrDoorFormatter() {
        MaskFormatter format = new MaskFormatter();
        try{
            format.setMask("AAAAAAAAAAAAAAAAAAAA");
            format.setValidCharacters("./");
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return format;
    }

    private MaskFormatter houseNumberFormatter() {
        MaskFormatter format = new MaskFormatter();
        try{
            format.setMask("AAAA");
            format.setValidCharacters("./");
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return format;
    }

    private MaskFormatter restrictedFormatter() {
        MaskFormatter format = new MaskFormatter();
        try{
            format.setInvalidCharacters("<>:\"/\\|?*");
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return format;
    }

    private void readCounties() {
        counties = new CountyList();
        FileIO io = new FileIO();
        counties.setCountyList(io.readFromTxtFile(countiesFilePath));
        io.readFromTxtFile(counties, citiesFilePath);
    }

    private void createNameDetails() {
        centerHolder = new JPanel(new GridLayout(3,1));

        nameDetailsHolder = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.1;
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        nameDetailsHolder.setBorder(new MatteBorder(0,0,1,0,Color.BLACK));

        MaskFormatter formatter = createFormatter();

        namePrefix = new JComboBox<>(namePrefixList);
        lastNameDescription = new JLabel(lastNameDesc);
        lastName = new JFormattedTextField(formatter);
        firstNameDescription = new JLabel(firstNameDesc);
        firstName = new JFormattedTextField(formatter);

        nameDetailsHolder.add(namePrefix,c);
        nameDetailsHolder.add(lastNameDescription,c);
        nameDetailsHolder.add(lastName,c);
        nameDetailsHolder.add(firstNameDescription,c);
        nameDetailsHolder.add(firstName,c);

        centerHolder.add(nameDetailsHolder);
    }

    private MaskFormatter createFormatter() {
        MaskFormatter format = new MaskFormatter();
        try{
            format.setMask("????????????????????");
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return format;
    }
}
