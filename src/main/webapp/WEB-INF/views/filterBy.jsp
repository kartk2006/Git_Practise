<%@page import="java.util.List"%>
<%@page import="com.tesco.mhub.order.constants.OrderConstants"%>
<%@page import="com.tesco.mhub.service.SystemMHubMappingLocalServiceUtil"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui"  prefix="aui" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@page import="java.util.Calendar"%>
<%
List<String> sysValueOfExpress =  SystemMHubMappingLocalServiceUtil.getSystemValue(OrderConstants.SYSTEM_NAME,OrderConstants.SYSTEM_ATTRIBUTE, "Express");
List<String> sysValueOfStandard =  SystemMHubMappingLocalServiceUtil.getSystemValue(OrderConstants.SYSTEM_NAME,OrderConstants.SYSTEM_ATTRIBUTE, "Standard");
List<String> sysValueOfClickCollect =  SystemMHubMappingLocalServiceUtil.getSystemValue(OrderConstants.SYSTEM_NAME,OrderConstants.SYSTEM_ATTRIBUTE, "Click & Collect");
List<String> sysValueOfStandard2Man =  SystemMHubMappingLocalServiceUtil.getSystemValue(OrderConstants.SYSTEM_NAME,OrderConstants.SYSTEM_ATTRIBUTE, "Standard 2 Man");


Calendar cal = Calendar.getInstance();
int currMonth = cal.get(Calendar.MONTH);
int currYear = cal.get(Calendar.YEAR);
int currDay = cal.get(Calendar.DAY_OF_MONTH);
int currHours = cal.get(Calendar.HOUR);
int currMinutes = cal.get(Calendar.MINUTE);
int currSeconds = cal.get(Calendar.SECOND);
%>




<script type="text/javascript">
var maxDate = new Date(<%=currYear%>,<%=currMonth %>,<%=currDay%>,<%=currHours%>,<%=currMinutes%>,<%=currSeconds%>,0);
var minDate = new Date(<%=currYear%>,<%=currMonth %>,<%=currDay%>,<%=currHours%>,<%=currMinutes%>,<%=currSeconds%>,0);
minDate = minDate.setDate(maxDate.getDate()-60);

function setTodayDate(){
	var today = new Date(<%=currYear%>,<%=currMonth %>,<%=currDay%>,<%=currHours%>,<%=currMinutes%>,<%=currSeconds%>,0);
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0
	if(dd >= 1 && dd <= 9){
		dd = "0" + dd; //append 0 to first 9 months. 1-9 shd dispaly as 01-09.
	}
	if(mm >= 1 && mm <= 9){
		mm = "0" + mm; //append 0 to first 9 months. 1-9 shd dispaly as 01-09.
	}
	var yyyy = today.getFullYear();
	var todayDate =  dd+'-'+mm+'-'+yyyy;
	$("#toDate").val(todayDate);
}


function applyFilter(){
	var checked, i, len;
	
	var deliveryOptionCheck = document.getElementsByName("<portlet:namespace/>selectDeliveryOptCheckbox");
	var selectDeliveryOpt = new Array();
	checked = 0;
	len = deliveryOptionCheck.length;
	for (i = 0; i < len; i++) {
		if (deliveryOptionCheck[i].type === "checkbox" && deliveryOptionCheck[i].checked){
			if(deliveryOptionCheck[i].value != ""){
				checked++;
				selectDeliveryOpt.push(deliveryOptionCheck[i].value);
			}
		}
	}
	
	var statusOptCheck = document.getElementsByName("<portlet:namespace/>selectStatusOptCheckbox");
	var statusOpt = new Array();
	checked = 0;
	len = statusOptCheck.length;
	for (i = 0; i < len; i++) {
		if (statusOptCheck[i].type === "checkbox" && statusOptCheck[i].checked){
			if(statusOptCheck[i].value != ""){
				checked++;
				statusOpt.push(statusOptCheck[i].value);
			}
		}
	}

	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	
	$("#filterResults").val("true");
	$("#filterDeliveryOption").val(selectDeliveryOpt);
	$("#filterStatus").val(statusOpt);
	$("#filterFromDate").val(fromDate);
	$("#filterToDate").val(toDate);
	$("#<portlet:namespace/>clearAllFilters").show();
	$("#pageNumber").val("1");
	pushDataToURL();
	displayOrderList();
}

