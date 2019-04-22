package com.tesco.mhub.order.dao;

import com.tesco.mhub.gmo.deliveryoption.request.TradingPartner;
import com.tesco.mhub.gmo.managetp.carrierDetails.CarrierAttributes;
import com.tesco.mhub.order.ack.orders.AcknowledgeOrders;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.retrieve.order.RetrieveOrders;
import com.tesco.mhub.order.returns.refunds.ReturnRefunds;
import com.tesco.mhub.order.search.SearchOrdersResponse;
import com.tesco.mhub.order.shipment.update.ShipmentUpdates;
import com.tesco.mhub.report.feed.ProcessingReport;

import java.util.Date;

import javax.portlet.PortletRequest;

public interface OrderDAO {

	RetrieveOrders getReleasedOrders(Date startDate, Date endDate, long sellerId) throws Exception;

	RetrieveOrders getDispatchedOrders(Date startDate, Date endDate, long sellerId) throws Exception;
	
	RetrieveOrders getSelectedOrders(String status,String[] orderIds, long sellerId) throws Exception;
	
	RetrieveOrders getSelectedOrdersForPrintDownLd(String status, Date startDate, Date endDate,	String deliveryOption, String sellerId, PortletRequest portletRequest) throws Exception;
	
	RetrieveOrders getOrderDetails(String orderId, long sellerId) throws Exception;
	
	Object getYodlLabel(String orderId, long sellerId, TradingPartner tp, Date pickUpDate, CarrierAttributes ca, Order order) throws Exception;

	String cancelOrders(AcknowledgeOrders acknowledgeOrders, long sellerId) throws Exception;
	
    ProcessingReport getTransactionProcessingReport(String transactionId) throws Exception;
    
    String dispatchOrders(ShipmentUpdates shipmentUpdates, long sellerId) throws Exception;
    
    String returnOrders(ReturnRefunds returnRefunds, long sellerId) throws Exception;
    
    SearchOrdersResponse getSearchResult(String statusSOR, Date startDateSOR, Date endDateSOR, String filterDateBySOR, String sortBySOR,String deliveryOptionSOR,String customerNameSOR,long sellerIdSOR,int pageNumberSOR) throws Exception;
    
    TradingPartner getSellerById(long tradingPartnerId) throws Exception;
    
    CarrierAttributes getCarrierAttributesById(long sellerId) throws Exception;
}
