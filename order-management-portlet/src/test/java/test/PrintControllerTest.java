package test;

import static org.junit.Assert.*;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderLineItem;
import com.tesco.mhub.order.portlet.PrintAndDownloadController;
import com.tesco.mhub.order.service.OrderBasketService;
import com.tesco.mhub.order.service.OrderService;
import com.tesco.mhub.order.util.OrderManagementUtil;
import com.tesco.mhub.service.SystemMHubMappingLocalServiceUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
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
import org.springframework.mock.web.portlet.MockPortletConfig;
import org.springframework.mock.web.portlet.MockPortletContext;
import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.mock.web.portlet.MockPortletSession;
import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;

@RunWith(PowerMockRunner.class)
@PrepareForTest({OrderManagementUtil.class,PortalUtil.class,ParamUtil.class,BeanUtils.class,OrderBasketService.class,OrderService.class,SystemMHubMappingLocalServiceUtil.class,OrderConstants.class})
public class PrintControllerTest {
/*
	@InjectMocks
	private PrintAndDownloadController portlet = new PrintAndDownloadController();

	private PortletConfig portletConfig = null;
	private PortletContext portletContext = new MockPortletContext();
	private PortletSession session = null;
	private String portletName = "order-management";
	DateFormat dateFormats = new SimpleDateFormat("d MMM yyyy");
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
		
		session = Mockito.mock(PortletSession.class);
		assertNotNull(session);
	}

	@After
	public void tearDown() throws Exception {
	}
*/
	/*@Test
	public void doPopupPrintRenderTest() {
		MockRenderRequest _request = new MockRenderRequest();
		
		RenderRequest renderRequest = (RenderRequest)_request;
		assertNotNull(renderRequest);
		
		Map<String, Order> displayOrder = new HashMap<String, Order>();
		Order order = createSimpleOrder();
		displayOrder.put("CH4GH6P-1", order);
		
		RenderResponse renderResponse = new MockRenderResponse();
		
		String pageDisplay = "open?orderId:CH4GH6P-1";
		
		String[] strArray = {"CH4GH6P-1"};
		
		PowerMockito.mockStatic(OrderManagementUtil.class);
		Mockito.when(OrderManagementUtil.getSellerId(renderRequest)).thenReturn(sellerId);
		
		try {
			Mockito.when(orderService.getSelectedOrders("released", strArray , sellerId, renderRequest)).thenReturn(displayOrder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PowerMockito.mockStatic(ParamUtil.class);
		Mockito.when(ParamUtil.getString(renderRequest, "pageDisplay")).thenReturn(pageDisplay);
		
		Mockito.when(session.getAttribute("SELECTED_TAB")).thenReturn("released");
			
		portlet.doPrintPopupRender(renderRequest, renderResponse);
	}
*/
	/*public  Order createSimpleOrder() {
		Order order = new Order();
		
		order.setOrderId("CH4GH6P-1");
		order.setCustomerOrderNumber("CH4GH6P");
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
	}*/
	
}
