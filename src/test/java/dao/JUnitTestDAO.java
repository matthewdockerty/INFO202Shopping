package dao;



import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author docma436
 */
public class JUnitTestDAO {

    private static final String dbUrl = "jdbc:h2:tcp://localhost:9017/project_test;IFEXISTS=TRUE";
    
    private ProductDAO dao;
    private Product productOne, productTwo, productThree;
    
    public JUnitTestDAO() {
        dao = new ProductDAOJdbc(dbUrl);
    }
    
    @Before
    public void setUp() {
        this.productOne = new Product("AA1111", "name1", "cat1", "desc1", 
                new BigDecimal("11.00"), new Integer("22"));
        this.productTwo = new Product("AA2222", "name2", "cat2", "desc2", 
                new BigDecimal("33.00"), new Integer("44"));
        this.productThree = new Product("AA3333", "name3", "cat3", "desc3", 
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
    
    @After
    public void tearDown() {
        dao.deleteProduct(productOne);
        dao.deleteProduct(productTwo);
        dao.deleteProduct(productThree);
    }
    
}
