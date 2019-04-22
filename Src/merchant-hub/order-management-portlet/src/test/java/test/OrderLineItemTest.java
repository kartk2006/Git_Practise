package test;

import com.tesco.mhub.order.model.OrderLineItem;



import junit.framework.Assert;

import org.junit.Test;

public class OrderLineItemTest {
	
	OrderLineItem orderLineItem = new OrderLineItem();

	@Test
	public void testGetOrderLineNumber() {
		orderLineItem.setOrderLineNumber(1234);
		Assert.assertEquals(1234, orderLineItem.getOrderLineNumber());
	}

	@Test
	public void testGetSku() {
		orderLineItem.setSku("xyz");
		Assert.assertEquals("xyz", orderLineItem.getSku());
	}
	
	@Test
	public void testGetDescription() {
		orderLineItem.setDescription("xyz");
		Assert.assertEquals("xyz", orderLineItem.getDescription());
	}

	@Test
	public void testGetLineItemStatus() {
		orderLineItem.setLineItemStatus("xyz");
		Assert.assertEquals("xyz", orderLineItem.getLineItemStatus());
	}

	@Test
	public void testGetOrderedQuantity() {
		orderLineItem.setOrderedQuantity(1234.0);
		Assert.assertEquals(1234.0, orderLineItem.getOrderedQuantity());
	}

	@Test
	public void testGetOpenQuantity() {
		orderLineItem.setOpenQuantity(1234.0);
		Assert.assertEquals(1234.0, orderLineItem.getOpenQuantity());
	}

	@Test
	public void testGetDispatchedQuantity() {
		orderLineItem.setDispatchedQuantity(1234.0);
		Assert.assertEquals(1234.0, orderLineItem.getDispatchedQuantity());
	}
	
	@Test
	public void testGetReturnedQuantity() {
		orderLineItem.setReturnedQuantity(1234.0);
		Assert.assertEquals(1234.0, orderLineItem.getReturnedQuantity());
	}

	@Test
	public void testGetCancelledQuantity() {
		orderLineItem.setCancelledQuantity(1234.0);
		Assert.assertEquals(1234.0, orderLineItem.getCancelledQuantity());
	}

	@Test
	public void testGetPrimaryReasonForCancellation() {
		orderLineItem.setPrimaryReasonForCancellation("xyz");
		Assert.assertEquals("xyz", orderLineItem.getPrimaryReasonForCancellation());
	}

	@Test
	public void testGetSecondaryReasonForCancellation() {
		orderLineItem.setSecondaryReasonForReturn("xyz");
		Assert.assertEquals("xyz", orderLineItem.getSecondaryReasonForReturn());
	}

	@Test
	public void testGetReasonForReturn() {
		orderLineItem.setReasonForReturn("xyz");
		Assert.assertEquals("xyz", orderLineItem.getReasonForReturn());
	}

	@Test
	public void testGetCostPerItem() {
		orderLineItem.setCostPerItem(1234d);
		Assert.assertEquals(1234d, orderLineItem.getCostPerItem());
	}
	
	@Test
	public void testGetRefundAmount() {
		orderLineItem.setRefundAmount(1234d);
		Assert.assertEquals(1234d, orderLineItem.getRefundAmount());
	}

	@Test
	public void testGetType() {
		orderLineItem.setType("xyz");
		Assert.assertEquals("xyz", orderLineItem.getType());
	}
	
	@Test
	public void testGetServiceActionType() {
		orderLineItem.setServiceActionType("xyz");
		Assert.assertEquals("xyz", orderLineItem.getServiceActionType());
	}
	
	@Test
	public void testGetUnitOfSale() {
		orderLineItem.setUnitOfSale("xyz");
		Assert.assertEquals("xyz", orderLineItem.getUnitOfSale());
	}
	
	@Test
	public void testGetShipToCity() {
		orderLineItem.setShipToCity("xyz");
		Assert.assertEquals("xyz", orderLineItem.getShipToCity());
	}
	
	@Test
	public void testGetShipToCountry() {
		orderLineItem.setShipToCountry("xyz");
		Assert.assertEquals("xyz", orderLineItem.getShipToCountry());
	}
	
	@Test
	public void testGetShipToPostcode() {
		orderLineItem.setShipToPostcode("xyz");
		Assert.assertEquals("xyz", orderLineItem.getShipToPostcode());
	}
	
	@Test
	public void testGetShipToFirstName() {
		orderLineItem.setShipToFirstName("xyz");
		Assert.assertEquals("xyz", orderLineItem.getShipToFirstName());
	}
	
	@Test
	public void testGetShipToLastName() {
		orderLineItem.setShipToLastName("xyz");
		Assert.assertEquals("xyz", orderLineItem.getShipToLastName());
	}

	@Test
	public void testGetshipingAddress1() {
		orderLineItem.setShipingAddress1("xyz");
		Assert.assertEquals("xyz", orderLineItem.getShipingAddress1());
	}
	
	@Test
	public void testGetshipingAddress2() {
		orderLineItem.setShipingAddress2("xyz");
		Assert.assertEquals("xyz", orderLineItem.getShipingAddress2());
	}
}

