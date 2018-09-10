package web;

import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;

/**
 *
 * @author docma436
 */
public class Server extends Jooby {

    public Server() {
        port(8080);
        get("/", () -> "Hello World");
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
