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
public class ProductDAOCollections implements ProductDAO {

    private static final Map<String, Product> products = new HashMap<>();
    private static final Multimap<String, Product> productCategories = HashMultimap.create();
    
    @Override
    public void saveProduct(Product product) {
        products.put(product.getProductID(), product);
        addProductToCategory(product.getCategory(), product);
    }
    
    /**
     *
     * @param category
     * @param product
     */
    @Override
    public void addProductToCategory(String category, Product product) {
        productCategories.put(product.getCategory(), product);
    }
    
    @Override
    public void removeProductFromCategory(String category, Product product) {
        productCategories.remove(category, product);
    }
    
    @Override
    public Collection<Product> getProducts() {
        return products.values();
    }
    
    @Override
    public Collection<String> getCategories() {
        Collection<String> categories = new HashSet<>();
        for (Product product : products.values()) {
            categories.add(product.getCategory());
        }
        
        return categories;
    }
    
    @Override
    public void deleteProduct(Product product) {
        products.remove(product.getProductID());
        productCategories.remove(product.getCategory(), product);
    }
    
    @Override
    public Product getProductByID(String productID) {
        return products.get(productID);
    }
    
    @Override
    public Collection<Product> getProductsByCategory(String category) {
        return productCategories.get(category);
    }
}
