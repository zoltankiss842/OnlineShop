package tools;

import entity.*;
import frame.cart.CartItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Class for dealing with file writing and reading
 */
public class FileIO {
    
    private String line = "";
    private final String cvsSplitBy = ";";
    private final String txtSplit = "\t";

    /**
     * Reading a CVS file which contains the products
     * @param productList       an array list to be read into
     * @param filePath          location of the file
     * @return                  an arraylist containing products
     */
    public ArrayList<Product> readFromFile(ArrayList<Product> productList, String filePath){
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {
                if(line.equals("productName;category;price;warehouseQuantity")) {
                    continue;
                }

                String[] values = line.split(cvsSplitBy);
                Product temp = null;
                try{
                    temp = new Product(values[0], values[1], (int)Double.parseDouble(values[2]), Integer.parseInt(values[3]));
                }
                catch(NumberFormatException format){
                    System.err.println("Format for " + line + " is wrong. Skipping!");
                }

                if(temp!=null){
                    productList.add(temp);
                }
            }

            br.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return productList;
    }

    /**
     * Reading a txt file which contains the counties
     * @param filePath          location of the file
     * @return                  an arraylist containing counties
     */
    public ArrayList<County> readFromTxtFile(String filePath){
        ArrayList<County> counties = null;
        try{
            counties = new ArrayList<>();

            BufferedReader br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {

                String[] values = line.split(txtSplit);
                County temp = null;
                try{
                    temp = new County(values[1], values[0]);
                }
                catch(NumberFormatException format){
                    System.err.println("Format for " + line + " is wrong. Skipping!");
                }

                if(temp!=null){
                    counties.add(temp);
                }
            }

            br.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return counties;
    }

    /**
     * Reading a txt file which contains the cities
     * @param list              a non-null county list
     * @param filePath          location of the file
     */
    public void readFromTxtFile(CountyList list, String filePath){
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {

                String[] values = line.split(txtSplit);

                for(County c : list.getCountyList()){
                    if(c.getCountyAbbreviation().equals(values[2])){
                        c.getCities().add(values[1]);
                    }
                }

            }

            br.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Reading a txt file which contains countries
     * @param filePath          location of the file
     * @return                  array of string containing countries
     */
    public String[] readFromTxtFileCountries(String filePath){
        String[] temp;
        ArrayList<String> countries = new ArrayList<>();
        try{

            BufferedReader br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {

                String[] values = line.split(txtSplit);
                countries.add(values[0]);


            }

            br.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        temp = new String[countries.size()];
        int flag = 0;
        for(String s : countries){
            temp[flag++] = s;
        }

        return temp;
    }

    /**
     * Creating a receipt for purchase
     * @param data      data from user input (name, address, bankcard, etc.)
     * @param cart      products that were purchased
     * @return          if the writing correctly terminated/happened
     */
    public boolean writeTransactionToTxt(String[] data, Cart cart){
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();

            String[] id = data[0].split("-");

            FileWriter fw = new FileWriter(data[3] + " " + data[4] + " - " + id[0] + ".txt");

            BufferedWriter writer = new BufferedWriter(fw);

            writer.write("---- Rendelési adatok ---\n");
            writer.write("Rendelési azonosító: " + data[0] + "\n");
            writer.write("Fizetett összeg: " + data[1] + "Ft\n");
            writer.write("Rendelés dátuma: " + dtf.format(now) + "\n");
            writer.write("Megrendelő neve: " + data[2] + " " + data[3] + " " + data[4] + "\n");
            writer.write("Megrendelő telefonszáma: " + data[5] + "\n");
            writer.write("Megrendelő emailje: " + data[6] + "\n");
            writer.write("Megrendelő címe: " + data[12] + " " + data[11] + " " + data[7] + " " + data[8] + " " + data[9] + " " + data[10] + " " + data[13]  + "\n");
            writer.write("Megjegyzés a rendeléshez: " + data[14]   + "\n");
            writer.write("\n");
            writer.write("\n");

            writer.write("---- Fizetési adatok ---\n");
            writer.write("Fizetési mód: Bankkártya\n");
            writer.write("Bankártya tulajdonos: " + data[15] + "\n");

            String s = "";
            for(int i = 0; i < data[16].length(); ++i){
                if(i < data[16].length()-4){
                    s = s + "X";
                }
                else{
                    s = s + data[16].charAt(i);
                }
            }

            writer.write("Bankártyaszám: " + s + "\n");

            writer.write("\n");
            writer.write("\n");

            int maxLength = 0;
            for(CartItem item : cart.getCart()){
                if(item.getProduct().getProductName().length() > maxLength){
                    maxLength = item.getProduct().getProductName().length();
                }
            }


            String formatString = "%-50s%s";
            writer.write("---- Rendelt termékek ---\n");
            for(CartItem item : cart.getCart()){
                String out = String.format(formatString, item.getProduct().getProductName(), item.getProduct().getAmountInCart());
                out = out + "db\t\t\t\t" + item.getProduct().getAmountInCart()*item.getProduct().getPrice() + "Ft\n";
                writer.write(out);
            }

            writer.write("\n");
            writer.write("\n");

            writer.write("Köszönjük a vásárlást!");


            writer.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }


        return true;
    }

    /**
     * Creating a cart and wishlist txt
     * @param list      list of products
     * @return          if the writing correctly terminated/happened
     */
    public boolean writeProductToTxt(ProductList list){
        try{
            String welcomeCartString = "Az Ön kosarának tartalma:\n";
            String emptyCartString = "Az Ön kosara üres.\n";

            String welcomeWishListString = "Az Ön kívánságlistája:\n";
            String emptyWishListString = "Az Ön kívánságlistája üres.\n";

            FileWriter fwCart = new FileWriter("kosar.txt", false);
            FileWriter fwWishList = new FileWriter("kivansaglista.txt", false);

            BufferedWriter writer = new BufferedWriter(fwCart);
            BufferedWriter writer2 = new BufferedWriter(fwWishList);

            StringBuilder toWriteCart = new StringBuilder();
            StringBuilder toWriteWishList = new StringBuilder();

            toWriteCart.append(welcomeCartString);
            toWriteWishList.append(welcomeWishListString);

            int cartItems = 0;
            int wishItems = 0;

            for(Product item : list.getProductList()){
                if(item.isOnWishList() && item.isInCart()){
                    String temp = String.format("%-30s%-15s%-15s%-15s%-15s",
                            item.getProductName(),
                            item.getCategory(),
                            item.getPrice() + "Ft",
                            item.getAmountInCart() + "db",
                            item.getAmountInCart()*item.getPrice() + "Ft");

                    toWriteCart.append(temp + "\n");

                    temp = String.format("%-30s%-15s%-15s",
                            item.getProductName(),
                            item.getCategory(),
                            item.getPrice() + "Ft");

                    toWriteWishList.append(temp + "\n");


                    cartItems++;
                    wishItems++;
                }
                else if(item.isOnWishList() && !item.isInCart()){
                    String temp = String.format("%-30s%-15s%-15s",
                            item.getProductName(),
                            item.getCategory(),
                            item.getPrice() + "Ft");

                    toWriteWishList.append(temp + "\n");

                    wishItems++;
                }
                else if(!item.isOnWishList() && item.isInCart()){
                    String temp = String.format("%-30s%-15s%-15s%-15s%-15s",
                            item.getProductName(),
                            item.getCategory(),
                            item.getPrice() + "Ft",
                            item.getAmountInCart() + "db",
                            item.getAmountInCart()*item.getPrice() + "Ft");

                    toWriteCart.append(temp + "\n");

                    cartItems++;
                }

            }

            if(cartItems == 0){
                writer.write(welcomeCartString.concat(emptyCartString));
            }
            else{
                writer.write(toWriteCart.toString());
            }

            if(wishItems == 0){
                writer2.write(welcomeWishListString.concat(emptyWishListString));
            }
            else{
                writer2.write(toWriteWishList.toString());
            }


            writer.close();
            writer2.close();

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }


        return true;
    }

}
