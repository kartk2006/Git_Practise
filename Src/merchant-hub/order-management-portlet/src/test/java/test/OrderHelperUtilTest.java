package test;

import static org.junit.Assert.assertEquals;

import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;
import com.tesco.mhub.BusinessFunctionStatusChecker;
import com.tesco.mhub.MhubAuditMessages;
import com.tesco.mhub.exception.MhubApplExcepMsgResolver;
import com.tesco.mhub.gmo.deliveryoption.request.PartnerAccountInformation;
import com.tesco.mhub.gmo.deliveryoption.request.PartnerContact;
import com.tesco.mhub.gmo.deliveryoption.request.TradingPartner;
import com.tesco.mhub.gmo.managetp.carrierDetails.CarrierAttributes;
import com.tesco.mhub.gmo.managetp.carrierDetails.WarehouseAddress;
import com.tesco.mhub.model.OrderLabelDetails;
import com.tesco.mhub.order.util.PortletProps;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderBasket;
import com.tesco.mhub.order.model.OrderExt;
import com.tesco.mhub.order.model.OrderLineItem;
import com.tesco.mhub.order.model.ParcelDetails;
import com.tesco.mhub.order.retrieve.order.AddressDetails;
import com.tesco.mhub.order.retrieve.order.CarrierDetails;
import com.tesco.mhub.order.retrieve.order.CustomerOrderType;
import com.tesco.mhub.order.retrieve.order.LineItemType;
import com.tesco.mhub.order.retrieve.order.RetrieveOrders;
import com.tesco.mhub.order.retrieve.order.Shipment;
import com.tesco.mhub.order.retrieve.order.ShipmentLines;
import com.tesco.mhub.order.retrieve.order.Shipments;
import com.tesco.mhub.order.search.OrderSummary;
import com.tesco.mhub.order.search.SearchOrdersResponse;
import com.tesco.mhub.order.service.OrderService;
import com.tesco.mhub.order.util.OrderHelperUtil;
import com.tesco.mhub.service.DeliveryDetailsLocalServiceUtil;
import com.tesco.mhub.service.FtEventMessageLocalServiceUtil;
import com.tesco.mhub.service.OrderLabelDetailsLocalServiceUtil;
import com.tesco.mhub.service.SystemMHubMappingLocalServiceUtil;
import com.tesco.mhub.yodl.request.Ndxml;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job.Segment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.portlet.MockActionRequest;
import org.springframework.mock.web.portlet.MockActionResponse;
import org.springframework.mock.web.portlet.MockPortletSession;
import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;
import org.springframework.mock.web.portlet.MockResourceRequest;
import org.springframework.mock.web.portlet.MockResourceResponse;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.MultiValueMap;

//@RunWith(Arquillian.class)
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"org.jacoco.agent.rt.*","javax.xml.*","org.xml.sax.*"})
@PrepareForTest({PortalUtil.class,PropsUtil.class,ConfigurationFactoryUtil.class,PortletProps.class,OrderLabelDetailsLocalServiceUtil.class
})
public class OrderHelperUtilTest {
	@Autowired
	private OrderService orderService;
	
