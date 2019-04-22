<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/views/init.jsp"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>

<liferay-theme:defineObjects />

<link rel="stylesheet"
	href="<%=themeDisplay.getPathThemeCss()%>/jquery-ui.css">

<style type="text/css">
#selectPrimaryCancel div.selectOptions {
	width: 460px;
}

#selectPrimaryCancel span.selected {
	width: 430px;
}

input[id^=updatedQuantity][readonly] {
	-webkit-box-shadow: none !important;
	-moz-box-shadow: none !important;
	box-shadow: none !important;
}

.ml-10 {
	margin-left: 10px;
}

.wd50 {
	width: 50px;
}

.break-word {
	word-wrap: break-word;
}

.div-width-1 {
	width: 3%;
	float: left;
}

.div-width-2 {
	width: 4%;
	float: left;
}

.div-width-3 {
	width: 8%;
	float: left;
	padding-right: 5px;
}

.div-width-4 {
	width: 14%;
	float: left;
}

.div-width-5 {
	width: 13%;
	float: left;
}

.div-width-6 {
	width: 7%;
	float: left;
	text-align: center;
}

.div-width-7 {
	width: 5%;
	float: left;
	text-align: center;
}

.div-width-8 {
	width: 12%;
	float: left;
	text-align: center;
}

.div-width-9 {
	width: 12%;
	float: left;
	text-align: center;
}

.div-width-10 {
	width: 5%;
	float: left;
	text-align: center;
}

.dispatch_section,.cancelSec {
	height: 78px;
	padding-left: 15px;
	margin-left: -10px;
	margin-top: 20px;
}

.div-width-10 input {
	width: 80%;
	text-align: center;
	padding-left: 0px;
}

.col-xs-11 .dd-icon {
	width: 24px;
}

.table .table-body {
	border-bottom: 0px solid #c7c6c4 !important;
}

.margin-right-0 {
	margin-right: 0px;
}

.table .thead h4 {
	margin-bottom: 1px !important;
}

span.selectOption {
	display: block;
	width: 100%;
	line-height: 1.1;
	padding: 5px 10px;
}

div.selectOptions {
	max-height: 120px;
	overflow-y: auto;
	z-index: 1001;
}

.return_section {
	padding: 20px 0;
}

.inline_error {
	font-size: 11px;
	color: #ec2028;
}

#ccPrintLabel>h3,#ccAssignLabel>h3,#viewItemsLabel>h3,.assign-qty>h3 {
	font-weight: bold;
}

.noCCTitle .ui-dialog-titlebar {
	background: none;
	border: none;
	padding: 0 24px;
	margin-top: 10px;
}

.ccbreadcrumb {
	display: block;
	margin: 0;
	text-decoration: none;
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
	color: #4777bf;
	font-size: 11px;
}

.ccbreadcrumb li {
	display: inline;
}

#parcelItems tr,#parcelItems td,#parcelItems tbody,#ccAssignLabel tr,#ccAssignLabel td,#ccAssignLabel tbody,#viewItemsLabel tr,#viewItemsLabel td,#viewItemsLabel tbody
	{
	border: none;
}

#parcelItems thead,#ccAssignLabel thead,#viewItemsLabel thead {
	font-weight: bold;
}

#ccPrintLabel,#ccAssignLabel,#viewItemsLabel {
	font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
}

#ccAssignLabel,#viewItemsLabel {
	margin-top: 13px;
}

#parcelItems,#viewItemsLabel.table {
	margin-top: 10px !important;
}

#parcelItems .table {
	margin-top: 10px !important;
}

#parcelItems .glyphicon {
	top: 0;
}

.right-align-btn {
	text-align: right;
	padding-right: 20px;
}

.primary-CTA-alt {
	text-shadow: none !important;
}

#parcelItems .label-no {
	text-align: center;
	padding-left: 0;
}

#viewItemsLabel .label-no {
	text-align: center;
	padding-left: 0;
}

#parcelItems .remove-tracking-label {
	text-align: right;
	padding-left: 0;
	padding-right: 0;
}

#assign-package-container .assign-package-remove {
	
}

.ajax-loader {
	display: none;
	height: 20px;
	clear: both;
}

.type-2 {
	background: transparent url(../images/ajax-loader-horizontal.gif)
		no-repeat left;
}

.ajax-loader-reset {
	display: none;
	height: 20px;
	clear: both;
}

.type-3 {
	background: transparent url(../images/ajax-loader-horizontal_reset.gif)
		no-repeat left;
}

.pickup-date {
	padding-top: 10px;
	text-align: right;
}

#dateSchedule .ui-widget-header {
	background: none;
	border: none;
	padding: 0 24px;
	margin-top: 10px;
}

#dateSchedule .ui-state-default,#dateSchedule .ui-widget-content .ui-state-default,#dateSchedule .ui-widget-header .ui-state-default
	{
	border: none;
	border-radius: 4px;
	padding: 4px;
}

#dateSchedule .ui-datepicker td {
	border: none;
	height: 20px;
	padding: 5px;
	text-align: center;
	width: 20px;
}

.ui-state-active,.ui-widget-content .ui-state-active,.ui-widget-header .ui-state-active
	{
	background-color: #04c;
	background-repeat: repeat-x;
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	color: #fff;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	border-radius: 4px;
}

.ui-datepicker-calendar tbody {
	border-bottom: none;
}

.noCCTitle .ccbreadcrumb  li {
	font-weight: normal;
}

.noCCTitle .ui-dialog .ui-dialog-titlebar-close {
	margin: -25px 0 0;
}

.noCCTitle.ui-dialog .ui-dialog-title {
	height: 20px;
}

#ccAssignLabel form {
	color: #000;
}

#viewItemsLabel form {
	color: #000;
}

#ccAssignLabel h3 {
	margin-top: 5px;
	margin-bottom: 5px;
}

#viewItemsLabel h3 {
	margin-top: 5px;
	margin-bottom: 5px;
}

#ccAssignLabel thead {
	font-size: 0.8em;
}

#viewItemsLabel thead {
	font-size: 0.8em;
}

#ccAssignLabel tbody {
	font-size: 0.8em;
}

#viewItemsLabel tbody {
	font-size: 0.8em;
}

#viewItemsLabel tbody {
	font-size: 0.8em;
}

#ccAssignLabel tbody td,#parcelItems tbody td,#viewItemsLabel tbody td {
	vertical-align: middle;
}

#ccAssignLabel thead td {
	padding: 5px 5px 0 5px;
}

#viewItemsLabel thead td {
	padding: 5px 5px 0 5px;
}

#ccAssignLabel select {
	-moz-appearance: window;
	-webkit-appearance: menulist;
	width: auto;
	padding: 5px 10px;
}

#ccAssignLabel #ccfrm3 {
	margin-left: 0;
}

#viewItemsLabel #ccfrm4 {
	margin-left: 0;
}

#ccAssignLabel .table {
	margin-left: 25px;
	width: 95%;
}

#viewItemsLabel .table {
	margin-left: 25px;
	width: 95%;
}

#assign-package-container {
	margin-top: 15px;
}

.assign-qty .glyphicon.glyphicon-remove-circle {
	padding-right: 10px;
}

.assign-qty {
	border-top: 1px solid #3d66a4;
}

.add-parcel {
	margin-left: 60px;
	text-decoration: underline;
	-moz-text-decoration-color: #4777bf; /* Code for Firefox */
	text-decoration-color: #4777bf;
}

.ui-widget-content .add-parcel>a {
	color: #4777bf;
}

.ui-widget-overlay {
	opacity: 0.88;
}

.dialog-ccdispatch,#ccfrm2 {
	color: #000;
}

.remove-tracking-label .glyphicon-remove-circle,.assign-qty .glyphicon-remove-circle
	{
	cursor: pointer;
}

p.error-check {
	color: #ec2028;
	marign: 30px;
}

#actionSection {
	padding-left: 0;
}
</style>

<portlet:renderURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="orderListingPage">
	<portlet:param name="prevPage" value="orderDetails" />
</portlet:renderURL>

<portlet:actionURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="confirmActionUrl">
	<portlet:param name="action" value="updateOrder" />
</portlet:actionURL>

<portlet:actionURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="confirmReturnActionUrl">
	<portlet:param name="action" value="returnOrder" />
</portlet:actionURL>

<portlet:renderURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="printPopupURL"
	windowState="<%=LiferayWindowState.POP_UP.toString()%>">
	<portlet:param name="jspPage" value="printOrders" />
	<portlet:param name="pageDisplay" value="dispatchLines" />
</portlet:renderURL>

<portlet:resourceURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="createYodlLabelUrl" id="getYodlLabel">
	<portlet:param name="action" value="getYodlLabel" />
</portlet:resourceURL>

<portlet:resourceURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="getItemAndParcelInfo" id="getItemAndParcelInfo">
	<portlet:param name="action" value="getItemAndParcelInfo" />
</portlet:resourceURL>

<portlet:actionURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="postCandCDispatch">
	<portlet:param name="action" value="postCandCDispatch" />
</portlet:actionURL>

<portlet:resourceURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="resetlLabel" id="resetlLabel">
	<portlet:param name="action" value="resetlLabel" />
</portlet:resourceURL>

<portlet:resourceURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="addYodlLabelUrl" id="addYodlLabel">
	<portlet:param name="action" value="addYodlLabel" />
</portlet:resourceURL>
<portlet:resourceURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="printLabelUrl" id="printLabel">
	<portlet:param name="action" value="printLabel" />
</portlet:resourceURL>
<portlet:resourceURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="printAllLabelUrl" id="printAllLabel">
	<portlet:param name="action" value="PrintAllLabel" />
</portlet:resourceURL>
<portlet:resourceURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="deletelLabelUrl" id="deletelLabel">
	<portlet:param name="action" value="deletelLabel" />
</portlet:resourceURL>
<portlet:resourceURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="deletelItemLabelUrl" id="deletelItemLabel">
	<portlet:param name="action" value="deletelItemLabel" />
</portlet:resourceURL>
<portlet:resourceURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="viewItemLabelUrl" id="viewItemLabel">
	<portlet:param name="action" value="viewItemLabel" />
</portlet:resourceURL>

<script type="text/javascript">
var closeDropdown = function(e){ /*Def#1028*/
    var $visibleDropdown = $('.selectBoxBind .selectOptions:visible');
	if ($visibleDropdown.length > 0 
			&& 
		(!$visibleDropdown.is(e.target) // if the target of the click isn't the container
			&& $visibleDropdown.has(e.target).length === 0) // ... nor a descendant of the container
		&&
		(!$visibleDropdown.siblings().is(e.target)) )// except if the arrow button is clicked
    {
    	$visibleDropdown.hide();
    }
	
};

