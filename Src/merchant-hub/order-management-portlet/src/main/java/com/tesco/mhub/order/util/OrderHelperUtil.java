package com.tesco.mhub.order.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.tesco.mhub.gmo.deliveryoption.request.PartnerAccountInformation;
import com.tesco.mhub.gmo.deliveryoption.request.PartnerContact;
import com.tesco.mhub.gmo.deliveryoption.request.TradingPartner;
import com.tesco.mhub.gmo.managetp.carrierDetails.CarrierAttributes;
import com.tesco.mhub.gmo.managetp.carrierDetails.CarrierDetails;
import com.tesco.mhub.gmo.managetp.carrierDetails.WarehouseAddress;
import com.tesco.mhub.model.OrderLabelDetails;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.model.ItemDetails;
import com.tesco.mhub.order.model.ItemInfo;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderBasket;
import com.tesco.mhub.order.model.OrderLineItem;
import com.tesco.mhub.order.model.OrderPackageDetails;
import com.tesco.mhub.order.model.ParcelDetails;
import com.tesco.mhub.order.model.ParcelItemInfo;
import com.tesco.mhub.order.model.ParcelJson;
import com.tesco.mhub.order.retrieve.order.RetrieveOrders;
import com.tesco.mhub.order.retrieve.order.Shipment;
import com.tesco.mhub.order.shipment.update.AddressDetails;
import com.tesco.mhub.order.shipment.update.ShipmentLine;
import com.tesco.mhub.order.shipment.update.ShipmentLines;
import com.tesco.mhub.order.shipment.update.ShipmentUpdate;
import com.tesco.mhub.order.shipment.update.Shipments;
import com.tesco.mhub.service.OrderLabelDetailsLocalServiceUtil;
import com.tesco.mhub.yodl.request.Ndxml;
import com.tesco.mhub.yodl.request.Ndxml.Credentials;
import com.tesco.mhub.yodl.request.Ndxml.Request;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job.Account;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job.International;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job.PickupDateTime;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job.Segment;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job.Segment.Address;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job.Segment.Address.Country;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job.Segment.Contact;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job.Segment.Contact.Telephone;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job.Segment.NonDocs;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job.Segment.OrderDateTime;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job.Service;
import com.tesco.mhub.yodl.request.Ndxml.Request.Job.Tariff;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.http.HttpHeaders;

public abstract class OrderHelperUtil {
	/**
	 * Order in Released status.
	 */
	private static String orderReleased = "released";
	/**
	 * Assign Status.
	 */
	private static String assignStatus = "status=";
	/**
	 * Assign startDateTime.
	 */
	private static String assignStrtDtTime = "startDateTime=";
	/**
	 * Assign endDateTime.
	 */
	private static String assignEndDtTime = "endDateTime=";
	/**
	 * Assign userid.
	 */
	private static String assignUserId = "userid=";
	/**
	 * Simple Date format pattern.
	 */
	private static String dateSplitter = "yyyy-MM-dd";
	/**
	 * Simple time format pattern.
	 */
	private static String timeSplitter = "HH:mm:ss";
	/**
	 * http Proxy Host.
	 */
	private static String httpProxyHost = "http.proxyHost";
	/**
	 * log is a variable.
	 */
	private static Log log = LogFactoryUtil.getLog(OrderHelperUtil.class);
	
	
	
	public static String getRetrieveOrdersURI(String status,
			String startDateTime, String endDateTime, String usrId) {
		
		StringBuilder sb = getOrderBaseURI();
		sb.append(OrderConstants.RETRIEVE_ORDER_FUNCTION_NAME);
		sb.append(StringPool.QUESTION);
		//This condition is added to fix IM #IM5434962.
		//This issue includes displaying Open and Acknowledged orders when Open tab is selected
		if((orderReleased).equalsIgnoreCase(status)){
			status = OrderConstants.RETRIEVE_ORDER_STATUS_RELEASED;
		}
		sb.append(assignStatus);
		sb.append(status);
		sb.append(StringPool.AMPERSAND);
		sb.append(assignStrtDtTime);
		sb.append(startDateTime);
		sb.append(StringPool.AMPERSAND);
		sb.append(assignEndDtTime);
		sb.append(endDateTime);
		sb.append("&userid=");
		sb.append(usrId);

		return sb.toString();
	}
	
	/**
	 * Constructing URI for retrieving only selected orders.
	 * @param status
	 * @param orderIds
	 * @param sellrId
	 * @return
	 */
	public static String getSelectedOrdersURI(String status,
			String[] orderIds, long sellrId) {
		
		StringBuilder sb = getOrderBaseURI();
		sb.append(OrderConstants.RETRIEVE_ORDER_FUNCTION_NAME);
		sb.append(StringPool.QUESTION);
		//Commenting below lines since status is not required for retrieving based on selected Order ID not on status.
		/*if((orderReleased).equalsIgnoreCase(status)){
			status = OrderConstants.RETRIEVE_ORDER_STATUS_RELEASED;
		}
		sb.append("status"+StringPool.EQUAL+status+StringPool.AMPERSAND);*/

		StringBuilder orderIdsString = new StringBuilder();
		for(int i=0; i<orderIds.length; i++){
			if(orderIds[i].contains(StringPool.DASH)){
				String orderID = orderIds[i].trim().split(StringPool.DASH)[0];
				orderIdsString.append(orderID.trim());
				if(i!=orderIds.length-1){
					orderIdsString.append(StringPool.PIPE);
				}
			}
		}
		
		sb.append("orderid="+orderIdsString+StringPool.AMPERSAND);
		sb.append("userid"+StringPool.EQUAL+sellrId);
		return sb.toString();
	}
	
