package tools;

import entity.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FileIO {
    
    private String line = "";
    private String cvsSplitBy = ",";

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
}
