package com.tesco.mhub.order.portlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.tesco.mhub.NoSuchSystemMHubMappingException;
import com.tesco.mhub.model.OrderLabelDetails;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.model.ItemInfo;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderBasket;
import com.tesco.mhub.order.model.OrderLabelMin;
import com.tesco.mhub.order.model.OrderLineItem;
import com.tesco.mhub.order.model.OrderPackageDetails;
import com.tesco.mhub.order.model.ParcelDetails;
import com.tesco.mhub.order.model.ParcelItemInfo;
import com.tesco.mhub.order.model.ParcelJson;
import com.tesco.mhub.order.model.ResponseOrderSummary;
import com.tesco.mhub.order.model.ResponseSearchOrders;
import com.tesco.mhub.order.model.ResponseSearchOrders.ResponseOrder;
import com.tesco.mhub.order.model.Transaction;
import com.tesco.mhub.order.service.OrderBasketService;
import com.tesco.mhub.order.service.OrderService;
import com.tesco.mhub.order.serviceimpl.OrderBasketServiceImpl;
import com.tesco.mhub.order.util.ConvertToCSV;
import com.tesco.mhub.order.util.OrderHelperUtil;
import com.tesco.mhub.order.util.OrderManagementUtil;
import com.tesco.mhub.order.util.PortletProps;
import com.tesco.mhub.service.OrderLabelDetailsLocalServiceUtil;
import com.tesco.mhub.service.SystemMHubMappingLocalServiceUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller(value = "orderMgmtController")
@RequestMapping("VIEW")
public class OrderMgmtController {
	/**
	 *local constant for totalOrdersCount that we get from orderService URL.
	 */
	private static final String TOTAL_ORDERS_COUNT = "totalOrdersCount";
	/**
	 *local constant for pageSize that we get from orderService URL.
	 */
	private static final String PAGE_SIZE = "pageSize";
	/**
	 *local constant for pageNo that we get from orderService URL.
	 */
	private static final String PAGE_NO = "pageNo";
	/**
	 * Character set "UTF-8".
	 */
	private String charactSetUtf8 = "UTF-8";

	/**
	 * service layer object.
	 */
	@Autowired(required = true)
	private OrderService orderService;
	
	/**
	 * order basket service object.
	 */
	@Autowired(required = true)
	private OrderBasketService orderBasketService;
	
	/**
	 * logger object.
	 */
	private Log log = LogFactoryUtil.getLog(getClass());
	
	/**
	 * dateFormat for formatting the date.
	 */
	private DateFormat dateFormats = new SimpleDateFormat("dd/MM/yyyy");
	/**
	 * dateFormat for formatting the dates.
	 */
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	/**
	 * Selected orders Hashmap.
	 */
	private String selectedOrdersMapStr = "selectedOrdersMap";
	/**
	 * constant that defines status of order as 'pending'.
	 */
	private String pending = "Pending";
	/**
	 * String Under score.
	 */
	private String undrScre = "_";
	/**
	 * Quick Dispatch orders.
	 */
	private String quickDispatchOrders = "quickDispatchOrders";
	/**
	 * Content Type.
	 */
	private String appJsonContType = "application/json";
	/**
	 *Seller Id log string.
	 */
	private String sellerIdLog = "SELLER ID : ";
	/**
	 *START log string.
	 */
	private String startString = " START ,";
	/**
	 *Class Name log string.
	 */
	private String className = "CLASS : OrderMgmtController, ";
	/**
	 *orders For Commit.
	 */
	private String ordrsComitMethNam = " METHOD : ordersForCommit";
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
	 *Get Order Package Details.
	 */
	private String methGetOrdName = " METHOD : getOrderPackageDetails";
	/**
	 *Post Quick Disp Orders.
	 */
	private String methPostQuickDispOrd = " METHOD : postQuickDispOrders";
	/**
	 * Click And Collect String.
	 */
	private String cAndCStr = "Click & Collect";

	@RenderMapping
	public String defaultRender(RenderRequest renderRequest,RenderResponse renderResponse, PortletSession portletSession) throws JSONException {
		renderRequest.getPortletSession().removeAttribute(quickDispatchOrders);
		String isBulkCnCAvail = OrderConstants.RENDER_BULK_CNC_FLAG;
		
		log.debug("inside defaultRender of Controller");
		
		String selectedTabStatus = null;
		String prevPage = ParamUtil.getString(renderRequest, "prevPage");
		
		if (prevPage.equals(OrderConstants.ORDER_DETAILS_PAGE)) {
			selectedTabStatus = Validator.isNotNull(renderRequest.getPortletSession().getAttribute(OrderConstants.STRING_SELECTED_TAB)) ? (String) renderRequest.getPortletSession().getAttribute(OrderConstants.STRING_SELECTED_TAB) : OrderConstants.RETRIEVE_ORDER_STATUS_RELEASED;
		} else {
			selectedTabStatus = OrderConstants.RETRIEVE_ORDER_STATUS_RELEASED;
		}
		renderRequest.setAttribute(OrderConstants.STRING_SELECTED_TAB_JSP, selectedTabStatus);
		if(isBulkCnCAvail != null && isBulkCnCAvail.length() >0){
		String res=isBulkCnCAvail.toUpperCase();
		renderRequest.setAttribute("isBulkCnCAvail", res);
		}
		return OrderConstants.STRING_ORDER_LIST;
	}

	@RenderMapping(params = "action=renderDisplayOpenOrders")
	public String displayReleasedOrders(Model model,RenderRequest renderRequest) {
		
		/*log.debug("inside displayReleasedOrders of Controller");*/
		
		Calendar cal = Calendar.getInstance();
		Date todays = cal.getTime();
		
		dateFormat.format(todays);
		cal.add(cal.DATE, -7);
		Calendar cals1 = Calendar.getInstance();
		cals1.add(cals1.DATE, 1);
		todays = cals1.getTime();
		
		Date startDate = cal.getTime();
		dateFormat.format(startDate);
		long sellerId = OrderManagementUtil.getSellerId(renderRequest);
		Map<String,Order> openOrders;
		
		try {
			
			openOrders = orderService.getReleasedOrders(startDate, todays,sellerId, renderRequest);
		}  catch (Exception e) {
			log.error(OrderConstants.ORDMG_E012+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E012)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
			log.debug("Return from displayReleasedOrders datewise of Controllers");
			/*e.printStackTrace();*/
			SessionErrors.add(renderRequest, OrderConstants.ERROR_GETTING_RELEASED_ORDER);
			return OrderConstants.VIEW_UPDATE_ORDERS;
			
		}
		renderRequest.setAttribute(OrderConstants.ORDER_DETAILS, openOrders);
		
		List<String> defaultReleasedDateList = new ArrayList<String>();
		defaultReleasedDateList = OrderManagementUtil.returnDates(openOrders.values().iterator(),true);
		renderRequest.setAttribute(OrderConstants.DATES_LIST, defaultReleasedDateList);
		/*log.debug("Return from displayReleasedOrders of Controller");*/
		OrderManagementUtil.setPermissions(renderRequest);
		//return OrderConstants.VIEW_UPDATE_ORDERS;
		return OrderConstants.STRING_ORDER_LIST;
	}

	/*private void checkSize(RenderRequest renderRequest,
			Map<String, Order> openOrders) {
		if(openOrders.size() == 0){
			SessionMessages.add(renderRequest, OrderConstants.ERROR_MESSAGE_ON_DATE_RANGE);
		}
	}*/