	public static String getOrderDetailsURI(String orderId, long sellrId) {
		
		StringBuilder sb = getOrderBaseURI();
		sb.append(OrderConstants.RETRIEVE_ORDER_FUNCTION_NAME);
		sb.append(StringPool.QUESTION);
		
		if(orderId.contains(StringPool.DASH)){
			orderId = orderId.split(StringPool.DASH)[0];
		}
		
		sb.append("orderid="+orderId+StringPool.AMPERSAND);
		sb.append("userid"+StringPool.EQUAL+sellrId);
		return sb.toString();
	}
	
	public static String getSearchOrdersURI(String status, String startDate, String endDate, String filterDateBy, String sortBy,String deliveryOption,String customerName,String sellerId,String pageNumber){
		StringBuilder sb = getOrderBaseURI();
		sb.append(OrderConstants.SEARCH_ORDER_FUNCTION_NAME);
		sb.append(StringPool.QUESTION);
		//Added this condition to retrieve acknowledged orders along with released orders 
		if((orderReleased).equalsIgnoreCase(status)){
			status = OrderConstants.RETRIEVE_ORDER_STATUS_RELEASED;
		}
		sb.append(assignStatus+status+StringPool.AMPERSAND);
		sb.append(assignStrtDtTime+startDate+StringPool.AMPERSAND);
		sb.append(assignEndDtTime+endDate+StringPool.AMPERSAND);
		sb.append("filterdateby="+filterDateBy+StringPool.AMPERSAND);
		sb.append("sortby="+sortBy+StringPool.AMPERSAND);
		if(!"".equalsIgnoreCase(deliveryOption.trim())){
			sb.append("deliveryOption="+deliveryOption+StringPool.AMPERSAND);
		}
		if(!"".equalsIgnoreCase(customerName)){
			sb.append("customername="+customerName+StringPool.AMPERSAND);
		}
		
		if(!"".equalsIgnoreCase(pageNumber)){
			sb.append("pageNumber="+pageNumber+StringPool.AMPERSAND);
		}
		String pageSizeFromProperty = PortletProps.get("order.search.service.parameter.page.size");
		if(Validator.isNotNull(pageSizeFromProperty) && !"".equals(pageSizeFromProperty.trim())){
			sb.append("pageSize="+pageSizeFromProperty+StringPool.AMPERSAND);
		}
		sb.append(assignUserId+sellerId);
		return sb.toString();
	}
	
	public static String getSearchOrdersURIForPrintDownload(String status, String startDate, String endDate, String deliveryOption, String sellerId){
		StringBuilder sb = getOrderBaseURI();
		sb.append(OrderConstants.RETRIEVE_ORDER_FUNCTION_NAME);
		sb.append(StringPool.QUESTION);
		//Added this condition to retrieve acknowledged orders along with released orders 
		if((orderReleased).equalsIgnoreCase(status)){
			status = OrderConstants.RETRIEVE_ORDER_STATUS_RELEASED;
		}
		sb.append("filterdateby="+"ReleasedDate"+StringPool.AMPERSAND);
		sb.append(assignStatus+status+StringPool.AMPERSAND);
		sb.append(assignStrtDtTime+startDate+StringPool.AMPERSAND);
		sb.append(assignEndDtTime+endDate+StringPool.AMPERSAND);
		if(!"".equalsIgnoreCase(deliveryOption.trim())){
			sb.append("deliveryOption="+deliveryOption+StringPool.AMPERSAND);
		}
		sb.append(assignUserId+sellerId);
		return sb.toString();
	}
	
	
	
