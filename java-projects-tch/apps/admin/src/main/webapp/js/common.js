<!-- All Commmon Javascript functions go here-->

function setupSessionTimeout() {
	
	var sessionTimer=new SessionTimer(15,14,3,expireSession,'TCH_SessionTimeout_Modal',controllerContextPath+'/keepAlive');
	sessionTimer.init();
	
	$('#extendSession').click(function() {
		sessionTimer.reset();
		$.modal.close();
	});
	
	$('#closeSession').click(logout);
};

function setupLogout() {
	$('#logout').click(function(){
		logout();
	});
};

function expireSession() {
	$('#isSessionExpired').val('Y');
	document.logOutForm.submit();
};

function logout() {
	document.logOutForm.submit();
};

function handleWindowResize() {
	/* For Login Validation */
	$('#TCH_btnSubmit').click(function(){
		if($('#TCH_loginName').val() == '' && ($('#TCH_loginPass').val() == '' )) {
			$('#TCH_fieldError').css('visibility','visible');
		} else {
			$('#TCH_fieldError').css('visibility','hidden');
		}
	});
};

function handleAjaxCsrf() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
};

function getAllFormElementsById() {
	var params={};
	$('input').each(function(){
		params[$(this).attr('id')]=$(this).val();
	});
	$('select').each(function(){
		params[$(this).attr('id')]=$(this).val();
	});

	return params;
};

function relogin() {
	document.loginForm.method='GET';
	document.loginForm.submit();
};

function reloginAfter(timeInMinutes) {
	window.setTimeout(relogin,timeInMinutes*60000);
};