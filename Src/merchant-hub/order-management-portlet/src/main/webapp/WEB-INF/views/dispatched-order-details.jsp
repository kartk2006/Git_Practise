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
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js">
                                                                                                                                                                                                            
    </script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js">
                                                                                                                                                                                                            
    </script>
    <![endif]-->
    <style type="text/css">
    .ml-10{margin-left:10px;}
    .wd50{ width:50px;}
    .dsp-none{display:none;}
    select{padding-right: 0px !important;}
    
 </style>
   
<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var ="renderDisplayOpenOrders">
 <portlet:param name="action" value="renderDisplayDispatchOrders"/>
</portlet:renderURL>
  
<portlet:actionURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="confirmActionUrl">
  <portlet:param name="action" value="returnOrder"/>
</portlet:actionURL>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui-1.9.2.custom.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/numeric.js"></script> --%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui.css">
<script type="text/javascript">

function BackToPreviousPage(){
    	var url = "<%=renderDisplayOpenOrders%>";
    	document.getElementById("frm1").action = url;
		document.getElementById("frm1").submit(); 
    }
 
function toggleButton(){
	var total = $("input[type=radio]").length;
	if ($("input[name='radioLineNumber']:checked").val() || total == 1) {
		$('#return_section').toggle();
	}
}

function validateReturn(){
	var totalRadios = $("input[type=radio]").length;
	//alert(totalRadios);
	if( totalRadios > 1 ){
		alert("inside if bloack");
		if (!$("input[name='radioLineNumber']:checked").val()) {
	 	       alert('No order ID selected! Please select an Order to be returned.');
	 	        return false;
	 	    }
	 	 else {
	 		var radioVal = $("input[name='radioLineNumber']:checked").val();
	       	var returnQty = $("#updatedQuantity-"+radioVal).val();
	       	if(returnQty <= 0 || returnQty == ""){
	       		 alert('Please provide quantity to be returned!!');
	    	        return false;
	       	}
	       	else {
	       		//alert("inside if :: qty edited " + returnQty);
	       		if(!validateQty("updatedQuantity-"+radioVal)){
	       			return false;
	       		}
	       			var refundAmt = $("#refundAmount").val();
	               	if(refundAmt <= 0 || refundAmt == "" || refundAmt == "£"){
	               		alert('Please provide refund amount!!');
	            	        return false;
	               	} else {
	               		//alert("validateRefund =" + validateRefund());
	               	if(!validateRefund()){
	               		return false;
	               	}
	             		var primaryReturnReason =$("#primaryReturnReason").val();
	             		var secondaryReturnReason =$("#secondaryReturnReason").val();
	             		if(primaryReturnReason == "" || secondaryReturnReason == "" || primaryReturnReason === "defaultPri" || secondaryReturnReason === "defaultSec"){
	          				alert('Provide reasons for refund');
	              			return false;
	              		}
	             		else {
	             			return true;
	             		}
	               }
	      	 }
	 	 }
	}
	else {
		
		var radioBtnID = $("input[type='radio']").attr("id");
		var lineNo = radioBtnID.substring(6,8);
		alert("lineNo = " +lineNo);
		var qty = $("#updatedQuantity-"+lineNo).val();
		
		//alert(" radioBtnID = " + radioBtnID + " qty = " +qty);
		//var refndAmt = document.getElementById("refundAmount");
			if(qty <= 0 || qty == ""){
				alert("Please enter quantity to be updated!!");
				return false;
				} 
			
			else {
				//alert("inside else block");
				var dispID = $("#dispatchedQty-"+lineNo);
				//alert(("#dispatchedQty-"+lineNo));
				var dispatchQty = dispID.val();
				 if(qty <= dispatchQty) {
					$("#err-"+lineNo).html("");
					return true;
				 } else {
				 	$("#err-"+lineNo).html("<%= LanguageUtil.get(pageContext, "ORDMGT-012") %>");
				 	document.getElementById("confirmReturnButton").disabled=true;
					return false; 
				 } 
				 var refundAmt = $("#refundAmount").val();
				 alert(refundAmt);
	               	if(refundAmt <= 0 || refundAmt == "" || refundAmt == "£"){
	               		alert('Please provide refund amount!!');
	            	        return false;
	               	} else {
	               	alert("validateRefund =" + validateRefund());
	               	if(!validateRefund()){
	               		return false;
	               	}
	             		var primaryReturnReason =$("#primaryReturnReason").val();
	             		var secondaryReturnReason =$("#secondaryReturnReason").val();
	             		if(primaryReturnReason == "" || secondaryReturnReason == "" || primaryReturnReason === "defaultPri" || secondaryReturnReason === "defaultSec"){
	          				alert('Provide reasons for refund');
	              			return false;
	              		} 
	             		else {
	             			return true;
	             		}
	               }
			}
		}
	
	//alert("final flag = " + flag);
	//return true;
}

