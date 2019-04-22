package com.tesco.mhub.order.portlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.tesco.mhub.gmo.deliveryoption.request.TradingPartner;
import com.tesco.mhub.gmo.managetp.carrierDetails.CarrierAttributes;
import com.tesco.mhub.gmo.managetp.carrierDetails.CarrierDetails;
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
import com.tesco.mhub.order.model.QuickOrderLabelDetails;
import com.tesco.mhub.order.service.OrderBasketService;
import com.tesco.mhub.order.service.OrderService;
import com.tesco.mhub.order.serviceimpl.OrderBasketServiceImpl;
import com.tesco.mhub.order.util.OrderHelperUtil;
import com.tesco.mhub.order.util.OrderManagementUtil;
import com.tesco.mhub.order.util.PortletProps;
import com.tesco.mhub.service.OrderLabelDetailsLocalServiceUtil;
import com.tesco.mhub.yodl.response.Ndxml;
import com.tesco.mhub.yodl.yodlErr.NdxmlErrRes;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.StateAwareResponse;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller(value = "orderDetailsController")
@RequestMapping("VIEW")
public class OrderDetailsController {
	/**
	 * constant that defines status of order as 'pending'.
	 */
	private static final String PENDING = "Pending";
	/**
	 * order basket service object.
	 */
	@Autowired(required = true)
	private OrderBasketService orderBasketService;
	/**
	 * service layer object.
	 */
	@Autowired(required = true)
	private OrderService orderService;
	/**
	 * logger object.
	 */
	private Log log = LogFactoryUtil.getLog(getClass());
	/**
	 * Content Type.
	 */
	private String appJsonContType = "application/json";
	/**
	 * Character set "UTF-8".
	 */
	private String charactSetUtf8 = "UTF-8";
	/**
	 * String Under score.
	 */
	private String undrScre = "_";
	/**
	 * String backSlash.
	 */
	private String backSlash = "/";
	/**
	 * String Hyphen.
	 */
	private String hyphen = "-";
	/**
	 * String true.
	 */
	private String trueStr = "true";
	/**
	 * String false.
	 */
	private String falseStr = "false";
	/**
	 * Order Pack details.
	 */
	private String orderPackDetails="orderPackDetails";
	/**
	 * Selected orders Hashmap.
	 */
	private String selectedOrdersMapStr = "selectedOrdersMap";
	/**
	 * Selected Order Numbers.
	 */
	private String selectedOrderNos = "selectedOrderNos";
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
	private String className = "CLASS : OrderDetailsController, ";
	/**
	 *Rendr Method Name.
	 */
	private String rendrMethodName = " METHOD : renderQuickDispatchOpenOrders";
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
	 *Action Quick Print Open Orders.
	 */
	private String actQuickPrintStr = " METHOD : actionQuickPrintOpenOrders";
	/**
	 *Action Add Yodl Label.
	 */
	private String actionAddYodlLabelStr = " METHOD : actionAddYodlLabel";
	@RenderMapping(params = "action=renderOrderDetails")
	public String renderOrderDetails(PortletRequest portletRequestROD) {
		String sellerOrderNumber = portletRequestROD.getParameter(OrderConstants.STRING_ORDER_ID);
		String orderStatus =  portletRequestROD.getParameter("orderStatus");
		String displayOrderDetailsPage = null;
		Map<String,Order> sessionReleasedOrders = null;
		long sellerId = OrderManagementUtil.getSellerId(portletRequestROD);
		portletRequestROD.setAttribute("usedLabel", OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrdIdSelIdIsUsdFinSubmit(sellerOrderNumber, sellerId,true,true));
		try {
			String selectedTab = portletRequestROD.getPortletSession().getAttribute(OrderConstants.STRING_SELECTED_TAB) !=null ? (String) portletRequestROD.getPortletSession().getAttribute(OrderConstants.STRING_SELECTED_TAB) : StringPool.BLANK;
			Order orderDetails = orderService.getOrderDetails(sellerOrderNumber, sellerId, portletRequestROD);
			if(orderDetails != null){
				portletRequestROD.getPortletSession().setAttribute(sellerId+undrScre+orderDetails.getOrderId(), orderDetails);
			}
			if(Validator.isNotNull(orderDetails) && Validator.isNotNull(orderDetails.getOrderLineItems()) && orderDetails.getOrderLineItems().size()>0){
				portletRequestROD.setAttribute("orderDetails", orderDetails);
				portletRequestROD.setAttribute("orderDetailsMap", orderDetails.getOrderLineItems());
				portletRequestROD.setAttribute("selectedTab", selectedTab);
				portletRequestROD.setAttribute("orderStatus", orderStatus);
				OrderManagementUtil.setPermissions(portletRequestROD);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		displayOrderDetailsPage = "orderDetails";
		return displayOrderDetailsPage;
	}
	@RenderMapping(params = "action=renderQuickDispatchOpenOrders")
	public String renderQuickDispatchOpenOrders(@RequestParam("selectedOrderNumbers") String[] selectedOrderNumbers, RenderRequest renderRequestQD, RenderResponse renderResponseQD) {
		long startSec = System.currentTimeMillis();
		log.debug(sellerIdLog+OrderManagementUtil.getSellerId(renderRequestQD)+StringPool.SPACE+StringPool.COMMA+startString+className+rendrMethodName+timeLog+startSec);
		renderRequestQD.getPortletSession().removeAttribute("quickDispatchOrders");
		String isBulkCnCAvail = OrderConstants.RENDER_BULK_CNC_FLAG;
		String renderPage = null;
		long sellerIdForQuickDis = OrderManagementUtil.getSellerId(renderRequestQD);
		String statusForQuickDis = renderRequestQD.getPortletSession().getAttribute(OrderConstants.STRING_SELECTED_TAB) !=null ? (String) renderRequestQD.getPortletSession().getAttribute(OrderConstants.STRING_SELECTED_TAB) : "released";
		Map<String, Order> displayOrderQuickDis = new HashMap<String, Order>();
		try {
			displayOrderQuickDis = orderService.getSelectedOrders(statusForQuickDis, selectedOrderNumbers, sellerIdForQuickDis, renderRequestQD);
			/** Store Selected Order details in Session for Quick Dispatch Page and remove it after **/
			if(displayOrderQuickDis != null & displayOrderQuickDis.size() != 0){
				renderRequestQD.getPortletSession().setAttribute(selectedOrdersMapStr, displayOrderQuickDis);
			}
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		String orderNumbersQD = "";
		List<Order> selectedOrders = new ArrayList<Order>();
		boolean flag=false;
		for(String orderid : selectedOrderNumbers){
			orderNumbersQD += "," + orderid;
			Order order = displayOrderQuickDis.get(orderid);	
			if("Click & Collect".equals(order.getDeliveryOption())){
				flag=true;
			}
			selectedOrders.add(order);			
		}
		if(orderNumbersQD.startsWith(",")){
			orderNumbersQD = orderNumbersQD.substring(1);
		}
		renderRequestQD.setAttribute("selectedOrders", selectedOrders);
		renderRequestQD.setAttribute(selectedOrderNos, orderNumbersQD);
		OrderManagementUtil.setPermissions(renderRequestQD);
		if(flag && ("ON").equalsIgnoreCase(isBulkCnCAvail)){
			renderPage =  "quick-dispatch-cnc-orders";
		} else {
			renderPage =  OrderConstants.DISPLAY_QUICK_UPDATE_CONFIRM;
		}
		long endSec = System.currentTimeMillis();
		log.debug(sellerIdLog+OrderManagementUtil.getSellerId(renderRequestQD)+StringPool.SPACE+StringPool.COMMA+endString+className+rendrMethodName+timeLog+endSec);
		log.debug(sellerIdLog+sellerIdForQuickDis+StringPool.SPACE+StringPool.COMMA+totalElapsedStr+className+rendrMethodName+timeInSec+(endSec-startSec)/1000);
		return renderPage;
	}	
	
	@ResourceMapping("printQuickOrders")
	public void actionQuickPrintOpenOrders(ResourceRequest resourceRequestLabl, ResourceResponse resourceResponseLabl, @RequestParam("selectedOrderNos") String selectedOrderNos, @RequestParam("selectedQTY") String selectedQTY) {
		long startSec = System.currentTimeMillis();
		log.debug(sellerIdLog+OrderManagementUtil.getSellerId(resourceRequestLabl)+StringPool.SPACE+StringPool.COMMA+startString+className+actQuickPrintStr+timeLog+startSec);
		long sellerIdForQuickDis = OrderManagementUtil.getSellerId(resourceRequestLabl);
		String statusForQuickDis = resourceRequestLabl.getPortletSession().getAttribute(OrderConstants.STRING_SELECTED_TAB) != null ? (String) resourceRequestLabl.getPortletSession().getAttribute(OrderConstants.STRING_SELECTED_TAB) : "released";
		String[] selectedOrderNumbers = selectedOrderNos.split(OrderConstants.SPLITTER);
		String[] selectedOrderQty = selectedQTY.split(OrderConstants.SPLITTER);
		Gson gsonObj = new Gson();
		NdxmlErrRes yodlErr= null;
		HashMap<String, String> resp = new HashMap<String, String>();
		StringBuffer ordLblJsonResp = new StringBuffer();
		Map<String, Order> displayOrderQuickDis = new HashMap<String, Order>();
		PortletSession getsession=resourceRequestLabl.getPortletSession();
		try {
			displayOrderQuickDis = orderService.getSelectedOrders(statusForQuickDis, selectedOrderNumbers, sellerIdForQuickDis, resourceRequestLabl);
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		String orderNumbersQD = "";
		List<Order> selectedOrders = new ArrayList<Order>();
		int i = 0;
		List<QuickOrderLabelDetails> ordrLblMinLst = new ArrayList<QuickOrderLabelDetails>();
		QuickOrderLabelDetails olm = null;
		boolean isProb = false;
		for (String orderid : selectedOrderNumbers) {
			orderNumbersQD += OrderConstants.COMMA + orderid;
			Order order = displayOrderQuickDis.get(orderid);
			if (OrderConstants.CNC.equals(order.getDeliveryOption())) {
				List<OrderLabelDetails> viewLblDtlLst = (List<OrderLabelDetails>) OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrderIdSellrIdIsUsed(orderid, sellerIdForQuickDis, false);
				int fromQty = viewLblDtlLst.size();
				int toQty = Integer.parseInt("NaN".equalsIgnoreCase(selectedOrderQty[i]) ? "0" : selectedOrderQty[i]);
				while (fromQty < toQty) {
				/*  if(!isProb){*/
					Date pickUpDate = null;
					TradingPartner tp = null;
					CarrierDetails cd = null;
					CarrierAttributes ca = null;
					Ndxml yodlResp = null;
					Calendar todayDate = Calendar.getInstance();
					try {
						Order orderDetails = new Order();
						orderDetails = order;
						tp = orderService.getSellerById(sellerIdForQuickDis); 
						ca = orderService.getCarrierAttributes(sellerIdForQuickDis);
						cd = ca.getCarrierDetails();
						String pickUpTime = OrderConstants.PICKUP_TIME;
						String cutOffTime = tp.getTradingPartnerDetails().getTradingPartnerExtraAttributes().getFpCutOffTime();
						DateFormat sdf = new SimpleDateFormat(OrderConstants.DDMMYY);
						DateFormat onlyDate = new SimpleDateFormat(OrderConstants.DATEFRMT);
						if(todayDate.get(Calendar.DAY_OF_WEEK) == 7){
							todayDate.add(Calendar.DATE, 2);
							Date newDate = todayDate.getTime();
							String filterDate = onlyDate.format(newDate);
							pickUpDate = sdf.parse(filterDate+StringPool.SPACE+pickUpTime);
						}else if(todayDate.get(Calendar.DAY_OF_WEEK) == 1){
							todayDate.add(Calendar.DATE, 1);
							Date newDate = todayDate.getTime();
							String filterDate = onlyDate.format(newDate);
							pickUpDate = sdf.parse(filterDate+StringPool.SPACE+pickUpTime);
						}else{
						Date today = new Date();
						String todayString = onlyDate.format(today);
						Date dateWithCutOffTime = sdf.parse(todayString+StringPool.SPACE+cutOffTime);
						pickUpDate = new Date();
						if (pickUpDate.compareTo(dateWithCutOffTime) > 0) {
						if (todayDate.get(Calendar.DAY_OF_WEEK) == 6) {
									todayDate.setTime(pickUpDate);
									todayDate.add(Calendar.DATE, 3);
						} else {
									todayDate.setTime(pickUpDate);
									todayDate.add(Calendar.DATE, 1);
							}
							Date newDate = todayDate.getTime();
							String filterDate = onlyDate.format(newDate);
							pickUpDate = sdf.parse(filterDate + StringPool.SPACE + pickUpTime);
							} else {
								String filterDate = onlyDate.format(pickUpDate);
								pickUpDate = sdf.parse(filterDate + StringPool.SPACE + pickUpTime);
							}
						}
						Object objectres = orderService.createYodlLabel(sellerIdForQuickDis, orderid, tp, pickUpDate, ca, orderDetails);
						if (objectres instanceof Ndxml) {
							yodlResp = (Ndxml) objectres;
							String filePath = OrderConstants.YODL_LABEL_PATH;
							String trackingId = yodlResp.getResponse().getJob().getConsignment().getNumber();
							String pdfUrl = yodlResp.getResponse().getJob().getLabelData().getUrl();
							String fullFilePath = OrderHelperUtil.downloadPDFAndSave(pdfUrl, filePath, trackingId);
							OrderLabelDetails orderLabelDetails = OrderLabelDetailsLocalServiceUtil.createOrderLabelDetails(trackingId);
							orderLabelDetails.setPdfFilePath(fullFilePath);
							orderLabelDetails.setOrderId(orderid);
							orderLabelDetails.setIsUsed(Boolean.FALSE);
							orderLabelDetails.setCreatedDate(new Date());
							orderLabelDetails.setSellerId(sellerIdForQuickDis);
							orderLabelDetails.setCollectionDate(pickUpDate);
							OrderLabelDetailsLocalServiceUtil.updateOrderLabelDetails(orderLabelDetails);
							if (fromQty == 0) {
								olm = new QuickOrderLabelDetails(orderid, trackingId, pickUpDate, "true");
							} else {
								olm = new QuickOrderLabelDetails(orderid, trackingId, pickUpDate, "false");
							}
							ordrLblMinLst.add(olm);
							fromQty++;
						}else if(objectres instanceof NdxmlErrRes){
							yodlErr = (NdxmlErrRes) objectres;
							if(yodlErr.getResponse().getStatus().getErrorCode() == 4003){
								resp.put(OrderConstants.IS_AUTH_FAIL, trueStr);
								resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E040));
								ordLblJsonResp.append(gsonObj.toJson(resp).toString());
							}else if(yodlErr.getResponse().getStatus().getErrorCode() == 8071){
								resp.put(OrderConstants.IS_AUTH_FAIL, trueStr);
								resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E040));
								ordLblJsonResp.append(gsonObj.toJson(resp).toString());
							}else if(yodlErr.getResponse().getStatus().getErrorCode() == 8057){
								resp.put(OrderConstants.IS_AUTH_FAIL, trueStr);
								resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E044));
								ordLblJsonResp.append(gsonObj.toJson(resp).toString());
							}else{
								resp.put(OrderConstants.IS_RES_NULL, trueStr);
								resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E041));
								ordLblJsonResp.append(gsonObj.toJson(resp).toString());
							}
							isProb = true;
							break;
							}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(isProb){
						break;
					}
					/*}else{
						break;
					}*/
				}
				/*if(isProb){
					break;
				}*/
			}
			i++;
			selectedOrders.add(order);
			if(isProb){
				break;
			}
		}
		if(!isProb){
			ordLblJsonResp.append(gsonObj.toJson(ordrLblMinLst).toString());
		}
		HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponseLabl);
		response.setContentType(appJsonContType);
		InputStream inputStream;
		try {
			inputStream = new ByteArrayInputStream(ordLblJsonResp.toString().getBytes(charactSetUtf8));
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long endSec = System.currentTimeMillis();
		log.info(sellerIdLog+sellerIdForQuickDis+StringPool.SPACE+StringPool.COMMA+endString+className+actQuickPrintStr+timeLog+endSec);
		log.info(sellerIdLog+sellerIdForQuickDis+StringPool.SPACE+StringPool.COMMA+totalElapsedStr+className+actQuickPrintStr+timeInSec+(endSec-startSec)/1000);
	}

	@ResourceMapping("PrintQuickAllLabel")
	public void actionPrintAllYodlLabel(ResourceRequest resourceRequestLabl, ResourceResponse resourceResponseLabl, @RequestParam("selectedOrderNos") String selectedOrderNos) throws IOException {
		long sellerIdForQuickDis = OrderManagementUtil.getSellerId(resourceRequestLabl);
		String[] selectedOrderNumbers = selectedOrderNos.split(OrderConstants.SPLITTER);
		List<OrderLabelDetails> orderLblLst = null;
		PDFMergerUtility ut = new PDFMergerUtility();
		for (String orderId : selectedOrderNumbers) {
			orderLblLst = (List<OrderLabelDetails>) OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrderIdSellrIdIsUsed(orderId, sellerIdForQuickDis, false);
			for (OrderLabelDetails itr : orderLblLst) {
				ut.addSource(itr.getPdfFilePath());
			}
		}
		String filePath = PropsUtil.get("file.yodl.location") + selectedOrderNos + OrderConstants.PDF;
		ut.setDestinationFileName(filePath);
		try {
			ut.mergeDocuments();
		} catch (COSVisitorException e1) {
			e1.printStackTrace();
		}
		File file = new File(filePath);
		ServletOutputStream stream = null;
		BufferedInputStream buf = null;
		HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponseLabl);
		try {
			stream = response.getOutputStream();
			response.setContentType(OrderConstants.APP_PDF);
			response.setDateHeader(OrderConstants.EXPIRE, 0);
			response.addHeader(OrderConstants.CONT_DISP, "inline; filename=trackingsAll.pdf");
			response.setContentLength((int) file.length());
			buf = new BufferedInputStream(new FileInputStream(file));
			int readBytes = 0;
			while ((readBytes = buf.read()) != -1) {
				stream.write(readBytes);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				stream.flush();
			}
			if (buf != null) {
				buf.close();
			}
			File pdfFl = null;
			pdfFl = new File(filePath);
			pdfFl.delete();
		}
	}
	@ActionMapping(params = "action=updateOrder")
	public void actionUpdateOrder(ActionRequest actionRequest, ActionResponse actionResponse,
			@RequestParam("lineItem") int[] lineItems,@RequestParam(OrderConstants.STRING_ORDER_ID) String orderId) {
		log.debug("inside action update order - order details controller");
		long sellerId = OrderManagementUtil.getSellerId(actionRequest);
		OrderBasket ordBasket = orderBasketService.getOrderBasket(actionRequest);
		List<Order> pendingOrds = ordBasket.getOrdersForUpdate();
		Map<String,Order> releasedOrdersFromBasket = null;
		StringBuilder trackId = null;
        String ordrId = ParamUtil.getString(actionRequest, OrderConstants.STRING_ORDER_ID);        
        String updateOrderAction = ParamUtil.getString(actionRequest, "updateOrderAction");
		if (updateOrderAction.equals(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH)
				|| updateOrderAction.equals(OrderConstants.UPDATE_ORDER_ACTION_CANCEL)) {
			releasedOrdersFromBasket = ordBasket.getAllOrderDetails();
					}
		
        Order currentOrder = releasedOrdersFromBasket.get(ordrId);
        Order oldOrder = new Order();
        getHardCopy(currentOrder, oldOrder);
       	ordBasket.getOriginalPendingItems().add(oldOrder);
       	currentOrder.setOrderStatus(PENDING);
        Order pendingOrder = new Order();
        Map<Integer, OrderLineItem> pendingOrderLineItems = new LinkedHashMap<Integer, OrderLineItem>();
        BeanUtils.copyProperties(currentOrder, pendingOrder);
        pendingOrder.setOrderLineItems(pendingOrderLineItems);
        
        if (updateOrderAction.equals(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH)) {
        	pendingOrder.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH);
        	pendingOrder.setTrackingId(ParamUtil.getString(actionRequest, "trackingId"));
        	pendingOrder.setTrackingURL(ParamUtil.getString(actionRequest, "trackingURL"));
        	pendingOrder.setCarrierName(ParamUtil.getString(actionRequest, "carrierName"));
        }
        if (updateOrderAction.equals(OrderConstants.UPDATE_ORDER_ACTION_RETURN)) {
        	pendingOrder.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_RETURN);
        }
        if (updateOrderAction.equals(OrderConstants.UPDATE_ORDER_ACTION_CANCEL)) {
        	pendingOrder.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_CANCEL);
        }
        Map<Integer, OrderLineItem> currentOrderLineItems = currentOrder.getOrderLineItems();
        for (Integer lineNumber : currentOrderLineItems.keySet()) {
			for(int lineItemNumber:lineItems){
			    	if(lineNumber == lineItemNumber){
			    		double updatedQuantity = ParamUtil.getDouble(actionRequest, (OrderConstants.UPDATED_QUANTITY+lineNumber));
			        	OrderLineItem currentOrderLineItem = currentOrderLineItems.get(lineNumber);
			        	if (updateOrderAction.equals(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH)) {
				        	currentOrderLineItem.setDispatchedQuantity(updatedQuantity);
				        	currentOrderLineItem.setOpenQuantity(currentOrderLineItem.getOpenQuantity()-(currentOrderLineItem.getDispatchedQuantity()));
				        	currentOrderLineItem.setLineItemStatus(PENDING);
				        	currentOrderLineItem.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH);
				        	if(("Click & Collect").equalsIgnoreCase(currentOrder.getDeliveryOption())){
				        		trackId = new StringBuilder(ParamUtil.getString(actionRequest, "trackingId"));
				        		currentOrderLineItem.setTrackingId(trackId);
				        	}
				        	pendingOrderLineItems.put(lineNumber, currentOrderLineItem);
			        	}
			    		if (updateOrderAction.equals(OrderConstants.UPDATE_ORDER_ACTION_CANCEL)) {
			    			currentOrderLineItem.setPrimaryReasonForCancellation(ParamUtil.getString(actionRequest, "primaryCancelReason"));
			    			currentOrderLineItem.setCancelledQuantity(updatedQuantity);
			    			currentOrderLineItem.setOpenQuantity(currentOrderLineItem.getOrderedQuantity());
			    			currentOrderLineItem.setLineItemStatus(PENDING);
			    			currentOrderLineItem.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_CANCEL);
			    			pendingOrderLineItems.put(lineNumber, currentOrderLineItem);
			    		}
				}
			}
		}
        currentOrder.setOrderLineItems(currentOrderLineItems);
        OrderManagementUtil.setPermissions(actionRequest);
        pendingOrds.add(pendingOrder);
        ordBasket.setOrdersForUpdate(pendingOrds);
        orderBasketService.setOrderBasket(ordBasket, actionRequest);
        SessionMessages.add(actionRequest, OrderConstants.POST_FINAL_CONFIRM_MESSAGE);
        if(actionRequest.getPortletSession().getAttribute(sellerId+undrScre+orderId) != null){
        	actionRequest.getPortletSession().removeAttribute(sellerId+undrScre+orderId);
		}
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
	@ActionMapping(params="action=returnOrder")
	public void actionReturnOrder(ActionRequest actionRequestRO, ActionResponse actionResponseRO,
			@RequestParam("lineItem") int[] lineItems,@RequestParam(OrderConstants.STRING_ORDER_ID) String orderId ) {
		int lineItemNo = lineItems[0];
		OrderBasket ordrBasket = orderBasketService.getOrderBasket(actionRequestRO);
		List<Order> pendingOrd = ordrBasket.getOrdersForUpdate();
		if(pendingOrd == null){
			pendingOrd = new ArrayList<Order>();
		}
		Map<String,Order> allOrderFromBasket = null;
		String ordrId = ParamUtil.getString(actionRequestRO, OrderConstants.STRING_ORDER_ID);  
		allOrderFromBasket = ordrBasket.getAllOrderDetails();
        Order currentOrd = allOrderFromBasket.get(ordrId); 
        Order oldOrder = new Order();
        getHardCopy(currentOrd, oldOrder);
        currentOrd.setOrderStatus(PENDING);
        ordrBasket.getOriginalPendingItems().add(oldOrder);
        Order returnOrder = new Order();
        Map<Integer, OrderLineItem> pendingOrderLineItems = new LinkedHashMap<Integer, OrderLineItem>();
        BeanUtils.copyProperties(currentOrd, returnOrder);
        returnOrder.setOrderLineItems(pendingOrderLineItems);
        returnOrder.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_RETURN);
        Map<Integer, OrderLineItem> returnOrderLineItems = currentOrd.getOrderLineItems();
		double returnQuantity = ParamUtil.getDouble(actionRequestRO, (OrderConstants.UPDATED_QUANTITY+lineItemNo));
    	OrderLineItem returnOrderLineItem = returnOrderLineItems.get(lineItemNo);
		returnOrderLineItem.setReasonForReturn(ParamUtil.getString(actionRequestRO, "primaryReturnReason"));
		returnOrderLineItem.setSecondaryReasonForReturn(ParamUtil.getString(actionRequestRO, "secondaryReturnReason"));
		returnOrderLineItem.setRefundAmount(ParamUtil.getDouble(actionRequestRO, "refundAmount"));
		returnOrderLineItem.setReturnedQuantity(returnQuantity);
		returnOrderLineItem.setDispatchedQuantity(returnOrderLineItem.getDispatchedQuantity()-(returnOrderLineItem.getReturnedQuantity()));
		returnOrderLineItem.setLineItemStatus(PENDING);
		returnOrderLineItem.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_RETURN);
		pendingOrderLineItems.put(lineItemNo, returnOrderLineItem);
        currentOrd.setOrderLineItems(returnOrderLineItems);
        pendingOrd.add(returnOrder);
        ordrBasket.setOrdersForUpdate(pendingOrd);
        orderBasketService.setOrderBasket(ordrBasket, actionRequestRO);
        OrderManagementUtil.setPermissions(actionRequestRO);
        SessionMessages.add(actionRequestRO, OrderConstants.POST_FINAL_CONFIRM_MESSAGE);
		log.debug("Return from actionRetrunOrder method in Controller.");
	}
	
	@ActionMapping(params = "action=quickDispatchOpenOrders")
	public void actionQuickDispatchOpenOrders(ActionRequest actionRequest,ActionResponse actionResponse) {
		log.debug("Coming To actionQuickDispatchOpenOrders method in controller.");
		String[] selectedOrderNumbers = ParamUtil.getParameterValues(actionRequest, selectedOrderNos);
		if(selectedOrderNumbers  != null){
			actionRequest.getPortletSession().removeAttribute(OrderConstants.SELECTED_ORDER_NUMBERS);
		}
		OrderBasket orderBasket = orderBasketService.getOrderBasket(actionRequest);
		List<Order> pendingOrders = orderBasket.getOrdersForUpdate();
		if(pendingOrders == null){
			pendingOrders = new ArrayList<Order>();
		}
        Map<String,Order> releasedOrdersFromBasket = orderBasket.getAllOrderDetails();
		for (String selectedOrderNumber:selectedOrderNumbers) {
			Order pendingOrder = new Order();
			Order currentOrd = releasedOrdersFromBasket.get(selectedOrderNumber.trim());
			Order oldOrder = new Order();
		    getHardCopy(currentOrd, oldOrder);
		    orderBasket.getOriginalPendingItems().add(oldOrder);
		    currentOrd.setOrderStatus(PENDING);
			Map<Integer, OrderLineItem> pendingOrderLineItems = new LinkedHashMap<Integer, OrderLineItem>();
	        BeanUtils.copyProperties(currentOrd, pendingOrder);
	        pendingOrder.setOrderLineItems(pendingOrderLineItems);
	        pendingOrder.setTrackingId(ParamUtil.getString(actionRequest, "trackingId-"+selectedOrderNumber));
	        pendingOrder.setTrackingURL(ParamUtil.getString(actionRequest, "trackingURL-"+selectedOrderNumber));
	        pendingOrder.setCarrierName(ParamUtil.getString(actionRequest, "carrierName-"+selectedOrderNumber));
	        pendingOrder.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH);
	        
	        
	        Map<Integer, OrderLineItem> currentLineItems = currentOrd.getOrderLineItems();
	        for (Integer lineNum : currentLineItems.keySet()) {
	        	OrderLineItem orderLineItem = currentLineItems.get(lineNum);
	        	orderLineItem.setLineItemStatus(PortletProps.get(OrderConstants.STATUS_PORTAL_DISPATCHED));
	        	orderLineItem.setDispatchedQuantity(orderLineItem.getOpenQuantity());
	        	orderLineItem.setOpenQuantity(orderLineItem.getOpenQuantity()-(orderLineItem.getDispatchedQuantity()));
	        	orderLineItem.setLineItemStatus(PENDING);
	        	orderLineItem.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH);
	        	pendingOrderLineItems.put(lineNum, orderLineItem);
	        }
	        currentOrd.setOrderLineItems(currentLineItems);	        
	        pendingOrders.add(pendingOrder);
		}
        orderBasket.setOrdersForUpdate(pendingOrders);
        OrderManagementUtil.setPermissions(actionRequest);
        orderBasketService.setOrderBasket(orderBasket, actionRequest);
		log.debug("Return from actionQuickDispatchOpenOrders method in Controller.");
		SessionMessages.add(actionRequest, OrderConstants.POST_FINAL_CONFIRM_MESSAGE);
		actionResponse.setRenderParameter(OrderConstants.STRING_ACTION, "renderDisplayOpenOrders");
	}
	
	@ActionMapping(params = "action=removeItemAction")
	public void actionRemoveFromBasket(ActionRequest actionRequest) {
	    log.debug("Comming to actionRemoveFromBasket method of Order Details Controller");
	    int index = ParamUtil.getInteger(actionRequest, "index");
	    OrderBasket orderBasket = orderBasketService.getOrderBasket(actionRequest);
	    Order order = orderBasket.getOrdersForUpdate().get(index);
	    orderBasket.getOrdersForUpdate().remove(index);
	    List<Order> ord = orderBasket.getOriginalPendingItems();
	    Order orderForRemove = null;
	    for(Order ords : ord){
	    	if(order.getServiceActionType().equals(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH) || order.getServiceActionType().equals(OrderConstants.UPDATE_ORDER_ACTION_CANCEL)){
	    		if(ords.getOrderId().equals(order.getOrderId())){
		    		orderBasket.getReleasedOrders().put(ords.getOrderId(), ords);
		    		orderForRemove = ords;
		    	}
	    	}else if(order.getServiceActionType().equals(OrderConstants.UPDATE_ORDER_ACTION_RETURN)){
	    		if(ords.getOrderId().equals(order.getOrderId())){
		    		orderBasket.getDispatchedOrders().put(ords.getOrderId(), ords);
		    		orderForRemove = ords;
		    	}
	    	}
	    }
	    if(orderForRemove != null){
	    	ord.remove(orderForRemove);
	    }
	    orderBasketService.setOrderBasket(orderBasket, actionRequest);
	    OrderManagementUtil.setPermissions(actionRequest);
	}
	
	@ResourceMapping("getYodlLabel")
	public void actionGetYodlLabel(ResourceRequest resourceRequestLabl, ResourceResponse resourceResponseLabl,
			@RequestParam(OrderConstants.STRING_ORDER_ID) String orderId, @RequestParam(OrderConstants.COLLECTION_DATE) String collectionDate ) {
		/*long sellerId = 1000004L;
		orderId = "D6WMC8D-1";
		String yodlAccntId = "4536316";
		String identity = "waterf24";
		String password = "waterf24vtp";*/
		long sellerId = OrderManagementUtil.getSellerId(resourceRequestLabl);
		Date pickUpDate = null;
		TradingPartner tp = null;
		CarrierDetails cd = null;
		CarrierAttributes ca = null;
		Gson gsonObj = new Gson();
		StringBuffer ordLblJsonResp = new StringBuffer();
		Ndxml yodlResp = null;
		NdxmlErrRes yodlErr= null;
		HashMap<String, String> resp = new HashMap<String, String>();
		PortletSession getsession=resourceRequestLabl.getPortletSession();
		Calendar todayDate = Calendar.getInstance();
		
		try {
			List<OrderLabelDetails> orderLblLst = new ArrayList<OrderLabelDetails>();
			List<OrderLabelMin> ordrLblMinLst = new ArrayList<OrderLabelMin>();
			orderLblLst = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrderIdSellrIdIsUsed(orderId, sellerId,false);
			if(orderLblLst.size() <= 0){ //Checking if labels are already exists for selected OrderId and Seller ID
				Order orderDetails = orderService.getOrderDetails(orderId, sellerId, resourceRequestLabl);
				tp = orderService.getSellerById(sellerId); //Get Trading Partner Details
				ca = orderService.getCarrierAttributes(sellerId); //Get Carrier Attributes by Seller Id
				cd = ca.getCarrierDetails();
				String pickUpTime = OrderConstants.PICKUP_TIME;
				String cutOffTime = tp.getTradingPartnerDetails().getTradingPartnerExtraAttributes().getFpCutOffTime();
				DateFormat sdf = new SimpleDateFormat(OrderConstants.DDMMYY);
				DateFormat onlyDate = new SimpleDateFormat(OrderConstants.DATEFRMT);
				if(todayDate.get(Calendar.DAY_OF_WEEK) == 7){
					todayDate.add(Calendar.DATE, 2);
					Date newDate = todayDate.getTime();
					String filterDate = onlyDate.format(newDate);
					pickUpDate = sdf.parse(filterDate+StringPool.SPACE+pickUpTime);
				}else if(todayDate.get(Calendar.DAY_OF_WEEK) == 1){
					todayDate.add(Calendar.DATE, 1);
					Date newDate = todayDate.getTime();
					String filterDate = onlyDate.format(newDate);
					pickUpDate = sdf.parse(filterDate+StringPool.SPACE+pickUpTime);
				}else{
				Date today = new Date();
				String todayString = onlyDate.format(today);
				Date dateWithCutOffTime = sdf.parse(todayString+StringPool.SPACE+cutOffTime);
				//if(collectionDate == null || collectionDate.length() <= 0){//First time on Click of Dispatch button
					pickUpDate = new Date();
					if(pickUpDate.compareTo(dateWithCutOffTime) > 0){
						if(todayDate.get(Calendar.DAY_OF_WEEK) == 6){
							todayDate.setTime(pickUpDate);
							todayDate.add(Calendar.DATE, 3);
						}else{
							todayDate.setTime(pickUpDate);
							todayDate.add(Calendar.DATE, 1);
						}
						Date newDate = todayDate.getTime();
						String filterDate = onlyDate.format(newDate);
						pickUpDate = sdf.parse(filterDate+StringPool.SPACE+pickUpTime);
					}else{
						String filterDate = onlyDate.format(pickUpDate);
						pickUpDate = sdf.parse(filterDate+StringPool.SPACE+pickUpTime);
					}
				//}
				}
			
				Object objectres = orderService.createYodlLabel(sellerId, orderId, tp, pickUpDate,ca, orderDetails);
				if(objectres instanceof Ndxml){
					
					log.info("OrderDetailsContraoller--> ValidRes from yodel" + objectres.getClass());
				yodlResp = (Ndxml) objectres;
				log.info("Yodel Status code :"+yodlResp.getResponse().getStatus().getCode());
				String filePath = OrderConstants.YODL_LABEL_PATH;
				String trackingId = yodlResp.getResponse().getJob().getConsignment().getNumber();
				String pdfUrl = yodlResp.getResponse().getJob().getLabelData().getUrl();
				log.info("Trackng Id for Order "+orderId+" is "+trackingId);
				//Download Yodl Label PDF and store in Folder
				String fullFilePath = OrderHelperUtil.downloadPDFAndSave(pdfUrl, filePath, trackingId);
				/* TODO - Later Change code to cretae yodel when there are no unused labels for this seller and order */
				//Update in DB 
				OrderLabelDetails orderLabelDetails = OrderLabelDetailsLocalServiceUtil.createOrderLabelDetails(trackingId);
				orderLabelDetails.setPdfFilePath(fullFilePath);
				orderLabelDetails.setOrderId(orderId);
				orderLabelDetails.setIsUsed(Boolean.FALSE);
				orderLabelDetails.setCreatedDate(new Date());
				orderLabelDetails.setSellerId(sellerId);
				orderLabelDetails.setCollectionDate(pickUpDate);
				OrderLabelDetailsLocalServiceUtil.updateOrderLabelDetails(orderLabelDetails);
				orderLblLst = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrderIdSellrIdIsUsed(orderId, sellerId,false);

				OrderLabelMin olm = null;
				for (OrderLabelDetails itr : orderLblLst) {
					olm = new OrderLabelMin(itr.getOrderId(), itr.getTrackingId(), itr.getCollectionDate());
					ordrLblMinLst.add(olm);
				}
				ordLblJsonResp.append(gsonObj.toJson(ordrLblMinLst).toString());
				
				}else if(objectres instanceof NdxmlErrRes){
					
					log.info("OrderDetailsContraoller--> ErrorRes from yodel " + objectres.getClass());
					
				yodlErr = (NdxmlErrRes) objectres;
				if(yodlErr.getResponse().getStatus().getErrorCode() == 4003){
					resp.put(OrderConstants.IS_AUTH_FAIL, trueStr);
					resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E040));
					ordLblJsonResp.append(gsonObj.toJson(resp).toString());
				}else if(yodlErr.getResponse().getStatus().getErrorCode() == 8071){
					resp.put(OrderConstants.IS_AUTH_FAIL, trueStr);
					resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E040));
					ordLblJsonResp.append(gsonObj.toJson(resp).toString());
				}else if(yodlErr.getResponse().getStatus().getErrorCode() == 8057){
					resp.put(OrderConstants.IS_AUTH_FAIL, trueStr);
					resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E044));
					ordLblJsonResp.append(gsonObj.toJson(resp).toString());
				}else if(yodlErr.getResponse().getStatus().getErrorCode() == 8070){	
					if(getsession.getAttribute(OrderConstants.IS_FIRST) == null){
						resp.put(OrderConstants.IS_FIRST, trueStr);					
					}else{
						resp.put(OrderConstants.IS_FIRST, falseStr);
					}
					if(collectionDate == null ||collectionDate.length() <= 0){
						Date todaysDate = new Date();
						String todaysString = onlyDate.format(todaysDate);
						collectionDate = todaysString;
					}	
						resp.put(OrderConstants.IS_CALENDAR,trueStr);
						String[] rep=collectionDate.split(hyphen);
						String jnow = rep[2]+backSlash+rep[1]+backSlash+rep[0].substring(2,4);
						resp.put(OrderConstants.IS_DATE,jnow);
						ordLblJsonResp.append(gsonObj.toJson(resp).toString());
						getsession.setAttribute(OrderConstants.IS_FIRST,"get");
						log.info("Response for ERROR in CNC for actionAddYodlLabel111: "+ordLblJsonResp);
				}else{
					
					resp.put(OrderConstants.IS_RES_NULL, trueStr);
					resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E041));
					ordLblJsonResp.append(gsonObj.toJson(resp).toString());
				}
				}
			} else{
				log.info("OrderDetailsContraoller--> Null Response from yodel");
				
				
					OrderLabelMin olm = null;
					for (OrderLabelDetails itr : orderLblLst) {
						olm = new OrderLabelMin(itr.getOrderId(), itr.getTrackingId(), itr.getCollectionDate());
						ordrLblMinLst.add(olm);
					}
					ordLblJsonResp.append(gsonObj.toJson(ordrLblMinLst).toString());
			}
				log.info("Order Label Details in JSON format : "+ordLblJsonResp);
		} catch (NullPointerException e) {
			resp.put(OrderConstants.IS_RES_NULL, trueStr);
			resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E041));
			ordLblJsonResp.append(gsonObj.toJson(resp).toString());
			e.printStackTrace();
		} catch (Exception e) {
			resp.put(OrderConstants.IS_RES_NULL, trueStr);
			resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E041));
			ordLblJsonResp.append(gsonObj.toJson(resp).toString());
			e.printStackTrace();
		}
		//Get YodlAccount ID, identity, password
		HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponseLabl);
		response.setContentType(appJsonContType);
		InputStream inputStream;
		try {
			inputStream = new ByteArrayInputStream(ordLblJsonResp.toString().getBytes(charactSetUtf8));
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			if(inputStream!=null){
				inputStream.close();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@ResourceMapping("resetlLabel")
	public void resetYodlLabel(ResourceRequest resourceRequestLabl, ResourceResponse resourceResponseLabl,
			@RequestParam(OrderConstants.STRING_ORDER_ID) String orderId) {
		long sellerId = OrderManagementUtil.getSellerId(resourceRequestLabl);
		TradingPartner tp = null;
		CarrierDetails cd = null;
		CarrierAttributes ca = null;
		Order orderDetails = null;
		StringBuffer ordLblJsonResp = new StringBuffer();
		Ndxml yodlResp = null;
		NdxmlErrRes yodlErr= null;
		HashMap<String, String> resp = new HashMap<String, String>();
		Object objectres = null;
		String pdfFolderPath = OrderConstants.YODL_LABEL_PATH;
		StringBuffer pdfFilePath = new StringBuffer();
		Gson gsonObj = new Gson();
		PortletSession resetsession = resourceRequestLabl.getPortletSession();
		Calendar todayDate =Calendar.getInstance();
		Date pickUpDate = null;
		try {
			tp = orderService.getSellerById(sellerId);
			ca = orderService.getCarrierAttributes(sellerId); //Get Carrier Attributes by Seller Id
			List<OrderLabelDetails> orderLblDtlLst = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrderIdSellrIdIsUsed(orderId, sellerId, false);
			cd = ca.getCarrierDetails();
			String pickUpTime = OrderConstants.PICKUP_TIME;
			String cutOffTime = tp.getTradingPartnerDetails().getTradingPartnerExtraAttributes().getFpCutOffTime();
			DateFormat sdf = new SimpleDateFormat(OrderConstants.DDMMYY);
			DateFormat onlyDate = new SimpleDateFormat(OrderConstants.DATEFRMT);
			String filePath = null;
			File pdfFl = null;
			boolean isDeleted = false;
			for (OrderLabelDetails itr : orderLblDtlLst) {
				isDeleted = false;
				pdfFilePath = new StringBuffer();
				pdfFilePath.append(pdfFolderPath);
				pdfFilePath.append(itr.getTrackingId()+OrderConstants.PDF);
				pdfFl = new File(pdfFilePath.toString());
				//Delete label PDF from folder
				pdfFl.setWritable(true);
				isDeleted = pdfFl.delete();
				if(isDeleted){ //If label is deleted from Folder, delete from DB
					OrderLabelDetailsLocalServiceUtil.deleteOrderLabelDetails(itr.getTrackingId());

				}else{
					throw new Exception("Unable to delete the PDF file");
				}
			}
			if(todayDate.get(Calendar.DAY_OF_WEEK) == 7){
				todayDate.add(Calendar.DATE, 2);
				Date newDate = todayDate.getTime();
				String filterDate = onlyDate.format(newDate);
				pickUpDate = sdf.parse(filterDate+StringPool.SPACE+pickUpTime);
			}else if(todayDate.get(Calendar.DAY_OF_WEEK) == 1){
				todayDate.add(Calendar.DATE, 1);
				Date newDate = todayDate.getTime();
				String filterDate = onlyDate.format(newDate);
				pickUpDate = sdf.parse(filterDate+StringPool.SPACE+pickUpTime);
			}else{
			Date today = new Date();
			String todayString = onlyDate.format(today);
			Date dateWithCutOffTime = sdf.parse(todayString+StringPool.SPACE+cutOffTime);
			pickUpDate = new Date();
			if(pickUpDate.compareTo(dateWithCutOffTime) > 0){
				Calendar c = Calendar.getInstance();
				if(todayDate.get(Calendar.DAY_OF_WEEK) == 6){
					c.setTime(pickUpDate);
					c.add(Calendar.DATE, 3);
				}else{
					c.setTime(pickUpDate);
					c.add(Calendar.DATE, 1);
				}
				Date newDate = c.getTime();
				String filterDate = onlyDate.format(newDate);
				//pickUpDate.setTime(pickUpTime);
				pickUpDate = sdf.parse(filterDate+StringPool.SPACE+pickUpTime);
			}else{
				String filterDate = onlyDate.format(pickUpDate);
				pickUpDate = sdf.parse(filterDate+StringPool.SPACE+pickUpTime);
			}
			}
			orderDetails = orderService.getOrderDetails(orderId, sellerId, resourceRequestLabl);
			objectres = orderService.createYodlLabel(sellerId, orderId, tp, pickUpDate,ca, orderDetails);
			if(objectres instanceof Ndxml){
				yodlResp = (Ndxml) objectres;
				log.info("Y0del Status code :"+yodlResp.getResponse().getStatus().getCode());
				filePath = OrderConstants.YODL_LABEL_PATH;
				String trackingId = yodlResp.getResponse().getJob().getConsignment().getNumber();
				String pdfUrl = yodlResp.getResponse().getJob().getLabelData().getUrl();
				log.info("Trackng Id for Order "+orderId+" is "+trackingId);
				//Download Yodl Label PDF and store in Folder
				String fullFilePath = OrderHelperUtil.downloadPDFAndSave(pdfUrl, filePath, trackingId);
				/* TODO - Later Change code to cretae yodel when there are no unused labels for this seller and order */
				//Update in DB 
				OrderLabelDetails orderLabelDetails = OrderLabelDetailsLocalServiceUtil.createOrderLabelDetails(trackingId);
				orderLabelDetails.setPdfFilePath(fullFilePath);
				orderLabelDetails.setOrderId(orderId);
				orderLabelDetails.setIsUsed(Boolean.FALSE);
				orderLabelDetails.setCreatedDate(new Date());
				orderLabelDetails.setSellerId(sellerId);
				orderLabelDetails.setCollectionDate(pickUpDate);
				OrderLabelDetailsLocalServiceUtil.updateOrderLabelDetails(orderLabelDetails);
				List<OrderLabelDetails> oldetLst = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrderIdSellrIdIsUsed(orderId, sellerId, false);
				List<OrderLabelMin> ordrLblMinLst = new ArrayList<OrderLabelMin>();
				OrderLabelMin olm = null;
				for (OrderLabelDetails oldtItr : oldetLst) {
					olm = new OrderLabelMin(oldtItr.getOrderId(), oldtItr.getTrackingId(), oldtItr.getCollectionDate());
					ordrLblMinLst.add(olm);
				}

				ordLblJsonResp.append(gsonObj.toJson(ordrLblMinLst).toString());
			}else if(objectres instanceof NdxmlErrRes){
				yodlErr = (NdxmlErrRes) objectres;
				if(yodlErr.getResponse().getStatus().getErrorCode() == 4003){
					resp.put(OrderConstants.IS_AUTH_FAIL, trueStr);
					resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E040));
					ordLblJsonResp.append(gsonObj.toJson(resp).toString());
				}else if(yodlErr.getResponse().getStatus().getErrorCode() == 8071){
					resp.put(OrderConstants.IS_AUTH_FAIL, trueStr);
					resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E040));
					ordLblJsonResp.append(gsonObj.toJson(resp).toString());
				}else if(yodlErr.getResponse().getStatus().getErrorCode() == 8057){
					resp.put(OrderConstants.IS_AUTH_FAIL, trueStr);
					resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E044));
					ordLblJsonResp.append(gsonObj.toJson(resp).toString());
				}else if(yodlErr.getResponse().getStatus().getErrorCode() == 8070){	
					if(resetsession.getAttribute(OrderConstants.IS_FIRST) == null){							
							resp.put(OrderConstants.IS_FIRST, trueStr);
						}else{
							resp.put(OrderConstants.IS_FIRST, falseStr);
						}
						resp.put(OrderConstants.IS_CALENDAR,trueStr);
						String filterDate = onlyDate.format(pickUpDate);
						String[] rep=filterDate.split(hyphen);
						String jnow = rep[2]+backSlash+rep[1]+backSlash+rep[0].substring(2,4);
						resp.put(OrderConstants.IS_DATE,jnow);
						ordLblJsonResp.append(gsonObj.toJson(resp).toString());
						resetsession.setAttribute(OrderConstants.IS_FIRST,"reset");
						log.info("Response for ERROR in CNC for actionAddYodlLabel:222 "+ordLblJsonResp);
				}else{
					resp.put(OrderConstants.IS_RES_NULL, trueStr);
					resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E041));
					ordLblJsonResp.append(gsonObj.toJson(resp).toString());
				}
				}
		}catch (NullPointerException e) {
			resp.put(OrderConstants.IS_RES_NULL, trueStr);
			resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E041));
			ordLblJsonResp.append(gsonObj.toJson(resp).toString());
			e.printStackTrace();
		}catch (Exception e) {
			resp.put(OrderConstants.IS_RES_NULL, trueStr);
			resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E041));
			ordLblJsonResp.append(gsonObj.toJson(resp).toString());
			e.printStackTrace();
		}

		/*Send Response to Ajax call*/
		HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponseLabl);
		response.setContentType(appJsonContType);
		InputStream inputStream;
		try {
			inputStream = new ByteArrayInputStream(ordLblJsonResp.toString().getBytes(charactSetUtf8));
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			if(inputStream!=null){
				inputStream.close();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@ResourceMapping("getItemAndParcelInfo")
	public void getItemAndParcelInfo(ResourceRequest resReq, ResourceResponse resResp,
			@RequestParam(OrderConstants.STRING_ORDER_ID) String orderId) {
		String[] lineItemStr =  resReq.getParameter("lineItem").toString().split(StringPool.COMMA);
		int[] lineItems = new int[lineItemStr.length];
		for(int i = 0;i < lineItemStr.length;i++) {
			lineItems[i] = Integer.parseInt(lineItemStr[i]);
		}
		long sellerId = OrderManagementUtil.getSellerId(resReq);
		Order ord = (Order) resReq.getPortletSession().getAttribute(sellerId+undrScre+orderId);

		Map<Integer, OrderLineItem> orderLineItems = ord.getOrderLineItems();
		List<OrderLabelDetails> oldetLst = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrderIdSellrIdIsUsed(orderId, sellerId, false);
		ItemInfo itemInfo = null; 
		List<ParcelItemInfo> parItmInfLst = new ArrayList<ParcelItemInfo>();
		ParcelItemInfo pii = null;
		List<ItemInfo> itemInfoLst = null;
		for(OrderLabelDetails ordLblDet : oldetLst){
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
		Gson gsonObj = new Gson();
		String parcelInfoResp = gsonObj.toJson(parItmInfLst);
		log.debug("Item Parcel Info to client side "+parcelInfoResp);
		log.debug("Parcel Info Response :"+parcelInfoResp);
		/*Send Response to Ajax call*/
		HttpServletResponse response = PortalUtil.getHttpServletResponse(resResp);
		response.setContentType(appJsonContType);
		InputStream inputStream;
		try {
			inputStream = new ByteArrayInputStream(parcelInfoResp.getBytes(charactSetUtf8));
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			if(inputStream!=null){
				inputStream.close();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@ActionMapping(params = "action=postCandCDispatch")
	public void postCandCDispatch(ActionRequest resReq, ActionResponse resResp,
			@RequestParam(OrderConstants.STRING_ORDER_ID) String orderId) {
		long sellerId = OrderManagementUtil.getSellerId(resReq);
		String updatedparcItmInf = (String) resReq.getParameter("updatedparcItmInf");
		log.debug("Parcel Item Info from Client side "+updatedparcItmInf);
		Gson gsonObj = new Gson();
		java.lang.reflect.Type parcelInfType = new TypeToken<List<ParcelItemInfo>>(){}.getType();
		List<ParcelItemInfo> parcelInfLst = gsonObj.fromJson(updatedparcItmInf, parcelInfType);
		log.debug("inside action postCandCDispatch order - order details controller");
		OrderLabelDetails orderLabelDet = null;
		for (ParcelItemInfo parcelItemInfo : parcelInfLst) {
			OrderBasket ordBasket = orderBasketService.getOrderBasket(resReq);
			List<Order> pendingOrds = ordBasket.getOrdersForUpdate();
			Map<String,Order> releasedOrdersFromBasket = null;
			releasedOrdersFromBasket = ordBasket.getAllOrderDetails();
			Order currentOrder = releasedOrdersFromBasket.get(orderId);
			Order oldOrder = new Order();
			getHardCopy(currentOrder, oldOrder);
			ordBasket.getOriginalPendingItems().add(oldOrder);
			currentOrder.setOrderStatus(PENDING);
			// order for adding into the pending list
			Order pendingOrder = new Order();
			Map<Integer, OrderLineItem> pendingOrderLineItems = new LinkedHashMap<Integer, OrderLineItem>();
			BeanUtils.copyProperties(currentOrder, pendingOrder);
			pendingOrder.setOrderLineItems(pendingOrderLineItems);

			pendingOrder.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH);
			pendingOrder.setTrackingId(parcelItemInfo.getTrackingId());
			pendingOrder.setTrackingURL(ParamUtil.getString(resReq, "trackingURL"));
			pendingOrder.setCarrierName(ParamUtil.getString(resReq, "Yodel"));
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
						currentOrderLineItem.setLineItemStatus(PENDING);
						currentOrderLineItem.setServiceActionType(OrderConstants.UPDATE_ORDER_ACTION_DISPATCH);
						pendingOrderLineItems.put(lineNumber, currentOrderLineItem);
					}
				}
			}
			pendingOrder.setOrderLineItems(pendingOrderLineItems);
			pendingOrds.add(pendingOrder);
			ordBasket.setOrdersForUpdate(pendingOrds);
			orderBasketService.setOrderBasket(ordBasket, resReq);
		}
		OrderManagementUtil.setPermissions(resReq);
		if(resReq.getPortletSession().getAttribute(sellerId+undrScre+orderId) != null){
			resReq.getPortletSession().removeAttribute(sellerId+undrScre+orderId);
		}
		SessionMessages.add(resReq, OrderConstants.POST_FINAL_CONFIRM_MESSAGE);
		// resResp.setRenderParameter(OrderConstants.STRING_ACTION, "renderDisplayOpenOrders");
	}
	@ResourceMapping("addYodlLabel")
	public void actionAddYodlLabel(ResourceRequest resourceRequestLabl, ResourceResponse resourceResponseLabl,
			@RequestParam(OrderConstants.STRING_ORDER_ID) String orderId, @RequestParam(OrderConstants.COLLECTION_DATE) String collectionDate ) {
		long startSec = System.currentTimeMillis();
		log.debug(sellerIdLog+OrderManagementUtil.getSellerId(resourceRequestLabl)+StringPool.SPACE+StringPool.COMMA+startString+className+actionAddYodlLabelStr+timeLog+startSec);
		long sellerId = OrderManagementUtil.getSellerId(resourceRequestLabl);
		Date pickUpDate = null;
		TradingPartner tp = null;
		CarrierDetails cd = null;
		CarrierAttributes ca = null;
		Gson gsonObj = new Gson();
		StringBuffer ordLblJsonResp = new StringBuffer();
		Ndxml yodlResp = null;
		NdxmlErrRes yodlErr = null;
		HashMap<String, String> resp = new HashMap<String, String>();
		PortletSession addsession = resourceRequestLabl.getPortletSession();
		Calendar todayDate = Calendar.getInstance();
		try {
			Order orderDetails = orderService.getOrderDetails(orderId, sellerId, resourceRequestLabl);
			tp = orderService.getSellerById(sellerId); //Get Trading Partner Details
			ca = orderService.getCarrierAttributes(sellerId); //Get Carrier Attributes by Seller Id
			cd = ca.getCarrierDetails();
			String pickUpTime = OrderConstants.PICKUP_TIME;
			String cutOffTime = tp.getTradingPartnerDetails().getTradingPartnerExtraAttributes().getFpCutOffTime();
			log.info("OrderDetailsController : actionAddYodlLabel() : cutOffTime :"+cutOffTime);
			DateFormat sdf = new SimpleDateFormat(OrderConstants.DDMMYY);
			DateFormat onlyDate = new SimpleDateFormat(OrderConstants.DATEFRMT);
			
			if(todayDate.get(Calendar.DAY_OF_WEEK) == 7){
				todayDate.add(Calendar.DATE, 2);
				Date newDate = todayDate.getTime();
				String filterDate = onlyDate.format(newDate);
				pickUpDate = sdf.parse(filterDate+StringPool.SPACE+pickUpTime);
				log.info("OrderDetailsController : actionAddYodlLabel() : first if pickUpDate :"+pickUpDate.toString());
			}else if(todayDate.get(Calendar.DAY_OF_WEEK) == 1){
				todayDate.add(Calendar.DATE, 1);
				Date newDate = todayDate.getTime();
				String filterDate = onlyDate.format(newDate);
				pickUpDate = sdf.parse(filterDate+StringPool.SPACE+pickUpTime);
				log.info("OrderDetailsController : actionAddYodlLabel() : else if pickUpDate :"+pickUpDate.toString());
			}else{
			Date today = new Date();
			String todayString = onlyDate.format(today);
			Date dateWithCutOffTime = sdf.parse(todayString+StringPool.SPACE+cutOffTime);
			log.info("OrderDetailsController : actionAddYodlLabel() : dateWithCutOffTime :"+dateWithCutOffTime.toString());
			if(collectionDate == null || collectionDate.length() <= 0){//First time on Click of Dispatch button
				pickUpDate = new Date();
				log.info("OrderDetailsController : actionAddYodlLabel() : pickUpdate :"+pickUpDate.toString());
				log.info("Pickupdate and dateWithcutOfftime comparission : "+pickUpDate.compareTo(dateWithCutOffTime));
				if(pickUpDate.compareTo(dateWithCutOffTime) > 0){
					if(todayDate.get(Calendar.DAY_OF_WEEK) == 6){
						todayDate.setTime(pickUpDate);
						todayDate.add(Calendar.DATE, 3);
					}else{
						todayDate.setTime(pickUpDate);
						todayDate.add(Calendar.DATE, 1);
					}
					Date newDate = todayDate.getTime();
					String filterDate = onlyDate.format(newDate);
					//pickUpDate.setTime(pickUpTime);
					pickUpDate = sdf.parse(filterDate+StringPool.SPACE+pickUpTime);
				}else{
					String filterDate = onlyDate.format(pickUpDate);
					pickUpDate = sdf.parse(filterDate+StringPool.SPACE+pickUpTime);
				}
			}
			log.info("OrderDetailsController : actionAddYodlLabel() : else pickUpDate :"+pickUpDate.toString());
			}
			log.info("OrderDetailsController : actionAddYodlLabel() : "+pickUpDate.toString());
			Object objectres = orderService.createYodlLabel(sellerId, orderId, tp, pickUpDate,ca, orderDetails);
			if(objectres instanceof Ndxml){
			yodlResp = (Ndxml) objectres;
			String filePath = OrderConstants.YODL_LABEL_PATH;
			String trackingId = yodlResp.getResponse().getJob().getConsignment().getNumber();
			String pdfUrl = yodlResp.getResponse().getJob().getLabelData().getUrl();
			String fullFilePath = OrderHelperUtil.downloadPDFAndSave(pdfUrl, filePath, trackingId);
			OrderLabelDetails orderLabelDetails = OrderLabelDetailsLocalServiceUtil.createOrderLabelDetails(trackingId);
			orderLabelDetails.setPdfFilePath(fullFilePath);
			orderLabelDetails.setOrderId(orderId);
			orderLabelDetails.setIsUsed(Boolean.FALSE);
			orderLabelDetails.setCreatedDate(new Date());
			orderLabelDetails.setSellerId(sellerId);
			orderLabelDetails.setCollectionDate(pickUpDate);
			OrderLabelDetailsLocalServiceUtil.updateOrderLabelDetails(orderLabelDetails);
			List<OrderLabelDetails> orderLblLst = null;
			orderLblLst = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrderIdSellrIdIsUsed(orderId, sellerId,false);
			List<OrderLabelMin> ordrLblMinLst = new ArrayList<OrderLabelMin>();
			OrderLabelMin olm = null;
			for (OrderLabelDetails itr : orderLblLst) {
				if(itr.getTrackingId().equalsIgnoreCase(trackingId)){
					olm = new OrderLabelMin(itr.getOrderId(), itr.getTrackingId(), itr.getCollectionDate());
					ordrLblMinLst.add(olm);
				}
			}
			ordLblJsonResp.append(gsonObj.toJson(ordrLblMinLst).toString());
			log.info("Order Label Details in JSON format value: "+ordLblJsonResp);
			}else if(objectres instanceof NdxmlErrRes){
			yodlErr = (NdxmlErrRes)	objectres;
			if(yodlErr.getResponse().getStatus().getErrorCode() == 4003){
				resp.put(OrderConstants.IS_AUTH_FAIL, trueStr);
				resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E040));
				ordLblJsonResp.append(gsonObj.toJson(resp).toString());
			}else if(yodlErr.getResponse().getStatus().getErrorCode() == 8071){
				resp.put(OrderConstants.IS_AUTH_FAIL, trueStr);
				resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E040));
				ordLblJsonResp.append(gsonObj.toJson(resp).toString());
			}else if(yodlErr.getResponse().getStatus().getErrorCode() == 8057){
				resp.put(OrderConstants.IS_AUTH_FAIL, trueStr);
				resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E044));
				ordLblJsonResp.append(gsonObj.toJson(resp).toString());
			}else if(yodlErr.getResponse().getStatus().getErrorCode() == 8070){						
				if(addsession.getAttribute(OrderConstants.IS_FIRST) == null){
					resp.put(OrderConstants.IS_FIRST, trueStr);					
				}else{
					resp.put(OrderConstants.IS_FIRST, falseStr);
				}
					resp.put(OrderConstants.IS_CALENDAR,trueStr);
					if(collectionDate == null ||collectionDate.length() <= 0){
						Date today = new Date();
						String todayString = onlyDate.format(today);
						collectionDate = todayString;
					}					
					String[] rep=collectionDate.split(hyphen);
					String jnow = rep[2]+backSlash+rep[1]+backSlash+rep[0].substring(2,4);
					resp.put(OrderConstants.IS_DATE,jnow);
					ordLblJsonResp.append(gsonObj.toJson(resp).toString());
					addsession.setAttribute(OrderConstants.IS_FIRST,"adddone");
					log.debug("Response for ERROR in CNC for actionAddYodlLabel:333 "+ordLblJsonResp);
			}else{
				resp.put(OrderConstants.IS_RES_NULL, trueStr);
				resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E041));
				ordLblJsonResp.append(gsonObj.toJson(resp).toString());
			}
			}
		} catch (NullPointerException e) {
			resp.put(OrderConstants.IS_RES_NULL, trueStr);
			resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E041));
			ordLblJsonResp.append(gsonObj.toJson(resp).toString());
			e.printStackTrace();
		} catch (Exception e) {
			resp.put(OrderConstants.IS_RES_NULL, trueStr);
			resp.put(OrderConstants.ERR_MSG, PortletProps.get(OrderConstants.ORDMG_E041));
			ordLblJsonResp.append(gsonObj.toJson(resp).toString());
			e.printStackTrace();
		}
		HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponseLabl);
		response.setContentType(appJsonContType);
		InputStream inputStream;
		try {
			inputStream = new ByteArrayInputStream(ordLblJsonResp.toString().getBytes(charactSetUtf8));
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			if(inputStream!=null){
				inputStream.close();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long endSec = System.currentTimeMillis();
		log.info(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+endString+className+actionAddYodlLabelStr+timeLog+endSec);
		log.info(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+totalElapsedStr+className+actionAddYodlLabelStr+timeInSec+(endSec-startSec)/1000);
	}
	@ResourceMapping("printLabel")
	public void actionPrintYodlLabel(ResourceRequest resourceRequestLabl, ResourceResponse resourceResponseLabl,
			@RequestParam(OrderConstants.STRING_ORDER_ID) String orderId,@RequestParam(OrderConstants.STRING_TRACKING_ID) String trackingId,@RequestParam("isUsed") boolean isUsed,@RequestParam("viewLabel") boolean viewLabel) throws IOException {
		long sellerId = OrderManagementUtil.getSellerId(resourceRequestLabl);
		List<OrderLabelDetails> orderLblLst = null;
		orderLblLst = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrdIdSelIdIsUsdFinSubmit(orderId, sellerId,isUsed,viewLabel);
		String pdfURL=null;
		for (OrderLabelDetails itr : orderLblLst) {
			if(itr.getTrackingId().equalsIgnoreCase(trackingId)){
				pdfURL=itr.getPdfFilePath();
				break;
			}
		}
		File file = new File(pdfURL);
		ServletOutputStream stream = null;
        BufferedInputStream buf = null;
        HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponseLabl);
        try {
            stream = response.getOutputStream();// set response headers
            response.setContentType(OrderConstants.APP_PDF);
            response.setDateHeader(OrderConstants.EXPIRE, 0);
            response.addHeader(OrderConstants.CONT_DISP,"inline; filename=tracking.pdf");
            response.setContentLength((int) file.length());
            buf = new BufferedInputStream(new FileInputStream(file));
            int readBytes = 0;
            while ((readBytes = buf.read()) != -1){
                stream.write(readBytes);
            }
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
            if (stream != null){
            	 stream.flush();
            }
            if (buf != null){
            	 buf.close();
            }
        }
		} 
	@ResourceMapping("printAllLabel")
	public void actionPrintAllYodlLabel(ResourceRequest resourceRequestLabl, ResourceResponse resourceResponseLabl,
			@RequestParam(OrderConstants.STRING_ORDER_ID) String orderId,@RequestParam("isUsed") boolean isUsed,@RequestParam("viewLabel") boolean viewLabel) throws  IOException {
		long sellerId = OrderManagementUtil.getSellerId(resourceRequestLabl);
		List<OrderLabelDetails> orderLblLst = null;
		orderLblLst = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrdIdSelIdIsUsdFinSubmit(orderId, sellerId,isUsed,viewLabel);
		PDFMergerUtility ut = new PDFMergerUtility();
		for (OrderLabelDetails itr : orderLblLst) {
				ut.addSource(itr.getPdfFilePath());
		}
		String filePath=PropsUtil.get("file.yodl.location")+orderId+OrderConstants.PDF;
		ut.setDestinationFileName(filePath);
		try {
			ut.mergeDocuments();
		} catch (COSVisitorException e1) {
			e1.printStackTrace();
		}
		File file = new File(filePath);
		ServletOutputStream stream = null;
        BufferedInputStream buf = null;
        HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponseLabl);
        try {
            stream = response.getOutputStream();
            response.setContentType(OrderConstants.APP_PDF);
            response.setDateHeader(OrderConstants.EXPIRE, 0);
            response.addHeader(OrderConstants.CONT_DISP,"inline; filename=trackingAll.pdf");
            response.setContentLength((int) file.length());
            buf = new BufferedInputStream(new FileInputStream(file));
            int readBytes = 0;
            while ((readBytes = buf.read()) != -1){
                stream.write(readBytes);
            }
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
            if (stream != null){
            	 stream.flush();
            }
            if (buf != null){
            	 buf.close();
            }
            File pdfFl = null;
            pdfFl = new File(filePath);
			 pdfFl.delete();
			
        }
        }
	@ResourceMapping("deletelLabel")
	public void actionDeleteYodlLabel(ResourceRequest resourceRequestLabl, ResourceResponse resourceResponseLabl,
			@RequestParam(OrderConstants.STRING_ORDER_ID) String orderId ,@RequestParam(OrderConstants.STRING_TRACKING_ID) String trackId) {
		long sellerId = OrderManagementUtil.getSellerId(resourceRequestLabl);
		TradingPartner tp = null;
		StringBuffer ordLblJsonResp = new StringBuffer();
		Gson gsonObj = new Gson();
		List<OrderLabelMin> ordrLblMinLst = new ArrayList<OrderLabelMin>();
		OrderLabelMin olm = null;
		int orderCountAfterDelete = 0;
		try {
			List<OrderLabelDetails> orderLblDtlLst = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrderIdSellrIdIsUsed(orderId, sellerId, false);
			String filePath = null;
			File pdfFl = null;
			orderCountAfterDelete = orderLblDtlLst.size();
			for (OrderLabelDetails itr : orderLblDtlLst) {
				if(itr.getTrackingId().equalsIgnoreCase(trackId)){
				filePath = itr.getPdfFilePath();
				pdfFl = new File(filePath);
				boolean isDeleted = pdfFl.delete();
				if(isDeleted){ //If label is deleted from Folder, delete from DB
					OrderLabelDetailsLocalServiceUtil.deleteOrderLabelDetails(itr.getTrackingId());
					log.info("Tracking id has been deleted from DB : " + itr.getTrackingId());
					orderCountAfterDelete--;
				}else{
					throw new Exception("Unable to delet the PDF file at location ");
				}
				}
			}
			ordrLblMinLst.add(new OrderLabelMin(orderId, "success", null));
		} catch (Exception e) {
			ordrLblMinLst.add(new OrderLabelMin(orderId, "failure", null));
			e.printStackTrace();
		}
		/** Set parcel count after deleting from DB and send it in AJAX response - for (-) minus  **/
		String orderCountStringToAjaxResp = Integer.toString(orderCountAfterDelete);
		ordrLblMinLst.add(new OrderLabelMin(orderId, orderCountStringToAjaxResp, null));
		ordLblJsonResp.append(gsonObj.toJson(ordrLblMinLst).toString());
		HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponseLabl);
		response.setContentType(appJsonContType);
		InputStream inputStream;
		try {
			inputStream = new ByteArrayInputStream(ordLblJsonResp.toString().getBytes(charactSetUtf8));
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			if(inputStream!=null){
				inputStream.close();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