function clearAllFilters(){
	
	 $('#datetimepicker1').data("DateTimePicker").setMinDate(minDate);
	 $('#datetimepicker1').data("DateTimePicker").setMaxDate(maxDate);
	 $('#datetimepicker4').data("DateTimePicker").setMinDate(minDate);
     $('#datetimepicker4').data("DateTimePicker").setMaxDate(maxDate);
     $('#datetimepicker4').val("");
     $('#datetimepicker1').val("");
	
	$('#<portlet:namespace/>clearAllFilters').hide();
	$('#<portlet:namespace/>expressCheckbox').prop('checked', false);
	$('#<portlet:namespace/>standardCheckbox').prop('checked', false);
	$('#<portlet:namespace/>sellerCheckbox').prop('checked', false);
	$('#<portlet:namespace/>standard2manCheckbox').prop('checked', false);
	$('#<portlet:namespace/>noneDelOptions').hide();
	$('#<portlet:namespace/>allDelOptions').show();
	
	$('#<portlet:namespace/>releasedCheckbox').prop('checked', false);
    $('#<portlet:namespace/>dispatchedCheckbox').prop('checked', false);
    $('#<portlet:namespace/>cancelledCheckbox').prop('checked', false);
    $('#<portlet:namespace/>returnedCheckbox').prop('checked', false);
    $('#<portlet:namespace/>noneStatusOptions').hide();
    $('#<portlet:namespace/>allStatusOptions').show();
    
    $("#filterDeliveryOption").val("");
	$("#filterStatus").val("");
	$("#fromDate").val("");
	$("#toDate").val("");
	
	$("#fromDateInitiated").val("false");
	$("#toDateInitiated").val("false");
	var filterResults = $("#filterResults").val();
	if("true"==filterResults){
		$("#filterResults").val("false");
		displayOrderList();
	}
}


function hideFilterOption(){
	$('#<portlet:namespace/>clearAllFilters').hide();
	$('#result-filter').hide();
	clearAllFilters();
}

$(document).ready(function(){
	
	$('#<portlet:namespace/>filterResults').click(function(){
		$('#result-filter').toggle(); /*Def#885*/
	});
	
	$('#<portlet:namespace/>clearAllFilters').click(function(){
		clearAllFilters();
	});
	
	$('#todayDate').click(function(){
		setTodayDate();
	});
	
	$('#cancelFilter').click(function(){
		hideFilterOption();
	});
	
	$('#applyFilter').click(function(){
		applyFilter();
	});
	
	
	$('#datetimepicker4,#datetimepicker1').datetimepicker({
		pickTime: false, 
		minDate: minDate,
		maxDate: maxDate,
	});
	$("#datetimepicker4").on("dp.hide",function (e) {
        $('#datetimepicker1').data("DateTimePicker").setMinDate(e.date);
    });
	
	$("#datetimepicker1").on("dp.hide",function (e) {
        $('#datetimepicker4').data("DateTimePicker").setMaxDate(e.date);
	});
	
	var today = new Date(<%=currYear%>,<%=currMonth %>,<%=currDay%>,<%=currHours%>,<%=currMinutes%>,<%=currSeconds%>,0);
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0
	if(dd >= 1 && dd <= 9){
		dd = "0" + dd; //append 0 to first 9 months. 1-9 shd dispaly as 01-09.
	}
	if(mm >= 1 && mm <= 9){
		mm = "0" + mm; //append 0 to first 9 months. 1-9 shd dispaly as 01-09.
	}
	var yyyy = today.getFullYear();
	var todayDate =  dd+'-'+mm+'-'+yyyy;
	
	$("#datetimepicker4").on("dp.show",function (e) {
		var maxDateSet = $('#datetimepicker1').data("DateTimePicker").getDate();
		var minDateSet = $('#datetimepicker4').data("DateTimePicker").getDate();
		var fromDateInitiated = $("#fromDateInitiated").val();
		var toDateInitiated = $("#toDateInitiated").val();
		$("#fromDateInitiated").val("true");
		if(minDateSet>maxDateSet && "true" == toDateInitiated && "false" == fromDateInitiated){
			$("#datetimepicker4").data("DateTimePicker").setDate(maxDateSet);
			$('#datetimepicker4').data("DateTimePicker").show();
		}else if("false" == fromDateInitiated){
			$("#datetimepicker4").data("DateTimePicker").setDate(todayDate);
			$('#datetimepicker4').data("DateTimePicker").show();
		}
	});
	
	$("#datetimepicker1").on("dp.show",function (e) {
		var toDateInitiated = $("#toDateInitiated").val();
		$("#toDateInitiated").val("true");
		if("false" == toDateInitiated){
			$("#datetimepicker1").data("DateTimePicker").setDate(todayDate);
			$('#datetimepicker1').data("DateTimePicker").show();
		}
	});
	
});

