package com.tesco.mhub.order.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.tesco.mhub.order.constants.OrderConstants;

import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;

public final class PortletProps {
	

	/**
	 * Properties file logs.
	 */
	private static Log log = LogFactoryUtil.getLog(PortletProps.class);
	
	/**
	 * making a static properties object.
	 */
	private static Properties portletProperties;
		
	static {		
		portletProperties = new Properties();
		InputStream inpStream = null;
		try {
			inpStream = PortletProps.class.getClassLoader().getResourceAsStream("portlet.properties");
			portletProperties.load(inpStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(OrderConstants.ORDMG_E030 + OrderConstants.LOG_MESSAGE_SEPERATOR + PortletProps.get(OrderConstants.ORDMG_E030) + OrderConstants.LOG_MESSAGE_SEPERATOR
					+ e.getMessage());
		}
        finally{
        	if(inpStream != null){
        		try {
					inpStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
	}

	private PortletProps() {}
	
	public static String get(String key) {
		return (String) portletProperties.get(key);
	}
}
