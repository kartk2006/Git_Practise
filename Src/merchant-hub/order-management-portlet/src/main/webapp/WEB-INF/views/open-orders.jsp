<%@page import="java.util.List"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.tesco.mhub.order.util.OrderBasketUtil"%>
<%@page import="com.tesco.mhub.order.serviceimpl.OrderBasketServiceImpl"%>
<%@page import="com.tesco.mhub.order.service.OrderBasketService"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.tesco.mhub.order.model.OrderBasket"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui"  prefix="aui" %>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ include file="/WEB-INF/views/init.jsp"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>

<portlet:defineObjects/>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title></title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
<%--     <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-latest.min.js"></script> --%>
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jquery-ui.css">

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
    
        <link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap-datetime-min.css"/>
      <script src="<%=request.getContextPath() %>/js/vendor/moment.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/vendor/bootstrap-datetimepicker.js"></script>
    
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js">
                                                                                                                                                                                                            
    </script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js">
                                                                                                                                                                                                            
    </script>
    <![endif]-->
  
<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="renderDisplayDispatchOrders">
 <portlet:param name="action" value="renderDisplayDispatchOrders"/> 
</portlet:renderURL>

<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="quickOrderDispatchURL">
	<portlet:param name="action" value="renderQuickDispatchOpenOrders" />
</portlet:renderURL>

<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="selectIDs">
	<portlet:param name="action" value="selectIDs"></portlet:param>
</portlet:renderURL>

<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="viewPrintURL">
	<portlet:param name="action" value="viewPrintURL" />
</portlet:renderURL>

<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="dateWiseOrderDisplayURL">
   <portlet:param name="action" value="datewiseOrderDisplay"/>
   <portlet:param name="ordStatus" value="open-orders"/>
</portlet:renderURL>  

<portlet:actionURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="commitOrders">
	<portlet:param name="action" value="commitOrders"/>
</portlet:actionURL>


<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="clearFilterURL">
    <portlet:param name="action" value="clearFilter"/>
    <portlet:param name="fromPage" value="open-orders"/>
</portlet:renderURL>
<style>
.portlet-msg-success{
margin-top:50px;
}
.btn-primary:hover, .btn-primary:focus {
background-image: none;
}
.table .thead h4 {
margin-left: -4px!important;
}
h2.col-xs-3 {
font-size: 1.3em;
}
</style>
<script type="text/javascript">
 function dispatchOrders() {
    var checked,i, len, inputs = document.frm1.getElementsByTagName("input"); 
    var arrayOfStatuses = new Array();
	var arrayOfCheckedOnes = new Array();
	checked = 0;
	len = inputs.length;
	
 		 for (i = 0; i < len; i++) {
      			if (inputs[i].type === "checkbox" && inputs[i].checked){
      				if(inputs[i].value != ""){
      					checked++;
      					arrayOfCheckedOnes.push(inputs[i].value);
      					}		
         		 };
  			}
    	 if(checked == 0 )
	 		{
	 			alert("select at least one order!!!");
	 			return false;						
	 		} 	
    	var selectedIDS = arrayOfCheckedOnes.length;
    	for(var j = 0 ; j < selectedIDS ; j++){
    		var currentID = arrayOfCheckedOnes[j];
    		/* var optionSelected = "opt-" + currentID; */
    		var statusSelected = "status-" + currentID;
     		var status = document.getElementById(statusSelected).value;
    		arrayOfStatuses.push(status);
    	}
	     for(var k = 0 ; k < arrayOfStatuses.length ; k++ ){
	    	 for(var l = 0; l < arrayOfStatuses.length ; l++ )
	    	 {
				if(arrayOfStatuses[k] !== arrayOfStatuses[l]){
					//*********************** defect id = ************
					alert("Orders must be in the same status to proceed. Please review your selection.");
					return false;
				}
 			}
	     }
	     var url = "<%=quickOrderDispatchURL%>";
		   document.getElementById("frm1").action = url;
		   document.getElementById("frm1").submit();  
	     return true;
	    
 }
 
 function fnOpenNormalDialog(event) {
	 var size = ${basketSize};
		if (size>0){
	 if (event.preventDefault) { 
		 event.preventDefault(); 
		 }
	 else { 
		 event.returnValue = false; 
		 } 

		//alert("inside overlay!!!");
	    $("#dialog-confirm").html("This update is final. Do you confirm (Confirm/Cancel?)");


	    // Define the Dialog and its properties.
	    
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
                 var url = "<%=commitOrders%>";
	         	   document.getElementById("frm1").action = url;
	         	   document.getElementById("frm1").submit();   
                
            }
        }
	    });
		}else{
			 return false;
		}
	}

 function cancel () { return false; };
 
 function disableDivs() {
	 	
	 	//status-${ordDetails.value.getOrderLineNumber()}
	 	$("input:checkbox[name='selectedOrderNumbers']").each(function(){
	   			var lineNo = $(this).val();
	   			//alert("lineNo " + lineNo);
	 			var status = $("#status-"+lineNo).val();
	 			if(status === "Pending"){
	 				
	 				$("#div-"+lineNo).attr('disabled','disabled');
	 				var currentDiv = "div-"+lineNo; 
	 				var nodes = document.getElementById(currentDiv).getElementsByTagName('*');
	    			//alert("number of nodes = "+nodes.length);
	    		    for (var k = 0; k < nodes.length; k++) {
	    		        nodes[k].setAttribute('disabled', true);
	    		        nodes[k].onclick = cancel;
	    		    } 
	 			}
	 	});
		   
		 }
	 
	 $(document).ready(function(){
		 //disableDivs();
	 });
 
