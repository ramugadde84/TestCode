/**
 * Token Rules which is related to PAN_BINS table of database.
 */
function setupTokenRules() {
	$('#TCH_edit_tckns').click(function(e) {
		e.preventDefault();
		makeAjaxCall();
		return false;
	});
	
	/**
	 * We are saving token Rules in database.
	 */
	$("#TCH_saveChange").click(function(){
	    
		$('#TCH_webTknsModal #TCH_fieldMessage').html('');
		$('#tokenRulesErrors').html('');
        $('#tokenRulesErrors1').html('');
		$('#tokenRulesErrors2').html('');
		$('#tokenRulesErrors3').html('');
		$('#tokenRulesErrors4').html('');
		$('#tokenRulesErrors5').html('');
		$('#tokenRulesErrors6').html('');
		$('#tokenRulesErrors7').html('');
		$('#tokenRulesErrors8').html('');
		var binValue=$("#TCH_push option:selected").val();
	    var tokenPushValue=$("#tokenPushOnCreation:checked").val();
	    var tokenPullValue=$("#tokenPullEnable:checked").val();
		var tokenExpirationMinutes=$("#tokenExpirationMinutes").val();
		var numberOfDetokenizedRequests=$("#numberOfDetokenizedRequests").val();
		var tokenExpireAfterSingleDollarAmountTransaction=$("#tokenExpireAfterSingleDollarAmountTransaction").val();
		var dpiPerDay=$("#dpiPerDay").val();
		var dollarAmountForExpireDpi=$("#dollarAmountForExpireDpi").val();
		var timeInMinutesAfterExpirationForReuse=$("#timeInMinutesAfterExpirationForReuse").val();
		var tokenId=$("#hidden").val();
		var iisn=$("#iisn").val();
		 var myObj = 
				 
				  {
				     'id':tokenId,
				     'realBin':binValue, 
					 'dollarAmountForExpireDpi':dollarAmountForExpireDpi,
					 'dpiPerDay':dpiPerDay,
					 'numberOfDetokenizedRequests':numberOfDetokenizedRequests,
					 'timeInMinutesAfterExpirationForReuse':timeInMinutesAfterExpirationForReuse,
					 'tokenExpirationMinutes':tokenExpirationMinutes,
					 'tokenExpireAfterSingleDollarAmountTransaction':tokenExpireAfterSingleDollarAmountTransaction,
					 'tokenPullEnable':tokenPullValue,
					 'tokenPushOnCreation':tokenPushValue,
					 'iisn':iisn
						 
				  }; 
		 
		
		 $.ajax({
		   method:"POST",
		   url:controllerContextPath+'/issuer/tokenRules/saveTokenRules',
		   data:myObj,
		   }).done(function (data) {
			   if(data!=null){
                   if(data.success==true){
            		   $('#TCH_webTknsModal #TCH_fieldMessage').html(data.successMessage);
            	   }
            	   else if(data.success==false){
            		       if(data.dollarAmountForExpireDpiErrorMessage!=null){
                		     $("#tokenRulesErrors1").html(data.dollarAmountForExpireDpiErrorMessage);
                		   }
                		   if(data.dpiPerDayErrorMessage!=null){
                		      $("#tokenRulesErrors2").html(data.dpiPerDayErrorMessage);
                		   }
                		   if(data.numberOfDetokenizedRequestsErrorMessage!==null){
                			   $("#tokenRulesErrors4").html(data.numberOfDetokenizedRequestsErrorMessage); 
                		   }
                		   if(data.realBinErrorMessage!==null){
                			   $("#tokenRulesErrors8").html(data.realBinErrorMessage); 
                		   }
                		   if(data.timeInMinutesAfterExpirationForReuseErrorMessage!==null){
                			   $("#tokenRulesErrors").html(data.timeInMinutesAfterExpirationForReuseErrorMessage);  
                		   }
                		   if(data.tokenExpirationMinutesErrorMessage!==null){
                			   $("#tokenRulesErrors5").html(data.tokenExpirationMinutesErrorMessage); 
                		   }
                		   if(data.tokenExpireAfterSingleDollarAmountTransactionErrorMessage!==null){
                			   $("#tokenRulesErrors3").html(data.tokenExpireAfterSingleDollarAmountTransactionErrorMessage);  
                		   }
                		   if(data.tokenPullEnableErrorMessage!==null){
                			   $("#tokenRulesErrors6").html(data.tokenPullEnableErrorMessage); 
                		   }
                		   if(data.tokenPushOnCreationErrorMessage!==null){
                			   $("#tokenRulesErrors7").html(data.tokenPushOnCreationErrorMessage);  
                		   }
            		   
                   }//ended else if
               }
           });
	});
	
};

