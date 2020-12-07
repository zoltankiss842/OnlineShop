package tools;

import entity.County;
import entity.CountyList;
import entity.Product;

import java.io.BufferedReader;
import java.io.FileReader;
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
                    temp = new Product(values[0], values[1], Double.parseDouble(values[2]), Integer.parseInt(values[3]));
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
}