	public static String getProcessingReportURI(String transactionId) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(OrderConstants.HTTP_PROTOCOL);
		sb.append(OrderConstants.HOST);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(OrderConstants.ORDERAPI_PROCESSINGREPORT);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(transactionId);
		//sb.append(StringPool.QUESTION);
		//sb.append(DecryptPassword.decrypt(PropsUtil.get("marketplace.order.auth.header")));
		//sb.append(OrderConstants.HEADER);
		return sb.toString();
	}
	
	
	public static String getAcknowledgeOrdersURI(long userId) {

		StringBuilder stringBuilder = getOrderBaseURI();
		stringBuilder.append(OrderConstants.ACKNOWLEDGE_ORDER_FUNCTION_NAME);
		getQuestionMarkAndUserId(userId, stringBuilder);
		return stringBuilder.toString();
	}

	private static void getQuestionMarkAndUserId(long userId,
			StringBuilder stringBuilder) {
		stringBuilder.append(StringPool.QUESTION);		
		stringBuilder.append(assignUserId);
		stringBuilder.append(userId);
	}
	
	
	public static String getShipmentUpdatesOrdersURI(long userId) {

		StringBuilder stringBuilder = getOrderBaseURI();
		stringBuilder.append(OrderConstants.SHIPMENTUPDATES_ORDER_FUNCTION_NAME);
		getQuestionMarkAndUserId(userId, stringBuilder);
		return stringBuilder.toString();
	}

    public static String getReturnRefundsOrderURI(long userId) {

		StringBuilder stringBuilder = getOrderBaseURI();
		stringBuilder.append(OrderConstants.RETURNREFUNDS_ORDER_FUNCTION_NAME);
		getQuestionMarkAndUserId(userId, stringBuilder);
		return stringBuilder.toString();
	} 
		
	private static StringBuilder getOrderBaseURI() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OrderConstants.HTTP_PROTOCOL);
		stringBuilder.append(OrderConstants.HOST);
		stringBuilder.append(StringPool.FORWARD_SLASH);
		stringBuilder.append(OrderConstants.ORDER_API_NAME);
		stringBuilder.append(StringPool.FORWARD_SLASH);
		return stringBuilder;
	}
	
	public static HttpHeaders getMarketPlaceAPIAuthHeaders() {
		String authHeader =  OrderConstants.HEADER;
		//String authHeader = PropsUtil.get("marketplace.v1.auth.header");
		String equl = "="; 
		String[] authEncrypHeaderLst = authHeader.split("&");
		String appKeyTokEncryptVal = authEncrypHeaderLst[0].split(equl)[1].trim();
		String appKeyEncryptVal = authEncrypHeaderLst[1].split(equl)[1].trim();
		String appKeyTokDecryptVal = DecryptPassword.decrypt(appKeyTokEncryptVal);
		String appKeyDecryptVal = DecryptPassword.decrypt(appKeyEncryptVal);
		StringBuilder sb = new StringBuilder();
		sb.append(authEncrypHeaderLst[0].split(equl)[0].trim());
		sb.append(equl);
		sb.append(appKeyTokDecryptVal.trim());
		sb.append("&");
		sb.append(authEncrypHeaderLst[1].split("=")[0].trim());
		sb.append(equl);
		sb.append(appKeyDecryptVal.trim());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", sb.toString());
		//headers.add("Authorization", OrderConstants.HEADER);
		headers.add("Content-Type", "application/xml");
		headers.add("Accept", "application/xml");
		return headers;
	}
			
	public static RetrieveOrders overlayOrderStatus(
			RetrieveOrders releasedOrders, OrderBasket basketOrders) {
			    
		return releasedOrders;
	}
	
	public static RetrieveOrders getRetrieveOrders(Shipment shipment){
		RetrieveOrders rOrders = new RetrieveOrders(); 
		return rOrders;
	}
	
	public static boolean isValidList(List list) {

		if(list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	public static boolean isValidMap(Map map) {

		if(map != null && map.size() > 0) {
			return true;
		}
		return false;
	}
	
	public static Ndxml getYodelRequest(String orderId, long sellerId, TradingPartner tp, Date pickUpDate, CarrierAttributes ca, Order order) {
		log.info("OrderHelperUtil : getYodelRequest() : pickUpDate :"+pickUpDate.toString());
		CarrierDetails cd =new CarrierDetails();
		WarehouseAddress wh = new WarehouseAddress();
		if(ca != null){
			if(ca.getCarrierDetails() != null){
				cd = ca.getCarrierDetails();
			}
			if(ca.getWarehouseAddress() != null){
				wh = ca.getWarehouseAddress();
			}
		}
				
		Ndxml ndxml = new Ndxml();
		Credentials credentials = new Credentials();
		if(cd.getCarrierAccountUsername() != null){
			credentials.setIdentity(cd.getCarrierAccountUsername());
		}else{
			credentials.setIdentity("");
		}
		if(cd.getCarrierAccountPassword() != null){
			credentials.setPassword(DecryptPassword.decrypt(cd.getCarrierAccountPassword()));
		}else{
			credentials.setPassword("");
		}
		
		/*credentials.setIdentity("waterf24");
		credentials.setPassword("waterf24vtp");*/
		 
		Request req= new Request();
		req.setResponseType("labelURL");
		req.setStyleTag("VCTYInt");
		req.setId((byte) 1);
		req.setFunction("createNewJob");
		
		Job job = new Job();
		job.setJobType("HT");
		
		Tariff tariff = new Tariff();
		tariff.setCode("S12");
		job.setTariff(tariff);
		
		Service service = new Service();
		service.setCode("IN");
		job.setService(service);
		
		Account acct = new Account();
		//acct.setId((int) sellerId);    /*** Seller Acct id ***/
		/* TODO - Below Seller ID is hardcoded, Remove later and uncomment above line */
		if(cd.getCarrierAccountNumber() != null){
			acct.setId(Integer.parseInt(cd.getCarrierAccountNumber()));
		}else{
			acct.setId(0);
		}
		
		job.setAccount(acct);
		
		International inernational = buildInternationalRequest();
		
		job.setInternational(inernational);
		
		PickupDateTime pickupDateTime = new PickupDateTime();
		XMLGregorianCalendar xmlGregCal;
		try {
			SimpleDateFormat ft = new SimpleDateFormat(dateSplitter);
			SimpleDateFormat onlyTime = new SimpleDateFormat(timeSplitter);
			String pickUpString = ft.format(pickUpDate);
			String pickUpTime = onlyTime.format(pickUpDate);
			log.info("OrderHelperUtil : getYodelRequest() : pickUpString :"+pickUpString);
			xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(pickUpString);//2015-04-06
			log.info("OrderHelperUtil : getYodelRequest() : xmlGregCal :"+xmlGregCal.toString());
			pickupDateTime.setDate(xmlGregCal);
			xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(pickUpTime);
			pickupDateTime.setTime(xmlGregCal);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		job.setPickupDateTime(pickupDateTime);
		log.info("OrderHelperUtil : getYodelRequest() : job.getPickupDateTime :"+job.getPickupDateTime().toString());
		String orderNumber = orderId;
		job.setReference(orderNumber);
		Ndxml.Request.Job.Options options = new Ndxml.Request.Job.Options();
		
		String confirmEmail = getPartnerContact(tp, OrderConstants.CUSTOMER_SERVICE_CONTACT).getEmailAddress();
		if(confirmEmail != null){
			options.setConfirmEmail(confirmEmail);
		}else{
			options.setConfirmEmail("");
		}
		
		Ndxml.Request.Job.Options.WaitAndReturn waitAndReturn = new Ndxml.Request.Job.Options.WaitAndReturn();
		waitAndReturn.setValue("false");
		options.setWaitAndReturn(waitAndReturn);
		job.setOptions(options);
		
		job.getSegment().add(getPSegment(pickUpDate, cd, wh, order, tp));
		job.getSegment().add(getDSegment(pickUpDate, cd, wh, order, tp));
		
		/*** ***/
		req.setJob(job);
		ndxml.setCredentials(credentials);
		ndxml.setRequest(req);
		ndxml.setVersion((float) 2.0);
		log.info("Creating NDXML request has been completed coming out from the method");
		return ndxml;
	}
	
	public static Ndxml.Request.Job.International buildInternationalRequest() {
		
		Ndxml.Request.Job.International international = new Ndxml.Request.Job.International();
		Ndxml.Request.Job.International.Parcels parcels = new Ndxml.Request.Job.International.Parcels();
		Ndxml.Request.Job.International.Parcels.Parcel parcel = new Ndxml.Request.Job.International.Parcels.Parcel();
		Ndxml.Request.Job.International.Parcels.Parcel.ParcelContent parcelContent = new Ndxml.Request.Job.International.Parcels.Parcel.ParcelContent();
		
		parcels.setCount(1);
		
		parcel.setNumParcels(1);
		parcel.setLength(0);
		parcel.setWidth(0);
		parcel.setHeight(0);
		parcel.setWeight(1);
		
		parcelContent.setProductDes("Non Documents");
		parcelContent.setHSTariff(12121212);
		parcelContent.setOrigin("UK");
		parcelContent.setUnitQty(1);
		parcelContent.setUnitValue(0);
		parcelContent.setUnitWeight(1);
		parcelContent.setUnitCurrency("GBP");
		
		parcel.setParcelContent(parcelContent);
		parcels.setParcel(parcel);
		
		international.setParcels(parcels);
		
		return international;
	}
	
	public static Segment getPSegment(Date pickUpDat, CarrierDetails cd, WarehouseAddress wh, Order order, TradingPartner tp) {
		Ndxml.Request.Job.Segment segment1 = new Ndxml.Request.Job.Segment();
		segment1.setType("P");
		segment1.setNumber((byte) 1);
		segment1.setPieces((byte) 1);
		OrderDateTime orderDateTime = new OrderDateTime();
		XMLGregorianCalendar xmlGregCal3;
		XMLGregorianCalendar xmlGregCal4;
		try {
			SimpleDateFormat ft = new SimpleDateFormat(dateSplitter);
			SimpleDateFormat onlyTime = new SimpleDateFormat(timeSplitter);
			String pickUpString = ft.format(pickUpDat);
			String pickUpTime = onlyTime.format(pickUpDat);
			
			xmlGregCal3 = DatatypeFactory.newInstance().newXMLGregorianCalendar(pickUpString);
			orderDateTime.setDate(xmlGregCal3);
			xmlGregCal4 = DatatypeFactory.newInstance().newXMLGregorianCalendar(pickUpTime);
			orderDateTime.setTime(xmlGregCal4);
			segment1.setOrderDateTime(orderDateTime);
		} catch(DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		
		Address addr = new Address();
		Country country = new Country();
		country.setValue("United Kingdom");
		country.setISOCode("GB");
		
		addr.setCountry(country);
		if(wh != null){
			if(wh.getWarehouseAddressPostCode() != null){
				addr.setZip(wh.getWarehouseAddressPostCode());
			}
			if(wh.getWarehouseAddressLine1() != null){
				addr.setBuilding(wh.getWarehouseAddressLine1());
			}
			if(wh.getWarehouseAddressLine2() != null){
				addr.setStreet(wh.getWarehouseAddressLine2());
			}
			if(wh.getWarehouseAddressRegion() != null){
				addr.setLocality(wh.getWarehouseAddressRegion());
			}
			if(wh.getWarehouseAddressCityOrTown() != null){
				addr.setTown(wh.getWarehouseAddressCityOrTown());
			}
		}
		addr.setCounty("county");
		addr.setCompany(order.getCustomerFirstName()+order.getCustomerLastName()); //Adding Customer Name	
		segment1.setAddress(addr);
		
		Contact contact = new Contact();
		 //Setting Trading Name for Contact Name
		contact.setName(tp.getTradingPartnerDetails().getTradingPartnerSellerDetails().getTradingName());
		PartnerContact corporatePartnerContact = getPartnerContact(tp, OrderConstants.CORPORATE_OR_BILLING_ADDRESS_CONTACT);
		Telephone telephone = new Telephone();
		telephone.setExt(corporatePartnerContact.getExtnNumber());
		telephone.setValue(corporatePartnerContact.getPhoneNumber());
		contact.setTelephone(telephone);
		
		segment1.setAddress(addr);
		segment1.setContact(contact);
		segment1.setOrderDateTime(orderDateTime);
		segment1.setWeight((byte) 1.0);
		
		NonDocs nonDocs = new NonDocs();
		nonDocs.setValue("true");
		segment1.setNonDocs(nonDocs);

		return segment1;
	}
	public static Segment getDSegment(Date dropDate, CarrierDetails cd, WarehouseAddress wh, Order order, TradingPartner tp) {
		Ndxml.Request.Job.Segment segment1 = new Ndxml.Request.Job.Segment();
		segment1.setType("D");
	 	
		segment1.setNumber((byte) 2);
		segment1.setPieces((byte) 1);

		OrderDateTime orderDateTime = new OrderDateTime();
		XMLGregorianCalendar xmlGregCal3;
		XMLGregorianCalendar xmlGregCal4;
		try {
			SimpleDateFormat ft = new SimpleDateFormat(dateSplitter);
			SimpleDateFormat onlyTime = new SimpleDateFormat(timeSplitter);
			String dropDateString = ft.format(dropDate);
			String dropTime = onlyTime.format(dropDate);
			
			xmlGregCal3 = DatatypeFactory.newInstance().newXMLGregorianCalendar(dropDateString);
			orderDateTime.setDate(xmlGregCal3);
			xmlGregCal4 = DatatypeFactory.newInstance().newXMLGregorianCalendar(dropTime);
			orderDateTime.setTime(xmlGregCal4);
		} catch(DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		Address addr = new Address();
		Country country = new Country();
		country.setValue("United Kingdom");
		country.setISOCode("GB");
		addr.setCountry(country);
		Integer lineNum = null;
		for (Integer keyItr : order.getOrderLineItems().keySet()) {
			lineNum = keyItr;
			break;
		}
		if(order.getOrderLineItems().get(lineNum).getShipToPostcode() != null){
			addr.setZip(order.getOrderLineItems().get(lineNum).getShipToPostcode());	
		}else{
			addr.setZip(StringPool.SPACE);
		}
		if(order.getOrderLineItems().get(lineNum).getShipToCounty() != null){
			addr.setCounty(order.getOrderLineItems().get(lineNum).getShipToCounty());
		}else{
			addr.setCounty(StringPool.SPACE);
		}
		if((order.getOrderLineItems().get(lineNum).getShipToFirstName() != null) && (order.getOrderLineItems().get(lineNum).getShipToLastName() != null) ){
			addr.setCompany(order.getOrderLineItems().get(lineNum).getShipToFirstName()+order.getOrderLineItems().get(lineNum).getShipToLastName());
		}else{
			addr.setCompany(StringPool.SPACE);
		}
		if(order.getOrderLineItems().get(lineNum).getShipingAddress1() != null){
			addr.setBuilding(order.getOrderLineItems().get(lineNum).getShipingAddress1());
		}else{
			addr.setBuilding(StringPool.SPACE);
		}
		if(order.getOrderLineItems().get(lineNum).getShipingAddress2() != null){
			addr.setStreet(order.getOrderLineItems().get(lineNum).getShipingAddress2());
		}else{
			addr.setStreet(StringPool.SPACE);
		}
		if(order.getOrderLineItems().get(lineNum).getShipingAddress3() != null){
			addr.setLocality(order.getOrderLineItems().get(lineNum).getShipingAddress3());
		}else{
			addr.setLocality(StringPool.SPACE);
		}
		if(order.getOrderLineItems().get(lineNum).getShipToCity() != null){
			addr.setTown(order.getOrderLineItems().get(lineNum).getShipToCity());
		}else{
			addr.setTown(StringPool.SPACE);
		}
		
		segment1.setAddress(addr);
		
		Contact contact = new Contact();
		 //Setting Trading Name for Contact Name
		if((order.getOrderLineItems().get(lineNum).getShipToFirstName() != null) && (order.getOrderLineItems().get(lineNum).getShipToLastName() != null)){
			contact.setName(order.getOrderLineItems().get(lineNum).getShipToFirstName()+order.getOrderLineItems().get(lineNum).getShipToLastName());
		}else{
			contact.setName(StringPool.SPACE);
		}
		
		Telephone telephone = new Telephone();
		telephone.setExt("0123");
		if((order.getCustomerDayTelephone() != null)){
			telephone.setValue(order.getCustomerDayTelephone());
		}else{
			telephone.setValue(StringPool.SPACE);
		}
		contact.setTelephone(telephone);
		segment1.setAddress(addr);
		segment1.setContact(contact);
		segment1.setOrderDateTime(orderDateTime);
		segment1.setWeight((byte) 1.0);
		
		NonDocs nonDocs = new NonDocs();
		nonDocs.setValue("true");
		segment1.setNonDocs(nonDocs);
		
		return segment1;
	}
	
	public static String downloadPDFAndSave(String pdfUrl, String filePath, String trackingId) {
		log.info("downloadPDFAndSave URL : "+pdfUrl);
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        String fullFilePath = filePath+trackingId+".pdf";
        boolean isFolderCreated = true;
        boolean isFileCreated = false;
        Proxy proxy = null;
        try{
        	File pdfFl = new File(fullFilePath);
        	if(!pdfFl.getParentFile().exists()){
        		isFolderCreated = pdfFl.getParentFile().mkdirs();
        	}
        	if(isFolderCreated){
        		isFileCreated = pdfFl.createNewFile();
	        	if(isFileCreated){
	        		if(("yes").equalsIgnoreCase(PropsUtil.get(OrderConstants.PROXY_EXISTS))){
	        			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(InetAddress.getByName(String.valueOf(PropsUtil.get(OrderConstants.SERVER_PROXY))), Integer.valueOf(PropsUtil.get(OrderConstants.SERVER_PORT))));
	        			
	        		}
		        	
		    		//System.setProperty("http.keepAlive", "false");
		            //URL url = new URL(pdfUrl);
		            if(("yes").equalsIgnoreCase(PropsUtil.get(OrderConstants.PROXY_EXISTS))){
		            	bin = new BufferedInputStream(new URL(pdfUrl).openConnection(proxy).getInputStream());
		            }else{
		            	bin = new BufferedInputStream(new URL(pdfUrl).openStream());
		            }
		            fout = new FileOutputStream(pdfFl);
		 
		            final byte[] data = new byte[1024];
		            int count;
		            while((count = bin.read(data, 0, 1024)) != -1) {
		                fout.write(data, 0, count);
		            }
	        }else{
	        	throw new Exception("Unable to Create Label PDF File");
	        }
        }else{
        	throw new Exception("Unable to Create Folder for storing Label PDF File");
        }
        } catch(Exception e) {
        	e.printStackTrace();
        } finally {
            if(bin != null) {
                try {
					bin.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
            }
            if(fout != null) {
                try {
					fout.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
            }
        }
        
        return fullFilePath;
    }
	
	/**
	 * This method is used to get the customer Contact info from Trading partner.
	 * @param tradingPartner
	 * @param partnerContactType
	 * @return
	 */
	public static PartnerContact getPartnerContact(TradingPartner tradingPartner, String partnerContactType) {
		PartnerContact contact = null;

		for(PartnerContact partnerContact : tradingPartner.getPartnerContact()) {
			if(partnerContact.getPartnerContactType() != null && partnerContact.getPartnerContactType().getContactType() != null) {
				if(partnerContactType.equals(partnerContact.getPartnerContactType().getContactType())) {
					contact = partnerContact;
					break;
				}
			}
		}

		return contact;
	}

	public static PartnerAccountInformation getPartnerAccountInformation(TradingPartner tradingPartner, String partnerAccountType) {
		PartnerAccountInformation partnerAccountInfo = null;
		for(PartnerAccountInformation partnerAccountInformation : tradingPartner.getPartnerAccountInformation()) {
			if(partnerAccountInformation.getPartnerAddressType() != null && partnerAccountInformation.getPartnerAddressType().getAddressType() != null) {
				if(partnerAccountType.equals(partnerAccountInformation.getPartnerAddressType().getAddressType())) {
					partnerAccountInfo = partnerAccountInformation;
					break;
				}
			}
		}

		return partnerAccountInfo;
	}
	
	public static List<ShipmentUpdate> getShipUpdateForCAndC(Map<String, List<Order>> cAndCOrdrMap, long sellerId){
		//For Click & Collect orders
		List<ShipmentUpdate> shipmentUpdatesList = new ArrayList<ShipmentUpdate>();
		ShipmentUpdate shipmentUpdate = null; 
		for(String ordId : cAndCOrdrMap.keySet()){
			shipmentUpdate = new ShipmentUpdate();
			List<Order> itrOrdLst = cAndCOrdrMap.get(ordId);
			shipmentUpdate.setSellerOrderNumber(ordId);
			shipmentUpdate.setCustomerOrderNumber(itrOrdLst.get(0).getCustomerOrderNumber());
			shipmentUpdate.setSellerId(String.valueOf(sellerId));
			Shipments shipments = new Shipments();
			shipments.setIsConsolidatedParcel("");
			shipments.setOuterCaseTrackingNumber("N");
			List<com.tesco.mhub.order.shipment.update.Shipment> shipLst = new ArrayList<com.tesco.mhub.order.shipment.update.Shipment>();
			for (Order ordr : itrOrdLst) {
				com.tesco.mhub.order.shipment.update.Shipment shipment = new com.tesco.mhub.order.shipment.update.Shipment();
				shipment.setDispatchDate(DateUtil.asXMLGregorianCalendar(new Date()));
				shipment.setParcelTrackingNumber(ordr.getTrackingId());
				shipment.setTotalWeight(new BigDecimal(1.0));
				shipment.setTotalWeightUOM("2e214234");	

				//Carrier Details
				com.tesco.mhub.order.shipment.update.CarrierDetails carrierDetails = new com.tesco.mhub.order.shipment.update.CarrierDetails();
				carrierDetails.setCartonNumber("");
				carrierDetails.setIsTFSUsed("N");
				String cName="";
				if(!(ordr.getCarrierName().equals(cName))){
					carrierDetails.setThirdPartyCarrierName(ordr.getCarrierName());
				}
				shipment.setCarrierDetails(carrierDetails);

				//Address Details
				AddressDetails addressDetails = new AddressDetails();
				addressDetails.setFirstName(ordr.getCustomerFirstName());
				addressDetails.setLastName(ordr.getCustomerLastName());

				addressDetails.setPostCode(OrderConstants.SHIPPINGPOSTCODE);
				addressDetails.setHomePhone(OrderConstants.SHIPPINGHOMEPHONE);
				shipment.setAddressDetails(addressDetails);	

				//Shipment lines
				Map<Integer, OrderLineItem> cAndOrderLineItems = new LinkedHashMap<Integer, OrderLineItem>();
				cAndOrderLineItems = ordr.getOrderLineItems();
				List<ShipmentLine> shipmentLineList = new ArrayList<ShipmentLine>();
				ShipmentLines shipmentLines = new ShipmentLines();
				for (Integer orderLineItemId : cAndOrderLineItems.keySet()) {
					OrderLineItem cAndCOrdLineItem = cAndOrderLineItems.get(orderLineItemId);
					ShipmentLine shipmentLine = new ShipmentLine();	
					shipmentLine.setOrderLineNumber(""+cAndCOrdLineItem.getOrderLineNumber());
					shipmentLine.setQuantity(new BigDecimal(cAndCOrdLineItem.getDispatchedQuantity()));
					shipmentLine.setShipmentLineNo("" + cAndCOrdLineItem.getOrderLineNumber());
					shipmentLine.setSubLineNo(""+cAndCOrdLineItem.getOrderLineNumber());
					shipmentLine.setUnitOfMeasure(cAndCOrdLineItem.getUnitOfSale());																	
					shipmentLineList.add(shipmentLine);
				}
				shipmentLines.setShipmentLine(shipmentLineList);
				shipment.setShipmentLines(shipmentLines);
				shipLst.add(shipment);
			}
			shipments.setShipment(shipLst);
			shipmentUpdate.setShipments(shipments);
			shipmentUpdatesList.add(shipmentUpdate);
		}
		return shipmentUpdatesList;
	}
	
	public static List<OrderPackageDetails> createOrdPacDetails(){
		List<OrderPackageDetails> opdLst = new ArrayList<OrderPackageDetails>();
		List<ItemDetails> idLst = new ArrayList<ItemDetails>();
		List<ParcelDetails> pdList = new ArrayList<ParcelDetails>();
		ItemDetails id = new ItemDetails();
		id.setSkuId("123456");
		id.setDescription("Red Flat");
		id.setQty(1);
		idLst.add(id);
		id = new ItemDetails();
		id.setSkuId("MDKD-4C5");
		id.setDescription("Marble");
		id.setQty(10);
		idLst.add(id);
		id = new ItemDetails();
		id.setSkuId("KLOP12345");
		id.setDescription("SuitCase");
		id.setQty(1);
		idLst.add(id);
		ParcelDetails pd = new ParcelDetails();
		pd.setTrackingId("M815OR5");
		pd.setItemDetailsLst(idLst);
		pdList.add(pd);
		pd = new ParcelDetails();
		pd.setTrackingId("0835458AD");
		pd.setItemDetailsLst(idLst);
		pdList.add(pd);
		OrderPackageDetails opd = new OrderPackageDetails();
		opd.setOrderId("P3CDM-1");
		opd.setCustomerName("P Bear");
		opd.setParcelSize(2);
		opd.setParcelDetailsList(pdList);
		opdLst.add(opd);
		id = new ItemDetails();
		id.setSkuId("38765");
		id.setDescription("Cap");
		id.setQty(1);
		idLst.add(id);
		id = new ItemDetails();
		id.setSkuId("POWR-5C4");
		id.setDescription("Broccoli");
		id.setQty(4);
		idLst.add(id);
		id = new ItemDetails();
		id.setSkuId("SB8Y-5C8");
		id.setDescription("Bow-tie");
		id.setQty(1);
		idLst.add(id);
		pd = new ParcelDetails();
		pd.setTrackingId("G8738AP");
		pd.setItemDetailsLst(idLst);
		pdList.add(pd);
		pd = new ParcelDetails();
		pd.setTrackingId("PW2737W");
		pd.setItemDetailsLst(idLst);
		pdList.add(pd);
		opd = new OrderPackageDetails();
		opd.setOrderId("KDCDM2-1");
		opd.setCustomerName("C Jhon");
		opd.setParcelSize(2);
		opd.setParcelDetailsList(pdList);
		opdLst.add(opd);
		return opdLst;
	}

	@SuppressWarnings("unchecked")
	public static List<ParcelDetails> getParcelDetailsListFromDB(RenderRequest renderRequest, long sellerId, String orderId) {
		
		List<ParcelDetails> parcelDetailsList = new ArrayList<ParcelDetails>();
		List<OrderLabelDetails> orderLabelDetailsList = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsByOrderIdSellrIdIsUsed(orderId, sellerId, false);
		
		Map<String, Order> selectedOrdersMap = null;
		if((renderRequest.getPortletSession().getAttribute("selectedOrdersMap")) instanceof Map<?, ?>){
			selectedOrdersMap = (Map<String, Order>) renderRequest.getPortletSession().getAttribute("selectedOrdersMap");
		}
		
		
		if(selectedOrdersMap != null && selectedOrdersMap.size() != 0) {
			Order ord = selectedOrdersMap.get(orderId);
			if(ord != null) {
				for(OrderLabelDetails orderLabelDetails : orderLabelDetailsList) {
					ParcelDetails parcelDetails = new ParcelDetails();
					parcelDetails.setTrackingId(orderLabelDetails.getTrackingId());
					parcelDetails.setItemDetailsLst(getItemDetailsFromSession(renderRequest,sellerId, orderId, ord));
					parcelDetailsList.add(parcelDetails);
				}
			}
		}
		return parcelDetailsList;
	}
	private static List<ItemDetails> getItemDetailsFromSession(RenderRequest renderRequest, long sellerId, String orderId, Order ord) {
		
		List<ItemDetails> itemDetailsList = new ArrayList<ItemDetails>();
		
		Map<Integer, OrderLineItem> orderLineItems = ord.getOrderLineItems();
		
		for(Map.Entry<Integer, OrderLineItem> orderLineItemsMap : orderLineItems.entrySet()) {
			ItemDetails itemDetails = new ItemDetails();
			
			OrderLineItem orderLineItem = orderLineItemsMap.getValue();
			if(orderLineItem.getOpenQuantity() > 0){
			if((orderLineItem.getSku() != null)){
				if(!(("").equalsIgnoreCase(orderLineItem.getSku().trim()))){
				itemDetails.setSkuId(orderLineItem.getSku());
				}else{
					itemDetails.setSkuId("");
				}
			} else{
					itemDetails.setSkuId("");
				}
			itemDetails.setDescription(orderLineItem.getDescription());
			itemDetails.setQty( (int) orderLineItem.getOpenQuantity());
			itemDetails.setLineNumber(orderLineItem.getOrderLineNumber());
			itemDetailsList.add(itemDetails);
		}
		}
		return itemDetailsList;
	}
	
	public static List<ParcelItemInfo> getParcItemInfoLst(List<ParcelJson> parcJsobObjLst){
		List<ParcelItemInfo> parcItmInfLst = new ArrayList<ParcelItemInfo>();
		List<ItemInfo> itmInfLst = new ArrayList<ItemInfo>();
		ItemInfo itmInf = null;
		ParcelItemInfo parItmInf = null; 
		boolean isFound = false;
		for (ParcelJson itr : parcJsobObjLst) {
			isFound = false;
			for (ParcelItemInfo parItr : parcItmInfLst) {
				if(itr.getTrackingId().equalsIgnoreCase(parItr.getTrackingId())){
					isFound = true;
					itmInf = new ItemInfo(itr.getLineNumber(), "", "", itr.getQty());
					itmInfLst = parItr.getItemInfoLst();
					itmInfLst.add(itmInf);
					break;
				}
			}
			if(!isFound){
				itmInfLst = new ArrayList<ItemInfo>();
				itmInf = new ItemInfo(itr.getLineNumber(), "", "", itr.getQty());
				itmInfLst.add(itmInf);
				parItmInf = new ParcelItemInfo(itr.getTrackingId(), itmInfLst);
				parcItmInfLst.add(parItmInf);
			}
			
		}
		return parcItmInfLst;
	}
}