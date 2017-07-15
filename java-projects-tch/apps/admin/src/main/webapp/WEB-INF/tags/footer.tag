<%@ attribute name="jsFiles" required="false" rtexprvalue="true"%>
<%@ attribute name="docReadyFn" required="false" rtexprvalue="true"%>
<%@ attribute name="sessionTimerNotRequired" required="false" rtexprvalue="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

</div>
<div id="TCH_SessionTimeout_Modal" style="display:none">
		<div class="TCH_pageHeader" ><h3>Session Expiry</h3></div>
		<div class="savemodalContent">
			<div class="TCH_labelValue">
				<label for="extendSession">Your session is about to expire.</label>
			</div>
		</div>
		<div id="savemodalContentBtn">
			<input id="extendSession" class="TCH_button" type="button" value="Stay Logged In"/>
			<input id="closeSession" class="TCH_button" type="button" value="Logout"/>
		</div>
	</div>
<!-- This is common overall application -->
<div id="TCH_footer">
	<div id="TCH_footerDiv">
		<span class="TCH_copyright">&copy; 2014 The clearing House Payments Company L.L.C.</span>
	</div>
</div>
<!-- End -->

<script src="${staticContextPath}/js/jquery-1.11.1.min.js"></script>
<script src="${staticContextPath}/js/sessionTimer.js"></script>
<script src="${staticContextPath}/js/common.js"></script>

<%-- Import all the explicitly specified (as attribute) JS files here. comma seperated list --%>
<c:if test="${not empty jsFiles}">
	<c:forEach var="jsFile" items="${jsFiles}">
		<script src="${staticContextPath}/js/${jsFile}.js"
			type="text/javascript"></script>
	</c:forEach>
</c:if>

<script>
    var controllerContextPath = "${controllerContextPath}";
    
   	$(document).ready(function(){
   		<c:choose>
	   		<c:when test="${sessionTimerNotRequired==null}">
	   			setupSessionTimeout();
	   		</c:when>
	   		<c:otherwise>
	   			reloginAfter(15);
	   		</c:otherwise>
	   	</c:choose>
   		
   		setupLogout();
   		handleWindowResize();
   		handleAjaxCsrf();
   		<c:if test="${docReadyFn!=null}">
   			${docReadyFn}();
   		</c:if>
   	});
   
</script>
</body>
</html>