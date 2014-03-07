package com.cmpe.two81.viewobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jue
 * Date: 12/1/13
 * Time: 2:04 PM
 */
public class Order {

	public Order(Cart cart) {
		this.userId = cart.getUser().getId();
		this.totalCost = cart.getTotalCost();
		this.purchaseDate = cart.getOrderDate();
		this.storeId = cart.getStore().getID__c();
		this.productInfos = cart.getProductInfos();
	}

	String userId;
	List<ProductInfo> productInfos = new ArrayList<ProductInfo>();
	Double totalCost;
	Date purchaseDate;
	Integer storeId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<ProductInfo> getProductInfos() {
		return productInfos;
	}

	public void setProductInfos(List<ProductInfo> productInfos) {
		this.productInfos = productInfos;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
}
