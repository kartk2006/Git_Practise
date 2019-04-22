package com.tesco.mhub.order.serviceimpl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.tesco.mhub.NoSuchSystemMHubMappingException;
import com.tesco.mhub.gmo.deliveryoption.request.TradingPartner;
import com.tesco.mhub.gmo.managetp.carrierDetails.CarrierAttributes;
import com.tesco.mhub.model.OrderLabelDetails;
import com.tesco.mhub.order.ack.orders.AcknowledgeOrders;
import com.tesco.mhub.order.ack.orders.OrderLine;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.dao.OrderDAO;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderBasket;
import com.tesco.mhub.order.model.OrderLabelMin;
import com.tesco.mhub.order.model.OrderLineItem;
import com.tesco.mhub.order.model.ResponseOrderSummary;
import com.tesco.mhub.order.model.ResponseSearchOrders;
import com.tesco.mhub.order.model.Transaction;
import com.tesco.mhub.order.retrieve.order.CustomerOrderType;
import com.tesco.mhub.order.retrieve.order.LineItemType;
import com.tesco.mhub.order.retrieve.order.OrderStatusType;
import com.tesco.mhub.order.retrieve.order.RetrieveOrders;
import com.tesco.mhub.order.returns.refunds.Return;
import com.tesco.mhub.order.returns.refunds.ReturnRefunds;
import com.tesco.mhub.order.returns.refunds.ReturnRequestLines;
import com.tesco.mhub.order.search.OrderSummary;
import com.tesco.mhub.order.search.SearchOrdersResponse;
import com.tesco.mhub.order.service.OrderBasketService;
import com.tesco.mhub.order.service.OrderService;
import com.tesco.mhub.order.shipment.update.AddressDetails;
import com.tesco.mhub.order.shipment.update.CarrierDetails;
import com.tesco.mhub.order.shipment.update.Shipment;
import com.tesco.mhub.order.shipment.update.ShipmentLine;
import com.tesco.mhub.order.shipment.update.ShipmentLines;
import com.tesco.mhub.order.shipment.update.ShipmentUpdate;
import com.tesco.mhub.order.shipment.update.ShipmentUpdates;
import com.tesco.mhub.order.shipment.update.Shipments;
import com.tesco.mhub.order.util.DateUtil;
import com.tesco.mhub.order.util.ExcelUtil;
import com.tesco.mhub.order.util.OrderBasketUtil;
import com.tesco.mhub.order.util.OrderDetailUtil;
import com.tesco.mhub.order.util.OrderHelperUtil;
import com.tesco.mhub.order.util.OrderManagementUtil;
import com.tesco.mhub.order.util.PortletProps;
import com.tesco.mhub.report.feed.ProcessingReport;
import com.tesco.mhub.service.OrderLabelDetailsLocalServiceUtil;
import com.tesco.mhub.service.SystemMHubMappingLocalServiceUtil;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceImpl implements OrderService {

	/**
	 * order dao object.
	 */
	@Autowired
	private OrderDAO orderDAO;
	/**
	 * order basket service object.
	 */
	@Autowired
	private OrderBasketService orderBasketService;
	
	/**
	 * order basket service object.
	 */
	@Autowired
	private OrderBasketUtil orderBasketUtil;
	/**
	 * logger object.
	 */
	private Log log = LogFactoryUtil.getLog(getClass());
	
	/**
	 * Order in Cancelled status.
	 */
	private String cancelledOrder = "Cancelled";
	/**
	 * Order in no status.
	 */
	private String noOrderStatus = "--";
	/**
	 *Seller Id log string.
	 */
	private String sellerIdLog = "SELLER ID : ";
	/**
	 *START log string.
	 */
	private String startString = " START ,";
	/**
	 *class OrderServName.
	 */
	private String classOrderServName = "CLASS : OrderServiceImpl, ";
	/**
	 *Get Select Meth Name.
	 */
	private String getSelectMethName = " METHOD : getSelectedOrders";
	/**
	 *Time string.
	 */
	private String timeLog = " TIME : ";
	/**
	 *End string.
	 */
	private String endString = " END ,";
	/**
	 *Time string.
	 */
	private String totalElapsedStr = " TOTAL ELAPSED";
	/**
	 *Time in Sec string.
	 */
	private String timeInSec = " TIME in Sec : ";
	/**
	 *Dispatch Ord Name.
	 */
	private String dispatchOrdMeth= " METHOD : dispatchOrders";
	/**
	 *Create Yodl Label.
	 */
	private String crateYodlMethName= " METHOD : createYodlLabel";
	@Override
	public ResponseSearchOrders getSearchOrders(String status, Date startDate,Date endDate, String filterDateBy, String sortBy,String deliveryOption, String customerName, long sellerId,int pageNumber,
			PortletRequest portletRequest) {
		ResponseSearchOrders mySearchOrder = null;
		try {
			SearchOrdersResponse searchOrderResponse = orderDAO.getSearchResult(status, startDate, endDate, filterDateBy, sortBy, deliveryOption, customerName, sellerId,pageNumber);
			if(Validator.isNotNull(searchOrderResponse)){
				mySearchOrder = new ResponseSearchOrders();
				List<com.tesco.mhub.order.search.SearchOrdersResponse.Order> searchResultFromService = searchOrderResponse.getOrder();
				if(Validator.isNotNull(searchResultFromService) && searchResultFromService.size()>0){
					List<ResponseSearchOrders.ResponseOrder> mySearchResults = new ArrayList<ResponseSearchOrders.ResponseOrder>();
					mySearchOrder.setPageNumber(searchOrderResponse.getPageNumber().intValue());
					mySearchOrder.setPageSize(searchOrderResponse.getPageSize().intValue());
					mySearchOrder.setTotalOrders(searchOrderResponse.getTotalNumberOfOrders().intValue());
					for (com.tesco.mhub.order.search.SearchOrdersResponse.Order orderFromService : searchResultFromService) {
						ResponseSearchOrders.ResponseOrder myOrder = new ResponseSearchOrders.ResponseOrder();
						ResponseOrderSummary myOrderSummary = new ResponseOrderSummary();
						OrderSummary orderSummeryFromService = orderFromService.getOrderSummary();
						myOrderSummary.setOrderId(orderSummeryFromService.getOrderID());
						myOrderSummary.setReleaseOrderNumber(orderSummeryFromService.getReleaseOrderNumber());
						myOrderSummary.setTotalLineItems(orderSummeryFromService.getTotalNumberOfLines().intValue());
						myOrderSummary.setTotalOpenLineItems(orderSummeryFromService.getTotalNumberOfLinesOpen().intValue());
						OrderBasket orderBasket = orderBasketService.getOrderBasket(portletRequest);
						List<Order> ordersForUpdateInBasket = orderBasket.getOrdersForUpdate();
						HashMap<String, Transaction> transDetailsForDisAndCan = orderBasket.getTransactionDetailsOfDispatchAndCancel();
						HashMap<String, Transaction> transDetailsForReturn = orderBasket.getTransactionDetailsOfReturn();
						transDetailsForDisAndCan.putAll(transDetailsForReturn);
						String transactionStatus = null;
						if(Validator.isNotNull(transDetailsForDisAndCan)){
							for (Map.Entry<String, Transaction> entry:transDetailsForDisAndCan.entrySet()) {
								if(entry.getKey().equalsIgnoreCase(myOrderSummary.getReleaseOrderNumber())){
									transactionStatus = getTransactionStatusOfOrder(entry.getValue(), portletRequest);
								}
							}
						}
						if(Validator.isNotNull(transactionStatus) && OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INPROGRESS.equalsIgnoreCase(transactionStatus)){
							myOrderSummary.setStatus(OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INPROGRESS);
						}else if(Validator.isNotNull(ordersForUpdateInBasket)){
							myOrderSummary.setStatus(getOrderStatusFromBasket(myOrderSummary.getReleaseOrderNumber(),ordersForUpdateInBasket));
						}
						if(Validator.isNull(myOrderSummary.getStatus()) || "".equals(myOrderSummary.getStatus().trim())){
							myOrderSummary.setStatus(orderSummeryFromService.getStatus());
						}
						
						myOrderSummary.setDeliveryOption(orderSummeryFromService.getDeliveryOption());
						
						Date utilDateForDisplay = null;
						
						utilDateForDisplay =DateUtil.toDate(orderSummeryFromService.getReleasedDate());
						if(Validator.isNotNull(utilDateForDisplay)){
							myOrderSummary.setReleasedDate(DateUtil.formatDate(utilDateForDisplay));
						}
						utilDateForDisplay = DateUtil.toDate(orderSummeryFromService.getExpectedDispatchDate());
						if(Validator.isNotNull(utilDateForDisplay)){
							myOrderSummary.setExpectedDispatchDate(DateUtil.formatDate(utilDateForDisplay));
							myOrderSummary.setExpectedShipmentDate(DateUtil.formatDate(utilDateForDisplay));
						}
						utilDateForDisplay = DateUtil.toDate(orderSummeryFromService.getDispatchedDate());
						if(Validator.isNotNull(utilDateForDisplay)){
							myOrderSummary.setDispatchedDate(DateUtil.formatDate(utilDateForDisplay));
						}
						utilDateForDisplay = DateUtil.toDate(orderSummeryFromService.getCancelledDate());
						if(Validator.isNotNull(utilDateForDisplay)){
							myOrderSummary.setCancelledDate(DateUtil.formatDate(utilDateForDisplay));
						}
						utilDateForDisplay = DateUtil.toDate(orderSummeryFromService.getReturnedDate());
						if(Validator.isNotNull(utilDateForDisplay)){
							myOrderSummary.setReturnedDate(DateUtil.formatDate(utilDateForDisplay));
						}
						myOrderSummary.setBillToName(orderSummeryFromService.getBillToName());
						myOrderSummary.setShipToName(orderSummeryFromService.getShipToName());
						
						myOrder.setOrderSummary(myOrderSummary);
						mySearchResults.add(myOrder);
					}
					mySearchOrder.setResponseOrderList(mySearchResults);
					portletRequest.getPortletSession().setAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY, StringPool.BLANK);
				}else{
					portletRequest.getPortletSession().setAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY, OrderConstants.NO_ORDERS_FOUND);
				}
			}else{
				portletRequest.getPortletSession().setAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY, "Service Unavailable.");
				log.error("searchOrderResponse is NULL");
			}
		} catch (Exception e) {
			portletRequest.getPortletSession().setAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY, "Error in manupulating the date");
			log.error(OrderConstants.ORDMG_E031+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E031)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
			e.printStackTrace();
		}
		return mySearchOrder;
	}
	
	private String getOrderStatusFromBasket(String orderId, List<Order> ordersForUpdateInBasket) {
		for (Order pendingOrder : ordersForUpdateInBasket) {
			if(pendingOrder.getOrderId().equalsIgnoreCase(orderId)){
				if(OrderConstants.PENDING_ORDER_STATUS.equalsIgnoreCase(pendingOrder.getOrderStatus())){
					return OrderConstants.PENDING_ORDER_STATUS;
				}
			}
		}
		return StringPool.BLANK;
	}
	@Override
	public Map<String, Order> getSelectedOrders(String status,String[] orderIds, long sellerId, PortletRequest portletRequest) throws Exception{
		long startSec = System.currentTimeMillis();
		log.debug(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+startString+classOrderServName+getSelectMethName+timeLog+startSec);
		Map<String, Order> selectedOrders = new HashMap<String, Order>();
		OrderBasket orderBasket = orderBasketService.getOrderBasket(portletRequest);
		for(String ordID : orderIds){
			log.debug("orderid : " + ordID);
		}
		RetrieveOrders selectedOrdersFromService = orderDAO.getSelectedOrders(status, orderIds, sellerId);	
		selectedOrders =  extractOrdersForDisplay(portletRequest, selectedOrdersFromService.getCustomerOrder());
		Map<String, Order> tempselOrders = new HashMap<String, Order>();
		//Filtering only selected released orders
		for (String ordrId : orderIds) {
			if(selectedOrders.get(ordrId) != null){
				tempselOrders.put(ordrId, selectedOrders.get(ordrId));
			}
		}
		selectedOrders = tempselOrders;
		orderBasket.setAllOrderDetails(selectedOrders);
		orderBasketService.setOrderBasket(orderBasket, portletRequest);
		long endSec = System.currentTimeMillis();
		log.debug(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+endString+classOrderServName+getSelectMethName+timeLog+endSec);
		log.debug(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+totalElapsedStr+classOrderServName+getSelectMethName+timeInSec+(endSec-startSec)/1000);
		return selectedOrders;
	}
	@Override
	public Map<String, Order> getOrdersForPrintDownload(String status, Date startDate, Date endDate, String deliveryOption, String sellerId, PortletRequest portletRequest) throws Exception{
		Map<String, Order> selectedOrders = new HashMap<String, Order>();
		OrderBasket orderBasket = orderBasketService.getOrderBasket(portletRequest);
		RetrieveOrders selectedOrdersFromService = orderDAO.getSelectedOrdersForPrintDownLd(status, startDate, endDate, deliveryOption, sellerId, portletRequest);	
		selectedOrders =  extractOrdersForDisplay(portletRequest, selectedOrdersFromService.getCustomerOrder());
		orderBasket.setAllOrderDetails(selectedOrders);
		log.info("total orders selected : " + selectedOrders.keySet().size());
		orderBasketService.setOrderBasket(orderBasket, portletRequest);
		return selectedOrders;
	}
	@Override
	public Order getOrderDetails(String orderId, long sellerId, PortletRequest portletRequest) throws Exception{
		Order selectedOrderDetails = null;
		OrderBasket orderBasket = orderBasketService.getOrderBasket(portletRequest);
		RetrieveOrders selectedOrderDetailsFromService = orderDAO.getOrderDetails(orderId, sellerId);
		selectedOrderDetails =  extractOrderDetails(portletRequest, selectedOrderDetailsFromService.getCustomerOrder(),orderId);
		if(selectedOrderDetails != null){
			Map<String, Order> allOrderDetails = new HashMap<String, Order>();
			allOrderDetails.put(selectedOrderDetails.getOrderId(), selectedOrderDetails);
			orderBasket.setAllOrderDetails(allOrderDetails);
			orderBasketService.setOrderBasket(orderBasket, portletRequest);
		}
		return selectedOrderDetails;
	}
	/**
	 * get order list.
	 */
	@Override
	public ResponseSearchOrders getOrderList(String orderId, long sellerId, PortletRequest portletRequest) {
		ResponseSearchOrders mySearchOrder = null;
		Map<String,Order> searchedOrdersMap = null;
		try {
			RetrieveOrders matchedOrderDetails = orderDAO.getOrderDetails(orderId, sellerId);
			if(Validator.isNotNull(matchedOrderDetails)){
				if(matchedOrderDetails.getCustomerOrder().size() != 0) {
					searchedOrdersMap = new LinkedHashMap<String, Order>();
					for(CustomerOrderType custOrderType : matchedOrderDetails.getCustomerOrder()) {
						Order order = getOrderDetailsWithStatus(custOrderType, portletRequest);
						searchedOrdersMap.put(order.getOrderId(), order);
					}
					if(searchedOrdersMap.size()>0){
						mySearchOrder = new ResponseSearchOrders();
						List<ResponseSearchOrders.ResponseOrder> mySearchResults = populateResponseOrderDetails(searchedOrdersMap, portletRequest);
						mySearchOrder.setResponseOrderList(mySearchResults);
						mySearchOrder.setTotalOrders(searchedOrdersMap.size());
						portletRequest.getPortletSession().setAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY, StringPool.BLANK);
					} else{
						portletRequest.getPortletSession().setAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY, OrderConstants.NO_ORDERS_FOUND);
					}
				} else{
					portletRequest.getPortletSession().setAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY, OrderConstants.NO_ORDERS_FOUND);
				}
			}else{
				portletRequest.getPortletSession().setAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY, "Service Unavailable.");
				log.error("searchOrderResponse is NULL");
			}
		} catch (Exception e) {
			portletRequest.getPortletSession().setAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY, "Error in manupulating the date");
			log.error(OrderConstants.ORDMG_E031+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E031)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
			e.printStackTrace();
		}
		return mySearchOrder;
	}
	private List<ResponseSearchOrders.ResponseOrder> populateResponseOrderDetails(Map<String, Order> searchedOrdersMap, PortletRequest portletRequest) {

		List<ResponseSearchOrders.ResponseOrder> mySearchResults = new ArrayList<ResponseSearchOrders.ResponseOrder>();
		for (Map.Entry<String, Order> orderFromServiceMap : searchedOrdersMap.entrySet()) {
			Order orderFromService = orderFromServiceMap.getValue();
			ResponseSearchOrders.ResponseOrder myOrder = new ResponseSearchOrders.ResponseOrder();
			ResponseOrderSummary myOrderSummary = new ResponseOrderSummary();
			
			myOrderSummary.setOrderId(orderFromService.getCustomerOrderNumber());
			myOrderSummary.setReleaseOrderNumber(orderFromService.getOrderId());
			myOrderSummary.setTotalLineItems(orderFromService.getNoOfLines());
			
			OrderBasket orderBasket = orderBasketService.getOrderBasket(portletRequest);
			List<Order> ordersForUpdateInBasket = orderBasket.getOrdersForUpdate();
			
			HashMap<String, Transaction> transDetailsForDisAndCan = orderBasket.getTransactionDetailsOfDispatchAndCancel();
			HashMap<String, Transaction> transDetailsForReturn = orderBasket.getTransactionDetailsOfReturn();
			transDetailsForDisAndCan.putAll(transDetailsForReturn);
			String transactionStatus = null;
			if(Validator.isNotNull(transDetailsForDisAndCan)){
				for (Map.Entry<String, Transaction> entry:transDetailsForDisAndCan.entrySet()) {
					if(entry.getKey().equalsIgnoreCase(myOrderSummary.getReleaseOrderNumber())){
						transactionStatus = getTransactionStatusOfOrder(entry.getValue(), portletRequest);
					}
				}
			}
			if(Validator.isNotNull(transactionStatus) && OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INPROGRESS.equalsIgnoreCase(transactionStatus)){
				myOrderSummary.setStatus(OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INPROGRESS);
			}else if(Validator.isNotNull(ordersForUpdateInBasket)){
				myOrderSummary.setStatus(getOrderStatusFromBasket(myOrderSummary.getReleaseOrderNumber(),ordersForUpdateInBasket));
			}
			
			if(Validator.isNull(myOrderSummary.getStatus()) || "".equals(myOrderSummary.getStatus().trim())){
				myOrderSummary.setStatus(orderFromService.getOrderStatus());
			}
			myOrderSummary.setExpectedDispatchDate(DateUtil.formatDate(orderFromService.getExpectedDispatchDate()));
			myOrderSummary.setDeliveryOption(orderFromService.getDeliveryOption());
			myOrderSummary.setBillToName(orderFromService.getCustomerFirstName() + " " + orderFromService.getCustomerLastName());
			myOrderSummary.setShipToName(orderFromService.getShipToFName() + " " + orderFromService.getShipToLName());
			
			myOrder.setOrderSummary(myOrderSummary);
			mySearchResults.add(myOrder);
		}
		return mySearchResults;
	}
	@Override
	public Map<String, Order> getReleasedOrders(Date startDate, Date endDate, long sellerId, PortletRequest portletRequest) throws Exception {
		
		PortletSession portletSession = portletRequest.getPortletSession();
		
		long oldSellerId = Validator.isNotNull(portletSession.getAttribute(OrderConstants.CURRENT_SELLER_ID,PortletSession.APPLICATION_SCOPE)) ? (Long) portletSession.getAttribute(OrderConstants.CURRENT_SELLER_ID,PortletSession.APPLICATION_SCOPE) : sellerId;
		Map<String, Order> releasedOrdersForDisplay = null;
		
		OrderBasket orderBasket = orderBasketService.getOrderBasket(portletRequest);
		if(sellerId != oldSellerId){
			orderBasket.clear();
		}
		releasedOrdersForDisplay = orderBasket.getReleasedOrders();
		//If released orders not found in order basket i.e session then get it from OMS via DAO
		if (!(releasedOrdersForDisplay != null && releasedOrdersForDisplay.size() > 0)){
			//Get released orders based on date filter criteria selected by user.
			
			List<CustomerOrderType> releasedOrdersFromDAO = null;
			try {
				releasedOrdersFromDAO = orderDAO.getReleasedOrders(startDate, endDate, sellerId).getCustomerOrder();
			} catch (Exception e) {
				log.error(OrderConstants.ORDMG_E007+OrderConstants.LOG_MESSAGE_SEPERATOR+PropsUtil.get(OrderConstants.ORDMG_E007)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
			}
			//Get a Map of Order objects that can be displayed in released orders page. 
			releasedOrdersForDisplay = extractOrdersForDisplay(portletRequest, releasedOrdersFromDAO);
			if(releasedOrdersForDisplay != null){
				orderBasket.setReleasedOrders(releasedOrdersForDisplay);
				orderBasketService.setOrderBasket(orderBasket, portletRequest);
				portletSession.setAttribute(OrderConstants.CURRENT_SELLER_ID,sellerId, PortletSession.APPLICATION_SCOPE);
			}
		} else {
			
			log.debug("Released orders already available in session, hence not fetching from DAO");
		}
		
		return releasedOrdersForDisplay;
	}
	@Override
	public Map<String, Order> getDispatchedOrders(Date startDate, Date endDate, long sellerId, PortletRequest portletRequest) throws Exception {
		
		log.debug("Coming into getDispatchedOrders() of service");
		PortletSession portletSession = portletRequest.getPortletSession();
		
		long oldSelerId = Validator.isNotNull(portletSession.getAttribute(OrderConstants.CURRENT_SELLER_ID_IN_DISPATCH,PortletSession.APPLICATION_SCOPE)) ? (Long) portletSession.getAttribute(OrderConstants.CURRENT_SELLER_ID_IN_DISPATCH,PortletSession.APPLICATION_SCOPE) : sellerId;
		
		Map<String, Order> dispatchedOrdersForDisplay = null;
		
		OrderBasket orderBasket = orderBasketService.getOrderBasket(portletRequest);
		if(sellerId != oldSelerId){
			orderBasket.clear();
		}
		dispatchedOrdersForDisplay = orderBasket.getDispatchedOrders();
		
		//If dispatched orders not found in order basket i.e session then get it from OMS via DAO
		if (!(dispatchedOrdersForDisplay != null && dispatchedOrdersForDisplay.size() > 0)){
			
			//Get dispatched orders based on date filter criteria selected by user.
			List<CustomerOrderType> dispatchedOrdersFromDAO = null;
			try {
				dispatchedOrdersFromDAO = orderDAO.getDispatchedOrders(startDate, endDate, sellerId).getCustomerOrder();
			} catch (Exception e) {
				log.error(OrderConstants.ORDMG_E009+OrderConstants.LOG_MESSAGE_SEPERATOR+PropsUtil.get(OrderConstants.ORDMG_E009)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
			}
			
			//Get a Map of Order objects that can be displayed in dispatched orders page. 
			dispatchedOrdersForDisplay = extractOrdersForDispatchDisplay(portletRequest, dispatchedOrdersFromDAO);
			if(dispatchedOrdersForDisplay != null){
				orderBasket.setDispatchedOrders(dispatchedOrdersForDisplay);
				orderBasketService.setOrderBasket(orderBasket, portletRequest);
				portletSession.setAttribute(OrderConstants.CURRENT_SELLER_ID_IN_DISPATCH,sellerId, PortletSession.APPLICATION_SCOPE);
			}
		} else {
			
			log.debug("Dispatched orders already available in session, hence not fetching from DAO");
		}
		return dispatchedOrdersForDisplay;	
	}
	public Order extractOrderDetails(PortletRequest portletRequest, List<CustomerOrderType> ordersFromDAO,String selectOrderItem) {
		Order orderDetails = null;
		List<CustomerOrderType> orders =ordersFromDAO;
		CustomerOrderType orderFromDAO = null;
		
		if(Validator.isNotNull(ordersFromDAO) && ordersFromDAO.size()>0){
			int orderDet = 0;
			boolean isOrderExist = false;
			for(int i=0; i<orders.size(); i++){
				if(selectOrderItem.equalsIgnoreCase(ordersFromDAO.get(i).getSellerOrderNumber())){
					orderDet = i;
					isOrderExist = true;
					break;
				}
			}
			if(isOrderExist){ //If searched order exists
				orderFromDAO = ordersFromDAO.get(orderDet);
				orderDetails = getOrderDetailsWithStatus(orderFromDAO, portletRequest);
			}
		}
		return orderDetails;
	}
	private Order getOrderDetailsWithStatus(CustomerOrderType orderFromDAO, PortletRequest portletRequest) {
		
		Order orderDetails = new Order();
		
		double noOfDispatchedOrderQty = 0;
		double noOfCancelledOrderQty = 0;
		double noOfCollectedOrderQty=0;
		double noOfAwaitCollectedOrderQty=0;
		double noOfReleasedOrderQty = 0;
		double noOfAcknowledgedQty = 0;
		double noOfReturnedOrderQty = 0;
		double noOfOrderedQty = 0;
		double orderedQuantity = 0;
		double costPerItem = 0;
		double itemValue = 0;
		String cancellationReason = null;
		String primaryReturnReason = null;
		String secondaryReturnReason = null;
		boolean isDeliveryInstructionFound = false;
		orderDetails.setOrderId(orderFromDAO.getSellerOrderNumber());
		orderDetails.setCustomerOrderNumber(orderFromDAO.getCustomerOrderNumber());
		Date utilDate = DateUtil.toDate(orderFromDAO.getOrderDate());
		orderDetails.setOrderPlacedDate(DateUtil.formatDate(utilDate));
		List<LineItemType> orderLineItemsFromDAO = orderFromDAO.getOrderLines().getLineItem();
		OrderBasket orderBasket = orderBasketService.getOrderBasket(portletRequest);
		List<Order> ordersInBasket = orderBasket.getOrdersForUpdate();
		boolean isOrderInBasket = false;
		Map<Integer, OrderLineItem> basketOrdLines =  new HashMap<Integer, OrderLineItem>();
		for (Order order : ordersInBasket) {
			if(order.getOrderId().equalsIgnoreCase(orderFromDAO.getSellerOrderNumber())){
				isOrderInBasket = true;
				basketOrdLines.putAll(order.getOrderLineItems());
			}
		}
		Map<Integer, StringBuilder> trackingListDisp = new LinkedHashMap<Integer, StringBuilder>();
		List<com.tesco.mhub.order.retrieve.order.Shipment> shipmentTrackingList=new LinkedList<com.tesco.mhub.order.retrieve.order.Shipment>();
		if(orderFromDAO.getShipments() != null){
			 shipmentTrackingList=orderFromDAO.getShipments().getShipment();
		}
		for (com.tesco.mhub.order.retrieve.order.Shipment shipmentTrackingFromDAO : shipmentTrackingList) {
			List<com.tesco.mhub.order.retrieve.order.ShipmentLine> shipmentLine=new LinkedList<com.tesco.mhub.order.retrieve.order.ShipmentLine>();
			shipmentLine=shipmentTrackingFromDAO.getShipmentLines().getShipmentLine();
			String trackingId=shipmentTrackingFromDAO.getParcelTrackingNumber();
			Iterator<com.tesco.mhub.order.retrieve.order.ShipmentLine> itr=shipmentLine.iterator();
			while(itr.hasNext()){
				int id=Integer.parseInt(itr.next().getOrderLineNumber());
				if(trackingListDisp.get(id) != null){
					if(trackingListDisp.get(id).length()>0){
						trackingListDisp.get(id).append("<br>");
					}
					trackingListDisp.put(id,trackingListDisp.get(id).append(trackingId));
				}else{
					trackingListDisp.put(id,new StringBuilder(trackingId));
				}	
			}
			
		}
		orderDetails.setShipmentTrackingDetails(trackingListDisp);
		Map<Integer, OrderLineItem> orderLineItemsForDisplay = new LinkedHashMap<Integer, OrderLineItem>();
		for (LineItemType orderLineItemFromDAO : orderLineItemsFromDAO) {
			
			double noOfDispatchedOrderLineQty = 0;
			double noOfCancelleOrderdLineQty = 0;
			double noOfCollectedOrderdLineQty = 0;
			double noOfReleasedOrderLineQty = 0;
			double noOfAcknowledgedLineQty = 0;
			double noOfReturnedOrderLineQty = 0;
			double noOfOrderedLineQty = 0;
			double noOfAwaitCollectedOrderdLineQty= 0;
			noOfOrderedLineQty = orderLineItemFromDAO.getOrderedQuantity().doubleValue();
			OrderLineItem orderLineItemForDisplay = new OrderLineItem();
			orderDetails.setNoOfLines(orderLineItemsFromDAO.size());
			orderDetails.setCustomerFirstName(orderLineItemFromDAO.getCustomerDetails().getFirstName());
			orderDetails.setCustomerLastName(orderLineItemFromDAO.getCustomerDetails().getLastName());
			try {
				orderDetails.setDeliveryOption(SystemMHubMappingLocalServiceUtil.getMHubValue(OrderConstants.SYSTEM_NAME, OrderConstants.SYSTEM_ATTRIBUTE,orderLineItemFromDAO.getDeliveryDetails().getDeliveryOption()));
			} catch (NoSuchSystemMHubMappingException e) {
				log.error("NoSuchSystemMHubMappingException");
				orderDetails.setDeliveryOption(orderLineItemFromDAO.getDeliveryDetails().getDeliveryOption());
			} catch (SystemException e) {
				log.error("SystemException");
				orderDetails.setDeliveryOption(orderLineItemFromDAO.getDeliveryDetails().getDeliveryOption());
			}
			orderDetails.setCustomerEmail(orderLineItemFromDAO.getCustomerDetails().getEmail());
			orderDetails.setCustomerMobile(orderLineItemFromDAO.getCustomerDetails().getMobileNo());
			orderDetails.setDeliveryCost(Double.parseDouble(orderLineItemFromDAO.getDeliveryDetails().getDeliveryCharges()));
			utilDate = DateUtil.toDate(orderLineItemFromDAO.getDeliveryDetails().getExpectedDispatchDate());
			orderDetails.setExpectedDispatchDate(DateUtil.formatDate(utilDate));
			utilDate = DateUtil.toDate(orderLineItemFromDAO.getDeliveryDetails().getExpectedDeliveryDate());
			orderLineItemForDisplay.setExpectedDeliveryDate(DateUtil.formatDate(utilDate));
			orderDetails.setCustomerDayTelephone(orderLineItemFromDAO.getCustomerDetails().getHomeTelDay());
			orderDetails.setCustomerEveningTelephone(orderLineItemFromDAO.getCustomerDetails().getHomeTelNight());
			orderDetails.setShipToAddress(orderLineItemFromDAO.getShippingAddress().getShipToAddressLine1() 
					+ StringPool.SPACE + orderLineItemFromDAO.getShippingAddress().getShipToAddressLine2() 
					+ StringPool.SPACE + orderLineItemFromDAO.getShippingAddress().getShipToAddressLine3() 
					+ StringPool.SPACE + orderLineItemFromDAO.getShippingAddress().getShipToAddressLine4() 
					+ StringPool.SPACE + orderLineItemFromDAO.getShippingAddress().getShipToAddressLine5() 
					+ StringPool.SPACE + orderLineItemFromDAO.getShippingAddress().getShipToAddressLine6()
					+ StringPool.SPACE + orderLineItemFromDAO.getShippingAddress().getShipToCity()
					+ StringPool.SPACE + orderLineItemFromDAO.getShippingAddress().getShipToCounty()
					+ StringPool.SPACE + orderLineItemFromDAO.getShippingAddress().getShipToCountry()
					+ StringPool.SPACE + orderLineItemFromDAO.getShippingAddress().getShipToPostCode()
					);
			orderDetails.setShipToMobile(orderLineItemFromDAO.getShippingAddress().getShipToMobileNo());
			List<OrderStatusType> statHistory = orderLineItemFromDAO.getOrderLineStatusHistory().getOrderStatus();
			for(OrderStatusType ordStatus : statHistory){
				if (Validator.isNotNull(ordStatus.getCancellationReasonCode())){
					cancellationReason = ordStatus.getCancellationReasonCode();
				} else {
					cancellationReason = StringPool.DASH;
				}
				if(Validator.isNotNull(ordStatus.getPrimaryReturnReasonCode())){
					primaryReturnReason = ordStatus.getPrimaryReturnReasonCode();
				} else {
					primaryReturnReason = StringPool.DASH;
				}
				if(Validator.isNotNull(ordStatus.getSecondaryReturnReasonCode())){
					secondaryReturnReason = ordStatus.getSecondaryReturnReasonCode();
				} else {
					secondaryReturnReason = StringPool.DASH;
				}
				if((OrderConstants.STRING_ORDER_STATUS_3200_210).equals(ordStatus.getStatus())){
					// reason code 3200.210 indicates status = released
					noOfReleasedOrderLineQty += ordStatus.getQuantity().doubleValue();
					noOfReleasedOrderQty += ordStatus.getQuantity().doubleValue();
					if(!StringPool.DASH.equals(primaryReturnReason)){
					orderLineItemForDisplay.setPrimaryReasonForCancellation(PortletProps.get(OrderConstants.REASON_CODE + primaryReturnReason));
					}else {
						orderLineItemForDisplay.setPrimaryReasonForCancellation(StringPool.DASH);
					}
					if(!StringPool.DASH.equals(secondaryReturnReason)){
					orderLineItemForDisplay.setSecondaryReasonForReturn(PortletProps.get(OrderConstants.SECONDARY_REASON_CODE + secondaryReturnReason));
					}  else {
						orderLineItemForDisplay.setSecondaryReasonForReturn(StringPool.DASH);
					}
				}
				if((OrderConstants.STRING_ORDER_STATUS_3700).equals(ordStatus.getStatus())){
					// reason code 3700 indicates status = dispatch
					noOfDispatchedOrderLineQty += ordStatus.getQuantity().doubleValue();
					noOfDispatchedOrderQty += ordStatus.getQuantity().doubleValue();
					if(!StringPool.DASH.equals(primaryReturnReason)){
					orderLineItemForDisplay.setPrimaryReasonForCancellation(PortletProps.get(OrderConstants.REASON_CODE + primaryReturnReason));
					} else {
						orderLineItemForDisplay.setPrimaryReasonForCancellation(StringPool.DASH);
					}
					
					if(!StringPool.DASH.equals(secondaryReturnReason)){
					orderLineItemForDisplay.setSecondaryReasonForReturn(PortletProps.get(OrderConstants.SECONDARY_REASON_CODE + secondaryReturnReason));
					} else {
						orderLineItemForDisplay.setSecondaryReasonForReturn(StringPool.DASH);
					}
				}
				if((OrderConstants.STRING_ORDER_STATUS_9000).equals(ordStatus.getStatus())){
					// reason code 9000 indicates status = cancel 
					noOfCancelleOrderdLineQty += ordStatus.getQuantity().doubleValue();
					noOfCancelledOrderQty += ordStatus.getQuantity().doubleValue();
					if(!StringPool.DASH.equals(cancellationReason)){
					orderLineItemForDisplay.setPrimaryReasonForCancellation(PortletProps.get(OrderConstants.REASON_CODE + cancellationReason));
					} else {
						orderLineItemForDisplay.setPrimaryReasonForCancellation(StringPool.DASH);
					}
					
					if(!StringPool.DASH.equals(secondaryReturnReason)){
					orderLineItemForDisplay.setSecondaryReasonForReturn(PortletProps.get(OrderConstants.SECONDARY_REASON_CODE + secondaryReturnReason));
					} else {
						orderLineItemForDisplay.setSecondaryReasonForReturn(StringPool.DASH);
					}
				}
				if(OrderConstants.STRING_ORDER_STATUS_3700_01.equals(ordStatus.getStatus())){
					//reason code 3700.01 indicates status = return
					noOfReturnedOrderLineQty += ordStatus.getQuantity().doubleValue();
					noOfReturnedOrderQty += ordStatus.getQuantity().doubleValue();
					if(!StringPool.DASH.equals(primaryReturnReason)){
					orderLineItemForDisplay.setPrimaryReasonForCancellation(PortletProps.get(OrderConstants.REASON_CODE + primaryReturnReason));
					} else {
						orderLineItemForDisplay.setPrimaryReasonForCancellation(StringPool.DASH);
					}
					
					if(!StringPool.DASH.equals(secondaryReturnReason)){
					orderLineItemForDisplay.setSecondaryReasonForReturn(PortletProps.get(OrderConstants.SECONDARY_REASON_CODE + secondaryReturnReason));
					} else {
						orderLineItemForDisplay.setSecondaryReasonForReturn(StringPool.DASH);
					}
				}
				if(OrderConstants.STRING_ORDER_STATUS_3700_9400.equals(ordStatus.getStatus())){
					//reason code 3700.9400 indicates status = collected
					noOfCollectedOrderdLineQty += ordStatus.getQuantity().doubleValue();
					noOfCollectedOrderQty += ordStatus.getQuantity().doubleValue();
					if(!StringPool.DASH.equals(primaryReturnReason)){
					orderLineItemForDisplay.setPrimaryReasonForCancellation(PortletProps.get(OrderConstants.REASON_CODE + primaryReturnReason));
					} else {
						orderLineItemForDisplay.setPrimaryReasonForCancellation(StringPool.DASH);
					}
					
					if(!StringPool.DASH.equals(secondaryReturnReason)){
					orderLineItemForDisplay.setSecondaryReasonForReturn(PortletProps.get(OrderConstants.SECONDARY_REASON_CODE + secondaryReturnReason));
					} else {
						orderLineItemForDisplay.setSecondaryReasonForReturn(StringPool.DASH);
					}
				}
				if(OrderConstants.STRING_ORDER_STATUS_3700_8300.equals(ordStatus.getStatus())){
					//reason code 3700.9400 indicates status = collected
					noOfAwaitCollectedOrderdLineQty += ordStatus.getQuantity().doubleValue();
					noOfAwaitCollectedOrderQty += ordStatus.getQuantity().doubleValue();
					if(!StringPool.DASH.equals(primaryReturnReason)){
					orderLineItemForDisplay.setPrimaryReasonForCancellation(PortletProps.get(OrderConstants.REASON_CODE + primaryReturnReason));
					} else {
						orderLineItemForDisplay.setPrimaryReasonForCancellation(StringPool.DASH);
					}
					
					if(!StringPool.DASH.equals(secondaryReturnReason)){
					orderLineItemForDisplay.setSecondaryReasonForReturn(PortletProps.get(OrderConstants.SECONDARY_REASON_CODE + secondaryReturnReason));
					} else {
						orderLineItemForDisplay.setSecondaryReasonForReturn(StringPool.DASH);
					}
				}
				//Checking for Acknowledged order line item
				if(OrderConstants.STRING_ORDER_STATUS_3200_215.equals(ordStatus.getStatus())){
					//reason code 3700.01 indicates status = return
					noOfAcknowledgedLineQty += ordStatus.getQuantity().doubleValue();
					noOfAcknowledgedQty += ordStatus.getQuantity().doubleValue();
					if(!StringPool.DASH.equals(primaryReturnReason)){
					orderLineItemForDisplay.setPrimaryReasonForCancellation(PortletProps.get(OrderConstants.REASON_CODE + primaryReturnReason));
					} else {
						orderLineItemForDisplay.setPrimaryReasonForCancellation(StringPool.DASH);
					}
					
					if(!StringPool.DASH.equals(secondaryReturnReason)){
					orderLineItemForDisplay.setSecondaryReasonForReturn(PortletProps.get(OrderConstants.SECONDARY_REASON_CODE + secondaryReturnReason));
					} else {
						orderLineItemForDisplay.setSecondaryReasonForReturn(StringPool.DASH);
					}
				}
			}
			noOfOrderedLineQty = noOfOrderedLineQty + noOfCancelleOrderdLineQty;
			noOfOrderedQty += noOfOrderedLineQty;
			orderLineItemForDisplay.setOpenQuantity(noOfReleasedOrderLineQty+noOfAcknowledgedLineQty);
			orderLineItemForDisplay.setDispatchedQuantity(noOfDispatchedOrderLineQty);
			orderLineItemForDisplay.setCollectedQuantity(noOfCollectedOrderdLineQty);
			orderLineItemForDisplay.setCancelledQuantity(noOfCancelleOrderdLineQty);
			orderLineItemForDisplay.setReturnedQuantity(noOfReturnedOrderLineQty);
			orderLineItemForDisplay.setAcknowledgedQuantity(noOfAcknowledgedLineQty);
			orderLineItemForDisplay.setOrderedQuantity(noOfOrderedLineQty);
			for (Map.Entry<Integer, StringBuilder> entry : trackingListDisp.entrySet()){
			    if(entry.getValue().length()<=0){
			    	entry.setValue(new StringBuilder().append("N/A"));
			    }
			}
			if(trackingListDisp.get(orderLineItemFromDAO.getLineNumber().intValue()) != null){
				orderLineItemForDisplay.setTrackingId(trackingListDisp.get(orderLineItemFromDAO.getLineNumber().intValue()));
			}
			orderLineItemForDisplay.setOrderLineNumber(orderLineItemFromDAO.getLineNumber().intValue());				
			orderLineItemForDisplay.setDescription(orderLineItemFromDAO.getItemShortDescription());
			orderLineItemForDisplay.setSku(orderLineItemFromDAO.getMerchantSkuId());
			orderLineItemForDisplay.setCostPerItem(new Double(orderLineItemFromDAO.getRetailPrice()));
			orderLineItemForDisplay.setUnitOfSale(orderLineItemFromDAO.getUnitOfSale());
			//set gift related info
			orderLineItemForDisplay.setIsGiftWrappedOrder(orderLineItemFromDAO.getIsGiftWrappedOrder());
			orderLineItemForDisplay.setGiftMessage(orderLineItemFromDAO.getGiftMessage());
			//installtion Number and bundle item line number
			orderLineItemForDisplay.setInstallationLineNumber(orderLineItemFromDAO.getInstallationLineNumber());
			orderLineItemForDisplay.setBundleItemLineNumber(orderLineItemFromDAO.getBundleItemLineNumber());
			//set shipping details
			orderLineItemForDisplay.setShipToFirstName(orderLineItemFromDAO.getShippingAddress().getShipToFirstName());
			orderDetails.setShipToFName(orderLineItemForDisplay.getShipToFirstName());
			orderLineItemForDisplay.setShipToLastName(orderLineItemFromDAO.getShippingAddress().getShipToLastName());
			orderDetails.setShipToLName(orderLineItemForDisplay.getShipToLastName());
			orderLineItemForDisplay.setShipToCounty(orderLineItemFromDAO.getShippingAddress().getShipToCounty());
			orderLineItemForDisplay.setShipToCountry(orderLineItemFromDAO.getShippingAddress().getShipToCountry());
			orderLineItemForDisplay.setShipToPostcode(orderLineItemFromDAO.getShippingAddress().getShipToPostCode());
			orderLineItemForDisplay.setShipToCity(orderLineItemFromDAO.getShippingAddress().getShipToCity());
			orderLineItemForDisplay.setShipingAddress1(orderLineItemFromDAO.getShippingAddress().getShipToAddressLine1());
			orderLineItemForDisplay.setShipingAddress2(orderLineItemFromDAO.getShippingAddress().getShipToAddressLine2());
			orderLineItemForDisplay.setShipingAddress3(orderLineItemFromDAO.getShippingAddress().getShipToAddressLine3());
			orderLineItemForDisplay.setShipingAddress4(orderLineItemFromDAO.getShippingAddress().getShipToAddressLine4());
			orderLineItemForDisplay.setShipingAddress5(orderLineItemFromDAO.getShippingAddress().getShipToAddressLine5());
			orderLineItemForDisplay.setShipingAddress6(orderLineItemFromDAO.getShippingAddress().getShipToAddressLine6());
			orderLineItemForDisplay.setShipToStoreName(orderLineItemFromDAO.getShippingAddress().getShipToStoreName());
			orderLineItemForDisplay.setShipToStoreNumber(orderLineItemFromDAO.getShippingAddress().getShipToStoreNumber());
			orderLineItemForDisplay.setShipToHomeTelDay(orderLineItemFromDAO.getShippingAddress().getShipToHomeTelDay());
			orderLineItemForDisplay.setShipToHomeTelNight(orderLineItemFromDAO.getShippingAddress().getShipToHomeTelNight());
			log.info("shiptohometelday " + orderLineItemForDisplay.getShipToHomeTelDay() + " night " + orderLineItemForDisplay.getShipToHomeTelNight());
			//set delivery details
			orderLineItemForDisplay.setCarrierName(orderLineItemFromDAO.getDeliveryDetails().getCarrierName());
			orderLineItemForDisplay.setCarrierServiceCode(orderLineItemFromDAO.getDeliveryDetails().getCarrierServiceCode());
			orderLineItemForDisplay.setIsPODRequired(orderLineItemFromDAO.getDeliveryDetails().getIsPodRequired());
			orderLineItemForDisplay.setMinimumAgeRequired(orderLineItemFromDAO.getDeliveryDetails().getMinimumAgeRequired());
			orderLineItemForDisplay.setDeliveryInstructions(orderLineItemFromDAO.getDeliveryDetails().getDeliveryInstructions());
			/** R6 - Get Delivery instruction from order line items **/
			if(orderLineItemForDisplay.getDeliveryInstructions() != null && !isDeliveryInstructionFound) {
				orderDetails.setDeliveryInstructions(orderLineItemForDisplay.getDeliveryInstructions());
				isDeliveryInstructionFound = true;
			}
			orderLineItemsForDisplay.put(orderLineItemFromDAO.getLineNumber().intValue(), orderLineItemForDisplay);
			if(noOfCancelleOrderdLineQty>0 && noOfReleasedOrderLineQty==0 && noOfDispatchedOrderLineQty==0 && noOfReturnedOrderLineQty==0 && noOfAcknowledgedLineQty==0 && noOfCollectedOrderdLineQty==0 ){
				orderLineItemForDisplay.setLineItemStatus(cancelledOrder);
			}else if(noOfCancelleOrderdLineQty>0){
				orderLineItemForDisplay.setLineItemStatus(OrderConstants.STRING_PARTIALLY_CANCELLED);
			}else if(noOfReturnedOrderLineQty>0 && noOfDispatchedOrderLineQty==0 && noOfReleasedOrderLineQty==0 && noOfAcknowledgedLineQty==0){
				orderLineItemForDisplay.setLineItemStatus(OrderConstants.SEARCH_ORDER_STATUS_RETURNED);
			}else if(noOfReturnedOrderLineQty>0){
				orderLineItemForDisplay.setLineItemStatus(OrderConstants.SEARCH_ORDER_STATUS_PARTIALLY_RETURNED);
			}else if(noOfDispatchedOrderLineQty>0 && noOfReleasedOrderLineQty==0 && noOfAcknowledgedLineQty==0){
				orderLineItemForDisplay.setLineItemStatus(OrderConstants.RETRIEVE_ORDER_STATUS_DISPATCHED);
			}else if(noOfDispatchedOrderLineQty>0){
				orderLineItemForDisplay.setLineItemStatus(OrderConstants.STRING_PARTIALLY_DISPATCHED);
			}else if(noOfAcknowledgedLineQty>0 && noOfReleasedOrderLineQty==0){
				orderLineItemForDisplay.setLineItemStatus(OrderConstants.RETRIEVE_ORDER_STATUS_ONLY_ACKNOWLEDGED);
			}else if(noOfAcknowledgedLineQty>0){
				orderLineItemForDisplay.setLineItemStatus(OrderConstants.STRING_PARTIALLY_ACKNOWLEDGED);
			}else if(noOfReleasedOrderLineQty>0){
				orderLineItemForDisplay.setLineItemStatus(OrderConstants.OPEN_VAR);
			}else if(noOfCollectedOrderdLineQty>0){
				orderLineItemForDisplay.setLineItemStatus("Collected");
				orderLineItemForDisplay.setDispatchedQuantity(noOfCollectedOrderdLineQty);
			}else if(noOfAwaitCollectedOrderdLineQty>0){
				orderLineItemForDisplay.setLineItemStatus("Checked In Awaiting Collection");
				orderLineItemForDisplay.setDispatchedQuantity(noOfAwaitCollectedOrderdLineQty);
			}else{
				orderLineItemForDisplay.setLineItemStatus(noOrderStatus);
			}
			if(Validator.isNotNull(basketOrdLines)){
				
				OrderLineItem basketLineItem =  basketOrdLines.get(orderLineItemFromDAO.getLineNumber().intValue());
				if(Validator.isNotNull(basketLineItem)){
					orderLineItemForDisplay.setLineItemStatus(basketLineItem.getLineItemStatus());
				}
			}
			costPerItem = orderLineItemForDisplay.getCostPerItem();
			double orderedQuantityForCost = noOfOrderedLineQty - noOfCancelleOrderdLineQty;
			itemValue += (orderedQuantityForCost * costPerItem);
			orderDetails.setOrderLineItems(orderLineItemsForDisplay);
		}
		if(!isDeliveryInstructionFound && orderDetails.getDeliveryInstructions() == null) {
			orderDetails.setDeliveryInstructions(OrderConstants.NO_DELIVERY_INSTRUCTIONS);
		}
		orderDetails.setOrderValue(itemValue);
		getOrderStatus(orderDetails, noOfCancelledOrderQty,noOfReleasedOrderQty,noOfDispatchedOrderQty,noOfReturnedOrderQty,noOfAcknowledgedQty,noOfCollectedOrderQty,noOfAwaitCollectedOrderQty);
		if(isOrderInBasket){
			orderDetails.setOrderStatus("Pending");
		}
		return orderDetails;
	}
	private void getOrderStatus(Order orderDetails,double noOfCancelledOrderQty,double noOfReleasedOrderQty,double noOfDispatchedOrderQty,double noOfReturnedOrderQty,
			double noOfAcknowledgedQty,double noOfCollectedOrderQty, double noOfAwaitCollectedOrderQty) {
		if(noOfCancelledOrderQty>0 && noOfReleasedOrderQty==0 && noOfDispatchedOrderQty==0 && noOfReturnedOrderQty==0 && noOfAcknowledgedQty==0){
			orderDetails.setOrderStatus(cancelledOrder);
		}else if(noOfCancelledOrderQty>0){
			orderDetails.setOrderStatus(OrderConstants.STRING_PARTIALLY_CANCELLED);
		}else if(noOfReturnedOrderQty>0 && noOfDispatchedOrderQty==0 && noOfReleasedOrderQty==0 && noOfAcknowledgedQty==0){
			orderDetails.setOrderStatus("Returned");
		}else if(noOfReturnedOrderQty>0){
			orderDetails.setOrderStatus(OrderConstants.SEARCH_ORDER_STATUS_PARTIALLY_RETURNED);
		}else if(noOfCollectedOrderQty>0 && noOfReleasedOrderQty==0 && noOfAcknowledgedQty==0){
			orderDetails.setOrderStatus(OrderConstants.COLLECTED);
		}else if(noOfDispatchedOrderQty>0 && noOfReleasedOrderQty==0 && noOfAcknowledgedQty==0){
			orderDetails.setOrderStatus(OrderConstants.RETRIEVE_ORDER_STATUS_DISPATCHED);
		}else if(noOfDispatchedOrderQty>0){
			orderDetails.setOrderStatus(OrderConstants.STRING_PARTIALLY_DISPATCHED);
		}else if(noOfAcknowledgedQty>0 && noOfReleasedOrderQty==0){
			orderDetails.setOrderStatus(OrderConstants.RETRIEVE_ORDER_STATUS_ONLY_ACKNOWLEDGED);
		}else if(noOfAcknowledgedQty>0){
			orderDetails.setOrderStatus(OrderConstants.STRING_PARTIALLY_ACKNOWLEDGED);
		}else if(noOfReleasedOrderQty>0){
			orderDetails.setOrderStatus(OrderConstants.OPEN_VAR);
		}else if(noOfAwaitCollectedOrderQty>0){
			orderDetails.setOrderStatus(OrderConstants.AWAIT);
		}else{
			orderDetails.setOrderStatus(noOrderStatus);
		}
	}
	public Map<String, Order> extractOrdersForDisplay(PortletRequest portletRequest, List<CustomerOrderType> ordersFromDAO) {
		Map<String,Order> ordersForDisplay = new LinkedHashMap<String, Order>();
		for (CustomerOrderType orderFromDAO : ordersFromDAO) {
			double noOfDispatchedOrderQty = 0;
			double noOfCancelledOrderQty = 0;
			double noOfReleasedOrderQty = 0;
			double noOfAcknowledgedQty = 0;
			double noOfOrderedQty = 0;
			double orderedQuantity = 0;
			double costPerItem = 0;
			double itemValue = 0;
			double noOfReturnedOrderQty = 0;
			Order ordForDisp = new Order();
			ordForDisp.setOrderId(orderFromDAO.getSellerOrderNumber());
			ordForDisp.setCustomerOrderNumber(orderFromDAO.getCustomerOrderNumber());
			List<OrderLabelMin> ordrLblMinLst=new ArrayList<OrderLabelMin>();
			long sellerId = OrderManagementUtil.getSellerId(portletRequest);
			List<OrderLabelDetails> viewLblDtlLst = (List<OrderLabelDetails>) OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrderIdSellrIdIsUsed(ordForDisp.getOrderId(), sellerId, false);
			OrderLabelMin olm = null;
			if(viewLblDtlLst.size() >0){
			for (OrderLabelDetails itr : viewLblDtlLst) {
				olm = new OrderLabelMin(itr.getOrderId(), itr.getTrackingId(), itr.getCollectionDate());
				ordrLblMinLst.add(olm);
			}
			}else{
				log.debug("No label details available"+viewLblDtlLst.size());
			}
			Collections.reverse(ordrLblMinLst);
			ordForDisp.setOrderLblList(ordrLblMinLst);
			Date utilDate = DateUtil.toDate(orderFromDAO.getOrderDate());
			ordForDisp.setOrderPlacedDate(DateUtil.formatDate(utilDate));
			List<LineItemType> orderLineItemsFromDAO = orderFromDAO.getOrderLines().getLineItem();
			String cancellationReason = null;
			String primaryReturnReason = null;
			String secondaryReturnReason = null;
			boolean isDeliveryInstructionFound = false;
			Map<Integer, OrderLineItem> orderLineItemsForDisplay = new LinkedHashMap<Integer, OrderLineItem>();
			for (LineItemType orderItemLineFromDAO : orderLineItemsFromDAO) {
				double noOfDispatchedOrderLineQty = 0;
				double noOfCancelleOrderdLineQty = 0;
				double noOfAcknowledgedLineQty = 0;
				double noOfReturnedOrderLineQty = 0;
				double noOfReleasedOrderLineQty = 0;
				noOfOrderedQty += orderItemLineFromDAO.getOrderedQuantity().doubleValue();
				OrderLineItem ordItemLineForDisplay = new OrderLineItem();
				ordForDisp.setNoOfLines(orderLineItemsFromDAO.size());
				ordForDisp.setCustomerFirstName(orderItemLineFromDAO.getCustomerDetails().getFirstName());
				ordForDisp.setCustomerLastName(orderItemLineFromDAO.getCustomerDetails().getLastName());
				try {
					ordForDisp.setDeliveryOption(SystemMHubMappingLocalServiceUtil.getMHubValue(OrderConstants.SYSTEM_NAME, OrderConstants.SYSTEM_ATTRIBUTE,orderItemLineFromDAO.getDeliveryDetails().getDeliveryOption()));
				} catch (NoSuchSystemMHubMappingException e) {
					log.debug("NoSuchSystemMHubMappingException");
				} catch (SystemException e) {
					log.debug("SystemException");
				}
				utilDate = DateUtil.toDate(orderItemLineFromDAO.getDeliveryDetails().getExpectedDispatchDate());
				ordForDisp.setExpectedDispatchDate(DateUtil.formatDate(utilDate));
				utilDate = DateUtil.toDate(orderItemLineFromDAO.getDeliveryDetails().getExpectedDeliveryDate());
				ordItemLineForDisplay.setExpectedDeliveryDate(DateUtil.formatDate(utilDate));
				ordForDisp.setCustomerEmail(orderItemLineFromDAO.getCustomerDetails().getEmail());
				ordForDisp.setCustomerMobile(orderItemLineFromDAO.getCustomerDetails().getMobileNo());
				ordForDisp.setDeliveryCost(Double.parseDouble(orderItemLineFromDAO.getDeliveryDetails().getDeliveryCharges()));
				ordForDisp.setCustomerDayTelephone(orderItemLineFromDAO.getCustomerDetails().getHomeTelDay());
				ordForDisp.setCustomerEveningTelephone(orderItemLineFromDAO.getCustomerDetails().getHomeTelNight()+ StringPool.SPACE + orderItemLineFromDAO.getShippingAddress().getShipToAddressLine2());
				ordForDisp.setQuickShipToAddress(orderItemLineFromDAO.getShippingAddress().getShipToAddressLine1()+ StringPool.SPACE + orderItemLineFromDAO.getShippingAddress().getShipToAddressLine2());
				ordForDisp.setShipToAddress(orderItemLineFromDAO.getShippingAddress().getShipToAddressLine1() 
						+ StringPool.SPACE + orderItemLineFromDAO.getShippingAddress().getShipToAddressLine2() 
						+ StringPool.SPACE + orderItemLineFromDAO.getShippingAddress().getShipToAddressLine3() 
						+ StringPool.SPACE + orderItemLineFromDAO.getShippingAddress().getShipToAddressLine4() 
						+ StringPool.SPACE + orderItemLineFromDAO.getShippingAddress().getShipToAddressLine5() 
						+ StringPool.SPACE + orderItemLineFromDAO.getShippingAddress().getShipToAddressLine6()
						+ StringPool.SPACE + orderItemLineFromDAO.getShippingAddress().getShipToCity()
						+ StringPool.SPACE + orderItemLineFromDAO.getShippingAddress().getShipToCounty()
						+ StringPool.SPACE + orderItemLineFromDAO.getShippingAddress().getShipToCountry()
						+ StringPool.SPACE + orderItemLineFromDAO.getShippingAddress().getShipToPostCode()
						);
				ordForDisp.setShipToMobile(orderItemLineFromDAO.getShippingAddress().getShipToMobileNo());
				//Getting the latest status (OrderStatusType) from OrderLineStatusHistory
				List<OrderStatusType> statusHistory = orderItemLineFromDAO.getOrderLineStatusHistory().getOrderStatus();
				for(OrderStatusType ordStat : statusHistory){
					if((OrderConstants.STRING_ORDER_STATUS_3200_210).equals(ordStat.getStatus())){
						// 3200.210 staus is for release status
						noOfReleasedOrderLineQty += ordStat.getQuantity().doubleValue();
						noOfReleasedOrderQty += ordStat.getQuantity().doubleValue();
					}
					if(OrderConstants.STRING_ORDER_STATUS_3700_01.equals(ordStat.getStatus())){
						//reason code 3700.01 indicates status = return
						noOfReturnedOrderLineQty+= ordStat.getQuantity().doubleValue();
						noOfReturnedOrderQty += ordStat.getQuantity().doubleValue();
					}
					//Checking for Acknowledged order line item
					if(OrderConstants.STRING_ORDER_STATUS_3200_215.equals(ordStat.getStatus())){
						//reason code 3700.01 indicates status = return
						noOfAcknowledgedLineQty += ordStat.getQuantity().doubleValue();
						noOfAcknowledgedQty += ordStat.getQuantity().doubleValue();
					}
					if((OrderConstants.STRING_ORDER_STATUS_3700).equals(ordStat.getStatus())){
						// 3700 staus is for dispatch status
						noOfDispatchedOrderLineQty += ordStat.getQuantity().doubleValue();
						noOfDispatchedOrderQty += ordStat.getQuantity().doubleValue();
					}
					if((OrderConstants.STRING_ORDER_STATUS_9000).equals(ordStat.getStatus())){
						// 9000 staus is for cancel status
						noOfCancelleOrderdLineQty += ordStat.getQuantity().doubleValue();
						noOfCancelledOrderQty += ordStat.getQuantity().doubleValue();
					}
				if (Validator.isNotNull(ordStat.getCancellationReasonCode())){
					cancellationReason = ordStat.getCancellationReasonCode();
				} 
				if(Validator.isNotNull(ordStat.getPrimaryReturnReasonCode())){
					primaryReturnReason = ordStat.getPrimaryReturnReasonCode();
				}
				if(Validator.isNotNull(ordStat.getSecondaryReturnReasonCode())){
					secondaryReturnReason = ordStat.getSecondaryReturnReasonCode();
				}
				}
				ordItemLineForDisplay.setOpenQuantity(noOfReleasedOrderLineQty+noOfAcknowledgedLineQty);
				ordItemLineForDisplay.setDispatchedQuantity(noOfDispatchedOrderLineQty);
				ordItemLineForDisplay.setCancelledQuantity(noOfCancelleOrderdLineQty);
				ordItemLineForDisplay.setReturnedQuantity(noOfReturnedOrderLineQty);
				ordItemLineForDisplay.setAcknowledgedQuantity(noOfAcknowledgedLineQty);
				
				ordItemLineForDisplay.setOrderedQuantity(orderItemLineFromDAO.getOrderedQuantity().doubleValue());
				ordItemLineForDisplay.setOrderLineNumber(orderItemLineFromDAO.getLineNumber().intValue());				
				ordItemLineForDisplay.setDescription(orderItemLineFromDAO.getItemShortDescription());
				ordItemLineForDisplay.setSku(orderItemLineFromDAO.getMerchantSkuId());
				ordItemLineForDisplay.setCostPerItem(new Double(orderItemLineFromDAO.getRetailPrice()));
				ordItemLineForDisplay.setUnitOfSale(orderItemLineFromDAO.getUnitOfSale());
				//set gift related info
				ordItemLineForDisplay.setIsGiftWrappedOrder(orderItemLineFromDAO.getIsGiftWrappedOrder());
				ordItemLineForDisplay.setGiftMessage(orderItemLineFromDAO.getGiftMessage());
				//installtion Number and bundle item line number
				ordItemLineForDisplay.setInstallationLineNumber(orderItemLineFromDAO.getInstallationLineNumber());
				ordItemLineForDisplay.setBundleItemLineNumber(orderItemLineFromDAO.getBundleItemLineNumber());
				//set shipping details
				ordItemLineForDisplay.setShipToFirstName(orderItemLineFromDAO.getShippingAddress().getShipToFirstName());
				ordItemLineForDisplay.setShipToLastName(orderItemLineFromDAO.getShippingAddress().getShipToLastName());
				ordItemLineForDisplay.setShipToCountry(orderItemLineFromDAO.getShippingAddress().getShipToCountry());
				ordItemLineForDisplay.setShipToPostcode(orderItemLineFromDAO.getShippingAddress().getShipToPostCode());
				ordItemLineForDisplay.setShipToCity(orderItemLineFromDAO.getShippingAddress().getShipToCity());
				ordItemLineForDisplay.setShipingAddress1(orderItemLineFromDAO.getShippingAddress().getShipToAddressLine1());
				ordItemLineForDisplay.setShipingAddress2(orderItemLineFromDAO.getShippingAddress().getShipToAddressLine2());
				ordItemLineForDisplay.setShipingAddress3(orderItemLineFromDAO.getShippingAddress().getShipToAddressLine3());
				ordItemLineForDisplay.setShipingAddress4(orderItemLineFromDAO.getShippingAddress().getShipToAddressLine4());
				ordItemLineForDisplay.setShipingAddress5(orderItemLineFromDAO.getShippingAddress().getShipToAddressLine5());
				ordItemLineForDisplay.setShipingAddress6(orderItemLineFromDAO.getShippingAddress().getShipToAddressLine6());
				ordItemLineForDisplay.setShipToStoreName(orderItemLineFromDAO.getShippingAddress().getShipToStoreName());
				ordItemLineForDisplay.setShipToStoreNumber(orderItemLineFromDAO.getShippingAddress().getShipToStoreNumber());
				ordItemLineForDisplay.setShipToHomeTelDay(orderItemLineFromDAO.getShippingAddress().getShipToHomeTelDay());
				ordItemLineForDisplay.setShipToHomeTelNight(orderItemLineFromDAO.getShippingAddress().getShipToHomeTelNight());
				ordItemLineForDisplay.setCarrierName(orderItemLineFromDAO.getDeliveryDetails().getCarrierName());
				ordItemLineForDisplay.setCarrierServiceCode(orderItemLineFromDAO.getDeliveryDetails().getCarrierServiceCode());
				ordItemLineForDisplay.setIsPODRequired(orderItemLineFromDAO.getDeliveryDetails().getIsPodRequired());
				ordItemLineForDisplay.setMinimumAgeRequired(orderItemLineFromDAO.getDeliveryDetails().getMinimumAgeRequired());
				ordItemLineForDisplay.setDeliveryInstructions(orderItemLineFromDAO.getDeliveryDetails().getDeliveryInstructions());
				/** R6 - Get Delivery instruction from order line items **/
				if(ordItemLineForDisplay.getDeliveryInstructions() != null && !isDeliveryInstructionFound) {
					ordForDisp.setDeliveryInstructions(ordItemLineForDisplay.getDeliveryInstructions());
					isDeliveryInstructionFound = true;
				}
				orderLineItemsForDisplay.put(orderItemLineFromDAO.getLineNumber().intValue(), ordItemLineForDisplay);
				if(noOfCancelleOrderdLineQty>0 && noOfReleasedOrderLineQty==0 && noOfDispatchedOrderLineQty==0 && noOfAcknowledgedLineQty==0 && noOfReturnedOrderLineQty==0){
					ordItemLineForDisplay.setLineItemStatus(cancelledOrder);
				}else if(noOfCancelleOrderdLineQty>0){
					ordItemLineForDisplay.setLineItemStatus(OrderConstants.STRING_PARTIALLY_CANCELLED);
				}else if(noOfReturnedOrderLineQty>0 && noOfDispatchedOrderLineQty==0 && noOfReleasedOrderLineQty==0 && noOfAcknowledgedLineQty==0){
					ordItemLineForDisplay.setLineItemStatus(OrderConstants.SEARCH_ORDER_STATUS_RETURNED);
				}else if(noOfReturnedOrderLineQty>0){
					ordItemLineForDisplay.setLineItemStatus(OrderConstants.SEARCH_ORDER_STATUS_PARTIALLY_RETURNED);
				}else if(noOfDispatchedOrderLineQty>0 && noOfReleasedOrderLineQty==0 && noOfAcknowledgedLineQty==0){
					ordItemLineForDisplay.setLineItemStatus(OrderConstants.RETRIEVE_ORDER_STATUS_DISPATCHED);
				}else if(noOfDispatchedOrderLineQty>0){
					ordItemLineForDisplay.setLineItemStatus(OrderConstants.STRING_PARTIALLY_DISPATCHED);
				}else if(noOfAcknowledgedLineQty>0 && noOfReleasedOrderLineQty==0){
					ordItemLineForDisplay.setLineItemStatus(OrderConstants.RETRIEVE_ORDER_STATUS_ONLY_ACKNOWLEDGED);
				}else if(noOfAcknowledgedLineQty>0){
					ordItemLineForDisplay.setLineItemStatus(OrderConstants.STRING_PARTIALLY_ACKNOWLEDGED);
				}else if(noOfReleasedOrderLineQty>0){
					ordItemLineForDisplay.setLineItemStatus(OrderConstants.OPEN_VAR);
				}else{
					ordItemLineForDisplay.setLineItemStatus(noOrderStatus);
				}
				orderedQuantity = ordItemLineForDisplay.getOrderedQuantity();
				costPerItem = ordItemLineForDisplay.getCostPerItem();
				itemValue += (orderedQuantity * costPerItem);
				ordForDisp.setOrderLineItems(orderLineItemsForDisplay);
			}
			if(!isDeliveryInstructionFound && ordForDisp.getDeliveryInstructions() == null) {
				ordForDisp.setDeliveryInstructions(OrderConstants.NO_DELIVERY_INSTRUCTIONS);
			}
			ordForDisp.setOrderValue(itemValue);
			ordForDisp.setOrderStatus(OrderConstants.RETRIEVE_ORDER_STATUS_ONLY_RELEASED);
			ordersForDisplay.put(orderFromDAO.getSellerOrderNumber(), ordForDisp);
		}	
		return ordersForDisplay;	
}
public Map<String, Order> extractOrdersForDispatchDisplay(PortletRequest portletRequest, List<CustomerOrderType> ordersFromDAO) {
		Map<String,Order> ordForDisplay = new LinkedHashMap<String, Order>();
		for (CustomerOrderType ordFromDAO : ordersFromDAO) {
			double totalNoOfDispatchedItem = 0;
			double totalNoOfReturnedItem = 0;
			double totalNoOfReleasedItem = 0;
			double totalQty = 0;
			Order ordrForDisplay = new Order();
			ordrForDisplay.setOrderId(ordFromDAO.getSellerOrderNumber());
			ordrForDisplay.setCustomerOrderNumber(ordFromDAO.getCustomerOrderNumber());
			Date utilDate = DateUtil.toDate(ordFromDAO.getOrderDate());
			ordrForDisplay.setOrderPlacedDate(DateUtil.formatDate(utilDate));
			double orderedQuantity = 0;
			double costPerItem = 0;
			double itemValue = 0;
			List<LineItemType> ordLineItemFromDAO = ordFromDAO.getOrderLines().getLineItem();
			Map<Integer, OrderLineItem> ordLineItemsForDisplay = new LinkedHashMap<Integer, OrderLineItem>();
			for (LineItemType ordrLineItemFromDAO : ordLineItemFromDAO) {
				double noOfDispatchedItem = 0;
				double noOfReturnedItem = 0;
				double noOfReleasedItem = 0;
				OrderLineItem orderLineItemForDisplay = new OrderLineItem();
				ordrForDisplay.setNoOfLines(ordLineItemFromDAO.size());
				ordrForDisplay.setCustomerFirstName(ordrLineItemFromDAO.getCustomerDetails().getFirstName());
				ordrForDisplay.setCustomerLastName(ordrLineItemFromDAO.getCustomerDetails().getLastName());
				ordrForDisplay.setDeliveryOption(ordrLineItemFromDAO.getDeliveryDetails().getDeliveryOption());
				ordrForDisplay.setCustomerEmail(ordrLineItemFromDAO.getCustomerDetails().getEmail());
				ordrForDisplay.setCustomerMobile(ordrLineItemFromDAO.getCustomerDetails().getMobileNo());
				ordrForDisplay.setDeliveryCost(Double.parseDouble(ordrLineItemFromDAO.getDeliveryDetails().getDeliveryCharges()));
				ordrForDisplay.setCustomerDayTelephone(ordrLineItemFromDAO.getCustomerDetails().getHomeTelDay());
				ordrForDisplay.setCustomerEveningTelephone(ordrLineItemFromDAO.getCustomerDetails().getHomeTelNight());
				ordrForDisplay.setShipToAddress(ordrLineItemFromDAO.getShippingAddress().getShipToAddressLine1() 
						+ StringPool.SPACE + ordrLineItemFromDAO.getShippingAddress().getShipToAddressLine2() 
						+ StringPool.SPACE + ordrLineItemFromDAO.getShippingAddress().getShipToAddressLine3() 
						+ StringPool.SPACE + ordrLineItemFromDAO.getShippingAddress().getShipToAddressLine4() 
						+ StringPool.SPACE + ordrLineItemFromDAO.getShippingAddress().getShipToAddressLine5() 
						+ StringPool.SPACE + ordrLineItemFromDAO.getShippingAddress().getShipToAddressLine6());
				ordrForDisplay.setShipToMobile(ordrLineItemFromDAO.getShippingAddress().getShipToMobileNo());
				//Getting the latest status (OrderStatusType) from OrderLineStatusHistory
				List<OrderStatusType> statusHistory = ordrLineItemFromDAO.getOrderLineStatusHistory().getOrderStatus();
				for(OrderStatusType orderStatus : statusHistory){
					if((OrderConstants.STRING_ORDER_STATUS_3700_01).equals(orderStatus.getStatus())){
						noOfReturnedItem += orderStatus.getQuantity().doubleValue();
						totalNoOfReturnedItem += orderStatus.getQuantity().doubleValue();
						log.debug("Comming to returned if>>>>>>..");
					}
					if((OrderConstants.STRING_ORDER_STATUS_3700).equals(orderStatus.getStatus())){
						noOfDispatchedItem += orderStatus.getQuantity().doubleValue();
						totalNoOfDispatchedItem += orderStatus.getQuantity().doubleValue();
						log.debug("Comming to dispatched if>>>>>>..");
					}
					if((OrderConstants.STRING_ORDER_STATUS_3200_210).equals(orderStatus.getStatus())){
						noOfReleasedItem += orderStatus.getQuantity().doubleValue();
						totalNoOfReleasedItem += orderStatus.getQuantity().doubleValue();
						log.debug("Comming to released if>>>>>>..");
					}
				}
				if(noOfDispatchedItem > 0){
					orderLineItemForDisplay.setOrderedQuantity(ordrLineItemFromDAO.getOrderedQuantity().doubleValue());
					orderLineItemForDisplay.setDispatchedQuantity(noOfDispatchedItem);
					orderLineItemForDisplay.setOrderLineNumber(ordrLineItemFromDAO.getLineNumber().intValue());
					orderLineItemForDisplay.setReturnedQuantity(noOfReturnedItem);
					orderLineItemForDisplay.setDescription(ordrLineItemFromDAO.getItemShortDescription());
					orderLineItemForDisplay.setSku(ordrLineItemFromDAO.getMerchantSkuId());
					orderLineItemForDisplay.setCostPerItem(new Double(ordrLineItemFromDAO.getRetailPrice()));
					orderLineItemForDisplay.setUnitOfSale(ordrLineItemFromDAO.getUnitOfSale());
					orderLineItemForDisplay.setShipToFirstName(ordrLineItemFromDAO.getShippingAddress().getShipToFirstName());
					orderLineItemForDisplay.setShipToLastName(ordrLineItemFromDAO.getShippingAddress().getShipToLastName());
					orderLineItemForDisplay.setShipToCountry(ordrLineItemFromDAO.getShippingAddress().getShipToCountry());
					orderLineItemForDisplay.setShipToPostcode(ordrLineItemFromDAO.getShippingAddress().getShipToPostCode());
					orderLineItemForDisplay.setShipToCity(ordrLineItemFromDAO.getShippingAddress().getShipToCity());
					orderLineItemForDisplay.setShipingAddress1(ordrLineItemFromDAO.getShippingAddress().getShipToAddressLine1());
					orderLineItemForDisplay.setShipingAddress2(ordrLineItemFromDAO.getShippingAddress().getShipToAddressLine2());
					orderedQuantity = orderLineItemForDisplay.getOrderedQuantity();
					costPerItem = orderLineItemForDisplay.getCostPerItem();
					itemValue += (orderedQuantity * costPerItem);
					ordLineItemsForDisplay.put(ordrLineItemFromDAO.getLineNumber().intValue(), orderLineItemForDisplay);
					if(orderedQuantity == noOfDispatchedItem){
						orderLineItemForDisplay.setLineItemStatus("Dispatched");
					}
					if(noOfReleasedItem > 0){
						orderLineItemForDisplay.setLineItemStatus(OrderConstants.STRING_PARTIALLY_DISPATCHED);
					}
					if(noOfReturnedItem > 0){
						orderLineItemForDisplay.setLineItemStatus("Partially Returned");
					}
				}	
				ordrForDisplay.setOrderLineItems(ordLineItemsForDisplay);
				
				ordrForDisplay.setOrderValue(itemValue);
			}  
			totalQty = totalNoOfDispatchedItem + totalNoOfReturnedItem + totalNoOfReleasedItem;
			if(totalQty == totalNoOfDispatchedItem){
				ordrForDisplay.setOrderStatus("Dispatched");
			}
			if(totalNoOfReleasedItem > 0){
				ordrForDisplay.setOrderStatus("Partially Dispatched");
			}
			if(totalNoOfReturnedItem > 0){
				ordrForDisplay.setOrderStatus("Partially Returned");
			}
			ordForDisplay.put(ordFromDAO.getSellerOrderNumber(), ordrForDisplay);
		}	
		
		return ordForDisplay;	
	}
	@Override
	public String getProcessingReport(String transactionId) throws Exception {
		ProcessingReport processingReport = null;
		try {
			processingReport = orderDAO.getTransactionProcessingReport(transactionId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E010+OrderConstants.LOG_MESSAGE_SEPERATOR+PropsUtil.get(OrderConstants.ORDMG_E010)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		}
		log.info("-------log---"+processingReport.getTransactionStatus());
		if(processingReport.getTransactionCode().equals(OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONCODE_200) 
				&& processingReport.getTransactionStatus().equals(OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_SUCCEEDED))
		{
			return OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_SUCCEEDED_KEY;
			
		} else if(processingReport.getTransactionCode().equals(OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONCODE_200) 
				&& processingReport.getTransactionStatus().equals(OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_PARTIALLYSUCCEEDED)) {
			//TODO update pendingOrders map in basket/session for each line item
			return OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_PARTIALLYSUCCEEDED_KEY;
			
		} else if(processingReport.getTransactionCode().equals(OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONCODE_400) 
				&& processingReport.getTransactionStatus().equals(OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_FAILED)) {
			//TODO revert status in pendingOrders map in basket/session, so user can try again.
			return OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_FAILED_KEY;
			
		} else if(processingReport.getTransactionCode().equals(OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONCODE_202) 
				&& processingReport.getTransactionStatus().equalsIgnoreCase(OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INPROGRESS)) {
			
			return OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INPROGRESS_KEY;
			
		} else if(processingReport.getTransactionCode().equals(OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONCODE_500)) {
		
			return OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INTERNAL_SERVER_ERROR_KEY;			
		}
		return StringPool.BLANK;
	}
	@Override
	public File exportOrderDetails(List<Order> order) {
		File file = FileUtil.createTempFile();
		log.debug("inside exportOrderDetails() of service");
		file = ExcelUtil.writeFile(order);
		return file;
	}

   @Override	
   public String cancelOrders(long sellerId, List<Order> cancelOrders) throws Exception{
	   log.debug(">>>>>>>>>>>>>>> inside cancelOrders : ");
		String acknowledgeOrdersTransactionId = "";
		AcknowledgeOrders acknowledgeOrders = new AcknowledgeOrders();
		List<OrderLine> orderLines = new ArrayList<OrderLine>();
		if(OrderHelperUtil.isValidList(cancelOrders)){
			for (Order order : cancelOrders) {
				Map<Integer, OrderLineItem> orderLineItemDetailsMapFromBaskt = order.getOrderLineItems();
				if(OrderHelperUtil.isValidMap(orderLineItemDetailsMapFromBaskt)){
					for (Integer orderLineItemId : orderLineItemDetailsMapFromBaskt.keySet()) {	
						OrderLineItem orderLineItemFromBasket = orderLineItemDetailsMapFromBaskt.get(orderLineItemId);
						log.info("cancellation reason is = " + orderLineItemFromBasket.getPrimaryReasonForCancellation() + PortletProps.get(OrderConstants.REASON_CODE+orderLineItemFromBasket.getPrimaryReasonForCancellation()));
						OrderLine orderLine = new OrderLine();
						orderLine.setCustomerOrderNumber(order.getCustomerOrderNumber());
						orderLine.setSellerOrderNumber(order.getOrderId());
						orderLine.setSellerId(new Long(sellerId).toString());
						orderLine.setOrderLineNumber(String.valueOf(orderLineItemFromBasket.getOrderLineNumber()));
						//TODO quantity is null, to be tested when basket is updated, check jsp is passing value correctly?
						//orderLine.setQuantity(new BigDecimal(orderLineItemFromBasket.getQuantity()));
						orderLine.setQuantity(new BigDecimal(orderLineItemFromBasket.getCancelledQuantity()));
						// 100 status is stands for cancel the order 126 for reason code
						orderLine.setLineStatus("100");
						orderLine.setLineStatusReasonCode(orderLineItemFromBasket.getPrimaryReasonForCancellation());
						orderLines.add(orderLine);		
					}
				} else {
					log.debug("Order Details of orderId: "+order+" is not available, hence this order is not submitted to OrderAPI");
				}	
			}
			acknowledgeOrders.setOrderLine(orderLines);			
			acknowledgeOrdersTransactionId = orderDAO.cancelOrders(acknowledgeOrders, sellerId);
			log.info("OrderServiceImpl - cancel orders - Transaction ID - "+acknowledgeOrdersTransactionId);
		} else {
			log.debug("Pending Orders from OrderBasket is not available, hence OrderAPI not called");
		}
		return acknowledgeOrdersTransactionId;		
	}
   @Override	
   public String dispatchOrders(long sellerId, List<Order> dispatchOrder) throws Exception{
	   long startSec = System.currentTimeMillis();
	   log.debug(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+startString+classOrderServName+dispatchOrdMeth+timeLog+startSec);
		String dispatchOrdersTransactionId = "";
		List<Order> cAndCOrdLst = new ArrayList<Order>();
		for (Order order : dispatchOrder) {
			if(("click & collect").equalsIgnoreCase(order.getDeliveryOption())){
				cAndCOrdLst.add(order);
			}
		}
		//Remove Click & Collect order from dispatch order list
		for(Order order : cAndCOrdLst){
			dispatchOrder.remove(order);
		}
		Map<String, List<Order>> cAndCOrdrMap = new HashMap<String, List<Order>>();
		List<Order> existOrdLst = null;
		if(cAndCOrdLst.size() > 0){
			for (Order order : cAndCOrdLst) {
				if(cAndCOrdrMap.get(order.getOrderId()) == null){
					List<Order> ordLst = new ArrayList<Order>();
					ordLst.add(order);
					cAndCOrdrMap.put(order.getOrderId(), ordLst);
				}else{
					existOrdLst = cAndCOrdrMap.get(order.getOrderId());
					existOrdLst.add(order);
					cAndCOrdrMap.put(order.getOrderId(), existOrdLst);
				}
			}
		}
		dispatchOrder = mergeOrders(dispatchOrder); // This method is called to merge different same orders with different line items
		ShipmentUpdates shipmentUpdates = new ShipmentUpdates();
		List<ShipmentUpdate> shipmentUpdatesList = new ArrayList<ShipmentUpdate>();
		for (Order ordreForShip : dispatchOrder) {
			List<Shipment> shipmentlist = new ArrayList<Shipment>();
			List<ShipmentLine> shipmentLineList = new ArrayList<ShipmentLine>();
			ShipmentUpdate shipmentUpdate = new ShipmentUpdate();
			Shipments shipments = new Shipments();
			Shipment shipment = new Shipment();	
			ShipmentLines shipmentLines = new ShipmentLines();
			shipmentUpdate.setSellerOrderNumber(ordreForShip.getOrderId());
			shipmentUpdate.setCustomerOrderNumber(ordreForShip.getCustomerOrderNumber());
			shipmentUpdate.setSellerId(String.valueOf(sellerId));
			shipments.setIsConsolidatedParcel("");
			shipments.setOuterCaseTrackingNumber("N");
			CarrierDetails carrierDetails = new CarrierDetails();
			AddressDetails addressDetails = new AddressDetails();																						
			shipment.setDispatchDate(DateUtil.asXMLGregorianCalendar(new Date()));
			shipment.setParcelTrackingNumber(ordreForShip.getTrackingId());
			shipment.setTotalWeight(new BigDecimal(1.0));
			shipment.setTotalWeightUOM("2e214234");																															
			carrierDetails.setCartonNumber("");
			carrierDetails.setIsTFSUsed("N");
			String cName="";
			if(ordreForShip.getCarrierName().equals(cName)){
				log.debug("No Carrier name");
			}else {
				carrierDetails.setThirdPartyCarrierName(ordreForShip.getCarrierName());
			}
		    shipment.setCarrierDetails(carrierDetails);
		    addressDetails.setFirstName(ordreForShip.getCustomerFirstName());
			addressDetails.setLastName(ordreForShip.getCustomerLastName());
			addressDetails.setPostCode(OrderConstants.SHIPPINGPOSTCODE);
			addressDetails.setHomePhone(OrderConstants.SHIPPINGHOMEPHONE);
			shipment.setAddressDetails(addressDetails);	
			Map<Integer, OrderLineItem> orderLineItemDetailsMapFromBasket = ordreForShip.getOrderLineItems();
			if(OrderHelperUtil.isValidMap(orderLineItemDetailsMapFromBasket)){																		
				for (Integer orderLineItemId : orderLineItemDetailsMapFromBasket.keySet()) {	
					OrderLineItem orderLineItemFromBasket = orderLineItemDetailsMapFromBasket.get(orderLineItemId);
					ShipmentLine shipmentLine = new ShipmentLine();	
					shipmentLine.setOrderLineNumber(""+orderLineItemFromBasket.getOrderLineNumber());
					shipmentLine.setQuantity(new BigDecimal(orderLineItemFromBasket.getDispatchedQuantity()));
					shipmentLine.setShipmentLineNo("" + orderLineItemFromBasket.getOrderLineNumber());
					shipmentLine.setSubLineNo(""+orderLineItemFromBasket.getOrderLineNumber());
					shipmentLine.setUnitOfMeasure(orderLineItemFromBasket.getUnitOfSale());																	
					shipmentLineList.add(shipmentLine);
				}
				shipmentLines.setShipmentLine(shipmentLineList);
				shipment.setShipmentLines(shipmentLines);
				shipmentlist.add(shipment);
				log.info("Shipment List Size ::"+shipmentlist.size());
				shipments.setShipment(shipmentlist);
				shipmentUpdate.setShipments(shipments);		
			}
			shipmentUpdatesList.add(shipmentUpdate);
		}
		//For Click & Collect orders
		shipmentUpdatesList.addAll(OrderHelperUtil.getShipUpdateForCAndC(cAndCOrdrMap, sellerId));
		shipmentUpdates.setShipmentUpdate(shipmentUpdatesList);
		dispatchOrdersTransactionId = orderDAO.dispatchOrders(shipmentUpdates, sellerId);
		OrderDetailUtil odu = new OrderDetailUtil();
		odu.updateFinalSubmit(cAndCOrdLst);
		log.info("OrderServiceImpl - dispatch orders - Transaction ID - "+dispatchOrdersTransactionId);
		long endSec = System.currentTimeMillis();
		log.debug(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+endString+classOrderServName+dispatchOrdMeth+timeLog+endSec);
		log.debug(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+totalElapsedStr+"CLASS : OrderDAOImpl, "+dispatchOrdMeth+timeInSec+(endSec-startSec)/1000);
		return dispatchOrdersTransactionId;
 }
  @Override	
  public String returnOrders(long sellerId, List<Order> returnOrder) throws Exception{
		String returnOrdersTransactionId = "";
		List<ReturnRequestLines> rLines = null;
		List<Return> returnOrders = new ArrayList<Return>();
		ReturnRefunds returnRefunds = new ReturnRefunds();
		for (Order returnOrdersFromBasket : returnOrder) {
			rLines = new ArrayList<ReturnRequestLines>();
			Return return1 = new Return();
			return1.setOrderId(returnOrdersFromBasket.getCustomerOrderNumber());
			return1.setReturnOption("NO_RETURN_REQUIRED");
			return1.setSellerId(String.valueOf(sellerId));
			Map<Integer,OrderLineItem> lineItemsOfReturnOrder = returnOrdersFromBasket.getOrderLineItems();
			for(Integer lineItem : lineItemsOfReturnOrder.keySet()){
	  			OrderLineItem orderLineItem = lineItemsOfReturnOrder.get(lineItem);	
	  			double quantity = orderLineItem.getReturnedQuantity();
	  			double consolidatedRefundAmount = orderLineItem.getRefundAmount();
	  			double refundAmount = consolidatedRefundAmount / quantity;
	  			BigDecimal tempRefAmt = null;
	  			for(int i=0; i < quantity; i++){
	  				ReturnRequestLines requestLines = new ReturnRequestLines();
					requestLines.setOrderPrimeLineNo(orderLineItem.getOrderLineNumber());
					requestLines.setPrimaryReasonCode(orderLineItem.getReasonForReturn());
					requestLines.setSecondaryReasonCode(orderLineItem.getSecondaryReasonForReturn());
					requestLines.setComments("No comments provided");
					tempRefAmt = new BigDecimal(String.valueOf(refundAmount));
					tempRefAmt = tempRefAmt.setScale(2, RoundingMode.HALF_EVEN);
					requestLines.setRefundAmount(tempRefAmt);
					rLines.add(requestLines);
	  			}
			}
			return1.setReturnRequestLines(rLines);
			returnOrders.add(return1);
		}	
		returnRefunds.set_return(returnOrders);
		returnOrdersTransactionId = orderDAO.returnOrders(returnRefunds, sellerId);
		log.info("OrderServiceImpl - return orders - Transaction ID - "+returnOrdersTransactionId);
		return returnOrdersTransactionId;
   }
  public String getTransactionStatusOfOrder(Transaction transaction,PortletRequest request){
		try {
			String transactionId = transaction.getTransactionId();
			String processingResult = getProcessingReport(transactionId);
			if(processingResult.equals(OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INPROGRESS_KEY)){
				return OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INPROGRESS;
			}else{
				OrderBasket orderBasket = orderBasketService.getOrderBasket(request);
				HashMap<String, Transaction> transDetailsDisptachAndCancel = orderBasket.getTransactionDetailsOfDispatchAndCancel();
				HashMap<String, Transaction> transDetailsReturn = orderBasket.getTransactionDetailsOfReturn();
				String orderId = transaction.getOrderId(); 
				if(Validator.isNotNull(transDetailsDisptachAndCancel)){
					if(transDetailsDisptachAndCancel.containsKey(orderId)){
						transDetailsDisptachAndCancel.remove(transaction.getOrderId());
						orderBasket.setTransactionDetailsOfDispatchAndCancel(transDetailsDisptachAndCancel);
						orderBasketService.setOrderBasket(orderBasket, request);
					}
				}
				if(Validator.isNotNull(transDetailsReturn)){
					if(transDetailsReturn.containsKey(orderId)){
						transDetailsReturn.remove(transaction.getOrderId());
						orderBasket.setTransactionDetailsOfReturn(transDetailsReturn);
						orderBasketService.setOrderBasket(orderBasket, request);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
  /**
   * This method is used to merge two same orders with different Line items.
   * @param dispOrdLst
   * @return
   */
  public List<Order> mergeOrders(List<Order> dispOrdLst){
	  List<Order> mergOrdrLst = new ArrayList<Order>();
	  Map<Integer, OrderLineItem> oldOrdLnItems = null;
	  Map<Integer, OrderLineItem> newOrdLnItems = null;
	  //Order tempOrder = null;
	  boolean isFound = false;
	  for (Order i : dispOrdLst) {
		 // tempOrder = i;
		  isFound = false;
		for(Order j : mergOrdrLst){
			if(i.getOrderId().equalsIgnoreCase(j.getOrderId())){
				oldOrdLnItems = i.getOrderLineItems();
				newOrdLnItems = j.getOrderLineItems();
				oldOrdLnItems.putAll(newOrdLnItems);
				j.setOrderLineItems(oldOrdLnItems);
				isFound = true;
				break;
			}
		}
		if(!isFound){
			mergOrdrLst.add(i);
		}
	}
	  return mergOrdrLst;
  }
  /**
   * Create Yodl Label.
   */
  @Override
  public Object createYodlLabel(long sellerId,String orderId, TradingPartner tp, Date pickUpDate, CarrierAttributes ca, Order order) throws Exception{
	  long startSec = System.currentTimeMillis();
	  log.info(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+startString+classOrderServName+crateYodlMethName+timeLog+startSec);
	  Object yodlResp = orderDAO.getYodlLabel(orderId, sellerId, tp, pickUpDate, ca, order);
	  long endSec = System.currentTimeMillis();
	  log.info(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+endString+classOrderServName+crateYodlMethName+timeLog+endSec);
	  log.info(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+totalElapsedStr+classOrderServName+crateYodlMethName+timeInSec+(endSec-startSec)/1000);
	  return yodlResp;
  }
  /**
   * Get TradingPartner for sellerId.
   * @throws Exception 
   */
  @Override
  public TradingPartner getSellerById(long tradingPartnerId) throws Exception{
	  TradingPartner tp = orderDAO.getSellerById(tradingPartnerId);
	  return tp;
  }
  @Override
  public CarrierAttributes getCarrierAttributes(long tradingPartnerId) throws Exception{
	  CarrierAttributes ca = orderDAO.getCarrierAttributesById(tradingPartnerId);
	  return ca;
  }
}
