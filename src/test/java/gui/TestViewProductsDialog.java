package gui;

import dao.ProductDAO;
import domain.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author docma436
 */
public class TestViewProductsDialog {

    private ProductDAO dao;
    private DialogFixture fixture;
    private Robot robot;

    @Before
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        robot.settings().delayBetweenEvents(75);

        Collection<Product> products = new HashSet<>();
        products.add(new Product("AA1111", "name1", "desc1", "cat1",
                new BigDecimal("11.00"), new Integer("22")));
        products.add(new Product("ZZ2222", "name2", "desc2", "cat2",
                new BigDecimal("33.00"), new Integer("44")));
        products.add(new Product("AA2222", "name3", "desc3", "cat2",
                new BigDecimal("33.00"), new Integer("44")));

        dao = mock(ProductDAO.class);

        // stub the getProducts method to return the test products
        when(dao.getProducts()).thenReturn(products);
    }

    @Test
    public void testViewAll() {
        // create dialog passing in product and mocked DAO
        DialogViewProducts dialog = new DialogViewProducts(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);

        // show the dialog on the screen, and ensure it is visible
        fixture.show().requireVisible();

        // Get data from list
        List list = Arrays.asList(fixture.list("listProducts").contents());
        Collection<Product> daoProducts = dao.getProducts();
        
        // Verify that all the products in the dao are displayed in the list.
        for (Product p : daoProducts) {
            assertTrue("list contains all products in the dao", list.contains(p.toString()));
        }

        // Verify that the number of elements in the list is the same number of elements in the dao. i.e. no extra products in the list!
        assertEquals("same number of products in list and dao", list.size(), daoProducts.size());

        // Verify list alphabetical order.
        
        List correctOrder = new ArrayList(daoProducts);
        Collections.sort(correctOrder, (a, b) -> a.toString().compareTo(b.toString()));
        
        for (int i = 0; i < correctOrder.size(); i++)
            assertEquals("list alphabetical order", list.get(i), correctOrder.get(i).toString());
    }

    @After
    public void tearDown() {
        fixture.cleanUp();
    }
}
