/*package test;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.tesco.mhub.gmo.deliveryoption.request.TradingPartner;
import com.tesco.mhub.gmo.managetp.carrierDetails.CarrierAttributes;
import com.tesco.mhub.order.util.PortletProps;
import com.tesco.mhub.order.ack.orders.AcknowledgeOrders;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.dao.OrderDAO;
import com.tesco.mhub.order.daoimpl.OrderDAOImpl;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderBasket;
import com.tesco.mhub.order.model.OrderLineItem;
import com.tesco.mhub.order.model.OrderLineItemExt;
import com.tesco.mhub.order.retrieve.order.RetrieveOrders;
import com.tesco.mhub.order.service.OrderBasketService;
import com.tesco.mhub.order.service.OrderService;
import com.tesco.mhub.order.serviceimpl.OrderServiceImpl;
import com.tesco.mhub.order.util.ExcelUtil;
import com.tesco.mhub.order.util.OrderHelperUtil;
import com.tesco.mhub.yodl.response.Ndxml;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.WindowState;
import javax.servlet.http.Cookie;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@RunWith(Arquillian.class)
public class OrderServiceImplTest {*/
	
	//OrderServiceImpl orderServiceImpl = null;
	/**
	 * order dao object.
	 */
	/*@Autowired
	private OrderDAO orderDAO;*/
	/**
	 * Order service object.
	 */
	/*@Autowired
	private OrderService orderService;*/
	/**
	 * order basket service object.
	 */
