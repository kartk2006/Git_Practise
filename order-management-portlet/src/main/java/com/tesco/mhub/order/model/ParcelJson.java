package com.tesco.mhub.order.model;

public class ParcelJson {
	/**
	 * Order Id.
	 */
	private String orderId;
	/**
	 * Tracking Id.
	 */
	private String trackId;
	/**
	 * Sku Id.
	 */
	private String skuId;
	/**
	 * Order Line number.
	 */
	private int lineNum;
	/**
	 * Quantity.
	 */
	private int qty;
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the trackingId
	 */
	public String getTrackingId() {
		return trackId;
	}
	/**
	 * @param trackingId the trackingId to set
	 */
	public void setTrackingId(String trackingId) {
		this.trackId = trackingId;
	}
	/**
	 * @return the skuId
	 */
	public String getSkuId() {
		return skuId;
	}
	/**
	 * @param skuId the skuId to set
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	/**
	 * @return the lineNumber
	 */
	public int getLineNumber() {
		return lineNum;
	}
	/**
	 * @param lineNumber the lineNumber to set
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNum = lineNumber;
	}
	/**
	 * @return the qty
	 */
	public int getQty() {
		return qty;
	}
	/**
	 * @param qty the qty to set
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}
}
