var activePaymentInstrumentsInitial = [];
var inactivePaymentInstrumentsInitial = [];
var isDuplicateSubmit = false;

function bootstrapAuth() {
	handleAjaxAndCsrf();
	handleCancels();
	handleLogin();
	handlePaymentInstrumentSubmission();
};

function handleAjaxAndCsrf() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
		xhr.setRequestHeader('Referer', controllerContextPath);
		$.mobile.loading( 'show');
	});
	$(document).ajaxComplete(function(event,xhr,options) {
		$.mobile.loading( 'hide');
	});
	$(document).ajaxError(function(event,xhr,options) {
		$.mobile.loading( 'hide');
	});
};

function handleCancels() {
	$('#cancelLogin').click(function(event){
		event.preventDefault();
		window.location.href=staticContextPath+'/'+$('#redirectUrl').val();
	});
	$('#cancelRegistration').click(function(event){
		event.preventDefault();
		window.location.href=staticContextPath+'/'+$('#redirectUrl').val();
	});
	$('#cancelInstrumentSave').click(function(event) {
		event.preventDefault();
		$.mobile.pageContainer.pagecontainer("change", "#pageSelectPi", {
			changeHash : false,
			transition : 'slide'
		});
	});
	$('#cancelEmptyPi').click(function(event) {
		event.preventDefault();
		$.mobile.pageContainer.pagecontainer("change", "#pageSelectPi", {
			changeHash : false,
			transition : 'slide'
		});
	});	
	
};
function handleLogin() {
	$("#login").click(function(event) {
		if(!isDuplicateSubmit) {
			isDuplicateSubmit = true;
			var validator = $("#frmLogin").validate({
				errorPlacement : function(error, element) {
				error.insertAfter(element.parent());
			}
		});
		if ($('#frmLogin').valid()) {
			doLogin();
		}
	}
	});
};

function doLogin() {
	var usernameVal = $("#username").val();
	var passwordVal = $("#password").val();
	var iisnVal = $("#iisn").val();
	var tridVal = $("#trId").val();
	var ciidVal = $("#ciid").val();
	var params = {
		userId : usernameVal,
		password : passwordVal,
		iisn : iisnVal,
		trId : tridVal,
		ciid : ciidVal
	};
	$.post(controllerContextPath + "/login", params, function(data) {
		if (data.success) {
			
			if( data.paymentInstruments ==null || $.isEmptyObject(data.paymentInstruments)) {
				$('#errorDialogPi p').html(data.errorMessage);
				$('#cancelRegistration').hide();
				$('#submitPi').hide();
				navigateToPi();
				$("#pageSelectPi").on( "pageshow", function( event ) { 
					$('#errorDialogPi').popup('open');
					$('#cancelEmptyPi').click(function(event){
						event.preventDefault();
						window.location.href=staticContextPath+'/'+data.redirectUrl;
					});
				} );
				
		} else {
			$('#cancelRegistration').show();
			$('#submitPi').show();
			var paymentInstrumentHtml = '';
			var paymentInstrumentsVal = data.paymentInstruments;			
			for ( var i = 0; i < paymentInstrumentsVal.length; ++i) {
				var checkBoxIndex = 'chk' + i;
				var checked = paymentInstrumentsVal[i].active;				
				var paymentInstrumentInfo = 'Account ending '
					+ paymentInstrumentsVal[i].panLastFour;
				if (paymentInstrumentsVal[i].nickName != null && paymentInstrumentsVal[i].nickName !='') {
				paymentInstrumentInfo = paymentInstrumentsVal[i].nickName;
				}

			if (checked) {
				activePaymentInstrumentsInitial.push(paymentInstrumentsVal[i].id.toString());
				paymentInstrumentHtml += '<input type="checkbox"'
						+ ' checked="' + checked + '"' + ' name="'
						+ checkBoxIndex + '" id="' + checkBoxIndex
						+ '" class="custom" /><label for="' + checkBoxIndex
						+ '">' + paymentInstrumentInfo + '</label>';
			} else {
				inactivePaymentInstrumentsInitial.push(paymentInstrumentsVal[i].id.toString());
				paymentInstrumentHtml += '<input type="checkbox" name="'
						+ checkBoxIndex + '" id="' + checkBoxIndex
						+ '" class="custom" /><label for="' + checkBoxIndex
						+ '">' + paymentInstrumentInfo + '</label>';
			}
			paymentInstrumentHtml += '<input type="hidden" id="id' + i
					+ '" value="' + paymentInstrumentsVal[i].id + '">';
		}
		$("fieldset").append(paymentInstrumentHtml);
		navigateToPi();
		$('.ui-controlgroup-controls').css('width', '100%');
		} } else {				
			$('#password').val('');
			$('#errorHeading p').html(data.errorMessage);
			$('#errorDialog').popup('open');
			$('#errorDialog').on('popupafterclose',function(event,ui) {
				$('#errorDialog').off('popupafterclose');
				if (data.locked) {
					window.location.href=staticContextPath+'/'+$('#redirectUrl').val();
				} else {
					isDuplicateSubmit = false;
				}
			});
		}
		
	});
};