/**
 * This method is used to populate the Bins in options box.
 * @param data - contains bins data.
 */
function fillDataInModal(data) {
    var listItems = '<option selected="selected">Select BIN</option>';
	for ( var i = 0; i < data.bins.length; i++) {
		listItems += "<option>" + data.bins[i] + "</option>";
	}
	$('#TCH_push').html(listItems);

}

function fetchBinRules() {
	var iisn = $("#iisn").val();
    $('#TCH_push').change(
			function() {
				/**
				 * From Drop Down when we change one bin to another bin we have to remove all the 
				 * validation errors,and also we are hiding success message.
				 */
				$("#tokenRulesErrors").html('');
				$("#tokenRulesErrors1").html('');
				$("#tokenRulesErrors2").html('');
				$("#tokenRulesErrors3").html('');
				$("#tokenRulesErrors4").html('');
				$("#tokenRulesErrors5").html('');
				$("#tokenRulesErrors6").html('');
				$("#tokenRulesErrors7").html('');
				$("#tokenRulesErrors8").html('');
				$('#tokenExpirationMinutes').val('');
				$("#dpiPerDay").val('');
			    $("#dollarAmountForExpireDpi").val('');
			    $("#timeInMinutesAfterExpirationForReuse").val('');
			    $("#tokenExpireAfterSingleDollarAmountTransaction").val('');
				$("#numberOfDetokenizedRequests").val('');
				$('#TCH_webTknsModal #TCH_fieldMessage').html('');
                
                /**
                 * Form DropDown when we wont select any Bin then we have to drop two radio boxes,means
                 * no any radio box want's to select. 
                 */
          	    var bin = $("#TCH_push option:selected").text();
          	    if(bin=='Select BIN') {
                   $('input:radio[name=tokenPushOnCreation]').removeAttr('checked','true');
                   $('input:radio[name=tokenPullEnable]').removeAttr('checked','true');
                }
                
				/**
				 * Here we are doing Ajax call to TokenRequest controller method,we are sending bin value 
				 * and iisn as request.
				 */
                $.get(controllerContextPath + '/issuer/tokenRules', {
					'iisn': iisn,
					'bin' : bin
				}, function(data) {
					$('#tokenExpirationMinutes').val(
							data.tokenRule.tokenExpirationMinutes);
					if (data.tokenRule.tokenPushOnCreation == 1) {
						$('input:radio[name=tokenPushOnCreation]')[0].checked = true;
					} else if(data.tokenRule.tokenPushOnCreation == 0){
						$('input:radio[name=tokenPushOnCreation]')[1].checked = true;
					}
					if (data.tokenRule.tokenPullEnable == 1) {
						$('input:radio[name=tokenPullEnable]')[0].checked = true;
					} else if(data.tokenRule.tokenPullEnable == 0)  {
						$('input:radio[name=tokenPullEnable]')[1].checked = true;
					}
					$("#dpiPerDay").val(data.tokenRule.dpiPerDay);
				    $("#dollarAmountForExpireDpi").val(data.tokenRule.dollarAmountForExpireDpi);
				    $("#timeInMinutesAfterExpirationForReuse").val(data.tokenRule.timeInMinutesAfterExpirationForReuse);
				    $("#tokenExpireAfterSingleDollarAmountTransaction").val(data.tokenRule.tokenExpireAfterSingleDollarAmountTransaction);
					$("#numberOfDetokenizedRequests").val(data.tokenRule.numberOfDetokenizedRequests);
				    $("#hidden").val(data.tokenRule.id);
				});
				
				
			});

}

function makeAjaxCall() {

	var iisn = $("#iisn").val();

	$.get(controllerContextPath + '/issuer/tokenRules', {
		'iisn' : iisn
	}, function(data) {
		if (data.success) {
			fillDataInModal(data);
			$(this).closest('tr').addClass('ui-state-highlight');
			$('#TCH_webTknsModal').modal({
				dataCss : {
					height : "640px",
					width : "950px"
				}
			});
			fetchBinRules();
		} 
	});

}
