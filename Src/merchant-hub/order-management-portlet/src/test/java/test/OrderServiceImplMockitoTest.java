package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.tesco.mhub.NoSuchSystemMHubMappingException;
import com.tesco.mhub.gmo.manageft.assr.transactionresponse.TransactionResponse.TransactionDetails;
import com.tesco.mhub.order.ack.orders.AcknowledgeOrders;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.dao.OrderDAO;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderBasket;
import com.tesco.mhub.order.model.OrderLineItem;
import com.tesco.mhub.order.model.ResponseSearchOrders;
import com.tesco.mhub.order.model.Transaction;
import com.tesco.mhub.order.retrieve.order.CustomerDetailsType;
import com.tesco.mhub.order.retrieve.order.CustomerOrderType;
import com.tesco.mhub.order.retrieve.order.CustomerOrderType.OrderLines;
import com.tesco.mhub.order.retrieve.order.DeliveryDetailsType;
import com.tesco.mhub.order.retrieve.order.LineItemType;
import com.tesco.mhub.order.retrieve.order.OrderLineStatusHistoryType;
import com.tesco.mhub.order.retrieve.order.OrderStatusType;
import com.tesco.mhub.order.retrieve.order.RetrieveOrders;
import com.tesco.mhub.order.retrieve.order.ShippingAddressType;
import com.tesco.mhub.order.search.OrderSummary;
import com.tesco.mhub.order.search.SearchOrdersResponse;
import com.tesco.mhub.order.service.OrderBasketService;
import com.tesco.mhub.order.serviceimpl.OrderServiceImpl;
import com.tesco.mhub.order.util.OrderHelperUtil;
import com.tesco.mhub.order.util.OrderManagementUtil;
import com.tesco.mhub.report.feed.ProcessingReport;
import com.tesco.mhub.service.SystemMHubMappingLocalServiceUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.portlet.MockPortletConfig;
import org.springframework.mock.web.portlet.MockPortletContext;
import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.mock.web.portlet.MockPortletSession;

@RunWith(PowerMockRunner.class)
@PrepareForTest({OrderManagementUtil.class,PortalUtil.class,ParamUtil.class,OrderConstants.class,SystemMHubMappingLocalServiceUtil.class,OrderHelperUtil.class})
public class OrderServiceImplMockitoTest {

	@InjectMocks
	private OrderServiceImpl serviceImpl = new OrderServiceImpl();
	
	private PortletConfig portletConfig = null;
	private PortletContext portletContext = new MockPortletContext();
	private PortletSession session = null;
	private String portletName = "order-management";
	DateFormat dateFormats = new SimpleDateFormat("dd/MM/yyyy");
	long sellerId = 1013632l;
	Order order;
	
	@Mock
	OrderBasketService orderBasketService;
	@Mock
	OrderDAO orderDAO;
	@Mock
	ThemeDisplay themeDisplay;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		assertNotNull(serviceImpl);
		assertNotNull(portletContext);
		
		MockPortletConfig _portletConfig = new MockPortletConfig(portletContext,portletName);
		assertNotNull(_portletConfig);
		portletConfig = (PortletConfig)_portletConfig;
		
		session = new MockPortletSession();
		assertNotNull(session);
		
