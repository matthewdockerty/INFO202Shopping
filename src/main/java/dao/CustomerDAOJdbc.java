package dao;

import domain.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author docma436
 */
public class CustomerDAOJdbc implements CustomerDAO {

    private String dbUrl = "jdbc:h2:tcp://localhost:9017/project;IFEXISTS=TRUE";

    public CustomerDAOJdbc() {
    }

    public CustomerDAOJdbc(String url) {
        dbUrl = url;
    }

    @Override
    public void save(Customer customer) {
        String sql = "INSERT INTO Customer (Username, First_Name, Surname, Password, Email_Address, Shipping_Address, Credit_Card_Details) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = JdbcConnection.getConnection(dbUrl);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, customer.getUsername());
            statement.setString(2, customer.getFirstName());
            statement.setString(3, customer.getSurname());
            statement.setString(4, customer.getPassword());
            statement.setString(5, customer.getEmailAddress());
            statement.setString(6, customer.getShippingAddress());
            statement.setString(7, customer.getCreditCardDetails());

            statement.executeUpdate();

            System.out.println("Saved customer: " + customer);
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Customer getCustomer(String username) {
        String sql = "SELECT * FROM Customer WHERE Username = ?";

        try (
                Connection connection = JdbcConnection.getConnection(dbUrl);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();

                customer.setPersonID(rs.getInt("Person_ID"));
                customer.setUsername(rs.getString("Username"));
                customer.setFirstName(rs.getString("First_Name"));
                customer.setSurname(rs.getString("Surname"));
                customer.setPassword(rs.getString("Password"));
                customer.setEmailAddress(rs.getString("Email_Address"));
                customer.setShippingAddress(rs.getString("Shipping_Address"));
                customer.setCreditCardDetails(rs.getString("Credit_Card_Details"));

                return customer;
            }
            return null;
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    public String hashPassword(String password) {
        if (password.length() > 32 || password.length() < 6) {
            throw new DAOException("Password length must be between 6 and 32 characters.");
        }

        String salt = BCrypt.gensalt();
        
        return BCrypt.hashpw(password, salt);
    }

    @Override
    public Boolean validateCredentials(String username, String password) {
        Customer customer = getCustomer(username);

        return customer != null && BCrypt.checkpw(password, customer.getPassword());
    }

}
