/*package test;

import static org.junit.Assert.assertNotNull;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderBasket;
import com.tesco.mhub.order.model.OrderLineItem;
import com.tesco.mhub.order.model.Transaction;
import com.tesco.mhub.order.portlet.OrderDetailsController;
import com.tesco.mhub.order.service.OrderBasketService;
import com.tesco.mhub.order.service.OrderService;
import com.tesco.mhub.order.util.OrderManagementUtil;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
import org.springframework.beans.BeanUtils;
import org.springframework.mock.web.portlet.MockActionRequest;
import org.springframework.mock.web.portlet.MockActionResponse;
import org.springframework.mock.web.portlet.MockPortletConfig;
import org.springframework.mock.web.portlet.MockPortletContext;
import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.mock.web.portlet.MockPortletSession;
import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;
import org.springframework.ui.Model;

@RunWith(PowerMockRunner.class)
@PrepareForTest({OrderManagementUtil.class,PortalUtil.class,ParamUtil.class,BeanUtils.class})
public class OrderDetailsControllerTest {

	@InjectMocks
	private OrderDetailsController portlet = new OrderDetailsController();
	
	private PortletConfig portletConfig = null;
	private PortletContext portletContext = new MockPortletContext();
	private PortletSession session = null;
	private String portletName = "order-management";
	DateFormat dateFormats = new SimpleDateFormat("dd/MM/yyyy");
	long sellerId = 1013632l;
	Order order;
	@Mock
	OrderService orderService;
	@Mock
	OrderBasketService orderBasketService;
	@Mock
	ThemeDisplay themeDisplay;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		assertNotNull(portlet);
		assertNotNull(portletContext);
		
		MockPortletConfig _portletConfig = new MockPortletConfig(portletContext,portletName);
		assertNotNull(_portletConfig);
		portletConfig = (PortletConfig)_portletConfig;
		
		session = new MockPortletSession();
		assertNotNull(session);
		
		session.setAttribute("requestMethod",null,PortletSession.PORTLET_SCOPE);		
		session.setAttribute("httpReferer",null,PortletSession.PORTLET_SCOPE);
		
		order = new Order();
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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void renderOrderDetailsTest()
	throws PortletException, IOException, JSONException
	{
		MockRenderRequest _request = new MockRenderRequest();
		_request.setSession(session);
		RenderRequest renderRequest = (RenderRequest)_request;
		assertNotNull(renderRequest);
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		RenderResponse renderResponse = new MockRenderResponse();
		
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
		
		OrderBasket orderBasket = new OrderBasket();
		Map<String, Order> dispatchedOrders = new HashMap<String, Order>();
		orderBasket.setDispatchedOrders(dispatchedOrders );
		
		List<Order> originalPendingItems = new ArrayList<Order>();
		originalPendingItems.add(order);
		orderBasket.setOriginalPendingItems(originalPendingItems);
		
		orderBasket.setTotalSize(10);
		
		HashMap<String, Transaction> transactionDetailsOfDispatchAndCancel = new HashMap<String, Transaction>();
		Transaction transaction = new Transaction();
		transaction.setOldOrder(order);
		transaction.setOrderId("CXHFGH");
		transaction.setTransactionId("afe54sdfsdfg20sdfhdgf2dfg5");
		transaction.setTransactionStatus("succeeded");
		transaction.setTransactionType("dispatch");
		transactionDetailsOfDispatchAndCancel.put("CXHFGH" , transaction);
		//new line --------------> put transaction object into map. 
		orderBasket.setTransactionDetailsOfDispatchAndCancel(transactionDetailsOfDispatchAndCancel );
		
		//new line --------------> haven't put transaction object into map. 
		HashMap<String, Transaction> transactionDetailsOfReturn = new HashMap<String, Transaction>();
		
		orderBasket.setTransactionDetailsOfReturn(transactionDetailsOfReturn );
		
		PowerMockito.mockStatic(OrderManagementUtil.class);
		Mockito.when(OrderManagementUtil.getSellerId(portletRequest)).thenReturn(sellerId);
		
		MockActionRequest _actionRequest = new MockActionRequest();
		_actionRequest.setSession(session);
		ActionRequest actionRequest = (ActionRequest)_actionRequest;
		assertNotNull(actionRequest);
		Mockito.when(orderBasketService.getOrderBasket(portletRequest)).thenReturn(orderBasket);
		portlet.renderOrderDetails(portletRequest);

	}

	@Test
	public void renderQuickDispatchOrdersTest(){
		MockRenderRequest _request = new MockRenderRequest();
		_request.setSession(session);
		RenderRequest renderRequest = (RenderRequest)_request;
		assertNotNull(renderRequest);
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		Model model = PowerMockito.mock(Model.class);
		RenderResponse renderResponse = new MockRenderResponse();
		PowerMockito.mockStatic(OrderManagementUtil.class);
		String[] selectedOrderNumbers = {"CH4HFQH-1"};
		PowerMockito.mockStatic(OrderManagementUtil.class);
		Mockito.when(OrderManagementUtil.getSellerId(renderRequest)).thenReturn(sellerId);
		//Mockito.when(renderRequest.getPortletSession().getAttribute("SELECTED_TAB")).thenReturn("released");
		portlet.renderQuickDispatchOpenOrders(selectedOrderNumbers, renderRequest, renderResponse);
	}
	
	@Test
	public void actionUpdateOrderTest() throws PortalException, SystemException, IOException{
		MockRenderRequest _request = new MockRenderRequest();
		_request.setSession(session);
		RenderRequest renderRequest = (RenderRequest)_request;
		assertNotNull(renderRequest);
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		//OrderBasket orderBasket = new OrderBasket();
		Model model = PowerMockito.mock(Model.class);
		RenderResponse renderResponse = new MockRenderResponse();
		PowerMockito.mockStatic(OrderManagementUtil.class);
		
		MockActionRequest _actionRequest = new MockActionRequest();
		_actionRequest.setSession(session);
		ActionRequest actionRequest = (ActionRequest)_actionRequest;
		assertNotNull(actionRequest);
		
		ActionResponse actionResponse = new MockActionResponse();
		actionRequest.setAttribute("THEME_DISPLAY", themeDisplay);
		actionResponse.sendRedirect("THEME_DISPLAY");
		
		Order order = new Order();
		order.setOrderId("CH4HFQH-1");
		order.setCustomerOrderNumber("CH4HFQH");
		order.setCustomerFirstName("Prashanth");
		order.setCustomerLastName("Nagraj");
		order.setNoOfLines(2);
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
		order.setTrackingId("track002");
		order.setTrackingURL("track002");
		order.setCarrierName("bluedart");
		OrderLineItem orderLineItem = new OrderLineItem();
		orderLineItem.setCancelledQuantity(2l);
		orderLineItem.setCostPerItem(10l);
		orderLineItem.setDescription("LG super mixer");
		orderLineItem.setDispatchedQuantity(2l);
		orderLineItem.setLineItemStatus("Released");
		orderLineItem.setOpenQuantity(2l);
		orderLineItem.setOrderedQuantity(4l);
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
		orderLineItem.setDispatchedQuantity(2.0);
		orderLineItem.setShipToLastName("Dongre");
		orderLineItem.setShipToPostcode("400031");
		orderLineItem.setSku("LGSuperMixer");
		orderLineItem.setType("xyz");
		orderLineItem.setUnitOfSale("each");
		orderLineItem.setShipingAddress1("Kasturba bhavan");
		orderLineItem.setShipingAddress2("Haji Ali Road");
		
		OrderLineItem orderLineItem2 = new OrderLineItem();
		orderLineItem2.setCancelledQuantity(0l);
		orderLineItem2.setCostPerItem(10l);
		orderLineItem2.setDescription("LG super mixer");
		orderLineItem2.setDispatchedQuantity(2l);
		orderLineItem2.setLineItemStatus("Released");
		orderLineItem2.setOpenQuantity(3l);
		orderLineItem2.setOrderedQuantity(6l);
		orderLineItem2.setOrderLineNumber(2);
		orderLineItem2.setPrimaryReasonForCancellation("");
		orderLineItem2.setReasonForReturn("");
		orderLineItem2.setRefundAmount(0l);
		orderLineItem2.setReturnedQuantity(0l);
		orderLineItem2.setSecondaryReasonForReturn("");
		orderLineItem2.setServiceActionType("Dispatch");
		orderLineItem2.setShipToCity("Mumbai");
		orderLineItem2.setShipToCountry("India");
		orderLineItem2.setShipToFirstName("Dhananjay");
		orderLineItem2.setLineItemStatus("Dispatched");
		orderLineItem2.setDispatchedQuantity(3.0);
		orderLineItem2.setShipToLastName("Dongre");
		orderLineItem2.setShipToPostcode("400031");
		orderLineItem2.setSku("LGSuperMixer");
		orderLineItem2.setType("xyz");
		orderLineItem2.setUnitOfSale("each");
		orderLineItem2.setShipingAddress1("Kasturba bhavan");
		orderLineItem2.setShipingAddress2("Haji Ali Road");
		
		Map<Integer, OrderLineItem> orderLineItems = new HashMap<Integer, OrderLineItem>();
		orderLineItems.put(1, orderLineItem);
		orderLineItems.put(2, orderLineItem2);
		order.setOrderLineItems(orderLineItems);
		int[] lineItems = {1,2};
		String orderId = "CH4HFQH-1";
		String updateOrderAction = "dispatch";
		
		OrderBasket orderBasket = new OrderBasket();
		Map<String, Order> dispatchedOrders = new HashMap<String, Order>();
		dispatchedOrders.put(orderId, order);
		orderBasket.setDispatchedOrders(dispatchedOrders );
		orderBasket.setAllOrderDetails(dispatchedOrders);
		
		List<Order> originalPendingItems = new ArrayList<Order>();
		originalPendingItems.add(order);
		orderBasket.setOriginalPendingItems(originalPendingItems);
		
		orderBasket.setTotalSize(1);
		orderBasket.setOrdersForUpdate(originalPendingItems);

		PowerMockito.mockStatic(ParamUtil.class);
		Mockito.when(ParamUtil.getString(actionRequest, "orderId")).thenReturn(orderId);
		Mockito.when(ParamUtil.getString(actionRequest, "updateOrderAction")).thenReturn(updateOrderAction);
		
		
		PowerMockito.mockStatic(OrderManagementUtil.class);
		Mockito.when(orderBasketService.getOrderBasket(actionRequest)).thenReturn(orderBasket);
		
		PowerMockito.mockStatic(BeanUtils.class);
		
		PowerMockito.mockStatic(PortalUtil.class);
		portlet.actionUpdateOrder(actionRequest, actionResponse, lineItems, orderId);
			
	}
	
	@Test
	public void actionReturnOrderTest() throws PortalException, SystemException, IOException{
		MockRenderRequest _request = new MockRenderRequest();
		_request.setSession(session);
		RenderRequest renderRequest = (RenderRequest)_request;
		assertNotNull(renderRequest);
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		//OrderBasket orderBasket = new OrderBasket();
		Model model = PowerMockito.mock(Model.class);
		RenderResponse renderResponse = new MockRenderResponse();
		PowerMockito.mockStatic(OrderManagementUtil.class);
		
		MockActionRequest _actionRequest = new MockActionRequest();
		_actionRequest.setSession(session);
		ActionRequest actionRequest = (ActionRequest)_actionRequest;
		assertNotNull(actionRequest);
		
		ActionResponse actionResponse = new MockActionResponse();
		actionRequest.setAttribute("THEME_DISPLAY", themeDisplay);
		
		Order order = new Order();
		order.setOrderId("CH4GYCW-1");
		order.setCustomerOrderNumber("CH4GYCW");
		order.setCustomerFirstName("Prashanth");
		order.setCustomerLastName("Nagraj");
		order.setNoOfLines(1);
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
		order.setServiceActionType("Return");
		order.setTrackingId("track002");
		order.setTrackingURL("track002");
		order.setCarrierName("bluedart");
		OrderLineItem orderLineItem = new OrderLineItem();
		orderLineItem.setCancelledQuantity(0l);
		orderLineItem.setCostPerItem(10l);
		orderLineItem.setDescription("LG super mixer");
		orderLineItem.setDispatchedQuantity(3l);
		orderLineItem.setLineItemStatus("returned");
		orderLineItem.setOpenQuantity(0l);
		orderLineItem.setOrderedQuantity(4l);
		orderLineItem.setOrderLineNumber(3);
		orderLineItem.setPrimaryReasonForCancellation("Faulty Product");
		orderLineItem.setReasonForReturn("Damaged product");
		orderLineItem.setRefundAmount(10l);
		orderLineItem.setReturnedQuantity(1l);
		orderLineItem.setSecondaryReasonForReturn("Damaged Product");
		orderLineItem.setServiceActionType("Return");
		orderLineItem.setShipToCity("Mumbai");
		orderLineItem.setShipToCountry("India");
		orderLineItem.setShipToFirstName("Dhananjay");
		orderLineItem.setLineItemStatus("Returned");
		orderLineItem.setDispatchedQuantity(3.0);
		orderLineItem.setShipToLastName("Dongre");
		orderLineItem.setShipToPostcode("400031");
		orderLineItem.setSku("LGSuperMixer");
		orderLineItem.setType("xyz");
		orderLineItem.setUnitOfSale("each");
		orderLineItem.setShipingAddress1("Kasturba bhavan");
		orderLineItem.setShipingAddress2("Haji Ali Road");
		
		
		Map<Integer, OrderLineItem> orderLineItems = new HashMap<Integer, OrderLineItem>();
		// we are returning 1 qty of 3rd line number so add 2 extra order lines.But as it is working without adding 2 extra lines , I just added order line 2 b cancelled.
		orderLineItems.put(3, orderLineItem);
		order.setOrderLineItems(orderLineItems);
		int[] lineItems = {3};
		String orderId = "CH4GYCW-1";
		String updateOrderAction = "return";
		String priRetReason = "faulty product";
		String secRetReason = "damaged product";
		double refAmt = 10.00;
		double retQty = 1.0;
		int lineItemNo = lineItems[0];
		
		OrderBasket orderBasket = new OrderBasket();
		Map<String, Order> dispatchedOrders = new HashMap<String, Order>();
		dispatchedOrders.put(orderId, order);
		orderBasket.setDispatchedOrders(dispatchedOrders);
		orderBasket.setAllOrderDetails(dispatchedOrders);
		
		List<Order> originalPendingItems = new ArrayList<Order>();
		originalPendingItems.add(order);
		orderBasket.setOriginalPendingItems(originalPendingItems);
		
		orderBasket.setTotalSize(1);
		orderBasket.setOrdersForUpdate(originalPendingItems);

		PowerMockito.mockStatic(ParamUtil.class);
		Mockito.when(ParamUtil.getString(actionRequest, "orderId")).thenReturn(orderId);
		Mockito.when(ParamUtil.getDouble(actionRequest, ("updatedQuantity-"+lineItemNo))).thenReturn(retQty);
		Mockito.when(ParamUtil.getString(actionRequest, "updateOrderAction")).thenReturn(updateOrderAction);
		Mockito.when(ParamUtil.getString(actionRequest, "primaryReturnReason")).thenReturn(priRetReason);
		Mockito.when(ParamUtil.getString(actionRequest, "secondaryReturnReason")).thenReturn(secRetReason);
		Mockito.when(ParamUtil.getDouble(actionRequest, "refundAmount")).thenReturn(refAmt);
		
		PowerMockito.mockStatic(OrderManagementUtil.class);
		Mockito.when(orderBasketService.getOrderBasket(actionRequest)).thenReturn(orderBasket);
		
		PowerMockito.mockStatic(BeanUtils.class);
		
		PowerMockito.mockStatic(PortalUtil.class);
		portlet.actionReturnOrder(actionRequest, actionResponse, lineItems, orderId);
	}
	
	@Test
	public void actionQuickDipatchOpenOrderTest() throws PortalException, SystemException, IOException{
		MockRenderRequest _request = new MockRenderRequest();
		_request.setSession(session);
		RenderRequest renderRequest = (RenderRequest)_request;
		assertNotNull(renderRequest);
		MockPortletRequest _portletRequest = new MockPortletRequest();
		PortletRequest portletRequest = (PortletRequest)_portletRequest;
		//OrderBasket orderBasket = new OrderBasket();
		Model model = PowerMockito.mock(Model.class);
		RenderResponse renderResponse = new MockRenderResponse();
		PowerMockito.mockStatic(OrderManagementUtil.class);
		
		MockActionRequest _actionRequest = new MockActionRequest();
		_actionRequest.setSession(session);
		ActionRequest actionRequest = (ActionRequest)_actionRequest;
		assertNotNull(actionRequest);
		
		ActionResponse actionResponse = new MockActionResponse();
		actionRequest.setAttribute("THEME_DISPLAY", themeDisplay);
		
		String[] selectedOrderNumbers = {"CH4HFQH-1" , "CH4HJ6M-1"};
		
		Order order = new Order();
		order.setOrderId("CH4HFQH-1");
		order.setCustomerOrderNumber("CH4HFQH");
		order.setCustomerFirstName("Prashanth");
		order.setCustomerLastName("Nagraj");
		order.setNoOfLines(1);
		order.setOrderStatus("dispatched");
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
		order.setTrackingId("track002");
		order.setTrackingURL("track002");
		order.setCarrierName("bluedart");
		OrderLineItem orderLineItem = new OrderLineItem();
		orderLineItem.setCancelledQuantity(2l);
		orderLineItem.setCostPerItem(10l);
		orderLineItem.setDescription("LG super mixer");
		orderLineItem.setLineItemStatus("dispatched");
		orderLineItem.setOpenQuantity(0l);
		orderLineItem.setOrderedQuantity(4l);
		orderLineItem.setOrderLineNumber(1);
		orderLineItem.setPrimaryReasonForCancellation("");
		orderLineItem.setReasonForReturn("");
		orderLineItem.setRefundAmount(10l);
		orderLineItem.setReturnedQuantity(0l);
		orderLineItem.setSecondaryReasonForReturn("");
		orderLineItem.setServiceActionType("Dispatch");
		orderLineItem.setShipToCity("Mumbai");
		orderLineItem.setShipToCountry("India");
		orderLineItem.setShipToFirstName("Dhananjay");
		orderLineItem.setLineItemStatus("Returned");
		orderLineItem.setDispatchedQuantity(3.0);
		orderLineItem.setShipToLastName("Dongre");
		orderLineItem.setShipToPostcode("400031");
		orderLineItem.setSku("LGSuperMixer");
		orderLineItem.setType("xyz");
		orderLineItem.setUnitOfSale("each");
		orderLineItem.setShipingAddress1("Kasturba bhavan");
		orderLineItem.setShipingAddress2("Haji Ali Road");
		
		
		Order order2 = new Order();
		order2.setOrderId("CH4HJ6M-1");
		order2.setCustomerOrderNumber("CH4HJ6M");
		order2.setCustomerFirstName("Prashanth");
		order2.setCustomerLastName("Nagraj");
		order2.setNoOfLines(1);
		order2.setOrderStatus("dispatched");
		order2.setDeliveryOption("Express");
		Date orderPlacedDate2 = new Date();
		order2.setOrderPlacedDate(orderPlacedDate2);
		order2.setCustomerEmail("john@smith.com");
		order2.setCustomerMobile("987124356");
		order2.setCustomerDayTelephone("98745612");
		order2.setCustomerEveningTelephone("98745613");
		order2.setDeliveryCost(13.33);
		order2.setShipToAddress("Kasturba Bhavan,Haji Ali Road,Mumbai");
		order2.setShipToMobile("956874123");
		order2.setOrderValue(53.33);
		order2.setServiceActionType("Dispatch");
		order2.setTrackingId("track003");
		order2.setTrackingURL("track003");
		order2.setCarrierName("bluedart");
		OrderLineItem orderLineItem2 = new OrderLineItem();
		orderLineItem2.setCancelledQuantity(0l);
		orderLineItem2.setCostPerItem(10l);
		orderLineItem2.setDescription("LG super mixer");
		orderLineItem2.setLineItemStatus("dispatched");
		orderLineItem2.setOpenQuantity(0l);
		orderLineItem2.setOrderedQuantity(6l);
		orderLineItem2.setOrderLineNumber(3);
		orderLineItem2.setPrimaryReasonForCancellation("");
		orderLineItem2.setReasonForReturn("");
		orderLineItem2.setRefundAmount(0l);
		orderLineItem2.setReturnedQuantity(0l);
		orderLineItem2.setSecondaryReasonForReturn("");
		orderLineItem2.setServiceActionType("Dispatch");
		orderLineItem2.setShipToCity("Mumbai");
		orderLineItem2.setShipToCountry("India");
		orderLineItem2.setShipToFirstName("Dhananjay");
		orderLineItem2.setLineItemStatus("Returned");
		orderLineItem2.setDispatchedQuantity(6.0);
		orderLineItem2.setShipToLastName("Dongre");
		orderLineItem2.setShipToPostcode("400031");
		orderLineItem2.setSku("LGSuperMixer");
		orderLineItem2.setType("xyz");
		orderLineItem2.setUnitOfSale("each");
		orderLineItem2.setShipingAddress1("Kasturba bhavan");
		orderLineItem2.setShipingAddress2("Haji Ali Road");
		
		Map<Integer, OrderLineItem> orderLineItems = new HashMap<Integer, OrderLineItem>();
		orderLineItems.put(1, orderLineItem);
		order.setOrderLineItems(orderLineItems);
		String orderId1 = "CH4HFQH-1";
		
		Map<Integer, OrderLineItem> orderLineItems2 = new HashMap<Integer, OrderLineItem>();
		orderLineItems2.put(3, orderLineItem2);
		order2.setOrderLineItems(orderLineItems2);
		String orderId2 = "CH4HJ6M-1";
		
		OrderBasket orderBasket = new OrderBasket();
		Map<String, Order> dispatchedOrders = new HashMap<String, Order>();
		dispatchedOrders.put(orderId1, order);
		dispatchedOrders.put(orderId2, order2);
		orderBasket.setAllOrderDetails(dispatchedOrders);
		
		List<Order> originalPendingItems = new ArrayList<Order>();
		originalPendingItems.add(order);
		originalPendingItems.add(order2);
		orderBasket.setOriginalPendingItems(originalPendingItems);
		
		orderBasket.setTotalSize(2);
		orderBasket.setOrdersForUpdate(originalPendingItems);

		PowerMockito.mockStatic(ParamUtil.class);
		Mockito.when(ParamUtil.getParameterValues(actionRequest, "selectedOrderNos")).thenReturn(selectedOrderNumbers);
		
		PowerMockito.mockStatic(OrderManagementUtil.class);
		Mockito.when(orderBasketService.getOrderBasket(actionRequest)).thenReturn(orderBasket);
		
		PowerMockito.mockStatic(BeanUtils.class);
		
		PowerMockito.mockStatic(PortalUtil.class);
		portlet.actionQuickDispatchOpenOrders(actionRequest, actionResponse);
	}
	
	@Test
	public void actionRemoveOrderTest() throws PortalException, SystemException, IOException{
	MockRenderRequest _request = new MockRenderRequest();
	_request.setSession(session);
	RenderRequest renderRequest = (RenderRequest)_request;
	assertNotNull(renderRequest);
	MockPortletRequest _portletRequest = new MockPortletRequest();
	PortletRequest portletRequest = (PortletRequest)_portletRequest;
	//OrderBasket orderBasket = new OrderBasket();
	Model model = PowerMockito.mock(Model.class);
	RenderResponse renderResponse = new MockRenderResponse();
	PowerMockito.mockStatic(OrderManagementUtil.class);
	
	MockActionRequest _actionRequest = new MockActionRequest();
	_actionRequest.setSession(session);
	ActionRequest actionRequest = (ActionRequest)_actionRequest;
	assertNotNull(actionRequest);
	
	ActionResponse actionResponse = new MockActionResponse();
	actionRequest.setAttribute("THEME_DISPLAY", themeDisplay);
	
	Order order = new Order();
	order.setOrderId("CH4HFQH-1");
	order.setCustomerOrderNumber("CH4HFQH");
	order.setCustomerFirstName("Prashanth");
	order.setCustomerLastName("Nagraj");
	order.setNoOfLines(1);
	order.setOrderStatus("dispatched");
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
	order.setTrackingId("track002");
	order.setTrackingURL("track002");
	order.setCarrierName("bluedart");
	OrderLineItem orderLineItem = new OrderLineItem();
	orderLineItem.setCancelledQuantity(2l);
	orderLineItem.setCostPerItem(10l);
	orderLineItem.setDescription("LG super mixer");
	orderLineItem.setLineItemStatus("dispatched");
	orderLineItem.setOpenQuantity(0l);
	orderLineItem.setOrderedQuantity(4l);
	orderLineItem.setOrderLineNumber(1);
	orderLineItem.setPrimaryReasonForCancellation("");
	orderLineItem.setReasonForReturn("");
	orderLineItem.setRefundAmount(10l);
	orderLineItem.setReturnedQuantity(0l);
	orderLineItem.setSecondaryReasonForReturn("");
	orderLineItem.setServiceActionType("Dispatch");
	orderLineItem.setShipToCity("Mumbai");
	orderLineItem.setShipToCountry("India");
	orderLineItem.setShipToFirstName("Dhananjay");
	orderLineItem.setLineItemStatus("Returned");
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
	String orderId1 = "CH4HFQH-1";
	
	OrderBasket orderBasket = new OrderBasket();
	Map<String, Order> dispatchedOrders = new HashMap<String, Order>();
	dispatchedOrders.put(orderId1, order);
	orderBasket.setAllOrderDetails(dispatchedOrders);
	
	List<Order> originalPendingItems = new ArrayList<Order>();
	originalPendingItems.add(order);
	orderBasket.setOriginalPendingItems(originalPendingItems);
	
	orderBasket.setOrdersForUpdate(originalPendingItems);

	PowerMockito.mockStatic(ParamUtil.class);
	Mockito.when(ParamUtil.getInteger(actionRequest, "index")).thenReturn(0);
	
	PowerMockito.mockStatic(OrderManagementUtil.class);
	Mockito.when(orderBasketService.getOrderBasket(actionRequest)).thenReturn(orderBasket);
	
	PowerMockito.mockStatic(BeanUtils.class);
	
	PowerMockito.mockStatic(PortalUtil.class);
	portlet.actionRemoveFromBasket(actionRequest);
	}
}
*/