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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

    <portlet:renderURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="confirmQuickDispatchResourceUrl">
    <portlet:param name="action" value="quickDispatchCandCOrders"/>
    </portlet:renderURL> 
    
    <portlet:actionURL secure="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure() %>" var="commitOrders">
	<portlet:param name="action" value="commitOrders"/>
	</portlet:actionURL> 
    
    <portlet:resourceURL secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
		var="deletelLabelUrl" id="deletelLabel">
		<portlet:param name="action" value="deletelLabel" />
	</portlet:resourceURL>

	 <portlet:resourceURL secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
		var="printQuickOrdersURL" id="printQuickOrders">
		<portlet:param name="action" value="printQuickOrders" />
	</portlet:resourceURL>
	
	<portlet:resourceURL secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
		var="printQuickAllLabelUrl" id="PrintQuickAllLabel">
		<portlet:param name="action" value="PrintQuickAllLabel" />
	</portlet:resourceURL>

<portlet:resourceURL secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
		var="refereshQuickOrdersURL" id="refereshQuickOrders">
		<portlet:param name="action" value="refereshQuickOrders" />
	</portlet:resourceURL>
<portlet:resourceURL
	secure="<%=GetterUtil.getBoolean(PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS)) || request.isSecure()%>"
	var="addYodlLabelUrl" id="addYodlLabel">
	<portlet:param name="action" value="addYodlLabel" />
