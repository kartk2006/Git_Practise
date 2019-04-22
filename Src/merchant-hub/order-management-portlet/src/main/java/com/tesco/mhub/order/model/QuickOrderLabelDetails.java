package com.tesco.mhub.order.model;

import java.util.Date;

public class QuickOrderLabelDetails extends OrderLabelMin {
	/**
	 * Tracking flag.
	 */
	private String trackingFlag;
	
	public QuickOrderLabelDetails(String orderId, String trackingId, Date collectionDate,String trackingFlag) {
		super(orderId, trackingId, collectionDate);
		this.trackingFlag=trackingFlag;
	}
	/**
	 * @return the trackingFlag
	 */
	public String getTrackingFlag() {
		return trackingFlag;
	}
	/**
	 * @param trackingFlag the trackingFlag to set
	 */
	public void setTrackingFlag(String trackingFlag) {
		this.trackingFlag = trackingFlag;
	}
	
	
}
