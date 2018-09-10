package web;

import dao.CustomerDAO;
import domain.Customer;
import org.jooby.Jooby;
import org.jooby.Status;

/**
 *
 * @author docma436
 */
public class CustomerModule extends Jooby {

    public CustomerModule(CustomerDAO customerDAO) {
        get("/api/customers/:username", (req) -> {
            String username = req.param("username").value();
            return customerDAO.getCustomer(username);
        });
        
        post("/api/register", (req, rsp) -> {
            Customer customer = req.body().to(Customer.class);
            customerDAO.save(customer);
            rsp.status(Status.CREATED);
        });
        
        
    }

}
