package unasat.sr.buysmart.Entities;

import java.util.Date;

public class Order {

    private int id;
    private int productId;
    private int customerId;
    private String customerName;
    private String orderedDate;

    public Order() {}

    public Order(int id, int productId, int customerId, String orderedDate) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
        this.orderedDate = orderedDate;
    }

    public Order(int id, int productId, int customerId ,  String orderedDate,String customerName ) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.orderedDate = orderedDate;
    }

    public int getId() {
        return id;
    }

   /* public void setId(int id) {
        this.id = id;
    }*/

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productId=" + productId +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", orderedDate='" + orderedDate + '\'' +
                '}';
    }
}