</portlet:resourceURL>
    <script type="text/javascript">
    var parcelItemArray = [];
    
    $(document).ready(function() {
    	
    	
    	
    	// To check initially whether CC tracking iD empty or not
    	$('.trackingId').each(function(){
    		var attr = $(this).attr('data-cc');

    		if (typeof attr !== typeof undefined && attr !== false) {
    			if(!($(this).data('cc'))){
        			console.log($(this).data('cc'));
        			$('#confirlQuickDispatch').removeClass(' primary-CTA-alt').addClass('disabled');
        		}
    		}
    		
    		
    	});
    	
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
    	
        //$("#div-"+divno).remove();
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
    	var parcelArray = [];
    	parcelItemArray = [];
    	$('.td-parcel').each(function() {
    	    		if($(this).children('.parcel')){
    	    			parcelArray.push(parseInt($(this).children('.parcel').text()));
    	    	    	}else
    	    		{
    	    	          	parcelArray.push(0);
    	    	       	}
    	    	});
    	var flag=false;
    	var arrayLength = parcelArray.length;
    	for (var i = 0; i < arrayLength; i++) {
	    		if(parseInt(parcelArray[i])>1){
	 	    		flag=true;
	 	    		break;
	    		}
    	}
    	var quickDispatchCandCItems = $('.quickDispatchItem');
    	if(flag===false){
    		quickDispatchCandCItems.each(function(i,val){
        		console.log($(this).attr('id'));
        		parcelItemArray.push($(this).attr('id').substring(4));
        	});
    	    		popUp();
    	}else{
    	var qDispatchUrl = '<%=confirmQuickDispatchResourceUrl%>';
    	quickDispatchCandCItems.each(function(i,val){
    		console.log($(this).attr('id'));
    		parcelItemArray.push($(this).attr('id').substring(4));
    	});
    	
    	console.log('my array'+parcelItemArray);
    	qDispatchUrl+='&orderArr='+parcelItemArray;

		$('#frm1').attr('action', qDispatchUrl);
		$("#frm1").submit(); 
    	}
    }
    
    function popUp() {
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
                            var url = '<%=confirmQuickDispatchResourceUrl%>';
                            url+='&orderArr='+parcelItemArray;
                            document.getElementById("frm1").action = url;
                            document.getElementById("frm1").submit();  
                            return false;
                      }
               }
        });
        //$(".ui-dialog-titlebar-close").css("display","none");
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
    
    $(function(){
		
    	var initArrayParcel = [];
    	
    	// Display error msg if renerate button avail
    	regenCheckMsg();
		
    	// function to display message for regenerate
        
        function regenCheckMsg(){
      	  if($('.quick-dispatch-tables tbody tr').has('.td-regenerate').length){
      		  //show the msg
      		  $('.qd-msg').text('One or more of your orders has an old collection date. To regenerate the label, tracking ID and collection date, please click on the refresh icon next to the order.');
      	  }
      	  else{
      		  $('.qd-msg').text('Please note that partial dispatches cannot be completed via Quick Dispatch.');
      	  }
        }
    	
        //Remove row
        $('.remove').click(function(){
            $(this).parent().parent().nextUntil('.orderid-row').remove();
            $(this).parent().parent().remove();
            
         // To check initially whether CC tracking iD empty or not
        	$('.trackingId').each(function(){
        		var attr = $(this).attr('data-cc');

        		if (typeof attr !== typeof undefined && attr !== false) {
        			if(!($(this).data('cc'))){
            			console.log($(this).data('cc'));
            			$('#confirlQuickDispatch').removeClass(' primary-CTA-alt').addClass('disabled');
            		}
        		}else{
        			$('#confirlQuickDispatch').addClass(' primary-CTA-alt').removeClass('disabled');
        		}
        		
        		
        	});
            
            //$(this).parent().parent().nextUntil('.orderid-row').remove();
            var rCount = $('.quick-dispatch-tables tbody tr').length;
            if(rCount==0){
                $('.cc-btns').hide();
            }
            //console.log(rCount);
        }); 
        
        // Disable the sub button if value ==1 
       $('.parcel').each(function(i) {

           $(this).text(parseInt($(this).parent().parent().nextUntil('.orderid-row').length)+1);

           if((parseInt($( this ).text())) == 1){
            $(this).parent().find('.parcel-sub').addClass('sub-hide');
           }
           //console.log("length"+$(this).parent().parent().nextUntil('.orderid-row').length);
           //Count the number of rows
           
           
        });
        
        
        //Add pacrel +button
        $('.parcel-add').click(function(){

            //var av = parseInt($(this).parent().parent().nextUntil('.orderid-row').length)+1

            //console.log("click--"+av);
            
            var parcelVal =parseInt($(this).prev('.parcel').text());
            
            if(parcelVal == 1){
            	//parcelVal++;
                $(this).parent().find('.parcel-sub').removeClass('sub-hide');
                //return false;
                
            }
            
            if(parcelVal>1){
                //parcelVal++;
                $(this).prev('.parcel').text(parcelVal);
            }
            
            
            parcelVal++;
            console.log(parcelVal);
            $(this).prev('.parcel').text(parcelVal);
            
             $('#confirlQuickDispatch').removeClass('primary-CTA-alt').addClass('disabled');
            
            
            return false;
        });
      
        
     // Regenerate click
        $('.regenerate').on('click',function(){
        	var $thisRegenerate = $(this);
        	var ajxYodelUrl = '<%=deletelLabelUrl%>';
        	var tNode = $(this).parent().parent().children('.td-trackingId').children('.trackingId');
        	var tID = tNode.val();
        	var oID = $(this).parent().parent().attr('id').substring(4);
        	ajxYodelUrl += '&orderId='+oID+'&trackingId='+tID;
    		$('.ajax-loader').show();
    		 $(".qd-msg").hide().empty();
    		$("#regenerate-message").dialog( "open" );
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
        					var dateSet = $thisRegenerate.parent().parent().children('.td-ColecDate');
        					var ajxYodelUrl = '<%=addYodlLabelUrl%>';
        					var calendarDate = $("#dateSelected").val();
        					ajxYodelUrl += "&orderId="+oID+"&collectionDate="+'';
        					$('.ajax-loader').show();
        					$.ajax({
        							type : "POST",
        							url : ajxYodelUrl,
        							dataType : 'text'
        						}).done(function(msg6) {				
        							var msg6 = $.trim(msg6);
        							var msg8 = JSON.parse(msg6);
        							 if (msg6 == '' || msg6.indexOf('##ERROR##') != -1) {
     		                            if (console) {
     		                                console.log('MHUB failed to retrieve C&C.' + msg6);
     		                            }
     		                          } else {
     		                        	  console.log('MHUB Passed to retrieve C&C for MULTI SKU');	                        	
     		                          }
        							
        							   if(msg8.isAuthFail !== undefined) {
     		                        	  console.log(msg8.errMsg);
     		                        	  $(".qd-msg").show().text(msg8.errMsg);
     		                          }
     		                          else if(msg8.isResNull !== undefined) {
     		                        	 console.log(msg8.errMsg);
     		                        	 $(".qd-msg").show().text(msg8.errMsg);
     		                          }else {
     		                        	 $(".qd-msg").show().text('Please note that partial dispatches cannot be completed via Quick Dispatch.');
        								var dtID =msg8[0].trackingId;
        	        		        	var dummyDate = new Date(msg8[0].collectionDate);
        								var dat = dummyDate.getDate();
        			                    if(dat <=9){dat ="0"+dat;}
        		                        dateString =dat + "/"+ ("0"+ (dummyDate.getMonth() + 1)).slice(-2) + "/" + dummyDate.getFullYear().toString();
        								
        	        		        	tNode.val(dtID);
        	        		        	console.log(dummyDate);
        	        		        	dateSet.text(dateString);
        	        		        	console.log('Regenerate->'+tID+'|'+oID);
        	        		        	$thisRegenerate.hide();
        	        		        	$thisRegenerate.parent('.td-regenerate').siblings('.td-empty').remove();
        	        		        	$thisRegenerate.parent('.td-regenerate').remove();
        	        		        	regenCheckMsg();
        							}
        						}).fail(function(jqXHR, textStatus) {
        		    				if (console){
        		    					console.log('Refresh Parcel Request failed with status: ' +textStatus);
        		    				}				
        		    			}).always(function() {
        		    				$('.ajax-loader').hide();
        		    				$("#regenerate-message").dialog( "close" );
        		    			});	
        					
    				}
    			}).fail(function(jqXHR, textStatus) {
    				if (console){
    					console.log('Refresh Parcel Request failed with status: ' +textStatus);
    				}				
    			}).always(function() {
    				$('.ajax-loader').hide();
    				
    			});	
        });
        //Sub parcel - button
        $('.parcel-sub').click(function(){
            $this = $(this);
            var parcelVal =parseInt($(this).next('.parcel').text());
            
            var orderId =$(this).parent().parent().attr('id').substring(4);
            var orderCount = $(this).parent().parent().children('.orderId').children('.orderCount-'+orderId).val();
            
            console.log('OrderCount & parcelVal ==>'+orderCount + " | " + parcelVal);
           	var trackingId =  $(this).parent().parent().nextUntil('.orderid-row').last().children('.td-trackingId').children('.trackingId').val();
            console.log('orderid & tracking id : ' + orderId + " | " + trackingId);
            
            if(parcelVal>1 && parcelVal == orderCount){
            	var ajxYodelDeleteUrl = '<%=deletelLabelUrl%>';
			    ajxYodelDeleteUrl += '&orderId='+orderId+'&trackingId='+trackingId;
				$.ajax({
					type : "POST",
					url : ajxYodelDeleteUrl,
					dataType : 'text'
				}).done(function(msg) {
					//ordLblJsonResp.append(gsonObj.toJson(ordrLblMinLst).toString());
					console.dir(msg);
					var resp = $.trim(msg);					
			        var respObj = JSON.parse(resp);
			        $('.orderCount-'+orderId).val(respObj[1].trackingId);
			        
                   $this.parent().parent().nextUntil('.orderid-row').last().remove();
        		});
            }
            parcelVal--;
            $this.next('.parcel').text(parcelVal);
            if(parcelVal==1){
                $(this).addClass('sub-hide');
            }
            
            var currentparcelArray = [];
            initArrayParcel=[];
        	
        	$('.order-count').each(function(){
        		initArrayParcel.push($(this).val());
        	});
        	
        	$('.td-parcel').each(function() {
        		if($(this).children('.parcel')){
        			currentparcelArray.push(parseInt($(this).children('.parcel').text()));
        	    }
        		else{
        			currentparcelArray.push(0);
        		}
        	});
        	
        	var flag=true;
        	  for (var i = 0, len = initArrayParcel.length; i < len; i++){
                  if (parseInt(initArrayParcel[i]) !== currentparcelArray[i] && currentparcelArray[i] >0 && initArrayParcel[i] >0 ){
                      flag=false;
                      break;
                  }
              }
              if(flag===true && confirmBtnStatus){
            	  
            	  $('#confirlQuickDispatch').addClass('primary-CTA-alt').removeClass('disabled');
              }else if(flag===true && !confirmBtnStatus){
            	  $('#confirlQuickDispatch').addClass('disabled').removeClass('primary-CTA-alt');            	  
            	  }
            
          

     		
            return false;
        });

        //dialog for confirm
        $( "#confirm-dialog" ).dialog({
            autoOpen: false,
            resizable: false,
            modal: true,
            width: 400,
            open: function(){
                $(this).siblings('.ui-dialog-titlebar').remove();
            },
            buttons: [
                {
                    text: "Cancel",
                    click: function() {
                        $( this ).dialog( "close" );
                    }
                },
                {
                    text: "Confirm",
                    click: function() {
                        $( this ).dialog( "close" );
                    }
                }
            ]
        });


        

        $("#printlable-btn").click(function( event ) {
        	var confirmCheck = $("#confirlQuickDispatch").hasClass("disabled");
        	console.log(typeof confirmCheck);
        	 if(confirmCheck === true){
        		$("#printlable-message").dialog( "open" );
        	}
            var ajxYodelUrl = '<%=printQuickOrdersURL%>';
            $(".qd-msg").hide().empty();
            var totalSelectedIds = $("#selectedOrderNos").val();
    		var idArray = new Array();
        	idArray = totalSelectedIds.split(",");
      	  	var parcelArray = [];

        	//Inside print btn
        	$('.td-parcel').each(function() {
        		if($(this).children('.parcel')){
        			parcelArray.push(parseInt($(this).children('.parcel').text()));
        	    	}else
        		{
        	          	parcelArray.push(0);
        	       	}
        	});
        	console.log('idArray: '+idArray +'parcels'+parcelArray); //BDz:Testing
        	
        	var totalIds = idArray.length;
        	$("#selectedOrderNos").val(totalSelectedIds);
        	ajxYodelUrl += "&selectedOrderNos=" + idArray+"&selectedQTY=" + parcelArray +" ";
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
    				
    				if (msg == '' || msg.indexOf('##ERROR##') != -1) {
                         if (console) {
                             console.log('MHUB failed to retrieve C&C.' + msg6);
                         }
                       } else {
                     	  console.log('MHUB Passed to retrieve C&C for MULTI SKU');	                        	
                       }
					
					   if(msg2.isAuthFail !== undefined) {
                     	  console.log(msg2.errMsg);
                     	 $('#printlable-message').dialog('close');
                     	  $(".qd-msg").show().text(msg2.errMsg);
                       }
                       else if(msg2.isResNull !== undefined) {
                     	 console.log(msg2.errMsg);
                     	$('#printlable-message').dialog('close');
                     	 $(".qd-msg").show().text(msg2.errMsg);
                       }else {
    					console.log('success: ' +msg2+'length--'+msg2.length);
    					$(".qd-msg").show().text('Please note that partial dispatches cannot be completed via Quick Dispatch.');
    					$.each(msg2,function(i,val){
    						var ndate = new Date(val.collectionDate);
							var dat = ndate.getDate();
		                    if(dat <=9){dat ="0"+dat;}
	                        dateString =dat + "/"+ ("0"+ (ndate.getMonth() + 1)).slice(-2) + "/" + ndate.getFullYear().toString();
							if(val.trackingFlag=="true"){
								
    							$('.qd-'+val.orderId).children('.td-ColecDate').text(dateString);
    							$('.qd-'+val.orderId).children('.td-trackingId').children('.trackingId').val(val.trackingId);
    							$('.qd-'+val.orderId).children('.td-courier').children('.Courier').val('YODEL');
    						}else{
    								
    								var row = $('<tr class="sub-row qd->' + val.orderId + '" id="div-'+val.orderId+'">');
    		                        row.append($('<td colspan="6" class="colspan"></td>'));
    		    		   			row.append($('<td class="td-ColecDate"> ' + dateString+ '</td>'));
    		                        row.append($('<td class="td-trackingId"><input class="form-control trackingId" id="trackingId" name="trackingId-"+"'+val.orderId+'" type="text" value="'+val.trackingId+'" readonly></td>'));
    		                        row.append($('<td class="td-courier"><input class="form-control Courier" id="Courier" name="carrierName-"+"'+val.orderId+'" type="text" value="YODEL" readonly></td><td></td><td></td>'));
    		                        $('.qd-'+val.orderId).last().after(row);
    		                        
    						}
    					});
    						var arrayLength = parcelArray.length;
    						for (var i = 0; i < arrayLength; i++) {
    	    				if(parseInt(parcelArray[i])>1)
    	    					$('.qd-'+idArray[i]).children('.orderId').children('.orderCount-'+idArray[i]).val(parseInt(parcelArray[i]));
    						}
    					var popupUrl = '<%=printQuickAllLabelUrl%>';
    					popupUrl += "&selectedOrderNos=" + idArray+'';
    					console.log("printBtn");
    					newwindow = window.open(popupUrl,'name','height=650,width=735,toolbar=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
    					$('#printlable-message').dialog('close');
        				$('#confirlQuickDispatch').addClass('primary-CTA-alt').removeClass('disabled');
    					newwindow.print();
    					console.log("After");
    					confirmBtnStatus=true;
    					
    				}
    				if(msg2.isAuthFail !== undefined) {
               	  		console.log(msg2.errMsg);
    				}
    				console.log('all done');
    			}).fail(function(jqXHR, textStatus) {
    				if (console){
    					console.log('fail: ' +textStatus);
    				}				
    			}).always(function() {
    				
    				$('.ajax-loader').hide();
    			});		
    		
            event.preventDefault();
        });

        //confirm button click to open the dialog
        $("#confirm-btn").click(function( event ) {
            $("#confirm-dialog").dialog( "open" );

            event.preventDefault();
        });
        
      //Print lables dialog
       
        $("#printlable-message").dialog({
          	autoOpen: false,
            resizable: false,
            modal: true,
            width: 400,
            open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
        });
      
      
      //Print lables dialog
        $("#regenerate-message").dialog({
          	autoOpen: false,
            resizable: false,
            modal: true,
            width: 400,
            height:100,
            open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
        });
      
      
      // confirm button status
      
      var confirmBtnStatus = !($('#confirlQuickDispatch').hasClass('disabled'));
      console.log('Confirm button status =>'+confirmBtnStatus);
      
      
      
    });    
    </script>
    
    <style type="text/css">
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
            font-weight: normal
        }

        .heading{
            font-size: 18px;
            font-weight: bold;
        }
        
        .quick-dispatch-tables thead{
            font-weight: bold;
        }
        
        .quick-dispatch-tables tr td{
            text-align: left;
        }
        
  
        .courier,.trackingId{
            width:140px;
        }
    
        .parcel{
            padding:0 5px;
             position: relative;
            top:-4px;
        }
        

        span.glyphicon{
            font-size: 24px;
        }
        
        a.glyphicon{
            display: inline-block;
            font-size:24px;
            color:#999;
            text-decoration: none;
        }

        a.glyphicon.regenerate{
            color:#DF0627;
        }
        
        a.glyphicon.sub-show{
           color:#999;
            pointer-events: auto;
            
        }
        
        a.glyphicon.sub-hide{
            color:#E6E7E2;
            pointer-events: none;
        }
        
        .td-lines a{
            text-decoration: underline;
        }
        
        .sub-row .colspan{
            border-top:0;
        }

        #confirm-dialog .ui-dialog-titlebar{
            display:none;
        }

        .hide{
            display:none;
        }

        .quick-dispatch-tables .orderid-row td{
            border-top:1px solid #ddd;
        }

    
    
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
  	
  	.quick-dispatch-tables{
 		margin-bottom:0;
  	}
  	
  	.quick-dispatch-tables thead{
		font-size: .9em;
	  	color: #000;
	}
	
	.quick-dispatch-tables thead tr td{
		border-bottom: 2px solid #ddd;
	}
	
	.quick-dispatch-tables tbody tr:firsr-child tr{
		border:0;
	}
	
	.quick-dispatch-tables .th-orderid{
		width:100px;
	}
	
	.quick-dispatch-tables .th-cname{
		width:140px;
	}
	
	.quick-dispatch-tables .th-coldate{
		width:100px;
		text-align:center;
	}
	
	.quick-dispatch-tables .th-address,.quick-dispatch-tables .th-delivery{
		width:130px;
	}
	
	.quick-dispatch-tables .th-parcles{
		width:80px;
	}
	
	.quick-dispatch-tables .th-trackingid{
		width: 160px;
	}
	
	.quick-dispatch-tables .parcel{
		padding:0;
		top:-4px;
		width:16px;
		display: inline-block;
		text-align:center;
	}
	
	.quick-dispatch-tables a.glyphicon{
		font-size: 20px;
	}
	
	.qd-msg{
		padding-top: 8px;
  		color: #F71515;
	}
	
	.quick-dispatch-tables td.td-ColecDate,.quick-dispatch-tables td.td-parcel{
		text-align:center;
	}
	
	.quick-dispatch-tables td.td-parcel,.quick-dispatch-tables td.td-ColecDate, .quick-dispatch-tables td.orderId, .quick-dispatch-tables td.order-lines,.quick-dispatch-tables td.cname,.quick-dispatch-tables td.add1,.quick-dispatch-tables td.dlption{
		padding:16px 0 0 0;
	}
	
	
	.quick-dispatch-tables td.td-parcel .glyphicon{
		top:1px;
	}
	
	
	 .quick-dispatch-tables .orderid-row{
	 	border-top:2px solid #ddd;
	 }
	 
	.quick-dispatch-tables tbody{
		border-bottom:0;
	} 
	
	.bottomHR{
		padding-top:20px;
		border-top:1px solid #c7c6c4;
	}
	
    </style>
