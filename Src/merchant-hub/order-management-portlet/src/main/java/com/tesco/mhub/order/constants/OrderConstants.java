package com.tesco.mhub.order.constants;

import com.liferay.portal.kernel.util.PropsUtil;
import com.tesco.mhub.order.util.DecryptPassword;
import com.tesco.mhub.order.util.PortletProps;

public abstract class OrderConstants {
	
	/**
	 * It is a String Constant SYSTEM_NAME.
	 */
	public static final String SYSTEM_NAME = "Order Management";
	
	/**
	 * It is a String Constant SYSTEM_ATTRIBUTE.
	 */
	public static final String SYSTEM_ATTRIBUTE = "OMS API- Order Cache";
	
	/**
	 * It is a String Constant COLLECTION_DATE.
	 */
	public static final String COLLECTION_DATE = "collectionDate";
	
	/**
	 * It is a String Constant PICKUP_TIME.
	 */
	public static final String PICKUP_TIME = PropsUtil.get("tp.carrierDetails.pickup.time");
	
	/**
	 * It is a String Constant CUSTOMER_SERVICE_CONTACT.
	 */
	public static final String CUSTOMER_SERVICE_CONTACT = "CUSTOMER_SERVICE_CONTACT";
	
	/**
	 * It is a String Constant CORPORATE_OR_BILLING_ADDRESS_CONTACT.
	 */
	public static final String CORPORATE_OR_BILLING_ADDRESS_CONTACT = "CORPORATE_OR_BILLING_ADDRESS_CONTACT";
	
	/**
	 * It is a String Constant SYSTEM_ATTRIBUTE.
	 */
	public static final String TPCARRIERNAME = PortletProps.get("shipping.orderdetails.thirdparty.carriername");
	
	/**
	 * It is a String Constant SYSTEM_ATTRIBUTE.
	 */
	public static final String 	SHIPPINGPOSTCODE = PortletProps.get("shipping.orderdetails.postcode");
	
	/**
	 * It is a String Constant SYSTEM_ATTRIBUTE.
	 */
	public static final String 	SHIPPINGHOMEPHONE = PortletProps.get("shipping.orderdetails.homephone");
	
	/**
	 * It is a String Constant SYSTEM_ATTRIBUTE.
	 */
	public static final String CARRIERURL = PortletProps.get("shipping.orderdetails.carrier.url");
	/**
	 * It is a String Constant orderId.
	 */
	public static final String STRING_ORDER_ID = "orderId";
	
	/**
	 * It is a String Constant SELECTED_TAB.
	 */
	public static final String STRING_SELECTED_TAB = "SELECTED_TAB";
	/**
	 * It is a String Constant selectedTab.
	 */
	public static final String STRING_SELECTED_TAB_JSP = "selectedTab";
	/**
	 *  It is a String Constant action.
	 */
	public static final String STRING_ACTION = "action";
	/**
	 * It is a String Constant orderList.
	 */
	public static final String STRING_ORDER_LIST = "orderList";
	/**
	 * Order Status 3200.210.
	 */
	public static final String STRING_ORDER_STATUS_3200_210 = "3200.210";
	
	/**
	 * Order Status 3700.
	 */
	public static final String STRING_ORDER_STATUS_3700 = "3700";
	
	/**
	 * Order Status 9000.
	 */
	public static final String STRING_ORDER_STATUS_9000 = "9000";
	
	/**
	 * Order Status 3700.01.
	 */
	public static final String STRING_ORDER_STATUS_3700_01 = "3700.01";
	/**
	 * Order Status 3700.9400.
	 */
	public static final String STRING_ORDER_STATUS_3700_9400 = "3700.9400";
	/**
	 * Order Status 3700.8300.
	 */
	public static final String STRING_ORDER_STATUS_3700_8300 = "3700.8300";
	/**
	 * Order Status 3200.215.
	 */
	public static final String STRING_ORDER_STATUS_3200_215 = "3200.215";
	/**
	 * String constant partially cancelled.
	 */
	public static final String STRING_PARTIALLY_CANCELLED ="Partially Cancelled";
	
	/**
	 * String constant partially dispatched.
	 */
	public static final String STRING_PARTIALLY_DISPATCHED ="Partially Dispatched";
	
	/**
	 * String constant partially acknowledged.
	 */
	public static final String STRING_PARTIALLY_ACKNOWLEDGED ="Partially Acknowledged";
	
