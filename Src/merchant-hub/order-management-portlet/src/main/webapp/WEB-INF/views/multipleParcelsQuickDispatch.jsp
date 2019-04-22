<%@page import="com.tesco.mhub.order.model.ParcelDetails"%>
<%@page import="com.tesco.mhub.order.model.ItemDetails"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.tesco.mhub.order.model.OrderPackageDetails"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="java.util.List"%>

<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui"  prefix="aui" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ include file="/WEB-INF/views/init.jsp"%>

<%
	List<OrderPackageDetails> ordPackDetails = (List<OrderPackageDetails>)(request.getAttribute("orderPackDetails"));
	String orderStrLst = (String)(request.getAttribute("orderStrLst"));
	String orderArrLst[] = request.getAttribute("orderStrLst").toString().split(StringPool.COMMA);
	
%>

<portlet:defineObjects/>
<liferay-theme:defineObjects/>
<link rel="stylesheet" href="<%=themeDisplay.getPathThemeCss()%>/jquery-ui.css">
    <script src="<%=themeDisplay.getPathThemeJavaScript() %>/jquery-ui.js"></script>


<portlet:actionURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="cnfrmQckDispParcelUrl">
	<portlet:param name="action" value="cnfrmQckDispParcel" />
</portlet:actionURL>

<portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="quickOrderDispatchURL">
	<portlet:param name="action" value="renderQuickDispatchOpenOrders" />
</portlet:renderURL>

