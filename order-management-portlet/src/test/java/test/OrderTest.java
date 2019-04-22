package test;

import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderLineItem;

import java.util.Date;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class OrderTest {
	
	Order order = new Order();
	
	@Test
	public void testGetOrderId() {
		order.setOrderId("xyz");
		Assert.assertEquals("xyz", order.getOrderId());
	}
	
	@Test
	public void testGetCustomerOrderNumber() {
		order.setCustomerOrderNumber("xyz");
		Assert.assertEquals("xyz", order.getCustomerOrderNumber());
	}
	
	@Test
	public void testGetCustomerFirstName() {
		order.setCustomerFirstName("xyz");
		Assert.assertEquals("xyz", order.getCustomerFirstName());
	}
	@Test
	public void testGetCustomerLastName() {
		order.setCustomerLastName("xyz");
		Assert.assertEquals("xyz", order.getCustomerLastName());
	}	
	
	@Test
	public void testGetNoOfLines() {
		order.setNoOfLines(10);
		Assert.assertEquals(10, order.getNoOfLines());
	}
	
	@Test
	public void testGetOrderStatus() {
		order.setOrderStatus("xyz");
		Assert.assertEquals("xyz", order.getOrderStatus());
	}
	
	@Test
	public void testGetDeliveryOption() {
		order.setDeliveryOption("xyz");
		Assert.assertEquals("xyz", order.getDeliveryOption());
	}
	
	@Test
	public void testGetOrderPlacedDate() {
		Date orderPlacedDate = new Date();
		order.setOrderPlacedDate(orderPlacedDate);
		Assert.assertEquals(orderPlacedDate, order.getOrderPlacedDate());
	}
	
	@Test
	public void testGetCustomerEmail() {
		order.setCustomerEmail("xyz");
		Assert.assertEquals("xyz", order.getCustomerEmail());
	}
	
	@Test
	public void testGetCustomerMobile() {
		order.setCustomerMobile("xyz");
		Assert.assertEquals("xyz", order.getCustomerMobile());
	}
	
	@Test
	public void testGetCustomerDayTelephone() {
		order.setCustomerDayTelephone("xyz");
		Assert.assertEquals("xyz", order.getCustomerDayTelephone());
	}
	
	@Test
	public void testGetCustomerEveningTelephone() {
		order.setCustomerEveningTelephone("xyz");
		Assert.assertEquals("xyz", order.getCustomerEveningTelephone());
	}
	
	@Test
	public void testGetDeliveryCost() {
		order.setDeliveryCost(1234.0);
		Assert.assertEquals(1234.0, order.getDeliveryCost());
	}
	
	@Test
	public void testGetShipToAddress() {
		order.setShipToAddress("xyz");
		Assert.assertEquals("xyz", order.getShipToAddress());
	}
	
	@Test
	public void testGetShipToMobile() {
		order.setShipToMobile("xyz");
		Assert.assertEquals("xyz", order.getShipToMobile());
	}
	
	@Test
	public void testGetOrderValue() {
		order.setOrderValue(1234.0);
		Assert.assertEquals(1234.0, order.getOrderValue());
	}
	
	@Test
	public void testGetOrderLineItems() {
		Map<Integer, OrderLineItem> orderLineItems = null;
		order.setOrderLineItems(orderLineItems);
		Assert.assertEquals(orderLineItems, order.getOrderLineItems());
	}
	
	
	
	@Test
	public void testGetServiceActionType() {
		order.setServiceActionType("xyz");
		Assert.assertEquals("xyz", order.getServiceActionType());
	}
	
	@Test
	public void testGetShipToFName() {
		order.setShipToFName("xyz");
		Assert.assertEquals("xyz", order.getShipToFName());
	}
	
	@Test
	public void testGetShipToLName() {
		order.setShipToLName("xyz");
		Assert.assertEquals("xyz", order.getShipToLName());
	}
	
	@Test
	public void testGetTrackingId() {
		order.setTrackingId("xyz");
		Assert.assertEquals("xyz", order.getTrackingId());
	}
	
	@Test
	public void testGetTrackingURL() {
		order.setTrackingURL("xyz");
		Assert.assertEquals("xyz", order.getTrackingURL());
	}
	
	@Test
	public void testGetCarrierName() {
		order.setCarrierName("xyz");
		Assert.assertEquals("xyz", order.getCarrierName());
	}
	
	@Test
	public void testExpectedDispatchDate() {
		Date expectedDispatchDate = new Date();
		order.setExpectedDispatchDate(expectedDispatchDate);
		Assert.assertEquals(expectedDispatchDate, order.getExpectedDispatchDate());
	}
	
	
}
