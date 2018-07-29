package dao;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
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
    private static Multimap<String, Product> productCategories = HashMultimap.create();
    
    public void saveProduct(Product product) {
        products.put(product.getProductID(), product);
        productCategories.put(product.getCategory(), product);
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
        products.remove(product.getProductID());
        productCategories.remove(product.getCategory(), product);
    }
    
    public Product getProductByID(String productID) {
        return products.get(productID);
    }
    
    public Collection<Product> getProductsByCategory(String category) {
        return productCategories.get(category);
    }
}
