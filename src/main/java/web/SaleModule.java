package web;

import dao.DAOException;
import dao.SaleDAO;
import domain.Sale;
import domain.SaleItem;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.concurrent.CompletableFuture;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
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

                CompletableFuture.runAsync(() -> {
                    try {
                        new Emails().sendOrderEmail(sale);
                    } catch (MalformedURLException | EmailException e) {
                        System.err.println("Unable to send email: " + e.getMessage());
                    }
                });

            } catch (DAOException e) {
                rsp.status(Status.FORBIDDEN);
                rsp.send(e.getMessage());
            }
        });

    }

}
