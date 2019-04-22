package com.tesco.mhub.order.model;

import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderBasket {

	/**
	 * totalSize is a variable.
	 */
	private int totalSize;
	/**
	 * acknowledgedOrders.
	 */
	private List<Order> ordersForUpdate;
	/**
	 * released orders.
	 */
	private Map<String,Order> releasedOrders;
	/**
	 * dispatched orders.
	 */
	private Map<String,Order> dispatchedOrders;
	
	/**
	 * dispatched orders.
	 */
	private Map<String,Order> allOrderDetails;
	
	/**
	 * originalPendingItems orders.
	 */
	private List<Order> originalPendingItems;
	
	/**
	 * transactionDetails of dispatch and cancel.
	 */
	private HashMap<String,Transaction> transactionDetailsOfDispatchAndCancel;
	
	/**
	 * transactionDetails of return.
	 */
	private HashMap<String,Transaction> transactionDetailsOfReturn;

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public List<Order> getOrdersForUpdate() {
		if(Validator.isNull(ordersForUpdate)){
			ordersForUpdate = new ArrayList<Order>();
		}
		return ordersForUpdate;
	}

	public void setOrdersForUpdate(List<Order> ordersForUpdate) {
		this.ordersForUpdate = ordersForUpdate;
	}

	public Map<String, Order> getReleasedOrders() {
		if(Validator.isNull(releasedOrders)){
			releasedOrders = new HashMap<String, Order>();
		}
		return releasedOrders;
	}

	public void setReleasedOrders(Map<String, Order> releasedOrders) {
		this.releasedOrders = releasedOrders;
	}

	public Map<String, Order> getDispatchedOrders() {
		if(Validator.isNull(dispatchedOrders)){
			dispatchedOrders = new HashMap<String, Order>();
		}
		return dispatchedOrders;
	}

	public void setDispatchedOrders(Map<String, Order> dispatchedOrders) {
		this.dispatchedOrders = dispatchedOrders;
	}
	
	public Map<String, Order> getAllOrderDetails() {
		return allOrderDetails;
	}

	public void setAllOrderDetails(Map<String, Order> allOrderDetails) {
		this.allOrderDetails = allOrderDetails;
	}

	public List<Order> getOriginalPendingItems() {
		if(Validator.isNull(originalPendingItems)){
			originalPendingItems = new ArrayList<Order>();
		}
		return originalPendingItems;
	}

	public void setOriginalPendingItems(List<Order> originalPendingItems) {
		this.originalPendingItems = originalPendingItems;
	}

	public HashMap<String, Transaction> getTransactionDetailsOfDispatchAndCancel() {
		if(Validator.isNull(transactionDetailsOfDispatchAndCancel)){
			transactionDetailsOfDispatchAndCancel = new HashMap<String, Transaction>();
		}
		return transactionDetailsOfDispatchAndCancel;
	}

	public void setTransactionDetailsOfDispatchAndCancel(
			HashMap<String, Transaction> transactionDetailsOfDispatchAndCancel) {
		this.transactionDetailsOfDispatchAndCancel = transactionDetailsOfDispatchAndCancel;
	}

	public HashMap<String, Transaction> getTransactionDetailsOfReturn() {
		if(Validator.isNull(transactionDetailsOfReturn)){
			transactionDetailsOfReturn = new HashMap<String, Transaction>();
		}
		return transactionDetailsOfReturn;
	}

	public void setTransactionDetailsOfReturn(
			HashMap<String, Transaction> transactionDetailsOfReturn) {
		this.transactionDetailsOfReturn = transactionDetailsOfReturn;
	}	
	public void clear(){
		ordersForUpdate = new ArrayList<Order>();
		originalPendingItems = new ArrayList<Order>();
		releasedOrders = new HashMap<String, Order>();
		dispatchedOrders = new HashMap<String, Order>();
		transactionDetailsOfDispatchAndCancel = new HashMap<String, Transaction>();
		transactionDetailsOfReturn = new HashMap<String, Transaction>();
	}
}
