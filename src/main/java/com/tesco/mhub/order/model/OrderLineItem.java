package com.tesco.mhub.order.model;

import java.io.Serializable;


public class OrderLineItem extends OrderLineItemExt implements Serializable{

	/**
	 * Serializable iad.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * order line number.
	 */
	private int orderLineNumber;
	/**
	 * sku details.
	 */
	private String sku;
	/**
	 * short description of order.
	 */
	private String description;
	/**
	 * order.portal.status.open = Open.
	 * order.portal.status.acknowledged = Acknowledged
	 * order.portal.status.dispatched = Dispatched
	 * order.portal.status.returned = Returned
	 * order.portal.status.cancelled = Cancelled
	 * order.portal.status.partial.acknowledged = Partial Acknowledged  
	 * order.portal.status.partially.dispatched = Partially Dispatched 
	 * order.portal.status.partially.cancelled = Partially Cancelled 
	 * order.portal.status.partially.returned = Partially Returned
	 */
	private String lineItemStatus;
	/**
	 * order quantity.
	 */
	private double orderedQuantity;
	/**
	 * open quantity.
	 */
	private double openQuantity;
	/**
	 * dispatched quantity.
	 */
	private double dispatchedQuantity;
	/**
	 * returnedQuantity.
	 */
	private double returnedQuantity;
	/**
	 * cancelledQuantity.
	 */
	private double cancelledQuantity;
	/**
	 * acknowledgedQuantity.
	 */
	private double acknowledgedQuantity;
	/**
	 * primaryReason for cancellation.
	 */
	private String primaryReasonForCancellation;
	/**
	 * secondaryReason for cancellation.
	 */
	private String secondaryReasonForReturn;
	/**
	 * reason for returning a line item.
	 */
	private String reasonForReturn;
	/**
	 * cost per item.
	 */
	private double costPerItem;
	/**
	 * refundAmount.
	 */
	private double refundAmount;
	/**
	 * unitOfSale.
	 */
	private String unitOfSale;
	/**
	 * shipToCity.
	 */
	private String shipToCity;
	/**
	 * shipToCountry.
	 */
	private String shipToCountry;
	/**
	 * shipToPostcode.
	 */
	private String shipToPostcode;
	/**
	 * shipToFirstName.
	 */
	private String shipToFirstName;
	/**
	 * shipToLastName.
	 */
	private String shipToLastName;
	/**
	 * is a variable.
	 */
	private String type;

	/**
	 * is a variable and it should be Dispatch,Cancel or Return.
	 */
	private String serviceActionType;

	/**
	 * is a address of shiping details1.
	 */
	private String shipingAddress1;

	/**
	 * is a address of shiping details2.
	 */
	private String shipingAddress2;

	public OrderLineItem(){

	}
	
	public OrderLineItem(OrderLineItem oli) {
		super();
		this.orderLineNumber =oli.orderLineNumber;
		this.sku = oli.sku;
		this.description = oli.description;
		this.lineItemStatus = oli.lineItemStatus;
		this.orderedQuantity = oli.orderedQuantity;
		this.openQuantity = oli.openQuantity;
		this.dispatchedQuantity = oli.dispatchedQuantity;
		this.returnedQuantity = oli.returnedQuantity;
		this.cancelledQuantity = oli.cancelledQuantity;
		this.acknowledgedQuantity = oli.acknowledgedQuantity;
		this.primaryReasonForCancellation =  oli.primaryReasonForCancellation;
		this.secondaryReasonForReturn = oli.secondaryReasonForReturn;
		this.reasonForReturn = oli.reasonForReturn;
		this.costPerItem = oli.costPerItem;
		this.refundAmount = oli.refundAmount;
		this.unitOfSale = oli.unitOfSale;
		this.shipToCity = oli.shipToCity;
		this.shipToCountry =  oli.shipToCountry;
		this.shipToPostcode = oli.shipToPostcode;
		this.shipToFirstName = oli.shipToFirstName;
		this.shipToLastName = oli.shipToLastName;
		this.type = oli.type;
		this.serviceActionType = oli.serviceActionType;
		this.shipingAddress1 = oli.shipingAddress1;
		this.shipingAddress2 = oli.shipingAddress2;
	}

