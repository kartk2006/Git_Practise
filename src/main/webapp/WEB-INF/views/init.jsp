<%@page import="java.util.List"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.tesco.mhub.order.util.OrderBasketUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui"  prefix="aui" %>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@page import="com.tesco.mhub.order.service.OrderBasketServiceFactory"%>
<%@page import="com.tesco.mhub.order.model.Order"%>
<%@page import="com.tesco.mhub.order.service.OrderBasketService"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>

<portlet:defineObjects/>

<%
OrderBasketService basktService = OrderBasketServiceFactory.getOrderBasketService();
List<Order> basktOrder = basktService.getOrderBasket(renderRequest).getOrdersForUpdate();
int size = 0;
if(basktOrder!=null){
	size =basktOrder.size();
}
pageContext.setAttribute("basketSize",size);
%>

<script type="text/javascript">
function confirmLogout(event) {
	var size = ${basketSize};
	if (size>0){
	 if (event.preventDefault) { 
		 	event.preventDefault(); 
		 }
	 else { 
		 event.returnValue = false; 
		 } 
		    $("#dialog-confirm").html("You will lose all your order updates that have not been submitted into Tesco. Do you wish to continue?");
	
		    $("#dialog-confirm").dialog({
		        resizable: false,
		        modal: true,
		        title: "Confirm final submit of orders",
		        height: 250,
		        width: 400,
		    buttons: {
	            "Cancel": function () {
	                $(this).dialog('close');
	            },
	            "Confirm": function () {
	                $(this).dialog('close');
	              window.location.href = "/c/portal/logout";
	            }
	        }
		    });
	}
	 else{
		 window.location.href = "/c/portal/logout";
	 }

}
	
</script>