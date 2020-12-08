package tools;

import entity.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileIO {
    
    private String line = "";
    private String cvsSplitBy = ",";
    private String txtSplit = "\t";

    public ArrayList<Product> readFromFile(ArrayList<Product> productList, String filePath){
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {
                if(line.equals("productName,category,price,warehouseQuantity")) {
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

    public boolean writeTransactionToTxt(String[] data, Cart cart){
        try{
            FileWriter fw = new FileWriter(data[0] + ".txt");

            BufferedWriter writer = new BufferedWriter(fw);

            writer.write("---- Rendelési adatok ---\n");
            writer.write("Rendelési azonosító: " + data[0] + "\n");
            writer.write("Fizetett összeg: " + data[1] + "Ft\n");
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
                    s = s + String.valueOf(data[16].charAt(i));
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
                String out = String.format(formatString, item.getProduct().getProductName(), String.valueOf(item.getProduct().getAmountInCart()));
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
}
