
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script src="<%=request.getContextPath() %>/js/jquery.printPage.js"></script>
<style type="text/css">
@media print {
	body * {
		visibility: hidden;
	}
	.print * {
		visibility: visible;
	}
}


div.footnotes { 
  page-break-after:always; 
}

*{
	padding:0px;
	margin:0px;
}
body.portal-popup {
	padding:0px;
}
#printContent{
	padding:10px 10px 10px 20px;
	margin:0px auto;
	width:100%;
}

hr{
	border: 1px solid #000000;
    float: left;
    margin-bottom: 3px;
    margin-left: 20px !important;
    margin-right: 20px !important;
    margin-top: 0 !important;
    width: 90%;
}
#leftDivWidTable{
	padding-bottom:40px;
}
tbody{
	border:none;
}
tr{
	border-bottom: 1px solid #C7C6C4;
}
th, td{
 	padding: 0 5px!important;
    word-wrap: break-word !important;
}
th div{
	border: 1px solid rgb(0, 0, 0);
	border-radius: 80px;
	text-align:center !important;
	font-weight:normal !important;
	width:50px !important;
}

.phone-label-print{
	display: inline-block;
	width: 25%;
}

.zero-results-found{
font-size: 1.4em;
color:#000;
}

.no-results-found-suggestions
{
font-size:1.3em;
margin-top:30px;
}

.no-results-found-suggestions-list{
color:#000;
margin-top:10px;
}
</style>

