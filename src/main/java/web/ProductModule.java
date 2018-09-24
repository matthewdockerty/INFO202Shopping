package web;

import dao.DAOException;
import dao.ProductDAO;
import org.jooby.Jooby;

/**
 *
 * @author docma436
 */
public class ProductModule extends Jooby {

    public ProductModule(ProductDAO productDAO) {
        get("/api/products", () -> productDAO.getProducts());

        get("/api/products/:id", (req) -> {
            String id = req.param("id").value();
            return productDAO.getProductByID(id);
        });

        get("/api/categories/", () -> productDAO.getCategories());

        get("/api/categories/:category", (req) -> {
            String category = req.param("category").value();
            return productDAO.getProductsByCategory(category);
        });

        get("/api/popular", () -> {
            try {
                return productDAO.getPopularProducts();
            } catch (DAOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        });
    }

}
