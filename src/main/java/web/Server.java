package web;

import dao.CustomerDAO;
import dao.CustomerDAOCollections;
import dao.CustomerDAOJdbc;
import dao.ProductDAO;
import dao.ProductDAOJdbc;
import dao.SaleDAO;
import dao.SaleDAOJdbc;
import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.json.Gzon;

/**
 *
 * @author docma436
 */
public class Server extends Jooby {

    private ProductDAO productDAO;
    private CustomerDAO customerDAO;
    private SaleDAO saleDAO;
    
    public Server() {
        productDAO = new ProductDAOJdbc();
        customerDAO = new CustomerDAOJdbc();
        saleDAO = new SaleDAOJdbc();
        
        port(8080);

        use(new Gzon());
        
        use(new ProductModule(productDAO));
        use(new CustomerModule(customerDAO));
        use(new SaleModule(saleDAO));
        
        use(new AssetModule());
    }

    public static void main(String[] args) throws Exception {
        System.out.println("\nStarting Server.");
        Server server = new Server();

        CompletableFuture.runAsync(() -> {
            server.start();
        });

        server.onStarted(() -> {
            System.out.println("\nPress Enter to stop the server.");
        });

        // wait for user to hit the Enter key
        System.in.read();
        System.exit(0);
    }

}
