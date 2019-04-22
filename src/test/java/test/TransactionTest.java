package test;

import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.Transaction;

import org.junit.Test;

import junit.framework.Assert;


public class TransactionTest {
	
	Transaction transaction = new Transaction();
	
	@Test
	public void testGetOrderId() {
		
		transaction.setOrderId("123");
		Assert.assertEquals("123", transaction.getOrderId());
	}

	
	@Test
	public void testGetTransactionId() {
		
		transaction.setTransactionId("123");
		Assert.assertEquals("123", transaction.getTransactionId());
	}

	
	@Test
	public void testGetTransactionType() {
		
		transaction.setTransactionType("type");
		Assert.assertEquals("type", transaction.getTransactionType());
	}

	
	@Test
	public void testGetTransactionStatus() {
		
		transaction.setTransactionStatus("status");
		Assert.assertEquals("status", transaction.getTransactionStatus());
	}

	
	@Test
	public void testGetOldOrder() {
		
		Order oldOrder = new Order();;
		transaction.setOldOrder(oldOrder );
		transaction.getOldOrder();
	}
	
	@Test
	public void testToString(){
		transaction.toString();
	}

	
}
