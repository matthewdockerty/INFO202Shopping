package dao;



import domain.Product;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author docma436
 */
public class JUnitTestDAO {
    
    private ProductDAO dao;
    private Product productOne, productTwo, productThree;
    
    public JUnitTestDAO() {
        dao = new ProductDAOJdbc();
    }
    
    @Before
    public void setUp() {
        this.productOne = new Product("AA1111", "name1", "cat1", "desc1", 
                new BigDecimal("11.0"), new Integer("22"));
        this.productTwo = new Product("AA2222", "name2", "cat2", "desc2", 
                new BigDecimal("33.0"), new Integer("44"));
        this.productThree = new Product("AA3333", "name3", "cat3", "desc3", 
                new BigDecimal("55.0"), new Integer("66"));
        
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
    
    @After
    public void tearDown() {
        dao.deleteProduct(productOne);
        dao.deleteProduct(productTwo);
        dao.deleteProduct(productThree);
    }
    
}