	/*@Deployment
	public static JavaArchive createDeployment() {

		// adding empty beans.xml is required as Arquillian uses CDI

		JavaArchive appDeployable = ShrinkWrap
				.create(JavaArchive.class, "OrderHelperUtilTest.jar")
				.addClass(OrderHelperUtil.class)
				.addClass(LogFactoryUtil.class)
				.addClass(OrderConstants.class)
				.addClass(PortletProps.class)
				.addClass(HttpHeaders.class)
				.addClass(RetrieveOrders.class)
				.addClass(OrderBasket.class)
				.addClass(Shipment.class)
				.addClass(PropsUtil.class)
				.addClass(MultiValueMap.class)
				.addClass(CustomerOrderType.class)
				.addClass(CarrierDetails.class)
				.addClass(OrderExt.class)
				.addClass(LinkedCaseInsensitiveMap.class)
				.addClass(org.springframework.util.Assert.class)
				.addClass(StringPool.class)
				.addClass(LineItemType.class)
				.addClass(AddressDetails.class)
				.addClass(Shipments.class)
				.addClass(PartnerAccountInformation.class)
				.addClass(PartnerContact.class)
				.addClass(ShipmentLines.class)
				.addClass(OrderSummary.class)
				.addClass(OrderService.class)
				.addClass(SearchOrdersResponse.class)
				.addClass(OrderExt.class)
				.addClass(CarrierAttributes.class)
				.addClass(com.tesco.mhub.yodl.response.Ndxml.class)
				.addClass(TradingPartner.class)
				.addClass(com.tesco.mhub.order.retrieve.order.ShipmentLine.class)
				.addClass(org.springframework.http.HttpMethod.class)
				.addClass(org.springframework.http.InvalidMediaTypeException.class)
				.addClass(org.springframework.http.MediaType.class)
				.addClass(com.tesco.mhub.order.model.OrderLineItem.class)
				.addClass(com.tesco.mhub.order.retrieve.order.CustomerDetailsType.class)
				.addClass(com.tesco.mhub.order.retrieve.order.ShippingAddressType.class)
				.addClass(com.tesco.mhub.order.util.DecryptPassword.class)
				.addAsResource("portlet.properties")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				// this is needed to make the test jar use Liferay portal
				// depndencies
				.addAsManifestResource("jboss-deployment-structure.xml");

		return appDeployable;
	}*/
	/**
	 * logger object.
	 */
	//private Log logg = LogFactoryUtil.getLog(getClass());
	/**
	 * date.
	 */
	/*OrderHelperUtil orderHelperUtil = new OrderHelperUtil() {
	};*/
	
	MockRenderRequest mockRenderRequest;
	MockRenderResponse mockRenderResponse;
	MockPortletSession mockPortletSession;
	
