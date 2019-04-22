package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.tesco.mhub.MhubAuditMessages;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderBasket;
import com.tesco.mhub.order.model.OrderLineItem;
import com.tesco.mhub.order.model.ResponseOrderSummary;
import com.tesco.mhub.order.model.ResponseSearchOrders;
import com.tesco.mhub.order.model.Transaction;
import com.tesco.mhub.order.portlet.OrderMgmtController;
import com.tesco.mhub.order.retrieve.order.CustomerOrderType;
import com.tesco.mhub.order.service.OrderBasketService;
import com.tesco.mhub.order.service.OrderService;
import com.tesco.mhub.order.serviceimpl.OrderServiceImpl;
import com.tesco.mhub.order.util.OrderHelperUtil;
import com.tesco.mhub.order.util.OrderManagementUtil;
import com.tesco.mhub.service.SystemMHubMappingLocalServiceUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.MockitoAnnotations.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.portlet.MockActionRequest;
import org.springframework.mock.web.portlet.MockActionResponse;
import org.springframework.mock.web.portlet.MockPortletConfig;
import org.springframework.mock.web.portlet.MockPortletContext;
import org.springframework.mock.web.portlet.MockPortletSession;
import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;
import org.springframework.mock.web.portlet.MockResourceRequest;
import org.springframework.mock.web.portlet.MockResourceResponse;
import org.springframework.ui.Model;


@RunWith(PowerMockRunner.class)
@PrepareForTest({OrderManagementUtil.class,PortalUtil.class,ParamUtil.class,OrderConstants.class,PropsUtil.class,WebKeys.class,SystemMHubMappingLocalServiceUtil.class,
	MhubAuditMessages.class,Transaction.class,OrderHelperUtil.class,IOUtils.class,FileUtil.class,JSONFactoryUtil.class,PortletBeanLocatorUtil.class,Calendar.class})
public class OrderMgmtControllerTest {	

	@InjectMocks
	private OrderMgmtController portlet = new OrderMgmtController();
	
	private PortletConfig portletConfig = null;
	private PortletContext portletContext = new MockPortletContext();
	private PortletSession session = null;
	private String portletName = "order-management";
	long sellerId = 1013632;
	Order order;
	@Mock
	OrderService orderService;
	@Mock
	OrderBasketService orderBasketService;
	@Mock
	ThemeDisplay themeDisplay;
	
	String filePath="DownloadTemplate/testDownloadOrder.xlsx";
	
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	DateFormat dateFormats = new SimpleDateFormat("dd-MM-yyyy");
	@Before
	public void setup() throws MalformedURLException
	{
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
		
	}
	
	public  Order createSimpleOrder() {
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
	}

	@Test
	public void testDefaultRender() throws JSONException{
		MockRenderRequest _request = new MockRenderRequest();
		_request.setSession(session);
		RenderRequest renderRequest = (RenderRequest)_request;
		assertNotNull(renderRequest);
		RenderResponse renderResponse = new MockRenderResponse();
		
		PowerMockito.mockStatic(PropsUtil.class);
		PowerMockito.mockStatic(OrderConstants.class);
		
		session.setAttribute("SELECTED_TAB", "released");
		
		portlet.defaultRender(renderRequest, renderResponse, session);
	}
	
	@Test
	public void testDisplayReleasedOrders() throws Exception{
		MockRenderRequest _request = new MockRenderRequest();
		_request.setSession(session);
		RenderRequest renderRequest = (RenderRequest)_request;
		assertNotNull(renderRequest);
		
		Calendar cal = Calendar.getInstance();
		Date todays = cal.getTime();
		System.out.println("todays " + todays);
		dateFormat.format(todays);
		cal.add(cal.DATE, -7);
		
		Calendar cals1 = Calendar.getInstance();
		cals1.add(cals1.DATE, 1);
		todays = cals1.getTime();
		
		Date startDate = cal.getTime();
		dateFormat.format(startDate);
		
		PowerMockito.mockStatic(OrderManagementUtil.class);
		
		Mockito.when(OrderManagementUtil.getSellerId(renderRequest)).thenReturn(sellerId);
		Model model = PowerMockito.mock(Model.class);
		
		Order order = createSimpleOrder();
		Map<String,Order> openOrders = new HashMap<String,Order>();
		openOrders.put("CH4GH6P-1", order);
		
		Mockito.when(orderService.getReleasedOrders(startDate, todays,sellerId, renderRequest)).thenReturn(openOrders);
		
		portlet.displayReleasedOrders(model, renderRequest);
	}
	