	public String getUnitOfSale() {
		return unitOfSale;
	}
	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}
	public String getShipToCity() {
		return shipToCity;
	}
	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}
	public String getShipToCountry() {
		return shipToCountry;
	}
	public void setShipToCountry(String shipToCountry) {
		this.shipToCountry = shipToCountry;
	}
	public String getShipToPostcode() {
		return shipToPostcode;
	}
	public void setShipToPostcode(String shipToPostcode) {
		this.shipToPostcode = shipToPostcode;
	}
	public String getShipToFirstName() {
		return shipToFirstName;
	}
	public void setShipToFirstName(String shipToFirstName) {
		this.shipToFirstName = shipToFirstName;
	}
	public String getShipToLastName() {
		return shipToLastName;
	}
	public void setShipToLastName(String shipToLastName) {
		this.shipToLastName = shipToLastName;
	}
	public int getOrderLineNumber() {
		return orderLineNumber;
	}
	public void setOrderLineNumber(int orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLineItemStatus() {
		return lineItemStatus;
	}
	public void setLineItemStatus(String lineItemStatus) {
		this.lineItemStatus = lineItemStatus;
	}
	public double getOrderedQuantity() {
		return orderedQuantity;
	}
	public void setOrderedQuantity(double orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}
	public double getOpenQuantity() {
		return openQuantity;
	}
	public void setOpenQuantity(double openQuantity) {
		this.openQuantity = openQuantity;
	}
	public double getAcknowledgedQuantity() {
		return acknowledgedQuantity;
	}
	public void setAcknowledgedQuantity(double acknowledgedQuantity) {
		this.acknowledgedQuantity = acknowledgedQuantity;
	}
	public double getDispatchedQuantity() {
		return dispatchedQuantity;
	}
	public void setDispatchedQuantity(double dispatchedQuantity) {
		this.dispatchedQuantity = dispatchedQuantity;
	}
	public double getReturnedQuantity() {
		return returnedQuantity;
	}
	public void setReturnedQuantity(double returnedQuantity) {
		this.returnedQuantity = returnedQuantity;
	}
	public double getCancelledQuantity() {
		return cancelledQuantity;
	}
	public void setCancelledQuantity(double cancelledQuantity) {
		this.cancelledQuantity = cancelledQuantity;
	}

	public String getPrimaryReasonForCancellation() {
		return primaryReasonForCancellation;
	}
	public void setPrimaryReasonForCancellation(String primaryReasonForCancellation) {
		this.primaryReasonForCancellation = primaryReasonForCancellation;
	}
	public String getSecondaryReasonForReturn() {
		return secondaryReasonForReturn;
	}
	public void setSecondaryReasonForReturn(
			String secondaryReasonForCancellation) {
		this.secondaryReasonForReturn = secondaryReasonForCancellation;
	}
	public String getReasonForReturn() {
		return reasonForReturn;
	}
	public void setReasonForReturn(String reasonForReturn) {
		this.reasonForReturn = reasonForReturn;
	}
	public double getCostPerItem() {
		return costPerItem;
	}
	public void setCostPerItem(double costPerItem) {
		this.costPerItem = costPerItem;
	}
	public double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getServiceActionType() {
		return serviceActionType;
	}
	public void setServiceActionType(String serviceActionType) {
		this.serviceActionType = serviceActionType;
	}
	public String getShipingAddress1() {
		return shipingAddress1;
	}
	public void setShipingAddress1(String shipingAddress1) {
		this.shipingAddress1 = shipingAddress1;
	}
	public String getShipingAddress2() {
		return shipingAddress2;
	}
	public void setShipingAddress2(String shipingAddress2) {
		this.shipingAddress2 = shipingAddress2;
	}
}
