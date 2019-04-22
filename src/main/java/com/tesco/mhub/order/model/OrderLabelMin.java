package com.tesco.mhub.order.model;

import java.util.Date;

public class OrderLabelMin {
	/**
	 * Order ID variable.
	 */
	private String orderId;
	/**
	 * Tracking ID variable.
	 */
	private String trackingId;
	/**
	 * Collection Date.
	 */
	private Date collectionDate;
	
	public OrderLabelMin(String orderId, String trackingId,
			Date collectionDate) {
		super();
		this.orderId = orderId;
		this.trackingId = trackingId;
		this.collectionDate = collectionDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public Date getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}
}
