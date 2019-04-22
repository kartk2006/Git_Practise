<%@ taglib uri="http://alloy.liferay.com/tld/aui"  prefix="aui" %>


<script type="text/javascript">
function displaySortByOptions(){
	var selectedTabSortUI = $("#selectedTab").val();
	
	if(selectedTabSortUI == "released|acknowledged") {
		selectedTabSortUI = "released";
	}
	
	if("released" == selectedTabSortUI){
		$("#sortReleasedTab").show();
		
		$("#sortDispatchedTab").hide();
		$("#sortCancelledTab").hide();
		$("#sortReturnedTab").hide();
		$("#sortSearchResult").hide();
		$("#statusFilterDiv").hide();

	}else if("dispatched" == selectedTabSortUI){
		$("#sortDispatchedTab").show();
		$("#sortReleasedTab").hide();
		$("#sortCancelledTab").hide();
		$("#sortReturnedTab").hide();
		$("#sortSearchResult").hide();
		$("#statusFilterDiv").hide();
		
	}else if("cancelled" == selectedTabSortUI){
		$("#sortCancelledTab").show();
		$("#sortReleasedTab").hide();
		$("#sortDispatchedTab").hide();
		$("#sortReturnedTab").hide();
		$("#sortSearchResult").hide();
		$("#statusFilterDiv").hide();
		
	}else if("returned" == selectedTabSortUI){
		$("#sortReturnedTab").show();
		$("#sortReleasedTab").hide();
		$("#sortDispatchedTab").hide();
		$("#sortCancelledTab").hide();
		$("#sortSearchResult").hide();
		$("#statusFilterDiv").hide();
	}else{
		$("#sortSearchResult").show();
		$("#sortReleasedTab").hide();
		$("#sortDispatchedTab").hide();
		$("#sortCancelledTab").hide();
		$("#sortReturnedTab").hide();
		$("#statusFilterDiv").show();
		
	}
}

function resetSortByOption(){
	
	$('#sortReleasedTab').children('span.selected').html('Release Date (Oldest First)');
	$('#sortReleasedTab').attr('value', 'ReleasedDate-asc');
	
	$('#sortDispatchedTab').children('span.selected').html('Dispatch date (Newest first)');
	$('#sortDispatchedTab').attr('value', 'DispatchedDate-desc');
	
	$('#sortCancelledTab').children('span.selected').html('Cancel date (Newest first)');
	$('#sortCancelledTab').attr('value', 'CancelledDate-desc');
	
	$('#sortReturnedTab').children('span.selected').html('Return date (Newest first)');
	$('#sortReturnedTab').attr('value', 'ReturnedDate-desc');
	
	$('#sortSearchResult').children('span.selected').html('Release Date (Oldest First)');
	$('#sortSearchResult').attr('value', 'ReleasedDate-asc');
}

function sortListByFinalCall(){
	displayOrderList();
	$("#sortByDateIsApplied").val("false");
}

function sortListBy(selectBox){
	$("#sortByDate").val(selectBox);
	$("#sortByDateIsApplied").val("true");
	pushDataToURL();
	sortListByFinalCall();
}
 
function enableSelectBoxes(){
	
	$('div.selectBox').each(function(){
		$(this).children('span.selected').html($(this).children('div.selectOptions').children('span.selectOption:first').html());
		$(this).attr('value',$(this).children('div.selectOptions').children('span.selectOption:first').attr('value'));
		
		$(this).children('span.selected,span.selectArrow').click(function(){
			if($(this).parent().children('div.selectOptions').css('display') == 'none'){
				$(this).parent().children('div.selectOptions').css('display','block');
			}else{
				$(this).parent().children('div.selectOptions').css('display','none');
			}
		});
		
		$(this).find('span.selectOption').click(function(){
			$(this).parent().css('display','none');
			$(this).closest('div.selectBox').attr('value',$(this).attr('value'));
			$(this).parent().siblings('span.selected').html($(this).html());
		});
	});
	
	$('div.selectBoxBind').each(function(){
		
		$(this).children('span.selected,span.selectArrow').click(function(){
		
			if($(this).parent().children('div.selectOptions').css('display') == 'none'){
				$(this).parent().children('div.selectOptions').css('display','block');
			}else{
				$(this).parent().children('div.selectOptions').css('display','none');
			}
		});
		
		$(this).find('span.selectOption').click(function(){
			$(this).parent().css('display','none');
			$(this).closest('div.selectBoxBind').attr('value',$(this).attr('value'));
			$(this).parent().siblings('span.selected').html($(this).html());
		});
	});				
}