<style>

        .btn {
            display: inline-block;
            text-align: center;
            vertical-align: middle;
            cursor: pointer;
            white-space: nowrap;
            padding: 6px 12px;
            line-height: 1.428571429;
            border-radius: 4px;
        }

        .btn.primary-CTA {
            background-color: #4777bf;
            background-image: linear-gradient(#4777BF, #3D66A4);
            border: medium none;
            color: #FFFFFF;
            text-shadow: none !important;
        }

        .btn.primary-CTA:hover {
            background-color: #325486;
            background-image: -webkit-gradient(linear,50% 0,50% 100%,color-stop(0%,#325486),color-stop(100%,#3d66a4));
            background-image: -webkit-linear-gradient(#325486,#3d66a4);
            background-image: -moz-linear-gradient(#325486,#3d66a4);
            background-image: -o-linear-gradient(#325486,#3d66a4);
            background-image: linear-gradient(#325486,#3d66a4);
            border: 0;
            color: white;
            text-decoration: none;
            text-shadow: none;
        }

        .btn.primary-CTA:active {
            background-color: #325486;
            background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #325486),
                color-stop(100%, #3d66a4));
            background-image: -webkit-linear-gradient(#325486, #3d66a4);
            background-image: -moz-linear-gradient(#325486, #3d66a4);
            background-image: -o-linear-gradient(#325486, #3d66a4);
            background-image: linear-gradient(#325486, #3d66a4);
        }


        .btn.primary-CTA:disabled {
            background-color: #eaeaea;
            background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #eaeaea),
                color-stop(100%, #eaeaea));
            background-image: -webkit-linear-gradient(#eaeaea, #eaeaea);
            background-image: -moz-linear-gradient(#eaeaea, #eaeaea);
            background-image: -o-linear-gradient(#eaeaea, #eaeaea);
            background-image: linear-gradient(#eaeaea, #eaeaea);
            color: #bfbfbf;
            border: 1px solid #BFBFBF;
        }

        .btn.primary-CTA-alt {
            background-color: #ffa200;
            background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #ffa200),
                color-stop(100%, #dd8c00));
            background-image: -webkit-linear-gradient(#ffa200, #dd8c00);
            background-image: -moz-linear-gradient(#ffa200, #dd8c00);
            background-image: -o-linear-gradient(#ffa200, #dd8c00);
            background-image: linear-gradient(#ffa200, #dd8c00);
            border: none;
            color: white;
        }

        .btn.primary-CTA-alt:active {
            background-color: #ffa200;
            background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #ffa200),
                color-stop(100%, #dd8c00));
            background-image: -webkit-linear-gradient(#ffa200, #dd8c00);
            background-image: -moz-linear-gradient(#ffa200, #dd8c00);
            background-image: -o-linear-gradient(#ffa200, #dd8c00);
            background-image: linear-gradient(#ffa200, #dd8c00);
        }

        .btn.primary-CTA-alt:disabled {
            background-color: #eaeaea;
            background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #eaeaea),
                color-stop(100%, #eaeaea));
            background-image: -webkit-linear-gradient(#eaeaea, #eaeaea);
            background-image: -moz-linear-gradient(#eaeaea, #eaeaea);
            background-image: -o-linear-gradient(#eaeaea, #eaeaea);
            background-image: linear-gradient(#eaeaea, #eaeaea);
            color: #bfbfbf;
        }

        .btn.secondary-CTA {
            background-color: #ededed;
            background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #ffffff),
                color-stop(100%, #ededed));
            background-image: -webkit-linear-gradient(#ffffff, #ededed);
            background-image: -moz-linear-gradient(#ffffff, #ededed);
            background-image: -o-linear-gradient(#ffffff, #ededed);
            background-image: linear-gradient(#ffffff, #ededed);
            border: 1px solid silver;
            color: #868686;
        }

        .btn.secondary-CTA:active {
            background-color: #c6c6c6;
            background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #c6c6c6),
                color-stop(100%, #ededed));
            background-image: -webkit-linear-gradient(#c6c6c6, #ededed);
            background-image: -moz-linear-gradient(#c6c6c6, #ededed);
            background-image: -o-linear-gradient(#c6c6c6, #ededed);
            background-image: linear-gradient(#c6c6c6, #ededed);
        }

        .btn.secondary-CTA:disabled {
            background-color: #eaeaea;
            color: #bfbfbf;
        }
        .btn-primary span input[type="submit"], .btn-primary span input[type="button"]{
            background-color: #4777bf;
            background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #4777bf),
                color-stop(100%, #3d66a4));
            background-image: -webkit-linear-gradient(#4777bf, #3d66a4);
            background-image: -moz-linear-gradient(#4777bf, #3d66a4);
            background-image: -o-linear-gradient(#4777bf, #3d66a4);
            background-image: linear-gradient(#4777bf, #3d66a4);
            border: none;
            color: white;
            text-shadow: none;
        }

        a.btn.primary-CTA,a.btn.secondary-CTA {
            text-decoration: none;
        }
        
        .portlet-msg-error,.lfr-message-error {
            border-color: #dca7a7;
            border-radius: 4px;
            box-shadow: 0 1px 0 rgba(255,255,255,0.25) inset,0 1px 2px rgba(0,0,0,0.05);
            color: #a94442;
            padding-top: 10px;
            text-shadow: 0 1px 0 rgba(255,255,255,0.2);
            font-weight: normal;
        }

       /*custom Css*/

       .heading{
       	color:#868686;
       }
        .multiple-parcels{
            display:inline-block;
            width:100%;
        }

       .multiple-parcels thead td{
            font-weight:bold;
            text-transform:uppercase;
       }

       .multiple-parcels tbody{
            display:inline-block;
            width:100%;
            height:300px;
            overflow-y: auto;
            border-top:2px solid #ddd;
       }
      
        
        .multiple-parcels .td-orderid,
        .multiple-parcels .td-cname,
        .multiple-parcels .td-trackingid{
            width:150px;
        }
        
         .multiple-parcels .td-desc{
            width:238px;
        }
        
        .multiple-parcels .td-parcels,.multiple-parcels .td-sku,.multiple-parcels .td-qty{
            width:125px;
        }
        
        .multiple-parcels  .td-sku,.multiple-parcels tbody .td-parcels,.multiple-parcels thead .td-parcels{
        	text-align:center;
        }

       .open-qty{
            width:55px;
            padding:5px;
            -webkit-appearance: menulist;
            -moz-appearance: window;
       }

       .mutiple-parcels-btns{
            width:21.5%;
       }

       .alert-danger{
        padding:10px;
       }
        
        .multiple-parcels thead tr{
           border-bottom:0
        }
       
        .multiple-parcels tbody tr:first-child td{
            border-top:0;
        }
        
         .multiple-parcels .tr-sub .colspan{
            border-top:0;
         }

         .tr-sub .alert-col{
            margin-bottom: 0;
            width:400px;
         }
         
         .mp-error{
         	padding-top: 26px;
  			color: #F71515;
  			font-style:italic;
         }
         
         .multiple-parcels tbody td:empty{
         	border-top:0;
         }
         
         .multiple-parcels .td-qd-error{
            color:#F71515;
            font:13px/19px arial;
            border-top:0;
         }
         
          .multiple-parcels .td-qd-error-dummy{
            color:#F71515;
            font:13px/19px arial;
            border-top:0;
         }
</style>

<script type="text/javascript">
	//alert("Order Details Size"+${fn:length(orderPackDetails)});
	var skuTrackArr = [];
	var isError=false;
	var parcelItemArr = [];
	var trackIdAllArr = [];
	var selOrdArr = [];
	var subParcelItemArr = [];
	var parcelJson = null;
	
$( document ).ready(function() {
    createParcelItemArr();
  	createSkuTrackArr();
  	getSelOrdArr();
    for(var z=0;z<skuTrackArr.length;z++){
    	console.log("SKU ID :"+skuTrackArr[z].skuId);
    	var trckArr = skuTrackArr[z].trackIdArr;
    	for(var m=0; m<trckArr.length;m++){
    		console.log("Tracking ID :"+trckArr[m]);
    	}
    		
    }
    
	$('#confirm-btn').click(function(){
		createParcelItemArr();
		validateQty();
		if(!isError){
			constructfinalParcItmArr();
			confirmQuickDispatch();
			
		}
	});
	
	$('#cancel-btn').click(function(){
		var url = "<%=quickOrderDispatchURL%>";
		for(var y=0;y<selOrdArr.length;y++){
			url+='&selectedOrderNumbers='+selOrdArr[y];
		}
		$('#qckDispfrm').attr('action', url);
    	$("#qckDispfrm").submit(); 
			
		
	});
	
	
});

function createParcelItemArr(){
	parcelItemArr = [];
	trackIdAllArr = [];
	var opdObj ={};
	<%	List<ParcelDetails> prcDetLst1 = null;
		List<ItemDetails> itmDetLst1 = null;
		for(int itr=0;itr<ordPackDetails.size();itr++){
			prcDetLst1 = ordPackDetails.get(itr).getParcelDetailsList();%>
			<%for(int itmItr=0;itmItr<prcDetLst1.size();itmItr++) {%>
			trackIdAllArr.push("<%=prcDetLst1.get(itmItr).getTrackingId()%>");
				<%itmDetLst1 = prcDetLst1.get(itmItr).getItemDetailsLst();
					for(int itdItr=0;itdItr<itmDetLst1.size();itdItr++){%>
					 	opdObj ={};
					 	opdObj.orderId = "<%=ordPackDetails.get(itr).getOrderId()%>"
						opdObj.trackId = "<%=prcDetLst1.get(itmItr).getTrackingId()%>";
						<%if(!(itmDetLst1.get(itdItr).getSkuId().trim().equals(""))){%>
								opdObj.skuId = "<%=itmDetLst1.get(itdItr).getSkuId()%>";
						<%}else{%>
								opdObj.skuId = "sku-"+<%=itdItr%>;
						<%}%>
						opdObj.lineNum = "<%=itmDetLst1.get(itdItr).getLineNumber()%>";
						opdObj.qty = "<%=itmDetLst1.get(itdItr).getQty()%>";
						parcelItemArr.push(opdObj);
					<%}%>	
			<%}%>
			<%}%>
			console.dir("parcelItemArr : "+parcelItemArr);
 }
 
function createSkuTrackArr(){
	 var skuTrackObj = null;
	<%	List<ParcelDetails> prcDetLst2 = null;
		List<ItemDetails> itmDetLst2 = null;
		int itmItr=0;
		int itdItr=0;
		for(int itr=0;itr<ordPackDetails.size();itr++){
			itmItr=0;
			itdItr=0;
			prcDetLst2 = ordPackDetails.get(itr).getParcelDetailsList();%>
			 var trackArr = [];
			<%for(;itmItr<prcDetLst2.size();) {
				itmDetLst2 = prcDetLst2.get(itmItr).getItemDetailsLst();
					for(;itdItr<itmDetLst2.size();){%>
						trackArr.push("<%=prcDetLst2.get(itmItr).getTrackingId()%>");
						<%++itmItr;
						if(itmItr==prcDetLst2.size()){%>
							skuTrackObj = {};
							skuTrackObj.trackIdArr = trackArr;
							trackArr = [];
							<%if(!(itmDetLst2.get((itdItr)).getSkuId().trim().equals(""))){%>
								skuTrackObj.skuId = "<%=itmDetLst2.get((itdItr)).getSkuId()%>";
							<%}else{%>
								skuTrackObj.skuId = "sku-"+<%=itdItr%>;
							<%}%>	
							skuTrackArr.push(skuTrackObj);
							<%++itdItr;
							itmItr=0;
						}%>
					<%	break;
						}%>	
			<%
				if(itdItr==itmDetLst2.size()){
					break;
				}
			}%>
			<%}%>
}

function getOrigQty(sId, tId){
	for(var qtyItr = 0; qtyItr<parcelItemArr.length;qtyItr++){
		if((parcelItemArr[qtyItr].skuId==sId) && (parcelItemArr[qtyItr].trackId==tId)){
			return parseInt(parcelItemArr[qtyItr].qty);
		}
	}
}

function getOrdrId(sId, tkId){
	for(var qtyItr = 0; qtyItr<parcelItemArr.length;qtyItr++){
		if((parcelItemArr[qtyItr].skuId==sId) && (parcelItemArr[qtyItr].trackId==tkId)){
			return  parcelItemArr[qtyItr].orderId;
		}
	}
}

function validateQty(){
	isError=false;
	$(".mp-error").html("").hide();
	$(".td-qd-error").html("");
	var skuId = null;
	var trackId = null;
	var origQty = null;
	var ordId = null;
	
	//Assigned quantities must not exceed total open quantity
	for(var valItr=0;valItr<skuTrackArr.length;valItr++ ){
		var skId = skuTrackArr[valItr].skuId;
		origQty = parseInt(getOrigQty(skId, skuTrackArr[valItr].trackIdArr[0]));
		var trIdAr = skuTrackArr[valItr].trackIdArr;
		var actQty = parseInt(0);
		for(var trIdItr=0;trIdItr<trIdAr.length;trIdItr++){
			actQty = parseInt(actQty)+parseInt($("#qty-"+trIdAr[trIdItr]+"_"+skId).val());
		}
		console.log("Actual Quantity : "+actQty);
		if(actQty>origQty){
			isError = true;
			ordId = getOrdrId(skId, skuTrackArr[valItr].trackIdArr[0]);
			$("#err-"+ordId).html("Your assigned quantities exceed the total open quantity.").show();
		}
	}
	
	//To submit a parcel with no quantities at all
	if(!isError){
		//alert("In iserror");
		var skId = null;
		var trId = null;
		var isAllSkuZero = true;
		var skuQty = null;
		for(var flTrcItr=0;flTrcItr<trackIdAllArr.length;flTrcItr++){
			isAllSkuZero = true;
			for(var pItr=0;pItr<parcelItemArr.length;pItr++){
				if(trackIdAllArr[flTrcItr]==parcelItemArr[pItr].trackId){
					skId = parcelItemArr[pItr].skuId;
					trId = parcelItemArr[pItr].trackId;
					skuQty = parseInt($("#qty-"+parcelItemArr[pItr].trackId+"_"+parcelItemArr[pItr].skuId).val());
					if(skuQty > 0){
						//alert("In if");
						isAllSkuZero = false;
						break;
					}
				}
			}
			if(isAllSkuZero){
				//alert("In isAllSkuZero");
				isError = true;
				if($("#dummy-"+trackIdAllArr[flTrcItr]).length > 0){
					//alert("in dummy");
					$("#dummy-"+trackIdAllArr[flTrcItr]).hide();
					$("#err2-"+trackIdAllArr[flTrcItr]).html("Please select the quantity  you wish to dispatch.").hide();
					$("#err-"+trackIdAllArr[flTrcItr]).html("Please select the quantity  you wish to dispatch.").show();
				}else{
					//alert("in else and trId :"+trId);
					$("#err2-"+trackIdAllArr[flTrcItr]).html("Please select the quantity  you wish to dispatch.").show();
				}
			}
		}
		
	}
	
	//Error triggered when a parcel is submitted with zero quantity
	
	if(!isError){
		var skId = null;
		var trIdAr = null;
		var trckId = null;
		for(var valItr=0;valItr<skuTrackArr.length;valItr++ ){
			skId = skuTrackArr[valItr].skuId;
			trIdAr = skuTrackArr[valItr].trackIdArr;
			var actQty = parseInt(0);
			for(var trIdItr=0;trIdItr<trIdAr.length;trIdItr++){
				trckId = trIdAr[trIdItr];
				actQty = parseInt(actQty)+parseInt($("#qty-"+trIdAr[trIdItr]+"_"+skId).val());
			}
			console.log("Actual Quantity : "+actQty);
			if(actQty==0){
				isError = true;
				break;
			}
			
		}
		if(isError){
			for(var trIdItr=0;trIdItr<trIdAr.length;trIdItr++){
				//alert("In 2nd validation for");
				if($("#dummy-"+trIdAr[trIdItr]).length > 0){
					//alert("In 2nd validation If");
					$("#dummy-"+trIdAr[trIdItr]).hide();
					$("#err2-"+trIdAr[trIdItr]).html("Please select the quantity  you wish to dispatch for SKU "+"'"+skId+"'"+".").hide();
					$("#err-"+trIdAr[trIdItr]).html("Please select the quantity  you wish to dispatch for SKU "+"'"+skId+"'"+".").show();
				}else{
					$("#err2-"+trIdAr[trIdItr]).html("Please select the quantity  you wish to dispatch for SKU "+"'"+skId+"'"+".").show();
				}
			}
		}
	}
	
	

	
	//To submit an order where the SKU only has a partial qty allocated
	if(!isError){
		//Assigned quantities must not exceed total open quantity
		for(var valItr=0;valItr<skuTrackArr.length;valItr++ ){
			var skId = skuTrackArr[valItr].skuId;
			origQty = parseInt(getOrigQty(skId, skuTrackArr[valItr].trackIdArr[0]));
			var trIdAr = skuTrackArr[valItr].trackIdArr;
			var actQty = parseInt(0);
			for(var trIdItr=0;trIdItr<trIdAr.length;trIdItr++){
				actQty = parseInt(actQty)+parseInt($("#qty-"+trIdAr[trIdItr]+"_"+skId).val());
			}
			console.log("Actual Quantity : "+actQty);
			if(actQty<origQty){
				isError = true;
				for(var trIdItr=0;trIdItr<trIdAr.length;trIdItr++){
					if($("#dummy-"+trIdAr[trIdItr]).length > 0){
						$("#dummy-"+trIdAr[trIdItr]).hide();
						$("#err-"+trIdAr[trIdItr]).html("Quantity for SKU "+skId+" does not match the total open quantity of "+origQty+".").show();
						$("#err2-"+trIdAr[trIdItr]).html("Quantity for SKU "+skId+" does not match the total open quantity of "+origQty+".").hide();
					}else{
						$("#err2-"+trIdAr[trIdItr]).html("Quantity for SKU "+skId+" does not match the total open quantity of "+origQty+".").show();
					}
				}
			}
		}
	}
	
	if(isError){
		//alert("In isError");
		$(".mp-error").html("Please fix the errors below").show();
	}
	
}

function confirmQuickDispatch(){
	//alert("calling pop-up");
	popUp();
}

function getSelOrdArr(){
	<%for(int z = 0;z<orderArrLst.length;z++){%>
		console.log("Order Arrays : "+"<%=orderArrLst[z]%>");
		selOrdArr.push("<%=orderArrLst[z]%>");
	<%}%>
	console.dir("selOrdArr : "+selOrdArr);
}

function popUp() {
	//alert("Inisde pop-up");
    $("#dialog-confirm").html("You have provided the information required for processing your selected orders.Please confirm to move your orders to the updates basket.");
    $("#dialog-confirm").dialog({
           resizable: false,
           modal: true,
           title: "Quick Dispatch Confirmation",
           height: 210,
           width : 550,
           buttons: {
                  " Cancel ": function () {
                        $(this).dialog('close');
                        $(".overlay").css("display","block");
                        $(".dialog-confirm").css("display","block");
                        $(".date-in-group-by-date").css('background-color','white');
                        $(".hr-in-group-by-date").css('margin-left','10px');
                  },
                  " Confirm ": function () {
                        $(".overlay").css("display","block");
                        $(".dialog-confirm").css("display","block");
                    	var quickDispUrl = '<%=cnfrmQckDispParcelUrl%>';
                    	quickDispUrl+='&parcelJson='+parcelJson;
                    	$('#qckDispfrm').attr('action', quickDispUrl);
                    	$("#qckDispfrm").submit(); 
                        return false;
                  }
           }
    });
    $(".ui-dialog-titlebar-close").css("display","none");
 }
 
function constructfinalParcItmArr(){
	subParcelItemArr = [];
	var subOpdObj ={};
	for(var g=0;g<parcelItemArr.length;g++){
		subOpdObj = parcelItemArr[g];
		subOpdObj.qty = parseInt($("#qty-"+subOpdObj.trackId+"_"+subOpdObj.skuId).val());
		console.log("Quantity, "+subOpdObj.qty +" "+subOpdObj.trackId+", skuid "+subOpdObj.skuId);
		subParcelItemArr.push(subOpdObj);
	}
	if(subParcelItemArr.length > 0){
		parcelJson =  JSON.stringify(subParcelItemArr);
		console.log("@@@ parcelJson @@ : "+parcelJson);
	}
	
	return;
}
</script>
<form method="post" id="qckDispfrm" name="qckDispfrm">
<div id="dialog-confirm"></div>
 <div class="container">
      <div class="row">
        <div class="col-xs-6">
            <h1 class="heading">Quick Dispatch - Multiple Parcels</h1>
        </div>
        <div class="col-xs-10">
           <div class="mp-error">You have selected more than one package for a click and collect order.<br/>Please assign the items / quantities to the tracking IDs.</div>
        </div>
      </div>
        <div class="row">
        <div class="col-xs-16 multiple-parcels-wrapper">
            <table class="table table-hover multiple-parcels">
                <thead>
                    <tr>
                        <td class="td-orderid">Order Id</td>
                        <td class="td-cname">Cust Name</td>
                        <td class="td-parcels">Parcels</td>
                        <td class="td-trackingid">Tracking Id</td>
                        <td class="td-sku">SKU</td>
                        <td class="td-desc">Description</td>
                        <td class="td-qty">QTY</td>
                    </tr>
                </thead>
                <tbody>
                 <!--error html <tr>
                        <td colspan="7" class="td-qd-error">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Magni, odit.</td>
                    </tr> -->
           			<%
           			boolean opdComplete=false;
            		boolean trackComplete=false;
            		boolean itmDetComplete=false;
            		int i=0;
            		int j=0;
            		int k=0;
            		List<ParcelDetails> pdtls = ordPackDetails.get(i).getParcelDetailsList();
            		List<ItemDetails> itmDetls =pdtls.get(j).getItemDetailsLst();
           			for(; i<ordPackDetails.size();){%>
           			<%if(!((k==(itmDetls.size()-1))) || (itmDetls.size() == 1)) {%>
           				 <tr class="tr-main">
           				 <%if(!opdComplete){ %>
		                        <td class="td-orderid"><%=ordPackDetails.get(i).getOrderId()%></td>
		                        <td class="td-cname"><%=ordPackDetails.get(i).getCustomerName()%></td>
		                        <td class="td-parcels"><%=ordPackDetails.get(i).getParcelSize()%></td>
                        <%}else{ %>
		                        <td class="td-orderid"></td>
		                        <td class="td-cname"></td>
		                        <td class="td-parcels"></td>
                        <%} %>
                        <%pdtls = ordPackDetails.get(i).getParcelDetailsList();
                        
                        for(; j<pdtls.size();){%>
                        <%if(!trackComplete){ %>
                        	<td class="td-trackingid"><%=pdtls.get(j).getTrackingId()%></td>
                        <%}else{ %>
                        	<td class="td-trackingid"></td>
                        <%} %>
                        <%itmDetls =pdtls.get(j).getItemDetailsLst();
                        for(; k<itmDetls.size();){
                        %>
                        <%if((itmDetls.get(k).getSkuId().trim() != "")){ %>
                        	<td class="td-sku"><%=itmDetls.get(k).getSkuId()%></td>
                        <%}else{%>
                        	<td class="td-sku">-</td>
                        <%} %>
                        <td class="td-desc"><%=itmDetls.get(k).getDescription()%></td>
                         <td class="td-qty">
                         <%if((itmDetls.get(k).getSkuId().trim() != "")){ %>
                            <select class="open-qty" id="qty-<%=pdtls.get(j).getTrackingId()%>_<%=itmDetls.get(k).getSkuId()%>">
                            	<%for(int x=itmDetls.get(k).getQty();x>=0;x-- ){%>
	                                <option value="<%=x%>"><%=x %></option>
                                <%} %>
                            </select>
                            <%}else{%>
                            	  <select class="open-qty" id="qty-<%=pdtls.get(j).getTrackingId()%>_sku-<%=k%>">
                              	<%for(int x=itmDetls.get(k).getQty();x>=0;x-- ){%>
  	                                <option value="<%=x%>"><%=x %></option>
                                  <%} %>
                              </select>
                            <%}%>
                        </td>
                         </tr>
                         <%if(itmDetls.size() == 1) {%>
                    		<tr>
                    			<td colspan="7" class="td-qd-error" id="err2-<%=pdtls.get(j).getTrackingId()%>" style="display:none">Error message at track ID level</td>
                    		</tr>
                    	<%} %>
                         
                        <%
                        	++k;
                        	if(k==itmDetls.size()){
                        		k=0;
                        		++j;
                        		trackComplete=false;
                        	}else{
                        		trackComplete = true;
                        	} %>
                        	
                        	<%break;} %>
                   
                    <%
                    	if(j==pdtls.size()){
                    		j=0;
                    		++i;
                    		opdComplete = false;
                    	}else{
                    		opdComplete = true;
                    	}
                    	break;
                    	} %>
           			<%}else{%>
           				 <tr>
		                        <td colspan="4" class="td-qd-error" id="err-<%=pdtls.get(j).getTrackingId()%>" style="display:none">Error message at track ID level</td>
		                        <td colspan="4" class="td-qd-error-dummy" id="dummy-<%=pdtls.get(j).getTrackingId()%>"></td>
                        <%pdtls = ordPackDetails.get(i).getParcelDetailsList();
                        
                        for(; j<pdtls.size();){%>
                        <%itmDetls =pdtls.get(j).getItemDetailsLst();
                        for(; k<itmDetls.size();){
                        %>
                        <%if((itmDetls.get(k).getSkuId().trim() != "")){ %>
                        	<td class="td-sku"><%=itmDetls.get(k).getSkuId()%></td>
                        <%}else{%>
                        	<td class="td-sku">-</td>
                        <%} %>
                        <td class="td-desc"><%=itmDetls.get(k).getDescription()%></td>
                         <td class="td-qty">
                         <%if((itmDetls.get(k).getSkuId().trim() != "")){ %>
                            <select class="open-qty" id="qty-<%=pdtls.get(j).getTrackingId()%>_<%=itmDetls.get(k).getSkuId()%>">
                            	<%for(int x=itmDetls.get(k).getQty();x>=0;x-- ){%>
	                                <option value="<%=x%>"><%=x %></option>
                                <%} %>
                            </select>
                            <%}else{%>
                            	  <select class="open-qty" id="qty-<%=pdtls.get(j).getTrackingId()%>_sku-<%=k%>">
                              	<%for(int x=itmDetls.get(k).getQty();x>=0;x-- ){%>
  	                                <option value="<%=x%>"><%=x %></option>
                                  <%} %>
                              </select>
                            <%}%>
                        </td>
                         </tr>
                        <%
                        	++k;
                        	if(k==itmDetls.size()){
                        		k=0;
                        		++j;
                        		trackComplete=false;
                        	}else{
                        		trackComplete = true;
                        	} %>
                        	<%break;} %>
                   
                    <%
                    	if(j==pdtls.size()){
                    		j=0;
                    		++i;
                    		opdComplete = false;
                    	}else{
                    		opdComplete = true;
                    	}
                    	break;
                    	} %>
           			<%}%>
           			<%if(!opdComplete) {%>
                	<tr>
                		<td colspan="7" class="td-qd-error" id="err-<%=ordPackDetails.get(i-1).getOrderId()%>" style="display:none">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Magni, odit.</td>
                	</tr>
                	<% }%>
           			<%}%>
           		
                </tbody>
            </table>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-16">
            <div class="col-xs-4 pull-right mutiple-parcels-btns">
                <button id="cancel-btn" class="btn secondary-CTA" type="button">Cancel</button>
                <button id="confirm-btn" class="btn primary-CTA" type="button">Confirm</button>
            </div>
        </div>
      </div>
      <!-- Dialogs -->
  </div>
</form>