</head>
<body>
	<div id="dialog-confirm"></div>
	<div class="innerContainer">
		<form method="post" id="frm1" name="frm1">
			<input type="hidden" value="${selectedOrderNos}"
				name="selectedOrderNos" id="selectedOrderNos" />
			<c:set var="displayHeader" value="0" />
			<c:set var="QuickOrderDispatchDetailsSize"
				value="${fn:length(selectedOrders)}" />
			<section id="body" class="body">
				<section class="container body-section">
					<c:if test="${displayHeader <1}">
						<div class="header-row relative quickDisheader">
							<!-- hiding back arrow to close defect id - 773 -->
							<!--  <div class="backArrow" onclick="BackToPreviousPage()"></div> -->
							<h5>ORDER MANAGEMENT</h5>
						</div>
						<div class="col-xs-3 padding-left-0">
							<div class="row padding-left-0">
								<h1 class="header-alpha quickHeading" id="quick-dispatch-label">Quick
									Dispatch</h1>
							</div>
							
						</div>
						<div class="col-xs-12">
								<div class="qd-msg">
									Please note that partial dispatches cannot be completed via Quick Dispatch.
								</div>
							</div>
					</c:if>
					<div class="row" id="quickDispatch-screen1">
					<div class="col-xs-16">
						<table class="table table-hover quick-dispatch-tables altRow">
							<thead>
								<tr>
								    <th class="th-orderid">ORDER ID</th>
								    <th class="th-cname">CUSTOMER NAME</th>
								    <th class="th-address">ADDRESS LINE 1</th>
								    <th class="th-lines">LINES</th>
								    <th class="th-delivery">DELIVERY OPTION</th>
								    <th class="th-parcles">PARCELS</th>
								    <th class="th-coldate">PICK UP</th>
								    <th class="th-trackingid">TRACKING ID</th>
								    <th class="th-couriers">CARRIER NAME</th>
								    <th class="th-close"></th>
								    <th class="th-regen"></th>
								</tr>
															
							</thead>
							<tbody>
								<c:forEach items="${selectedOrders}" var="QuickDispDetails">
									<tr class="quickDispatchItem orderid-row qd-${QuickDispDetails.orderId}"
										id="div-${QuickDispDetails.orderId}">
										<td class="orderId"><c:out value="${QuickDispDetails.orderId}" /> <input
											type="hidden" name="orderId"
											value="${QuickDispDetails.orderId}" /> <input type="hidden"
											name="ordStatus" value="${QuickDispDetails.orderStatus}" />
											
											<input type="hidden" name="orderCount" value="${QuickDispDetails.orderLblList.size()}" class="order-count orderCount-${QuickDispDetails.orderId}" />
											
											 <c:set var="orderId" value="${QuickDispDetails.orderId}" />
												
										</td>
										<td class="add1"><c:out
												value="${QuickDispDetails.customerFirstName} ${QuickDispDetails.customerLastName}" />
											<input type="hidden" name="firstName"
											value="${QuickDispDetails.customerFirstName}" /></td>
										<td class="cname"><c:out value="${QuickDispDetails.quickShipToAddress}" />
											<input type="hidden" name="shipingTo"
											value="${QuickDispDetails.quickShipToAddress}" /></td>
										<td class="order-lines" style="text-align: center;"><a
											href="#" class="order-line">
												${QuickDispDetails.noOfLines} </a>
											<div class="table col-xs-16 quickOrderDialog">
												<div class="thead row order-titles">
													<h4 class="col-xs-2 item padding-left-0">LINE</h4>
													<h4 class="col-xs-3 item">SKU</h4>
													<h4 class="col-xs-6 item">DESCRIPTION</h4>
													<h4 class="col-xs-3 item text-center">ORDERED QTY</h4>
													<h4 class="col-xs-2 item text-center">OPEN QTY</h4>
												</div>
												<c:forEach items="${QuickDispDetails.orderLineItems}"
													var="entry">
													<div class="table-body border-bottom-none margin-bottom-10">
														<div class="row table-row">
															<div class="col-xs-2 item">
																<c:out value="${entry.value.getOrderLineNumber()}" />
																<input type="hidden" name="lineNumber"
																	value="${entry.value.getOrderLineNumber()}" />
															</div>
															<div class="col-xs-3 item">
																<c:if test="${ empty entry.value.getSku()}">
																	<c:out value="-" />
																	<input type="hidden" name="sku" value="-" />
																</c:if>
																<c:if test="${not empty entry.value.getSku()}">
																	<c:out value="${entry.value.getSku()}" />
																	<input type="hidden" name="sku"
																		value="${entry.value.getSku()}" />
																</c:if>
															</div>
															<div class="col-xs-6 item">
																<c:out value="${entry.value.getDescription()}" />
																<input type="hidden" name="description"
																	value="${entry.value.getDescription()}" />
															</div>
															<div class="col-xs-3 item text-center">
																<span class="badge badge-square"><c:out
																		value="${entry.value.getOrderedQuantity()}" /></span> <input
																	type="hidden" name="orderedQuantity"
																	value="${entry.value.getOrderedQuantity()}" />
															</div>
															<div class="col-xs-2 item text-center">
																<c:out value="${entry.value.getOpenQuantity()}" />
																<input type="hidden" name="openQuantity"
																	value="${entry.value.getOpenQuantity()}" />
																<c:set var="openQuantity"
																	value="${entry.value.getOpenQuantity()}" />
															</div>
														</div>
													</div>
												</c:forEach>
											</div></td>
										<td class="dlption"><c:out value="${QuickDispDetails.deliveryOption}" />
											<input type="hidden" name="deliveryOption"
											value="${QuickDispDetails.deliveryOption}" class="deliveryOption"/></td>
										<c:if test="${QuickDispDetails.orderLblList.size() <= 0 }">
											<c:if
												test="${ QuickDispDetails.deliveryOption eq 'Click & Collect'}">
												<td class="td-parcel">
												<a class="glyphicon glyphicon-minus-sign parcel-sub"
												href="javascript:void(0);"></a> <span class="parcel">1</span>
												<a class="glyphicon glyphicon-plus-sign parcel-add"
												href="javascript:void(0);"></a></td>
												<td class="td-ColecDate">No collection scheduled</td>
												<td class="td-trackingId"><input
												class="form-control trackingId" id="trackingId" type="text" name="trackingId-${QuickDispDetails.orderId}"
												value="" data-cc="false" readonly ></td>
											<td class="td-courier"><input
												class="form-control Courier" id="Courier" type="text" name="carrierName-${QuickDispDetails.orderId}"
												value="" readonly></td>	
											</c:if>
											<c:if
												test="${ QuickDispDetails.deliveryOption ne 'Click & Collect'}">
												<td class="td-parcel">N/A</td>
												<td class="td-ColecDate">N/A</td>
											
											<td class="td-trackingId"><input
												class="form-control trackingId" id="trackingId" type="text" name="trackingId-${QuickDispDetails.orderId}"
												value="" placeholder="Tracking ID"></td>
											<td class="td-courier"><input
												class="form-control Courier" id="Courier" type="text" name="carrierName-${QuickDispDetails.orderId}"
												value="" placeholder="Carrier name"></td>
											</c:if>
											<td ><span class="glyphicon glyphicon-remove-circle remove" onclick="removeThis(this);" id="remove-${QuickDispDetails.orderId}"></span></td>
											<td></td>
										</c:if>
										<c:if test="${QuickDispDetails.orderLblList.size() > 0 }">
										<td class="td-parcel"><a
												class="glyphicon glyphicon-minus-sign parcel-sub"
												href="javascript:void(0);"></a> <span class="parcel">${QuickDispDetails.orderLblList.size()}</span>
												<a class="glyphicon glyphicon-plus-sign parcel-add"
												href="javascript:void(0);"></a></td>
										<c:forEach items="${QuickDispDetails.orderLblList}"
											var="OrderLabelList" varStatus="i">
											<td class="td-ColecDate">
											<jsp:useBean id="now" class="java.util.Date"/>
											<fmt:formatDate value="${OrderLabelList.collectionDate}" var="formattedDate" type="date" pattern="dd/MM/yyyy" />
											<fmt:formatDate value="${OrderLabelList.collectionDate}" var="olddate" type="date" pattern="dd" />
											<fmt:formatDate value="${now}" var="nowdate" type="date"  pattern="dd" />
											${formattedDate}
											</td>
											<td class="td-trackingId"><input
												class="form-control trackingId" id="trackingId" type="text" name="trackingId-${QuickDispDetails.orderId}"
												value="${OrderLabelList.trackingId}" readonly></td>
											<td class="td-courier"><input
												class="form-control Courier" id="Courier" type="text" name="carrierName-${QuickDispDetails.orderId}"
												value="YODEL" readonly></td>
											<c:if test="${i.index ==0 }">
											<td ><span class="glyphicon glyphicon-remove-circle remove" onclick="removeThis(this);" id="remove-${QuickDispDetails.orderId}"></span></td>
											</c:if>
											 
											<c:if test="${olddate < nowdate   || OrderLabelList.collectionDate.month lt now.month || OrderLabelList.collectionDate.year lt now.year  }">
												<c:if test="${i.index >0 }"><td class="td-empty"></td></c:if>
											<td class="td-regenerate"><a
												class="glyphicon glyphicon-repeat regenerate"
												href="javascript:void(0);"></a></td>
											</c:if>
												<c:if test="${olddate == nowdate  && OrderLabelList.collectionDate.month eq now.month && OrderLabelList.collectionDate.year eq now.year  }">
												<c:if test="${i.index >0 }"><td></td></c:if>
											<td></td>
											</c:if>
											<c:if test="${olddate > nowdate   || OrderLabelList.collectionDate.month gt now.month || OrderLabelList.collectionDate.year gt now.year  }">
												<c:if test="${i.index >0 }"><td></td></c:if>
											<td></td>
											</c:if>
											</tr>
											<c:if test="${i.index <QuickDispDetails.orderLblList.size()-1 }">
											<tr class="sub-row qd-${QuickDispDetails.orderId}" id="div-${QuickDispDetails.orderId}">
												<td colspan="6" class="colspan"></td>
											</c:if>
										</c:forEach>
										</c:if>

								</c:forEach>
							</tbody>
						</table>

						<div class="col-xs-16 bottomHR"></div>
						<div class="pull-right">
							<!--  commented in R2 as this cancel button cancels the quick dispatch operation and not enables the cancel order operation -->
							<%-- <c:if test="${isCancelOdrsVisible}"> --%>
							<button type="button" id="cancelDispatch_btn"
								class="btn secondary-CTA">Cancel</button>
							<button id="printlable-btn" class="btn primary-CTA">Print
								Labels</button>
							<button type="button" id="confirlQuickDispatch"
								class="btn primary-CTA-alt">Confirm</button>
							<!--<aui:button type="button" id="confirlQuickDispatch" class="btn  ml-10" value="Confirm" />-->
						</div>
					</div>
					</div>

					<div class="updateBox hide">
						<c:if
							test="${isQuickDispatchMultiOdrsVisible && isSubmitFinalOdrVisible}">
							<%@ include file="/WEB-INF/views/orderBasket.jsp"%>
						</c:if>
					</div>
				</section>
			</section>
		</form>
	</div>
	<div id="orderLinesDialog"></div>
	<div id="printlable-message" title="Generating labels and Tracking IDs">
           Please wait while we get the Tracking IDs for your Click and Collect orders. When complete you will be able to print them.
    </div>
    <div id="regenerate-message" title="Please Wait...">
           Your new label is being generated.
    </div>
	
	
</body>
</html>