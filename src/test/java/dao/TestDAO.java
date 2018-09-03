package dao;

import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author docma436
 */
public class TestDAO {

    private static final String dbUrl = "jdbc:h2:tcp://localhost:9017/project_test;IFEXISTS=TRUE";

    private ProductDAO dao;
    private Product productOne, productTwo, productThree, productFour;

    public TestDAO() {
        dao = new ProductDAOJdbc(dbUrl);
//        dao = new ProductDAOCollections();
    }

    @Before
    public void setUp() {
        this.productOne = new Product("AA1111", "name1", "desc1", "cat1",
                new BigDecimal("11.00"), new Integer("22"));
        this.productTwo = new Product("AA2222", "name2", "desc2", "cat2",
                new BigDecimal("33.00"), new Integer("44"));
        this.productThree = new Product("AA3333", "name3", "desc3", "cat3",
                new BigDecimal("55.00"), new Integer("66"));

        this.productFour = new Product("AA4444", "name4", "desc4", "cat1",
                new BigDecimal("55.00"), new Integer("66"));

        dao.saveProduct(productOne);
        dao.saveProduct(productTwo);
    }

    @Test
    public void testDAOSave() {
        dao.saveProduct(productThree);
        Product retrieved = dao.getProductByID("AA3333");

        assertEquals("Retrieved product should be the same", productThree, retrieved);
    }

    @Test
    public void testDAOEdit() {
        dao.saveProduct(productThree);

        Product retrieved = dao.getProductByID("AA3333");

        retrieved.setName("New Name!");
        retrieved.setCategory("New Category!");
        retrieved.setDescription("New Description!");
        retrieved.setListPrice(new BigDecimal("10.01"));
        retrieved.setQuantityInStock(new Integer(100));

        dao.saveProduct(retrieved);

        Product edited = dao.getProductByID("AA3333");
        
        assertEquals(edited.getProductID(), "AA3333");
        assertEquals(edited.getName(), "New Name!");
        assertEquals(edited.getDescription(), "New Description!");
        assertEquals(edited.getCategory(), "New Category!");
        assertEquals(edited.getListPrice(), new BigDecimal("10.01"));
        assertEquals(edited.getQuantityInStock(), new Integer(100));
    }

    @Test
    public void testDAODelete() {
        dao.deleteProduct(productOne);
        Product retrieved = dao.getProductByID("AA1111");

        assertNull("Product should no longer exist", retrieved);
    }

    @Test
    public void testDAOGetAll() {
        Collection<Product> products = dao.getProducts();

        assertTrue("productOne should exist", products.contains(productOne));
        assertTrue("productTwo should exist", products.contains(productTwo));

        assertEquals("Only two products in result", 2, products.size());

        for (Product product : products) {
            if (product.equals(productOne)) {
                assertEquals(productOne.getProductID(), product.getProductID());
                assertEquals(productOne.getName(), product.getName());
                assertEquals(productOne.getDescription(), product.getDescription());
                assertEquals(productOne.getCategory(), product.getCategory());
                assertEquals(productOne.getListPrice(), product.getListPrice());
                assertEquals(productOne.getQuantityInStock(), product.getQuantityInStock());
            }
        }
    }

    @Test
    public void testDAOGetByID() {
        Product retrieved = dao.getProductByID("AA1111");
        assertEquals("Retrieved product with ID should be the same", retrieved, productOne);

        assertEquals(productOne.getProductID(), retrieved.getProductID());
        assertEquals(productOne.getName(), retrieved.getName());
        assertEquals(productOne.getDescription(), retrieved.getDescription());
        assertEquals(productOne.getCategory(), retrieved.getCategory());
        assertEquals(productOne.getListPrice(), retrieved.getListPrice());
        assertEquals(productOne.getQuantityInStock(), retrieved.getQuantityInStock());

        Product nonExistent = dao.getProductByID("XX9999");
        assertNull("Product should not exist in database", nonExistent);
    }

    @Test
    public void testDAOGetCategories() {
        Collection<String> categories = dao.getCategories();

        assertTrue("cat1 should exist", categories.contains("cat1"));
        assertTrue("cat2 should exist", categories.contains("cat2"));

        assertTrue("There should only be two categories", categories.size() == 2);
    }

    @Test
    public void testDAOGetByCategory() {
        dao.saveProduct(productFour);

        List<Product> products = (List<Product>) dao.getProductsByCategory("cat1");
        assertTrue("Retrieved products collection should only contain products with category",
                products.contains(productOne) && products.contains(productFour)
                && !products.contains(productTwo) && !products.contains(productThree));

        assertTrue("Retrived products should not contain any other products", products.size() == 2);

        for (Product retrieved : products) {
            Product product = retrieved.equals(productOne) ? productOne : productFour;

            assertEquals(product.getProductID(), retrieved.getProductID());
            assertEquals(product.getName(), retrieved.getName());
            assertEquals(product.getDescription(), retrieved.getDescription());
            assertEquals(product.getCategory(), retrieved.getCategory());
            assertEquals(product.getListPrice(), retrieved.getListPrice());
            assertEquals(product.getQuantityInStock(), retrieved.getQuantityInStock());
        }

    }

    @After
    public void tearDown() {
        dao.deleteProduct(productOne);
        dao.deleteProduct(productTwo);
        dao.deleteProduct(productThree);
        dao.deleteProduct(productFour);
    }

}
