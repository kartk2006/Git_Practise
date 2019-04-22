<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap-datetime-min.css"/>
      <script src="<%=request.getContextPath() %>/js/vendor/moment.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/vendor/bootstrap-datetimepicker.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jquery-ui.css">

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js">
                                                                                                                                                                                                          
    </script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js">
                                                                                                                                                                                                            
    </script>
    <![endif]-->
 <style>
    .form-control.date{width:150px !important;}
    .padding-right-0{padding-right:0px;}
    .mr10{margin-right:10px;}
    
.portlet-msg-success{
margin-top:50px;
}
.btn-primary:hover, .btn-primary:focus {
background-image: none;
}
h2.col-xs-3 {
font-size: 1.3em;
}
</style>
</head>

<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var ="renderDisplayOpenOrders">
 <portlet:param name="action" value="renderDisplayOpenOrders"/>
</portlet:renderURL>

<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="viewPrintURL">
	<portlet:param name="action" value="viewPrintURL" />
	
</portlet:renderURL>

 <portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="dateWiseOrderDispatchedDisplayURL">
   <portlet:param name="action" value="datewiseOrderDispatchedDisplay"/>
 </portlet:renderURL>   

<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="selectIDs">
	<portlet:param name="action" value="selectIDs"></portlet:param>
</portlet:renderURL>

<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="dateWiseOrderDisplayURL">
   <portlet:param name="action" value="datewiseOrderDisplay"/>
   <portlet:param name="ordStatus" value="dispatched-orders"/>
</portlet:renderURL>
 
<portlet:actionURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="commitOrders">
	<portlet:param name="action" value="commitOrders"/>
</portlet:actionURL>

<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="clearFilterURL">
    <portlet:param name="action" value="clearFilter"/>
    <portlet:param name="fromPage" value="dispatched-orders"/>
</portlet:renderURL>
   
