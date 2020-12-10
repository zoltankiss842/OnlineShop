package entity;

import tools.FileIO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This entity represents a list of products.
 *
 * @see Product
 */
public class ProductList {

    // Fields for ProductList class
    private final ArrayList<Product> productList;       // The list of products
    private ArrayList<String> categoryList;             // The list of the products' categories

    // Constructor with parameters
    public ProductList(ArrayList<Product> productList, ArrayList<String> categoryList) {
        this.productList = productList;
        this.categoryList = categoryList;
    }

    // Constructor with file path
    public ProductList(String filePath){
        productList = new ArrayList<>();
        new FileIO().readFromFile(productList, filePath);
        updateCategoryList();
    }

    private void updateCategoryList(){
        if(productList != null && !productList.isEmpty()){
            Set<String> categories = new HashSet<>();   // Set allows us to have only one instance of everything, that's what sets do
            for(Product p : productList){
                categories.add(p.getCategory());
            }

            categoryList = new ArrayList<>();
            categoryList.addAll(categories);            // Converting the set into an arraylist

            Collections.sort(categoryList);             // Sorting the arraylist alphabetically
        }
        else{
            categoryList = new ArrayList<>();
        }

    }

    // Setters

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public ArrayList<String> getCategoryList() {
        return categoryList;
    }

}
