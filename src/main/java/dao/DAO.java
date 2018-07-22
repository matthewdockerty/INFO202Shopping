package dao;

import domain.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author docma436
 */
public class DAO {

    private static List<Product> products = new ArrayList<>();
    
    public static void saveProduct(Product product) {
        products.add(product);
    }
    
    public static List<Product> getProducts() {
        return products;
    }
}
