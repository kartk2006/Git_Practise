package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import com.tesco.mhub.order.model.OrderLineItemExt;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class OrderLineItemExtTest {
	
	
	
	OrderLineItemExt orderLineItemExt = new OrderLineItemExt();

	@Test
	public void testBundleItemLineNumber() {
		orderLineItemExt.setBundleItemLineNumber("635467623");
		Assert.assertEquals("635467623", orderLineItemExt.getBundleItemLineNumber());
	}

	@Test
	public void testInstallationLineNumber() {
		orderLineItemExt.setInstallationLineNumber("6546254");
		Assert.assertEquals("6546254", orderLineItemExt.getInstallationLineNumber());
	}

	@Test
	public void testIsGiftWrappedOrder() {
		orderLineItemExt.setIsGiftWrappedOrder("Y");
		Assert.assertEquals("Y", orderLineItemExt.getIsGiftWrappedOrder());
	}
	
	//giftMessage
	
	@Test
	public void testGiftMessage() {
		orderLineItemExt.setGiftMessage("Happy Birth day!!!!");
		Assert.assertEquals("Happy Birth day!!!!", orderLineItemExt.getGiftMessage());
	}
	
	//shipingAddress3
	
	@Test
	public void testShipingAddress3() {
		orderLineItemExt.setShipingAddress3("Ramnagar");
		Assert.assertEquals("Ramnagar", orderLineItemExt.getShipingAddress3());
	}
	
	//shipingAddress4
	
	@Test
	public void testShipingAddress4() {
		orderLineItemExt.setShipingAddress4("Hosmat Hospital");
		Assert.assertEquals("Hosmat Hospital", orderLineItemExt.getShipingAddress4());
	}
	
	//shipingAddress5
	
	@Test
	public void testShipingAddress5() {
		orderLineItemExt.setShipingAddress5("Murgesh Palya");
		Assert.assertEquals("Murgesh Palya", orderLineItemExt.getShipingAddress5());
	}
	
	//shipingAddress6
	
	@Test
	public void testShipingAddress6() {
		orderLineItemExt.setShipingAddress6("Domlur");
		Assert.assertEquals("Domlur", orderLineItemExt.getShipingAddress6());
	}
	
	
	//shipToCounty
	@Test
	public void testShipToCounty() {
		orderLineItemExt.setShipToCounty("GB");
		Assert.assertEquals("GB", orderLineItemExt.getShipToCounty());
	}
	
	//ShipToStoreNumber
	@Test
	public void testShipToStoreNumber() {
		orderLineItemExt.setShipToStoreNumber("JN01");
		Assert.assertEquals("JN01", orderLineItemExt.getShipToStoreNumber());
	}
	
	//ShipToStoreName
	@Test
	public void testShipToStoreName() {
		orderLineItemExt.setShipToStoreName("Mother's Choice");
		Assert.assertEquals("Mother's Choice", orderLineItemExt.getShipToStoreName());
	}
	
	//CarrierName
	@Test
	public void testCarrierName() {
		orderLineItemExt.setCarrierName("Carrier");
		Assert.assertEquals("Carrier", orderLineItemExt.getCarrierName());
	}
	
	//CarrierServiceCode
	@Test
	public void testCarrierServiceCode() {
		orderLineItemExt.setCarrierServiceCode("123");
		Assert.assertEquals("123", orderLineItemExt.getCarrierServiceCode());
	}
	
	//ExpectedDeliveryDate
	@Test
	public void testExpectedDeliveryDate() {
		Date expectedDeliveryDate = new Date();
		orderLineItemExt.setExpectedDeliveryDate(expectedDeliveryDate);
		Assert.assertEquals(expectedDeliveryDate, orderLineItemExt.getExpectedDeliveryDate());
	}
	
	//MinimumAgeRequired
	@Test
	public void testMinimumAgeRequired() {
		orderLineItemExt.setMinimumAgeRequired("18");
		Assert.assertEquals("18", orderLineItemExt.getMinimumAgeRequired());
	}
	
	//IsPODRequired
	@Test
	public void testIsPODRequired() {
		orderLineItemExt.setIsPODRequired("N");
		Assert.assertEquals("N", orderLineItemExt.getIsPODRequired());
	}
	
	//DeliveryInstructions
	@Test
	public void testDeliveryInstructions() {
		orderLineItemExt.setDeliveryInstructions("Deliver at doorstep");
		Assert.assertEquals("Deliver at doorstep", orderLineItemExt.getDeliveryInstructions());
	}
	
	//ShipToHomeTelDay
		@Test
		public void testShipToHomeTelDay() {
			orderLineItemExt.setShipToHomeTelDay("62534662354");
			Assert.assertEquals("62534662354", orderLineItemExt.getShipToHomeTelDay());
		}
		
	//ShipToHomeTelNight
		@Test
		public void testShipToHomeTelNight() {
			orderLineItemExt.setShipToHomeTelNight("62534662354");
			Assert.assertEquals("62534662354", orderLineItemExt.getShipToHomeTelNight());
		}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
}
