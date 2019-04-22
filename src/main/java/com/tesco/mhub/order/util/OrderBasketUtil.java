package com.tesco.mhub.order.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderBasket;
import com.tesco.mhub.order.model.Transaction;
import com.tesco.mhub.order.service.OrderService;
import com.tesco.mhub.order.serviceimpl.OrderServiceImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderBasketUtil {
	/**
	 * log object.
	 */
	private static Log log = LogFactoryUtil.getLog(OrderBasketUtil.class);
	

	/**
	 * service layer object.
	 */
	@Autowired(required = true)
	private OrderService orderService;
	
	
	public OrderBasket getOrderBasket() {
		OrderBasket orderBasket = new OrderBasket();

		return orderBasket;
	}
	
	public Map<String,Order> mergeOrders(Map<String,Order> ordersForDisplay, List<Order> ordersFromBasket) {
 	 Map<String,Order> releasedOrders = new LinkedHashMap<String, Order>();
        if(OrderHelperUtil.isValidMap(ordersForDisplay) && OrderHelperUtil.isValidList(ordersFromBasket)){
        	
			for(String orderId : ordersForDisplay.keySet()){
				for(Order orderIdInBasket : ordersFromBasket){
					if(!(orderId.equals(orderIdInBasket.getOrderId()))){
						/*log.info("Comming to if block...");*/
						releasedOrders.put(orderId, ordersForDisplay.get(orderId));					
					}
				}
			}
			
			for(Order ordIdInBasket : ordersFromBasket){
				/*log.info("Order Status In Basket ::"+ordIdInBasket.getOrderStatus());
				log.info("Comming to else block...");*/
				releasedOrders.put(ordIdInBasket.getOrderId(), ordIdInBasket);
				
			}
		}
        return releasedOrders;
	}
	
	public Map<String, Order> mergeReleasedOrders(Map<String,Order> ordsForDisplay,Map<String, Transaction> ordersFromTransactionBasket) throws Exception{
		log.info("Comming to mergeReleasedOrders >>>>>>>>>>>>");
		Map<String,Order> relesedOrders = new LinkedHashMap<String, Order>();
        if(OrderHelperUtil.isValidMap(ordsForDisplay) && OrderHelperUtil.isValidMap(ordersFromTransactionBasket)){
		
        	for(String odIdForDisp : ordsForDisplay.keySet()){
        		if(ordersFromTransactionBasket.containsKey(odIdForDisp)){
    				log.info("Comming to fst if >>>>>>>>>>>>>>>>");
    				Transaction txnss = ordersFromTransactionBasket.get(odIdForDisp);
    				String txnIds = txnss.getTransactionId();
    				log.info("Transaction Id in Util ::"+txnIds);
    				String processingResult = orderService.getProcessingReport(txnIds);	
    				log.info("Processing Result while reloading >>>>  ::"+processingResult);
    				if(processingResult.equals(OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INPROGRESS_KEY)){
    					log.info("Comming to 2nd if >>>>>>>>>>>>>>>");
    					relesedOrders.put(odIdForDisp, txnss.getOldOrder());
    				}else{
    					relesedOrders.put(odIdForDisp, ordsForDisplay.get(odIdForDisp));
    				}
				}else{
					relesedOrders.put(odIdForDisp, ordsForDisplay.get(odIdForDisp));
					
				}
        	}
        }
		return relesedOrders;
	}
	
	public Map<String, Order> mergeDispatchedOrders(Map<String,Order> ordsForDispDisplay,Map<String, Transaction> ordersFromTransactionBasketDisp) throws Exception{
		
		Map<String,Order> dispatchedOrders = new LinkedHashMap<String, Order>();
        if(OrderHelperUtil.isValidMap(ordsForDispDisplay) && OrderHelperUtil.isValidMap(ordersFromTransactionBasketDisp)){
		
        	for(String odIdForDisp : ordsForDispDisplay.keySet()){
        		if(ordersFromTransactionBasketDisp.containsKey(odIdForDisp)){
    				log.info("Comming to fst if >>>>>>>>>>>>>>>>");
    				Transaction txn = ordersFromTransactionBasketDisp.get(odIdForDisp);
    				String txnId = txn.getTransactionId();
    				log.info("Transaction Id in Util ::"+txnId);
    				String processingResult = orderService.getProcessingReport(txnId);	
    				log.info("Processing Result while reloading >>>>  ::"+processingResult);
    				if(processingResult.equals(OrderConstants.ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INPROGRESS_KEY)){
    					log.info("Comming to 2nd if >>>>>>>>>>>>>>>");
    					dispatchedOrders.put(odIdForDisp, txn.getOldOrder());
    				}else{
    					dispatchedOrders.put(odIdForDisp, ordsForDispDisplay.get(odIdForDisp));
    				}
				}else{
					dispatchedOrders.put(odIdForDisp, ordsForDispDisplay.get(odIdForDisp));
					
				}
        	}
        }
		return dispatchedOrders;
	}
}
