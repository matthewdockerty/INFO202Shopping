package dao;

import domain.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author docma436
 */
public class DAOPersistent implements DAO {

    
    private String dbUrl = "jdbc:h2:tcp://localhost:9017/project;IFEXISTS=TRUE";
    
    public DAOPersistent() {}
    
    public DAOPersistent(String url) {
        dbUrl = url;
    }
    
    @Override
    public void saveProduct(Product product) {
        String sql = "MERGE INTO Product (Product_ID, Name, Description, Category, List_Price, Quantity_In_Stock) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (
            Connection connection = JdbcConnection.getConnection(dbUrl);
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, product.getProductID());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getCategory());
            statement.setBigDecimal(5, product.getListPrice());
            statement.setInt(6, product.getQuantityInStock());
            
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteProduct(Product product) {
        String sql = "DELETE FROM Product WHERE Product_ID = ? ";
        
        try (
            Connection connection = JdbcConnection.getConnection(dbUrl);
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, product.getProductID());
            
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Collection<String> getCategories() {
        String sql = "SELECT DISTINCT Category FROM Product ORDER BY Category";
        
        try (
            Connection connection = JdbcConnection.getConnection(dbUrl);
            PreparedStatement statement = connection.prepareStatement(sql);    
        ) {
            ResultSet rs = statement.executeQuery();
            
            List<String> categories = new ArrayList<>();
            
            while (rs.next()) {
                String category = rs.getString("Category");
                categories.add(category);
            }
            
            return categories;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Product getProductByID(String productID) {
        String sql = "SELECT * FROM Product WHERE Product_ID = ?";
            
        try (
            Connection connection = JdbcConnection.getConnection(dbUrl);
            PreparedStatement statement = connection.prepareStatement(sql);    
        ) {
            statement.setString(1, productID);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                Product product = new Product();

                product.setProductID(rs.getString("Product_ID"));
                product.setName(rs.getString("Name"));
                product.setDescription(rs.getString("Description"));
                product.setCategory(rs.getString("Category"));
                product.setListPrice(rs.getBigDecimal("List_Price"));
                product.setQuantityInStock(rs.getInt("Quantity_In_Stock"));
                
                return product;
            }
            return null;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Collection<Product> getProducts() {
        String sql = "SELECT * FROM Product ORDER BY Product_ID";
        
        try (
            Connection connection = JdbcConnection.getConnection(dbUrl);
            PreparedStatement statement = connection.prepareStatement(sql);    
        ) {
            ResultSet rs = statement.executeQuery();
            List<Product> products = new ArrayList<>();
            
            while (rs.next()) {
                Product product = new Product();

                product.setProductID(rs.getString("Product_ID"));
                product.setName(rs.getString("Name"));
                product.setDescription(rs.getString("Description"));
                product.setCategory(rs.getString("Category"));
                product.setListPrice(rs.getBigDecimal("List_Price"));
                product.setQuantityInStock(rs.getInt("Quantity_In_Stock"));

                products.add(product);
            }
            
            return products;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Collection<Product> getProductsByCategory(String category) {
        String sql = "SELECT * FROM Product WHERE Category = ? ORDER BY Product_ID";
        
        try (
            Connection connection = JdbcConnection.getConnection(dbUrl);
            PreparedStatement statement = connection.prepareStatement(sql);    
        ) {
            statement.setString(1, category);
            ResultSet rs = statement.executeQuery();
            
            List<Product> products = new ArrayList<>();
            
            while (rs.next()) {
                Product product = new Product();

                product.setProductID(rs.getString("Product_ID"));
                product.setName(rs.getString("Name"));
                product.setDescription(rs.getString("Description"));
                product.setCategory(rs.getString("Category"));
                product.setListPrice(rs.getBigDecimal("List_Price"));
                product.setQuantityInStock(rs.getInt("Quantity_In_Stock"));

                products.add(product);
            }
            
            return products;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public void addProductToCategory(String category, Product product) {
        // Only required in collections DAO.
    }

    @Override
    public void removeProductFromCategory(String category, Product product) {
        // Only required in collections DAO.
    }
    
}
