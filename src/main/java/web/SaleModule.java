package web;

import dao.CustomerDAO;
import dao.SaleDAO;
import domain.Customer;
import domain.Sale;
import org.jooby.Err;
import org.jooby.Jooby;
import org.jooby.Status;

/**
 *
 * @author docma436
 */
public class SaleModule extends Jooby {

    public SaleModule(SaleDAO saleDAO) {
        post("/api/sales", (req, rsp) -> {
            Sale sale = req.body().to(Sale.class);
            System.out.println(sale);
            saleDAO.save(sale);
            rsp.status(Status.CREATED);
        });
        
        
    }

}
