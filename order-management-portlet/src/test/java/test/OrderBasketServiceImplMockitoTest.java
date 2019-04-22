package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.tesco.mhub.BusinessFunctionStatusChecker;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.dao.OrderDAO;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderBasket;
import com.tesco.mhub.order.model.OrderLineItem;
import com.tesco.mhub.order.service.OrderBasketService;
import com.tesco.mhub.order.service.OrderService;
import com.tesco.mhub.order.serviceimpl.OrderBasketServiceImpl;
import com.tesco.mhub.order.util.OrderManagementUtil;
import com.tesco.mhub.service.SystemMHubMappingLocalServiceUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

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
@PrepareForTest({OrderManagementUtil.class,PortalUtil.class,ParamUtil.class,OrderConstants.class,SystemMHubMappingLocalServiceUtil.class})
public class OrderBasketServiceImplMockitoTest {

	@InjectMocks
	private OrderBasketServiceImpl orderBasketServiceImpl = new OrderBasketServiceImpl();
	
	private PortletConfig portletConfig = null;
	private PortletContext portletContext = new MockPortletContext();
	private PortletSession session = null;
	private String portletName = "order-management";
	long sellerId = 1013632l;
	Order order;
	@Mock
	OrderService orderService;
	@Mock
	OrderBasketService orderBasketService;
	@Mock
	OrderDAO orderDAO;
	@Mock
	ThemeDisplay themeDisplay;
	
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		assertNotNull(orderBasketServiceImpl);
		assertNotNull(portletContext);
		
		MockPortletConfig _portletConfig = new MockPortletConfig(portletContext,portletName);
		assertNotNull(_portletConfig);
		portletConfig = (PortletConfig)_portletConfig;
		
		session = Mockito.mock(PortletSession.class);
		assertNotNull(session);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetOrderBasketFromSession() {
		
		long userId = 10234;
		int totalBasketSize = 5;
		MockPortletRequest portletRequest = new MockPortletRequest();
		MockPortletSession portletSession = new MockPortletSession();
		
		Map<Long,OrderBasket> orderBasketMapFromSession = new HashMap<Long, OrderBasket>();
		OrderBasket orderBasket = new OrderBasket();
		orderBasket.setTotalSize(totalBasketSize);
		orderBasketMapFromSession.put(userId, orderBasket);
		
		portletSession.setAttribute(OrderConstants.ORDER_BASKET, orderBasketMapFromSession, PortletSession.APPLICATION_SCOPE);
		portletRequest.setSession(portletSession);
		PowerMockito.mockStatic(PortalUtil.class);
		Mockito.when(PortalUtil.getUserId(portletRequest)).thenReturn(userId);
		OrderBasket actualOrderBasket = orderBasketServiceImpl.getOrderBasket(portletRequest);
		
		assertEquals(totalBasketSize, actualOrderBasket.getTotalSize());
		
	}
	@Test
	public void testGetOrderBasketFromSessionLensedUser() {
		
		long userId = 10234;
		long lenseUserId = 12345;
		int totalBasketSize = 5;
		int expectedBasketSize = 0;
		MockPortletRequest portletRequest = new MockPortletRequest();
		MockPortletSession portletSession = new MockPortletSession();
		
		Map<Long,OrderBasket> orderBasketMapFromSession = new HashMap<Long, OrderBasket>();
		OrderBasket orderBasket = new OrderBasket();
		orderBasket.setTotalSize(totalBasketSize);
		orderBasketMapFromSession.put(userId, orderBasket);
		
		portletSession.setAttribute(OrderConstants.ORDER_BASKET, orderBasketMapFromSession, PortletSession.APPLICATION_SCOPE);
		portletRequest.setSession(portletSession);
		PowerMockito.mockStatic(PortalUtil.class);
		Mockito.when(PortalUtil.getUserId(portletRequest)).thenReturn(lenseUserId);
		OrderBasket actualOrderBasket = orderBasketServiceImpl.getOrderBasket(portletRequest);
		
		assertEquals(expectedBasketSize, actualOrderBasket.getTotalSize());
		
	}
	@Test
	public void testGetOrderBasketNotFromSession() {
		
		long userId = 10234;
		long lenseUserId = 12345;
		int totalBasketSize = 5;
		int expectedBasketSize = 0;
		MockPortletRequest portletRequest = new MockPortletRequest();
		PowerMockito.mockStatic(PortalUtil.class);
		Mockito.when(PortalUtil.getUserId(portletRequest)).thenReturn(userId);
		OrderBasket actualOrderBasket = orderBasketServiceImpl.getOrderBasket(portletRequest);
		
		assertEquals(expectedBasketSize, actualOrderBasket.getTotalSize());
		
	}
	@Test
	public void setOrderBasketTest() {
		int totalBasketSize = 5;
		long userId = 10234;
		MockPortletRequest portletRequest = new MockPortletRequest();
		OrderBasket orderBasket = new OrderBasket();
		orderBasket.setTotalSize(totalBasketSize);
		PowerMockito.mockStatic(PortalUtil.class);
		Mockito.when(PortalUtil.getUserId(portletRequest)).thenReturn(userId);
		orderBasketServiceImpl.setOrderBasket(orderBasket, portletRequest);
		PortletSession portletSession = portletRequest.getPortletSession();
	
		Map<Long,OrderBasket> orderBasketMap = (Map<Long, OrderBasket>) portletSession.getAttribute(OrderConstants.ORDER_BASKET,PortletSession.APPLICATION_SCOPE); 
		assertNotNull(orderBasketMap.get(userId));
	}
	
	public Order createSimpleOrder() {
		
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
}