function confirmReturn() {
	//alert("confirmReturn()");
	if(validateReturn()){
		$("#dialog-confirm").html("All order lines within these orders will be updated with the same status and the same reason. Do you confirm (Cancel/Confirm?)");
	    // Define the Dialog and its properties.
	    $("#dialog-confirm").dialog({
	        resizable: false,
	        modal: true,
	        title: "Confirm Return Order Lines",
	        height: 250,
	        width: 500,
	        buttons: {
	        	"Cancel": function () {
	                $(this).dialog('close');
	                $('#frm1')[0].reset();
	                $('.txtQtyBox').prop("disabled", "disabled");
	                $(".ret_sec").css('display', 'none');
	                document.getElementById("confirmReturnButton").disabled=true;
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
</script>

<script type="text/javascript">	    

$("document").ready(function()
			{
		var status = $("#ordStatus").val();
		if(status == "Pending"){
			$("input[type='radio']").prop('disabled', 'disabled');
			$("input[type='radio']").css('display', 'none');
			$("#btnCreateReturnRefund").prop('disabled', 'disabled');
			alert("Only one line item of an order can be returned at a time!!!");
		}
		
			});
			
                 function enableQtyField(data){	
               	 //alert(data.id);
               	 var sub = (data.id).substring(6,8);
   	        	 var elements = document.getElementsByTagName('input');
   	        	 for (var i = 0; i < elements.length; i++) {
	   	        	if (elements[i].type === 'text') {
	   	        		elements[i].disabled = true;
	   	        		} 
   	        		 } 	        	
   	     			var txtID = "updatedQuantity-" + sub;
	              	document.getElementById(txtID).disabled = false;
	              	document.getElementById("refundAmount").disabled = false;
  	         	}
                    	        	 
					function validateQty(qtyFieldId){
						var totalRadios = $("input[type=radio]").length;
						//alert(totalRadios);
						var qtyField = $("#"+qtyFieldId);
						//alert(qtyField);
						var qty = $("#"+qtyFieldId).val();
						//alert(qty);
						var sub = (qtyFieldId).substring(16,18);
						if(qty <= 0 || qty ==""){														
							 $("#err-"+sub).html("Enter data for quantity field.");
							 //qtyField.focus();
							 return false;
						 }	
						else if(!$.isNumeric($("#"+qtyFieldId).val())) {
								$("#err-"+sub).html("Quantity field can not have alphabets.");
								$("#refundAmount").prop("disabled","disabled");
								 return false;
							}
						else{
							var radioVal = null;
							if (totalRadios == 1) {
								radioVal = sub;
							} else {
								radioVal = $("input[name='radioLineNumber']:checked").val();
							}
							
							//alert("radioval = " + radioVal);
				   	       	  var dispatchedQty = parseFloat($("#dispatchedQty-"+radioVal).val());
				   	       	  //var Qty = parseFloat(qty);
				   	          //alert("dispatchedQty :: " + dispatchedQty + " qty = " + qty);
							  if(qty <= dispatchedQty) {
				        			$("#err-"+sub).html("");
							 		return true;
				        	  } else {
				        			$("#err-"+sub).html("<%= LanguageUtil.get(pageContext, "ORDMGT-013") %>");
				        			document.getElementById("confirmReturnButton").disabled=true;
							 		return false;
				        	  }	 	 	
						}
						 /************* IT IS GIVING THIS ERROR IF RETURN_QTY < DISPATCHED_QTY***************** */
						 /* if(qty.match(/\s/g)){
							 $("#errordiv").html("This field can not have spaces between words.");
							 qtyField.value=data.value.replace(/\s/g,'');
							 //qtyField.focus();
							 return false;
							 }  */
						 
						 					
					 
					}

																								
				function validateRefund()
				{
					var totalRadios = $("input[type=radio]").length;
					if (totalRadios > 1) {
						if ($("input[name='radioLineNumber']").is(":checked")) {
							var refundAmt = $("#refundAmount").val();
							//var space = refundAmt.match(/\s/g);
							//alert("space" + space);
							
							if((refundAmt <= 0 || $('#refundAmount').val()=="")){	
								 $("#errorRefundAmt").html("Enter data in refund amount field.");
								 //refundTxt.focus();
								 document.getElementById("confirmReturnButton").disabled=true;
								 return false;
							}
							
							else if(!$.isNumeric($('#refundAmount').val())) {
								$("#errorRefundAmt").html("Refund amount field can not have alphabets.");
								 //refundTxt.focus();
								 document.getElementById("confirmReturnButton").disabled=true;
								 return false;
							}
							
							else if(refundAmt.length >= 400){														
								 $("#errorRefundAmt").html("Refund amount field can not have more than 400 characters.");
								 //refundTxt.focus();
								 document.getElementById("confirmReturnButton").disabled=true;
								 return false;
							}
							else if(refundAmt.match(/\s/g)){
								 $("#errorRefundAmt").html("Refund amount field can not have spaces between words.");
								 //refundTxt.focus();
								 document.getElementById("confirmReturnButton").disabled=true;
								 return false;
							}	
							else{
								var radioVal = $("input[name='radioLineNumber']:checked").val();
				   	        	var returnQty = parseFloat($("#updatedQuantity-"+radioVal).val());
				   	        	var costPerItem = parseFloat($("#RRP-"+radioVal).val());
				   	        	 
				   	        	//alert("costPerItem :: " + costPerItem);
				   	        	var refundCost = costPerItem * returnQty;
								//alert("calculated refund = " + refundCost);
								 if(parseFloat($("#refundAmount").val()) <= refundCost) {
				      				 $("#errorRefundAmt").html("");
				      				checkReasons();
					 			 	 return true;
				      			 } else {
				      				$("#errorRefundAmt").html("<%= LanguageUtil.get(pageContext, "ORDMGT-013") %>");
				      				document.getElementById("confirmReturnButton").disabled=true;
					 			 	 return false;
				      			 }
								 $("#errorRefundAmt").html("");
								 checkReasons();
								 return true;
							}
							
						} else {
							$("#errorRefundAmt").html("Select order to be returned!!");
							$("#RefundAmt").val("");
							document.getElementById("confirmReturnButton").disabled=true;
							 return false;
						}
					} else {
						//alert("inside else block");
						var radioBtnID = $("input[type='radio']").attr("id");
						var lineNo = radioBtnID.substring(6,8);
						//alert("lineNo = " +lineNo + "radioBtnID =" +radioBtnID);
						var returnQty = parseFloat($("#updatedQuantity-"+lineNo).val());
		   	        	var costPerItem = parseFloat($("#RRP-"+lineNo).val());
		   	        	 
		   	        	//alert("costPerItem :: " + costPerItem);
		   	        	var refundCost = costPerItem * returnQty;
						//alert("calculated refund = " + refundCost);
						 if(parseFloat($("#refundAmount").val()) <= refundCost) {
		      				 $("#errorRefundAmt").html("");
		      				 
		      				checkReasons();
			 			 	 return true;
		      			 } else {
		      				var refundAmt = $("#refundAmount").val();
		      				if((refundAmt <= 0 || $('#refundAmount').val()=="")){	
								 $("#errorRefundAmt").html("Enter data in refund amount field.");
								 //refundTxt.focus();
								 document.getElementById("confirmReturnButton").disabled=true;
								 return false;
							}
							
							else if(!$.isNumeric($('#refundAmount').val())) {
								$("#errorRefundAmt").html("Refund amount field can not have alphabets.");
								 //refundTxt.focus();
								 document.getElementById("confirmReturnButton").disabled=true;
								 return false;
							}
		      				$("#errorRefundAmt").html("<%= LanguageUtil.get(pageContext, "ORDMGT-013") %>");
		      				document.getElementById("confirmReturnButton").disabled=true;
			 			 	 return false;
		      			 }
						 $("#errorRefundAmt").html("");
						 return false;
					}
			}
				
										
	function hide_updateBoxes(event){
		 $('#frm1')[0].reset();
		 //$('#return_section').disable;
		 $(".ret_sec").css('display', 'none');
		 document.getElementById("confirmReturnButton").disabled=true;
	}
				
</script>

<script type="text/javascript">
        	function checkReasons(){
        		var p = document.getElementById("primaryReturnReason");
        		var strPrimaryReason = p.options[p.selectedIndex].value;	                                     			                                     		
        		var s = document.getElementById("secondaryReturnReason");
        		var strSecondaryReason = s.options[s.selectedIndex].value;	  
        		var refundAmt = $("#refundAmount").val();
        		//alert($('#errorRefundAmt').html());
        		if($('#errorRefundAmt').html() != "" || refundAmt == "£")
        			{
        			document.getElementById("confirmReturnButton").disabled=true;
        			return false;
        			}
        		if(strPrimaryReason != ""){
        			if(strPrimaryReason === "defaultPri"){
         			$("#errorPriReason").html("You must select primary reason for return / refund of orders!!!");
         			document.getElementById("confirmReturnButton").disabled=true;
         			return false;
        			} else {
        				if(strSecondaryReason != "" && strSecondaryReason !== "defaultSec"){
            				$("#errorSecReason").html("");
            				$("#errorPriReason").html("");
		          			document.getElementById("confirmReturnButton").disabled=false;
		          			return true;
             			}
         			}
        		}
        		if(strSecondaryReason != "" && strSecondaryReason !== "defaultSec"){
        				if(strPrimaryReason != "" && strPrimaryReason !== "defaultPri"){
		            		$("#errorPriReason").html("");
		            		$("#errorSecReason").html("You must select secondary reason for return / refund of orders!!!");
		          			document.getElementById("confirmReturnButton").disabled=false;
		          			return true;
             			}
        				else{		                                     			
        					$("#errorPriReason").html("You must select primary reason for return / refund of orders!!!");
            				document.getElementById("confirmReturnButton").disabled=true;
            				return false;
        				}
        		} else {
        			$("#errorSecReason").html("You must select secondary reason for return / refund of orders!!!");
    				document.getElementById("confirmReturnButton").disabled=true;
    				return false;
        		}
        		if(strPrimaryReason != "" && strSecondaryReason != ""){
        			if(strPrimaryReason !== "defaultPri" && strSecondaryReason !== "defaultSec"){
        				$("#errorPriReason").html("");
        				$("#errorSecReason").html("");
	         			document.getElementById("confirmReturnButton").disabled=false;
	         			return true;
        			}
        		}
        	}                                     	
         </script>				  

</head>
<body>
	<div id="dialog-confirm"></div>
    <!--[if lt IE 7]>
                                                                                    <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->
    <!-- Add your site or application content here -->
    <%-- <form action="${confirmActionUrl}" method="post" name="frm1" id="frm1"> --%>
     <form method="post" name="frm1" id="frm1">
    <div id="wrapper" class="wrapper">
        <div class="innerContainer">
                      
            <c:set var="displayHeader" value="0"/>
            <c:set var="orderDetailsSize" value="${fn:length(orderDetailsMap)}"/>
            
            <section id="body" class="body">
                <section class="container body-section">
                	<c:if test="${displayHeader <1}">
	                    <div class="header-row relative">
	                        <div class="backArrow" onclick="BackToPreviousPage()"></div>
	                        <h6>DISPATCHED ORDERS</h6>
	                        <div class="row padding-left-10">
	                            <h1 class="header-alpha col-xs-4">ORDER DETAILS</h1>
	                        </div>
	                    </div>
                    <div class="row margin-bottom-5">
                    <h4 class="ml-10"><a href="${renderDisplayOpenOrders}">All Dispatch Orders</a></h4>
                    </div>
                   <div class="row top-section margin-top-30">
                        <section class="col-xs-5 left-section right-border1px padding-right-0 padding-left-5"> 
                            <h3 class="col-header">Shipping &amp; Delivery</h3>
                            <div class="col-xs-16 padding-left-0">
                                  <label class="col-xs-7 padding-left-0">Delivery Option</label>
                                  <div class="col-xs-9 txt-value">
                                  <span class="padding-5 bg-red"><c:out value="${orderDetails.deliveryOption}"/></span>
                                  <input type="hidden" name="deliveryOption" id="deliveryOption" value="${orderDetails.deliveryOption}"/></div>
                            </div>
                            <div class="col-xs-16 padding-left-0">
                                  <label class="col-xs-7 padding-left-0" >Delivery Cost</label>
                                  <div class="col-xs-9 txt-value" ><b>£</b><c:out value="${orderDetails.deliveryCost}"/>
                                  <input type="hidden" name="deliveryCost" id="deliveryCost" value="${orderDetails.deliveryCost}"/>
                                  </div>                      
                            </div>
                            <div class="col-xs-16 padding-left-0">
                                  <label class="col-xs-7 padding-left-0">Ship to</label>
                                  <div class="col-xs-9 txt-value"><c:out value="${orderDetails.customerFirstName} ${orderDetails.customerLastName}"/>
                                  	<br/>
					  				<c:out value="${orderDetails.shipToAddress}"/>
                                  <input type="hidden" name="shipingTo" id="shipingTo" value="${orderDetails.shipToAddress}"/></div>
                            </div>
                        </section>
                        <section class="col-xs-11">
                            <div class="col-xs-8 left-section padding-left-0">
                                  	<h3 class="col-header pl-18">Order Details</h3>
                              		<div class="col-xs-16">
	                                    <label class="col-xs-7">Order Status</label>
	                                  
	                                    <div class=" col-xs-9 txt-value">
	                                    <span class="padding-5 bg-red"><c:out value="${orderDetails.orderStatus}"/></span>
	                                    <input type="hidden" name="ordStatus" id="ordStatus" value="${orderDetails.orderStatus}"/>
	                                    </div>
                                    </div>
                                    <div class="col-xs-16">
	                                    <label class="col-xs-7">Order Id</label>
	                                   
	                                    <div class="col-xs-9 txt-value"><c:out value="${orderDetails.orderId}"/>
	                                    <input type="hidden" name="orderId" id="orderId" value="${orderDetails.orderId}"/>
	                                    </div>
                             		</div>
                              		<div class="col-xs-16">
	                                    <label class="col-xs-7">Order Placed</label>
	                                    <div class="col-xs-9 txt-value"><c:out value="${orderDetails.orderPlacedDate}"/>
	                                    <input type="hidden" name="placedDate" id="placedDate" value="${orderDetails.orderPlacedDate}"/>
                                    	</div>
                              		</div>
                              		<div class="col-xs-16">
                                    <label class="col-xs-7">Order Value</label>
                                    <div class="col-xs-9 txt-value"><b>£</b><c:out value="${orderDetails.orderValue}"/>
                                    <input type="hidden" name="orderValue" id="orderValue" value="${orderDetails.orderValue}"/>
                                    </div>
                              </div>   
                              </div>
                           
                            <div class="col-xs-8 right-section">
                                 <h3 class="col-header pl-18">Customer Details</h3>
                              <div class="col-xs-16">
                                    <label class="col-xs-5">Name</label>
                                  
                                   <div class="col-xs-11 txt-value"><c:out value="${orderDetails.customerFirstName} ${orderDetails.customerLastName}"/>
                                    <input type="hidden" name="firstName" id="firstName" value="${orderDetails.customerFirstName}"/>
                                    </div>
                              </div>
                              <div class="col-xs-16">
                                    <label class="col-xs-5">Email</label>
                                   
                                    <div class="col-xs-11 txt-value"><c:out value="${orderDetails.customerEmail}"/>
                                    <input type="hidden" name="email" id="email" value="${orderDetails.customerEmail}"/>
                                    </div>
                              </div>
                              <div class="col-xs-16">
                                    <label class="col-xs-5">Telephone</label>
                                    
                                    <!-- *****************Display TELEPHONE NUMBER AND NOT MOBILE NUMBER ************* -->
                                    <div class="col-xs-11 txt-value"><c:out value="${orderDetails.customerMobile}"/>
                                    <input type="hidden" name="mobileNumber" id="mobileNumber" value="${orderDetails.customerMobile}"/>
                                    </div>
                              </div>
                              <div class="col-xs-16">
                                    <label class="col-xs-5">Bill to</label>
                                    <div class="col-xs-11 txt-value"><c:out value="${orderDetails.shipToAddress}"/>
                                    <input type="hidden" name="billTo" id="billTo" value="${orderDetails.shipToAddress}"/>
                                    </div>
                              </div>
                            </div>
                        </section>
						</div>
                    <div class="row margin-top-30 printBtnDiv">
                          <div class="pull-right">   
                          <c:if test="${isOrderDetailsPrintBtnVisible}">
                           <input type="button" class="btn secondary-CTA" id="print" onclick="openPopup()" value="Print" /> 
                           </c:if>                        
                           <!-- <button type="button" class="btn secondary-CTA" id="print" onclick="openPopup()">Print</button> --> 
                            <portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="printPopupURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
								<portlet:param name="jspPage" value="printOrders"/> 
								<portlet:param name="pageDisplay" value="returnLines"/>
						 	</portlet:renderURL>								
 				<script type="text/javascript">
					function openPopup(){										
						var popupUrl = '<%=printPopupURL%>';
						//alert("inside openpopup1()");
						var orderID = document.getElementById("orderId").value;
						//alert(orderID);
						finalPopupURL = popupUrl + "?selectTab:"+"NA"+"*selOrderOpt:" + "NA"+"*orderId:" + orderID;
						//alert(finalPopupURL);
						document.getElementById("frm1").action = "";
						if (window.showModalDialog) {
							 // window.showModalDialog(finalPopupURL,"name","dialogWidth:625px;dialogHeight:750px;center:yes");
							 newwindow = window.open(finalPopupURL,'name','height=750,width=625,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no ,modal=yes');
								 newwindow.print();
						 }  else {
							 newwindow = window.open(finalPopupURL,'name',
						  'height=500,width=500,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no ,modal=yes');
							 newwindow.print();
						 }
						/* if (window.showModalDialog) {
							  window.showModalDialog(finalPopupURL,"name","dialogWidth:625px;dialogHeight:750px");
							 } else {
							  window.open(finalPopupURL,'name',
							  'height=500,width=500,toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no ,modal=yes');
							 }; */
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
                                 <c:if test="${isReturnOdrLinesVisible}">
                                  <button id="btnCreateReturnRefund" type="button" class="btn primary-CTA"  onclick="toggleButton();">Create Return/Refund</button>
                                  </c:if>
                                </div>
                            </div>                            
                          </c:if>                               
                              <div class="table margin-top-40">
                              <c:if test="${displayHeader <1}">
                                  <div class="thead row">
                                  	  <h4 class="col-xs-1 item">&nbsp;</h4>
                                      <h4 class="col-xs-1 item">LINE</h4>
                                      <h4 class="col-xs-2 item">SKU</h4>
                                      <h4 class="col-xs-5 item">DESCRIPTION</h4>
                                      <h4 class="col-xs-2 item">RRP</h4>
                                      <h4 class="col-xs-2 item">ORDER QUANTITY</h4>
                                      <h4 class="col-xs-2 item">DISPATCHED QUANTITY</h4>
                                      <h4 class="col-xs-1 item">QTY?</h4>
                                  </div>
                               </c:if>

                                  <div class="table-body" style="border:none">
                                   <c:forEach items="${orderDetailsMap}" var="ordDetails">  
                                      <div class="row table-row  margin-top-20 orderRows">
                                          <div class="col-xs-1 item" style="height:50px">
                                          
                                            <c:choose>
											    <c:when test="${orderDetailsSize > 1}">
											       		<c:set var="style1" value="display:block"></c:set>
											       		<c:set var="className" value="css-checkbox unchecked"></c:set>
			   											<div class="check">											
														      <input  name="radioLineNumber" type="radio" style="${style1}" id="radio-${ordDetails.value.getOrderLineNumber()}"  class="radio" value="${ordDetails.value.getOrderLineNumber()}" onclick="return enableQtyField(this)" />
														      <label for="${ordDetails.value.getOrderLineNumber()}"></label>											        
														</div>
											    </c:when>
											    
											    <c:otherwise>
											   <!--  id="lineItem" -->
											    <%-- <input type="hidden" name="radioLineNumber" value="${ordDetails.value.getOrderLineNumber()}"/>  --%>
											        <c:set var="style2" value="display:none"></c:set>
			   											<div class="check">	
			   											<input  name="radioLineNumber" type="radio" style="${style2}" id="radio-${ordDetails.value.getOrderLineNumber()}"  class="radio" value="${ordDetails.value.getOrderLineNumber()}"  />
														      <label for="${ordDetails.value.getOrderLineNumber()} style="${style2}"></label>										
														</div>
														<input  name="radioLineNumber" type="hidden"  id="radio-${ordDetails.value.getOrderLineNumber()}"  class="radio" value="${ordDetails.value.getOrderLineNumber()}" />
											    </c:otherwise>
											</c:choose>	
                                          </div>
                                          <div class="col-xs-1 item"><c:out value="${ordDetails.value.getOrderLineNumber()}"/>
                                          <c:set var="lineNo" value="${ordDetails.value.getOrderLineNumber()}"/> 
                                          <input type="hidden" name="lineNumber" id="line-${ordDetails.value.getOrderLineNumber()}" value="${ordDetails.value.getOrderLineNumber()}"/>
                                          </div>
                                          <div class="col-xs-2 item"><c:out value="${ordDetails.value.getSku()}"/></div>
                                          <div class="col-xs-5 item "><c:out value="${ordDetails.value.getDescription()}"/></div>
                                          <div class="col-xs-2 item">£<c:out value="${ordDetails.value.getCostPerItem()}"/>
                                          <input type="hidden" name="RRP-${ordDetails.value.getOrderLineNumber()}" id="RRP-${ordDetails.value.getOrderLineNumber()}" value="${ordDetails.value.getCostPerItem()}"/>
                                          <!-- <input type="hidden" name="deliveryCharges" value="TODO"/> -->
                                          </div>
                                          <div class="col-xs-2 item">
                                          <span class="badge badge-square"><c:out value="${ordDetails.value.getOrderedQuantity()}"/></span>
                                          </div>
                                          <div class="col-xs-2 item"><c:out value="${ordDetails.value.dispatchedQuantity}"/>
                                          <input type="hidden" name="dispatchedQty-${ordDetails.value.getOrderLineNumber()}" id="dispatchedQty-${ordDetails.value.getOrderLineNumber()}" value="${ordDetails.value.dispatchedQuantity}"/>
                                          <fmt:parseNumber var="dispatchQty" integerOnly="true" type="number" value="${ordDetails.value.dispatchedQuantity}" />
                                            <c:set var="dispatchedQty" value="${dispatchQty}"/> 
                                          </div>
                                          <div class="col-xs-1 item">
                                          <c:set var="txtClass" value="form-control wd50 qty"/>
                                          <c:choose>
										    <c:when test="${dispatchedQty == 1}">
										    <c:set var="text2" value="1"></c:set>											        	
										        	 <input type="text" maxlength="400" value="${text2}" id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}" name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}" placeholder="" class="form-control wd50 txtQtyBox" readonly="readonly"/>									       		
										    </c:when>
										    
										    <c:when test="${orderDetailsSize == 1 && dispatchedQty >= 1}">
										    <c:set var="text1" value=""></c:set>											        	
										        	 <input type="text" maxlength="400" value="${ordDetails.value.dispatchedQuantity}" id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}" name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}" class="form-control wd50 txtQtyBox" placeholder="" class="form-control wd50" onblur="return validateQty('updatedQuantity-${ordDetails.value.getOrderLineNumber()}');"/>									       		
										    </c:when>
										    
										     <c:when test="${orderDetailsSize > 1 && dispatchedQty >= 1}">
										    <c:set var="text1" value=""></c:set>											        	
										        	 <input type="text" maxlength="400" value="${ordDetails.value.dispatchedQuantity}" id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}" name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}" class="form-control wd50 txtQtyBox" placeholder="" class="${txtClass}" disabled onblur="return validateQty('updatedQuantity-${ordDetails.value.getOrderLineNumber()}');"/>									       		
										    </c:when>
											</c:choose>    
                                          </div>                                          
	                                      <div class="errordiv" id="err-${ordDetails.value.getOrderLineNumber()}" style="font-size: medium; color: red;"></div>	                                      
                                      </div>
                                      </c:forEach>
                                  </div>                                  
             
                                  <div class="table-body dsp-none ret_sec" id="return_section">                                 
                                     <div class="row table-row  margin-top-20 lightText">
	                                     <div class="col-xs-6 item">
	                                     	<div class="col-xs-16">
	                                     		<label class="col-xs-6">REFUND AMOUNT</label>
	                                     		<div class="col-xs-10">
	                                     		<input maxlength="400" class="pull-right" type="text" placeholder="£"  id="refundAmount" name="refundAmount" onblur="return validateRefund();">
	                                     		</div>
	                                     	</div>
	                                     	<div class="col-xs-16">
	                                     		<span class="smallText">Maximum £###.## (inc. Delivery Cost)</span>
	                                     	</div>
	                                     	<div class="col-xs-16" id="errorRefundAmt" style="font-size: medium; color: red;">
	                                     	</div>
	                                     </div>
	                                     <div class="col-xs-7" style="float:left;">
	                                     <div class="col-xs-16 item">
	                                     <label class="col-xs-6">PRIMARY REASON FOR RETURN</label>
	                                      <div class="col-xs-10">
	                                      <label class="select-label">
										    <select id="primaryReturnReason" name="primaryReturnReason" onchange="return checkReasons()">
										        <option selected value="defaultPri"> Select Box </option>
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
										</label>
										
	                                     </div>
	                                     <div class="col-xs-16" id="errorPriReason" style="padding-bottom:15px; color: red;">
	                                     </div>
	                                     </div>
	                                     <div class="col-xs-16 item">
	                                     <label class="col-xs-6">SECONDARY REASON FOR RETURN</label>
	                                      <div class="col-xs-10">
	                                      <label class="select-label">
										    <select id="secondaryReturnReason" name="secondaryReturnReason" onchange="return checkReasons()">
										        <option value="defaultSec" selected> Select Box </option>
										        <option value="200">Faulty Product</option>
										        <option value="226">Packaging or transit is damaged</option>
										        <option value="227">Packaging is correct</option>
										        <option value="de1">Missing items</option>
										        <option value="de2">Missing Components</option>
										        <option value="301">Cheaper elesewhere</option>
										        <option value="302">Customer ordered wrong product</option>
										        <option value="303">Customer ordered wrong size</option>
										        <option value="304">Customer ordered wrong colour</option>
										        <option value="305">Customer ordered wrong quantity</option>
										        <option value="306">Unable to install / assemble</option>
										        <option value="326">Wrong Product delivered</option>
										        <option value="327">Wrong size delivered</option>
										        <option value="350">Wrong description on website</option>
										        <option value="375">Emergency product withdrawal</option>
										        <option value="401">Assmebly or mis-pack</option>
										        <option value="402">Lost in courier</option>
										        <option value="403">Lost in store</option>
										        <option value="404">Wrong product delivered</option>
										        <option value="405">Fraud suspected</option>
										        <option value="425">Problem with other item, returning all items</option>
										        <option value="450">Late delivery not within the expected date</option>
										    </select>
										</label>
										
	                                     </div>
	                                     <div class="col-xs-16" id="errorSecReason" style="padding-bottom:15px; color: red;">
	                                     </div>
	                                     </div>
	                                     </div>
	                                     <div class="col-xs-3 item pull-right margin-right-0 padding-right-0"> 
	                                     <div class="pull-right">
	                                     <button type="button" class="btn secondary-CTA" id="cancelReturn_btn" onclick="hide_updateBoxes(event)">Cancel</button>
	                                     <button type="button" class="btn primary-CTA ml-10" id="confirmReturnButton" onclick="return confirmReturn()" disabled>Confirm</button>
	                                     <!--  <button type="submit" class="btn primary-CTA ml-10" id="confirmReturnButton">Confirm</button> -->
	                                     </div>
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