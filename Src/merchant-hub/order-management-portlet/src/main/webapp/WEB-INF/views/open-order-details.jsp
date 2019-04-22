<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/init.jsp"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>


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
 <%--    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap-datetime-min.css"/> --%>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/jquery-ui.css">
    <script src="<%=request.getContextPath() %>/js/vendor/moment.min.js"></script>
   <%--  <script src="<%=request.getContextPath() %>/js/vendor/bootstrap-datetimepicker.js"></script> --%>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
    .ml-10{margin-left:10px;}
    .wd50{ width:50px;}
    </style>
    
<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var ="renderDisplayOpenOrders">
 <portlet:param name="action" value="renderDisplayOpenOrders"/>
</portlet:renderURL>
  
<portlet:actionURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="confirmActionUrl">
  <portlet:param name="action" value="updateOrder"/>
</portlet:actionURL> 
    
    <script type="text/javascript">
    function toggle_sections(event){
    	
    	var flag = 0;
    	var total = 0;
    	
    	$("input:checkbox:not(:checked)").each(function()	{
			  total++;
    		var uncheckedLineNo = $(this).val();
    		var selectAllID = $(this).attr('id');
    		
    		if(selectAllID!="selectAll"){
    			$(".qty_section").css('display', 'none');
	    		$("#updatedQuantity-"+uncheckedLineNo).css('display', 'none');
				$("#updatedQuantity-"+uncheckedLineNo).attr('disabled', 'disabled');
    		}
    	  });
    	
    	$("input[type='checkbox']:checked").each(function(){
  			flag ++;
  			total++;
  			var selectAllID = $(this).attr('id');
  			
  			var lineNo = (selectAllID).substring(6,8);
  			
  			//$("#updatedQuantity-"+lineNo).css('display', 'block');
  			$(".qty_section").css('display', 'block');
  			$("#updatedQuantity-"+lineNo).css('display', 'block');
				$("#updatedQuantity-"+lineNo).attr('disabled', false);
  		  });
    	
    	if(event.currentTarget.id=="dispatch_btn"){
    		
    		$('input[name=updateOrderAction]').val("dispatch");
    		
    		if($(".dispatch_section").css("display")=="block"){
    			$(".dispatch_section").css("display","none");
    			$(".qty_section").css("display","none");
    		    
    		}
    		else{
    			if(flag >= 1 || total == 1)
    				{
    				
    			$(".dispatch_section").css("display","block");
        		$(".return_section").css("display","none");
        		/* $("h4.descHead").width('5%'); 
        		$("div.desc").width('5%'); */
        		var totalCheck = $("input[type=checkbox]").length;
        		if(total == 1){
        			$(".qty_section").css("display","block");
        			$("#updatedQuantity-1").css('display', 'block');
    				$("#updatedQuantity-1").attr('disabled', false);
        		}
    				}
    		}
    	}
    	else if(event.currentTarget.id=="cancel_btn"){
    		
    		$('input[name=updateOrderAction]').val("cancel");
    		
    		if($(".return_section").css("display")=="block"){
    			$(".return_section").css("display","none");
    			$(".qty_section").css("display","none");
    		}
    		else{
    			if(flag >= 1 || total ==1)
				{
    			$(".dispatch_section").css("display","none");
        		$(".return_section").css("display","block");
        		/* $("h4.descHead").width('5%'); 
        		$("div.desc").width('5%');  */
        		var totalCheck = $("input[type=checkbox]").length;
        		if(total == 1){
        			$(".qty_section").css("display","block");
        			$("#updatedQuantity-1").css('display', 'block');
    				$("#updatedQuantity-1").attr('disabled', false);
        		}    		}
    		}
    	}
    }
    
    function hide_updateBoxes(event){
    	$(".dispatch_section").css("display","none");
		$(".return_section").css("display","none");
		$(".qty_section").css("display","none");
		
    	 $('#frm1')[0].reset();
    	 //$('input[type=text]').css("visibility" , "hidden");
    }
    
    
    function BackToPreviousPage(){
    	var url = "<%=renderDisplayOpenOrders%>";
    	document.getElementById("frm1").action = url;
		document.getElementById("frm1").submit(); 
    }
    
    function validateDispatch(){
   	var flag=false,
   		flagArray = new Array(),
   		textArray = new Array(),
   		totalCheck = $("input[type=checkbox]").length;
   	
   	if(totalCheck > 1){
		 var trackigID = document.getElementById("trackingId"),
		 	carrierID = document.getElementById("carrierName");
		if(!$("input[name='lineItem']:checked").val()) {
    	        flag= false;
   	    }else if(!alphanumericspaces(trackigID)){
			alert("Please recheck values entered in Tracking Id field!!");
			flag = false;
		}else if(!alphanumericspaces(carrierID)) {
				alert("Please recheck values entered in Carrier Name field!!");
				flag = false;
		} else {
			
	      		$("input[type='checkbox']:checked").each(function(){
	      			var id = $(this).attr("id");
	      			if((id != "selectAll")){
	      				textArray.push(id);
	      			}
	      		  });
	      		arrLen = textArray.length;
	      		for(var i = 0 ; i < arrLen ; i++){
	      			var txtId = textArray[i];
	      			var lineNo = (txtId).substring(6,8);
	      			var qty = $("#updatedQuantity-"+lineNo).val();
	      			var openQty = $("#openQty-"+lineNo).val();
	      				if(openQty == 0){
	      					alert("Open quantity is Zero. No operation can be performed!!!");
		      				flag= false;
	      				} else if(qty < 1 || qty == ""){
		      				alert("Please enter quantity to be updated!!");
		      				flag = false;
	      				}
	      				else {
	      					var openQty = parseFloat($("#openQty-"+lineNo).val());
							 var givenQty = parseFloat($("#updatedQuantity-"+lineNo).val());
							 if(givenQty <= openQty) {
								$("#err-"+lineNo).html("");
								flag = true;
							 } else {
							 	$("#err-"+lineNo).html("<%= LanguageUtil.get(pageContext, "ORDMGT-011") %>");
								flag = false; 
							 } 
							 flagArray.push(flag);
							 var count = 0;
					      		var flagLength = flagArray.length;
					      		for(var j = 0 ; j < flagLength ; j++){
					      			if(flagArray[j] == false){
					      				count++;
					      			}
					      		}
					      		if(count > 0){
				  					flag = false;
				       			} else {
				   					flag = true;
				   				}
	      				}
	      		}
			 }
	}
	else {
		var trackigID = document.getElementById("trackingId"),
			carrierID = document.getElementById("carrierName"),
			checkboxID = $("input[type='checkbox']").attr("id"),
			lineNo = checkboxID.substring(6,8),
			qty = $("#updatedQuantity-"+lineNo).val(),
			openQty = $("#openQty-"+lineNo).val();
			if(openQty == 0){
				alert("Open quantity is Zero. No operation can be performed!!!");
				flag= false;
			}else if(qty < 1 || qty == ""){
				alert("Please enter quantity to be updated!!");
				flag= false;
			}else if(!alphanumericspaces(trackigID)){
  					alert("Please recheck values entered in Tracking Id field!!");
  					flag = false;
 			}else if(!alphanumericspaces(carrierID)) {
  					alert("Please recheck values entered in Carrier Name field!!");
  					flag = false;
			}else {
				var openQty = parseFloat($("#openQty-"+lineNo).val()),
					givenQty = parseFloat($("#updatedQuantity-"+lineNo).val());
				 if(givenQty <= openQty) {
					$("#err-"+lineNo).html("");
					flag = true;
				 } else {
				 	$("#err-"+lineNo).html("<%= LanguageUtil.get(pageContext, "ORDMGT-011") %>");
					flag = false; 
				 } 
			}
		}
   		return flag;
       }

   function confirmDispatch() {
    	if(validateDispatch()){
    		$("#dialog-confirm").html("All order lines within these orders will be updated with the same status and the same reason. Do you confirm (Cancel/Confirm?)");
    	    $("#dialog-confirm").dialog({
    	        resizable: false,
    	        modal: true,
    	        title: "Confirm Dispatch Order Lines",
    	        height: 250,
    	        width: 500,
    	        buttons: {
    	        	"Cancel": function () {
    	                $(this).dialog('close');
    	                $('#frm1')[0].reset();
    	                $(".dispatch_section").css("display","none");
    	        		$(".return_section").css("display","none");
    	        		$(".qty_section").css("display","none");
    	                $('.numeric').prop("disabled", "disabled");
    	                
    	                window.location.href ="#";
    	            },
    	            "Confirm": function () {
    	            	 $(this).dialog('close');
    	            	 var url = "<%=confirmActionUrl%>";
    	            	 $('#frm1').attr('action', url);
    	            	 $("#frm1").submit(); 
    	         	 	}
    	               
    	            }
    	    });
    	}
    }
    
    function validateCancel(){
    	var flag=false;
       	var flagArray = new Array();
       	var textArray = new Array();
    	var totalCheck = $("input[type=checkbox]").length;
		if(totalCheck > 1){
    		if(!$("input[name='lineItem']:checked").val()) {
       	       alert('No order ID selected! Please select an Order to be cancelled.');
       	        flag = false;
       	    }
       	 else {
       		var reasonForCancellation = $("#primaryCancelReason").val();
			if(reasonForCancellation == "defualtPri" || reasonForCancellation == ""){
					alert("Please select reason for cancellation!!");
     				flag = false;
				} 
			else {
       		$("input[type='checkbox']:checked").each(function(){
      			var id = $(this).attr("id");
      			if((id != "selectAll")){
      				textArray.push(id);
      			}
      		  });
      		arrLen = textArray.length;
      		for(var i = 0 ; i < arrLen ; i++){
      			var txtId = textArray[i];
      			var lineNo = (txtId).substring(6,8);
      			var qty = $("#updatedQuantity-"+lineNo).val();
      			var openQty = $("#openQty-"+lineNo).val();
      				if(openQty == 0){
      					alert("Open quantity is Zero. No operation can be performed!!!");
	      				flag= false;
      				} else if(qty < 0 || qty == ""){
	      				alert("Please enter quantity to be updated!!");
	      				flag= false;
      				} else if(qty >= 1){
      					var openQty = parseFloat($("#openQty-"+lineNo).val());
						 var givenQty = parseFloat($("#updatedQuantity-"+lineNo).val());
						 if(givenQty <= openQty) {
							$("#err-"+lineNo).html("");
							flag = true;
						 } else {
						 	$("#err-"+lineNo).html("<%= LanguageUtil.get(pageContext, "ORDMGT-011") %>");
							flag = false; 
						 } 
						flagArray.push(flag);
      				}
      		}
      		var count = 0;
	      		var flagLength = flagArray.length;
	      		for(var j = 0 ; j < flagLength ; j++){
	      			if(flagArray[j] == false){
	      				count++;
	      			}
	      		}
	      		if(count > 0){
						flag = false;
	   			} else {
						flag = true;
				}
		 	}
    		} 
		}
    		 else {
    		
    				var checkboxID = $("input[type='checkbox']").attr("id");
    				var lineNo = checkboxID.substring(6,8);
    				var qty = $("#updatedQuantity-"+lineNo).val();
    				var openQty = $("#openQty-"+lineNo).val();
    					if(openQty == 0){
    						alert("Open quantity is Zero. No operation can be performed!!!");
    						flag= false;
    					}
    					else if(qty < 1 || qty == ""){
    						alert("Please enter quantity to be updated!!");
    						flag= false;
    						}
    					else {
    						var openQty = parseFloat($("#openQty-"+lineNo).val());
    						 var givenQty = parseFloat($("#updatedQuantity-"+lineNo).val());
    						 if(givenQty <= openQty) {
    							$("#err-"+lineNo).html("");
    							flag = true;
    						 } else {
    						 	$("#err-"+lineNo).html("<%= LanguageUtil.get(pageContext, "ORDMGT-011") %>");
    							flag = false; 
    						 } 
    					}
    	} 
   		return flag;
       }
    
 function confirmCancel() {
    	if(validateCancel()){
    		$("#dialog-confirm").html("All order lines within these orders will be updated with the same status and the same reason. Do you confirm (Cancel/Confirm?)");
    	    $("#dialog-confirm").dialog({
    	        resizable: false,
    	        modal: true,
    	        title: "Confirm Cancel Order Lines",
    	        height: 250,
    	        width: 500,
    	        buttons: {
    	        	"Cancel": function () {
    	                $(this).dialog('close');
    	                $('#frm1')[0].reset();
    	                $(".dispatch_section").css("display","none");
    	        		$(".return_section").css("display","none");
    	        		$(".qty_section").css("display","none");
    	                $('.numeric').prop("disabled", "disabled");
    	                
    	                window.location.href ="#";
    	            },
    	            "Confirm": function () {
    	            	 $(this).dialog('close');
    	            	 var url = "<%=confirmActionUrl%>";
    	            	 $('#frm1').attr('action', url);
    	            	 $("#frm1").submit(); 
    	         	 	}
    	               
    	            }
    	    });
    	}
    }
    
    function cancel () { return false; };
    
    function disableDivs() {
    	
    	var count = 1;
    	$("input:checkbox[name='lineItem']").each(function(){
      			var lineNo = $(this).val();
      			//alert("lineNo " + lineNo);
    			var status = $("#status-"+lineNo).val();
    			if(status === "Pending"){
    				$("#div-"+lineNo).attr('disabled','disabled');
    				$("#check-"+lineNo).css('display','none');
    				$(".unchecked").css('display','none');
    				count++;
    				var currentDiv = "div-"+lineNo; 
    				var nodes = document.getElementById(currentDiv).getElementsByTagName('*');
       		    	for (var k = 0; k < nodes.length; k++) {
       		        nodes[k].setAttribute('disabled', true);
       		        nodes[k].onclick = cancel;
       		    		} 
    			}
    			
				var ordStat = $("#ordStatus").val();
    			var remaining = $("input[type='checkbox']");
				var totalCheckboxes = remaining.length;
				if(count == totalCheckboxes){
					$("#selectAll").css('display','none');
					$(".checkLabel").css('display','none');
				}
				if(totalCheckboxes == 1 && ordStat === "Pending"){
					$("#confirmDispatch_btn").attr('disabled','disabled');
					$("#confirmButton").attr('disabled','disabled');
					alert("Order already in pending state.No other operation can be performed!!!");
				}
	   	 });
   	 }
    
    $(document).ready(function(){
    	
   	 disableDivs();
    });
    
    
    </script>
    
