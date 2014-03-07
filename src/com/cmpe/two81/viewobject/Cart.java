package com.cmpe.two81.viewobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cart implements Serializable {

	private static final long serialVersionUID = 7612390689444845675L;

	private List<ProductInfo> savedItems;
    private double totalCost;
    private Date orderDate;
    private User user;
    private Store store;

    private PaymentInfo paymentInfo;

    public Cart(){
        savedItems = new ArrayList<ProductInfo>(2);
    }

    public List<ProductInfo> getProductInfos() {
        return savedItems;
    }

    public void setProductInfos(List<ProductInfo> productInfos) {
        this.savedItems = productInfos;
    }
    
    public double getTotalCost(){
        return totalCost;
    }

    public void setTotalCost(double cost) {
        totalCost = cost;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User usr) {
        user = usr;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store str) {
        store = str;
    }
    
    public Date getOrderDate(){
        return orderDate;
    }
    
    public void setOrderDate(Date dt) {
        orderDate = dt;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
}