$(document).ready(function(){
	$('div[name="sortBy"] .selectOptions .selectOption').on('click', function(){
		sortListBy($(this).attr('value'));
	});
});
</script>

	<%-- <aui:select name="sortBy" id="sortReleasedTab" cssClass="sortReleasedTabClass" onChange="sortListBy(this);">
		<aui:option id="releaseTabDefault"  value="ReleasedDate-asc">Release Date (Oldest First)</aui:option>
		<aui:option value="ReleasedDate-desc">Release Date (Newest First)</aui:option>
		<aui:option value="ExpectedDispatchDate-desc">Expected Shipment date (Newest first)</aui:option>
		<aui:option value="ExpectedDispatchDate-asc">Expected Shipment date (Oldest first)</aui:option>
	</aui:select>  --%>
	<div class="sort_select">
	<input type="button" class="alt_select_btn" value="Sort By" id="btnReleased">
	<div class='selectBox' id='sortReleasedTab' name="sortBy" style="display:none;">
        <span class='selected'></span>
        <span class='selectArrow'>&#9660;</span>
        <div class="selectOptions" >
              <span class="selectOption" id="releaseTabDefault"  value="ReleasedDate-asc">Release Date (Oldest First)</span>
              <span class="selectOption" value="ReleasedDate-desc">Release Date (Newest First)</span>
              <span class="selectOption" value="ExpectedDispatchDate-desc">Expected Shipment date (Newest first)</span>
              <span class="selectOption" value="ExpectedDispatchDate-asc">Expected Shipment date (Oldest first)</span>
        </div>
	</div>
	
	<%-- <aui:select name="sortBy" id="sortDispatchedTab" cssClass="sortDispatchedTabClass" onChange="sortListBy(this);">
		<aui:option id="dipatchTabDefault" value="DispatchedDate-desc">Dispatch date (Newest first)</aui:option>
		<aui:option value="DispatchedDate-asc">Dispatch date (Oldest first)</aui:option>
	</aui:select> --%>
	<div class='selectBox' id='sortDispatchedTab' name="sortBy" style="display:none;">
        <span class='selected'></span>
        <span class='selectArrow'>&#9660;</span>
        <div class="selectOptions" >
              <span class="selectOption" id="dipatchTabDefault"  value="DispatchedDate-desc">Dispatch date (Newest first)</span>
              <span class="selectOption" value="DispatchedDate-asc">Dispatch date (Oldest first)</span>
        </div>
	</div>
	
	
	<%-- <aui:select name="sortBy" id="sortCancelledTab" cssClass="sortCancelledTabClass" onChange="sortListBy(this);">
		<aui:option id="cancelTabDefault" value="CancelledDate-desc">Cancel date (Newest first)</aui:option>
		<aui:option value="CancelledDate-asc">Cancel date (Oldest first)</aui:option>
	</aui:select> --%>
	
	<div class='selectBox' id='sortCancelledTab' name="sortBy" style="display:none;">
        <span class='selected'></span>
        <span class='selectArrow'>&#9660;</span>
        <div class="selectOptions" >
              <span class="selectOption" id="cancelTabDefault"  value="CancelledDate-desc">Cancel date (Newest first)</span>
              <span class="selectOption" value="CancelledDate-asc">Cancel date (Oldest first)</span>
        </div>
	</div>
	
	<%-- <aui:select name="sortBy" id="sortReturnedTab" cssClass="sortReturnedTabClass" onChange="sortListBy(this);">
		<aui:option id="returnTabDefault" value="ReturnedDate-desc">Return date (Newest first)</aui:option>
		<aui:option value="ReturnedDate-asc">Return date (Oldest first)</aui:option>
	</aui:select> --%>
	<div class='selectBox' id='sortReturnedTab' name="sortBy" style="display:none;">
        <span class='selected'></span>
        <span class='selectArrow'>&#9660;</span>
        <div class="selectOptions" >
              <span class="selectOption" id="returnTabDefault"  value="ReturnedDate-desc">Return date (Newest first)</span>
              <span class="selectOption" value="ReturnedDate-asc">Return date (Oldest first)</span>
        </div>
	</div>
	
	<%-- <aui:select name="sortBy" id="sortSearchResult" cssClass="sortSearchResultClass" onChange="sortListBy(this);">
		<aui:option id="searchresultDefault" value="ReleasedDate-asc">Release Date (Oldest First)</aui:option>
		<aui:option value="ReleasedDate-desc">Release Date (Newest First)</aui:option>
	</aui:select> --%>
	<div class='selectBox' id='sortSearchResult' name="sortBy" style="display:none;">
        <span class='selected'></span>
        <span class='selectArrow'>&#9660;</span>
        <div class="selectOptions" >
              <span class="selectOption" id="searchresultDefault"  value="ReleasedDate-asc">Release Date (Oldest First)</span>
              <span class="selectOption" value="ReleasedDate-desc">Release Date (Newest First)</span>
        </div>
	</div>
	</div>
	
