<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui"  prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>

<%@ include file="/WEB-INF/views/init.jsp"%>

<portlet:defineObjects/>
<liferay-theme:defineObjects/>

<link rel="stylesheet" href="<%=themeDisplay.getPathThemeCss()%>/jquery-ui.css">
<link rel="stylesheet" href="<%=themeDisplay.getPathThemeCss()%>/bootstrap-datetime-min.css"/>
<script src="<%=themeDisplay.getPathThemeJavaScript() %>/moment.min.js"></script>
<script src="<%=themeDisplay.getPathThemeJavaScript() %>/bootstrap-datetimepicker.js"></script>

<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="printPopupURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="jspPage" value="printOrders"/> 
	<portlet:param name="pageDisplay" value="open"/>
</portlet:renderURL>

<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="quickOrderDispatchURL">
	<portlet:param name="action" value="renderQuickDispatchOpenOrders" />
</portlet:renderURL>

<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="renderOrderDetailsURL">
	<portlet:param name="action" value="renderOrderDetails" />
</portlet:renderURL>

<portlet:actionURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="commitOrders" >
	<portlet:param name="action" value="commitOrders"/>
</portlet:actionURL>

<portlet:resourceURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" id="orderDownload" var="orderDownloadURL">
	<portlet:param name="action" value="orderDownloadURL" />																			
	<portlet:param name="pageDisplay" value="open"/>
</portlet:resourceURL>

<portlet:resourceURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" id="getList" var="getListURL" >
</portlet:resourceURL>

<portlet:resourceURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" id="getOrderList" var="getOrderListURL" >
</portlet:resourceURL>

<portlet:resourceURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" id="checkOrderExist" var="checkOrderExistURL"></portlet:resourceURL>


<script type="text/javascript">

function redirectToOrderDetails(orderId,status){
	var orderDetailsURL = '<%=renderOrderDetailsURL%>';
	var finalOrderDetailsURL = orderDetailsURL + "&orderId=" + orderId.toString() +"&orderStatus="+status;
	
	document.getElementById("frm1").action = finalOrderDetailsURL;
	document.getElementById("frm1").submit();
}

function pushDataToURL(){
	
	var selectedTab = $("#selectedTab").val();
	var customerName = $("#searchOrderText").val();
	
	var pageNumberForReq = $("#pageNumber").val();
	
	var filterResults = $("#filterResults").val();
	
	var filterDeliveryOption = $("#filterDeliveryOption").val();
	var filterStatus = $("#filterStatus").val();
	var filterFromDate = $("#filterFromDate").val();
	var filterToDate = $("#filterToDate").val();
	
	var sortByDateIsApplied = $("#sortByDateIsApplied").val();
	var sortByDate = $("#sortByDate").val();
	
	var doAsUserId = getParameterByName("doAsUserId") ;
	var parameterList = "";
	if(doAsUserId!=""){
		parameterList = "doAsUserId="+doAsUserId+"&";
	}

	parameterList = parameterList+ "selectedTab="+selectedTab+"&customerName="+customerName+"&pageNumberForReq="+pageNumberForReq+"&filterResults="+filterResults+"&filterDeliveryOption="+filterDeliveryOption+"&filterStatus="+filterStatus+"&filterFromDate="+filterFromDate+"&filterToDate="+filterToDate+"&sortByDateIsApplied="+sortByDateIsApplied+"&sortByDate="+sortByDate;
	window.history.pushState('object','New Title', ("my-orders?"+parameterList));
}

$(window).bind("popstate", function(e) {
	//var parameterList = filterToDate="+filterToDate+"&sortByDateIsApplied="+sortByDateIsApplied+"&sortByDate="+sortByDate;
	var selectedTab = getParameterByName("selectedTab");
	var customerName = getParameterByName("customerName");
	var pageNumberForReq = getParameterByName("pageNumberForReq");
	var filterResults = getParameterByName("filterResults");
	var filterDeliveryOption = getParameterByName("filterDeliveryOption");
	var filterStatus = getParameterByName("filterStatus");
	var filterFromDate = getParameterByName("filterFromDate");
	var filterToDate = getParameterByName("filterToDate");
	var sortByDateIsApplied = getParameterByName("sortByDateIsApplied");
	var sortByDate = getParameterByName("sortByDate");
	
	if(selectedTab == ""  || selectedTab == "released|acknowledged"){
		selectedTab = "released";
	}
	$("#selectedTab").val(selectedTab);
	$("#searchOrderText").val(customerName);
	$("#pageNumber").val(pageNumberForReq);
	
	$("#sortByDateIsApplied").val(sortByDateIsApplied);
	$("#sortByDate").val(sortByDate);
	
	if("true"!=sortByDateIsApplied && "true"!=filterResults && (selectedTab=="released" | selectedTab=="dispatched" | selectedTab=="cancelled" | selectedTab=="returned")){

		displayOrdersOnSubmitTabChange(selectedTab);
	}
	if(customerName!=""){
		if("true"!=sortByDateIsApplied){
			$('#sortSearchResult').children('span.selected').html('Release Date (Oldest First)');
			$('#sortSearchResult').attr('value', 'ReleasedDate-asc');
		}
		submitSearchForCustomerName();
	}
	if("true"==sortByDateIsApplied){
		changeUIonSelectedTabInBackAction();
		var sortByHtmlText = "";
		if("ReleasedDate-asc"==sortByDate){
			sortByHtmlText = "Release Date (Oldest First)";
		}else if("ReleasedDate-desc"==sortByDate){
			sortByHtmlText = "Release Date (Newest First)";
		}else if("ExpectedDispatchDate-asc"==sortByDate){
			sortByHtmlText = "Expected Shipment date (Oldest first)";
		}else if("ExpectedDispatchDate-desc"==sortByDate){
			sortByHtmlText = "Expected Shipment date (Newest first)";
		}else if("DispatchedDate-asc"==sortByDate){
			sortByHtmlText = "Dispatch Date (Oldest First)";
		}else if("DispatchedDate-desc"==sortByDate){
			sortByHtmlText = "Dispatch Date (Newest First)";
		}else if("CancelledDate-asc"==sortByDate){
			sortByHtmlText = "Cancel Date (Oldest First)";
		}else if("CancelledDate-desc"==sortByDate){
			sortByHtmlText = "Cancel Date (Newest First)";
		}else if("ReturnedDate-asc"==sortByDate){
			sortByHtmlText = "Return Date (Oldest First)";
		}else if("ReturnedDate-desc"==sortByDate){
			sortByHtmlText = "Return Date (Newest First)";
		}

		if("released"==selectedTab){
			$('#sortReleasedTab').children('span.selected').html(sortByHtmlText);
			$('#sortReleasedTab').attr('value', sortByDate);
		}else if("dispatched"==selectedTab){
			$('#sortDispatchedTab').children('span.selected').html(sortByHtmlText);
			$('#sortDispatchedTab').attr('value', sortByDate);
		}else if("cancelled"==selectedTab){
			$('#sortCancelledTab').children('span.selected').html(sortByHtmlText);
			$('#sortCancelledTab').attr('value', sortByDate);
		}else if("returned"==selectedTab){
			$('#sortReturnedTab').children('span.selected').html(sortByHtmlText);
			$('#sortReturnedTab').attr('value', sortByDate);
		}else if("released|dispatched|cancelled|returned"==selectedTab){
			$('#sortSearchResult').children('span.selected').html(sortByHtmlText);
			$('#sortSearchResult').attr('value', sortByDate);
		}
		
		sortListByFinalCall();
	}
	if("true"==filterResults){
		changeUIonSelectedTabInBackAction();
		$("#filterResults").val(filterResults);
		$("#filterDeliveryOption").val(filterDeliveryOption);
		$("#filterStatus").val(filterStatus);
		$("#filterFromDate").val(filterFromDate);
		$("#filterToDate").val(filterToDate);
		$("#<portlet:namespace/>clearAllFilters").show();
		changeFilterUIOnBackAction();
		displayOrderList();
	}else{
		$("#<portlet:namespace/>clearAllFilters").hide();
	}
	
});

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)");
    var results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function submitSearchForCustomerName() {
	clearAllFilters();

	var customerName = $("#searchOrderText").val();
	if(customerName.length<3){
		$("#pageStatus").empty();
		$("#tableHeading").empty();
		$("#tableBody").empty();

		$("#tableHeading").html("<div class='padding-left-20' id='errorMsg'></div>");
		$("#tableHeading").removeClass("border-top-thick");
		
		$("#errorMsg").append("<div class='zero-results-found'>Your search returned 0 results.</div>");
		$("#errorMsg").append("<div class='no-results-found-suggestions'>Suggestions:</div>");
		$("#errorMsg").append("<div class='no-results-found-suggestions-list'><ul><li>Ensure that all words are spelt correctly</li><li>Ensure that your search is 3 or more characters long</li><li>Try different keywords</li><li>Ensure you have only used permitted special characters &lsquo;&#46;&#45;</li></ul></div>");
		$("#filterByPage, #sortByPage, #actionButtons").hide();
		$(".ajax-pagination").hide();
		clearAllFilters();
	}else{
		displayOrderList();
		
		$("#print").removeAttr("style");
		$("#download").show();
		$("#quickDispatch, #quickDispatchSpan").show();
		$("#dateRangeBy").html("Release Date Range");
	}
		
	//displayHideActionButton(0);
	
	$("#releasedTab").removeClass("selected");
	$("#dispatchedTab").removeClass("selected");
	$("#cancelledTab").removeClass("selected");
	$("#returnedTab").removeClass("selected");
}