	@Test
	public void testDisplayDispatchedOrders() throws Exception{
		MockRenderRequest _request = new MockRenderRequest();
		_request.setSession(session);
		RenderRequest renderRequest = (RenderRequest)_request;
		assertNotNull(renderRequest);
		
		Calendar cal = Calendar.getInstance();
		Date todays = cal.getTime();
		
		dateFormat.format(todays);
		cal.add(cal.DATE, -7);
		
		Calendar cals1 = Calendar.getInstance();
		cals1.add(cals1.DATE, 1);
		todays = cals1.getTime();
		
		Date startDate = cal.getTime();
		dateFormat.format(startDate);
		
		PowerMockito.mockStatic(OrderManagementUtil.class);
		
		Mockito.when(OrderManagementUtil.getSellerId(renderRequest)).thenReturn(sellerId);
		Model model = PowerMockito.mock(Model.class);
		
		Order order = createSimpleOrder();
		Map<String,Order> openOrders = new HashMap<String,Order>();
		openOrders.put("CH4GH6P-1", order);
		
		Mockito.when(orderService.getDispatchedOrders(startDate, todays,sellerId, renderRequest)).thenReturn(openOrders);
		
		portlet.displayDispatchedOrders(model, renderRequest);
	}
	
	@Test
	public void testOrdersForCommit() throws Exception{
		MockActionRequest _request = new MockActionRequest();
		_request.setSession(session);
		ActionRequest actionRequest = (ActionRequest)_request;
		assertNotNull(actionRequest);
		ActionResponse actionResponse = new MockActionResponse();
		
		String url = "http://order-mgmt/my-orders/";
		
		
		List<Order> dispatchOrder = new ArrayList<Order>();
		List<Order> cancelOrder = new ArrayList<Order>();
		List<Order> returnOrder = new ArrayList<Order>();
		
		
		PowerMockito.mockStatic(WebKeys.class);
		PowerMockito.mockStatic(MhubAuditMessages.class);
		PowerMockito.mockStatic(OrderManagementUtil.class);
		PowerMockito.mockStatic(PortalUtil.class);
		PowerMockito.mockStatic(PropsUtil.class);
		PowerMockito.mockStatic(OrderConstants.class);
		PowerMockito.mockStatic(OrderHelperUtil.class);
		Transaction tx = Mockito.mock(Transaction.class);
		
		OrderBasket orderBasket = Mockito.mock(OrderBasket.class);
		List<Order> ordersForUpdate = new ArrayList<Order>();
		Order order = createSimpleOrder();
		
		actionRequest.setAttribute("THEME_DISPLAY", themeDisplay);
		actionResponse.sendRedirect("THEME_DISPLAY");
		Mockito.when(PortalUtil.getLayoutURL(themeDisplay)).thenReturn(url);
		
		
		//--------------------DISPATCH ORDERS -------------------------------
		order.setServiceActionType("dispatch");
		ordersForUpdate.add(order);
		dispatchOrder.add(order);
		
		String transactionId = "hfdghd2shdgfhsd4tywertew";
		
		Mockito.when(OrderManagementUtil.getSellerId(actionRequest)).thenReturn(sellerId);
		Mockito.when(orderBasketService.getOrderBasket(actionRequest)).thenReturn(orderBasket);
		Mockito.when(orderBasket.getOrdersForUpdate()).thenReturn(ordersForUpdate);
		Mockito.when(orderService.dispatchOrders(sellerId, dispatchOrder)).thenReturn(transactionId);
		Mockito.when(orderService.getProcessingReport(transactionId)).thenReturn("IN PROGRESS");
		portlet.ordersForCommit(actionRequest, actionResponse);
		
		//--------------------CANCEL ORDERS -------------------------------
		transactionId = "hfdghd2shdgfhsd4tywertew";
		order.setServiceActionType("cancel");
		ordersForUpdate.add(order);
		cancelOrder.add(order);
		Mockito.when(OrderManagementUtil.getSellerId(actionRequest)).thenReturn(sellerId);
		Mockito.when(orderBasketService.getOrderBasket(actionRequest)).thenReturn(orderBasket);
		Mockito.when(orderBasket.getOrdersForUpdate()).thenReturn(ordersForUpdate);
		Mockito.when(orderService.cancelOrders(sellerId, cancelOrder)).thenReturn(transactionId);
		Mockito.when(orderService.getProcessingReport(transactionId)).thenReturn("IN PROGRESS");
		portlet.ordersForCommit(actionRequest, actionResponse);		
		
		//--------------------RETURN ORDERS -------------------------------
		transactionId = "hfdghd2shdgfhsd4tywertew";
		order.setServiceActionType("return");
		ordersForUpdate.add(order);
		returnOrder.add(order);
		Mockito.when(OrderManagementUtil.getSellerId(actionRequest)).thenReturn(sellerId);
		Mockito.when(orderBasketService.getOrderBasket(actionRequest)).thenReturn(orderBasket);
		Mockito.when(orderBasket.getOrdersForUpdate()).thenReturn(ordersForUpdate);
		Mockito.when(orderService.returnOrders(sellerId, returnOrder)).thenReturn(transactionId);
		Mockito.when(orderService.getProcessingReport(transactionId)).thenReturn("IN PROGRESS");
		portlet.ordersForCommit(actionRequest, actionResponse);
	}
	
