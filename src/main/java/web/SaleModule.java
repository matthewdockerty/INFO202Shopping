package web;

import dao.DAOException;
import dao.SaleDAO;
import domain.Sale;
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

            try {
                saleDAO.save(sale);
                rsp.status(Status.CREATED);
            } catch (DAOException e) {
                rsp.status(Status.FORBIDDEN);
                rsp.send(e.getMessage());
            }
        });
        
        
    }

}
