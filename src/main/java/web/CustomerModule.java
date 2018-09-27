package web;

import dao.CustomerDAO;
import dao.DAOException;
import domain.Customer;
import java.sql.SQLException;
import org.h2.api.ErrorCode;
import org.jooby.Err;
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
            Customer customer = customerDAO.getCustomer(username);

            if (customer == null) {
                throw new Err(Status.NOT_FOUND);
            }

            customer.setPassword(null); // don't send password!
            return customer;
        });

        post("/api/register", (req, rsp) -> {
            try {
                Customer customer = req.body().to(Customer.class);

                customer.setPassword(customerDAO.hashPassword(customer.getPassword()));

                customerDAO.save(customer);
                rsp.status(Status.CREATED);
            } catch (DAOException e) {
                SQLException ex = (SQLException) e.getCause();
                rsp.status(Status.FORBIDDEN);
                rsp.send(ex.getErrorCode() == ErrorCode.DUPLICATE_KEY_1 ? "Username or email address already in use." : e.getMessage());
            }
        });

    }

}