	/*@Test
	public void testDownloadOrders() throws Exception{
		
		MockResourceRequest _request = new MockResourceRequest();
		_request.setSession(session);
		ResourceRequest resourceRequest = (ResourceRequest)_request;
		assertNotNull(resourceRequest);
		ResourceResponse resourceResponse = new MockResourceResponse();
		
		PowerMockito.mockStatic(WebKeys.class);
		PowerMockito.mockStatic(MhubAuditMessages.class);
		PowerMockito.mockStatic(OrderManagementUtil.class);
		PowerMockito.mockStatic(PortalUtil.class);
		PowerMockito.mockStatic(PropsUtil.class);
		PowerMockito.mockStatic(OrderConstants.class);
		PowerMockito.mockStatic(OrderHelperUtil.class);
		PowerMockito.mockStatic(FileUtil.class);
		PowerMockito.mockStatic(IOUtils.class);
		
		
		String[] orderIds = {"CH4GH6P-1"};
		String orderId = "CH4GH6P-1";
		session.setAttribute("SELECTED_TAB", "released");
		List<Order> downloadLists = new ArrayList<Order>();
		Order order = createSimpleOrder();
		downloadLists.add(order);
		Map<String, Order> downloadOrder = new HashMap<String, Order>();
		downloadOrder.put(orderId, order);
		
		URI uri=null;
		try {
		uri = OrderMgmtControllerTest.class.getClassLoader().getResource(filePath).toURI();
		} catch (URISyntaxException e3) {
		e3.printStackTrace();
		}

		File file1 = new File(uri);
		PowerMockito.mockStatic(FileUtil.class);
		Mockito.when(FileUtil.createTempFile()).thenReturn(file1);
		
		File fileToDwnld = Mockito.mock(File.class);
		
		InputStream inputStream = Mockito.mock(InputStream.class);
		FileInputStream fileInputStream = Mockito.mock(FileInputStream.class);
		Mockito.when(OrderManagementUtil.getSellerId(resourceRequest)).thenReturn(sellerId);
		
		Mockito.when(ParamUtil.getString(resourceRequest, "pageDisplay")).thenReturn("open?selectTab:dispatched*selOrderOpt:NA*orderId:D6KXYPG-1");
		Mockito.when(orderService.getSelectedOrders("released", orderIds, sellerId, resourceRequest)).thenReturn(downloadOrder);
		//Mockito.when(orderService.exportOrderDetails(downloadLists)).thenReturn(fileToDwnld);
		Mockito.when(orderService.exportOrderDetails(downloadLists)).thenReturn(file1);
		PowerMockito.whenNew(FileInputStream.class).withAnyArguments().thenReturn(fileInputStream);
		//Mockito.when(fileToDwnld.getPath()).thenReturn("C:/Users/TE50/Desktop/testDownloadOrder.xlsx");
		
		portlet.downloadOrders(resourceRequest, resourceResponse);
	}*/
	
