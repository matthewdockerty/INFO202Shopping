package web;

import dao.ProductDAO;
import dao.ProductDAOJdbc;
import org.jooby.Jooby;
import org.jooby.Request;
import org.jooby.Response;
import org.jooby.Results;
import org.jooby.Route;
import org.jooby.Status;
import org.jooby.handlers.AssetHandler;

public class AssetModule extends Jooby {

    public AssetModule(ProductDAO productDAO) {
        assets("/*.html");
        assets("/css/*.css");
        assets("/js/*.js");
        assets("/images/*.png");
        assets("/images/*.jpg");
        // make index.html the default page
        assets("/", "index.html");
        // prevent 404 errors due to browsers requesting favicons
        get("/favicon.ico", () -> Results.noContent());

        assets("/images/product/:id", new AssetHandler("/images/product/*") {
            @Override
            public void handle(Request req, Response rsp, Route.Chain chain) throws Throwable {
                String id = req.param("id").value();
                if (id.matches("((^[A-Za-z]{0,2})|(^[A-Za-z]{2}[0-9]{0,4}))")) {
                    rsp.header("Content-Type", "image/png");

                    byte[] data = productDAO.getProductImage(id);
                    if (data == null) {
                        rsp.status(Status.NOT_FOUND);
                    } else {
                        rsp.send(data);
                        rsp.status(Status.OK);
                    }
                } else {
                    rsp.status(Status.NOT_FOUND);
                }
            }

        });
    }
}