	/**
	 * currentSellerId is the current loggedin seller.
	 */
	public static final String CURRENT_SELLER_ID = "currentSellerId";
	/**
	 * currentSellerId IN DISPATCH is the current loggedin seller.
	 */
	public static final String CURRENT_SELLER_ID_IN_DISPATCH = "currentSellerIdInDispatch";
	/**
	 * No Transaction Id constant.
	 */
	public static final String NO_TRANSACTION_ID = "No Transaction Id";
	/**
	 * default status line for orders.
	 */
	public static final String ORDER_STATUS_PREFIX = "order.portal.status.";	
	/**
	 * default status line for orders that are fulfilled.
	 */
	public static final String ORDER_FULFILLMENT_STATUS = "order.fulfillment.status.";
	/**
	 * 
	 */
	public static final String ORDER_STATUS_PREFIX_PARTIALLY = "order.portal.status.partially";
	
	/**
	 * HOST.
	 */
	public static final String HOST = PropsUtil.get("marketplace.v1.service.host");	
	/**
	 * Yodl Label Request Url.
	 */
	public static final String YODL_LABEL_REQUEST = PropsUtil.get("get.seller.yodl.label");
	//Order Statuses END
	/**
	 * 
	 */
	public static final String STATUS_OMS_OPEN = "order.fulfillment.status.open";
	/**
	 * 
	 */
	public static final String STATUS_OMS_ACKNOWLEDGED = "order.fulfillment.status.acknowledged";
	/**
	 * 
	 */
	public static final String STATUS_OMS_DISPATCHED = "order.fulfillment.status.dispatched";
	/**
	 * 
	 */
	public static final String STATUS_OMS_RETURNED = "order.fulfillment.status.returned";
	/**
	 * 
	 */
	public static final String STATUS_OMS_CANCELLED = "order.fulfillment.status.cancelled";	
	/**
	 * 
	 */
	public static final String STATUS_PORTAL_OPEN = "order.portal.status.3200.21";
	/**
	 * 
	 */
	public static final String STATUS_PORTAL_ACKNOWLEDGED = "order.portal.status.3200.215";
	/**
	 * 
	 */
	public static final String STATUS_PORTAL_DISPATCHED = "order.portal.status.3700";
	/**
	 * 
	 */
	public static final String STATUS_PORTAL_RETURNED = "order.portal.status.3700.01";
	/**
	 * 
	 */
	public static final String STATUS_PORTAL_CANCELLED = "order.portal.status.9000";
	//Order Statuses END
	
	/**
	 * ORDER_BASKET.
	 */
	public static final String ORDER_BASKET = "ORDER_BASKET";

	/**
	 * order details constant.
	 */
	public static final String ORDER_DETAILS = "ordDetails";
	/**
	 * SELECTED_ORDER_NUMBERS constant.
	 */
	public static final String SELECTED_ORDER_NUMBERS = "selectedOrderNumbers";
	/**
	 * UPDATE_ORDER_ACTION_DISPATCH constant.
	 */
	public static final String UPDATE_ORDER_ACTION_DISPATCH = "dispatch";
	/**
	 * UPDATE_ORDER_ACTION_CANCEL constant.
	 */
	public static final String UPDATE_ORDER_ACTION_CANCEL = "cancel";
	/**
	 * UPDATE_ORDER_ACTION_RETURN constant.
	 */
	public static final String UPDATE_ORDER_ACTION_RETURN = "return";
	/**
	 * viewupdateorders jsp.
	 */
	public static final String VIEW_UPDATE_ORDERS = "open-orders";
	/**
	 * viewUpdateDispatchOrders JSP.
	 */
	public static final String VIEW_UPDATE_DISPATCHED_ORDERS = "dispatched-orders";

	/**
	 * variable.
	 */
	public static final String DISPLAY_VAR = "display";

	/**
	 * variable.
	 */
	public static final String RETURN_LINES_VAR = "returnLines";
	/**
	 * variable.
	 */
	public static final String OPEN_VAR = "Open";
	/**
	 * SESSION RELEASED STRING.
	 */
	public static final String SESSION_RELEASED_ORDERS = "sessionReleasedOrders";
	/**
	 * SESSION DISPATCHED STRING.
	 */
	public static final String SESSION_DISPATCHED_ORDERS = "sessionDispatchedOrders";
	/**
	 * variable.
	 */
	public static final String DISPATCH_LINE_VAR = "dispatchLines";

