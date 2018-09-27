package dao;

import domain.Customer;
import web.auth.CredentialsValidator;

/**
 *
 * @author docma436
 */
public interface CustomerDAO extends CredentialsValidator {
    
    public void save(Customer customer);
    public Customer getCustomer(String username);
    public String hashPassword(String password);

}
