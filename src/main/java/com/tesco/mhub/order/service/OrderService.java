package com.tesco.mhub.order.service;

import com.tesco.mhub.gmo.deliveryoption.request.TradingPartner;
import com.tesco.mhub.gmo.managetp.carrierDetails.CarrierAttributes;
import com.tesco.mhub.gmo.managetp.carrierDetails.CarrierDetails;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.ResponseSearchOrders;
import com.tesco.mhub.yodl.response.Ndxml;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

public interface OrderService {

	Map<String, Order> getReleasedOrders(Date startDate, Date endDate, long sellerId, PortletRequest portletRequest) throws Exception;

	Map<String, Order> getDispatchedOrders(Date startDate, Date endDate, long sellerId, PortletRequest portletRequest) throws Exception;
	
	ResponseSearchOrders getSearchOrders(String statusForSearch, Date startDateForSearch, Date endDateForSearch, String filterDateByForSearch, String sortByForSearch,String deliveryOptionForSearch,String customerNameForSearch,long sellerIdForSearch,int pageNumberForSearch,PortletRequest portletRequestForSearch);

    String getProcessingReport(String transactionId) throws Exception;

    Map<String, Order> getSelectedOrders(String status,String[] orderIds, long sellerId, PortletRequest portletRequest) throws Exception;
    
    Order getOrderDetails(String orderId, long sellerId, PortletRequest portletRequest) throws Exception;
    
	String cancelOrders(long sellerId, List<Order> cancelOrders) throws Exception;

	File exportOrderDetails(List<Order> orders);
			
	String returnOrders(long sellerId, List<Order> returnOrder) throws Exception;
	
    String dispatchOrders(long sellerId, List<Order> dispatchOrder) throws Exception;
    
    Map<String, Order> getOrdersForPrintDownload(String status, Date startDate, Date endDate, String deliveryOption, String sellerId, PortletRequest portletRequest) throws Exception;
    
    Object createYodlLabel(long sellerId, String orderId, TradingPartner tradingPartner, Date pickUpDate, CarrierAttributes ca,  Order order) throws Exception;
    
    TradingPartner getSellerById(long tradingPartnerId) throws Exception;
    
    CarrierAttributes getCarrierAttributes(long tradingPartnerId) throws Exception;

	ResponseSearchOrders getOrderList(String orderId, long sellerId,PortletRequest portletRequest);
    
}

