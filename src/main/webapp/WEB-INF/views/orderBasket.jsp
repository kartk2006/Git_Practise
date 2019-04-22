<%@page import="com.tesco.mhub.order.service.OrderBasketServiceFactory"%>
<%@page import="com.tesco.mhub.order.model.Order"%>
<%@page import="java.util.List"%>
<%@page import="com.tesco.mhub.order.service.OrderBasketService"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<portlet:defineObjects/>
<%
OrderBasketService basketService = OrderBasketServiceFactory.getOrderBasketService();
List<Order> basketOrder = basketService.getOrderBasket(renderRequest).getOrdersForUpdate();
pageContext.setAttribute("basketOrder", basketOrder);
%>
<script type="text/javascript">

  function removeItem(url) {
	  //alert("Comming ::"+url);
	  window.location.href = url;
  }
  
  $(document).ready(function(){
	 	$('#btnSubmitFinalUpdate').click(function(event){
		  return fnOpenNormalDialog(event);
		});
	  
  });
	
</script>

<style type="text/css">
	.orderBasketSpacing{
	padding-right:5px;
	padding-left:10px;
	}
	
</style>


<section class="col-xs-4 right-section fltL">
    <div class="notification-box">
        <div class="notification-header row">
            <div class="col-xs-16 margin-bottom-10 heavyText">
                <div class="pull-left">UPDATES</div>
                <div class="pull-right badge"></div>    
            </div>
            <div class="col-xs-16"><span>Updates are listed in chronological order</span></div>
        </div>
        <div class="row notification-body">
         <c:set var="index" value="${0}"></c:set>
        <c:forEach items= "${basketOrder}" var = "basketOrder">
            <div class="col-xs-16 margin-bottom-10" style="padding-left:4px;">
                <div class="col-xs-2 red-font" style="padding:0px;">
                    <portlet:actionURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="removeItemsFromBasketURL">
						<portlet:param name="index" value="${index}"/>
						<portlet:param name="action" value="removeItemAction"/>
				    </portlet:actionURL>
                    <span class="glyphicon glyphicon-remove-circle hand-cursor" onclick="removeItem('${removeItemsFromBasketURL}')"></span>
                </div>
                
                <c:if test="${basketOrder.serviceActionType eq 'dispatch'}">
                	<c:set var="orderServiceAction"  value="Disp"/>
                </c:if>
                <c:if test="${basketOrder.serviceActionType eq 'cancel'}">
                	<c:set var="orderServiceAction"  value="Canc"/>
                </c:if>
                <c:if test="${basketOrder.serviceActionType eq 'return'}">
                	<c:set var="orderServiceAction"  value="Ret"/>
                </c:if>
                <div class="col-xs-4 margin-top-10" style="padding: 0px 10px 0px 0px !important;"><span class="badge">
                	<c:out value="${orderServiceAction}"/></span>
                </div>
                <div class="col-xs-8 orderBasketSpacing fltL" style="padding:10px 0px 0px 11px; width:62%"> ${basketOrder.orderId} <div class="orderLines">(${fn:length(basketOrder.orderLineItems)}L)</div>  </div>
            </div>
            <c:set var="index" value="${index+1}"></c:set>
          </c:forEach>
        </div>
        <div class="row notification-footer">
            <button id="btnSubmitFinalUpdate" class="btn primary-CTA-alt col-xs-16">Submit Updates</button>
        </div>
    </div>
</section>
<style>
.badge{
padding:3px 12px !important;
}
</style>