	@Test
	public void testCheckOrderExist() throws Exception{
		MockResourceRequest _request = new MockResourceRequest();
		_request.setSession(session);
		ResourceRequest resourceRequest = (ResourceRequest)_request;
		assertNotNull(resourceRequest);
		ResourceResponse resourceResponse = new MockResourceResponse();
		
		PowerMockito.mockStatic(WebKeys.class);
		PowerMockito.mockStatic(MhubAuditMessages.class);
		PowerMockito.mockStatic(OrderManagementUtil.class);
		PowerMockito.mockStatic(PortalUtil.class);
		PowerMockito.mockStatic(PropsUtil.class);
		PowerMockito.mockStatic(ParamUtil.class);
		PowerMockito.mockStatic(OrderConstants.class);
		PowerMockito.mockStatic(OrderHelperUtil.class);
		PowerMockito.mockStatic(FileUtil.class);
		PowerMockito.mockStatic(IOUtils.class);
		
		HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		String orderId = "CH4GH6P-1";
		Order order = createSimpleOrder();
		
		Mockito.when(OrderManagementUtil.getSellerId(resourceRequest)).thenReturn(sellerId);
		Mockito.when(PortalUtil.getHttpServletResponse(resourceResponse)).thenReturn(httpServletResponse);
		Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);
		Mockito.when(ParamUtil.getString(resourceRequest, "orderId")).thenReturn(orderId);
		Mockito.when(orderService.getOrderDetails(orderId, sellerId, resourceRequest)).thenReturn(order);
		
