package test;

import com.tesco.mhub.order.service.OrderBasketServiceFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderBasketServiceFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorIsPrivate() throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		
	  Constructor<OrderBasketServiceFactory> constructor = OrderBasketServiceFactory.class.getDeclaredConstructor();
	  Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	  constructor.setAccessible(true);
	  Object o = constructor.newInstance();
	}
	
	@Test
	public void testGetOrderBasketService(){
		OrderBasketServiceFactory.getOrderBasketService();
	}

}
