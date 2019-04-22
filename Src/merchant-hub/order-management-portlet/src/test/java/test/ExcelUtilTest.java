package test;
/*

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.tesco.mhub.order.util.PortletProps;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.dao.OrderDAO;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderExt;
import com.tesco.mhub.order.model.OrderLineItem;
import com.tesco.mhub.order.model.OrderLineItemExt;
import com.tesco.mhub.order.retrieve.order.RetrieveOrders;
import com.tesco.mhub.order.retrieve.order.Shipment;
import com.tesco.mhub.order.util.ExcelUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class) */
public class ExcelUtilTest {
	/*
	ExcelUtil excelUtil = null;
	
	@Deployment
	public static WebArchive createDeploymentWar() {
	    File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve("org.springframework:spring-context:3.2.6.RELEASE",
	    		"org.springframework:spring-web:3.2.6.RELEASE",
	    		"com.liferay.portal:portal-service:6.1.30",
	    		"org.springframework:spring-webmvc-portlet:3.2.6.RELEASE",
	    		"com.liferay.portal:util-bridges:6.1.30",
	    		"com.liferay.portal:util-taglib:6.1.30",
	    		"com.liferay.portal:util-java:6.1.30",
	    		"poi:poi:3.6").withTransitivity().asFile();
	    WebArchive war = ShrinkWrap.create(WebArchive.class, "ExcelUtilTest.war")
				.addClasses(ExcelUtil.class,LogFactoryUtil.class,OrderConstants.class,PortletProps.class
						,RetrieveOrders.class,OrderDAO.class,Shipment.class,PropsUtil.class)
				.addClass(com.tesco.mhub.order.util.OrderHelperUtil.class)
				.addClass(FileUtil.class)
				.addClass(com.tesco.mhub.order.model.Order.class)
				.addClass(com.tesco.mhub.order.model.OrderLineItem.class)
				.addClass(OrderLineItemExt.class)
				.addClass(com.tesco.mhub.order.model.OrderExt.class)
				.addAsLibraries(libs)
	            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				// this is needed to make the test jar use Liferay portal
				// depndencies
				.addAsManifestResource("jboss-deployment-structure.xml");
	    return war;
	}
	
	@Before
	public void setUp() throws Exception {
		excelUtil = new ExcelUtil() {
		};
	}

	*//**
	 * @throws java.lang.Exception
	 *//*
	@After
	public void tearDown() throws Exception {
		excelUtil = null;
	}
	
	*//**
	 * logger object.
	 *//*
	private static Log log = LogFactoryUtil.getLog(ExcelUtil.class);
	
	@Test
	public void testWriteFile() {
		File expectedFile = null;
		try{
		
		Order order = new Order();
		order.setOrderId("xyz");
		order.setCustomerOrderNumber("xyz");
		order.setCustomerFirstName("xyz");
		order.setCustomerLastName("xyz");
		order.setNoOfLines(10);
		order.setOrderStatus("xyz");
		order.setDeliveryOption("xyz");
		Date orderPlacedDate = new Date();
		order.setOrderPlacedDate(orderPlacedDate);
		order.setCustomerEmail("xyz");
		order.setCustomerMobile("xyz");
		order.setCustomerDayTelephone("xyz");
		order.setCustomerEveningTelephone("xyz");
		order.setDeliveryCost(1234.0);
		order.setShipToAddress("xyz");
		order.setShipToMobile("xyz");
		order.setOrderValue(1234.0);
		order.setServiceActionType("xyz");
		order.setTrackingId("xyz");
		order.setTrackingURL("xyz");
		order.setCarrierName("xyz");
		OrderLineItem orderLineItem = new OrderLineItem();
		orderLineItem.setCancelledQuantity(123l);
		orderLineItem.setCostPerItem(123l);
		orderLineItem.setDescription("xyx");
		orderLineItem.setDispatchedQuantity(123l);
		orderLineItem.setLineItemStatus("Live");
		orderLineItem.setOpenQuantity(1234l);
		orderLineItem.setOrderedQuantity(1234l);
		orderLineItem.setOrderLineNumber(123);
		orderLineItem.setPrimaryReasonForCancellation("xyx");
		orderLineItem.setReasonForReturn("xyx");
		orderLineItem.setRefundAmount(123l);
		orderLineItem.setReturnedQuantity(123l);
		orderLineItem.setSecondaryReasonForReturn("xyx");
		orderLineItem.setServiceActionType("xyx");
		orderLineItem.setShipToCity("xyx");
		orderLineItem.setShipToCountry("xyx");
		orderLineItem.setShipToFirstName("FirstName");
		orderLineItem.setLineItemStatus("trwtrs");
		orderLineItem.setDispatchedQuantity(1.0);
		orderLineItem.setShipToLastName("LastName");
		orderLineItem.setShipToPostcode("dfd12");
		orderLineItem.setSku("sku");
		orderLineItem.setType("xtyx");
		orderLineItem.setUnitOfSale("12sfs");
		orderLineItem.setShipingAddress1("wqerwre");
		orderLineItem.setShipingAddress2("dsfgttdrgfgh");
		Map<Integer, OrderLineItem> orderLineItems = new HashMap<Integer, OrderLineItem>();
		orderLineItems.put(1, orderLineItem);
		order.setOrderLineItems(orderLineItems);
		
		
		List<Order> orderList = new ArrayList<Order>();

		orderList.add(order);

		expectedFile = ExcelUtil.writeFile(orderList);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Assert.assertNotNull(expectedFile);
	}*/

}
