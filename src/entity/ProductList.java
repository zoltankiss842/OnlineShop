package entity;

import tools.FileIO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ProductList {

    private ArrayList<Product> productList;
    private ArrayList<String> categoryList;

    public ProductList(String filePath){
        FileIO io = new FileIO();
        productList = new ArrayList<>();
        io.readFromFile(productList, filePath);
        updateCategoryList();
    }

    private void updateCategoryList(){
        Set<String> categories = new HashSet<>();
        for(Product p : productList){
            categories.add(p.getCategory());
        }

        categoryList = new ArrayList<>();
        categoryList.addAll(categories);

        Collections.sort(categoryList);
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public ArrayList<String> getCategoryList() {
        return categoryList;
    }

    public void printProductList(){
        printOutProductList();
    }

    public void printCategoryList(){
        printOutCategoryList();
    }

    private void printOutProductList(){
        for(Product p : productList){
            System.out.println(p.toString());
        }
    }

    private void printOutCategoryList(){
        for(String c : categoryList){
            System.out.println(c);
        }
    }

    public void printIfOnWishList(){
        for(Product p : productList){
            if(p.isOnWishList()){
                System.out.println(p.toString());
            }
        }
    }

}
