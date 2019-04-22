package test;

import static org.junit.Assert.assertEquals;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.tesco.mhub.order.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateUtilTest {

	/**
	 * logger object.
	 */
	private Log loggs = LogFactoryUtil.getLog(getClass());
	private static DatatypeFactory df;
	DateUtil dateUtil = new DateUtil() {
	};
	static {
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException dce) {
            throw new IllegalStateException(
                "Exception while obtaining DatatypeFactory instance", dce);
        }
    }  
	@Before
	public void setUp() throws Exception {
		loggs.info("comming to setUp().....");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
		loggs.info("comming to tearDown().....");
	}
	@Test
	public void testToDate() throws DatatypeConfigurationException {
		Date date = new Date();
		date.setDate(17/04/2014);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		Date dt = DateUtil.toDate(date2);
		assertEquals(date, dt);
		
		XMLGregorianCalendar calendar = null;
		Assert.assertNull(DateUtil.toDate(calendar));
		
	}
		
	@Test
	public void testFormatDate() {
		Date convertedDate = null;
		Date date = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat(
				"yyyy-MM-dd'T'hh:mm:ss");
		String dateFormat = formatDate.format(date);
		try {
			if (convertedDate == null) {
				convertedDate = (Date) formatDate.parse(dateFormat);
			}
		} catch (ParseException p) {
			p.printStackTrace();
		}
		Date actualDate = DateUtil.formatDate(date);
		Assert.assertNotNull(actualDate);
	}
	
	@Test
	public void asXMLGregorianCalendar() {
		Date date = new Date();
		XMLGregorianCalendar acutualxMLGregorianCalendar;
		XMLGregorianCalendar xMLGregorianCalendar  = null;
        if (date == null) {
        	acutualxMLGregorianCalendar = null;
        } else {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(date.getTime());
            acutualxMLGregorianCalendar = df.newXMLGregorianCalendar(gc);
            //xMLGregorianCalendar  = DateUtil.asXMLGregorianCalendar(date);
        }
        xMLGregorianCalendar  = DateUtil.asXMLGregorianCalendar(date);
        Assert.assertNotNull(xMLGregorianCalendar);
        date = null;
        Assert.assertNull(DateUtil.asXMLGregorianCalendar(date));
	}

}
