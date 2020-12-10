package tools.sort;

import entity.Product;

import java.util.Comparator;

/**
 * Implementation for Alphabetical sorting
 * Ascending and Descending
 */
public class Alphabetical implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        return o1.getSanitizedProductName().compareTo(o2.getSanitizedProductName());
    }

    @Override
    public Comparator<Product> reversed() {
        Comparator<Product> reverse = new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                int result = o1.getSanitizedProductName().compareTo(o2.getSanitizedProductName());

                if(result > 0){
                    return -1;
                }

                if(result < 0){
                    return 1;
                }

                return 0;
            }
        };

        return reverse;
    }
}
