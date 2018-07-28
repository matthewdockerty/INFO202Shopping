package dao;

import domain.Product;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author docma436
 */
public class DAO {

    private static Collection<Product> products = new HashSet<>();
    
    public void saveProduct(Product product) {
        products.add(product);
    }
    
    public Collection<Product> getProducts() {
        return products;
    }
    
    public Collection<String> getCategories() {
        Collection<String> categories = new HashSet<>();
        for (Product product : products) {
            categories.add(product.getCategory());
        }
        
        return categories;
    }
}