function changeFilterUIOnBackAction(){
	var filterDeliveryOption = getParameterByName("filterDeliveryOption");
	var filterStatus = getParameterByName("filterStatus");
	var filterFromDate = getParameterByName("filterFromDate");
	var filterToDate = getParameterByName("filterToDate");
	
	$("#fromDate").val(filterFromDate);
	$("#toDate").val(filterToDate);
	
	$("input[name=<portlet:namespace/>selectDeliveryOptCheckbox]").each(function(){
		if(filterDeliveryOption.indexOf($(this).val()) > -1){
			$(this).prop('checked', true);
		}else{
			$(this).prop('checked', false);
		}
		
	});
	$("input[name=<portlet:namespace/>selectStatusOptCheckbox]").each(function(){
		if(filterStatus.indexOf($(this).val()) > -1){
			$(this).prop('checked', true);
		}else{
			$(this).prop('checked', false);
		}
	});
}

/*Def#885*/
function toggleAllNoneDelOptions(){
	if( $('[id$=expressCheckbox]').prop('checked')
		&& $('[id$=standardCheckbox]').prop('checked')
		&& $('[id$=sellerCheckbox]').prop('checked')
		&& $('[id$=standard2manCheckbox]').prop('checked') ){
		
		$('[id$=noneDelOptions]').show();
		$('[id$=allDelOptions]').hide();
	}else{
		if( !$('[id$=expressCheckbox]').prop('checked')
			|| !$('[id$=standardCheckbox]').prop('checked')
			|| !$('[id$=sellerCheckbox]').prop('checked')
			|| !$('[id$=standard2manCheckbox]').prop('checked') ){
			
			$('[id$=noneDelOptions]').hide();
			$('[id$=allDelOptions]').show();
		}
	}
}
</script>


<aui:script use="aui-base">

/*AUI().use('aui-viewport');*/

function handleApplyButton() {
	var isApplyButtonEnable = false;
	A.all('input[name=<portlet:namespace/>selectDeliveryOptCheckbox]').each(function(object) {
		if(object.get('checked')==true){
			isApplyButtonEnable = true;
		}
	});
	A.all('input[name=<portlet:namespace/>selectStatusOptCheckbox]').each(function(object) {
		if(object.get('checked')==true){
			isApplyButtonEnable = true;
		}
	});
	if(A.one('#fromDate').val()!='' && A.one('#toDate').val()==''){
		if(validateDate(A.one('#fromDate').val())){
			isApplyButtonEnable = true;
		}else{
			isApplyButtonEnable = false;
		}
	}else if(A.one('#toDate').val()!='' && A.one('#fromDate').val()==''){
		if(validateDate(A.one('#toDate').val())){
			isApplyButtonEnable = true;
		}else{
			isApplyButtonEnable = false;
		}
	}else if(A.one('#toDate').val()!='' && A.one('#fromDate').val()!=''){
		if(validateDate(A.one('#toDate').val()) && validateDate(A.one('#fromDate').val()) ){
			isApplyButtonEnable = true;
		}else{
			isApplyButtonEnable = false;
		}
	}
	var button = A.one('#applyFilter');
	var span = button.ancestor('.aui-button');
	if(isApplyButtonEnable){
		$('a[id$=clearAllFilters]').show(); /*Def#885*/
		//A.one("#<portlet:namespace/>clearAllFilters").show();
		//A.one("#<portlet:namespace/>clearAllFilters").set('visible',true);
		button.set('disabled',false);
		span.removeClass('aui-button-disabled');
	}else{
		$('a[id$=clearAllFilters]').hide(); /*Def#885*/
		button.set('disabled',true);
		span.addClass('aui-button-disabled');
	}
	
}
function validateDate(testdate) {
    var date_regex = /^(0[1-9]|1\d|2\d|3[01])\-(0[1-9]|1[0-2])\-(19|20)\d{2}$/ ;
    return date_regex.test(testdate);
}
A.all('input[name=<portlet:namespace/>selectDeliveryOptCheckbox]').on('click',function(object) {
	handleApplyButton();
	toggleAllNoneDelOptions(); /*Def#885*/
});

