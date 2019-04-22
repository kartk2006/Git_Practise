package test;

import com.liferay.portal.kernel.util.PropsUtil;
import com.tesco.mhub.order.util.PortletProps;
import com.tesco.mhub.order.constants.OrderConstants;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class OrderConstantsTest {
	
	
	@Deployment
	public static JavaArchive createDeployment() {

		// adding empty beans.xml is required as Arquillian uses CDI

		JavaArchive appDeployable = ShrinkWrap
				.create(JavaArchive.class, "OrderConstantsTest.jar")
				.addClass(OrderConstants.class)
				.addClass(PortletProps.class)
				.addClass(PropsUtil.class)
				.addAsResource("portlet.properties")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				// this is needed to make the test jar use Liferay portal
				// depndencies
				.addAsManifestResource("jboss-deployment-structure.xml");

		return appDeployable;
	}

	@Test
	public void test() {
		OrderConstants OrderConstants = new OrderConstants() {
		};
	}

}