		portlet.checkOrderExist(resourceRequest, resourceResponse);
	}
	
	@Test
	public void testGetList() throws IOException{
		MockResourceRequest _request = new MockResourceRequest();
		_request.setSession(session);
		ResourceRequest resourceRequest = (ResourceRequest)_request;
		assertNotNull(resourceRequest);
		ResourceResponse resourceResponse = new MockResourceResponse();
		
		PowerMockito.mockStatic(WebKeys.class);
		PowerMockito.mockStatic(MhubAuditMessages.class);
		PowerMockito.mockStatic(OrderManagementUtil.class);
		PowerMockito.mockStatic(PortalUtil.class);
		PowerMockito.mockStatic(PropsUtil.class);
		PowerMockito.mockStatic(ParamUtil.class);
		PowerMockito.mockStatic(OrderConstants.class);
		PowerMockito.mockStatic(OrderHelperUtil.class);
		PowerMockito.mockStatic(FileUtil.class);
		PowerMockito.mockStatic(IOUtils.class);
		PowerMockito.mockStatic(JSONFactoryUtil.class);
		
		HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		String orderId = "CH4GH6P-1";
		String selectedTab = "released";
		String customerName = ""; // try with some name
		String pageNumber = "1";
		String deliveryOption = "";
		String filterResults = "false"; // try with true
		String sortByDate = "";
		String sortByDateIsApplied = "false"; // try with true
		
		Mockito.when(OrderManagementUtil.getSellerId(resourceRequest)).thenReturn(sellerId);
		Mockito.when(PortalUtil.getHttpServletResponse(resourceResponse)).thenReturn(httpServletResponse);
		Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);
		Mockito.when(ParamUtil.getString(resourceRequest, "selectedTab")).thenReturn(selectedTab);
		Mockito.when(ParamUtil.getString(resourceRequest, "customerName")).thenReturn(customerName);
		Mockito.when(ParamUtil.getString(resourceRequest, "pageNumber")).thenReturn(pageNumber);
		Mockito.when(ParamUtil.getString(resourceRequest, "filterResults")).thenReturn(filterResults);
		Mockito.when(ParamUtil.getString(resourceRequest, "sortByDate")).thenReturn(sortByDate);
		Mockito.when(ParamUtil.getString(resourceRequest, "sortByDateIsApplied")).thenReturn(sortByDateIsApplied);
		
		JSONObject jsonOrderResponse = Mockito.mock(JSONObject.class);
		Mockito.when(JSONFactoryUtil.createJSONObject()).thenReturn(jsonOrderResponse);
		
		try {
			portlet.getList(resourceRequest, resourceResponse);
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetListSearchByOrderIdWithoutReleaseNumber() throws IOException, SystemException, PortalException{
		MockResourceRequest _request = new MockResourceRequest();
		_request.setSession(session);
		ResourceRequest resourceRequest = (ResourceRequest)_request;
		assertNotNull(resourceRequest);
		ResourceResponse resourceResponse = new MockResourceResponse();
		
		PowerMockito.mockStatic(WebKeys.class,MhubAuditMessages.class,OrderManagementUtil.class,SystemMHubMappingLocalServiceUtil.class,PortalUtil.class,PropsUtil.class,ParamUtil.class,OrderConstants.class,OrderHelperUtil.class,FileUtil.class,IOUtils.class,JSONFactoryUtil.class);
		
		HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		String orderId = "CH4GH6P";
		String orderIdRel = "CH4GH6P-1";
		String selectedTab = "released";
		String pageNumber = "1";
		String deliveryOption = "";
		String filterResults = "false"; // try with true
		String sortByDate = "";
		String sortByDateIsApplied = "false"; // try with true
		long sellerId = 1000005;
		String searchType = "searchByOrderId";
		
		Mockito.when(OrderManagementUtil.getSellerId(resourceRequest)).thenReturn(sellerId);
		Mockito.when(PortalUtil.getHttpServletResponse(resourceResponse)).thenReturn(httpServletResponse);
		Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);
		Mockito.when(ParamUtil.getString(resourceRequest, "selectedTab")).thenReturn(selectedTab);
		Mockito.when(ParamUtil.getString(resourceRequest, "orderId")).thenReturn(orderId);
		Mockito.when(ParamUtil.getString(resourceRequest, "pageNumber")).thenReturn(pageNumber);
		Mockito.when(ParamUtil.getString(resourceRequest, "filterResults")).thenReturn(filterResults);
		Mockito.when(ParamUtil.getString(resourceRequest, "sortByDate")).thenReturn(sortByDate);
		Mockito.when(ParamUtil.getString(resourceRequest, "sortByDateIsApplied")).thenReturn(sortByDateIsApplied);
		Mockito.when(ParamUtil.getString(resourceRequest, "searchType")).thenReturn(searchType);
		
		ResponseSearchOrders mySearchOrder =  new ResponseSearchOrders(); 
		ResponseOrderSummary myOrderSummary = new ResponseOrderSummary();
		myOrderSummary.setOrderId(orderId);
		myOrderSummary.setReleaseOrderNumber(orderIdRel);
		myOrderSummary.setTotalLineItems(2);
		ResponseSearchOrders.ResponseOrder myOrder = new ResponseSearchOrders.ResponseOrder();
		Mockito.when(SystemMHubMappingLocalServiceUtil.getMHubValue(OrderConstants.SYSTEM_NAME, OrderConstants.SYSTEM_ATTRIBUTE,myOrderSummary.getDeliveryOption())).thenReturn("Express");
		myOrder.setOrderSummary(myOrderSummary);
		List<ResponseSearchOrders.ResponseOrder> responseOrderList = new ArrayList<ResponseSearchOrders.ResponseOrder>();
		responseOrderList.add(myOrder);
		mySearchOrder.setTotalOrders(3);
		mySearchOrder.setResponseOrderList(responseOrderList);
		Mockito.when(orderService.getOrderList(orderId, sellerId, resourceRequest)).thenReturn(mySearchOrder);
		JSONObject jsonOrderResponse = Mockito.mock(JSONObject.class);
		Mockito.when(JSONFactoryUtil.createJSONObject()).thenReturn(jsonOrderResponse);
		JSONArray jsonOrderResponse2 = Mockito.mock(JSONArray.class);
		Mockito.when(JSONFactoryUtil.createJSONArray()).thenReturn(jsonOrderResponse2);
		
		portlet.getList(resourceRequest, resourceResponse);
	}
	@After
	public void tearDown() throws Exception {
	}

}
