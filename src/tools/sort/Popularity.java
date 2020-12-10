package tools.sort;

import entity.Product;

import java.util.Comparator;

/**
 * Implementation for popularity sorting
 * Ascending and Descending
 */
public class Popularity implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        if(o1.getPopularity() > o2.getPopularity()){
            return -1;
        }
        if(o1.getPopularity() < o2.getPopularity()){
            return 1;
        }

        return 0;
    }

    @Override
    public Comparator<Product> reversed() {
        Comparator<Product> reverse = new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if(o1.getPopularity() > o2.getPopularity()){
                    return -1;
                }
                if(o1.getPopularity() < o2.getPopularity()){
                    return 1;
                }

                return 0;
            }
        };

        return reverse;
    }
}