function openPopup(){
	var popupUrl = '<%=printPopupURL%>';
	var orderID = document.getElementById("orderId").value;
	 finalPopupURL = popupUrl + "?selectTab:"+"NA"+"*selOrderOpt:" + "NA"+"*orderId:" + orderID;
	document.getElementById("frm1").action = "";
	
	newwindow = window.open(finalPopupURL,'name','height=650,width=735,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
	newwindow.print();
}
function enableQtyField(data){
	CheckStatuses(data);
	if($(".dispatch_section").css("display")=="block" || $(".cancelSec").css("display")=="block" || $(".return_section").css("display")=="block"){
		var flag = 0;
		$("input:checkbox:not(:checked)").each(function(){
			var uncheckedLineNo = $(this).val();
			var selectAllID = $(this).attr('id');
			//var count = $("input[type=checkbox]:checked").length;
			//if(selectAllID!="selectAll" && count == 0){
			if(selectAllID!="selectAll"){
				if(($("#cancel_btn").prop('disabled')) && ($("#dispatch_btn").prop('disabled')) && ($("#btnCreateReturnRefund").prop('disabled'))){
					$(".dispatch_section").css('display', 'none');
					$(".cancelSec").css('display', 'none');
					$(".return_section").css('display', 'none');
					$("#updatedQuantity-"+uncheckedLineNo).css('display', 'none');
					$("#updatedQuantity-"+uncheckedLineNo)
						.prop('readonly', true)
						.on('focus click',function(){$(this).blur();}); //Def#880
				}
				//$(".qty_section").css('display', 'none');
			}
		});
		
		if (!(data.checked)){
	    	var unchecked = $(data).val(),
				selectAllID = $(this).attr('id');
			var count = $("input[type=checkbox]:checked").length;
			if(selectAllID!="selectAll"){
				//if (count == 0){
				if(($("#cancel_btn").prop('disabled')) && ($("#dispatch_btn").prop('disabled')) && ($("#btnCreateReturnRefund").prop('disabled'))){
					$(".qty_section").css('display', 'none');
					$(".dispatch_section").css('display', 'none');
					$(".cancelSec").css('display', 'none');
					$(".return_section").css('display', 'none');
				}
				$("#updatedQuantity-"+unchecked).css('display', 'none');
				$("#updatedQuantity-"+unchecked)
					.prop('readonly', true)
					.on('focus click',function(){$(this).blur();}); //Def#880
			}
	    	if($("#err-"+unchecked).html() !=""){
				document.getElementById("err-"+unchecked).innerHTML = "";	
			}
	    	document.getElementsByName("errorInQuantity").innerHTML = " ";
			if($("#errorRefundAmt").html != ""){
				document.getElementById("errorRefundAmt").innerHTML = "";	
			}
			if($("#errorTracking").html != ""){
				document.getElementById("errorTracking").innerHTML = "";	
			}
			if($("#errorCarrier").html != ""){
				document.getElementById("errorCarrier").innerHTML = "";	
			}
	    }
		var statusArray = new Array();
		
		$("input:checkbox[name='lineItem']:checked").each(function(){
			flag++;
			var selectAllID = $(this).attr('id');
			if(selectAllID!="selectAll"){
				var lineNo = $(this).val();
				$(".qty_section").css('display', 'block');
				$('#selectAll').prop('checked', false);
				$("#updatedQuantity-"+lineNo).css('display', 'block');
				$("#updatedQuantity-"+lineNo).prop('readonly', false).prop('disabled', false).off('focus click'); //Def#880
				//Added by Hasan S for defect ID : 1041
				 var lineVal = $("#updatedQuantity-"+lineNo).val();
				 var openQty = $("#openQty-"+lineNo).val();				 
				 if(openQty < 2)
				 {
					if(lineVal == "0" || lineVal == "1" || lineVal == "1.0"){ 
					$("#updatedQuantity-"+lineNo).prop('readOnly',true);
					} 
				 }
				var status = $("#status-"+lineNo).val();
				statusArray.push(status);
			}
		});
		if(flag == 0){
			$('#frm1')[0].reset();
		}
	}else{
		$("input:checkbox[name='lineItem']:not(:checked)").each(function(){
			var uncheckedLineNo = $(this).val();
			var selectAllID = $(this).attr('id');
			if(selectAllID!="selectAll"){
				$("#updatedQuantity-"+uncheckedLineNo).css('display', 'none');
				$("#updatedQuantity-"+uncheckedLineNo)
					.prop('readonly', true)
					.on('focus click',function(){$(this).blur();}); //Def#880
					
				if($("#err-"+uncheckedLineNo).html() !=""){
					document.getElementById("err-"+uncheckedLineNo).innerHTML = "";	
				}// Def#1018	
			}
		});
		
		//Def#880
		$("input:checkbox[name='lineItem']:checked").each(function(){
			var checkedLineNo = $(this).val();
			var selectAllID = $(this).attr('id');
			var openQty = $("#openQty-"+checkedLineNo).val();
			if(selectAllID!="selectAll"){
				if(openQty > 1){
					$("#updatedQuantity-"+checkedLineNo)
						.prop('readonly', false)
						.off('focus click'); //Def#880
				}else{
					$("#updatedQuantity-"+checkedLineNo)
						.prop('readonly', true)
						.on('focus click',function(){$(this).blur();}); //Def#880
				}
			}
		});
	}
}

function CheckStatuses(checkBox){
	var enableDispatch = true,
		enableCancel = true,
		enableReturn = true,
		count = 0;
	$("input[type='checkbox']:checked").each(function(){
		count = count + 1;
		var selectAllID = $(this).attr('id');
		if(typeof selectAllID != 'undefined'){
			var lineNo = (selectAllID).substring(6,8);
			if(selectAllID != "selectAll"){
				var openQty = $("#openQty-"+lineNo).val();
				var dispatchQty = $("#dispatchedQty-"+lineNo).val();
				var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
				var enableFlag = false;

				if(openQty > 0){
					enableFlag = true;
				}else if(acknowldgQty > 0){
					enableFlag = true;
				}
						
				if(!enableFlag) {
					enableDispatch = false;
					enableCancel = false;
				}
				if(dispatchQty <= 0){
					enableReturn = false;
				} 
				//Added by Hasan S for defect ID : 1041				
				 if(openQty < 2)
				 {
					 if(openQty == 0 || openQty ==1){
						$("#updatedQuantity-"+lineNo).prop('readOnly',true);
					} 
				 }				  
			}
		}
	});
	
	if(count==0){
		enableDispatch = false;
		enableCancel = false;
		enableReturn = false;
		$(".cancelSec").css("display","none");
		$(".dispatch_section").css("display","none");
		$(".return_section").css("display","none");
	}
	
	if(enableDispatch){
		$("#dispatch_btn").prop('disabled', false);
	}else{
		$("#dispatch_btn").prop('disabled', true);
	}
	if(enableCancel){
		$("#cancel_btn").prop('disabled', false);
	}else{
		$("#cancel_btn").prop('disabled', true);
	}
	if(enableReturn){
		$("#btnCreateReturnRefund").prop('disabled', false);
	}else{
		$("#btnCreateReturnRefund").prop('disabled', true);
	}
}

function toggle_sections(event){
	
	var flag = 0;
	var total = 0;
	var totalCheck ="${fn:length(orderDetailsMap)}";
	console.log("totalcheck",totalCheck);
	var count = $("input[type=checkbox]:checked").length;
	console.log("count",count);
	
	if(totalCheck == 1) {
		var checkObj = $('input[type=checkbox][id*=check-]');
		checkObj = $(checkObj[0]).attr("id");
		var singleCheckboxID = checkObj;
		var lineNo = (singleCheckboxID).substring(6,8);
		$("#err-"+lineNo).html("");
		$(".qty_section").css('display', 'block');
		$("#updatedQuantity-"+lineNo).css('display', 'block');
		var openQty = $("#openQty-"+lineNo).val();
		var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
		var updatEnblFlg = true;
		if(openQty > 0){
			$("#updatedQuantity-"+lineNo).val(""+openQty);
		}
		if(acknowldgQty > 0){
			$("#updatedQuantity-"+lineNo).val(""+acknowldgQty);
		}
		if (openQty == 1){
			$("#updatedQuantity-"+lineNo)
				.prop('readonly', true)
				.on('focus click',function(){$(this).blur();}); //Def#880
			//$("#updatedQuantity-"+lineNo).prop('disabled', true);
		}else if(openQty > 1){
			//$("#updatedQuantity-"+lineNo).prop('disabled', false);
			$("#updatedQuantity-"+lineNo)
				.prop('readonly', false)
				.off('focus click'); //Def#880;
		} else {
			updatEnblFlg = false;
			//$("#updatedQuantity-"+lineNo).prop('disabled', true);
			$("#updatedQuantity-"+lineNo)
				.prop('readonly', true)
				.on('focus click',function(){$(this).blur();}); //Def#880
		}
		
		if(!updatEnblFlg){
			if (acknowldgQty == 1){
				//$("#updatedQuantity-"+lineNo).prop('disabled', true);
				$("#updatedQuantity-"+lineNo)
					.prop('readonly', true)
					.on('focus click',function(){$(this).blur();}); //Def#880
			} else if(acknowldgQty > 1){
				//$("#updatedQuantity-"+lineNo).prop('disabled', false);
				$("#updatedQuantity-"+lineNo)
					.prop('readonly', false)
					.off('focus click'); //Def#880
			} else {
				//$("#updatedQuantity-"+lineNo).prop('disabled', true);
				$("#updatedQuantity-"+lineNo)
					.prop('readonly', true)
					.on('focus click',function(){$(this).blur();}); //Def#880
			}
		}
		var enableDispatch = true, enableCancel = true, enableReturn = true;
	
		var dispatchQty = $("#dispatchedQty-"+lineNo).val();
		var enableFlag = false;

		if(openQty > 0){
			enableFlag = true;
		}else if(acknowldgQty > 0){
			enableFlag = true;
		}
				
		if(!enableFlag) {
			enableDispatch = false;
			enableCancel = false;
		}
		
		if(dispatchQty <= 0){
			enableReturn = false;
		} 
		if(enableDispatch){
			$("#dispatch_btn").prop('disabled', false);
		}else{
			$("#dispatch_btn").prop('disabled', true);
		}
		if(enableCancel){
			$("#cancel_btn").prop('disabled', false);
		}else{
			$("#cancel_btn").prop('disabled', true);
		}
		if(enableReturn){
			$("#btnCreateReturnRefund").prop('disabled', false);
		}else{
			$("#btnCreateReturnRefund").prop('disabled', true);
		}
		if($("#err-"+lineNo).html() !=""){
			document.getElementById("err-"+lineNo).innerHTML = "";	
		}
		if($("#errorRefundAmt").html != ""){
			document.getElementById("errorRefundAmt").innerHTML = "";	
		}
		if($("#errorTracking").html != ""){
			document.getElementById("errorTracking").innerHTML = "";	
		}
		if($("#errorCarrier").html != ""){
			document.getElementById("errorCarrier").innerHTML = "";	
		}
		if(event.currentTarget.id=="dispatch_btn"){
			$("#buttonClicked").val("dispatch_btn");
			$('input[name=updateOrderAction]').val("dispatch");

			/*R5*/
			console.log("dispatch button clicked");
			var checkDOption = $('#deliveryOption').val().toLowerCase();			
			if(checkDOption === "click & collect"){
				$(".qty_section").css("display","none");
				console.log("c&c",checkDOption);
			    //$("#dialog-confirm").html();
							    
				var ajxYodelUrl = '<%=createYodlLabelUrl%>';
				var orderID = $("#orderId").val();
				$("#ccPrintLabel").find('.error-check').empty().hide();
				// init code to hide dialogbox elements by default 

                var _viewItems = $('#viewItemsLabel'),
                	_parcelItems = $('#parcelItems'),
                    _actionSection = $('#actionSection'),
                    _dateSchedule = $('#dateSchedule'),
                    _ccAssignLabel = $("#ccAssignLabel");
					_viewItems.hide();
                    _parcelItems.hide();
                    _actionSection.hide();
                    _dateSchedule.hide();
                    _ccAssignLabel.hide();
				
				if (ajxYodelUrl && orderID && orderID != "") {				
					$('.ajax-loader').show();
					 ajxYodelUrl += "&orderId=" + orderID + "&collectionDate=" + " ";
					if (console){
						//console.log('Initiating AJAX call to get CC(' +orderID+ ') pricing.\nURL: ' + ajxYodelUrl);
					}
					$.ajax({
							type : "POST",
							url : ajxYodelUrl,
							dataType : 'text'
						}).done(function(msg) {
							
							$("#dialog-ccdispatch").removeClass("hide");
							var msg = $.trim(msg);
							var msg2 = JSON.parse(msg);							
	                          if (msg == '' || msg.indexOf('##ERROR##') != -1) {
	                            if (console) {
	                                console.log('MHUB failed to retrieve C&C.' + msg);
	                            }
	                          } else {
	                        	  console.log('MHUB Passed to retrieve C&C for SINGLE SKU');	                        	
	                          }
	                          var dynamicTitle = '<ol class="ccbreadcrumb"> <li class="active"><strong>PRINT LABELS</strong></li><li> &gt; </li><li>ASSIGN PACKAGES</li></ol>';
	                          if(msg2.isAuthFail !== undefined) {
	                        	  console.log(msg2.errMsg);
	                        	  $("#ccPrintLabel").find('.error-check').show().text(msg2.errMsg);
	                          }
	                          else if(msg2.isResNull !== undefined) {
	                        	  $("#ccPrintLabel").find('.error-check').show().text(msg2.errMsg);
	                          }
	                          else if(msg2.isCalendar !== undefined) {
	                        	   console.log("calendar for singline trans by vk91 111");
		                        	console.log("calendar for singline trans");
		                        	var now = ms2.isDate;
		                        	_dateSchedule.show();
		                        	var firstmsg ="We are unable to schedule a pickup for  "+now+". Please pick another day from the calendar. Please also note that, once generated labels will only be available to print for 48 hours.",
		                        		secondmsg = "We are unable to schedule on this date. Please pick another day from the calendar.";
									if(msg2.isFirst ==true) {
										$('.calendar-error-info').text(firstmsg);
									}else {
									 $('.calendar-error-info').text(secondmsg);
									}
	                        	    $("#lblDatePicker").datepicker(
	 									{onSelect: function(dateText) {
	 						            	console.log(dateText);
	 						            	$("#dateSelected").val(dateText);
	 						        	},
	 						        	minDate:0,
	 						        	 dateFormat: 'yy-mm-dd'
	 						        	});	                        	  
		                      } else {
		                    	  _parcelItems.show();
		                          _actionSection.show();
		                          _dateSchedule.hide();
		                          _parcelItems.find('tbody tr').remove();
		                          _parcelItems.find('tbody').append("<tr/>");
		                    	  
		                    	  var i = 1;
		                          $.each(msg2, function(arrayID,group) {
		                        	  var cDate = group.collectionDate;
			                            cDate = cDate.substring(0,12);
			                            var ndate = new Date(cDate);
			                            var dat = ndate.getDate();
			    	                    if(dat <=9){dat ="0"+dat;}
										dateString =dat + "/" +("0"+ (ndate.getMonth() + 1)).slice(-2)+ "/" + ndate.getFullYear().toString().substr(2,2);			                            
		                        	  var row = $("<tr />");
			                            row.append($('<td class="remove-tracking-label"><span class="glyphicon glyphicon-remove-circle" id="remove-'+group.trackingId+'"></span></td>'));
			                            row.append($('<td class="label-no">' + i + '</td>'));
			                            row.append($('<td>' + group.trackingId + '</td>'));
				                        row.append($('<td>' + dateString + '</td>'));
			                            row.append($('<td><a type="button" class="btn primary-CTA" id="printBtn-'+group.trackingId+'">Print</a></td>')); 
			                            _parcelItems.find('tbody tr').last().after(row);
			                            i++;
		                          });
		                          console.log("message length",msg2.length);
		                          cnt = msg2.length;
		                          if(msg2.length == 1) {
		                            $(".remove-tracking-label span").hide();
		                            $("#createLblPrintAll").hide();
		                          }else {
			                            $(".remove-tracking-label span").show();
			                            $("#createLblPrintAll").show();
			                            if(msg2.length <=3) {
					  			           $("#createLblPrintAll").parent('.right-align-btn').css('left','0');
				  			        	  }
			                            if(msg2.length >3) {
			                            	 $("#createLblPrintAll").parent('.right-align-btn').css('left','-15px');
			  			        	  	}
			                       }
		                      }
	                          $("#dialog-ccdispatch").dialog({
	                            open: function() {
	                                $(this)
	                                  .parent()
	                                  .find(".ui-dialog-title").html(dynamicTitle);
	                                $('.ui-dialog-titlebar-close')
	                                  .html('<span class="ui-button-icon-primary ui-icon ui-icon-closethick"></span>');
	                            }
	                          }).dialog("open");
							
						}).fail(function(jqXHR, textStatus) {
							
							if (console){
								console.log('Request failed with status: ' +textStatus);
							}
							
						}).always(function() {
							$('.ajax-loader').hide();
						});
				
				} else {
					console.log("order id or url is emtpy");				
				}
			    
			    $("#dialog-ccdispatch").dialog({
			        resizable: false,
			        modal: true,
			       // title: "Confirm Quick Dispatch",
			        width: 750,
		            dialogClass: 'noCCTitle'
			    });			    
			/*R5*/	
			} else {
				console.log("not c&c",checkDOption);
				
				if($(".dispatch_section").css("display")=="block"){
					$(".dispatch_section").css("display","none");
					$(".qty_section").css("display","none");
					$(".return_section").css("display","none");
					$("#dispatch_btn, #cancel_btn, #btnCreateReturnRefund").prop('disabled', false);
				} else {
					$(".dispatch_section").css("display","block");
					$(".cancelSec").css("display","none");
					$(".qty_section").css("display","block");
					$(".return_section").css("display","none");
				}
			}
			
		} else if(event.currentTarget.id=="cancel_btn"){
			$("#buttonClicked").val("cancel_btn");
			$('input[name=updateOrderAction]').val("cancel");
			if($(".cancelSec").css("display")=="block"){
				$(".cancelSec").css("display","none");
				$(".qty_section").css("display","none");
				$(".return_section").css("display","none");
				$("#cancel_btn, #dispatch_btn, #btnCreateReturnRefund").prop('disabled', false);
			} else {
				$(".cancelSec").css("display","block");
				$(".dispatch_section").css("display","none");
				$(".qty_section").css("display","block");
				$(".return_section").css("display","none");
			}
		}
	} else {
			$("input:checkbox:not(:checked)").each(function()	{
				total++;
				var uncheckedLineNo = $(this).val();
				var selectAllID = $(this).prop('id');
				if(selectAllID!="selectAll"){
					$(".qty_section").css('display', 'none');
					$("#updatedQuantity-"+uncheckedLineNo).css('display', 'none');
					$("#updatedQuantity-"+uncheckedLineNo)
						.prop('readonly', true)
						.on('focus click',function(){$(this).blur();}); //Def#880
				}
			});
			
			$("input[type='checkbox']:checked").each(function(){
				flag ++;
				total++;
				var selectAllID = $(this).attr('id');
				if(typeof selectAllID != 'undefined'){
				var lineNo = (selectAllID).substring(6,8);
				$("#err-"+lineNo).html("");
				var openQty = $("#openQty-"+lineNo).val();
				var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
				$(".qty_section").css('display', 'block');
				var updatEnblFlg = true;
				
				if(openQty > 0){
					$("#updatedQuantity-"+lineNo).val(""+openQty);
				}
				if(acknowldgQty > 0){
					$("#updatedQuantity-"+lineNo).val(""+acknowldgQty);
				}
				
				if (openQty == 1){
					//$("#updatedQuantity-"+lineNo).prop('disabled', true);
					$("#updatedQuantity-"+lineNo)
						.prop('readonly', true)
						.on('focus click',function(){$(this).blur();}); //Def#880
				}else if(openQty > 1){
					$("#updatedQuantity-"+lineNo).prop('disabled', false);
					$("#updatedQuantity-"+lineNo)
						.prop('readonly', false)
						.off('focus click',function(){$(this).blur();}); //Def#880
				} else {
					updatEnblFlg = false;
					//$("#updatedQuantity-"+lineNo).prop('disabled', true);
					$("#updatedQuantity-"+lineNo)
						.prop('readonly', true)
						.on('focus click',function(){$(this).blur();}); //Def#880
				}
				
				if(!updatEnblFlg){
					if (acknowldgQty == 1){
						//$("#updatedQuantity-"+lineNo).prop('disabled', true);
						$("#updatedQuantity-"+lineNo)
							.prop('readonly', true)
							.on('focus click',function(){$(this).blur();}); //Def#880
					}else if(acknowldgQty > 1){
						$("#updatedQuantity-"+lineNo).prop('disabled', false);
						$("#updatedQuantity-"+lineNo)
							.prop('readonly', false)
							.off('focus click'); //Def#880
					} else {
						//$("#updatedQuantity-"+lineNo).prop('disabled', true);
						$("#updatedQuantity-"+lineNo)
							.prop('readonly', true)
							.on('focus click',function(){$(this).blur();}); //Def#880
					}
				}
				$("#updatedQuantity-"+lineNo).css('display', 'block');
			  }
			});
			if(event.currentTarget.id=="dispatch_btn"){
				$("#buttonClicked").val("dispatch_btn");
				$('input[name=updateOrderAction]').val("dispatch");
				
				/*R5*/	
				console.log("dispatch button clicked multiple items");
				var checkDOption = $('#deliveryOption').val().toLowerCase();				
				if(checkDOption === "click & collect"){
					$(".qty_section").css("display","none");
					console.log("c&c",checkDOption);
				    //$("#dialog-confirm").html();
					$("#ccPrintLabel").find('.error-check').empty().hide();			    
					var ajxYodelUrl = '<%=createYodlLabelUrl%>';
					var orderID = $("#orderId").val();
					var _viewItems = $('#viewItemsLabel'),
					_parcelItems = $('#parcelItems'),
                    _actionSection = $('#actionSection'),
                    _dateSchedule = $('#dateSchedule'),
                    _ccAssignLabel = $("#ccAssignLabel");  
					_viewItems.hide();
                    _parcelItems.hide();
                    _actionSection.hide();
                    _dateSchedule.hide();
                    _ccAssignLabel.hide();
                    
					
					if (ajxYodelUrl && orderID && orderID != "") {				
						$('.ajax-loader').show();
						ajxYodelUrl += '&orderId='+orderID+'&collectionDate='+' ';
						
						if (console){
						//	console.log('Initiating AJAX call to get CC(' +orderID+ ') pricing.\nURL: ' + ajxYodelUrl);
						}
						$.ajax({
								type : "POST",
								url : ajxYodelUrl,
								dataType : 'text'
							}).done(function(msg) {
								$("#dialog-ccdispatch").removeClass("hide");
								var msg = $.trim(msg);
								var msg2 = JSON.parse(msg);							
		                          if (msg == '' || msg.indexOf('##ERROR##') != -1) {
		                            if (console) {
		                                console.log('MHUB failed to retrieve C&C.' + msg);
		                            }
		                          } else {
		                        	  console.log('MHUB Passed to retrieve C&C for MULTI SKU');	                        	
		                          }
		                         var dynamicTitle = '<ol class="ccbreadcrumb"> <li class="active"><strong>PRINT LABELS</strong></li><li> &gt; </li><li>ASSIGN PACKAGES</li></ol>';
		                          if(msg2.isAuthFail !== undefined) {
		                        	  console.log(msg2.errMsg);
		                        	  $("#ccPrintLabel").find('.error-check').show().text(msg2.errMsg);
		                          }
		                          else if(msg2.isResNull !== undefined) {
		                        	  console.log(msg2.errMsg);
		                        	  $("#ccPrintLabel").find('.error-check').show().text(msg2.errMsg);
		                          }
		                          else if(msg2.isCalendar !== undefined) {
			                        	console.log("calendar for multi trans"+msg2.isFirst);
			                        	console.log("calendar for singline trans by vk91 222"+msg2.isCalendar);
			                        	var now = msg2.isDate;
			                        	_dateSchedule.show();
			                        	var firstmsg ="We are unable to schedule a pickup for  "+now+". Please pick another day from the calendar. Please also note that, once generated labels will only be available to print for 48 hours.",
			                        		secondmsg = "We are unable to schedule on this date. Please pick another day from the calendar.";
										if(msg2.isFirst) {
											$('.calendar-error-info').text(firstmsg);
										}else {
										    $('.calendar-error-info').text(secondmsg);
										}
		                        	    $("#lblDatePicker").datepicker(
		 									{onSelect: function(dateText) {
		 						            	console.log(dateText);
		 						            	$("#dateSelected").val(dateText);
		 						        	},
		 						        	minDate:0,
		 						        	 dateFormat: 'yy-mm-dd'
		 						        	});	                        	  
			                      } else {
			                    	  _parcelItems.show();
			                          _actionSection.show();
			                          _dateSchedule.hide();
			                          _parcelItems.find('tbody tr').remove();
			                          _parcelItems.find('tbody').append("<tr/>");
			                    	  
			                    	  var i = 1;
			                          $.each(msg2, function(arrayID,group) {
			                        	  var cDate = group.collectionDate;
				                            cDate = cDate.substring(0,12);
				                            var ndate = new Date(cDate);
				                            var dat = ndate.getDate();
				    	                    if(dat <=9){dat ="0"+dat;}
											dateString = dat+ "/" +("0"+ (ndate.getMonth() + 1)).slice(-2)+ "/" + ndate.getFullYear().toString().substr(2,2);			                            
			                        	  var row = $("<tr />");
				                            row.append($('<td class="remove-tracking-label"><span class="glyphicon glyphicon-remove-circle" id="remove-'+group.trackingId+'"></span></td>'));
				                            row.append($('<td class="label-no">' + i + '</td>'));
				                            row.append($('<td>' + group.trackingId + '</td>'));
					                        row.append($('<td>' + dateString + '</td>'));
				                            row.append($('<td><a type="button" class="btn primary-CTA" id="printBtn-'+group.trackingId+'">Print</a></td>')); 
				                            _parcelItems.find('tbody tr').last().after(row);
				                            i++;
			                          });
			                          console.log("message length",msg2.length);
			                          cnt = msg2.length;
			                          if(msg2.length == 1) {
			                            $(".remove-tracking-label span").hide();
			                            $("#createLblPrintAll").hide();
			                          }else {
				                            $(".remove-tracking-label span").show();
				                            $("#createLblPrintAll").show();
				                            if(msg2.length <=3) {
						  			        	  $("#createLblPrintAll").parent('.right-align-btn').css('left','0');
					  			        	  }
				                            if(msg2.length >3) {
					  			        		   $("#createLblPrintAll").parent('.right-align-btn').css('left','-15px');
					  			        	  }
				                           
				                       }
			                      }
		                          $("#dialog-ccdispatch").dialog({
		                            open: function() {
		                                $(this)
		                                  .parent()
		                                  .find(".ui-dialog-title").html(dynamicTitle);
		                                $('.ui-dialog-titlebar-close')
		                                  .html('<span class="ui-button-icon-primary ui-icon ui-icon-closethick"></span>');
		                            }
		                          }).dialog("open");
								
							}).fail(function(jqXHR, textStatus) {
								
								if (console){
									console.log('Request failed with status: ' +textStatus);
								}
								
							}).always(function() {
								$('.ajax-loader').hide();
							});
					
					} else {
						console.log("order id or url is emtpy");				
					}
				    
				    $("#dialog-ccdispatch").dialog({
				        resizable: false,
				        modal: true,
				       // title: "Confirm Quick Dispatch",
				        width: 750,
			            dialogClass: 'noCCTitle'
				    });			    
				/*R5*/	
				} else {
				console.log("not c&c",checkDOption);
				if($(".dispatch_section").css("display")=="block"){
					$(".dispatch_section").css("display","none");
					$(".qty_section").css("display","none");
					$(".return_section").css("display","none");
					$("#cancel_btn, #dispatch_btn, #btnCreateReturnRefund").prop('disabled', true);
					//$("input[type='checkbox']").removeAttr('checked');
				}else{
					if(flag >= 1 || total == 1){
						$(".dispatch_section").css("display","block");
						$(".cancelSec").css("display","none");
						$(".return_section").css("display","none");
						if(total == 1){
							$(".qty_section").css("display","block");
							$("#updatedQuantity-1").css('display', 'block');
							$("#updatedQuantity-1")
								.prop('disabled', false).prop('readonly', false)
								.off('focus click'); //Def#880
							$("#dispatch_btn, #cancel_btn, #btnCreateReturnRefund").prop('disabled', false);
						}
					}
				}
				}
			}
			else if(event.currentTarget.id=="cancel_btn"){
				$("#buttonClicked").val("cancel_btn");
				//alert("buttonClicked = " + $("#buttonClicked").val());
				$('input[name=updateOrderAction]').val("cancel");
				if($(".cancelSec").css("display")=="block"){
					$(".cancelSec").css("display","none");
					$(".qty_section").css("display","none");
					$(".return_section").css("display","none");
					$("#cancel_btn, #dispatch_btn, #btnCreateReturnRefund").prop('disabled', true);
					//$("input[type='checkbox']").removeAttr('checked');
				} else {
					if(flag >= 1 || total == 1){
						$(".dispatch_section").css("display","none");
						$(".cancelSec").css("display","block");
						$(".return_section").css("display","none");
						if(total == 1){
							$(".qty_section").css("display","block");
							$("#updatedQuantity-1").css('display', 'block');
							$("#updatedQuantity-1")
								.prop('disabled', false).prop('readonly', false)
								.off('focus click'); //Def#880
							
							$("#dispatch_btn, #cancel_btn, #btnCreateReturnRefund").prop('disabled', false);
						}    		
					}
				}
			}
		}
	}

function toggleButton(){
	var total = 0;
	var flag = 0;
	$("#buttonClicked").val("btnCreateReturnRefund");
	$('input[name=updateOrderAction]').val("return");
	var totalCheck = "${fn:length(orderDetailsMap)}";
	var count = $("input[type=checkbox]:checked").length;
	if(totalCheck == 1){
		if($(".return_section").css("display")=="block"){
			var checkObj = $('input[type=checkbox][id*=check-]');
			checkObj = $(checkObj[0]).attr("id");
			var singleCheckboxID = $.trim(checkObj);
			$(".cancelSec").css("display","none");
			$(".qty_section").css("display","none");
			$(".return_section").css("display","none");
			var enableDispatch = true;
			var enableCancel = true;
			var enableReturn = true;
		
			var lineNo = (singleCheckboxID).substring(6,8);
			var openQty = $("#openQty-"+lineNo).val();
			var dispatchQty = $("#dispatchedQty-"+lineNo).val();
			var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
			var enableFlag = false;

			if(openQty > 0){
				enableFlag = true;
			}else if(acknowldgQty > 0){
				enableFlag = true;
			}
					
					
			if(!enableFlag) {
				enableDispatch = false;
				enableCancel = false;
			}
			
			 
			if(dispatchQty <= 0){
				enableReturn = false;
			} 
			
			//alert(" openQty = " + openQty + " dispatchQty = " + dispatchQty);
			if(enableDispatch){
				$("#dispatch_btn").prop('disabled', false);
			}else{
				$("#dispatch_btn").prop('disabled', true);
			}
			if(enableCancel){
				$("#cancel_btn").prop('disabled', false);
			}else{
				$("#cancel_btn").prop('disabled', true);
			}
			if(enableReturn){
				$("#btnCreateReturnRefund").prop('disabled', false);
			}else{
				$("#btnCreateReturnRefund").prop('disabled', true);
			}
			if($("#err-"+lineNo).html() !=""){
				document.getElementById("err-"+lineNo).innerHTML = "";	
			}
			document.getElementsByName("errorInQuantity").innerHTML = " ";
			if($("#errorRefundAmt").html != ""){
				document.getElementById("errorRefundAmt").innerHTML = "";	
			}
			if($("#errorTracking").html != ""){
				document.getElementById("errorTracking").innerHTML = "";	
			}
			if($("#errorCarrier").html != ""){
				document.getElementById("errorCarrier").innerHTML = "";	
			}
			/* $("#cancel_btn").attr('disabled', false);
			$("#dispatch_btn").attr('disabled', false);
			$("#btnCreateReturnRefund").attr('disabled', false); */
		} else {
				var checkObj = $('input[type=checkbox][id*=check-]');
				checkObj = $(checkObj[0]).attr("id");
				var singleCheckboxID = $.trim(checkObj);
				var lineNo = (singleCheckboxID).substring(6,8);
				var dispQty = $("#dispatchedQty-"+lineNo).val();
				$(".qty_section").css('display', 'block');
				$("#updatedQuantity-"+lineNo).css('display', 'block');
				if(dispQty > 0){
					$("#updatedQuantity-"+lineNo).val(""+dispQty);
				}
				if(dispQty == 1){
					$("#updatedQuantity-"+lineNo)
						.prop('readonly', true)
						.on('focus click',function(){$(this).blur();}); //Def#880
					//$("#updatedQuantity-"+lineNo).prop('disabled', true);
				} else if(dispQty > 1){
					$("#updatedQuantity-"+lineNo).prop('disabled', false);
					$("#updatedQuantity-"+lineNo)
						.prop('readonly', false)
						.off('focus click'); //Def#880
				} else {
					//$("#updatedQuantity-"+lineNo).prop('disabled', true);
					$("#updatedQuantity-"+lineNo)
						.prop('readonly',true)
						.on('focus click',function(){$(this).blur();}); //Def#880
				}

				$("#updatedQuantity-"+lineNo).css('display', 'block');
				$(".cancelSec").css("display","none");
				$(".dispatch_section").css("display","none");
				$(".return_section").css('display', 'block');
				var enableDispatch = true;
				var enableCancel = true;
				var enableReturn = true;
			
				var lineNo = (singleCheckboxID).substring(6,8);
				var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
				var openQty = $("#openQty-"+lineNo).val();
				var dispatchQty = $("#dispatchedQty-"+lineNo).val();
				var enableFlag = false;

				if(openQty > 0){
					enableFlag = true;
				}else if(acknowldgQty > 0){
					enableFlag = true;
				}
						
						
				if(!enableFlag) {
					enableDispatch = false;
					enableCancel = false;
				}
				
				if(dispatchQty <= 0){
					enableReturn = false;
				} 
				if(enableDispatch){
					$("#dispatch_btn").prop('disabled', false);
				}else{
					$("#dispatch_btn").prop('disabled', true);
				}
				if(enableCancel){
					$("#cancel_btn").prop('disabled', false);
				}else{
					$("#cancel_btn").prop('disabled', true);
				}
				if(enableReturn){
					$("#btnCreateReturnRefund").prop('disabled', false);
				}else{
					$("#btnCreateReturnRefund").prop('disabled', true);
				}
			}
		} else {
			if (count == 1) {
				$("input:checkbox:not(:checked)").each(function()	{
					var uncheckedLineNo = $(this).val();
					var selectAllID = $(this).attr('id');
					if(selectAllID!="selectAll"){
						$(".qty_section").css('display', 'none');
						$("#updatedQuantity-"+uncheckedLineNo).css('display', 'none');
						$("#updatedQuantity-"+uncheckedLineNo).prop('disabled', true);						
					}
				});
				$("input[type='checkbox']:checked").each(function(){
					flag ++;
					total++;
					var selectAllID = $(this).attr('id');
				 if(typeof selectAllID != 'undefined'){
					var lineNo = (selectAllID).substring(6,8);
					$("#err-"+lineNo).html("");
					var dispQty = $("#dispatchedQty-"+lineNo).val();
					$(".qty_section").css('display', 'block');
					$("#updatedQuantity-"+lineNo).css('display', 'block');
					if(dispQty > 0){
						$("#updatedQuantity-"+lineNo).val(""+dispQty);
					}
					if(dispQty == 1){
						$("#updatedQuantity-"+lineNo)
							.prop('readonly', true)
							.on('focus click',function(){$(this).blur();}); //Def#880
						//$("#updatedQuantity-"+lineNo).prop('disabled', true);
					} else if(dispQty > 1){
						$("#updatedQuantity-"+lineNo).prop('disabled', false);
						$("#updatedQuantity-"+lineNo)
							.prop('readonly', false)
							.off('focus click'); //Def#880
					} else {
						//$("#updatedQuantity-"+lineNo).prop('disabled', true);
						$("#updatedQuantity-"+lineNo)
							.prop('readonly',true)
							.on('focus click',function(){$(this).blur();}); //Def#880
					}
				  }
				});
				if($(".return_section").css("display")=="block"){
					$(".cancelSec, .qty_section, .return_section").css("display","none");
					$("#cancel_btn, #dispatch_btn, #btnCreateReturnRefund").prop('disabled', true);
					//$("input[type='checkbox']").removeAttr('checked');
				}else {
					if(flag >= 1 || total == 1){
						$(".dispatch_section").css("display","none");
						$(".cancelSec").css("display","none");
						$(".return_section").css("display","block");
					}
				}
		} else {
			//alert("Only one line item can be returned at a time!!!");
			showValidationDialog( {msg:'Only one line item can be returned at a time.'} );
			$("#cancel_btn, #dispatch_btn, #btnCreateReturnRefund").prop('disabled', true);
			$(".dispatch_section").css("display","none");
			$(".cancelSec").css("display","none");
			$(".qty_section").css("display","none");
			$(".return_section").css("display","none");
			//$("input[type='checkbox']").removeAttr('checked');
			}
		}
	}

function validateRefund(){
	var totalRads = "${fn:length(orderDetailsMap)}";
	var checkedChkboxes = $("input[type=checkbox]:checked").length;
	if (totalRads > 1) {
		if(checkedChkboxes == 1){
			if ($("input[name='lineItem']").is(":checked")) {
				var refundAmt = $.trim($("#refundAmount").val());
				if(refundAmt==""){	
					$("#errorRefundAmt").html("Enter data in refund amount field.");
					document.getElementById("confirmReturnButton").disabled=true;
					return false;
				}else if(!$.isNumeric(refundAmt)) {
					$("#errorRefundAmt").html("Refund amount field can not have alphabets.");
					document.getElementById("confirmReturnButton").disabled=true;
					return false;
				}else if(refundAmt.length >= 400){														
					$("#errorRefundAmt").html("Refund amount field can not have more than 400 characters.");
					document.getElementById("confirmReturnButton").disabled=true;
					return false;
				}else if(refundAmt.match(/\s/g)){
					$("#errorRefundAmt").html("Refund amount field can not have spaces between words.");
					document.getElementById("confirmReturnButton").disabled=true;
					return false;
				}else{
					refundAmt = parseFloat(refundAmt);
					
					if(refundAmt <= 0 ){
						$("#errorRefundAmt").html("Enter data in refund amount field.");
						document.getElementById("confirmReturnButton").disabled=true;
						return false;
					} else {
						var radioVal = $("input[name='lineItem']:checked").val();
						var returnQty = parseFloat($("#updatedQuantity-"+radioVal).val());
						var costPerItem = parseFloat($("#RRP-"+radioVal).val());
						var deliveryCost = parseFloat($("#deliveryCost").val());
						var refundCost = parseFloat(costPerItem * returnQty) + deliveryCost;
						if(refundAmt <= refundCost) {
							$("#errorRefundAmt").html("");
							checkReturnReasons();
							return true;
						} else {
							$("#errorRefundAmt").html("<%=LanguageUtil.get(pageContext, "ORDMGT-013")%>");
							document.getElementById("confirmReturnButton").disabled=true;
							return false;
						}
					}
				}
			}else{
				$("#errorRefundAmt").html("Select order to be returned!!");
				$("#RefundAmt").val("");
				document.getElementById("confirmReturnButton").disabled=true;
				return false;
			}
		}else{
			alert("Only one item can be returned at a time!!!");
			return false;
		}
	}
	
	var totalRadios = "${fn:length(orderDetailsMap)}";
	if(totalRadios == 1){
		var checkObj = $('input[type=checkbox][id*=check-]');
		checkObj = $(checkObj[0]).attr("id");
		var radioBtnID = $.trim(checkObj);
		var lineNo = radioBtnID.substring(6,8);
		var returnQty = parseFloat($("#updatedQuantity-"+lineNo).val());
		var costPerItem = parseFloat($("#RRP-"+lineNo).val());
		var deliveryCost = parseFloat($("#deliveryCost").val());
		var refundCost = parseFloat(costPerItem * returnQty) + deliveryCost;
		var refundAmt = $.trim($("#refundAmount").val());
		if(refundAmt==""){	
			$("#errorRefundAmt").html("Enter data in refund amount field.");
			document.getElementById("confirmReturnButton").disabled=true;
			return false;
		}else if(!$.isNumeric(refundAmt)) {
			$("#errorRefundAmt").html("Refund amount field can not have alphabets.");
			document.getElementById("confirmReturnButton").disabled=true;
			return false;
		}else if(refundAmt.length >= 400){														
			$("#errorRefundAmt").html("Refund amount field can not have more than 400 characters.");
			document.getElementById("confirmReturnButton").disabled=true;
			return false;
		}else if(refundAmt.match(/\s/g)){
			$("#errorRefundAmt").html("Refund amount field can not have spaces between words.");
			document.getElementById("confirmReturnButton").disabled=true;
			return false;
		}else if(parseFloat($("#refundAmount").val()) <= refundCost) {
			$("#errorRefundAmt").html("");
			checkReturnReasons();
			return true;
		}else{
			refundAmt = parseFloat(refundAmt);
			var deliveryCost = parseFloat($("#deliveryCost").val());
			var refundCost = parseFloat(costPerItem * returnQty) + deliveryCost;
			if(refundAmt <= 0 ){
				$("#errorRefundAmt").html("Enter data in refund amount field.");
				document.getElementById("confirmReturnButton").disabled=true;
				return false;
			} else {
				if(refundAmt <= refundCost) {
					$("#errorRefundAmt").html("");
					checkReturnReasons();
					return true;
				} else {
					$("#errorRefundAmt").html("<%=LanguageUtil.get(pageContext, "ORDMGT-013")%>");
					document.getElementById("confirmReturnButton").disabled=true;
					return false;
				}
			}
		}
	}
	$("#errorRefundAmt").html("");
	return false;
}


function validateReturn(){
	var totalRadios = "${fn:length(orderDetailsMap)}";
	var totalChecked = $("input[type=checkbox]:checked").length;
	if(totalRadios > 1 ){
		if (totalChecked == 1){
			if (!$("input[name='lineItem']:checked").val()) {
				alert('No order ID selected! Please select an Order to be returned.');
				return false;
			}else {
				var radioVal = $("input[name='lineItem']:checked").val();
				var returnQty = $("#updatedQuantity-"+radioVal).val();
				if(returnQty <= 0 || returnQty == ""){
					$("#err-"+radioVal).html("Please provide quantity to be returned!!");
					return false;
				}else{
					if(!validateReturnQty("updatedQuantity-"+radioVal)){
						return false;
					}
					var refundAmt = $("#refundAmount").val();
					if(refundAmt <= 0 || refundAmt == "" || refundAmt == "£"){
						$("#errorRefundAmt").html("Please provide refund amount.");
						return false;
					}else{
						if(!validateRefund()){
							return false;
						}
						var primaryReturnReason =$("#primaryReturnReason").val();
						var secondaryReturnReason =$("#secondaryReturnReason").val();
						if(primaryReturnReason == "" || primaryReturnReason === "defaultPri"){
							$("#errorPriReason").html("Provide primary reason for refund");
							return false;
						} else if (secondaryReturnReason == "" || secondaryReturnReason === "defaultSec"){
							$("#errorSecReason").html("Provide secondary reason for refund");
							return false;
						} else {
							return true;
						}
					}
				}
			}
		} else {
			alert('Only one item can be returned at a time!!!');
			return false;
		}
	}else{
		var checkObj = $('input[type=checkbox][id*=check-]');
		checkObj = $(checkObj[0]).attr("id");
		var radioBtnID = $.trim(checkObj);
		var lineNo = radioBtnID.substring(6,8);
		var qty = $("#updatedQuantity-"+lineNo).val();
		if(qty <= 0 || qty == ""){
			$("#err-"+lineNo).html("Please provide quantity to be returned!!");
			return false;
		}else{
			var dispID = $("#dispatchedQty-"+lineNo);
			var dispatchQty = dispID.val();
			qty = parseFloat(qty);
			dispatchQty = parseFloat(dispatchQty);
			if(qty <= dispatchQty) {
				$("#err-"+lineNo).html("");
			}else{
				$("#err-"+lineNo).html("<%=LanguageUtil.get(pageContext, "ORDMGT-012")%>");
				document.getElementById("confirmReturnButton").disabled=true;
				return false; 
			} 
			var refundAmt = $("#refundAmount").val();
			if(refundAmt <= 0 || refundAmt == "" || refundAmt == "£"){
				$("#errorRefundAmt").html("Please provide refund amount.");
				return false;
			}else{
				if(!validateRefund()){
					return false;
				}
				var primaryReturnReason =$("#primaryReturnReason").val();
				var secondaryReturnReason =$("#secondaryReturnReason").val();
				if(primaryReturnReason == "" || primaryReturnReason === "defaultPri"){
					$("#errorPriReason").html("Provide primary reason for refund");
					return false;
				} else if (secondaryReturnReason == "" || secondaryReturnReason === "defaultSec"){
					$("#errorSecReason").html("Provide secondary reason for refund");
					return false;
				}
			}
		}
		return true;
	}
}

function confirmReturn(){
	if(validateReturn()){
		$("#dialog-confirm").html("All order lines within these orders will be updated with the same status and the same reason. Do you confirm (Cancel/Confirm?)");
		$("#dialog-confirm").dialog({
			resizable: false,
			modal: true,
			title: "Confirm Return Order Lines",
			height: 250,
			width: 500,
			buttons: {
				"Cancel": function (){
					$(this).dialog('close');
					$('#frm1')[0].reset();
					//$('.txtQtyBox').prop("disabled", true);
					//$(".ret_sec").css('display', 'none');
					//$(".qty_section").css('display', 'none');
					//document.getElementById("confirmReturnButton").disabled=true;
					hide_updateBoxes();
					window.location.href ="#";
				},
				"Confirm": function () {
					$(this).dialog('close');
					var url = "<%=confirmReturnActionUrl%>";

					$('#frm1').attr('action', url);
					$("#frm1").submit(); 
				}
			}
		});
	}
}

function validateTrackingID(){
	var trackingID = document.getElementById("trackingId").value,
		flag = false,
		msg = 'The Tracking ID is not in the correct format.';
	if(trackingID.length > 0){
		var regEx = /^8([A-Za-z0-9]){12}$/ig;
		if(regEx.test(trackingID)){
			flag = true;
		}
	}else{
		msg = 'Please provide a Parcel Tracking ID.';
	}
	if(!flag){
		//showValidationDialog({msg:msg, focusOn:"trackingId"});
		document.getElementById("errorTracking").innerHTML = msg;
		document.getElementById("trackingId").select();
	}
	return flag;
}

function showValidationDialog(options){
	if(typeof options.msg != 'undefined' && options.msg != ''){
		var defaults = {
			height: 200
			,width: 500
			,title: 'Message from Merchant Hub'
			,resizable: false
			,modal: true
			,focusOn: ''
		};
		var settings = $.extend({}, defaults, options);
		
		$("#dialog-confirm").html(settings.msg);
		$("#dialog-confirm").dialog({
			resizable: settings.resizable,
			modal: settings.modal,
			title: settings.title,
			height: settings.height,
			width: settings.width,
			buttons: {
				"OK": function () {
					$(this).dialog('close');
					if(typeof settings.focusOn != 'undefined' && settings.focusOn != ''){
						if(typeof settings.focusOn == 'string'){
							try{ document.getElementById(settings.focusOn).select(); } catch(e){}
						}
						if(typeof settings.focusOn == 'object'){
							try{ settings.focusOn.select(); } catch(e){}
						}
					}
				}
			}
		});
	}
}

function validateDispatch(){
	var flag=false,
		flagArray = new Array(),
		textArray = new Array(),
		totalCheck = "${fn:length(orderDetailsMap)}";
	
	if($('#deliveryOption').val().toLowerCase()=='click & collect' && !validateTrackingID()){
		flag = false;
		return flag;
	}
	
	if(totalCheck > 1){
		var trackigID = document.getElementById("trackingId"),
			carrierID = document.getElementById("carrierName");
		if(!$("input[name='lineItem']:checked").val()) {
			flag= false;
		}else if(!alphanumericspaces(trackigID)){
			$("#errorTracking").html("Please recheck values entered in Tracking Id field!!");
			flag = false;
		}else if(!alphanumericspaces(carrierID)) {
			$("#errorCarrier").html("Please recheck values entered in Carrier Name field!!");
			flag = false;
		}else{
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
				var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
				if((openQty == 0) && (acknowldgQty == 0)){
					alert("Open quantity is Zero. No operation can be performed!!!");
					flag= false;
				} else if(qty < 1 || qty == "" || isNaN(qty)){
					$("#err-"+lineNo).html("Please enter quantity to be updated!!");
					flag = false;
				} else if(qty % 1 != 0){ 
					$("#err-"+lineNo).html("Decimal values not allowed");
					return false;
				} else {
					var openQty = parseFloat($("#openQty-"+lineNo).val());
					var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
					var givenQty = parseFloat($("#updatedQuantity-"+lineNo).val());
					
					var enblFlg = false;
					if((givenQty <= openQty)) {
						enblFlg = true;
					}else if(givenQty <= acknowldgQty){
						enblFlg = true;
					}
					
					if(enblFlg) {
						$("#err-"+lineNo).html("");
						flag = true;
					} else {
						$("#err-"+lineNo).html("<%=LanguageUtil.get(pageContext, "ORDMGT-011")%>");
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
	}else {
		var trackigID = document.getElementById("trackingId");
		var carrierID = document.getElementById("carrierName");
		var checkObj = $('input[type=checkbox][id*=check-]');
		checkObj = $(checkObj[0]).attr("id");
		var checkboxID = $.trim(checkObj);
		var lineNo = checkboxID.substring(6,8);
		var qty = $("#updatedQuantity-"+lineNo).val();
		var openQty = $("#openQty-"+lineNo).val();
		var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
		if((openQty == 0) && (acknowldgQty == 0)){
			$("#err-"+lineNo).html("Open quantity is Zero. No operation can be performed!!!");
			flag= false;
		}else if(qty < 1 || qty == "" || isNaN(qty)){
			$("#err-"+lineNo).html("Please enter quantity to be updated!!");
			flag= false;
		} else if(qty % 1 != 0){ 
			$("#err-"+lineNo).html("Decimal values not allowed");
			return false;
		} else if(!alphanumericspaces(trackigID)){
			$("#trackingId").html("Please recheck values entered in Tracking Id field!!");
			flag = false;
		} else if(!alphanumericspaces(carrierID)) {
			$("#carrierName").html("Please recheck values entered in Carrier Name field!!");
			flag = false;
		} else {
			var openQty = parseFloat($("#openQty-"+lineNo).val());
			var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
			var givenQty = parseFloat($("#updatedQuantity-"+lineNo).val());
			var enblFlg = false;
			if((givenQty <= openQty)) {
				enblFlg = true;
			}else if(givenQty <= acknowldgQty){
				enblFlg = true;
			}
			
			if(enblFlg) {
				$("#err-"+lineNo).html("");
				flag = true;
			} else {
				$("#err-"+lineNo).html("<%=LanguageUtil.get(pageContext, "ORDMGT-011")%>");
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
					/* $(".dispatch_section").css("display","none");
					$(".cancelSec").css("display","none");
					$(".qty_section").css("display","none");
					$('.numeric').prop("disabled", true); */
					hide_updateBoxes();
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
	var totalCheck = "${fn:length(orderDetailsMap)}";
	if(totalCheck > 1){
		if(!$("input[name='lineItem']:checked").val()) {
			alert('No order ID selected! Please select an Order to be cancelled.');
			flag = false;
		}else{
			var reasonForCancellation = $("#primaryCancelReason").val();
			if(reasonForCancellation == "defaultPri" || reasonForCancellation == ""){
				$("#errorPriCancelReason").html("Please select reason for cancellation!!");
				flag = false;
			}else {
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
					var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
					if((openQty == 0) && (acknowldgQty == 0)){
						alert("Open quantity is Zero. No operation can be performed!!!");
						flag= false;
					} else if(qty < 0 || qty == "" || isNaN(qty)){
						$("#err-"+lineNo).html("Please enter quantity to be updated!!");
						flag= false;
					} else if(qty % 1 != 0){ 
						$("#err-"+lineNo).html("Decimal values not allowed");
						return false;
					} else if(qty >= 1){
						var openQty = parseFloat($("#openQty-"+lineNo).val());
						var acknowldgQty = parseFloat($("#acknowledgedQty-"+lineNo).val());
						var givenQty = parseFloat($("#updatedQuantity-"+lineNo).val());
						
						var enblFlg = false;
						if((givenQty <= openQty)) {
							enblFlg = true;
						}else if(givenQty <= acknowldgQty){
							enblFlg = true;
						}
						
						if(enblFlg) {
								$("#err-"+lineNo).html("");
							    flag = true;
						} else {
								$("#err-"+lineNo).html("<%=LanguageUtil.get(pageContext, "ORDMGT-011")%>");
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
	}else{
		var checkObj = $('input[type=checkbox][id*=check-]');
		checkObj = $(checkObj[0]).attr("id");
		var checkboxID = $.trim(checkObj);
		var lineNo = checkboxID.substring(6,8);
		var qty = $("#updatedQuantity-"+lineNo).val();
		var openQty = $("#openQty-"+lineNo).val();
		var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
		var reasonForCancellation = $("#primaryCancelReason").val();
		if(reasonForCancellation == "defaultPri" || reasonForCancellation == ""){
			$("#errorPriCancelReason").html("Please select reason for cancellation!!");
			flag = false;
		} else if((openQty == 0) && (acknowldgQty == 0)){
			alert("Open quantity is Zero. No operation can be performed!!!");
			flag= false;
		} else if(qty < 1 || qty == "" || isNaN(qty)){
			$("#err-"+lineNo).html("Please enter quantity to be updated!!");
			flag= false;
		} else if(qty % 1 != 0){ 
			$("#err-"+lineNo).html("Decimal values not allowed");
			return false;
		} else {
			var openQty = parseFloat($("#openQty-"+lineNo).val());
			var acknowldgQty = parseFloat($("#acknowledgedQty-"+lineNo).val());
			var givenQty = parseFloat($("#updatedQuantity-"+lineNo).val());
			
			var enblFlg = false;
			if((givenQty <= openQty)) {
				enblFlg = true;
			}else if(givenQty <= acknowldgQty){
				enblFlg = true;
			}
			
			if(enblFlg) {
				$("#err-"+lineNo).html("");
				flag = true;
			} else {
				$("#err-"+lineNo).html("<%=LanguageUtil.get(pageContext, "ORDMGT-011")%>");
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
					/* $(".dispatch_section").css("display","none");
					$(".cancelSec").css("display","none");
					$(".qty_section").css("display","none");
					$('.numeric').prop("disabled", true); */
					hide_updateBoxes();
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

function disableDivs() {
	
	var count = 1;
	$("input:checkbox[name='lineItem']").each(function(){
			var lineNo = $(this).val();
			var status = $("#status-"+lineNo).val();
			if(status == "Pending"){
				$("#div-"+lineNo).attr('disabled','disabled');
				$("#check-"+lineNo).css('display','none');
				$(".unchecked").css('display','none');
				count++;
				var currentDiv = "div-"+lineNo; 
				var nodes = document.getElementById(currentDiv).getElementsByTagName('*');
				for (var k = 0; k < nodes.length; k++) {
					nodes[k].setAttribute('readonly', true);
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
			if(totalCheckboxes == 1 && ordStat == "Pending"){
				$("#confirmDispatch_btn, #confirmButton").prop('disabled', true);
				alert("Order already in pending state. No other operation can be performed!!!");
			}
	});
}


function hide_updateBoxes(){
	var totalCheck = "${fn:length(orderDetailsMap)}";
	
	$(".dispatch_section").css("display","none");
	$(".return_section").css("display","none");
	$(".cancelSec").css("display","none");
	$(".qty_section").css("display","none");
	$("span.selected").html("Select Box");
	if(totalCheck > 1){
		$("#dispatch_btn, #cancel_btn, #btnCreateReturnRefund").prop('disabled', true);
		//document.getElementsByName("errorInQuantity").innerHTML = " ";
		$("input[type='checkbox']:checked").each(function(){
			var uncheckedLineNo = $(this).val();
			$("#err-"+uncheckedLineNo).html("");
		});
		$('#frm1')[0].reset();
	} else {
		var checkObj = $('input[type=checkbox][id*=check-]');
		checkObj = $(checkObj[0]).attr("id");
		var singleChkID = $.trim(checkObj);
		var lineNo = singleChkID.substring(6,8);
		var openQty = $("#openQty-"+lineNo).val();
		var dispatchedQty = $("#dispatchedQty-"+lineNo).val();
		var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
		$("#err-"+lineNo).html("");
		if((openQty > 0) || (acknowldgQty > 0)){
			$("#dispatch_btn").prop('disabled', false);
			$("#cancel_btn").prop('disabled', false);
		} else if (dispatchedQty > 0) {
			$("#btnCreateReturnRefund").prop('disabled', false);
		} else if ((openQty > 0 ||  acknowldgQty > 0) && dispatchedQty > 0) {
			$("#dispatch_btn").prop('disabled', false);
			$("#cancel_btn").prop('disabled', false);
			$("#btnCreateReturnRefund").prop('disabled', false);
		} else {
			$("#dispatch_btn, #cancel_btn, #btnCreateReturnRefund").prop('disabled', true);
		}
	}
}

function IsAlphaNumeric(e,id) {
	var specialKeys = new Array();
    //specialKeys.push(8); //Backspace
    specialKeys.push(9); //Tab
    specialKeys.push(46); //Delete
    specialKeys.push(36); //Home
    specialKeys.push(35); //End
    specialKeys.push(37); //Left
    specialKeys.push(39); //Right
    var sub = id.substring(16,18);
    var keyCode = e.keyCode == 0 ? e.charCode : e.keyCode;
    var ret = ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 65 && keyCode <= 90) || (keyCode >= 97 && keyCode <= 122) || (specialKeys.indexOf(e.keyCode) != -1 && e.charCode != e.keyCode));
    //document.getElementById("err-"+id).style.display = ret ? "none" : "inline";
    if(!ret){
    	document.getElementById("err-"+sub).innerHTML = "Special characters not allowed.";
    } else {
    	document.getElementById("err-"+sub).innerHTML = "";
    }
    return ret;
}

function validateReturnQty(qtyFieldId){
	var totalRadios = "${fn:length(orderDetailsMap)}";
	var qty = $("#"+qtyFieldId).val();
	var sub = qtyFieldId.substring(16,18);
	$("#refundAmount").prop("disabled", false);	
	if(qty.length > 400){														
		$("#err-"+lineNo).html("This field can not have more than 400 characters.");
		document.getElementById("confirmButton").disabled=true;
		document.getElementById("confirmDispatch_btn").disabled=true;
		$("#refundAmount").prop("disabled", true);
		return false;
	} else if(qty == ""){
		$("#err-"+lineNo).html("Quantity field can not be left blank.");
		document.getElementById("confirmButton").disabled=true;
		document.getElementById("confirmDispatch_btn").disabled=true;
		$("#refundAmount").prop("disabled", true);
		return false;
	} else if(!$.isNumeric($("#"+qtyFieldId).val())  || isNaN(qty)){
		$("#err-"+sub).html("Please only enter numbers in the quantity field.");
		$("#refundAmount").prop("disabled", true);
		document.getElementById("confirmReturnButton").disabled=true;
		return false;
	}  else {
		var radioVal = 0;
		if (totalRadios == 1) {
			radioVal = sub;
		} else {
			radioVal = $("input[name='lineItem']:checked").val();
		}
		var dispatchedQty = parseFloat($("#dispatchedQty-"+radioVal).val());
		qty = parseFloat(qty);
		if(qty % 1 != 0){ 
			$("#refundAmount").prop("disabled", true);
			$("#err-"+sub).html("Decimal values not allowed");
			document.getElementById("confirmReturnButton").disabled=true;
			return false;
		}
		
		if(qty <= 0){	
			$("#refundAmount").prop("disabled", true);
			$("#err-"+sub).html("Enter data for quantity field.");
			document.getElementById("confirmReturnButton").disabled=true;
			return false;
		}
		//alert("comparing dispatchedQty " + dispatchedQty + " wid qty entered by you " + qty);
		if(qty <= dispatchedQty) {
			$("#err-"+sub).html("");
			return true;
		}else {
			$("#err-"+sub).html("<%=LanguageUtil.get(pageContext, "ORDMGT-012")%>"); 
			$("#refundAmount").prop("disabled", true);
			document.getElementById("confirmReturnButton").disabled=true;
			return false;
		}
	}
}

 function validateQty(qtyFieldId){ 
	var qtyField = $("#"+qtyFieldId);
	var lineNo = qtyFieldId.substring(16, 18);
	var openQty = $("#openQty-"+lineNo).val();
	var dispatchedQty = $("#dispatchedQty-"+lineNo).val();
	var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
	var buttonClicked = $("#buttonClicked").val().toUpperCase();
	if(dispatchedQty > 0 && buttonClicked == "btnCreateReturnRefund".toUpperCase()){
		validateReturnQty(qtyFieldId);
	}
	if((openQty > 0 || acknowldgQty > 0) && (buttonClicked == "cancel_btn".toUpperCase() || buttonClicked == "dispatch_btn".toUpperCase())){
		var qty = qtyField.val();
		if(qty.length > 400){														
			$("#err-"+lineNo).html("This field can not have more than 400 characters.");
			document.getElementById("confirmButton").disabled=true;
			document.getElementById("confirmDispatch_btn").disabled=true;
			return false;
		} else if(qty == ""){
			$("#err-"+lineNo).html("Quantity field can not be left blank.");
			document.getElementById("confirmButton").disabled=true;
			document.getElementById("confirmDispatch_btn").disabled=true;
			return false;
		}  else if(!$.isNumeric($('#'+qtyFieldId).val()) || isNaN(qty)){
			$("#err-"+lineNo).html("Please only enter numbers in the quantity field.");
			document.getElementById("confirmButton").disabled=true;
			document.getElementById("confirmDispatch_btn").disabled=true;
			return false;
		}else {
			//var qty = $("#updatedQuantity-"+lineNo).val();
			qty = parseFloat(qty);
			if (qty <= 0 ){
				$("#err-"+lineNo).html("Quantity field can not be left blank.");
				document.getElementById("confirmButton").disabled=true;
				document.getElementById("confirmDispatch_btn").disabled=true;
				return false;
			}
			else if(qty % 1 != 0){ 
				$("#err-"+sub).html("Decimal values not allowed");
				document.getElementById("confirmReturnButton").disabled=true;
				return false;
			}
			/* if(openQty == 0){
				alert("Open quantity is Zero. No operation can be performed!!!");
				return false;
			}else if(qty < 1 || qty == ""){
				alert("Please enter quantity to be updated!!");
				return false;
			} */
			else{
				var openQty = parseFloat($("#openQty-"+lineNo).val());
				var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
				//var givenQty = parseFloat($("#updatedQuantity-"+lineNo).val());
				
				var enblFlg = false;
				if((qty <= openQty)) {
					enblFlg = true;
				}else if(qty <= acknowldgQty){
					enblFlg = true;
				}
				
				
				if(enblFlg) {
					$("#err-"+lineNo).html("");
					document.getElementById("confirmButton").disabled=false;
					document.getElementById("confirmDispatch_btn").disabled=false;
					return true;
				}else{
					$("#err-"+lineNo).html("<%=LanguageUtil.get(pageContext, "ORDMGT-011")%>");
					document.getElementById("confirmButton").disabled=true;
					document.getElementById("confirmDispatch_btn").disabled=true;
					return false; 
				} 
			}
		}
	}
} 


function alphanumericspaces(txt){
	var stdMsg = "This field can not have more than 400 characters.";
	var stdMsg2 = "Space is not allowed.";
	var numaric = txt.value;
	if(numaric.length >= 400){
		if(txt.id == "trackingId"){
			document.getElementById("errorTracking").innerHTML = stdMsg;	
			return false;
		}else if(txt.id == "carrierName"){	
			document.getElementById("errorCarrier").innerHTML = stdMsg;	
			return false;
		}else{
			document.getElementById("errorTracking").innerHTML = "";	
			document.getElementById("errorCarrier").innerHTML = "";	
			return true;
		}
	}
	if(numaric.match(/\s/g)){
		if(txt.id == "trackingId"){	
			document.getElementById("errorTracking").innerHTML = stdMsg2;	
			return false;
		}else{
			document.getElementById("errorTracking").innerHTML = "";	
			return true;
		}
		if(txt.id == "carrierName"){	
			document.getElementById("errorCarrier").innerHTML = stdMsg2;	
			return false;
		}else{
			document.getElementById("errorCarrier").innerHTML = "";	
			return true;
		}
	}	
	document.getElementById("errorTracking").innerHTML = "";	
	document.getElementById("errorCarrier").innerHTML = "";	
	return true;
}

function checkReasons(){
	var ordStat = $("#ordStatus").val();
	var totalCheckboxes = "${fn:length(orderDetailsMap)}";

	if(totalCheckboxes == 1 && ordStat == "Pending"){
		$("#confirmDispatch_btn, #confirmButton").prop('disabled', true);
		alert("Order already in pending state.No other operation can be performed!!!");
	}else{
		var strPrimaryReason = $("#primaryCancelReason").val();
		if(strPrimaryReason != ""){
			if(strPrimaryReason === "defaultPri"){
				document.getElementById("errorPriCancelReason").innerHTML = "You must select primary reason for cancellation of orders!!!";
				document.getElementById("confirmButton").disabled=true;
				return false;
			}else{
				document.getElementById("errorPriCancelReason").innerHTML = "";
				document.getElementById("confirmButton").disabled=false;
				return true;
			}
		}
	}
}
function checkReturnReasons(){
	//var p = document.getElementById("primaryReturnReason");
	//var strPrimaryReason = p.options[p.selectedIndex].value;
	var flagPri, flagSec, flag;
	var strPrimaryReason = document.getElementById("primaryReturnReason").value;
	//var s = document.getElementById("secondaryReturnReason");
	//var strSecondaryReason = s.options[s.selectedIndex].value;
	var strSecondaryReason =document.getElementById("secondaryReturnReason").value;
	var refundAmt = $("#refundAmount").val();
	if($('#errorRefundAmt').html() != "" || refundAmt == "£"){
		document.getElementById("confirmReturnButton").disabled=true;
		return false;
	}
	if(strPrimaryReason != "" && strPrimaryReason != "defaultPri"){
		$("#errorPriReason").html("");
		flagPri = true;
	} else {
		$("#errorPriReason").html("You must select primary reason for return / refund of orders!!!");
		flagPri = false;
	}
	
	if(strSecondaryReason != "" && strSecondaryReason != "defaultSec"){
		$("#errorSecReason").html("");
		flagSec = true;
	} else {
		$("#errorSecReason").html("You must select secondary reason for return / refund of orders!!!");
		flagSec = false;
	}
	
	if(strPrimaryReason != "" && strSecondaryReason != ""){
		if(strPrimaryReason !== "defaultPri" && strSecondaryReason !== "defaultSec"){
			flag = true;
		}
	}
	if(flagPri == true && flagSec == true){
		$("#errorPriReason").html("");
		$("#errorSecReason").html("");
		document.getElementById("confirmReturnButton").disabled=false;
		flag = true;
	} else {
		document.getElementById("confirmReturnButton").disabled=true;
		flag = false;
	}
	return flag;
}
/*R5*/
var createLblAjax = false,
	cnt = 1,
	cntTrakingid= 0;
var  responseString = null,
	 assignTotalQty = 0;
/*R5*/
$(document).ready(function(){	
		window.responseString = "";
		OrderdDetailsClickColect();
		$('#createLblDispatch').on('click',function(){
			console.log('generate label');
			var ajxYodelUrl = '<%=getItemAndParcelInfo%>';
			var orderID = $("#orderId").val();
			
			var totalCheck = "${fn:length(orderDetailsMap)}";
			var totalChecked = null;
			if(totalCheck == 1){
				totalChecked = $('[id*="check"]').map(function() {return this.value;}).get();
			} else {
				totalChecked = $('[id*="check"]:checked').map(function () {return this.value;}).get().join(",");
			}
						
			if (ajxYodelUrl && orderID && orderID != "") {	
				ajxYodelUrl+='&orderId='+orderID+'&lineItem='+totalChecked;
				$.ajax({
						type : "POST",
						url : ajxYodelUrl,
						dataType : 'text'
						}).done(function(msg) {
							console.log("success action");
							var msg2 = JSON.parse(msg);
							var count = 0;
							$("#ccPrintLabel").hide();
							$("#viewItemsLabel").hide();
							$("#ccAssignLabel").show();
							var cnt=0;
							var dynamicTitle = '<ol class="ccbreadcrumb"> <li class="active">PRINT LABELS</li><li><strong> &gt; </strong></li><li><strong>ASSIGN PACKAGES</strong></li></ol>';
							$('.ccbreadcrumb').html(dynamicTitle);							
							window.responseString = JSON.parse(msg);
							//console.log("Next button response",window.responseString);
							$("#assign-package-container").html("");
					        $.each(msg2, function(arrayID,group) {
					        	cnt=0;
					             $('<div class="assign-qty"><h3><span class="glyphicon glyphicon-remove-circle assign-package-remove" id="removelbl-'+group.trackingId+'"></span>Parcel '+(arrayID+1)+' ('+group.trackingId+'):</h3></div>').appendTo("#assign-package-container");
					            var row2 = $('<table class="table" style="margin-top: 20px!important;"><thead><tr><td class="col-xs-4">SKU</td><td class="col-xs-6">Description</td><td class="col-xs-4">Quantity</td></tr></thead><tbody><tr></tr></tbody></table>');
					            $.each(group.itemInfoLst, function(eventID,eventData) {
					             	count++;
					             	cnt++;
					             	var skid = (eventData.skuId == "") ? "-" : eventData.skuId;					             						             	
					            	var row = $("<tr />");
					                row.append('<td>'+skid+'<span id="sukid'+count+'" style="display: none;">sukid'+cnt+'</span><span id="quantity'+count+'" style="display: none;">'+eventData.quantity+'</span></td>');
					                row.append('<td>'+eventData.description+'</td>');
					                var selName = "lineNumber"+count;
					                var combo = $("<select />",{id:selName});
					                for(var kk=eventData.quantity; kk>=0; --kk){
					                    combo.append("<option>" + kk + "</option>");
					                };
					              
					                row.append($('<td/>').append(combo));					               
					                row2.find('tbody tr').last().after(row);
					                
					              $("#assign-package-container").find('div').last().append(row2);
					             });
					            var errorSpan="<span id='error"+group.trackingId+"' style='color:red;'></span>";
					             $("#assign-package-container").find('div').last().append(errorSpan);
					        });
					        cnt = msg2.length;
	                          if(msg2.length == 1) {
	                            $(".assign-package-remove").hide();
	                          }else {
		                            $(".assign-package-remove").show();
		                       }
							
						})
						.fail(function(jqXHR, textStatus) {
							console.log("failed action");
						})
						.always(function() {
							console.log("always action");
						});
				}
		});
		$('#ccAssignLabel').on('click','[id*="removelbl"]',function(){ 
			console.log("D-Remove clicked");
			var _that = $(this);
		    var selectedRecord = _that.attr('id');
		    selectedRecord = selectedRecord.substr(10);
			var orderID = document.getElementById("orderId").value;
			console.log(selectedRecord);    
			
			var totalCheck = "${fn:length(orderDetailsMap)}";
			var totalChecked = null;
			if(totalCheck == 1){
				totalChecked = $('[id*="check"]').map(function() {return this.value;}).get();
			} else {
				totalChecked = $('[id*="check"]:checked').map(function () {return this.value;}).get().join(",");
			}
			
			var ajxYodelDeleteUrl = '<%=deletelItemLabelUrl%>';
		    ajxYodelDeleteUrl += '&orderId='+orderID+'&trackingId='+selectedRecord+'&lineItem='+totalChecked;
		
			$.ajax({
				type : "POST",
				url : ajxYodelDeleteUrl,
				dataType : 'text'
			}).done(function(msg) {
				var msg = $.trim(msg);
				var msg2 = JSON.parse(msg);
				var count = 0;
				var cnt=0;
		          if (msg2 == '' || msg.indexOf('##ERROR##') != -1) {	                            
		                console.log('MHUB failed to delete assign Label.' + msg2);
		          } else {
		              console.log('MHUB Passed to delete assign Label' + msg2);
		          }
		        window.responseString = JSON.parse(msg);
		        var dynamicTitle = '<ol class="ccbreadcrumb"> <li class="active">PRINT LABELS</li><li><strong> &gt; </strong></li><li><strong>ASSIGN PACKAGES</strong></li> </ol>';
				$('.ccbreadcrumb').html(dynamicTitle);
				$("#assign-package-container").html("");
		        $.each(msg2, function(arrayID,group) {
		        	cnt=0;
		             $('<div class="assign-qty"><h3><span class="glyphicon glyphicon-remove-circle assign-package-remove" id="removelbl-'+group.trackingId+'"></span>Parcel '+(arrayID+1)+' ('+group.trackingId+'):</h3></div>').appendTo("#assign-package-container");
		            var row2 = $('<table class="table" style="margin-top: 20px!important;"><thead><tr><td class="col-xs-4">SKU</td><td class="col-xs-6">Description</td><td class="col-xs-4">Quantity</td></tr></thead><tbody><tr></tr></tbody></table>');
		            $.each(group.itemInfoLst, function(eventID,eventData) {
		             	count++;
		             	cnt++;
		             	var skid = (eventData.skuId == "") ? "-" : eventData.skuId;					             						             	
		            	var row = $("<tr />");
		                row.append('<td>'+skid+'<span id="sukid'+count+'" style="display: none;">sukid'+cnt+'</span><span id="quantity'+count+'" style="display: none;">'+eventData.quantity+'</span></td>');
		                row.append('<td>'+eventData.description+'</td>');
		                var selName = "lineNumber"+count;
		                var combo = $("<select />",{id:selName});
		                for(var kk=eventData.quantity; kk>=0; --kk){
		                    combo.append("<option>" + kk + "</option>");
		                };
		                row.append($('<td/>').append(combo));					               
		                row2.find('tbody tr').last().after(row);
		                
		              $("#assign-package-container").find('div').last().append(row2);
		             });
		            var errorSpan="<span id='error"+group.trackingId+"' style='color:red;'></span>";
		             $("#assign-package-container").find('div').last().append(errorSpan);
		        });
		        
		        cnt = msg2.length;
                if(msg2.length == 1) {
                  $(".assign-package-remove").hide();
                }else {
                      $(".assign-package-remove").show();
                 }
		        
		         /* if(msg2[0].trackingId === "failure") {		          	
		        	  console.log("failure xx");
		      	  }
		          else if(msg2[0].trackingId === "success") {
		        	  console.log("success yy");
		        	  _that.parent().parent('.assign-qty').remove();
		          }
		          */
		          
		          
		         }).fail(function(jqXHR, textStatus) {
					if (console){
						console.log('Delete Request failed with status: ' +textStatus);
					}
					
				}).always(function() {
					//$('.ajax-loader').hide();
				});
		});
		
		function addToObjArr(tempArr,obj){
			var count=0;
			var isFound=false;		
				while(count<tempArr.length){
					if(tempArr[count].skid==obj.skid){				
						var temObj=tempArr[count];
						temObj.qnt=+temObj.qnt + +obj.qnt;
						if(temObj.tid!=obj.tid){
							temObj.tranList[temObj.tranList.length]=obj.tid;
						}
						tempArr[count]=temObj;
						isFound=true;
					}
					count++;
				}
				if(isFound==false){
					tempArr[count]=obj;
				}
				return tempArr;
		}
		function validateDispatchPackage(tempobject){
			var cnt=0;
			var isValidate=true;
			var iszeroqnt=false;
			var qnt=0;
			var tempArr=new Array();
			var obj;
			var k=0;
			$.each(tempobject, function(arrayID1,group1) {
				qnt=0;
	            $.each(group1.itemInfoLst, function(arrayID12,group12) {   
	            	cnt++;
	                var jj = "#lineNumber"+cnt;                
	                group12.quantity = $(jj).find(":selected").text();
	                group12.description=$("#quantity"+cnt).text();
	               qnt=+qnt + +group12.quantity;
	               obj= new Object();
	               obj.tid=group1.trackingId;
	               obj.qnt=group12.quantity;
	               obj.totQnt=group12.description;
	               obj.skid=group12.skuId==""?$("#sukid"+cnt).text():group12.skuId;
	               obj.tranList=new Array();
	               obj.tranList[0]=group1.trackingId;
	               tempArr=addToObjArr(tempArr,obj);
	            });  
	            if(qnt==0){
	            	iszeroqnt=true;
	            	isValidate=false;
	            	fillErrorCode("error"+group1.trackingId,"Please select the quantity  you wish to dispatch.");
	            }else{
	            	clearElm("error"+group1.trackingId);
	            }       
	        });
			
			while(k<tempArr.length){
				var t=tempArr[k];
				if(t.totQnt<t.qnt){
					//for(var i=0;i<t.tranList.length;i++){
						var len=+t.tranList.length- +1;
						isValidate=false;
						fillErrorCode("error"+t.tranList[len],"Your assigned quantities exceed the total open quantity.");
					//}
					
				}
				if(t.qnt==0){
					for(var j=0;j<t.tranList.length;j++){
						isValidate=false;
						fillErrorCode("error"+t.tranList[j],"Please select the quantity  you wish to dispatch for SKU '"+t.skid+"'.");	
					}
				}
				k++;
				
			}
			return isValidate;		
		}
		
		function clearElm(id){
			$("#"+id).html("");
		}
		function fillErrorCode(id,msg){
			clearElm(id);
			$("#"+id).html(msg);		
		}
			
		
		$('#dispatchPackage').on('click',function(){
			console.log('generate label');			
			var ajxYodelUrl = '<%=postCandCDispatch%>';
			var orderID = $("#orderId").val();
			
			
			if (ajxYodelUrl && orderID && orderID != "") {	
				ajxYodelUrl+='&orderId='+orderID;
				console.log("test2",window.responseString);
				
				var newobject = window.responseString;
				var tempobject=newobject;
				var count =0;
				$.each(newobject, function(arrayID,group) {
		            $.each(group.itemInfoLst, function(arrayID2,group2) {
		                count++;
		                var jj = "#lineNumber"+count;                
		                group2.quantity = $(jj).find(":selected").text();
		            });      
		        });
				if(validateDispatchPackage(tempobject)==false){
					return;
				}
				newobject = JSON.stringify(newobject);
				//alert("window.responseString  : "+window.responseString);
				console.log("New Obj  : "+newobject);
				ajxYodelUrl+='&updatedparcItmInf='+newobject;
				//alert("url : "+ajxYodelUrl);
				$('#frmDialog').attr('action', ajxYodelUrl);
				$("#frmDialog").submit(); 
				
			}
		});
		
	/*R5*/
	
	/*Def#1028*/
	$(document).mouseup( function(e){
		closeDropdown(e);
	});	
	/* Def#880 */
	$('input[id^=updatedQuantity][readonly]').on('focus click',function(){$(this).blur();});
	
	$('#print').click(function(){
		openPopup();
	});
	
	$('#cancelDispatch_btn').click(function(){
		hide_updateBoxes();
	});
	
	$('#confirmDispatch_btn').click(function(){
		return confirmDispatch();
	});
	
	$('#cancelC_btn').click(function(){
		hide_updateBoxes();
	});
	
	$('#confirmButton').click(function(){
		return confirmCancel();
	});
	
	$('#cancelReturn_btn').click(function(){
		hide_updateBoxes();
	});
	
	$('#confirmReturnButton').click(function(){
		return confirmReturn();
	});
	
	$('.primaryCancel').click(function(){
		 $('input[name="primaryCancelReason"]').attr('value',$(this).attr('value'));
		 checkReasons();
	});
	
	$('.primaryReturn').click(function(){
		$('input[name="primaryReturnReason"]').attr('value',$(this).attr('value'));
		 checkReturnReasons();
	});
	
	$('.secondaryReturn').click(function(){
		$('input[name="secondaryReturnReason"]').attr('value',$(this).attr('value'));
		 checkReturnReasons();
	});
	
	$('#selectSecondary').children('span.selected').html('Select Box');
	$('#selectSecondary').attr('value','defaultSec');
	
	$('#selectPrimary').children('span.selected').html('Select Box');
	$('#selectPrimary').attr('value','defaultPri');
	
	$('#selectPrimaryCancel').children('span.selected').html('Select Box');
	$('#selectPrimaryCancel').attr('value','defaultPri');
	
	
	var totalCheck = $('input[type=checkbox][id*=check-]').length;
	var checkboxObj = $('input[type=checkbox][id*=check-]');
	
	var checkboxID = $(checkboxObj).attr("id");
	if("${fn:length(orderDetailsMap)}" == 1){
		checkboxID = $(checkboxObj[0]).attr("id");
		checkboxID = $.trim(checkboxID);
	}
	var lineNo = checkboxID.substring(6,8);
	var openQty = $("#openQty-"+lineNo).val();
	var dispatchQty = $("#dispatchedQty-"+lineNo).val();
	var acknowldgQty = $("#acknowledgedQty-"+lineNo).val();
	if ("${fn:length(orderDetailsMap)}" == 1){ 
		var enableDispatch = true;
		var enableCancel = true;
		var enableReturn = true;
		var enableFlag = true;
		
		if(openQty > 0){
			enableFlag = false;
		}else if(acknowldgQty > 0){
			enableFlag = false;
		}
		
		if(enableFlag) {
			enableDispatch = false;
			enableCancel = false;
		} 
		if(dispatchQty <= 0){
			enableReturn = false;
		} 
	
		if(enableDispatch){
			$("#dispatch_btn").prop('disabled', false);
		}else{
			$("#dispatch_btn").prop('disabled', true);
		}
		if(enableCancel){
			$("#cancel_btn").prop('disabled', false);
		}else{
			$("#cancel_btn").prop('disabled', true);
		}
		if(enableReturn){
			$("#btnCreateReturnRefund").prop('disabled', false);
		}else{
			$("#btnCreateReturnRefund").prop('disabled', true);
		}
	}
	disableDivs();
	
	$(".unchecked").on("click", function(){
		var remainUnchecked = $("input[type='checkbox'].unchecked");
		var remaining = $("input[type='checkbox'].unchecked").length;
		var totalChecked = $("input[type='checkbox']:checked").length;
		if(remaining == totalChecked){
			if (!($("#selectAll").is(':checked'))) {				
				$("#selectAll").prop('checked', true);
				$(remainUnchecked).prop('checked', true);
			}else{
			$("#selectAll").prop('checked', false);
			}
		}
	});
	
	$("#selectAll").click(function() {
		var checkboxes = $("input[type='checkbox']");
		if ($(this).is(':checked')){
			$("input:checkbox[name='lineItem'].unchecked").each(function(){
				var lineNo = $(this).val();
				
				var status = $("#status-"+lineNo).val(); 
				if(status != "Pending"){
					$("#check-"+lineNo).prop('checked', true);
					CheckStatuses("check-"+lineNo);
					if($(".dispatch_section").css("display")=="block" || $(".cancelSec").css("display")=="block"){
						$(".qty_section").css('display', 'block');
						$("#updatedQuantity-"+lineNo).css('display', 'block');
						$("#updatedQuantity-"+lineNo).prop('disabled', false).prop('readonly', false);
						$("#updatedQuantity-"+lineNo).off('focus click'); //Def#880
						$("#cancel_btn, #dispatch_btn, #btnCreateReturnRefund").prop('disabled', false);
					}
				} 
			});
		} else {
			checkboxes.prop('checked', false);
			$("input:checkbox:not(:checked)").each(function()	{
				var uncheckedLineNo = $(this).val();
				$(".dispatch_section").css("display","none");
				$(".cancelSec").css("display","none");
				$(".qty_section").css('display', 'none');
				$(".return_section").css('display', 'none');
				$("#updatedQuantity-"+uncheckedLineNo).css('display', 'none');
				$("#updatedQuantity-"+uncheckedLineNo).prop('readonly', true);
				$('#updatedQuantity-'+uncheckedLineNo).on('focus click',function(){$(this).blur();}); //Def#880
				$("#btnCreateReturnRefund, #cancel_btn, #dispatch_btn").prop('disabled', true);
				$("#err-"+uncheckedLineNo).html("");
			});
		};
	});
	
});

/*Added by hasan on 22-02-2015*/

function viewLabel(){
	$("#viewLblPrintAll").hide();
	var ajxYodelUrl = '<%=viewItemLabelUrl%>';
	var orderID = document.getElementById("orderId").value;
	var _viewItems = $('#viewItemsLabel'),
	_parcelItems = $('#parcelItems'),
    _actionSection = $('#actionSection'),
    _dateSchedule = $('#dateSchedule'),
    _ccAssignLabel = $("#ccAssignLabel"); 
	_viewItems.hide();
    _parcelItems.hide();
    _actionSection.hide();
    _dateSchedule.hide();
    _ccAssignLabel.hide();
	cnt = $('.remove-tracking-label').length;
	ajxYodelUrl += "&orderId="+orderID;
	$('.ajax-loader').show();
	$.ajax({
			type : "POST",
			url : ajxYodelUrl,
			dataType : 'text'
		}).done(function(msg) {				
			$("#dialog-ccdispatch").removeClass("hide");
			$("#ccPrintLabel").hide();
			$("#ccAssignLabel").hide();
			$("#viewItemsLabel").show();
			
			var msg = $.trim(msg);
			var msg2 = JSON.parse(msg);
			if(msg2[0].trackingId !== undefined){
				_viewItems.show();
				_parcelItems.hide();
                _actionSection.hide();
                _dateSchedule.hide();
                _viewItems.find('tbody tr').remove();
                _viewItems.find('tbody').append("<tr/>");	
                var cnt = 1;
                $.each(msg2, function(arrayID,group) {
				var cDate =  group.collectionDate;
                    cDate = cDate.substring(0,12);                  
                    var ndate = new Date(cDate);
                    var dat = ndate.getDate();
                    if(dat <=9){dat ="0"+dat;}
                    dateString =dat+ "/"+ ("0"+ (ndate.getMonth() + 1)).slice(-2) + "/" + ndate.getFullYear().toString().substr(2,2);
                    var row = $("<tr />");
                    row.append($('<td class="remove-tracking-label"><span class="glyphicon glyphicon-remove-circle" id="remove-'+group.trackingId+'"></span></td>'));
                    row.append($('<td class="label-no">' + cnt + '</td>'));
                    row.append($('<td>' + group.trackingId + '</td>'));
                    row.append($('<td>' + dateString + '</td>'));
                    row.append($('<td><a type="button" class="btn primary-CTA" id="printBtn-'+group.trackingId+'">Print</a></td>'));
                    $('#viewItemsLabel').find('tbody tr').last().after(row);
                    $("#viewLblPrintAll").show();
					$(".remove-tracking-label span").hide();
					cnt++;
                });               
                console.log("message length in view Label : "+msg2.length);
                cnt = msg2.length;               
                if(msg2.length == 0) {
                	 $("#viewLblPrintAll").parent('.col-xs-14.right-align-btn').hide();
                	 $(".remove-tracking-label span").hide();
                	 $(".print-info").css('display','none');
                	 $("#viewItemsLabelScroll").text('Sorry, we are unable to display the labels at the moment.');
                	 $("#viewItemsLabelScroll").css('padding-left','10px');
                	 $("#viewItemsLabelScroll").css('color','red');
                }
                if(msg2.length == 1) {
                  $(".remove-tracking-label span").hide();
                  $("#viewLblPrintAll").hide();
                }else {
                      $(".remove-tracking-label span").hide();
                      $("#viewLblPrintAll").show();
                      if(msg2.length>3){
                    	  $("#viewLblPrintAll").css('margin-right','14px');
                      }
                 }
			}else{
				 $("#viewLblPrintAll").parent('.col-xs-14.right-align-btn').hide();
            	 $(".remove-tracking-label span").hide();
            	 $(".print-info").css('display','none');
            	 $("#viewItemsLabelScroll").text('Sorry, we are unable to display the labels at the moment.');
            	 $("#viewItemsLabelScroll").css('padding-left','10px');
            	 $("#viewItemsLabelScroll").css('color','red');
            	 console.log("Tracking Id and collection date should not be blank!!!");
			}
			 $("#dialog-ccdispatch").dialog({
                 open: function() {
                     $(this)
                       .parent()
                       .find(".ui-dialog-title").html("");
                     $('.ui-dialog-titlebar-close')
                       .html('<span class="ui-button-icon-primary ui-icon ui-icon-closethick"></span>');
                 }
               }).dialog("open");
		}).fail(function(jqXHR, textStatus) {
			if (console){
				console.log('View Label Request failed with status: ' +textStatus);
			}				
		}).always(function() {
			$('.ajax-loader').hide();
		});	
	 $("#dialog-ccdispatch").dialog({
	        resizable: false,
	        modal: true,
	       // title: "Confirm Quick Dispatch",
	        width: 750,
         dialogClass: 'noCCTitle'
	    });	
	 $('#viewItemsLabel').on('click','[id*="printBtn"]',function(){ 
			console.log("printBtn1111");
			var popupUrl = '<%=printLabelUrl%>';
			var _that = $(this);
		    var selectedRecord = _that.attr('id');
		    selectedRecord = selectedRecord.substr(9);
			console.log(selectedRecord);
			var orderID = document.getElementById("orderId").value;
			var isUsed = true;
			var viewLabel = true;
			popupUrl += '&orderId='+orderID+'&trackingId='+selectedRecord+'&isUsed='+isUsed+'&viewLabel='+viewLabel;
			newwindow = window.open(popupUrl,'name','height=650,width=735,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
			//setTimeout(function(){ newwindow.print(); }, 3000);
			newwindow.print();
		});			
		$('#viewLblPrintAll').on('click',function(){ 
			console.log("printBtn");
			var popupUrl = '<%=printAllLabelUrl%>';
			var _that = $(this);
			var isUsed=true;
			var viewLabel = true;
			var orderID = document.getElementById("orderId").value;
			popupUrl += '&orderId='+orderID+'&isUsed='+isUsed+'&viewLabel='+viewLabel;
			newwindow = window.open(popupUrl,'name','height=650,width=735,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
			//setTimeout(function(){ newwindow.print(); }, 3000);
			//newwindow = window.open(finalPopupURL,'name','height=650,width=735,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
			newwindow.print();
		});		
		
}

/*R5*/
function OrderdDetailsClickColect() {
	
	$("#createLblPrintAll").hide();
	$('.ajax-loader').hide();
	$("#dialog-ccdispatch").dialog({autoOpen: false});
	
	$("#addParcel").on('click', function(e){
		e.preventDefault();			
		var ajxYodelUrl = '<%=addYodlLabelUrl%>';
		$("#ccPrintLabel").find('.error-check').empty().hide();
		var orderID = document.getElementById("orderId").value;
		cnt = $('.remove-tracking-label').length;
		ajxYodelUrl += "&orderId=" + orderID + "&collectionDate=" + " ";
		$('.ajax-loader').show();
		$.ajax({
				type : "POST",
				url : ajxYodelUrl,
				dataType : 'text'
			}).done(function(msg) {
				$("#ccPrintLabel").find('.error-check').hide().empty();
				var msg = $.trim(msg);
				if(msg.length!=0){
					$('.ajax-loader').show();
				}
				var msg2 = JSON.parse(msg);
				if (console){
					console.log('Add Parcel Request passed with status: ' +msg2);
				}
				if(msg2.isAuthFail !== undefined) {
              	  console.log(msg2.errMsg);
              		$("#ccPrintLabel").find('.error-check').show().text(msg2.errMsg);
                }
                else if(msg2.isResNull !== undefined) {
              	  console.log(msg2.errMsg);
              		$("#ccPrintLabel").find('.error-check').show().text(msg2.errMsg);
                }
                else if(msg2.isCalendar) {
                  	console.log("calendar for add new trans333"+msg2.isFirst);
                  	console.log("calendar for singline trans by vk91 333"+msg2.isDate);
                  	var now = ms2.isDate;
                  	$("#dateSchedule").show();
                	$("#actionSection").hide();
                  	var firstmsg ="We are unable to schedule a pickup for  "+now+". Please pick another day from the calendar. Please also note that, once generated labels will only be available to print for 48 hours.",
                  		secondmsg = "We are unable to schedule on this date. Please pick another day from the calendar.";
						if(msg2.isFirst == true) {
							$('.calendar-error-info').text(firstmsg);
						}else {
						 	$('.calendar-error-info').text(secondmsg);
						}
              	    $("#lblDatePicker").datepicker(
							{onSelect: function(dateText) {
				            	console.log("dateText is : "+dateText);
				            	$("#dateSelected").val(dateText);
				        	},
				        	minDate:0,
				        	 dateFormat: 'yy-mm-dd'
				        	});	                        	   
                } else {
                	cnt++;
    				var cDate = msg2[0].collectionDate;
                        cDate = cDate.substring(0,12);
    	                var ndate = new Date(cDate);
    	                var dat = ndate.getDate();
    	                if(dat <=9){dat ="0"+dat;}
    	                dateString = dat + "/"+ ("0"+ (ndate.getMonth() + 1)).slice(-2) + "/" + ndate.getFullYear().toString().substr(2,2);
                        var row = $("<tr />");
                        row.append($('<td class="remove-tracking-label"><span class="glyphicon glyphicon-remove-circle" id="remove-'+msg2[0].trackingId+'"></span></td>'));
                        row.append($('<td class="label-no">' + cnt + '</td>'));
                        row.append($('<td>' + msg2[0].trackingId + '</td>'));
    		   			row.append($('<td>' + dateString + '</td>'));
                        row.append($('<td><a type="button" class="btn primary-CTA" id="printBtn-'+msg2[0].trackingId+'">Print</a></td>'));
                        $('#parcelItems').find('tbody tr').last().after(row);
    					$(".remove-tracking-label span").show();
    					  var zlength = $('#parcelItems').find('tbody tr').size(); // Hide Remove & Print All in case of single parcel
			        	  console.log("success zz"+zlength);
    					  $("#createLblPrintAll").show();
                          if(zlength <=4) {
                        	  $("#createLblPrintAll").parent('.right-align-btn').css('left','0');
    			          }else{
    			        	  $("#createLblPrintAll").parent('.right-align-btn').css('left','-15px');
  		        	  	}
                }
			}).fail(function(jqXHR, textStatus) {
				if (console){
					console.log('Add Parcel Request failed with status: ' +textStatus);
				}				
			}).always(function() {
				$('.ajax-loader').hide();
			});		
	});
	$("#resetCalendar").on('click',function(){
		console.log("calendar reset click");
		var closeDialog = $("#parcelItems").find('table').is(":visible");		
		if(closeDialog) {
			$("#dateSchedule").hide();
			$("#actionSection").show();			
		} else {
			$("#dialog-ccdispatch").dialog("close");			
		}		
	});
	
	$("#calGenerate").on('click',function(){					
		var ajxYodelUrl = '<%=addYodlLabelUrl%>';
		var orderID = document.getElementById("orderId").value;
		var calendarDate = $("#dateSelected").val();
		cnt = $('.remove-tracking-label').length;
		ajxYodelUrl += "&orderId="+orderID+"&collectionDate="+calendarDate;
		$('.ajax-loader').show();
		$.ajax({
				type : "POST",
				url : ajxYodelUrl,
				dataType : 'text'
			}).done(function(msg) {				
				var msg = $.trim(msg);
				var msg2 = JSON.parse(msg);
				if (console){
					console.log('calendar Generated: ' +msg2);
				}				
				if(msg2.isCalendar) {
					console.log("if calendar first val is : "+msg2.isFirst+"msg2.isCalendar is : "+msg2.isCalendar);
					var firstmsg ="We are unable to schedule a pickup for"+msg2.isDate+". Please pick another day from the calendar. Please also note that, once generated labels will only be available to print for 48 hours.",
              		secondmsg = "We are unable to schedule on this date. Please pick another day from the calendar.";
					if(msg2.isFirst == true) {
						$('.calendar-error-info').text(firstmsg);
					}else {
					 	$('.calendar-error-info').text(secondmsg);
					}
				} else {
					console.log("if not calendar"+msg2);
					cnt++;
					var cDate = msg2[0].collectionDate;
	                    cDate = cDate.substring(0,12);
	                    var ndate = new Date(msg2[0].collectionDate);
	                    var dat = ndate.getDate();
	                    if(dat <=9){dat ="0"+dat;}
	                    dateString =dat+ "/"+ ("0"+ (ndate.getMonth() + 1)).slice(-2) + "/" + ndate.getFullYear().toString().substr(2,2);                    
	                    var row = $("<tr />");
	                    row.append($('<td class="remove-tracking-label"><span class="glyphicon glyphicon-remove-circle" id="remove-'+msg2[0].trackingId+'"></span></td>'));
	                    row.append($('<td class="label-no">' + cnt + '</td>'));
	                    row.append($('<td>' + msg2[0].trackingId + '</td>'));
	                    row.append($('<td>' + dateString + '</td>'));
	                    row.append($('<td><a type="button" class="btn primary-CTA" id="printBtn-'+msg2[0].trackingId+'">Print</a></td>'));
	                    $('#parcelItems').find('tbody tr').last().after(row);
	                    $("#createLblPrintAll").show();
						$(".remove-tracking-label span").show();
				}

			}).fail(function(jqXHR, textStatus) {
				if (console){
					console.log('Add Parcel Request failed with status: ' +textStatus);
				}				
			}).always(function() {
				$('.ajax-loader').hide();
			});	
	});
	
	//createLblReset
	$("#createLblReset").on('click', function(){
		//e.preventDefault();
		//createYodlLabelUrl	resetlLabel		
		var ajxYodelUrl = '<%=resetlLabel%>';
		$("#ccPrintLabel").find('.error-check').empty().hide();
			var orderID = document.getElementById("orderId").value;
			cnt = $('.remove-tracking-label').length;
			ajxYodelUrl += "&orderId=" + orderID+ "&collectionDate=" + " ";
			$('.ajax-loader').show();
			$.ajax({
						type : "POST",
						url : ajxYodelUrl,
						dataType : 'text'
					}).done(
							function(msg) {
								$('#parcelItems').show();
								$("#ccPrintLabel").find('.error-check').hide().empty();
								var msg = $.trim(msg);
								var msg2 = JSON.parse(msg);								
								if (console) {
									console.log('Reset request passed with status: '+ msg2);
								}
								if(msg2.isAuthFail !== undefined) {
					              	  console.log(msg2.errMsg);
						              $('#parcelItems').find('tbody tr').remove();
									  $('#parcelItems').hide();
					              	  $("#ccPrintLabel").find('.error-check').show().text(msg2.errMsg);
					                }
					                else if(msg2.isResNull !== undefined) {
					                	console.log(msg2.errMsg);
						                $('#parcelItems').find('tbody tr').remove();
										$('#parcelItems').hide();
						              	$("#ccPrintLabel").find('.error-check').show().text(msg2.errMsg);
					                }
					                else if(msg2.isCalendar !== undefined) {
					                  	console.log("calendar for add new trans444");
					                  	console.log("calendar for singline trans by vk91 444");
					                  	var now = ms2.isDate;
					                  	$("#dateSchedule").show();
					                	$("#actionSection").hide();
					                  	var firstmsg ="We are unable to schedule a pickup for  "+now+". Please pick another day from the calendar. Please also note that, once generated labels will only be available to print for 48 hours.",
					                  		secondmsg = "We are unable to schedule on this date. Please pick another day from the calendar.";
											if(msg2.isFirst == true) {
												$('.calendar-error-info').text(firstmsg);
											}else {
											 	$('.calendar-error-info').text(secondmsg);
											}
					              	    $("#lblDatePicker").datepicker(
												{onSelect: function(dateText) {
									            	console.log(dateText);
									            	$("#dateSelected").val(dateText);
									        	},
									        	minDate:0,
									        	 dateFormat: 'yy-mm-dd'
									        	});	                        	  
					                } else {
										cnt = 1;
										$('#parcelItems').find('tbody tr').remove();
										$('#parcelItems').find('tbody').append("<tr/>");
										var ndate = new Date(msg2[0].collectionDate);
										var dat = ndate.getDate();
					                    if(dat <=9){dat ="0"+dat;}
				                        dateString =dat + "/"+ ("0"+ (ndate.getMonth() + 1)).slice(-2) + "/" + ndate.getFullYear().toString().substr(2,2);
										//cDate = cDate.substring(0, 12);
										var row = $("<tr />");
										row.append($('<td class="remove-tracking-label"><span class="glyphicon glyphicon-remove-circle" id="remove-'+msg2[0].trackingId+'"></span></td>'));
										row.append($('<td class="label-no">'+cnt+ '</td>'));
										row.append($('<td>'+msg2[0].trackingId+ '</td>'));
										row.append($('<td>'+dateString+ '</td>'));
										row.append($('<td><a type="button" class="btn primary-CTA" id="printBtn-'+msg2[0].trackingId+'">Print</a></td>'));
										$('#parcelItems').find('tbody tr').last().after(row);
										$("#createLblPrintAll").hide();
										$(".remove-tracking-label span").hide();
					                }
								
							}).fail(
							function(jqXHR, textStatus) {
								if (console) {
									console.log('Rest request failed with status: '+ textStatus);
								}
							}).always(function() {
								$('.ajax-loader').hide();
							});
		});

			$('#parcelItems').on('click','[id*="remove"]',function(){ 
				console.log("Remove clicked");
				var _that = $(this);
			    var selectedRecord = _that.attr('id');
			    selectedRecord = selectedRecord.substr(7);
				var orderID = document.getElementById("orderId").value;
				console.log(selectedRecord);    
				var ajxYodelDeleteUrl = '<%=deletelLabelUrl%>';
			    ajxYodelDeleteUrl += '&orderId='+orderID+'&trackingId='+selectedRecord;
		
				$.ajax({
					type : "POST",
					url : ajxYodelDeleteUrl,
					dataType : 'text'
				}).done(function(msg) {
					var msg = $.trim(msg);					
			        var msg2 = JSON.parse(msg);
			          if (msg == '' || msg.indexOf('##ERROR##') != -1) {	                            
			                console.log('MHUB failed to delete Label.' + msg);
			          } else {
			              console.log('MHUB Passed to delete Label' + msg);
			          }
			          
			          if(msg2[0].trackingId === "failure") {
			          	//_that.parent('tr').remove();
			        	  console.log("failure xx");
			        	  // $("#ccPrintLabel").find('.error-check').show().text(msg2[0].errMsg);
			      	  }
			          else if(msg2[0].trackingId === "success") {
			        	  console.log("success yy");
			        	  _that.parent().parent('tr').remove();			        	  
			        	  var slength = $('#parcelItems').find('tbody tr').size(); // Hide Remove & Print All in case of single parcel
			        	  console.log("success yy"+slength);
			        	  if(slength == 2) {
			        		  $("#createLblPrintAll").hide();
			        		  $(".remove-tracking-label span").hide();
			        	  }
			        	  if(slength <=4) {
		  			           $("#createLblPrintAll").parent('.right-align-btn').css('left','0');
	  			          }else {
			        		   $("#createLblPrintAll").parent('.right-align-btn').css('left','-15px');
			        	  	}
			          }
			          
			         }).fail(function(jqXHR, textStatus) {
						if (console){
							console.log('Delete Request failed with status: ' +textStatus);
						}
						
					}).always(function() {
						//$('.ajax-loader').hide();
					});
			});
			$('#parcelItems').on('click','[id*="printBtn"]',function(){ 
				console.log("printBtn");
				var popupUrl = '<%=printLabelUrl%>';
				var _that = $(this);
			    var selectedRecord = _that.attr('id');
			    selectedRecord = selectedRecord.substr(9);
				console.log(selectedRecord);
				var orderID = document.getElementById("orderId").value;
				var isUsed = false;
				var viewLabel=false;
				popupUrl += '&orderId='+orderID+'&trackingId='+selectedRecord+'&isUsed='+isUsed+'&viewLabel='+viewLabel;
				//popupUrl += '&orderId='+orderID+'&trackingId='+selectedRecord;
				newwindow = window.open(popupUrl,'name','height=650,width=735,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
				//setTimeout(function(){ newwindow.print(); }, 3000);
				newwindow.print();
			});			
			$('#createLblPrintAll').on('click',function(){ 
				console.log("printBtn");
				var popupUrl = '<%=printAllLabelUrl%>';
							var _that = $(this);
							var isUsed = false;
							var viewLabel = false;
							var orderID = document.getElementById("orderId").value;
							popupUrl += '&orderId=' + orderID + '&isUsed='
									+ isUsed + '&viewLabel=' + viewLabel;
							newwindow = window
									.open(
											popupUrl,
											'name',
											'height=650,width=735,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
							//setTimeout(function(){ newwindow.print(); }, 3000);
							//newwindow = window.open(finalPopupURL,'name','height=650,width=735,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
							newwindow.print();
						});
		$('#cancelPackage').on('click', function() {
			$("#dialog-ccdispatch").dialog("close");
			$("#ccAssignLabel").hide();
			$("#viewItemsLabel").hide();
			$("#ccPrintLabel").show();
		});

		$("#dialog-ccdispatch").dialog({
			beforeClose : function(event, ui) {
				$("#ccPrintLabel").show();
			}
		});
	}
	/*R5*/

	function cancel() {
		return false;
	};

	function doBlur(data) {
		if (typeof data != 'undefined' && typeof data == 'object') {
			try {
				$(data.obj).blur();
			} catch (e) {
				if (console)
					console.log('UI Exception: ' + e.message);
			}
		}
	}
</script>
<form method="post" name="frmDialog" id="frmDialog">
	<div id="dialog-ccdispatch" class="hide">
		<div class="cc-print-label col-xs-16" id="ccPrintLabel">
			<h3>Click &amp; Collect - Print Labels</h3>
			<p style="display: none" class="error-check">We are unable to
				generate any labels as your account details appear to be inaccurate
				. Please contact TAM.</p>
			<form method="post" id="ccfrm2" name="ccfrm2">
				<div id="parcelItems"
					style="max-height: 260px; overflow-x: hidden; overflow-y: auto">
					<p class="print-info">Should you need more parcels, please
						click 'Add Parcel'. Please print your label(s) and then click
						next.</p>
					<table class="table">
						<thead>
							<tr>
								<td class="col-xs-1">&nbsp;</td>
								<td class="col-xs-2 label-no">Parcel no.</td>
								<td class="col-xs-4">Tracking ID</td>
								<td class="col-xs-4">Collection Date</td>
								<td class="col-xs-4">&nbsp;</td>
							</tr>
						</thead>
						<tbody>
							<tr></tr>
						</tbody>
					</table>
				</div>
				<div class="col-xs-16" id="dateSchedule">
					<p class="calendar-error-info" style="color: red">We are unable
						to schedule on this date. Please pick another day from the
						calendar.</p>
					<div class="col-xs-8">
						<p class="pickup-date">Please select a pickup date:</p>
					</div>
					<div class="col-xs-8" id="">
						<input type="hidden" name="date" id="dateSelected" />
						<div id="lblDatePicker"></div>
					</div>
					<div class="col-xs-8">&nbsp;</div>
					<div class="col-xs-8 padding-top-30">
						<button type="button" id="resetCalendar" class="btn secondary-CTA">Cancel</button>
						<button type="button" id="calGenerate" class="btn primary-CTA">Generate</button>
					</div>
				</div>
				<div class="col-xs-16" id="actionSection">
					<div class="margin-bottom-20">
						<p class="add-parcel">
							<a href="#" id="addParcel" style="outline: none">Add Parcel</a>
						</p>
						<div class="ajax-loader type-2" style="margin-left: 60px;">
							<img
								src="<%=request.getContextPath()%>/images/ajax-loader-horizontal.gif">
						</div>
					</div>
					<div class="col-xs-8">
						<button type="button" id="createLblReset"
							class="btn secondary-CTA">Reset</button>
					</div>
					<div class="col-xs-8 right-align-btn">
						<button type="button" id="createLblPrintAll"
							class="btn primary-CTA">Print All</button>
						<button type="button" id="createLblDispatch"
							class="btn primary-CTA-alt">Next</button>
					</div>
				</div>
			</form>
		</div>
		<div class="cc-assign-label col-xs-16" id="ccAssignLabel">
			<h3>Click &amp; Collect - Assign Packages</h3>
			<form method="post" id="ccfrm3" name="ccfrm3">
				<div id="assign-package-container"
					style="max-height: 550px; overflow-x: hidden; overflow-y: auto"></div>
				<div class="col-xs-16" style="text-align: right;">
					<button type="button" id="cancelPackage" class="btn secondary-CTA">Cancel</button>
					<button type="button" id="dispatchPackage" class="btn primary-CTA">Dispatch</button>
				</div>
			</form>
		</div>
		<div id="viewItemsLabel">
			<h3 class="cc-assign-label col-xs-16">Click &amp; Collect - View
				Labels</h3>
			<form method="post" id="ccfrm4" name="ccfrm4">
				<p class="print-info" style="padding-left: 10px">The labels
					below are available for 48 hours after the dispatch notification is
					submitted.</p>
				<div id="viewItemsLabelScroll"
					style="max-height: 260px; overflow-y: auto">
					<table class="table">
						<thead>
							<tr style="font-size: 15px;">
								<td class="col-xs-1">&nbsp;</td>
								<td class="col-xs-2 label-no">Parcel no.</td>
								<td class="col-xs-4">Tracking ID</td>
								<td class="col-xs-4">Collection Date</td>
								<td class="col-xs-4">&nbsp;</td>
							</tr>
						</thead>
						<tbody style="font-size: 15px;">
							<tr></tr>
						</tbody>
					</table>
				</div>

				<div class="col-xs-14 right-align-btn"
					style="padding-right: 10px; padding-top: 20px;">
					<button type="button" id="viewLblPrintAll" class="btn primary-CTA">Print
						All</button>
				</div>
			</form>
		</div>
	</div>
</form>
<form method="post" name="frm1" id="frm1">
	<div id="dialog-confirm"></div>
	<div class="order-details-header">
		<input type="hidden" name="updateOrderAction" value="" />
		<c:set var="displayHeader" value="0" />
		<c:set var="orderDetailsSize" value="${fn:length(orderDetailsMap)}" />

		<div class="header-row relative">
			<h5>OPEN ORDERS</h5>
			<div class="row padding-left-10">
				<h1 class="header-alpha col-xs-4">Order Processing</h1>
			</div>
			<div class="previous-page">
				<c:set var="selectedTab" value="${selectedTab}" />
				<c:if test="${selectedTab == 'released'}">
					<h4 class="ml-10">
						<a href="${orderListingPage}" id="backToOpenOrders">All Open
							Orders</a>
					</h4>
				</c:if>
				<c:if test="${selectedTab == 'dispatched'}">
					<h4 class="ml-10">
						<a href="${orderListingPage}" id="backToDispatchedOrders">All
							Dispatched Orders</a>
					</h4>
				</c:if>
				<c:if test="${selectedTab == 'cancelled'}">
					<h4 class="ml-10">
						<a href="${orderListingPage}" id="backToCancelledOrders">All
							Cancelled Orders</a>
					</h4>
				</c:if>
				<c:if test="${selectedTab == 'returned'}">
					<h4 class="ml-10">
						<a href="${orderListingPage}" id="backToReturnedOrders">All
							Returned Orders</a>
					</h4>
				</c:if>
			</div>
		</div>
	</div>
	<div class="order-details-basic-information margin-bottom-20">
		<div class="row top-section margin-top-30 margin-right-0">
			<section
				class="col-xs-5 left-section right-border1px padding-right-0 padding-left-5">
				<h3 class="col-header">Shipping &amp; Delivery</h3>
				<div class="col-xs-16 padding-left-0">
					<label class="col-xs-7 padding-left-0">Delivery Option</label>
					<div class="col-xs-9 txt-value">
						<span class="padding-5 bg-red"><c:out
								value="${orderDetails.deliveryOption}" /></span> <input type="hidden"
							name="deliveryOption" id="deliveryOption"
							value="${orderDetails.deliveryOption}" />
					</div>
				</div>

				<div class="col-xs-16 padding-left-0">
					<label class="col-xs-7 padding-left-0">Delivery Cost</label>
					<div class="col-xs-9 txt-value">
					<c:choose>
								<c:when
									test="${not empty orderDetails.deliveryOption && orderDetails.deliveryOption != 'undefined'
									 && orderDetails.deliveryOption eq 'Click & Collect'}">
									N/A
									<input type="hidden" id="deliveryCost" name="deliveryCost" value="${orderDetails.deliveryCost}" />
								</c:when>
								<c:otherwise>
									<b>£</b>
						<fmt:formatNumber type="number" minFractionDigits="2"  value="${orderDetails.deliveryCost}" />
						<input type="hidden" id="deliveryCost" name="deliveryCost" value="${orderDetails.deliveryCost}" />
								</c:otherwise>
							</c:choose>
					</div>
				</div>
				<div class="col-xs-16 padding-left-0">
					<label class="col-xs-7 padding-left-0">Ship to</label>
					<div class="col-xs-9 txt-value">
						<c:out
							value="${orderDetails.shipToFName}
					  ${orderDetails.shipToLName}" />
						<br />
						<c:out value="${orderDetails.shipToAddress}" />
						<input type="hidden" name="shipingTo"
							value="${orderDetails.shipToAddress}" />
					</div>
				</div>
				<div class="col-xs-16 padding-left-0" style="margin-top: 5px;">
					<label class="col-xs-7 padding-left-0">Instructions</label>
					<div class="col-xs-9 txt-value">
						<c:out value="${orderDetails.deliveryInstructions}" />
						<br /> <input type="hidden" name="deliveryInstructions"
							value="${orderDetails.deliveryInstructions}" />
					</div>
				</div>
			</section>
			<section class="col-xs-11">
				<div class="col-xs-8 left-section padding-left-0">
					<h3 class="col-header pl-18">Order Details</h3>
					<div class="col-xs-16">
						<label class="col-xs-7">Order Status</label>
						<div class=" col-xs-9 txt-value">
							<c:choose>
								<c:when
									test="${not empty orderStatus && orderStatus != 'undefined' && orderStatus != ''}">
									<span class="padding-5 bg-red"><c:out
											value="${orderStatus}" /></span>
									<input type="hidden" id="ordStatus" name="ordStatus"
										value="${orderStatus}" />
								</c:when>
								<c:otherwise>
									<span class="padding-5 bg-red"><c:out
											value="${orderDetails.orderStatus}" /></span>
									<input type="hidden" id="ordStatus" name="ordStatus"
										value="${orderDetails.orderStatus}" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="col-xs-16">
						<label class="col-xs-7">Order Id</label>
						<div class="col-xs-9 txt-value">
							<c:out value="${orderDetails.orderId}" />
							<input type="hidden" name="orderId" id="orderId"
								value="${orderDetails.orderId}" />
						</div>
					</div>
					<div class="col-xs-16">
						<label class="col-xs-7">Order Placed</label>
						<div class="col-xs-9 txt-value">
							<c:out value="${orderDetails.orderPlacedDate}" />
							<input type="hidden" id="placedDate" name="placedDate"
								value="${orderDetails.orderPlacedDate}" />
						</div>
					</div>
					<div class="col-xs-16">
						<label class="col-xs-7">Order Value</label>
						<div class="col-xs-9 txt-value">
							<b>£</b>
							<fmt:formatNumber type="number" minFractionDigits="2"
								value="${orderDetails.orderValue}" />
							<input type="hidden" id="orderValue" name="orderValue"
								value="${orderDetails.orderValue}" />
						</div>
					</div>
				</div>
				<div class="col-xs-8 right-section">
					<h3 class="col-header pl-18">Customer Details</h3>

					<div class="col-xs-16">
						<label class="col-xs-5">Name</label>
						<div class="col-xs-11 txt-value">
							<c:out
								value="${orderDetails.customerFirstName} ${orderDetails.customerLastName}" />
							<input type="hidden" id="firstName" name="firstName"
								value="${orderDetails.customerFirstName}" />
						</div>
					</div>

					<div class="col-xs-16">
						<label class="col-xs-5">Email</label>
						<div class="col-xs-11 txt-value">
							<c:out value="${orderDetails.customerEmail}" />
							<input type="hidden" id="email" name="email"
								value="${orderDetails.customerEmail}" />
						</div>
					</div>
					<div class="col-xs-16">
						<label class="col-xs-5">Telephone</label>
						<div class="col-xs-11 txt-value">
							<c:out value="${orderDetails.customerMobile}" />
							<input type="hidden" id="mobileNumber" name="mobileNumber"
								value="${orderDetails.customerMobile}" />
						</div>
					</div>
				</div>
			</section>
			<div class="row margin-top-30 printBtnDiv">
				<div class="pull-right">
					<c:if
						test="${isOrderDetailsPrintBtnVisible || isMultiOrderDetailsPrintBtnVisible}">
						<input type="button" class="btn secondary-CTA" id="print"
							value="Print" />
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<div class="row table row-group-header orderlinesHeader">
		<h2 class="col-xs-2">Order Lines</h2>
		<div class="col-xs-14 hr"></div>
	</div>
	<div class="order-details-action-buttons">
		<div class="row btn-toolbar">
			<div class="btn-group">
				<c:if
					test="${isDispatchOdrLinesVisible || isQuickDispatchMultiOdrsVisible}">
					<button id="dispatch_btn" type="button" class="btn secondary-CTA"
						onclick="toggle_sections(event)" disabled>Dispatch</button>
				</c:if>
				<c:if test="${isCancelOdrLinesVisible}">
					<button id="cancel_btn" type="button" class="btn secondary-CTA"
						onclick="toggle_sections(event)" disabled>Cancel</button>
				</c:if>
				<c:if test="${isReturnOdrLinesVisible && selectedTab ne 'released'}">
					<button id="btnCreateReturnRefund" type="button"
						class="btn secondary-CTA" onclick="toggleButton();" disabled>Refund</button>
				</c:if>

				<c:if
					test="${not empty usedLabel && orderDetails.deliveryOption eq 'Click & Collect' && not empty orderDetails.shipmentTrackingDetails || orderDetails.shipmentTrackingDetails== ''}">
					<button id="btnViewLabels" type="button" class="btn secondary-CTA"
						onclick="viewLabel();">View Labels</button>
				</c:if>
			</div>
		</div>
	</div>
	<div class="order-details-line-items">
		<input type="hidden" name="buttonClicked" id="buttonClicked" value="" />
		<div class="table margin-top-40">
			<div class="thead row">
				<h4 class="div-width-1 item">
					<c:if test="${orderDetailsSize > 1}">
						<div class="check">
							<input type="checkbox" name="selectAll" id="selectAll"
								class="css-checkbox" value="" /> <label for="selectAll"
								class="css-label checkLabel"></label>
						</div>
					</c:if>
				</h4>
				<h4 class="div-width-2 item">LINE</h4>
				<h4 class="div-width-3 item">SKU</h4>
				<h4 class="div-width-4 item">DESCRIPTION</h4>
				<h4 class="div-width-5 item">STATUS</h4>
				<h4
					class='div-width-9 item<c:if test="${ empty orderDetails.shipmentTrackingDetails || orderDetails.shipmentTrackingDetails== ''}"> hidden</c:if> '>TRACKING
					ID</h4>
				<h4 class="div-width-6 item">ITEM PRICE</h4>
				<h4 class="div-width-7 item">ORDER QTY</h4>
				<h4 class="div-width-7 item">OPEN QTY</h4>
				<h4 class="div-width-8 item">REASON</h4>
				<h4 class="div-width-9 item">SECONDARY REASON</h4>
				<h4 class="div-width-10 item display-none qty_section">QTY?</h4>
			</div>
			<div class="table-body">
				<c:forEach items="${orderDetailsMap}" var="ordDetails">
					<div class="row table-row  margin-top-20 orderRows"
						id="div-${ordDetails.value.getOrderLineNumber()}">
						<div class="col-xs-16 item">
							<c:set var="statPending" value="Pending" />
							<div class="div-width-1 item">
								<c:if
									test="${orderDetailsSize > 1 && ordDetails.value.getLineItemStatus() != 'Pending' && orderStatus !='IN PROGRESS'}">
									<div class="check">
										<input type="checkbox" name="lineItem"
											id="check-${ordDetails.value.getOrderLineNumber()}"
											class="css-checkbox unchecked"
											value="${ordDetails.value.getOrderLineNumber()}"
											onclick="return enableQtyField(this);" /> <label
											for="check-${ordDetails.value.getOrderLineNumber()}"
											class="css-label checkLabel"> </label>
									</div>
								</c:if>
								<c:set var="lineItemStatus"
									value="${ordDetails.value.getLineItemStatus()}" />
								<c:if
									test="${(orderDetailsSize > 1)  &&  ( lineItemStatus eq 'Pending' || orderStatus eq 'IN PROGRESS')}">
									<div class="check" style="visibility: hidden;">
										<input type="checkbox" name="lineItem"
											id="check-${ordDetails.value.getOrderLineNumber()}"
											class="css-checkbox unchecked"
											value="${ordDetails.value.getOrderLineNumber()}"
											onclick="return enableQtyField(this);" /> <label
											for="check-${ordDetails.value.getOrderLineNumber()}"
											class="css-label"></label>
									</div>
								</c:if>
								<c:if test="${orderDetailsSize <= 1}">
									<input type="hidden" name="lineItem"
										value="${ordDetails.value.getOrderLineNumber()}" />
									<div class="check" style="visibility: hidden;">
										<input type="checkbox" name="lineItem"
											id="check-${ordDetails.value.getOrderLineNumber()}"
											class="css-checkbox unchecked"
											value="${ordDetails.value.getOrderLineNumber()}" /> <label
											for="check-${ordDetails.value.getOrderLineNumber()}"
											class="css-label"></label>
									</div>
								</c:if>
							</div>

							<div class="div-width-2 item">
								<c:out value="${ordDetails.value.getOrderLineNumber()}" />
								<c:set var="lineNo"
									value="${ordDetails.value.getOrderLineNumber()}" />
								<input type="hidden" name="lineNumber"
									value="${ordDetails.value.getOrderLineNumber()}" /> <input
									type="hidden"
									name="RRP-${ordDetails.value.getOrderLineNumber()}"
									id="RRP-${ordDetails.value.getOrderLineNumber()}"
									value="${ordDetails.value.getCostPerItem()}" />
							</div>
							<c:if test="${ empty ordDetails.value.getSku()}">
								<div class="div-width-3 item break-word">
									<c:out value="-" />
									<input type="hidden" name="sku" value="-" />
								</div>
							</c:if>
							<c:if test="${not empty ordDetails.value.getSku()}">
								<div class="div-width-3 item break-word">
									<c:out value="${ordDetails.value.getSku()}" />
									<input type="hidden" name="sku"
										value="${ordDetails.value.getSku()}" />
								</div>
							</c:if>

							<div class="div-width-4 item desc">
								<c:out value="${ordDetails.value.getDescription()}" />
								<input type="hidden" name="description"
									value="${ordDetails.value.getDescription()}" />
							</div>
							<div class="div-width-5 item">
								<c:out value="${ordDetails.value.getLineItemStatus()}" />
								<input type="hidden"
									name="status-${ordDetails.value.getOrderLineNumber()}"
									id="status-${ordDetails.value.getOrderLineNumber()}"
									value="${ordDetails.value.getLineItemStatus()}" />
							</div>
							<div
								class='div-width-9 item break-word<c:if test="${ empty orderDetails.shipmentTrackingDetails || orderDetails.shipmentTrackingDetails== ''}"> hidden</c:if> '>${ordDetails.value.getTrackingId()}
								<c:if test="${ordDetails.value.getTrackingId()==null}">&nbsp;</c:if>
								<input type="hidden" name="trackingNumber" id="trackingNumber"
									value="${ordDetails.value.getTrackingId()}" />
							</div>
							<div class="div-width-6 item">
								£
								<fmt:formatNumber type="number" minFractionDigits="2"
									value="${ordDetails.value.getCostPerItem()}" />
								<input type="hidden" name="itemPrice"
									value="${ordDetails.value.getCostPerItem()}" />
							</div>

							<div class="div-width-7 item">
								<span class="badge badge-square"><c:out
										value="${ordDetails.value.getOrderedQuantity()}" /></span> <input
									type="hidden" name="orderQunty"
									value="${ordDetails.value.getOrderedQuantity()}" />
								<c:set var="orderedQuantity"
									value="${ordDetails.value.getOrderedQuantity()}" />
								<input type="hidden"
									name="dispatchedQty-${ordDetails.value.getOrderLineNumber()}"
									id="dispatchedQty-${ordDetails.value.getOrderLineNumber()}"
									value="${ordDetails.value.dispatchedQuantity}" />
								<fmt:parseNumber var="dispatchQty" integerOnly="true"
									type="number" value="${ordDetails.value.dispatchedQuantity}" />
								<c:set var="dispatchedQty" value="${dispatchQty}" />
								<input type="hidden"
									name="acknowledgedQty-${ordDetails.value.getOrderLineNumber()}"
									id="acknowledgedQty-${ordDetails.value.getOrderLineNumber()}"
									value="${ordDetails.value.acknowledgedQuantity}" />
								<fmt:parseNumber var="acknowledgeQty" integerOnly="true"
									type="number" value="${ordDetails.value.acknowledgedQuantity}" />
								<c:set var="acknowledgedQty" value="${acknowledgeQty}" />
							</div>
							<div class="div-width-7 item">
								<fmt:parseNumber var="openQuantity" integerOnly="true"
									type="number" value="${ordDetails.value.getOpenQuantity()}" />
								<c:out value="${openQuantity}" />
								<input type="hidden"
									id="openQty-${ordDetails.value.getOrderLineNumber()}"
									value="${openQuantity}" />
							</div>
							<div class="div-width-8 item">
								&nbsp;
								<c:out
									value="${ordDetails.value.getPrimaryReasonForCancellation()}" />
							</div>
							<div class="div-width-9 item">
								&nbsp;
								<c:out value="${ordDetails.value.getSecondaryReasonForReturn()}" />
							</div>
							<!-- dispatch_section -->
							<div class="div-width-10 item display-none qty_section">

								<c:set var="txtClass" value="form-control wd50 qty" />
								<c:if test="${orderDetailsSize <= 1}">
									<c:choose>
										<c:when test="${openQuantity == 1 || dispatchQty == 1}">
											<c:set var="text2" value="1"></c:set>
											<input style="height: auto" type="text" maxlength="401"
												value="1"
												id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												placeholder="" class="form-control wd50" readonly />
										</c:when>
										<c:when test="${openQuantity > 0 && dispatchQty <= 0}">
											<input type="text" maxlength="401" value="${openQuantity}"
												id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												placeholder="" class="form-control wd50"
												style="display: block;"
												onblur="return validateQty('updatedQuantity-${ordDetails.value.getOrderLineNumber()}');" />
										</c:when>
										<c:when test="${openQuantity <= 0 && dispatchQty > 0}">
											<input type="text" maxlength="401" value="${dispatchQty}"
												id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												placeholder="" class="form-control wd50"
												style="display: block;"
												onblur="return validateQty('updatedQuantity-${ordDetails.value.getOrderLineNumber()}');" />
										</c:when>
										<c:when test="${openQuantity > 1 && dispatchQty > 1}">
											<input type="text" maxlength="401" value="${openQuantity}"
												id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												placeholder="" class="form-control wd50"
												style="display: block;"
												onblur="return validateQty('updatedQuantity-${ordDetails.value.getOrderLineNumber()}');" />
										</c:when>
										<c:when test="${openQuantity <= 0 && dispatchQty <= 0}">
											<input type="text" maxlength="401" value="0"
												id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												placeholder="" class="form-control wd50"
												style="display: none;" />
										</c:when>
									</c:choose>
								</c:if>

								<c:if test="${orderDetailsSize > 1}">
									<c:choose>
										<c:when test="${openQuantity <= 0}">
											<c:set var="text1" value=""></c:set>
											<input maxlength="401" style="height: auto" type="text"
												value="${dispatchQty}"
												id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												placeholder="" class="${txtClass}" readonly
												onblur="return validateQty('updatedQuantity-${ordDetails.value.getOrderLineNumber()}');" />
										</c:when>
										<c:when test="${dispatchQty <= 0}">
											<c:set var="text1" value=""></c:set>
											<input maxlength="401" style="height: auto" type="text"
												value="${openQuantity}"
												id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												placeholder="" class="${txtClass}" readonly
												onblur="return validateQty('updatedQuantity-${ordDetails.value.getOrderLineNumber()}');" />
										</c:when>
										<c:when test="${openQuantity > 1 && dispatchQty <= 1}">
											<c:set var="text1" value=""></c:set>
											<input maxlength="401" style="height: auto" type="text"
												value="${openQuantity}"
												id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												placeholder="" class="${txtClass}" readonly
												onblur="return validateQty('updatedQuantity-${ordDetails.value.getOrderLineNumber()}');" />
										</c:when>
										<c:when test="${openQuantity == 1 && dispatchQty == 1}">
											<c:set var="text1" value=""></c:set>
											<input maxlength="401" style="height: auto" type="text"
												value="1"
												id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												placeholder="" class="${txtClass}" readonly
												onblur="return validateQty('updatedQuantity-${ordDetails.value.getOrderLineNumber()}');" />
										</c:when>
										<c:when test="${openQuantity > 1 && dispatchQty > 1}">
											<c:set var="text1" value=""></c:set>
											<input maxlength="401" type="text" style="height: auto"
												id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												placeholder="" class="${txtClass}" style="display:block;"
												onblur="return validateQty('updatedQuantity-${ordDetails.value.getOrderLineNumber()}');" />
										</c:when>
										<c:when test="${dispatchQty > 1 && openQuantity <= 1}">
											<c:set var="text1" value=""></c:set>
											<input maxlength="401" type="text" style="height: auto"
												value="${dispatchQty}"
												id="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												name="updatedQuantity-${ordDetails.value.getOrderLineNumber()}"
												placeholder="" class="${txtClass}" style="display:block;"
												onblur="return validateQty('updatedQuantity-${ordDetails.value.getOrderLineNumber()}');" />
										</c:when>
									</c:choose>
								</c:if>
							</div>
						</div>
						<div class="col-xs-16 item">
							<div id="err-${ordDetails.value.getOrderLineNumber()}"
								name="errorInQuantity" class="inline_error"></div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="table-body display-none dispatch_section dispatchSec">
			<!--The following is for a single row -->
			<div
				class="row table-row  margin-top-20 margin-right-0 padding-right-0">
				<div class="col-xs-6 item padding-left-0 padding-right-0">
					<label>TRACKING ID</label>
					<div>
						<input type="text" placeholder="Enter a tracking id"
							maxlength="401" name="trackingId" id="trackingId"
							onblur="return alphanumericspaces(this);">
					</div>
					<div class="inline_error" id="errorTracking"></div>
				</div>
				<div class="col-xs-6 item padding-right-0">
					<label>CARRIER NAME</label>
					<div>
						<input class="col-xs-10" type="text" maxlength="401"
							placeholder="Enter a carrier name" name="carrierName"
							id="carrierName" onblur="return alphanumericspaces(this);">
					</div>
					<div class="inline_error" id="errorCarrier"></div>
				</div>
				<div class="col-xs-4 item pull-right">
					<div class="pull-right">
						<button type="button" id="cancelDispatch_btn"
							class="btn secondary-CTA">Cancel</button>
						<button type="button" class="btn primary-CTA ml-10"
							id="confirmDispatch_btn">Confirm</button>
					</div>
				</div>
			</div>
		</div>
		<div class="table-body display-none cancelSec">
			<div class="row table-row  margin-top-20">
				<div class="col-xs-10 item padding-left-0">
					<label class="padding-left-0">PRIMARY REASON</label>
					<div class="col-xs-12">
						<div class="selectBoxBind" id="selectPrimaryCancel">
							<input name="primaryCancelReason" id="primaryCancelReason"
								type="hidden" value="defaultPri"></input> <span class='selected'></span>
							<span class='selectArrow'>&#9660;</span>
							<div class="selectOptions">
								<span class="selectOption primaryCancel" value="defaultPri">Select
									Box</span> <span class="selectOption primaryCancel" value="101">Pick
									failed</span> <span class="selectOption primaryCancel" value="126">Customer
									had a change of mind - the order is no longer needed</span> <span
									class="selectOption primaryCancel" value="127">Customer
									changed the quantity</span> <span class="selectOption primaryCancel"
									value="128">Customer found the product cheaper elsewhere</span>
								<span class="selectOption primaryCancel" value="129">Customer
									address was incorrect</span> <span class="selectOption primaryCancel"
									value="130">Customer delivery date was incorrect</span> <span
									class="selectOption primaryCancel" value="131">Order was
									late or delayed</span> <span class="selectOption primaryCancel"
									value="132">Wanted specified delivery time</span> <span
									class="selectOption primaryCancel" value="102">Others</span>
							</div>
						</div>
					</div>
					<div class="col-xs-16 inline_error" id="errorPriCancelReason"
						style="padding-bottom: 15px;"></div>
				</div>
				<!-- <div class="col-xs-2 item">
					<div id="errordiv1" style="font-size: medium; color: red; font-weight: bold;"></div>
				</div>	  -->
				<div class="col-xs-5 item  margin-right-0 padding-right-0"
					style="margin-left: 28px">
					<div class="pull-right">
						<button type="button" id="cancelC_btn" class="btn secondary-CTA">Cancel</button>
						<button type="button" class="btn primary-CTA ml-10"
							id="confirmButton" disabled>Confirm</button>
					</div>
				</div>
			</div>
		</div>
		<div class="table-body display-none ret_sec return_section"
			id="return_section">
			<div class="row table-row lightText">
				<div class="col-xs-6 item">
					<div class="col-xs-16">
						<label class="col-xs-6">REFUND AMOUNT</label>
						<div class="col-xs-10">
							<input maxlength="400" class="pull-right" type="text"
								placeholder="£" id="refundAmount" name="refundAmount"
								onblur="return validateRefund();">
						</div>
					</div>
					<div class="col-xs-16">
						<!-- <span class="smallText">Maximum £###.## (inc. Delivery Cost)</span> -->
					</div>
					<div class="col-xs-16 inline_error" id="errorRefundAmt"></div>
				</div>
				<div class="col-xs-7" style="float: left;">
					<div class="col-xs-16 item">
						<label class="col-xs-6">PRIMARY REASON FOR RETURN</label>
						<div class="col-xs-10">
							<div class='selectBoxBind' id='selectPrimary'>
								<input name="primaryReturnReason" id="primaryReturnReason"
									type="hidden" value="defaultPri"></input> <span
									class='selected'></span> <span class='selectArrow'>&#9660;</span>
								<div class="selectOptions" id="selectOptionsPrimary">
									<span class="selectOption primaryReturn" value="defaultPri">
										Select Box</span> <span class="selectOption primaryReturn"
										value="200">Faulty Product</span> <span
										class="selectOption primaryReturn" value="225">Damaged
										product</span> <span class="selectOption primaryReturn" value="250">Missing
										items</span> <span class="selectOption primaryReturn" value="275">Missing
										Components</span> <span class="selectOption primaryReturn"
										value="300">No longer wanted</span> <span
										class="selectOption primaryReturn" value="325">Product
										not as expected</span> <span class="selectOption primaryReturn"
										value="350">Wrong description on website</span> <span
										class="selectOption primaryReturn" value="375">Emergency
										product withdrawal</span> <span class="selectOption primaryReturn"
										value="400">Never delivered</span> <span
										class="selectOption primaryReturn" value="425">Problem
										with other item, returning all items</span> <span
										class="selectOption primaryReturn" value="450">Late
										delivery not within the expected date</span>
								</div>
							</div>
						</div>
						<div class="col-xs-16 inline_error" id="errorPriReason"
							style="padding-bottom: 15px;"></div>
					</div>
					<div class="col-xs-16 item">
						<label class="col-xs-6">SECONDARY REASON FOR RETURN</label>
						<div class="col-xs-10">

							<div class='selectBoxBind' id='selectSecondary'>
								<input name="secondaryReturnReason" id="secondaryReturnReason"
									type="hidden" value="defaultSec"></input> <span
									class='selected'></span> <span class='selectArrow'>&#9660;</span>
								<div class="selectOptions" id="selectOptionsSecondary">
									<span class="selectOption secondaryReturn" value="defaultSec">
										Select Box</span> <span class="selectOption secondaryReturn"
										value="200">Faulty Product</span> <span
										class="selectOption secondaryReturn" value="226">Packaging
										or transit is damaged</span> <span
										class="selectOption secondaryReturn" value="227">Packaging
										is correct</span> <span class="selectOption secondaryReturn"
										value="de1">Missing items</span> <span
										class="selectOption secondaryReturn" value="de2">Missing
										Components</span> <span class="selectOption secondaryReturn"
										value="301">Cheaper Elsewhere</span> <span
										class="selectOption secondaryReturn" value="302">Customer
										ordered wrong product</span> <span
										class="selectOption secondaryReturn" value="303">Customer
										ordered wrong size</span> <span class="selectOption secondaryReturn"
										value="304">Customer ordered wrong colour</span> <span
										class="selectOption secondaryReturn" value="305">Customer
										ordered wrong quantity</span> <span
										class="selectOption secondaryReturn" value="306">Unable
										to install / assemble</span> <span
										class="selectOption secondaryReturn" value="326">Wrong
										Product delivered</span> <span class="selectOption secondaryReturn"
										value="327">Wrong size delivered</span> <span
										class="selectOption secondaryReturn" value="350">Wrong
										description on website</span> <span
										class="selectOption secondaryReturn" value="375">Emergency
										product withdrawal</span> <span class="selectOption secondaryReturn"
										value="401">Assembly or mis-pack</span> <span
										class="selectOption secondaryReturn" value="402">Lost
										in courier</span> <span class="selectOption secondaryReturn"
										value="403">Lost in store</span> <span
										class="selectOption secondaryReturn" value="405">Fraud
										suspected</span> <span class="selectOption secondaryReturn"
										value="425">Problem with other item, returning all
										items</span> <span class="selectOption secondaryReturn" value="450">Late
										delivery not within the expected date</span>
								</div>
							</div>
						</div>
						<div class="col-xs-16 inline_error" id="errorSecReason"
							style="padding-bottom: 15px;"></div>
					</div>
				</div>
				<div class="col-xs-3 item pull-right margin-right-0 padding-right-0">
					<div>
						<button type="button" class="btn secondary-CTA"
							id="cancelReturn_btn">Cancel</button>
						<button type="button" class="btn primary-CTA ml-10"
							id="confirmReturnButton" disabled>Confirm</button>
					</div>
				</div>
			</div>
		</div>

	</div>
</form>
