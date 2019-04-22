/**
 * 
 */
package com.tesco.mhub.order.model;

import com.tesco.mhub.order.search.OrderSummary;
import com.tesco.mhub.order.search.SearchOrdersResponse;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author SP02
 *
 */
public class ResponseSearchOrders implements Serializable {
	/**
	 * Serializable iiid.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *pageNumber. 
	 */
	private int pageNumber;
	
	/**
	 *pageSize.
	 */
	private int pageSize;
	
	/**
	 * totalOrders.
	 */
	private int totalOrders;
	
	/**
	 * responseOrderList.
	 */
	private List<ResponseSearchOrders.ResponseOrder> responseOrderList;
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalOrders() {
		return totalOrders;
	}

	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}
	
	public List<ResponseSearchOrders.ResponseOrder> getResponseOrderList() {
		return responseOrderList;
	}

	public void setResponseOrderList(
		List<ResponseSearchOrders.ResponseOrder> responseOrderList) {
		this.responseOrderList = responseOrderList;
	}

	public static class ResponseOrder {
		/**
		 * orderSummary in Response Order.
		 */
		private ResponseOrderSummary orderSummary;

		public ResponseOrderSummary getOrderSummary() {
			return orderSummary;
		}

		public void setOrderSummary(ResponseOrderSummary orderSummary) {
			this.orderSummary = orderSummary;
		}
		
	}
	
}
