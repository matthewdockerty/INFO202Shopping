package gui;


import dao.ProductDAO;
import domain.Product;
import gui.DialogProductEditor;
import java.math.BigDecimal;
import java.util.Collection;
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
public class TestProductEditorDialog {

    private ProductDAO dao;
    private DialogFixture fixture;
    private Robot robot;

    @Before
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        robot.settings().delayBetweenEvents(75);

        Collection<String> categories = new TreeSet<>();
        categories.add("Category 1");
        categories.add("Category 2");

        dao = mock(ProductDAO.class);

        // stub the getCategories method to return the test categories
        when(dao.getCategories()).thenReturn(categories);
    }

    @Test
    public void testEdit() {
        Product product = new Product("AA1111", "Product Name", "Description", "Category 1",
                new BigDecimal("11.00"), new Integer("22"));

        // create dialog passing in product and mocked DAO
        DialogProductEditor dialog = new DialogProductEditor(null, true, product, dao);

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

    @Test
    public void testSave() {
        // create the dialog passing in the mocked DAO
        DialogProductEditor dialog = new DialogProductEditor(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        // enter some details into the UI components
        fixture.textBox("txtID").enterText("AA1111");
        fixture.textBox("txtName").enterText("Product Name");
        fixture.textBox("txtAreaDescription").enterText("Description");
        fixture.comboBox("comboBoxCategory").selectItem("Category 1");
        fixture.textBox("txtPrice").enterText("10.01");
        fixture.textBox("txtQuantityInStock").enterText("100");

        // click the save button
        fixture.button("buttonSave").click();

        // create a Mockito argument captor to use to retrieve the passed product from the mocked DAO
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

        // verify that the DAO.save method was called, and capture the passed product
        verify(dao).saveProduct(argument.capture());

        // retrieve the passed product from the captor
        Product savedProduct = argument.getValue();

        // test that the product's details were properly saved
        assertEquals("Ensure the ID was saved", "AA1111", savedProduct.getProductID());
        assertEquals("Ensure the name was saved", "Product Name", savedProduct.getName());
        assertEquals("Ensure the description was saved", "Description", savedProduct.getDescription());
        assertEquals("Ensure the category was saved", "Category 1", savedProduct.getCategory());
        assertEquals("Ensure the price was saved", new BigDecimal("10.01"), savedProduct.getListPrice());
        assertEquals("Ensure the quantity was saved", new Integer(100), savedProduct.getQuantityInStock());
    }

    @After
    public void tearDown() {
        fixture.cleanUp();
    }
}
