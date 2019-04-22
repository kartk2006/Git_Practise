package com.tesco.mhub.order.service;

import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderBasket;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

public interface OrderBasketService {

	OrderBasket getOrderBasket(PortletRequest portletRequest);
	void setOrderBasket(OrderBasket orderBasket, PortletRequest portletRequest);
	void updateStatusInOrderBasket(List<Order> ordersMapFromBasket, Map<String, Order> ordersMap, String orderStatusPending);
}