function submitSearchForm(){
	var serachOrderTxt = $("#searchOrderText").val();
	$("#pageNumber").val("1");
	if(""!=serachOrderTxt){		

		var orderIdAlphaNumFormat = /^([0-9A-Za-z])(?=.*[a-zA-Z])(?=.*[0-9])[0-9A-Za-z]+$/;
		var orderIdWithHyphenFormat = /^([A-Za-z0-9]{1,})-([0-9]{1,2})$/;
		
		$("#<portlet:namespace/>filterResults").show();
		$(".sort_select").show();
		
		if (orderIdWithHyphenFormat.test(serachOrderTxt)){			/** Order id search with Release Number **/
			searchByOrderIdWithRelNumAndRedirectToOrderDetails(serachOrderTxt);
			
		} else if (orderIdAlphaNumFormat.test(serachOrderTxt)){		/** Order id search without Release Number  **/
			searchByOrderIdWithoutRelNumAndRedirectToOrderList(serachOrderTxt);
			
		} else{														/** Order id search without Release Number; If no results, then search by Customer Name **/
			$("#selectedTab").val("released|dispatched|cancelled|returned|acknowledged");
    		$("#sortByDate").val("ReleasedDate-asc");
    		if(window.history.pushState) pushDataToURL();
        	submitSearchForCustomerName();
		}
		
		$("#dateRangeBy").html("Release Date Range");
	}
	$("#releasedTab").removeClass("selected");
	$("#dispatchedTab").removeClass("selected");
	$("#cancelledTab").removeClass("selected");
	$("#returnedTab").removeClass("selected");
	
}
function searchByOrderIdWithoutRelNumAndRedirectToOrderList() {

	clearAllFilters();
	$("#<portlet:namespace/>filterResults").show();
	$(".sort_select").show();

	var $errPrintDownload = $('#errPrintDownload');
	if($errPrintDownload.length > 0 && $errPrintDownload.is(':visible')){
		$('#errPrintDownload').hide().text('');
	}
	$("#selectedTab").val("released|dispatched|cancelled|returned|acknowledged");
	var selectedTab = $("#selectedTab").val();

 	if(selectedTab == "" || selectedTab == "released|acknowledged") {
		selectedTab = "released";
	}
	var orderId = $("#searchOrderText").val();
	jQuery.ajax({
		type: "POST",
		cache: false,
		async: false,
		data: {orderId : orderId,selectedTab:selectedTab},
		url: "${getOrderListURL}",
		dataType : "json"
		}).done(function( data ){
			var totalOrdersCount = parseInt(data['totalOrdersCount']);
			var orderIdWithRelNum = data['orderId'];
			
			if(totalOrdersCount == 1) {
				$("#download").hide();
				$("#print").hide();
				$("#print").attr("disabled","disabled");
				redirectToOrderDetails(orderIdWithRelNum);
			} else {
				getOrderListDetails(data);
				$("#<portlet:namespace/>filterResults").hide();
				$(".sort_select").hide();
				$("#print").removeAttr("style");
				$("#download").removeAttr("style");
				$("#download").show();
				$("#print").show();
			}
	});
	
		$("#quickDispatch, #quickDispatchSpan").show();
		$("#dateRangeBy").html("Release Date Range");
}

function searchByOrderIdWithRelNumAndRedirectToOrderDetails(serachOrderTxt) {
	jQuery.ajax({
		type: "POST",
		cache: false,
		async: false,
		data: {orderId : serachOrderTxt},
		url: "${checkOrderExistURL}",
		dataType : "text"
	}).done(function( data ){
		if("SUCCESS" == data){
			redirectToOrderDetails(serachOrderTxt);
		}else{
			
			$("#pageStatus").empty();
			$("#tableHeading").empty();
			$("#tableBody").empty();
			$("#tableHeading").removeClass("border-top-thick");

			$("#tableHeading").html("<div class='padding-left-20' id='errorMsg'></div>");
			$("#errorMsg").append("<div class='zero-results-found'>Your search returned 0 results.</div>");
			$("#errorMsg").append("<div class='no-results-found-suggestions'>Suggestions:</div>");
			$("#errorMsg").append("<div class='no-results-found-suggestions-list'><ul><li>Ensure that all words are spelt correctly</li><li>Ensure that your search is 3 or more characters long</li><li>Try different keywords</li><li>Ensure you have only used permitted special characters &lsquo;&#46;&#45;</li></ul></div>");
			$("#filterByPage, #sortByPage, #actionButtons").hide();
			$(".ajax-pagination").hide();
			clearAllFilters();
		}
	});
}
$(document).keypress(function(e) {
    if(e.which == 13) {
    	submitSearchForm();
    	e.preventDefault();
    }
});