A.all('input[name=<portlet:namespace/>selectStatusOptCheckbox]').on('click',function(object) {
	handleApplyButton();
});
A.one('#fromDate').on(['blur'],function(object){
	handleApplyButton();
});
A.one('#toDate').on(['blur'],function(object){
	handleApplyButton();
});

A.one('#fromDateIcon').on(['click','mouseout'],function(object){
	handleApplyButton();
});
A.one('#toDateIcon').on(['click','mouseout'],function(object){
	handleApplyButton();
});

A.one('#todayDate').on('click',function(object){
	handleApplyButton();
});

</aui:script>



<aui:a cssClass="padding-left-20" href="javascript:void(0);" id="filterResults">Filter your results</aui:a>
<aui:a cssClass="padding-left-20" href="javascript:void(0);" id="clearAllFilters" style="display:none;">Clear Filters</aui:a>
<input type="hidden" id="fromDateInitiated" name="fromDateInitiated" value="false"/>
<input type="hidden" id="toDateInitiated" name="toDateInitiated" value="false"/>

<div class="col-xs-16 form-back display-none" id="result-filter">
	<div class="col-xs-4">
		<h5 class="filterTypeHead">Delivery Option (<aui:a href="javascript:void(0);" id="allDelOptions">all</aui:a><aui:a href="javascript:void(0);" id="noneDelOptions" style="display:none" >none</aui:a>)</h5>
		<div>
			<aui:input type="checkbox" value="<%=sysValueOfExpress %>" name="selectDeliveryOpt" id="express" cssClass="delOpt filterOptions" label="Express"></aui:input>
			<aui:input type="checkbox" value="<%=sysValueOfStandard %>" name="selectDeliveryOpt" id="standard" cssClass="delOpt filterOptions" label="Standard"></aui:input>
			<aui:input type="checkbox" value="<%=sysValueOfClickCollect %>" name="selectDeliveryOpt" id="seller" cssClass="delOpt filterOptions" label="Click and Collect"></aui:input>
			<aui:input type="checkbox" value="<%=sysValueOfStandard2Man %>" name="selectDeliveryOpt" id="standard2man" cssClass="delOpt filterOptions" label="Standard 2 Man"></aui:input>
		</div>
	</div>
	<div class="col-xs-4" id="statusFilterDiv">
         <h5 class="filterTypeHead">Status (<aui:a href="javascript:void(0);" id="allStatusOptions">all</aui:a><aui:a href="javascript:void(0);" id="noneStatusOptions" style="display:none" >none</aui:a>)</h5>
         <div>
                <aui:input type="checkbox" value="released" name="selectStatusOpt" id="released"  cssClass="statusOpt filterOptions" label="Open"></aui:input>
                <aui:input type="checkbox" value="dispatched" name="selectStatusOpt" id="dispatched" cssClass="statusOpt filterOptions" label="Dispatched"></aui:input>
                <aui:input type="checkbox" value="cancelled" name="selectStatusOpt" id="cancelled" cssClass="statusOpt filterOptions" label="Cancelled"></aui:input>
                <aui:input type="checkbox" value="returned" name="selectStatusOpt" id="returned" cssClass="statusOpt filterOptions" label="Returned"></aui:input>
         </div>
    </div>
	
	<div class="col-xs-4">
		<h5 class="filterTypeHead" id="dateRangeBy">Release Date Range</h5>
		<div class="col-xs-16 sortDate pull-left">
                                
			<div  class="col-xs-16 input-append padding-right-0 padding-left-0 margin-left-0 margin-right-0 pull-left" data-date-format="DD/MM/YYYY">
				<span class="date-width lbl fltL  padding-right-0 padding-left-0 margin-left-0 margin-right-0">From Date</span>
				
				<div  CLASS='INPUT-GROUP DATE' id="datetimepicker4" data-date-format="DD-MM-YYYY">
				<input type="text" name="fromDate" id="fromDate" class="form-control date-input fltL" placeholder="dd-mm-yyyy" readonly="readonly"></input>
				<span class="add-on cal-span" id="fromDateIcon">
				<i data-time-icon="icon-time" data-date-icon="icon-calendar" class="btn primary-CTA glyphicon glyphicon-calendar margin-top_15">
				</i>
				</span>
				</div>
			</div>
			<div  class="col-xs-16 margin-top-20 input-append padding-right-0 padding-left-0 margin-left-0 margin-right-0 pull-left clr-both" data-date-format="DD/MM/YYYY">
				<span class="date-width lbl fltL  padding-right-0 padding-left-0 margin-left-0 margin-right-0">To Date (<a id="todayDate" href="javascript:void(0);">today</a>)</span>
				
				<div  CLASS='INPUT-GROUP DATE' id="datetimepicker1" data-date-format="DD-MM-YYYY">
				<input type="text" name="toDate" id="toDate" class="form-control date-input fltL" placeholder="dd-mm-yyyy" readonly="readonly"></input>
				<span class="add-on cal-span" id="toDateIcon">
				<i data-time-icon="icon-time" data-date-icon="icon-calendar" class="btn primary-CTA glyphicon glyphicon-calendar margin-top_15">
				</i>
				</span>
				</div>
			</div>
		
		</div>
	</div>
	<div class="col-xs-16 form-row">
		<div class="col-xs-14">
			
		</div>
		<div class="col-xs-1">
			<aui:button type="button" value="Cancel" id="cancelFilter" cssClass="btn pull-right">
			</aui:button>
		</div>
		<div class="col-xs-1"> <!-- disabled="true" -->
			<aui:button type="button" id="applyFilter" value="Apply" cssClass="btn primary-btn pull-right" disabled="true"></aui:button>
		</div>
	</div>
