package unasat.sr.buysmart.Entities;

public class Product {
    private int id;
    private String name;
    private int price;
    private int productTypeId;
    
    public Product() {}

    public Product( String name, int price, int productTypeId) {
        this.name = name;
        this.price = price;
        this.productTypeId = productTypeId;
    }

    public Product(int id, String name, int price, int productTypeId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productTypeId = productTypeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", productTypeId=" + productTypeId +
                '}';
    }
}
