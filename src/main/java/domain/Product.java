package domain;

import java.math.BigDecimal;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

/**
 *
 * @author Matthew
 */
public class Product {

    @NotNull(message = "ID must be provided.")
    @NotBlank(message = "ID must be provided.")
    @MatchPattern(pattern = {"[A-Z]{2}[0-9]{4}"}, message = "ID must have a 2 letter prefix and a 4 digit suffix. E.g. AB1234")
    private String productID;
    
    @NotNull(message = "Name must be provided.")
    @NotBlank(message = "Name must be provided.")
    @Length(min = 2, message = "Name must contain at least two characters.")
    private String name;
    
    @NotNull(message = "Description must be provided.")
    @NotBlank(message = "Description must be provided.")
    @Length(min = 4, message = "Description must contain at least four characters.")
    private String description;
    
    @NotNull(message = "Category must be provided.")
    @NotBlank(message = "Category must be provided.")
    private String category;
    
    @NotNull(message = "Price must be provided.")
    @NotNegative(message = "Price must be zero or greater.")
    private BigDecimal listPrice;
    
    @NotNull(message = "Quantity must be provided.")
    @NotNegative(message = "Quantity must be zero or greater.")
    private Integer quantityInStock;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public String toString() {
        return productID + ", " + name;
    }

}