	/**
	 * SINGLE DISPATCH LINE UPDATE JSP.
	 */
	public static final String VIEW_UPDATE_ORDER_DETAILS_SINGLE_DISPATCH = "open-order-details";

	/**
	 * RETURN MULTIPLE LINES JSP.
	 */
	public static final String RETURN_MULTIPLE_LINES = "dispatched-order-details";

	/**
	 * DISPLAY QUICK UPDATE CONFIRM JSP.
	 */
	public static final String DISPLAY_QUICK_UPDATE_CONFIRM = "quick-dispatch-open-orders";
	
	/**
	 * PENDING ORDER SIZE.
	 */
	public static final String PENDING_ORDER_STATUS = "Pending";
	
	/**
	 * PENDING ORDER SIZE.
	 */
	public static final String PENDING_ORDER_SIZE = "PendingOrdSize";
	
	/**
	 * PENDING ORDER SIZE.
	 */
	public static final String DATES_LIST = "placedDateList";
	
	/**
	 * PENDING ORDER LINE SIZE.
	 */
	public static final String PENDING_ORDER_LINE_SIZE = "PendingOrdLineSize";
	
	/**
	 * session error msg key.
	 */
	public static final String SESSION_ERROR_MESSAGE_KEY = "SESSION_ERROR_MESSAGE";
	/**
	 * error message constant. 
	 */
	public static final String ERROR_MESSAGE = "error-in-retrieving-data"; 
	/**
	 * error message constantQQ. 
	 */
	public static final String ERROR_GETTING_ORDER = "error-in-getting-order"; 
	/**
	 * error message constantAA. 
	 */
	public static final String ERROR_DATEWISE_RELOADING = "error-in-reloading-order"; 
	/**
	 * error message constantXX. 
	 */
	public static final String ERROR_GETTING_RELEASED_ORDER = "error-in-getting-released-order"; 
	/**
	 * error message constantAQQ. 
	 */
	public static final String ERROR_GETTING_DISPATCHED_ORDER = "error-in-getting-dispatched-order"; 
	/**
	 * error message constantsss. 
	 */
	public static final String ERROR_MESSAGE_ON_DATE_RANGE = "order-not-present-in-this-date-range"; 
	/**
	 * no order message constantsss. 
	 */
	public static final String ERROR_MESSAGE_ON_NO_ORDERS = "no-orders-present"; 
	/**
	 * error message constant11. 
	 */
	public static final String POST_FINAL_SUBMIT_MESSAGE = "sucess-in-final-submit"; 
	/**
	 * error message constantwewr. 
	 */
	public static final String POST_FINAL_CONFIRM_MESSAGE = "sucess-in-confirm-orders"; 
	
	//Constants for Order API in Tesco Marketplace
	
	/**
	 * Order API Name.
	 */
	public static final String ORDER_API_NAME = "orders";
	/**
	 * Retrieve order function name.
	 */
	public static final String RETRIEVE_ORDER_FUNCTION_NAME = "retrieveorders";
	/**
	 * search order function name.
	 */
	public static final String SEARCH_ORDER_FUNCTION_NAME = "searchorders";
	
	/**
	 * String constant Partially Returned.
	 */
	public static final String SEARCH_ORDER_STATUS_PARTIALLY_RETURNED = "Partially Returned";
	/**
	 * String constant  Returned.
	 */
	public static final String SEARCH_ORDER_STATUS_RETURNED = "Returned";
	/**
	 * RETRIEVE_ORDER_STATUS_RELEASED is Order API Function Name for released and acknowledged.
	 */
	public static final String RETRIEVE_ORDER_STATUS_RELEASED = "released|acknowledged";
	/**
	 * RETRIEVE_ORDER_STATUS_RELEASED is Order API Function Name.
	 */
	public static final String RETRIEVE_ORDER_STATUS_ONLY_RELEASED = "Released";
	/**
	 * RETRIEVE_ORDER_STATUS_ONLY_ACKNOWLEDGED is Order API Function Name for acknowledged.
	 */
	public static final String RETRIEVE_ORDER_STATUS_ONLY_ACKNOWLEDGED = "Acknowledged";
	/**
	 * RETRIEVE_ORDER_STATUS_DISPATCHED is Order API Function Name.
	 */
	public static final String RETRIEVE_ORDER_STATUS_DISPATCHED = "Dispatched";
	/**
	 * Acknowledge orders function name.
	 */
	public static final String ACKNOWLEDGE_ORDER_FUNCTION_NAME = "acknowledgeorders";
	/**
	 * ShipmentUpdates orders function name.
	 */
	public static final String SHIPMENTUPDATES_ORDER_FUNCTION_NAME = "shipmentupdate";
	/**
	 * ReturnRefunds orders function name.
	 */
	public static final String RETURNREFUNDS_ORDER_FUNCTION_NAME = "returnsrefunds";
	/**
	 * quantity set during updation of orders.
	 */
	public static final String UPDATED_QUANTITY = "updatedQuantity-";
	
