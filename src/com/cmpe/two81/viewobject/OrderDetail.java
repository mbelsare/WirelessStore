package com.cmpe.two81.viewobject;

/**
 * Created by IntelliJ IDEA.
 * User: jue
 * Date: 12/1/13
 * Time: 2:04 PM
 */
public class OrderDetail {
	Integer orderId;
	String productId;
	Integer quantity;
	Double totalCost;

	public OrderDetail(Integer orderId, String productId, Integer quantity, Double totalCost) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.totalCost = totalCost;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
}
