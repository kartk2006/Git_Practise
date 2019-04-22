/**
 * 
 */
package com.tesco.mhub.order.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author SP02
 *
 */
public class ResponseOrderSummary implements Serializable {
	/**
	 * orderId.
	 */
	private String orderId;
	
	/**
	 * releaseOrderNumber.
	 */
	private String releaseOrderNumber;
	
	/**
	 * totalLineItems.
	 */
	private int totalLineItems;
	
	/**
	 * totalOpenLineItems.
	 */
	private int totalOpenLineItems;
	
	/**
	 * status.
	 */
	private String status;
	
	/**
	 * deliveryOption.
	 */
	private String deliveryOption;
	
	/**
	 * releasedDate.
	 */
	private Date releasedDate;
	
	/**
	 * expectedDispatchDate.
	 */
	private Date expectedDispatchDate;
	
	/**
	 * dispatchedDate.
	 */
	private Date dispatchedDate;
	
	/**
	 * cancelledDate.
	 */
	private Date cancelledDate;
	
	/**
	 * returnedDate.
	 */
	private Date returnedDate;
	
	/**
	 * expectedShipmentDate.
	 */
	private Date expectedShipmentDate;
	
	/**
	 * billToName.
	 */
	private String billToName;
	
	/**
	 * shipToName.
	 */
	private String shipToName;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getReleaseOrderNumber() {
		return releaseOrderNumber;
	}

	public void setReleaseOrderNumber(String releaseOrderNumber) {
		this.releaseOrderNumber = releaseOrderNumber;
	}

	public int getTotalLineItems() {
		return totalLineItems;
	}

	public void setTotalLineItems(int totalLineItems) {
		this.totalLineItems = totalLineItems;
	}

	public int getTotalOpenLineItems() {
		return totalOpenLineItems;
	}

	public void setTotalOpenLineItems(int totalOpenLineItems) {
		this.totalOpenLineItems = totalOpenLineItems;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public Date getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(Date releasedDate) {
		this.releasedDate = releasedDate;
	}

	public Date getExpectedDispatchDate() {
		return expectedDispatchDate;
	}

	public void setExpectedDispatchDate(Date expectedDispatchDate) {
		this.expectedDispatchDate = expectedDispatchDate;
	}

	public Date getDispatchedDate() {
		return dispatchedDate;
	}

	public void setDispatchedDate(Date dispatchedDate) {
		this.dispatchedDate = dispatchedDate;
	}

	public Date getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}

	public Date getReturnedDate() {
		return returnedDate;
	}

	public void setReturnedDate(Date returnedDate) {
		this.returnedDate = returnedDate;
	}

	public Date getExpectedShipmentDate() {
		return expectedShipmentDate;
	}

	public void setExpectedShipmentDate(Date expectedShipmentDate) {
		this.expectedShipmentDate = expectedShipmentDate;
	}

	public String getBillToName() {
		return billToName;
	}

	public void setBillToName(String billToName) {
		this.billToName = billToName;
	}

	public String getShipToName() {
		return shipToName;
	}

	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}
}
