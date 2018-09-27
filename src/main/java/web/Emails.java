package web;

import domain.Sale;
import domain.SaleItem;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

/**
 *
 * @author docma436
 */
class Emails {

    public void sendOrderEmail(Sale sale) throws MalformedURLException, EmailException {
        DecimalFormat df = new DecimalFormat("#.00");

        // load your HTML email template
        String htmlEmailTemplate = "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\">"
                + "<div class=\"container mt-5\">"
                + "     <table class=\"table cart\">"
                + "                <h1>Hi " + sale.getCustomer().getFirstName() + "!</h1>"
                + "                <p>Your recent order from the Doohickies and Widgets Shop has been placed and is currently being processed.</p>"
                + "                <p>Your order receipt is below.</p>"
                + "                <thead>"
                + "                    <tr>"
                + "                        <th scope=\"col\"></th>"
                + "                        <th scope=\"col\">Product Name</th>"
                + "                        <th scope=\"col\">Price</th>"
                + "                        <th scope=\"col\">Quantity</th>"
                + "                        <th scope=\"col\">Total</th>"
                + "                    </tr>"
                + "                </thead>"
                + "                <tbody>";

        for (SaleItem item : sale.getItems()) {
            htmlEmailTemplate += "                    <tr>"
                    + "                        <th scope=\"row\"><img width=\"100\" src=\"http://localhost:8080/images/product/" + item.getProduct().getProductID() + "\"></th>"
                    + "                        <td>" + item.getProduct().getName() + "</td>"
                    + "                        <td>$" + df.format(item.getSalePrice()) + "</td>"
                    + "                        <td>" + item.getQuantityPurchased() + "</td>"
                    + "                        <td>$" + df.format(item.getItemTotal()) + "</td>"
                    + "                    </tr>";
        }

        htmlEmailTemplate += "<tr>"
                + "                        <td></td>"
                + "                        <td></td>"
                + "                        <td></td>"
                + "                        <th scope=\"row\">Total</th>"
                + "                        <td>$" + df.format(sale.getTotal()) + "</td>"
                + "                    </tr>"
                + "                </tbody>"
                + "            </table>"
                + "            <br>"
                + "            <p>Be sure to contact us if you have any issues or queries.</p>"
                + "        </div>";

        // define you base URL to resolve relative resource locations
        URL url = new URL("http://localhost:8080");

        // create the email message
        ImageHtmlEmail email = new ImageHtmlEmail();
        email.setDataSourceResolver(new DataSourceUrlResolver(url));
        email.setHostName("localhost");
        email.setSmtpPort(2525);
        email.setFrom("orders@doohickiesandwidgets.com");

        email.setSubject("Order Details - Doohickies and Widgets Shop");
        email.addTo(sale.getCustomer().getEmailAddress(), sale.getCustomer().getFirstName() + " " + sale.getCustomer().getSurname());

        // set the html message
        email.setHtmlMsg(htmlEmailTemplate);

        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");

        // send the email
        email.send();
    }

}