</script>
<%
Calendar cal = Calendar.getInstance();

  int currMonth = cal.get(Calendar.MONTH);
  int currYear = cal.get(Calendar.YEAR);
  int currDay = cal.get(Calendar.DAY_OF_MONTH);
  int currHours = cal.get(Calendar.HOUR);
  int currMinutes = cal.get(Calendar.MINUTE);
  int currSeconds = cal.get(Calendar.SECOND);
 
%>
 <script type="text/javascript">
   var time = new Date(<%=currYear%>,<%=currMonth %>,<%=currDay%>,<%=currHours%>,<%=currMinutes%>,<%=currSeconds%>,0);
   time.setDate(time.getDate()-83);
   $(function() {
	 $('#datetimepicker4').datetimepicker({
	  pickTime: false, 
	  minDate: time,
	  maxDate: new Date(<%=currYear%>,<%=currMonth %>,<%=currDay%>,<%=currHours%>,<%=currMinutes%>,<%=currSeconds%>,0) ,
	});
  });   


</script>

<script type="text/javascript">
	$(document).ready(function() {
		var checked = 0;
		$("#selectAll").click(function() {
			var checkboxes = $("input[type='checkbox']");
			if ($(this).is(':checked')) {
				//checkboxes.prop('checked', true);
				$("input:checkbox[name='selectedOrderNumbers'].unchecked").each(function()
	 					{
						var orderNo = $(this).val();
						var status = $("#status-"+orderNo).val();
						if(status != "Pending"){
							$("#"+orderNo).prop('checked', true);
						}
	 					});
				
				$("input:checkbox[name='selectedOrderNumbers']:checked").each(function()
	 					{
						var orderNo = $(this).val();
	 					$("#updatedQuantity-"+orderNo).css('display', 'block');
	 					 $("#updatedQuantity-"+orderNo).attr('disabled', false);
	 					});
				
			} else {
				checkboxes.prop('checked', false);
			};
		});
		var remaining = $("input[type='checkbox'].unchecked");
		$(".unchecked").on("click", function() {
			for (var i = 0; i < remaining.length; i++) {
				if ($(remaining[i]).is(':checked')) {
					checked++;
					if (checked == remaining.length) {
						break;
					};
				};
			}
			if (checked == remaining.length) {				
				if (!($("#selectAll").is(':checked'))) {				
					$("#selectAll").prop('checked', true);
					remaining.prop('checked', true);
				};
			} else {
				$("#selectAll").prop('checked', false);
			}
			checked = 0;
		});

	});
	function getDatewiseReleasedOrders(){	
		var fromDate= document.getElementById("fromDate").value;
		if(fromDate == ""){
		alert("Please provide the date!!!");
		return false; 
		} else { 
		       var url = "<%=dateWiseOrderDisplayURL%>";
			   document.getElementById("frm1").action = url;
			   document.getElementById("frm1").submit();  
			   return true;
		}
	}
	function setDays(days){
		jQuery("#days").val(days);

		jQuery("#1").removeClass("active");
		jQuery("#2").removeClass("active");
		jQuery("#3").removeClass("active");
		jQuery("#4").removeClass("active");
		jQuery("#5").removeClass("active");
		jQuery("#6").removeClass("active");
		jQuery("#7").removeClass("active");

		jQuery("#"+days).addClass("active");		
	}	
	
	
	function clearFilter(){
		/* var date = new Date();
		document.getElementById("fromDate").value = (date.getDate() + '/' + (date.getMonth() + 1) + '/' +  date.getFullYear()); */
		   var url = "<%=clearFilterURL%>";
		   document.getElementById("frm1").action = url;
		   document.getElementById("frm1").submit(); 
	}
