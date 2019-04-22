package com.tesco.mhub.order.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Order extends OrderExt implements Serializable{

	
	/**
	 * orderId is a variable.
	 */
	private String orderId;
	/**
	 * customerOrderNumber is a variable.
	 */
	private String customerOrderNumber;
	/**
	 * customer first name.
	 */
	private String customerFirstName;
	
	/**
	 * customer last name.
	 */
	private String customerLastName;
	/**
	 * lineNumber is a variable.
	 */
	private int noOfLines;
	
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
	private String orderStatus;
	/**
	 * delivery is a variable.
	 */
	private String deliveryOption;
	/**
	 * placedDate is a variable.
	 */
	private Date orderPlacedDate;
	/**
	 * OrderLines.FirstName.Customer.
	 */
	private String customerEmail;
	/**
	 * customerMobile.
	 */
	private String customerMobile;
	/**
	 * HomeTel-Day.
	 */
	private String customerDayTelephone;
	/**
	 * HomeTel-Night.
	 */
	private String customerEveningTelephone;
	/**
	 * deliveryCost.
	 */
	private double deliveryCost;
	/**
	 * shipToAddress.
	 */
	private String shipToAddress;
	/**
	 * shipToMobile.
	 */
	private String shipToMobile;
	/**
	 * orderValue.
	 */
	private double orderValue;
	/**
	 * orderLineItems.
	 */
	
	/**
	 * is a variable and it should be Dispatch,Cancel or Return.
	 */
	private String serviceActionType;
	/**
	 * order tracking id.
	 */
	private String trackingId;
	/**
	 * order tracking url.
	 */
	private String trackingURL;
	/**
	 * carrier name.
	 */
	private String carrierName;
	/**
	 * orderLineItems name.
	 */
	private Date expectedDispatchDate;
	
	/**
	 * carrier name.
	 */
	private String shipToFName;

	
	/**
	 * carrier name.
	 */
	private String shipToLName;

	/**
	 *Map which stores OrderLineItemNo and Tracking Id Details.
	 */
	private Map<Integer,StringBuilder> shipmentTrackingDetails;

	/**
	 *Map which stores OrderLineItemNo and OrderMineItem Details.
	 */
	private Map<Integer,OrderLineItem> orderLineItems;
	
	public Date getExpectedDispatchDate() {
		return expectedDispatchDate;
	}
	public void setExpectedDispatchDate(Date expectedDispatchDate) {
		this.expectedDispatchDate = expectedDispatchDate;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCustomerOrderNumber() {
		return customerOrderNumber;
	}
	public void setCustomerOrderNumber(String customerOrderNumber) {
		this.customerOrderNumber = customerOrderNumber;
	}
	public int getNoOfLines() {
		return noOfLines;
	}
	public void setNoOfLines(int noOfLines) {
		this.noOfLines = noOfLines;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getDeliveryOption() {
		return deliveryOption;
	}
	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}
	public Date getOrderPlacedDate() {
		return orderPlacedDate;
	}
	public void setOrderPlacedDate(Date orderPlacedDate) {
		this.orderPlacedDate = orderPlacedDate;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public String getCustomerDayTelephone() {
		return customerDayTelephone;
	}
	public void setCustomerDayTelephone(String customerDayTelephone) {
		this.customerDayTelephone = customerDayTelephone;
	}
	public String getCustomerEveningTelephone() {
		return customerEveningTelephone;
	}
	public void setCustomerEveningTelephone(String customerEveningTelephone) {
		this.customerEveningTelephone = customerEveningTelephone;
	}
	public double getDeliveryCost() {
		return deliveryCost;
	}
	public void setDeliveryCost(double deliveryCost) {
		this.deliveryCost = deliveryCost;
	}
	public String getShipToAddress() {
		return shipToAddress;
	}
	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}
	public String getShipToMobile() {
		return shipToMobile;
	}
	public void setShipToMobile(String shipToMobile) {
		this.shipToMobile = shipToMobile;
	}
	public double getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(double orderValue) {
		this.orderValue = orderValue;
	}
	public Map<Integer, OrderLineItem> getOrderLineItems() {
		return orderLineItems;
	}
	public void setOrderLineItems(Map<Integer, OrderLineItem> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}
	public String getServiceActionType() {
		return serviceActionType;
	}
	public void setServiceActionType(String serviceActionType) {
		this.serviceActionType = serviceActionType;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getTrackingURL() {
		return trackingURL;
	}
	public void setTrackingURL(String trackingURL) {
		this.trackingURL = trackingURL;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}	
	public String getCustomerFirstName() {
		return customerFirstName;
	}
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}
	public String getCustomerLastName() {
		return customerLastName;
	}
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}
	public String getShipToFName() {
		return shipToFName;
	}
	public void setShipToFName(String shipToFName) {
		this.shipToFName = shipToFName;
	}
	public String getShipToLName() {
		return shipToLName;
	}
	public void setShipToLName(String shipToLName) {
		this.shipToLName = shipToLName;
	}
	public Map<Integer,StringBuilder> getShipmentTrackingDetails() {
		return shipmentTrackingDetails;
	}
	public void setShipmentTrackingDetails(Map<Integer,StringBuilder> shipmentTrackingDetails) {
		this.shipmentTrackingDetails = shipmentTrackingDetails;
	}
}
