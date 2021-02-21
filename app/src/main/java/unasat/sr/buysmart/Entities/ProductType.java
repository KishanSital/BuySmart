package unasat.sr.buysmart.Entities;

public class ProductType {

    private int productTypeId;
    private String name;

    public  ProductType(){}

    public ProductType(int productTypeId, String name) {
        this.productTypeId = productTypeId;
        this.name = name;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "productTypeId=" + productTypeId +
                ", name='" + name + '\'' +
                '}';
    }
}
