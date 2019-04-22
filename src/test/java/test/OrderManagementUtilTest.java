/*
package test;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;
import com.tesco.mhub.order.util.PortletProps;
import com.tesco.mhub.BusinessFunctionStatusChecker;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.dao.OrderDAO;
import com.tesco.mhub.order.daoimpl.OrderDAOImpl;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.retrieve.order.RetrieveOrders;
import com.tesco.mhub.order.retrieve.order.Shipment;
import com.tesco.mhub.order.service.OrderService;
import com.tesco.mhub.order.util.OrderManagementUtil;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.WindowState;
import javax.portlet.filter.PortletRequestWrapper;
import javax.servlet.http.Cookie;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class OrderManagementUtilTest {
	
	@Deployment
	public static WebArchive createDeploymentWar() {
	    File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve("org.springframework:spring-context:3.2.6.RELEASE",
	    		"org.springframework:spring-web:3.2.6.RELEASE",
	    		"com.liferay.portal:portal-service:6.1.30",
	    		"org.springframework:spring-webmvc-portlet:3.2.6.RELEASE",
	    		"com.liferay.portal:util-bridges:6.1.30",
	    		"com.liferay.portal:util-taglib:6.1.30",
	    		"com.liferay.portal:util-java:6.1.30").withTransitivity().asFile();
	    WebArchive war = ShrinkWrap.create(WebArchive.class, "OrderManagementUtilTest.war")
				.addClasses(OrderManagementUtil.class,LogFactoryUtil.class,OrderConstants.class,PortletProps.class
						,RetrieveOrders.class,OrderDAO.class,Shipment.class,PropsUtil.class,ThemeDisplay.class)
				.addClass(com.tesco.mhub.order.util.OrderHelperUtil.class)
				.addAsLibraries(libs)
	            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				// this is needed to make the test jar use Liferay portal
				// depndencies
				.addAsManifestResource("jboss-deployment-structure.xml");
	    return war;
	}
	*//**
	 * logger object.
	 *//*
	private static Log log = LogFactoryUtil.getLog(OrderManagementUtil.class);
	
	@Test
	public void testSetPermissions() throws PortalException, SystemException{
	   
		//set all the boolean attribute for aal the roles here
		PortletRequest request = new PortletRequest() {
			
			@Override
			public void setAttribute(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeAttribute(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isWindowStateAllowed(WindowState arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isUserInRole(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isSecure() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isRequestedSessionIdValid() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isPortletModeAllowed(PortletMode arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public WindowState getWindowState() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getWindowID() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Principal getUserPrincipal() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getServerPort() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getServerName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getScheme() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getResponseContentTypes() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getResponseContentType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRequestedSessionId() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRemoteUser() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String[]> getPublicParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getPropertyNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getProperty(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getProperties(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String[]> getPrivateParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletPreferences getPreferences() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletSession getPortletSession(boolean arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletSession getPortletSession() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletMode getPortletMode() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortalContext getPortalContext() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String[] getParameterValues(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getParameterNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String[]> getParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getParameter(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<Locale> getLocales() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Locale getLocale() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Cookie[] getCookies() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContextPath() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getAuthType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getAttribute(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
			 	//open orders page - call these variables on page
		
				long companyId = PortalUtil.getDefaultCompanyId();		
				long userID = UserLocalServiceUtil.getDefaultUserId(companyId);
				boolean isViewReleasedOrders = BusinessFunctionStatusChecker.businessFunctionCheck(userID, OrderConstants.VIEW_OPEN_ORDERS_SELLER);
				request.setAttribute("isViewReleasedOrders", isViewReleasedOrders);
				
				boolean isMultiOdrPrintBtnVisible = BusinessFunctionStatusChecker.businessFunctionCheck(userID, OrderConstants.PRINT_MULTPLE_ORDERS_SELLER);
				request.setAttribute("isMultiOdrPrintBtnVisible", isMultiOdrPrintBtnVisible);
				
				boolean isOrderDetailsPrintBtnVisible = BusinessFunctionStatusChecker.businessFunctionCheck(userID, OrderConstants.PRINT_ORDER_DETAILS_SELLER);
				request.setAttribute("isOrderDetailsPrintBtnVisible", isOrderDetailsPrintBtnVisible);
				
				boolean isQuickDispatchMultiOdrsVisible = BusinessFunctionStatusChecker.businessFunctionCheck(userID, OrderConstants.QUICK_DISPATCH_MULTIPLE_ORDERS_SELLER);
				request.setAttribute("isQuickDispatchMultiOdrsVisible", isQuickDispatchMultiOdrsVisible);
				
				boolean isDwnldMultiOrdersVisible = BusinessFunctionStatusChecker.businessFunctionCheck(userID, OrderConstants.DOWNLOAD_MULTIPLE_ORDERS_SELLER);
				request.setAttribute("isDwnldMultiOrdersVisible", isDwnldMultiOrdersVisible);
				
				boolean isViewDispatchedOrders = BusinessFunctionStatusChecker.businessFunctionCheck(userID, OrderConstants.VIEW_DISPATCHED_ORDERS_SELLER);
				request.setAttribute("isViewDispatchedOrders", isViewDispatchedOrders);
				
				boolean isReturnOdrLinesVisible = BusinessFunctionStatusChecker.businessFunctionCheck(userID, OrderConstants.RETURN_ORDER_QUANTITY_LINE_SELLER);
				request.setAttribute("isReturnOdrLinesVisible", isReturnOdrLinesVisible);

				//check this variable on open/dispatched order details page - for rendering the details
				boolean isViewOrderDetails = BusinessFunctionStatusChecker.businessFunctionCheck(userID, OrderConstants.VIEW_ORDER_DETAILS_SELLER);
				request.setAttribute("isViewOrderDetails", isViewOrderDetails);
				
				//check this variable on open/dispatched order details page - for print fn
				boolean isMultiOrderDetailsPrintBtnVisible = BusinessFunctionStatusChecker.businessFunctionCheck(userID, OrderConstants.PRINT_MULTIPLE_ORDER_DETAILS_SELLER);
				request.setAttribute("isMultiOrderDetailsPrintBtnVisible", isMultiOrderDetailsPrintBtnVisible);
				
				//check this variable on open order details page - for dispatch order lines fn
				boolean isDispatchOdrLinesVisible = BusinessFunctionStatusChecker.businessFunctionCheck(userID, OrderConstants.DISPATCH_ORDER_QUANTITY_LINE_SELLER);
				request.setAttribute("isDispatchOdrLinesVisible", isDispatchOdrLinesVisible);
				
				//check this variable on open order details page - for cancel order lines fn
				boolean isCancelOdrLinesVisible = BusinessFunctionStatusChecker.businessFunctionCheck(userID, OrderConstants.CANCEL_ORDER_QUANTITY_LINE_SELLER);
				request.setAttribute("isCancelOdrLinesVisible", isCancelOdrLinesVisible);
				
				boolean isCancelOdrsVisible = BusinessFunctionStatusChecker.businessFunctionCheck(userID, OrderConstants.CANCEL_ORDERS_SELLER);
				request.setAttribute("isCancelOdrsVisible", isCancelOdrsVisible);
				
				//keep this variable on both the pages
				boolean isSubmitFinalOdrVisible = BusinessFunctionStatusChecker.businessFunctionCheck(userID, OrderConstants.SUBMIT_PENDING_UPDATES_SELLER);
				request.setAttribute("isSubmitFinalOdrVisible", isSubmitFinalOdrVisible);
				
				//OrderManagementUtil.setPermissions(request);
	}

	@Test
	public void returnDates(){
		Iterator<Order> dispOrders = new Iterator<Order>() {

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Order next() {
				// TODO Auto-generated method stub
				Order order = new Order();
				order.setOrderId("xyz");
				order.setCustomerOrderNumber("xyz");
				order.setCustomerFirstName("xyz");
				order.setCustomerLastName("xyz");
				order.setNoOfLines(10);
				order.setOrderStatus("xyz");
				order.setDeliveryOption("xyz");
				Date orderPlacedDate = new Date();
				order.setOrderPlacedDate(orderPlacedDate);
				order.setCustomerEmail("xyz");
				order.setCustomerMobile("xyz");
				order.setCustomerDayTelephone("xyz");
				order.setCustomerEveningTelephone("xyz");
				order.setDeliveryCost(1234.0);
				order.setShipToAddress("xyz");
				order.setShipToMobile("xyz");
				order.setOrderValue(1234.0);
				order.setServiceActionType("xyz");
				order.setTrackingId("xyz");
				order.setTrackingURL("xyz");
				order.setCarrierName("xyz");
				return new Order();
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub
				
			}
		};
		boolean reverse = true;
		
		List<Date> allDate = new ArrayList<Date>();
		List<String> placedDateList = new ArrayList<String>();
		
		while(dispOrders.hasNext()){
			Order ds = dispOrders.next();
			allDate.add(ds.getOrderPlacedDate());
		}
		Collections.sort(allDate);
		if(reverse){
			Collections.reverse(allDate);
		}
		for(Date dt1:allDate){
			
			String date1 = dt1.toString();
			date1 = date1.substring(0, 10);
			
			if(!placedDateList.contains(date1)){
				placedDateList.add(date1);
			}
		}
		List<String> list = placedDateList;
		OrderManagementUtil.returnDates(dispOrders, reverse);
	}
	
	@Test
	public void getSellerId(){
		OrderService orderservice;
		PortletRequest request = new PortletRequest() {
			
			@Override
			public void setAttribute(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeAttribute(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isWindowStateAllowed(WindowState arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isUserInRole(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isSecure() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isRequestedSessionIdValid() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isPortletModeAllowed(PortletMode arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public WindowState getWindowState() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getWindowID() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Principal getUserPrincipal() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getServerPort() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getServerName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getScheme() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getResponseContentTypes() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getResponseContentType() {
				// TODO Auto-generated method stub
				return "success";
			}
			
			@Override
			public String getRequestedSessionId() {
				// TODO Auto-generated method stub
				return "123";
			}
			
			@Override
			public String getRemoteUser() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String[]> getPublicParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getPropertyNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getProperty(String arg0) {
				// TODO Auto-generated method stub
				return "property";
			}
			
			@Override
			public Enumeration<String> getProperties(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String[]> getPrivateParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletPreferences getPreferences() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletSession getPortletSession(boolean arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletSession getPortletSession() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletMode getPortletMode() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortalContext getPortalContext() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String[] getParameterValues(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getParameterNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String[]> getParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getParameter(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<Locale> getLocales() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Locale getLocale() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Cookie[] getCookies() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContextPath() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getAuthType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getAttribute(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		long sellerId = 0; //long sellerId = 1000033;   //long sellerId = 1013632;
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			long companyId = PortalUtil.getDefaultCompanyId();
			User user = UserLocalServiceUtil.getDefaultUser(companyId);
			
			List<Organization> orgs = user.getOrganizations();
			
			
			long classNameId = ClassNameLocalServiceUtil.getClassNameId(Organization.class.getName());
			ExpandoTable table = ExpandoTableLocalServiceUtil.getDefaultTable(companyId,classNameId);
			ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.getColumn(table.getTableId(), "sellerId");
			
			if(orgs.size()>0){
				Organization org = orgs.get(0);
				ExpandoValue expandoValue = ExpandoValueLocalServiceUtil.getValue(table.getTableId(), expandoColumn.getColumnId(), org.getOrganizationId());
				if(Validator.isNotNull(expandoValue)) {
					sellerId = expandoValue.getLong();
				}
			}
		} catch (PortalException e) {
		} catch (SystemException e) {
		}
		//OrderManagementUtil.getSellerId(request);
	}
	
	@Test
	public void test_GetOrgNameBySellerId() throws PortalException, SystemException{
		
		long companyId = PortalUtil.getDefaultCompanyId();
		User user = UserLocalServiceUtil.getDefaultUser(companyId);
		
		try {
			OrderManagementUtil.getOrgNameBySellerId(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	@Test
	public void test_(){
		
		PortletRequest portletRequest = new PortletRequest() {
			
			@Override
			public void setAttribute(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeAttribute(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isWindowStateAllowed(WindowState arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isUserInRole(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isSecure() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isRequestedSessionIdValid() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isPortletModeAllowed(PortletMode arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public WindowState getWindowState() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getWindowID() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Principal getUserPrincipal() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getServerPort() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getServerName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getScheme() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getResponseContentTypes() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getResponseContentType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRequestedSessionId() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRemoteUser() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String[]> getPublicParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getPropertyNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getProperty(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getProperties(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String[]> getPrivateParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletPreferences getPreferences() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletSession getPortletSession(boolean arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletSession getPortletSession() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortletMode getPortletMode() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PortalContext getPortalContext() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String[] getParameterValues(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getParameterNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String[]> getParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getParameter(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<Locale> getLocales() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Locale getLocale() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Cookie[] getCookies() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContextPath() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getAuthType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getAttribute(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	System.out.println(portletRequest);
	}
	
}*/
