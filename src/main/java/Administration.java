
import dao.ProductDAO;
import dao.ProductDAOJdbc;
import gui.FrameMainMenu;

/**
 *
 * @author docma436
 */
public class Administration {

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAOJdbc();
        
        FrameMainMenu frame = new FrameMainMenu(dao);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}
