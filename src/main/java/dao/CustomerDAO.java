package dao;

import domain.Customer;

/**
 *
 * @author docma436
 */
public interface CustomerDAO {
    
    public void save(Customer customer);
    public Customer getCustomer(String username);
    public Boolean validateCredentials(String username, String password);
    
}