	/**
	 * ORDERAPI_PROCESSINGREPORT is Order API Function Name.
	 */
	public static final String ORDERAPI_PROCESSINGREPORT = "processingreport";
	/**
	 *  ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_SUCCEEDED is a Constant.
	 */
	public static final String ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_SUCCEEDED = "Succeeded";	
	/**
	 * ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_PARTIALLYSUCCEEDED is a Constant.
	 */
	public static final String ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_PARTIALLYSUCCEEDED = "PartiallySucceeded";
	/**
	 * ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INPROGRESS is a Constant.
	 */
	public static final String ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INPROGRESS = "In progress";
	/**
	 * ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_FAILED is a constant.
	 */
	public static final String ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_FAILED = "Failed";
	/**
	 *  ORDERAPI_PROCESSINGREPORT_TRANSACTIONCODE_200 is a Constant.
	 */
	public static final String ORDERAPI_PROCESSINGREPORT_TRANSACTIONCODE_200 = "200";
	/**
	 *  ORDERAPI_PROCESSINGREPORT_TRANSACTIONCODE_202 is a Constant.
	 */
	public static final String ORDERAPI_PROCESSINGREPORT_TRANSACTIONCODE_202 = "202";
	/**
	 *  ORDERAPI_PROCESSINGREPORT_TRANSACTIONCODE_400 is a Constant.
	 */
	public static final String ORDERAPI_PROCESSINGREPORT_TRANSACTIONCODE_400 = "400";
	/**
	 *  ORDERAPI_PROCESSINGREPORT_TRANSACTIONCODE_500 is a Constant.
	 */
	public static final String ORDERAPI_PROCESSINGREPORT_TRANSACTIONCODE_500 = "500";	
	/**
	 *  ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_SUCCEEDED_KEY is a Constant.
	 */
	public static final String ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_SUCCEEDED_KEY = "order.processing.report.transaction.status.succeeded";	
	/**
	 * ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_PARTIALLYSUCCEEDED_KEY is a Constant.
	 */
	public static final String ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_PARTIALLYSUCCEEDED_KEY = "order.processing.report.transaction.status.partiallySucceeded";
	/**
	 * ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INPROGRESS_KEY is a Constant.
	 */
	public static final String ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INPROGRESS_KEY = "order.processing.report.transaction.status.inProgress";
	/**
	 * ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_FAILED_KEY is a constant.
	 */
	public static final String ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_FAILED_KEY = "order.processing.report.transaction.status.failed";
	/**
	 * ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INTERNAL_SERVER_ERROR_KEY is a constant.
	 */
	public static final String ORDERAPI_PROCESSINGREPORT_TRANSACTIONSTATUS_INTERNAL_SERVER_ERROR_KEY = "order.processing.report.transaction.status.internalServerError";	
	/**
	 * HOST_STRING IS http://.
	 */
	public static final String HTTP_PROTOCOL = "http://";
	
	
	/**
	 *  Business Functions Names
	 */
	/**
	 * BUSINESS FUNCTION NAME FOR View Released Orders (Seller).
	 */
	public static final String VIEW_OPEN_ORDERS_SELLER = "View Released Orders (Seller)";
	/**
	 * BUSINESS FUNCTION NAME FOR View Dispatched Orders (Seller).
	 */
	public static final String VIEW_DISPATCHED_ORDERS_SELLER = "View Dispatched Orders (Seller)";
	/**
	 * BUSINESS FUNCTION NAME FOR View Open Order Details (Seller).
	 */
	public static final String VIEW_ORDER_DETAILS_SELLER = "View Order Details (Seller)";
	/**
	 * BUSINESS FUNCTION NAME FOR Quick Dispatch Multiple Orders (Seller).
	 */
	public static final String QUICK_DISPATCH_MULTIPLE_ORDERS_SELLER = "Quick Dispatch Multiple Orders (Seller)";
	/**
	 * BUSINESS FUNCTION NAME FOR Dispatch Order/Quantity Line (Seller).
	 */
	public static final String DISPATCH_ORDER_QUANTITY_LINE_SELLER = "Dispatch Order/Quantity Line (Seller)";
	/**
	 * BUSINESS FUNCTION NAME FOR Return Order/Quantity Line (Seller).
	 */
	public static final String RETURN_ORDER_QUANTITY_LINE_SELLER = "Return Order/Quantity Line (Seller)";
	/**
	 * BUSINESS FUNCTION NAME FOR Cancel Order(s) (Seller).
	 */
	public static final String CANCEL_ORDERS_SELLER = "Cancel Order(s) (Seller)";
	/**
	 * BUSINESS FUNCTION NAME FOR Cancel Order/Quantity Line (Seller).
	 */
	public static final String CANCEL_ORDER_QUANTITY_LINE_SELLER = "Cancel Order/Quantity Line (Seller)";
	/**
	 * BUSINESS FUNCTION NAME FOR Print Open/Dispatched Order Details (Seller).
	 */
	public static final String PRINT_ORDER_DETAILS_SELLER = "Print Order Details (Seller)";
	/**
	 * BUSINESS FUNCTION NAME FOR Print Multiple Orders (Seller).
	 */
	public static final String PRINT_MULTPLE_ORDERS_SELLER = "Print Multiple Orders (Seller)";
	/**
	 * BUSINESS FUNCTION NAME FOR Print Multiple Order Details (Seller).
	 */
	public static final String PRINT_MULTIPLE_ORDER_DETAILS_SELLER = "Print Multiple Order Details (Seller)";
	
