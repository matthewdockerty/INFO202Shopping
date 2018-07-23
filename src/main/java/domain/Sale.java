package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author docma436
 */
public class Sale {

    private String saleID;
    private Date date;
    private char status;

    private List<SaleItem> items;
    private Customer customer;

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (SaleItem item : items) {
            total.add(item.getItemTotal());
        }

        return total;
    }

    public void addItem(SaleItem item) {
        this.items.add(item);
    }

    public String getSaleID() {
        return saleID;
    }

    public void setSaleID(String saleID) {
        this.saleID = saleID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Sale{" + "saleID=" + saleID + ", date=" + date + ", status=" + status + ", items=" + items + ", customer=" + customer + '}';
    }
    
}
