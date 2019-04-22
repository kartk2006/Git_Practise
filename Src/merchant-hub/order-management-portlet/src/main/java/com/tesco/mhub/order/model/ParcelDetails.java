package com.tesco.mhub.order.model;

import java.util.List;

public class ParcelDetails {
	/**
	 * Parcel Tracking Id.
	 */
	private String trackingId;
	/**
	 * ItemDetails List.
	 */
	private List<ItemDetails> itemDetailsLst;
	
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public List<ItemDetails> getItemDetailsLst() {
		return itemDetailsLst;
	}
	public void setItemDetailsLst(List<ItemDetails> itemDetailsLst) {
		this.itemDetailsLst = itemDetailsLst;
	}
}