<script type="text/javascript">
 function selectOrders() {	
 			//alert("arrayOfCheckedOnes = " + arrayOfCheckedOnes);	
 			if(checked == 0)
 			{
 				alert("select at least one order!!!");
 				return;						
 			}
 			else { 
 			  var url = "<%=selectIDs%>";
 		 	  document.getElementById("frm1").action = url;
 		 	  document.getElementById("frm1").submit();		
 			}	
 		}
 
 
 function BackToPreviousPage(){
 	var url = "<%=renderDisplayOpenOrders%>";
 	document.getElementById("frm1").action = url;
    document.getElementById("frm1").submit(); 
 }
 
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
				checkboxes.prop('checked', true);
			} else {
				checkboxes.prop('checked', false);
			}
		});
		var remaining = $("input[type='checkbox'].unchecked");
		$(".unchecked").on("click", function() {
			for (var i = 0; i < remaining.length; i++) {
				if ($(remaining[i]).is(':checked')) {
					checked++;		
					if (checked == remaining.length) {
						break;
					}
				}
			}
			if (checked == remaining.length) {				
				if (!($("#selectAll").is(':checked'))) {				
					$("#selectAll").prop('checked', true);
					remaining.prop('checked', true);
				}
			} else {
				$("#selectAll").prop('checked', false);
			}
			checked = 0;
		});

	});	
		
	
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
	
	function getDatewiseDispatchedOrders(){
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

<body>
    <!--[if lt IE 7]>
                                                                                    <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <!-- Add your site or application content here -->
 <liferay-ui:success key="sucess-in-final-submit" message="ORDMGT-002"/>
 <liferay-ui:success key="sucess-in-confirm-orders" message="ORDMGT-014"/>
 <liferay-ui:error key="error-in-retrieving-data" message="ORDMGT-001"></liferay-ui:error>
 <liferay-ui:error key="error-in-getting-order" message="ORDMGT-003"></liferay-ui:error>
 <liferay-ui:error key="error-in-reloading-order" message="ORDMGT-004"></liferay-ui:error>
 <liferay-ui:error key="error-in-getting-released-order" message="ORDMGT-005"></liferay-ui:error>
 <liferay-ui:error key="error-in-reloading-order" message="ORDMGT-006"></liferay-ui:error>
 <liferay-ui:error key="error-in-getting-dispatched-order" message="ORDMGT-007"></liferay-ui:error>  
 <liferay-ui:success key="no-orders-present" message="ORDMGT-015"></liferay-ui:success>
<form method="post" name="frm1" id="frm1">
            <section id="body" class="body">
                <section class="container body-section">
                    <div class="header-row relative">
                        <h6>ORDER MANAGEMENT</h6>
                        <div class="row padding-left-10">
                            <h1 class="header-alpha col-xs-4"><a href="${renderDisplayOpenOrders}">Open Orders</a></h1>
                            <h1 class="header-alpha col-xs-offset-4" style=""><%-- <a href="${renderDisplayDispatchOrders}"> --%>Dispatched Orders</h1>
                        </div>
                        <div class="row padding-top-20">
                         <div class="btn-group">
                         <c:if test="${isMultiOdrPrintBtnVisible || isDispatchedOrderPrintButtonVisible}">
							<button type="button" class="btn secondary-CTA" id="print" onclick="openPopup()">Print</button> 
						</c:if>
                         <c:if test="${isDwnldMultiOrdersVisible}">
								<button type="button" class="btn secondary-CTA" id="download" onclick="downLoadOrders()">Download</button>
						  </c:if>   
							<!--  <button type="button" class="btn secondary-CTA" id="print" onclick="openPopup()">Print</button> 
							 <button type="button" class="btn secondary-CTA" id="download" onclick="downLoadOrders()">Download</button>  -->                            
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <section class="col-xs-12 left-section fltL">
                            <div class="top-options-row row form-inline">
                            
                                <div class="col-xs-7 sortDate pull-left">
                                <span class="lbl fltL padding-right-0 padding-left-0 margin-left-0 margin-right-0 ">FROM DATE:</span>
                                <div id="datetimepicker4" class="input-append  padding-right-0 padding-left-0 margin-left-0 margin-right-0 pull-left" data-date-format="DD/MM/YYYY">
									<input type="text" name="fromDate" id="fromDate" class="form-control date-input" placeholder="DD/MM/YYYY"></input>
									<span class="add-on">
									  <i data-time-icon="icon-time" data-date-icon="icon-calendar" class="btn primary-CTA glyphicon glyphicon-calendar margin-top_15">
									  </i>
									</span>
								</div>
								 <button style="margin-left:4px" type="button" class="btn secondary-CTA pull-left" onclick="clearFilter()">Today</button> 
                                </div>
                                <div class="col-xs-6 padding-right-0 days">
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
                                <button class="btn btn-primary pull-right" type="button" onclick="getDatewiseDispatchedOrders()">Reload</button>
                                </div>
                            </div>
                              <div class="table">
                                  <div class="thead row margin-bottom-20 margin-left-20">
                                      <h4 class="col-xs-1 item" style="padding:0px;float:left">
											<div class="check" style="position:relative;z-index:100">
											          <input type="checkbox" name="selectAll" id="selectAll" class="css-checkbox"  value=""/>
											          <label for="selectAll" class="css-label checkLabel">
											          </label>
											</div>
									</h4>
                                      <h4 class="col-xs-3 item">ORDER ID</h4>
                                      <h4 class="col-xs-5 item">CUST. NAME</h4>
                                      <h4 class="col-xs-2 item"># LINES</h4>
                                      <h4 class="col-xs-3 item">STATUS</h4>
                                  </div>
                                  <c:set var="orderListSize" value="${fn:length(ordDetails)}"/>
                                 
                                  <div class="table-body">
                                  <c:if test="${isViewDispatchedOrders}">
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
                                     	<div class="row table-row">
                                           <div class="col-xs-1 item">
											<div class="check">	
											              <c:set var = "status" value="${entry.value.getOrderStatus()}"/>
                                                          <c:set var = "statusConfirm" value="Only lineitem status available"/>
                                                          <c:if test="${status != statusConfirm}">
                                                            <input type="checkbox" name="selectedOrderNumbers" id="${entry.value.getOrderId()}" class="css-checkbox unchecked" value="${entry.value.getOrderId()}"/>
                                                            <label for="${entry.value.getOrderId()}" class="css-label"> 
                                                            </label> 
                                                          </c:if>
											</div> 
										</div>
                                          <div class="col-xs-3 item"><portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="renderOrderDetailsURL">
	                                                                        <portlet:param name="action" value="renderOrderDetails" />                                                                       
	                                                                        <portlet:param name="orderId" value="${entry.value.getOrderId()}" />
	                                                                        <portlet:param name="comingFromPageName" value="dispatched-orders"/>
                                                                     </portlet:renderURL>
                                                                     <portlet:resourceURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="orderDownloadURL">
																			<portlet:param name="action" value="orderDownloadURL" />
																			<portlet:param name="pageDisplay" value="display"/>
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
									 											<portlet:param name="pageDisplay" value="display"/>
																	 </portlet:renderURL>
								 <script type="text/javascript">

									function openPopup(){
										//alert("inside popup");
										var checked,i, len, inputs = document.frm1.getElementsByTagName("input");
										var arrayOfCheckedOnes = new Array();
										checked = 0;
										len = inputs.length;
									 		 for (i = 0; i < len; i++) {
									      			if (inputs[i].type === "checkbox" && inputs[i].checked){
									         			/*  checked++;   									         			
									         			arrayOfCheckedOnes.push(inputs[i].value);	 */   											        
									      				if(inputs[i].value != ""){
									      					checked++;
									      					arrayOfCheckedOnes.push(inputs[i].value);
									      					}
									         		 };
									  			}								 		 
										popupUrl = '<%=printPopupURL%>';
										finalPopupURL = popupUrl + "?selectTab:"+"NA"+"*selOrderOpt:" + "NA"+"*orderId:" + arrayOfCheckedOnes.toString();
										document.getElementById("frm1").action = "";
										//alert("url with IDs = " + finalPopupURL);
										 if(checked == 0)
								 			{
								 			alert("select at least one order!!!");
								 			return false;						
								 			}
										 else {
									 /* if (window.showModalDialog) {
									  window.showModalDialog(finalPopupURL,"name","dialogWidth:625px;dialogHeight:750px");
									 } else {
									  window.open(finalPopupURL,'name',
									  'height=500,width=500,toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no ,modal=yes');
									 }; */
									 if (window.showModalDialog) {
										 // window.showModalDialog(finalPopupURL,"name","dialogWidth:625px;dialogHeight:750px;center:yes");
											 newwindow = window.open(finalPopupURL,'name','height=750,width=625,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no ,modal=yes');
												 newwindow.print();
										 }  else {
											 newwindow = window.open(finalPopupURL,'name',
										  'height=500,width=500,toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no ,modal=yes');
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
								            	}
								    		}
								    	  if(checked == 0)
								 			{
								 			alert("select at least one order!!!");
								 			return;						
								 			}
								 	  else	 	{ 
								 				var url = "<%=orderDownloadURL%>";
								 				var arrayToBeSent = new Array();
								 				arrayToBeSent = arrayOfCheckedOnes.toString();
								 	       		document.getElementById("frm1").action = url;
								 	       		document.getElementById("frm1").submit();    	   
								 				}
								 	}						     
								
								</script>                                                                                                                                                   
                                          </div>
                                          <div class="col-xs-5 item"><c:out value="${entry.value.getCustomerFirstName()} ${entry.value.getCustomerLastName()}" /></div>
                                          <div class="col-xs-2 item "><c:out value="${entry.value.getNoOfLines()}" /></div>
                                          <div class="col-xs-3 item"><c:out value="${entry.value.getOrderStatus()}"/></div>
                                          </div>
                                      </c:if>
                                      </c:forEach>
                                  </div>
                                  </c:forEach>  
                                  </c:if>                                  
                                  </div>
                            </div>
                             <c:if test="${orderListSize == 0}">
                                             <%-- <b style="font-size: 20px"><c:out value= "There are no orders available for your criteria. Please try again."/></b> --%>
                                             <liferay-ui:success key="order-not-present-in-this-date-range" message="ORDMGT-010"/>
                             </c:if>
                             <div id="dialog-confirm"></div>
                        </section>  
                        <c:if test="${isSubmitFinalOdrVisible && isReturnOdrLinesVisible}">                  
                       <%@ include file="/WEB-INF/views/orderBasket.jsp"%>
                       </c:if>
                        </div>
                       
                    </section>
                    </section>
             
  </form>            
                <!-- ///////////////////////////////////////////////////////////////////////////////////////////////// -->
                <!-- BODY SECTION ENDS HERE -->
                <!-- ///////////////////////////////////////////////////////////////////////////////////////////////// -->
</body>
</html>