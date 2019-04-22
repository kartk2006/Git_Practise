package com.tesco.mhub.order.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.service.OrderBasketService;
import com.tesco.mhub.order.service.OrderService;
import com.tesco.mhub.order.util.OrderManagementUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller
@RequestMapping("VIEW")
public class PrintAndDownloadController {
	/**
	 * service layer object.
	 */
	@Autowired(required = true)
	private OrderService ordService;

	/**
	 * order basket service object.
	 */
	@Autowired(required = true)
	private OrderBasketService ordBasketService;
	/**
	 * logger object.
	 */
	private Log log = LogFactoryUtil.getLog(getClass());
	/**
	 * date format object.
	 */
	private DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
	
	@RenderMapping(params = "jspPage=printOrders")
	public String doPrintPopupRender(RenderRequest renderRequestPrint,RenderResponse response) {
		
			/*log.info("its in popup module");*/

			long sellerIdForPrint = OrderManagementUtil.getSellerId(renderRequestPrint);
			String statusForPrint = (renderRequestPrint.getPortletSession().getAttribute(OrderConstants.STRING_SELECTED_TAB) !=null && !"".equals(renderRequestPrint.getPortletSession().getAttribute(OrderConstants.STRING_SELECTED_TAB).toString().trim())) ? (String) renderRequestPrint.getPortletSession().getAttribute(OrderConstants.STRING_SELECTED_TAB) : "released";
			
			String pageNameForPrint = ParamUtil.getString(renderRequestPrint, "pageDisplay");
			log.info("pagename = " + pageNameForPrint);
			
			/////
			String[] qryParams =  pageNameForPrint.split(StringPool.BACK_SLASH + StringPool.QUESTION);//"\\?");
			String[] splitQryParams = qryParams[1].split(StringPool.BACK_SLASH + StringPool.STAR);
			String selectedTab = (splitQryParams[0].split(StringPool.COLON))[1];
			String selOrderOpt = (splitQryParams[1].split(StringPool.COLON))[1];
			
			////
			//String[] splitPageName1 = pageNameForPrint.split(StringPool.COLON);
			// returns the page name - display if rendering Dispatched Orders.
			//String[] display = splitPageName1[0].split(StringPool.BACK_SLASH + StringPool.QUESTION);//"\\?");
			//String pageDisplayed = display[0].toString();
			//log.info("pageDisplayed = " + pageDisplayed.toString());
			// returns the selected ORDER IDs from dispatched orders / open orders
			// page.
			
			
			
			Map<String, Order> displayOrder = new HashMap<String, Order>();
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
			String dtFormat = "dd-MM-yyyy";
			dateFormat.format(startDate);
			dateFormat.format(endDate);
			
			
			try {
				if(OrderConstants.RETRIEVE_ORDER_STATUS_RELEASED.equalsIgnoreCase(selectedTab) || OrderConstants.RETRIEVE_ORDER_STATUS_ONLY_RELEASED.equalsIgnoreCase(selectedTab)){ //For Open tab
					
					if("Selecteditems".equalsIgnoreCase(selOrderOpt)){
						orderIdsForPrint =  ((splitQryParams[2].split(StringPool.COLON))[1]).split(StringPool.COMMA);
						log.info("orderIds = " + Arrays.toString(orderIdsForPrint));
						displayOrder = ordService.getSelectedOrders(statusForPrint, orderIdsForPrint, sellerIdForPrint, renderRequestPrint);
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
						displayOrder = ordService.getOrdersForPrintDownload(OrderConstants.RETRIEVE_ORDER_STATUS_ONLY_RELEASED, startDate, endDate, deliveryOption, String.valueOf(sellerIdForPrint), renderRequestPrint);
					}else if("AllCcOrders".equalsIgnoreCase(selOrderOpt)){
						deliveryOption = "Store_Collect";
						displayOrder = ordService.getOrdersForPrintDownload(OrderConstants.RETRIEVE_ORDER_STATUS_ONLY_RELEASED, startDate, endDate, deliveryOption, String.valueOf(sellerIdForPrint), renderRequestPrint);
						if(displayOrder.size() > 0){
							displayOrder = getClickAndCollect(displayOrder);
						}
					}else if("AllFilter".equalsIgnoreCase(selOrderOpt)){
						String selDelOptLst = "";
						if((splitQryParams[2].split(StringPool.COLON)).length > 1){//Checking whether any delivery option is selected in filters
							selDelOptLst = (splitQryParams[2].split(StringPool.COLON))[1];
						}
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
						displayOrder = ordService.getOrdersForPrintDownload(OrderConstants.RETRIEVE_ORDER_STATUS_ONLY_RELEASED, startDate, endDate, deliveryOption, String.valueOf(sellerIdForPrint), renderRequestPrint);
					}
				
				}else { //For Dispatched, Returned and Cancelled tabs
					orderIdsForPrint =  ((splitQryParams[2].split(StringPool.COLON))[1]).split(StringPool.COMMA);
					displayOrder = ordService.getSelectedOrders(statusForPrint, orderIdsForPrint, sellerIdForPrint, renderRequestPrint);
					log.info("printing " + displayOrder.keySet().size() + " orders.");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(displayOrder != null && displayOrder.size() > 0){
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
					}
				}
			}
			
			renderRequestPrint.setAttribute("sessionDispatchedOrders", displayOrderToPrint);
			renderRequestPrint.setAttribute("displayOrderMap", displayOrderMap);
			OrderManagementUtil.setPermissions(renderRequestPrint);
			
			String activity = displayOrder.toString();
		/*	if(("open").equals(pageDisplayed)){
				log.info("Comming to open ");
			OrderManagementUtil.createAuditMessage(renderRequestPrint, activity, "Print List");
			}
			if(("dispatchLines").equals(pageDisplayed)){
				log.info("Comming to dispatchLines ");
			OrderManagementUtil.createAuditMessage(renderRequestPrint, activity, "Print Order Details");
			}*/
			return "printOrders";
	}
	
	private Map<String, Order> getClickAndCollect(Map<String, Order> displayOrder){
		Map<String, Order> ccOrdersLst = new HashMap<String, Order>();
		for (String oId : displayOrder.keySet()) {
			Order ord = displayOrder.get(oId);
			if(("Click & Collect").equalsIgnoreCase(ord.getDeliveryOption())){
				ccOrdersLst.put(oId, ord);
			}
			
		}
		
		return ccOrdersLst;
	}
	
	
}
