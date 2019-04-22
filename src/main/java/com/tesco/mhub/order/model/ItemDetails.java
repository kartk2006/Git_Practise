package com.tesco.mhub.order.model;

public class ItemDetails {
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
	private int qty;
	/**
	 * @return the lineNumber
	 */
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
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
}
