package com.tesco.mhub.order.daoimpl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.tesco.mhub.gmo.managetp.carrierDetails.CarrierAttributes;
import com.tesco.mhub.gmo.sellerinviteresponse.TransactionResponse;
import com.tesco.mhub.gmo.deliveryoption.request.TradingPartner;
import com.tesco.mhub.order.ack.orders.AcknowledgeOrders;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.dao.OrderDAO;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.retrieve.order.RetrieveOrders;
import com.tesco.mhub.order.returns.refunds.ReturnRefunds;
import com.tesco.mhub.order.search.SearchOrdersResponse;
import com.tesco.mhub.order.shipment.update.ShipmentUpdates;
import com.tesco.mhub.order.util.OMResponseErrorHandler;
import com.tesco.mhub.order.util.OrderHelperUtil;
import com.tesco.mhub.order.util.PortletProps;
import com.tesco.mhub.report.feed.ProcessingReport;
import com.tesco.mhub.yodl.response.Ndxml;
import com.tesco.mhub.yodl.yodlErr.NdxmlErrRes;

import java.io.StringWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;



/**
 * @author SP02
 *
 */
public class OrderDAOImpl implements OrderDAO {
	/**
	 * yes is the property to check yodl.proxy.isExists.
	 */	
	private static final String YES = "yes";
	/**
	 * PROXY_HOST is the property to check yodl.proxy.httpServer.
	 */
	private static final String PROXY_HOST = "proxyHost";
	/**
	 *PROXY_PORT is the property to check yodl.proxy.httpPort.
	 */
	private static final String PROXY_PORT = "proxyPort";
	/**
	 * x request id is the property of headers for each request made to the web service.
	 */
	private static final String X_REQUEST_ID = "X-RequestId";
	/**
	 * x request id is the property of headers for each request made to the web service.
	 */
	private static final String X_TESCO_MESSAGE = "X-TescoMessage";
	/**
	 *Seller Id log string.
	 */
	private String sellerIdLog = "SELLER ID : ";
	/**
	 *ClassName.
	 */
	private String className = "CLASS : OrderDAOImpl, ";
	/**
	 *Method Name.
	 */
	private String methodName = " METHOD : getSelectedOrders";
	/**
	 *Time string.
	 */
	private String timeLog = " TIME : ";
	/**
	 *Method Get Yodl String.
	 */
	private String methodGetYodl = " METHOD : getYodlLabel";
	/**
	 * xmlDateFormat is a variable.
	 */
	private SimpleDateFormat xmlDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
	/**
	 *Date Format Which needs to send to service.
	 */
	private SimpleDateFormat searchDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * log is a variable.
	 */
	private Log log = LogFactoryUtil.getLog(getClass());

