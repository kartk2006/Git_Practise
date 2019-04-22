package com.tesco.mhub.order.model;

import java.util.List;

public class ParcelItemInfo {
	/**
	 * parcel Tracking Id.
	 */
	private String trackingId;
	/**
	 * Item Information.
	 */
	private List<ItemInfo> itemInfoLst;
	
	public ParcelItemInfo(String trackingId, List<ItemInfo> itemInfoLst) {
		super();
		this.trackingId = trackingId;
		this.itemInfoLst = itemInfoLst;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public List<ItemInfo> getItemInfoLst() {
		return itemInfoLst;
	}
	public void setItemInfoLst(List<ItemInfo> itemInfoLst) {
		this.itemInfoLst = itemInfoLst;
	}
	
	
}
