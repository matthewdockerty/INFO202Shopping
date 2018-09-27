package web;

import dao.DAOException;
import dao.SaleDAO;
import domain.Sale;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
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
                        Email email = new SimpleEmail();
                        email.setHostName("localhost");
                        email.setSmtpPort(2525);
                        email.setFrom("orders@doohickiesandwidgets.com");
                        email.setSubject("Your Order");
                        email.setMsg("Thank you for your order.");
                        email.addTo(sale.getCustomer().getEmailAddress());
                        email.send();
                    } catch (EmailException e) {
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