<style>
.alt_select_btn{
	float:left;border-radius: 4px 0px 0px 4px !important;height:30px;line-height: 15px !important;
}
span.selected{
	border-top-left-radius: 0px;
	border-bottom-left-radius: 0px;border-left: none;
}
div.selectBox, div.selectBoxBind 
{
	position:relative;
	display:inline-block;
	cursor:default;
	text-align:left;
	line-height:30px;
	clear:both;
	color:#888;

}
span.selected
{
	width:270px; /*width:190px*/
	text-indent:20px;
	border:1px solid #ccc;
	border-right:none;
	background-image: -webkit-gradient(linear,50% 0,50% 100%,color-stop(0%,#fff),color-stop(100%,#ededed)); 
	background-image: -webkit-linear-gradient(#fff,#ededed); 
	background-image: -moz-linear-gradient(#fff,#ededed);
	background-image: -o-linear-gradient(#fff,#ededed);
	background-image: linear-gradient(#fff,#ededed);
	overflow:hidden;
}
span.selectArrow
{
	width:30px;
	border:1px solid #ccc;
	border-left:none;
	border-top-right-radius:5px;
	border-bottom-right-radius:5px;
	text-align:center;
	font-size:10px;
	-webkit-user-select: none;
	-khtml-user-select: none;
	-moz-user-select: none;
	-o-user-select: none;
	user-select: none;
	background-image: -webkit-gradient(linear,50% 0,50% 100%,color-stop(0%,#fff),color-stop(100%,#ededed)); 
	background-image: -webkit-linear-gradient(#fff,#ededed); 
	background-image: -moz-linear-gradient(#fff,#ededed);
	background-image: -o-linear-gradient(#fff,#ededed);
	background-image: linear-gradient(#fff,#ededed);
}

span.selectArrow,span.selected
{
	position:relative;
	float:left;
	height:30px;
	z-index:1;
	
}

div.selectOptions
{
	position:absolute;
	top:28px;
	left:0;
	width:300px;/*width:220px;*/
	border:1px solid #ccc;
	border-bottom-right-radius:5px;
	border-bottom-left-radius:5px;
	overflow:hidden;
	background:#f6f6f6;
	padding-top:2px;
	display:none;
	z-index:2;
}
	
span.selectOption
{
	display:block;
	width:100%;
	line-height:10px;
	padding:5px 10%;
	z-index:2;
	line-height: 1.2
}

span.selectOption:hover
{
	color:#f6f6f6;
	background:#4096ee;	
	width:100%;
}	
.sort_select{
	/*width:300px;*/
	float:right; /*Def#888*/
}
</style>
