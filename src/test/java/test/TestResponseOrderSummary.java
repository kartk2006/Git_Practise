/**
 * 
 */
package test;

import static org.junit.Assert.*;

import com.tesco.mhub.order.model.ResponseOrderSummary;

import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author TE50
 *
 */
public class TestResponseOrderSummary {
	
	ResponseOrderSummary order = new ResponseOrderSummary();
	
	/**
	 * orderId.
	 */
	@Test
	public void testGetOrderId() {
		order.setOrderId("CHFN2FE");
		Assert.assertEquals("CHFN2FE", order.getOrderId());
	}

	/**
	 * releaseOrderNumber.
	 */
	@Test
	public void testGetReleaseOrderNumber() {
		order.setReleaseOrderNumber("CHFN2FE");
		Assert.assertEquals("CHFN2FE", order.getReleaseOrderNumber());
	}
	
	/**
	 * totalLineItems.
	 */
	@Test
	public void testGetTotalLineItems() {
		order.setTotalLineItems(10);
		Assert.assertEquals(10, order.getTotalLineItems());
	}
	
	/**
	 * totalOpenLineItems.
	 */
	@Test
	public void testGetTotalOpenLineItems() {
		order.setTotalOpenLineItems(3);
		Assert.assertEquals(3, order.getTotalOpenLineItems());
	}
	
	/**
	 * status.
	 */
	@Test
	public void testGetStatus() {
		order.setStatus("returned");
		Assert.assertEquals("returned", order.getStatus());
	}
	
	/**
	 * deliveryOption.
	 */
	@Test
	public void testGetDeliveryOption() {
		order.setDeliveryOption("MKTPLACE_STANDARD");
		Assert.assertEquals("MKTPLACE_STANDARD", order.getDeliveryOption());
	}
	
	/**
	 * releasedDate.
	 */
	@Test
	public void testGetReleasedDate() {
		Date orderReleasedDate = new Date();
		order.setReleasedDate(orderReleasedDate);
		Assert.assertEquals(orderReleasedDate, order.getReleasedDate());
	}
	
	
	
	/**
	 * dispatchedDate.
	 */
	@Test
	public void testGetDispatchedDate() {
		Date dispatchedDate = new Date();
		order.setDispatchedDate(dispatchedDate);
		Assert.assertEquals(dispatchedDate, order.getDispatchedDate());
	}
	
	/**
	 * cancelledDate.
	 */
	@Test
	public void testGetCancelledDate() {
		Date cancelledDate = new Date();
		order.setCancelledDate(cancelledDate);
		Assert.assertEquals(cancelledDate, order.getCancelledDate());
	}
	
	/**
	 * returnedDate.
	 */
	@Test
	public void testGetReturnedDate() {
		Date returnedDate = new Date();
		order.setReturnedDate(returnedDate);
		Assert.assertEquals(returnedDate, order.getReturnedDate());
	}
	
	/**
	 * expectedDispatchDate.
	 */
	@Test
	public void testGetExpectedDispatchedDate() {
		Date expectedDispatchedDate = new Date();
		order.setExpectedDispatchDate(expectedDispatchedDate);
		Assert.assertEquals(expectedDispatchedDate, order.getExpectedDispatchDate());
	}
	
	/**
	 * expectedShipmentDate.
	 */
	@Test
	public void testGetExpectedShipmentDate() {
		Date expectedShipmentDate = new Date();
		order.setExpectedShipmentDate(expectedShipmentDate);
		Assert.assertEquals(expectedShipmentDate, order.getExpectedShipmentDate());
	}
	
	/**
	 * billToName.
	 */
	@Test
	public void testGetBillToName() {
		order.setBillToName("Gopal");
		Assert.assertEquals("Gopal", order.getBillToName());
	}	
	
	/**
	 * shipToName.
	 */
	@Test
	public void testGetShipToName() {
		order.setShipToName("Mukhergi");
		Assert.assertEquals("Mukhergi", order.getShipToName());
	}
		
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
