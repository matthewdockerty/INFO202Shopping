package dao;

import dao.DAOException;
import dao.SaleDAO;
import domain.Customer;
import domain.Product;
import domain.Sale;
import domain.SaleItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleDAOJdbc implements SaleDAO {

    private static final String url = "jdbc:h2:tcp://localhost:9017/project;IFEXISTS=TRUE";

    @Override
    public void save(Sale sale) {

        Connection con = JdbcConnection.getConnection(url);
        try {
            try (
                    PreparedStatement insertSaleStmt = con.prepareStatement(
                            "INSERT INTO Sale (Date, Status, Person_ID) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement insertSaleItemStmt = con.prepareStatement(
                            "INSERT INTO Sale_Item (Quantity_Purchased, Sale_Price, Product_ID, Sale_ID) VALUES (?, ?, ?, ?)");
                    PreparedStatement updateProductStmt = con.prepareStatement(
                            "UPDATE Product SET Quantity_In_Stock = ? WHERE Product_ID = ?");) {
                PreparedStatement selectProductStmt = con.prepareStatement(
                        "SELECT Quantity_In_Stock FROM Product WHERE Product_ID = ?");

                // Since saving and sale involves multiple statements across
                // multiple tables we need to control the transaction ourselves
                // to ensure our DB remains consistent.
                //
                // Turn off auto-commit which effectively starts a new transaction.
                con.setAutoCommit(false);

                Customer customer = sale.getCustomer();

                // #### save the sale ### //
                // add a date to the sale if one doesn't already exist
                if (sale.getDate() == null) {
                    sale.setDate(new Date());
                }
                
                sale.setStatus('P'); // Set status to 'P' for 'processing'.

                // convert sale date into to java.sql.Timestamp
                Date date = sale.getDate();
                Timestamp timestamp = new Timestamp(date.getTime());

                insertSaleStmt.setTimestamp(1, timestamp);
                insertSaleStmt.setString(2, sale.getStatus() + "");
                insertSaleStmt.setInt(3, customer.getPersonID());

                insertSaleStmt.executeUpdate();

                // get the auto-generated sale ID from the database
                ResultSet rs = insertSaleStmt.getGeneratedKeys();

                Integer saleId = null;

                if (rs.next()) {
                    saleId = rs.getInt(1);
                } else {
                    throw new DAOException("Problem getting generated Sale ID");
                }

                Collection<SaleItem> items = sale.getItems();

                for (SaleItem item : items) {
                    Product product = item.getProduct();

                    /* 
                     * Check that the product is in stock in case someone 
                     * else has purchased it during this customer's checkout process!
                     */
                    selectProductStmt.setString(1, product.getProductID());
                    rs = selectProductStmt.executeQuery();

                    int quantityInStock = 0;
                    if (rs.next()) {
                        quantityInStock = rs.getInt(1);
                        System.out.println(quantityInStock + " !");
                    } else {
                        throw new DAOException("Problem checking quantity in stock.");
                    }

                    if (quantityInStock < item.getQuantityPurchased()) {
                        throw new DAOException("Insufficient " + product.getName() + " in stock to fulfil order. Only " + quantityInStock + " remaining.");
                    }

                    insertSaleItemStmt.setInt(1, item.getQuantityPurchased());
                    insertSaleItemStmt.setBigDecimal(2, item.getSalePrice());
                    insertSaleItemStmt.setString(3, product.getProductID());
                    insertSaleItemStmt.setInt(4, saleId);

                    insertSaleItemStmt.executeUpdate();

                    updateProductStmt.setInt(1, quantityInStock - item.getQuantityPurchased());
                    updateProductStmt.setString(2, product.getProductID());

                    updateProductStmt.executeUpdate();
                }

                // commit the transaction
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {

            Logger.getLogger(SaleDAOJdbc.class.getName()).log(Level.SEVERE, null, ex);

            try {
                // something went wrong so rollback
                con.rollback();

                // turn auto-commit back on
                con.setAutoCommit(true);

                // and throw an exception to tell the user something bad happened
                throw new DAOException(ex.getMessage(), ex);
            } catch (SQLException ex1) {
                throw new DAOException(ex1.getMessage(), ex1);
            }

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(SaleDAOJdbc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