	@Before
	public void setup() {
		mockRenderRequest = new MockRenderRequest();
		mockRenderResponse = new MockRenderResponse();
		mockPortletSession = new MockPortletSession();
		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetParcelDetailsListFromDB() throws ParseException {
		
		long sellerId = 1000005;
		String orderId = "D8888MW-1";
		String trackingId = "8T6ND3303505A"; String pdfFilePath = "/u01/app/jboss/liferay/uploadedYodelLabelPdfFiles/8T6ND3303505A.pdf";
		Date collectionDate = new Date();
		Date createdDate = new Date();
		boolean isUsed = false;
		boolean isFinalSubmit = false;
		String expDescrptn = "LG super mixer";
		double expOpenQuantity = 3.0;
		
		PowerMockito.mockStatic(OrderLabelDetailsLocalServiceUtil.class);

		List<OrderLabelDetails> mockParcelDetailsList = new ArrayList<OrderLabelDetails>();
		OrderLabelDetails orderLabelDetails = Mockito.mock(OrderLabelDetails.class);
		
		orderLabelDetails.setOrderId(orderId);
		orderLabelDetails.setSellerId(sellerId);
		orderLabelDetails.setTrackingId(trackingId);
		orderLabelDetails.setPdfFilePath(pdfFilePath);
		orderLabelDetails.setCollectionDate(collectionDate);
		orderLabelDetails.setIsUsed(isUsed);
		orderLabelDetails.setIsFinalSubmit(isFinalSubmit);
		orderLabelDetails.setCreatedDate(createdDate);
		mockParcelDetailsList.add(orderLabelDetails);
		Mockito.when(orderLabelDetails.getTrackingId()).thenReturn(trackingId);
		
		Mockito.when(OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrderIdSellrIdIsUsed(orderId, sellerId, false)).thenReturn(mockParcelDetailsList);
		Map<String, Order> selectedOrdersMap = new HashMap<String, Order>();
		Order mockOrder = createSimpleOrder();
		selectedOrdersMap.put(orderId, mockOrder);
		mockPortletSession.setAttribute("selectedOrdersMap", selectedOrdersMap);
		mockRenderRequest.setSession(mockPortletSession);
		 
		List<ParcelDetails> parcelDetailsList = OrderHelperUtil.getParcelDetailsListFromDB(mockRenderRequest, sellerId, orderId);
		assertEquals(trackingId, parcelDetailsList.get(0).getTrackingId());
		assertEquals(expDescrptn,parcelDetailsList.get(0).getItemDetailsLst().get(0).getDescription());
		assertEquals(expOpenQuantity,parcelDetailsList.get(0).getItemDetailsLst().get(0).getQty(),0.00);
	}
	public  Order createSimpleOrder() {
		String orderId = "D8888MW-1";
		Order order = new Order();
		
		order.setOrderId(orderId);
		order.setCustomerOrderNumber("D8888MW");
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
	/*
	
	@Test
	public void testGetRetrieveOrdersURI() {
		
		String status = "Live";
		String startDateTime = "12";
		String endDateTime = "13";
		String usrId = "123";
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OrderConstants.HTTP_PROTOCOL);
		stringBuilder.append("ukdbt81apiws01v");
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.ORDER_API_NAME);
		stringBuilder.append(StringPool.FORWARD_SLASH);
		//StringBuilder stringBuilder = getOrderBaseURI();
		stringBuilder.append(OrderConstants.RETRIEVE_ORDER_FUNCTION_NAME);
		stringBuilder.append(StringPool.QUESTION);
		stringBuilder.append("status=");
		stringBuilder.append(status);
		stringBuilder.append(StringPool.AMPERSAND);
		stringBuilder.append("startDateTime=");
		stringBuilder.append(startDateTime);
		stringBuilder.append(StringPool.AMPERSAND);
		stringBuilder.append("endDateTime=");
		stringBuilder.append(endDateTime);
		stringBuilder.append(StringPool.AMPERSAND);
		stringBuilder.append("userid=");
		stringBuilder.append(usrId);
		
		String expectedstringBuilder = stringBuilder.toString();
		String actualstringBuilder = OrderHelperUtil.getRetrieveOrdersURI(status, startDateTime, endDateTime, usrId);
	}
	
	@Test
	public void testGetSearchOrdersURI() {
		
		String status = "realesed";
		String startDateTime = "2014-04-30T10:14:10";
		String endDateTime = "2014-05-30T01:12:12";
		String filterDateBy = "DispatchedDate";
		String sortBy = "ReleasedDate";
		String deliveryOption = "MKTPLACE_Standard";
		String customerName = "test";
		String usrId = "1013632";
		String pageNumber = "2";
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OrderConstants.HTTP_PROTOCOL);
		stringBuilder.append("ukdbt81apiws01v");
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.ORDER_API_NAME);
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.SEARCH_ORDER_FUNCTION_NAME);
		stringBuilder.append(StringPool.QUESTION);
		stringBuilder.append("status=");
		stringBuilder.append(status);
		stringBuilder.append(StringPool.AMPERSAND);
		stringBuilder.append("startDateTime=");
		stringBuilder.append(startDateTime);
		stringBuilder.append(StringPool.AMPERSAND);
		stringBuilder.append("endDateTime=");
		stringBuilder.append(endDateTime);
		stringBuilder.append(StringPool.AMPERSAND);
		stringBuilder.append("filterdateby=");
		stringBuilder.append(filterDateBy);
		stringBuilder.append(StringPool.AMPERSAND);
		stringBuilder.append("sortby=");
		stringBuilder.append(sortBy);
		stringBuilder.append(StringPool.AMPERSAND);
		stringBuilder.append("deliveryOption=");
		stringBuilder.append(deliveryOption);
		stringBuilder.append(StringPool.AMPERSAND);
		stringBuilder.append("customername=");
		stringBuilder.append(customerName);
		stringBuilder.append(StringPool.AMPERSAND);
		stringBuilder.append("pageNumber=");
		stringBuilder.append(pageNumber);
		stringBuilder.append(StringPool.AMPERSAND);
		stringBuilder.append("userid=");
		stringBuilder.append(usrId);
		
		String expectedstringBuilder = stringBuilder.toString();
		String actualstringBuilder = OrderHelperUtil.getSearchOrdersURI(status, startDateTime, endDateTime, filterDateBy, sortBy, deliveryOption, customerName, usrId, pageNumber);
	}
	
	@Test
	public void testGetSelectedOrdersURI() {
		
		String status = "realesed";
		String[] orderIds = {"DSFG2ER-1" , "ERQWYU7-2"};
		long usrId = 1013632;
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OrderConstants.HTTP_PROTOCOL);
		stringBuilder.append("ukdbt81apiws01v");
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.ORDER_API_NAME);
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.SEARCH_ORDER_FUNCTION_NAME);
		stringBuilder.append(StringPool.QUESTION);
		stringBuilder.append("status=");
		stringBuilder.append(status);
		stringBuilder.append(StringPool.AMPERSAND);
		
		StringBuilder orderIdsString = new StringBuilder();
		for(int i=0; i<orderIds.length; i++){
			if(orderIds[i].contains(StringPool.DASH)){
				String orderID = orderIds[i].trim().split(StringPool.DASH)[0];
				orderIdsString.append(orderID.trim()+"|");
			}
		}
		stringBuilder.append("orderid="+orderIdsString+StringPool.AMPERSAND);
		stringBuilder.append("userid=");
		stringBuilder.append(usrId);
		
		String expectedstringBuilder = stringBuilder.toString();
		String actualstringBuilder = OrderHelperUtil.getSelectedOrdersURI(status,orderIds,usrId);
	}
	
	@Test
	public void testGetOrderDetailsURI() {
		
		String orderId = "DSFG2ER-1";
		long usrId = 1013632;
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OrderConstants.HTTP_PROTOCOL);
		stringBuilder.append("ukdbt81apiws01v");
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.ORDER_API_NAME);
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.SEARCH_ORDER_FUNCTION_NAME);
		stringBuilder.append(StringPool.QUESTION);
		if(orderId.contains(StringPool.DASH)){
			orderId = orderId.split(StringPool.DASH)[0];
		}
		stringBuilder.append("orderid="+orderId+StringPool.AMPERSAND);
		stringBuilder.append("userid=");
		stringBuilder.append(usrId);
		
		String expectedstringBuilder = stringBuilder.toString();
		String actualstringBuilder = OrderHelperUtil.getOrderDetailsURI(orderId,usrId);
	}
	
		
	@Test
	public void testGetProcessingReportURI() {
		
		String transactionId = "1234";
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OrderConstants.HTTP_PROTOCOL);
		stringBuilder.append("ukdbt81apiws01v");
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.ORDERAPI_PROCESSINGREPORT);
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(transactionId);
		stringBuilder.append(StringPool.QUESTION);
		stringBuilder.append("appKeyToken=ILUser001&appKey=A4C18F48-78C9-44FF-8945-4944003EE9E9");
		
		String expectedstringBuilder = stringBuilder.toString();
		String actualstringBuilder = OrderHelperUtil.getProcessingReportURI(transactionId);
	}
	
	@Test
	public void testGetAcknowledgeOrdersURI() {
		
		long userId = 123;

		//StringBuilder stringBuilder = getOrderBaseURI();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OrderConstants.HTTP_PROTOCOL);
		stringBuilder.append("ukdbt81apiws01v");
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.ORDER_API_NAME);
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.ACKNOWLEDGE_ORDER_FUNCTION_NAME);
		//getQuestionMarkAndUserId(userId, stringBuilder);
		stringBuilder.append(StringPool.QUESTION);		
		stringBuilder.append("userid=");
		stringBuilder.append(userId);
		
		String expectedstringBuilder = stringBuilder.toString();
		String actualstringBuilder = OrderHelperUtil.getAcknowledgeOrdersURI(userId);
	}*/

	/*private static void getQuestionMarkAndUserId(long userId,StringBuilder stringBuilder) {
		
		stringBuilder.append(StringPool.QUESTION);		
		stringBuilder.append("userid=");
		stringBuilder.append(userId);
		
		
	}*/
	
	/*@Test
	public void testGetShipmentUpdatesOrdersURI() {
		
		long userId = 1234;
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OrderConstants.HTTP_PROTOCOL);
		stringBuilder.append("ukdbt81apiws01v");
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.ORDER_API_NAME);
		stringBuilder.append(StringPool.FORWARD_SLASH);
		//StringBuilder stringBuilder = getOrderBaseURI();
		stringBuilder.append(OrderConstants.SHIPMENTUPDATES_ORDER_FUNCTION_NAME);
		//getQuestionMarkAndUserId(userId, stringBuilder);
		stringBuilder.append(StringPool.QUESTION);		
		stringBuilder.append("userid=");
		stringBuilder.append(userId);
		
		String expectedstringBuilder = stringBuilder.toString();
		String actualstringBuilder = OrderHelperUtil.getShipmentUpdatesOrdersURI(userId);
	}*/
	
	/*@Test
    public void testGetReturnRefundsOrderURI() {

		long userId = 1234;
		//StringBuilder stringBuilder = getOrderBaseURI();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OrderConstants.HTTP_PROTOCOL);
		stringBuilder.append("ukdbt81apiws01v");
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.ORDER_API_NAME);
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.RETURNREFUNDS_ORDER_FUNCTION_NAME);
		//getQuestionMarkAndUserId(userId, stringBuilder);
		stringBuilder.append(StringPool.QUESTION);		
		stringBuilder.append("userid=");
		stringBuilder.append(userId);

		String expectedstringBuilder = stringBuilder.toString();
		String actualstringBuilder = OrderHelperUtil.getReturnRefundsOrderURI(userId);
	} */
		
	/*private static StringBuilder getOrderBaseURI() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OrderConstants.HTTP_PROTOCOL);
		stringBuilder.append("ukdbt81apiws01v");
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.ORDER_API_NAME);
		stringBuilder.append(StringPool.FORWARD_SLASH);
		return stringBuilder;
	}*/

	//@Test
	/*public void testGetMarketPlaceAPIAuthHeaders() {
		String authHeader = "appKeyToken=ILUser001&appKey=A4C18F48-78C9-44FF-8945-4944003EE9E9";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", authHeader);
		headers.add("Content-Type", "application/xml");
		headers.add("Accept", "application/xml");
		HttpHeaders expectedHeaders = OrderHelperUtil.getMarketPlaceAPIAuthHeaders();
	}
			
	@Test
	public void testOverlayOrderStatus() {
		RetrieveOrders releasedOrders = new RetrieveOrders();
		OrderBasket basketOrders = new OrderBasket();
			    
		RetrieveOrders expectedReleasedOrders = OrderHelperUtil.overlayOrderStatus(releasedOrders, basketOrders);
	}
	
	@Test
	public void testGetRetrieveOrders(){
		Shipment shipment = new Shipment();
		//shipment.setAddressDetails(null);
		shipment.setIsConsolidatedParcel("xyz");
		RetrieveOrders rOrders = new RetrieveOrders(); 
		RetrieveOrders expectedReleasedOrders = OrderHelperUtil.getRetrieveOrders(shipment);
	}
	
	@Test
	public void isValidList() {
		List list = new List() {

			@Override
			public int size() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean contains(Object o) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Iterator iterator() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object[] toArray() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object[] toArray(Object[] a) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean add(Object e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean remove(Object o) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean containsAll(Collection c) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean addAll(Collection c) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean addAll(int index, Collection c) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean removeAll(Collection c) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean retainAll(Collection c) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void clear() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Object get(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object set(int index, Object element) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void add(int index, Object element) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Object remove(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int indexOf(Object o) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int lastIndexOf(Object o) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public ListIterator listIterator() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ListIterator listIterator(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List subList(int fromIndex, int toIndex) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		boolean isvalid = false;

		if(list != null && list.size() > 0) {
			isvalid = true;
		}
		isvalid = false;
		boolean expectedIsvalid = OrderHelperUtil.isValidList(list);
	}
	@Test
	public void isValidMap() {
		Map map = new Map() {

			@Override
			public int size() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean containsKey(Object key) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean containsValue(Object value) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public Object get(Object key) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object put(Object key, Object value) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object remove(Object key) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void putAll(Map m) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void clear() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Set keySet() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Collection values() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Set entrySet() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		boolean isvalid = false;
		if(map != null && map.size() > 0) {
			isvalid = true;
		}
		isvalid = false;
		boolean expectedIsvalid = OrderHelperUtil.isValidMap(map);
	}
	
	@Test
	public void getYodelREq(){
		long sellerId = 1000005; 
		Date pickUpDate = new Date();
		Order order = createSimpleOrder();
		TradingPartner tp = null;
		CarrierAttributes ca = null;
		try {
			tp = orderService.getSellerById(sellerId);
			ca = orderService.getCarrierAttributes(sellerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Ndxml ndxmReq = OrderHelperUtil.getYodelRequest(order.getOrderId(), sellerId, tp, pickUpDate, ca, order);
		Assert.assertNotNull(ndxmReq);
	}
	
	@Test
	public void getPSegment(){
		long sellerId = 1000005; 
		Date pickUpDate = new Date();
		Order order = createSimpleOrder();
		TradingPartner tp = null;
		CarrierAttributes ca = null;
		try {
			tp = orderService.getSellerById(sellerId);
			ca = orderService.getCarrierAttributes(sellerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Segment pSegment =  OrderHelperUtil.getPSegment(pickUpDate, ca.getCarrierDetails(), ca.getWarehouseAddress(),order, tp);
		Assert.assertNotNull(pSegment);
	}
	
	@Test
	public void getDSegment(){
		long sellerId = 1000005; 
		Date pickUpDate = new Date();
		Order order = createSimpleOrder();
		TradingPartner tp = null;
		CarrierAttributes ca = null;
		try {
			tp = orderService.getSellerById(sellerId);
			ca = orderService.getCarrierAttributes(sellerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Segment pSegment =  OrderHelperUtil.getDSegment(pickUpDate, ca.getCarrierDetails(), ca.getWarehouseAddress(),order, tp);
		Assert.assertNotNull(pSegment);
	}*/
	
/*	@Test
	public void downloadPDFAndSave(){
		long sellerId = 1000005; 
		Date pickUpDate = new Date();
		Order order = createSimpleOrder();
		TradingPartner tp = null;
		CarrierAttributes ca = null;
		try {
			tp = orderService.getSellerById(sellerId);
			ca = orderService.getCarrierAttributes(sellerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		com.tesco.mhub.yodl.response.Ndxml yodlResp = null;
		try {
			yodlResp = orderService.createYodlLabel(sellerId, order.getOrderId(), tp, pickUpDate, ca, order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String filePath = OrderConstants.YODL_LABEL_PATH;
		String trackingId = yodlResp.getResponse().getJob().getConsignment().getNumber();
		String pdfUrl = yodlResp.getResponse().getJob().getLabelData().getUrl();
		//Download Yodl Label PDF and store in Folder
		String fullFilePath = new String();
		fullFilePath = OrderHelperUtil.downloadPDFAndSave(pdfUrl, filePath, trackingId);
		Assert.assertNotNull(fullFilePath);
	}
	*/
/*	@Test
	public void getPartnerContact(){
		TradingPartner tp = null;
		long sellerId = 1000005; 
		try {
			tp = orderService.getSellerById(sellerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PartnerContact pc = new PartnerContact();
		pc = OrderHelperUtil.getPartnerContact(tp, OrderConstants.CUSTOMER_SERVICE_CONTACT);
		Assert.assertNotNull(pc);
	}*/
	
/*	@Test
	public void getPartnerAccountInformation(){
		PartnerAccountInformation pac = new PartnerAccountInformation();
		long sellerId = 1000005;
		TradingPartner tp = null;
		try {
			tp = orderService.getSellerById(sellerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pac = OrderHelperUtil.getPartnerAccountInformation(tp, OrderConstants.CUSTOMER_SERVICE_CONTACT);
		Assert.assertNotNull(pac);
	}*/
	
/*	public Order createSimpleOrder(){
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
*/	

}
