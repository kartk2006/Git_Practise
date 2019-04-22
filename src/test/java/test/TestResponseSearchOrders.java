/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tesco.mhub.order.model.ResponseOrderSummary;
import com.tesco.mhub.order.model.ResponseSearchOrders;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

/**
 * @author TE50
 *
 */
public class TestResponseSearchOrders {
	
	ResponseSearchOrders responseSearchOrders = new ResponseSearchOrders();
	ResponseOrderSummary reponseOrderSummary = new ResponseOrderSummary();
	
	/**
	 * page number.
	 */
	@Test
	public void testGetPageNumber() {
		responseSearchOrders.setPageNumber(4);
		Assert.assertEquals(4, responseSearchOrders.getPageNumber());
	}
	
	/**
	 *pageSize.
	 */
	@Test
	public void testGetPageSize() {
		responseSearchOrders.setPageSize(20);
		Assert.assertEquals(20, responseSearchOrders.getPageSize());
	}
	
	/**
	 * totalOrders.
	 */
	@Test
	public void testGetTotalNoOfOrders() {
		responseSearchOrders.setTotalOrders(80);
		Assert.assertEquals(80, responseSearchOrders.getTotalOrders());
	}
	
	/**
	 * List of orders
	 */	
	@Test
	public void testGetResponseOrderList() {
		List<ResponseSearchOrders.ResponseOrder> responseOrderList = new ArrayList<ResponseSearchOrders.ResponseOrder>();
		responseSearchOrders.setResponseOrderList(responseOrderList);
		Assert.assertEquals(responseOrderList, responseSearchOrders.getResponseOrderList());
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
