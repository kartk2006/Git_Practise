package com.tesco.mhub.order.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;
import com.tesco.mhub.BusinessFunctionStatusChecker;
import com.tesco.mhub.MhubAuditMessages;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.model.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletRequest;

public abstract class OrderManagementUtil {
	
	
	/**
	 * logger object.
	 */
	private static Log log = LogFactoryUtil.getLog(OrderManagementUtil.class);
	
	
	public static void setPermissions(PortletRequest request){
		//set all the boolean attribute for aal the roles here
		
			 	//open orders page - call these variables on page
				boolean isViewReleasedOrders = businessFunctionCheck(PortalUtil.getUserId(request), OrderConstants.VIEW_OPEN_ORDERS_SELLER);
				request.setAttribute("isViewReleasedOrders", isViewReleasedOrders);
				
				boolean isMultiOdrPrintBtnVisible = businessFunctionCheck(PortalUtil.getUserId(request), OrderConstants.PRINT_MULTPLE_ORDERS_SELLER);
				request.setAttribute("isMultiOdrPrintBtnVisible", isMultiOdrPrintBtnVisible);
				
				boolean isOrderDetailsPrintBtnVisible = businessFunctionCheck(PortalUtil.getUserId(request), OrderConstants.PRINT_ORDER_DETAILS_SELLER);
				request.setAttribute("isOrderDetailsPrintBtnVisible", isOrderDetailsPrintBtnVisible);
				
				boolean isQuickDispatchMultiOdrsVisible = businessFunctionCheck(PortalUtil.getUserId(request), OrderConstants.QUICK_DISPATCH_MULTIPLE_ORDERS_SELLER);
				request.setAttribute("isQuickDispatchMultiOdrsVisible", isQuickDispatchMultiOdrsVisible);
				
				boolean isDwnldMultiOrdersVisible = businessFunctionCheck(PortalUtil.getUserId(request), OrderConstants.DOWNLOAD_MULTIPLE_ORDERS_SELLER);
				request.setAttribute("isDwnldMultiOrdersVisible", isDwnldMultiOrdersVisible);
				
				boolean isViewDispatchedOrders = businessFunctionCheck(PortalUtil.getUserId(request), OrderConstants.VIEW_DISPATCHED_ORDERS_SELLER);
				request.setAttribute("isViewDispatchedOrders", isViewDispatchedOrders);
				
				boolean isReturnOdrLinesVisible = businessFunctionCheck(PortalUtil.getUserId(request), OrderConstants.RETURN_ORDER_QUANTITY_LINE_SELLER);
				request.setAttribute("isReturnOdrLinesVisible", isReturnOdrLinesVisible);

				//check this variable on open/dispatched order details page - for rendering the details
				boolean isViewOrderDetails = businessFunctionCheck(PortalUtil.getUserId(request), OrderConstants.VIEW_ORDER_DETAILS_SELLER);
				request.setAttribute("isViewOrderDetails", isViewOrderDetails);
				
				//check this variable on open/dispatched order details page - for print fn
				boolean isMultiOrderDetailsPrintBtnVisible = businessFunctionCheck(PortalUtil.getUserId(request), OrderConstants.PRINT_MULTIPLE_ORDER_DETAILS_SELLER);
				request.setAttribute("isMultiOrderDetailsPrintBtnVisible", isMultiOrderDetailsPrintBtnVisible);
				
				//check this variable on open order details page - for dispatch order lines fn
				boolean isDispatchOdrLinesVisible = businessFunctionCheck(PortalUtil.getUserId(request), OrderConstants.DISPATCH_ORDER_QUANTITY_LINE_SELLER);
				request.setAttribute("isDispatchOdrLinesVisible", isDispatchOdrLinesVisible);
				
				//check this variable on open order details page - for cancel order lines fn
				boolean isCancelOdrLinesVisible = businessFunctionCheck(PortalUtil.getUserId(request), OrderConstants.CANCEL_ORDER_QUANTITY_LINE_SELLER);
				request.setAttribute("isCancelOdrLinesVisible", isCancelOdrLinesVisible);
				
				boolean isCancelOdrsVisible = businessFunctionCheck(PortalUtil.getUserId(request), OrderConstants.CANCEL_ORDERS_SELLER);
				request.setAttribute("isCancelOdrsVisible", isCancelOdrsVisible);
				
				//keep this variable on both the pages
				boolean isSubmitFinalOdrVisible = businessFunctionCheck(PortalUtil.getUserId(request), OrderConstants.SUBMIT_PENDING_UPDATES_SELLER);
				request.setAttribute("isSubmitFinalOdrVisible", isSubmitFinalOdrVisible);
				
	}

	private static boolean businessFunctionCheck(long userId,String businessFunctionName) {
		return BusinessFunctionStatusChecker.businessFunctionCheck(userId, businessFunctionName);
	}
	
	public static List<String> returnDates(Iterator<Order> dispOrders, boolean reverse){
		
		List<Date> allDate = new ArrayList<Date>();
		List<String> placedDateList = new ArrayList<String>();
		
		while(dispOrders.hasNext()){
			Order ds = dispOrders.next();
			allDate.add(ds.getOrderPlacedDate());
		}
		Collections.sort(allDate);
		if(reverse){
			Collections.reverse(allDate);
		}
		for(Date dt1:allDate){
			
			String date1 = dt1.toString();
			date1 = date1.substring(0, 10);
			
			if(!placedDateList.contains(date1)){
				placedDateList.add(date1);
			}
		}
		/*log.info("allDate : " + allDate);*/
		return placedDateList;
	}
	
	public static long getSellerId(PortletRequest request){
		long sellerId = 0; //long sellerId = 1000033;   //long sellerId = 1013632;
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			User user = themeDisplay.getUser();
			
			List<Organization> orgs = user.getOrganizations();
			long companyId = PortalUtil.getDefaultCompanyId();
			
			long classNameId = ClassNameLocalServiceUtil.getClassNameId(Organization.class.getName());
			ExpandoTable table = ExpandoTableLocalServiceUtil.getDefaultTable(companyId,classNameId);
			ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.getColumn(table.getTableId(), "sellerId");
			
			if(orgs.size()>0){
				Organization org = orgs.get(0);
				ExpandoValue expandoValue = ExpandoValueLocalServiceUtil.getValue(table.getTableId(), expandoColumn.getColumnId(), org.getOrganizationId());
				if(Validator.isNotNull(expandoValue)) {
					sellerId = expandoValue.getLong();
				}
			}
		} catch (PortalException e) {
			log.error(OrderConstants.ORDMG_E026+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E026)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (SystemException e) {
			log.error(OrderConstants.ORDMG_E027+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E027)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		}
		return sellerId;
	}
	
	public static String getOrgNameBySellerId(User user) {
		String orgName = "";
		
		try {
			List<Organization> org = user.getOrganizations();
			orgName = org.get(0).getName();
		} catch (PortalException e) {
			log.error(OrderConstants.ORDMG_E028+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E028)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (SystemException e) {
			log.error(OrderConstants.ORDMG_E029+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E029)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		}
		
		return orgName;
	}

	/**
	 * @param request.
	 * @param activity.
	 * @param eventType.
	 */
	public static void createAuditMessage(PortletRequest request, String activity, String eventType) {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		String sellerId = String.valueOf(OrderManagementUtil.getSellerId(request));
		String tpName = OrderManagementUtil.getOrgNameBySellerId(themeDisplay.getUser());
		
		MhubAuditMessages.createAuditMessages(themeDisplay,	"order", eventType,	sellerId, tpName, activity);

	}
}
