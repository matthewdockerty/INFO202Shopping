package gui;

import dao.ProductDAO;
import domain.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import static org.assertj.swing.core.matcher.DialogMatcher.withTitle;
import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
import org.mockito.stubbing.OngoingStubbing;

/**
 *
 * @author docma436
 */
public class TestViewProductsDialog {

    private ProductDAO dao;
    private DialogFixture fixture;
    private Robot robot;

    private Product product1 = new Product("AA1111", "name1", "desc1", "cat1",
            new BigDecimal("11.00"), new Integer("22"));
    private Product product2 = new Product("ZZ2222", "name2", "desc2", "cat2",
            new BigDecimal("33.00"), new Integer("44"));
    private Product product3 = new Product("AA2222", "name3", "desc3", "cat2",
            new BigDecimal("33.00"), new Integer("44"));

    @Before
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        robot.settings().delayBetweenEvents(75);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

        // simulate order by statement in dao.
        Collections.sort(products, (a, b) -> a.getProductID().compareTo(b.getProductID()));

        dao = mock(ProductDAO.class);

        // stub the getProducts method to return the test products
        when(dao.getProducts()).thenReturn(products);

        // stub the getProductById method
        when(dao.getProductByID(product1.getProductID())).thenReturn(product1);
        when(dao.getProductByID(product2.getProductID())).thenReturn(product2);
        when(dao.getProductByID(product3.getProductID())).thenReturn(product3);

        when(dao.getCategories()).thenReturn(Arrays.asList(new String[]{"cat1", "cat2"}));
        when(dao.getProductsByCategory("cat1")).thenReturn(Arrays.asList(new Product[]{product1}));
        when(dao.getProductsByCategory("cat2")).thenReturn(Arrays.asList(new Product[]{product2, product3}));

    }

    @Test
    public void testViewAll() {
        // create dialog passing in mocked DAO
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
        List<Product> correctOrder = new ArrayList(daoProducts);
        Collections.sort(correctOrder, (a, b) -> a.getProductID().compareTo(b.getProductID()));

        for (int i = 0; i < correctOrder.size(); i++) {
            assertEquals("list alphabetical order", list.get(i), correctOrder.get(i).toString());
        }

        fixture.close();

    }

    @Test
    public void testEditButton() {
        // create dialog passing in mocked DAO
        DialogViewProducts dialog = new DialogViewProducts(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        fixture.list("listProducts").selectItem(product2.toString());
        fixture.button("buttonEdit").click();

        DialogFixture editDialog = fixture.dialog("dialogProductEditor");
        editDialog.textBox("txtID").requireText(product2.getProductID());

        fixture.close();

    }

    @Test
    public void testDeleteButton() {
        // create dialog passing in mocked DAO
        DialogViewProducts dialog = new DialogViewProducts(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        fixture.list("listProducts").selectItem(product3.toString());
        fixture.button("buttonDelete").click();

        DialogFixture confirmDialog = fixture.dialog(
                withTitle("Confirm Delete").andShowing()).requireVisible();

        confirmDialog.button(withText("Yes")).click();

        // create a Mockito argument captor to use to retrieve the passed product from the mocked DAO
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

        // verify that the DAO.save method was called, and capture the passed product
        verify(dao).deleteProduct(argument.capture());

        // retrieve the passed product from the captor
        Product deletedProduct = argument.getValue();

        assertEquals("deleted product", deletedProduct, product3);

        fixture.close();

    }

    @Test
    public void testSearchButton() {
        // create dialog passing in mocked DAO
        DialogViewProducts dialog = new DialogViewProducts(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        fixture.textBox("txtSearch").enterText(product1.getProductID());
        fixture.button("buttonSearch").click();

        // Get data from list
        List list = Arrays.asList(fixture.list("listProducts").contents());

        // Verify that the product with the searched id is in the list.
        assertTrue("list contains product after search", list.contains(product1.toString()));
        // Verify list only contains one product
        assertEquals("list contains only searched product", list.size(), 1);

        fixture.close();

    }

    @Test
    public void testCategoryFilter() {
        // create dialog passing in mocked DAO
        DialogViewProducts dialog = new DialogViewProducts(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        fixture.comboBox("comboBoxFilter").selectItem(product2.getCategory());

        // Get data from list
        List<String> list = Arrays.asList(fixture.list("listProducts").contents());

        for (String p : list) {
            assertTrue("list contains products with category", list.contains(p));
        }
        assertEquals("list contains only two products", list.size(), 2);

        fixture.close();
    }

    @After
    public void tearDown() {
        fixture.cleanUp();
    }
}