function changeUIonSelectedTabInBackAction(){
	var selectedTabUI = $("#selectedTab").val();
	/*3417.09-10*/
	$('#optPrintOrders').parent().hide();
	$('#optDownloadOrders').parent().hide();
	$("#print, #download").show();
	//$('#actionButtons').addClass('col-xs-12').removeClass('col-xs-6');
	$('#actionButtons').addClass('col-xs-10').removeClass('col-xs-4');/*def#888*/
	$("#quickDispatch").removeAttr("style");
	
	if(selectedTabUI == "" || selectedTabUI == "released|acknowledged") {
		selectedTabUI = "released";
	}
	
	if("released" == selectedTabUI){
		$("#releasedTab").addClass("selected");
		$("#dispatchedTab, #cancelledTab, #returnedTab").removeClass("selected");
		
		$("#print").removeAttr("style");
		$("#download, #quickDispatch, #quickDispatchSpan").show();
		
		$("#dateRangeBy").html("Release Date Range");
		
		/*3417.09-10*/
		$('#optPrintOrders').parent().show();
		$('#optDownloadOrders').parent().show();
		$("#print, #download").hide();
		//$('#actionButtons').addClass('col-xs-6').removeClass('col-xs-12');/*def#888*/
		$('#actionButtons').addClass('col-xs-4').removeClass('col-xs-10');
		$("#quickDispatch").css({"border-radius":"4px"});
		
	}else if("dispatched" == selectedTabUI){
		$("#dispatchedTab").addClass("selected");
		$("#releasedTab, #cancelledTab, #returnedTab").removeClass("selected");
		
		$("#print").css({"border-radius":"4px"});
		$("#download, #quickDispatch, #quickDispatchSpan").hide();
		
		$("#dateRangeBy").html("Dispatch Date Range");
		
	}else if("cancelled" == selectedTabUI){
		$("#cancelledTab").addClass("selected");
		$("#dispatchedTab, #releasedTab, #returnedTab").removeClass("selected");
		
		$("#print").css({"border-radius": "4px"});
		$("#download, #quickDispatch, #quickDispatchSpan").hide();

		$("#dateRangeBy").html("Cancel Date Range");
		
	}else if("returned" == selectedTabUI){
		$("#returnedTab").addClass("selected");
		$("#cancelledTab, #dispatchedTab, #releasedTab").removeClass("selected");

		$("#print").css({"border-radius":"4px"});
		$("#download, #quickDispatch, #quickDispatchSpan").hide();
		
		$("#dateRangeBy").html("Return Date Range");
	}
}
function changeUIonSelectedTab(){
	var selectedTabUI = $("#selectedTab").val();
	
	$("#<portlet:namespace/>filterResults").show();
	$(".sort_select").show();
	
	/*3417.09-10*/
	$('#optPrintOrders').parent().hide();
	$('#optDownloadOrders').parent().hide();
	$("#print, #download").show();
	//$('#actionButtons').addClass('col-xs-12').removeClass('col-xs-6');
	$('#actionButtons').addClass('col-xs-10').removeClass('col-xs-4');
	$("#quickDispatch").removeAttr("style");
	
	if(selectedTabUI == "" || selectedTabUI == "released|acknowledged") {
		selectedTabUI = "released";
	}
	if("released" == selectedTabUI){
		
		$("#releasedTab").addClass("selected");
		$("#cancelledTab, #dispatchedTab, #returnedTab").removeClass("selected");
		
		$("#print").removeAttr("style");
		$("#download, #quickDispatch, #quickDispatchSpan").show();
		
		$("#searchOrderText").val("");
		$("#sortByDate").val("ReleasedDate-asc");
		$("#dateRangeBy").html("Release Date Range");
		
		/*3417.09-10*/
		$('#optPrintOrders').parent().show();
		$('#optDownloadOrders').parent().show();
		$("#print, #download").hide();
		//$('#actionButtons').addClass('col-xs-6').removeClass('col-xs-12');
		$('#actionButtons').addClass('col-xs-4').removeClass('col-xs-10');
		$("#quickDispatch").css({"border-radius":"4px"});
		
	}else if("dispatched" == selectedTabUI){
		$("#dispatchedTab").addClass("selected");
		$("#cancelledTab, #releasedTab, #returnedTab").removeClass("selected");
		
		$("#print").css({"border-radius":"4px"});
		$("#download, #quickDispatch, #quickDispatchSpan").hide();
		
		$("#searchOrderText").val("");
		
		$("#sortByDate").val("DispatchedDate-desc");
		$("#dateRangeBy").html("Dispatch Date Range");
		
	}else if("cancelled" == selectedTabUI){
		$("#cancelledTab").addClass("selected");
		$("#dispatchedTab, #releasedTab, #returnedTab").removeClass("selected");
		
		$("#print").css({"border-radius":"4px"});
		$("#download, #quickDispatch, #quickDispatchSpan").hide();
		
		$("#searchOrderText").val("");
		$("#sortByDate").val("CancelledDate-desc");
		$("#dateRangeBy").html("Cancel Date Range");
		
	}else if("returned" == selectedTabUI){
		$("#returnedTab").addClass("selected");
		$("#dispatchedTab, #releasedTab, #cancelledTab").removeClass("selected");
		
		$("#print").css({"border-radius": "4px"});
		$("#download, #quickDispatch, #quickDispatchSpan").hide();
		
		$("#searchOrderText").val("");
		$("#sortByDate").val("ReturnedDate-desc");
		
		$("#dateRangeBy").html("Return Date Range");
	}
}

function submitTabChange(selectedTabUI){
	$("#selectedTab").val(selectedTabUI);
	$("#pageNumber").val("1");
	hideFilterOption();
	if(window.history.pushState) pushDataToURL();
	displayOrdersOnSubmitTabChange(selectedTabUI);
}

function displayOrdersOnSubmitTabChange(selectedTabUI){
	$("#selectedTab").val(selectedTabUI);
	$("#pageNumber").val("1");
	changeUIonSelectedTab();
	var filterResults = $("#filterResults").val();
	if(filterResults=="true"){
		clearAllFilters();
	}
	resetSortByOption();
	displayOrderList();
}


function pageChange(value){
	var pageNo = parseInt($("#pageNumber").val());
	if("previous"==value){
		pageNo = pageNo - 1;
	}else if("next"==value){
		pageNo = pageNo + 1;
	}
	$("#pageNumber").val(pageNo);
	displayOrderList();
}

function displayOrderList(){
	var $errPrintDownload = $('#errPrintDownload');
	if($errPrintDownload.length > 0 && $errPrintDownload.is(':visible')){
		$('#errPrintDownload').hide().text('');
	}
	
	displaySortByOptions();
	$("#print").attr("disabled","disabled");
	var selectedTab = $("#selectedTab").val();

 	if(selectedTab == "" || selectedTab == "released|acknowledged") {
		selectedTab = "released";
	}
	var customerName = $("#searchOrderText").val();
	var pageNumberForReq = $("#pageNumber").val();
	
	var filterResults = $("#filterResults").val();
	
	var filterDeliveryOption = $("#filterDeliveryOption").val();
	var filterStatus = $("#filterStatus").val();
	var filterFromDate = $("#filterFromDate").val();
	var filterToDate = $("#filterToDate").val();
	
	var sortByDateIsApplied = $("#sortByDateIsApplied").val();
	var sortByDate = $("#sortByDate").val();
	
	jQuery.ajax({
		type: "POST",
		cache: false,
		async: false,
		data: {selectedTab:selectedTab,customerName:customerName,pageNumber:pageNumberForReq,
			filterResults:filterResults,filterDeliveryOption:filterDeliveryOption,filterStatus:filterStatus,filterFromDate:filterFromDate,filterToDate:filterToDate,
			sortByDateIsApplied:sortByDateIsApplied,sortByDate:sortByDate
		},
		url: "${getListURL}",
		dataType : "json"
		}).done(function( data ){
			getOrderListDetails(data);
	});
}

