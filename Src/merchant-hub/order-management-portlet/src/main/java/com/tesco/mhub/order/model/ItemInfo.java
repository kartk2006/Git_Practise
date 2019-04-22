package com.tesco.mhub.order.model;

public class ItemInfo {
	/**
	 * Order Line item Number.
	 */
	private int lineNumber; 
	/**
	 * Merchant SKU ID.
	 */
	private String skuId;
	/**
	 * Item Description.
	 */
	private String description;
	/**
	 * Item Quantity.
	 */
	private double quantity;
	
	public ItemInfo(int lineNumber, String skuId, String description, double quantity) {
		super();
		this.lineNumber = lineNumber;
		this.skuId = skuId;
		this.description = description;
		this.quantity = quantity;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
}