	/**
	 * BUSINESS FUNCTION NAME FOR Download Open/Dispatched Multiple Orders (Seller).
	 */
	public static final String DOWNLOAD_MULTIPLE_ORDERS_SELLER = "Download Multiple Orders (Seller)";
	
	/**
	 * BUSINESS FUNCTION NAME FOR Submit Pending Updates (Seller).
	 */
	public static final String SUBMIT_PENDING_UPDATES_SELLER = "Submit Pending Updates (Seller)";
	/**
	 * HOST_STRING ISCONTENT.
	 */
	public static final String HTTP_SERVICE_CONTENT_TYPE = "Content-Type";
	/**
	 * HOST_STRING ISCONTENT.
	 */
	public static final String HTTP_SERVICE_CONTENT = "text/plain";
	/**
	 *  HEADER.
	 */
	public static final String HEADER = PropsUtil.get("marketplace.v1.auth.header");
	
	/**
	* LOG_MESSAGE_SEPERATOR to be used for log messages.
	*/
	public static final String LOG_MESSAGE_SEPERATOR = " : ";
	/**
	* Key in properties ORDMG-E001.
	*/
	public static final String ORDMG_E001 = "ORDMG-E001";
	/**
	* Key in properties ORDMG-E002.
	*/
	public static final String ORDMG_E002 = "ORDMG-E002";
	/**
	* Key in properties ORDMG-E003.
	*/
	public static final String ORDMG_E003 = "ORDMG-E003";
	/**
	* Key in properties ORDMG-E004.
	*/
	public static final String ORDMG_E004 = "ORDMG-E004";
	/**
	* Key in properties ORDMG-E005.
	*/
	public static final String ORDMG_E005 = "ORDMG-E005";
	/**
	* Key in properties ORDMG-E006.
	*/
	public static final String ORDMG_E006 = "ORDMG-E006";
	/**
	* Key in properties ORDMG-E007.
	*/
	public static final String ORDMG_E007 = "ORDMG-E007";
	/**
	* Key in properties ORDMG-E008.
	*/
	public static final String ORDMG_E008 = "ORDMG-E008";
	/**
	* Key in properties ORDMG-E009.
	*/
	public static final String ORDMG_E009 = "ORDMG-E009";
	/**
	* Key in properties ORDMG-E010.
	*/
	public static final String ORDMG_E010 = "ORDMG-E010";
	/**
	* Key in properties ORDMG-E011.
	*/
	public static final String ORDMG_E011 = "ORDMG-E011";
	/**
	* Key in properties ORDMG-E012.
	*/
	public static final String ORDMG_E012 = "ORDMG-E012";
	/**
	* Key in properties ORDMG-E013.
	*/
	public static final String ORDMG_E013 = "ORDMG-E013";
	/**
	* Key in properties ORDMG-E014.
	*/
	public static final String ORDMG_E014 = "ORDMG-E014";
	/**
	* Key in properties ORDMG-E015.
	*/
	public static final String ORDMG_E015 = "ORDMG-E015";
	/**
	* Key in properties ORDMG-E016.
	*/
	public static final String ORDMG_E016 = "ORDMG-E016";
	/**
	* Key in properties ORDMG-E017.
	*/
	public static final String ORDMG_E017 = "ORDMG-E017";
	/**
	* Key in properties ORDMG-E018.
	*/
	public static final String ORDMG_E018 = "ORDMG-E018";
	
