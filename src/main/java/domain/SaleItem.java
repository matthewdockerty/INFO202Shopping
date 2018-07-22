package domain;

import java.math.BigDecimal;

/**
 *
 * @author docma436
 */
public class SaleItem {

    private Integer quantityPurchased;
    private BigDecimal salePrice;
    private Product product;

    public SaleItem(Integer quantityPurchased, BigDecimal salePrice, Product product) {
        this.quantityPurchased = quantityPurchased;
        this.salePrice = salePrice;
        this.product = product;
    }

    public BigDecimal getItemTotal() {
        return salePrice.multiply(new BigDecimal(quantityPurchased));
    }

    public Integer getQuantityPurchased() {
        return quantityPurchased;
    }

    public void setQuantityPurchased(Integer quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "SaleItem{" + "quantityPurchased=" + quantityPurchased + ", salePrice=" + salePrice + '}';
    }

}
