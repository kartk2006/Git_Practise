package com.tesco.mhub.order.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.tesco.mhub.order.constants.OrderConstants;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class is used to decrypt the LDAP related password.
 * @author VL45
 *
 */
 
public final class DecryptPassword{
	
	/**
	 * log object.
	 */
	private static Log log = LogFactoryUtil.getLog(DecryptPassword.class);
	
	private DecryptPassword() {
	       
	    }
	
	public static String decrypt(String encodedPass){
		 byte[] decode = null;
		  try {
			byte[] kbytes = "jaas is the way".getBytes();
			     SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");
			     BigInteger n = new BigInteger(encodedPass, 16);
			     byte[] encoding = n.toByteArray();
			       //byte[] encoding = {73, -23, 25, 118, -90, 37, -77, -64};
			     //byte[] encoding = {115, 2, -75, 66, 46, -87, 65, -97, -79, 102, 8, -91, 57, 98, -10, -68}; 
			     if (encoding.length % 8 != 0){
			        int length = encoding.length;
			        int newLength = ((length / 8) + 1) * 8;
			        int pad = newLength - length; //number of leading zeros
			        byte[] old = encoding;
			        encoding = new byte[newLength];
			        for (int i = old.length - 1; i >= 0; i--){
			           encoding[i + pad] = old[i];
			        }
			        if (n.signum() == -1){
			           for (int i = 0; i < newLength - length; i++){
			              encoding[i] = (byte) -1;
			           }
			        }
			     }
			     
			     Cipher cipher = Cipher.getInstance("Blowfish");
			     cipher.init(Cipher.DECRYPT_MODE, key);
			     decode = cipher.doFinal(encoding);
			     
			     
		} catch (Exception e) {
			log.error(OrderConstants.ORDMG_E035+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E035)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		}
		  return new String(decode);
	  }
 
 }
