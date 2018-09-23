package dao;

import domain.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author docma436
 */
public class ProductDAOJdbc implements ProductDAO {

    private String dbUrl = "jdbc:h2:tcp://localhost:9017/project;IFEXISTS=TRUE";

    public ProductDAOJdbc() {
    }

    public ProductDAOJdbc(String url) {
        dbUrl = url;
    }

    @Override
    public void saveProduct(Product product, byte[] productImage) {
        String sqlProduct = "MERGE INTO Product (Product_ID, Name, Description, Category, List_Price, Quantity_In_Stock) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlImage = "INSERT INTO Product_Image (Product_ID, Image) VALUES (?, ?)";

        Connection connection = JdbcConnection.getConnection(dbUrl);

        try {
            try (
                    PreparedStatement statementProduct = connection.prepareStatement(sqlProduct);
                    PreparedStatement statementImage = connection.prepareStatement(sqlImage);) {

                connection.setAutoCommit(false);

                statementProduct.setString(1, product.getProductID());
                statementProduct.setString(2, product.getName());
                statementProduct.setString(3, product.getDescription());
                statementProduct.setString(4, product.getCategory());
                statementProduct.setBigDecimal(5, product.getListPrice());
                statementProduct.setInt(6, product.getQuantityInStock());

                statementProduct.executeUpdate();

                statementImage.setString(1, product.getProductID());
                statementImage.setBytes(2, productImage);

                statementImage.executeUpdate();

                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SaleDAOJdbc.class.getName()).log(Level.SEVERE, null, ex);

            try {
                // something went wrong so rollback
                connection.rollback();

                // turn auto-commit back on
                connection.setAutoCommit(true);

                // and throw an exception to tell the user something bad happened
                throw new DAOException(ex.getMessage(), ex);
            } catch (SQLException ex1) {
                throw new DAOException(ex1.getMessage(), ex1);
            }

        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(SaleDAOJdbc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void deleteProduct(Product product) {
        String sqlProduct = "DELETE FROM Product WHERE Product_ID = ? ";
        String sqlImage = "DELETE FROM Product_Image WHERE Product_ID = ?";
        Connection connection = JdbcConnection.getConnection(dbUrl);

        try {

            try (
                    PreparedStatement statementProduct = connection.prepareStatement(sqlProduct);
                    PreparedStatement statementImage = connection.prepareStatement(sqlImage);) {

                connection.setAutoCommit(false);

                statementImage.setString(1, product.getProductID());
                statementImage.executeUpdate();
                
                statementProduct.setString(1, product.getProductID());
                statementProduct.executeUpdate();
                
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(SaleDAOJdbc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Collection<String> getCategories() {
        String sql = "SELECT DISTINCT Category FROM Product ORDER BY Category";

        try (
                Connection connection = JdbcConnection.getConnection(dbUrl);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            ResultSet rs = statement.executeQuery();

            List<String> categories = new ArrayList<>();

            while (rs.next()) {
                String category = rs.getString("Category");
                categories.add(category);
            }

            return categories;
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Product getProductByID(String productID) {
        String sql = "SELECT * FROM Product WHERE Product_ID = ?";

        try (
                Connection connection = JdbcConnection.getConnection(dbUrl);
                PreparedStatement statement = connection.prepareStatement(sql);) {
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
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Product> getProducts() {
        String sql = "SELECT * FROM Product ORDER BY Product_ID";

        try (
                Connection connection = JdbcConnection.getConnection(dbUrl);
                PreparedStatement statement = connection.prepareStatement(sql);) {
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
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Product> getProductsByCategory(String category) {
        String sql = "SELECT * FROM Product WHERE Category = ? ORDER BY Product_ID";

        try (
                Connection connection = JdbcConnection.getConnection(dbUrl);
                PreparedStatement statement = connection.prepareStatement(sql);) {
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
            throw new DAOException(ex.getMessage(), ex);
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

    @Override
    public byte[] getProductImage(String productID) {
        String sql = "SELECT * FROM Product_Image WHERE Product_ID = ?";

        try (
                Connection connection = JdbcConnection.getConnection(dbUrl);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, productID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getBytes("Image");
            }
            return null;
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

}