function getOrderListDetails(data) {
	var searchKey = $("#searchOrderText").val();
	var filterResults = $("#filterResults").val();
	var sortByDate = $("#sortByDate").val();
	var selectedTab = $("#selectedTab").val();

 	if(selectedTab == "" || selectedTab == "released|acknowledged") {
		selectedTab = "released";
	}
	var errorMsg = data['SESSION_ERROR_MESSAGE'];
	var searchType = data['searchType'];

	if(errorMsg=="" || errorMsg=="NO_ORDERS_FOUND"){
		$("#pageStatus").empty();
		$("#tableHeading").empty();
		$("#tableBody").empty();
		$("#tableHeading").addClass("border-top-thick");
		$("#previous-button").attr('disabled','disabled');
		$("#next-button").attr('disabled','disabled');
		
		var pageNumber = parseInt(data['pageNo']);
		var pageSize = parseInt(data['pageSize']);
		var totalOrdersCount = parseInt(data['totalOrdersCount']);
		if(totalOrdersCount > 0){
			$("#filterByPage").show();
			$("#sortByPage").show();
			$("#actionButtons").show();
			var totalPages = Math.ceil(totalOrdersCount / pageSize);
			$("#pageStatus").html("Page "+pageNumber+" of "+totalPages);
			
			//----------------------------------------------------------------------------
			
			$("#pageNumber").val(pageNumber);
			
			var orderList = data['orderList'];
			
			$("<h3>", {"class":"div-width-1 item check","id":"selectALLH1"}).appendTo($("#tableHeading"));
			$("<input>",{"class":"css-checkbox","type":"checkbox","name":"selectAll","id":"selectAll","value":"selectAll"}).appendTo($("#selectALLH1"));
			$("<label>",{"for":"selectAll","class":"css-label"}).appendTo($("#selectALLH1"));
			$("<h3>", {"class":"div-width-2 item"}).append("ORDER ID").appendTo($("#tableHeading"));
			$("<h3>", {"class":"div-width-3 item"}).append("# LINES").appendTo($("#tableHeading"));
			$("<h3>", {"class":"div-width-4 item"}).append("CUSTOMER NAME").appendTo($("#tableHeading"));
			$("<h3>", {"class":"div-width-5 item"}).append("DELIVERY").appendTo($("#tableHeading"));
			$("<h3>", {"class":"div-width-6 item"}).append("STATUS").appendTo($("#tableHeading"));
			if("released"==selectedTab){
				$("<h3>", {"class":"div-width-7 item"}).append("EXPECTED SHIPMENT DATE").appendTo($("#tableHeading"));
			}else if("dispatched"==selectedTab){
				$("<h3>", {"class":"div-width-7 item"}).append("DISPATCH DATE").appendTo($("#tableHeading"));
			}else if("cancelled"==selectedTab){
				$("<h3>", {"class":"div-width-7 item"}).append("CANCEL DATE").appendTo($("#tableHeading"));
			}else if("returned"==selectedTab){
				$("<h3>", {"class":"div-width-7 item"}).append("RETURN DATE").appendTo($("#tableHeading"));
			}else{
				$("<h3>", {"class":"div-width-7 item"}).append("DISPATCH DATE").appendTo($("#tableHeading"));
			}
			$("<h3>", {"class":"div-width-8 item"}).append("RELEASE DATE").appendTo($("#tableHeading"));
			
			var oldGroupDate = "",
				newGroupDate = "",
				divGroupCount = 0;
			var newGroupByDateDiv = "date-group-",
				divDateMain = "date-main-",
				divDate = "date-";
			
			var todayDate = new Date(),
				yesterdayDate = new Date();
			yesterdayDate.setDate(todayDate.getDate()-1);
			
			var dd = todayDate.getDate(),
				mm = todayDate.getMonth()+1; //January is 0
			if(dd >= 1 && dd <= 9){
				dd = "0" + dd; //append 0 to first 9 months. 1-9 shd dispaly as 01-09.
			}
			if(mm >= 1 && mm <= 9){
				mm = "0" + mm; //append 0 to first 9 months. 1-9 shd dispaly as 01-09.
			}
			var yyyy = todayDate.getFullYear();
			var todayDateString =  dd+'/'+mm+'/'+yyyy;
			
			dd = yesterdayDate.getDate();
			mm = yesterdayDate.getMonth()+1; //January is 0
			if(dd >= 1 && dd <= 9){
				dd = "0" + dd; //append 0 to first 9 months. 1-9 shd dispaly as 01-09.
			}
			if(mm >= 1 && mm <= 9){
				mm = "0" + mm; //append 0 to first 9 months. 1-9 shd dispaly as 01-09.
			}
			yyyy = yesterdayDate.getFullYear();
			var yesterdayDateString =  dd+'/'+mm+'/'+yyyy;
			jQuery.each( orderList, function( i, val ) {
				var divID = "div-"+val['orderId'];
				var statusID = "status-"+divID;
				var orderIdDiv = "orderId-"+val['orderId'];
				var selectId = "select-"+val['orderId'];
				
				if(sortByDate=="ReleasedDate-asc" || sortByDate=="ReleasedDate-desc"){
					newGroupDate = val['releasedDate'];
				}else if(sortByDate=="ExpectedDispatchDate-asc" || sortByDate=="ExpectedDispatchDate-desc"){
					newGroupDate = val['expectedShipmentDate'];
				}else if(sortByDate=="DispatchedDate-asc" || sortByDate=="DispatchedDate-desc"){
					newGroupDate = val['dispatchedDate'];
				}else if(sortByDate=="CancelledDate-asc" || sortByDate=="CancelledDate-desc"){
					newGroupDate = val['cancelledDate'];
				}else if(sortByDate=="ReturnedDate-asc" || sortByDate=="ReturnedDate-desc"){
					newGroupDate = val['returnedDate'];
				}else{
					newGroupDate = val['releasedDate'];
				}
				
				if(newGroupDate!=oldGroupDate){
					divGroupCount = divGroupCount+1;
					divDateMain = "date-main-"+divGroupCount;
					divDate="date-"+divGroupCount;
					newGroupByDateDiv = "date-group-"+divGroupCount;
					
					$("<div>", {"id":newGroupByDateDiv,"class":"group-by-date"}).appendTo($("#tableBody"));
					$("<div>", {"id":divDateMain,"class":"group-by-date-wrapper"}).appendTo($("#"+newGroupByDateDiv));
					if(todayDateString==newGroupDate){
						$("<div>", {"id":divDate,"class":"date-in-group-by-date"}).append("Today").appendTo($("#"+divDateMain));
					}else if(yesterdayDateString==newGroupDate){
						$("<div>", {"id":divDate,"class":"date-in-group-by-date"}).append("Yesterday").appendTo($("#"+divDateMain));
					}else{
	
						var splitDate = newGroupDate.split("/");
						var dateInFormat = new Date(splitDate[2], splitDate[1] - 1, splitDate[0]);
						var dayIntToDisplay = dateInFormat.getDay();
						var dateToDisplay = dateInFormat.getDate();
						var monthToDisplay = dateInFormat.getMonth()+1;
						var dayToDisplay = "";
						
						if(dayIntToDisplay==0){
							dayToDisplay = "Sun";
						}else if(dayIntToDisplay==1){
							dayToDisplay = "Mon";
						}else if(dayIntToDisplay==2){
							dayToDisplay = "Tue";
						}else if(dayIntToDisplay==3){
							dayToDisplay = "Wed";
						}else if(dayIntToDisplay==4){
							dayToDisplay = "Thu";
						}else if(dayIntToDisplay==5){
							dayToDisplay = "Fri";
						}else if(dayIntToDisplay==6){
							dayToDisplay = "Sat";
						}
						
						var displayDate = dayToDisplay +" - "+dateToDisplay+"/"+monthToDisplay;
						$("<div>", {"id":divDate,"class":"date-in-group-by-date"}).append(displayDate).appendTo($("#"+divDateMain));
					}
					$("<hr>", {"class":"hr-in-group-by-date"}).appendTo($("#"+divDateMain));
					
					oldGroupDate = newGroupDate;
				}
				
				if(searchType == "searchByOrderIdWithoutRelNum") {
					$("<div>", {"id":divID,"class":"row table-row clr-both"}).appendTo($("#tableBody"));
				} else {
					$("<div>", {"id":divID,"class":"row table-row clr-both"}).appendTo($("#"+newGroupByDateDiv));
				}
				
				$("<div>", {"class":"div-width-1 item ","id":selectId}).appendTo($("#"+divID));
				$("<div>", {"class":"check","id":"check-"+selectId}).appendTo($("#"+selectId));
				$("<input>",{"type":"checkbox","name":"selectedOrderNumbers","id":val['orderId'],"class":"unchecked css-checkbox","value":val['orderId']}).appendTo($("#"+"check-"+selectId));
				$("<label>",{"for":val['orderId'],"class":"css-label"}).appendTo($("#"+"check-"+selectId));
				
				$("<div>", {"id":orderIdDiv,"class":"div-width-2 item"}).appendTo($("#"+divID));
				$("<span>", {"class":"orderIdInList","onclick":"redirectToOrderDetails('"+val['orderId']+"','"+val['status']+"')"}).append(val['orderId']).appendTo($("#"+orderIdDiv));
	
				//if("released"==selectedTab){
					//$("<div>", {"class":"div-width-3 item"}).append(val['totalNoOfOpenLines']).appendTo($("#"+divID));
				//}else{
					$("<div>", {"class":"div-width-3 item"}).append(val['totalLineItems']).appendTo($("#"+divID));
				//}
				$("<div>", {"class":"div-width-4 item"}).append(val['customerName']).appendTo($("#"+divID));
				//$("<div>", {"class":"div-width-5 item"}).append(val['deliveryOption']).appendTo($("#"+divID));
				if(val['deliveryOption']==null || val['deliveryOption']=="undefined" || val['deliveryOption']==""){
					$("<div>", {"class":"div-width-5 item"}).append("&nbsp;").appendTo($("#"+divID));
					} else {
					$("<div>", {"id":"deliveryOption-"+divID,"class":"div-width-5 item breakword padding-right-5"}).append(val['deliveryOption']).appendTo($("#"+divID));
					}
				if("released"==selectedTab && "Pending" != val['status'] && "In progress" != val['status']){
					$("<div>", {"id":statusID,"class":"div-width-6 item","value":"Open"}).append("Open").appendTo($("#"+divID));
				} else {
				$("<div>", {"id":statusID,"class":"div-width-6 item","value":val['status']}).append(val['status']).appendTo($("#"+divID));
				}
				
				if("released"==selectedTab){
					$("<div>", {"class":"div-width-7 item"}).append(val['expectedShipmentDate']).appendTo($("#"+divID));
				}else if("dispatched"==selectedTab){
					$("<div>", {"class":"div-width-7 item"}).append(val['dispatchedDate']).appendTo($("#"+divID));
				}else if("cancelled"==selectedTab){
					$("<div>", {"class":"div-width-7 item"}).append(val['cancelledDate']).appendTo($("#"+divID));
				}else if("returned"==selectedTab){
					$("<div>", {"class":"div-width-7 item"}).append(val['returnedDate']).appendTo($("#"+divID));
				}else{
					if(val['dispatchedDate']!=""){
						$("<div>", {"class":"div-width-7 item"}).append(val['dispatchedDate']).appendTo($("#"+divID));
					}else{
						$("<div>", {"class":"div-width-7 item"}).append("&nbsp;").appendTo($("#"+divID));
					}
					
				}
				
				$("<div>", {"class":"div-width-8 item"}).append(val['releasedDate']).appendTo($("#"+divID));
				
			});
			loadAllJavascript();
			
			//----------------------------------------------------------------------------
			var startOrderCount = 1;
			var endOrderCount = totalOrdersCount;
			$(".ajax-pagination").show();
			if(totalPages>1){
				$("#previous-button").css('display', 'block');
				$("#next-button").css('display', 'block');
				startOrderCount = (pageSize * (pageNumber-1))+1;
				endOrderCount =  pageSize * pageNumber;
				if(pageNumber==1){
					$("#showingOrdesLabel").html("Showing "+startOrderCount+"-"+endOrderCount +" of "+totalOrdersCount+" orders");
					$("#previous-button").attr('disabled','disabled');
					$("#next-button").attr('disabled',false);
				}else if(pageNumber==totalPages){
					endOrderCount = totalOrdersCount;
					$("#showingOrdesLabel").html("Showing "+startOrderCount+"-"+endOrderCount+" of "+totalOrdersCount+" orders");
					$("#previous-button").attr('disabled',false);
					$("#next-button").attr('disabled','disabled');
				}else{
					$("#showingOrdesLabel").html("Showing "+startOrderCount+"-"+endOrderCount+" of "+totalOrdersCount+" orders");
					$("#previous-button").attr('disabled',false);
					$("#next-button").attr('disabled', false);
				}
			}else if(totalPages==1){
				if(totalPages>1){
					endOrderCount = pageSize;
				}
				$("#previous-button").css('display', 'none');
				$("#next-button").css('display', 'none');
				$("#showingOrdesLabel").html("Showing "+startOrderCount+"-"+endOrderCount+" of "+totalOrdersCount+" orders");
			}
		}else{
			
			if(searchType == "searchByCustName") {
				searchByOrderIdWithoutRelNumAndRedirectToOrderList(searchKey);
			} else {
				$("#showingOrdesLabel").html("");
				$(".ajax-pagination").hide();
				if("true" == filterResults){
					$("#tableHeading").removeClass("border-top-thick");
					$("#tableHeading").html("<div class='padding-left-20' id='errorMsg'></div>");
					$("#errorMsg").append("<div class='zero-results-found'>Your filters returned 0 results.</div>");
					$("#errorMsg").append("<div class='no-results-found-suggestions'>Suggestions:</div>");
					$("#errorMsg").append("<div class='no-results-found-suggestions-list'><ul><li>Try different filter options</li><li>Tesco may be unable to retrieve orders with the date range given.</li></ul></div>");
				}else if("" != searchKey){
					$("#filterByPage").hide();
					$("#sortByPage").hide();
					$("#actionButtons").hide();
					$("#tableHeading").removeClass("border-top-thick");
					$("#tableHeading").html("<div class='padding-left-20' id='errorMsg'></div>");
					$("#errorMsg").append("<div class='zero-results-found'>Your search returned 0 results.</div>");
					$("#errorMsg").append("<div class='no-results-found-suggestions'>Suggestions:</div>");
					$("#errorMsg").append("<div class='no-results-found-suggestions-list'><ul><li> Ensure that all words are spelt correctly </li><li>Ensure that your search is 3 or more characters long </li><li>Try different keywords</li><li>Ensure you have only used permitted special characters &lsquo;&#46;&#45;</li></ul></div>");
				}else{
					$("#tableHeading").removeClass("border-top-thick");
					$("#tableHeading").html("There are no orders");
				}
			}
		}
	} else{
		
		$("#pageStatus, #tableHeading, #tableBody").empty();
		$(".ajax-pagination").hide();
		$("#tableHeading").removeClass("border-top-thick");
		$('#optPrintOrders').parent().hide(); /*3417.09-10*/
		$('#optDownloadOrders').parent().hide(); /*3417.09-10*/
		//$('#actionButtons').addClass('col-xs-12').removeClass('col-xs-6'); /*3417.09-10*/
		$('#actionButtons').addClass('col-xs-10').removeClass('col-xs-4'); /*Def#888*/
		$("<span>", {"class":"portlet-msg-error","id":"errorMsg"}).append(errorMsg).appendTo($("#tableHeading"));
		
	}
	
}
function loadAllJavascript(){
	var checked = 0;
	var tabSelected =  $("#selectedTab").val();
	$("#selectAll").click(function() {
		var checkboxes = $("input[type='checkbox']");
		if ($(this).is(':checked')) {
			$("input:checkbox[name='selectedOrderNumbers'].unchecked").each(function(){
				var orderNo = $(this).val();
				var status = $("#status-div-"+orderNo).val();
				if(status != "Pending"){
					$("#"+orderNo).prop('checked', true);
				}
 			});
			if (tabSelected == "dispatched" || tabSelected == "cancelled" || tabSelected == "returned"){
				$("#print").attr('disabled',false);
			} else {
				displayHideActionButton(1);
			}
		} else {
			displayHideActionButton(0);
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
				};
			};
		}
		displayHideActionButton(checked);
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

function dispatchOrders() {
	var checked,i, len, inputs = document.frm1.getElementsByTagName("input"); 
	var arrayOfStatuses = new Array(),arrayOfCheckedOnes = new Array();
	checked = 0;
	len = inputs.length;
	for (i = 0; i < len; i++){
		if (inputs[i].type === "checkbox" && inputs[i].checked){
			if(inputs[i].value != ""){
				var selectAllID = inputs[i].value;
				if(selectAllID!="selectAll"){
					checked++;
				if((jQuery.inArray( inputs[i].value, arrayOfCheckedOnes) < 0)){
					arrayOfCheckedOnes.push(inputs[i].value);
					}
				}
			}
		}
	}
	var selectedIDsLength = arrayOfCheckedOnes.length;
	for(var j = 0 ; j < selectedIDsLength ; j++){
		var currentID = arrayOfCheckedOnes[j];
		var statusSelected = "#deliveryOption-div-"+currentID;
		var stat = $(statusSelected).html();
		var cnc = stat.toLowerCase();
		if(cnc === "click &amp; collect"){
		arrayOfStatuses.push(cnc);
		break;
		}
	}
	if(arrayOfStatuses.length != 0){
		$(".date-in-group-by-date").css('background-color','transparent');
		$(".hr-in-group-by-date").css('margin-left','100px');
		var showPopupOrNot =$("#isBulkCnCAvail").val();
		if("OFF" == showPopupOrNot){
			popUp();
		}else{	
			var url = "<%=quickOrderDispatchURL%>";
			document.getElementById("frm1").action = url;
			document.getElementById("frm1").submit();  
			return false;
			}
	}else{	
	var url = "<%=quickOrderDispatchURL%>";
	document.getElementById("frm1").action = url;
	document.getElementById("frm1").submit();  
	return false;
	}
}

function displayHideActionButton(checkedOrNot){
	if(checkedOrNot>0){
		var checked,i, len, inputs = document.frm1.getElementsByTagName("input"); 
		var arrayOfStatuses = new Array(),
			arrayOfCheckedOnes = new Array();
		checked = 0;
		len = inputs.length;
		
		for (i = 0; i < len; i++){
			if (inputs[i].type === "checkbox" && inputs[i].checked){
				if(inputs[i].value != ""){
					var selectAllID = inputs[i].value;
					if(selectAllID!="selectAll"){
					checked++;
					if((jQuery.inArray( inputs[i].value, arrayOfCheckedOnes) < 0)){
						arrayOfCheckedOnes.push(inputs[i].value);
						}
					}
				}
			}
		}
		var selectedIDsLength = arrayOfCheckedOnes.length;
		for(var j = 0 ; j < selectedIDsLength ; j++){
			var currentID = arrayOfCheckedOnes[j];
			var statusSelected = "status-div-" + currentID;
			var stat = $("#"+statusSelected).html();
			arrayOfStatuses.push(stat);
		}
		
		var m = arrayOfStatuses.length;
		var allStatusSame = false;
		if(selectedIDsLength>1){
			for(var k = 0 ; k < m-1 ; k++ ){
				if(arrayOfStatuses[k] == arrayOfStatuses[k+1]){
					allStatusSame = true;
				}else{
					allStatusSame = false;
					break;
				}
			} 
		}else if(selectedIDsLength==1){
			allStatusSame = true;
		}else{
			allStatusSame = false;
		}
		if(allStatusSame){
			$("#print").attr('disabled', false);
			var finalStatus = arrayOfStatuses[m-1].toUpperCase();
			if (arrayOfStatuses[m-1].toUpperCase() == "Open".toUpperCase()){
				$("#download").attr('disabled', false);
				$("#quickDispatch").attr('disabled', false);
			}else if (finalStatus != 'Dispatched'.toUpperCase() || finalStatus != 'Returned'.toUpperCase()) {
				$("#download").attr('disabled', 'disabled');
				$("#quickDispatch").attr('disabled', 'disabled');
			} 
		}else{
				$("#download").attr('disabled', 'disabled');
				$("#quickDispatch").attr('disabled', 'disabled');
			}

		$("#print").removeAttr("disabled");
		$("#download").removeAttr("disabled");
		$("#quickDispatch").removeAttr("disabled");
	}else{	
		$("#print").attr("disabled","disabled");
		$("#download").attr("disabled","disabled");
		$("#quickDispatch").attr("disabled","disabled");
 	}
}

$(document).ready(function(){
	
	var selectedTabInURL = getParameterByName("selectedTab");
	
	$('#releasedTab').click(function(){
		submitTabChange("released");
	});
	$('#dispatchedTab').click(function(){
		submitTabChange("dispatched");
	});
	$('#cancelledTab').click(function(){
		submitTabChange("cancelled");
	});
	$('#returnedTab').click(function(){
		submitTabChange("returned");
	});
	
	$('#searchBtn').click(function(){
		submitSearchForm();
	});
	
	$('#print').click(function(){
		openPrintPopup();
		this.blur();
	});
	
	$('#download').click(function(){
		downLoadOrders();
		this.blur();
	});
	
	$('#quickDispatch').click(function(){
		this.blur();
		return dispatchOrders();
	});
	
	$('#previous-button').click(function(){
		pageChange('previous');
		this.blur();
	});
	
	$('#next-button').click(function(){
		pageChange('next');
		this.blur();
	});
	
	changeUIonSelectedTab();
	$('#<portlet:namespace/>allDelOptions').click(function(){
		$('#<portlet:namespace/>expressCheckbox').prop('checked', true);
		$('#<portlet:namespace/>standardCheckbox').prop('checked', true);
		$('#<portlet:namespace/>sellerCheckbox').prop('checked', true);
		$('#<portlet:namespace/>standard2manCheckbox').prop('checked', true);
		$('#<portlet:namespace/>allDelOptions').hide();
		$('#<portlet:namespace/>noneDelOptions').show();
		handleApplyButton();
	});
	
	$('#<portlet:namespace/>noneDelOptions').click(function(){
		$('#<portlet:namespace/>expressCheckbox').prop('checked', false);
		$('#<portlet:namespace/>standardCheckbox').prop('checked', false);
		$('#<portlet:namespace/>sellerCheckbox').prop('checked', false);
		$('#<portlet:namespace/>standard2manCheckbox').prop('checked', false);
		$('#<portlet:namespace/>noneDelOptions').hide();
		$('#<portlet:namespace/>allDelOptions').show();
		handleApplyButton();
	});
	
	$('#<portlet:namespace/>allStatusOptions').click(function(){
        $('#<portlet:namespace/>releasedCheckbox').prop('checked', true);
        $('#<portlet:namespace/>dispatchedCheckbox').prop('checked', true);
        $('#<portlet:namespace/>cancelledCheckbox').prop('checked', true);
        $('#<portlet:namespace/>returnedCheckbox').prop('checked', true);
        $('#<portlet:namespace/>allStatusOptions').hide();
        $('#<portlet:namespace/>noneStatusOptions').show();
	});

	$('#<portlet:namespace/>noneStatusOptions').click(function(){
	       $('#<portlet:namespace/>releasedCheckbox').prop('checked', false);
	       $('#<portlet:namespace/>dispatchedCheckbox').prop('checked', false);
	       $('#<portlet:namespace/>cancelledCheckbox').prop('checked', false);
	       $('#<portlet:namespace/>returnedCheckbox').prop('checked', false);
	       $('#<portlet:namespace/>noneStatusOptions').hide();
	       $('#<portlet:namespace/>allStatusOptions').show();
	});
	displayOrderList();
	
	/** 3417.09/10 **/
	$('#optPrintOrders .selectOption').on('click', function(){
		$('#errPrintDownload').hide().text('');
		$('input#hidPrintOrders').attr('value',$(this).attr('value'));
		openPrintPopup();
	});
	$('#optDownloadOrders .selectOption').on('click', function(){
		$('#errPrintDownload').hide().text('');
		$('input#hidDownloadOrders').attr('value',$(this).attr('value'));
		downLoadOrders();
	});
	
	//Default value for Print/Download select boxes
	$('#optDownloadOrders').children('span.selected').html('Download options');
	$('#optPrintOrders').children('span.selected').html('Print options');
	$('#optPrintDownload input#hidPrintOrders, #optDownloadOrders input#hidDownloadOrders').attr('value','');

});

function openPrintPopup(){
	var checked, i, len,
		inputs = document.frm1.getElementsByTagName("input");
	var popupUrl = '<%=printPopupURL%>',
		finalPopupURL = null;
	var selOrderOpt = $("#hidPrintOrders").val();
	
	if(["released","released|acknowledged"].indexOf($("#selectedTab").val())>-1){
		if(selOrderOpt != ""){
			if(selOrderOpt == "Selecteditems"){
				var arrayOfCheckedOnes = new Array();
			
				/* 3417.09-10 */
				var checkOrders = $('input[type=checkbox][name=selectedOrderNumbers]:checked');
				if(checkOrders.length==0){
					$('#errPrintDownload').text('Please select orders to Print.').show();
					return false;
				}else{
					
					$.each( checkOrders, function(i,obj){
						arrayOfCheckedOnes.push(obj.value);
					});
					
					finalPopupURL = popupUrl + "?selectTab:"+$("#selectedTab").val()+"*selOrderOpt:" + selOrderOpt+"*orderId:" + arrayOfCheckedOnes.toString();
				}
			}else if(selOrderOpt == "AllFilter"){
				var selDelOpt = new Array();
				var frmDte = $("#fromDate").val();
				var toDte = $("#toDate").val();
	
				/* 3417.09-10 */
				var delivOptCheck = $('input[type=checkbox][name$=selectDeliveryOptCheckbox]:checked');
				if(delivOptCheck.length==0 && frmDte=='' && toDte==''){
					$('#errPrintDownload').text('Please select filter(s) to Print order(s).').show();
					if(!$('#result-filter').is(':visible')) $('#result-filter').slideDown(500).effect("highlight",{color:"#e5e499"},"800");
					return false;
				}else{
					
					$.each( delivOptCheck, function(i,obj){
						selDelOpt.push(obj.value);
					});
					$("#filterDeliveryOption").val(selDelOpt);
					finalPopupURL = popupUrl +"?selectTab:"+$("#selectedTab").val()+"*selOrderOpt:"+selOrderOpt+"*selDelOpt:"+selDelOpt+"*frmDte:"+frmDte.toString()+"*toDte:"+toDte.toString();
				}
				
			}else if(selOrderOpt == "AllCcOrders"){
				finalPopupURL = popupUrl + "?selectTab:"+$("#selectedTab").val()+"*selOrderOpt:"+selOrderOpt;
				
			}else if(selOrderOpt == "AllOpenorders"){
				finalPopupURL = popupUrl +"?selectTab:"+$("#selectedTab").val()+"*selOrderOpt:"+selOrderOpt;
				
			}
		}else{
			return false;
		}
	}else{
		var arrayOfCheckedOnes = new Array();
		checked = 0;
		len = inputs.length;
		for (i = 0; i < len; i++) {
			if (inputs[i].type === "checkbox" && inputs[i].checked){
				if(inputs[i].value != ""){
					checked++;
					arrayOfCheckedOnes.push(inputs[i].value);
				}
			}
		}
		popupUrl = '<%=printPopupURL%>';
		finalPopupURL = popupUrl + "?selectTab:"+$("#selectedTab").val()+"*selOrderOpt:"+"NA"+"*orderId:" + arrayOfCheckedOnes.toString();
		
	}
	document.getElementById("frm1").action = "";
	
	newwindow = window.open(finalPopupURL,'name','height=650,width=735,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
	newwindow.print();
	return false;
}
function downLoadOrders() {
	var checked, i, len, inputs = document.frm1.getElementsByTagName("input");
	var selOrderOpt = $("#hidDownloadOrders").val();
	var downloadUrl = '<%=orderDownloadURL%>';
	var downloadfinalURL = null;
	
	if(["released","released|acknowledged"].indexOf($("#selectedTab").val())>-1){
		if(selOrderOpt != ""){
			if(selOrderOpt == "Selecteditems"){
				var arrayOfCheckedOnes = new Array();
				
				/* 3417.09-10 */
				var checkOrders = $('input[type=checkbox][name=selectedOrderNumbers]:checked');
				if(checkOrders.length==0){
					$('#errPrintDownload').text('Please select orders to Download.').show();
					return false;
				}else{
					
					$.each( checkOrders, function(i,obj){
						arrayOfCheckedOnes.push(obj.value);
					});
					
					downloadfinalURL = downloadUrl + "?selectTab:"+$("#selectedTab").val()+"*selOrderOpt:" + selOrderOpt+"*orderId:" + arrayOfCheckedOnes.toString();
				}
	
			}else if(selOrderOpt == "AllFilter"){
				
				var selDelOpt = new Array();
				var frmDte = $("#fromDate").val();
				var toDte = $("#toDate").val();
				
				/* 3417.09-10 */
				var delivOptCheck = $('input[type=checkbox][name$=selectDeliveryOptCheckbox]:checked');
				if(delivOptCheck.length==0 && frmDte=='' && toDte==''){
					$('#errPrintDownload').text('Please select filter(s) to Download order(s).').show();
					if(!$('#result-filter').is(':visible')) $('#result-filter').slideDown(500).effect("highlight",{color:"#e5e499"},"800");
					return false;
				}else{
					
					$.each( delivOptCheck, function(i,obj){
						selDelOpt.push(obj.value);
					});
					$("#filterDeliveryOption").val(selDelOpt);
					downloadfinalURL = downloadUrl +"?selectTab:"+$("#selectedTab").val()+"*selOrderOpt:"+selOrderOpt+"*selDelOpt:"+selDelOpt.toString()+"*frmDte:"+frmDte.toString()+"*toDte:"+toDte.toString();
				}
				
			}else if(selOrderOpt == "AllCcOrders"){
				downloadfinalURL = downloadUrl + "?selectTab:"+$("#selectedTab").val()+"*selOrderOpt:"+selOrderOpt;
			}else if(selOrderOpt == "AllOpenorders"){
				downloadfinalURL = downloadUrl +"?selectTab:"+$("#selectedTab").val()+"*selOrderOpt:"+selOrderOpt;
			}
		}else{
			return false;
		}
	}else{
		var arrayOfCheckedOnes = new Array();
		checked = 0;
		len = inputs.length;
		for (i = 0; i < len; i++) {
			if (inputs[i].type === "checkbox" && inputs[i].checked){
				if(inputs[i].value != ""){
					checked++;
					arrayOfCheckedOnes.push(inputs[i].value);
				}
			}
		}
		downloadfinalURL = downloadUrl + "?selectTab:"+$("#selectedTab").val()+"orderId:" + arrayOfCheckedOnes.toString();
	}
	
	document.getElementById("frm1").action = downloadfinalURL;
	document.getElementById("frm1").submit(); 
}


function fnOpenNormalDialog(event){
	var size = ${basketSize};
	if (size>0){
		if (event.preventDefault){
			event.preventDefault(); 
		}else {
			event.returnValue = false; 
		} 
		$("#dialog-confirm").html("This update is final. Do you confirm (Confirm/Cancel?)");
		// Define the Dialog and its properties
		$("#dialog-confirm").dialog({
			resizable: false,
			modal: true,
			title: "Confirm final submit of orders",
			height: 250,
			width: 400,
			buttons:{
				"Cancel": function(){
					$(this).dialog('close');
				},
				"Confirm": function() {
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

var closeDropdown = function(e){ /*Def#1028*/
    /* var $visibleDropdown = $('.selectBoxBind .selectOptions:visible');
    if($visibleDropdown.length == 0){
    	$visibleDropdown = $('.selectBox .selectOptions:visible');
    } */
    var $visibleDropdown = '';
    $visibleDropdown = $('.selectBoxBind .selectOptions:visible').length==0?
    		$('.selectBox .selectOptions:visible'):
    			$('.selectBoxBind .selectOptions:visible');
    
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

$(document).mouseup( function(e){
	closeDropdown(e);
});

function popUp() {
	$("#dialog-confirm").html("Please note that you can only generate labels  for click and collect orders in Merchant Hub,  via the individual order details page. If you wish to continue with the Quick Dispatch, including click and collect orders, you must have already generated and printed the labels via Net Despatch.");
	$("#dialog-confirm").dialog({
		resizable: false,
		modal: true,
		title: "Click and Collect Labels",
		height: 300,
		width : 550,
		buttons: {
			" Cancel ": function () {
				$(this).dialog('close');
				$(".overlay").css("display","block");
				$(".dialog-confirm").css("display","block");
				$(".date-in-group-by-date").css('background-color','white');
				$(".hr-in-group-by-date").css('margin-left','10px');
			},
			" Continue ": function () {
				$(".overlay").css("display","block");
				$(".dialog-confirm").css("display","block");
				var url = "<%=quickOrderDispatchURL%>";
				document.getElementById("frm1").action = url;
				document.getElementById("frm1").submit();  
				return false;
			}
		}
	});
	$(".ui-dialog-titlebar-close").css("display","none");
}
</script>


<%
	String selectedTab = request.getAttribute("selectedTab") !=null ? (String) request.getAttribute("selectedTab") : "released";
	String className = "non-selected";
%>

<div id="dialog-confirm"></div>
<liferay-ui:success key="sucess-in-confirm-orders" message="ORDMGT-014"></liferay-ui:success>
<input type="hidden" id="pageNumber" name="pageNumber" value="1"/>

<div class="innerContainer">
	<div class="header-row relative">
		<h6>ORDER MANAGEMENT</h6>
		<div class="padding-left-10 col-xs-12 customTabs" id="customTabs">
			<input type="hidden" id="selectedTab" name="selectedTab" value="<%=selectedTab%>"/>
			<input type="hidden" id="filterResults" name="filterResults" value="false"/>
			<input type="hidden" id="filterDeliveryOption" name="filterDeliveryOption" value=""/>
			<input type="hidden" id="filterStatus" name="filterStatus" value=""/>
			<input type="hidden" id="filterFromDate" name="filterFromDate" value=""/>
			<input type="hidden" id="filterToDate" name="filterToDate" value=""/>
			<input type="hidden" id="isBulkCnCAvail" name="isBulkCnCAvail" value="${isBulkCnCAvail}"/>
			<input type="hidden" id="sortByDate" name="sortByDate" value=""/>
			<input type="hidden" id="sortByDateIsApplied" name="sortByDateIsApplied" value="false"/>
			<h2 id="releasedTab" class='col-xs-2  tabHeading'>Open</h2>
			<h2 id="dispatchedTab" class='col-xs-3  tabHeading'>Dispatched</h2>
			<h2 id="cancelledTab" class='col-xs-3  tabHeading'>Cancelled</h2>
			<h2 id="returnedTab" class='col-xs-3  tabHeading'>Returned</h2>
		</div>
			
		<div class="input-group search-box margin-top-20 padding-left-20">
			<div class="input-group-btn">
				<button name="searchBtn" id = "searchBtn" type="button" value="Search" class="btn btn-default btn-search">
					<span class="glyphicon glyphicon-search margin-top-5"></span>
				</button>
			</div>
		
			<input name="searchField" id="searchOrderText" placeholder="Search by order ID, customer name" maxlength="256" type="text" class="form-control search-input"></input>
		    <input type="hidden" name="automComplete" id="automComplete" />
		</div>
	</div>
	<div class="row margin-top-10 margin-left-10">
		<div class="col-xs-3" style="display:none;">
			<div class="selectBoxBind" id="optPrintOrders">
				<input name="" id="hidPrintOrders" type="hidden" value="" />
	            <span class='selected' title="Select option for Print"></span>
	            <span class='selectArrow'>&#9660;</span>
	            <div class="selectOptions">
	            	<span class="selectOption" value="">Print options</span>
					<span class="selectOption" value="AllFilter">All in this current filter</span>
					<span class="selectOption" value="Selecteditems">Selected items</span>
					<span class="selectOption" value="AllOpenorders">All open orders</span>
	                <span class="selectOption" value="AllCcOrders">All C&amp;C open orders</span>
	                
	             </div>
			</div>
		</div>
		<div class="col-xs-3" style="display:none;">
			<div class="selectBoxBind" id="optDownloadOrders">
				<input name="" id="hidDownloadOrders" type="hidden" value="" />
	            <span class='selected' title="Select option for Download"></span>
	            <span class='selectArrow'>&#9660;</span>
	            <div class="selectOptions">
	            	<span class="selectOption" value="">Download options</span>
					<span class="selectOption" value="AllFilter">All in this filter</span>
					<span class="selectOption" value="Selecteditems">Selected items</span>
					<span class="selectOption" value="AllOpenorders">All open orders</span>
	                <span class="selectOption" value="AllCcOrders">All C&amp;C open orders (CSV)</span>
	             </div>
			</div>
		</div>
		<!--<div class="btn-group col-xs-12" id="actionButtons">-->
		<div class="btn-group col-xs-10" id="actionButtons">
			<button type="button" class="btn secondary-CTA" id="print">Print</button> 
			<button type="button" class="btn secondary-CTA" id="download">Download</button>
			<button type="button" class="btn secondary-CTA" id="quickDispatch" disabled="disabled">Quick Dispatch
				<span id="quickDispatchSpan"></span>
			</button>
		</div>
		<!--<div class="col-xs-4" id="sortByPage"> Def#888-->
		<div class="col-xs-6" id="sortByPage">
			<%@ include file="/WEB-INF/views/sortBy.jsp"%>
		</div>
		<div class="col-xs-8 portlet-msg-error margin-left-10" style="display:none;" id="errPrintDownload">
			MHUB encountered an exception while trying to Print/Download orders.
		</div>
		<div class="col-xs-16 padding-left-10" id="filterByPage">
			<label id="showingOrdesLabel"></label>
			<%@ include file="/WEB-INF/views/filterBy.jsp"%>
		</div>
	</div>
</div>

<form method="post" name="frm1" id="frm1">
<div class="div-width-78 margin-left-30">
	<div class="thead row margin-bottom_10 border-top-thick" id="tableHeading">
	</div>
	<div class="table-body margin-right_10" id="tableBody">
	</div>
	<div class="ajax-pagination" style="text-align:right; padding-top:25px; float:right;">
		<h4 id="pageStatus"  class="col-xs-3 tabHeading" style="width:150px;"></h4>
		<input type="button" id="previous-button" class="col-xs-2 tabHeading" value="&lt;" disabled />
		<input type="button" id="next-button" class="col-xs-2 tabHeading" style="margin-left:10px;" value="&gt;" disabled />
	</div>
</div>
<%@ include file="/WEB-INF/views/orderBasket.jsp"%>
</form>

<style>
.site-breadcrumbs{ 
margin-left: 10px;
}
input[type="checkbox"]{
margin:0px;
}                       
.search-box .glyphicon {
left: -3px;
top: 4px;
}
.div-width-1 > input[type="checkbox"]{
margin-top:-7px;
} 
#wrapper{
width:100%;
}
.search-box .search-input {
max-width: 100%;
}
.padding-right-10{
padding-right:10px;
}
h3{
color:#000;
font-weight:bold;
font-size:1em;
}
.col-xs-4.right-section{
width:18.75% !important;
float:right;
}
label {
font-weight: normal;
}
.innerContainer {
margin-bottom: 20!important;
}
.form-back{
background-color:#e5e4e2;
}
label.aui-choice-label {
width: 75%;
}
.header-row h6 {
padding-left: 20px;
}
.padding-right-20{
padding-right:20px;
}
form {
width: 100% !important;
}
.customTabs{
	color: #868686;
}
.orderIdInList{
	text-decoration: underline;
	cursor: pointer;
}
.div-width-1{
width:3%;
float:left;
}
.div-width-2{
width:10%;
float:left;
}
.div-width-3{
width:7%;
float:left;
}
.div-width-4{
width:20%;
float:left;
padding-right:10px;
}
.div-width-5{
width:15%;
float:left;
}
.div-width-6{
width:17%;
float:left;
}
.div-width-7{
width:14%;
float:left;
}
.div-width-8{
width:14%;
float:left;
}
.div-width-78{
width:78%;
float:left;
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
.date-in-group-by-date{
z-index: 5000;
background-color: white;
width: auto;
position: relative;
display: inline-block;
padding-right: 10px;
font-size: 1.5em;
color:#797979;
}
.hr-in-group-by-date{
position: relative;
top: -15px;
z-index: 0;
margin-left: 10px;
margin-top: 0px;
margin-bottom: 0px;
border-color: #bfbfbf !important;

}
.group-by-date{
margin-top:20px;
}
.group-by-date-wrapper{
height:30px;
margin-left:-25px;
margin-top:25px;
margin-bottom:10px;
}
.margin-bottom_10{
margin-bottom:-10px;
}
.div-width-1 > input[type="checkbox"]{
outline:0;
}
.ajax-pagination{
width:245px;
}
#quickDispatch{
padding-left:21px;
}
.border-top-thick{
border-top:#bfbfbf 4px solid;
}
.margin-right_10{
margin-right:-10px;
}
.breakword{
word-wrap: break-word;
}
.padding-right-5{
padding-right: 5px;
}
/*3417.09-10*/
#optPrintOrders .selected, #optDownloadOrders .selected{
	padding: 3px 0;
	height: 36px;
	width: 165px;
	border-top-left-radius:5px;
	border-bottom-left-radius:5px;
}
#optDownloadOrders .selected{
	/* border-radius: 0; */
}
#optPrintOrders .selectArrow{
	/* border-right: 0; */
}
#optPrintOrders .selectArrow, #optDownloadOrders .selectArrow{
	height: 36px;
	font-size: 12px;
	padding-top: 2px;
	/* border-radius: 0; */
}
#optPrintOrders .selectOptions,
#optDownloadOrders .selectOptions{
	width: 195px;
	top: 33px;
	/* left: 10px; */
}
</style>