package com.tesco.mhub.order.serviceimpl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PortalUtil;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.model.OrderLineItem;
import com.tesco.mhub.order.model.OrderBasket;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.service.OrderBasketService;
import com.tesco.mhub.order.util.OrderHelperUtil;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

public class OrderBasketServiceImpl implements OrderBasketService {

	/**
	 * log object.
	 */
	private Log log = LogFactoryUtil.getLog(getClass());
	
	@Override
	public OrderBasket getOrderBasket(PortletRequest portletRequest) {
		//TODO Overlay the status of orders retrieved with what is present in order basket content
		OrderBasket orderBasket = null;
		PortletSession portletSession = portletRequest.getPortletSession();
		
//		orderBasket = (OrderBasket) portletSession.getAttribute(OrderConstants.ORDER_BASKET, PortletSession.APPLICATION_SCOPE);

		/*** R4 ***/
		Map<Long,OrderBasket> orderBasketMapFromSession = (Map<Long, OrderBasket>) portletSession.getAttribute(OrderConstants.ORDER_BASKET, PortletSession.APPLICATION_SCOPE);
		long userIdInSession = 0;
		
		if(orderBasketMapFromSession == null) {
			orderBasket = createNewOrderBasket(portletRequest);
		} else {
			for(Map.Entry<Long, OrderBasket> entry:orderBasketMapFromSession.entrySet()) {
				userIdInSession = entry.getKey();
				orderBasket = entry.getValue();
			}
			if(userIdInSession != getCurrentUserId(portletRequest)) {
				orderBasket = createNewOrderBasket(portletRequest);
			} else {
				orderBasket = orderBasketMapFromSession.get(getCurrentUserId(portletRequest));
			}
		}
		return orderBasket;
	}

	private OrderBasket createNewOrderBasket(PortletRequest portletRequest) {
		PortletSession portletSession = portletRequest.getPortletSession();
		
		OrderBasket orderBasket = new OrderBasket();
		Map<Long,OrderBasket> orderBasketMap = new HashMap<Long, OrderBasket>();
		orderBasketMap.put(getCurrentUserId(portletRequest), orderBasket);
		
//			portletSession.setAttribute(OrderConstants.ORDER_BASKET,orderBasket, PortletSession.APPLICATION_SCOPE);
		portletSession.setAttribute(OrderConstants.ORDER_BASKET,orderBasketMap, PortletSession.APPLICATION_SCOPE);
		return orderBasket;
	}

	private long getCurrentUserId(PortletRequest portletRequest) {
		long currUserId = PortalUtil.getUserId(portletRequest);
		return currUserId;
	}
	
	public void setOrderBasket(OrderBasket orderBasket, PortletRequest portletRequest) {
		PortletSession portletSession = portletRequest.getPortletSession();
		//portletSession.setAttribute(OrderConstants.ORDER_BASKET,orderBasket, PortletSession.APPLICATION_SCOPE);
		Map<Long,OrderBasket> orderBasketMap = new HashMap<Long, OrderBasket>();
		orderBasketMap.put(getCurrentUserId(portletRequest), orderBasket);
		
		portletSession.setAttribute(OrderConstants.ORDER_BASKET,orderBasketMap, PortletSession.APPLICATION_SCOPE);
	}
	
	
	
	public OrderBasket mergeOrders(OrderBasket basket,Map<String,Order> releasedOrdersForDisplay,PortletRequest portletRequest){
		
		List<Order> pendingOrder = basket.getOrdersForUpdate();
		
		for (String ordId : releasedOrdersForDisplay.keySet()){				
			
			Order ordersFromService = releasedOrdersForDisplay.get(ordId);	
			
			/*for(String pendingOrdId : pendingOrder.keySet()){
				
				Order orderFromBasket = pendingOrder.get(pendingOrdId);
				if(ordersFromService.getOrderStatus().equals(orderFromBasket.getOrderStatus())){
					pendingOrder.put(ordId, ordersFromService);
				}
			}*/
		}
      // setOrderBasket(pendingOrders, portletRequest);
	return basket;		
	}

	/**	 * 
	 * Either released or dispatched orders of which status need to be updated are passed to this method.
	 * it will compare the pending orders in basket i.e those which are awaiting changes to be udpated in OrderAPI.
	 * 
	 * @param ordersMapFromBasket pending orders in basket
	 * @param ordersMap released or dispatched orders
	 * @param status
	 */
	@Override
	public void updateStatusInOrderBasket(List<Order> ordersMapFromBasket, Map<String, Order> ordersMap, String status) {		

			if(OrderHelperUtil.isValidList(ordersMapFromBasket) && OrderHelperUtil.isValidMap(ordersMap)){	
				for (Order ordersInBasket  : ordersMapFromBasket){				
					Map<Integer, OrderLineItem> orderLineItemDetailsMapInBasket = ordersInBasket.getOrderLineItems();
					
					if(OrderHelperUtil.isValidMap(orderLineItemDetailsMapInBasket)){				
						for (Integer lineItemId : orderLineItemDetailsMapInBasket.keySet()){
							OrderLineItem orderLineItemFromBasket = orderLineItemDetailsMapInBasket.get(lineItemId);
							orderLineItemFromBasket.setLineItemStatus(status);			
						}
					} else {
						log.debug("Order Details of orderId: "+ordersInBasket.getOrderId()+" is not available, hence this order status not updated for this order.");
					}
					
					//Update only that order in released/dispatched orders map which is there in pending orders
					if(ordersInBasket != null){
						Map<Integer, OrderLineItem> orderLineItemDetailsMap = ordersInBasket.getOrderLineItems();
						
						if(OrderHelperUtil.isValidMap(orderLineItemDetailsMap)){				
							for (Integer orderLineItemId : orderLineItemDetailsMap.keySet()){
								OrderLineItem orderLineItem = orderLineItemDetailsMap.get(orderLineItemId);
								orderLineItem.setLineItemStatus(status);			
							}
						} else {
							log.debug("Order Details of orderId: "+ordersInBasket.getOrderId()+" is not available, hence this order status not updated for this order.");
						}
					}
				}
			} else {
				log.debug("Orders Map from OrderBasket is not available, hence Order statuses not updated");
			}
		}
	}