</head>
<body>
<c:set var="orderDetailsSize" value="${fn:length(sessionDispatchedOrders)}"/>
<c:if test="${orderDetailsSize > 0}">
<c:forEach items="${sessionDispatchedOrders}" var="orderID">
 
	<div id="printContent" class="print">
		<div>
			<div style="float: left;width:auto;margin-top:20px;font-size:17px">ORDER ID:&nbsp;<c:out  value="${orderID.value.getOrderId()}" />
			<c:set var="custSellerID"  value="${orderID.value.getOrderId()}"/>
			 </div>
			<div style="float: right;width:238px;height:50px">
				<img src="<%=request.getContextPath() %>/images/tesco_logo_new.png" height="100%" width="100%" />
			</div>
			<br />
			
			<div style="width:100%;float:left;margin-top:15px;padding-bottom:30px">
			<div style="float: left;width:48%">
				<b>Customer:</b> <br />
				<div style="text-align: left;">
					<c:out value="${orderID.value.getCustomerFirstName()} ${orderID.value.getCustomerLastName()}" /> <br /> 
					<%-- <c:forEach items="${orderID.value.getOrderLineItems()}" var="orderLineItems">
					<c:set var = "custAddr" value="${orderLineItems.value.getCustomerAddress()}"/>
					</c:forEach>
					<c:out value="${custAddr}" /> <br /> --%>
					<c:out value="${orderID.value.getShipToAddress()}" /><br />
					<span class="phone-label-print">Daytime Tel:</span> 
					<span><c:out value="${orderID.value.getCustomerDayTelephone()}" /></span><br />
					<span class="phone-label-print">Evening Tel:</span>
					<span><c:out value="${orderID.value.getCustomerEveningTelephone()}" /></span><br />
					<span class="phone-label-print">Mobile:</span>
					<span><c:out value="${orderID.value.getCustomerMobile()}" /></span>
				</div>
			</div>

			<div style="float: right;width:48%">
				<b>Shipping And Delivery:</b> <br />
				<div style="text-align: left;">
					<c:forEach items="${orderID.value.getOrderLineItems()}" var="orderLineItems">
					<c:set var = "shipToFirstName" value="${orderLineItems.value.getShipToFirstName()}"/>
					<c:set var = "shipToLastName" value="${orderLineItems.value.getShipToLastName()}"/>
					<c:set var = "shipToHomeTelDay" value="${orderLineItems.value.getShipToHomeTelDay()}"/>
					<c:set var = "shipToHomeTelNight" value="${orderLineItems.value.getShipToHomeTelNight()}"/>
					</c:forEach>
					<c:out value="${shipToFirstName}" /> &nbsp;<c:out value="${shipToLastName}" /><br />
					<c:out value="${orderID.value.getShipToAddress()}" /><br />
					<span class="phone-label-print">Daytime Tel:</span>
					<span><c:out value="${shipToHomeTelDay}" /></span><br />
					<span class="phone-label-print">Evening Tel:</span>
					<span><c:out value="${shipToHomeTelNight}" /></span><br />
					<span class="phone-label-print">Mobile:</span>
					<span><c:out value="${orderID.value.getShipToMobile()}" /></span><br />
					<span class="phone-label-print">Instructions:</span>
					<span><c:out value="${orderID.value.getDeliveryInstructions()}" /></span>
				</div> 
			</div>
			
			</div>
			
		</div>
		<hr/><hr/>
		<div style="float: left; padding-top: 30px;width:100%" class="print">
			<div id="leftDivWidTable">
				<div>
					<b style="float: left">Order Details :</b><br />
				</div>
				<div>
					 <%-- Order Placed : <c:out value="${orderID.value.getOrderPlacedDate()}" />  <br /> --%>
					<c:forEach items="${displayOrderMap}" var="orderDates">
						<c:if test="${orderDates.key eq orderID.key}">
					 		Order Placed : <%-- <c:out value="${orderDates.value}" /> --%>
					 		<c:set var="string1" value="${orderDates.value}" />
					 		<c:set var="startingDate" value="${fn:substring(string1, 0, 2)}" />
					 		
					 		<c:set var="endDigit" value="${fn:substring(string1, 1, 2)}" />
					 		<c:set var="remainingDate" value="${fn:substring(string1, 3, 11)}" />
					 		
					 		<c:choose>
					 			<c:when test="${startingDate==11 || startingDate==12 || startingDate==13}">
					 				<c:out value="${startingDate}"/><sup>th</sup><c:out value="${remainingDate}"/>
					 			</c:when>
					 			<c:otherwise>
					 				<c:if test="${endDigit == 1}">
							 			<c:out value="${startingDate}"/><sup>st</sup><c:out value="${remainingDate}"/>
							 		</c:if>
							 		<c:if test="${endDigit == 2}">
							 			<c:out value="${startingDate}"/><sup>nd</sup><c:out value="${remainingDate}"/>
							 		</c:if>
							 		<c:if test="${endDigit == 3}">
							 			<c:out value="${startingDate}"/><sup>rd</sup><c:out value="${remainingDate}"/>
							 		</c:if>
							 		<c:if test="${endDigit >= 4 && endDigit <= 9}">
							 			<c:out value="${startingDate}"/><sup>th</sup><c:out value="${remainingDate}"/>
							 		</c:if> 
							 		<c:if test="${endDigit == 0}">
							 			<c:out value="${startingDate}"/><sup>th</sup><c:out value="${remainingDate}"/>
							 		</c:if> 
					 			</c:otherwise>
					 		</c:choose>
					 	</c:if>
					 </c:forEach>
				</div>
				<div>
					 Delivery Option : <c:out value="${orderID.value.getDeliveryOption()}" />
				</div>
			</div>
			<div class="print" style="float: left;width:100%">
				<div style="float: left;width:100%">
					<b style="float: left">Order Lines:</b>
				</div>
			</div>
		</div>
		
			<div>
				<table style="float: left;margin-top:10px;width:100%;margin-bottom:30px" cellpadding="2">
					<tr style="border:none">
						<th width="5%">Line</th>
						<th width="10%">SKU</th>
						<th width="20%">Description</th>
						<th width="10%">Status</th>
						<th width="15%">Ordered</th>
						<th width="15%"><div>DIS</div></th>
						<th width="15%"><div>CANC</div></th>
						<th width="15%"><div>RET</div></th>
					</tr>
					<c:forEach items="${orderID.value.getOrderLineItems()}" var="orderLineDetails">
						<tr>
							<td><c:out value="${orderLineDetails.value.getOrderLineNumber()}" /></td>
							<td><c:out value="${orderLineDetails.value.getSku()}" /></td>
							<td><c:out value="${orderLineDetails.value.getDescription()}" /></td> 						
							<td><c:out value="${orderLineDetails.value.getLineItemStatus()}" /></td> 
							<td><c:out value="${orderLineDetails.value.getOrderedQuantity()+orderLineDetails.value.getCancelledQuantity()}" /></td>
							<td><c:out value="${orderLineDetails.value.getDispatchedQuantity()}" /></td>							
							<td><c:out value="${orderLineDetails.value.getCancelledQuantity()}" /></td>
							<td><c:out value="${orderLineDetails.value.getReturnedQuantity()}" /></td>		
						</tr>	
					</c:forEach>				
				</table>
			</div>
	</div>	
	<div class="footnotes"></div>	
</c:forEach> 
</c:if>
<c:if test="${orderDetailsSize < 1}">
<div class='zero-results-found'>There are no records to print.</div>
</c:if>	
</body>
</html>