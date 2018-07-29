package dao;

import domain.Product;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author docma436
 */
public class DAO {

    private static Map<String, Product> products = new HashMap<>();
    
    public void saveProduct(Product product) {
        products.put(product.getProductID(), product);
    }
    
    public Collection<Product> getProducts() {
        return products.values();
    }
    
    public Collection<String> getCategories() {
        Collection<String> categories = new HashSet<>();
        for (Product product : products.values()) {
            categories.add(product.getCategory());
        }
        
        return categories;
    }
    
    public void deleteProduct(Product product) {
        products.remove(product);
    }
    
    public Product getProductByID(String productID) {
        return products.get(productID);
    }
}
