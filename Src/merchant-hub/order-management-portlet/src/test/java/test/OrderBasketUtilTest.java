package test;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderBasket;
import com.tesco.mhub.order.model.Transaction;
import com.tesco.mhub.order.util.OrderBasketUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class OrderBasketUtilTest {
	/**
	 * log object.
	 */
	private Log log = LogFactoryUtil.getLog(getClass());
	OrderBasketUtil orderBasketUtil = new OrderBasketUtil() {
	};
	@Test
	public void testGetOrderBasket() {
		OrderBasket actualOrderBasket = new OrderBasket();
		OrderBasket expectedOrderBasket = orderBasketUtil.getOrderBasket();
		Assert.assertNotNull(expectedOrderBasket);

	}
	
	@Test
	public void testMergeOrders(){
		
		Map<String, Order> ordersForDisplay = new HashMap<String, Order>();
		Order order = new Order();
		order.setOrderId("123");
		ordersForDisplay.put("1", order);
		List<Order> ordersFromBasket = new ArrayList<Order>();
		ordersFromBasket.add(order );
		Assert.assertNotNull(orderBasketUtil.mergeOrders(ordersForDisplay, ordersFromBasket));
	}
	
	@Test
	public void testMergeReleasedOrders() {
		
		Order order = new Order();
		order.setOrderId("123");
		Map<String, Order> ordsForDisplay = new HashMap<String, Order>();
		ordsForDisplay.put("1", order);
		
		Transaction transaction = new Transaction();
		transaction.setTransactionId("123");
		Map<String, Transaction> ordersFromTransactionBasket = new HashMap<String, Transaction>();
		ordersFromTransactionBasket.put("1", transaction);
		try {
			Assert.assertNotNull(orderBasketUtil.mergeReleasedOrders(ordsForDisplay, ordersFromTransactionBasket));
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
	}
	
	@Test
	public void testMergeDispatchedOrders(){
		
		Map<String, Order> ordsForDispDisplay = new HashMap<String, Order>();
		Order order = new Order();
		order.setOrderId("123");
		ordsForDispDisplay.put("1", order);
		Map<String, Transaction> ordersFromTransactionBasketDisp = new HashMap<String, Transaction>();
		Transaction transaction = new Transaction();
		transaction.setTransactionId("123");
		ordersFromTransactionBasketDisp.put("1", transaction);
		try {
			Assert.assertNotNull(orderBasketUtil.mergeDispatchedOrders(ordsForDispDisplay, ordersFromTransactionBasketDisp));
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
}
