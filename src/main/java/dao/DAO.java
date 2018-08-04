/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.util.Collection;

public interface DAO {

    void addProductToCategory(String category, Product product);

    void deleteProduct(Product product);

    Collection<String> getCategories();

    Product getProductByID(String productID);

    Collection<Product> getProducts();

    Collection<Product> getProductsByCategory(String category);

    void removeProductFromCategory(String category, Product product);

    void saveProduct(Product product);
    
}