</script>

</head>
<body>
     <!--[if lt IE 7]>0
                                                                                    <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <!-- Add your site or application content here -->
 <liferay-ui:success key="sucess-in-final-submit" message="ORDMGT-002"></liferay-ui:success>
 <liferay-ui:success key="sucess-in-confirm-orders" message="ORDMGT-014"></liferay-ui:success>
 <liferay-ui:error key="error-in-retrieving-data" message="ORDMGT-001"></liferay-ui:error>
 <liferay-ui:error key="error-in-getting-order" message="ORDMGT-003"></liferay-ui:error>
 <liferay-ui:error key="error-in-reloading-order" message="ORDMGT-004"></liferay-ui:error>
 <liferay-ui:error key="error-in-getting-released-order" message="ORDMGT-005"></liferay-ui:error>
 <liferay-ui:error key="error-in-reloading-order" message="ORDMGT-006"></liferay-ui:error>
 <liferay-ui:error key="error-in-getting-dispatched-order" message="ORDMGT-007"></liferay-ui:error> 
 <liferay-ui:success key="no-orders-present" message="ORDMGT-015"></liferay-ui:success>
 <div id="divStatus" style="display:none"><liferay-ui:error key="selected_orders-with-different-statuses" message="Orders must be in the same status to proceed. Please review your selection."></liferay-ui:error></div>
  <%-- action="${commitOrders}" --%>