	/**
	* Key in properties ORDMG-E019.
	*/
	public static final String ORDMG_E019 = "ORDMG-E019";
	/**
	* Key in properties ORDMG-E020.
	*/
	public static final String ORDMG_E020 = "ORDMG-E020";
	
	/**
	* Key in properties ORDMG-E021.
	*/
	public static final String ORDMG_E021 = "ORDMG-E021";
	
	/**
	* Key in properties ORDMG-E022.
	*/
	public static final String ORDMG_E022 = "ORDMG-E022";
	/**
	* Key in properties ORDMG-E023.
	*/
	public static final String ORDMG_E023 = "ORDMG-E023";
	/**
	* Key in properties ORDMG-E024.
	*/
	public static final String ORDMG_E024 = "ORDMG-E024";
	/**
	* Key in properties ORDMG-E025.
	*/
	public static final String ORDMG_E025 = "ORDMG-E025";
	
	/**
	* Key in properties ORDMG-E026.
	*/
	public static final String ORDMG_E026 = "ORDMG-E026";
	/**
	* Key in properties ORDMG-E027.
	*/
	public static final String ORDMG_E027 = "ORDMG-E027";
	
	/**
	* Key in properties ORDMG-E028.
	*/
	public static final String ORDMG_E028 = "ORDMG-E028";
	/**
	* Key in properties ORDMG-E029.
	*/
	public static final String ORDMG_E029 = "ORDMG-E029";
	
	/**
	* Key in properties ORDMG-E030.
	*/
	public static final String ORDMG_E030 = "ORDMG-E030";
	
	/**
	* Key in properties ORDMG-E031.
	*/
	public static final String ORDMG_E031 = "ORDMG-E031";
	
	/**
	* Key in properties ORDMG-E032.
	*/
	public static final String ORDMG_E032 = "ORDMG-E032";
	
	/**
	* Key in properties ORDMG-E033.
	*/
	public static final String ORDMG_E033 = "ORDMG-E033";
	
	/**
	* Key in properties ORDMG-E034.
	*/
	public static final String ORDMG_E034 = "ORDMG-E034";
	/**
	* Key in properties ORDMG-E035.
	*/
	public static final String ORDMG_E035 = "ORDMG-E035";
	
	/**
	* Key in properties ORDMG-E036.
	*/
	public static final String ORDMG_E036 = "ORDMG-E036";
	/**
	* Key in properties ORDMG-E037.
	*/
	public static final String ORDMG_E037 = "ORDMG-E037";
	
	/**
	* Key in properties ORDMG-E038.
	*/
	public static final String ORDMG_E038 = "ORDMG-E038";
	/**
	* Key in properties ORDMG-E037.
	*/
	public static final String ORDMG_E039 = "ORDMG-E039";
	/**
	* Key in properties ORDMG-E040.
	*/
	public static final String ORDMG_E040 = "ORDMG-E040";
	/**
	* Key in properties ORDMG-E041.
	*/
	public static final String ORDMG_E041 = "ORDMG-E041";
	/**
	* Key in properties ORDMG-E042.
	*/
	public static final String ORDMG_E042 = "ORDMG-E042";
	/**
	* Key in properties ORDMG-E043.
	*/
	public static final String ORDMG_E043 = "ORDMG-E043";
	/**
	* Key in properties ORDMG-E044.
	*/
	public static final String ORDMG_E044 = "ORDMG-E044";
	/**
	* Cancellation / Primary Return Reason code.
	*/
	public static final String REASON_CODE = "reason.code.";
	
