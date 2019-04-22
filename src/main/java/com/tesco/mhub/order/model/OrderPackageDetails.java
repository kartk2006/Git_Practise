package com.tesco.mhub.order.model;

import java.util.List;

public class OrderPackageDetails {
	/**
	 * Order Id.
	 */
	private String orderId;
	/**
	 * Customer Name.
	 */
	private String customerName;
	/**
	 * Number of parcels.
	 */
	private int parcelSize;
	/**
	 * Parcel Details list.
	 */
	private List<ParcelDetails> parcelDetailsList;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getParcelSize() {
		return parcelSize;
	}
	public void setParcelSize(int parcelSize) {
		this.parcelSize = parcelSize;
	}
	public List<ParcelDetails> getParcelDetailsList() {
		return parcelDetailsList;
	}
	public void setParcelDetailsList(List<ParcelDetails> parcelDetailsList) {
		this.parcelDetailsList = parcelDetailsList;
	}

}