<form method="post" name="frm1" id="frm1">
        <div class="innerContainer">
            <section id="body" class="body">
                <section class="container body-section">
                    <div class="header-row relative">
                        <h6>ORDER MANAGEMENT</h6>
                        <div class="row padding-left-10">
                             <h1 class="header-alpha col-xs-4">Open Orders</h1>
                             <h1 class="header-alpha col-xs-offset-4" style=""><a href="${renderDisplayDispatchOrders}">Dispatched Orders</a></h1> 
                        </div>
                        <div class="row padding-top-20">
                         <div class="btn-group">
                        <c:if test="${isMultiOdrPrintBtnVisible || isOpenOrderPrintButtonVisible}">
							<button type="button" class="btn secondary-CTA" id="print" onclick="openPopup()">Print</button> 
						</c:if>
                           <!--   <button type="button" class="btn secondary-CTA" id="print" onclick="openPopup()">Print</button>  -->
                           <c:if test="${isDwnldMultiOrdersVisible}">
								<button type="button" class="btn secondary-CTA" id="download" onclick="downLoadOrders()">Download</button>
						  </c:if>
						<!-- 	 <button type="button" class="btn secondary-CTA" id="download" onclick="downLoadOrders()">Download</button> -->
							 <c:if test="${isQuickDispatchMultiOdrsVisible}">
								<button type="button" class="btn secondary-CTA" id="quickDispatch" onclick="return dispatchOrders()">Quick Dispatch</button>
							</c:if>
							 
							 <!-- <button type="button" class="btn secondary-CTA" id="quickDispatch" onclick="return dispatchOrders()">Quick Dispatch</button> -->
                        </div>
                      </div>
                    </div>
                    <div class="row">
                        <section class="col-xs-12 left-section fltL">
                            <div class="top-options-row row form-inline">
                                
                                <div class="col-xs-7 sortDate pull-left">
                                <span class="lbl fltL  padding-right-0 padding-left-0 margin-left-0 margin-right-0">FROM DATE:</span>
                                <div id="datetimepicker4" class="input-append padding-right-0 padding-left-0 margin-left-0 margin-right-0 pull-left" data-date-format="DD/MM/YYYY">
									<input type="text" name="fromDate" id="fromDate" class="form-control date-input" placeholder="DD/MM/YYYY"></input>
									<span class="add-on">
									  <i data-time-icon="icon-time" data-date-icon="icon-calendar" class="btn primary-CTA glyphicon glyphicon-calendar margin-top_15">
									  </i>
									</span>
								</div>
								<button style="margin-left:4px" type="button" class="btn secondary-CTA" id="time" onClick = "clearFilter()" >Today</button>
                                </div>
                                
                                <div class="col-xs-6  padding-right-0 days">
                                <span class="lbl fltL mr10">DAYS:</span>
                                <%
                                Object daysObj = renderRequest.getPortletSession().getAttribute("days");
                                int days = 7;
                                if(daysObj!=null){
                                	days = (Integer)daysObj;
                                }
                                pageContext.setAttribute("days", days);
                                %>
                                <div class="paging-controls fltL">
                                    <ul class="pagination">
                                       <li ${days==1?"class='active'":""} id="1" onclick="setDays(1);"><a href="#">1</a></li> 
                                       <li ${days==2?"class='active'":""} id="2" onclick="setDays(2);"><a href="#">2</a></li>
                                       <li ${days==3?"class='active'":""} id="3" onclick="setDays(3);"><a href="#">3</a></li>
                                       <li ${days==4?"class='active'":""} id="4" onclick="setDays(4);"><a href="#">4</a></li>
                                       <li ${days==5?"class='active'":""} id="5" onclick="setDays(5);"><a href="#">5</a></li>
                                       <li ${days==6?"class='active'":""} id="6" onclick="setDays(6);"><a href="#">6</a></li>
                                       <li ${days==7?"class='active'":""} id="7" onclick="setDays(7);"><a href="#">7</a></li>
                                    </ul>
 									<input type="hidden" name="days" id="days" value="${days}">                              
                                </div>
                                </div>
                                <div class="col-xs-2 reloadBtn">
                                <button class="btn btn-primary pull-right" type="button" onclick="getDatewiseReleasedOrders()">Reload</button>
                                </div>
                            </div>
                              <div class="table">
                                  <div class="thead row margin-bottom-20 margin-left-20">
                                      <!-- <h4 class="col-xs-2 item">PLACED DATE</h4> -->
                                      <h4 class="col-xs-1 item">
											<div class="check" style="position:relative;z-index:100">
											          <input type="checkbox" name="selectAll" id="selectAll" class="css-checkbox"  value=""/>
											          <label for="selectAll" class="css-label checkLabel" style="margin-left:-6px">
											          </label>
											</div>
									  </h4>
                                      <h4 class="col-xs-3 item">ORDERID</h4>
                                      <h4 class="col-xs-3 item">CUST.NAME</h4>
                                      <h4 class="col-xs-5 item">DELIVERY</h4>
                                      <h4 class="col-xs-2 item">#LINES</h4>
                                      <h4 class="col-xs-2 item" style="padding-left:33px">STATUS</h4>
                                  </div>
                                  <c:set var="orderListSize" value="${fn:length(ordDetails)}"/>
                                 
                                  <div class="table-body">
                                  <c:if test="${isViewReleasedOrders}">
                                   <c:forEach items="${placedDateList}" var="date">
                                  
									<div class="row row-group-header">
										<h2 class="col-xs-3">																		
												<c:out value="${date}"/>
												<c:set var="datePlaced" value="${date}"/>	
																						
										</h2>
										<div class="col-xs-13 hr"></div>
									</div>
                                 <div class="margin-top-20 margin-bottom-0 margin-left-20">
                                
  					             <c:forEach items="${ordDetails}" var="entry" >
                                     <c:set var="subDate" value="${fn:substring(entry.value.getOrderPlacedDate(), 0, 10)}" />
                                    <c:if test="${datePlaced == subDate}">  
                                      <div class="row table-row" id="div-${entry.value.getOrderId()}">
                                          <div class="col-xs-1 item">
												<div class="check">
                                                          <c:if test="${entry.value.getOrderStatus() != 'Pending'}">
                                                          	<input type="checkbox" name="selectedOrderNumbers" id="${entry.value.getOrderId()}" class="css-checkbox unchecked" value="${entry.value.getOrderId()}"/>
                                                            <label for="${entry.value.getOrderId()}" class="css-label"> 
                                                            </label>
                                                          </c:if>
												</div> 
										  </div>
                                          <div class="col-xs-3 item">
                                          							<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="renderOrderDetailsURL">
	                                                                        <portlet:param name="action" value="renderOrderDetails" />                                                                       
	                                                                        <portlet:param name="orderId" value="${entry.value.getOrderId()}" />
	                                                                        <portlet:param name="comingFromPageName" value="open-orders"/>
                                                                     </portlet:renderURL>
                                                                     
                                                                     <portlet:resourceURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="orderDownloadURL">
																			<portlet:param name="action" value="orderDownloadURL" />																			
																			<portlet:param name="pageDisplay" value="open"/>
																	 </portlet:resourceURL>
																	  <c:if test="${isViewOrderDetails}">
																	 <a href="${renderOrderDetailsURL}">
                                                                              <c:out value="${entry.value.getOrderId()}"/>
                                                                      </a>                                                                              
                                                                       </c:if> 
                                                                       <c:if test="${!isViewOrderDetails}">
                                                                              <c:out value="${entry.value.getOrderId()}"/>
                                                                       </c:if>    
                                                                     <portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="printPopupURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
									 											<portlet:param name="jspPage" value="printOrders"/> 
									 											<portlet:param name="pageDisplay" value="open"/>
																	 </portlet:renderURL>
												</div>
								 <script type="text/javascript">

									function openPopup(){
										//alert("inside popup");
										var checked,i, len, inputs = document.frm1.getElementsByTagName("input");
										var arrayOfCheckedOnes = new Array();
										checked = 0;
										len = inputs.length;
									 		 for (i = 0; i < len; i++) {
									      			if (inputs[i].type === "checkbox" && inputs[i].checked){
									      				if(inputs[i].value != ""){
									      					checked++;
									      					arrayOfCheckedOnes.push(inputs[i].value);
									      					}		
									         		 };
									  			}
										popupUrl = '<%=printPopupURL%>';
										finalPopupURL = popupUrl + "?selectTab:"+"NA"+"*selOrderOpt:" + "NA"+"*orderId:" + arrayOfCheckedOnes.toString();
										//alert(finalPopupURL);
										document.getElementById("frm1").action = "";
										 if(checked == 0)
								 			{
								 			alert("select at least one order!!!");
								 			return false;						
								 			}
										 else {
											 if (window.showModalDialog) {
												 // window.showModalDialog(finalPopupURL,"name","dialogWidth:625px;dialogHeight:750px;center:yes");
													 newwindow = window.open(finalPopupURL,'name','height=750,width=625,toolbar=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=no ,modal=yes');
														 newwindow.print();
												 }  else {
													 newwindow = window.open(finalPopupURL,'name',
												  'height=500,width=500,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no ,modal=yes');
													 newwindow.print();
												 }
									}
									return false;
								}
								</script> 
								 <script type="text/javascript">
								function downLoadOrders() {
								       var checked,i, len, inputs = document.frm1.getElementsByTagName("input");
								       var arrayOfCheckedOnes = new Array();
								       len = inputs.length;
								       checked = 0;
								       for (i = 0; i < len; i++) {
								        		if (inputs[i].type === "checkbox" && inputs[i].checked){
								            		checked++;  
								            		arrayOfCheckedOnes.push(inputs[i].value);
								            	};
								    		}
								     if(checked == 0){
								 			alert("select at least one order!!!");
								 			return false;						
								 	 } else	{ 
								 				var url = "<%=orderDownloadURL%>";
								 				var arrayToBeSent = new Array();
								 				arrayToBeSent = arrayOfCheckedOnes.toString();
								 				//alert(arrayToBeSent.toString());
								 				
								 	       		document.getElementById("frm1").action = url;
								 	       		document.getElementById("frm1").submit();   
								 	       		
								 				};
								 	};						     
								</script>       
                                          
                                          <div class="col-xs-3 item"><c:out value="${entry.value.getCustomerFirstName()} ${entry.value.getCustomerLastName()}" /></div>
                                          <div class="col-xs-5 item"><c:out value="${entry.value.getDeliveryOption()}" />
                                          <input type="hidden" name="delOption" id="opt-${entry.value.getOrderId()}" value="${entry.value.getDeliveryOption()}"/>
                                          </div>
                                          <div class="col-xs-2 item"><c:out value="${entry.value.getNoOfLines()}" /></div>
                                          <div class="col-xs-2 item"><c:out value="${entry.value.getOrderStatus()}"/>
                                          <input type="hidden" name="orderStatus" id="status-${entry.value.getOrderId()}" value="${entry.value.getOrderStatus()}"/>
                                          </div>
                                         </div>
                                       </c:if> 
                                  </c:forEach>
                                 <%--  </c:forEach> --%>
                                  </div>
                                  </c:forEach>           
                                  </c:if>                         
                                  </div>
                                  
                            </div>
                             <c:if test="${orderListSize == 0}">
                                             <%-- <b style="font-size: 20px"><c:out value= "There are no orders available for your criteria. Please try again."/></b> --%>
        									<liferay-ui:success key="order-not-present-in-this-date-range" message="ORDMGT-010"></liferay-ui:success>
        						  </c:if>
                            <div id="dialog-confirm"></div>
                        </section>
                         <c:if test="${(isCancelOdrLinesVisible || isQuickDispatchMultiOdrsVisible || isCancelOdrsVisible || isDispatchOdrLinesVisible ) && isSubmitFinalOdrVisible}">                  
                       <%@ include file="/WEB-INF/views/orderBasket.jsp"%>
                       </c:if>
                        </div>
                        
                    </section>
                    </section>
                    
                </div>
                <%-- <div>
                   <c:set var="orderListSize" value="${fn:length(ordDetails)}"/>
                   <c:if test="${orderListSize > 100}">
                        <b style="font-size: 20px"><c:out value="More Orders..."/></b>
                   </c:if>
                </div> --%>
  </form>
</body>
</html>