</div>
<style>
.cal-span {
top: 4px;
position: relative;
left: 10px;
}
.date-width{
width:115px;
}
.aui-button-disabled input[type], .aui-button-disabled input[type]:hover{
	background-image: -webkit-gradient(linear,50% 0,50% 100%,color-stop(0%,#4777bf),color-stop(100%,#3d66a4));
	background-image: -webkit-linear-gradient(#4777bf,#3d66a4);
	background-image: -moz-linear-gradient(#4777bf,#3d66a4);
	background-image: -o-linear-gradient(#4777bf,#3d66a4);
	background-image: linear-gradient(#4777bf,#3d66a4);
	opacity:0.5;
	cursor:default;
}
span.aui-button-disabled span input[type="button"]:hover{
background-image: -webkit-gradient(linear,50% 0,50% 100%,color-stop(0%,#4777bf),color-stop(100%,#3d66a4));
	background-image: -webkit-linear-gradient(#4777bf,#3d66a4);
	background-image: -moz-linear-gradient(#4777bf,#3d66a4);
	background-image: -o-linear-gradient(#4777bf,#3d66a4);
	background-image: linear-gradient(#4777bf,#3d66a4);
	opacity:0.5;
}
.aui-button-disabled.btn{
cursor:default;
}
.filterTypeHead{
font-weight: bold;
}
.sortDate .form-control[disabled], .sortDate .form-control[readonly], .sortDate fieldset[disabled] .form-control {
	cursor: pointer;
}
</style>