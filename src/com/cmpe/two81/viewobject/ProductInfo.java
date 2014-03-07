package com.cmpe.two81.viewobject;

/**
 * This class should be used
 */
public class ProductInfo {
    private String productId;
    private String productName;
    private double price;
    private String imageLink;
    private int qty;

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return qty;
    }

    public void setQuantity(int qy) {
        this.qty = qy;
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String id) {
        this.productId = id;
    }
}
