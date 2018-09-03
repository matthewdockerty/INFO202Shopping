package gui;

import dao.ProductDAO;
import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
        products.add(new Product("AA2222", "name2", "desc2", "cat2",
                new BigDecimal("33.00"), new Integer("44")));

        dao = mock(ProductDAO.class);

        // stub the getProducts method to return the test products
        when(dao.getProducts()).thenReturn(products);
    }

    @Test
    public void testEdit() {
        // create dialog passing in product and mocked DAO
        DialogViewProducts dialog = new DialogViewProducts(parent, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);

        // show the dialog on the screen, and ensure it is visible
        fixture.show().requireVisible();

        // verify that the UI componenents contains the product's details
        fixture.textBox("txtID").requireText("AA1111");
        fixture.textBox("txtName").requireText("Product Name");
        fixture.comboBox("comboBoxCategory").requireSelection("Category 1");

        // edit the name and major
        fixture.textBox("txtName").selectAll().deleteText().enterText("New Name!");
        fixture.comboBox("comboBoxCategory").selectItem("Category 2");

        // click the save button
        fixture.button("buttonSave").click();

        // create a Mockito argument captor to use to retrieve the passed product from the mocked DAO
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

        // verify that the DAO.save method was called, and capture the passed product
        verify(dao).saveProduct(argument.capture());

        // retrieve the passed product from the captor
        Product editedProduct = argument.getValue();

        // check that the changes were saved
        assertEquals("Ensure the name was changed", "New Name!", editedProduct.getName());
        assertEquals("Ensure the category was changed", "Category 2", editedProduct.getCategory());
    }

    @After
    public void tearDown() {
        fixture.cleanUp();
    }
}
