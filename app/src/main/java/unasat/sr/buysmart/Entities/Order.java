package unasat.sr.buysmart.Entities;

import java.util.Date;

public class Order {

    private int id;
    private int productId;
    private int customerId;
    private String orderedDate;

    public Order() {}

    public Order(int id, int productId, int customerId, String orderedDate) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
        this.orderedDate = orderedDate;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productId=" + productId +
                ", customerId=" + customerId +
                ", orderedDate=" + orderedDate +
                '}';
    }
}