	private RetrieveOrders getOrders(String status, Date startDate,Date endDate, long sellerId) throws Exception{
		
		RetrieveOrders orders = null;
		String startDateTime = xmlDateFormat.format(startDate);
		String endDateTime = xmlDateFormat.format(endDate);
		
		RestTemplate restTemp = new RestTemplate();
		restTemp.setErrorHandler(OMResponseErrorHandler.getInstance());
		String retriveURI = OrderHelperUtil.getRetrieveOrdersURI(status,startDateTime, endDateTime, "" + sellerId);
		log.debug("retrive URI : " + retriveURI);
		try {
			ResponseEntity<RetrieveOrders> releasedOrdersResponse = restTemp.exchange(retriveURI, HttpMethod.GET, new HttpEntity<Object>(OrderHelperUtil.getMarketPlaceAPIAuthHeaders()),RetrieveOrders.class);
			if(restTemp.getRequestFactory() != null){
				log.debug("Proxy has been set");
			}else{
				log.debug("Proxy has not been set");
			}
			orders = releasedOrdersResponse.getBody();
			log.info("getOrders - request id : " + releasedOrdersResponse.getHeaders().getFirst(X_REQUEST_ID));
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RetrieveOrders.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
		} catch (RestClientException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E032+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E032)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error(OrderConstants.ORDMG_E001+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E001)+OrderConstants.LOG_MESSAGE_SEPERATOR+e1.getMessage());
		}
		
		return orders;
	}
	
	@Override
	public RetrieveOrders getSelectedOrders(String status,
			String[] orderIds, long sellerId) throws Exception {
		long startSec = System.currentTimeMillis();
		log.debug(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+" START ,"+className+methodName+timeLog+startSec);
		RetrieveOrders selectedOrds = null;
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(OMResponseErrorHandler.getInstance());
		String selectedOrdersUri = OrderHelperUtil.getSelectedOrdersURI(status, orderIds, sellerId);
		log.debug("selectedOrders URI : " + selectedOrdersUri);
		try {
			ResponseEntity<RetrieveOrders> selOrdersResp = restTemplate.exchange(selectedOrdersUri, HttpMethod.GET, new HttpEntity<Object>(OrderHelperUtil.getMarketPlaceAPIAuthHeaders()),RetrieveOrders.class);	
			selectedOrds = selOrdersResp.getBody();
			log.info("getSelectedOrders - request id : " + selOrdersResp.getHeaders().getFirst(X_REQUEST_ID));
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RetrieveOrders.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
		} catch (RestClientException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E032+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E032)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (Exception e2) {
			e2.printStackTrace();
			log.error(OrderConstants.ORDMG_E001+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E001)+OrderConstants.LOG_MESSAGE_SEPERATOR+e2.getMessage());
		}
		long endSec = System.currentTimeMillis();
		log.debug(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+" END ,"+className+methodName+timeLog+endSec);
		log.debug(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+" TOTAL ELAPSED"+className+methodName+" TIME in Sec : "+(endSec-startSec)/1000);
		return selectedOrds;
	}
	
	@Override
	public RetrieveOrders getSelectedOrdersForPrintDownLd(String status, Date startDate, Date endDate, String deliveryOption, String sellerId, PortletRequest portletRequest){

		RetrieveOrders selectedOrds = null;
		String startDateTime = searchDateFormat.format(startDate);
		String endDateTime = searchDateFormat.format(endDate);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(OMResponseErrorHandler.getInstance());
		String selectedOrdersUri = OrderHelperUtil.getSearchOrdersURIForPrintDownload(status, startDateTime, endDateTime, deliveryOption, sellerId);
		log.debug("selectedOrders URI : " + selectedOrdersUri);
		try {
			ResponseEntity<RetrieveOrders> selOrdersResp = restTemplate.exchange(selectedOrdersUri, HttpMethod.GET, new HttpEntity<Object>(OrderHelperUtil.getMarketPlaceAPIAuthHeaders()),RetrieveOrders.class);	
			selectedOrds = selOrdersResp.getBody();
			log.info("getSelectedOrders - request id : " + selOrdersResp.getHeaders().getFirst(X_REQUEST_ID));
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RetrieveOrders.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
		} catch (RestClientException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E032+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E032)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (Exception e2) {
			e2.printStackTrace();
			log.error(OrderConstants.ORDMG_E001+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E001)+OrderConstants.LOG_MESSAGE_SEPERATOR+e2.getMessage());
		}
		return selectedOrds;
	}

	@Override
	public RetrieveOrders getOrderDetails(String orderId, long sellerId)
			throws Exception {
		// TODO Auto-generated method stub
		RetrieveOrders selectedOrderDetails = null;
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(OMResponseErrorHandler.getInstance());
		String orderDetailsURI = OrderHelperUtil.getOrderDetailsURI(orderId, sellerId);
		log.debug("orderDetails URI : " + orderDetailsURI);
		try {
			ResponseEntity<RetrieveOrders> orderDetailsResp = restTemplate.exchange(orderDetailsURI, HttpMethod.GET, new HttpEntity<Object>(OrderHelperUtil.getMarketPlaceAPIAuthHeaders()),RetrieveOrders.class);	
			selectedOrderDetails = orderDetailsResp.getBody();
			log.debug("getOrderDetails - request id : " + orderDetailsResp.getHeaders().getFirst(X_REQUEST_ID));
		} catch (RestClientException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E032+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E032)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E011+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E011)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		}
		return selectedOrderDetails;
	}
	
	@Override
	public Object getYodlLabel(String orderId, long sellerId, TradingPartner tp, Date pickUpDate, CarrierAttributes ca, Order order)throws Exception{
		long startSec = System.currentTimeMillis();
		log.debug(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+" START ,"+className+methodGetYodl+timeLog+startSec);
		Ndxml yodlResp = null;
		NdxmlErrRes yodlErrorResp = null;
		SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		com.tesco.mhub.yodl.request.Ndxml yodlReq = OrderHelperUtil.getYodelRequest(orderId, sellerId, tp, pickUpDate, ca, order);
		log.info("##### Yodel Request for label has been completed ####");
		 
		final StringWriter result = new StringWriter();
		JAXBContext yodelReqContexts;
		yodelReqContexts = JAXBContext.newInstance(com.tesco.mhub.yodl.request.Ndxml.class);
		Marshaller yodelRequestMarshler = yodelReqContexts.createMarshaller();
		yodelRequestMarshler.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		yodelRequestMarshler.marshal(yodlReq, result);
		log.info("Yodel Request for label: " +result.toString());
		 
		Proxy proxy = null;
		if(YES.equalsIgnoreCase(PropsUtil.get(OrderConstants.PROXY_EXISTS))){
			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(InetAddress.getByName(String.valueOf(PropsUtil.get(OrderConstants.SERVER_PROXY))), Integer.valueOf(PropsUtil.get(OrderConstants.SERVER_PORT))));
			clientHttpRequestFactory.setProxy(proxy);
		}
		/*try{*/
			final String yodelUri = OrderConstants.YODL_LABEL_REQUEST;
			RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
			if(restTemplate.getRequestFactory() != null){
				log.info("Proxy has been set");
			}else{
				log.info("Proxy has not been set");
			}
			log.info("getYodelLabel URL : "+yodelUri);
			yodlResp = restTemplate.postForObject(yodelUri, yodlReq, Ndxml.class);
			
			if("ERROR".equalsIgnoreCase(yodlResp.getResponse().getStatus().getCode())){
					try{
						yodlErrorResp = restTemplate.postForObject(yodelUri, yodlReq, NdxmlErrRes.class);
						
						/*if(YES.equalsIgnoreCase(PropsUtil.get(OrderConstants.PROXY_EXISTS))){
							System.getProperties().remove(PROXY_HOST);
							System.getProperties().remove(PROXY_PORT);
						}*/
					}catch(Exception e){
						log.error("Yodel Service may be down while getting UK bank holidays date!!!");
						e.printStackTrace();
					}/*finally{
						if(YES.equalsIgnoreCase(PropsUtil.get(OrderConstants.PROXY_EXISTS))){
							System.getProperties().remove(PROXY_HOST);
							System.getProperties().remove(PROXY_PORT);
						}
					}*/
					long endSec = System.currentTimeMillis();
					log.info(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+" END ,"+className+methodGetYodl+timeLog+endSec);
					log.info(sellerIdLog+sellerId+StringPool.SPACE+StringPool.COMMA+" TOTAL ELAPSED"+className+methodGetYodl+" TIME in Sec : "+(endSec-startSec)/1000);
				return yodlErrorResp;
			}
			/*if(YES.equalsIgnoreCase(PropsUtil.get(OrderConstants.PROXY_EXISTS))){
				System.getProperties().remove(PROXY_HOST);
				System.getProperties().remove(PROXY_PORT);
			}*/
	/*	}catch(Exception e){
			log.info("Yodel Service may be down!!!");
			e.printStackTrace();
		}*/
		return yodlResp;
	}
	
	
	@Override
	public SearchOrdersResponse getSearchResult(String status, Date startDate,
			Date endDate, String filterDateBy, String sortBy,
			String deliveryOption, String customerName, long sellerId,int pageNumber)
			throws Exception {
		// TODO Auto-generated method stub
		SearchOrdersResponse searchOrdersResponse = null;
		
		String startDateTime = searchDateFormat.format(startDate);
		String endDateTime = searchDateFormat.format(endDate);
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(OMResponseErrorHandler.getInstance());
		String searchURI = OrderHelperUtil.getSearchOrdersURI(status, startDateTime, endDateTime, filterDateBy, sortBy, deliveryOption, customerName, StringUtil.valueOf(sellerId),StringUtil.valueOf(pageNumber));
		log.debug("search URI : " + searchURI);
		try {
		ResponseEntity<SearchOrdersResponse> searchOrdersRespEntity = restTemplate.exchange(searchURI, HttpMethod.GET, new HttpEntity<Object>(OrderHelperUtil.getMarketPlaceAPIAuthHeaders()),SearchOrdersResponse.class);
		searchOrdersResponse = searchOrdersRespEntity.getBody();
		log.info("total number of Orders : " + searchOrdersResponse.getTotalNumberOfOrders());
		log.info("getSearch result - request id : " + searchOrdersRespEntity.getHeaders().getFirst(X_REQUEST_ID));
		} catch (RestClientException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E032+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E032)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (Exception e) {
			log.error(OrderConstants.ORDMG_E031+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E031)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		}
		return searchOrdersResponse;
	}
	
	
	@Override
	public RetrieveOrders getReleasedOrders(Date startDate, Date endDate,long sellerId) throws Exception{
       
		/*File file = new File("D://orders//order.xml");

		JAXBContext jaxbContext = JAXBContext.newInstance(RetrieveOrders.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		RetrieveOrders releasedOrders = (RetrieveOrders) jaxbUnmarshaller.unmarshal(file);
		
		return releasedOrders;*/

		return getOrders(OrderConstants.RETRIEVE_ORDER_STATUS_RELEASED, startDate,endDate, sellerId);
	}

	public ProcessingReport getTransactionProcessingReport(String transactionId) throws Exception{
		
		ProcessingReport processingReport = null;
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(OMResponseErrorHandler.getInstance());
		String uri = OrderHelperUtil.getProcessingReportURI(transactionId);
		log.debug("processing reprt URI :" + uri);
		try {
			ResponseEntity<ProcessingReport> transactionOrdersResp = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<Object>(OrderHelperUtil.getMarketPlaceAPIAuthHeaders()),ProcessingReport.class);	
			processingReport = transactionOrdersResp.getBody();
			log.info("getTransactionProcessingReport - request id : " + transactionOrdersResp.getHeaders().getFirst(X_REQUEST_ID));
		} catch (RestClientException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E032+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E032)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (Exception e) {
			log.error(OrderConstants.ORDMG_E002+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E002)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		}
		
		return processingReport;
	}

	@Override
	public RetrieveOrders getDispatchedOrders(Date startDate, Date endDate,long sellerId) throws Exception{
		
		return getOrders(OrderConstants.RETRIEVE_ORDER_STATUS_DISPATCHED, startDate,endDate, sellerId);
	}
	
	@Override
	public String cancelOrders(AcknowledgeOrders acknowledgeOrders, long sellerId) throws Exception {
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(OMResponseErrorHandler.getInstance());
		String uri = OrderHelperUtil.getAcknowledgeOrdersURI(sellerId);	
		ResponseEntity<String> acknowledgeOrdersResp = null;
		/*log.info("RestTemplate object created.......");	*/	
		String acknowledgeOrdersXML = null;
		try {
			acknowledgeOrdersXML = getAcknowledgeOrdersXML(acknowledgeOrders);
		} catch (PropertyException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E033+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E033)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (JAXBException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E034+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E034)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		}
		
		log.debug("Ack Order Xml ::"+acknowledgeOrdersXML);
		HttpHeaders headers = getHeaders();
		HttpEntity bodyAndHeaders = new HttpEntity(acknowledgeOrdersXML, headers);
		
		String response = null;
		try {
		acknowledgeOrdersResp = restTemplate.exchange(uri, HttpMethod.POST, bodyAndHeaders, String.class);
			if(Validator.isNotNull(acknowledgeOrdersResp)){
				response = acknowledgeOrdersResp.getBody();
				log.info("acknowledgeOrdersResp is not null");
			}
			if(Validator.isNotNull(acknowledgeOrdersResp.getHeaders().getFirst(X_REQUEST_ID))){
				log.info("cancelOrders - request id : " + acknowledgeOrdersResp.getHeaders().getFirst(X_REQUEST_ID));
			}
			log.debug("OrderDaoImpl - cancel orders - Transaction ID - " + response);
		} catch (RestClientException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E032+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E032)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch(Exception e1){
			log.error("cancel orders resulted in exceptn"  + e1);
			if (Validator.isNotNull(acknowledgeOrdersResp.getHeaders().getFirst(X_TESCO_MESSAGE)) && (!acknowledgeOrdersResp.getHeaders().getFirst(X_TESCO_MESSAGE).equals(StringPool.BLANK))){
				log.error("cancelOrders - x - tesco mesage  : " + acknowledgeOrdersResp.getHeaders().getFirst(X_TESCO_MESSAGE));
			}
			throw e1;
		}
		
		return response;
	}

	
	@Override
	public String dispatchOrders(ShipmentUpdates shipmentUpdates, long sellerId) throws Exception {
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(OMResponseErrorHandler.getInstance());
		String uri = OrderHelperUtil.getShipmentUpdatesOrdersURI(sellerId);
		String shipmentOrdersXML = null;
		/*log.info("RestTemplate Object created for Dispatch Orders.....");*/		
		try {
			shipmentOrdersXML = getShipmentOrdersXML(shipmentUpdates);
		} catch (PropertyException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E033+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E033)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (JAXBException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E034+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E034)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		}
		
		log.debug("ShipMent Update Xml ::"+shipmentOrdersXML);
		//Changing Content-Type as OrderAPI accepts text/plain and not application/xml
	    HttpHeaders headers = getHeaders();
		HttpEntity bodyAndHeaders = new HttpEntity(shipmentOrdersXML, headers);
				
		String response = null;
		
		ResponseEntity<String> dispatchOrdersResp = null;
		try {
			dispatchOrdersResp = restTemplate.exchange(uri, HttpMethod.POST, bodyAndHeaders, String.class);
			if(Validator.isNotNull(dispatchOrdersResp)){
				response = dispatchOrdersResp.getBody();
				log.info("dispatchOrders - request id : " + dispatchOrdersResp.getHeaders().getFirst(X_REQUEST_ID));
			}
			log.debug("OrderDaoImpl - dispatch orders - Transaction ID - " + response);
		} catch (RestClientException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E032+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E032)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("exception thrown by dispatch orders method - "  + e);
			log.error("dispatchOrders - x - tesco mesage  : " + dispatchOrdersResp.getHeaders().getFirst(X_TESCO_MESSAGE));
			if (Validator.isNotNull(dispatchOrdersResp.getHeaders().getFirst(X_TESCO_MESSAGE)) && (!dispatchOrdersResp.getHeaders().getFirst(X_TESCO_MESSAGE).equals(StringPool.BLANK))){
				log.error("dispatchOrders - x - tesco mesage  : " + dispatchOrdersResp.getHeaders().getFirst(X_TESCO_MESSAGE));
			}
			throw e;
		}
		
		
		
		return response;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = OrderHelperUtil.getMarketPlaceAPIAuthHeaders();
	    headers.remove(OrderConstants.HTTP_SERVICE_CONTENT_TYPE);
		headers.add(OrderConstants.HTTP_SERVICE_CONTENT_TYPE, OrderConstants.HTTP_SERVICE_CONTENT);
		return headers;
	}

	@Override
	public String returnOrders(ReturnRefunds returnRefunds, long sellerId) throws Exception {
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(OMResponseErrorHandler.getInstance());
		String uri = OrderHelperUtil.getReturnRefundsOrderURI(sellerId);
		String returnRefundsOrderXML = null;
		/*log.info("RestTemplate Object created for Dispatch Orders.....");*/		
		try { 
			returnRefundsOrderXML = getReturnRefundsOrdersXML(returnRefunds);
		} catch (PropertyException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E033+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E033)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (JAXBException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E034+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E034)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		}
		
		log.debug("Return Refunds Xml ::"+returnRefundsOrderXML);
		HttpHeaders headers = getHeaders();
		HttpEntity bodyAndHeaders = new HttpEntity(returnRefundsOrderXML, headers);
				
		String response = null;
		
		ResponseEntity<String> returnRefundsOrdersResp = null;
		try {
			returnRefundsOrdersResp = restTemplate.exchange(uri, HttpMethod.POST, bodyAndHeaders, String.class);
			if(Validator.isNotNull(returnRefundsOrdersResp)){
				log.info("returnOrders - request id : " + returnRefundsOrdersResp.getHeaders().getFirst(X_REQUEST_ID));
				response = returnRefundsOrdersResp.getBody();
			}
			log.debug("OrderDaoImpl - return orders - Transaction ID - " + response);
		} catch (RestClientException e) {
			e.printStackTrace();
			log.error(OrderConstants.ORDMG_E032+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E032)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (Exception e) {
			log.error("return orders caused an exception - "  + e);
			//e.printStackTrace();
			if (Validator.isNotNull(returnRefundsOrdersResp.getHeaders().getFirst(X_TESCO_MESSAGE)) && (!returnRefundsOrdersResp.getHeaders().getFirst(X_TESCO_MESSAGE).equals(StringPool.BLANK))){
				log.error("returnOrders - x - tesco mesage  : " + returnRefundsOrdersResp.getHeaders().getFirst(X_TESCO_MESSAGE));
			}
			throw e;
		}		
		return response;
	}
	
	private String getAcknowledgeOrdersXML(AcknowledgeOrders acknowledgeOrders) throws JAXBException, PropertyException {
		
		String acknowledgeOrdersXML;
		JAXBContext jaxbContexts = JAXBContext.newInstance(AcknowledgeOrders.class);
		Marshaller jaxbMarshallers = jaxbContexts.createMarshaller();
		
		jaxbMarshallers.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter writer = new StringWriter();
		jaxbMarshallers.marshal(acknowledgeOrders, writer);
		acknowledgeOrdersXML = writer.toString();
		return acknowledgeOrdersXML;
	}
	
	
	private String getShipmentOrdersXML(ShipmentUpdates shipmentUpdates) throws JAXBException, PropertyException {
		
		String shipmentOrdersXML;
		JAXBContext jaxbContext = JAXBContext.newInstance(ShipmentUpdates.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		StringWriter writer = new StringWriter();
		jaxbMarshaller.marshal(shipmentUpdates, writer);
		shipmentOrdersXML = writer.toString();
		
		return shipmentOrdersXML;
	}
	
	private String getReturnRefundsOrdersXML(ReturnRefunds returnRefunds) throws JAXBException, PropertyException {
		
		String returnRefundsOrdersXML;
		JAXBContext jaxbContextss = JAXBContext.newInstance(ReturnRefunds.class);
		Marshaller jaxbMarshallerss = jaxbContextss.createMarshaller();
		jaxbMarshallerss.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		StringWriter writer = new StringWriter();
		jaxbMarshallerss.marshal(returnRefunds, writer);
		returnRefundsOrdersXML = writer.toString();
		return returnRefundsOrdersXML;
	}
	
	@Override
	public TradingPartner getSellerById(long tradingPartnerId) throws Exception{
			
			String uri = PropsUtil.get("calling.seller.deliveryoption.byid.v2.url") + tradingPartnerId;
			
	 		RestTemplate template = new RestTemplate();
	 		template.setErrorHandler(OMResponseErrorHandler.getInstance());
	 		
	 		TradingPartner tradingPartner = null;
	 		TransactionResponse transactionResponse = null;
			try {
				tradingPartner = template.getForObject(uri, TradingPartner.class);
			} catch (Exception e) {
				log.error(OrderConstants.ORDMG_E036+ OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E036)+ OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
				tradingPartner = new TradingPartner();
				try {
					transactionResponse = template.getForObject(uri, TransactionResponse.class);
					tradingPartner.setId(transactionResponse.getOrgId());
					tradingPartner.setStatus(transactionResponse.getStatus());
					transactionResponse = null;
				} catch (Exception ex) {
					log.error(OrderConstants.ORDMG_E038+ OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E039)+ OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
					tradingPartner.setStatus("Connection Exception:"+ex.getMessage());
				}
			}
			return tradingPartner;
	}
	
	@Override
	 public CarrierAttributes getCarrierAttributesById(long sellerId) throws Exception {
		String uri = PropsUtil.get("calling.seller.deliveryoption.byid.v2.url")+sellerId+"/carrierattributes";
		RestTemplate template = new RestTemplate();
		template.setErrorHandler(OMResponseErrorHandler.getInstance());
		CarrierAttributes carrierAttributes = null;
		try {
			carrierAttributes = template.getForObject(uri, CarrierAttributes.class);
		}catch (Exception e) {
			log.error(OrderConstants.ORDMG_E042+ OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
			String txResponse  = getTxResponseInCaseOfException(uri, template);
			throw new Exception(txResponse);
		}
		return carrierAttributes;
		
	 }
	
private String getTxResponseInCaseOfException(String uri, RestTemplate restTemplate) {
		
		TransactionResponse txResponse = null;
		String statusDescription = null;
		try {
			txResponse = restTemplate.getForObject(uri,  TransactionResponse.class);
			
			statusDescription = txResponse.getError().getValue().getErrorMessage().get(0).getStatusDescription();
			
		} catch (Exception e) {
			log.error(OrderConstants.ORDMG_E043+ OrderConstants.LOG_MESSAGE_SEPERATOR+statusDescription);
		}
		return statusDescription;
	}
}