		session.setAttribute("requestMethod",null,PortletSession.PORTLET_SCOPE);		
		session.setAttribute("httpReferer",null,PortletSession.PORTLET_SCOPE);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetSelectedOrders() {
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		
		Date date = new Date();
		date.setDate(17/04/2014);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		XMLGregorianCalendar date2 = null;
		try {
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e1) {
			e1.printStackTrace();
		}
		
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
		
		CustomerDetailsType custDetails = new CustomerDetailsType();
		custDetails.setFirstName("Hari");
		custDetails.setLastName("Sadu");
		custDetails.setMobileNo("9810343652");
		
		DeliveryDetailsType ddt = new DeliveryDetailsType();
		ddt.setDeliveryOption("Express");
		ddt.setExpectedDeliveryDate(date2);
		ddt.setExpectedDispatchDate(date2);
		ddt.setDeliveryCharges("12");
		ddt.setCarrierName("cosmic");
		
		ShippingAddressType sat = new ShippingAddressType();
		sat.setShipToAddressLine1("jahghajgd");
		sat.setShipToAddressLine2("yuwterwer");
		sat.setShipToAddressLine3("vbnvbnv");
		sat.setShipToAddressLine4("qweqweq");
		sat.setShipToAddressLine5("cvnbcvbcv");
		sat.setShipToCity("dadar");
		sat.setShipToHomeTelDay("74365734653");
		
		OrderLineStatusHistoryType olstp = new OrderLineStatusHistoryType();
		
		OrderStatusType ost = new OrderStatusType();
		ost.setCancellationReasonCode("hgfdagsfdghsfd");
		ost.setPrimaryReturnReasonCode("hsdjfgshjdgf");
		ost.setQuantity(new BigDecimal("5"));
		ost.setDescription("shjdgfhaj");
		ost.setSecondaryReturnReasonCode("gasfdajsfdjasd");
		ost.setStatus("Point of no return");
		
		olstp.getOrderStatus().add(ost);
		
		LineItemType ltp = new LineItemType();
		ltp.setLineNumber(new BigInteger("1"));
		ltp.setItemShortDescription("hellooo");
		ltp.setRetailPrice("23");
		ltp.setUnitOfSale("each");
		ltp.setOrderedQuantity(new BigDecimal("6"));
		
		ltp.setCustomerDetails(custDetails);
		ltp.setDeliveryDetails(ddt);
		ltp.setShippingAddress(sat);
		ltp.setOrderLineStatusHistory(olstp);
		
		Map<String, Order> selectedOrders = new HashMap<String, Order>();
		selectedOrders.put("CXH2FGH-1",order);
		OrderBasket orderBasket = new OrderBasket();
		orderBasket.setAllOrderDetails(selectedOrders);
		orderBasketService.setOrderBasket(orderBasket, portletRequest);
		
		CustomerOrderType custOrder = new CustomerOrderType();
		RetrieveOrders retrieveOrders = new RetrieveOrders();
		retrieveOrders.getCustomerOrder().add(custOrder);
		custOrder.setCustomerOrderNumber("CXH2FGH");
		custOrder.setOrderDate(date2);
		OrderLines orderLines = new OrderLines();
		orderLines.getLineItem().add(ltp);
		custOrder.setOrderLines(orderLines);
		custOrder.setSellerOrderNumber("CXH2FGH-1");
	
		retrieveOrders.getCustomerOrder().add(custOrder);
				
		/*PowerMockito.mockStatic(OrderServiceTestUtil.class);
		Order order = OrderServiceTestUtil.createSimpleOrder();
		
		Map<String, Order> selectedOrders = new HashMap<String, Order>();
		selectedOrders.put("CXH2FGH-1",order);
		OrderBasket orderBasket = new OrderBasket();
		orderBasket.setAllOrderDetails(selectedOrders);
		orderBasketService.setOrderBasket(orderBasket, portletRequest);
		
		PowerMockito.mockStatic(OrderServiceTestUtil.class);
		RetrieveOrders retrieveOrders = OrderServiceTestUtil.createCustomerOrder();
		CustomerOrderType cot = retrieveOrders.getCustomerOrder().get(0);
		OrderLines ols = cot.getOrderLines();
		List<LineItemType> ListItems = ols.getLineItem();
		LineItemType ltp = ListItems.get(0);*/
		
		PowerMockito.mockStatic(SystemMHubMappingLocalServiceUtil.class);
		try {
			Mockito.when(SystemMHubMappingLocalServiceUtil.getMHubValue(OrderConstants.SYSTEM_NAME, OrderConstants.SYSTEM_ATTRIBUTE,ltp.getDeliveryDetails().getDeliveryOption())).thenReturn("Express");
		} catch (NoSuchSystemMHubMappingException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		
		String[] orderIds = {"CXH2FGH-1"};
		Mockito.when(orderBasketService.getOrderBasket(portletRequest)).thenReturn(orderBasket);
		
		try {
			Mockito.when(orderDAO.getSelectedOrders("released", orderIds, 1013632l)).thenReturn(retrieveOrders);
		} catch (NullPointerException e) {
			System.out.println("Null Pointer Exception Occurred while getting orders");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception Occurred while getting orders");
			e.printStackTrace();
		}
		
		try {
			serviceImpl.getSelectedOrders("released", orderIds, 1013632l, portletRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  Order createSimpleOrder() {
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

	public  RetrieveOrders createCustomerOrder(){
		
		Date date = new Date();
		date.setDate(17/04/2014);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		XMLGregorianCalendar date2 = null;
		try {
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e1) {
			e1.printStackTrace();
		}
		
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
		orderLineItem.setDeliveryInstructions("Leave securely at back of house");
		
		CustomerDetailsType custDetails = new CustomerDetailsType();
		custDetails.setFirstName("Hari");
		custDetails.setLastName("Sadu");
		custDetails.setMobileNo("9810343652");
		
		DeliveryDetailsType ddt = new DeliveryDetailsType();
		ddt.setDeliveryOption("Express");
		ddt.setExpectedDeliveryDate(date2);
		ddt.setExpectedDispatchDate(date2);
		ddt.setDeliveryCharges("12");
		ddt.setCarrierName("cosmic");
		ddt.setDeliveryInstructions("Leave securely at back of house");
		
		ShippingAddressType sat = new ShippingAddressType();
		sat.setShipToAddressLine1("jahghajgd");
		sat.setShipToAddressLine2("yuwterwer");
		sat.setShipToAddressLine3("vbnvbnv");
		sat.setShipToAddressLine4("qweqweq");
		sat.setShipToAddressLine5("cvnbcvbcv");
		sat.setShipToCity("dadar");
		sat.setShipToHomeTelDay("74365734653");
		
		OrderLineStatusHistoryType olstp = new OrderLineStatusHistoryType();
		
		OrderStatusType ost = new OrderStatusType();
		ost.setCancellationReasonCode("shfgsdgfsf");
		ost.setPrimaryReturnReasonCode("bgadfgajsdfgas");
		ost.setQuantity(new BigDecimal("5"));
		ost.setDescription("shjdgfhaj");
		ost.setSecondaryReturnReasonCode("sdjhfgshfjgshd");
		ost.setStatus("3200.210");
		
		olstp.getOrderStatus().add(ost);
		
		LineItemType ltp = new LineItemType();
		ltp.setLineNumber(new BigInteger("1"));
		ltp.setItemShortDescription("hellooo");
		ltp.setRetailPrice("23");
		ltp.setUnitOfSale("each");
		ltp.setOrderedQuantity(new BigDecimal("6"));
		
		ltp.setCustomerDetails(custDetails);
		ltp.setDeliveryDetails(ddt);
		ltp.setShippingAddress(sat);
		ltp.setOrderLineStatusHistory(olstp);
		
		CustomerOrderType custOrder = new CustomerOrderType();
		RetrieveOrders retrieveOrders = new RetrieveOrders();
		retrieveOrders.getCustomerOrder().add(custOrder);
		custOrder.setCustomerOrderNumber("CXH2FGH");
		custOrder.setOrderDate(date2);
		OrderLines orderLines = new OrderLines();
		orderLines.getLineItem().add(ltp);
		custOrder.setOrderLines(orderLines);
		custOrder.setSellerOrderNumber("CXH2FGH-1");
	
		retrieveOrders.getCustomerOrder().add(custOrder);

		return retrieveOrders;
	}
	
	@Test
	public void testGetOrderDetails(){
		
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		
		//create object of your pojo class
		Order order = createSimpleOrder();
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		
		
		//set ur pojo object in basket
		
		Map<String, Order> selectedOrders = new HashMap<String, Order>();
		selectedOrders.put("CXH2FGH-1",order);
		OrderBasket orderBasket = new OrderBasket();
		orderBasket.setAllOrderDetails(selectedOrders);
		orderBasket.setOrdersForUpdate(orderList);
		orderBasket.setTotalSize(1);
		orderBasketService.setOrderBasket(orderBasket, portletRequest);
		Mockito.when(orderBasketService.getOrderBasket(portletRequest)).thenReturn(orderBasket);
		
		RetrieveOrders retrieveOrders = createCustomerOrder();
		CustomerOrderType cot = retrieveOrders.getCustomerOrder().get(0);
		OrderLines ols = cot.getOrderLines();
		List<LineItemType> ListItems = ols.getLineItem();
		LineItemType ltp = ListItems.get(0);
		
		//set delivery option in service class and map it.
		PowerMockito.mockStatic(SystemMHubMappingLocalServiceUtil.class);
		try {
			Mockito.when(SystemMHubMappingLocalServiceUtil.getMHubValue(OrderConstants.SYSTEM_NAME, OrderConstants.SYSTEM_ATTRIBUTE,ltp.getDeliveryDetails().getDeliveryOption())).thenReturn("Express");
		} catch (NoSuchSystemMHubMappingException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		
		//call dao layer from mockito
		try {
			Mockito.when(orderDAO.getOrderDetails("CXH2FGH-1", 1013632l)).thenReturn(retrieveOrders);
		} catch (NullPointerException e) {
			System.out.println("Null Pointer Exception Occurred while getting orders");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception Occurred while getting orders");
			e.printStackTrace();
		}
		
		//call actual impl method to be mockito tested.
		try {
			serviceImpl.getOrderDetails("CXH2FGH-1", 1013632l, portletRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void extractOrderDetailsTest(){
		
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		
		Order order = createSimpleOrder();
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		
		Map<String, Order> selectedOrders = new HashMap<String, Order>(); 
		selectedOrders.put("CXH2FGH-1",order);
		OrderBasket orderBasket = new OrderBasket();
		orderBasket.setAllOrderDetails(selectedOrders);
		orderBasket.setOrdersForUpdate(orderList);
		orderBasket.setTotalSize(1);
		orderBasketService.setOrderBasket(orderBasket, portletRequest);
		Mockito.when(orderBasketService.getOrderBasket(portletRequest)).thenReturn(orderBasket);
		
		/*OrderStatusType ost = new OrderStatusType();
		ost.setCancellationReasonCode("bdfgajhgfd");	//check with null
		ost.setPrimaryReturnReasonCode("gsdfdsfg");		//check with null
		ost.setSecondaryReturnReasonCode("hjdgashdjgahsd"); 	//check with null	
		ost.setStatus("3200.210"); 	//check with 900 , 3700.01 , 3700
		List<OrderStatusType> statHistory = new ArrayList<OrderStatusType>();
		statHistory.add(ost);*/
		//Mockito.when(orderLineItemFromDAO.getOrderLineStatusHistory().getOrderStatus())
		
		//create object of class that u r getting from service
		RetrieveOrders retrieveOrders = createCustomerOrder();
		CustomerOrderType cot = retrieveOrders.getCustomerOrder().get(0);
		OrderLines ols = cot.getOrderLines();
		List<LineItemType> ListItems = ols.getLineItem();
		LineItemType ltp = ListItems.get(0);
		
		
		
		//set delivery option in service class and map it.
		PowerMockito.mockStatic(SystemMHubMappingLocalServiceUtil.class);
		try {
			Mockito.when(SystemMHubMappingLocalServiceUtil.getMHubValue(OrderConstants.SYSTEM_NAME, OrderConstants.SYSTEM_ATTRIBUTE,ltp.getDeliveryDetails().getDeliveryOption())).thenReturn("Express");
		} catch (NoSuchSystemMHubMappingException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		
		Order actualOrder = serviceImpl.extractOrderDetails(portletRequest, retrieveOrders.getCustomerOrder(),"CXH2FGH-1");
		
		assertEquals("Leave securely at back of house", actualOrder.getDeliveryInstructions());
	}
	
	@Test
	public void extractOrdersForDisplayTest() {
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		
		Order order = createSimpleOrder();
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		
		Map<String, Order> selectedOrders = new HashMap<String, Order>(); 
		selectedOrders.put("CXH2FGH-1",order);
		OrderBasket orderBasket = new OrderBasket();
		orderBasket.setAllOrderDetails(selectedOrders);
		orderBasket.setOrdersForUpdate(orderList);
		orderBasket.setTotalSize(1);
		orderBasketService.setOrderBasket(orderBasket, portletRequest);
		Mockito.when(orderBasketService.getOrderBasket(portletRequest)).thenReturn(orderBasket);
		
		//create object of class that u r getting from service
		RetrieveOrders retrieveOrders = createCustomerOrder();
		CustomerOrderType cot = retrieveOrders.getCustomerOrder().get(0);
		OrderLines ols = cot.getOrderLines();
		List<LineItemType> ListItems = ols.getLineItem();
		LineItemType ltp = ListItems.get(0);
		
		//set delivery option in service class and map it.
		PowerMockito.mockStatic(SystemMHubMappingLocalServiceUtil.class);
		try {
			Mockito.when(SystemMHubMappingLocalServiceUtil.getMHubValue(OrderConstants.SYSTEM_NAME, OrderConstants.SYSTEM_ATTRIBUTE,ltp.getDeliveryDetails().getDeliveryOption())).thenReturn("Express");
		} catch (NoSuchSystemMHubMappingException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		
		//call the actual method that u r testing
		try {
			serviceImpl.extractOrdersForDisplay(portletRequest, retrieveOrders.getCustomerOrder());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void extractDispatchOrdersForDisplayTest() {
		
			MockPortletRequest _portletRequest = new MockPortletRequest();
			PortletRequest portletRequest = (PortletRequest)_portletRequest;
			
			Order order = createSimpleOrder();
			List<Order> orderList = new ArrayList<Order>();
			orderList.add(order);
			
			Map<String, Order> selectedOrders = new HashMap<String, Order>(); 
			selectedOrders.put("CXH2FGH-1",order);
			OrderBasket orderBasket = new OrderBasket();
			orderBasket.setAllOrderDetails(selectedOrders);
			orderBasket.setOrdersForUpdate(orderList);
			orderBasket.setTotalSize(1);
			orderBasketService.setOrderBasket(orderBasket, portletRequest);
			Mockito.when(orderBasketService.getOrderBasket(portletRequest)).thenReturn(orderBasket);
			
			//create object of class that u r getting from service
			RetrieveOrders retrieveOrders = createCustomerOrder();
			CustomerOrderType cot = retrieveOrders.getCustomerOrder().get(0);
			OrderLines ols = cot.getOrderLines();
			List<LineItemType> ListItems = ols.getLineItem();
			LineItemType ltp = ListItems.get(0);
			
			//set delivery option in service class and map it.
			PowerMockito.mockStatic(SystemMHubMappingLocalServiceUtil.class);
			try {
				Mockito.when(SystemMHubMappingLocalServiceUtil.getMHubValue(OrderConstants.SYSTEM_NAME, OrderConstants.SYSTEM_ATTRIBUTE,ltp.getDeliveryDetails().getDeliveryOption())).thenReturn("Express");
			} catch (NoSuchSystemMHubMappingException e1) {
				e1.printStackTrace();
			} catch (SystemException e1) {
				e1.printStackTrace();
			}
			
			//call the actual method that u r testing
			try {
				serviceImpl.extractOrdersForDispatchDisplay(portletRequest, retrieveOrders.getCustomerOrder());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	@Test
	public void getTransactionStatusOfOrderTest(){
		
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		
		Order order = createSimpleOrder();
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		
		Map<String, Order> selectedOrders = new HashMap<String, Order>(); 
		selectedOrders.put("CXH2FGH-1",order);
		OrderBasket orderBasket = new OrderBasket();
		orderBasket.setAllOrderDetails(selectedOrders);
		orderBasket.setOrdersForUpdate(orderList);
		orderBasket.setTotalSize(1);
		orderBasketService.setOrderBasket(orderBasket, portletRequest);
		Mockito.when(orderBasketService.getOrderBasket(portletRequest)).thenReturn(orderBasket);
		
		
		String txID = "ghagdfagsd5wjhgegfw67gsfdgjdf";
		
		//set the order for transaction ---- in dispatch and cancel map
		
		HashMap<String, Transaction> transactionDetailsOfDispatchAndCancel = new HashMap<String, Transaction>();
		TransactionDetails txDetails = new TransactionDetails();
		txDetails.setTransactionType("Dispatch");
		
		
		Transaction transaction = new Transaction();
		transaction.setOldOrder(order);
		transaction.setOrderId("CXH2FGH-1");
		transaction.setTransactionId(txID);
		transaction.setTransactionStatus("Succeeded");
	
		transaction.setTransactionType("dispatch");
		transactionDetailsOfDispatchAndCancel.put("CXH2FGH-1" , transaction);
		//new line --------------> put transaction object into map. 
		orderBasket.setTransactionDetailsOfDispatchAndCancel(transactionDetailsOfDispatchAndCancel );
		
		//new line --------------> haven't put transaction object into map. 
		HashMap<String, Transaction> transactionDetailsOfReturn = new HashMap<String, Transaction>();
		orderBasket.setTransactionDetailsOfReturn(transactionDetailsOfReturn );
		
		
		ProcessingReport processingReport = new ProcessingReport();
		processingReport.setTransactionCode("200");
		processingReport.setTransactionStatus("Succeeded");
		//processingReport.setTransactionDetails(txDetails);
		
		//Mockito.when(transaction.getTransactionId()).thenReturn(txID);
		System.out.println(" Transaction = " + transaction );
		
		PowerMockito.mockStatic(OrderManagementUtil.class);
		Mockito.when(OrderManagementUtil.getSellerId(portletRequest)).thenReturn(sellerId);
		
		try {
			Mockito.when(orderDAO.getTransactionProcessingReport(txID)).thenReturn(processingReport);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		serviceImpl.getTransactionStatusOfOrder(transaction, portletRequest);
		
	}

	@Test
	public void getSearchOrdersTest(){
		
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		
		int pageNumber = 1;
		String customerName = "Hari Sadu";
		Date releasedDate = new Date();
		String deliveryOption = "Express";
		String statusForSearch = "cancelled";
		Date date = new Date();
		date.setDate(17/04/2014);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		XMLGregorianCalendar date2 = null;
		try {
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e1) {
			e1.printStackTrace();
		}
		SearchOrdersResponse respSearchOrders = new SearchOrdersResponse();
		List<SearchOrdersResponse.Order> responseOrderList = new ArrayList<SearchOrdersResponse.Order>();
		OrderSummary orderSummary = new OrderSummary();
		SearchOrdersResponse.Order order = new SearchOrdersResponse.Order();
		
		Order simpleOrder = createSimpleOrder();
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(simpleOrder);
		
		HashMap<String, Transaction> transactionDetailsOfDispatchAndCancel = new HashMap<String, Transaction>();
		TransactionDetails txDetails = new TransactionDetails();
		txDetails.setTransactionType("Dispatch");
		
		String txID = "ghagdfagsd5wjhgegfw67gsfdgjdf";
		Transaction transaction = new Transaction();
		transaction.setOldOrder(simpleOrder);
		transaction.setOrderId("CXH2FGH-1");
		transaction.setTransactionId(txID);
		transaction.setTransactionStatus("Succeeded");
	
		transaction.setTransactionType("dispatch");
		transactionDetailsOfDispatchAndCancel.put("CXH2FGH-1" , transaction);
		
		Map<String, Order> selectedOrders = new HashMap<String, Order>(); 
		selectedOrders.put("CXH2FGH-1",simpleOrder);
		OrderBasket orderBasket = new OrderBasket();
		orderBasket.setAllOrderDetails(selectedOrders);
		orderBasket.setOrdersForUpdate(orderList);
		orderBasket.setTotalSize(1);
		orderBasket.setTransactionDetailsOfDispatchAndCancel(transactionDetailsOfDispatchAndCancel);
		orderBasketService.setOrderBasket(orderBasket, portletRequest);
		Mockito.when(orderBasketService.getOrderBasket(portletRequest)).thenReturn(orderBasket);
		
		orderSummary.setBillToName(customerName);
		orderSummary.setOrderID("CSX2HFG-1");
		orderSummary.setReleaseOrderNumber("CSX2HFG-1");
		orderSummary.setReleasedDate(date2);
		orderSummary.setCancelledDate(date2);
		orderSummary.setDispatchedDate(date2);
		orderSummary.setReturnedDate(date2);
		orderSummary.setExpectedDispatchDate(date2);
		orderSummary.setDeliveryOption(deliveryOption);
		orderSummary.setStatus("Partially cancelled");
		orderSummary.setTotalNumberOfLines(new BigInteger("5"));
		orderSummary.setTotalNumberOfLinesOpen(new BigInteger("0"));
		
		order.setOrderSummary(orderSummary);
		
		responseOrderList.add(order);
		respSearchOrders.setPageNumber(new BigInteger("1"));
		respSearchOrders.setPageSize(new BigInteger("1"));
		respSearchOrders.setTotalNumberOfOrders(new BigInteger("1"));
		respSearchOrders.getOrder().add(order);
		
		PowerMockito.mockStatic(OrderManagementUtil.class);
		Mockito.when(OrderManagementUtil.getSellerId(portletRequest)).thenReturn(sellerId);
		try {
			Mockito.when(orderDAO.getSearchResult(statusForSearch, releasedDate, releasedDate, "ReleasedDate", "ReleasedDate-Asc", deliveryOption, customerName, sellerId, pageNumber)).thenReturn(respSearchOrders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		serviceImpl.getSearchOrders(statusForSearch, releasedDate, releasedDate, "ReleasedDate", "ReleasedDate-Asc", deliveryOption, customerName, sellerId, pageNumber, portletRequest);
	}
	
	@Test
	public void getOrderDetailsTest() throws Exception{
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		
		String orderId = "CXH2FGH-1";
		
		Order simpleOrder = createSimpleOrder();
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(simpleOrder);
		Map<String, Order> selectedOrders = new HashMap<String, Order>(); 
		selectedOrders.put("CXH2FGH-1",simpleOrder);
		
		OrderBasket orderBasket = new OrderBasket();
		orderBasket.setAllOrderDetails(selectedOrders);
		orderBasket.setOrdersForUpdate(orderList);
		orderBasket.setTotalSize(1);
		orderBasketService.setOrderBasket(orderBasket, portletRequest);
		Mockito.when(orderBasketService.getOrderBasket(portletRequest)).thenReturn(orderBasket);
		
		PowerMockito.mockStatic(OrderManagementUtil.class);
		Mockito.when(OrderManagementUtil.getSellerId(portletRequest)).thenReturn(sellerId);
		
		RetrieveOrders selectedOrderDetailsFromService = createCustomerOrder();
		CustomerOrderType cot = selectedOrderDetailsFromService.getCustomerOrder().get(0);
		OrderLines ols = cot.getOrderLines();
		List<LineItemType> ListItems = ols.getLineItem();
		LineItemType ltp = ListItems.get(0);
		
		//set delivery option in service class and map it.
		PowerMockito.mockStatic(SystemMHubMappingLocalServiceUtil.class);
		try {
			Mockito.when(SystemMHubMappingLocalServiceUtil.getMHubValue(OrderConstants.SYSTEM_NAME, OrderConstants.SYSTEM_ATTRIBUTE,ltp.getDeliveryDetails().getDeliveryOption())).thenReturn("Express");
		} catch (NoSuchSystemMHubMappingException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		
		Mockito.when(orderDAO.getOrderDetails(orderId, sellerId)).thenReturn(selectedOrderDetailsFromService);
		
		serviceImpl.getOrderDetails(orderId, sellerId, portletRequest);
	}
	
	/*@Test 
	public void getReleasedOrdersTest() throws Exception{
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		session = new MockPortletSession();
		assertNotNull(session);
		
		Order order = createSimpleOrder();
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		
		Map<String, Order> selectedOrders = new HashMap<String, Order>(); 
		selectedOrders.put("CXH2FGH-1",order);
		OrderBasket orderBasket = new OrderBasket();
		orderBasket.setAllOrderDetails(selectedOrders);
		orderBasket.setOrdersForUpdate(orderList);
		orderBasket.setTotalSize(1);
		orderBasketService.setOrderBasket(orderBasket, portletRequest);
		Mockito.when(orderBasketService.getOrderBasket(portletRequest)).thenReturn(orderBasket);
		Date startDate = new Date();
		Date endDate = new Date();
		
		//create object of class that u r getting from service
		RetrieveOrders retrieveOrders = createCustomerOrder();
		CustomerOrderType cot = retrieveOrders.getCustomerOrder().get(0);
		OrderLines ols = cot.getOrderLines();
		List<LineItemType> ListItems = ols.getLineItem();
		LineItemType ltp = ListItems.get(0);
		Mockito.when(orderDAO.getReleasedOrders(startDate, endDate, sellerId)).thenReturn(retrieveOrders);
		//Mockito.when(retrieveOrders.getCustomerOrder()).thenReturn(releasedOrdersFromDAO);
		
		//set delivery option in service class and map it.
		PowerMockito.mockStatic(SystemMHubMappingLocalServiceUtil.class);
		try {
			Mockito.when(SystemMHubMappingLocalServiceUtil.getMHubValue(OrderConstants.SYSTEM_NAME, OrderConstants.SYSTEM_ATTRIBUTE,ltp.getDeliveryDetails().getDeliveryOption())).thenReturn("Express");
		} catch (NoSuchSystemMHubMappingException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		
		serviceImpl.getReleasedOrders(startDate, endDate, sellerId, portletRequest);
		
		System.out.println(">>>>>>>>>>>>>>>>>CASE 2");
		
		session.setAttribute("currentSellerIdInDispatch", "76253462");
		PowerMockito.mockStatic(OrderManagementUtil.class);
		Mockito.when(OrderManagementUtil.getSellerId(portletRequest)).thenReturn(sellerId);
		
		serviceImpl.getReleasedOrders(startDate, endDate, sellerId, portletRequest);
	}
*/
	@Test
	public void getDispatchedOrdersTest() throws Exception{
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		session = new MockPortletSession();
		assertNotNull(session);
		
		Order order = createSimpleOrder();
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		
		Map<String, Order> selectedOrders = new HashMap<String, Order>(); 
		selectedOrders.put("CXH2FGH-1",order);
		OrderBasket orderBasket = new OrderBasket();
		orderBasket.setAllOrderDetails(selectedOrders);
		orderBasket.setOrdersForUpdate(orderList);
		orderBasket.setTotalSize(1);
		orderBasketService.setOrderBasket(orderBasket, portletRequest);
		Mockito.when(orderBasketService.getOrderBasket(portletRequest)).thenReturn(orderBasket);
		Date startDate = new Date();
		Date endDate = new Date();
		
		//create object of class that u r getting from service
		RetrieveOrders retrieveOrders = createCustomerOrder();
		CustomerOrderType cot = retrieveOrders.getCustomerOrder().get(0);
		OrderLines ols = cot.getOrderLines();
		List<LineItemType> ListItems = ols.getLineItem();
		LineItemType ltp = ListItems.get(0);
		Mockito.when(orderDAO.getDispatchedOrders(startDate, endDate, sellerId)).thenReturn(retrieveOrders);
		//Mockito.when(retrieveOrders.getCustomerOrder()).thenReturn(releasedOrdersFromDAO);
		
		//set delivery option in service class and map it.
		PowerMockito.mockStatic(SystemMHubMappingLocalServiceUtil.class);
		try {
			Mockito.when(SystemMHubMappingLocalServiceUtil.getMHubValue(OrderConstants.SYSTEM_NAME, OrderConstants.SYSTEM_ATTRIBUTE,ltp.getDeliveryDetails().getDeliveryOption())).thenReturn("Express");
		} catch (NoSuchSystemMHubMappingException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		
		serviceImpl.getDispatchedOrders(startDate, endDate, sellerId, portletRequest);
		
		session.setAttribute("currentSellerIdInDispatch", "76253462");
		PowerMockito.mockStatic(OrderManagementUtil.class);
		Mockito.when(OrderManagementUtil.getSellerId(portletRequest)).thenReturn(sellerId);
		
		serviceImpl.getDispatchedOrders(startDate, endDate, sellerId, portletRequest);
		
		
	}
	
	@Test
	public void getProcessingReportTest() throws Exception{
		
		String transactionId = "hjasgdjhas5svfjgfdg6jhsfjksdhjf";
		ProcessingReport processingReport = new ProcessingReport();
		processingReport.setTransactionCode("200");
		processingReport.setTransactionStatus("Succeeded");
		
		Mockito.when(orderDAO.getTransactionProcessingReport(transactionId)).thenReturn(processingReport);
		
		serviceImpl.getProcessingReport(transactionId);
		
		processingReport.setTransactionCode("200");
		processingReport.setTransactionStatus("PartiallySucceeded");
		
		serviceImpl.getProcessingReport(transactionId);
		
		processingReport.setTransactionCode("400");
		processingReport.setTransactionStatus("Failed");
		
		serviceImpl.getProcessingReport(transactionId);
		
		processingReport.setTransactionCode("202");
		processingReport.setTransactionStatus("IN PROGRESS");
		
		serviceImpl.getProcessingReport(transactionId);
		
		processingReport.setTransactionCode("500");
		
		serviceImpl.getProcessingReport(transactionId);
	}
	
	@Test
	public void cancelOrdersTest() throws Exception{
		
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		
		String acknowledgeOrdersTransactionId = "jhsdfgash67hjasgdhjasgd";
		AcknowledgeOrders acknowledgeOrders = new AcknowledgeOrders();
		
		PowerMockito.mockStatic(OrderHelperUtil.class);
		
		Order order = createSimpleOrder();
		List<Order> orderList = new ArrayList<Order>();
		Map<Integer, OrderLineItem> orderLineItemDetailsMapFromBaskt = order.getOrderLineItems();
		orderList.add(order);
		
		PowerMockito.mockStatic(OrderManagementUtil.class);
		Mockito.when(OrderManagementUtil.getSellerId(portletRequest)).thenReturn(sellerId);
		
		Mockito.when(OrderHelperUtil.isValidList(orderList)).thenReturn(true);	//check with false
		Mockito.when(OrderHelperUtil.isValidMap(orderLineItemDetailsMapFromBaskt)).thenReturn(true);	//check with false
		Mockito.when(orderDAO.cancelOrders(acknowledgeOrders, sellerId)).thenReturn(acknowledgeOrdersTransactionId);
		
		serviceImpl.cancelOrders(sellerId, orderList);
	}
	
	@Test
	public void testMergeOrder(){
		Order testOrdr = createSimpleOrder();
		List<Order> ordLst = new ArrayList<Order>();
		ordLst.add(testOrdr);
		List<Order> mergedOrdLst = serviceImpl.mergeOrders(ordLst);
		Assert.assertNotNull("testMergeOrder test case failed",mergedOrdLst);
		
	}
	@Test
	public void testGetOrderList() throws Exception{
		
		String orderId = "CXH2FGH";
		String expStatus = "Open";
		
		long sellerId = 1000005;
		MockPortletRequest portletRequest = new MockPortletRequest();
		PowerMockito.mockStatic(SystemMHubMappingLocalServiceUtil.class);
		Order order = createSimpleOrder();
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		
		Map<String, Order> selectedOrders = new HashMap<String, Order>();
		selectedOrders.put(orderId,order);
		OrderBasket orderBasket = new OrderBasket();
		orderBasket.setAllOrderDetails(selectedOrders);
		orderBasket.setOrdersForUpdate(null);
		orderBasket.setTotalSize(1);
		orderBasketService.setOrderBasket(orderBasket, portletRequest);
		Mockito.when(orderBasketService.getOrderBasket(portletRequest)).thenReturn(orderBasket);
		
		RetrieveOrders retrieveOrders = createCustomerOrder();

		CustomerOrderType cot = retrieveOrders.getCustomerOrder().get(0);
		OrderLines ols = cot.getOrderLines();
		List<LineItemType> ListItems = ols.getLineItem();
		LineItemType ltp = ListItems.get(0);
		Mockito.when(SystemMHubMappingLocalServiceUtil.getMHubValue(OrderConstants.SYSTEM_NAME, OrderConstants.SYSTEM_ATTRIBUTE,ltp.getDeliveryDetails().getDeliveryOption())).thenReturn("Express");
		
		Mockito.when(orderDAO.getOrderDetails(orderId, sellerId)).thenReturn(retrieveOrders);
	
		ResponseSearchOrders mySearchOrder = serviceImpl.getOrderList(orderId, sellerId, portletRequest);
		
		assertEquals(orderId, mySearchOrder.getResponseOrderList().get(0).getOrderSummary().getOrderId());
		assertEquals(expStatus, mySearchOrder.getResponseOrderList().get(0).getOrderSummary().getStatus());
	}
}
