package com.tesco.mhub.order.service;

import com.tesco.mhub.order.serviceimpl.OrderBasketServiceImpl;

public final class OrderBasketServiceFactory {

	/**
	 * OrderBasketService is a constant.
	 */
	private static final OrderBasketService INSTANCE = new OrderBasketServiceImpl(); 
	
	private OrderBasketServiceFactory() {

	}
	
	public static OrderBasketService getOrderBasketService(){
		return INSTANCE;
	}
	
}
