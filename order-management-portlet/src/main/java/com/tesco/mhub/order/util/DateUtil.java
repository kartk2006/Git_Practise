package com.tesco.mhub.order.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.tesco.mhub.order.constants.OrderConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public abstract class DateUtil {
    
	/**
	 * logger object.
	 */
	private static Log log = LogFactoryUtil.getLog(ExcelUtil.class);
	
	/**
	 * df is a static variable.  
	 */
	private static DatatypeFactory df;
    static {
    	
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException dce) {
        	log.error(OrderConstants.ORDMG_E021+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E021)+OrderConstants.LOG_MESSAGE_SEPERATOR+dce.getMessage());
            throw new IllegalStateException(
                "Exception while obtaining DatatypeFactory instance", dce);
            
        }
    }  

	
	public static Date toDate(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		}
		return calendar.toGregorianCalendar().getTime();
	}

	public static Date formatDate(Date date) {
		Date convertedDate = null;
		SimpleDateFormat formatDate = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		String dateFormat = formatDate.format(date);
		try {
			if (convertedDate == null) {
				convertedDate = (Date) formatDate.parse(dateFormat);
			}
		} catch (ParseException p) {
			log.error(OrderConstants.ORDMG_E022+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E022)+OrderConstants.LOG_MESSAGE_SEPERATOR+p.getMessage());
		}
		return convertedDate;
	}
	
	public static XMLGregorianCalendar asXMLGregorianCalendar(java.util.Date date) {
        if (date == null) {
            return null;
        } else {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(date.getTime());
            return df.newXMLGregorianCalendar(gc);
        }
    }
}
