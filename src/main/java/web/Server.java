package web;

import dao.ProductDAO;
import dao.ProductDAOJdbc;
import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.json.Gzon;

/**
 *
 * @author docma436
 */
public class Server extends Jooby {

    private ProductDAO productDao;

    public Server() {
        productDao = new ProductDAOJdbc();

        port(8080);

        use(new Gzon());
        use(new ProductModule(productDao));
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
