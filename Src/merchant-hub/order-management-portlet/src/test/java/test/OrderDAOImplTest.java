/*
package test;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.tesco.mhub.gmo.deliveryoption.request.TradingPartner;
import com.tesco.mhub.gmo.managetp.carrierDetails.CarrierAttributes;
import com.tesco.mhub.order.ack.orders.AcknowledgeOrders;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.dao.OrderDAO;
import com.tesco.mhub.order.daoimpl.OrderDAOImpl;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderLineItem;
import com.tesco.mhub.order.retrieve.order.RetrieveOrders;
import com.tesco.mhub.order.returns.refunds.Return;
import com.tesco.mhub.order.returns.refunds.ReturnRefunds;
import com.tesco.mhub.order.returns.refunds.ReturnRequestLines;
import com.tesco.mhub.order.search.SearchOrdersResponse;
import com.tesco.mhub.order.service.OrderService;
import com.tesco.mhub.order.shipment.update.AddressDetails;
import com.tesco.mhub.order.shipment.update.CarrierDetails;
import com.tesco.mhub.order.shipment.update.Shipment;
import com.tesco.mhub.order.shipment.update.ShipmentLine;
import com.tesco.mhub.order.shipment.update.ShipmentLines;
import com.tesco.mhub.order.shipment.update.ShipmentUpdate;
import com.tesco.mhub.order.shipment.update.ShipmentUpdates;
import com.tesco.mhub.order.shipment.update.Shipments;
import com.tesco.mhub.order.util.OrderHelperUtil;
import com.tesco.mhub.order.util.PortletProps;
import com.tesco.mhub.report.feed.ProcessingReport;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job.Segment.Carrier;
import com.tesco.mhub.yodl.response.Ndxml;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class OrderDAOImplTest {

	
	
	@Deployment
	public static WebArchive createDeploymentWar() {
	    File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve("org.springframework:spring-context:3.2.6.RELEASE",
	    		"org.springframework:spring-web:3.2.6.RELEASE",
	    		"com.liferay.portal:portal-service:6.1.30",
	    		"org.springframework:spring-webmvc-portlet:3.2.6.RELEASE",
	    		"com.liferay.portal:util-bridges:6.1.30",
	    		"com.liferay.portal:util-taglib:6.1.30",
	    		"com.liferay.portal:util-java:6.1.30").withTransitivity().asFile();
	    WebArchive war = ShrinkWrap.create(WebArchive.class, "OrderDAOImplTest.war")
				.addClasses(OrderDAOImpl.class,LogFactoryUtil.class,OrderConstants.class,PortletProps.class
						,RetrieveOrders.class,OrderDAO.class,Shipment.class,PropsUtil.class,com.tesco.mhub.order.util.DateUtil.class)
				.addClass(com.tesco.mhub.order.util.OrderHelperUtil.class)
				.addClass(com.tesco.mhub.order.util.DecryptPassword.class)
				.addClass(CarrierAttributes.class)
				.addClass(TradingPartner.class)
				.addClass(Ndxml.class)
				.addClass(Order.class)
				.addClass(com.tesco.mhub.order.util.OMResponseErrorHandler.class)
				.addAsResource("portlet.properties")
				.addAsResource("portal.properties")
				.addAsLibraries(libs)
	            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				// this is needed to make the test jar use Liferay portal
				// depndencies
				.addAsManifestResource("jboss-deployment-structure.xml");
	    return war;
	}
	OrderDAOImpl orderDAOImpl;
	private Log logs = LogFactoryUtil.getLog(getClass());
	@Autowired
	private OrderDAO orderDao;
	@Autowired
	private OrderService orderService;
	@Before
	public void setUp() throws Exception {
		orderDAOImpl = new OrderDAOImpl();
	}

	*//**
	 * @throws java.lang.Exception
	 *//*
	@After
	public void tearDown() throws Exception {
		orderDAOImpl = null;
	}
	
	@Test
	public void testGetReleasedOrders() throws Exception {
		try{
		Date startDate = new Date();
		startDate.setDate(startDate.getDate() - 7);
		Date endDate = new Date();
		long sellerId = 1000004;
		RestTemplate restTemplate = new RestTemplate();
		
		RetrieveOrders retrieveOrders = orderDAOImpl.getReleasedOrders(startDate, endDate, sellerId);
		Assert.assertNotNull(retrieveOrders);
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	@Test
	public void testGetDispatchedOrders() throws Exception {
		try{
		System.out.println("coming to testGetDispatchedOrders().....");
		Date startDate = new Date();
		startDate.setDate(startDate.getDate() - 7);
		logs.info("startdate ::"+startDate);
		Date endDate = new Date();
		logs.info("enddate ::"+endDate);
		long sellerId = 1000004;
		
		RetrieveOrders retrieveOrders = orderDAOImpl.getDispatchedOrders(startDate, endDate, sellerId);
		Assert.assertNotNull(retrieveOrders);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSelectedOrders() throws Exception {
		try{
		System.out.println("coming to testGetSelectedOrders().....");
		String status = "released";
		String[] orderIds = {"CCNHCXF-1" , "CCNHCQJ-1"};
		long sellerId = 1013632;
		
		RetrieveOrders selectedOrders = orderDAOImpl.getSelectedOrders(status, orderIds, sellerId);
		Assert.assertNotNull(selectedOrders);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetOrderDetails() throws Exception {
		try{
		System.out.println("coming to testGetOrderDetails().....");
		String status = "released";
		Date startDate = new Date();
		startDate.setDate(startDate.getDate() - 60);
		System.out.println("startdate ::"+startDate);
		Date endDate = new Date();
		System.out.println("enddate ::"+endDate);
		String filterDateBy = "ReleasedDate";
		String sortBy = "ReleasedDate";
		String deliveryOption = "MKTPLACE_Express";
		String customerName = "test";
		long sellerId = 1013632;
		int pageNumber = 10;
		
		SearchOrdersResponse searchOrderResponse = orderDAOImpl.getSearchResult(status, startDate, endDate, filterDateBy, sortBy, deliveryOption, customerName, sellerId, pageNumber);
		//Assert.assertNotNull(searchOrderResponse);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSearchResult() throws Exception {
		try{
		System.out.println("coming to testGetSearchResult().....");
		String status = "released";
		String orderId = "AS4WERT";
		long sellerId = 1013632;
		
		RetrieveOrders orderDetails = orderDAOImpl.getOrderDetails(orderId, sellerId);
		//Assert.assertNotNull(orderDetails);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTransactionProcessingReport() throws Exception{
		
		String transactionId = "123";	
		String uri = OrderHelperUtil.getProcessingReportURI(transactionId);	
		
		ProcessingReport actualProcessingReport = orderDAOImpl.getTransactionProcessingReport(transactionId);
		System.out.println("processing report ::"+actualProcessingReport);
		//Assert.assertNotNull(actualProcessingReport);
		
	}
	
	@Test
	public void testCancelOrders() throws Exception {
		try{
		AcknowledgeOrders acknowledgeOrders = new AcknowledgeOrders();
		long sellerId = 1013632;
		RestTemplate restTemplate = new RestTemplate();
		String uri = OrderHelperUtil.getAcknowledgeOrdersURI(sellerId);	
		List<com.tesco.mhub.order.ack.orders.OrderLine> orderLine = null ;
		acknowledgeOrders.setOrderLine(orderLine);
		
		String actualResponse = orderDAOImpl.cancelOrders(acknowledgeOrders, sellerId);
		//Assert.assertNotNull(actualResponse);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDispatchOrders() throws Exception {
		try{
		
		long sellerId = 1013632;
		List<Shipment> shipmentlist = new ArrayList<Shipment>();
		List<ShipmentLine> shipmentLineList = new ArrayList<ShipmentLine>();
		List<ShipmentUpdate> shipmentUpdatesList = new ArrayList<ShipmentUpdate>();
		
		ShipmentUpdates shipmentUpdates = new ShipmentUpdates();
		ShipmentUpdate shipmentUpdate = new ShipmentUpdate();
		Shipments shipments = new Shipments();
		Shipment shipment = new Shipment();	
					
	
			ShipmentLines shipmentLines = new ShipmentLines();
			shipmentUpdate.setSellerOrderNumber("xxxxxx");
			shipmentUpdate.setCustomerOrderNumber("yyyyy");
			shipmentUpdate.setSellerId(String.valueOf(sellerId));
			shipments.setIsConsolidatedParcel("");
			shipments.setOuterCaseTrackingNumber("N");
									
			CarrierDetails carrierDetails = new CarrierDetails();
			AddressDetails addressDetails = new AddressDetails();																						
			
			//shipment.setDispatchDate(DateUtil.asXMLGregorianCalendar(new Date()));
			shipment.setParcelTrackingNumber("aaaaaaaa");
			shipment.setTotalWeight(new BigDecimal(1.0));
			shipment.setTotalWeightUOM("2e214234");																															
            
			carrierDetails.setCartonNumber("");
			carrierDetails.setIsTFSUsed("N");
			carrierDetails.setThirdPartyCarrierName("Cosmic Courier Services");
			carrierDetails.setCarrierUrl("http://www.cosmiccouriers.com");
		    shipment.setCarrierDetails(carrierDetails);
		    
		    addressDetails.setFirstName("adssee");
			addressDetails.setLastName("vbfvfg");
			addressDetails.setPostCode("AL7 1TW");
			addressDetails.setHomePhone("07979867698");
			shipment.setAddressDetails(addressDetails);	
			
			
					ShipmentLine shipmentLine = new ShipmentLine();	
					shipmentLine.setOrderLineNumber("1");
					shipmentLine.setQuantity(new BigDecimal(1.0));
					shipmentLine.setShipmentLineNo("1");
					shipmentLine.setSubLineNo("1");
					shipmentLine.setUnitOfMeasure("Each");																	
					shipment.setShipmentLines(shipmentLines);
					shipmentLineList.add(shipmentLine);
				shipmentUpdate.setShipments(shipments);										
				shipments.setShipment(shipmentlist);
				
				shipmentLines.setShipmentLine(shipmentLineList);
				shipmentlist.add(shipment);
			shipmentUpdatesList.add(shipmentUpdate);
		shipmentUpdates.setShipmentUpdate(shipmentUpdatesList);
		
		RestTemplate restTemplate = new RestTemplate();
		String uri = OrderHelperUtil.getShipmentUpdatesOrdersURI(sellerId);
				
		String actualResponse = orderDAOImpl.dispatchOrders(shipmentUpdates, sellerId);
		//Assert.assertNotNull(actualResponse);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReturnOrders() throws Exception {
		try{
		long sellerId = 1013632;
		ReturnRefunds returnRefunds = new ReturnRefunds();
		Return return1 = null;
		ReturnRequestLines returnRequestLines = new ReturnRequestLines();
		returnRequestLines.setComments("xyx");
		returnRequestLines.setOrderPrimeLineNo(123);
		returnRequestLines.setPrimaryReasonCode("xyx");
		returnRequestLines.setRefundAmount(new BigDecimal(123));
		returnRequestLines.setSecondaryReasonCode("xyx");
		List<ReturnRequestLines> returnRequestLinesList = new ArrayList<ReturnRequestLines>();
		List<Return> rList = new ArrayList<Return>();
		return1.setOrderId("xyx");
		return1.setReturnOption("xyx");
		return1.setReturnRequestLines(returnRequestLinesList);
		return1.setSellerId("xyx");
		return1.setSource("xyx");
		rList.add(return1);	
		RestTemplate restTemplate = new RestTemplate();
		String uri = OrderHelperUtil.getReturnRefundsOrderURI(sellerId);
		returnRefunds.set_return(rList);
		String actualResponse = orderDAOImpl.returnOrders(returnRefunds, sellerId);
		//Assert.assertNotNull(actualResponse);
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
	public void testgetYodlLabel(){
		Order order = createSimpleOrder();
		long sellerId = 1000005;
		TradingPartner tp = null;
		CarrierAttributes ca = null;
		
		Date pickUpDate = new Date();
		Ndxml yodelResp  = new Ndxml();
		try {
			ca = orderService.getCarrierAttributes(sellerId);
			tp = orderService.getSellerById(sellerId);
			yodelResp = orderDao.getYodlLabel(order.getOrderId(), sellerId, tp, pickUpDate, ca, order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(yodelResp);
	}

	@Test
	public void testGetSellerId(){
		long sellerId = 1000005;
		TradingPartner tp = new TradingPartner();
		try {
			tp = orderDao.getSellerById(sellerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(tp);
	}
	
	@Test
	public void testGetCarrrierAttributes(){
		long sellerId = 1000005;
		CarrierAttributes ca = new CarrierAttributes();
		try {
			ca = orderDao.getCarrierAttributesById(sellerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}*/
