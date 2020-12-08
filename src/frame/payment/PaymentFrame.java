package frame.payment;

import entity.Cart;
import entity.CountyList;
import tools.FileIO;
import tools.StringManipulation;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class PaymentFrame {

    //TODO - quantity have to be decreased
    //TODO - abort payment implementation
    //TODO - add date

    private final String paymentFrameTitle = "Termékek kifizetése";
    private final String paymentFrameFaviconPath = "resources\\images\\payment.png";
    private final String countiesFilePath = "resources\\data\\megyek.txt";
    private final String citiesFilePath = "resources\\data\\telepulesek.txt";
    private final String countriesFilePath = "resources\\data\\orszagok.txt";
    private final String visaPictureFilePath = "resources\\images\\visa.png";
    private final String masterCardPictureFilePath = "resources\\images\\mastercard.png";
    private final String maestroPictureFilePath = "resources\\images\\maestro.png";
    private final String successfulPaymentPictureFilePath = "resources\\images\\successfull_payment.png";
    private final String[] namePrefixList = {"", "Dr.", "Ifj.", "Id.", "Prof.", "Özv.", "Jr.", "Sr."};

    private final String paymentIdDesc = "Rendelési azonosító:";
    private final String paymentAmountDesc = "Fizetendő összeg:";

    private final String namePrefixDesc = "Megszólítás:";
    private final String lastNameDesc = "Vezetéknév*:";
    private final String firstNameDesc = "Keresztnév*:";
    private final String emailDesc = "Email cím*:";
    private final String phoneDesc = "Telefonszám:";
    private final String streetDesc = "Utca*:";
    private final String houseNumberDesc = "Házszám*:";
    private final String floorOrDoorDesc = "Emelet/Ajtó:";
    private final String cityDesc = "Város/Kerület*:";
    private final String countyDesc = "Megye*:";
    private final String countryDesc = "Ország:";
    private final String postCodeDesc = "Irányítószám*:";

    private final String cardHoldersNameDesc = "Kártyán lévő név*";
    private final String cardNumberDesc = "Kártyaszám*";
    private final String expiryDateDesc = "Lejárati dátum (pl. 09/20)*";
    private final String cvcDesc = "CVC*";
    private final String countryOfIssueDesc = "Kibocsátó ország";
    private final String commentDesc = "Megjegyzés:";

    private final String payButtonDesc = "Fizetés";
    private final String abortPaymentButtonDesc = "Fizetés megszakítása";

    private CountyList counties;

    private JLabel paymentId;
    private JLabel paymentAmount;
    private JPanel paymentDetailsHolder;

    private JPanel nameDetailsHolder;
    private JLabel namePrefixDescription;
    private JComboBox<String> namePrefix;
    private JLabel firstNameDescription;
    private JTextField firstName;
    private JLabel lastNameDescription;
    private JTextField lastName;
    private JLabel emailDescription;
    private JTextField email;
    private JLabel phoneDescription;
    private JTextField phone;

    private JPanel addressDetailsHolder;

    private JLabel streetDescription;
    private JTextField street;
    private JLabel houseNumberDescription;
    private JTextField houseNumber;
    private JLabel floorOrDoorDescription;
    private JTextField floorOrDoor;
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
    private JTextField cardHoldersName;
    private JPanel cardHolderHolder;

    private JLabel cardNumberDescription;
    private JTextField cardNumber;
    private JPanel cardNumberHolder;

    private JLabel expiryDateDescription;
    private JFormattedTextField expiryDate;
    private JLabel cvcDescription;
    private JFormattedTextField cvc;
    private JPanel expCvcHolder;

    private JLabel countryOfIssueDescription;
    private JComboBox<String> countryOfIssue;
    private JPanel countryOfIssueHolder;
    private JLabel commentDescription;
    private JTextField comment;
    private JPanel commentHolder;

    private BufferedImage visa;
    private BufferedImage masterCard;
    private BufferedImage maestro;
    private JLabel visaPicture;
    private JLabel masterCardPicture;
    private JLabel maestroPicture;
    private JPanel cardPicsHolder;

    private JPanel pageStartHolder;
    private JPanel centerHolder;

    private JButton payButton;
    private JButton abortPayment;
    private JPanel payButtonsHolder;

    private BufferedImage successfullPay;

    private boolean cardSelected;

    private JFrame paymentFrame;
    private Cart cart;
    private StringManipulation manipulation;
    private FileIO io;

    public void createPaymentFrame(Cart cart){
        cardSelected = false;
        io = new FileIO();

        this.cart = cart;

        paymentFrame = new JFrame(paymentFrameTitle);
        paymentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        paymentFrame.setLayout(new BorderLayout());

        /*paymentFrame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("Width: " + paymentFrame.getWidth() + ", Height: " + paymentFrame.getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });*/

        paymentFrame.setSize(600,650);
        paymentFrame.setLocationRelativeTo(null);
        paymentFrame.setResizable(false);

        ImageIcon icon = new ImageIcon(paymentFrameFaviconPath);
        paymentFrame.setIconImage(icon.getImage());

        manipulation = new StringManipulation();

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
        paymentId = new JLabel(paymentIdDesc + " " + cart.getId());
        paymentAmount = new JLabel(paymentAmountDesc+ " " + cart.getAmount());

        paymentDetailsHolder = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5,0,5,0);
        paymentDetailsHolder.setBorder(new MatteBorder(0,0,3,0,Color.BLACK));

        c.gridx = 0;
        c.gridy = 0;
        paymentDetailsHolder.add(paymentId,c);

        c.gridx = 0;
        c.gridy = 1;
        paymentDetailsHolder.add(paymentAmount,c);

        pageStartHolder = new JPanel(new GridBagLayout());

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        pageStartHolder.add(paymentDetailsHolder, c);

    }

    private void createPaymentButtons() {
        payButton = new JButton(payButtonDesc);

        payButton.addActionListener(payButtonActionListener());

        abortPayment = new JButton(abortPaymentButtonDesc);
        payButtonsHolder = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);

        payButtonsHolder.add(payButton,c);
        payButtonsHolder.add(abortPayment,c);

        try{
            successfullPay = ImageIO.read(new File(successfulPaymentPictureFilePath));
        }
        catch(Exception e){

        }


        paymentFrame.add(payButtonsHolder, BorderLayout.PAGE_END);

    }

    private ActionListener payButtonActionListener() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!checkEveryField()){
                    setBorderForWrongOnes();
                    JOptionPane.showMessageDialog(paymentFrame,
                            "Egy vagy több mező értéke nem megfelelő!",
                            "Rossz mező értékek!",
                            JOptionPane.ERROR_MESSAGE);
                }
                else{
                    clearBordersForWrongOnes();
                    String[] data = createData();
                    io.writeTransactionToTxt(data, cart);
                    JOptionPane.showMessageDialog(paymentFrame,
                            "A tranzakció sikeres volt.",
                            "Sikeres fizetés",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(successfullPay));
                }
            }
        };

        return action;
    }

    private String[] createData() {
        String[] data = new String[17];

        data[0] = cart.getId();
        data[1] = String.valueOf(cart.getAmount());
        data[2] = String.valueOf(namePrefix.getSelectedItem());
        data[3] = lastName.getText();
        data[4] = firstName.getText();
        data[5] = phone.getText();
        data[6] = email.getText();
        data[7] = street.getText();
        data[8] = houseNumber.getText();
        data[9] = floorOrDoor.getText();
        data[10] = String.valueOf(county.getSelectedItem());
        data[11] = String.valueOf(city.getSelectedItem());
        data[12] = postCode.getText();
        data[13] = String.valueOf(country.getSelectedItem());
        data[14] = comment.getText();
        data[15] = cardHoldersName.getText();
        data[16] = cardNumber.getText();

        return data;
    }

    private void clearBordersForWrongOnes() {
        setBorderForWrongOnes();
    }

    private void setBorderForWrongOnes() {
        boolean test = manipulation.isValidString(lastName.getText(), false);
        if(!test){
            lastName.setBorder(new LineBorder(Color.RED, 2));
        }
        else{
            lastName.setBorder(null);
        }

        test = manipulation.isValidString(firstName.getText(), false);
        if(!test){
            firstName.setBorder(new LineBorder(Color.RED, 2));
        }
        else{
            firstName.setBorder(null);
        }

        test = manipulation.isValidPhoneNumber(phone.getText(), true);
        if(!test){
            phone.setBorder(new LineBorder(Color.RED, 2));
        }
        else{
            phone.setBorder(null);
        }

        test = manipulation.isValidEmail(email.getText(), false);
        if(!test){
            email.setBorder(new LineBorder(Color.RED, 2));
        }
        else{
            email.setBorder(null);
        }

        test = manipulation.isValidString(street.getText(), false);
        if(!test){
            street.setBorder(new LineBorder(Color.RED, 2));
        }
        else{
            street.setBorder(null);
        }

        test = manipulation.isValidHouseNumber(houseNumber.getText(), false);
        if(!test){
            houseNumber.setBorder(new LineBorder(Color.RED, 2));
        }
        else{
            houseNumber.setBorder(null);
        }

        test = manipulation.isValidHouseNumber(floorOrDoor.getText(), true);
        if(!test){
            floorOrDoor.setBorder(new LineBorder(Color.RED, 2));
        }
        else{
            floorOrDoor.setBorder(null);
        }

        test = manipulation.isValidPostCode(postCode.getText(), false);
        if(!test){
            postCode.setBorder(new LineBorder(Color.RED, 2));
        }
        else{
            postCode.setBorder(null);
        }

        test = manipulation.isValidString(cardHoldersName.getText(), false);
        if(!test){
            cardHoldersName.setBorder(new LineBorder(Color.RED, 2));
        }
        else{
            cardHoldersName.setBorder(null);
        }

        test = manipulation.isValidCardNumber(cardNumber.getText(), false);
        if(!test){
            cardNumber.setBorder(new LineBorder(Color.RED, 2));
        }
        else{
            cardNumber.setBorder(null);
        }

        test = manipulation.isValidPhoneNumber(expiryDate.getText(), false);
        if(!test){
            expiryDate.setBorder(new LineBorder(Color.RED, 2));
        }
        else{
            expiryDate.setBorder(null);
        }

        test = manipulation.isValidPhoneNumber(cvc.getText(), false);
        if(!test){
            cvc.setBorder(new LineBorder(Color.RED, 2));
        }
        else{
            cvc.setBorder(null);
        }
    }

    private boolean checkEveryField() {
        boolean lN = manipulation.isValidString(lastName.getText(), false);
        boolean fN = manipulation.isValidString(firstName.getText(), false);
        boolean phNum = manipulation.isValidPhoneNumber(phone.getText(), true);
        boolean eMail = manipulation.isValidEmail(email.getText(), false);

        boolean str = manipulation.isValidString(street.getText(), false);
        boolean hN = manipulation.isValidHouseNumber(houseNumber.getText(), false);
        boolean floDoor = manipulation.isValidHouseNumber(floorOrDoor.getText(), true);
        boolean postcode = manipulation.isValidPostCode(postCode.getText(), false);

        boolean cardname = manipulation.isValidString(cardHoldersName.getText(), false);
        boolean cardNum = manipulation.isValidCardNumber(cardNumber.getText(), false);
        boolean expDate = manipulation.isValidPhoneNumber(expiryDate.getText(), false);
        boolean cVc = manipulation.isValidPhoneNumber(cvc.getText(), false);

        boolean result = lN && fN && phNum && eMail && str
                            && hN && floDoor && postcode && cardname
                            && cardNum && expDate && cVc;

        return result;
    }

    private void createCardDetails() {

        cardDetailsHolder = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        cardHoldersNameDescription = new JLabel(cardHoldersNameDesc);
        cardHoldersName = new JTextField();
        cardHolderHolder = new JPanel(new GridLayout(1,2));
        cardHolderHolder.add(cardHoldersNameDescription);
        cardHolderHolder.add(cardHoldersName);

        cardNumberDescription = new JLabel(cardNumberDesc);
        cardNumber = new JTextField();
        cardNumber.getDocument().addDocumentListener(createCardNumberDocumentListener());
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

        try{
            visa = ImageIO.read(new File(visaPictureFilePath));
            masterCard = ImageIO.read(new File(masterCardPictureFilePath));
            maestro = ImageIO.read(new File(maestroPictureFilePath));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        visaPicture = new JLabel(new ImageIcon(visa.getScaledInstance(100,60,Image.SCALE_SMOOTH)));
        masterCardPicture = new JLabel(new ImageIcon(masterCard.getScaledInstance(100,60,Image.SCALE_SMOOTH)));
        maestroPicture = new JLabel(new ImageIcon(maestro.getScaledInstance(100,60,Image.SCALE_SMOOTH)));

        cardPicsHolder = new JPanel(new GridBagLayout());
        c.insets = new Insets(0,5,0,5);

        cardPicsHolder.add(visaPicture,c);
        cardPicsHolder.add(masterCardPicture,c);
        cardPicsHolder.add(maestroPicture,c);

        c.insets = new Insets(0,0,0,0);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.HORIZONTAL;
        cardDetailsHolder.add(cardHolderHolder,c);

        c.gridx = 0;
        c.gridy = 1;
        cardDetailsHolder.add(cardNumberHolder,c);

        c.gridx = 0;
        c.gridy = 2;
        cardDetailsHolder.add(expCvcHolder,c);

        c.gridx = 0;
        c.gridy = 3;
        cardDetailsHolder.add(countryOfIssueHolder,c);

        c.gridx = 0;
        c.gridy = 4;
        cardDetailsHolder.add(cardPicsHolder,c);

        centerHolder.add(cardDetailsHolder);

        paymentFrame.add(centerHolder, BorderLayout.CENTER);

    }

    private DocumentListener createCardNumberDocumentListener() {
        DocumentListener action = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkCardNumberInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkCardNumberInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkCardNumberInput();
            }
        };

        return action;
    }

    private void checkCardNumberInput() {
        if(manipulation.containsExactAmountDigit(cardNumber.getText(), 4) == 0
                && !cardSelected){
            Random rnd = new Random();
            int option = rnd.nextInt(3);
            if(option == 0){
                visaPicture.setBorder(new LineBorder(Color.BLACK, 3));
                masterCardPicture.setBorder(null);
                maestroPicture.setBorder(null);
            }
            else if(option == 1){
                masterCardPicture.setBorder(new LineBorder(Color.BLACK, 3));
                visaPicture.setBorder(null);
                maestroPicture.setBorder(null);
            }
            else{
                maestroPicture.setBorder(new LineBorder(Color.BLACK, 3));
                visaPicture.setBorder(null);
                masterCardPicture.setBorder(null);
            }

            cardSelected = true;
        }
        else if(manipulation.containsExactAmountDigit(cardNumber.getText(), 4) > 0){
            visaPicture.setBorder(null);
            masterCardPicture.setBorder(null);
            maestroPicture.setBorder(null);
            cardSelected = false;
        }
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

    private void createAddressDetails() {

        centerHolder = new JPanel(new GridLayout(2,1));
        readCounties();

        addressDetailsHolder = new JPanel(new GridBagLayout());
        addressDetailsHolder.setBorder(new MatteBorder(0,0,3,0,Color.BLACK));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;

        streetDescription = new JLabel(streetDesc);
        street = new JTextField();

        houseNumberDescription = new JLabel(houseNumberDesc);
        houseNumber = new JTextField();

        floorOrDoorDescription = new JLabel(floorOrDoorDesc);
        floorOrDoor = new JTextField();

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

        commentDescription = new JLabel(commentDesc);
        comment = new JTextField();
        commentHolder = new JPanel(new GridLayout(1,2));
        commentHolder.add(commentDescription);
        commentHolder.add(comment);

        c.gridx = 0;
        c.gridy = 0;
        addressDetailsHolder.add(strHouseFloorHolder,c);

        c.gridx = 0;
        c.gridy = 1;
        addressDetailsHolder.add(cityCountyPostHolder,c);

        c.gridx = 0;
        c.gridy = 2;
        addressDetailsHolder.add(countryHolder,c);

        c.gridx = 0;
        c.gridy = 3;
        addressDetailsHolder.add(commentHolder,c);


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

    private void readCounties() {
        counties = new CountyList();
        FileIO io = new FileIO();
        counties.setCountyList(io.readFromTxtFile(countiesFilePath));
        io.readFromTxtFile(counties, citiesFilePath);
    }

    private void createNameDetails() {
        nameDetailsHolder = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.1;
        c.fill = GridBagConstraints.HORIZONTAL;

        nameDetailsHolder.setBorder(new MatteBorder(0,0,3,0,Color.BLACK));

        MaskFormatter formatter = createFormatter();

        namePrefixDescription = new JLabel(namePrefixDesc);
        namePrefix = new JComboBox<>(namePrefixList);
        lastNameDescription = new JLabel(lastNameDesc);
        lastName = new JTextField();
        firstNameDescription = new JLabel(firstNameDesc);
        firstName = new JTextField();
        phoneDescription = new JLabel(phoneDesc);
        phone = new JTextField();
        emailDescription = new JLabel(emailDesc);
        email = new JTextField();

        c.gridx = 0;
        c.gridy = 0;
        nameDetailsHolder.add(namePrefixDescription,c);

        c.gridx = 1;
        c.gridy = 0;
        nameDetailsHolder.add(namePrefix,c);

        c.gridx = 0;
        c.gridy = 1;
        nameDetailsHolder.add(lastNameDescription,c);

        c.gridx = 1;
        c.gridy = 1;
        nameDetailsHolder.add(lastName,c);

        c.gridx = 0;
        c.gridy = 2;
        nameDetailsHolder.add(firstNameDescription,c);

        c.gridx = 1;
        c.gridy = 2;
        nameDetailsHolder.add(firstName,c);

        c.gridx = 0;
        c.gridy = 3;
        nameDetailsHolder.add(phoneDescription,c);

        c.gridx = 1;
        c.gridy = 3;
        nameDetailsHolder.add(phone,c);

        c.gridx = 0;
        c.gridy = 4;
        nameDetailsHolder.add(emailDescription,c);

        c.gridx = 1;
        c.gridy = 4;
        nameDetailsHolder.add(email,c);

        c.gridx = 0;
        c.gridy = 1;
        pageStartHolder.add(nameDetailsHolder,c);

        paymentFrame.add(pageStartHolder, BorderLayout.PAGE_START);
    }

    private MaskFormatter createFormatter() {
        MaskFormatter format = new MaskFormatter();
        try{
            format.setMask("*****************************");
            format.setInvalidCharacters(",/#';][{}~@:?><`=¬!\"£$%^&*()+_;€\\|");
            format.setValidCharacters("qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM.-\\s");
            format.setPlaceholder(" ");
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return format;
    }
}
