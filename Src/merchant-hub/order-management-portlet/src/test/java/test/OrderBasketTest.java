package test;

import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderBasket;
import com.tesco.mhub.order.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class OrderBasketTest {
	
	OrderBasket orderBasket = new OrderBasket();
	
	@Test
	public void testGetTotalSize() {
		orderBasket.setTotalSize(10);
		Assert.assertEquals(10, orderBasket.getTotalSize());
	}
	
	@Test
	public void testGetOrdersForUpdate() {
		List<Order> ordersForUpdate = null;
		orderBasket.setOrdersForUpdate(ordersForUpdate);
		orderBasket.getOrdersForUpdate();
		
		Order order = new Order();
		order.setCarrierName("Name");
		ordersForUpdate = new ArrayList<Order>();
		ordersForUpdate.add(order);
		orderBasket.setOrdersForUpdate(ordersForUpdate);
		Assert.assertNotNull(orderBasket.getOrdersForUpdate());
	}
	
	@Test
	public void testGetReleasedOrders() {
		Map<String, Order> releasedOrders = null;
		orderBasket.setReleasedOrders(releasedOrders);
		orderBasket.getReleasedOrders();
		
		Order order = new Order();
		order.setCarrierName("Name");
		releasedOrders = new HashMap<String, Order>();
		releasedOrders.put("1",order);
		orderBasket.setReleasedOrders(releasedOrders);
		Assert.assertNotNull(orderBasket.getReleasedOrders());
		//Assert.assertEquals(releasedOrders, orderBasket.getReleasedOrders());
	}
	
	@Test
	public void testGetDispatchedOrders() {
		Map<String, Order> dispatchedOrders = null;
		orderBasket.setDispatchedOrders(dispatchedOrders);
		orderBasket.getDispatchedOrders();
		
		Order order = new Order();
		order.setCarrierName("Name");
		dispatchedOrders = new HashMap<String, Order>();
		dispatchedOrders.put("1",order);
		orderBasket.setReleasedOrders(dispatchedOrders);
		Assert.assertNotNull(orderBasket.getReleasedOrders());
	}
	
	@Test
	public void testGetAllOrderDetails() {
		Map<String, Order> orderDetails = new HashMap<String, Order>();
		Order order = new Order();
		order.setOrderId("1234");
		orderDetails.put(order.getOrderId(), order);
		orderBasket.setAllOrderDetails(orderDetails);
		Assert.assertNotNull(orderBasket.getAllOrderDetails());
	}
	
	@Test
	public void testGetTransactionDetailsOfReturn(){
		HashMap<String, Transaction> transactionDetailsOfReturn = null;
		
		orderBasket.setTransactionDetailsOfReturn(transactionDetailsOfReturn );
		orderBasket.getTransactionDetailsOfReturn();
		

		Transaction transaction = new Transaction();
		transaction.setOrderId("123");
		transactionDetailsOfReturn = new HashMap<String, Transaction>();
		transactionDetailsOfReturn.put("1",transaction);
		orderBasket.setTransactionDetailsOfReturn(transactionDetailsOfReturn );
		Assert.assertNotNull(orderBasket.getTransactionDetailsOfReturn());
	}
	
	@Test
	public void testGetTransactionDetailsOfDispatchAndCancel(){
		
		HashMap<String, Transaction> transactionDetailsOfDispatchAndCancel = null;
		orderBasket.setTransactionDetailsOfDispatchAndCancel(transactionDetailsOfDispatchAndCancel );
		orderBasket.getTransactionDetailsOfDispatchAndCancel();
		

		Transaction transaction = new Transaction();
		transaction.setOrderId("123");
		transactionDetailsOfDispatchAndCancel = new HashMap<String, Transaction>();
		transactionDetailsOfDispatchAndCancel.put("1",transaction);
		orderBasket.setTransactionDetailsOfReturn(transactionDetailsOfDispatchAndCancel );
		Assert.assertNotNull(orderBasket.getTransactionDetailsOfReturn());
	}
	
	@Test
	public void testGetOriginalPendingItems(){
		
		List<Order> originalPendingItems = null;
		orderBasket.setOriginalPendingItems(originalPendingItems );
		orderBasket.getOriginalPendingItems();
		
		Order order = new Order();
		order.setCarrierName("Name");
		originalPendingItems = new ArrayList<Order>();
		originalPendingItems.add(order);
		
		orderBasket.setOriginalPendingItems(originalPendingItems );
		Assert.assertNotNull(orderBasket.getOriginalPendingItems());
	}
	
	@Test
	public void testClear(){
		orderBasket.clear();
		Assert.assertEquals(0, orderBasket.getOrdersForUpdate().size());
		Assert.assertEquals(0, orderBasket.getOriginalPendingItems().size());
		Assert.assertEquals(0, orderBasket.getReleasedOrders().size());
		Assert.assertEquals(0, orderBasket.getDispatchedOrders().size());
		Assert.assertEquals(0, orderBasket.getTransactionDetailsOfDispatchAndCancel().size());
		Assert.assertEquals(0, orderBasket.getTransactionDetailsOfReturn().size());
	}

}
