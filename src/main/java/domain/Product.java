package domain;

import java.math.BigDecimal;
import java.util.Objects;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.Max;
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
    @MatchPattern(pattern = {"[A-Z]{2}[0-9]{4}"}, message = "ID must have a 2 letter prefix and a 4 digit suffix. E.g. AB1234.")
    private String productID;

    @NotNull(message = "Name must be provided.")
    @NotBlank(message = "Name must be provided.")
    @Length(min = 2, max = 32, message = "Name must contain between 2 and 32 characters.")
    private String name;

    @NotNull(message = "Description must be provided.")
    @NotBlank(message = "Description must be provided.")
    @Length(min = 16, max = 1024, message = "Description must contain between 16 and 1024 characters.")
    private String description;

    @NotNull(message = "Category must be provided.")
    @NotBlank(message = "Category must be provided.")
    @Length(min = 1, max = 32, message = "Category must contain between 1 and 32 characters.")
    private String category;

    @NotNull(message = "Price must be provided.")
    @NotNegative(message = "Price must be zero or greater.")
    @Max(value = 999999.99, message = "Price must be less than $999,999.99")
    private BigDecimal listPrice;

    @NotNull(message = "Quantity must be provided.")
    @NotNegative(message = "Quantity must be zero or greater.")
    @Max(value = 999999, message = "Quantity must be less than 999,999")
    private Integer quantityInStock;

    public Product() {
    }

    public Product(String productID, String name, String description, String category, BigDecimal listPrice, Integer quantityInStock) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.category = category;
        this.listPrice = listPrice;
        this.quantityInStock = quantityInStock;
    }

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.productID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productID, other.productID)) {
            return false;
        }
        return true;
    }

    

}
