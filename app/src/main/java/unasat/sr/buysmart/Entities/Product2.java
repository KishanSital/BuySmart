package unasat.sr.buysmart.Entities;

import java.util.Arrays;

public class Product2 {
    private int id;
    private String name;
    private int price;
    private int productTypeId;
    private byte [] image;



    public Product2() {}

    public Product2(String name, int price, int productTypeId) {
        this.name = name;
        this.price = price;
        this.productTypeId = productTypeId;
    }

    public Product2(int id, String name, int price, int productTypeId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productTypeId = productTypeId;
    }


    public Product2(int id, String name, int price, int productTypeId, byte[] image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productTypeId = productTypeId;
        this.image = image;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", productTypeId=" + productTypeId +
                ", image=" + Arrays.toString(image) +
                '}';
    }

    public String InttoString (int data){
        return data +"";
    }
}
