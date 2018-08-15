package dao;

/**
 *
 * @author Matthew
 */
public class DAOException extends RuntimeException {
    
    public DAOException(String reason) {
        super(reason);
    }
    
    public DAOException(String reason, Exception cause) {
        super(reason, cause);
    }
    
}
