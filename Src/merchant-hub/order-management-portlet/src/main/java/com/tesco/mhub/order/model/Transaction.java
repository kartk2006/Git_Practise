package com.tesco.mhub.order.model;

import java.io.Serializable;

public class Transaction implements Serializable {

	/**
	 * Seriallizable Id.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Seriallizable Id13221.
	 */
	private String orderId;
	/**
	 * Seriallizable Idwqewr.
	 */
	private String transactionId;
	/**
	 * Seriallizable Idwqrwer.
	 */
	private String transactionType;
	/**
	 * Seriallizable Idwqerwre.
	 */
	private String transactionStatus;
	/**
	 * Seriallizable Idwqrwer.
	 */
	private Order oldOrder;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Order getOldOrder() {
		return oldOrder;
	}

	public void setOldOrder(Order oldOrder) {
		this.oldOrder = oldOrder;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