/*	@Autowired
	private OrderBasketService orderBasketService;*/

	/**
	 * logger object.
	 */
	//private Log log = LogFactoryUtil.getLog(getClass());
	/*
	
	@Deployment
	public static WebArchive createDeployment() {

	// adding empty beans.xml is required as Arquillian uses CDI
		 File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve("org.springframework:spring-context:3.2.6.RELEASE",
		    		"org.springframework:spring-web:3.2.6.RELEASE",
		    		"com.liferay.portal:portal-service:6.1.30",
		    		"org.springframework:spring-webmvc-portlet:3.2.6.RELEASE",
		    		"com.liferay.portal:util-bridges:6.1.30",
		    		"com.liferay.portal:util-taglib:6.1.30",
		    		"com.liferay.portal:util-java:6.1.30").withTransitivity().asFile();

		 WebArchive appDeployable = ShrinkWrap.create(WebArchive.class, "OrderServiceImplTest.war")
				.addClass(LogFactoryUtil.class)
				.addClass(OrderConstants.class)
				.addClass(PortletProps.class)
				.addClass(RetrieveOrders.class)
				.addClass(PortletRequest.class)
				.addClasses(com.tesco.mhub.order.model.OrderLineItem.class,com.tesco.mhub.order.util.ExcelUtil.class
						,com.tesco.mhub.order.util.OrderHelperUtil.class,com.tesco.mhub.order.util.DateUtil.class
						,OrderDAOImpl.class,AcknowledgeOrders.class)
				.addClass(com.tesco.mhub.order.service.OrderService.class)
				.addClass(com.tesco.mhub.order.service.OrderBasketService.class)
				.addClass(com.tesco.mhub.order.serviceimpl.OrderServiceImpl.class)
				.addClass(com.tesco.mhub.order.retrieve.order.CustomerDetailsType.class)
				.addClass(com.tesco.mhub.order.retrieve.order.CustomerOrderType.class)
				.addClass(com.tesco.mhub.order.retrieve.order.Shipments.class)
				.addClass(com.tesco.mhub.order.retrieve.order.LineItemType.class)
				.addClass(com.tesco.mhub.order.model.OrderExt.class)
				.addClass(java.util.Date.class)
				.addClass(com.tesco.mhub.yodl.response.Ndxml.class)
				.addClass(com.tesco.mhub.gmo.deliveryoption.request.TradingPartner.class)
				.addClass(com.tesco.mhub.gmo.managetp.carrierDetails.CarrierAttributes.class)
				.addClass(com.tesco.mhub.order.dao.OrderDAO.class)
				.addClass(com.tesco.mhub.order.model.Order.class)
				.addClass(com.tesco.mhub.order.model.OrderBasket.class)
				.addClass(com.tesco.mhub.order.shipment.update.ShipmentUpdates.class)
				.addClass(OrderLineItemExt.class)
				.addAsResource("portlet.properties")
				.addAsLibraries(libs)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				// this is needed to make the test jar use Liferay portal
				// depndencies
				.addAsManifestResource("jboss-deployment-structure.xml");

		return appDeployable;
	}
	
	
	@Before
	public void setUp() throws Exception {
		orderServiceImpl = new OrderServiceImpl();
	}

	*//**
	 * @throws java.lang.Exception
	 *//*
	@After
	public void tearDown() throws Exception {
		orderServiceImpl = null;
	}
	
	@Test
	public void testGetReleasedOrdersDateWise() throws Exception {
		try{
		Date startDate = new Date();
		startDate.setDate(startDate.getDate() - 7);
		Date endDate = new Date();
		long sellerId = 1000033;
		PortletRequest portletRequest = new PortletRequest() {
			
			@Override
			public void setAttribute(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeAttribute(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isWindowStateAllowed(WindowState arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isUserInRole(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isSecure() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isRequestedSessionIdValid() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isPortletModeAllowed(PortletMode arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public WindowState getWindowState() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getWindowID() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Principal getUserPrincipal() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getServerPort() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getServerName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getScheme() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getResponseContentTypes() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getResponseContentType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRequestedSessionId() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRemoteUser() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String[]> getPublicParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getPropertyNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getProperty(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getProperties(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String[]> getPrivateParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletPreferences getPreferences() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletSession getPortletSession(boolean arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletSession getPortletSession() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletMode getPortletMode() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortalContext getPortalContext() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String[] getParameterValues(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getParameterNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String[]> getParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getParameter(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<Locale> getLocales() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Locale getLocale() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Cookie[] getCookies() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContextPath() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getAuthType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getAttribute(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		//orderServiceImpl.getReleasedOrdersDateWise(startDate, endDate, sellerId, portletRequest);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
	
	@Test
	public void testGetProcessingReport() throws Exception {
		try{
		String expectedString = null;
		String transactionId = "wqertegt-1232dsgf";
		OrderBasket orderBasket = new OrderBasket();
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
		orderLineItem.setShipToLastName("LastName");
		orderLineItem.setShipToPostcode("dfd12");
		orderLineItem.setSku("sku");
		orderLineItem.setType("xtyx");
		orderLineItem.setUnitOfSale("12sfs");
		Map<Integer, OrderLineItem> orderLineItems = new HashMap<Integer, OrderLineItem>();
		orderLineItems.put(1, orderLineItem);
		Map<String, Order> dispatchedOrders = new HashMap<String, Order>();
		dispatchedOrders.put("1", order);
		order.setOrderLineItems(orderLineItems);
		orderBasket.setDispatchedOrders(dispatchedOrders);
		List<Order> ordersForUpdate = new ArrayList<Order>();
		ordersForUpdate.add(order);
		orderBasket.setOrdersForUpdate(ordersForUpdate);
		Map<String, Order> releasedOrders = new HashMap<String, Order>();
		dispatchedOrders.put("1", order);
		orderBasket.setReleasedOrders(releasedOrders);
		orderBasket.setTotalSize(10);
		
		expectedString =  StringPool.BLANK;
		
		String actualString = orderServiceImpl.getProcessingReport(transactionId);
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	@Test
	public void testExportOrderDetails() {
		
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
		orderLineItem.setShipToLastName("LastName");
		orderLineItem.setShipToPostcode("dfd12");
		orderLineItem.setSku("sku");
		orderLineItem.setType("xtyx");
		orderLineItem.setUnitOfSale("12sfs");
		
		Map<Integer, OrderLineItem> orderLineItems = new HashMap<Integer, OrderLineItem>();
		orderLineItems.put(1, orderLineItem);
		order.setOrderLineItems(orderLineItems);
		
		
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		File expectedFile = FileUtil.createTempFile();
		log.debug("inside exportOrderDetails() of service");
		expectedFile = ExcelUtil.writeFile(orderList);
		
		File actualFile = orderServiceImpl.exportOrderDetails(orderList);
		
	}
	
	//@Test
	public void testCancelOrders() throws Exception{
		try{
		long sellerId = 1013632;
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
		orderLineItem.setShipToLastName("LastName");
		orderLineItem.setShipToPostcode("dfd12");
		orderLineItem.setSku("sku");
		orderLineItem.setType("xtyx");
		orderLineItem.setUnitOfSale("12sfs");
		
		Map<Integer, OrderLineItem> orderLineItems = new HashMap<Integer, OrderLineItem>();
		orderLineItems.put(1, orderLineItem);
		order.setOrderLineItems(orderLineItems);
		
		
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		OrderDAOImpl orderDAOImpl = new OrderDAOImpl();
		AcknowledgeOrders acknowledgeOrders = new AcknowledgeOrders();
		RestTemplate restTemplate = new RestTemplate();
		String uri = OrderHelperUtil.getAcknowledgeOrdersURI(sellerId);	
		List<com.tesco.mhub.order.ack.orders.OrderLine> orderLine = null ;
		acknowledgeOrders.setOrderLine(orderLine);
		orderDAOImpl.cancelOrders(acknowledgeOrders, sellerId);
		
		orderServiceImpl.cancelOrders(sellerId, orderList);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	 public void testDispatchOrders() throws Exception{
		
		try{
		long sellerId = 1013632;
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
		orderLineItem.setShipToLastName("LastName");
		orderLineItem.setShipToPostcode("dfd12");
		orderLineItem.setSku("sku");
		orderLineItem.setType("xtyx");
		orderLineItem.setUnitOfSale("12sfs");
		
		Map<Integer, OrderLineItem> orderLineItems = new HashMap<Integer, OrderLineItem>();
		orderLineItems.put(1, orderLineItem);
		order.setOrderLineItems(orderLineItems);
		
		
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		
		orderServiceImpl.dispatchOrders(sellerId, orderList);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReturnOrders(){
		try{
			long sellerId = 1013632;
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
			orderLineItem.setShipToLastName("LastName");
			orderLineItem.setShipToPostcode("dfd12");
			orderLineItem.setSku("sku");
			orderLineItem.setType("xtyx");
			orderLineItem.setUnitOfSale("12sfs");
			
			Map<Integer, OrderLineItem> orderLineItems = new HashMap<Integer, OrderLineItem>();
			orderLineItems.put(1, orderLineItem);
			order.setOrderLineItems(orderLineItems);
			
			
			List<Order> orderList = new ArrayList<Order>();
			orderList.add(order);
			
			orderServiceImpl.returnOrders(sellerId, orderList);
			}catch(Exception e){
				e.printStackTrace();
			}
		
	}
	
	public Order createSimpleOrder(){
		Order order = new Order();
		order.setOrderId("CXH2FGH-1");
		order.setCustomerOrderNumber("CXH2FGH");
		order.setCustomerFirstName("John");
		order.setCustomerLastName("Smith");
		order.setNoOfLines(4);
		order.setOrderStatus("released");
		order.setDeliveryOption("Express");
		Date orderPlacedDate = new Date();
		order.setOrderPlacedDate(orderPlacedDate);
		order.setCustomerEmail("john@smith.com");
		order.setCustomerMobile("987124356");
		order.setCustomerDayTelephone("98745612");
		order.setCustomerEveningTelephone("98745613");
		order.setDeliveryCost(13.33);
		order.setShipToAddress("Kasturba Bhavan,Haji Ali Road,Mumbai");
		order.setShipToMobile("956874123");
		order.setOrderValue(53.33);
		order.setServiceActionType("Dispatch");
		order.setTrackingId("track001");
		order.setTrackingURL("track001");
		order.setCarrierName("bluedart");
		OrderLineItem orderLineItem = new OrderLineItem();
		orderLineItem.setCancelledQuantity(2l);
		orderLineItem.setCostPerItem(10l);
		orderLineItem.setDescription("LG super mixer");
		orderLineItem.setDispatchedQuantity(3l);
		orderLineItem.setLineItemStatus("Released");
		orderLineItem.setOpenQuantity(3l);
		orderLineItem.setOrderedQuantity(5l);
		orderLineItem.setOrderLineNumber(1);
		orderLineItem.setPrimaryReasonForCancellation("Faulty Product");
		orderLineItem.setReasonForReturn("Damaged product");
		orderLineItem.setRefundAmount(20l);
		orderLineItem.setReturnedQuantity(0l);
		orderLineItem.setSecondaryReasonForReturn("");
		orderLineItem.setServiceActionType("Dispatch");
		orderLineItem.setShipToCity("Mumbai");
		orderLineItem.setShipToCountry("India");
		orderLineItem.setShipToFirstName("Dhananjay");
		orderLineItem.setLineItemStatus("Dispatched");
		orderLineItem.setDispatchedQuantity(3.0);
		orderLineItem.setShipToLastName("Dongre");
		orderLineItem.setShipToPostcode("400031");
		orderLineItem.setSku("LGSuperMixer");
		orderLineItem.setType("xyz");
		orderLineItem.setUnitOfSale("each");
		orderLineItem.setShipingAddress1("Kasturba bhavan");
		orderLineItem.setShipingAddress2("Haji Ali Road");
		
		Map<Integer, OrderLineItem> orderLineItems = new HashMap<Integer, OrderLineItem>();
		orderLineItems.put(1, orderLineItem);
		order.setOrderLineItems(orderLineItems);
		
		return order;
	}
	
	@Test
	public void testCreateYodlLabel(){
		Order order = createSimpleOrder();
		long sellerId = 1000005;
		TradingPartner tp = null;
		CarrierAttributes ca = null;
		
		Date pickUpDate = new Date();
		Ndxml yodelResp  = new Ndxml();
		try {
			ca = orderService.getCarrierAttributes(sellerId);
			tp = orderService.getSellerById(sellerId);
			yodelResp = orderService.createYodlLabel(sellerId, order.getOrderId(), tp, pickUpDate, ca, order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertNotNull(yodelResp);
	}
	
	@Test
	public void testGetBySellerId(){
		long sellerId = 1000005;
		TradingPartner tp = new TradingPartner();
		try {
			tp = orderService.getSellerById(sellerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(tp);
	}
	
	@Test
	public void testGetCarrierAttributes(){
		long sellerId = 1000005;
		CarrierAttributes ca = new CarrierAttributes();
		try {
			ca = orderService.getCarrierAttributes(sellerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertNotNull(ca);
	}

	
}*/
