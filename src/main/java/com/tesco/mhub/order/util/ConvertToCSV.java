package com.tesco.mhub.order.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderLineItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


public abstract class ConvertToCSV {
	/**
	 * log object.
	 */
	private static Log log = LogFactoryUtil.getLog(ConvertToCSV.class);
	
	public static File toCSV(List<Order> printOrderLst) {
		File ordersCSVFile = new File("my-orders.csv");
		StringBuffer data = new StringBuffer();
		data.append("Customer Order Number,Seller Order Number,Order Date,Line Number,Merchant SKU Id,Item Short Description,Ordered Quantity,Unit Of Sale,Retail Price,Is Gift Wrapped Order,Gift Message,Installation LineNumber,Bundle Item Line Number,First Name,Last Name,Mobile No,"
		+"HomeTel-Day,HomeTel-Night,Email,Ship To First Name,Ship To Last Name,Ship To Address Line1,Ship To Address Line2,Ship To Address Line3,Ship To Address Line4,Ship To Address Line5,Ship To Address Line6,Ship To City,Ship To County,Ship To Country,Ship To Post Code,Ship To Mobile No.,Ship to Store Number,"
		+"Ship To Store Name,Delivery Option,Expected Delivery Date,Expected Dispatch Date,Minimum Age Required,IsPodRequired,Delivery Instructions,Delivery Charges,Quantity,Cancellation Reason 1,Cancellation Reason 2");
		data.append("\n");
		DateFormat dateFormats = new SimpleDateFormat("dd/MM/yyyy");
		String clickAndCollectDeliveryCost= null;
		for (Order order : printOrderLst) {
			
			if(order!=null && order.getDeliveryOption()!=null){
				if(order.getDeliveryOption().equalsIgnoreCase(OrderConstants.CNC)){
					
					clickAndCollectDeliveryCost = OrderConstants.NOT_APPLICABLE;
				} else{
					clickAndCollectDeliveryCost = String.valueOf(order.getDeliveryCost());
				}
			}
			
			Map<Integer,OrderLineItem> lineItemDetails = order.getOrderLineItems();
			for(Integer key : lineItemDetails.keySet()){
				OrderLineItem item = lineItemDetails.get(key);
				String orderSqnc = order.getCustomerOrderNumber()+StringPool.COMMA+order.getOrderId()+StringPool.COMMA+String.valueOf(dateFormats.format(order.getOrderPlacedDate()))
						+StringPool.COMMA+String.valueOf(item.getOrderLineNumber())+StringPool.COMMA+item.getSku()+StringPool.COMMA+escapeString(item.getDescription())
						+StringPool.COMMA+String.valueOf(item.getOrderedQuantity())+StringPool.COMMA+item.getUnitOfSale()+StringPool.COMMA+String.valueOf(item.getCostPerItem())
						+StringPool.COMMA+item.getIsGiftWrappedOrder()+StringPool.COMMA+escapeString(item.getGiftMessage())+StringPool.COMMA+item.getInstallationLineNumber()
						+StringPool.COMMA+item.getBundleItemLineNumber()+StringPool.COMMA+escapeString(order.getCustomerFirstName())
						+StringPool.COMMA+escapeString(order.getCustomerLastName())+StringPool.COMMA+order.getCustomerMobile()
						+StringPool.COMMA+order.getCustomerDayTelephone()+StringPool.COMMA+order.getCustomerEveningTelephone()+StringPool.COMMA+order.getCustomerEmail()
						+StringPool.COMMA+escapeString(item.getShipToFirstName())+StringPool.COMMA+escapeString(item.getShipToLastName())
						+StringPool.COMMA+escapeString(item.getShipingAddress1())+StringPool.COMMA+escapeString(item.getShipingAddress2())+StringPool.COMMA+escapeString(item.getShipingAddress3())
						+StringPool.COMMA+escapeString(item.getShipingAddress4())+StringPool.COMMA+escapeString(item.getShipingAddress5())+StringPool.COMMA+escapeString(item.getShipingAddress6())
						+StringPool.COMMA+item.getShipToCity()+StringPool.COMMA+item.getShipToCounty()+StringPool.COMMA+item.getShipToCountry()
						+StringPool.COMMA+item.getShipToPostcode()+StringPool.COMMA+order.getShipToMobile()+StringPool.COMMA+item.getShipToStoreNumber()
						+StringPool.COMMA+escapeString(item.getShipToStoreName())+StringPool.COMMA+order.getDeliveryOption()
						+StringPool.COMMA+String.valueOf(dateFormats.format(item.getExpectedDeliveryDate()))+StringPool.COMMA+String.valueOf(dateFormats.format(order.getExpectedDispatchDate()))
						+StringPool.COMMA+item.getMinimumAgeRequired()+StringPool.COMMA+item.getIsPODRequired()+StringPool.COMMA+escapeString(item.getDeliveryInstructions())
						+StringPool.COMMA+clickAndCollectDeliveryCost+StringPool.COMMA+String.valueOf(item.getOpenQuantity())
						+StringPool.COMMA+escapeString(item.getPrimaryReasonForCancellation())+StringPool.COMMA+escapeString(item.getSecondaryReasonForReturn());
				
				data.append(orderSqnc);
				data.append("\n");
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(ordersCSVFile);
			fos.write(data.toString().getBytes());
	        fos.close();
		} catch (FileNotFoundException e) {
			log.error(OrderConstants.ORDMG_E024+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E024)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (IOException e) {
			log.error(OrderConstants.ORDMG_E025+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E025)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		}
				
		return ordersCSVFile;
	}
	
	public static String escapeString(String str) {
		 StringBuilder sb = new StringBuilder();
		if(str != null && str.length() > 0){
			sb.append('\"');
			sb.append(str);
			sb.append('\"');
			str = sb.toString();
		}
			return str;
	  }
	
	
}