</head>
<body>
-- TEST --
 <div id="dialog-confirm"></div>
    <!--[if lt IE 7]>
	<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <!-- Add your site or application content here -->
 <%--  <form action="${confirmActionUrl}" method="post" name="frm1" id="frm1"> --%>
   <form method="post" name="frm1" id="frm1">
  <input type="hidden" name="updateOrderAction" value=""/>
    <div id="wrapper" class="wrapper">
        <div class="innerContainer">
            <c:set var="displayHeader" value="0"/>
             <c:set var="orderDetailsSize" value="${fn:length(orderDetailsMap)}"/>     
        <section id="body" class="body">
          
                <section class="container body-section">                	
                	<c:if test="${displayHeader <1}">
	                    <div class="header-row relative">
	                        <div class="backArrow" onclick="BackToPreviousPage()"></div>
	                        <h5>OPEN ORDERS</h5>
	                        <div class="row padding-left-10">
	                            <h1 class="header-alpha col-xs-4">Order Processing</h1>
	                        </div>
	                    </div>
                    <div class="row margin-bottom-5">
                    <h4 class="ml-10"><a href="${renderDisplayOpenOrders}">All Open Orders</a></h4>
                    </div>
                    <div class="row top-section margin-top-30">                    
                        <section class="col-xs-5 left-section right-border1px padding-right-0 padding-left-5">                      
                            <h3 class="col-header">Shipping &amp; Delivery</h3>                     			
                            <div class="col-xs-16 padding-left-0">                           
                                  <label class="col-xs-7 padding-left-0">Delivery Option</label>
                                  <div class="col-xs-9 txt-value">
                                  <span class="padding-5 bg-red"><c:out value="${orderDetails.deliveryOption}"/></span>
                                  <input type="hidden" name="deliveryOption" id="deliveryOption" value="${orderDetails.deliveryOption}"/>
                                  </div>                           
                            </div>
                           <div class="col-xs-16 padding-left-0">
                                  <label class="col-xs-7 padding-left-0" >Delivery Cust</label>
                                  <div class="col-xs-9 txt-value" ><b>£</b><c:out value="${orderDetails.deliveryCost}"/>
                                  <input type="hidden" name="deliveryCost" value="${orderDetails.deliveryCost}"/>
                                  </div>                      
                            </div>
                            <div class="col-xs-16 padding-left-0">                           
                                  <label class="col-xs-7 padding-left-0">Ship to</label>
                                  <div class="col-xs-9 txt-value"><c:out value="${orderDetails.customerFirstName} ${orderDetails.customerLastName}"/>
                                  	<br/>
					  				<c:out value="${orderDetails.shipToAddress}"/>
                                  <input type="hidden" name="shipingTo" value="${orderDetails.shipToAddress}"/>
                                  </div> 
                            </div>
                        </section>
                        <section class="col-xs-11">
                            <div class="col-xs-8 left-section padding-left-0">
                                  <h3 class="col-header pl-18">Order Details</h3>                          
                              <div class="col-xs-16">
                                    <label class="col-xs-7">Order Status</label>
                                    <div class=" col-xs-9 txt-value">
                                    <span class="padding-5 bg-red"><c:out value="${orderDetails.orderStatus}"/></span>
                                    <input type="hidden" id="ordStatus" name="ordStatus" value="${orderDetails.orderStatus}"/>
                                    </div>
                              </div>
                              <div class="col-xs-16">
                                    <label class="col-xs-7">Order Id</label>
                                    <div class="col-xs-9 txt-value"><c:out value="${orderDetails.orderId}"/>
                                    <input type="hidden"  name="orderId" id="orderId" value="${orderDetails.orderId}"/>
                                    </div>
                              </div>
                              <div class="col-xs-16">
                                    <label class="col-xs-7">Order Placed</label>
                                    <div class="col-xs-9 txt-value"><c:out value="${orderDetails.orderPlacedDate}"/>
                                    <input type="hidden" id="placedDate" name="placedDate" value="${orderDetails.orderPlacedDate}"/>
                                    </div>
                              </div>
                            <div class="col-xs-16">
                                    <label class="col-xs-7">Order Value</label>
                                    <div class="col-xs-9 txt-value"><b>£</b><c:out value="${orderDetails.orderValue}"/>
                                    <input type="hidden" id="orderValue" name="orderValue" value="${orderDetails.orderValue}"/>
                                    </div>
                              </div>                   
                            </div>
                            <div class="col-xs-8 right-section">
                                 <h3 class="col-header pl-18">Customer Details</h3>
                          
                              <div class="col-xs-16">
                                    <label class="col-xs-5">Name</label>
                                    <div class="col-xs-11 txt-value"><c:out value="${orderDetails.customerFirstName} ${orderDetails.customerLastName}"/>
                                    <input type="hidden" id="firstName" name="firstName" value="${orderDetails.customerFirstName}"/>
                                    </div>
                              </div>
                             
                              <div class="col-xs-16">
                                    <label class="col-xs-5">Email</label>
                                    <div class="col-xs-11 txt-value"><c:out value="${orderDetails.customerEmail}"/>
                                    <input type="hidden" id="email" name="email" value="${orderDetails.customerEmail}"/>
                                    </div>
                              </div>
                              <div class="col-xs-16">
                                    <label class="col-xs-5">Telephone</label>
                                    <div class="col-xs-11 txt-value"><c:out value="${orderDetails.customerMobile}"/>
                                    <input type="hidden" id="mobileNumber" name="mobileNumber" value="${orderDetails.customerMobile}"/> 
                                    </div>
                              </div>       
                                         
                            </div>
                        </section>
                    </div>
                    <div class="row margin-top-30 printBtnDiv">
                          <div class="pull-right">
                           <!--  <button class="btn primary-CTA">Print</button> -->
                            <c:if test="${isOrderDetailsPrintBtnVisible || isMultiOrderDetailsPrintBtnVisible}">
                           <input type="button" class="btn secondary-CTA" id="print" onclick="openPopup()" value="Print" /> 
                           </c:if>
                            <portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="printPopupURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>">
								<portlet:param name="jspPage" value="printOrders"/> 
								<portlet:param name="pageDisplay" value="dispatchLines"/>
							 </portlet:renderURL>
								 <script type="text/javascript">
									function openPopup(){
										var popupUrl = '<%=printPopupURL%>';
										var orderID = document.getElementById("orderId").value;
										finalPopupURL = popupUrl + "?selectTab:"+"NA"+"*selOrderOpt:" + "NA"+"*orderId:" + orderID;
										document.getElementById("frm1").action = "";
										if (window.showModalDialog) {
												 newwindow = window.open(finalPopupURL,'name','height=750,width=625,toolbar=no,directories=no,status=yes,menubar=no,scrollbars=no,resizable=no ,modal=yes');
													 newwindow.print();
											 }  else {
												 newwindow = window.open(finalPopupURL,'name',
											  'height=500,width=500,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no ,modal=yes');
												 newwindow.print();
											 }
									}		
								</script> 							
                          </div>
                    </div>
                    <div class="row table row-group-header orderlinesHeader">
                        <h2 class="col-xs-3">Order Lines</h2>
                        <div class="col-xs-13 hr"></div>
                    </div>
                    </c:if>
                    <div class="row">
                        <section class="col-xs-16">
                        	<c:if test="${displayHeader <1}">
                            <div class="row btn-toolbar">
                                <div class="btn-group">
                                <c:if test="${isDispatchOdrLinesVisible || isQuickDispatchMultiOdrsVisible}">
                                  <button id="dispatch_btn" type="button" class="btn secondary-CTA" onclick="toggle_sections(event)">Dispatch Lines</button>
                                  </c:if>
                                  <c:if test="${isCancelOdrLinesVisible}">
                                  <button id="cancel_btn" type="button" class="btn secondary-CTA" onclick="toggle_sections(event)">Cancel Lines</button>
                                  </c:if>
                                </div>
                            </div>
                            </c:if>                                                      
                              <div class="table margin-top-40">                              	
                              <c:if test="${displayHeader <1}">
                                  <div class="thead row">
                                  <h4 class="col-xs-1 item">
                                  <c:if test="${orderDetailsSize > 1}"> 
                                  		<c:set var="style1" value="display:block"></c:set>
										  <%-- <button  type="button" name="selectAll" id="selectAll"  style="${style1}" class="btn secondary-CTA" onclick="checkAll()">Select All</button> --%>
										 <div class="check" style="margin-left:-9px">
											          <input type="checkbox" name="selectAll" id="selectAll" class="css-checkbox"  value=""/>
											          <label for="selectAll" class="css-label checkLabel">
											          </label>
											</div>
		  <script>
		  
			$(document).ready(function() {
				$("#selectAll").click(function() {
					var checkboxes = $("input[type='checkbox']");
					if ($(this).is(':checked')) {
						
						$("input:checkbox[name='lineItem'].unchecked").each(function()
			 					{
								 var lineNo = $(this).val();
								var status = $("#status-"+lineNo).val(); 
								if(status != "Pending"){
									
									$("#check-"+lineNo).prop('checked', true);
									if($(".dispatch_section").css("display")=="block" || $(".return_section").css("display")=="block"){
									$(".qty_section").css('display', 'block');
			 					$("#updatedQuantity-"+lineNo).css('display', 'block');
									$("#updatedQuantity-"+lineNo).prop('disabled', false);
									}
								} 
			 					});
						
					} else {
						checkboxes.prop('checked', false);
						$("input:checkbox:not(:checked)").each(function()	{
		 		    		 var uncheckedLineNo = $(this).val();
		 		    		$(".dispatch_section").css("display","none");
							$(".return_section").css("display","none");
			    			
		 		    		$(".qty_section").css('display', 'none');
		 		    		$("#updatedQuantity-"+uncheckedLineNo).css('display', 'none');
		 					 $("#updatedQuantity-"+uncheckedLineNo).attr('disabled', 'disabled');
		 		    	  });
					};
					
				});
			});
		  
		
		  </script>
									 </c:if>
									</h4>		
                                  	  <h4 class="col-xs-1 item">LINE</h4>
                                      <h4 class="col-xs-2 item">SKU</h4>
                                      <h4 class="col-xs-4 item descHead">DESCRIPTION</h4>
                                      <h4 class="col-xs-3 item">STATUS</h4>
                                      <h4 class="col-xs-2 item">ORDER QUANTITY</h4>
                                      <h4 class="col-xs-2 item">OPEN QUANTITY</h4>
                                       <!-- dispatch_section -->
                                      <h4 class="col-xs-1 item display-none qty_section">QTY?</h4>
                                  </div>
									</c:if>
									</div>
									
                                 <div class="table-body">           
                                 <c:forEach items="${orderDetailsMap}" var="ordDetails">                            
                                       <div class="row table-row  margin-top-20 orderRows" id="div-${ordDetails.value.getOrderLineNumber()}">
                                       <c:set var="statPending" value="Pending" />
	                                       <div class="col-xs-1 item">
	                                       <c:if test="${orderDetailsSize > 1 && ordDetails.value.getOpenQuantity() >= 1.00 && ordDetails.value.getLineItemStatus() != 'Pending'}">
	                                       <c:set var="className" value="css-checkbox unchecked"></c:set>
	                                       		<c:set var="style1" value="display:block"></c:set>
												<div class="check">
                                                    <input type="checkbox" name="lineItem" id="check-${ordDetails.value.getOrderLineNumber()}" class="css-checkbox unchecked" value="${ordDetails.value.getOrderLineNumber()}" onclick="return enableQtyField(this);"/>
                                                    <label for="check-${ordDetails.value.getOrderLineNumber()}" class="css-label"> 
                                                    </label> 
												</div> 
	                                       </c:if>
	                                       <c:if test="${orderDetailsSize > 1 && ordDetails.value.getOpenQuantity() >= 1.00 && ordDetails.value.getLineItemStatus() == 'Pending'}">
	                                       <c:set var="className" value="css-checkbox unchecked"></c:set>
												<div class="check" style="visibility: hidden;">
                                                    <input type="checkbox" name="lineItem" id="check-${ordDetails.value.getOrderLineNumber()}" class="css-checkbox unchecked" value="${ordDetails.value.getOrderLineNumber()}" onclick="return enableQtyField(this);"/>
                                                    <label for="check-${ordDetails.value.getOrderLineNumber()}" class="css-label"> 
                                                    </label> 
												</div> 
	                                       </c:if>
	                                       <c:if test="${orderDetailsSize > 1 && ordDetails.value.getOpenQuantity() == 0.00}">
	                                       <c:set var="className" value="css-checkbox unchecked"></c:set>
	                                       		<input type="hidden" name="lineItem" value="${ordDetails.value.getOrderLineNumber()}"/>
												<div class="check" style="visibility: hidden;">
                                                     <input type="checkbox" name="lineItem" id="check-${ordDetails.value.getOrderLineNumber()}"  class="css-checkbox unchecked" value="${ordDetails.value.getOrderLineNumber()}"/>
                                                     <label for="check-${ordDetails.value.getOrderLineNumber()}" class="css-label"> 
                                                     </label> 
												</div> 	
	                                       </c:if>
	                                        <c:if test="${orderDetailsSize <= 1}">
	                                       		<input type="hidden" name="lineItem" value="${ordDetails.value.getOrderLineNumber()}"/>
												<div class="check" style="visibility: hidden;">
                                                    <input type="checkbox" name="lineItem" id="check-${ordDetails.value.getOrderLineNumber()}" class="css-checkbox unchecked" value="${ordDetails.value.getOrderLineNumber()}"/>
                                                    <label for="check-${ordDetails.value.getOrderLineNumber()}" class="css-label"> 
                                                    </label> 
												</div> 	
	                                       </c:if> 
	                                       </div>
	                                        
                                          <div class="col-xs-1 item"><c:out value="${ordDetails.value.getOrderLineNumber()}"/>
                                          <c:set var="lineNo" value="${ordDetails.value.getOrderLineNumber()}"/> 
                                          <input type="hidden" name="lineNumber" value="${ordDetails.value.getOrderLineNumber()}"/>
                                          </div>
                                          <div class="col-xs-2 item"><c:out value="${ordDetails.value.getSku()}"/>
                                          <input type="hidden" name="sku" value="${ordDetails.value.getSku()}"/>
                                          </div>
                                          <div class="col-xs-4 item desc"><c:out value="${ordDetails.value.getDescription()}"/>
                                          <input type="hidden" name="description" value="${ordDetails.value.getDescription()}"/>
                                          </div>
                                          <div class="col-xs-3 item">&nbsp;<c:out value="${ordDetails.value.getLineItemStatus()}"/>
                                          <input type="hidden" name="status-${ordDetails.value.getOrderLineNumber()}" id="status-${ordDetails.value.getOrderLineNumber()}" value="${ordDetails.value.getLineItemStatus()}"/>
                                          </div>
                                          <div class="col-xs-2 item">
                                          <span class="badge badge-square"><c:out value="${ordDetails.value.getOrderedQuantity()}"/></span>
                                          <input type="hidden" name="orderQunty" value="${ordDetails.value.getOrderedQuantity()}"/>
                                          <c:set var="orderedQuantity" value="${ordDetails.value.getOrderedQuantity()}"/>
                                          </div>
                                          <div class="col-xs-2 item"><%-- <c:out value="${ordDetails.value.getOpenQuantity()}"/> --%>
                                          <%-- <input type="hidden" name="openQunty" value="${ordDetails.value.getOpenQuantity()}"/> --%>
                                          <fmt:parseNumber var="openQuantity" integerOnly="true" type="number" value="${ordDetails.value.getOpenQuantity()}" />
                                          <c:out value="${openQuantity}"/>
                                          <input type="hidden" id="openQty-${ordDetails.value.getOrderLineNumber()}" value="${openQuantity}"/>
                                         <%--  <c:set var="openQuantity" value="${ordDetails.value.getOpenQuantity()}"/> --%>
                                          </div>
                                          <!-- dispatch_section --> 
                                          <div class="col-xs-1 item display-none qty_section">
                                          <c:set var="txtClass" value="form-control wd50 qty"/>
                                           <c:choose>
										    <c:when test="${openQuantity == 1}">
										    <c:set var="text2" value="1"></c:set>	
									        	 <input style="height:auto" type="text" maxlength="401" value="1" id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}" name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}" placeholder="" class ="form-control wd50" disabled/>									       		
										    </c:when>
										    
										    <c:when test="${openQuantity <= 0}">
									        	 <input type="text" maxlength="401" value="0" id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}" name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}" placeholder="" class ="form-control wd50" style="display:none;"/>									       		
										    </c:when>
										    
										   <c:when test="${orderDetailsSize > 1 && openQuantity > 1}">
										   <c:set var="text1" value=""></c:set> 
												<input maxlength="401" style="height:auto" type="text" value="${ordDetails.value.getOpenQuantity()}" id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}" name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}" placeholder="" class="${txtClass}" disabled onblur="return validateQty('updatedQuantity-${ordDetails.value.getOrderLineNumber()}');"/> 
											</c:when>

											<c:when test="${orderDetailsSize == 1 && openQuantity > 1}">
											<c:set var="text1" value=""></c:set> 
											<input maxlength="401" type="text" style="height:auto" value="${ordDetails.value.getOpenQuantity()}" id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}" name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}" placeholder="" class="${txtClass}" style="display:block;" onblur="return validateQty('updatedQuantity-${ordDetails.value.getOrderLineNumber()}');"/> 
											</c:when>
										</c:choose>  
										  
                                          </div>  
					               <div class="col-xs-16 item">
					           		 <div id="err-${ordDetails.value.getOrderLineNumber()}" style="font-size: medium; color: red;"></div>
					               </div>
                                      </div>
                                      </c:forEach> 
                                  </div>         
                                                          
                           <div class="table-body display-none dispatch_section dispatchSec">
                                  <!--The following is for a single row -->
                                  <div class="row table-row  margin-top-20 margin-right-0 padding-right-0">                                		                              
	                                     <div class="col-xs-6 item padding-left-0 padding-right-0">
	                                     <label>TRACKING ID</label>
	                                     <div >
	                                     <input type="text" placeholder="Enter a tracking id" maxlength="401"  name="trackingId" id="trackingId" onblur="return alphanumericspaces(this);">
	                                     </div>
	                                     <div class="" id="errorTracking" style="font-size: medium; color: red;">
	                                     </div>
	                                     </div>
	                                   
	                                     <div class="col-xs-6 item padding-right-0">
	                                     <label>CARRIER NAME</label>
	                                     <div>
	                                     <input class="col-xs-10" type="text" maxlength="401" placeholder="Enter a carrier name"  name="carrierName" id="carrierName" onblur="return alphanumericspaces(this);">
	                                     </div>
	                                      <div class="" id="errorCarrier" style="font-size: medium; color: red;">
	                                     </div>
	                                     </div>	                                     
	                                   	                                      
	                                     <div class="item  margin-right-0 padding-right-0 pull-right" style="margin-right:18px"> 
	                                     <div class="pull-right">
	                                     <button type="button" id="cancelDispatch_btn" class="btn secondary-CTA" onclick="hide_updateBoxes(event)">Cancel</button>
	                                     <button type="button" class="btn primary-CTA ml-10" id="confirmDispatch_btn" onclick="return confirmDispatch();">Confirm</button>
	                                     </div>
	                                     </div>
	                                  
	                                </div>
	                          </div>
	                          
	           <script type="text/javascript">
 			   function enableQtyField(data){
 				  if($(".dispatch_section").css("display")=="block" || $(".return_section").css("display")=="block"){
 					 var flag = 0;
 					  $("input:checkbox:not(:checked)").each(function()	{
 					  
 		    		var uncheckedLineNo = $(this).val();
 		    		
 		    		var selectAllID = $(this).attr('id');
 		    		
 		    		if(selectAllID!="selectAll"){
 		    			$(".qty_section").css('display', 'none');
 	 		    		$("#updatedQuantity-"+uncheckedLineNo).css('display', 'none');
 	 					$("#updatedQuantity-"+uncheckedLineNo).attr('disabled', 'disabled');
 		    		}
 		    	  });
 				  
 		    	 var checked = 0;
 				 $("input:checkbox[name='lineItem']:checked").each(function()
 					{
 					 flag++;
					var selectAllID = $(this).attr('id');
 		    		if(selectAllID!="selectAll"){
 		    			var lineNo = $(this).val();
 	 					$(".qty_section").css('display', 'block');
 	 					$('#selectAll').prop('checked', false);
 	 					$("#updatedQuantity-"+lineNo).css('display', 'block');
 	 					$("#updatedQuantity-"+lineNo).attr('disabled', false);
 	  					//alert("showing");
 		    		} 
 					});
 				if(flag == 0)
				  {
						//alert("hide");
						$(".dispatch_section").css("display","none");
						$(".return_section").css("display","none");
		    			$(".qty_section").css("display","none");
						}
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
 				  } 	
	           }
 			   
 			  function validateQty(qtyFieldId)
				{ 
					var qtyField = $("#"+qtyFieldId);
					var lineNo = qtyFieldId.substring(16 , 18);
					var qty = parseFloat(qtyField.val());
					 if(!$.isNumeric($('#'+qtyFieldId).val())){
						 $("#err-"+lineNo).html("Quantity field can not have alphabets.");
						 return false;
					}
					 
					 else if(qty <= 0 || qty == ""){														
						 $("#err-"+lineNo).html("Enter data for quantity field.");
						 return false;
					 }	
					
					  else if(qty.length > 400){														
						 $("#err-"+lineNo).html("This field can not have more than 400 characters.");
						  return false;
					 }
					
					 else {
				      			var qty = $("#updatedQuantity-"+lineNo).val();
				      			var openQty = $("#openQty-"+lineNo).val();
				      				if(openQty == 0){
				      					alert("Open quantity is Zero. No operation can be performed!!!");
					      				flag= false;
				      				} else if(qty < 1 || qty == ""){
					      				alert("Please enter quantity to be updated!!");
					      				flag= false;
				      				} else {
				      					var openQty = parseFloat($("#openQty-"+lineNo).val());
										 var givenQty = parseFloat($("#updatedQuantity-"+lineNo).val());
										 if(givenQty <= openQty) {
											$("#err-"+lineNo).html("");
											flag = true;
										 } else {
										 	$("#err-"+lineNo).html("<%= LanguageUtil.get(pageContext, "ORDMGT-011") %>");
											flag = false; 
										 } 
				      				}
						 }
				}

         
			function alphanumericspaces(txt)
			{
				
				var stdMsg = "This field can not have more than 400 characters.";
				var stdMsg2 = "Space is not allowed.";
				var numaric = txt.value;
				if(numaric.length >= 400){
					if(txt.id == "trackingId"){	
						document.getElementById("errorTracking").innerHTML = stdMsg;	
						 return false;
					 } else if(txt.id == "carrierName"){	
						 document.getElementById("errorCarrier").innerHTML = stdMsg;	
						 return false;
						 } 
					 else {
						 document.getElementById("errorTracking").innerHTML = "";	
						 document.getElementById("errorCarrier").innerHTML = "";	
						 return true;
					 }
				}
				if(numaric.match(/\s/g)){
					 if(txt.id == "trackingId"){	
						 document.getElementById("errorTracking").innerHTML = stdMsg2;	
						 return false;
					 }
					 else {
						 document.getElementById("errorTracking").innerHTML = "";	
						 return true;
					 }
					if(txt.id == "carrierName"){	
						 document.getElementById("errorCarrier").innerHTML = stdMsg2;	
						 return false;
					 }
					else{
						 document.getElementById("errorCarrier").innerHTML = "";	
						return true;
					}
				 }	
				 document.getElementById("errorTracking").innerHTML = "";	
				 document.getElementById("errorCarrier").innerHTML = "";	
				 return true;
			 
			}	
									
			</script>		
					
                                  
                                  <div class="table-body display-none return_section cancelSec">                                                           
                                     <div class="row table-row  margin-top-20">
	                                     <div class="col-xs-10 item padding-left-0">
	                                     <label class="padding-left-0">PRIMARY REASON</label>
	                                     <div class="col-xs-11">
	                                       <select id="primaryCancelReason" class="form-control" name="primaryCancelReason" onchange="return checkReasons();">
										        <option value="defaultPri" selected> Select Box </option>
										        <option value="200">Faulty Product</option>
										        <option value="225">Damaged product</option>										       
										        <option value="250">Missing items</option>
										        <option value="275">Missing Components</option>
										        <option value="300">No longer wanted</option>
										        <option value="325">Product not as expected</option>
										        <option value="350">Wrong description on website</option>
										        <option value="375">Emergency product withdrawal</option>
										        <option value="400">Never delivered</option>
										        <option value="425">Problem with other item, returning all items</option>
										        <option value="450">Late delivery not within the expected date</option>
										 </select> 
									  <span class="dd-icon"><b class="caret"></b></span>
	                                  </div>
	                                  <div class="col-xs-16" id="errorPriReason" style="padding-bottom:15px; color: red;">
	                                     </div>
	                                     </div>
	                                     
	                                                       
	                                      <div class="col-xs-2 item">
                                  		 <div id="errordiv1" style="font-size: medium; color: red; font-weight: bold;"></div>
                                  		</div>	                                    
	                                     <script type="text/javascript">
	                                     	function checkReasons(){
	                                     		var ordStat = $("#ordStatus").val();
	                                     		var remaining = $("input[type='checkbox']");
	                            				var totalCheckboxes = remaining.length;
	                            				
	                            				if(totalCheckboxes == 1 && ordStat === "Pending"){
	                            					$("#confirmDispatch_btn").attr('disabled','disabled');
	                            					$("#confirmButton").attr('disabled','disabled');
	                            					alert("Order already in pending state.No other operation can be performed!!!");
	                            				}
	                            				else {
	                            					var strPrimaryReason = $("#primaryCancelReason").val();
		                                     		if(strPrimaryReason != ""){
		                                     			if(strPrimaryReason === "defaultPri"){
			                                     			document.getElementById("errorPriReason").innerHTML = "You must select primary reason for return / refund of orders!!!";
			                                     			document.getElementById("confirmButton").disabled=true;
			                                     			return false;
		                                     			} else {
		                                     				document.getElementById("errorPriReason").innerHTML = "";
			                                     			document.getElementById("confirmButton").disabled=false;
			                                     			return true;
		                                     			}	                                     			
		                                     		}
	                            				}
	                                     		
	                                     	}                                     	
	                                      </script>
	                                     <div class="col-xs-5 item  margin-right-0 padding-right-0" style="margin-left:28px"> 
	                                     <div class="pull-right">
	                                     <button type="button" id ="cancelC_btn" class="btn secondary-CTA" onclick="hide_updateBoxes(event);">Cancel</button>
	                                     <!-- <button type="submit" class="btn primary-CTA ml-10" id="confirmButton" disabled>Confirm</button> -->
	                                     <button type="button" class="btn primary-CTA ml-10" id="confirmButton" disabled onclick="return confirmCancel();">Confirm</button>
	                                     </div>
	                                     </div>
                                     </div>
                                     </div>
                        </section>
                        </div>
                    </section>
                    <c:set var="displayHeader" value="${displayHeader+1}"/>
                 
                    </section>
                </div>
 </div>
</form>
</body>
</html>