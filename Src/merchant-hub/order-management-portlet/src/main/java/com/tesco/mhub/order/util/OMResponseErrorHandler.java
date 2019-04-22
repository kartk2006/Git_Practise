package com.tesco.mhub.order.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public final class OMResponseErrorHandler implements ResponseErrorHandler {

	/**
	 * x request id is the property of headers for each request made to the web service.
	 */
	private static final String X_REQUEST_ID = "X-RequestId";
	/**
	 * x request id is the property of headers for each request made to the web service.
	 */
	private static final String X_TESCO_MESSAGE = "X-TescoMessage";

	/**
	 * logger is the property log object to be used.
	 * 
	 */
	private static final Log LOGGER = LogFactoryUtil.getLog(OMResponseErrorHandler.class);

	/**
	 * creating new instance of errorHnadler class.
	 */
	private static OMResponseErrorHandler instance = new OMResponseErrorHandler();

	private OMResponseErrorHandler() {
		// TODO Auto-generated constructor stub
	}

	public static OMResponseErrorHandler getInstance() {
		return instance;
	}

	@Override
	public void handleError(ClientHttpResponse clienthttpresponse)
			throws IOException {

		if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
			LOGGER.info(HttpStatus.FORBIDDEN+ " response. Throwing authentication exception");
		}

	}

	@Override
	public boolean hasError(ClientHttpResponse clienthttpresponse)
			throws IOException {
		
		
		LOGGER.debug("Response Status text :: " + clienthttpresponse.getStatusText());
		LOGGER.debug("Status code :: " + clienthttpresponse.getStatusCode());
		HttpHeaders headers = clienthttpresponse.getHeaders();
		LOGGER.debug("RequestId ::" + headers.get(X_REQUEST_ID));
		
		if (clienthttpresponse.getStatusCode() == HttpStatus.OK) {
			LOGGER.debug("service response ::" + clienthttpresponse.getBody());
		} else {
		
			LOGGER.debug("X_TESCO_MESSAGE ::" + headers.get(X_TESCO_MESSAGE));
			
			if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
				LOGGER.error("Call returned a error 403 forbidden resposne ");
				return true;
			}

		}
		return false;
	}

}

