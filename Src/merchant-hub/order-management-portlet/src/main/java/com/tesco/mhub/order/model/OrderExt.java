package com.tesco.mhub.order.model;

import java.util.List;

/**
 * @author OA18.
 *
 */
public class OrderExt {
	/**
	 * Quick Dispatch order.
	 */
	
	private String quickShipToAddress;
	/**
	 * deliveryInstructions.
	 */
	private String deliveryInstructions;

	/**
	 *Yodel label details.
	 */
	private List<OrderLabelMin> orderLblList;

	public String getDeliveryInstructions() {
		return deliveryInstructions;
	}
	public void setDeliveryInstructions(String deliveryInstructions) {
		this.deliveryInstructions = deliveryInstructions;
	}
	/**
	 * @return the quickShipToAddress
	 */
	public String getQuickShipToAddress() {
		return quickShipToAddress;
	}

	/**
	 * @param quickShipToAddress the quickShipToAddress to set
	 */
	public void setQuickShipToAddress(String quickShipToAddress) {
		this.quickShipToAddress = quickShipToAddress;
	}
	/**
	 * @return the orderLblList
	 */
	public List<OrderLabelMin> getOrderLblList() {
		return orderLblList;
	}
	/**
	 * @param orderLblList the orderLblList to set
	 */
	public void setOrderLblList(List<OrderLabelMin> orderLblList) {
		this.orderLblList = orderLblList;
	}
	
}