	@RenderMapping(params = "action=renderDisplayDispatchOrders")
	public String displayDispatchedOrders(Model model,
			RenderRequest renderRequest) {
		log.debug("inside displayDispatchedOrders of Controller");
		Calendar calcu = Calendar.getInstance();
		Date today = calcu.getTime();
		Calendar cals1 = Calendar.getInstance();
		cals1.setTime(today);
		cals1.add(cals1.DATE, 1);
		today = cals1.getTime();
		calcu.add(calcu.DATE, -7);
		Date startDate = calcu.getTime();
		dateFormat.format(startDate);
		
		long sellerId = OrderManagementUtil.getSellerId(renderRequest);
		Map<String,Order> dispatchedOrders = new LinkedHashMap<String, Order>();
		try {
			
			dispatchedOrders = orderService.getDispatchedOrders(startDate,today, sellerId, renderRequest);
			log.debug("Controller - dispatched order map size = " + dispatchedOrders.size());
		} catch (Exception e) {
			log.error(OrderConstants.ORDMG_E015+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E015)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
			/*log.debug("Exception in dispatched orders , visit controller");*/
			/*e.printStackTrace();*/
			SessionErrors.add(renderRequest, OrderConstants.ERROR_GETTING_DISPATCHED_ORDER);
			return OrderConstants.VIEW_UPDATE_DISPATCHED_ORDERS;
		}
		renderRequest.setAttribute(OrderConstants.ORDER_DETAILS,dispatchedOrders);
		List<String> defaultDispatchedDateList = new ArrayList<String>();
		defaultDispatchedDateList = OrderManagementUtil.returnDates(dispatchedOrders.values().iterator(),true);		
		log.debug("Controller - dispatched order - date list size = " + defaultDispatchedDateList.size());
		renderRequest.setAttribute(OrderConstants.DATES_LIST, defaultDispatchedDateList);
		OrderManagementUtil.setPermissions(renderRequest);
		return OrderConstants.VIEW_UPDATE_DISPATCHED_ORDERS;
		
	}
		
	
	@ActionMapping(params = "action=commitOrders")
	public void ordersForCommit(ActionRequest actionRequest, ActionResponse response){
		long startSec = System.currentTimeMillis();
		log.debug(sellerIdLog+OrderManagementUtil.getSellerId(actionRequest)+StringPool.SPACE+StringPool.COMMA+startString+className+ordrsComitMethNam+timeLog+startSec);
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		OrderManagementUtil.setPermissions(actionRequest);
		boolean hasError = false;
		try {
			OrderBasket orderBasket = orderBasketService.getOrderBasket(actionRequest);
			List<Order> ordersForUpdate = orderBasket.getOrdersForUpdate();
			List<Order> dispatchOrder = new ArrayList<Order>();
			List<Order> cancelOrder = new ArrayList<Order>();
			List<Order> returnOrder = new ArrayList<Order>();
			
			log.info("ordersForUpdate size :: " + ordersForUpdate.size());
			
			for(Order order : ordersForUpdate){
				if((OrderConstants.UPDATE_ORDER_ACTION_DISPATCH).equals(order.getServiceActionType())){
					dispatchOrder.add(order);
				}
				if((OrderConstants.UPDATE_ORDER_ACTION_CANCEL).equals(order.getServiceActionType())){
					cancelOrder.add(order);
				}
				if((OrderConstants.UPDATE_ORDER_ACTION_RETURN).equals(order.getServiceActionType())){
					returnOrder.add(order);
				}
			}
			String cancelOrdersTransactionId = OrderConstants.NO_TRANSACTION_ID;
			if(cancelOrder.size()>0){
				cancelOrdersTransactionId = orderService.cancelOrders(OrderManagementUtil.getSellerId(actionRequest), cancelOrder);
				HashMap<String,Transaction> transactionMapOfCancelOrd = new HashMap<String, Transaction>();
				if(!cancelOrdersTransactionId.equals(OrderConstants.NO_TRANSACTION_ID)){
					
					String cancelProcessingResult = orderService.getProcessingReport(cancelOrdersTransactionId);
					log.debug("Message from service For Canceled Order :: = "+cancelProcessingResult);
					for(Order odCancel : cancelOrder){
						Transaction txnCancel = new Transaction();
						txnCancel.setOrderId(odCancel.getOrderId());
						txnCancel.setTransactionId(cancelOrdersTransactionId);
						txnCancel.setTransactionStatus(cancelProcessingResult);
						txnCancel.setTransactionType(OrderConstants.UPDATE_ORDER_ACTION_CANCEL);
						txnCancel.setOldOrder(odCancel);
						transactionMapOfCancelOrd.put(odCancel.getOrderId(), txnCancel);
					}
					orderBasket.getTransactionDetailsOfDispatchAndCancel().putAll(transactionMapOfCancelOrd);
				}
				String activity = cancelOrder.toString();
				OrderManagementUtil.createAuditMessage(actionRequest, activity, "Order Line Cancelled");
			}
			String dispatchOrdersTransactionId = OrderConstants.NO_TRANSACTION_ID;
			if(dispatchOrder.size() > 0){
				dispatchOrdersTransactionId = orderService.dispatchOrders(OrderManagementUtil.getSellerId(actionRequest), dispatchOrder);
				HashMap<String,Transaction> transactionMapOfDispatchOrd = new HashMap<String, Transaction>();
				if(!dispatchOrdersTransactionId.equals(OrderConstants.NO_TRANSACTION_ID)){
					String dispatchedProcessingResult = orderService.getProcessingReport(dispatchOrdersTransactionId);
					log.debug("Message from service for Dispatched Order ::= "+dispatchedProcessingResult);
					for(Order odDispatch : dispatchOrder){
						log.debug("Older Order Status ::"+odDispatch.getOrderStatus());
						Transaction txnDispatch = new Transaction();
						txnDispatch.setOrderId(odDispatch.getOrderId());
						txnDispatch.setTransactionId(dispatchOrdersTransactionId);
						txnDispatch.setTransactionStatus(dispatchedProcessingResult);
						txnDispatch.setTransactionType(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH);
						txnDispatch.setOldOrder(odDispatch);
						transactionMapOfDispatchOrd.put(odDispatch.getOrderId(),txnDispatch);
					}
					orderBasket.getTransactionDetailsOfDispatchAndCancel().putAll(transactionMapOfDispatchOrd);
				}
				String activity = dispatchOrder.toString();
				OrderManagementUtil.createAuditMessage(actionRequest, activity, "Order Line Dispatched");
 			}
			String returnOrdersTransactionId = OrderConstants.NO_TRANSACTION_ID;
			if(returnOrder.size() > 0){
				returnOrdersTransactionId = orderService.returnOrders(OrderManagementUtil.getSellerId(actionRequest), returnOrder);
				HashMap<String,Transaction> transactionMapOfReturnOrd = new HashMap<String, Transaction>();
				if(!returnOrdersTransactionId.equals(OrderConstants.NO_TRANSACTION_ID)){
					String returnedProcessingResult = orderService.getProcessingReport(returnOrdersTransactionId);
					log.debug("Message from service for returned Order ::= "+returnedProcessingResult);
					for(Order odReturn : returnOrder){
						Transaction txnReturn = new Transaction();
						txnReturn.setOrderId(odReturn.getOrderId());
						txnReturn.setTransactionId(returnOrdersTransactionId);
						txnReturn.setTransactionStatus(returnedProcessingResult);
						txnReturn.setTransactionType(OrderConstants.UPDATE_ORDER_ACTION_RETURN);
						txnReturn.setOldOrder(odReturn);
						transactionMapOfReturnOrd.put(odReturn.getOrderId(),txnReturn);
					}
					orderBasket.getTransactionDetailsOfReturn().putAll(transactionMapOfReturnOrd);
				}
				String activity = returnOrder.toString();
				OrderManagementUtil.createAuditMessage(actionRequest, activity, "Order Line Returned");
			}
			
			log.info("cancelOrdersTransactionId ::" + cancelOrdersTransactionId);
			log.info("dispatchOrdersTransactionId ::" + dispatchOrdersTransactionId);
			log.info("returnOrdersTransactionId ::" + returnOrdersTransactionId);
			if(Validator.isNotNull(cancelOrdersTransactionId) || Validator.isNotNull(dispatchOrdersTransactionId) || Validator.isNotNull(returnOrdersTransactionId)){
				
				//Persist the cancelOrdersTransactionId in TransactionService and Portlet Session
				//TODO common-services not accessible in portlet, throwing CNFE
				
				/*Transaction transaction = TransactionLocalServiceUtil.createTransaction(0);	
				
				transaction.setTransactionCode(cancelOrdersTransactionId);
				transaction.setTransactionCode(dispatchOrdersTransactionId);
				transaction.setTransactionCode(returnOrdersTransactionId);
				
				transaction.setType("AcknowledgeOrders");
				
				TransactionLocalServiceUtil.addTransaction(transaction);*/
				
				//mark order statuses as 'Acknowledged' in basket as well as released and dispatched orders.
				/*List<Order> ordersMapFromBasket = orderBasket.getOrdersForUpdate();
				Map<String, Order> releasedOrdersMap = orderBasket.getReleasedOrders();
				Map<String, Order> dispatchedOrdersMap = orderBasket.getDispatchedOrders();
				orderBasketService.updateStatusInOrderBasket(ordersMapFromBasket, releasedOrdersMap, OrderConstants.STATUS_PORTAL_ACKNOWLEDGED);
				orderBasketService.updateStatusInOrderBasket(ordersMapFromBasket, dispatchedOrdersMap, OrderConstants.STATUS_PORTAL_ACKNOWLEDGED);*/
			
				orderBasket.getOrdersForUpdate().clear();
				//Set the updated OrderBasket in Portlet Session
				orderBasketService.setOrderBasket(orderBasket, actionRequest);
				String activity = ordersForUpdate.toString();
				OrderManagementUtil.createAuditMessage(actionRequest, activity, "Updates Submitted");
			} else {
				log.debug("Orders Update service wasn't successful and hence no updateOrdersTransactionId added to session.");
			}	
			
		} catch (Exception e) {
			log.error(OrderConstants.ORDMG_E016+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E016)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
			/*log.debug("updateOrders action wasn't successful and hence no updateOrdersTransactionId added to session.");*/
			e.printStackTrace();
			SessionErrors.add(actionRequest, OrderConstants.ERROR_MESSAGE);
			hasError = true;
		}
		if(!hasError){
			SessionMessages.add(actionRequest, OrderConstants.POST_FINAL_SUBMIT_MESSAGE);
		}
		/** Remove Selected Order details from Session stored for Quick Dispatch Page **/
		if(actionRequest.getPortletSession().getAttribute(selectedOrdersMapStr) != null){
			actionRequest.getPortletSession().removeAttribute(selectedOrdersMapStr);
		}
		try {
			response.sendRedirect(PortalUtil.getLayoutURL(themeDisplay));
		} catch (PortalException e) {
			log.error(OrderConstants.ORDMG_E017+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E017)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (SystemException e) {
			log.error(OrderConstants.ORDMG_E018+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E018)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (IOException e) {
			log.error(OrderConstants.ORDMG_E019+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E019)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		}
		long endSec = System.currentTimeMillis();
		log.debug(sellerIdLog+OrderManagementUtil.getSellerId(actionRequest)+StringPool.SPACE+StringPool.COMMA+endString+className+ordrsComitMethNam+timeLog+endSec);
		log.debug(sellerIdLog+OrderManagementUtil.getSellerId(actionRequest)+StringPool.SPACE+StringPool.COMMA+totalElapsedStr+"CLASS : OrderDAOImpl, "+ordrsComitMethNam+timeInSec+(endSec-startSec)/1000);
	}
	
	
	
	@ResourceMapping("orderDownload")
	public void downloadOrders(ResourceRequest request, ResourceResponse response) throws FileNotFoundException,
			IOException {
		try {
			long sellerIdForPrint = OrderManagementUtil.getSellerId(request);
			String statusFromSession = request.getPortletSession().getAttribute(OrderConstants.STRING_SELECTED_TAB) !=null ? (String) request.getPortletSession().getAttribute(OrderConstants.STRING_SELECTED_TAB) : OrderConstants.RETRIEVE_ORDER_STATUS_RELEASED;
			Map<String, Order> displayOrder = new HashMap<String, Order>();
			String pageNameForPrint = ParamUtil.getString(request, "pageDisplay");
			log.info("pagename = " + pageNameForPrint);
			
			String[] qryParams =  pageNameForPrint.split(StringPool.BACK_SLASH + StringPool.QUESTION);//"\\?");
			String[] splitQryParams = qryParams[1].split(StringPool.BACK_SLASH + StringPool.STAR);
			String selectedTab = (splitQryParams[0].split(StringPool.COLON))[1];
			String selOrderOpt = (splitQryParams[1].split(StringPool.COLON))[1];
			
			Map<String, Order> displayOrderToPrint = new HashMap<String, Order>();
			Map<String, String> displayOrderMap = new HashMap<String,String>();
			Calendar cals = Calendar.getInstance();
			Date today = cals.getTime();
			cals.setTime(today);
			cals.add(cals.DATE, -60);
			Date startDate = cals.getTime();
			cals.setTime(today);
			Date endDate = cals.getTime();
			String deliveryOption = "";
			String[] orderIdsForPrint = null;
			dateFormat.format(startDate);
			dateFormat.format(endDate);
			try {
				if(OrderConstants.RETRIEVE_ORDER_STATUS_RELEASED.equalsIgnoreCase(selectedTab) || OrderConstants.RETRIEVE_ORDER_STATUS_ONLY_RELEASED.equalsIgnoreCase(selectedTab)){ //For Open tab
					
					if("Selecteditems".equalsIgnoreCase(selOrderOpt)){
						orderIdsForPrint =  ((splitQryParams[2].split(StringPool.COLON))[1]).split(StringPool.COMMA);
						log.info("orderIds = " + Arrays.toString(orderIdsForPrint));
						displayOrder = orderService.getSelectedOrders(statusFromSession, orderIdsForPrint, sellerIdForPrint, request);
						Boolean ispresent = false;
						Map<String, Order> tempDispOrder = new HashMap<String, Order>();
						for (String oId : displayOrder.keySet()) {
							ispresent = false;
							for (String selOrderId : orderIdsForPrint) {
								if(oId.equalsIgnoreCase(selOrderId)){
									ispresent = true;
									break;
								}
							}
							if(ispresent){
								tempDispOrder.put(oId, displayOrder.get(oId));
							}
						}
						displayOrder = tempDispOrder;
						log.info("printing " + displayOrder.keySet().size() + " orders.");
					}else if ("AllOpenorders".equalsIgnoreCase(selOrderOpt)) {
						displayOrder = orderService.getOrdersForPrintDownload(OrderConstants.RETRIEVE_ORDER_STATUS_ONLY_RELEASED, startDate, endDate, deliveryOption, String.valueOf(sellerIdForPrint), request);
					}else if("AllCcOrders".equalsIgnoreCase(selOrderOpt)){
						deliveryOption = "Store_Collect";
						displayOrder = orderService.getOrdersForPrintDownload(OrderConstants.RETRIEVE_ORDER_STATUS_ONLY_RELEASED, startDate, endDate, deliveryOption, String.valueOf(sellerIdForPrint), request);
						if(displayOrder.size() > 0){
							displayOrder = getClickAndCollect(displayOrder);
						}
					}else if("AllFilter".equalsIgnoreCase(selOrderOpt)){
						String selDelOptLst = "";
						if((splitQryParams[2].split(StringPool.COLON)).length > 1){//Checking whether any delivery option is selected in filters
							selDelOptLst = (splitQryParams[2].split(StringPool.COLON))[1];
						}
					/*	String frmDte = (splitQryParams[3].split(StringPool.COLON))[1];
						String endDte = (splitQryParams[4].split(StringPool.COLON))[1];*/
						String frmDte = "";
						String endDte = "";
						if(splitQryParams[3].split(StringPool.COLON).length >= 2){
							frmDte = (splitQryParams[3].split(StringPool.COLON))[1];
						}
						if(splitQryParams[4].split(StringPool.COLON).length >= 2){
							endDte = (splitQryParams[4].split(StringPool.COLON))[1];
						}
						String dateFormatFill = "dd-MM-yyyy";
						if(Validator.isNotNull(frmDte) && Validator.isNotNull(endDte) && !"".equals(frmDte.trim()) && !"".equals(endDte.trim())){
							Date fromDateFilter = new SimpleDateFormat(dateFormatFill).parse(frmDte);
							Date toDateFilter = new SimpleDateFormat(dateFormatFill).parse(endDte);
							
							startDate  = fromDateFilter;
							endDate = toDateFilter;
							
						}else if(Validator.isNotNull(frmDte) && !"".equals(frmDte.trim()) ){
							Date tempFromDate = new SimpleDateFormat(dateFormatFill).parse(frmDte);
							Date tempToDateFilter = new SimpleDateFormat(dateFormatFill).parse(frmDte);
							
							startDate = tempFromDate;
							endDate = tempToDateFilter;
						}else if(Validator.isNotNull(endDte) && !"".equals(endDte.trim()) ){
							Date fromDate = new SimpleDateFormat(dateFormatFill).parse(endDte);
							Date toDate = new SimpleDateFormat(dateFormatFill).parse(endDte);
							
							startDate = fromDate;
							endDate = toDate;
						}
						if(selDelOptLst.length() > 0){//Checking whether any delivery option is selected or not
							deliveryOption = selDelOptLst.trim().replace(StringPool.COMMA+" ",StringPool.PIPE).trim().replace(StringPool.CLOSE_BRACKET, "").trim().replace(StringPool.OPEN_BRACKET, "").trim();
						}
						displayOrder = orderService.getOrdersForPrintDownload(OrderConstants.RETRIEVE_ORDER_STATUS_ONLY_RELEASED, startDate, endDate, deliveryOption, String.valueOf(sellerIdForPrint), request);
						
					}
				
				}else { //For Dispatched, Returned and Cancelled tabs
					orderIdsForPrint =  ((splitQryParams[1].split(StringPool.COLON))[1]).split(StringPool.COMMA);
					displayOrder = orderService.getSelectedOrders(statusFromSession, orderIdsForPrint, sellerIdForPrint, request);
					log.info("printing " + displayOrder.keySet().size() + " orders.");
				}
			}catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			List<Order> printLists = new ArrayList<Order>();
			List<String> orderIds = new ArrayList<String>();
			if(displayOrder != null && displayOrder.size() > 0){
				//orderIds = (List<String>) displayOrder.keySet();
				for(String oid : displayOrder.keySet()){
					if(oid.contains(StringPool.DASH)){
						Order odr = displayOrder.get(oid);
						//SimpleDateFormat format1 = new SimpleDateFormat();
						//format1.applyPattern("d MMM yyyy");
						//log.info("datePlaced 1 : " + format1.format(odr.getOrderPlacedDate()));
						String datePlaced = dateFormat.format(odr.getOrderPlacedDate());
						//log.info("datePlaced : " + datePlaced);
						displayOrderMap.put(oid,datePlaced);
						displayOrderToPrint.put(oid, odr);
						printLists.add(odr);
					}
				}
			}
			
			InputStream inputStream;
			if("AllCcOrders".equalsIgnoreCase(selOrderOpt)){
				HttpServletResponse httpresponse = PortalUtil.getHttpServletResponse(response);
				httpresponse.setHeader( "Content-Disposition", "filename="+"my-orders.csv");
				File csvOrderFile = ConvertToCSV.toCSV(printLists);
				
				inputStream = new FileInputStream(csvOrderFile);				
				httpresponse.setContentType("application/vnd.ms-excel");
				//response.setCharacterEncoding("utf-8");
				IOUtils.copy(inputStream, httpresponse.getOutputStream());
				httpresponse.flushBuffer();
			}else{
				File fileToDwnld = orderService.exportOrderDetails(printLists);
				inputStream = new FileInputStream(fileToDwnld);
				response.setContentType("application/vnd.ms-excel");
				IOUtils.copy(inputStream, response.getPortletOutputStream());
				response.flushBuffer();
			}
			inputStream.close();
			
			String activity = printLists.toString();
			if( displayOrder.keySet().size() == 1){
				log.debug("Comming to open ");
				OrderManagementUtil.createAuditMessage(request, activity, "Download Order Details");
			}else{
				log.debug("Comming to disp ");
				OrderManagementUtil.createAuditMessage(request, activity, "Download List");
			}
		} catch (FileNotFoundException e1) {
			log.error(e1.getMessage());
		} catch (IOException e2) {
			log.error(e2.getMessage());
		}
	}
	@ResourceMapping("getOrderList")
	public void getOrderList(ResourceRequest request,ResourceResponse response) throws SystemException, PortalException {
		long sellerId = OrderManagementUtil.getSellerId(request);
		HttpServletResponse httpResponse = PortalUtil.getHttpServletResponse(response);
		try {
			PrintWriter writer = httpResponse.getWriter();
			try{
				String selectedTab = ParamUtil.getString(request, OrderConstants.STRING_SELECTED_TAB_JSP);
				if(Validator.isNotNull(selectedTab) && !"".equals(selectedTab.trim())){
					request.getPortletSession().setAttribute(OrderConstants.STRING_SELECTED_TAB, selectedTab);
				}
				
				/** R6 - get order list when search by order id without release no. **/
				String	orderId =  ParamUtil.getString(request, OrderConstants.ORDERID);
				
				JSONObject jsonObject = getJSONOrderListForDisplayForOrderIdSearch(request, response, sellerId, orderId);

				if(!"".equalsIgnoreCase(orderId)) {
					String activitySearch = "Search by order ID";
					OrderManagementUtil.createAuditMessage(request, activitySearch, "OrderId Search");
				}
				
				writer.print(jsonObject.toString());
			}catch(Exception e){
				writer.print("ERROR ");
				e.printStackTrace();
			}
			writer.flush();
			writer.close();
			
		} catch (IOException e4) {
			log.error(e4.getMessage());
		}
	}
	@ResourceMapping("getList")
	public void getList(ResourceRequest request,ResourceResponse response) throws SystemException, PortalException {
		long sellerId = OrderManagementUtil.getSellerId(request);
		HttpServletResponse httpResponse = PortalUtil.getHttpServletResponse(response);
		try {
			PrintWriter writer = httpResponse.getWriter();
			try{
				String selectedTab = ParamUtil.getString(request, OrderConstants.STRING_SELECTED_TAB_JSP);
				if(Validator.isNotNull(selectedTab) && !"".equals(selectedTab.trim())){
					request.getPortletSession().setAttribute(OrderConstants.STRING_SELECTED_TAB, selectedTab);
				}
				String customerName = ParamUtil.getString(request, "customerName");
				String pageNumber = ParamUtil.getString(request, "pageNumber");
				String deliveryOption = "";
				int pageNumberInt = Integer.parseInt(pageNumber);
				Calendar cals = Calendar.getInstance();
				Date today = cals.getTime();
				cals.setTime(today);
				cals.add(cals.DATE, -60);
				Date startDate = cals.getTime();
				cals.setTime(today);
				Date endDate = cals.getTime();
				log.info("Seller Id::"+sellerId);
				
				String filterDateBy = "ReleasedDate";
				if(OrderConstants.RETRIEVE_ORDER_STATUS_RELEASED.equalsIgnoreCase(selectedTab)){
					filterDateBy = "ReleasedDate";
				}else if("dispatched".equalsIgnoreCase(selectedTab)){
					filterDateBy = "DispatchedDate";
				}else if("cancelled".equalsIgnoreCase(selectedTab)){
					filterDateBy = "CancelledDate";
				}else if("returned".equalsIgnoreCase(selectedTab)){
					filterDateBy = "ReturnedDate";
				}
				String filterResults = ParamUtil.getString(request, "filterResults");
				if(Validator.isNotNull(filterResults) && "true".equalsIgnoreCase(filterResults)){
					
					String filterDeliveryOption = ParamUtil.getString(request, "filterDeliveryOption");
					String filterStatus = ParamUtil.getString(request, "filterStatus");
					String filterFromDate = ParamUtil.getString(request, "filterFromDate");
					String filterToDate = ParamUtil.getString(request, "filterToDate");
					if(Validator.isNotNull(filterDeliveryOption) && !"".equalsIgnoreCase(filterDeliveryOption.trim())){
						deliveryOption = filterDeliveryOption.trim().replace(StringPool.COMMA,StringPool.PIPE).trim().replace(StringPool.CLOSE_BRACKET, "").trim().replace(StringPool.OPEN_BRACKET, "").trim();
					}
					if(Validator.isNotNull(filterStatus) && !"".equalsIgnoreCase(filterStatus)){
						selectedTab = filterStatus.trim().replace(StringPool.COMMA, StringPool.PIPE);
					}
					String dateFormat = "dd-MM-yyyy";
					if(Validator.isNotNull(filterFromDate) && Validator.isNotNull(filterToDate) && !"".equals(filterFromDate.trim()) && !"".equals(filterToDate.trim())){
						Date fromDateFilter = new SimpleDateFormat(dateFormat).parse(filterFromDate);
						Date toDateFilter = new SimpleDateFormat(dateFormat).parse(filterToDate);
						startDate = fromDateFilter;
						endDate = toDateFilter;
						
					}else if(Validator.isNotNull(filterFromDate) && !"".equals(filterFromDate.trim()) ){
						Date tempFromDate = new SimpleDateFormat(dateFormat).parse(filterFromDate);
						Date tempToDateFilter = new SimpleDateFormat(dateFormat).parse(filterFromDate);
						
						startDate = tempFromDate;
						endDate = tempToDateFilter;
					}else if(Validator.isNotNull(filterToDate) && !"".equals(filterToDate.trim()) ){
						Date fromDate = new SimpleDateFormat(dateFormat).parse(filterToDate);
						Date toDate = new SimpleDateFormat(dateFormat).parse(filterToDate);
						
						startDate = fromDate;
						endDate = toDate;
					}
					String activityFilter = "Use filter functionality";
					OrderManagementUtil.createAuditMessage(request, activityFilter, "Order Filter");
				}
				String sortByDate = ParamUtil.getString(request, "sortByDate");
				String sortByDateIsApplied = ParamUtil.getString(request, "sortByDateIsApplied");
				if("true".equalsIgnoreCase(sortByDateIsApplied)){
					String activitySort = "Use sort functionality";
					OrderManagementUtil.createAuditMessage(request, activitySort, "Order Sort");
				}
				sortByDate = !"".equalsIgnoreCase(sortByDate.trim()) ? sortByDate : "ReleasedDate-desc";
				//-------------------creating json object-------------------------
				
				/** R6 - get order list when search by order id without release no. **/
				
				JSONObject jsonObject = getJSONOrderListForDisplayForCustNameSearch(request, selectedTab, startDate, endDate, sellerId,deliveryOption,filterDateBy,sortByDate,customerName,pageNumberInt);
				
				if(!"".equalsIgnoreCase(customerName)){
					String activitySearchCust = "Search by customer name";
					OrderManagementUtil.createAuditMessage(request, activitySearchCust, "Order Search-Customer Name");
				}
				writer.print(jsonObject.toString());
			}catch(Exception e){
				writer.print("ERROR ");
				e.printStackTrace();
			}
			writer.flush();
			writer.close();
		} catch (IOException e4) {
			log.error(e4.getMessage());
		}
	}
	
	@ResourceMapping("checkOrderExist")
	public void checkOrderExist(ResourceRequest request,ResourceResponse response) throws SystemException, PortalException {
		try {
			long sellerId = OrderManagementUtil.getSellerId(request);
			HttpServletResponse httpResponse = PortalUtil.getHttpServletResponse(response);
			PrintWriter printWriter = httpResponse.getWriter();
			try {
				String orderId =  ParamUtil.getString(request, OrderConstants.ORDERID);
				Order order = orderService.getOrderDetails(orderId, sellerId, request);
				if(Validator.isNotNull(order) && Validator.isNotNull(order.getOrderLineItems()) && order.getOrderLineItems().size()>0){
					request.getPortletSession().setAttribute(OrderConstants.STRING_SELECTED_TAB,StringPool.BLANK);
					printWriter.print("SUCCESS");
				}else{
					printWriter.print("ERROR");
				}
				//String activitySearch = "Order ID "+orderId;
				String activitySearch = "Search by order ID";
				OrderManagementUtil.createAuditMessage(request, activitySearch, "OrderId Search");
				printWriter.flush();
				printWriter.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				printWriter.print("ERROR");
				printWriter.flush();
				printWriter.close();
				e.printStackTrace();
			}
			
		} catch (IOException e5) {
			// TODO Auto-generated catch block
			e5.printStackTrace();
		}
	}
	
	@RenderMapping(params="action=quickDispatchCandCOrders")
	public String getOrderPackageDetails(RenderRequest renderRequest,RenderResponse renderResponse, @RequestParam("orderArr") String orderStrLst) {
		long startSec = System.currentTimeMillis();
		log.debug(sellerIdLog+OrderManagementUtil.getSellerId(renderRequest)+StringPool.SPACE+StringPool.COMMA+startString+className+methGetOrdName+timeLog+startSec);
		renderRequest.getPortletSession().removeAttribute(quickDispatchOrders);
		String[] orderIdArr =  renderRequest.getParameter("orderArr").toString().split(StringPool.COMMA);
		List<OrderPackageDetails> orderPackageDetailsList = new ArrayList<OrderPackageDetails>();
		boolean isClickAndColExist = false;
		OrderLabelDetails orderLabelDet = null;
		long sellerId = OrderManagementUtil.getSellerId(renderRequest);
		String[] selectedOrderNumbers = ParamUtil.getParameterValues(renderRequest, "selectedOrderNos");
		if(selectedOrderNumbers  != null){
			renderRequest.getPortletSession().removeAttribute(OrderConstants.SELECTED_ORDER_NUMBERS);
		}
		//OrderBasketService obs = new OrderBasketServiceImpl();
		OrderBasket orderBasket = orderBasketService.getOrderBasket(renderRequest);
		List<Order> pendingOrders = new ArrayList<Order>();
		/*if(pendingOrders == null){
			pendingOrders = new ArrayList<Order>();
		}*/
		Map<String,Order> releasedOrdersFromBasket = orderBasket.getAllOrderDetails();
		Map<String, Order> selectedOrdersMap = null;
		if((renderRequest.getPortletSession().getAttribute(selectedOrdersMapStr)) instanceof Map<?, ?>){
			selectedOrdersMap = (Map<String, Order>) renderRequest.getPortletSession().getAttribute(selectedOrdersMapStr);
		}
		OrderPackageDetails opd = null;
		List<ParcelDetails> parcelDetailsList = null;
		String orderDelOption = null;
		String[] orderBasArr = new String[orderIdArr.length]; 
		int i =0;
		for(String orderId : orderIdArr) {
			if((cAndCStr).equalsIgnoreCase(selectedOrdersMap.get(orderId).getDeliveryOption())){ //Skipping non-click and collect orders
				log.info("Order ids from UI : " + orderId);
				opd = new OrderPackageDetails();
				parcelDetailsList = OrderHelperUtil.getParcelDetailsListFromDB(renderRequest,sellerId, orderId);
				if(parcelDetailsList.size() <= 1){//Skipping orders which is having single parcel
					orderBasArr[i] = orderId;
					++i;
					continue;
				}
				isClickAndColExist = true;
				opd.setCustomerName(selectedOrdersMap.get(orderId).getCustomerFirstName()+" "+selectedOrdersMap.get(orderId).getCustomerLastName());
				opd.setOrderId(orderId);
				opd.setParcelSize(parcelDetailsList.size());
				opd.setParcelDetailsList(parcelDetailsList);
				orderPackageDetailsList.add(opd);
			}else{
				orderBasArr[i] = orderId;
				++i;
			}
		}
		//Adding click & collect orders having single parcel and 
		String orderId = new String();
		for(int j=0;j<orderBasArr.length;j++){
			if(orderBasArr[j] == null){
				continue;
			}
			orderId = orderBasArr[j];
			Order pendingOrder = new Order();
			Order currentOrd = releasedOrdersFromBasket.get(orderId.trim());
			Order oldOrder = new Order();
			getHardCopy(currentOrd, oldOrder);
			//orderBasket.getOriginalPendingItems().add(oldOrder);
			currentOrd.setOrderStatus(pending);
			Map<Integer, OrderLineItem> pendingOrderLineItems = new LinkedHashMap<Integer, OrderLineItem>();
			BeanUtils.copyProperties(currentOrd, pendingOrder);
			pendingOrder.setOrderLineItems(pendingOrderLineItems);
			pendingOrder.setTrackingId(ParamUtil.getString(renderRequest, "trackingId-"+orderId));
			pendingOrder.setTrackingURL(ParamUtil.getString(renderRequest, "trackingURL-"+orderId));
			pendingOrder.setCarrierName(ParamUtil.getString(renderRequest, "carrierName-"+orderId));
			pendingOrder.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH);


			Map<Integer, OrderLineItem> currentLineItems = currentOrd.getOrderLineItems();
			for (Integer lineNum : currentLineItems.keySet()) {

				OrderLineItem orderLineItem = currentLineItems.get(lineNum);
				if(orderLineItem.getOpenQuantity() <= 0){
					continue;
				}
				orderLineItem.setLineItemStatus(PortletProps.get(OrderConstants.STATUS_PORTAL_DISPATCHED));
				orderLineItem.setDispatchedQuantity(orderLineItem.getOpenQuantity());
				orderLineItem.setOpenQuantity(orderLineItem.getOpenQuantity()-(orderLineItem.getDispatchedQuantity()));
				orderLineItem.setLineItemStatus(pending);
				orderLineItem.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH);
				pendingOrderLineItems.put(lineNum, orderLineItem);
			}
			currentOrd.setOrderLineItems(currentLineItems);	        
			pendingOrders.add(pendingOrder);
			//}
	}

		//if there are no click&Collect orders with more than one parcel then add these orders to basket
		if(!isClickAndColExist){
			if(pendingOrders.size() > 0){
				for (Order itr : pendingOrders) {
					if((cAndCStr).equalsIgnoreCase(itr.getDeliveryOption())){
						try {
							orderLabelDet = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetails(itr.getTrackingId());
							orderLabelDet.setCreatedDate(new Date());
							orderLabelDet.setIsUsed(true);
							OrderLabelDetailsLocalServiceUtil.updateOrderLabelDetails(orderLabelDet);
						} catch (PortalException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SystemException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}
			}
			List<Order> existingPendOrders = new ArrayList<Order>();
			existingPendOrders = orderBasket.getOrdersForUpdate();
			existingPendOrders.addAll(pendingOrders);
			orderBasket.setOrdersForUpdate(existingPendOrders);
			OrderManagementUtil.setPermissions(renderRequest);
			orderBasketService.setOrderBasket(orderBasket, renderRequest);
			SessionMessages.add(renderRequest, OrderConstants.POST_FINAL_CONFIRM_MESSAGE);
			renderRequest.getPortletSession().removeAttribute(quickDispatchOrders);
			return OrderConstants.STRING_ORDER_LIST;
		}
		//Adding click&Collect with single parcel and non-click&Collect orders to session
		renderRequest.getPortletSession().setAttribute(quickDispatchOrders, pendingOrders);
		renderRequest.setAttribute("orderPackDetails", orderPackageDetailsList);
		renderRequest.setAttribute("orderStrLst", orderStrLst);

		long endSec = System.currentTimeMillis();
		log.debug(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+endString+className+methGetOrdName+timeLog+endSec);
		log.debug(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+totalElapsedStr+className+methGetOrdName+timeInSec+(endSec-startSec)/1000);

		return "multipleParcelsQuickDispatch";
	}
	
	@ResourceMapping("viewItemLabel")
	public void viewItemLabel(ResourceRequest resourceRequestLabl, ResourceResponse resourceResponseLabl,@RequestParam(OrderConstants.STRING_ORDER_ID) String orderId) {
		List<OrderLabelMin> viewlabelLst = new ArrayList<OrderLabelMin>();
		Gson gsonObj = new Gson();
		long sellerId = OrderManagementUtil.getSellerId(resourceRequestLabl);
		try {
			List<OrderLabelDetails> viewLblDtlLst = (List<OrderLabelDetails>) OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrdIdSelIdIsUsdFinSubmit(orderId, sellerId, true, true);
			OrderLabelMin olm = null;
			if(viewLblDtlLst.size() >0){
			for (OrderLabelDetails itr : viewLblDtlLst) {
				olm = new OrderLabelMin(itr.getOrderId(), itr.getTrackingId(), itr.getCollectionDate());
				viewlabelLst.add(olm);
			}
			}else{
				log.debug("No label details available"+viewLblDtlLst.size());
			}
			String parcelInfoResp = gsonObj.toJson(viewlabelLst);
			log.debug("View label response to client side "+parcelInfoResp);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponseLabl);
			response.setContentType(appJsonContType);
			InputStream inputStream;
				inputStream = new ByteArrayInputStream(parcelInfoResp.getBytes(charactSetUtf8));
				IOUtils.copy(inputStream, response.getOutputStream());
				response.flushBuffer();
				if(inputStream!=null){
					inputStream.close();
				}
		}  catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@ActionMapping(params = "action=cnfrmQckDispParcel")
	public void postQuickDispOrders(ActionRequest actReq, ActionResponse actResp) {
		long startSec = System.currentTimeMillis();
		log.debug(sellerIdLog+OrderManagementUtil.getSellerId(actReq)+StringPool.SPACE+StringPool.COMMA+startString+className+methPostQuickDispOrd+timeLog+startSec);
		long sellerId = OrderManagementUtil.getSellerId(actReq);
		String parcelJsonStr = (String) actReq.getParameter("parcelJson");
		Gson gsonObj = new Gson();
		java.lang.reflect.Type parcJsobObj = new TypeToken<List<ParcelJson>>(){}.getType();
		List<ParcelJson> parcJsobObjLst = gsonObj.fromJson(parcelJsonStr, parcJsobObj);
		List<ParcelItemInfo> parcelItmInfLst = OrderHelperUtil.getParcItemInfoLst(parcJsobObjLst);
		//From here copy logic as in PostCandCDispatch (Test before)
		OrderLabelDetails orderLabelDet = null;
		String orderId = null;
		String trackTempId = null;
		OrderBasket ordBasket = orderBasketService.getOrderBasket(actReq);
		//Add non-Click&collect orders and click&Collect orders with single parcel to basket.
		if(actReq.getPortletSession().getAttribute(quickDispatchOrders) != null){
			@SuppressWarnings("unchecked")
			List<Order> pendingSessionOrders = (List<Order>) (actReq.getPortletSession().getAttribute(quickDispatchOrders));
			if(pendingSessionOrders != null && pendingSessionOrders.size() > 0){
				for (Order itr : pendingSessionOrders) {
					if((cAndCStr).equalsIgnoreCase(itr.getDeliveryOption())){
						try {
							orderLabelDet = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetails(itr.getTrackingId());
							orderLabelDet.setCreatedDate(new Date());
							orderLabelDet.setIsUsed(true);
							OrderLabelDetailsLocalServiceUtil.updateOrderLabelDetails(orderLabelDet);
						} catch (PortalException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SystemException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					}
					
				}
			List<Order> pendingOrderLst = new ArrayList<Order>();
			pendingOrderLst = ordBasket.getOrdersForUpdate();	
			pendingOrderLst.addAll(pendingSessionOrders);
			ordBasket.setOrdersForUpdate(pendingOrderLst);
			orderBasketService.setOrderBasket(ordBasket, actReq);
			}
			actReq.getPortletSession().removeAttribute(quickDispatchOrders);
		}
		
		for (ParcelItemInfo parcelItemInfo : parcelItmInfLst) {
			trackTempId = parcelItemInfo.getTrackingId();
			for (ParcelJson i : parcJsobObjLst) {
				if(i.getTrackingId().equalsIgnoreCase(trackTempId)){
					orderId = i.getOrderId();
					break;
				}
			}
			ordBasket = orderBasketService.getOrderBasket(actReq);
			List<Order> pendingOrds = ordBasket.getOrdersForUpdate();
			Map<String,Order> releasedOrdersFromBasket = null;
			releasedOrdersFromBasket = ordBasket.getAllOrderDetails();
			Order currentOrder = releasedOrdersFromBasket.get(orderId);
			Order oldOrder = new Order();
			getHardCopy(currentOrder, oldOrder);
			ordBasket.getOriginalPendingItems().add(oldOrder);
			currentOrder.setOrderStatus(pending);
			// order for adding into the pending list
			Order pendingOrder = new Order();
			Map<Integer, OrderLineItem> pendingOrderLineItems = new LinkedHashMap<Integer, OrderLineItem>();
			BeanUtils.copyProperties(currentOrder, pendingOrder);
			pendingOrder.setOrderLineItems(pendingOrderLineItems);

			pendingOrder.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH);
			pendingOrder.setTrackingId(parcelItemInfo.getTrackingId());
			pendingOrder.setTrackingURL(ParamUtil.getString(actReq, "trackingURL"));
			pendingOrder.setCarrierName("YODEL");
			//Update the Corresponding OrderLabelDetails with creation date and IsUsed 
			try {
				orderLabelDet = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetails(parcelItemInfo.getTrackingId());
				orderLabelDet.setCreatedDate(new Date());
				orderLabelDet.setIsUsed(true);
				OrderLabelDetailsLocalServiceUtil.updateOrderLabelDetails(orderLabelDet);
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Map<Integer, OrderLineItem> currentOrderLineItems = currentOrder.getOrderLineItems();

			List<ItemInfo> itemInfoLst = parcelItemInfo.getItemInfoLst();

			for (Integer lineNumber : currentOrderLineItems.keySet()) {

				for(ItemInfo itmInfIt:itemInfoLst){
					if(lineNumber == itmInfIt.getLineNumber()){
						if(itmInfIt.getQuantity() <= 0){ //Skipping line items whose quantity is zero
							continue;
						}
						OrderLineItem currentOrderLineItem = new OrderLineItem(currentOrderLineItems.get(lineNumber));
						currentOrderLineItem.setDispatchedQuantity(itmInfIt.getQuantity());
						currentOrderLineItem.setOpenQuantity(currentOrderLineItem.getOpenQuantity()-(currentOrderLineItem.getDispatchedQuantity()));
						currentOrderLineItem.setLineItemStatus(pending);
						currentOrderLineItem.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH);
						pendingOrderLineItems.put(lineNumber, currentOrderLineItem);
					}
				}
			}
			pendingOrder.setOrderLineItems(pendingOrderLineItems);
			pendingOrds.add(pendingOrder);
			ordBasket.setOrdersForUpdate(pendingOrds);
			orderBasketService.setOrderBasket(ordBasket, actReq);
			if(actReq.getPortletSession().getAttribute(sellerId+undrScre+orderId) != null){
				actReq.getPortletSession().removeAttribute(sellerId+undrScre+orderId);
			}
		}
		OrderManagementUtil.setPermissions(actReq);
		
		SessionMessages.add(actReq, OrderConstants.POST_FINAL_CONFIRM_MESSAGE);
		long endSec = System.currentTimeMillis();
		log.debug(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+endString+className+methPostQuickDispOrd+timeLog+endSec);
		log.debug(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+totalElapsedStr+className+methPostQuickDispOrd+timeInSec+(endSec-startSec)/1000);
	}
	
	private void getHardCopy(Order currentOrder, Order oldOrder) {
		BeanUtils.copyProperties(currentOrder, oldOrder);
        Map<Integer, OrderLineItem> oldOrderLineItems = new LinkedHashMap<Integer, OrderLineItem>();
        for(Map.Entry<Integer, OrderLineItem> entry:currentOrder.getOrderLineItems().entrySet()){
        	OrderLineItem oldOrderLineItem = new OrderLineItem();
        	BeanUtils.copyProperties(entry.getValue(), oldOrderLineItem);
        	oldOrderLineItems.put(entry.getKey(), oldOrderLineItem); 
        }
        oldOrder.setOrderLineItems(oldOrderLineItems);
	}
	
	private JSONObject getJSONOrderListForDisplayForOrderIdSearch(PortletRequest request, ResourceResponse resourceResponse, long userId, String orderId){
		
		JSONObject jsonOrderResponse = JSONFactoryUtil.createJSONObject();
		ResponseSearchOrders mySerachOrderResponse = orderService.getOrderList(orderId, userId, request);
		
		if(mySerachOrderResponse != null) {
			int pageNum = 1;
			int pageSize = 20;
			
			mySerachOrderResponse.setPageNumber(pageNum);
			mySerachOrderResponse.setPageSize(pageSize);
		}
		createJsonOrderReponse(jsonOrderResponse, mySerachOrderResponse, request);
		jsonOrderResponse.put(OrderConstants.SEARCH_TYPE, "searchByOrderIdWithoutRelNum");
		
		return jsonOrderResponse;
	}
	private JSONObject getJSONOrderListForDisplayForCustNameSearch(PortletRequest request,String status,Date startDate,Date endDate,long userId,String deliveryOption,String filterDateBy,String sortByDate, String customerName,int pageNumber){
		
		JSONObject jsonOrderResponse = JSONFactoryUtil.createJSONObject();
		ResponseSearchOrders mySerachOrderResponse = orderService.getSearchOrders(status, startDate, endDate, filterDateBy, sortByDate, deliveryOption,customerName, userId, pageNumber, request);
		
		createJsonOrderReponse(jsonOrderResponse, mySerachOrderResponse, request);
		
		if(!"".equalsIgnoreCase(customerName)){
			jsonOrderResponse.put(OrderConstants.SEARCH_TYPE, "searchByCustName");
		}
		return jsonOrderResponse;
	}
	
	private void createJsonOrderReponse(JSONObject jsonOrderResponse, ResponseSearchOrders mySerachOrderResponse, PortletRequest request) {
		
		if(Validator.isNotNull(mySerachOrderResponse)){
			jsonOrderResponse.put(PAGE_NO, mySerachOrderResponse.getPageNumber());
			jsonOrderResponse.put(PAGE_SIZE,  mySerachOrderResponse.getPageSize());
			jsonOrderResponse.put(TOTAL_ORDERS_COUNT,  mySerachOrderResponse.getTotalOrders());
			
			List<ResponseOrder> responseOrderList = mySerachOrderResponse.getResponseOrderList();
			
			if(Validator.isNotNull(responseOrderList) && responseOrderList.size()>0){
				
				if(responseOrderList.size() == 1) {
					jsonOrderResponse.put(OrderConstants.ORDERID,responseOrderList.get(0).getOrderSummary().getReleaseOrderNumber());
					request.getPortletSession().setAttribute(OrderConstants.STRING_SELECTED_TAB,StringPool.BLANK);
				}
				
				JSONArray jsonOrderList = JSONFactoryUtil.createJSONArray();
				for (ResponseOrder responseOrder : responseOrderList) {
					ResponseOrderSummary orderSummery =  responseOrder.getOrderSummary();
					JSONObject jsonOrderSummary  = JSONFactoryUtil.createJSONObject();
					
					jsonOrderSummary.put(OrderConstants.ORDERID, orderSummery.getReleaseOrderNumber());
					jsonOrderSummary.put("totalLineItems", orderSummery.getTotalLineItems());
					jsonOrderSummary.put("totalNoOfOpenLines", orderSummery.getTotalOpenLineItems());
					jsonOrderSummary.put("customerName", orderSummery.getBillToName());
					String dOpt="deliveryOption";
					try {
						jsonOrderSummary.put(dOpt, SystemMHubMappingLocalServiceUtil.getMHubValue(OrderConstants.SYSTEM_NAME, OrderConstants.SYSTEM_ATTRIBUTE,orderSummery.getDeliveryOption()));
					} catch (NoSuchSystemMHubMappingException e) {
						log.error("NoSuchSystemMHubMappingException");
						jsonOrderSummary.put(dOpt,orderSummery.getDeliveryOption());
					} catch (SystemException e) {
						log.error("SystemException");
						jsonOrderSummary.put(dOpt,orderSummery.getDeliveryOption());
					}
					jsonOrderSummary.put("status", orderSummery.getStatus());
					jsonOrderSummary.put("expectedShipmentDate", Validator.isNotNull(orderSummery.getExpectedShipmentDate()) ?  dateFormats.format(orderSummery.getExpectedShipmentDate()) : "");
					jsonOrderSummary.put("dispatchedDate", Validator.isNotNull(orderSummery.getDispatchedDate())? dateFormats.format(orderSummery.getDispatchedDate()) : "");
					jsonOrderSummary.put("cancelledDate", Validator.isNotNull(orderSummery.getCancelledDate()) ? dateFormats.format(orderSummery.getCancelledDate()) : "");
					jsonOrderSummary.put("returnedDate", Validator.isNotNull(orderSummery.getReturnedDate()) ? dateFormats.format(orderSummery.getReturnedDate()) : "");
					jsonOrderSummary.put("releasedDate", Validator.isNotNull(orderSummery.getReleasedDate()) ? dateFormats.format(orderSummery.getReleasedDate()) :"");
					jsonOrderList.put(jsonOrderSummary);
				}
				jsonOrderResponse.put(OrderConstants.STRING_ORDER_LIST, jsonOrderList);
				String errorMsg = request.getPortletSession().getAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY) !=null ? (String) request.getPortletSession().getAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY) : StringPool.BLANK;
				jsonOrderResponse.put(OrderConstants.SESSION_ERROR_MESSAGE_KEY,errorMsg);
			}else{
				String errorMsg = request.getPortletSession().getAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY) !=null ? (String) request.getPortletSession().getAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY) : StringPool.BLANK;
				jsonOrderResponse.put(PAGE_NO,0);
				jsonOrderResponse.put(PAGE_SIZE,  0);
				jsonOrderResponse.put(TOTAL_ORDERS_COUNT,  0);
				jsonOrderResponse.put(OrderConstants.STRING_ORDER_LIST, "NULL");
				jsonOrderResponse.put(OrderConstants.SESSION_ERROR_MESSAGE_KEY,errorMsg);
			}
		}else{
			String errorMsg = request.getPortletSession().getAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY) !=null ? (String) request.getPortletSession().getAttribute(OrderConstants.SESSION_ERROR_MESSAGE_KEY) : StringPool.BLANK;
			jsonOrderResponse.put(PAGE_NO,0);
			jsonOrderResponse.put(PAGE_SIZE,  0);
			jsonOrderResponse.put(TOTAL_ORDERS_COUNT,  0);
			jsonOrderResponse.put(OrderConstants.STRING_ORDER_LIST, "NULL");
			jsonOrderResponse.put(OrderConstants.SESSION_ERROR_MESSAGE_KEY,errorMsg);
			
		}
		
	}

	private Map<String, Order> getClickAndCollect(Map<String, Order> displayOrder){
		Map<String, Order> ccOrdersLst = new HashMap<String, Order>();
		for (String oId : displayOrder.keySet()) {
			Order ord = displayOrder.get(oId);
			if((cAndCStr).equalsIgnoreCase(ord.getDeliveryOption())){
				ccOrdersLst.put(oId, ord);
			}
			
		}
		
		return ccOrdersLst;
	}
	
	@ResourceMapping("deletelItemLabel")
	public void actionDeleteItemYodlLabel(ResourceRequest resourceRequestLabl, ResourceResponse resourceResponseLabl,
			@RequestParam(OrderConstants.STRING_ORDER_ID) String orderId ,@RequestParam(OrderConstants.STRING_TRACKING_ID) String trackId) {
		String[] lineItemStr =  resourceRequestLabl.getParameter("lineItem").toString().split(StringPool.COMMA);
		int[] lineItems = new int[lineItemStr.length];
		for(int i = 0;i < lineItemStr.length;i++) {
			lineItems[i] = Integer.parseInt(lineItemStr[i]);
		}
		long sellerId = OrderManagementUtil.getSellerId(resourceRequestLabl);
		Order ord = (Order) resourceRequestLabl.getPortletSession().getAttribute(sellerId+undrScre+orderId);
		Map<Integer, OrderLineItem> orderLineItems = ord.getOrderLineItems();
		Gson gsonObj = new Gson();
		List<OrderLabelDetails> orderLblDtlFltrLst = new ArrayList<OrderLabelDetails>();
		try {
			List<OrderLabelDetails> orderLblDtlLst = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrderIdSellrIdIsUsed(orderId, sellerId, false);
			String filePath = null;
			File pdfFl = null;
			for (OrderLabelDetails itr : orderLblDtlLst) {
				if(itr.getTrackingId().equalsIgnoreCase(trackId)){
				filePath = itr.getPdfFilePath();
				pdfFl = new File(filePath);
				//Delete label PDF from folder
				boolean isDeleted = pdfFl.delete();
				if(isDeleted){ //If label is deleted from Folder, delete from DB
					OrderLabelDetailsLocalServiceUtil.deleteOrderLabelDetails(itr.getTrackingId());
								
				}else{
					throw new Exception("Unable to delet the PDF file at location ");
				}
				}else{
					orderLblDtlFltrLst.add(itr);
				}
			}
			ItemInfo itemInfo = null; 
			List<ParcelItemInfo> parItmInfLst = new ArrayList<ParcelItemInfo>();
			ParcelItemInfo pii = null;
			List<ItemInfo> itemInfoLst = null;
			for(OrderLabelDetails ordLblDet : orderLblDtlFltrLst){
				itemInfoLst = new ArrayList<ItemInfo>();
				for (Integer lineNumber : orderLineItems.keySet()) {
					for(int lineItemNumber:lineItems){
						if(lineNumber == lineItemNumber){
							OrderLineItem itrLineItem = orderLineItems.get(lineNumber);
							itemInfo = new ItemInfo(itrLineItem.getOrderLineNumber(), itrLineItem.getSku(), itrLineItem.getDescription(), itrLineItem.getOpenQuantity());
							itemInfoLst.add(itemInfo);
						}
					}
				}
				pii = new ParcelItemInfo(ordLblDet.getTrackingId(), itemInfoLst);
				parItmInfLst.add(pii);
			}
			String parcelInfoResp = gsonObj.toJson(parItmInfLst);
			log.debug("Item Parcel Info to client side "+parcelInfoResp);
			log.debug("Parcel Info Response :"+parcelInfoResp);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponseLabl);
			response.setContentType(appJsonContType);
			InputStream inputStream;
				inputStream = new ByteArrayInputStream(parcelInfoResp.getBytes(charactSetUtf8));
				IOUtils.copy(inputStream, response.getOutputStream());
				response.flushBuffer();
				if(inputStream!=null){
					inputStream.close();
				}
		}  catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
