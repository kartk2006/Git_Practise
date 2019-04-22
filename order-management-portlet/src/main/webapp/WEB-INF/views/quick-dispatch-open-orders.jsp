<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui"  prefix="aui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/init.jsp"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>

    <liferay-theme:defineObjects/>
	<link rel="stylesheet" href="<%=themeDisplay.getPathThemeCss()%>/jquery-ui.css">
    <script src="<%=themeDisplay.getPathThemeJavaScript() %>/jquery-ui.js"></script>

    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js">
                                                                                                                                                                                                            
    </script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js">
                                                                                                                                                                                                            
    </script>
    <![endif]-->
    
    <portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var ="renderDisplayOpenOrdersURL">
	</portlet:renderURL>

    <portlet:actionURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="confirmActionUrl">
    <portlet:param name="action" value="quickDispatchOpenOrders"/>
    </portlet:actionURL> 
    
    <portlet:actionURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="commitOrders">
	<portlet:param name="action" value="commitOrders"/>
	</portlet:actionURL> 
    
    <script type="text/javascript">
    
    $(document).ready(function() {
    	$('#cancelDispatch_btn').click(function(){
    		backToOrders();
    	});
    	$('#confirlQuickDispatch').click(function(){
    		if(console) console.log('validateTrackingID(): '+validateTrackingID());
    		if(validateTrackingID()){
    			confirmDispatch();
    		}
    	});

    	var $dialog = $('#orderLinesDialog');
   		$dialog.dialog({
   			autoOpen: false,
    	    width: 900,
    	    modal: true,
    	    draggable: true,
    	    resizable: true,
    	    onclose: function(){
    	    	$(this).dialog('destroy');
    	    }
   		});
    	
    	$('.order-line').on('click', function(e){
    		e.preventDefault(); 
    		var cur = $(e.target).siblings('.quickOrderDialog').html();
    		var order_id = $(this).parents('tr').attr('id').substr(4);
    		
    		//var $dialog = $('#orderLinesDialog');
    		$('#orderLinesDialog')
    			.html(cur)
    			.dialog('option','title','ORDER ID: '+order_id)
    			.dialog('open');
   		});
     });
    
    function toggleTable(event){
    	var d=0;
    	$(event.currentTarget).parent().parent().parent().parent().children(".hidden_table").toggle();
    	if($(event.currentTarget).parent().parent().parent().parent().children(".hidden_table").css("display")=="block"){
    		d=90;
    	}
    	$(event.currentTarget).parent().parent().children("span.glyphicon.glyphicon-play").css({
            '-moz-transform':'rotate('+d+'deg)',
            '-webkit-transform':'rotate('+d+'deg)',
            '-o-transform':'rotate('+d+'deg)',
            '-ms-transform':'rotate('+d+'deg)',
            'transform': 'rotate('+d+'deg)'
       });
    }
    
    function backToOrders(){
    	var url = "<%=renderDisplayOpenOrdersURL%>";
		window.location.href=url;
    }
    
    function BackToPreviousPage(){
    	var url = "<%=renderDisplayOpenOrdersURL%>";
		window.location.href=url;
    }
    
    function removeThis(object){
    	var totalSelectedIds = $("#selectedOrderNos").val();
    	//var totalIds = totalSelectedIds.split(",").length;
    	var idArray = new Array();
    	idArray = totalSelectedIds.split(",");
    	    	
    	var buttonClicked = object.id,
    		//divno = buttonClicked.substring(7,17);
    		divno = buttonClicked.substring(7,buttonClicked.length);/*Def#1128*/
    	
    	console.log('idArray: '+idArray+'\nbuttonClicked: '+buttonClicked+'\ndivno: '+divno); //BDz:Testing
    	
    	var totalIds = idArray.length;
    	
    	//if user has selected only 1 order for QD and that too he don't want to dispatch , user shd go back to previous page.
    	if( totalIds == 1){
    		BackToPreviousPage();
    	}
    	
        $("#div-"+divno).remove();
        $("#track-"+divno).remove();
        
		var index = idArray.indexOf(divno);
        if(index < totalIds-1){
        	var replace = divno+',';
            totalSelectedIds = totalSelectedIds.replace(replace,'');
        } else {
        	var replace1 = ','+divno;
            totalSelectedIds = totalSelectedIds.replace(replace1,'');
        }
        
        $("#selectedOrderNos").val(totalSelectedIds);
    }
    
    function confirmDispatch(){
    	$("#dialog-confirm").html("All order lines within these orders will be updated with the same status and the same reason. Do you confirm (Cancel/Confirm?)");
	    $("#dialog-confirm").dialog({
	        resizable: false,
	        modal: true,
	        title: "Confirm Quick Dispatch",
	        height: 250,
	        width: 500,
	        buttons: {
	        	"Cancel": function () {
	                $(this).dialog('close');
	                $('#frm1')[0].reset();  
	                return false;
	                //BackToPreviousPage();
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
    
      
	function fnOpenNormalDialog(event) {
		var size = ${basketSize};
		if (size>0){
			if (event.preventDefault) { 
				event.preventDefault(); 
			}else{ 
				event.returnValue = false; 
			} 
			$("#dialog-confirm").html("This update is final. Do you confirm (Confirm/Cancel?)");
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
    
    function alphanumericspaces(alphane){
		var stdMsg = "This is field can not have more than 400 characters.";	
		var numaric = alphane.value;
		if(numaric != ""){			
			if(numaric.length >= 400){	
				document.getElementById("errordiv").innerHTML = stdMsg;	
				alphane.focus();
				 return false;
			 }
			 if(numaric.match(/\s/g)){
				 document.getElementById("errordiv").innerHTML = "This field can not have spaces between words.";
				 t.value=t.value.replace(/\s/g,'');
				 alhane.focus();
				 return false;
			 }
		}									 
		return true;
    }

    function validateTrackingID(){
    	var quickDispatchItems = $('.quickDispatchItem'), flag = true;
    	var divs = quickDispatchItems.length;
    	if(console) console.log('divs: '+divs);
    	if(divs > 0){
    		var errorFormat = 'The Tracking ID is not in the correct format.',
	    		errorEmpty = 'Please provide a Parcel Tracking ID.',
	    		orderID = '', $trackingID = '';
	    	for(var x=0; x<divs; x++){
	    		
	    		var $deliveryOption = $(quickDispatchItems[x]).find('input[type=hidden][name=deliveryOption]');
	    		
	    		if($deliveryOption.length == 1 && $deliveryOption.val().toLowerCase()=='click & collect'){    		
		    		orderID = $(quickDispatchItems[x]).attr('id').substr(4);
		    		if(typeof orderID != 'undefined' && orderID != ''){
			    		$trackingID = $("input[type=text][name=trackingId-"+orderID+"]");
			    		if($trackingID.length==1){
			    			trackingID = $trackingID.val();
			    			var $errorHolder = $('div[id=err-trackingId-'+orderID+']');
			    			$errorHolder.html('');
					    	if(trackingID.length > 0){
					    		var regEx = /^8([A-Za-z0-9]){12}$/ig;
					    		if(!regEx.test(trackingID)){
					    			flag = false;
					    			$errorHolder = $('div[id=err-trackingId-'+orderID+']');
					    			$errorHolder.html(errorFormat);
					    		}
					    	}else{
					    		flag = false;
					    		$errorHolder = $('div[id=err-trackingId-'+orderID+']');
				    			$errorHolder.html(errorEmpty);
					    	}
				    	}
		    		}else{
		    			if(console.log) console.log('Unable to get orderID: '+orderID);
		    		}
	    		}
	    	}
	    	if(!flag){
	    		//alert(msg);
	    		//document.getElementById("trackingId").select();
	    	}
    	}
    	return flag;
    }
    </script>
    
    <style type="text/css">
    .hidden_table {
    	display:none;
    }
    .error-red {
    	color: #f00;
    }
    /** 3xxx.10 **/
    #orderLinesDialog, .quickOrderDialog{
    	display:none;
    }
    #orderLinesDialog .row {
    	margin-bottom:0;
    }
    #orderLinesDialog .ui-widget-header {
    	border:none;
    	background:none;
    }
    .table-striped thead {
    	font-size: 0.9em;
    	color:#000;
    }
    .error-red {
    	font-size:11px;
    }
    a.order-line {
    	text-decoration: underline;
    }
    span.glyphicon-remove-circle{
		cursor: pointer;
    }
    h4.item {
    	font-weight: bold;
    }
    #orderLinesDialog, .order-titles h4{
    	font-size:13px;
    	font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
    } 
    .table>thead>tr>th, .table>tbody>tr>th, .table>thead>tr>td, .table>tbody>tr>td {
  		padding: 8px 5px;
  	}
  	.ui-dialog .ui-dialog-titlebar {
  		padding-left:2px;
  	}
    </style>
</head>
<body>
<div id="dialog-confirm"></div>
<div class="innerContainer">
<form  method="post" id="frm1" name="frm1">
<input type="hidden" value="${selectedOrderNos}" name="selectedOrderNos" id="selectedOrderNos"/>
<c:set var="displayHeader" value="0"/>
<c:set var="QuickOrderDispatchDetailsSize" value="${fn:length(selectedOrders)}"/>
<section id="body" class="body">
	<section class="container body-section">
	<c:if test="${displayHeader <1}">
	    <div class="header-row relative quickDisheader">
	    <!-- hiding back arrow to close defect id - 773 -->
	       <!--  <div class="backArrow" onclick="BackToPreviousPage()"></div> -->
        	<h5>ORDER MANAGEMENT</h5>
	    </div>
	    <div class="col-xs-12 padding-left-0">
      	<div class="row padding-left-0">
			<h1 class="header-alpha col-xs-4 quickHeading">Quick Dispatch</h1>
        </div>
	    </div>
    </c:if>
    <div class="col-xs-16">
    	<table class="table table-striped">
	      <thead>
	        <tr>
	        	<th class="col-xs-2">ORDER ID</th>
				<th class="col-xs-2">CUSTOMER NAME</th>
				<th class="col-xs-3">ADDRESS LINE 1</th>
				<th class="col-xs-1">LINES</th>
				<th class="col-xs-2">DELIVERY OPTION</th>
				<th class="col-xs-3">TRACKING ID</th>
				<th class="col-xs-3">CARRIER NAME</th>
				<th class="col-xs-1">&nbsp;</th>
	        </tr>
	      </thead>
	      <tbody>
	      <c:forEach items="${selectedOrders}" var="QuickDispDetails">
	        <tr class="quickDispatchItem" id="div-${QuickDispDetails.orderId}">
	          <td>
		          <c:out value="${QuickDispDetails.orderId}" />
		          <input type="hidden" name="orderId" value="${QuickDispDetails.orderId}"/>
		          <input type="hidden" name="ordStatus" value="${QuickDispDetails.orderStatus}"/>
		          <c:set var="orderId" value="${QuickDispDetails.orderId}"/>
	          </td>
	          <td>
	          	<c:out value="${QuickDispDetails.customerFirstName} ${QuickDispDetails.customerLastName}" />
	          	<input type="hidden" name="firstName" value="${QuickDispDetails.customerFirstName}"/>
	          </td>
	          <td>
	          	<c:out value="${QuickDispDetails.quickShipToAddress}"/>
	          	<input type="hidden" name="shipingTo" value="${QuickDispDetails.quickShipToAddress}"/>
	          </td>
	          <td class="order-lines" style="text-align:center;">
		          <a href="#" class="order-line">
		          	${QuickDispDetails.noOfLines}
		          </a>
		          <div class="table col-xs-16 quickOrderDialog">                                
					  <div class="thead row order-titles">
						  <h4 class="col-xs-2 item padding-left-0">LINE</h4>
						  <h4 class="col-xs-3 item">SKU</h4>
						  <h4 class="col-xs-6 item">DESCRIPTION</h4>
						  <h4 class="col-xs-3 item text-center">ORDERED QTY</h4>
						  <h4 class="col-xs-2 item text-center">OPEN QTY</h4>
					  </div>        
					   <c:forEach items="${QuickDispDetails.orderLineItems}" var="entry">                                                  
						  <div class="table-body border-bottom-none margin-bottom-10">
							  <div class="row table-row">
								  <div class="col-xs-2 item"><c:out value="${entry.value.getOrderLineNumber()}" />
								  <input type="hidden" name="lineNumber" value="${entry.value.getOrderLineNumber()}"/>
								  </div>
								  <div class="col-xs-3 item">
								  	<c:if test="${ empty entry.value.getSku()}">
									  	<c:out value="-"/>
										<input type="hidden" name="sku" value="-"/>
									</c:if>
									<c:if test="${not empty entry.value.getSku()}">
									  	<c:out value="${entry.value.getSku()}" />
									  	<input type="hidden" name="sku" value="${entry.value.getSku()}"/>
								  	</c:if>
								  </div>
								  <div class="col-xs-6 item"><c:out value="${entry.value.getDescription()}" />
								  <input type="hidden" name="description" value="${entry.value.getDescription()}"/>
								  </div>
								  <div class="col-xs-3 item text-center"><span class="badge badge-square"><c:out value="${entry.value.getOrderedQuantity()}"/></span>
								  <input type="hidden" name="orderedQuantity" value="${entry.value.getOrderedQuantity()}"/>
								  </div>
								  <div class="col-xs-2 item text-center"><c:out value="${entry.value.getOpenQuantity()}"/>
								  <input type="hidden" name="openQuantity" value="${entry.value.getOpenQuantity()}"/>
								  <c:set var="openQuantity" value="${entry.value.getOpenQuantity()}"/>
								  </div>                                          
							  </div>                                                                                                                                                                            
						  </div>
					   </c:forEach>
				</div>
	          </td>
	          <td>
	          	<c:out value="${QuickDispDetails.deliveryOption}" />
	          	<input type="hidden" name="deliveryOption" value="${QuickDispDetails.deliveryOption}"/>
	          </td>
	          <td>
	          	<input class="form-control" type="text" name="trackingId-${QuickDispDetails.orderId}" onblur="return alphanumericspaces(this);" placeholder="Enter a Tracking ID">
	          	 <div class="error-red" id="err-trackingId-${QuickDispDetails.orderId}"></div>
	          </td>
	          <td>
	          <input class="form-control" type="text" name="carrierName-${QuickDispDetails.orderId}" onblur="return alphanumericspaces(this);" placeholder="Enter a carrier name" >
	          </td>
	          <td>
	          	<span class="glyphicon glyphicon-remove-circle" onclick="removeThis(this);" id="remove-${QuickDispDetails.orderId}"></span>
	          </td>
	        </tr>
	       </c:forEach>
	      </tbody>
	    </table>
    
	<div class="col-xs-16 bottomHR"></div>
	<div class="pull-right">
		<!--  commented in R2 as this cancel button cancels the quick dispatch operation and not enables the cancel order operation -->
		<%-- <c:if test="${isCancelOdrsVisible}"> --%>
		<button type="button" id="cancelDispatch_btn" class="btn secondary-CTA" >Cancel</button>
		<%-- </c:if>	 --%>					
		<!-- <button type="button" class="btn primary-CTA ml-10">Confirm</button> -->
		<button type="button" id="confirlQuickDispatch" class="btn primary-CTA" >Confirm</button>
		<!--<aui:button type="button" id="confirlQuickDispatch" class="btn primary-CTA ml-10" value="Confirm" />-->
	</div> 
</div>
                
	<div class="updateBox hide">
		<c:if test="${isQuickDispatchMultiOdrsVisible && isSubmitFinalOdrVisible}">
			<%@ include file="/WEB-INF/views/orderBasket.jsp"%>
		</c:if>
	</div>                  
	</section>                   
</section>
</form>
</div>
<div id="orderLinesDialog"></div>
</body>
</html>