	/**
	* Seconary Return Reason code.
	*/
	public static final String SECONDARY_REASON_CODE = "secondary.reason.code.";
	/**
	* Navigating from OrderDetails page.
	*/
	public static final String ORDER_DETAILS_PAGE = "orderDetails";
	/**
	* Navigating from OrderDetails page.
	*/
	public static final String COLLECTED = "Collected";
	/**
	* Navigating from OrderDetails page.
	*/
	public static final String AWAIT = "Checked In Awaiting Collection";
	/**
	* Yodl PDF file Location.
	*/
	public static final String YODL_LABEL_PATH = PropsUtil.get("file.yodl.location");
	/**
	* Yodl PDF file TrackingId.
	*/
	public static final String STRING_TRACKING_ID = "trackingId";
	/**
	* Yodl PDF file TrackingId.
	*/
	public static final String DDMMYY ="yyyy-MM-dd HH:mm:ss";
	/**
	* Yodl PDF file TrackingId.
	*/
	public static final String DATEFRMT = "yyyy-MM-dd";
	/**
	* Yodl PDF file TrackingId.
	*/
	public static final String PDFLOCATION = "file.yodl.location";
	
	/**
	* Yodl proxy exists.
	*/
	
	public static final String PROXY_EXISTS ="yodl.proxy.isExists";
	
	/**
	* Yodl proxy exists.
	*/
	
	public static final String SERVER_PROXY ="yodl.proxy.httpServer";
	/**
	* Yodl proxy exists.
	*/
	
	public static final String SERVER_PORT ="yodl.proxy.httpPort";
	
	/**
	 * constant that defines status for CNC.
	 */
	public static final String IS_ERROR = "isError";
	/**
	 * constant that defines status for Yodel Account authentication failed.
	 */
	public static final String IS_AUTH_FAIL = "isAuthFail";
	/**
	 * constant that defines status for CNC.
	 */
	public static final String IS_FIRST = "isFirst";
	/**
	 * Message for Error scenario.
	 */
	public static final String ERR_MSG = "errMsg";
	/**
	 * Is response null.
	 */
	public static final String IS_RES_NULL = "isResNull";
	/**
	 * constant that defines status for CNC.
	 */
	public static final String IS_CALENDAR = "isCalendar";
	/**
	 * constant that defines today date for CNC.
	 */
	public static final String IS_DATE = "isDate";
	/**
	 * constant that defines content type.
	 */
	public static final String APP_PDF ="application/pdf";
	
	/**
	 * constant that defines content disposition.
	 */
	public static final String CONT_DISP ="Content-Disposition";
	/**
	 * constant that defines about expiry.
	 */
	public static final String EXPIRE ="Expires";
	/**
	 * constant that defines PDF format.
	 */
	public static final String PDF =".pdf";

	/**
	 * no delivery instructions.
	 */
	public static final String NO_DELIVERY_INSTRUCTIONS = "No courier instructions";
	
	/**
	 * This returns ON or OFF to access BULK QUICK DISPATCH for Click n collect.
	 */
	public static final String RENDER_BULK_CNC_FLAG = PropsUtil.get("render.bulk.click.and.collect");

	/**
	 * order id.
	 */
	public static final String ORDERID = "orderId";

	/**
	 * search type.
	 */
	public static final String SEARCH_TYPE = "searchType";

	/**
	 * "NO_ORDERS_FOUND".
	 */
	public static final String NO_ORDERS_FOUND = "NO_ORDERS_FOUND";	
	
	/**
	 * "NO_ORDERS_FOUND".
	 */
	public static final String SPLITTER = "[\\,\\s]+";	
	
	/**
	 * "NO_ORDERS_FOUND".
	 */
	public static final String COMMA = ",";	
	
	/**
	 * "NO_ORDERS_FOUND".
	 */
	public static final String CNC = "Click & Collect";
	
	/**
	 * "NOT APPLICABLE C&C".
	 */
	public static final String NOT_APPLICABLE = "N/A";	
}
