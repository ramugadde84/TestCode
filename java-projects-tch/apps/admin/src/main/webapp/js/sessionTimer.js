function SessionTimer(sessionIdleDuration,userWarningTimer,sessionKeepAliveTimer,logoutFunction,modalDivId,keepAliveUrl) {
	var self=this;
	
	self.sessionIdleDuration=sessionIdleDuration;
	self.userWarningTimer=userWarningTimer;
	self.sessionKeepAliveTimer=sessionKeepAliveTimer;
	self.timerInterval=60000;
	self.idleMinutes=0;
	self.logoutFunction=logoutFunction;
	self.lastSentRequestTime=new Date().getTime();
	self.modalDivId=modalDivId;
	self.keepAliveUrl=keepAliveUrl;
	
	
	self.init=function() {
		window.setInterval(self.idleTimer,self.timerInterval);
		self.handleEvents();
	};
	
	self.reset=function() {
		self.idleMinutes=0;
	};
	
	self.idleTimer=function() {
		self.idleMinutes++;
		if (self.idleMinutes>=self.sessionIdleDuration) {
			//Log out
			logoutFunction();
		}
		else
		if (self.idleMinutes>=self.userWarningTimer) {
			// To show Modal.
			$(this).closest('tr').addClass('ui-state-highlight');
			$('#'+modalDivId).modal({ dataCss: { height: "200px", width: "500px" }});
		}
	};
	
	self.handleEvents=function() {
		$('select').change(self.processActivity);
		$(':button').click(self.processActivity);
		$(':text').keypress(self.processActivity);
		$(':checkbox').click(self.processActivity);
		$('textarea').keypress(self.processActivity);
		$(document).ajaxComplete(function(){
			self.lastSentRequestTime=new Date().getTime();
		});
	};
	
	self.processActivity=function() {
		self.idleMinutes=0;
		var currTime=new Date().getTime();
		if (((currTime-self.lastSentRequestTime)/1000/60) > self.sessionKeepAliveTimer) {
			$.post(self.keepAliveUrl,{},function(data){
				self.lastSentRequestTime=new Date().getTime();
			});
		}
	};
};