function navigateToPi() {
	$.mobile.pageContainer.pagecontainer("change", "#pageSelectPi", {
		changeHash : false,
		transition : 'slide'
	});
}


function handlePaymentInstrumentSubmission() {
	
	var activePaymentInstrumentListValues = [];
	var inactivePaymentInstrumentListValues = [];	
	
	$('#submitPi').off('click');
	$('#submitPaymentInstruments').off('click');
	$('#submitPi').click(function() {
		var errorMessage = "Please edit Payment Instrument to save changes";
		$("input:checked").each(function() {
			var checkBoxId = '#id' + $(this).attr('id').replace('chk', '');
			activePaymentInstrumentListValues.push($(checkBoxId).val());
		});

		$('input:checkbox:not(:checked)').each(function() {
			var uncheckBoxId = '#id' + $(this).attr('id').replace('chk', '');
			inactivePaymentInstrumentListValues.push($(uncheckBoxId).val());
		});
		if(isPaymentInstrumentsAddedOrRemoved(activePaymentInstrumentListValues,inactivePaymentInstrumentListValues)) {
			$('#confirmDialog').popup('open');
			$('#confirmDialog').on('popupafterclose',function(event,ui) {
				$('#confirmDialog').off('popupafterclose');
			});
			activePaymentInstrumentListValues = [];
			inactivePaymentInstrumentListValues = [];
		} else {
			$('#errorDialogPi p').html(errorMessage);
			$('#errorDialogPi').popup('open');
			$('#errorDialogPi').on('popupafterclose',function(event,ui) {
				$('#errorDialogPi').off('popupafterclose');
			});
			activePaymentInstrumentListValues = [];
			inactivePaymentInstrumentListValues = [];
		}
		
	});
	$('#submitPaymentInstruments').click(function(event) {
		event.preventDefault();
		var iisnVal = $("#iisn").val();
		var ciidVal = $("#ciid").val();
		var tridVal = $("#trId").val();
		$("input:checked").each(function() {
			var checkBoxId = '#id' + $(this).attr('id').replace('chk', '');
			activePaymentInstrumentListValues.push($(checkBoxId).val());
		});

		$('input:checkbox:not(:checked)').each(function() {
			var uncheckBoxId = '#id' + $(this).attr('id').replace('chk', '');
			inactivePaymentInstrumentListValues.push($(uncheckBoxId).val());
		});
		var data = {
			iisn : iisnVal,
			ciid : ciidVal,
			trId : tridVal,
			activePaymentInstruments : activePaymentInstrumentListValues,
			inactivePaymentInstruments : inactivePaymentInstrumentListValues
		};
		$.ajax({
			url : controllerContextPath + "/PaymentInstrumentService",
			type : "POST",
			data : JSON.stringify(data),
			contentType : "application/json; charset=UTF-8",
			dataType : 'json',

		}).done(function(response) {
			if (response.redirectUrl!=null) {
				window.location.href=staticContextPath+'/'+response.redirectUrl;
			}
			else {
				window.location.href=staticContextPath+'/'+$('#redirectUrl').val();
			}

		});
	});
};

function isPaymentInstrumentsAddedOrRemoved(activePaymentInstruments,inactivePaymentInstruments) {
	var isActivated = arraysIdentical(activePaymentInstrumentsInitial,activePaymentInstruments);
	var isDeactivated = arraysIdentical(inactivePaymentInstrumentsInitial,inactivePaymentInstruments);
	if(isActivated || isDeactivated) return false;
	else return true ;
};

function arraysIdentical(arrayOne,arrayTwo) {
	var i = arrayOne.length;
    if (i != arrayTwo.length) return false;
    while (i--) {
        if (arrayOne[i] !== arrayTwo[i]) return false;
    }
    return true;
};

