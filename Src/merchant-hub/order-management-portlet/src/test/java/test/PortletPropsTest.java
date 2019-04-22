/*
package test;

import com.tesco.mhub.order.util.PortletProps;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class PortletPropsTest {
	String key="ORDMG_E030";
	@Deployment
	public static WebArchive createDeploymentWar() {
	    File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile();
	    WebArchive war = ShrinkWrap.create(WebArchive.class, "props.war")
				.addClasses(PortletProps.class)
	            .addAsResource("portlet.properties")
	    		.addAsWebInfResource("jboss-deployment-structure.xml");
	    for (File file : libs) {
	        war.addAsLibrary(file);
	    }
	    return war;
	}
	
	@Before
	public void setUp() throws Exception {
	}


	@After
	public void tearDown() throws Exception {
	}	
	@Test
	public void testGetArray() {
		String[] stringArray = new String[]{};
		PortletProps.get(key);
	}
	
	@Test
	public void testConstructorIsPrivate() throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		
	  Constructor<PortletProps> constructor = PortletProps.class.getDeclaredConstructor();
	  Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	  constructor.setAccessible(true);
	  Object o = constructor.newInstance();
	}
}*/
